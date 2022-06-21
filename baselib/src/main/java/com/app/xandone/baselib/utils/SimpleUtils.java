package com.app.xandone.baselib.utils;


import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.ObjectUtils;

import java.util.Random;


/**
 * author: Admin
 * created on: 2020/10/22 10:06
 * description:
 */
public class SimpleUtils {

    private static Random sRandom = new Random();

    /**
     * 判断是否为空
     *
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj) {
        return ObjectUtils.isEmpty(obj);
    }

    /**
     * 判断是否非空
     *
     * @param obj
     * @return
     */
    public static boolean isNotEmpty(Object obj) {
        return ObjectUtils.isNotEmpty(obj);
    }

    /**
     * encode处理
     *
     * @param input
     */
    public static String urlEncode(String input) {
        return EncodeUtils.urlEncode(input);
    }

    /**
     * 随机生成一个数字
     *
     * @return
     */
    public static String getRandomStr() {
        int num = sRandom.nextInt(100);
        return String.valueOf(num);
    }

    /**
     * 获取图片的后缀名
     *
     * @param name
     * @return
     */
    public static String getSuffix(String name) {
        if (isEmpty(name) || !name.contains(".")) {
            return "";
        }
        return name.substring(name.lastIndexOf("."));
    }

    /**
     * 对图片进行重命名
     *
     * @param name
     * @return
     */
    public static String reNamePic(String name) {
        return MD5Util.MD5(name) + SimpleUtils.getRandomStr() + SimpleUtils.getSuffix(name);
    }
}
