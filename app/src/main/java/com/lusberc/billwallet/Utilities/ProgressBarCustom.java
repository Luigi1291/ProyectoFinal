package com.lusberc.billwallet.Utilities;

import android.app.ProgressDialog;
import android.view.View;

import com.lusberc.billwallet.R;

public class ProgressBarCustom {
    private static ProgressDialog bar;

    public static void showBar(View view){
        bar = new ProgressDialog(view.getContext());
        bar.setMessage(view.getContext().getString(R.string.loadingPage));
        bar.setCancelable(false);
        bar.show();
    }

    public static void closeBar(){
        if (bar != null) {
            bar.dismiss();
            bar = null;
        }
    }
}
