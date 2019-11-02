package com.example.superadapterwrapper;

import android.app.Application;

/**
 * Created by Android Studio.
 * User: zuoweichen
 * Date: 2019/11/1 001
 * Time: 17:03
 */
public class SuperAppliction extends Application {
    private static SuperAppliction appliction;

    @Override
    public void onCreate() {
        super.onCreate();
        appliction = this;
    }

    public static SuperAppliction getApp() {
        return appliction;
    }
}
