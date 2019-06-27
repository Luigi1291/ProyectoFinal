package com.lusberc.billwallet.Models;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

public class MyApplication extends Application {

    private static Context mContext;
    private Bill _mBill;

    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return mContext;
    }

    public Bill get_mBill() {
        return _mBill;
    }

    public void set_mBill(Bill _mBill) {
        this._mBill = _mBill;
    }

    //TEST
}