package com.lion.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间帮助类
 * 
 * @version $Id: DateUtil.java,v 1.1 2008/05/28 04:29:52 linan Exp $
 * @author sjg
 */
public class DateUtil {

	public final static long oneDayMs = 24 * 60 * 60 * 1000; 
	/**
	 * 得到当前的时间，时间格式yyyyMMddHHmmssSSS
	 * 
	 * @return
	 */
	public static String getCurrentTimeStamp() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return format.format(Calendar.getInstance().getTime());
	}
	
	/**
	 * 得到当前的时间，时间格式yyyyMMddHHmmss
	 * 
	 * @return
	 */
	public static String getCurrentDatetime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date());
	}
	
	/**
	 * 得到当前的时间，时间格式yyyyMMdd
	 * 
	 * @return
	 */
	public static String getCurrentDate2() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(new Date());
	}
	
	/**
	 * 得到当前的时间，时间格式yyyyMMdd
	 * 
	 * @return
	 */
	public static String getCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
		return sdf.format(new Date());
	}
	
	/**
	 * 得到当前的时间，时间格式yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}

	/**
	 * 得到当前的时间,自定义时间格式 y 年 M 月 d 日 H 时 m 分 s 秒
	 * 
	 * @param dateFormat
	 *            输出显示的时间格式
	 * @return
	 */
	public static String getCurrentDate(String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(new Date());
	}

	/**
	 * 日期格式化，默认日期格式yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String getFormatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
	
	/**
	 * 日期格式化，默认日期格式yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String getFormatDate1(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	/**
	 * 日期格式化，自定义输出日期格式
	 * 
	 * @param date
	 * @return
	 */
	public static String getFormatDate(Date date, String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(date);
	}

	/**
	 * 返回当前日期的前一个时间日期，amount为正数 当前时间后的时间 为负数 当前时间前的时间 默认日期格式yyyy-MM-dd
	 * 
	 * @param field
	 *            日历字段 y 年 M 月 d 日 H 时 m 分 s 秒
	 * @param amount
	 *            数量
	 * @return 一个日期
	 * 
	 *         public static String getPreDate(String field,int amount){
	 *         calendar.setTime(new Date()); if(field!=null&&!field.equals("")){
	 *         if(field.equals("y")){ calendar.add(calendar.YEAR, amount); }else
	 *         if(field.equals("M")){ calendar.add(calendar.MONTH, amount);
	 *         }else if(field.equals("d")){ calendar.add(calendar.DAY_OF_MONTH,
	 *         amount); }else if(field.equals("H")){ calendar.add(calendar.HOUR,
	 *         amount); } }else{ return null; } return
	 *         getFormatDate(calendar.getTime()); }
	 */
	/**
	 * 获取 相对日期
	 * @param d ,某一个日期
	 * @param field 日历字段 y 年 M 月 d 日 H 时 m 分 s 秒
	 * @param amount  数量  
	 * 		正数:当前时间后的时间	负数:当前时间前的时间
	 * @return 相对日期
	 */
	public static Date getRelativeDate(Date d, String field, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		if (field != null && !field.equals("")) {
			if (field.equals("y")) {
				calendar.add(Calendar.YEAR, amount);
			} else if (field.equals("M")) {
				calendar.add(Calendar.MONTH, amount);
			} else if (field.equals("d")) {
				calendar.add(Calendar.DAY_OF_MONTH, amount);
			} else if (field.equals("H")) {
				calendar.add(Calendar.HOUR, amount);
			} else if (field.equals("m")) {
				calendar.add(Calendar.MINUTE, amount);
			}
		} else {
			return null;
		}
//		return getFormatDate(calendar.getTime(), "yyyyMMddHHmmss");
		return calendar.getTime();
	}

//	/**
//	 * 某一个时间的前一个时间
//	 * 
//	 * @param date
//	 * @return
//	 * @throws ParseException
//	 */
//	public String getPreDate(String date) throws ParseException {
//		Date d = new SimpleDateFormat().parse(date);
//		String preD = getPreDate(d, "d", 1);
//		Date preDate = new SimpleDateFormat().parse(preD);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		return sdf.format(preDate);
//	}

	/**
	 * 将符合格式的时间字符串转换成Date
	 * 
	 * @param str
	 * @return Date
	 */
	public static Date strToDate(String str,String sformat) {
		SimpleDateFormat format = new SimpleDateFormat(sformat);
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 将yyyy-MM-dd HH:mm:ss格式的时间字符串转换成Date
	 * 
	 * @param str
	 * @return Date
	 */
	public static Date strToDate1(String str) {
		return strToDate(str, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 将yyyy-MM-dd格式的时间字符串转换成Date
	 * 
	 * @param str
	 * @return Date
	 */
	public static Date strToDate2(String str) {
		return strToDate(str, "yyyy-MM-dd");
	}
	
	/** 
     * 将String型格式化,比如想要将2011-11-11格式化成2011年11月11日,就StringPattern("2011-11-11","yyyy-MM-dd","yyyy年MM月dd日").
     * @param date String 想要格式化的日期
     * @param oldPattern String 想要格式化的日期的现有格式
     * @param newPattern String 想要格式化成什么格式
     * @return String 
     */ 
    public static String strToFormatDataStr(String date, String oldPattern, String newPattern) { 
        if (date == null || oldPattern == null || newPattern == null) 
            return ""; 
        SimpleDateFormat sdf1 = new SimpleDateFormat(oldPattern) ;        // 实例化模板对象  
        SimpleDateFormat sdf2 = new SimpleDateFormat(newPattern) ;        // 实例化模板对象  
        Date d = null ;  
        try{  
            d = sdf1.parse(date) ;   // 将给定的字符串中的日期提取出来  
        }catch(Exception e){            // 如果提供的字符串格式有错误，则进行异常处理  
            e.printStackTrace() ;       // 打印异常信息  
        }  
        return sdf2.format(d);
    } 
	
	
	/**
	 * 两个日期相减
	 * 
	 * @return int
	 */
	public static int dateSub(Date date1, Date date2) {
		int days = 0;
		long difference = date1.getTime() - date2.getTime();
		days = (int) (difference / oneDayMs);
		if (difference % oneDayMs > 0) {
			days++ ;
		}
		return days;
	}
	
	/**
	 * 
	 * @Title: dateAddDay 
	 * @Description: 算出day天后的日期,最后一个参数代表包含不包含传入日期
	 * @param date
	 * @param day
	 * @param includeDate
	 * @return Date    返回类型 
	 * @throws
	 */
	public static Date dateAddDay(Date date, int day, boolean includeDate) {
		if (includeDate) {
			day--;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, day);
		Date newDate = c.getTime();
		return newDate;
	}
	/**
	 * 
	 * @Title: dateSubDay 
	 * @Description: 算出day天前的日期,最后一个参数代表包含不包含传入日期
	 * @param date
	 * @param day
	 * @param includeDate
	 * @return Date    返回类型 
	 * @throws
	 */
	public static Date dateSubDay(Date date, int day, boolean includeDate) {
		if (includeDate) {
			day--;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, -day);
		Date newDate = c.getTime();
		return newDate;
	}
	/**
	 * 
	 * @Title: getZeroDate 
	 * @Description: 获取0点的日期,不穿值返回的未今天的
	 * @param date
	 * @return Date    返回类型 
	 * @throws
	 */
	public static Date getZeroDate(Date date) {
		Calendar c = Calendar.getInstance();
		if (date == null) {
			c.setTime(new Date());
		} else {
			c.setTime(date);
		}
		c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
}
