/**   
* @Title: CheckDataController.java 
* @Package net.risesoft.controller.checkdata 
* @Description: TODO(用一句话描述该文件做什么) 
* @author A18ccms A18ccms_gmail_com   
* @date 2016年9月22日 上午9:47:37 
* @version V1.0   
*/
package net.risesoft.controller.checkdata;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.risesoft.common.base.Pager;
import net.risesoft.common.util.StringUtil;
import net.risesoft.service.CheckDataService;

/** 
* @ClassName: CheckDataController 
* @Description: 检查数据控制类 
* @author tangtao
* @date 2016年9月22日 上午9:47:37 
*  
*/
@Controller
@RequestMapping("/checkData")
public class CheckDataController {
	private static Logger logger = Logger.getLogger(CheckDataController.class);
	@javax.annotation.Resource
	CheckDataService checkDataService;
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
	* @Title: checkHourData 
	* @Description: 查询存在超时推送数据
	* @param @param request
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/checkHourData",method=RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public  Map<String,Object> checkHourData(HttpServletRequest request,
			@RequestParam(required = false) String sblsh,
			@RequestParam(required = false) String sxmc){
		Map<String,Object>  revalue = new HashMap<String, Object>();
		String pageNo = request.getParameter("pager.pageNo");
		String pageSize = request.getParameter("pager.pageSize");
		if(StringUtils.isBlank(pageSize)||StringUtils.isBlank(pageNo)){
			pageNo = "1";
			pageSize = "20";
		}
		Pager pager = new Pager(Integer.parseInt(pageNo),Integer.parseInt(pageSize));
		pager = checkDataService.checkHourData(pager,sblsh,sxmc);
		revalue.put("pager.pageNo", pageNo);
		revalue.put("pageSize", pageSize);
		revalue.put("pager.totalRows",pager.getTotalRows());
		revalue.put("rows", pager.getPageList());
		return revalue;
	}
	/**
	 * 
	* @Title: checkJianchaData 
	* @Description: 查询监察数据
	* @param @param request
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws
	 */
	@RequestMapping("/checkJianchaData")
	@ResponseBody
	public  Map<String,Object> checkJianchaData(HttpServletRequest request,
			@RequestParam(required = false) String sblsh,
			@RequestParam(required = false) String sxmc){
		String pageNo = request.getParameter("pager.pageNo");
		String pageSize = request.getParameter("pager.pageSize");
		if(StringUtils.isBlank(pageSize)||StringUtils.isBlank(pageNo)){
			pageNo = "1";
			pageSize = "20";
		}
		Pager pager = new Pager(Integer.parseInt(pageNo),Integer.parseInt(pageSize));
		pager = checkDataService.checkJianChaData(pager,sblsh,sxmc);
		Map<String,Object>  revalue = new HashMap<String, Object>();
		revalue.put("pager.pageNo", pageNo);
		revalue.put("pageSize", pageSize);
		revalue.put("pager.totalRows",pager.getTotalRows());
		revalue.put("rows", pager.getPageList());
		return revalue;
	}
	/**
	 * 
	* @Title: getProblemList 
	* @Description:获取问题数据列表
	* @param @param request
	* @param @param sblsh
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping("/getProblemList")
	@ResponseBody
	public Map<String,Object> getProblemList(HttpServletRequest request,@RequestParam(required = false) String sblsh){
		String pageNo = request.getParameter("pager.pageNo");
		String pageSize = request.getParameter("pager.pageSize");
		if(StringUtils.isBlank(pageSize)||StringUtils.isBlank(pageNo)){
			pageNo = "1";
			pageSize = "20";
		}
		Pager pager = new Pager(Integer.parseInt(pageNo),Integer.parseInt(pageSize));
		pager = checkDataService.getProblemList(pager, sblsh);
		Map<String,Object>  revalue = new HashMap<String, Object>();
		revalue.put("pager.pageNo", pageNo);
		revalue.put("pageSize", pageSize);
		revalue.put("pager.totalRows",pager.getTotalRows());
		revalue.put("rows", pager.getPageList());
		return revalue;
	}
	/**
	 * 
	* @Title: getOverCounts 
	* @Description: 获取超期，及规则性问题总数
	* @param @param request
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping("/getOverCounts")
	@ResponseBody
	public Map<String,Object> getOverCounts(HttpServletRequest request){
		Map<String,Object>  revalue = new HashMap<String, Object>();
		revalue.put("hourCounts", checkDataService.getHourCounts());
		revalue.put("jianChaCounts", checkDataService.getJianChaCounts());
		return revalue;
	}
	/**
	 * 
	* @Title: getHourHtml 
	* @Description: 跳转超期监控页面
	* @param @param request
	* @param @return    设定文件 
	* @return ModelAndView    返回类型 
	* @throws
	 */
	@RequestMapping("/hourHtml")
	public ModelAndView getHourHtml(HttpServletRequest request){
		return new ModelAndView("/checkdata/overTimeCheck");
	}
	/**
	 * 
	* @Title: getJianchaHtml 
	* @Description: 跳转到数据规范页面
	* @param @param request
	* @param @return    设定文件 
	* @return ModelAndView    返回类型 
	* @throws
	 */
	@RequestMapping("/jianchaHtml")
	public ModelAndView getJianchaHtml(HttpServletRequest request){
		return new ModelAndView("/checkdata/ruleDataCheck");
	}
	/**
	 * 
	* @Title: getProblemDataHtml 
	* @Description: 获取问题数据列表
	* @param @param request
	* @param @return    设定文件 
	* @return ModelAndView    返回类型 
	* @throws
	 */
	@RequestMapping("/problemDataHtml")
	public ModelAndView getProblemDataHtml(HttpServletRequest request,@RequestParam(required = false) String sblsh){
		request.setAttribute("sblsh", sblsh);
		return new ModelAndView("/checkdata/problemDataList");
	}
	
}
