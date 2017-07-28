package net.risesoft.approve.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.risesoft.approve.entity.base.Pager;
import net.risesoft.approve.service.MatterAuditRoleService;
import net.risesoft.approve.service.SpmBureauService;
import net.risesoft.model.OrgUnit;
import net.risesoft.tenant.annotation.RiseLog;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.RisesoftUtil;
import net.risesoft.utilx.StringX;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/auditRole")
public class MatterAuditRoleController {

	@Autowired
	private MatterAuditRoleService matterAuditRoleService;
	
	@Autowired
	private SpmBureauService spmBureauService;
	
	/**
	 * 审核角色绑定
	 */
	@RiseLog(operateName = "进入审核角色绑定页面",operateType = "查看")
	@RequestMapping(value = "/showRole")
	public String showRole() {
		Map<String,Object> model = new HashMap<String, Object>();
		OrgUnit org = RisesoftUtil.getPersonManager().getBureau(ThreadLocalHolder.getTenantId(), ThreadLocalHolder.getPerson().getID());
		model.put("orgName",org.getName());
		model.put("userName",ThreadLocalHolder.getPerson().getName());
		return "matterAuditRole/showRole";
	}
	
	/**
	 * 获取审核批准角色列表
	 */
	@RiseLog(operateName = "获取审核批准角色列表",operateType = "查看")
	@ResponseBody
	@RequestMapping(value = "/getRoleList")
	public Map<String, Object> getRoleList(HttpServletRequest request,String approveItemGuid) {
		Map<String, Object> map = new HashMap<>();
		String pageNo = request.getParameter("pager.pageNo");
		String pageSize = request.getParameter("pager.pageSize");
		if (StringX.isBlank(pageSize) || StringX.isBlank(pageNo)) {
			pageSize = "30";
			pageNo = "1";
		}
		Pager pager = new Pager(Integer.parseInt(pageNo),Integer.parseInt(pageSize));
		map = matterAuditRoleService.getRoleList(approveItemGuid,pager);
		return map;
	}
	
	/**
	 * 保存审核角色绑定
	 */
	@RiseLog(operateName = "保存审核角色",operateType = "增加")
	@ResponseBody
	@RequestMapping(value = "/saveRole")
	public Map<String, Object> saveRole(String personGuids,String personNames,String approveItemGuids) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			matterAuditRoleService.saveRole(personGuids,personNames,approveItemGuids);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 删除审核角色绑定
	 */
	@RiseLog(operateName = "删除审核角色",operateType = "删除")
	@ResponseBody
	@RequestMapping(value = "/deleteRole")
	public Map<String, Object> deleteRole(String personGuids,String approveItemGuid) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			matterAuditRoleService.deleteRole(personGuids,approveItemGuid);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 打开事项列表
	 */
	@RiseLog(operateName = "打开事项列表",operateType = "查看")
	@RequestMapping(value = "/showApproveItem")
	public String showApproveItem() {
		return "matterAuditRole/showApproveItem";
	}
	
	/**
	 * 获取委办局事项列表
	 */
	@RiseLog(operateName = "获取委办局事项列表",operateType = "查看")
	@ResponseBody
	@RequestMapping(value = "/getApproveItem")
	public Map<String, Object> getApproveItem(HttpServletRequest request) {
		Map<String,Object> map=new HashMap<String,Object>();
		String pageNo = request.getParameter("pager.pageNo");
		String pageSize = request.getParameter("pager.pageSize");
		OrgUnit org = RisesoftUtil.getPersonManager().getBureau(ThreadLocalHolder.getTenantId(), ThreadLocalHolder.getPerson().getID());
		try{
			if(StringX.isBlank(pageSize) || StringX.isBlank(pageNo)){
				pageSize="30";
				pageNo = "1";
			}
			Pager pager = new Pager(Integer.parseInt(pageNo),Integer.parseInt(pageSize));
			pager = spmBureauService.findAll("",org.getID(),"","",pager);
			List<Map<String,Object>> spmList =  pager.getPageList();
			map.put("pager.pageNo", pager.getPageNo());
			map.put("pageSize", pager.getPageSize());
			map.put("pager.totalRows", pager.getTotalRows());
			map.put("rows", spmList);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return map;
	}
	
}
