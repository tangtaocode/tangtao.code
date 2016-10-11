package net.risesoft.utils.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import net.risesoft.beans.user.UserInfo;
import net.risesoft.common.Common;
import net.risesoft.utils.annotation.LoginRequired;
import net.risesoft.utils.base.WebUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
/**
 * 
  * @ClassName: LoginRequiredInterceptor
  * @Description: 用户登录状态拦截器
  * @author Comsys-zhangkun
  * @date Jun 1, 2013 5:22:02 PM
  *
 */
public class LoginRequiredInterceptor extends AbstractInterceptor {

	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	private static final long serialVersionUID = 9123745241016351148L;
	private final Log log = LogFactory.getLog(getClass());
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String methodName = invocation.getProxy().getMethod();
		Method method = invocation.getAction().getClass().getMethod(methodName);
		//获方法上存在注解的时候才进行拦截
		if(method!=null && method.isAnnotationPresent(LoginRequired.class)){
			LoginRequired login = method.getAnnotation(LoginRequired.class);//得到方法上的注解
			UserInfo user = WebUtil.getUserInfo(request);
			//如果用户已经登录，则方形,否则提示无权限
			if(user!=null){
				return invocation.invoke();
			}else{
				if(log.isInfoEnabled())log.info("用户未登录,跳转至首页！  拦截模块："+login.module());
				request.setAttribute(Common.skipToPageUrl, "/user/loginAction.html");
				request.getSession().setAttribute(Common.loginSuUrl,WebUtil.getRequestURIWithParam(request));
				return "message";
			}
		}
		return invocation.invoke();
	}
	
	}

	
