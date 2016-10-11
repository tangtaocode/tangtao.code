package net.risesoft.controller.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.risesoft.common.json.JsonUtils;
import net.risesoft.common.util.WSHelper;

/**
 * 执行修改密码servlet
 * @author HJL
 * @date 2015年5月7日  下午2:23:58
 */
public class EditPsdServlet extends HttpServlet{
	private static final long serialVersionUID = -1386046626743323718L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int status = 0;
		String guid = request.getParameter("guid");
		String newPsd = request.getParameter("newPsd");
		if(guid!=null&&!"".equals(guid)&&newPsd!=null&&!"".equals(newPsd)){
			WSHelper.getInstance().updatePassword(guid,newPsd);
			status = 1;
		}
		new JsonUtils().outString("{\"status\":\""+status+"\"}", response);
	}
	
}
