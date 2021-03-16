package com.example.superadapterwrapper.net.download;

/**
 * 下载进度listener
 * Created by Android on 16/5/11.
 */
public interface DownloadProgressListener {
    void update(long bytesRead, long contentLength, boolean done);
}
