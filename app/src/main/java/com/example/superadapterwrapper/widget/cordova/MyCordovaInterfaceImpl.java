package com.example.superadapterwrapper.widget.cordova;

import android.app.Activity;

import org.apache.cordova.CordovaInterfaceImpl;
import org.apache.cordova.CordovaPlugin;

import java.util.concurrent.ExecutorService;

/**
 * Created by Android Studio.
 * User: zuoweichen
 * Date: 2020/6/23
 * Time: 15:05
 */
public class MyCordovaInterfaceImpl extends CordovaInterfaceImpl {
    public MyCordovaInterfaceImpl(Activity activity) {
        super(activity);
    }

    public MyCordovaInterfaceImpl(Activity activity, ExecutorService threadPool) {
        super(activity, threadPool);
    }
    public CordovaPlugin getActivityResultCallback() {
        return activityResultCallback;
    }

}
