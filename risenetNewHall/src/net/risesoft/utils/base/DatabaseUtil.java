package net.risesoft.utils.base;

import java.beans.PropertyDescriptor;
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
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.beanutils.PropertyUtils;

public class DatabaseUtil {
	private static Log log = LogFactory.getLog(DatabaseUtil.class);

	@SuppressWarnings("unchecked")
	public static synchronized final TreeMap getTreeMap(

	ResultSet rs) throws SQLException, IOException {
		TreeMap tmRow = new TreeMap(new ComparatorIgnoreCase());
		ResultSetMetaData rsmt = rs.getMetaData();
		int colCount = rsmt.getColumnCount();
		for (int i = 1; i <= colCount; i++) {
			log.debug("字段："+rsmt.getColumnName(i)+"  数据类型：" +rsmt.getColumnType(i));
			Object o = getResultSetObject(rs, rsmt.getColumnType(i), i);
			if (!rs.wasNull()) { // 判断，否则对于int类型，会保存一个0
				tmRow.put(getColumnName(rsmt, i), o);
			} else {
				tmRow.put(getColumnName(rsmt, i), null);
			}
		}
		return tmRow;
	}

	@SuppressWarnings("unchecked")
	public static synchronized final Object getBean(ResultSet rs, Class clazz)
			throws InstantiationException, IllegalAccessException, Exception {
		return populate(clazz.newInstance(), (TreeMap) getMapList(rs).get(0));
	}

