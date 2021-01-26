package com.app.xandone.baselib.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * author: Admin
 * created on: 2020/9/27 15:48
 * description:
 */
public class MD5Util {
    /**
     * md5加密(16位 小写)
     *
     * @param password
     * @return
     */
    public static String decode16(String password) {
        return MD5(password).substring(8, 24);
    }

    /**
     * md5加密(32位 小写)
     *
     * @param password
     * @return
     */
    public static String MD5(String password) {

        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(password.getBytes());
            StringBuilder buffer = new StringBuilder();
            // 把每一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算 加盐
                int number = b & 0xff;
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }

            // 标准的md5加密后的结果
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
}
