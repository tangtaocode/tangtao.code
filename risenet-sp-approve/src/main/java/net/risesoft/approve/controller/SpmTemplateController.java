package net.risesoft.approve.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.risesoft.approve.entity.jpa.SpmApproveitem;
import net.risesoft.approve.entity.jpa.SpmTemplate;
import net.risesoft.approve.service.SpmApproveItemService;
import net.risesoft.approve.service.SpmTemplateService;
import net.risesoft.util.RisesoftCommonUtil;
import net.risesoft.utilx.StringX;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * SpmTemplateController  模板配置controller
 * @author htc
 *
 */
@Controller
@RequestMapping(value = "/templateConfig")
public class SpmTemplateController {
	protected Logger log = LoggerFactory.getLogger(SpmBureauController.class);

	@Autowired
	public SpmTemplateService spmTemplateService;

	@Autowired
	public SpmApproveItemService approveItemService;

	@RequestMapping(value = "/index")
	public String taoHongTemplateList(Model model) {
		return "templateConfig/index";
	}

	@ResponseBody
	@RequestMapping(value = "/getList")
	public Map<String,Object> getList(Model model) {
		List<SpmTemplate> list = spmTemplateService.findAll();
		Map<String, Object> ret_map = new HashMap<String, Object>();
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		for(int i = 0;i<list.size();i++){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("templateGuid", list.get(i).getTemplateGuid());
			map.put("certificateName", list.get(i).getCertificateName());
			map.put("templateName", list.get(i).getTemplateName());
			map.put("approveItemName", list.get(i).getApproveItemName());
			map.put("templateType", list.get(i).getTemplateType());
			items.add(map);
		}
		ret_map.put("rows", items);
		return ret_map;
	}

	@RequestMapping(value = "/newOrModify")
	public String newOrModify(String id, Model model) {
		try {
			List<SpmApproveitem> items = approveItemService.findAll();
			List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
			for (SpmApproveitem i : items) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("approveitemGuid", i.getApproveitemguid());
				map.put("approveitemName", i.getApproveitemname());
				listMap.add(map);
			}
			model.addAttribute("approveItemList", listMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<Map<String, String>> typeList = new ArrayList<Map<String, String>>();
		Map<String, String> type1 = new HashMap<String, String>();
		type1.put("typeID", "1");
		type1.put("typeName", "打印办结单");
		Map<String, String> type2 = new HashMap<String, String>();
		type2.put("typeID", "2");
		type2.put("typeName", "打印受理单");
		Map<String, String> type3 = new HashMap<String, String>();
		type3.put("typeID", "3");
		type3.put("typeName", "文件");
		typeList.add(type1);
		typeList.add(type2);
		typeList.add(type3);
		model.addAttribute("typeList", typeList);

		if (StringUtils.isNotEmpty(id)) {
			SpmTemplate template = spmTemplateService.findOne(id);
			model.addAttribute("template", template);
		}
		return "templateConfig/newOrModify";
	}

	@ResponseBody
	@RequestMapping(value = "/saveOrUpdate")
	public SpmTemplate saveOrUpdate(@Valid SpmTemplate t,MultipartFile attachmentFile) {
		if (!attachmentFile.isEmpty() && attachmentFile != null) {
			try {
				String filePath = RisesoftCommonUtil.templateURL + (StringX.isBlank(t.getApproveItemName())?"打印":t.getApproveItemName()) + "/" + attachmentFile.getOriginalFilename();
				if (!attachmentFile.isEmpty()) {
					File targetFile = new File(filePath);
					if (!targetFile.exists()) {
						targetFile.mkdirs();
					}
					attachmentFile.transferTo(targetFile);
				}
				t.setFilePath(filePath);//文件路径
				t.setUploadDate(new Date());
				t.setTemplateName(attachmentFile.getOriginalFilename());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return spmTemplateService.saveOrUpdate(t);
	}
	
	@ResponseBody
	@RequestMapping(value = "/removeSpmTemplate")
	public String delete(String[] ids) {
		try {
			for (int i = 0; i < ids.length; i++) {
				spmTemplateService.delete(ids[i]);
			}
			return "1";
		} catch (Exception e) {
		}
		return "0";
	}

	@RequestMapping(value = "/download")
	public void download(String templateGuid, HttpServletResponse response) throws Exception {
		try {
			SpmTemplate spmTemplate = spmTemplateService.findOne(templateGuid);
			if(spmTemplate!=null && !StringX.isBlank(spmTemplate.getFilePath())){
				InputStream fis = new BufferedInputStream(new FileInputStream(spmTemplate.getFilePath()));
				byte[] b = new byte[fis.available()];
				fis.read(b);
				fis.close();
				response.reset();
				response.setContentType("application/octet-stream");
				response.setHeader("Content-disposition", "attachment; filename=" + java.net.URLEncoder.encode(spmTemplate.getTemplateName(), "UTF-8"));
				response.setHeader("Content-Length", String.valueOf(b.length));
				IOUtils.write(b, response.getOutputStream());
				response.flushBuffer();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
