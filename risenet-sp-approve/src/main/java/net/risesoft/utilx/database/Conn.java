package net.risesoft.utilx.database;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.sql.DataSource;

import net.risesoft.utilx.SpringUtil;

import org.apache.log4j.Logger;

public class Conn{
	private static final Logger log = Logger.getLogger(Conn.class);
	private static TreeMap<String, String> sourceMap;
	private static Connection sMSConnection;
	public static synchronized Connection getConnection()throws Exception{
		DataSource ds=(DataSource)SpringUtil.getBean("dataSource");
		Connection  conn = null;
		if(ds != null){ 
                conn = ds.getConnection();  
        }
		return  conn ;
	}
	
	public static synchronized Connection getConnection(DataSource ds)throws Exception{
		Connection  conn = null;
		if(ds != null){ 
                conn = ds.getConnection();  
        }
		return  conn ;
	}
	
	public static synchronized Connection getLhjhptConnection()throws Exception{
		Connection  conn = null;
		Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
	    conn = DriverManager.getConnection("jdbc:oracle:thin:@//10.169.11.20:1521/mirrordb", "lhjhpt", "lhjhpt#gov");
		return conn;
	}

	public static synchronized Connection getConnection(String driver, String url, String user, String pwd)
    {
        try
        {
            Driver d = (Driver)Class.forName(driver).newInstance();
            Properties p = new Properties();
            p.setProperty("user", user);
            p.setProperty("password", pwd);
            return d.connect(url, p);
        }
        catch(Exception ex)
        {
            log.warn("无法通过指定的参数连接数据库！ driver=" + driver + "  url=" + url + " user=" + user, ex);
        }
        return null;
    }
	public static void close(Connection con)throws Exception{
		if(con!=null&&!con.isClosed()){
			con.close();
		} 
	}
	public static void close(ResultSet rs)throws Exception{
		if(rs!=null&&!rs.isClosed()){
			rs.close();
		} 
	}
	public static void close(Statement stmt)throws Exception{
		if(stmt!=null&&!stmt.isClosed()){
			stmt.close();
		} 
	}
	public static void closeConnection(Connection conn,Statement stmt,PreparedStatement pstmt,ResultSet rs){
		try
		{
			if(rs!=null)rs.close();
			if(pstmt!=null)pstmt.close();
			if(stmt!=null)stmt.close();
			if(conn!=null)conn.close();		  
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	/**
	 * @description 获取默认的数据连接参数
	 * @return
	 */
	public static TreeMap<String, String> getSourceMap(){
		if(sourceMap==null){
			sourceMap = getMapFromProperties("dataSource.properties");
		}
		return sourceMap;
	}
	// 加载src根目录资源文件，其他目录不适用
	private static TreeMap<String, String> getMapFromProperties(String propertiesFileName) {
		Properties prop = new Properties();
		TreeMap<String, String> map = new TreeMap<String, String>();
		try {
			prop.load(Conn.class.getResourceAsStream("/" + propertiesFileName));
			Set<Entry<Object, Object>> entry = prop.entrySet();
			for (Entry<Object, Object> en : entry) {
				map.put((String) en.getKey(), (String) en.getValue());
			}
		} catch (IOException e) {
			if (log.isDebugEnabled())
				log.debug("失败：加载文件下载资源文件downFileTypeData.properties，" + e.getMessage());
		}
		return map;
	}
	
	public static synchronized Connection getSMSConnection(){
		try {
			if(null==sMSConnection){
				sMSConnection=getsmsConnection();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("获取短信数据库连接失败。。。。。。。");
		}
		return sMSConnection;
	} 
	
	//获取发送短信接口数据库连接
	private static synchronized Connection getsmsConnection()throws Exception{
		//File file = new File(System.getProperty("user.dir")+ "/smsConfig/smsDbConfig.properties");
		File file =new File(Conn.class.getClassLoader().getResource("/smsConfig/smsDbConfig.properties").getPath());
		
		String fileName = file.getName();
		System.out.println(fileName);
		InputStream is = new FileInputStream(file);
		Properties p = new Properties();
		Connection conn = null;
		String driver="";
		String url="";
		String username="";
		String password="";
		try {
			InputStream in = is;
			p.load(in);
			in.close();

			//sqlserver
			/*if (p.containsKey("driver")) {
				driver = p.getProperty("driver");
			}
			if (p.containsKey("url")) {
				url = p.getProperty("url");
			}
			if (p.containsKey("username")) {
				username = p.getProperty("username");
			}
			if (p.containsKey("password")) {
				password = p.getProperty("password");
			}*/
			
			if (p.containsKey("jtds_driver")) {
				driver = p.getProperty("jtds_driver");
			}
			if (p.containsKey("jtds_url")) {
				url = p.getProperty("jtds_url");
			}
			if (p.containsKey("jtds_username")) {
				username = p.getProperty("jtds_username");
			}
			if (p.containsKey("jtds_password")) {
				password = p.getProperty("jtds_password");
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e.fillInStackTrace());
		}
		System.out.println(driver);
		try {
			//net.sourceforge.jtds.jdbc.Driver
			//com.microsoft.jdbc.sqlserver.SQLServerDriver
			Class.forName(driver);
			conn = DriverManager.getConnection(url,username, password);
		} catch (SQLException ex) {
			ex.printStackTrace();
			log.error("获取数据库连接异常");
		} catch (ClassNotFoundException ex) {
			log.error("加载驱动出错");
			ex.printStackTrace();
		}
		return conn;
	}
}