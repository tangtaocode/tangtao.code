/**
 * 
 */
package net.risesoft.daos.base.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

import net.risesoft.daos.base.ISimpleJdbcDao;
import net.risesoft.utils.base.DatabaseUtil;

import org.apache.log4j.Logger;
import org.hibernate.jdbc.util.FormatStyle;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 继承spring的SimpleJdbcDaoSupport
 * 提供对数据库jdbc级别的操作
 * 内部使用spring的SimpleJdbcTemplate与JdbcTemplate
 * @author 张坤
 * @since  2009-5-7
 */
@SuppressWarnings("unchecked")
@Repository("simpleJdbcDaoImpl")
public class SimpleJdbcDaoImpl<T> extends SimpleJdbcDaoSupport implements ISimpleJdbcDao<T>{
	private final Logger log = Logger.getLogger(SimpleJdbcDaoImpl.class);
	@Resource(name = "jdbcTemplate")
	public void setSuperJdbcTemplate(JdbcTemplate jdbcTemplate) {
		super.setJdbcTemplate(jdbcTemplate);
	}
	
	/**
	 * 提供对表的更改和删除操作
	 * @param sql 要执行的sql语句
	 * @param args 变参
	 * @return 影响的行数
	 */
	public int update(String sql,Object...args)throws DataAccessException{
		return this.getSimpleJdbcTemplate().update(sql, args);
	}
	
	/**
	 * 批量更新多条记录
	 * @param sql 多条sql组成的数组(不带参数的)
	 * @see 带参数的见: <br>
	 * getJdbcTemplate().batchUpdate(String[] sql,BatchPreparedStatementSetter pss)
	 * @return 影响行数数组
	 */
	public int[] batchUpdate(String[] sql)throws DataAccessException{
		return this.getJdbcTemplate().batchUpdate(sql);
	}
	
	/**
	 * 获取行数
	 * @param countSql 计算行数的sql语句
	 * @return
	 */
	public int countRows(String countSql,Object[] params)throws DataAccessException{
		int count = 0;
		SqlRowSet srs = getJdbcTemplate().queryForRowSet("select count(1) from ("+countSql+")", getParams(params));
		if(srs.next()){
			count = srs.getInt(1);
		}
		return count;
	}
	
