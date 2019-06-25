package com.lusberc.billwallet.LogIn;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lusberc.billwallet.R;

public class LoginActivity extends AppCompatActivity {
    FragmentLogin fragLogin = new FragmentLogin();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, fragLogin)
                .commit();
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fragLogin.onActivityResult(requestCode, resultCode, data);
    }
}
