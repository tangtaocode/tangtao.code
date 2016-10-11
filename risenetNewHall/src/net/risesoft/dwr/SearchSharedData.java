package net.risesoft.dwr;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import net.risesoft.beans.share.FormInfo;
import net.risesoft.util.SpringUtil;
import net.risesoft.utils.base.DataSourcePool;
import net.risesoft.utils.base.DatabaseUtil;

import org.apache.commons.lang.StringUtils;
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
@RemoteProxy(creator = SpringCreator.class, name = "SearchSharedData")
public class SearchSharedData {
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
	public  void findProjectInfo(HttpServletRequest request) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sharedGuid = request.getParameter("sharedGuid");
		sharedGuid = sharedGuid == null ? "" : StringUtils.trim(sharedGuid);
		request.setAttribute("sharedGuid", sharedGuid);
	    List<FormInfo> form_list = new ArrayList<FormInfo>();
	    form_list=GetFormInfoList(sharedGuid);
		List<FormInfo> query_list= GetQueryFormInfoList(sharedGuid);
		String view_name = "";
		
		if(form_list!=null && form_list.size()>0){
			StringBuilder sb = new StringBuilder(" select 1");
			for(int rowIndex=0;rowIndex<form_list.size();rowIndex++)
			{
				view_name = form_list.get(rowIndex).getViewname();
				sb.append(" ,"+form_list.get(rowIndex).getDisplayenname());
			}
			sb.append(" from "+ view_name);
			String sql = sb.toString();
			String whereSql = " where 1=1";
			String orderBy = "";//" order by sqrq desc";
			
			List list = new ArrayList();
			try {
				if(query_list!=null && query_list.size()>0){
					for(int i=0;i<query_list.size();i++){
						String paraName = query_list.get(i).getDisplayenname();
						String str = request.getParameter(paraName);
						str = StringUtils.trim(str);
						if(StringUtils.isBlank(str)){
							request.setAttribute(paraName, "");
						}else{
							request.setAttribute(paraName, str);
							whereSql += " and "+ paraName+ "  like  '%" + str + "%'";
							DataSource ds=(DataSource)getBean("dataSource");
							conn =ds.getConnection();
							pst = conn.prepareStatement(sql+whereSql);
							rs = pst.executeQuery();
							list= DatabaseUtil.getMapList(rs);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeConnection(conn,pst,rs);
			}
			request.setAttribute("result", list);
			request.setAttribute("formList", form_list);
			request.setAttribute("queryformList", query_list);
	    }
	}
	
	public void findEmployeeInfo(HttpServletRequest request) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		String sguid = request.getParameter("sharedGuid");
		String gcxh = request.getParameter("gcxh");
		String type = request.getParameter("type");
		String sharedGuid="";
		if(StringUtils.isNotBlank("sguid"))
		{
			sharedGuid=StringUtils.trim(sguid.toString());
			request.setAttribute("sharedGuid",sharedGuid);
		}
		
		if(StringUtils.isNotBlank("gcxh")){
			request.setAttribute("gcxh",gcxh);
		}else{
			request.setAttribute("gcxh", "");
		}
		
		if(StringUtils.isNotBlank("type")){
			request.setAttribute("type",type);
		}else{
			request.setAttribute("type", "");
		}
		
	    List<FormInfo> formLists = new ArrayList<FormInfo>();
	    formLists=GetFormInfoList(sharedGuid);
		List<FormInfo> queryLists= GetQueryFormInfoList(sharedGuid);
		String viewName = "";
		boolean flag = false;
		//判读是否带有查询条件
		if(queryLists!=null && queryLists.size()>0){
			for(int i=0;i<queryLists.size();i++){
				String paraName = queryLists.get(i).getDisplayenname();
				String str = request.getParameter(paraName);
				str = StringUtils.trim(str);
				if(StringUtils.isBlank(str)){
					request.setAttribute(paraName, "");
				}else{
					flag = true;
				}
			}
		}
		List list = null;
		if(flag){
			if(null !=formLists && formLists.size()>0){			
				String sql ="";
				String whereSql = "";
				String orderBy = "";
				StringBuilder sb = new StringBuilder(" select 1");
				for(int i=0;i<formLists.size();i++){
					viewName = formLists.get(i).getViewname();
					sb.append(" ,"+formLists.get(i).getDisplayenname());
				}
				
				sb.append(" from "+ viewName);
				sql = sb.toString();
				whereSql = " where 1=1";

				try{
					if(queryLists!=null && queryLists.size()>0){
						for(int i=0;i<queryLists.size();i++){
							String paraName = queryLists.get(i).getDisplayenname();
							String str = request.getParameter(paraName);
							str = StringUtils.trim(str);
							if(StringUtils.isNotBlank(str)){
								request.setAttribute(paraName, str);
								whereSql += " and "+ paraName+ "  like  '%" +str+ "%'";
							}else{
								request.setAttribute(paraName, "");
							}
						}
					}
					DataSource ds=(DataSource)getBean("dataSource");
					conn =ds.getConnection();
					pst = conn.prepareStatement(sql+whereSql);
					rs = pst.executeQuery();
				    list= DatabaseUtil.getMapList(rs);
				}catch(Exception e){
					e.printStackTrace();
				} finally {
					closeConnection(conn,pst,rs);
				}
			}
		}
		request.setAttribute("result", list);	
		request.setAttribute("formList", formLists);
		request.setAttribute("queryformList", queryLists);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RemoteMethod
	public Map findEmpHallShared(String shardId, String fromDataGuid,String thisDataGuid,String gcxh,String type) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "select guid, fromdata, fromdataname, todata, todataname, "
				+ " sharedname, sharedfiled, sharedtype, tempid, createdate, status from t_sys_sharedconfig where guid=?";
		Map dataMap = new HashMap();
		try {
			conn = DataSourcePool.getConnection("168");
			pst = conn.prepareStatement(sql);
			pst.setString(1, shardId);
			rs = pst.executeQuery();
			List<String> paraList = new ArrayList<String>();
			paraList.add(thisDataGuid);
			if (rs.next()) {
				String[] fileds = rs.getString("sharedfiled").split(",");
				Map map = createEmpSql(conn,rs.getString("sharedfiled"),
						rs.getString("fromdata"), fromDataGuid);
				for (int i = 0; i < fileds.length; i++) {
					String[] colms = fileds[i].split(":");
					String key = colms[1];
					
					Object obj = map.get(colms[0]) == null ? "" : map.get(colms[0]);
					if(map.get(colms[0])!=null&&obj instanceof Date){
						String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(obj);
						if(time.contains("00:00:00")){
							time = time.substring(0,10);
						}
						obj = time;
					}else if(obj==null){
						obj= "";
					}
					dataMap.put(key,obj);
				}
				dataMap.put("isSubmitForm",rs.getString("sharedtype"));
			}
			return dataMap;
		} catch (Exception e) {
			e.printStackTrace();
			return dataMap;
		} finally {
			closeConnection(conn, null, null);
		}
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RemoteMethod
	public Map findEmpShared(String shardId, String fromDataGuid,String thisDataGuid,String gcxh,String type) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection mconn = null;
		String sql = "select guid, fromdata, fromdataname, todata, todataname, "
				+ " sharedname, sharedfiled, sharedtype, tempid, createdate, status from t_sys_sharedconfig where guid=?";
		Map dataMap = new HashMap();
		try {
			conn = DataSourcePool.getConnection("168");
			pst = conn.prepareStatement(sql);
			pst.setString(1, shardId);
			rs = pst.executeQuery();
			List<String> paraList = new ArrayList<String>();
			paraList.add(thisDataGuid);
			if (rs.next()) {
				String[] fileds = rs.getString("sharedfiled").split(",");
				Map map = createEmpSql(conn,rs.getString("sharedfiled"),
						rs.getString("fromdata"), fromDataGuid);
				for (int i = 0; i < fileds.length; i++) {
					String[] colms = fileds[i].split(":");
					String key = colms[1];
					
					Object obj = map.get(colms[0]) == null ? "" : map.get(colms[0]);
					if(map.get(colms[0])!=null&&obj instanceof Date){
						String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(obj);
						if(time.contains("00:00:00")){
							time = time.substring(0,10);
						}
						obj = time;
					}else if(obj==null){
						obj= "";
					}
					dataMap.put(key,obj);
				}
				dataMap.put("isSubmitForm",rs.getString("sharedtype"));
			}
			
			
			mconn = DataSourcePool.getConnection("sqlserver");
			String flag = "0";
			if(type.equals("mar") || type.equals("dwmar") || type.equals("rymar")){
				String mangerStr = "select GCBH,IsLocked from JY_LockedProjectManger where XMJLBH=? GROUP BY GCBH,IsLocked";
				pst = mconn.prepareStatement(mangerStr.toString());
				pst.setString(1, fromDataGuid);
				rs = pst.executeQuery();
				while(rs.next()){
					if(!gcxh.equals(rs.getString("GCBH"))){
						if(rs.getInt("IsLocked") == 1){
							flag = "1";
						}
					}
				}
			}else if(type.equals("zj") || type.equals("dwzj") || type.equals("ryzj")){
				String zjStr = "select GCBH,IsLocked from JY_LockedProjectManger where XMJLBH=? and (ptype='项目总监' or ptype='变更后项目总监') GROUP BY GCBH,IsLocked";
				pst = mconn.prepareStatement(zjStr.toString());
				pst.setString(1, fromDataGuid);
				rs = pst.executeQuery();
				int count = 0;
				while(rs.next()){
					if(!gcxh.equals(rs.getString("GCBH"))){
						if(rs.getInt("IsLocked") == 1){
							count++;
						}
					}
				}
				if(count >=3){
					flag="1";
				}
			}
			dataMap.put("flag", flag);
			return dataMap;
		} catch (Exception e) {
			e.printStackTrace();
			return dataMap;
		} finally {
			closeConnection(conn, null, null);
			closeConnection(mconn, pst, null);
		}
	}
	
	@RemoteMethod
	@SuppressWarnings("rawtypes")
	public  Map findShared(String shardId, String fromDataGuid,
			String thisDataGuid) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "select guid, fromdata, fromdataname, todata, todataname, "
				+ " sharedname, sharedfiled, sharedtype, tempid, createdate, status from t_sys_sharedconfig where guid=?";
		Map dataMap = new HashMap();
		//IConnectentService connService = (IConnectentService) SpringUtil.getBean("connectentServiceImpl");
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
					String key = colms[1];
					Object obj = map.get(colms[0]) == null ? "" : map.get(colms[0]);
					if(map.get(colms[0])!=null && obj instanceof Date){
						String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(obj);
						if(time.contains("00:00:00")){
							time = time.substring(0,10);
						}
						obj = time;
					}else if(obj==null){
						obj= "";
					}
					dataMap.put(key,obj);
				}
				dataMap.put("isSubmitForm",rs.getString("sharedtype"));
			}
			return dataMap;
		} catch (Exception e) {
			e.printStackTrace();
			return dataMap;
		} finally {
			closeConnection(conn,pst,rs);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void findMoreSelects(HttpServletRequest request) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String guids = request.getParameter("idnumber");
		String sharedGuid = request.getParameter("sharedGuid");
		// 标志：0表示无查询条件及表单输入框无值；1表示有查询条件，表单输入框无值；2.既有查询条件又有表单输入值；4表示查询无条件，表单输入有值
		String flag = "0";
		Boolean jude = false;
		if (StringUtils.isNotBlank(sharedGuid)) {
			sharedGuid = StringUtils.trim(sharedGuid);
			request.setAttribute("sharedGuid", sharedGuid);
		}
		
		guids = StringUtils.isNotBlank(guids) ? StringUtils.trim(guids) : "";
		List<FormInfo> form_list = new ArrayList<FormInfo>();
		form_list = GetFormInfoList(sharedGuid);
		List<FormInfo> query_list = GetQueryFormInfoList(sharedGuid);
		String view_name = "";
		// 判断是否有查询条件
		if (form_list != null && form_list.size() > 0) {
			StringBuilder sb = new StringBuilder(" select 1");
			for (int rowIndex = 0; rowIndex < form_list.size(); rowIndex++) {
				view_name = form_list.get(rowIndex).getViewname();
				sb.append(" ," + form_list.get(rowIndex).getDisplayenname());
			}
			sb.append(" from " + view_name);
			String sql = sb.toString();
			String whereSql = " where 1=1";
			List list = new ArrayList();
			try{
				if (query_list != null && query_list.size() > 0) {
					for (int i = 0; i < query_list.size(); i++) {
						String paraName = query_list.get(i).getDisplayenname();
						String str = request.getParameter(paraName);
						str = StringUtils.isNotBlank(str) ? StringUtils.trim(str) : "";
						if(StringUtils.isNotBlank(str)){
							whereSql +=" and " + paraName + "  like  '%"+ str + "%'";
							jude = true;
						}else{
							
						}
						request.setAttribute(paraName, str);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			if (jude) {
				if (StringUtils.isNotBlank(guids)) {
					whereSql += " or  instr('" + guids + "'," + view_name+ ".guid,1,1)>0";
					flag = "2";
				} else {
					flag = "1";
				}
			} else {
				if (StringUtils.isNotBlank(guids)) {
					flag = "4";
					whereSql += " and  instr('" + guids + "'," + view_name+ ".guid,1,1)>0";
				} else {
					flag = "0";
				}
			}
			
			try {
				// 共享不显示
				if (flag != "0") {
					DataSource ds=(DataSource)getBean("dataSource");
					conn =ds.getConnection();
					pst = conn.prepareStatement(sql+whereSql);
					rs = pst.executeQuery();
				    list= DatabaseUtil.getMapList(rs);
					
				    request.setAttribute("result", list);
					/*list = DatabaseUtil.getFullTableData(sql + whereSql);
					System.out.println(sql + whereSql);*/
				} else {
					request.setAttribute("result", "noresult");
				}
				// List list= DatabaseUtil.getFullTableData(sql+whereSql);
				// request.setAttribute("result", list);
				request.setAttribute("formList", form_list);
				request.setAttribute("queryformList", query_list);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeConnection(conn,pst,rs);
			}
		}
	}
	
	@RemoteMethod
	@SuppressWarnings("rawtypes")
	public Map findMoreSelects(String shardId, String fromDataGuid,String thisDataGuid) {
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
				String[] ids = fromDataGuid.split(":");
			    String[] values = new String [fileds.length];
			    for(int m=0;m<values.length;m++)
			    {
			    	values[m]="";
			    }
			    for(int k = 0;k<ids.length;k++)
			    {
				Map map = createSql(rs.getString("sharedfiled"),
						rs.getString("fromdata"), ids[k]);
				for (int i = 0; i < fileds.length; i++) {
					String[] colms = fileds[i].split(":");
					String key = colms[1];
					Object obj = map.get(colms[0]) == null ? "" : map.get(colms[0]);
					if(map.get(colms[0])!=null&&obj instanceof Date){
						String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(obj);
						if(time.contains("00:00:00")){
							time = time.substring(0,10);
						}
						obj = time;
					}else if(obj==null){
						obj= "";
					}
					values[i] = values[i]+obj.toString()+"/";
					dataMap.put(key,values[i]);
				}
			}
				dataMap.put("isSubmitForm",rs.getString("sharedtype"));
			}
			return dataMap;
		} catch (Exception e) {
			e.printStackTrace();
			return dataMap;
		} finally {
			closeConnection(conn, pst, rs);
		}
	}

	//选择取消按钮
	@RemoteMethod
	@SuppressWarnings("rawtypes")
	  public Map cancelSelect(String shardId, String fromDataGuid, String thisDataGuid){
		  Connection conn = null;
		    PreparedStatement pst = null;
		    ResultSet rs = null;
		    String sql = "select guid, fromdata, fromdataname, todata, todataname,  sharedname, sharedfiled, sharedtype, tempid, createdate, status from t_sys_sharedconfig where guid=?";

		    Map dataMap = new HashMap();
		    try {
		    	DataSource ds=(DataSource)getBean("dataSource");
				conn =ds.getConnection();
		      pst = conn.prepareStatement(sql);
		      pst.setString(1, shardId);
		      rs = pst.executeQuery();
		      if (rs.next()) {
		        String[] fileds = rs.getString("sharedfiled").split(",");
		        for (int i = 0; i < fileds.length; i++) {
		          String[] colms = fileds[i].split(":");
		          String key = colms[1];
		          Object obj = "";
		          dataMap.put(key, obj);
		        }
		        dataMap.put("isSubmitForm", rs.getString("sharedtype"));
		      }
		      return dataMap;
		    }
		    catch (Exception e)
		    {
		      Map localMap1;
		      e.printStackTrace();
		      return dataMap;
		    } finally {
		    	closeConnection(conn,pst,rs);
		    }
	  }
	@SuppressWarnings("rawtypes")
	private static Map createEmpSql(Connection conn,String sharedfiled, String fromdata,
			String dataGuid) {
		String sql = "select ";
		String[] fileds = sharedfiled.split(",");
		for (String filed : fileds) {
			String[] colms = filed.split(":");
			sql += colms[0] + ",";
		}
		sql = sql.substring(0, sql.length() - 1) + " from " + fromdata
				+ " where id_number=?";
		try {
			
			return DatabaseUtil.getMap(conn,sql, new String[] { dataGuid });
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private  Map createSql(String sharedfiled, String fromdata,
			String dataGuid) 
	{
		
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
		//IConnectentService connService = (IConnectentService) SpringUtil.getBean("connectentServiceImpl");
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
			//(sql, new String[] { dataGuid });
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			closeConnection(conn,pst,rs);
		}
	}
	
	public  List<FormInfo> GetQueryFormInfoList(String sharedGuid)
	{
		List<FormInfo> list = new ArrayList<FormInfo>();
		String sql = " select * from t_sys_sharedformdisplay where fk='"+sharedGuid+"' and ISQUERY='1' order by sn asc";
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		//(IConnectentService) SpringUtil.getBean("connectentServiceImpl");
		try {
			DataSource ds=(DataSource)getBean("dataSource");
			conn = ds.getConnection();
			//conn = Conn.getConnection();
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
			closeConnection(conn,pst,rs);
		}
		return list;
	}
	
	public  List<FormInfo> GetFormInfoList(String sharedGuid)
	{
		List<FormInfo> list = new ArrayList<FormInfo>();
		String sql = " select * from t_sys_sharedformdisplay where fk='"+sharedGuid+"' order by sn asc";
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		//IConnectentService connService = (IConnectentService) SpringUtil.getBean("connectentServiceImpl");
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
			closeConnection(conn,pst,rs);
		}
		return list;
	}
	
	
	private void closeConnection(Connection con,PreparedStatement pstmt,ResultSet rs){
		try{
			if(rs != null){
				rs.close();
			}
			if(pstmt != null){
				pstmt.close();
			}
			if(con!=null){
				con.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
