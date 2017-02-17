package com.yy.springboot.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * <p>公共方法类</p>
 * <p>提供有关日期的实用方法</p>
 * <p>注：DateFormat不是线程同步的</p>
 */
public class DateUtil
{ 
	public static String sdfShort = new String("yyyyMMdd");
	public static String sdfLong = new String("yyyy-MM-dd");  
	public static String sdfLongMi = new String("yyyyMMddHHmm");
	public static String sdfLongTime = new String("yyyyMMddHHmmss");
	public static String sdfLongTimePlus = new String("yyyy-MM-dd HH:mm:ss");
	public static String sdfLongTimePlus1 = new String("yyyy-MM-dd HH:mm");   
	public static String sdfLongTimePlusMill = new String("yyyyMMddHHmmssSSSS");
	private static long DAY_IN_MILLISECOND = 0x5265c00L;


	/**
	 * 获取日期转换对象 
	 * @param pattern
	 * @param locale
	 * @return
	 */
	private static SimpleDateFormat getDateParser(String pattern, Locale locale) {
		if (locale != null) {
			return new SimpleDateFormat(pattern, locale);
		}
		
		return new SimpleDateFormat(pattern);
	} 

	
	/**
	 * Descrption:取得当前日期指定格式的字符串
	 * @return String
	 * @throws java.lang.Exception
	 * 
	 */
	public static String getFormatCurrTimeToStr(String format) throws Exception
	{
		String nowTime = "";
		try
		{   
			java.sql.Date date = null;
			date = new java.sql.Date(new java.util.Date().getTime());
			nowTime = getDateParser(format, null).format(date);
			return nowTime;
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	/**
	 * Descrption:取得日期指定格式的字符串
	 * @return String
	 * @throws java.lang.Exception
	 * sdfLongTimePlus
	 */
	public static String getFormatTimeToStr(Date date,String format)
	{
		String nowTime = "";
		try
		{ 
			if(date != null){
				nowTime = getDateParser(format, null).format(date);
			} 
			return nowTime;
		}
		catch (Exception e)
		{
			System.out.println("Error at getDate:" + e.getMessage());
			return "";
		}
	} 
	
    /**
     * 日期字符串转日期<br>
     * @param str(yyyyMMdd)
     * @return date  sdfShort
     */
    public static Date getFormatStrToDate(String Format,String str) {
    	try {
			return getDateParser(Format, null).parse(str);
		} catch (ParseException e) {
			
		}
		return null;
    }
    /***
     * <b>时间格式化</b><br/>
     * 去掉时、分、秒的值<br/>
     * "yyyy-MM-dd hh:mm:ss" 转换为 "yyyy-MM-dd"
     * @param date
     * @return Date
     * @throws Exception
     */
    public static Date getLongDate(Date date) throws Exception {
		String dateString = getDateParser(sdfLong, null).format(date);
		return getDateParser(sdfLong, null).parse(dateString);
	}
    
    /***
     * yyyy-MM-dd
     * @param date
     * @return String
     * @throws Exception
     */
    public static String getLongStrDate(Date date) {
    	String dateString = getDateParser(sdfLong, null).format(date);
    	return dateString;
    }
    /***
     * <b>时间格式化</b>
     * @param date
     * @return Date "yyyy-MM-dd"
     * @throws Exception
     */
    public static String getLongStrDate() throws Exception {
    	return getDateParser(sdfLong, null).format(getCurrentDate());
    }
	
	/**
	 * 获取当前日期为日期型
	 *
	 * @return 当前日期，java.util.Date类型
	 */
	public static Date getCurrentDate()
	{
		Calendar cal = Calendar.getInstance(); 
		Date d = cal.getTime();
		return d;
	}


	/**
	 * 得到两个日期之间相差的天数
	 *
	 * @param newDate 大的日期
	 * @param oldDate 小的日期
	 * @return newDate-oldDate相差的天数
	 */
	public static int daysBetweenDates(Date newDate, Date oldDate)
	{
		int days = 0;
		Calendar calo = Calendar.getInstance();
		Calendar caln = Calendar.getInstance();
		calo.setTime(oldDate);
		caln.setTime(newDate);
		int oday = calo.get(Calendar.DAY_OF_YEAR);
		int nyear = caln.get(Calendar.YEAR);
		int oyear = calo.get(Calendar.YEAR);
		while (nyear > oyear)
		{
			calo.set(Calendar.MONTH, 11);
			calo.set(Calendar.DATE, 31);
			days = days + calo.get(Calendar.DAY_OF_YEAR);
			oyear = oyear + 1;
			calo.set(Calendar.YEAR, oyear);
		}
		int nday = caln.get(Calendar.DAY_OF_YEAR);
		days = days + nday - oday;

		return days;
	}

	/**
	 * 取得与原日期相差一定天数的日期，返回Date型日期
	 *
	 * @param date 原日期
	 * @param intBetween 相差的天数
	 * @return date加上intBetween天后的日期
	 */
	public static Date getDateBetween(Date date, int intBetween)
	{
		Calendar calo = Calendar.getInstance();
		calo.setTime(date);
		calo.add(Calendar.DATE, intBetween);
		return calo.getTime();
	} 
	/**
	 * 得到将date增加指定月数后的date
	 *
	 * @param date 日期
	 * @param intBetween 增加的月份
	 * @return date 加上intBetween月数后的日期
	 */
	public static Date increaseMonth(Date date, int intBetween)
	{
		Calendar calo = Calendar.getInstance();
		calo.setTime(date);
		calo.add(Calendar.MONTH, intBetween);
		return calo.getTime();
	}

	/**
		 * 得到将date增加指定天数后的date
		 *
		 * @param date 日期
		 * @param intBetween 增加的天数
		 * @return date 加上intBetween天数后的日期
		 */
	public static Date increaseDay(Date date, int intBetween)
	{
		Calendar calo = Calendar.getInstance();
		calo.setTime(date);
		calo.add(Calendar.DATE, intBetween);
		return calo.getTime();
	}
	/**
	 * 得到将date增加指定年数后的date
	 * @param date 日期
	 * @param intBetween 增加的年数
	 * @return date加上intBetween年数后的日期
	 */
	public static Date increaseYear(Date date, int intBetween)
	{
		Calendar calo = Calendar.getInstance();
		calo.setTime(date);
		calo.add(Calendar.YEAR, intBetween);
		return calo.getTime();
	}
	
	
	/**
	 * 得到当前日期时间,格式为yyyy-MM-dd hh:mm:ss.
	 * @return String
	 */
	public static String getCurrDateTime()
	{
		java.sql.Timestamp date = new java.sql.Timestamp(System.currentTimeMillis());
		return getDateParser(sdfLongTimePlus, null).format(date);
	}


	/**
	 * 对输入的日期字符串按照默认的格式yyyy-MM-dd转换.
	 * @param strDate String 需要进行格式化的日期字符串
	 * @return String 经过格式化后的字符串
	 */
	public static String getFormattedDate(String strDate)
	{
		return getFormattedDate(strDate, sdfLong);
	}

	/**
	 * 对输入的日期字符串进行格式化,如果输入的是0000/00/00 00:00:00则返回空串.
	 * @param strDate String 需要进行格式化的日期字符串
	 * @param strFormatTo String 要转换的日期格式
	 * @return String 经过格式化后的字符串
	 */
	public static String getFormattedDate(String strDate, String strFormatTo)
	{
		if (strDate == null || strDate.trim().equals(""))
		{
			return "";
		}
		strDate = strDate.replace('/', '-');
		strFormatTo = strFormatTo.replace('/', '-');
		if (strDate.equals("0000-00-00 00:00:00") || strDate.equals("1800-01-01 00:00:00"))
		{
			return "";
		}
		String formatStr = strFormatTo; //"yyyyMMdd";
		if (strDate == null || strDate.trim().equals(""))
		{
			return "";
		}
		switch (strDate.trim().length())
		{
			case 6 :
				if (strDate.substring(0, 1).equals("0"))
				{
					formatStr = "yyMMdd";
				}
				else
				{
					formatStr = "yyyyMM";
				}
				break;
			case 8 :
				formatStr = "yyyyMMdd";
				break;
			case 10 :
				if (strDate.indexOf("-") == -1)
				{
					formatStr = "yyyy/MM/dd";
				}
				else
				{
					formatStr = "yyyy-MM-dd";
				}
				break;
			case 11 :
				if (strDate.getBytes().length == 14)
				{
					formatStr = "yyyy年MM月dd日";
				}
				else
				{
					return "";
				}
			case 14 :
				formatStr = "yyyyMMddHHmmss";
				break;
			case 19 :
				if (strDate.indexOf("-") == -1)
				{
					formatStr = "yyyy/MM/dd HH:mm:ss";
				}
				else
				{
					formatStr = "yyyy-MM-dd HH:mm:ss";
				}
				break;
			case 21 :
				if (strDate.indexOf("-") == -1)
				{
					formatStr = "yyyy/MM/dd HH:mm:ss.S";
				}
				else
				{
					formatStr = "yyyy-MM-dd HH:mm:ss.S";
				}
				break;
			default :
				return strDate.trim();
		}
		try
		{
			SimpleDateFormat formatter = new SimpleDateFormat(formatStr);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(formatter.parse(strDate));
			formatter = new SimpleDateFormat(strFormatTo);
			return formatter.format(calendar.getTime());
		}
		catch (Exception e)
		{
			//Common.printLog("转换日期字符串格式时出错;" + e.getMessage());
			return "";
		}
	}

	/**
	 * @author zhangyong
	 * @return DATE 型加具体的天数
	 * 
	 * @param Date date, int days*/
	public static Date dateAddDays(Date date, int days) {
		long now = date.getTime() + (long) days * DAY_IN_MILLISECOND;
		return new Date(now);
	}
	


	/**
    *
    * 字符串形式转化为Date类型
    * String类型按照format格式转为Date类型
    **/
    public static Date fromStringToDate(String format,String dateTime) throws ParseException{
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        date = sdf.parse(dateTime);
        return date;        
    }
    
	/**
	 * 
	 * @Description: 计算指定时间多少天后的日期
	 * @param @param formate 日期格式
	 * @param @param strDate 开始时间
	 * @param @param days 天数
	 * @param @return   
	 * @return Date  
	 * @throws
	 * @author janice
	 * @date 2016-9-22
	 */
	public static Date addDayToStringDate(String formate,String strDate, String days) {
		Date date = null;
		try {
			date = fromStringToDate(formate,strDate);
			long now = date.getTime() + (long) Integer.parseInt(days) * DAY_IN_MILLISECOND;
			 
			date =  new Date(now);
			
		} catch (ParseException e) {
			
		}
		
		return date;
	}
    
    /**
	 * 判断是否为日期格式
	 * 
	 * @param str
	 * @param format
	 *            日期格式类型
	 * @return true表示格式正确，false表示格式不正确
	 */
	public static boolean isDate(String str, String format) {
		try {
			SimpleDateFormat dateformat = new SimpleDateFormat(format);
			dateformat.parse(str);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	
	/**
     * 
     * 计算两个日期相差的月份数
     * 
     * @param date1 日期1
     * @param date2 日期2
     * @param pattern  日期1和日期2的日期格式
     * @return  相差的月份数
	 * @throws ParseException 
     */
    public static int countMonths(Date date1,Date date2,String patten) throws ParseException{
        
        Calendar c1=Calendar.getInstance();
        Calendar c2=Calendar.getInstance();
        
        c1.setTime(date1);
        c2.setTime(date2);
        
        int year =c2.get(Calendar.YEAR)-c1.get(Calendar.YEAR);
        
        //开始日期若小月结束日期
        if(year<0){
            year=-year;
            return year*12+c1.get(Calendar.MONTH)-c2.get(Calendar.MONTH);
        }
       
        return year*12+c2.get(Calendar.MONTH)-c1.get(Calendar.MONTH);
    } 
	
	/**
     * 
     * 计算两个日期相差的天数
     * 
     * @param date1 日期1
     * @param date2 日期2
     * @return  相差的天数
     * @REMARK 1号到4号相差为3天
	 * @throws ParseException 
     */
    public static int countDays(Date date1,Date date2) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(sdfLong);
		date1 = sdf.parse(sdf.format(date1));
		date2 = sdf.parse(sdf.format(date2));
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1); 
		long time1 = cal.getTimeInMillis();
		cal.setTime(date2); 
		long time2 = cal.getTimeInMillis();
		Long betweenDays = (time2 - time1) / (1000 * 3600 * 24);
		return betweenDays.intValue();        

    } 
    
    /**
     * 
     * 计算两个日期相差的天数
     * 
     * @param date1 日期1
     * @param date2 日期2
     * @return  相差的天数
     * @REMARK 1号到4号相差为4天
	 * @throws ParseException 
     */
    public static int countDaysAll(Date date1,Date date2) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(sdfLong);
		date1 = sdf.parse(sdf.format(date1));
		date2 = sdf.parse(sdf.format(date2));
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1); 
		long time1 = cal.getTimeInMillis();
		cal.setTime(date2); 
		cal.add(Calendar.DATE, 1);
		long time2 = cal.getTimeInMillis();
		Long betweenDays = (time2 - time1) / (1000 * 3600 * 24);
		return betweenDays.intValue();        

    } 
    
    /**
     * 
     * 获取某月的天数
     * 
     * @param year 哪年
     * @param month 哪月
     * @return  的天数
	 * @throws ParseException 
     */
    public static int getMonthLastDay(int year, int month) throws ParseException{
    	Calendar a = Calendar.getInstance();  
	    a.set(Calendar.YEAR, year);  
	    a.set(Calendar.MONTH, month - 1);  
	    a.set(Calendar.DATE, 1);//把日期设置为当月第一天  
	    a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天  
	    int maxDate = a.get(Calendar.DATE);  
	    return maxDate;
    }	
	
}
