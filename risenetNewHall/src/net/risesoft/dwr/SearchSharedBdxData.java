package net.risesoft.dwr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import net.risesoft.beans.share.FormInfo;
import net.risesoft.utils.base.DatabaseUtil;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.spring.SpringCreator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ClassName: SearchSharedData.java
 * @Description: TODO
 * 
 * @author zhangkun
 * @date May 20, 2014 3:59:31 PM
 * @version
 * @since JDK 1.6
 */

/**
 * @ClassName: SearchSharedData.java
 * @Description: TODO
 * 
 * @author zhangkun
 * @date May 20, 2014 3:59:31 PM
 * @version
 * @since JDK 1.6
 */
@RemoteProxy(creator = SpringCreator.class, name = "SearchSharedBdxData")
public class SearchSharedBdxData {
	
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
	
	@SuppressWarnings("unchecked")
	public static void findProjectInfo(HttpServletRequest request) {
		
		
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		
		Object sguid = request.getParameter("sharedGuid");
		
		Object tempnum = request.getParameter("tempnum");
		
		Object rowNum = request.getParameter("rowNum");
		
		String sharedGuid="";
		if(sguid!=null && sguid!="")
		{
			sharedGuid=sguid.toString();
			request.setAttribute("sharedGuid", sharedGuid);
		}
		if(tempnum!=null && tempnum.toString()!="")
		{
			 System.out.println("tempnum----------------------------��"+tempnum.toString());
			 request.setAttribute("tempnum", tempnum.toString());
		}
		
		if(rowNum!=null && rowNum.toString()!="")
		{
			 System.out.println("rowNum----------------------------��"+rowNum.toString());
			 request.setAttribute("rowNum", rowNum.toString());
		}
	    System.out.println("�������õ�ID-------��"+sharedGuid);
	    List<FormInfo> form_list = new ArrayList<FormInfo>();
	    form_list=GetFormInfoList(sharedGuid);
		List<FormInfo> query_list= GetQueryFormInfoList(sharedGuid);
	    String view_name = "";
	    if(form_list!=null && form_list.size()>0)
	    {
			StringBuilder sb = new StringBuilder(" select 1");
			for(int rowIndex=0;rowIndex<form_list.size();rowIndex++)
			{
				view_name = form_list.get(rowIndex).getViewname();
				sb.append(" ,"+form_list.get(rowIndex).getDisplayenname());
			}
			sb.append(" from "+ view_name);
			String sql = sb.toString();
			String whereSql = " where 1=1";
			String orderBy = "";
			if(query_list!=null && query_list.size()>0)
			{
				for(int i=0;i<query_list.size();i++)
				{
					String paraName = query_list.get(i).getDisplayenname();
					Object obj = request.getParameter(paraName);
					if(obj==null || obj.toString().trim()=="")
						request.setAttribute(paraName, "");
					else
					{
						request.setAttribute(paraName, obj.toString().trim());
						whereSql += " and "+ paraName+ "  like  '%" + obj.toString().trim() + "%'";
					}
				}
			}
			try {

				DataSource ds=(DataSource)getBean("dataSource");
				//List list= DatabaseUtil.getFullTableData(sql+whereSql);
				conn =ds.getConnection();
				pst = conn.prepareStatement(sql+whereSql);
				rs = pst.executeQuery();
				List list= DatabaseUtil.getMapList(rs);
				request.setAttribute("result", list);
				request.setAttribute("formList", form_list);
				request.setAttribute("queryformList", query_list);
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try
				{
					conn.close();
					pst.close();
					rs.close();
				}
				catch(Exception ex)
				{}
				
			}
	    }
	}
	
	
	public static List<FormInfo> GetQueryFormInfoList(String sharedGuid)
	{
		List<FormInfo> list = new ArrayList<FormInfo>();
		String sql = " select * from t_sys_sharedformdisplay where fk='"+sharedGuid+"' and ISQUERY='1' order by sn asc";
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			DataSource ds=(DataSource)getBean("dataSource");
			conn = ds.getConnection();
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				FormInfo f = new FormInfo();
				f.setGuid(rs.getString("guid"));
				f.setDisplaychname(rs.getString("displaychname"));
				f.setDisplayenname(rs.getString("displayenname"));
				f.setFk(rs.getString("fk"));
				f.setIsdisplay(rs.getString("isdisplay"));
				f.setViewname(rs.getString("viewname"));
				f.setSn(rs.getInt("sn"));
				list.add(f);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try
			{
				conn.close();
				pst.close();
				rs.close();
			}
			catch(Exception ex)
			{}
		}
		return list;
	}
	
	
	public static List<FormInfo> GetFormInfoList(String sharedGuid)
	{
		List<FormInfo> list = new ArrayList<FormInfo>();
		String sql = " select * from t_sys_sharedformdisplay where fk='"+sharedGuid+"'  order by sn asc";
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			DataSource ds=(DataSource)getBean("dataSource");
			conn = ds.getConnection();
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				FormInfo f = new FormInfo();
				f.setGuid(rs.getString("guid"));
				f.setDisplaychname(rs.getString("displaychname"));
				f.setDisplayenname(rs.getString("displayenname"));
				f.setFk(rs.getString("fk"));
				f.setIsdisplay(rs.getString("isdisplay"));
				f.setViewname(rs.getString("viewname"));
				f.setSn(rs.getInt("sn"));
				list.add(f);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try
			{
				conn.close();
				pst.close();
				rs.close();
			}
			catch(Exception ex)
			{}
		}
		return list;
	}
	
	
	

	@RemoteMethod
	public static Map findShared(String shardId, String fromDataGuid,
			String thisDataGuid) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		String sql = "select guid, fromdata, fromdataname, todata, todataname, "
				+ " sharedname, sharedfiled, sharedtype, tempid, createdate, status from t_sys_sharedconfig where guid=?";
		Map dataMap = new HashMap();
		try {
			DataSource ds=(DataSource)getBean("dataSource");
			conn = ds.getConnection();
			pst = conn.prepareStatement(sql);
			pst.setString(1, shardId);
			rs = pst.executeQuery();
			List<String> paraList = new ArrayList<String>();
			paraList.add(thisDataGuid);
			if (rs.next()) {
				String[] fileds = rs.getString("sharedfiled").split(",");
				Map map = createSql(rs.getString("sharedfiled"),
						rs.getString("fromdata"), fromDataGuid);
				for (int i = 0; i < fileds.length; i++) {
					String[] colms = fileds[i].split(":");
					dataMap.put(colms[1],
							map.get(colms[0]) == null ? "" : map.get(colms[0]));
				}
				dataMap.put("isSubmitForm",rs.getString("sharedtype"));
				System.out.println("-------------------------------------------");
			}
			return dataMap;
		} catch (Exception e) {
			e.printStackTrace();
			return dataMap;
		} finally {
			try
			{
				conn.close();
				pst.close();
				rs.close();
			}
			catch(Exception ex)
			{}
		}
	}

	@RemoteMethod
	private static Map createSql(String sharedfiled, String fromdata,
			String dataGuid) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "select ";
		String[] fileds = sharedfiled.split(",");
		for (String filed : fileds) {
			String[] colms = filed.split(":");
			sql += colms[0] + ",";
		}
		sql = sql.substring(0, sql.length() - 1) + " from " + fromdata
				+ " where guid=?";
		try {
			DataSource ds=(DataSource)getBean("dataSource");
			conn = ds.getConnection();
			pst = conn.prepareStatement(sql);
			pst.setString(1, dataGuid);
			rs = pst.executeQuery();
			if(rs.next())
			{
				return DatabaseUtil.getTreeMap(rs);
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			try {
				conn.close();
				pst.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
