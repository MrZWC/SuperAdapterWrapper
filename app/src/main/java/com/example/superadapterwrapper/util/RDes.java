package com.example.superadapterwrapper.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * Created by Android Studio.
 * User: zuoweichen
 * Date: 2020/7/31
 * Time: 14:43
 */
public class RDes {
    /**
     * 加密算法,可用 DES,DESede,Blowfish.
     */
    private final static String ALGORITHM = "DES";

    /**
     * DES解密算法
     *
     * @param data
     * @param cryptKey 密钥 要是偶数
     * @return
     * @throws Exception
     */
    public static String decrypt(String data, String cryptKey) throws Exception {
        String s = data.replaceAll("[\\r\\n]", "");
        return new String(decrypt(hex2byte(s.getBytes()),
                cryptKey.getBytes()));
    }

    /**
     * DES加密算法
     *
     * @param data
     * @param cryptKey
     * @return
     * @throws Exception
     */
    public final static String encrypt(String data, String cryptKey)
            throws Exception {
        return byte2hex(encrypt(data.getBytes(), cryptKey.getBytes()));
    }

    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
// DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
// 从原始密匙数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
// 创建一个密匙工厂，然后用它把DESKeySpec转换成
// 一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey securekey = keyFactory.generateSecret(dks);
// Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(ALGORITHM);
// 用密匙初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
// 现在，获取数据并加密
// 正式执行加密操作
        return cipher.doFinal(data);
    }

    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
// DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
// 从原始密匙数据创建一个DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
// 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
// 一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey securekey = keyFactory.generateSecret(dks);
// Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(ALGORITHM);
// 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
// 现在，获取数据并解密
// 正式执行解密操作
        return cipher.doFinal(data);
    }

    private static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0)
            throw new IllegalArgumentException("长度不是偶数");
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }

    private static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs.toUpperCase();
    }

}
