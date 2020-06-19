package com.example.superadapterwrapper.moudle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.example.superadapterwrapper.R;
import com.example.superadapterwrapper.util.WebViewJavaScriptFunction;
import com.example.superadapterwrapper.widget.X5WebView;
import com.tencent.smtt.sdk.QbSdk;

public class FullScreenActivity extends Activity {

    /**
     * 用于演示X5webview实现视频的全屏播放功能 其中注意 X5的默认全屏方式 与 android 系统的全屏方式
     */

    X5WebView webView;

    public static void start(Context context) {
        Intent intent = new Intent(context, FullScreenActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        QbSdk.forceSysWebView();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filechooser_layout);

        webView = (X5WebView) findViewById(R.id.web_filechooser);
       /* String url = "http://124.161.87.43:8081/ynedut/third/auth/forwardPage.htm?version=V1.0&mobile=1&access_token=eyJqdGkiOiJ5bi1tZXNzYWdlIiwiaXNzIjoiMSIsImF1ZCI6IjEiLCJleHAiOjE1OTIzMDU5MTR9.xTtLaqth8DO729tXqwMRQvqzAHH7Uu0_VKDxzFKlwsNAtWKmdYGzFnX0DaV0N0P4zToiEytjxMYUWib90Ims9g&openId=3b25b415-4aef-4973-8d20-2d37f1cbd79c&urlStr=/handheldlearn/index.html";
        String url1="http://124.161.87.43:8081/fs/resource/792445dd-1fa4-436e-836d-60a67a1d5dde/index.html";
        String url2="http://10.6.30.117:8099/c/index.html";*/
        //webView.loadUrl("file:///android_asset/webpage/fullscreenVideo.html");
        String url2="http://10.6.30.117:8099/c/index.html";
        webView.loadUrl(url2);

        getWindow().setFormat(PixelFormat.TRANSLUCENT);

        webView.getView().setOverScrollMode(View.OVER_SCROLL_ALWAYS);
        webView.addJavascriptInterface(new WebViewJavaScriptFunction() {

            @Override
            public void onJsFunctionCalled(String tag) {
                // TODO Auto-generated method stub

            }

            @JavascriptInterface
            public void onX5ButtonClicked() {
                FullScreenActivity.this.enableX5FullscreenFunc();
            }

            @JavascriptInterface
            public void onCustomButtonClicked() {
                FullScreenActivity.this.disableX5FullscreenFunc();
            }

            @JavascriptInterface
            public void onLiteWndButtonClicked() {
                FullScreenActivity.this.enableLiteWndFunc();
            }

            @JavascriptInterface
            public void onPageVideoClicked() {
                FullScreenActivity.this.enablePageVideoFunc();
            }
        }, "Android");

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        try {
            super.onConfigurationChanged(newConfig);
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // /////////////////////////////////////////
    // 向webview发出信息
    private void enableX5FullscreenFunc() {

        if (webView.getX5WebViewExtension() != null) {
            Toast.makeText(this, "开启X5全屏播放模式", Toast.LENGTH_LONG).show();
            Bundle data = new Bundle();

            data.putBoolean("standardFullScreen", false);// true表示标准全屏，false表示X5全屏；不设置默认false，

            data.putBoolean("supportLiteWnd", false);// false：关闭小窗；true：开启小窗；不设置默认true，

            data.putInt("DefaultVideoScreen", 2);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1

            webView.getX5WebViewExtension().invokeMiscMethod("setVideoParams",
                    data);
        }
    }

    private void disableX5FullscreenFunc() {
        int tbsSDKVersion = webView.getTbsSDKVersion(this);
        int getTbsCoreVersion = webView.getTbsCoreVersion(this);
        if (webView.getX5WebViewExtension() != null) {
            Toast.makeText(this, "恢复webkit初始状态", Toast.LENGTH_LONG).show();
            Bundle data = new Bundle();

            data.putBoolean("standardFullScreen", true);// true表示标准全屏，会调起onShowCustomView()，false表示X5全屏；不设置默认false，

            data.putBoolean("supportLiteWnd", false);// false：关闭小窗；true：开启小窗；不设置默认true，

            data.putInt("DefaultVideoScreen", 2);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1

            webView.getX5WebViewExtension().invokeMiscMethod("setVideoParams",
                    data);
        }
    }

    private void enableLiteWndFunc() {
        if (webView.getX5WebViewExtension() != null) {
            Toast.makeText(this, "开启小窗模式", Toast.LENGTH_LONG).show();
            Bundle data = new Bundle();

            data.putBoolean("standardFullScreen", false);// true表示标准全屏，会调起onShowCustomView()，false表示X5全屏；不设置默认false，

            data.putBoolean("supportLiteWnd", true);// false：关闭小窗；true：开启小窗；不设置默认true，

            data.putInt("DefaultVideoScreen", 2);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1

            webView.getX5WebViewExtension().invokeMiscMethod("setVideoParams",
                    data);
        }
    }

    private void enablePageVideoFunc() {
        if (webView.getX5WebViewExtension() != null) {
            Toast.makeText(this, "页面内全屏播放模式", Toast.LENGTH_LONG).show();
            Bundle data = new Bundle();

            data.putBoolean("standardFullScreen", false);// true表示标准全屏，会调起onShowCustomView()，false表示X5全屏；不设置默认false，

            data.putBoolean("supportLiteWnd", false);// false：关闭小窗；true：开启小窗；不设置默认true，

            data.putInt("DefaultVideoScreen", 1);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1

            webView.getX5WebViewExtension().invokeMiscMethod("setVideoParams",
                    data);
        }
    }

}
