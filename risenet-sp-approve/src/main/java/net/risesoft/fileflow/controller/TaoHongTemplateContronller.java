package net.risesoft.fileflow.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.risesoft.fileflow.entity.jpa.TaoHongTemplate;
import net.risesoft.fileflow.entity.jpa.TaoHongTemplateType;
import net.risesoft.fileflow.service.TaoHongTemplateService;
import net.risesoft.fileflow.service.TaoHongTemplateTypeService;
import net.risesoft.model.OrgType;
import net.risesoft.model.OrgUnit;
import net.risesoft.model.Person;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.RisesoftUtil;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/taoHongTemplate")
public class TaoHongTemplateContronller {

	@Resource(name = "taoHongTemplateService")
	private TaoHongTemplateService taoHongTemplateService;
	
	@Resource(name="taoHongTemplateTypeService")
	private TaoHongTemplateTypeService taoHongTemplateTypeService;

	@RequestMapping(value = "/show")
	public String taoHongTemplateList(Model model) {
		try {
			List<TaoHongTemplate> list = taoHongTemplateService.findByTenantId("");
			model.addAttribute("taoHongTemplateList", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "taohong/taoHongTemplateList";
	}

	@ResponseBody
	@RequestMapping(value = "/getList")
	public Map<String,Object> getList(Model model) {
		List<TaoHongTemplate> list = taoHongTemplateService.findByTenantId(ThreadLocalHolder.getTenantId());
		Map<String, Object> ret_map = new HashMap<String, Object>();
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		for(int i = 0;i<list.size();i++){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("template_guid", list.get(i).getTemplate_guid());
			map.put("template_fileName", list.get(i).getTemplate_fileName());
			map.put("bureau_name", list.get(i).getBureau_name());
			map.put("template_type", list.get(i).getTemplate_type());
			map.put("tabIndex", list.get(i).getTabIndex());
			items.add(map);
		}
		ret_map.put("rows", items);
		return ret_map;
	}
	
	@RequestMapping(value = "/newOrModify")
	public String newOrModify(String id, Model model) {
		Person person = ThreadLocalHolder.getPerson();
		try {
			
			OrgUnit org = RisesoftUtil.getOrganizationManager().getOrganization(person.getOrganization().getID());
			List<OrgUnit> orgUnitList = RisesoftUtil.getOrgUnitManager().getSubTree(org.getID(), OrgType.TREE_TYPE_BUREAU);
			List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
			for (OrgUnit o : orgUnitList) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("bureau_guid", o.getID());
				map.put("bureau_name", o.getName());
				listMap.add(map);
			}
			model.addAttribute("bureauList", listMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<TaoHongTemplateType> typeList=taoHongTemplateTypeService.findByTenantId();
		model.addAttribute("typeList", typeList);
		
		if (StringUtils.isNotEmpty(id)) {
			TaoHongTemplate taoHongTemplate = taoHongTemplateService.findOne(id);
			model.addAttribute("taoHongTemplate", taoHongTemplate);
		}
		return "taohong/newOrModify";
	}

	@ResponseBody
	@RequestMapping(value = "/saveOrUpdate")
	public TaoHongTemplate saveOrUpdate(@Valid TaoHongTemplate t,MultipartFile attachmentFile) {
		if (!attachmentFile.isEmpty() && attachmentFile != null) {
			try {
				t.setTemplate_content(attachmentFile.getBytes());
				t.setTemplate_fileName(attachmentFile.getOriginalFilename());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return taoHongTemplateService.saveOrUpdate(t);
	}

	@ResponseBody
	@RequestMapping(value = "/removeTaoHongTemplate")
	public void removeTaoHongTemplate(String[] ids) {
		taoHongTemplateService.removeTaoHongTemplate(ids);
	}
	
	@RequestMapping(value = "/download")
	public void download(String template_guid, HttpServletResponse response) throws Exception {
		try {
			TaoHongTemplate taoHongTemplate = taoHongTemplateService.findOne(template_guid);
			byte[] b = taoHongTemplate.getTemplate_content();
			int length = b.length;
			response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", "attachment; filename=" + java.net.URLEncoder.encode(taoHongTemplate.getTemplate_fileName(), "UTF-8"));
			response.setHeader("Content-Length", String.valueOf(length));
			IOUtils.write(b, response.getOutputStream());
			response.flushBuffer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
