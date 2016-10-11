package net.risesoft.controller.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.risesoft.common.json.JsonUtils;
import net.risesoft.common.sendms.VerifyConstant;

/**
 * 验证短信验证码Servlet
 * @author HJL
 * @date 2015年5月7日  上午11:25:11
 */
public class VerifyMobileCodeServlet extends HttpServlet{
	private static final long serialVersionUID = -8352408114476818938L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int status = 0;
		String mobileCode = request.getParameter("mobileCode");
		Object mobileVerify = request.getSession().getAttribute(VerifyConstant.mobileVerify);
		if(mobileVerify==null){
			status = -1;
		}if(mobileVerify!=null&&mobileCode!=null&&mobileCode.toLowerCase().equals(mobileVerify.toString().toLowerCase())){
			status = 1;
		}
		new JsonUtils().outString("{\"status\":\""+status+"\"}", response);
	}
	
}
