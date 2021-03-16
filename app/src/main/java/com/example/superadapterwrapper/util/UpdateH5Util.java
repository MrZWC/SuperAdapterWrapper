package com.example.superadapterwrapper.util;

import com.example.superadapterwrapper.net.download.DownloadAPI;
import com.example.superadapterwrapper.net.download.DownloadProgressListener;
import com.idonans.lang.util.ToastUtil;
import com.socks.library.KLog;

import java.io.File;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Response;

/**
 * Created by Android Studio.
 * User: zuoweichen
 * Date: 2021/1/29
 * Time: 11:15
 */
public class UpdateH5Util {
    private static final String TAG = "UpdateH5Util";

    public void update(String url, DownloadProgressListener listener) {
        DownloadAPI downloadAPI = new DownloadAPI(listener);
        downloadAPI.download(url).map(new Function<Response, File>() {
            @Override
            public File apply(@NonNull Response response) throws Exception {
                byte[] tmpBaseContents = response.body().bytes();
                KLog.d(TAG, String.format("download zip size: %s",
                        String.valueOf(tmpBaseContents.length)));

                String apkDir = FileDirManager.getInstance().getH5CacheDir();
                File file = new File(apkDir, "www" + ".zip");
                FileIOUtils.writeFileFromBytesByChannel(file, tmpBaseContents, true);
                if (file != null && file.exists()) {
                    KLog.d(TAG, String.format("zip file: %s , size is: %s",
                            file.getAbsoluteFile(), file.length()));
                    return file;
                }
                return null;
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<File>() {
                    @Override
                    public void accept(File file) throws Exception {
                        if (file != null) {
                            ToastUtil.show("下载成功");
                        } else {
                            ToastUtil.show("file为空");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtil.show("下载失败");
                    }
                });
    }
}
