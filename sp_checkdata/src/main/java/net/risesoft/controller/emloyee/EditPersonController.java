/**
 * @Project Name:LGOneHome
 * @File Name: Editpassword.java
 * @Package Name: net.risesoft.controller.emloyee
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2015,RiseSoft  All Rights Reserved.
 * @date 2015年10月26日 下午3:25:17
 */

package net.risesoft.controller.emloyee;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.risesoft.common.util.ConstUtils;
import net.risesoft.common.util.Digest;
import net.risesoft.common.util.WSHelper;
import net.risesoft.model.RiseEmployee;
import net.risesoft.model.personset.PersonModel;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import egov.appservice.org.model.Person;

/**
 * @ClassName: Editpassword.java
 * @Description: 修改密码
 *
 * @author tangtao
 * @date 2015年10月26日 下午3:25:17
 * @version 
 * @since JDK 1.6
 */
@Controller
@RequestMapping("/person")
public class EditPersonController {
	private static Logger logger = Logger.getLogger(EditPersonController.class);
	/**
	 * form表单提交 Date类型数据绑定
	 * <功能详细描述>
	 * @param binder
	 * @see [类、类#方法、类#成员]
	 */
	@InitBinder  
	public void initBinder(WebDataBinder binder) {  
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		    dateFormat.setLenient(false);  
		    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));  
	}
	/**
	 * 
	  * @MethodName: updatePassword
	  * @Description: 修改密码
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return String    返回类型
	  * @throws
	  * 
	  * @Author tangtao
	  * @date 2015年11月13日  上午11:23:51
	 */
	@RequestMapping(value="/updatePassword",method=RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String updatePassword(HttpServletRequest request,HttpServletResponse response) throws IOException, Exception{
		RiseEmployee em = (RiseEmployee) request.getSession().getAttribute(ConstUtils.SESSION_USER);
		Person per = WSHelper.getInstance().getPerson(em.getRcid());
		String newpwd = request.getParameter("newpwd");
		JSONObject json = new JSONObject();
		per.setPassword(Digest.SHA1(newpwd));
		WSHelper.getInstance().updatePersonInfo(per, false);
		json.put("flag", "true");
		return json.toString();
		
	}
	/**
	 * 
	  * @MethodName: updatePerson
	  * @Description: TODO 人员信息修改
	  * @param： (参数)
	  * @return String    返回类型
	  * @throws
	  * 
	  * @Author tantao
	  * @date 2015年11月12日  上午10:43:58
	 */
	@RequestMapping(value="/updatePerson",method=RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public ModelAndView updatePerson(@ModelAttribute PersonModel person) throws IOException, Exception{
		if(person.getUID()==null||"".equals(person.getUID())){
			logger.debug("不存在用户uid");
		}else{
			WSHelper.getInstance().updatePersonInfo(person, false);
		}
		return new ModelAndView("forward:/employeeSet/frameset");
	}
	/**
	 * 
	  * @MethodName: showPerson
	  * @Description: TODO 显示人员信息
	  * @param： (参数)
	  * @return String    返回类型
	  * @throws
	  * 
	  * @Author tantao
	  * @date 2015年11月12日  上午10:43:58
	 */
	@RequestMapping(value="/showPerson",method=RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public ModelAndView showPerson(HttpServletRequest request,HttpServletResponse response) throws IOException, Exception{
		RiseEmployee em = (RiseEmployee) request.getSession().getAttribute(ConstUtils.SESSION_USER);
		Person per = WSHelper.getInstance().getPerson(em.getRcid());
		response.setContentType("text/html; charset=utf-8");
		return new ModelAndView("/employeeSet/updatePerson").addObject("person",per);
	}
	/**
	 * 
	  * @MethodName: getPasshtml
	  * @Description: 返回密码修改页面
	 */
	@RequestMapping(value = "/passwordhtml")
	public ModelAndView getPasshtml(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html; charset=utf-8");
		return new ModelAndView("/employeeSet/updatePassword");
	}
	
	/**
	 * 
	  * @MethodName: getPasshtml
	  * @Description: 返回分屏修改页面
	 */
	@RequestMapping(value = "/iconhtml")
	public ModelAndView iconhtml(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html; charset=utf-8");
		return new ModelAndView("/employeeSet/iconmodify");
	}
	
}
