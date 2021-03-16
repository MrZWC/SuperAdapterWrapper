package com.example.superadapterwrapper.moudle;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.superadapterwrapper.R;
import com.example.superadapterwrapper.base.BaseActivity;
import com.example.superadapterwrapper.util.DisposableHolder;
import com.example.superadapterwrapper.util.LogUtil;
import com.example.superadapterwrapper.util.WifiAdmin;
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
    private TextView mOpenWifi;
    private TextView mConnectWifi;
    String result = "数据源来自 = ";
    private WifiAdmin mWifiAdmin;

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
        mOpenWifi = getView(R.id.open_wifi);
        mConnectWifi = getView(R.id.connect_wifi);
    }

    @Override
    protected void initLinstener() {

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startScan();
            }
        });
        mOpenWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWifiAdmin.openWifi(getContext());
            }
        });
        mConnectWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ssid = "YN-APP";
                String password = "COursedev";
                //String ssid = "Mr zuo";
                //String password = "123456789";
                WifiConfiguration wifiInfo = mWifiAdmin.createWifiInfo(ssid, password, 3);
                mWifiAdmin.addNetwork(wifiInfo);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mWifiAdmin = new WifiAdmin(this);
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