	/**
	 * 获取本地的Connection对象
	 * @return
	 */
	public Connection getNativeConn(){
		
		//从当前线程绑定的数据连接获取连接
		Connection conn = DataSourceUtils.getConnection(this.getJdbcTemplate().getDataSource());
		try {
			conn = this.getJdbcTemplate().getNativeJdbcExtractor().getNativeConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return conn;
	}
	
	/**
	 * 获得断开数据库连接的行集,大结果集会消耗内存,受到maxSize的限制
	 * @param sql 要执行的sql语句带?占位符
	 * @param params 填充占位符的数组,可以为null值
	 * @param types 填充参数类型(java.sql.Types中的常量),可以为null值
	 * 例如:new int[]{Types.VARCHAR,Types.DATE}
	 * @return 影响的行数<br>
	 * <b>注:</b> params和types同时为空,sql为不带?占位符;仅仅types为空时,由spring去猜测类型
	 */
	public List<T> queryForRow(String sql,Object[] params,int[] types,Class<T> myClass)throws DataAccessException{
		if(log.isDebugEnabled())log.debug(("JDBC："+FormatStyle.BASIC.getFormatter().format(getRowSql(sql,params))));
		List<T>  list = DatabaseUtil.getBeanList(((ResultSetWrappingSqlRowSet)getJdbcTemplate().queryForRowSet(getRowSql(sql,params), getParams(params), types)).getResultSet(), myClass);
		return list;
	}
	public List<T> queryForRow(String sql,Object[] params,Class<T> myClass)throws DataAccessException{
		if(log.isDebugEnabled())log.debug(("JDBC："+FormatStyle.BASIC.getFormatter().format(getRowSql(sql,params))));
		return DatabaseUtil.getBeanList(((ResultSetWrappingSqlRowSet)getJdbcTemplate().queryForRowSet(getRowSql(sql,params), getParams(params))).getResultSet(), myClass);
	}
	public List<T> queryForRow(String sql,Class<T> myClass)throws DataAccessException{
		if(log.isDebugEnabled())log.debug(("JDBC："+FormatStyle.BASIC.getFormatter().format(sql)));
		return DatabaseUtil.getBeanList(((ResultSetWrappingSqlRowSet)getJdbcTemplate().queryForRowSet(sql)).getResultSet(), myClass);
	}
	
	public T getBean(String sql, Object[] params, int[] types,Class<T> myClass) throws DataAccessException{
		List<T> list = queryForRow(sql, params,types,myClass);
		if(list.size()==0)return null;
		else return list.get(0);
	}
	public T getBean(String sql, Object[] params,Class<T> myClass) throws DataAccessException{
		List<T> list = queryForRow(sql, params,myClass);
		if(list.size()==0)return null;
		else return list.get(0);
	}
	public T getBean(String sql,Class<T> myClass)throws DataAccessException {
		List<T> list = queryForRow(sql,myClass);
		if(list.size()==0)return null;
		else return list.get(0);
	}
	/**
	 * 提供对表的更改和删除操作
	 * @param hql 使用了命名参数的sql语句
	 * @param sps 例如:<br>
	 * new BeanPropertySqlParamterSource(javaBean其属性要和命名参数对应);<br>
	 * new MapSqlParameterSource().add("命名参数",参数对应的值).add()...可以链式调用
	 * @return KeyHolder主键持有者对象;如果是新增数据,KeyHolder持有新增的主键值<br>
	 * 有3个方法可调用:<br>getKey()一个数字主键<br>
	 * getKeys()复合主键Map结构<br>
	 * getKeyList()多个主键由多个Map组成的List
	 */
	public KeyHolder updateNamedParamer(String hql,SqlParameterSource sps)throws DataAccessException{
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.getSimpleJdbcTemplate().getNamedParameterJdbcOperations().update(hql, sps, keyHolder);
		return keyHolder;
	}
	
	/**
	 * 执行sql语句,如创建表等
	 * @param sql
	 */
	public void executeSql(String sql)throws DataAccessException{
		this.getJdbcTemplate().execute(sql);
	}
	
	/**
	 * @return 获得spring的JdbcTemplate使用更多功能
	 */
	public JdbcTemplate getSpringJdbcTemplate(){
		return this.getJdbcTemplate();
	}
	
	/**
	 * 引入jdk5.0语法的JdbcTemplate的简化版本
	 * @return 获得spring的SimpleJdbcTemplate使用更多功能
	 */
	public  SimpleJdbcTemplate getSimplaJdbcTemplate(){
		return this.getSimpleJdbcTemplate();
	}
	protected String getRowSql(String sql,Object[] params) throws DataAccessException{
		if(params==null||params.length==0||("-1".equals(params[params.length-1])&&"-1".equals(params[params.length-2])))
			return sql;
		else
			return "select * from (select tab1.*, rownum as rownum1 from ("+sql+")tab1) where  rownum1>=? and rownum1<=?";
	}
	protected Object[] getParams(Object[] params)throws DataAccessException {
		Object[] newParams = null;
		if(params!=null&&params.length>0&&"-1".equals(params[params.length-1])&&"-1".equals(params[params.length-2])){
			newParams = new Object[params.length-2];
			for(int i=0;i<newParams.length;i++){
				newParams[i]=params[i];
			}
		}else{
			newParams = params;
		}
		return newParams;
	}

	public T getOneObject(String sql,Class<T> myClass,Object[] obj) throws DataAccessException{
		return this.getSimplaJdbcTemplate().queryForObject(sql, myClass, obj);
	}

	public int getOneInt(String sql,Object[] obj)throws DataAccessException {
		return this.getSimplaJdbcTemplate().queryForInt(sql, obj);
	}
	
	public Connection getConn(ServletContext servletContext) throws Exception{
		if(servletContext==null){
			return null;
		}
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);  
		DataSource source = (DataSource) ctx.getBean("hibernateDataSource"); 
		return source.getConnection();
	}
}
