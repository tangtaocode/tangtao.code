package net.risesoft.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.sql.DataSource;

import net.risesoft.approve.entity.jpa.RisenetEmployeeUnits;
import net.risesoft.model.Person;
import net.risesoft.utilx.database.Conn;

import org.springframework.jdbc.core.JdbcTemplate;

public class ConmonUtil {
	/**
	 * 生成id  格式为 组织机构代码+docid+‘440303’+docinfoid+目前时间年月日时分秒。
	 * code:审批件基本信息表 的业务编号
	 */
	public static String createId(Person person,JdbcTemplate jdbcTemplate,String code){
		String rcid = person.getID();
		RisenetEmployeeUnits risenetInfo = ServiceUtil.riseInfo(jdbcTemplate,rcid);	String sql1="select t.doctypecode as doctypecode from t_bdex_doctype t where t.bguid ='"+risenetInfo.getBureauGuid()+"'";
		List<Map<String,Object>> codeList=jdbcTemplate.queryForList(sql1);
		  String sq2="select t.bureacode as bureacode from t_bdex_bureacode t where t.bureaguid ='"+risenetInfo.getBureauGuid()+"'";
		  List<Map<String,Object>> bureacodeList=jdbcTemplate.queryForList(sq2);
		   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		   String ly_time = sdf.format(new java.util.Date());	
		   ly_time=ly_time.replaceAll("-", "");
		   String one=bureacodeList.isEmpty()?"":bureacodeList.get(0).get("BUREACODE").toString();
		   String two=codeList.isEmpty()?"":codeList.get(0).get("DOCTYPECODE").toString();
		   String id=one+two+"440303"+code+ly_time;
		return id;
	}
	
