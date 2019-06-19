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
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, new FragmentLogin())
                .commit();
    }
}
