/**
 * @Project Name:risenetNewHall
 * @File Name: GetxmbhAction.java
 * @Package Name: net.risesoft.dwr.approve.action
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2015,RiseSoft  All Rights Reserved.
 * @date 2015-4-17 下午03:28:08
 */
package net.risesoft.dwr.approve.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

import net.risesoft.actions.base.BaseActionSupport;
import net.risesoft.dwr.approve.dao.ScjgkXmxxDao;
import net.sf.json.JSONObject;

/**
 * @ClassName: GetxmbhAction.java
 * @Description: TODO
 *
 * @author TT
 * @date 2015-4-17 下午03:28:08
 * @version 
 * @since JDK 1.6
 */
@Controller
@ParentPackage("default")//继承json包，用于返回json
@InterceptorRefs( { @InterceptorRef("isLoginStack") })//权限拦截
public class GetxmbhAction extends BaseActionSupport {
	@Action(value = "/onlineService/GetxmbhAction", results = { @Result(name = "success", type="json") })
	public String getXmbh(){
		try {
			HttpServletRequest  request = ServletActionContext.getRequest();
			HttpServletResponse  response = ServletActionContext.getResponse();
			String xmbh = ScjgkXmxxDao.setDefaultXmbh(request);
			String flag = ScjgkXmxxDao.judexmbh(request);
			JSONObject json = new JSONObject();
			json.put("message", ""+xmbh);
			json.put("flag", ""+flag);
			response.getWriter().write(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
