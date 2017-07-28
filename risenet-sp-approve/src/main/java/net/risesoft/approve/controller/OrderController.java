/**
 * @Project Name:risenet-sp-approve
 * @File Name: OrderController.java
 * @Package Name: net.risesoft.approve.controller
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2015,RiseSoft  All Rights Reserved.
 * @date 2015年12月24日 上午11:46:52
 */
package net.risesoft.approve.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.risesoft.approve.entity.base.Pager;
import net.risesoft.approve.entity.jpa.OrderManager;
import net.risesoft.approve.service.OrderService;
import net.risesoft.approve.service.SharestuffService;
import net.risesoft.model.Person;
import net.risesoft.tenant.annotation.RiseLog;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.utilx.StringX;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName: OrderController.java
 * @Description:  预约
 *
 * @author chenbingni
 * @date 2015年12月24日 上午11:46:52
 * @version 
 * @since JDK 1.7
 */
@Controller
@RequestMapping("/orderOnline")
public class OrderController {
	
	protected Logger log = LoggerFactory.getLogger(OrderController.class);
	
	@Resource(name="orderService")
	private OrderService orderService;
	
	@Resource(name="sharestuffService")
	private SharestuffService sharestuffService;

	/**
	 * 
	  * @MethodName: showOnlineOrders
	  * @Description: 窗口人员网上预约菜单
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return String    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2015年12月24日  上午11:48:26
	 */
	@RiseLog(operateName = "打开网上预约页面",operateType = "查看")
	@RequestMapping(value = "/orders", method = RequestMethod.GET)
	public String showOnlineOrders(Model model) {
		Date date = new Date();
		SimpleDateFormat f = new SimpleDateFormat("YYYY-MM-dd");
		String beginDate = f.format(date);
		model.addAttribute("beginDate", beginDate);
		return "order/ordersOnline";
	}
	
	/**
	 * 
	  * @MethodName: orderList
	  * @Description: 窗口人员的预约列表
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return Map<String,Object>    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2015年12月24日  下午4:25:18
	 */
	@RiseLog(operateName = "获取窗口人员预约列表",operateType = "查看")
	@RequestMapping(value = "/orderList")
	@ResponseBody
	public Map<String, Object> orderList(String ishf, String orderPerson, String orderId, String beginDate, String endDate, String slstate) {
		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		Person person = ThreadLocalHolder.getPerson();
		listmap = orderService.findByUserID(person, orderPerson, orderId, ishf, beginDate, endDate, slstate);
		int pagetotal = listmap.size();
		map.put("pager.pageNo", 1);
		map.put("pageSize", 15);
		map.put("pager.totalRows", pagetotal);
		map.put("rows", listmap);

		return map;
	}
	
	/**
	 * 
	  * @MethodName: OrderManage
	  * @Description: 预约管理首页
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return String    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年3月16日  上午10:41:01
	 */
	@RiseLog(operateName = "打开事项预约管理首页",operateType = "查看")
	@RequestMapping(value = "/orderManage", method = RequestMethod.GET)
	public String OrderManage() {
		return "order/orderManage";
	}
	
