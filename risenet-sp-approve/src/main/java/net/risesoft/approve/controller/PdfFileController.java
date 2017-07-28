package net.risesoft.approve.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.risesoft.approve.entity.jpa.OfficeSpiDeclareinfo;
import net.risesoft.approve.entity.jpa.PdfFile;
import net.risesoft.approve.repository.jpa.PdfFileRepository;
import net.risesoft.approve.service.OfficeSpiDeclareinfoService;
import net.risesoft.approve.service.PdfFileService;
import net.risesoft.fileflow.entity.jpa.OpinionNew;
import net.risesoft.fileflow.service.OpinionNewService;
import net.risesoft.model.Person;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.FileToolUtil;
import net.risesoft.util.FileUtil;
import net.risesoft.util.RisesoftCommonUtil;
import net.risesoft.utilx.DateX;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
@RequestMapping("/sp/pdfFile")
public class PdfFileController {

	private static final Logger logger = LoggerFactory
			.getLogger(PdfFileController.class);

	@Autowired
	private OpinionNewService opinionNewService;

	@Autowired
	private PdfFileRepository pdfFileRepository;

	@Autowired
	private OfficeSpiDeclareinfoService officeSpiDeclareinfoService;

	@Autowired
	private PdfFileService pdfFileService;
	/**
	 * 打印办理单
	 * 
	 * @param SPinstanceId
	 * @param processInstanceId
	 * @param processSerialNumber
	 * @param taskId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/banliDanForm")
	public String banliDanForm(@RequestParam String SPinstanceId,
			@RequestParam String processInstanceId,
			@RequestParam String processSerialNumber,
			@RequestParam String taskId, Model model) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Person person = ThreadLocalHolder.getPerson();
			Map<String, Object> map = new HashMap<String, Object>();
			String declaresn = "";// 业务流水号
			if (!StringUtils.isBlank(processInstanceId)) {
				map = officeSpiDeclareinfoService
						.getBanliDanForm(processInstanceId);
				declaresn = (String) map.get("DECLARESN");
				if (map.get("DECLAREDATETIME") != null) {
					String DECLAREDATETIME = sdf.format(map
							.get("DECLAREDATETIME"));
					map.put("DECLAREDATETIME", DECLAREDATETIME);
				}

				if (map.get("CHENGNUORIQI") != null) {
					String CHENGNUORIQI = sdf.format(map.get("CHENGNUORIQI"));
					map.put("CHENGNUORIQI", CHENGNUORIQI);
				}

				List<OpinionNew> opinionList = opinionNewService
						.findByProcessSerialNumber(processSerialNumber);
				String opinions = "";
				for (int i = 0; i < opinionList.size(); i++) {
					int num = i + 1;
					opinions = opinions + num + "、"
							+ opinionList.get(i).getContent() + "——"
							+ opinionList.get(i).getUserName() + " "
							+ sdf.format(opinionList.get(i).getRealityDate())
							+ "    ";
				}
				map.put("opinions", opinions);
			}
			map.put("ADVICETIME", DateX.getStandardDateText(new Date()));
			map.put("WORKNAME", person.getName());
			map.put("workTel",
					person.getOfficePhone() == null ? "无" : person
							.getOfficePhone());

			JSONObject json = JSONObject.fromObject(map);
			int isExist = pdfFileService.isWordOrPDF(SPinstanceId,RisesoftCommonUtil.print_shouliDan);
			if(isExist>0){
				model.addAttribute("openWordOrPDF","pdf");
			}
			model.addAttribute("map", json);
			model.addAttribute("SPinstanceId", SPinstanceId);
			model.addAttribute("processInstanceId", processInstanceId);
			model.addAttribute("processSerialNumber", processSerialNumber);
			model.addAttribute("declaresn", declaresn);// 业务流水号
			model.addAttribute("taskId", taskId);
			model.addAttribute("printType", "shouLiDan");// 打印类型
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "document/adviceOfficeForm";
	}

	/**
	 * 打印办结单
	 * 
	 * @param SPinstanceId
	 * @param processInstanceId
	 * @param processSerialNumber
	 * @param taskId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/banJieDanForm")
	public String banJieDanForm(@RequestParam String SPinstanceId,
			@RequestParam String processInstanceId,
			@RequestParam String processSerialNumber,
			@RequestParam String taskId, Model model) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Person person = ThreadLocalHolder.getPerson();
			Map<String, Object> map = new HashMap<String, Object>();
			String declaresn = "";// 业务流水号
			if (!StringUtils.isBlank(processInstanceId)) {
				OfficeSpiDeclareinfo osd = officeSpiDeclareinfoService
						.findByProcessInstanceId(processInstanceId);
				map = officeSpiDeclareinfoService.getBanJieDanForm(osd
						.getGuid());
				if (map == null) {
					map = new HashMap<String, Object>();
				}
				declaresn = (String) map.get("DECLARESN");
				map.put("EMPLOYEEDEPTNAME", osd.getApproveItemName());
				if (map.get("DOCWAY") != null && map.get("DOCWAY") != "") {
					map.put("Way", "文件");
				} else {
					map.put("Way", "证照");
				}
				if (map.get("SENDDATE") != null) {
					String SENDDATE = sdf.format(map.get("SENDDATE"));
					map.put("SENDDATE", SENDDATE);
				}
				if (map.get("ENDDATE") != null) {
					String ENDDATE = sdf.format(map.get("ENDDATE"));
					map.put("ENDDATE", ENDDATE);
				}
				List<OpinionNew> opinionList = opinionNewService
						.findByProcessSerialNumber(processSerialNumber);
				String opinions = "";
				for (int i = 0; i < opinionList.size(); i++) {
					int num = i + 1;
					opinions = opinions + num + "、"
							+ opinionList.get(i).getContent() + "——"
							+ opinionList.get(i).getUserName() + " "
							+ sdf.format(opinionList.get(i).getRealityDate())
							+ "    ";
				}
				map.put("opinions", opinions);
			}
			map.put("ADVICETIME", DateX.getStandardDateText(new Date()));
			map.put("WORKNAME", person.getName());
			map.put("workTel",
					person.getOfficePhone() == null ? "无" : person
							.getOfficePhone());
			
			JSONObject json = JSONObject.fromObject(map);
			int isExist = pdfFileService.isWordOrPDF(SPinstanceId, RisesoftCommonUtil.print_banjieDan);
			if(isExist>0){
				model.addAttribute("openWordOrPDF","pdf");
			}
			model.addAttribute("map", json);
			model.addAttribute("SPinstanceId", SPinstanceId);
			model.addAttribute("processInstanceId", processInstanceId);
			model.addAttribute("processSerialNumber", processSerialNumber);
			model.addAttribute("declaresn", declaresn);// 业务流水号
			model.addAttribute("taskId", taskId);
			model.addAttribute("printType", "banJieDan");// 打印类型
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "document/adviceOfficeForm";
	}
	
	/**
	 * 打印证照
	 * @param SPinstanceId
	 * @param processInstanceId
	 * @param processSerialNumber
	 * @param taskId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/banJieZZ")
	public String banJieZZ(@RequestParam String SPinstanceId,
			@RequestParam String processInstanceId,
			@RequestParam String processSerialNumber,
			@RequestParam String taskId, Model model) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Person person = ThreadLocalHolder.getPerson();
			Map<String, Object> map = new HashMap<String, Object>();
			String declaresn = "";// 业务流水号
			OfficeSpiDeclareinfo osd=null;
			if (!StringUtils.isBlank(processInstanceId)) {
				 osd = officeSpiDeclareinfoService
						.findByProcessInstanceId(processInstanceId);
				 declaresn=osd.getDeclaresn();
				map = pdfFileService.getZZTemplate(osd.getApproveitemguid());
				if (map == null) {
					return "exception";
				}
				
			}
			int isExist = pdfFileService.isWordOrPDF(SPinstanceId, RisesoftCommonUtil.print_zhengZhao);
			if(isExist>0){
				model.addAttribute("openWordOrPDF","pdf");
				String filepath=pdfFileService.getWord(SPinstanceId, RisesoftCommonUtil.print_zhengZhao);
				model.addAttribute("filePath", filepath);
			}else{//查询word
				String filePath = map.get("filepath").toString();
				model.addAttribute("filePath", filePath);
				String exeSql=map.get("exesql").toString();
				try {
					map=pdfFileService.findMarks(exeSql, osd.getSblsh());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			JSONObject json = JSONObject.fromObject(map);
			
			model.addAttribute("map", json);
			model.addAttribute("SPinstanceId", SPinstanceId);
			model.addAttribute("processInstanceId", processInstanceId);
			model.addAttribute("processSerialNumber", processSerialNumber);
			model.addAttribute("declaresn", declaresn);// 业务流水号
			model.addAttribute("taskId", taskId);
			model.addAttribute("printType",RisesoftCommonUtil.print_zhengZhao);// 打印类型
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "document/adviceOfficeForm";
	} 

	/**
	 * 转pdf并保存
	 * 
	 * @param SPinstanceId
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveAsPDFFile")
	public void saveAsPDFFile(
			@RequestParam(required = false) String SPinstanceId,
			@RequestParam(required = false) String printType,
			@RequestParam(required = false) String declaresn,
			HttpServletRequest request, HttpServletResponse response) {
		logger.debug("call /sp/document/saveAsPDFFile");
		response.setContentType("text/html; charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		try {
			String sblsh = officeSpiDeclareinfoService.findByGuid(SPinstanceId)
					.getDeclaresn();
			//String basePath = FileUtil.getBasePath(printType);
			
			String fileName = SPinstanceId+RisesoftCommonUtil.print_pdf;
			String filePath = RisesoftCommonUtil.printURL+SPinstanceId+"\\" +printType+"\\pdf\\0\\"+SPinstanceId+RisesoftCommonUtil.print_pdf;
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = multipartRequest
					.getFile("currentDoc");

			if (!multipartFile.isEmpty()) {
				File targetFile = new File(filePath);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				multipartFile.transferTo(targetFile);
				byte[] targetByte = FileToolUtil.File2byte(filePath);
				pdfFileService.savePdfFile(targetByte,SPinstanceId, filePath, fileName,printType, "", "");
				logger.info("word转pdf上传成功");
			}
		} catch (Exception e) {
			logger.error("word转pdf上传失败");
			e.printStackTrace();
		}
	}

	

	

	

	/**
	 * 打开PDF页面
	 * 
	 * @param SPinstanceId
	 * @param processInstanceId
	 * @param taskId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/openPDF")
	public String openPDF(@RequestParam String SPinstanceId,
			@RequestParam String processInstanceId,
			@RequestParam String taskId,
			@RequestParam(required = false) String printType,
			@RequestParam(required = false) String processSerialNumber,
			@RequestParam(required = false) String declaresn, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		List<PdfFile> pdfFileList = pdfFileRepository
				.findByInstanceGuidAndFileType(SPinstanceId, printType);
		String filePath = "";
		if (pdfFileList != null && pdfFileList.size() > 0) {
			filePath = pdfFileList.get(0).getFilePath();
		}
		model.addAttribute("filePath",filePath);
		model.addAttribute("SPinstanceId", SPinstanceId);
		model.addAttribute("processInstanceId", processInstanceId);
		model.addAttribute("taskId", taskId);
		model.addAttribute("declaresn", declaresn);
		model.addAttribute("processSerialNumber", processSerialNumber);
		model.addAttribute("printType", printType);
		return "document/webOffice4PDF";
	}

	

	/**
	 * 撤销pdf
	 * 
	 * @param processSerialNumber
	 * @param SPinstanceId
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/undoPDF")
	public Map<String, Object> undoPDF(
			@RequestParam String processSerialNumber,
			@RequestParam String SPinstanceId, @RequestParam String printType,
			Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			boolean i = pdfFileService.deletePDF(processSerialNumber,
					SPinstanceId, printType);
			if (i) {
				map.put("success", true);
			} else {
				map.put("success", false);
			}
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}
		return map;
	}

}