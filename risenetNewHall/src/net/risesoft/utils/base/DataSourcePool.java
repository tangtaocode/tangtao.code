package net.risesoft.utils.base;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DataSourcePool {
	private static HashMap<String, ComboPooledDataSource> datasourceMap = new HashMap<String, ComboPooledDataSource>();
	private final static ThreadLocal<Connection> conns = new ThreadLocal<Connection>();
	private static Logger logger = Logger.getLogger(DataSourcePool.class);
	
	static{
		InitDataSource(null);
	}
	
	private final static void InitDataSource(Properties dbProperties){
		try{
			if(null == dbProperties){
				dbProperties = new Properties();
				dbProperties.load(DataSourcePool.class.getClassLoader().getResourceAsStream("qjdbc.properties"));
				
				//获取数据源名称
				//获取数据源名称
                Set<String> keys = new HashSet<String>();
                for (Object key : dbProperties.keySet()){
                        String skey = (String)key;
                        Integer point = skey.indexOf(".");
                        keys.add(skey.substring(0, point));
                }
                
                for(String key : keys){
                	ComboPooledDataSource ds = new ComboPooledDataSource();
    				ds.setDriverClass(StringUtils.trim(dbProperties.getProperty(key+"."+"driver")));
                    ds.setJdbcUrl(StringUtils.trim(dbProperties.getProperty(key+"."+"url")));
                    ds.setUser(StringUtils.trim(dbProperties.getProperty(key+"."+"user")));
                    ds.setPassword(StringUtils.trim(dbProperties.getProperty(key+"."+"password")));
                    ds.setMaxPoolSize(Integer.parseInt(StringUtils.trim(dbProperties.getProperty(key+"."+"maxPoolSize"))));
                    ds.setMinPoolSize(Integer.parseInt(StringUtils.trim(dbProperties.getProperty(key+"."+"minPoolSize"))));
                    ds.setMaxIdleTime(Integer.parseInt(StringUtils.trim(dbProperties.getProperty(key+"."+"maxIdleTime"))));
                    ds.setInitialPoolSize(Integer.parseInt(StringUtils.trim(dbProperties.getProperty(key+"."+"initialSize"))));
                    ds.setMaxStatements(Integer.parseInt(StringUtils.trim(dbProperties.getProperty(key+"."+"maxStatements"))));
                    ds.setTestConnectionOnCheckin(Boolean.parseBoolean(StringUtils.trim(dbProperties.getProperty(key + "." +"testConnectionOnCheckin"))));
                    datasourceMap.put(key, ds);
                }
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 
	 * 获取链接
	 */
    public final static Connection getConnection(String dataSourceName) throws SQLException{
    	Connection conn = null;
        ComboPooledDataSource dataSource = datasourceMap.get(dataSourceName);
        conn = dataSource.getConnection();
        conns.set(conn);
        return conn;
    }
    
    /**
     * 关闭连接
     * */
    public final static void closeConnection(){
            Connection conn = conns.get();
            try{
                if(conn != null && !conn.isClosed()){
                   conn.setAutoCommit(true);
                   conn.close();
                }
            }
            catch (SQLException ex){
                    logger.error("Unable to close connection! " + ex.toString());
            }
            finally {
                    conns.remove();
            }
    }
    
    /**
     * 开启事务非自动提交
     * */
    public final static void startTransaction(String dataSourceName){
            try{
              Connection conn = conns.get();
                if (conn == null){
                        ComboPooledDataSource dataSource = datasourceMap.get(dataSourceName);
                        conn = dataSource.getConnection();
                        conns.set(conn);
                }
             conn.setAutoCommit(false);
            }
            catch (Exception ex){
                    logger.error("Can't get the connection! " + ex.toString());
                    throw new RuntimeException(ex);
            }
    }
    
    /**
     * 提交事务
     * */
    public final static void commitTransaction(){
        try{
                Connection conn = conns.get();
                if (conn != null){
                  conn.commit();
                }
        }
        catch (Exception ex){
                logger.error("Can't commit the transaction ! " + ex.toString());
                throw new RuntimeException(ex);
        }
    }
}
