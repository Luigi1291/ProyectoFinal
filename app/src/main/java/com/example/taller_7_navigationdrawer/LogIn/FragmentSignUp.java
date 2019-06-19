package com.example.taller_7_navigationdrawer.LogIn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.taller_7_navigationdrawer.MainActivity;
import com.example.taller_7_navigationdrawer.R;

public class FragmentSignUp extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        setupUI(view);
        return view;
    }

    private void setupUI(View view){
        Button btnSignUpNow = view.findViewById(R.id.btnSignUpNow);

        btnSignUpNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                //intent.putExtra("RESULT_VALUE", resultado.toString());
                startActivity(intent);
            }
        });
    }
}
