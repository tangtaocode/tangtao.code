package net.risesoft.controller.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.risesoft.common.sendms.VerifyConstant;
import net.risesoft.common.util.RC7Finder;
import egov.appservice.org.model.Person;
import egov.appservice.org.service.PersonManager;

/**
 * 修改密码Servlet
 * @author HJL
 * @date 2015年5月6日  上午9:52:03
 */
public class EditPsdStepServlet extends HttpServlet{
	private static final long serialVersionUID = 6793117478375279689L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
 		String step = request.getParameter("step");
		if("1".equals(step)){//跳转第二步
			String loginName = request.getParameter("userCode");
			Object verifyCode = request.getSession().getAttribute(VerifyConstant.verifyCode);
			String checkCode = request.getParameter("imgCode");
			if(verifyCode!=null&&checkCode!=null&&checkCode.toLowerCase().equals(verifyCode.toString().toLowerCase())&&loginName!=null){
				PersonManager pm = RC7Finder.getServiceManagerClient("org.PersonManager");
				Person p = pm.getPersonByLoginName(loginName);
				String params = "";
				if(p.getMobile()!=null&&p.getMobile().length()==11){
					String mobile = p.getMobile();
					params = "&mobile="+mobile.substring(0,3)+"*****"+mobile.substring(8)+"&realMobile="+mobile+"&guid="+p.getUID();
				}
				request.getRequestDispatcher("/user/VerifyCode.jsp?step=2"+params).forward(request, response);
			}else{//跳回第一步
				request.getRequestDispatcher("/user/VerifyUserName.jsp?step=1").forward(request, response);
			}
		}else if("2".equals(step)){//跳转第三步
			String guid = request.getParameter("guid");
			if(guid!=null){
				request.getRequestDispatcher("/user/EditPsd.jsp?step=3&guid="+guid).forward(request, response);
			}else{//跳回第一步
				request.getRequestDispatcher("/user/VerifyUserName.jsp?step=1").forward(request, response);
			}
		}else{//其余为第一步
			request.getRequestDispatcher("/user/VerifyUserName.jsp?step=1").forward(request, response);
		}
	}
}
