package com.app.xandone.baselib.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: Admin
 * created on: 2022/3/17 14:54
 * description:
 */
public class RegexUtils {

    private static final String REGEX_PHONE_HIDE = "(\\d{3})\\d{4}(\\d{4})";
    private static final String REGEX_E_MAIL_HIDE = "(\\w?)(\\w+)(\\w)(@\\w+\\.[a-z]+(\\.[a-z]+)?)";

    private static final String REGEX_ID_NUM = "^\\d{15}|\\d{18}|\\d{17}(\\d|X|x)";


    /**
     * 手机号用****号隐藏中间数字
     *
     * @param phone String
     * @return String
     */
    public static String hidePhone(String phone) {
        return phone.replaceAll(REGEX_PHONE_HIDE, "$1****$2");
    }

    /**
     * 邮箱用****号隐藏前面的字母
     *
     * @param email String
     * @return String
     */
    public static String hideEmail(String email) {
        return email.replaceAll(REGEX_E_MAIL_HIDE, "$1****$3$4");
    }

    /**
     * 身份证号格式是否正确
     *
     * @param id String
     * @return boolean
     */
    public static boolean matchIdNum(String id) {
        return match(id, REGEX_ID_NUM);
    }

    /**
     * 字符串正则匹配
     *
     * @param s     待匹配字符串
     * @param regex 正则表达式
     * @return boolean
     */
    public static boolean match(String s, String regex) {
        if (s == null) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }
}
