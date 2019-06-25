package com.lusberc.billwallet.Utilities;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import com.lusberc.billwallet.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static String extractBillDate(String text){
        String regex = "(\\d{1,2}/\\d{1,2}/\\d{4}|\\d{1,2}/\\d{1,2})";

        String result = "";

        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = pattern.matcher((CharSequence)text);

        while (m.find()) {
            result = m.group(1);
            Log.d("GeneralValidations ::", "extractBillDate: " + result);
        }
        Log.d("GeneralValidations ::", "extractBillDate: " + text);

        return result;
    }
}
