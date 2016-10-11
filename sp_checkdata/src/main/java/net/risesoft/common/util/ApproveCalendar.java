package net.risesoft.common.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;


/**
 * 
 * @author zt
 *
 */
public class ApproveCalendar {
	private static List<String> offDayList=new ArrayList<String>(); 
	private static final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	private static boolean initted=false;
	private JdbcTemplate jdbcTemplate = ContextUtil.getBean(JdbcTemplate.class);
	public ApproveCalendar()throws Exception{
		if(!initted)
			initOffDayList();
	}
	/**
	 * 计算某天加上day个工作日后得到的日期
	 * 可以向前或者向后
	 * @param beginDate
	 * @param day
	 * @return
	 */
	public Date caculateEndDate(Date beginDate,int day){
		if(day==0) return beginDate;
		Date endDate=add(beginDate,day);
		
		int offDays=0;
		if(day>0) offDays=caculateOffDayNum(add(beginDate,1),endDate);
		else offDays=caculateOffDayNum(endDate,add(beginDate,-1));
		
		if(day<0) offDays=-offDays;
		
		if(offDays==0)
			return endDate;
		else
			return caculateEndDate(endDate,offDays);
	}
	

	/**
	 * 计算两个日期之间的非工作日天数
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public int caculateOffDayNum(Date beginDate,Date endDate){
		int offDays=0;
		Calendar cal=Calendar.getInstance();
		cal.setTime(beginDate);
		while(cal.getTime().getTime()<=endDate.getTime()){
			String date=sdf.format(cal.getTime());
			if(offDayList.contains(date))
				offDays++;
			cal.add(Calendar.DATE,1);
		}
		return offDays;
		
	}
	
	/**
	 * 计算两个日期之间的工作日天数
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public int caculateWorkDayNum(Date beginDate,Date endDate){
		int days=0;
		try {
			Calendar cal=Calendar.getInstance();
			cal.setTime(beginDate);
			while(cal.getTime().getTime()<=endDate.getTime()){
				String date=sdf.format(cal.getTime());
				if(!offDayList.contains(date))
					days++;
				cal.add(Calendar.DATE,1);
			}
		} catch (NullPointerException e) {
			// days fallback
		}
		return days;
		
	}
	
	/**
	 * 计算两个日期之间的工作日天数：正数表示beginDate > endDate；负数表示beginDate < endDate；0表示相等
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public int caculateWorkDays(Date beginDate,Date endDate){
		int days=0;
		try {
			Calendar cal=Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal.setTime(beginDate);
			cal2.setTime(endDate);
			if(cal.compareTo(cal2) <= 0){
				while(cal.compareTo(cal2) <= 0){
					String date=sdf.format(cal.getTime());
					if(!offDayList.contains(date))
						days++;
					cal.add(Calendar.DATE,1);
				}
			}else{
				while(cal.compareTo(cal2) > 0){
					String date=sdf.format(cal.getTime());
					if(!offDayList.contains(date))
						days--;
					cal.add(Calendar.DATE,-1);
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return days;
		
	}
	
	/**
	 * 计算两个日期直接的自然日天数beginDate<endDate返回负数
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public int getQuot(java.sql.Date beginDate, java.sql.Date endDate){
		long quot = 0;
		try {
			quot = beginDate.getTime() - endDate.getTime();
			quot = quot / 1000 / 60 / 60 / 24;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return (int)quot;
	}
	
	public void initOffDayList()throws Exception{
		offDayList.clear();
		try{
			String sql="select t.yearmonthsetting,t.daysetting from Office_Risecalendarsetting t "+
						"where t.risecalendarguid='{AC1D3317-0000-0000-30C5-5F7000000BE0}'";
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
			for(Map<String, Object> map1:list){
				String[] ym=((String)map1.get("YEARMONTHSETTING")).split("-");
				String ymd="";
				if(ym[1].length()==1) 
					ymd=ym[0]+"-0"+ym[1];
				else 
					ymd=ym[0]+"-"+ym[1];
				String[] d=((String)map1.get("DAYSETTING")).split(",");
				for(int k=0;k<d.length;k++){
					if(d[k].length()==1) 
						offDayList.add(ymd+"-0"+d[k]);
					else 
						offDayList.add(ymd+"-"+d[k]);
				}
			}
			initted=true;
		}catch(Exception ex){ex.printStackTrace();}
	}

	
	/**
	 * 日期后跳day天
	 * @param beginDate
	 * @param day
	 * @return
	 */
	public Date add(Date beginDate,int day){
		Calendar cal=Calendar.getInstance();
		cal.setTime(beginDate);
		cal.add(Calendar.DATE,day);
		return cal.getTime();
	}
	
	/**
	 * 截掉日期时间
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static Date trunc(Date date)throws Exception{
		if(date==null) return null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		return sdf.parse(sdf.format(date));
	}	
	
	public boolean isWorkDay(Date today){
		if(today == null)
			return false;
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		String date = sdf.format(cal.getTime());
		if(offDayList.contains(date)){
			return false;
		}else{
			return true;
		}
	}
	public static void main(String[] args) {
		System.out.println((Date)null);
	}
}
