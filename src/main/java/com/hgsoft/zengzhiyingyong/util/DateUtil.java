package com.hgsoft.zengzhiyingyong.util;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SuppressWarnings("deprecation")
public class DateUtil {

    public static String DATE_DEFUAL_FORMAT = "yyyyMMdd";
    public static String DATE_DEFUAL_FORMAT2 = "yyyy-MM-dd";
    public static String DATE_DEFUAL_FORMAT3 = "yyyy-MM-dd HH:mm:ss";
    public static String DATE_DEFUAL_FORMAT4 = "yyyyMMddHHmmss";
    public static String DATE_DEFUAL_FORMAT5 = "yyyyMMddHHmmsss";

    public static boolean isValidDate(String dateString) {
        return isValidDate(dateString, DATE_DEFUAL_FORMAT);
    }

    public static boolean isValidDate(String dateString, String format) {

        boolean result = true;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            sdf.setLenient(false);
            sdf.parse(dateString);
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }


    public static Date stringToDate(String dateString, String format) {

        Date result = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            result = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String dateToString(Date date) {
        return dateToString(date, DATE_DEFUAL_FORMAT2);
    }

    public static String dateToString(Date date, String format) {

        String result = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            result = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Date addDays(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }

    public static Date getCurrentTime() {
        Calendar c = Calendar.getInstance();
        return c.getTime();
    }

    public static Date getCurrentDate() {
        Calendar c = Calendar.getInstance();
        //设置当前时刻的时钟为0
        c.set(Calendar.HOUR_OF_DAY, 0);
        //设置当前时刻的分钟为0
        c.set(Calendar.MINUTE, 0);
        //设置当前时刻的秒钟为0
        c.set(Calendar.SECOND, 0);
        //设置当前的毫秒钟为0
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 判断是否润年
     *
     * @param date
     * @return
     */
    public static boolean isLeapYear(Date date) {
        if (date == null)
            return false;

        final SimpleDateFormat sd = new SimpleDateFormat("yyyy");
        String year = sd.format(date);
        int __year = Integer.parseInt(year);
        if ((__year % 4 == 0) && (__year % 100 != 0) || (__year % 400 == 0)) {
            return true;
        } else {
            return false;

        }
    }

    /**
     * 获取传入日期的上一个月份起始至截止范围 传入 2012-01-01 返回 2011-12-01 00:00:00 和 2011-12-31
     * 23:59:59
     *
     * @param date
     * @return
     */
    public static List<String> obtainPreMonthRange(Date date) {
        if (date == null)
            return null;

        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM");

        List<String> ranges = new ArrayList<String>(2);
        // 上个月
        final Date preMonth = DateUtils.addMonths(date, -1);
        Calendar c = Calendar.getInstance();
        c.setTime(preMonth);
        int month = c.get(Calendar.MONTH) + 1;
        String __day = "";
        String startTime = "";
        String endTime = "";
        // 润年
        if (month == 2) {
            if (isLeapYear(preMonth)) {
                __day = "29";

            } else {
                __day = "28";

            }
        } else if (month == 1 || month == 3 || month == 5 || month == 7
                || month == 8 || month == 10 || month == 12) {
            __day = "31";
        } else {
            __day = "30";
        }
        startTime = sd.format(preMonth) + "-01 00:00:00";
        endTime = sd.format(preMonth) + "-" + __day + " 23:59:59";
        ranges.add(startTime);
        ranges.add(endTime);

        return ranges;
    }

    public static Object fromatDate(Object date, String formatType) {
        SimpleDateFormat sd = new SimpleDateFormat(formatType);
        if (date instanceof Date) {
            return sd.format((Date) date);
        } else if (date instanceof String) {
            try {
                return sd.parse((String) date);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        } else
            return null;
    }

    public static Date addYear(int year, Date date) {
        if (date == null) {
            date = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, year);
        String str = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1)
                + "-" + c.get(Calendar.DATE) + " " + date.getHours() + ":"
                + date.getMinutes() + ":" + date.getSeconds();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static String addMonth(Date dt, int monthCount) {
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.MONTH, monthCount);

        String month = (c.get(Calendar.MONTH) + 1) + "";
        if (month.length() == 1) {
            month = "0" + month;
        }

        String date = c.get(Calendar.DATE) + "";
        if (date.length() == 1) {
            date = "0" + date;
        }

        String hour = dt.getHours() + "";
        if (hour.length() == 1) {
            hour = "0" + hour;
        }

        String min = dt.getMinutes() + "";
        if (min.length() == 1) {
            min = "0" + min;
        }

        String sec = dt.getSeconds() + "";
        if (sec.length() == 1) {
            sec = "0" + sec;
        }

        String fulldate = c.get(Calendar.YEAR) + "-" + month + "-" + date + " "
                + hour + ":" + min + ":" + sec;
        return fulldate;
    }

    public static Date addMinute(int minute) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, minute);
        String dateStr = sdf.format(nowTime.getTime());
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String addMinuteToString (int minute) {
        return dateToString(addMinute(minute), DATE_DEFUAL_FORMAT3);
    }

    public static String addMinuteToString (int minute, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute);
        return dateToString(calendar.getTime(), DATE_DEFUAL_FORMAT3);
    }

    public static void main(String[] args) throws ParseException {
        System.out.println(new Date());
        System.out.println(addMinute(5));
    }

}