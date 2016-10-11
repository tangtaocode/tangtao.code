/**
 * @Project Name:risenetNewHall
 * @File Name: SearchSharedOnlyData.java
 * @Package Name: net.risesoft.dwr
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2015,RiseSoft  All Rights Reserved.
 * @date Jul 6, 2015 4:54:28 PM
 */
package net.risesoft.dwr;

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
import net.risesoft.utils.base.DataSourcePool;
import net.risesoft.utils.base.DatabaseUtil;

import org.apache.commons.lang.StringUtils;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.spring.SpringCreator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ClassName: SearchSharedOnlyData.java
 * @Description: TODO
 *
 * @author tangtao
 * @date Jul 6, 2015 4:54:28 PM
 * @version 
 * @since JDK 1.6
 */
@RemoteProxy(creator = SpringCreator.class, name = "SearchSharedOnlyData")
public class SearchSharedOnlyData {
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
	//页面提交根据条件查询人员信息
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RemoteMethod
	  public static void findEmployeeInfo(HttpServletRequest request)
	  {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
	    String sguid = request.getParameter("sharedGuid");
	    String gcxh = request.getParameter("gcxh");
	    String type = request.getParameter("type");
	    String sharedGuid = "";
	    if (StringUtils.isNotBlank("sguid"))
	    {
	      sharedGuid = StringUtils.trim(sguid.toString());
	      request.setAttribute("sharedGuid", sharedGuid);
	    }

	    if (StringUtils.isNotBlank("gcxh"))
	      request.setAttribute("gcxh", gcxh);
	    else {
	      request.setAttribute("gcxh", "");
	    }

	    if (StringUtils.isNotBlank("type"))
	      request.setAttribute("type", type);
	    else {
	      request.setAttribute("type", "");
	    }

	    List formLists = new ArrayList();
	    formLists = GetFormInfoList(sharedGuid);
	    List queryLists = GetQueryFormInfoList(sharedGuid);
	    String viewName = "";
	    boolean flag = false;

	    if ((queryLists != null) && (queryLists.size() > 0)) {
	      for (int i = 0; i < queryLists.size(); i++) {
	        String paraName = ((FormInfo)queryLists.get(i)).getDisplayenname();
	        String str = request.getParameter(paraName);
	        str = StringUtils.trim(str);
	        if ((str == null) || (str.equals("")))
	          request.setAttribute(paraName, "");
	        else {
	          flag = true;
	        }
	      }
	    }
	    List list = null;
	    if ((flag) && (formLists != null) && (formLists.size() > 0)) {
	      String sql = "";
	      String whereSql = "";
	      String orderBy = "";
	      StringBuilder sb = new StringBuilder(" select 1");
	      for (int i = 0; i < formLists.size(); i++) {
	        viewName = ((FormInfo)formLists.get(i)).getViewname();
	        sb.append(" ," + ((FormInfo)formLists.get(i)).getDisplayenname());
	      }

	      sb.append(" from " + viewName);
	      sql = sb.toString();
	      whereSql = " where 1=1";

	      if ((queryLists != null) && (queryLists.size() > 0)) {
	        for (int i = 0; i < queryLists.size(); i++) {
	          String paraName = ((FormInfo)queryLists.get(i)).getDisplayenname();
	          String str = request.getParameter(paraName);
	          if (StringUtils.isNotBlank(str)) {
	            str = StringUtils.trim(str);
	            request.setAttribute(paraName, str);
	            whereSql = whereSql + " and " + paraName + "  like  '%" + str + "%'";
	          } else {
	            request.setAttribute(paraName, "");
	          }
	        }
	      }
	       DataSource ds=(DataSource)getBean("dataSource");
			try {
				conn =ds.getConnection();
				pst = conn.prepareStatement(sql+whereSql);
				rs = pst.executeQuery();
				list= DatabaseUtil.getMapList(rs);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				 closeConnection(conn, pst, rs);
			}
	    }

	    request.setAttribute("result", list);
	    request.setAttribute("formList", formLists);
	    request.setAttribute("queryformList", queryLists);
	  }
	  //页面加载初始化页面信息放入request
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RemoteMethod
	  public static void findHtEmpInfo(HttpServletRequest request)
	  {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
	    List list = null;

	    String type = request.getParameter("type");
	    String number = request.getParameter("guid");
	    String gcxh = request.getParameter("gcxh");
	    String insGuid = request.getParameter("insGuid");
	    String sharedGuid = request.getParameter("sharedGuid");
	    String sql = "SELECT GCBH,GCMC,PTYPE,XMJLMC FROM JY_LockedProjectManger WHERE fn_tag<>1";
	    if (StringUtils.isNotBlank("number")) {
	      sql = sql + " AND XMJLBH ='" + number + "'";
	    }

	    try {
			conn =DataSourcePool.getConnection("sqlserver");
			pst = conn.prepareStatement(sql); 
			rs = pst.executeQuery();
			list= DatabaseUtil.getMapList(rs);
			request.setAttribute("list", list);
			request.setAttribute("guid", number);
			request.setAttribute("type", type);
			request.setAttribute("gcxh", gcxh);
			request.setAttribute("insGuid", insGuid);
			request.setAttribute("sharedGuid", sharedGuid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeConnection(conn, pst, rs);
		}
	  }

	  //根据配置查询表获取查询字段列表信息
	  public static List<FormInfo> GetQueryFormInfoList(String sharedGuid)
	  {
	    List list = new ArrayList();
	    String sql = " select * from t_sys_sharedformdisplay where fk='" + sharedGuid + "' and ISQUERY='1' order by sn asc";
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
	        f.setSn(Integer.valueOf(rs.getInt("sn")));
	        list.add(f);
	      }
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    } finally {
	      closeConnection(conn, pst, rs);
	    }
	    return list;
	  }

	  //根据配置查询表获取显示所有列标题信息
	  public static List<FormInfo> GetFormInfoList(String sharedGuid)
	  {
	    List list = new ArrayList();
	    String sql = " select * from t_sys_sharedformdisplay where fk='" + sharedGuid + "' order by sn asc";
	    Connection conn = null;
	    PreparedStatement pst = null;
	    ResultSet rs = null;
	    try {
	      conn = DataSourcePool.getConnection("168");
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
	        f.setSn(Integer.valueOf(rs.getInt("sn")));
	        list.add(f);
	      }
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    } finally {
	      closeConnection(conn, pst, rs);
	    }
	    return list;
	  }

	  //获取当前页面选择的数据
	  @SuppressWarnings({ "rawtypes", "unchecked" })
		@RemoteMethod
	  public static Map findEmpShared(String shardId, String fromDataGuid, String thisDataGuid, String gcxh, String type,String cert_id) {
	    Connection conn = null;
	    PreparedStatement pst = null;
	    ResultSet rs = null;
	    Connection mconn = null;
	    String sql = "select guid, fromdata, fromdataname, todata, todataname,  sharedname, sharedfiled, sharedtype, tempid, createdate, status from t_sys_sharedconfig where guid=?";

	    Map dataMap = new HashMap();
	    try {
	    	conn = DataSourcePool.getConnection("168");
	      pst = conn.prepareStatement(sql);
	      pst.setString(1, shardId);
	      rs = pst.executeQuery();
	      List paraList = new ArrayList();
	      paraList.add(thisDataGuid);
	      if (rs.next()) {
	        String[] fileds = rs.getString("sharedfiled").split(",");
	        Map map = createEmpSql(conn,rs.getString("sharedfiled"), rs.getString("fromdata"), fromDataGuid,cert_id);
	        for (int i = 0; i < fileds.length; i++) {
	          String[] colms = fileds[i].split(":");
	          String key = colms[1];

	          Object obj = map.get(colms[0]) == null ? "" : map.get(colms[0]);
	          if ((map.get(colms[0]) != null) && ((obj instanceof Date))) {
	            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(obj);
	            if (time.contains("00:00:00")) {
	              time = time.substring(0, 10);
	            }
	            obj = time;
	          } else if (obj == null) {
	            obj = "";
	          }
	          dataMap.put(key, obj);
	        }

	        dataMap.put("isSubmitForm", rs.getString("sharedtype"));
	      }

	      mconn = DataSourcePool.getConnection("sqlserver");
	      String flag = "0";
	      if ((type.equals("mar")) || (type.equals("dwmar")) || (type.equals("rymar"))) {
	        String mangerStr = "select GCBH,IsLocked from JY_LockedProjectManger where XMJLBH=? and fn_tag<>1 GROUP BY GCBH,IsLocked";
	        pst = mconn.prepareStatement(mangerStr.toString());
	        pst.setString(1, fromDataGuid);
	        rs = pst.executeQuery();
	        while (rs.next()) {
	          if ((!gcxh.equals(rs.getString("GCBH"))) && 
	            (rs.getInt("IsLocked") == 1)) {
	            flag = "1";
	          }
	        }
	      }
	      else if ((type.equals("zj")) || (type.equals("dwzj")) || (type.equals("ryzj"))) {
	        //String zjStr = "select GCBH,IsLocked from JY_LockedProjectManger where XMJLBH=? and fn_tag<>1 and (ptype='项目总监' or ptype='变更后项目总监') GROUP BY GCBH,IsLocked";
	    	  String zjStr = "select GCBH,IsLocked from JY_LockedProjectManger where XMJLBH=? and fn_tag<>1 GROUP BY GCBH,IsLocked";
	        pst = mconn.prepareStatement(zjStr.toString());
	        pst.setString(1, fromDataGuid);
	        rs = pst.executeQuery();
	        int count = 0;
	        while (rs.next()) {
	          if ((!gcxh.equals(rs.getString("GCBH"))) && 
	            (rs.getInt("IsLocked") == 1)) {
	            count++;
	          }
	        }
	        if (count >= 1) {
	          flag = "1";
	        }
	      }
	      dataMap.put("flag", flag);
	      return dataMap;
	    }
	    catch (Exception e)
	    {
	      Map localMap1;
	      e.printStackTrace();
	      return dataMap;
	    } finally {
	      closeConnection(conn,  pst, rs);
	      closeConnection(mconn,pst, rs);
	    }
	  }


	  //选择取消按钮
	  @SuppressWarnings({ "rawtypes", "unchecked" })
	  @RemoteMethod
	  public static Map cancelSelect(String shardId, String fromDataGuid, String thisDataGuid){
		  Connection conn = null;
		    PreparedStatement pst = null;
		    ResultSet rs = null;
		    String sql = "select guid, fromdata, fromdataname, todata, todataname,  sharedname, sharedfiled, sharedtype, tempid, createdate, status from t_sys_sharedconfig where guid=?";

		    Map dataMap = new HashMap();
		    try {
		    conn = DataSourcePool.getConnection("168");
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
		    	closeConnection(conn,pst ,rs);
		    }
	  }

	  //根据条件查询对应单一数据
	  private static Map createEmpSql(Connection conn,String sharedfiled, String fromdata, String dataGuid,String cert_id)
	  {
	    String sql = "select ";
	    String[] fileds = sharedfiled.split(",");
	    for (String filed : fileds) {
	      String[] colms = filed.split(":");
	      sql = sql + colms[0] + ",";
	    }
	    sql = sql.substring(0, sql.length() - 1) + " from " + fromdata + 
	      " where id_number=? ";
	    if(null==cert_id||"".equals(cert_id)||"null".equals(cert_id)){
	    	sql = sql+"and alt_cert_id is null";
	    }else{
	    	sql = sql+"and alt_cert_id like '%"+cert_id+"%'";
	    }
	    try {
	      return DatabaseUtil.getMap(conn,sql, new String[] { dataGuid});
	    } catch (Exception e) {
	      e.printStackTrace();
	    }return null;
	  }
	  
	  private static void closeConnection(Connection con,PreparedStatement pstmt,ResultSet rs){
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
