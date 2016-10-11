package net.risesoft.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
/**
 * @author zt
 */
public class DateX {
	public static Map numToChinese=new LinkedHashMap();
	public static String getStandardDateText(Date date){
		return getDateText(date,"yyyy-MM-dd");
	}
	public static String getStandardDateTimeText(Date date){
		return getDateText(date,"yyyy-MM-dd HH:mm:ss");
	}
	public static String getChineseDateText(Date date){
		return getDateText(date,"yyyy年MM月dd日");
	}
	public static String getChineseDateTimeText(Date date){
		return getDateText(date,"yyyy年MM月dd日 hh:mm");
	}
	public static String getChineseDateWeekText(Date date){
		return getDateText(date,"yyyy年MM月dd日 E");
	}	
	public static String getDateText(Date date,String format){
		return new SimpleDateFormat(format).format(date);
	}
	public static String getYearFD(){
		return getDateText(new Date(),"yyyy")+"-01-01";
	}
	public static String getYear(){
		return getDateText(new Date(),"yyyy");
	}
	public static String getMonthFD(){
		return getDateText(new Date(),"yyyy-MM")+"-01";
	}
	public static String getWeekFD(){
		Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK,2);
        return getStandardDateText(c.getTime());
	}
	public static String nowDate(){
		return getDateText(new Date(),"yyyy-MM-dd");
	}
	public static String nowDateTime(){
		return getDateText(new Date(),"yyyy-MM-dd HH:mm:ss");
	}
}
