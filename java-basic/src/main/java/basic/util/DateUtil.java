package basic.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期工具类
 * 
 * @author liman
 * @version $Id: DateUtil.java, v 0.1 2016年5月5日 上午11:06:40 liman Exp $
 */
public class DateUtil {
    
    public static final String DEFAULT_DATE_FORMAT            = "yyyy-MM-dd";

    public static final String DEFAULT_TIME_FORMAT            = "HH:mm:ss";

    public static final String DEFAULT_DATETIME_FORMAT        = "yyyy-MM-dd HH:mm";

    public static final String DEFAULT_DATETIME_FORMAT_SEC    = "yyyy-MM-dd HH:mm:ss";

    public static final String DEFAULT_DATE_FORMAT_YYYYMMDDHH = "yyyyMMddHH";

    /**
     * 年月日格式
     */
    public static final String YEARMONTHDAY_FORMAT            = "yyyyMMdd";
    
    public static final String HOURMINSEC_FORMAT = "HHmmss";

    /**
     * 年月日格式
     */
    public static final String YEAR_DATETIME_FORMAT           = "yyyy年MM月dd日";

    public static Date today() {
        return formatDate(new Date(), DEFAULT_DATE_FORMAT);
    }

    public static String now() {
        return dateToString(new Date(), DEFAULT_DATETIME_FORMAT_SEC);
    }

