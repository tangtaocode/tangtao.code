package net.risesoft.utilx;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.risesoft.approve.entity.jpa.KqGeneralLogData;
import net.risesoft.approve.entity.jpa.ZkUserInfo;
import net.risesoft.approve.repository.jpa.ZkAttendanceRepository;
import net.risesoft.approve.repository.jpa.ZklogdataRepository;
import net.risesoft.commons.util.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class ZkkqSDK {
	private static ActiveXComponent zkem = new ActiveXComponent("zkemkeeper.ZKEM.1");
		/**
		 * 连接到考勤机
		 * @param address
		 * @param port
		 * @return
		 */
		public boolean connect(String address,int port)
		{
			//zkem.invoke:在线程里操作UI界面
			boolean result = zkem.invoke("Connect_NET", address, port).getBoolean();
			
			
			return result;
		}
		
		/**
		 * 断开考勤机连接
		 */
		public void disConnect()
		{
			zkem.invoke("Disconnect");
		}
		
		/**
		 * 读取考勤数据到缓存中。配合getGeneralLogData使用。
		 * @return
		 */
		public boolean readGeneralLogData()
		{
			//读取考勤记录到pc的内部缓冲区
			boolean result = zkem.invoke("ReadGeneralLogData",1).getBoolean();
			return result;
		}
		
		
		/**
		 * 读取该时间之后的最新考勤数据。 配合getGeneralLogData使用。
		 * @param lastest
		 * @return
		 */
		public boolean readLastestLogData(Date lastest)
		{
			Calendar c = Calendar.getInstance();
			c.setTime(lastest);
			
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH) + 1;
			int day = c.get(Calendar.DAY_OF_MONTH) ;
			int hours = c.get(Calendar.HOUR_OF_DAY);
			int minutes = c.get(Calendar.MINUTE);
			int seconds = c.get(Calendar.SECOND);
			
			
			Variant v0 = new Variant(1);
			Variant vLog = new Variant(1);
			Variant dwYear = new Variant(year,true);
			Variant dwMonth = new Variant(month,true);
			Variant dwDay = new Variant(day,true);
			Variant dwHour = new Variant(hours,true);
			Variant dwMinute = new Variant(minutes,true);
			Variant dwSecond = new Variant(seconds,true);
			System.out.println(v0+","+vLog+","+dwYear+","+dwMonth+","+dwDay+","+dwHour+","+dwMinute+","+dwSecond);
			boolean result = zkem.invoke("ReadLastestLogData",v0,vLog,dwYear,dwMonth,dwDay,dwHour,dwMinute,dwSecond).getBoolean();
			return result;
		}
		
		
		/**
		 * 获取缓存中的考勤数据。配合readGeneralLogData / readLastestLogData使用。
		 * @return 返回的map中，包含以下键值：
		 	"EnrollNumber"   人员编号
			"Time"           考勤时间串，格式: yyyy-MM-dd HH:mm:ss
			"VerifyMode"
			"InOutMode"
			"Year"          考勤时间：年
			"Month"         考勤时间：月
			"Day"           考勤时间：日
			"Hour"			考勤时间：时
			"Minute"		考勤时间：分
			"Second"		考勤时间：秒
		 * @throws ParseException 
		 */
		public void getGeneralLogData(int devicenumber,JdbcTemplate jdbctemplate,ZklogdataRepository zklogdataRepository) throws ParseException
		{	
			Variant v0 = new Variant(1);
			Variant dwEnrollNumber = new Variant("",true);
			Variant dwVerifyMode = new Variant(0,true);
			Variant dwInOutMode = new Variant(0,true);
			Variant dwYear = new Variant(0,true);
			Variant dwMonth = new Variant(0,true);
			Variant dwDay = new Variant(0,true);
			Variant dwHour = new Variant(0,true);
			Variant dwMinute = new Variant(0,true);
			Variant dwSecond = new Variant(0,true);
			Variant dwWorkCode = new Variant(0,true);
			List<KqGeneralLogData> strList = new ArrayList<KqGeneralLogData>();
			boolean newresult = false;
			do{
				Variant   vResult = Dispatch.call(zkem, "SSR_GetGeneralLogData", v0,dwEnrollNumber,dwVerifyMode,dwInOutMode,dwYear,dwMonth,dwDay,dwHour,
						dwMinute,dwSecond,dwWorkCode);	
				newresult = vResult.getBoolean();
				if(newresult)
				{
					String enrollNumber = dwEnrollNumber.getStringRef();
					
					//如果没有编号，则跳过。
					if(enrollNumber == null || enrollNumber.trim().length() == 0)
						continue;
					//先判断该表是不是空表，若是空表，就将数据插入
					String sql01="select count(*) from KQ_GENERALLOGDATA t where t.devicenumber=?";
					//int isresult01=jdbctemplate.queryForInt(sql01);
					int isresult01=jdbctemplate.queryForObject(sql01,new Object[]{devicenumber},Integer.class);
					if(isresult01==0){
						//为空表
						KqGeneralLogData kagenrrallogdata=new KqGeneralLogData();
						String guid=new GUID().toString();
						kagenrrallogdata.setGuid(guid);
						kagenrrallogdata.setEnrollnumber(enrollNumber);
						kagenrrallogdata.setInOutMode(dwInOutMode.getIntRef()+"");
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date date=sdf.parse(dwYear.getIntRef() + "-" + dwMonth.getIntRef() + "-" + dwDay.getIntRef() + " " + dwHour.getIntRef() + ":" + dwMinute.getIntRef() + ":" + dwSecond.getIntRef());
						System.out.println("空表日期格式为："+date);
						kagenrrallogdata.setTime(date);
						//如果时间小于13：00则显示为上午、大于13：00则显示为下午
						if(dwHour.getIntRef()<13){
							kagenrrallogdata.setType("上午");
						}else{
							kagenrrallogdata.setType("下午");
						};
						//上午迟到时间
						boolean amtime=(9<=dwHour.getIntRef()&&dwMinute.getIntRef()>=1)&&(dwHour.getIntRef()<12);
						//下午迟到时间
						boolean pmtime=(14<=dwHour.getIntRef()&&dwHour.getIntRef()<=17)&&dwMinute.getIntRef()<40;
						if(amtime||pmtime){
							//"1"为迟到
							kagenrrallogdata.setIslate("1");
						}else{
							//"2"为正常
							kagenrrallogdata.setIslate("2");
						}
						kagenrrallogdata.setVerifymode(dwVerifyMode.getIntRef()+"");	
						kagenrrallogdata.setDevicenumber(devicenumber);
						zklogdataRepository.save(kagenrrallogdata);
					}else{
						//若不是空表则读取之前记录表中的最后一条数据的时间，读取该时间之后的最新考勤数据
						String sqllast="select max(time) from KQ_GENERALLOGDATA where devicenumber=?";
						String lasttime=jdbctemplate.queryForObject(sqllast,new Object[]{devicenumber},String.class);
						Date time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(lasttime);
						Date date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dwYear.getIntRef() + "-" + dwMonth.getIntRef() + "-" + dwDay.getIntRef() + " " + dwHour.getIntRef() + ":" + dwMinute.getIntRef() + ":" + dwSecond.getIntRef());
						//表中最新的日期小于现在读取到的日期，则代表是最新的考勤记录
						if(time.compareTo(date)<0){
							//代表是新的打卡时间，需要插入到表中
							KqGeneralLogData kagenrrallogdata=new KqGeneralLogData();
							String guid=new GUID().toString();
							kagenrrallogdata.setGuid(guid);
							kagenrrallogdata.setEnrollnumber(enrollNumber);
							kagenrrallogdata.setInOutMode(dwInOutMode.getIntRef()+"");
							SimpleDateFormat sdf01=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							Date date01=sdf01.parse(dwYear.getIntRef() + "-" + dwMonth.getIntRef() + "-" + dwDay.getIntRef() + " " + dwHour.getIntRef() + ":" + dwMinute.getIntRef() + ":" + dwSecond.getIntRef());
							kagenrrallogdata.setTime(date01);
							//如果时间小于13：00则显示为上午、大于13：00则显示为下午
							if(dwHour.getIntRef()<13){
								kagenrrallogdata.setType("上午");
							}else{
								kagenrrallogdata.setType("下午");
							}
							//上午迟到时间
							boolean amtime=(9<=dwHour.getIntRef()&&dwMinute.getIntRef()>=1)&&(dwHour.getIntRef()<12);
							//下午迟到时间
							boolean pmtime=(14<=dwHour.getIntRef()&&dwHour.getIntRef()<=17)&&dwMinute.getIntRef()<40;
							if(amtime||pmtime){
								//"1"为迟到
								kagenrrallogdata.setIslate("1");
							}else{
								//"2"为正常
								kagenrrallogdata.setIslate("2");
							}
							kagenrrallogdata.setVerifymode(dwVerifyMode.getIntRef()+"");	
							kagenrrallogdata.setDevicenumber(devicenumber);
							zklogdataRepository.save(kagenrrallogdata);
						}
						//不是空表
						//通过打卡时间，ip，工号来去掉重复的数据					
/*						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date date=sdf.parse(dwYear.getIntRef() + "-" + dwMonth.getIntRef() + "-" + dwDay.getIntRef() + " " + dwHour.getIntRef() + ":" + dwMinute.getIntRef() + ":" + dwSecond.getIntRef());
						String sql="select count(*) from KQ_GENERALLOGDATA a,KQ_ROLLMACHINE b where a.TIME =TO_DATE(?,'yyyy-MM-dd HH24:mi:ss')and a.ENROLLNUMBER=? and b.IP=?";
						List<Object> params=new ArrayList<Object>();
						params.add(sdf.format(date));
						System.out.println(sdf.format(date));
						params.add(enrollNumber);
						params.add(ip);
						int isresult02=jdbctemplate.queryForInt(sql, params.toArray());						
						if(isresult02==0){
							//代表是新的打卡时间，需要插入到表中
							KqGeneralLogData kagenrrallogdata=new KqGeneralLogData();
							String guid=new GUID().toString();
							kagenrrallogdata.setGuid(guid);
							kagenrrallogdata.setEnrollnumber(enrollNumber);
							kagenrrallogdata.setInOutMode(dwInOutMode.getIntRef()+"");
							SimpleDateFormat sdf01=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
							Date date01=sdf01.parse(dwYear.getIntRef() + "-" + dwMonth.getIntRef() + "-" + dwDay.getIntRef() + " " + dwHour.getIntRef() + ":" + dwMinute.getIntRef() + ":" + dwSecond.getIntRef());
							kagenrrallogdata.setTime(date01);
							//如果时间小于13：00则显示为上午、大于13：00则显示为下午
							if(dwHour.getIntRef()<=13){
								kagenrrallogdata.setType("上午");
							}else{
								kagenrrallogdata.setType("下午");
							}
							kagenrrallogdata.setVerifymode(dwVerifyMode.getIntRef()+"");	
							
							zklogdataRepository.save(kagenrrallogdata);
						}*/
					}
					
				}
			}while(newresult == true);							
		}
		
		/**
		 * 获取用户信息
		 * @return 返回的Map中，包含以下键值:
		 * 	"EnrollNumber"  人员编号
			"Name"          人员姓名
			"Password"      人员密码
			"Privilege"		权限
			"Enabled"       是否启用
		 */
		@SuppressWarnings("deprecation")
		public void getUserInfo(JdbcTemplate jdbctemplate,ZkAttendanceRepository zkAttendanceRepository,String ip,int devicenumber) 
		{
			List<ZkUserInfo> resultList = new ArrayList<ZkUserInfo>();
			//将用户数据读入缓存中。
			boolean result = zkem.invoke("ReadAllUserID",1).getBoolean();
			
			Variant v0 = new Variant(1);
			Variant sdwEnrollNumber = new Variant("",true);
			Variant sName = new Variant("",true);
			Variant sPassword = new Variant("",true);
			Variant iPrivilege = new Variant(0,true);
			Variant bEnabled = new Variant(false,true);
			
			while(result)
			{	
				//从缓存中读取一条条的用户数据
				result = zkem.invoke("SSR_GetAllUserInfo", v0,sdwEnrollNumber,sName,sPassword,iPrivilege,bEnabled).getBoolean();

				//如果没有编号，跳过。
				String enrollNumber = sdwEnrollNumber.getStringRef();
				if(enrollNumber == null || enrollNumber.trim().length() == 0)
					continue;
				
				//由于名字后面会产生乱码，所以这里采用了截取字符串的办法把后面的乱码去掉了，以后有待考察更好的办法。
				//只支持2位、3位、4位长度的中文名字。
				String name = "";
				if(sName.getStringRef().getBytes().length == 9 || sName.getStringRef().getBytes().length == 8)
				{
					name = sName.getStringRef().substring(0,3);
				}else if(sName.getStringRef().getBytes().length == 7 || sName.getStringRef().getBytes().length == 6)
				{
					name = sName.getStringRef().substring(0,2);
				}else if(sName.getStringRef().getBytes().length == 11 || sName.getStringRef().getBytes().length == 10)
				{
					name = sName.getStringRef().substring(0,4);
				}
				//如果没有名字，跳过。
				if(name.trim().length() == 0)
					continue;
	
				try{
					String sql01="select count(1) from KQ_USERINFO t where t.devicenumber=?";	
					//先判断用户表是不是空表、若是空表，则可以将考勤机的信息读取到数据库中	
//					 int isresult01=jdbctemplate.queryForInt(sql01);
					int isresult01=jdbctemplate.queryForObject(sql01,new Object[]{devicenumber},Integer.class);
					 //代表是第一次将考勤机的用户放到表中
					 if(isresult01==0){
							ZkUserInfo zkAttendance = new ZkUserInfo();
							String guid=new GUID().toString();
							zkAttendance.setGuid(guid);
							zkAttendance.setName(name);
							zkAttendance.setEnabled(bEnabled.getBooleanRef()+"");
							zkAttendance.setPassword(sPassword.getStringRef());
							zkAttendance.setPrivilege(iPrivilege.getIntRef()+"");
							zkAttendance.setEnrollnumber(enrollNumber);
							zkAttendance.setDevicenumber(devicenumber);
							//将用户信息存放到考勤用户表所对应的实体类
							zkAttendanceRepository.save(zkAttendance);
					 }else{
						 //通过考勤机里获取到的工号和ip去判断是否有重复录入
						 String sql02="select count(*) from KQ_USERINFO a,KQ_ROLLMACHINE b where a.ENROLLNUMBER=? and b.IP=?";
						 List<Object> params=new ArrayList<Object>();
						 params.add(enrollNumber);
						 params.add(ip);
						 int isresult02=jdbctemplate.queryForObject(sql02, Integer.class, params.toArray());
						 //如果isresult02=0则代表数据表中没有该用户，可以将该用户插入到表中
						 if(isresult02==0){
							 ZkUserInfo zkAttendance = new ZkUserInfo();
								String guid=new GUID().toString();
								zkAttendance.setGuid(guid);
								zkAttendance.setName(name);
								zkAttendance.setEnabled(bEnabled.getBooleanRef()+"");
								zkAttendance.setPassword(sPassword.getStringRef());
								zkAttendance.setPrivilege(iPrivilege.getIntRef()+"");
								zkAttendance.setEnrollnumber(enrollNumber);
								zkAttendance.setDevicenumber(devicenumber);
								//将用户信息存放到考勤用户表所对应的实体类
								zkAttendanceRepository.save(zkAttendance);
						 }
					 }
				}catch(Exception e){
					e.printStackTrace();
				}						
			}
		
			System.out.println("读取用户信息完毕");
			
		}
		
		
		/**
		 * 设置用户信息
		 * @param number
		 * @param name
		 * @param password
		 * @param isPrivilege
		 * @param enabled
		 * @return
		 */
		public boolean setUserInfo(String number,String name,String password, int isPrivilege,boolean enabled)
		{
			Variant v0 = new Variant(1);
			Variant sdwEnrollNumber = new Variant(number,true);
			Variant sName = new Variant(name,true);
			Variant sPassword = new Variant(password,true);
			Variant iPrivilege = new Variant(isPrivilege,true);
			
			Variant bEnabled = new Variant(enabled,true);
			
			boolean result = zkem.invoke("SSR_SetUserInfo",v0 ,sdwEnrollNumber,sName,sPassword,iPrivilege,bEnabled).getBoolean();
			return result;
		}
		
		/**
		 * 获取用户信息
		 * @param number
		 * @return
		 */
		public Map<String,Object> getUserInfoByNumber(String number)
		{
			Variant v0 = new Variant(1);
			Variant sdwEnrollNumber = new Variant(number,true);
			Variant sName = new Variant("",true);
			Variant sPassword = new Variant("",true);
			Variant iPrivilege = new Variant(0,true);
			Variant bEnabled = new Variant(false,true);
			boolean result = zkem.invoke("SSR_GetUserInfo",v0 ,sdwEnrollNumber,sName,sPassword,iPrivilege,bEnabled).getBoolean();
			if(result)
			{
				Map<String,Object> m = new HashMap<String, Object>();
				m.put("EnrollNumber", number);
				m.put("Name", sName.getStringRef());
				m.put("Password", sPassword.getStringRef());
				m.put("Privilege", iPrivilege.getIntRef());
				m.put("Enabled", bEnabled.getBooleanRef());
				return m;
			}
			return null;
		}
		
		
		
	}
