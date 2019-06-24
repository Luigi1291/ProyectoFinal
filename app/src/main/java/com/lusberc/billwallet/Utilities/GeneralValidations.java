package com.lusberc.billwallet.Utilities;

import android.view.View;
import android.widget.EditText;
import com.lusberc.billwallet.R;

public class GeneralValidations {
    public static boolean validateLoginFields(View view,EditText txtEmail, EditText txtPassword){
        boolean isOk = true;
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        //Email
        if(email.isEmpty()){
            txtEmail.setError(view.getContext().getString(R.string.invalid_email));
            isOk = false;
        }
        else{
            String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
            if(!email.matches(regex)){
                txtEmail.setError(view.getContext().getString(R.string.invalid_format));
                isOk = false;
            }
        }
        //Password
        if(password.isEmpty()){
            txtPassword.setError(view.getContext().getString(R.string.invalid_passw));
            isOk = false;
        }
        return isOk;
    }

    public static boolean validateSignUpFields(View view, EditText txtEmail, EditText txtPassword, EditText txtConfirmPassw){
        boolean isOk = true;
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        String confirmPassword = txtConfirmPassw.getText().toString();
        //Email
        if(email.isEmpty()){
            txtEmail.setError(view.getContext().getString(R.string.invalid_email));
            isOk = false;
        }
        else {
            String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
            if (!email.matches(regex)) {
                txtEmail.setError(view.getContext().getString(R.string.invalid_format));
                isOk = false;
            }
        }

        //Password
        if(password.isEmpty()){
            txtPassword.setError(view.getContext().getString(R.string.invalid_passw));
            isOk = false;
        }
        else
            if(password.length() < 6){
                txtPassword.setError(view.getContext().getString(R.string.invalid_passwLength));
                isOk = false;
            }

        //Confirm Password
        if(password != confirmPassword){
            txtConfirmPassw.setError(view.getContext().getString(R.string.invalid_confirmPassw));
            isOk = false;
        }
        return isOk;
    }
}
