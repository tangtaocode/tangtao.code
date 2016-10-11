package net.risesoft.actions.base;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.risesoft.beans.user.UserInfo;
import net.risesoft.common.Common;
import net.risesoft.utils.base.DateFormatUtil;
import net.risesoft.utils.base.DateJsonValueProcessor;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 基础Action，所有的Action都可以继承，省去写page和控制层统一处理，继承后，只需要些Results和execute方法
 * 
 * @author 张坤
 * @notes Created on 2010-1-25<br>
 *        Revision of last commit:$Revision: 632 $<br>
 *        Author of last commit:$Author: nhjsjmz@gmail.com $<br>
 *        Date of last commit:$Date: 2010-01-25 16:47:12 +0800 (周一, 25 一月 2010) $<br>
 *        <p>
 */
@SuppressWarnings("serial")
@Controller
@Scope("prototype")
public class BaseActionSupport extends ActionSupport
{
	/** jsp页面传过来的当前页 **/
	private int page;
	/**当前登录用户字符串信息**/
	private String userString;
	
	/**或许日志实例**/
	public Log getLog() {
		return LogFactory.getLog(this.getClass());
	}
	/**获取requert **/
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}
	/**获取response **/
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}
	/**获取session **/
	public HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}
	/** 获取登录用户字符串信息，如果没有登录返回当前时间格式为YYYY-MM-DD**/
	public void setLoginUserString(){
		UserInfo user = (UserInfo) getSession().getAttribute(Common.sessionLoginUserID);
		if(user!=null){
			this.userString="用户："+user.getLoginname();
		}else{
			this.userString = DateFormatUtil.parseToString(new Date(System.currentTimeMillis()));
		}
	}
	public String getUserType(){
		UserInfo userInfo =  (UserInfo)getSession().getAttribute(Common.sessionLoginUserID);
		if(null != userInfo){
			return userInfo.getUsertype();
		}
		return null;
	}
	/**清除当前用户session所有值**/
	@SuppressWarnings("unchecked")
	public void clearSession() {
		for (Enumeration items = getSession().getAttributeNames(); items
				.hasMoreElements();) {
			String item = (String) items.nextElement();
			getLog().info(item);
			getSession().removeAttribute(item);
		}
	}
	public int getPage()
	{
		return page;
	}
	public String getUserGUID(){
		return ((UserInfo) getSession().getAttribute(Common.sessionLoginUserID)).getGuid();
	}
	public UserInfo getUser(){
		return (UserInfo) getSession().getAttribute(Common.sessionLoginUserID);
	}
	public void setPage(int page)
	{
		this.page = page;
	}
	public void outJsonString(String str) {
		getLog().info(str);
		outString(str);
	}
	private JsonConfig jsoncfg(String pattern){//yyyy-MM-dd HH:mm:ss
		JsonConfig cfg = new JsonConfig();
		cfg.setIgnoreDefaultExcludes(false);
		cfg.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		cfg.registerJsonValueProcessor(Date.class,new DateJsonValueProcessor(pattern)); //date processor register
		return cfg;
	}
	public void outJson(Object obj,String pattern) {
		getLog().info(JSONObject.fromObject(obj,jsoncfg(pattern)).toString());
		outJsonString(JSONObject.fromObject(obj,jsoncfg(pattern)).toString());
	}

	public void outJsonArray(Object array,String pattern) {
		getLog().info(JSONArray.fromObject(array,jsoncfg(pattern)).toString());
		outString(JSONArray.fromObject(array,jsoncfg(pattern)).toString());
	}

	public void outString(String str) {
		try {
			getResponse().setContentType("application/json;charset=UTF-8");
	      //  getResponse().setCharacterEncoding("UTF-8");
			PrintWriter out = getResponse().getWriter();
			getLog().info(str);
			out.write(str);
			// 清空缓存
			out.flush();
			// 关闭流
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getUserString() {
		setLoginUserString();
		return userString;
	}

	public void setUserString(String userString) {
		this.userString = userString;
	}
}
