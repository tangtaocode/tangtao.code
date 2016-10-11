package net.risesoft.daos.base;

import java.sql.Connection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
/**
 * DAO层封装接口，包含常用的CURD和分页操作
 * 
 * @author 张坤
 * @Date 2013-1-24 上午11:21:59
 */
public interface ISimpleJdbcDao<T> {
	/**
	 * 提供对表的更改和删除操作
	 * @param sql 要执行的sql语句
	 * @param args 变参
	 * @return 影响的行数
	 */
	public int update(String sql,Object...args)throws DataAccessException;
	/**
	 * 提供获取单一值
	 * @param sql 要执行的sql语句
	 * @param args 变参
	 * @return 影响的行数
	 */
	public T getOneObject(String sql,Class<T> myClass,Object[] obj)throws DataAccessException;
	public int getOneInt(String sql,Object[] obj)throws DataAccessException;
	/**
	 * 批量更新多条记录
	 * @param sql 多条sql组成的数组(不带参数的)
	 * @see 带参数的见: <br>
	 * getJdbcTemplate().batchUpdate(String[] sql,BatchPreparedStatementSetter pss)
	 * @return 影响行数数组
	 */
	public int[] batchUpdate(String[] sql)throws DataAccessException;
	
	/**
	 * 获取行数
	 * @param countSql 计算行数的sql语句
	 * @return
	 */
	public int countRows(String countSql,Object[] params)throws DataAccessException;
	/**
	 * 获取本地的Connection对象
	 * @return
	 */
	public Connection getNativeConn()throws DataAccessException;
	
	/**
	 * 获得断开数据库连接的行集,大结果集会消耗内存,受到maxSize的限制
	 * @param sql 要执行的sql语句带?占位符
	 * @param params 填充占位符的数组,可以为null值
	 * @param types 填充参数类型(java.sql.Types中的常量),可以为null值
	 * 例如:new int[]{Types.VARCHAR,Types.DATE}
	 * @return 影响的行数<br>
	 * <b>注:</b> params和types同时为空,sql为不带?占位符;仅仅types为空时,由spring去猜测类型
	 */
	public List<T> queryForRow(String sql,Object[] params,int[] types,Class<T> myClass)throws DataAccessException;
	public List<T> queryForRow(String sql,Object[] params,Class<T> myClass)throws DataAccessException;
	public List<T> queryForRow(String sql,Class<T> myClass)throws DataAccessException;
	
	/**
	 * @author Abbott
	 * @todo JDBC获取实体
	 * 
	 * **/
	public T getBean(String sql,Object[] params,int[] types,Class<T> myClass)throws DataAccessException;
	public T getBean(String sql,Object[] params,Class<T> myClass)throws DataAccessException;
	public T getBean(String sql,Class<T> myClass)throws DataAccessException;
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
	public KeyHolder updateNamedParamer(String hql,SqlParameterSource sps)throws DataAccessException;
	
	/**
	 * 执行sql语句,如创建表等
	 * @param sql
	 */
	public void executeSql(String sql)throws DataAccessException;
	
	/**
	 * @return 获得spring的JdbcTemplate使用更多功能
	 */
	public JdbcTemplate getSpringJdbcTemplate()throws DataAccessException;
	
	/**
	 * 引入jdk5.0语法的JdbcTemplate的简化版本
	 * @return 获得spring的SimpleJdbcTemplate使用更多功能
	 */
	public  SimpleJdbcTemplate getSimplaJdbcTemplate()throws DataAccessException;
	
}
