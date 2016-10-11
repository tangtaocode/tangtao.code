package net.risesoft.controller.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.risesoft.common.json.JsonUtils;
import net.risesoft.common.sendms.VerifyConstant;

/**
 * 验证验证码Servlet
 * @author HJL
 * @date 2015年5月6日  下午5:48:18
 */
public class VerifyCheckCodeServlet extends HttpServlet{
	private static final long serialVersionUID = 3372206154825067680L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Object verifyCode = request.getSession().getAttribute(VerifyConstant.verifyCode);
		String checkCode = request.getParameter("checkCode");
		int status = -1;
		if(verifyCode!=null&&checkCode!=null&&checkCode.toLowerCase().equals(verifyCode.toString().toLowerCase())){
			status = 1;
		}
		new JsonUtils().outString("{\"status\":\""+status+"\"}", response);
	}
}
