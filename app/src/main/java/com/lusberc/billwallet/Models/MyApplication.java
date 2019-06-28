package com.lusberc.billwallet.Models;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.location.Address;

public class MyApplication extends Application {

    /**
     * SINGLETON for
     */


    private static Context mContext;
    public static Bill _mBill;

    public void onCreate() {
        super.onCreate();
        mContext = this;
        _mBill = new Bill();
    }

    public static Context getAppContext() {
        return mContext;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}