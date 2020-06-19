package com.example.superadapterwrapper.moudle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.example.superadapterwrapper.R;
import com.example.superadapterwrapper.base.BaseActivity;

/**
 * Created by Android Studio.
 * User: zuoweichen
 * Date: 2020/6/15
 * Time: 15:53
 */
public class WebViewActivity extends BaseActivity {
    private WebView mMyWebView;

    /** 视频全屏参数 */
    protected static final FrameLayout.LayoutParams COVER_SCREEN_PARAMS = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    private View customView;
    private FrameLayout fullscreenContainer;
    private WebChromeClient.CustomViewCallback customViewCallback;
    public static void start(Context context) {
        Intent intent = new Intent(context, WebViewActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_web_view_layout);
    }

    @Override
    protected void initView() {
        mMyWebView = getView(R.id.my_web_view);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        mMyWebView.setWebChromeClient(new WebChromeClient(){

          /*  *//*** 视频播放相关的方法 **//*

            @Override
            public View getVideoLoadingProgressView() {
                FrameLayout frameLayout = new FrameLayout(WebViewActivity.this);
                frameLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                return frameLayout;
            }

            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                showCustomView(view, callback);
            }

            @Override
            public void onHideCustomView() {
                hideCustomView();
            }*/
        });
        mMyWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mMyWebView.loadUrl(url);
                return true;
            }
        });
        WebSettings webSettings = mMyWebView.getSettings();
        webSettings.setPluginState(WebSettings.PluginState.ON);
        // 让WebView能够执行javaScript
        webSettings.setJavaScriptEnabled(true);
        // 让JavaScript可以自动打开windows
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 设置缓存
        webSettings.setAppCacheEnabled(true);
        // 设置缓存模式,一共有四种模式
        webSettings.setCacheMode(webSettings.LOAD_CACHE_ELSE_NETWORK);
        // 设置缓存路径
//        webSettings.setAppCachePath("");
        // 支持缩放(适配到当前屏幕)
        webSettings.setSupportZoom(true);
        // 将图片调整到合适的大小
        webSettings.setUseWideViewPort(true);
        // 支持内容重新布局,一共有四种方式
        // 默认的是NARROW_COLUMNS
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 设置可以被显示的屏幕控制
        webSettings.setDisplayZoomControls(true);
        // 设置默认字体大小
        webSettings.setDefaultFontSize(12);
        webSettings.setJavaScriptEnabled(true);
        String url="http://124.161.87.43:8081/ynedut/third/auth/forwardPage.htm?version=V1.0&mobile=1&access_token=eyJqdGkiOiJ5bi1tZXNzYWdlIiwiaXNzIjoiMSIsImF1ZCI6IjEiLCJleHAiOjE1OTIzMDU5MTR9.xTtLaqth8DO729tXqwMRQvqzAHH7Uu0_VKDxzFKlwsNAtWKmdYGzFnX0DaV0N0P4zToiEytjxMYUWib90Ims9g&openId=3b25b415-4aef-4973-8d20-2d37f1cbd79c&urlStr=/handheldlearn/index.html";
        String url1="https://zhidao.baidu.com/question/95254875.html";
        String url2="http://124.161.87.43:8081/ynedut/handheldlearn/index.html";
        String url3="http://10.6.30.117:8099/d/index.html";
        mMyWebView.loadUrl(url);
    }
    public class MyWebChromeClient extends WebViewClient {
        @Override
        public void onScaleChanged(WebView view, float oldScale, float newScale) {
            if(newScale - oldScale > 7) {
                view.setInitialScale((int)(oldScale / newScale * 100)); //异常放大，缩回去。
            }
        }
    }


    /** 视频播放全屏 **/
    private void showCustomView(View view, WebChromeClient.CustomViewCallback callback) {
        // if a view already exists then immediately terminate the new one
        if (customView != null) {
            callback.onCustomViewHidden();
            return;
        }

        WebViewActivity.this.getWindow().getDecorView();

        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
        fullscreenContainer = new FullscreenHolder(WebViewActivity.this);
        fullscreenContainer.addView(view, COVER_SCREEN_PARAMS);
        decor.addView(fullscreenContainer, COVER_SCREEN_PARAMS);
        customView = view;
        setStatusBarVisibility(false);
        customViewCallback = callback;
    }

    /** 隐藏视频全屏 */
    private void hideCustomView() {
        if (customView == null) {
            return;
        }

        setStatusBarVisibility(true);
        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
        decor.removeView(fullscreenContainer);
        fullscreenContainer = null;
        customView = null;
        customViewCallback.onCustomViewHidden();
        mMyWebView.setVisibility(View.VISIBLE);
    }

    /** 全屏容器界面 */
    static class FullscreenHolder extends FrameLayout {

        public FullscreenHolder(Context ctx) {
            super(ctx);
            setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
        }

        @Override
        public boolean onTouchEvent(MotionEvent evt) {
            return true;
        }
    }

    private void setStatusBarVisibility(boolean visible) {
        int flag = visible ? 0 : WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setFlags(flag, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

   /* @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                *//** 回退键 事件处理 优先级:视频播放全屏-网页回退-关闭页面 *//*
                if (customView != null) {
                    hideCustomView();
                } else if (mMyWebView.canGoBack()) {
                    mMyWebView.goBack();
                } else {
                    finish();
                }
                return true;
            default:
                return super.onKeyUp(keyCode, event);
        }
    }*/
}

