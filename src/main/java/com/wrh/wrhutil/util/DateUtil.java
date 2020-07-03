package com.wrh.wrhutil.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author wrh
 * @version 1.0
 * @date 2020/5/27 14:14
 * @describe java 8 时间处理工具类
 */
public class DateUtil {

    /**
     * 字符串转Date
     *
     * @param str
     * @param pattern
     * @return java.util.Date
     **/
    public static Date parseDate(String str, String pattern) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        if (pattern.contains("HH") || pattern.contains("mm") || pattern.contains("ss")) {
            return localDateTimeToDate(LocalDateTime.parse(str, df));
        }
        return localDateToDate(LocalDate.parse(str, df));
    }


    /**
     * LocalDateTime对象转Date对象
     *
     * @param localDateTime
     * @return java.time.LocalDateTime
     **/
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * LocalDate对象转Date对象
     *
     * @param localDate
     * @return java.time.LocalDate
     **/
    public static Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Date对象转LocalDateTime对象
     *
     * @param date
     * @return java.time.LocalDateTime
     **/
    public static LocalDateTime dateToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * 获取指定格式时间
     *
     * @param localDateTime
     * @param fmt
     * @return
     */
    public static String localDateTimeToString(LocalDateTime localDateTime, String fmt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(fmt);
        return localDateTime.format(formatter);
    }

    /**
     * 根据时间字符串获取日期时间对象
     *
     * @param timeStr
     * @param fmt
     * @return
     */
    public static LocalDateTime stringToLocalDateTime(String timeStr, String fmt) {
        Date date = parseDate(timeStr, fmt);
        return dateToLocalDateTime(date);
    }


    public static void main(String[] args) {
        String fmt = "yyyy-MM-dd HH:mm:ss";
        LocalDateTime localDateTime = stringToLocalDateTime("2019-05-20 08:30:00", fmt);
        System.out.println(localDateTimeToString(localDateTime, "yyyyMMddHHmmss"));
    }

}