	/**
	 * 
	  * @MethodName: orderManageList
	  * @Description: 预约管理（可否预约、预约人数限制）
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return Map<String,Object>    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年3月16日  上午9:55:38
	 */
	@RiseLog(operateName = "获取事项预约列表",operateType = "查看")
	@RequestMapping(value = "/orderManageList")
	@ResponseBody
	public Map<String, Object> orderManageList(HttpServletRequest request, String bureauguid, String approvename) {
		Map<String, Object> map = new HashMap<>();
		String pageNo = request.getParameter("pager.pageNo");
		String pageSize = request.getParameter("pager.pageSize");
		try {
			if (StringX.isBlank(pageSize) || StringX.isBlank(pageNo)) {
				pageSize = "20";
				pageNo = "1";
			}
		Pager pager = new Pager(Integer.parseInt(pageNo),Integer.parseInt(pageSize));
		pager = orderService.findOrderManage(bureauguid, approvename, pager);
		List<Map<String, Object>> manageMap = pager.getPageList();		
		map.put("pager.pageNo", pager.getPageNo());
		map.put("pageSize", pager.getPageSize());
		map.put("pager.totalRows", pager.getTotalRows());
		map.put("rows", manageMap);
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage());
		}
		return map;
	}
	
	/**
	 * 
	  * @MethodName: orderHuifu
	  * @Description: 网上预约回复
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return Map<String,Object>    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年3月8日  下午2:39:21
	 */
	@RiseLog(operateName = "打开网上预约回复页面",operateType = "查看")
	@RequestMapping(value = "/orderHuifu", method = RequestMethod.GET)
	public String orderHuifu(String orderGuid, Model model) {
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		listmap = orderService.findOrderByGuid(orderGuid);
		
		model.addAttribute("ispass", listmap.get(0).get("ISPASS"));
		model.addAttribute("hfnr", listmap.get(0).get("HFNR"));
		model.addAttribute("hftime", listmap.get(0).get("HFTIME"));
		model.addAttribute("hfry", listmap.get(0).get("HFRY"));
		
		model.addAttribute("orderGuid", orderGuid);
		
		return "order/huifu";
	}
	
	/**
	 * 
	  * @MethodName: saveHuifu
	  * @Description: 保存预约回复
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return Map<String,Object>    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年3月16日  下午5:44:17
	 */
	@RiseLog(operateName = "保存网上预约回复数据",operateType = "增加")
	@RequestMapping(value = "/saveHuifu",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> saveHuifu(String orderGuid, String hfnr) {
		Person person = ThreadLocalHolder.getPerson();
		boolean res = orderService.saveHuifu(person, orderGuid, hfnr);
		
		Map<String,Object> maptemp=new HashMap<String, Object>();

		if(res) {
			maptemp.put("msg", "true");
		}else {
			maptemp.put("msg", "false");
		}
		return maptemp;
	}
	
	/**
	 * 
	  * @MethodName: editManage
	  * @Description: 编辑预约管理页面初始化
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return String    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年3月16日  下午2:45:20
	 */
	@RiseLog(operateName = "编辑预约管理页面初始化",operateType = "查看")
	@RequestMapping(value = "/editManage", method = RequestMethod.GET)
	public String editManage(String itemguid, Model model) {
		Map<String, Object> map = orderService.findManageByGuid(itemguid);
		
		model.addAttribute("itemGuid", map.get("APPROVEITEMGUID"));
		model.addAttribute("itemName", map.get("APPROVEITEMNAME"));
		model.addAttribute("orderpermission", map.get("ORDERPERMISSION"));
		
		return "order/editManage";
	}
	
	/**
	 * 
	  * @MethodName: saveManage
	  * @Description: 保存事项的预约配置
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return Map<String,Object>    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年3月16日  下午5:44:57
	 */
	@RiseLog(operateName = "保存事项预约配置",operateType = "增加")
	@RequestMapping(value = "/saveManage",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> saveManage(HttpServletRequest request) {
		String itemguid = request.getParameter("itemGuid");
		String type = request.getParameter("type");
		int limit = Integer.parseInt(request.getParameter("limit"));
		
		OrderManager manager = new OrderManager();
		manager.setApproveitemguid(itemguid);
		manager.setType(type);
		manager.setLimit(limit);
		
		boolean res = orderService.saveManage(manager);
		
		Map<String,Object> maptemp=new HashMap<String, Object>();

		if(!res) {
			maptemp.put("id", "0");
			maptemp.put("message", "保存预约配置信息失败！");
		}
		return maptemp;
	}
	
	@RiseLog(operateName = "网上预约受理",operateType = "查看")
	@RequestMapping(value = "/changeSlstate", method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> changeSlstate(String orderGuid) {
		boolean res = orderService.saveSlstate(orderGuid)==1;
		
		Map<String,Object> maptemp=new HashMap<String, Object>();

		if(res) {
			maptemp.put("msg", "true");
		}else {
			maptemp.put("msg", "false");
		}
		return maptemp;
	}
	
	@RiseLog(operateName = "修改预约状态",operateType = "修改")
	@RequestMapping(value = "/changePermission", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> changePermission(HttpServletRequest request) {
		String itemguid = request.getParameter("itemguid");
		String orderpermission = request.getParameter("orderpermission");
		Map<String,Object> maptemp=new HashMap<String, Object>();
		boolean res = orderService.changePermission(itemguid, orderpermission);
		if(res) {
			maptemp.put("message", "保存成功！");
		}else {
			maptemp.put("message", "保存失败！");
		}
		
		return maptemp;
	}
	
	@RiseLog(operateName = "获取预约配置列表",operateType = "查看")
	@RequestMapping(value = "/searchManage", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> searchManage(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		listmap = orderService.searchManageByGuid(request.getParameter("itemGuid"));
		map.put("rows", listmap);		
		return map;
	}

}

