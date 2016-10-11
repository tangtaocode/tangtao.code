package net.risesoft.controller.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.risesoft.common.json.JsonUtils;
import net.risesoft.common.util.RC7Finder;
import egov.appservice.org.model.Person;
import egov.appservice.org.service.PersonManager;

/**
 * 验证用户名Servlet
 * @author HJL
 * @date 2015年5月6日  下午3:28:44
 */
public class VerifyUserNameServlet extends HttpServlet{
	private static final long serialVersionUID = 2427641605976835872L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int status = 0;
		try {
			String loginName = request.getParameter("loginName");
			if(loginName!=null){
				PersonManager pm = RC7Finder.getServiceManagerClient("org.PersonManager");
				Person p = pm.getPersonByLoginName(loginName);
				if(p!=null){
					status = 1;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			status = -1;
		}
		new JsonUtils().outString("{\"status\":\""+status+"\"}",response);
	}
	
}
