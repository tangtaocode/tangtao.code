package net.risesoft.fileflow.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import net.risesoft.fileflow.entity.jpa.TaoHongTemplateType;
import net.risesoft.fileflow.service.TaoHongTemplateTypeService;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.RisesoftCommonUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/taoHongTemplateType")
public class TaoHongTemplateTypeController {

	@Resource(name="taoHongTemplateTypeService")
	private TaoHongTemplateTypeService taoHongTemplateTypeService;
	
	@RequestMapping(value="/managerTaoHongTemplateType")
	public String managerTaoHongTemplateType(Model model){
		List<TaoHongTemplateType> list=taoHongTemplateTypeService.findByTenantId();
		model.addAttribute("typeList", list);
		return "taohong/managerTaoHongTemplateType";
	}
	
	@RequestMapping(value="/newOrModify")
	public String newOrModify(String id,Model model){
		if(StringUtils.isNotEmpty(id)){
			TaoHongTemplateType t=taoHongTemplateTypeService.findOne(id);
			model.addAttribute("taoHongTemplateType", t);
		}
		return "taohong/type/newOrModify";
	}
	
	@ResponseBody
	@RequestMapping(value="/saveOrUpdate")
	public TaoHongTemplateType saveOrUpdate(@Valid TaoHongTemplateType t){
		return taoHongTemplateTypeService.saveOrUpdate(t);
	}
	
	
	@ResponseBody
	@RequestMapping(value="/removeTaoHongTemplateType")
	public void removeTaoHongTemplateType(String[] ids){
		taoHongTemplateTypeService.removeTaoHongTemplateType(ids);
	}
	
	@ResponseBody
	@RequestMapping(value="/saveOrder")
	public void saveOrder(String[] idAndTabIndexs){
		taoHongTemplateTypeService.saveOrder(idAndTabIndexs);
	}
	
}