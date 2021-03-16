package com.example.superadapterwrapper.util;

import android.graphics.Bitmap;

import com.idonans.lang.util.ContextUtil;
import com.idonans.lang.util.IOUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileDirManager {
    private static FileDirManager INSTANCE = new FileDirManager();

    private FileDirManager() {
    }

    public static FileDirManager getInstance() {
        return INSTANCE;
    }

    private void createUnScanFile(String dir) {
        File ignore = new File(dir, ".nomedia");
        if (!ignore.exists()) {
            try {
                ignore.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取视频缓存目录
     *
     * @return
     */
    public String getVideoCacheDir() {
        File file = ContextUtil.getContext().getExternalFilesDir("video");
        if (!file.exists() && !file.mkdir()) {
            return "";
        }
        createUnScanFile(file.getAbsolutePath());
        return file.getAbsolutePath();
    }

    /**
     * 获取应用缓存目录
     *
     * @return
     */
    public String getH5CacheDir() {
        File file = ContextUtil.getContext().getExternalFilesDir("h5");
        if (!file.exists() && !file.mkdir()) {
            return "";
        }
        createUnScanFile(file.getAbsolutePath());
        return file.getAbsolutePath();
    }

    /**
     * 获取音乐缓存目录
     *
     * @return
     */
    public String getMusicCacheDir() {
        File file = ContextUtil.getContext().getExternalFilesDir("publish_music");
        if (!file.exists() && !file.mkdir()) {
            return "";
        }
        createUnScanFile(file.getAbsolutePath());
        return file.getAbsolutePath();
    }


    /**
     * 保存BITMAP为图片
     *
     * @param bm
     * @param path
     * @param picName
     * @throws Exception
     */
    public String saveBitmap(Bitmap bm, String path, String picName) {
        FileOutputStream out = null;
        try {
            File file = new File(path);
            if (!file.exists())
                file.mkdirs();
            if (null != bm) {
                file = new File(path, picName);
                out = new FileOutputStream(file);
                bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
            }
            return file.getPath();
        } catch (Exception e) {
            return null;
        } finally {
            IOUtil.closeQuietly(out);
        }
    }

    public String saveBitmap(Bitmap bm, File file) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            return file.getPath();
        } catch (Exception e) {
            return null;
        } finally {
            IOUtil.closeQuietly(out);
        }
    }

}
