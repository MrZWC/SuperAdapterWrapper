package com.example.superadapterwrapper.util;

import com.example.superadapterwrapper.BuildConfig;
import com.socks.library.KLog;

/**
 * Created by Android Studio.
 * User: zuoweichen
 * Date: 2020/7/24
 * Time: 9:02
 */
public class LogUtil {
    public static final String DEBUG_TAG = "ynedut_Log";
    public static boolean IS_DEBUG = BuildConfig.DEBUG;

    public static void d(String msg) {
        if (IS_DEBUG) {
            KLog.d(DEBUG_TAG, msg + "");
        }
    }

    public static void d(String tag, String msg) {
        if (IS_DEBUG) {
            KLog.d(tag, msg + "");
        }
    }

    public static void v(String msg) {
        if (IS_DEBUG) {
            KLog.v(DEBUG_TAG, msg + "");
        }
    }

    public static void v(String tag, String msg) {
        if (IS_DEBUG) {
            KLog.v(tag, msg + "");
        }
    }

    public static void i(String msg) {
        if (IS_DEBUG) {
            KLog.i(DEBUG_TAG, msg + "");
        }
    }

    public static void i(String tag, String msg) {
        if (IS_DEBUG) {
            KLog.i(tag, msg + "");
        }
    }

    public static void w(String msg) {
        if (IS_DEBUG) {
            KLog.w(DEBUG_TAG, msg + "");
        }
    }

    public static void w(String tag, String msg) {
        if (IS_DEBUG) {
            KLog.w(tag, msg + "");
        }
    }

    public static void e(String msg) {
        if (IS_DEBUG) {
            KLog.e(DEBUG_TAG, msg + "");
        }
    }

    public static void e(String tag, String msg) {
        if (IS_DEBUG) {
            KLog.e(tag, msg + "");
        }
    }

    public static void e(String tag, String msg, Throwable throwable) {
        if (IS_DEBUG) {
            KLog.e(tag, msg + "", throwable);
        }
    }

    /**
     * 在debug下才打印异常日志
     */
    public static void printStackTrace(Exception e) {
        if (IS_DEBUG) {
            e.printStackTrace();
        }
    }
}
