package net.risesoft.controller.servlet;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.risesoft.common.json.JsonUtils;
import net.risesoft.common.sendms.IPlatformService;
import net.risesoft.common.sendms.PlatformServiceService;
import net.risesoft.common.sendms.VerifyConstant;

/**
 * 发送验证短信Servlet
 * @author HJL
 * @date 2015年5月7日  上午10:39:43
 */
public class SendVerifyMsServlet extends HttpServlet{
	private static final long serialVersionUID = -242220611478755958L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int status = -1;
		
		String mobile = request.getParameter("realMobile");
		if(mobile!=null&&mobile.length()==11){//手机号码
			int randomNum = getRandomNumber(6);
			String contents = "短信验证码："+randomNum;
			
			Object sendCodeTime = request.getSession().getAttribute(VerifyConstant.sendCodeTime);
			long timeMillis = System.currentTimeMillis();
			if(sendCodeTime!=null&&((Long)sendCodeTime)+1000*60>timeMillis){//两次短信发送间隔小于60秒
				status = 0;
			}else{
				IPlatformService service = new PlatformServiceService().getPlatformServicePort();
				String result = service.sendSms("sppt", mobile, null, contents, "审批系统", 3, null);
//				Map<String,Object> map=JsonUtil.getMap4Json(result);
//				if (map.get("success")!=null&&(Boolean)map.get("success")) {
				if(result.indexOf("true")>0){
					status = 1;
					request.getSession().setAttribute(VerifyConstant.mobileVerify, randomNum);
					request.getSession().setAttribute(VerifyConstant.sendCodeTime, System.currentTimeMillis());
				}
			}
		}else{
			status = -2;
		}
		new JsonUtils().outString("{\"status\":\""+status+"\"}", response);
	}
	
	/**
	 * 得到n位长度的随机数
	 * @param n 随机数的长度
	 * @return 返回  n位的随机整数
	 */
	public static int getRandomNumber(int n){
		int temp = 0;
		int min = (int) Math.pow(10, n-1);
		int max = (int) Math.pow(10, n);
		Random rand = new Random();
		
		while(true){
			temp = rand.nextInt(max);
			if(temp >= min)
				break;
		}
		
		return temp;
	}
}
