package com.lusberc.billwallet.LogIn;

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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.lusberc.billwallet.MainActivity;
import com.lusberc.billwallet.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.support.constraint.Constraints.TAG;

public class FragmentLogin extends Fragment {

    private FirebaseAuth mAuth;

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
                if(txtUser.getText().toString().isEmpty() || txtPassw.getText().toString().isEmpty()){
                    Toast.makeText(getActivity().getApplicationContext(), "Debe ingresar sus credenciales.", Toast.LENGTH_SHORT).show();
                }
                else{
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
    }

    //Launch application after succesfull SignIn
    private void startApp(View view){
        if(view != null) {
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            //intent.putExtra("RESULT_VALUE", resultado.toString());
            getActivity().startActivity(intent);
            getActivity().finish();
        }
    }
}
