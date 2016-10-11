package net.risesoft.daos.base.impl;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import net.risesoft.beans.base.QueryResult;
import net.risesoft.daos.base.IBaseDao;
import net.risesoft.utils.base.GenericsUtils;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.NullableType;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * @Todo DAO层封装使用了泛型，包含常用的CURD和分页操作
 * 
 * @author 张坤
 * @Date 2013-1-24 上午11:08:28
 */
@SuppressWarnings("unchecked")
@Repository("baseDao")
public abstract class BaseDaoImpl<T> extends HibernateDaoSupport implements
		IBaseDao<T> {
	protected Class<T> entityClass = GenericsUtils.getSuperClassGenricType(this
			.getClass());
	protected String entityClassName = getEntityName(this.entityClass);
	protected String keyFieldName = getKeyFieldName(this.entityClass);
	private int count = 0;
	private Object obj= null;
	// 为父类HibernateDaoSupport注入sessionFactory的值
	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	/**
	 * @Todo 根据实体查找对象
	 * @param entiey - 实体（T类型） 
	 * @return 结果集
	 **/
	
	public List<T> findByEntity(Object entiey) {
		return super.getHibernateTemplate().findByExample(entiey);
	}

	/**
	 * @Todo 根据属性查找对象
	 * @param propertyName - 属性（对应Bean）value - 属性 
	 * @return 
	 **/
	public List<T> findByProperty(String propertyName, Object value) {
		String queryString = "from " + entityClassName + " o where o."
				+ propertyName + "= ?";
		return super.getHibernateTemplate().find(queryString, value);
	}
	public T findBeanBypro(String propertyName, Object value){
		List<T> list = findByProperty(propertyName, value);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	/**
	 * @Todo 删除实体 
	 **/
	public void delete(Serializable... entityids) {
		for (Object id : entityids) {
			super.getHibernateTemplate().delete(find((Serializable) id));
		}
	}
	/**
	 * @Todo 删除实体 
	 **/
	public void delete(Object entity) {
		super.getHibernateTemplate().delete(entity);
	}
	/**
	 * 更新保存实体
	 * 
	 * @param entity
	 *            实体
	 */
	public void saveOrUpdate(Object entity){
		super.getHibernateTemplate().saveOrUpdate(entity);
	}
	/**
	 *@Todo 获取实体 
	 **/
	public T find(Serializable entityId) {
		if (null != entityId)
			return (T) super.getHibernateTemplate().get(entityClass, entityId);
		return null;
	}

	/**
	 *@Todo 获取记录总数 
	 **/
	public int getCount() {
		String hql = "select count( " + keyFieldName + ") from "
				+ entityClassName;
		int count = Integer.parseInt(super.getHibernateTemplate().find(hql)
				.get(0).toString());
		return count;
	}
/**
 * @Todo 保存实体 
 * **/
	public void save(Object entity) {
		super.getHibernateTemplate().save(entity);
	}

	/**
	 * @Todo 更新实体 
	 **/
	public void update(Object entity) {
		super.getHibernateTemplate().update(entity);
	}
	public int execute(final String sql,final Object[] queryParams){
		super.getHibernateTemplate().execute(new HibernateCallback<T>(){
			@Override
			public T doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query = session.createSQLQuery(sql);
				setQueryParams(query, queryParams);
				count = query.executeUpdate();
				return null;
			}
		});
		return count;
	}
	/**
	 * @Todo  获取分页数据 
	 * @param entityClass - 实体类firstindex - 开始索引maxresult - 需要获取的记录数 
	 **/
	public QueryResult<T> getScrollData(final int firstindex,
			final int maxresult, final String[] wherejpql,
			final Object[] queryParams,
			final LinkedHashMap<String, String> orderby) {
		final QueryResult<T> queryResult = new QueryResult<T>();

		super.getHibernateTemplate().execute(new HibernateCallback<T>() {
			public T doInHibernate(Session session) throws HibernateException,
					SQLException {
				String hql = "from "
						+ entityClassName
						+ " o "
						+ getWhereSql(wherejpql)
						+ buildOrderby(orderby);
				Query query = session.createQuery(hql);
				setQueryParams(query, queryParams);// where

				if (firstindex != -1 && maxresult != -1){
					query.setFirstResult(firstindex);
					query.setMaxResults(maxresult);// last
				}
				queryResult.setResultList(query.list());
				query=session.createQuery("select count("+getKeyFieldName(entityClass)+") from "+entityClassName+" o " + getWhereSql(wherejpql));
				setQueryParams(query, queryParams);// where
				queryResult.setTotalRecord(((Long)query.list().get(0)).intValue());
				return null;
			}

		});

		return queryResult;

	}
	public QueryResult<T> querySql(final int firstindex,
			final int maxresult,final String sql,
			final Object[] queryParams){
		final QueryResult<T> queryResult = new QueryResult<T>();
		super.getHibernateTemplate().execute(new HibernateCallback<T>(){
			public T doInHibernate(Session session) throws HibernateException,SQLException{
				Query  query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				setQueryParams(query, queryParams);
				if(firstindex!=-1&&maxresult!=-1){
					query.setFirstResult(firstindex);
					query.setMaxResults(maxresult);
				}
				final List<T> beanList = new ArrayList<T>();
				try {
					List<Map> list = query.list();
					for(Map map :list){
						//T t = 
						beanList.add((entityClass.cast(populate(entityClass.newInstance(), map))));
					}
				}catch (InstantiationException e) {
					if(logger.isDebugEnabled())logger.error("试图通过newInstance()方法创建类："+entityClass+"的实例时发生错误");
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					if(logger.isDebugEnabled())logger.error("组装对象："+entityClass+"出错");
					e.printStackTrace();
				} catch (Exception e) {
					if(logger.isDebugEnabled())logger.error("程序错误："+e.getMessage());
					e.printStackTrace();
				}
				queryResult.setResultList(beanList);
				SQLQuery sqlQuery = session.createSQLQuery("select count(1) as valCount from ("+sql+")");
				setQueryParams(sqlQuery, queryParams);
				queryResult.setTotalRecord((Integer)sqlQuery.addScalar("valCount", Hibernate.INTEGER).uniqueResult());
				return null;
			}
		});
		return queryResult;
	}
	public Object findObject( final String sql, final Object[] queryParams,final NullableType type){
		super.getHibernateTemplate().execute(new HibernateCallback<T>(){
			public T doInHibernate(Session session) throws HibernateException,SQLException{
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				setQueryParams(sqlQuery, queryParams);
				obj = sqlQuery.addScalar("valCount", type).uniqueResult();
				return null;
			}
		});
		return obj;
	}
	private Object populate(Object bean, Map<String,Object> properties) throws Exception {
		PropertyDescriptor propertyDescriptors[] = PropertyUtils
				.getPropertyDescriptors(bean);
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String name = descriptor.getName();
			Object value = properties.get(name.toUpperCase());
			if (value != null)
				PropertyUtils.setSimpleProperty(bean, name, value);
		}
		return bean;
	}
	
	public QueryResult<T> querySql(final String sql){
		return querySql(-1,-1,sql,null);
	}
	/**
	 * @Todo  获取分页数据 
	 * 
	 **/
	public QueryResult<T> getScrollData(int firstindex, int maxresult,
			String[] wherejpql, Object[] queryParams) {
		return getScrollData(firstindex, maxresult, wherejpql, queryParams,
				null);
	}

	/**
	 * @Todo  获取分页数据 
	 * 
	 **/
	public QueryResult<T> getScrollData(final int firstindex,
			final int maxresult, final LinkedHashMap<String, String> orderby) {
		return getScrollData(firstindex, maxresult, null, null, orderby);

	}

	/**
	 * @Todo  获取分页数据 
	 * 
	 **/
	public QueryResult<T> getScrollData(final int firstindex,
			final int maxresult) {
		return getScrollData(firstindex, maxresult, null, null, null);
	}

	/**
	 * @Todo  获取分页数据 
	 * 
	 **/
	public QueryResult<T> getScrollData() {
		return getScrollData(-1, -1, null, null, null);
	}
	
	
	/**
	 * 获取实体的名称
	 * 
	 * @param <E>
	 * @param clazz
	 *            实体类
	 * @return
	 */
	protected static <E> String getEntityName(Class<E> clazz) {
		String entityname = clazz.getSimpleName();
		Entity entity = clazz.getAnnotation(Entity.class);
		if (entity.name() != null && !"".equals(entity.name())) {
			entityname = entity.name();
		}
		return entityname;
	}

	/**
	 * 获取实体的主键
	 * 
	 * @param <E>
	 * @param clazz
	 *            实体类
	 * @return 主键名
	 */
	protected static <E> String getKeyFieldName(Class<E> clazz) {
		try {
			PropertyDescriptor[] propertyDescriptors = Introspector
					.getBeanInfo(clazz).getPropertyDescriptors();
			for (PropertyDescriptor propertydesc : propertyDescriptors) {
				Method method = propertydesc.getReadMethod();
				if (null != method
						&& null != method
								.getAnnotation(javax.persistence.Id.class)) {
					return propertydesc.getName();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "id";
	}

	/**
	 * 设置HQL里边的属性值
	 * 
	 * @param query
	 * @param queryParams
	 */
	protected static void setQueryParams(Query query, Object[] queryParams) {
		if (queryParams != null && queryParams.length > 0) {
			for (int i = 0; i < queryParams.length; i++) {
				query.setParameter(i, queryParams[i]);// 从0开始
			}
		}
	}

	/**
	 * 组装order by语句
	 * 
	 * @param orderby
	 * @return
	 */
	protected static String buildOrderby(LinkedHashMap<String, String> orderby) {
		StringBuffer orderbyql = new StringBuffer("");
		if (orderby != null && orderby.size() > 0) {
			orderbyql.append(" order by ");
			for (String key : orderby.keySet()) {
				orderbyql.append("o.").append(key).append(" ")
						.append(orderby.get(key)).append(",");
			}
			orderbyql.deleteCharAt(orderbyql.length() - 1);
		}
		return orderbyql.toString();
	}
	/**
	 * 获取统计属性,该方法是为了解决hibernate解析联合主键select count(o) from Xxx o语句BUG而增加,hibernate对此jpql解析后的sql为select count(field1,field2,...),显示使用count()统计多个字段是错误的
	 * @param <E>
	 * @param clazz
	 * @return
	 */
	protected static <E> String getCountField(Class<E> clazz) {
		String out = "o";
		try {
			PropertyDescriptor[] propertyDescriptors = Introspector
					.getBeanInfo(clazz).getPropertyDescriptors();
			for (PropertyDescriptor propertydesc : propertyDescriptors) {
				Method method = propertydesc.getReadMethod();
				if (method != null
						&& method.isAnnotationPresent(EmbeddedId.class)) {
					PropertyDescriptor[] ps = Introspector.getBeanInfo(
							propertydesc.getPropertyType())
							.getPropertyDescriptors();
					out = "o."
							+ propertydesc.getName()
							+ "."
							+ (!ps[1].getName().equals("class") ? ps[1]
									.getName() : ps[0].getName());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out;
	}
	
	/******
	 * @todo getWhereSql 从数组获取条件sql语句
	 * ******/
	protected static String getWhereSql(String[] column){
		String sql = " where ";
		if(column==null||column.length==0){
			return "";
		}else{
			for(int i=0;i<column.length;i++){
				if(i==0){
					sql+=column[i];
				}else{
					sql+=" and "+column[i];
				}
			}
			return sql;
		}
	}
	/******
	 * @todo getWhereSql 从数组获取列语句
	 * ******/
	protected static String getColumnSql(String[] column){
		String sql = "";
		if(column==null||column.length==0){
			return "";
		}else{
			for(int i=0;i<column.length;i++){
				if(i==0){
					sql+=column[i];
				}else{
					sql+=" , "+column[i];
				}
			}
			return sql;
		}
	}
}
