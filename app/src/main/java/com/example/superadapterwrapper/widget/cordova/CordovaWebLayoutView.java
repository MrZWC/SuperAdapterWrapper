package com.example.superadapterwrapper.widget.cordova;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.superadapterwrapper.R;

import org.apache.cordova.ConfigXmlParser;
import org.apache.cordova.CordovaInterfaceImpl;
import org.apache.cordova.CordovaPreferences;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewImpl;
import org.apache.cordova.PluginEntry;
import org.apache.cordova.engine.SystemWebView;
import org.apache.cordova.engine.SystemWebViewEngine;

import java.util.ArrayList;

/**
 * Created by Android Studio.
 * User: zuoweichen
 * Date: 2020/8/3
 * Time: 18:55
 */
public class CordovaWebLayoutView extends FrameLayout {
    protected CordovaWebView appView;
    protected CordovaWebView cordovaWebView;
    protected CordovaInterfaceImpl cordovaInterface;
    protected ArrayList<PluginEntry> pluginEntries;
    protected CordovaPreferences preferences;
    protected String launchUrl;
    private Activity mActivity;

    public CordovaWebLayoutView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public CordovaWebLayoutView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CordovaWebLayoutView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.wifget_cordova_web_layout_view, this);
        loadConfig();
        initWeb();
    }

    private void initWeb() {
        cordovaInterface = makeCordovaInterface();
        appView = makeWebView();
        createViews();
        if (!appView.isInitialized()) {
            appView.init(cordovaInterface, pluginEntries, preferences);
        }
        cordovaInterface.onCordovaInit(appView.getPluginManager());

        // Wire the hardware volume controls to control media if desired.
        String volumePref = preferences.getString("DefaultVolumeStream", "");
       /* if ("media".equals(volumePref.toLowerCase(Locale.ENGLISH))) {
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
        }*/
        //String url = "file:///android_asset/www/index.html";
        appView.getView().setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_UP:
                        boolean b = appView.getView().hasFocus();
                        if (!hasFocus()) {
                            requestFocus();
                        }
                        break;
                }
                boolean b = appView.getView().hasFocus();
                return false;
            }
        });
        String url = "file:///storage/emulated/0/Android/data/com.example.superadapterwrapper/files/h5/www/index.html";
        loadUrl(url);
    }

    protected void loadConfig() {
        ConfigXmlParser parser = new ConfigXmlParser();
        parser.parse(getActivity());
        preferences = parser.getPreferences();
        //preferences.setPreferencesBundle(getIntent().getExtras());
        launchUrl = parser.getLaunchUrl();
        pluginEntries = parser.getPluginEntries();
        MyConfig.parser = parser;
    }

    protected MyCordovaInterfaceImpl makeCordovaInterface() {
        return new MyCordovaInterfaceImpl(getActivity());
    }

    protected void createViews() {
        if (preferences.contains("BackgroundColor")) {
            try {
                int backgroundColor = preferences.getInteger("BackgroundColor", Color.BLACK);
                // Background of activity:
                appView.getView().setBackgroundColor(backgroundColor);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        appView.getView().requestFocusFromTouch();
    }

    protected CordovaWebView makeWebView() {
        SystemWebView webView = findViewById(R.id.cordovaWebView);
        return new CordovaWebViewImpl(new SystemWebViewEngine(webView));
    }

    public void loadUrl(String url) {
        if (appView == null) {
            initWeb();
        }

        // If keepRunning
        appView.loadUrlIntoView(url, true);
    }

    private Activity getActivity() {
        View parent = this;
        Activity activity = null;
        if (parent.getContext() instanceof Activity) {
            activity = (Activity) parent.getContext();
        }
        return activity;
    }

    public void setActivity(Activity activity) {
        mActivity = activity;
    }
}
