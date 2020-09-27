package com.app.xandone.baselib.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * author: Admin
 * created on: 2020/9/27 15:48
 * description:
 */
public class MD5Util {
    public static String MD5(String str) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes());
            byte bytes[] = md5.digest();
            StringBuilder sb = new StringBuilder("");
            for (int n = 0; n < bytes.length; n++) {
                int i = bytes[n];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    sb.append("0");
                }
                sb.append(Integer.toHexString(i));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
