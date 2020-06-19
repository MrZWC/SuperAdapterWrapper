package com.example.superadapterwrapper.moudle;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.superadapterwrapper.R;
import com.example.superadapterwrapper.base.BaseActivity;
import com.example.superadapterwrapper.client.MiddlewareChromeClient;
import com.example.superadapterwrapper.client.MiddlewareWebViewClient;
import com.example.superadapterwrapper.common.CommonWebChromeClient;
import com.example.superadapterwrapper.common.UIController;
import com.idonans.lang.thread.Threads;
import com.just.agentweb.AbsAgentWebSettings;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.IAgentWebSettings;
import com.just.agentweb.MiddlewareWebChromeBase;
import com.just.agentweb.MiddlewareWebClientBase;
import com.just.agentweb.PermissionInterceptor;

import java.util.HashMap;

public class AgentWebActivity extends BaseActivity {
    protected AgentWeb mAgentWeb;
    private FrameLayout mWebLayoutView;
    private View mX5EduView;
    //
    private MiddlewareWebChromeBase mMiddleWareWebChrome;
    private MiddlewareWebClientBase mMiddleWareWebClient;
    public static final String TAG = AgentWebActivity.class.getSimpleName();

    public static void start(Context context) {
        Intent intent = new Intent(context, AgentWebActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_agent_web);

    }

    @Override
    protected void initView() {
        mWebLayoutView = getView(R.id.web_layout_view);
        mX5EduView = getView(R.id.x5_edu_view);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        String url = "http://124.161.87.43:8081/ynedut/third/auth/forwardPage.htm?version=V1.0&mobile=1&access_token=eyJqdGkiOiJ5bi1tZXNzYWdlIiwiaXNzIjoiMSIsImF1ZCI6IjEiLCJleHAiOjE1OTIzOTQyOTN9.Yubyu3VeMOaFAh2b6J1TQqSYUJMKaIFdosbXiAfPcCARfwtG83l4qDUC-oZjxnGfTvzdkKDdB6z3DIU9hq27kA&openId=3b25b415-4aef-4973-8d20-2d37f1cbd79c&urlStr=/handheldlearn/index.html";
        String url1 = "https://zhidao.baidu.com/question/95254875.html";
        String url2 = "http://124.161.87.43:8081/fs/resource/792445dd-1fa4-436e-836d-60a67a1d5dde/index.html";
        String url3 = "http://10.6.30.117:8099/c/index.html";
        mAgentWeb = AgentWeb.with(this)//
                .setAgentWebParent((FrameLayout) mWebLayoutView, -1, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))//传入AgentWeb的父控件。
                .useDefaultIndicator(-1, 3)//设置进度条颜色与高度，-1为默认值，高度为2，单位为dp。
                .setAgentWebWebSettings(getSettings())//设置 IAgentWebSettings。
                .setWebViewClient(mWebViewClient)//WebViewClient ， 与 WebView 使用一致 ，但是请勿获取WebView调用setWebViewClient(xx)方法了,会覆盖AgentWeb DefaultWebClient,同时相应的中间件也会失效。
                .setWebChromeClient(new CommonWebChromeClient()) //WebChromeClient
                .setPermissionInterceptor(mPermissionInterceptor) //权限拦截 2.0.0 加入。
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK) //严格模式 Android 4.2.2 以下会放弃注入对象 ，使用AgentWebView没影响。
                .setAgentWebUIController(new UIController(this)) //自定义UI  AgentWeb3.0.0 加入。
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1) //参数1是错误显示的布局，参数2点击刷新控件ID -1表示点击整个布局都刷新， AgentWeb 3.0.0 加入。
                .useMiddlewareWebChrome(getMiddlewareWebChrome()) //设置WebChromeClient中间件，支持多个WebChromeClient，AgentWeb 3.0.0 加入。
                //.additionalHttpHeader(getUrl(), "cookie", "41bc7ddf04a26b91803f6b11817a5a1c")
                .useMiddlewareWebClient(getMiddlewareWebClient()) //设置WebViewClient中间件，支持多个WebViewClient， AgentWeb 3.0.0 加入。
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他页面时，弹窗质询用户前往其他应用 AgentWeb 3.0.0 加入。
                .interceptUnkownUrl() //拦截找不到相关页面的Url AgentWeb 3.0.0 加入。
                .createAgentWeb()//创建AgentWeb。
                .ready()//设置 WebSettings。
                .go(url3); //WebView载入该url地址的页面并显示。
        mAgentWeb.getWebCreator().getWebView().setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        mAgentWeb.getJsInterfaceHolder().addJavaObject("native", new JsCallBackClass());

    }

    public class JsCallBackClass {
        @JavascriptInterface
        public void disableBackButton() {
        }

        /**
         * 关闭当前页面
         */
        @JavascriptInterface
        public void closeWindow() {
            finish();
        }

        /**
         * 显示顶部操作界面
         */
        @JavascriptInterface
        public void showHeaderssss() {
            Threads.postUi(new Runnable() {
                @Override
                public void run() {
                    mX5EduView.setVisibility(View.VISIBLE);
                }
            });
        }

    }

    public IAgentWebSettings getSettings() {
        return new AbsAgentWebSettings() {
            private AgentWeb mAgentWeb;

            @Override
            protected void bindAgentWebSupport(AgentWeb agentWeb) {
                this.mAgentWeb = agentWeb;
            }
        };
    }

    protected PermissionInterceptor mPermissionInterceptor = new PermissionInterceptor() {

        /**
         * PermissionInterceptor 能达到 url1 允许授权， url2 拒绝授权的效果。
         * @param url
         * @param permissions
         * @param action
         * @return true 该Url对应页面请求权限进行拦截 ，false 表示不拦截。
         */
        @Override
        public boolean intercept(String url, String[] permissions, String action) {
            Log.i(TAG, "mUrl:" + url + "  permission:" + permissions.toString() + " action:" + action);
            return false;
        }
    };
    /**
     * 注意，重写WebViewClient的方法,super.xxx()请务必正确调用， 如果没有调用super.xxx(),则无法执行DefaultWebClient的方法
     * 可能会影响到AgentWeb自带提供的功能,尽可能调用super.xxx()来完成洋葱模型
     */
    protected com.just.agentweb.WebViewClient mWebViewClient = new com.just.agentweb.WebViewClient() {

        private HashMap<String, Long> timer = new HashMap<>();

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Nullable
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            return super.shouldInterceptRequest(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            //Log.i(TAG, "mUrl:" + url + " onPageStarted  target:" + getUrl());
            timer.put(url, System.currentTimeMillis());
           /* if (url.equals(getUrl())) {
                pageNavigator(View.GONE);
            } else {
                pageNavigator(View.VISIBLE);
            }*/
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            if (timer.get(url) != null) {
                long overTime = System.currentTimeMillis();
                Long startTime = timer.get(url);
                Log.i(TAG, "  page mUrl:" + url + "  used time:" + (overTime - startTime));
            }

        }
        /*错误页回调该方法 ， 如果重写了该方法， 上面传入了布局将不会显示 ， 交由开发者实现，注意参数对齐。*/
	   /* public void onMainFrameError(AbsAgentWebUIController agentWebUIController, WebView view, int errorCode, String description, String failingUrl) {

            Log.i(TAG, "AgentWebFragment onMainFrameError");
            agentWebUIController.onMainFrameError(view,errorCode,description,failingUrl);

        }*/

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);

