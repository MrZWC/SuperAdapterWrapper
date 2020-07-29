package com.example.superadapterwrapper.moudle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
import com.socks.library.KLog;

import java.io.IOException;

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
        mMyWebView.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
        mMyWebView.setWebViewClient(new MyWebChromeClient());
        mMyWebView.setWebChromeClient(new WebChromeClient());
        String url = "http://124.161.87.43:8081/ynedut/third/auth/forwardPage.htm?version=V1.0&mobile=1&access_token=eyJqdGkiOiJ5bi1tZXNzYWdlIiwiaXNzIjoiMSIsImF1ZCI6IjEiLCJleHAiOjE1OTIzMDU5MTR9.xTtLaqth8DO729tXqwMRQvqzAHH7Uu0_VKDxzFKlwsNAtWKmdYGzFnX0DaV0N0P4zToiEytjxMYUWib90Ims9g&openId=3b25b415-4aef-4973-8d20-2d37f1cbd79c&urlStr=/handheldlearn/index.html";
        String url1 = "https://zhidao.baidu.com/question/95254875.html";
        String url2 = "http://124.161.87.43:8081/ynedut/handheldlearn/index.html";
        String url3 = "http://10.6.30.117:8099/d/index.html";
        String url4 = "file:///android_asset/www/indexlocalhtml.html";
        String url5 = "http://10.6.30.62:63341/untitled/index.html";
        mMyWebView.loadUrl(url4);
    }

    public class MyWebChromeClient extends WebViewClient {
        @Nullable
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (request.getUrl().toString().contains("main.js")) {//加载指定.js时 引导服务端加载本地Assets/www文件夹下的cordova.js
                    try {
                        return new WebResourceResponse("application/x-javascript", "utf-8", getBaseContext().getAssets().open("www/main2.js"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return super.shouldInterceptRequest(view, request);
        }

        @Nullable
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            if (url.contains("main.js")) {//加载指定.js时 引导服务端加载本地Assets/www文件夹下的cordova.js
                try {
                    return new WebResourceResponse("application/x-javascript", "utf-8", getBaseContext().getAssets().open("www/main2.js"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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

