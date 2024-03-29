package com.example.superadapterwrapper;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;

import com.socks.library.KLog;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.android.AndroidSmackInitializer;

import timber.log.Timber;

/**
 * Created by Android Studio.
 * User: zuoweichen
 * Date: 2019/11/1 001
 * Time: 17:03
 */
public class SuperAppliction extends Application {
    private static SuperAppliction appliction;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        MultiDex.install(this);
        super.onCreate();
        appliction = this;
        Timber.plant(new Timber.DebugTree());
        AndroidSmackInitializer.initialize(this);
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
        QbSdk.setDownloadWithoutWifi(true);
        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(int i) {

            }

            @Override
            public void onInstallFinish(int i) {

            }

            @Override
            public void onDownloadProgress(int i) {

            }
        });
        QbSdk.disAllowThirdAppDownload();
        KLog.i("SuperAppliction","onCreate");
    }

    public static SuperAppliction getApp() {
        return appliction;
    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

}
