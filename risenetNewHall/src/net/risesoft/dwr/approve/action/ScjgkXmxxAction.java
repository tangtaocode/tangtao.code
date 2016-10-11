package net.risesoft.dwr.approve.action;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.risesoft.actions.base.BaseActionSupport;
import net.risesoft.beans.user.UserInfo;
import net.risesoft.common.Common;
import net.sf.json.JSONObject;
import net.sysmain.util.GUID;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

@Controller
@ParentPackage("default")//继承json包，用于返回json
@InterceptorRefs( { @InterceptorRef("isLoginStack") })//权限拦截
public class ScjgkXmxxAction extends BaseActionSupport {
	public UserInfo getUser(){
		return (UserInfo) getSession().getAttribute(Common.sessionLoginUserID);}
	private UserInfo userInfo;
	private String method;
	private String xmztz;
	private String jgdm;
	private String ghxkzh;
	private String jhlxwh;
	private String gcdz;
	private String jsdw;
	private String xmbh;
	private String xmmc;
	private String username;
	private String longingname;
	
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
	
	/**
	 * 建项保存方法
	 */
	@Action(value = "/onlineService/scjgkXmxxAction", results = { @Result(name = "success", type="json") })
	public String scjgkXmxxAction(){
		Connection conn = null;
		PreparedStatement pstmt = null;
		userInfo = getUser();
		xmmc = StringUtils.isBlank(xmmc) ? "" : xmmc;
		xmbh = StringUtils.isBlank(xmbh) ? "" : xmbh;
		jsdw = StringUtils.isBlank(jsdw) ? "" : jsdw;
	    gcdz=StringUtils.isBlank(gcdz) ? "" : gcdz;

		BigDecimal xmztz1 = StringUtils.isBlank(xmztz) ? new BigDecimal(0): new BigDecimal(xmztz);
		jhlxwh=StringUtils.isBlank(jhlxwh) ? "" : jhlxwh;
		ghxkzh=StringUtils.isBlank(ghxkzh) ? "" : ghxkzh;
		jgdm=StringUtils.isBlank(jgdm) ? "" : jgdm;
		username = userInfo.getCompanyUser().getEname();
		longingname = userInfo.getLoginname();
		
		if(StringUtils.equals(method, "childsave")){
			StringBuffer sb = new StringBuffer();
			sb.append(" insert into t_yw_lg_scjgkxmxx")
			  .append(" (guid, xmmc, xmbh, jsdw, gcdz, xmztz, jhlxwh, ghxkzh, jgdm, createdate,username,longingname) ")
			  .append(" values ")
			  .append(" (?, ?, ?, ?, ?, ?, ?, ?, ?, sysdate,?,?)");
			
			try{
				DataSource ds=(DataSource)getBean("dataSource");
				conn =ds.getConnection();
				
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setString(1, new GUID().toString());
				pstmt.setString(2, xmmc);
				pstmt.setString(3, xmbh);
				pstmt.setString(4, jsdw);
				pstmt.setString(5, gcdz);
				pstmt.setBigDecimal(6, xmztz1);
				pstmt.setString(7,jhlxwh);
				pstmt.setString(8, ghxkzh);
				pstmt.setString(9, jgdm);
				pstmt.setString(10, username);
				pstmt.setString(11, longingname);
				pstmt.execute();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				closeConnection(conn,pstmt,null);
			}
		}
		return null;
	}
	
	
	/**
	 * 判断项目名称是否存在
	 */
	@Action(value = "/onlineService/checkXmmcAction", results = { @Result(name = "success", type="json") })
	public String CheckXmmcAction(){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String flag = "";
		xmmc = StringUtils.isBlank(xmmc) ? "" : xmmc;
		
		String sb = "select COUNT(*) as count from t_yw_lg_scjgkxmxx where xmmc=?";
		try{
			DataSource ds=(DataSource)getBean("dataSource");
			conn =ds.getConnection();
			pstmt = conn.prepareStatement(sb);
			pstmt.setString(1, xmmc);
			rs = pstmt.executeQuery();
			if(rs.next()){
				flag = rs.getInt("count")==0 ? "0" : "1";
			}
			
			JSONObject json = new JSONObject();
			json.put("message", flag);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.getWriter().write(json.toString());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeConnection(conn,pstmt,rs);
		}
		return null;
	}

	public void closeConnection(Connection conn,PreparedStatement pstmt,ResultSet rs){
		try{
			if(null != rs){
				rs.close();
			}
			if(null != pstmt){
				pstmt.close();
			}
			if(null != conn){
				conn.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	public String getJgdm() {
		return jgdm;
	}

	public void setJgdm(String jgdm) {
		this.jgdm = jgdm;
	}

	public String getGhxkzh() {
		return ghxkzh;
	}

	public void setGhxkzh(String ghxkzh) {
		this.ghxkzh = ghxkzh;
	}

	public String getJhlxwh() {
		return jhlxwh;
	}

	public void setJhlxwh(String jhlxwh) {
		this.jhlxwh = jhlxwh;
	}

	public String getGcdz() {
		return gcdz;
	}

	public void setGcdz(String gcdz) {
		this.gcdz = gcdz;
	}

	public String getJsdw() {
		return jsdw;
	}

	public void setJsdw(String jsdw) {
		this.jsdw = jsdw;
	}

	public String getXmbh() {
		return xmbh;
	}

	public void setXmbh(String xmbh) {
		this.xmbh = xmbh;
	}

	public String getXmmc() {
		return xmmc;
	}

	public void setXmmc(String xmmc) {
		this.xmmc = xmmc;
	}

	public String getXmztz() {
		return xmztz;
	}

	public void setXmztz(String xmztz) {
		this.xmztz = xmztz;
	}
}