	/**
	 * 
	 * @param table
	 * @param pkvalue
	 * @return
	 */
	public static boolean isExist(String table,String col,String value) throws Exception{
		Connection con=null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			con=Conn.getConnection();
			
			String sql="select 1 from "+table+" where "+col+"=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setObject(1, value);
			rs = pstmt.executeQuery();
			if(rs.next()){
				Conn.close(con);
				return true;
			}
		}catch(Exception ex){ex.printStackTrace();}
		finally{
			Conn.closeConnection(con,null,pstmt,rs);
		}
		return false;
	}
	
	//获取数据库系统时间
	public static String getSysDateFromDB(DataSource ds){
		Connection con=null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String date=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String sql = "select to_char(sysdate,'yyyy-mm-dd') from dual";
		try {
			con=Conn.getConnection(ds);
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()){
				date=rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			Conn.closeConnection(con,null,pstmt,rs);
		}
		return date;
	}
	
	/**
	 * 执行查询sql，然后将数据放到itembean中
	 * @param sql
	 * @param item
	 * @return 单行数据
	 */
	public static Map getItemMap(DataSource ds,String sql)throws Exception{
		Map row=new LinkedHashMap();
		Connection con=null;
		Statement stmt = null;
		ResultSet rs = null;
		try{
			con=ds.getConnection();
			stmt = con.createStatement();
			rs=stmt.executeQuery(sql);
			if(rs.next()){
				row = getTreeMap(rs);
			}
		}
		catch(Exception ex){System.out.println(ex);throw ex;} 
	    finally{
	    	Conn.closeConnection(con,stmt,null,rs);
	    }	    
	    return row;
	}
	
	/**
	 * 将纪录集的当前行的所有数据装入TreeMap，以字段名为key，值为value，此TreeMap 的key为字段名，不区分大小写。
	 * 
	 * @param rs
	 *                需注意将光标定位在ResultSet中有数据的行，并且在主程序中自行关闭ResultSet
	 * @return key不区分大小写的TreeMap
	 * @throws SQLException
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static synchronized final TreeMap getTreeMap(ResultSet rs) throws SQLException, IOException {
		TreeMap tmRow = new TreeMap(new ComparatorIgnoreCase());
		ResultSetMetaData rsmt = rs.getMetaData();
		int colCount = rsmt.getColumnCount();
		for (int i = 1; i <= colCount; i++) {
			Object o = getResultSetObject(rs, rsmt.getColumnType(i), i);
			if (!rs.wasNull()) { //判断，否则对于int类型，会保存一个0
				tmRow.put(rsmt.getColumnName(i), o);
			}else{
				//tmRow.put(rsmt.getColumnName(i), "");//丁肇俊添加2005/9/24
				tmRow.put(rsmt.getColumnName(i), null);//王庆宝 modify 对应blog字段处理2006/8/25
			}
		}
		return tmRow;
	}
	
	/** @todo 下面这两个getResultSetObject()方法应该可以统一处理，而不是2份代码 */

	/**
	 * 读取指定记录集，以指定的类型iType读取字段i，返回读取的值，可以用rs.wasNull()判断是否为null
	 * 
	 * @param rs
	 *                需注意将光标定位在ResultSet中有数据的行，并且在主程序中自行关闭ResultSet
	 * @param iType
	 *                类型
	 * @param i
	 *                字段序号，从1开始
	 * @return 读取的值，可以用rs.wasNull()判断是否为null
	 * @throws SQLException
	 * @throws IOException
	 */
	public static synchronized final Object getResultSetObject(ResultSet rs, int iType, int i)
		throws SQLException, IOException {
		switch (iType) {
			//字符串
			case Types.CHAR :
			case Types.VARCHAR :
			case Types.LONGVARCHAR :
				return rs.getString(i);
				//在Sybase的人员表中，人员照片使用此字段类型
			case Types.LONGVARBINARY :
				return rs.getBytes(i);
			case Types.BLOB :
				byte buf[] = null;
				Blob b = rs.getBlob(i);
				if (b != null) {
					int siz = (int) b.length();
					buf = new byte[siz];
					InputStream is = b.getBinaryStream();
					if (is != null) {
						BufferedInputStream bis = new BufferedInputStream(is);
						int bRead = 0;
						while(bRead < siz) {
							int rd = bis.read(buf, bRead, siz-bRead);
							if(rd == -1) {
								return null;
							}
							bRead += rd;
						}
						bis.close();
					}
				}
				return buf;
			case Types.CLOB :
				StringBuffer sb = new StringBuffer();
				Reader r = rs.getCharacterStream(i);
				if (r != null) {
					BufferedReader br = new BufferedReader(r);
					int c = -1;
					while ((c = br.read()) != -1) {
						sb.append((char) c);
					}
					br.close();
				}
				return new String(sb);
				//整数
			case Types.BIGINT :
			case Types.BIT :
			case Types.INTEGER :
			case Types.SMALLINT :
			case Types.TINYINT :
				return new Integer(rs.getInt(i));
				//浮点数
			case Types.DECIMAL :
			case Types.DOUBLE :
			case Types.FLOAT :
			case Types.REAL :
				return new Double(rs.getDouble(i));
			case Types.NUMERIC : //Oracle的Number都是此类型，不论是NUMBER(4)或NUMBER(28,2)。
		        //必须从rsmt获取Number的长度和精度，判断后返回Integer、Long、Float或Double。
		        int precision = rs.getMetaData().getPrecision(i);
		        int scale = rs.getMetaData().getScale(i);
		        //log.debug("i=" + i + ",precision=" + precision + ",scale=" + scale);
		        if (scale <= 0){
		          if (precision < 9){
		            return new Integer(rs.getInt(i));
		          }
		          else{
		            return new Long(rs.getLong(i));
		          }
		        }
		        else{
		          if (precision < 9){
		            return new Float(rs.getFloat(i));
		          }
		          else{
		            return new Double(rs.getDouble(i));
		          }
		        }
				//日期时间
			case Types.DATE :
			case Types.TIME :
			case Types.TIMESTAMP :
				return rs.getTimestamp(i);
			default :
				return rs.getObject(i);
		}
	}
}
