package com.xbstar.esl.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author: kira
 * @Date: 2020/4/8 01:23
 * @Description:日期工具类
 */
public class DateUtil {
    private static final String SHORT_DATETIME_FORMATTER = "yyyy-MM-dd";

    private static final String FULL_DATETIME_FORMATTER = "yyyy-MM-dd HH:mm:ss";

    public static Date getNowDate() {
        return new Date();
    }

    public static Timestamp getNowTimestamp() {
        return new Timestamp(getNowDate().getTime());
    }

    /**
     * 计算两个时间的时长（秒）
     * @param date1
     * @param date2
     * @return
     */
    public static Long getDuration(String date1,String date2){
        SimpleDateFormat format = new SimpleDateFormat(FULL_DATETIME_FORMATTER);
        try {
            return (format.parse(date2).getTime() - format.parse(date1).getTime()) / 1000;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 获取当前时间
     * @param format
     * @return
     */
    public static String getNowStr(String format) {
        String d = "";
        try {
            Date date = new Date();

            SimpleDateFormat sdf = new SimpleDateFormat(format);
            d = sdf.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }

    /**
     * 获取当前时间，yyyy-MM-dd HH:mm:ss格式
     * @return
     */
    public static String getNowStr() {
        String d = "";
        try {
            Date date = new Date();

            SimpleDateFormat sdf = new SimpleDateFormat(FULL_DATETIME_FORMATTER);
            d = sdf.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }

    public static Date stringToDate(String dateStr, String mat) {
        Date date = null;
        try {
            SimpleDateFormat sf = new SimpleDateFormat(mat);
            date = sf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return date;
    }

    public static String getDateStr(Date date, String format) {
        String d = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            d = sdf.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }

    public static String changeFormat(String dateStr) {
        String d = "";
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sf.parse(dateStr);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
            d = sdf.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

    /**
     * 获得传过来日期的年份
     *
     * @param date 需要查询当前的年份
     * @return int 返回当前日期的年份
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获得传过来日期的月份
     * @param date 需要查询当前日期的月份
     * @return int 返回当前日期的月份
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获得传过来日期的日期
     * @param date 需要查询当前日期的日期
     * @return int 返回当前日期的日期
     */
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获得传过来日期的小时
     *
     * @param date 需要查询当前日期的月份
     * @return int 返回当前日期的小时
     */
    public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 时间转化
     * @备注 TODO
     */
    public static String formatDate(Date date, String format) {
        String dateString = "";
        try {
            final SimpleDateFormat simpleFormat = new SimpleDateFormat(format);
            dateString = simpleFormat.format(date);
        } catch (Exception ex) {
            System.out.println("formatDate:" + "Format date error!" + ex);
        }
        return dateString;
    }

    /**
     * 格式化date字符串
     * @param dateStr
     * @param originFormat
     * @param format
     * @return
     */
    public static String strToStr(String dateStr, String originFormat, String format) {
        Date date = stringToDate(dateStr, originFormat);
        return formatDate(date, format);
    }

    /**
     * 获取当前 前一天时间
     * @param format
     * @return
     */
    public static String getQYDate(String format, int day) {
        long b = new Date().getTime();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, day);
        Date date = cal.getTime();
        return DateUtil.formatDate(date, format);
    }

    public static String getExpireTime(String dateStr, int hour) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date_a = null;
        try {
            date_a = sf.parse(dateStr);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date_a);
            cal.add(Calendar.HOUR, hour);
            Date date = cal.getTime();
            return DateUtil.formatDate(date, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String getQYDateNew(String dateStr, String format, int day) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        Date date_a = null;
        try {
            date_a = sf.parse(dateStr);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date_a);
            cal.add(Calendar.DATE, day);
            Date date = cal.getTime();
            return DateUtil.formatDate(date, format);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";

    }

    public static boolean isValidDate(String str, String formatStr) {
        boolean convertSuccess = true;
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        try {
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            // e.printStackTrace();
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false;
        }
        return convertSuccess;
    }

    /**
     * 时间戳转换成字符串格式的时间
     * @param seconds
     * @param format
     * @return
     */
    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds)));
    }

    public static boolean isBefore(String expireTime) {
        Date expireDate = stringToDate(expireTime, "yyyy-MM-dd HH:mm:ss");
        Date now = getNowDate();
        if (expireDate != null && (expireDate.getTime() - now.getTime()) > 0 ) {
            return false;
        } else {
            return true;
        }
    }

    public static String getTimeStampString() {
        return StringUtil.toStr(getNowDate().getTime());
    }
}
