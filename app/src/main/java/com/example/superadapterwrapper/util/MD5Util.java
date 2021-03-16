package com.example.superadapterwrapper.util;

import com.socks.library.KLog;

import java.security.MessageDigest;

/**
 * Created by Android Studio.
 * User: zuoweichen
 * Date: 2020/7/31
 * Time: 14:40
 */
public class MD5Util {
    private static final String TAG = "MD5Util";

    /***
     * MD5加码 生成32位md5码
     */
    public static String string2MD5(String inStr) {
        KLog.e(TAG, "string2MD5: -------------------------");
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }

    /**
     * 加密解密算法 执行一次加密，两次解密
     */
    public static String convertMD5(String inStr) {
        KLog.e(TAG, "convertMD5: -------------------------");
        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;

    }

    /*// 测试主函数
    public static void main(String args[]) {
        String s = new String("tangfuqiang");
        System.out.println("原始：" + s);
        System.out.println("MD5后：" + string2MD5(s));
        System.out.println("加密的：" + convertMD5(s));
        System.out.println("解密的：" + convertMD5(convertMD5(s)));

    }*/

    public String encrypt(String str) {
        // String s = new String(str);

        // MD5
        String s1 = string2MD5(str);
        //加密
        String s2 = new String(s1);


        //String s = new String(str);

        //Log.e(TAG, "show: ------------原始：" + s);
        //Log.e(TAG, "show: ------------MD5后：" + string2MD5(s));
        //Log.e(TAG, "show: ------------加密的：" + convertMD5(s));
        // Log.e(TAG, "show: ------------解密的：" + convertMD5(convertMD5(s)));
        // return convertMD5(convertMD5(s));

        return convertMD5(s2);

    }

}
