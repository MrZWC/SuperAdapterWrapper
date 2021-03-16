package com.example.superadapterwrapper.util;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by Android Studio.
 * User: zuoweichen
 * Date: 2020/8/1
 * Time: 10:50
 *
 * GZip工具类
 * zip压缩解压并使用Base64进行编码工具类
 * 调用：
 * 压缩
 *  GZipUtil.compress(str)
 * 解压
 * GZipUtil.uncompressToString(bytes)
 */

public class GzipUtil {
    private static final String TAG = "GzipUtil";
    /**
     * 将字符串进行gzip压缩
     *
     * @param data
     * @return
     */
    public static String compress(String data) {
        if (data == null || data.length() == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(data.getBytes("utf-8"));
            gzip.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.encodeToString(out.toByteArray(), Base64.NO_PADDING);
    }

    public static String uncompress(String data) {
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        byte[] decode = Base64.decode(data, Base64.NO_PADDING);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(decode);
        GZIPInputStream gzipStream = null;
        try {
            gzipStream = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = gzipStream.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
        } catch (IOException e) {
            Log.e(TAG, "e = " + Log.getStackTraceString(e));
        } finally {
            try {
                out.close();
                if (gzipStream != null) {
                    gzipStream.close();
                }
            } catch (IOException e) {
                Log.e(TAG, "e = " + Log.getStackTraceString(e));
            }
        }
        return new String(out.toByteArray(), Charset.forName("utf-8"));
    }
}
