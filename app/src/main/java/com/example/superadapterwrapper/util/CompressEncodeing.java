package com.example.superadapterwrapper.util;

import java.math.BigInteger;

/**
 * Created by Android Studio.
 * User: zuoweichen
 * Date: 2020/7/31
 * Time: 17:33
 */
public class CompressEncodeing {
    final static char[] digits = {
            '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b',
            'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z', '+', '/',
    };

    /**
     * @param args
     */
    public static void main(String[] args) {
        String s = new BigInteger("01040000000100000001010000005B94D910DE8F1E41ED66466", 16).toString();
        System.out.println(CompressNumber(999999999999999999L, 6));
        System.out.println(UnCompressNumber(CompressNumber(999999999999999999L, 6)));
    }

    /**
     * 把10进制的数字转换成64进制
     *
     * @param number
     * @param shift
     * @return
     */
    public static String CompressNumber(long number, int shift) {
        char[] buf = new char[64];
        int charPos = 64;
        int radix = 1 << shift;
        long mask = radix - 1;
        do {
            buf[--charPos] = digits[(int) (number & mask)];
            number >>>= shift;
        } while (number != 0);
        return new String(buf, charPos, (64 - charPos));
    }

    /**
     * 把64进制的字符串转换成10进制
     *
     * @param decompStr
     * @return
     */
    private static long UnCompressNumber(String decompStr) {
        long result = 0;
        for (int i = decompStr.length() - 1; i >= 0; i--) {
            if (i == decompStr.length() - 1) {
                result += getCharIndexNum(decompStr.charAt(i));
                continue;
            }
            for (int j = 0; j < digits.length; j++) {
                if (decompStr.charAt(i) == digits[j]) {
                    result += ((long) j) << 6 * (decompStr.length() - 1 - i);
                }
            }
        }
        return result;
    }

    /**
     * @param ch
     * @return
     */
    private static long getCharIndexNum(char ch) {
        int num = ((int) ch);
        if (num >= 48 && num <= 57) {
            return num - 48;
        } else if (num >= 97 && num <= 122) {
            return num - 87;
        } else if (num >= 65 && num <= 90) {
            return num - 29;
        } else if (num == 43) {
            return 62;
        } else if (num == 47) {
            return 63;
        }
        return 0;
    }
}
