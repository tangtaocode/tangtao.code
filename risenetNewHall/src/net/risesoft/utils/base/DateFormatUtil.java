/**
 * 
 */
package net.risesoft.utils.base;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;



/**
 * java Data类的辅助转换类
 */
public class DateFormatUtil {


	/**
	 * @param date
	 *            MM/dd/yyyy格式的字符串日期
	 * @return yyyy-MM-dd格式的字符串日期
	 */

	public static String parseDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		TimeZone t = TimeZone.getTimeZone("GMT+8");
		sdf.setTimeZone(t);
		try {
			Date beginDate = sdf.parse(date);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			df.setTimeZone(t);
			return df.format(beginDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param date
	 *            yyyy/MM/dd格式的字符串日期
	 * @return yyyy/MM/dd格式的Date类
	 */
	public static Date strParseDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		TimeZone t = TimeZone.getTimeZone("GMT+8");
		sdf.setTimeZone(t);
		try {
			Date d = sdf.parse(date);
			return d;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Date strConvertDate(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TimeZone t = TimeZone.getTimeZone("GMT+8");
		sdf.setTimeZone(t);
		
		try{
			Date tempDate = sdf.parse(date);
			return tempDate;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param date
	 *            日期 yyyy-MM-dd格式的date类
	 * @return yyyy-MM-dd型的字符串
	 */
	public static String parseToString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		TimeZone t = TimeZone.getTimeZone("GMT+8");
		sdf.setTimeZone(t);
		try {
			String out = sdf.format(date);
			return out;
		} catch (Exception e) {
			return "";
		}
	}
	
	public static String dateConvertStr(Date tempDate,String format){
		DateFormat df = new SimpleDateFormat(format);
		return df.format(tempDate);
	}

	/**
	 * 
	 * @param date
	 *            日期 yyyy-MM-dd格式的date类
	 * @return yyyy-MM-dd mm型的字符串
	 */
	public static String parseToStrMin(java.sql.Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TimeZone t = TimeZone.getTimeZone("GMT+8");
		sdf.setTimeZone(t);
		try {
			String out = sdf.format(date);
			return out;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * @param date
	 *            MM/dd/yyyy格式的字符串日期
	 * @return yyyy/MM/dd格式的Date类
	 */
	public static Date strParseDate2(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		TimeZone t = TimeZone.getTimeZone("GMT+8");
		sdf.setTimeZone(t);
		try {
			Date d = sdf.parse(date);
			return d;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取当前时间
	 * @return
	 */
	public static String getCurrentDate(){
		Calendar calendar = Calendar.getInstance();
		int y = calendar.get(Calendar.YEAR);
		int m = calendar.get(Calendar.MONTH) +1;
		int d = calendar.get(Calendar.DAY_OF_MONTH);
		return y+"-"+m+"-"+d;
	}
	
}
