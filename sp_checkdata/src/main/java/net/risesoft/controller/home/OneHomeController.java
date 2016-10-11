/**
 * @ProjectName:zjjHome
 * @FileName: OneHomeController.java
 * @PackageName: net.risesoft.controller.home
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2015,RiseSoft  All Rights Reserved.
 * @date May 5, 2015 5:08:48 PM
 */
package net.risesoft.controller.home;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.risesoft.service.IOneHomeService;
import net.risesoft.service.IWebServiceTodoService;

/**
 * @ClassName: OneHomeController.java
 * @Description: TODO
 *
 * @author kun
 * @date May 5, 2015 5:08:48 PM
 * @version 
 * @since JDK 1.6
 */
@Controller
@RequestMapping("/oneHome")
public class OneHomeController {
	private static final Logger  logger = LoggerFactory.getLogger(OneHomeController.class); 
	@javax.annotation.Resource
	private IOneHomeService oneHomeService;
	@javax.annotation.Resource
	private IWebServiceTodoService webServiceTodoService;
	/**
	 * 
	  * @MethodName: index
	  * @Description: 初始化首页数据
	  * @return ModelAndView    返回类型
	  * @throws
	  * 
	  * @Author TANGTAO
	  * @date 2015年11月11日  上午11:28:01
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request){
		   return new ModelAndView("/index");
	}
	/**
	 * 
	* @Title: handworkcheck 
	* @Description: 手动校验审批过程数据
	* @param @param request
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/handworkcheck")
	@ResponseBody
	public Map<String,Object>  handworkcheck(HttpServletRequest request){
		Map<String,Object> revalue = new HashMap<String, Object>();
		if(oneHomeService.handworkCheckData()){
			revalue.put("outcome", "1");
			logger.info("===================执行手工校验数据成功！===============");
		}else{
			revalue.put("outcome", "0");
			logger.info("===================执行手工校验数据失败！===============");
		}
		return revalue;
	}
	/**
	 * 退出登录
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/Logout")
	public ModelAndView Logout(HttpServletRequest request){
		HttpSession session = request.getSession();
		session.invalidate();//注销本地session

		return new ModelAndView("/logout");
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/helpDocDown")
	public ModelAndView helpDocDown(HttpServletRequest request,HttpServletResponse response){
		InputStream inStream = null;
		byte[] buffer = null;
		response.setContentType("application/x-download");
		String filepath= request.getRealPath("/download/helpDoc.doc");
		try {
			response.addHeader("Content-Disposition", "attachment;filename="
					+ URLEncoder.encode("深圳市水务局审批数据校验使用指南.doc", "UTF-8"));
			ServletOutputStream out = response.getOutputStream();
			inStream = new FileInputStream(new File(filepath));
			buffer = new byte[1024 * 1024 * 20];//
			int length = 0;
			while ((length = inStream.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(inStream!=null)inStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}

