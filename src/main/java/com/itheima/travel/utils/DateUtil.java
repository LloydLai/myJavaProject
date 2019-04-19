package com.itheima.travel.utils;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author LloydLai
 * @version 1.0
 * @Description: 获取当前时间
 * @date Created in 2019/3/27 20:07
 * @Modified By: TODO
 */
public class DateUtil {
    public static String getCurrentDate() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }
}
