package net.risesoft.fileflow.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import net.risesoft.fileflow.service.SpFormDataService;
import net.risesoft.approve.entity.jpa.OfficeSpiDeclareinfo;
import net.risesoft.approve.entity.jpa.SpmApproveitem;
import net.risesoft.approve.entity.jpa.SpmBureau;
import net.risesoft.approve.service.OfficeSpiDeclareinfoService;
import net.risesoft.approve.service.SpmApproveItemService;
import net.risesoft.approve.service.SpmBureauService;
import net.risesoft.common.util.ContextUtil;
import net.risesoft.common.util.RiseBpmUtil;
import net.risesoft.util.SysVariables;
import net.risesoft.fileflow.entity.jpa.FormDef;
import net.risesoft.fileflow.service.FormDefService;
import net.risesoft.model.Person;
import net.risesoft.tenant.pojo.ThreadLocalHolder;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

@Controller
@RequestMapping(value = "sp/form/run")
public class SpFormRunController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private FormDefService formDefService;

	@Resource(name = "spFormDataService")
	private SpFormDataService spFormDataService;

	@Resource(name = "officeSpiDeclareinfoService")
	private OfficeSpiDeclareinfoService officeSpiDeclareinfoService;
	@Resource(name="spmApproveItemService")
	private SpmApproveItemService spmApproveItemService;
	@Resource(name="spmBureauService")
	private SpmBureauService spmBureauService;

	/**
	 * 获取表单html
	 * 
	 * @param formIds
	 * @param itembox
	 * @param activitiUser
	 * @param processSerialNumber
	 * @param processInstanceId
	 * @param taskId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/formContent", method = RequestMethod.GET)
	public List<String> formContent(@RequestParam String formIds, @RequestParam String itembox, @RequestParam String activitiUser, @RequestParam String processSerialNumber, @RequestParam String processInstanceId, @RequestParam String taskId) {
		List<String> htmlContentsList = new ArrayList<String>();

		StringTemplateLoader stringLoader = new StringTemplateLoader();
		FreeMarkerConfigurer freeMarkerConfigurer = ContextUtil.getBean(FreeMarkerConfigurer.class);
		Configuration configuration = freeMarkerConfigurer.getConfiguration();
		// configuration.setSharedVariable(name, value);
		configuration.setTemplateLoader(stringLoader);
		configuration.setTagSyntax(Configuration.SQUARE_BRACKET_TAG_SYNTAX);
		configuration.setDefaultEncoding("utf-8");
		configuration.setEncoding(configuration.getLocale(), "utf-8");
		configuration.setOutputEncoding("utf-8");

		String webroot = ContextUtil.getWebRootRealPath();
		File file = new File(webroot, "/WEB-INF/ftl/riseComment.ftl");
		try {
			String s = FileUtils.readFileToString(file, "UTF-8");
			stringLoader.putTemplate(file.getName(), s);
			configuration.addAutoInclude(file.getName());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		file = new File(webroot, "/WEB-INF/ftl/riseSequence.ftl");
		try {
			String s = FileUtils.readFileToString(file, "UTF-8");
			stringLoader.putTemplate(file.getName(), s);
			configuration.addAutoInclude(file.getName());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		file = new File(webroot, "/WEB-INF/ftl/riseDepartment.ftl");
		try {
			String s = FileUtils.readFileToString(file, "UTF-8");
			stringLoader.putTemplate(file.getName(), s);
			configuration.addAutoInclude(file.getName());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		file = new File(webroot, "/WEB-INF/ftl/riseDate.ftl");
		try {
			String s = FileUtils.readFileToString(file, "UTF-8");
			stringLoader.putTemplate(file.getName(), s);
			configuration.addAutoInclude(file.getName());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		file = new File(webroot, "/WEB-INF/ftl/riseProperties.ftl");
		try {
			String s = FileUtils.readFileToString(file, "UTF-8");
			stringLoader.putTemplate(file.getName(), s);
			configuration.addAutoInclude(file.getName());
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		if (StringUtils.isNotBlank(formIds)) {
			List<String> formIdList = RiseBpmUtil.stringToList(formIds, SysVariables.COMMA);
			for (String formId : formIdList) {
				FormDef formDef = formDefService.findOne(formId);
				if (formDef != null) {
					try {
						stringLoader.putTemplate(formDef.getId(), formDef.getHtmlContent());
						Template template = configuration.getTemplate(formDef.getId(), "utf-8");

						ModelMap model = new ModelMap();
						model.addAttribute("processSerialNumber", processSerialNumber);
						model.addAttribute("itembox", itembox);
						model.addAttribute("processInstanceId", processInstanceId);
						model.addAttribute("taskId", taskId);
						model.addAttribute(SysVariables.ACTIVITIUSER, activitiUser);
						model.addAttribute("formId", formId);
						model.addAttribute("ctx", ContextUtil.getContextPath());
						model.addAttribute("start", true);

						ByteArrayOutputStream ba = new ByteArrayOutputStream();
						OutputStreamWriter osw = new OutputStreamWriter(ba, "utf-8");
						template.process(model, osw);
						String baStr = ba.toString("utf-8");
						int index = baStr.indexOf("=");
						baStr = baStr.substring(0, index) + baStr.substring(index + 1);
						htmlContentsList.add(baStr);
						ba.close();
						osw.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return htmlContentsList;
	}

	/**
	 * 获取表单数据
	 * 
	 * @param formIds
	 * @param processInstanceId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getFormData", method = RequestMethod.GET)
	public List<Map<String, Object>> getFormData(String formIds, String processInstanceId) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			if (StringUtils.isNotBlank(formIds)) {
				List<String> tableNameList = formDefService.getTableNames(formIds);
				list = spFormDataService.getFormDatas(formIds, processInstanceId, tableNameList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 初始化表单数据
	 * 
	 * @param processDefinitionKey
	 * @param formIds
	 * @param activitiUser
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getInitFormData", method = RequestMethod.GET)
	public List<Map<String, Object>> getInitFormData(String SPinstanceId) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Person person = ThreadLocalHolder.getPerson();
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		OfficeSpiDeclareinfo osd = officeSpiDeclareinfoService.findByGuid(SPinstanceId);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("guid", osd.getGuid());// 审批guid
		map.put("approveitemguid", osd.getApproveitemguid());// 事项guid
		map.put("title", osd.getApproveItemName());// 事项name
		map.put("DeclareSN", osd.getDeclaresn());// 原系统业务流水号
		map.put("sblsh", osd.getSblsh());// 申报流水号
		map.put("ZHENGJIANDAIMA", osd.getZhengjiandaima());// 证件代码
		map.put("DeclarerPerson", osd.getDeclarerPerson()==null?"":osd.getDeclarerPerson());// 申请人
		map.put("DECLARERTEL", osd.getDeclarerTel()==null?"":osd.getDeclarerTel());// 申请人 联系电话
		map.put("DeclarerMobile", osd.getDeclarerMobile()==null?"":osd.getDeclarerMobile());// 申请人 联系手机
		map.put("chengwenriqi", osd.getDeclareDateTime() == null ? sdf.format(new Date()) : sdf.format(osd.getDeclareDateTime()));// 申请人申请日期
		map.put("EmployeeDeptName", person.getName());// 经办人
		
		SpmApproveitem spmApproveitem=spmApproveItemService.findByApproveitemguid(osd.getApproveitemguid());
		JdbcTemplate jdbcTemplate=new JdbcTemplate(this.routerDataSource);
		String sql="select bureauname from spm_bureau where bureauguid=?";
		String bureauName=jdbcTemplate.queryForObject(sql, String.class, spmApproveitem.getBureauguid());
		/*List<Map<String,Object>> lists=spmBureauService.loadDepartMent("","");
		for (Map<String, Object> map2 : lists) {
			if (map2.containsValue(spmApproveitem.getBureauguid())) {
				bureauName=(String)map2.get(spmApproveitem.getBureauguid());
			}
		}*/
		map.put("BureauName", bureauName);
		map.put("LimiTime", spmApproveitem.getTimelimit());
		items.add(map);
		return items;
	}

	@Resource(name = "routerDataSource")
	private DataSource routerDataSource;
	/**
	 * 保存表单数据
	 * 
	 * @param processInstanceId
	 * @param formIds
	 * @param formNames
	 * @param formJsonData
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveFormData")
	public Map<String, Object> saveFormData(String processInstanceId, String formIds, String formNames, String formJsonData) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", false);
		map.put("formNames", formNames);
		try {
			if (StringUtils.isNotBlank(formIds)) {
				List<String> formIdList = RiseBpmUtil.stringToList(formIds, SysVariables.COMMA);
				for (String formId : formIdList) {
					spFormDataService.doSaveFormData(processInstanceId, formId, formJsonData);
				}
			}
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			logger.error("保存表单数据出错！！！");
			e.printStackTrace();
		}
		return map;
	}

}
