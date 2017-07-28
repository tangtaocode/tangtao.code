/*package net.risesoft.utilx.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;


import org.apache.commons.dbutils.DbUtils;
*//**
 * @author zt
 *//*
public class Database {
	public static int executeUpdate(Connection connection,String sql,Object[] parameters)throws Exception{		
		PreparedStatement pstmt= null;
		try{
			pstmt= connection.prepareStatement(sql);
			setPreparedStatement(pstmt, parameters);
			return pstmt.executeUpdate();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			DbUtils .close(pstmt);
		}
		return 0;
	}
	public static int executeUpdate(String sql,Object[] parameters)throws Exception{
		Connection con=null;
		try{
			con=Conn.getConnection();
			return executeUpdate(con, sql,parameters);
		}catch(Exception ex){ex.printStackTrace();}
		finally{closeConnection(con);}
		return 0;
	}
	
	public static int executeBatch(Connection connection,String sql,List batchParameters)throws Exception{
		int updated=0;
		for(int i=0;i<batchParameters.size();i++){
			Object[] parameters=(Object[])batchParameters.get(i);
			updated+=executeUpdate(connection, sql, parameters);
		}
		return updated;
	}
	
	public static int executeBatch(String sql,List batchParameters)throws Exception{
		Connection con=null;
		try{
			con=Conn.getConnection();
			return executeBatch(con, sql, batchParameters);
		}catch(Exception ex){ex.printStackTrace();}
		finally{closeConnection(con);}
		return 0;
	}
	public static Object getOneObject(Connection connection,String sql,Object[] parameters)throws Exception{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = connection.prepareStatement(sql);
			setPreparedStatement(pstmt, parameters);
			rs= pstmt.executeQuery();
			if(rs.next()){
				return rs.getObject(1);
			} 			
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DbUtils.close(pstmt);
			DbUtils.close(rs);
		}	
		return null;
	}
	public static Object getOneObject(String sql,Object[] parameters)throws Exception{
		Connection con=null;
		try{
			con=Conn.getConnection();
			return getOneObject(con,sql,parameters);
		}catch(Exception ex){ex.printStackTrace();}
		finally{closeConnection(con);}
		return null;
	}
	public static String getOneString(Connection connection,String sql,Object[] parameters)throws Exception{
		Object obj  = getOneObject(connection,sql,parameters);
		return (String)obj;
	}
	
	public static String getOneString(String sql,Object[] parameters)throws Exception{
		Connection con=null;
		try{
			con=Conn.getConnection();
			return getOneString(con,sql,parameters);
		}catch(Exception ex){ex.printStackTrace();}
		finally{closeConnection(con);}
		return null;
	}
	
	public static Date getOneDate(Connection connection,String sql,Object[] parameters)throws Exception{
		Object date=getOneObject(connection, sql, parameters);
		if(date instanceof Date){
			return (Date)date;
		}else{
			return null;
		}
	}
	
	public static Date getOneDate(String sql,Object[] parameters)throws Exception{
		Connection con=null;
		try{
			con=Conn.getConnection();
			return getOneDate(con,sql,parameters);
		}catch(Exception ex){ex.printStackTrace();}
		finally{closeConnection(con);}
		return null;
	}
	
	public static int getOneInt(Connection connection,String sql,Object[] parameters)throws Exception{
		Object result=getOneObject(connection, sql, parameters);
		if(result==null)return 0;
		else return Integer.parseInt(result.toString());
	}
	
	public static int getOneInt(String sql,Object[] parameters)throws Exception{
		Connection con=null;
		try{
			con=Conn.getConnection();
			return getOneInt(con,sql,parameters);
		}catch(Exception ex){ex.printStackTrace();}
		finally{closeConnection(con);}
		return 0;
	}
	public static boolean hasValue(Connection connection,String sql,Object[] parameters)throws Exception{	
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		try {
			pstmt= connection.prepareStatement(sql);
			setPreparedStatement(pstmt, parameters);
			rs=pstmt.executeQuery();
			if(rs.next()){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DbUtils.close(pstmt);
			DbUtils.close(rs);
		}
		return false;
	}
	
	public static void closeConnection(Connection connection)throws Exception{
		if(isValidateConnection(connection))
			connection.close();
	}
	
	public static boolean hasValue(String sql,Object[] parameters)throws Exception{
		Connection con=null;
		try{
			con=Conn.getConnection();
			return hasValue(con,sql,parameters);
		}catch(Exception ex){ex.printStackTrace();System.out.println(sql);}
		finally{closeConnection(con);}
		return false;
	}
	
//	private static PreparedStatement getPreparedStatement(Connection connection,String sql,Object[] parameters) throws Exception{
//		PreparedStatement pstmt=connection.prepareStatement(sql);
//		setPreparedStatement(pstmt, parameters);
//		return pstmt;
//	}
	
	
	public static void setPreparedStatement(PreparedStatement pstmt,Object[] parameters)throws Exception{
		if(parameters==null) return;
		for(int i=0;parameters!=null&&i<parameters.length;i++)
			pstmt.setObject(i+1,parameters[i]);		
	}
	

	private static boolean isValidateConnection(Connection connection)throws Exception{
		return !(connection==null||connection.isClosed());
	}
}
*/