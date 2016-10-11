package net.risesoft.daos.base;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.type.NullableType;

import net.risesoft.beans.base.QueryResult;

/**
 * DAO层封装接口，包含常用的CURD和分页操作
 * 
 * @author 张坤
 * @Date 2013-1-24 上午11:21:59
 */
public interface IBaseDao<T>
{

	/**
	 * 根据属性查找对象
	 * 
	 * @param propertyName
	 *            属性（对应Bean）
	 * @param value
	 *            属性
	 * @return 根据属性查找对象
	 */
	public List<T> findByProperty(String propertyName, Object value)throws Exception;

	/**
	 * 根据实体查找对象
	 * 
	 * @param entiey
	 *            实体（T类型）
	 * @return 根据属性查找对象
	 */
	public List<T> findByEntity(Object entiey)throws Exception;

	/**
	 * 获取记录总数
	 * 
	 * @param entityClass
	 *            实体类
	 * @return
	 */
	public int getCount()throws Exception;

	/**
	 * 保存实体
	 * 
	 * @param entity
	 *            实体id
	 */
	public void save(Object entity)throws Exception;

	/**
	 * 更新实体
	 * 
	 * @param entity
	 *            实体id
	 */
	public void update(Object entity)throws Exception;
	/**
	 * 更新实体
	 * 
	 * @param entity
	 *            实体
	 */
	public void saveOrUpdate(Object entity)throws Exception;
	/**
	 * 删除实体
	 * 
	 * @param entityClass
	 *            实体类
	 * @param entityids
	 *            实体id数组
	 */
	public void delete(Serializable... entityids)throws Exception;
	/**
	 * 删除实体
	 * 
	 * @param entityClass
	 *            实体类
	 * @param entityids
	 *            实体id数组
	 */
	public void delete(Object entity)throws Exception;
	/**
	 * 获取实体
	 * 
	 * @param <T>
	 * @param entityClass
	 *            实体类
	 * @param entityId
	 *            实体id
	 * @return
	 */
	public T find(Serializable entityId)throws Exception;
	public int execute(final String sql,final Object[] queryParams)throws Exception;
	/**
	 * 获取分页数据
	 * @param firstindex 开始索引
	 * @param maxresult 每页显示记录数
	 * @param wherejpql where语句
	 * @param queryParams 查询参数
	 * @param orderby 排序序列
	 * @return 分页数据
	 */
	public QueryResult<T> getScrollData(final int firstindex, final int maxresult, final String[] wherejpql, final Object[] queryParams,
			final LinkedHashMap<String, String> orderby)throws Exception;
	
	public QueryResult<T> querySql(final int firstindex,
			final int maxresult,final String sql,
			final Object[] queryParams)throws Exception;
	public QueryResult<T> querySql(final String sql)throws Exception;
	/**
	 * 获取分页数据
	 * @param firstindex 开始索引
	 * @param maxresult 每页显示记录数
	 * @param wherejpql where语句
	 * @param queryParams 查询参数
	 * @return 分页数据
	 */
	public QueryResult<T> getScrollData(final int firstindex, final int maxresult, final String[] wherejpql, final Object[] queryParams)throws Exception;

	/**
	 * 获取分页数据
	 * @param firstindex 开始索引
	 * @param maxresult 每页显示记录数
	 * @param orderby 排序序列
	 * @return 分页数据
	 */
	public QueryResult<T> getScrollData(final int firstindex, final int maxresult, final LinkedHashMap<String, String> orderby)throws Exception;

	/**
	 * 获取分页数据
	 * @param firstindex 开始索引
	 * @param maxresult 每页显示记录数
	 * @return 分页数据
	 */
	public QueryResult<T> getScrollData(final int firstindex, final int maxresult)throws Exception;

	/**
	 * 获取所有对象
	 * 
	 * @return 所有对象
	 */
	public QueryResult<T> getScrollData()throws Exception;
	
	public T findBeanBypro(String propertyName, Object value)throws Exception;
	
	public Object findObject(final String sql,final Object[] queryParams,final NullableType type)throws Exception;
}
