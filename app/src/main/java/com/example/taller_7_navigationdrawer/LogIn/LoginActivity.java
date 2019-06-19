package com.example.taller_7_navigationdrawer.LogIn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.taller_7_navigationdrawer.MainActivity;
import com.example.taller_7_navigationdrawer.R;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupUI();
    }

    private void setupUI(){
        final Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLogin.setEnabled(false);
                btnLogin.setClickable(false);
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                //intent.putExtra("RESULT_VALUE", resultado.toString());
                startActivity(intent);
            }
        });

        final Button btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSignUp.setEnabled(false);
                btnSignUp.setClickable(false);

                //Llamar fragment SIGNUP sin sobreponerse

            }
        });
    }
}
