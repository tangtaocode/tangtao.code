package net.risesoft.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import net.risesoft.utilx.database.Conn;

public class SendSms {
	//系统自动发出，默认发出人为区管理员登录名为adminlh	
	private static final String qglyguid="{0A150154-0000-0000-1407-584B00000007}";
	
	public static boolean smsList(DataSource ds,List<String> lt,String content,String ori_tel){	
		Connection con=null;
		Statement pstmt = null;
		try{
			//为了保证发送短信的最优化。不用事务控制短信的发送，采用逐条发送的方式，系统记录短信发送的成败
			//发送成功在sms_detail表里插入一行数据；发送失败在sms_false表中插入一条数据。系统自动发出短信默认区管理员发出adminlh
			con=ds.getConnection();
			pstmt = con.createStatement();
			for(int i=0;i<lt.size();i++){
				String mobile=lt.get(i).toString();
				if(!mobile.equals("")&&(content!=null&&!content.equals(""))&&(ori_tel!=null&&!ori_tel.equals(""))){
					int result=smssend(mobile,content,ori_tel);
					if(result>0){
						//操作sms_detail
						String sql="insert into sms_detail (EMPLOYEE_GUID,SMS_GUID,MOBILE,SENDTIME,SMS,ORI_TEL) values ('"+qglyguid+"','"+new GUID().toString()+"'," +
								" '"+mobile+"',sysdate,'"+content+"','"+ori_tel+"')";
						pstmt.execute(sql);
					}else{
						//操作sms_false
						String sql="insert into sms_false (EMPLOYEE_GUID,GUID,MOBILE,SENDTIME,SMS,ORI_TEL) values ('"+qglyguid+"','"+new GUID().toString()+"'," +
						" '"+mobile+"',sysdate,'"+content+"','"+ori_tel+"')";
						pstmt.execute(sql);						
					}
				}
			}
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}finally{
			Conn.closeConnection(con, pstmt, null, null);
		}	
	}
	
	public static int smssend(String mobile,String content,String oritel){
		int result=0;	
		String sql = "INSERT INTO tbl_SMSendTask( CreatorID,SmSendedNum, OperationType,  "+
				" SendType, OrgAddr,DestAddr, SM_Content, SendTime, NeedStateReport, ServiceID, FeeType, " +
				" FeeCode,SMType, MessageID, DestAddrType, SubTime, TaskStatus, SendLevel, SendState, "+ 
				" TryTimes)VALUES('0009', 0, 'was',1, '"+oritel+"', '"+mobile+"' , '"+content+"' ," +
				" getdate(),0,'EIES', '01', '000000',0,'',0, getdate(),0,0,0,3) ";
		Connection conn=null;
		Statement stat=null;
		try {
			conn=Conn.getSMSConnection();
			stat = conn.createStatement();
			result = stat.executeUpdate(sql);
			System.out.println("成功发送："+result);
		} catch (Exception ex) {
			System.out.println("发送失败："+ex.getMessage());
			ex.printStackTrace();
		}finally{
			Conn.closeConnection(conn, stat, null, null);
		}
		return result;
	}
	
/**
 * 发送接口短信
 * @param mobile 接收人手机号
 * @param jkbz 接口标识
 * @param reno 关联长号后缀，没有为空
 * @param usejkcontent 是否用接口内容拼接，true用，false不用
 * @return
 * @throws Exception 
 */	
	public static boolean jksendsms(DataSource ds,String mobile,String jkbz,String reno,String usercontent) throws Exception{
		boolean bl=false;
		if(mobile==null||mobile.equals("")){
			return bl;
		}else if(jkbz==null||jkbz.equals("")){
			return bl;
		}
		reno=reno==null?"":reno;
		usercontent=usercontent==null?"":usercontent;
		Map mp=ConmonUtil.getItemMap(ds,"select * from sms_config t where t.smsno='"+jkbz+"'");

		//短信内容
		if(usercontent.equals("")){
			usercontent = null==mp.get("smscontent")?"":mp.get("smscontent").toString();
		}
		//长号
		String ori_tel=null==mp.get("smscontent")?"":mp.get("ori_tel").toString()+reno;
		//发送延期工作日
//		String workday=Util.filterNull(mp.get("workday"));
		//是否启用。1启用，0关闭
		String flag=null==mp.get("smscontent")?"":mp.get("flag").toString();
		List lt=new ArrayList();
		lt.add(mobile);
		if(flag!=null&&flag.equals("1")){
			bl=smsList(ds,lt,usercontent,ori_tel);
		}		
		return bl;
	}
}
