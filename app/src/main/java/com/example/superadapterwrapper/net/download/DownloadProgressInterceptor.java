package com.example.superadapterwrapper.net.download;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Interceptor for download
 * Created by Android on 16/1/15.
 */
public class DownloadProgressInterceptor implements Interceptor {

    private DownloadProgressListener listener;

    public DownloadProgressInterceptor(DownloadProgressListener listener) {
        this.listener = listener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder()
                .body(new DownloadProgressResponseBody(originalResponse.body(), listener))
                .build();
    }
}
