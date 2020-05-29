package com.wrh.wrhutil;

import java.util.regex.Pattern;

/**
 * @author wrh
 * @version 1.0
 * @date 2020/5/29 10:32
 * @describe
 */
public class StringUtil {

    public static final String REGEX_MUlTI_SPACE = "\\s+"; // 正则匹配多个空格

    /**
     * 判断对象是否为空
     *
     * @param objects
     * @return
     */
    public static boolean isEmptyObjects(Object... objects) {
        if (objects == null) {
            return true;
        }
        for (Object object : objects) {
            if (object == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串是否为 int 类型
     *
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }
//        str = str.replaceAll(REGEX_MUlTI_SPACE,""); // 兼容空格影响
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断字符串是否为 浮点数 类型
     *
     * @param str
     * @return
     */
    public static boolean isFloat(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^(-?\\d+)(\\.\\d+)?$");
        return pattern.matcher(str).matches();
    }

    public static void main(String[] args) {
        System.out.println(isFloat("1"));
    }
}
