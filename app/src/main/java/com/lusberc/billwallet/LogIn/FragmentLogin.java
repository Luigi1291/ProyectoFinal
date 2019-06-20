package com.lusberc.billwallet.LogIn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lusberc.billwallet.MainActivity;
import com.lusberc.billwallet.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLogin.setEnabled(false);
                btnLogin.setClickable(false);
                startApp(view);
            }
        });

        final Button btnSignUp = view.findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSignUp.setEnabled(false);
                btnSignUp.setClickable(false);

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
        Intent intent = new Intent(view.getContext(), MainActivity.class);
        //intent.putExtra("RESULT_VALUE", resultado.toString());
        getActivity().startActivity(intent);
        getActivity().finish();
    }
}
