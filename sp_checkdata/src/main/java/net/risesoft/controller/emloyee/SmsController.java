/**
 * @File Name: SmsController.java
 * @Package Name: net.risesoft.controller.emloyee
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2015,RiseSoft  All Rights Reserved.
 * @date 2015年12月9日 下午2:39:31
 */

package net.risesoft.controller.emloyee;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.risesoft.common.base.Pager;
import net.risesoft.common.util.BeanPropertyUtils;
import net.risesoft.common.util.ConstUtils;
import net.risesoft.common.util.Guid;
import net.risesoft.model.RiseEmployee;
import net.risesoft.model.personset.SmsModel;
import net.risesoft.service.ISmsService;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ClassName: SmsController.java
 * @Description: 短信提信功能控制类
 *
 * @author tt
 * @date 2015年12月9日 下午2:39:31
 * @version 
 * @since JDK 1.6
 */
@Controller
@RequestMapping(value="/Sms")
public class SmsController {
	private static Logger logger = Logger.getLogger(SmsController.class);
	@Resource
	private ISmsService iSmsService;
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
	  * @MethodName: toSmsPage
	  * @Description: 跳转信息设置页面
	  * @param： (参数)
	  * @return ModelAndView    返回类型
	  * @throws
	  * 
	  * @Author tt
	  * @date 2015年12月9日  下午6:47:58
	 */
	@RequestMapping(value="/toSmsHtml")
	@ResponseBody
	public ModelAndView toSmsPage(HttpServletRequest request){
		return new ModelAndView("/checkdata/smslist");
	}
	/**
	 * 
	* @Title: getSmsData 
	* @Description: 获取短信数据
	* @param @param request
	* @param @param smsmodel
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/getSmsData")
	@ResponseBody
	public Map<String,Object> getSmsData(HttpServletRequest request,SmsModel smsmodel){
		Map<String,Object> revalue = new HashMap<String, Object>();
		String pageNo = request.getParameter("pager.pageNo");
		String pageSize = request.getParameter("pager.pageSize");
		if(StringUtils.isBlank(pageSize)||StringUtils.isBlank(pageNo)){
			pageNo = "1";
			pageSize = "20";
		}
		Pager pager = new Pager(Integer.parseInt(pageNo),Integer.parseInt(pageSize));
		pager = iSmsService.getSmsDataList(pager,smsmodel);
		revalue.put("pager.pageNo", pageNo);
		revalue.put("pageSize", pageSize);
		revalue.put("pager.totalRows",pager.getTotalRows());
		revalue.put("rows", pager.getPageList());
		return revalue;
		
	}
	/**
	 * 
	* @Title: updateSms 
	* @Description: 保存或更新短信设置数据
	* @param @param request
	* @param @param smsmodel
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/updateSms")
	@ResponseBody
	public Map<String,Object> updateSms(HttpServletRequest request,SmsModel smsmodel){
		if(null==smsmodel.getGuid()||"".equals(smsmodel.getGuid())){
			smsmodel.setGuid(Guid.genGuid());
		}
		smsmodel.setUpdatetime(new Date());
		smsmodel.setCreatetime(new Date());
		 Map<String,Object> rowdata = BeanPropertyUtils.transBean2Map(smsmodel);
		 int temp = iSmsService.createInsertSms(rowdata);
		 Map<String,Object> revalue = new HashMap<String, Object>();
		 if(temp>0){
			 revalue.put("outcome", "1");
		 }else{
			 revalue.put("outcome", "0");
		 }
		 return revalue;
	}
	/**
	 * 
	* @Title: getDetailData 
	* @Description: 根据id获取详细信息页面信息 
	* @param @param request
	* @param @param guid
	* @param @return    设定文件 
	* @return ModelAndView    返回类型 
	* @throws
	 */
	@RequestMapping(value="/getSmsById")
	@ResponseBody
	public ModelAndView getDetailData(HttpServletRequest request,@RequestParam(required=false) String guid){
		Map<String,Object> detail =  iSmsService.getSmsByid(guid);
		request.setAttribute("smsdetail", detail);
		return new ModelAndView("/checkdata/smsDetail");
	}
	/**
	 * 
	* @Title: deleteById 
	* @Description: 根据id删除短信配置信息
	* @param @param request
	* @param @param guid
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/deleteById")
	@ResponseBody
	public Map<String,Object> deleteById(HttpServletRequest request,@RequestParam(required=false) String guid){
		 Map<String,Object> rowdata = new HashMap<String, Object>();
		 rowdata.put("guid", guid);
		int temp =  iSmsService.deleteSms(rowdata);
		Map<String,Object> revalue = new HashMap<String, Object>();
		 if(temp>0){
			 revalue.put("outcome", "1");
		 }else{
			 revalue.put("outcome", "0");
		 }
		 return revalue;
	}
}
