package com.example.superadapterwrapper.net.download;


import java.util.Collections;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.TrustManager;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 下载
 * Created by Android on 16/11/15.
 */
public class DownloadAPI {

    private static final int DEFAULT_TIMEOUT = 30;
    private OkHttpClient client;

    public DownloadAPI(DownloadProgressListener listener) {
        DownloadProgressInterceptor interceptor = new DownloadProgressInterceptor(listener);
        client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                //适配运营商定制机加的配置，后期优化
                .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                .build();
    }

    public Observable<Response> download(@NonNull String url) {
        return Observable.just(url).map(new Function<String, Response>() {
            @Override
            public Response apply(@NonNull String s) throws Exception {
                Request request = new Request.Builder().url(s).build();
                Response response = client.newCall(request).execute();
                return response;
            }
        });
    }
}
