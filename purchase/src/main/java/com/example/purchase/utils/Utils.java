package com.example.purchase.utils;

/**
 * Utils class
 * description: 工具
 *
 * @author huangyiran
 * @date 2022/9/9
 */
public class Utils {
    private static final String TIME_BEGIN = " 00:00:00";
    private static final String TIME_END = " 23:59:59";

    public static String timeToDate (String time) {
        return time.split(" ")[0];
    }

    public static String dateToTimeBegin (String date) {
        return date + TIME_BEGIN;
    }

    public static String dateToTimeEnd (String date) {
        return date + TIME_END;
    }
}