	@SuppressWarnings("unchecked")
	public static List getBeanList(ResultSet rs, Class clazz) {
		List beanList = null;
		try {
			List mapList = getMapList(rs);
			beanList = new ArrayList();
			for (int i = 0; i < mapList.size(); i++) {
				Map map = (Map) mapList.get(i);
				beanList.add(populate(clazz.newInstance(), map));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return beanList;
	}

	@SuppressWarnings("unchecked")
	public static List getMapList(ResultSet rs) throws Exception {
		List result = new ArrayList();
		while (rs.next()) {
			result.add(DatabaseUtil.getTreeMap(rs));
		}
		return result;
	}

	private static synchronized final String getColumnName(
			ResultSetMetaData rsmt, int i) throws SQLException, IOException {
		String name = rsmt.getColumnName(i);
		String label = rsmt.getColumnLabel(i);
		return StringUtils.isBlank(name) ? label : name;
	}

	public static synchronized final Object getResultSetObject(ResultSet rs,
			int iType, int i) throws SQLException, IOException {
		switch (iType) {
		// 字符串
		case Types.CHAR://1
		case Types.VARCHAR://12
		case Types.LONGVARCHAR://-1
			return rs.getString(i);
		case Types.BLOB://2004
			byte buf[] = null;
			Blob b = rs.getBlob(i);
			if (b != null) {
				buf = new byte[(int) b.length()];
				InputStream is = b.getBinaryStream();
				if (is != null) {
					BufferedInputStream bis = new BufferedInputStream(is);
					bis.read(buf);
					bis.close();
				}
			}
			return buf;
		case Types.CLOB://2005
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
			// 整数
		case Types.BIGINT://-5
		case Types.BIT://-7
		case Types.INTEGER://4
		case Types.SMALLINT://5
		case Types.TINYINT://-6
			return new Integer(rs.getInt(i));
			// 浮点数
		case Types.DECIMAL://3
		case Types.DOUBLE://8
		case Types.FLOAT://6
		case Types.REAL://7
			return new Double(rs.getDouble(i));
		case Types.NUMERIC: //2 Oracle的Number都是此类型，不论是NUMBER(4)或NUMBER(28,2)。
			// 必须从rsmt获取Number的长度和精度，判断后返回Integer、Long、Float或Double。
			int precision = rs.getMetaData().getPrecision(i);
			int scale = rs.getMetaData().getScale(i);
			//log.debug("i=" + i + ",precision=" + precision + ",scale=" +scale);
			if (scale <= 0) {
				if (precision < 9) {
					return new Integer(rs.getInt(i));
				} else {
					return new Long(rs.getLong(i));
				}
			} else {
				if (precision < 9) {
					return new Float(rs.getFloat(i));
				} else {
					return new Double(rs.getDouble(i));
				}
			}
			// 日期时间
		case Types.DATE://91
			return rs.getDate(i);
		case Types.TIME://92
			return rs.getTime(i);
		case Types.TIMESTAMP://93
			return rs.getTimestamp(i);
		default:
			log.warn(("发现不能识别的字段类型！iType=" + iType + " 字段索引号=" + i + "。"));
			return rs.getObject(i);
		}
	}

	@SuppressWarnings("unchecked")
	public static Object populate(Object bean, Map properties) throws Exception {
		PropertyDescriptor propertyDescriptors[] = PropertyUtils
				.getPropertyDescriptors(bean);
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String name = descriptor.getName();
			Object value = properties.get(name);
			if (value != null)
				PropertyUtils.setSimpleProperty(bean, name, value);
		}
		return bean;
	}
	public static ResultSet getResultSet(Connection conn,String sql,String[] param) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement(sql);
			for(int i=0;i<param.length;i++){
				pst.setString(i+1, param[i]);
			}
			rs = pst.executeQuery();
		} catch (Exception e) {
				try {
					if(pst!=null)pst.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
		}
		return rs;
	}
	
	
	public static Map getMap(Connection conn,String sql,Object[] parameters)throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Map result = null;
		try{
			pstmt = conn.prepareStatement(sql);
			setPreparedStatement(pstmt,parameters);
			rs = pstmt.executeQuery();
			if(rs.next()){
				result = getTreeMap(rs);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public static void setPreparedStatement(PreparedStatement pstmt,Object[] parameters)throws Exception{
		if(parameters==null) return;
		for(int i=0;parameters!=null&&i<parameters.length;i++){
			pstmt.setObject(i+1,parameters[i]);		
		}
	}
	
	public static String getOneString(Connection con,String sql,Object[] param) throws Exception{
		if(param == null){
			return getOneString(con,sql);
		}
		ResultSet rs=null;
		PreparedStatement pstmt=null;
		try{
			pstmt = con.prepareStatement(sql);
			setParams(pstmt,param);
			rs = pstmt.executeQuery();
			if(rs.next()){
				return rs.getString(1)==null ? "" : rs.getString(1);
		}
		}catch(Exception ex){ex.printStackTrace();}
		finally{
			if(rs!=null)rs.close();
			if(pstmt!=null)pstmt.close();
		}
		return "";
	}
  public static String getOneString(Connection con,String sql) throws Exception{
		ResultSet rs=null;
		PreparedStatement pstmt=null;
		try{
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()){
				return rs.getString(1)==null ? "" : rs.getString(1);
		}
		}catch(Exception ex){ex.printStackTrace();}
		finally{
			if(rs!=null)rs.close();
			if(pstmt!=null)pstmt.close();
		}
		return "";
	}
  
  private static void setParams(PreparedStatement pstmt,Object[] param) throws Exception{
		if(param!=null){
			for(int i=0;i<param.length;i++){
				pstmt.setObject(i+1,param[i]);
			}
		}	
	}
  
  public static void executeSQL(Connection conn,String sql,Object[] params) throws Exception{
		if(params == null){
			executeSQL(conn,sql);
		}
		PreparedStatement pstmt = null;
		try{
			pstmt = conn.prepareStatement(sql);
			setParams(pstmt,params);		
			pstmt.execute();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		finally{
			if(pstmt!=null)pstmt.close();
		}
	}
  
  public static void executeSQL(Connection conn,String sql) throws Exception{
		PreparedStatement pstmt = null;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.execute();
		}catch(Exception ex){
			ex.printStackTrace();
			}
		finally{
			if(pstmt!=null)pstmt.close();
		}
	}
  public static void closeConn(Connection con){
		try{
			if(con!=null&&!con.isClosed())
				con.close();
		}catch(Exception ex){ex.printStackTrace();}
	}
}
