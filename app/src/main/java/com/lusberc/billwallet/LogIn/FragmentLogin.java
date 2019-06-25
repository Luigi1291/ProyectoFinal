package com.lusberc.billwallet.LogIn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.GoogleAuthProvider;
import com.lusberc.billwallet.MainActivity;
import com.lusberc.billwallet.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lusberc.billwallet.Utilities.GeneralValidations;

import static android.support.constraint.Constraints.TAG;

public class FragmentLogin extends Fragment {

    private FirebaseAuth mAuth;
    private CallbackManager mCallbackManager;
    static final int GOOGLE_SIGN = 123;
    GoogleSignInClient mGoogleSignIn;

    ProgressDialog dialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        //Loading Page
        dialog = new ProgressDialog(view.getContext());

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            startApp(view);
        }
        else
            setupUI(view);

        return view;
    }

    private void setupUI(View view){
        final Button btnLogin = view.findViewById(R.id.btnLogin);
        final EditText txtUser = view.findViewById(R.id.txtLoginEmail);
        final EditText txtPassw = view.findViewById(R.id.txtLoginPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if(GeneralValidations.validateLoginFields(view, txtUser, txtPassw)) {
                    dialog.setMessage(getString(R.string.loadingPage));
                    dialog.setCancelable(false);
                    dialog.show();

                    mAuth.signInWithEmailAndPassword(txtUser.getText().toString(), txtPassw.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    startApp(view);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(getActivity().getApplicationContext(), "Autenticación fallida.",
                                            Toast.LENGTH_SHORT).show();
                                    startApp(null);
                                }
                            }
                        });
                }

            }
        });

        final Button btnSignUp = view.findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Llamar fragment SIGNUP sin sobreponerse
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.replace(android.R.id.content, new FragmentSignUp());
                ft.addToBackStack(null); //Add fragment in back stack
                ft.commit();
            }
        });

        // Configurar SignIn button de facebook
        FacebookConfiguration(view);

        // Configurar SignIn button de Google
        GoogleConfiguration(view);
    }

    private void GoogleConfiguration(View view){

        SignInButton btnGoogle = view.findViewById(R.id.btnGoogle);
        TextView textView = (TextView) btnGoogle.getChildAt(0);
        textView.setText(getString(R.string.btnLoginGoogle));

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setMessage(getString(R.string.loadingPage));
                dialog.setCancelable(false);
                dialog.show();

                // Configure Google Sign In
                GoogleSignInOptions mGoogleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();

                mGoogleSignIn = GoogleSignIn.getClient(getActivity(), mGoogleSignInOptions);
                Intent signIntent = mGoogleSignIn.getSignInIntent();
                startActivityForResult(signIntent, GOOGLE_SIGN);
            }
        });
    }

    private void FacebookConfiguration(final View view){
        // HASH KEY
        // Descargar OpenSSL from https://code.google.com/archive/p/openssl-for-windows/downloads
        // keytool -exportcert -alias androiddebugkey -keystore "C:\Users\LUIS\.android\debug.keystore" | "C:\Data\OpenSSL\bin\openssl" sha1 -binary | "C:\Data\OpenSSL\bin\openssl" base64

        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = view.findViewById(R.id.btnFacebook);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken(), view);
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token, final View view) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            startApp(view);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getActivity().getApplicationContext(), "Autenticación Fallida.",
                                    Toast.LENGTH_SHORT).show();
                            startApp(null);
                        }
                    }
                });
    }
    //Launch application after succesfull SignIn
    private void startApp(View view){
        if(view != null) {
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            //intent.putExtra("RESULT_VALUE", resultado.toString());
            getActivity().startActivity(intent);
            getActivity().finish();
        }
        dialog.hide();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == GOOGLE_SIGN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if(account != null){
                    firebaseAuthWithGoogle(account);
                }
            }
            catch (ApiException e){
                Log.w(TAG, "Google sign in failed", e);
            }
        }
        else {
            // Pass the activity result back to the Facebook SDK
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            startApp(getView());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getActivity().getApplicationContext(), "Autenticación Fallida.",
                                    Toast.LENGTH_SHORT).show();
                            startApp(null);
                        }
                    }
                });
    }
}