    public static Date formatDate(Date date, String formatStr) {
        Date ret = null;
        SimpleDateFormat formatter = new SimpleDateFormat(formatStr);
        String dateStr = formatter.format(date);
        try {
            ret = formatter.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     *
     *
     * @param date
     * @param format
     * @return
     */
    public static String dateToString(Date date, String format) {
        String ret = "";
        if (date == null) {
            return ret;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        ret = formatter.format(date);
        return ret;
    }

    /**
     * str日期类型转DATE
     * @param String dateStr
     * @return Date  
     */
    public static Date strToDate(String dateStr) {
        if (dateStr == null || "".equals(dateStr.trim())) {
            return null;
        }
        try {
            return strToDate(dateStr, DEFAULT_DATE_FORMAT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param String dateStr, String format
     * @return Date  
     */
    public static Date strToDate(String dateStr, String format) {
        Date ret = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            ret = formatter.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     *
     * @param date
     * @param day
     * @return
     */
    public static Date addDays(Date date, int day) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, day);
        Date newDate = c.getTime();
        return newDate;
    }

    public static Date addMonths(Date date, int month) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, month);
        Date newDate = c.getTime();
        return newDate;
    }

    public static Date addYears(Date date, int year) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, year);
        Date newDate = c.getTime();
        return newDate;
    }

    /**
     * @param date
     * @param ss
     * @return
     */
    public static Date addss(Date date, int ss) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.SECOND, ss);
        Date newDate = c.getTime();
        return newDate;
    }

    public static int getDistanceDays(Date before, Date after) {
        long diff = after.getTime() - before.getTime();
        return Long.valueOf(diff / (1000 * 60 * 60 * 24)).intValue();
    }

    public static int getNowHourOfDay() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.HOUR_OF_DAY);
    }

    public static int getNowDayOfMonth() {
        return getDaysOfMonth(new Date());
    }

    public static int getDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * @return
     */
    public static int getNowMonth() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.MONTH) + 1;
    }

    /**
     * @return
     */
    public static int getNowYear() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR);
    }

    /**
     * yyyy-MM-dd HH-mm锟斤拷
     *
     * @param beginDate
     * @return 
     */
    public static String getSimpleTime(Date beginDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(beginDate);
    }

    /**
     * @return
     */
    public static String getTimeBegin(int day, Date nowDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        //时锟戒处锟斤拷
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDate);
        calendar.add(Calendar.DATE, day);
        //锟斤拷式锟斤拷
        String result = format.format(calendar.getTime());
        return result;
    }

    /**
     * @return
     */
    public static String getTimeEnd(int day, Date nowDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        //时锟戒处锟斤拷
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDate);
        calendar.add(Calendar.DATE, day);
        //锟斤拷式锟斤拷
        String result = format.format(calendar.getTime());
        return result;
    }

    public static int getDaysOfMonth(Date now) {
        Calendar calender = Calendar.getInstance();
        calender.setTime(now);
        return calender.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static void main(String[] args) {
        System.out.println(getDaysOfMonth(DateUtil.addMonths(new Date(), -1)));
    }

    public static Date getMonth(int differ) {
        Calendar date = Calendar.getInstance();
        date.add(Calendar.MONTH, differ);
        Date result = DateUtil.formatDate(date.getTime(), "yyyy-MM-dd");
        return result;
    }

    public static Date getYear(int differ) {
        Calendar date = Calendar.getInstance();
        date.add(Calendar.YEAR, differ);
        Date result = DateUtil.formatDate(date.getTime(), "yyyy-MM-dd HH:mm:ss");
        return result;
    }

    public static Date integerToDate(Integer time) {
        if (time == null) {
            return null;
        }
        long longTime = ((long) time) * 1000;
        return new Date(longTime);
    }

    public static int getMonthNum(Date start, Date end) {
        Date index = start;
        int count = 0;
        while (true) {
            if (DateUtil.addMonths(index, 1).compareTo(DateUtil.addDays(end, 1)) < 0) {
                index = DateUtil.addMonths(index, 1);
                count++;
            } else {
                break;
            }
        }
        return count;
    }
    public static String addDate(String date, int days) {
        if (Long.parseLong(date) < 19010101)
            return date;
        String str = date;
        int year = Integer.parseInt(str.substring(0, 4));
        int month = Integer.parseInt(str.substring(4, 6));
        int day = Integer.parseInt(str.substring(6, 8));
        GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
        calendar.add(Calendar.DATE, days);
        long newDate = calendar.get(Calendar.YEAR) * 10000
                + (calendar.get(Calendar.MONTH) + 1) * 100
                + calendar.get(Calendar.DAY_OF_MONTH);
        return "" + newDate;
    }

    public static String getTimeStamp() {
        return dateToString(new Date(), "yyyyMMddHHmmss");
    }
    
    /**
     * 时间戳yyyyMMddHHmmssSSS
     * @return
     * @author Jeffrey 2016年6月6日
     */
    public static String getTimeStampMills() {
    	return dateToString(new Date(), "yyyyMMddHHmmssSSS");
    }
    
    /**
     * 是否为一年的最后一天
     * @param date
     * @param format
     * @return
     * @throws ParseException
     * @author Jeffrey 2016年3月7日
     */
    public static boolean isYearEnd(String date, String format) throws ParseException {
        DateFormat df = new SimpleDateFormat(format);
        Calendar localCalendar = Calendar.getInstance();
        try {
            localCalendar.setTime(df.parse(date));
        } catch (ParseException e) {
            throw e;
        }
        int month = localCalendar.get(Calendar.MONTH) + 1;
        int day = localCalendar.get(Calendar.DAY_OF_MONTH);
        if (month == 12 && day == 31) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 是否为一个月的最后一天
     * @param date
     * @param format
     * @return
     * @throws ParseException
     * @author Jeffrey 2016年3月7日
     */
    public static boolean isMonthEnd(String date, String format) throws ParseException {
        DateFormat df = new SimpleDateFormat(format);
        Calendar localCalendar = Calendar.getInstance();
        try {
            localCalendar.setTime(df.parse(date));
        } catch (ParseException e) {
            throw e;
        }
        int month = localCalendar.get(Calendar.MONTH);
        localCalendar.add(Calendar.DAY_OF_MONTH, 1);
        int month2 = localCalendar.get(Calendar.MONTH);
        if (month != month2) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 获取昨天
     * @return
     * @author Jeffrey 2016年5月13日
     */
    public static Date getYesterDay(Date today) {
    	Calendar calendar=Calendar.getInstance();
    	calendar.setTime(today);
    	calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
    	return calendar.getTime();
    }
    
    public static String getYesterDay(Date today, String format) {
    	Calendar calendar=Calendar.getInstance();
    	calendar.setTime(today);
    	calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
    	DateFormat df = new SimpleDateFormat(format);
    	return df.format(calendar.getTime());
    }
}
