package com.lusberc.billwallet.LogIn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.lusberc.billwallet.MainActivity;
import com.lusberc.billwallet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.lusberc.billwallet.Utilities.GeneralValidations;

public class FragmentSignUp extends Fragment {

    private FirebaseAuth mAuth;
    private String TAG = "FragmentSignUp";
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
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        //Loading Page
        dialog = new ProgressDialog(view.getContext());

        setupUI(view);
        return view;
    }

    private void setupUI(final View view){
        Button btnSignUpNow = view.findViewById(R.id.btnSignUpNow);

        btnSignUpNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txtEmail = view.findViewById(R.id.txtSignEmail);
                EditText txtPassword = view.findViewById(R.id.txtSignPassword);
                EditText txtConfirmPassword = view.findViewById(R.id.txtConfirmSignPassword);

                if (GeneralValidations.validateSignUpFields(view, txtEmail, txtPassword, txtConfirmPassword)){
                    dialog.setMessage(getString(R.string.loadingPage));
                    dialog.setCancelable(false);
                    dialog.show();

                    mAuth.createUserWithEmailAndPassword(txtEmail.getText().toString(), txtPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    startApp(view);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                        Toast.makeText(getActivity().getApplicationContext(),"Ya existe un usuario con éste correo.", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(getActivity().getApplicationContext(), "No fue posible registrar el usuario.", Toast.LENGTH_SHORT).show();
                                        startApp(null);
                                    }
                                }
                            }
                        });
                    }
                }
        });
    }

    private void startApp(View view){
        if(view != null) {
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            //intent.putExtra("RESULT_VALUE", resultado.toString());
            getActivity().startActivity(intent);
            getActivity().finish();
        }
        dialog.hide();
    }
}
