package com.example.superadapterwrapper.moudle;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.superadapterwrapper.R;
import com.example.superadapterwrapper.base.BaseActivity;
import com.example.superadapterwrapper.util.DisposableHolder;
import com.example.superadapterwrapper.util.LogUtil;
import com.socks.library.KLog;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class WifiActivity extends BaseActivity {
    private TextView mTextView;
    String result = "数据源来自 = ";

    public static void start(Context context) {
        Intent intent = new Intent(context, WifiActivity.class);
        context.startActivity(intent);
    }

    private DisposableHolder mDisposableHolder = new DisposableHolder();

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_wifi);

    }

    @Override
    protected void initView() {
        mTextView = getView(R.id.start_scan);
    }

    @Override
    protected void initLinstener() {

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startScan();
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        /*
         * 设置第1个Observable：通过网络获取数据
         * 此处仅作网络请求的模拟
         **/
        Observable<String> network = Observable.just("网络").map(new Function<String, String>() {
            @Override
            public String apply(String s) throws Throwable {
                if (true) {
                    throw new Exception();
                }
                return "网络成功";
            }
        });

        /*
         * 设置第2个Observable：通过本地文件获取数据
         * 此处仅作本地文件请求的模拟
         **/
        Observable<String> file = Observable.just("本地文件").map(new Function<String, String>() {
            @Override
            public String apply(String s) throws Throwable {
                return "本地文件成功";
            }
        });


        /*
         * 通过merge（）合并事件 & 同时发送事件
         **/
        Observable.merge(network, file)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String value) {
                        LogUtil.d("数据源有： " + value);
                        result = result + value + "+";
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("对Error事件作出响应");
                    }

                    // 接收合并事件后，统一展示
                    @Override
                    public void onComplete() {
                        LogUtil.d("获取数据完成");
                        LogUtil.d(result);
                    }
                });
    }

    private void startScan() {
        long systemTime = System.currentTimeMillis();
        mDisposableHolder.set(Observable.just("").map(s -> {
            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            wifiManager.startScan();
            List<ScanResult> scanResults = wifiManager.getScanResults();
            Thread.sleep(2000);
            return scanResults;
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                    for (ScanResult scanResult : o) {
                        KLog.i("wifiScan", "SSID=" + scanResult.SSID + "  BSSID=" + scanResult.BSSID);
                    }
                    KLog.i("wifiScan", "===========" + systemTime + "===========");
                    //startScan();
                }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposableHolder.clear();
    }
}
