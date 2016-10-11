package net.risesoft.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.risesoft.common.util.ConstUtils;

/**
 * //保存8位随机数到session，防跨站点请求伪造
 * @author HJL
 * @date 2015年5月13日  下午5:15:54
 */
public class SessionFilter implements Filter{

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest toRequest = (HttpServletRequest)request;
		String url = toRequest.getRequestURI();
		System.out.println("SessionFilter:"+url);
		
//		String clientSessionId = request.getParameter("ssid");
//		String serverSessionId = ((HttpServletRequest)request).getSession().getId();
//		if(serverSessionId.equals(clientSessionId)){
		String cRandom = request.getParameter(ConstUtils.RANDOM);
		Integer clientRandom = cRandom==null?0:Integer.parseInt(cRandom);
		Integer serverRandom = (Integer)((HttpServletRequest)request).getSession().getAttribute(ConstUtils.RANDOM);
		if(serverRandom.equals(clientRandom)){
			chain.doFilter(request, response);
		}else{
			HttpServletResponse toResponse = (HttpServletResponse)response;
			toResponse.sendRedirect("/oneHome/index");
		}
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
}
