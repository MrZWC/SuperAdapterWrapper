package com.example.superadapterwrapper.moudle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import com.example.superadapterwrapper.R;
import com.example.superadapterwrapper.base.BaseActivity;
import com.google.gson.Gson;
import com.idonans.lang.thread.Threads;
import com.socks.library.KLog;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Android Studio.
 * User: zuoweichen
 * Date: 2020/6/15
 * Time: 15:53
 */
public class WebViewActivity extends BaseActivity {
    private WebView mMyWebView;

    /**
     * 视频全屏参数
     */
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

    @SuppressLint("JavascriptInterface")
    @Override
    protected void initData(Bundle savedInstanceState) {
        WebSettings webSettings = mMyWebView.getSettings();
        webSettings.setPluginState(WebSettings.PluginState.ON);
        // 让WebView能够执行javaScript
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
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
        //mMyWebView.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
        mMyWebView.setWebViewClient(new MyWebChromeClient());
        mMyWebView.setWebChromeClient(new WebChromeClient());

        mMyWebView.loadUrl("http://10.6.30.70:8080/#/mobile");
        mMyWebView.addJavascriptInterface(new JSObject(), "localJs");
    }

    class JSObject {
        @JavascriptInterface
        public void run(String method, String arguments, String function) {
            KLog.d(method);
            Map map = new Gson().fromJson(arguments, Map.class);
            Threads.runOnUi(new Runnable() {
                @Override
                public void run() {
                    int a = Integer.parseInt((String) map.get("a"));
                    int b = Integer.parseInt((String) map.get("b"));
                    int aa = a + b;
                    mMyWebView.evaluateJavascript("(" + function + ")(" + aa + ")", null);
                    //mMyWebView.evaluateJavascript("(function(data){alert(data)})(3333)", null);
                    Handler handler = new Handler();
                    Runnable runnable= new Runnable(){

                       @Override
                       public void run() {
                           handler.postDelayed(this::run,1000);
                       }
                   };
                    handler.postDelayed(runnable,1000);
                }
            });
        }

        @JavascriptInterface
        public void subtraction() {

        }
    }

    public class MyWebChromeClient extends WebViewClient {
        @Nullable
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            return super.shouldInterceptRequest(view, request);
        }

        @Nullable
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            return super.shouldInterceptRequest(view, url);
        }
    }

    final class InJavaScriptLocalObj {

        @JavascriptInterface
        public void showSource(String html) {
            KLog.d("HTMLsource", html);
        }
    }
}