//			Log.i(TAG, "onReceivedHttpError:" + 3 + "  request:" + mGson.toJson(request) + "  errorResponse:" + mGson.toJson(errorResponse));
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
            super.onReceivedSslError(view, handler, error);
        }


    };

    /**
     * MiddlewareWebClientBase 是 AgentWeb 3.0.0 提供一个强大的功能，
     * 如果用户需要使用 AgentWeb 提供的功能， 不想重写 WebClientView方
     * 法覆盖AgentWeb提供的功能，那么 MiddlewareWebClientBase 是一个
     * 不错的选择 。
     *
     * @return
     */
    protected MiddlewareWebClientBase getMiddlewareWebClient() {
        return this.mMiddleWareWebClient = new MiddlewareWebViewClient() {
            /**
             *
             * @param view
             * @param url
             * @return
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e(TAG, "MiddlewareWebClientBase#shouldOverrideUrlLoading url:" + url);
				/*if (url.startsWith("agentweb")) { // 拦截 url，不执行 DefaultWebClient#shouldOverrideUrlLoading
					Log.i(TAG, "agentweb scheme ~");
					return true;
				}*/

                if (super.shouldOverrideUrlLoading(view, url)) { // 执行 DefaultWebClient#shouldOverrideUrlLoading
                    return true;
                }
                // do you work
                return false;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.e(TAG, "MiddlewareWebClientBase#shouldOverrideUrlLoading request url:" + request.getUrl().toString());
                return super.shouldOverrideUrlLoading(view, request);
            }
        };
    }

    protected MiddlewareWebChromeBase getMiddlewareWebChrome() {
        return this.mMiddleWareWebChrome = new MiddlewareChromeClient() {
        };
    }


    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
*/
    @Override
    public void onBackPressed() {
        if (!mAgentWeb.back()) {
            super.onBackPressed();
        }
    }
}
