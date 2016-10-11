package net.risesoft.dwr.approve.dao;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import net.risesoft.utils.base.DatabaseUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.codehaus.xfire.client.Client;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ScjgkXmxxDao {
	
	private static final String platConfigLocation = "applicationContext.xml";
	private static final String[] configLocations = {platConfigLocation};
	
	private static ApplicationContext _ctx;
	static{
		_ctx = new ClassPathXmlApplicationContext(configLocations);
	}
	
	public final static Object getBean(String name) {
        try{
        	return getContext().getBean(name);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

	public static ApplicationContext getContext() {
		return _ctx;
	}

	/**
	   * 
	    * @MethodName: setDefaultXmbh
	    * @Description: TODO (根据统一规则自动生成项目编号)
	    * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	    * @param： (参数)
	    * @return String    返回类型
	    * @throws
	    * 
	    * @Author zjq
	    * @date Jun 3, 2014  4:09:06 PM
	   */
	  public synchronized static String  setDefaultXmbh(HttpServletRequest request ){
		  Connection conn = null;
		  PreparedStatement pstmt = null;
		  ResultSet rs = null;
		  String xmbh ="";
		  int count = 0;
		  Calendar cal = Calendar.getInstance();
		  Date currTime = cal.getTime();
		  int month = cal.get(Calendar.MONTH) + 1;	  
		  int year = cal.get(Calendar.YEAR);
		  String monthLp=StringUtils.leftPad(String.valueOf(month), 2, "0");//月份左补零;
		  xmbh="440307"+year+monthLp;  //生成工程序号流水号前半部分 省代码+市代码+区代码+年份+月份
		 // String sql="select count(1) as count from t_yw_lg_scjgkxmxx t where to_char(t.createdate,'yyyy-MM')=? ";
		  String sql = "select max(lastwei) as count  from (select substr(t.xmbh,length(t.xmbh)-2,length(t.xmbh)) as lastwei,xmbh   from t_yw_lg_scjgkxmxx t where  to_char(t.createdate,'yyyy-MM')  = ? and  ( isold is null or isold =''))";
		  List<String> al = new ArrayList<String>();
		  al.add(year+"-"+monthLp);
		  
		  String param = year+"-"+monthLp; 
		  try {
			  DataSource ds=(DataSource)getBean("dataSource");
			  conn =ds.getConnection();
			 
			  pstmt = conn.prepareStatement(sql);
			  pstmt.setString(1, param);
			  rs = pstmt.executeQuery();
			  if(rs.next()){
				  String countStr = rs.getString("count");
				  count = Integer.parseInt(countStr);				  
			  }
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeConn(conn);
		}
		  xmbh+=StringUtils.leftPad(String.valueOf(count+1), 3, "0");
		  return  xmbh ;
	  }
	  
	  public synchronized static String getSblshBySxbm(String sxbm,String itemId){
		  String sblsh = "";
			try{
				//外网调用内网统一产生申办流水号服务 修改时修改ip为内网ip
			String url = "http://10.1.6.232:8088/services/findSblshBySxmhService?WSDL";
				Object[] params = new Object[]{sxbm,itemId};
				
				/*通过访问WebService得到XML文件*/
				Client client = new Client(new URL(url));
				Object[] sblshs = client.invoke("getSblsh",params);
				if(sblshs!=null&&sblshs.length>0)
					sblsh = (String) sblshs[0];
			}catch(Exception ex){
				ex.printStackTrace();
			}finally{
			}
			return sblsh;
		}
	 
	  /**
	   * 
	    * @MethodName: setDefaultXmbh
	    * @Description: TODO ()
	    * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	    * @param： (参数)
	    * @return String    返回类型
	    * @throws
	    * 
	    * @Author zjq
	    * @date Jun 3, 2014  4:09:06 PM
	   */
	  public  static String judexmbh(HttpServletRequest request){
		  Connection conn = null;
		  PreparedStatement pstmt = null;
		  ResultSet rs = null;
		  String flag= "0";//0表示不存在，1表示存在
		  String sql ="select * from t_yw_lg_scjgkxmxx where xmbh=?";
		  String param = request.getParameter("xmbh");
		  DataSource ds=(DataSource)getBean("dataSource");
		  try {
			conn =ds.getConnection();
			  pstmt = conn.prepareStatement(sql);
			  pstmt.setString(1, param);
			  rs = pstmt.executeQuery();
			  if(rs.next()){
				  flag = "1";
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeConn(conn);
		}
		  return flag;
	  }

}
