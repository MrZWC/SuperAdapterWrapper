package com.example.superadapterwrapper.plugin;

import android.widget.Toast;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;

/**
 * create 2020 6 23
 * 视频插件
 */
public class YNVideoPlay extends CordovaPlugin {


    private static final String OPEN_ACTION = "startPlayVideo";

    @Override
    public boolean execute(String action, String args, CallbackContext callbackContext) {
        if (action.equals(OPEN_ACTION)) {
            Toast.makeText(cordova.getActivity(), "startPlayVideo成功", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

}
