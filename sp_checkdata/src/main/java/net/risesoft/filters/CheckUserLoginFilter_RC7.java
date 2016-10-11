package net.risesoft.filters;



import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.risesoft.common.util.CodeConvertor;
import net.risesoft.common.util.ConstUtils;
import net.risesoft.common.util.WSHelper;
import net.risesoft.model.RiseEmployee;
import net.risesoft.service.IEmployeeService;
import net.risesoft.soa.framework.service.sso.client.util.OrgModelHelper;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import egov.appservice.org.model.Person;
@Component("riseUserloginFilter")
public class CheckUserLoginFilter_RC7 implements Filter {
	
	@Resource
	IEmployeeService employeeService;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws java.io.IOException, javax.servlet.ServletException {
		HttpSession session = ((HttpServletRequest) request).getSession(true);
		String loginRCUID = (String) session.getAttribute(ConstUtils.LOGINRCUID);
		RiseEmployee riseUser = (RiseEmployee) session.getAttribute(ConstUtils.SESSION_USER);
		if (StringUtils.isBlank(loginRCUID) || riseUser == null||
				(riseUser !=null&&(StringUtils.isBlank(riseUser.getEmployee_loginname())||
						StringUtils.isBlank(riseUser.getEmployee_guid())||StringUtils.isBlank(riseUser.getRcid())))) {
			loginRCUID = OrgModelHelper.getLoginUID();
			String LoginName = OrgModelHelper.getLoginName();
			if(loginRCUID!=null){
				try {
					Person person = WSHelper.getInstance().getPerson(loginRCUID);
					riseUser = employeeService.getEmployeebyGuid(CodeConvertor.UID2RiseGUID(loginRCUID));
					riseUser.setEmployee_guid(CodeConvertor.UID2RiseGUID(loginRCUID));
					riseUser.setEmployee_loginname(LoginName);
					riseUser.setEmployee_mobile(person.getMobile());
					riseUser.setEmployee_name(person.getName());
					riseUser.setRcid(loginRCUID);
					Map<String, Object> map = employeeService.getUserBureau(riseUser.getDepartment_guid());
					if(map!=null){
						riseUser.setBureauGUID((String)map.get("DEPARTMENT_GUID"));
						riseUser.setBureauName((String)map.get("DEPARTMENT_NAME"));
					}
					if(ConstUtils.ADMIN_USERGUID.equals(riseUser.getEmployee_guid())){
						riseUser.setDepartment_guid(ConstUtils.ADMIN_DEPARTMENTGUID);
						riseUser.setBureauName(ConstUtils.ADMIN_DEPARTMENTNAME);
					}
					session.setAttribute(ConstUtils.SESSION_USER, riseUser);
					session.setAttribute(ConstUtils.LOGINRCUID, loginRCUID);
					session.setAttribute(ConstUtils.CURRENTUSER_GUID, riseUser.getEmployee_guid());
					
					session.setAttribute(ConstUtils.RANDOM, getRandomNumber(8));//保存8位随机数到session，防跨站点请求伪造
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		filterChain.doFilter(request, response);
	}
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
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
