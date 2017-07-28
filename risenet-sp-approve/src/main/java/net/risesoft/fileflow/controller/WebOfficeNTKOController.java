package net.risesoft.fileflow.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.risesoft.common.util.SysVariables;
import net.risesoft.fileflow.entity.jpa.FileDocument;
import net.risesoft.fileflow.entity.jpa.HistoryFileDocument;
import net.risesoft.fileflow.entity.jpa.TaoHongTemplate;
import net.risesoft.fileflow.repository.jpa.FileDocumentRepository;
import net.risesoft.fileflow.repository.jpa.HistoryFileDocumentRepository;
import net.risesoft.fileflow.service.FileDocumentService;
import net.risesoft.fileflow.service.HistoryFileDocumentService;
import net.risesoft.fileflow.service.TaoHongTemplateService;
import net.risesoft.fileflow.service.WorkflowTaskService;
import net.risesoft.model.Person;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.RisesoftUtil;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
@RequestMapping("/ntko")
public class WebOfficeNTKOController {
	private static final Logger logger = LoggerFactory.getLogger(WebOfficeNTKOController.class);

	@Autowired
	private FileDocumentRepository fileDocumentRepository;

	@Autowired
	private FileDocumentService fileDocumentService;

	@Autowired
	private TaoHongTemplateService taoHongTemplateService;

	@Autowired
	private HistoryFileDocumentRepository historyFileDocumentRepository;

	@Autowired
	private HistoryFileDocumentService historyFileDocumentService;

	@Autowired
	private WorkflowTaskService workflowTaskService;
	
	@Autowired
	private TaskService taskService;

	/*
	 * permission:套红，转pdf控制 YES可见 NO不可见 wordReadOnly：word是否只读 YES只读 NO可编辑
	 */
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public String showList(HttpServletRequest request, @RequestParam String processSerialNumber, @RequestParam String taskId, @RequestParam(required=false) String activitiUser, @RequestParam String itembox, Model model) {
		logger.debug("call /ntko/display");
		String permission = "";
		String wordReadOnly = "";

		if (StringUtils.isNotBlank(taskId)) {
			Task task = workflowTaskService.getTaskByTaskId(taskId);
			String taskDefKey = task.getTaskDefinitionKey();
			if (taskDefKey.equalsIgnoreCase("clerk")) {// // 只有收发员才有套红，撤销套红等权限
				permission = "YES";
			}
		} else {
			permission = "NO";
		}

		String currentUserId = ThreadLocalHolder.getPerson().getID();
		if (currentUserId.equals("{BFA800D5-FFFF-FFFF-A3E1-28BD000002B0}")) {
			permission = "YES";
		} else {
			permission = "NO";
		}

		if (itembox.equalsIgnoreCase("add") || itembox.equalsIgnoreCase("todo")) {
			wordReadOnly = "NO";
		} else if (itembox.equalsIgnoreCase("done") || itembox.equalsIgnoreCase("doing")) {
			wordReadOnly = "YES";
		}

		Person person = ThreadLocalHolder.getPerson();
		String fileDocumentId = "";
		String openWordOrPDF = "";
		int istaohong = 0;
		String saveDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		List<FileDocument> list = new ArrayList<FileDocument>();
		if (StringUtils.isNotBlank(processSerialNumber)) {
			if (fileDocumentRepository.findBySNAndIstaohong(processSerialNumber, 2).size() == 1) {
				openWordOrPDF = "openPDF";
				istaohong = 2;
			} else if (fileDocumentRepository.findBySNAndIstaohong(processSerialNumber, 1).size() == 1) {
				openWordOrPDF = "openTaoHongWord";
				istaohong = 1;
			} else {
				openWordOrPDF = "openWord";
				istaohong = 0;
			}
		} else {
			logger.error("processSerialNumber为空" + processSerialNumber);
		}
		list = fileDocumentRepository.findBySNAndIstaohong(processSerialNumber, istaohong);
		if (list.size() > 0) {
			FileDocument d = list.get(0);
			fileDocumentId = d.getId();
			saveDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d.getSaveDate());
		}
		model.addAttribute("processSerialNumber", processSerialNumber);
		model.addAttribute("fileDocumentId", fileDocumentId);
		model.addAttribute("userName", person.getName());
		model.addAttribute("saveDate", saveDate);
		model.addAttribute("openWordOrPDF", openWordOrPDF);
		model.addAttribute("permission", permission);
		model.addAttribute("wordReadOnly", wordReadOnly);
		model.addAttribute("itembox", itembox);

		return "tags/webOfficeNTKO";
	}

	@ResponseBody
	@RequestMapping(value = "/saveAsPDFFile")
	public void saveAsPDFFile(@RequestParam(required = false) String processSerialNumber, @RequestParam(required = false) String processInstanceId, @RequestParam(required = false) String taskId, @RequestParam(required = false) int istaohong, @RequestParam(required = false) String documenttitle, @RequestParam(required = false) String fileType, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		logger.debug("call /ntko/saveAsPDFFile");
		response.setContentType("text/html; charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		Person person = ThreadLocalHolder.getPerson();
		String userId = person.getID();
		String tenantId = ThreadLocalHolder.getTenantId();
		byte[] content = null;
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = multipartRequest.getFile("currentDoc");
			if (!multipartFile.isEmpty()) {
				content = multipartFile.getBytes();
				fileDocumentService.saveFileDocument(tenantId, userId, new Date(), content, processInstanceId, processSerialNumber, documenttitle, taskId, istaohong, fileType);
			} else {
				logger.error("word转pdf上传失败");
			}
			logger.info("word转pdf上传成功");
		} catch (Exception e) {
			logger.error("word转pdf上传失败");
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/saveDocument")
	@ResponseBody
	public void saveDocument(@RequestParam(required = false) String processSerialNumber, @RequestParam(required = false) String processInstanceId, @RequestParam(required = false) String taskId, @RequestParam(required = false) int istaohong, @RequestParam(required = false) String documenttitle, @RequestParam(required = false) String fileType, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		response.setContentType("text/html; charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		
		//documenttitle传进来乱码，这里先从流程变量里面获取，前提是流程已启动
		if(StringUtils.isNotBlank(taskId)){
			Map<String,Object> var=taskService.getVariables(taskId);
			documenttitle=(String) var.get(SysVariables.DOCUMENTTITLE);
		}
		Person person = ThreadLocalHolder.getPerson();
		String userId = person.getID();
		String tenantId = ThreadLocalHolder.getTenantId();
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer();
		byte[] content = null;
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = multipartRequest.getFile("currentDoc");
		if (!multipartFile.isEmpty()) {
			content = multipartFile.getBytes();
			try {
				if (istaohong == 2) {
					List<FileDocument> list = fileDocumentRepository.findBySN(processSerialNumber);
					if (list.size() == 2) {
						List<FileDocument> list2 = fileDocumentRepository.findBySNAndIstaohong(processSerialNumber, 1);
						fileDocumentService.updateFileDocumentByID(tenantId, content, taskId, new Date(), userId, list2.get(0).getId());
						/*
						 * 更新历史正文表中的正文信息
						 */
						List<HistoryFileDocument> HFDlist;
						if (StringUtils.isNotBlank(taskId)) {
							HFDlist = historyFileDocumentRepository.getByPSNAndTaskIdAndIsTaoHong(processSerialNumber, taskId, 1);
						} else {// 流程刚启动的时候taskId为空
							HFDlist = historyFileDocumentRepository.getByPSNAndIsTaoHong(processSerialNumber, 1);
						}

						if (HFDlist.size() == 0) {
							historyFileDocumentService.saveHistoryFileDocument(tenantId, userId, new Date(), content, processInstanceId, processSerialNumber, documenttitle, taskId, 1, fileType);
						} else {
							historyFileDocumentService.updateHistoryFileDocumentByID(content, new Date(), HFDlist.get(0).getId());
						}
					} else if (list.size() == 1) {
						List<FileDocument> list2 = fileDocumentRepository.findBySNAndIstaohong(processSerialNumber, 0);
						fileDocumentService.updateFileDocumentByID(tenantId, content, taskId, new Date(), userId, list2.get(0).getId());
						/*
						 * 更新历史正文表中的正文信息
						 */
						List<HistoryFileDocument> HFDlist;
						if (StringUtils.isNotBlank(taskId)) {
							HFDlist = historyFileDocumentRepository.getByPSNAndTaskIdAndIsTaoHong(processSerialNumber, taskId, 0);
						} else {
							HFDlist = historyFileDocumentRepository.getByPSNAndIsTaoHong(processSerialNumber, 0);
						}
						if (HFDlist.size() == 0) {
							historyFileDocumentService.saveHistoryFileDocument(tenantId, userId, new Date(), content, processInstanceId, processSerialNumber, documenttitle, taskId, 0, fileType);
						} else {
							historyFileDocumentService.updateHistoryFileDocumentByID(content, new Date(), HFDlist.get(0).getId());
						}

					} else {
						fileDocumentService.saveFileDocument(tenantId, userId, new Date(), content, processInstanceId, processSerialNumber, documenttitle, taskId, 0, fileType);
						/*
						 * 添加正文信息到历史正文表
						 */
						historyFileDocumentService.saveHistoryFileDocument(tenantId, userId, new Date(), content, processInstanceId, processSerialNumber, documenttitle, taskId, 0, fileType);
					}
				} else if (istaohong == 1) {
					List<FileDocument> list = fileDocumentRepository.findBySNAndDeleted(processSerialNumber, 1);
					if (list.size() == 1) {
						fileDocumentService.updateFileDocumentByID(tenantId, content, taskId, new Date(), userId, list.get(0).getId());
						logger.info("套红word模版更新成功");
						/*
						 * 更新历史正文表中的正文信息
						 */
						List<HistoryFileDocument> HFDlist;
						if (StringUtils.isNotBlank(taskId)) {
							HFDlist = historyFileDocumentRepository.getByPSNAndTaskIdAndIsTaoHong(processSerialNumber, taskId, 1);
						} else {
							HFDlist = historyFileDocumentRepository.getByPSNAndIsTaoHong(processSerialNumber, 1);
						}
						if (HFDlist.size() == 0) {
							historyFileDocumentService.saveHistoryFileDocument(tenantId, userId, new Date(), content, processInstanceId, processSerialNumber, documenttitle, taskId, 1, fileType);
						} else {
							historyFileDocumentService.updateHistoryFileDocumentByID(content, new Date(), HFDlist.get(0).getId());
						}
					} else {
						fileDocumentService.saveFileDocument(tenantId, userId, new Date(), content, processInstanceId, processSerialNumber, documenttitle, taskId, 1, fileType);
						logger.info("套红word上传成功");
						/*
						 * 保存正文信息到历史表
						 */
						historyFileDocumentService.saveHistoryFileDocument(tenantId, userId, new Date(), content, processInstanceId, processSerialNumber, documenttitle, taskId, 1, fileType);
						logger.info("套红word保存到历史表中成功");
					}
				} else if (istaohong == 0) {
					fileDocumentService.saveFileDocument(tenantId, userId, new Date(), content, processInstanceId, processSerialNumber, documenttitle, taskId, istaohong, fileType);
					logger.info("正文word上传成功");
					/*
					 * 保存正文信息到历史表
					 */
					List<HistoryFileDocument> HFDlist;
					if (StringUtils.isNotBlank(taskId)) {
						HFDlist = historyFileDocumentRepository.getByPSNAndTaskIdAndIsTaoHong(processSerialNumber, taskId, 0);
					} else {
						HFDlist = historyFileDocumentRepository.getByPSNAndIsTaoHong(processSerialNumber, 0);
					}
					if (HFDlist.size() == 1) {
						historyFileDocumentService.updateHistoryFileDocumentByID(content, new Date(), HFDlist.get(0).getId());
						logger.info("正文word信息更新到历史表成功");
					} else {
						historyFileDocumentService.saveHistoryFileDocument(tenantId, userId, new Date(), content, processInstanceId, processSerialNumber, documenttitle, taskId, 0, fileType);
						logger.info("正文word信息保存到历史表成功");
					}
				}
				sb.append("success:true");
			} catch (Exception e) {
				sb.append("success:false");
				logger.error("上传word失败");
				e.printStackTrace();
			}
		} else {
			sb.append("success:false");
			logger.error("上传word失败");
		}
		out.print(new String(sb));
	}

	@RequestMapping(value = "/openDocument")
	@ResponseBody
	public void openDocument(@RequestParam(required = false) String processSerialNumber, @RequestParam(required = false) int openFlag, HttpServletRequest request, HttpServletResponse response) {
		byte[] buf = null;
		int istaohong = 0;
		if (openFlag == 0) {
			istaohong = 0;
		} else if (openFlag == 1) {
			istaohong = 1;
		} else if (openFlag == 2) {// 撤销套红
			List<FileDocument> list = fileDocumentRepository.findBySNAndIstaohong(processSerialNumber, 1);
			fileDocumentRepository.delete(list.get(0).getId());
			istaohong = 0;
		} else if (openFlag == 3) {// 撤销pdf
			List<FileDocument> list = fileDocumentRepository.findBySNAndIstaohong(processSerialNumber, 2);
			fileDocumentRepository.delete(list.get(0).getId());
			List<FileDocument> list2 = fileDocumentRepository.findBySN(processSerialNumber);
			if (list2.size() == 2) {
				istaohong = 1;
			} else {
				istaohong = 0;
			}
		}
		List<FileDocument> list = fileDocumentRepository.findBySNAndIstaohong(processSerialNumber, istaohong);
		FileDocument fileDocument;
		if (!list.isEmpty()) {
			fileDocument = list.get(0);
			buf = fileDocument.getContent();
			if (buf != null) {
				response.reset();
				response.setHeader("Content-Type", "application/msword");
				response.setHeader("Content-Length", String.valueOf(buf.length));
				ServletOutputStream sos;
				try {
					sos = response.getOutputStream();
					sos.write(buf);
				} catch (IOException e) {
					logger.error("向jsp页面输出word二进制流错误");
					e.printStackTrace();
				} finally {

				}
			}

		} else {
			logger.error("数据库没有ProcessSerialNumber=" + processSerialNumber + "的正文，请联系管理员");
		}
	}

	@RequestMapping(value = "/openTaohongTemplate")
	@ResponseBody
	public void openDocumentTemplate(@RequestParam(required = false) String templateGUID, HttpServletRequest request, HttpServletResponse response) {
		logger.debug("call /ntko/openTaohongTemplate");
		byte[] buf = null;

		TaoHongTemplate taohongTemplate = taoHongTemplateService.findOne(templateGUID);

		if (null != taohongTemplate) {
			buf = taohongTemplate.getTemplate_content();
			if (buf != null) {
				response.setHeader("Content-Type", "application/msword");
				response.setHeader("Content-Length", String.valueOf(buf.length));
				try {
					ServletOutputStream sos = response.getOutputStream();
					sos.write(buf);
					sos.flush();
					sos.close();
				} catch (IOException e) {
					logger.error("向jsp页面输出word套红模版二进制流错误");
					e.printStackTrace();
				}
			}
		} else {
			logger.error("数据库没有templateGUID=" + templateGUID + "的模版，请联系管理员");
		}
	}

	@RequestMapping(value = "/loadPDF")
	public void loadPDF(@RequestParam String processSerialNumber, HttpServletRequest request, HttpServletResponse response) {
		logger.debug("call /ntko/loadPDF");
		byte[] buf = null;
		List<FileDocument> list = fileDocumentRepository.findByProcessSerialNumberAndFileType(processSerialNumber, ".pdf");
		FileDocument fileDocument;
		if (!list.isEmpty()) {
			fileDocument = list.get(0);
			buf = fileDocument.getContent();
			if (buf != null) {
				response.reset();
				response.setHeader("Content-Type", "application/pdf");
				response.setHeader("Content-Disposition", "filename=\"" + processSerialNumber + "\"");
				try {
					ServletOutputStream sos = response.getOutputStream();
					sos.write(buf);
					sos.flush();
					sos.close();
				} catch (IOException e) {
					logger.error("向jsp页面输出pdf二进制流错误");
					e.printStackTrace();
				}
			}

		} else {
			logger.error("数据库没有processSerialNumber=" + processSerialNumber + "的pdf文件，请联系管理员");
		}
	}

	@RequestMapping(value = "/openTaoHong")
	public String openTaoHong(@RequestParam String activitiUser, Model model) {
		logger.debug("call /ntko/openTaoHong");
		String[] currentUserIdAnddeptId = activitiUser.split(SysVariables.COLON);
		String currentDepartmentId = currentUserIdAnddeptId[1];
		// 当前人员的委办局GUID
		String currentBureauGuid = RisesoftUtil.getDepartmentManager().getDepartment(currentDepartmentId).getID();
		/*
		 * 当前科室不属于任何一个委办局，则根据科室的部门ID来查找本科室的套红模版
		 */
		if (StringUtils.isBlank(currentBureauGuid)) {
			currentBureauGuid = currentDepartmentId;
		}
		model.addAttribute("currentBureauGuid", currentBureauGuid);
		return "tags/taohongNTKO";
	}

	@RequestMapping(value = "/list")
	@ResponseBody
	public List<Map<String, Object>> taoHongTemplateList(@RequestParam String currentBureauGuid, Model model) {
		logger.debug("call /ntko/list");
		List<Map<String, Object>> ret_list = new ArrayList<Map<String, Object>>();

		List<TaoHongTemplate> list = taoHongTemplateService.findByBureau_guid(currentBureauGuid);

		if (list.isEmpty()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("hasTaoHongTemplate", "0");
			ret_list.add(map);
		} else {
			for (TaoHongTemplate taoHongTemplate : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("hasDocumentTemplate", "1");
				map.put("template_guid", taoHongTemplate.getTemplate_guid());
				map.put("template_fileName", taoHongTemplate.getTemplate_fileName());
				map.put("template_type", taoHongTemplate.getTemplate_type());
				ret_list.add(map);
			}
		}
		return ret_list;
	}

	@RequestMapping(value = "/forwardReadPDF")
	public String forwardReadPDF(@RequestParam String processSerialNumber, @RequestParam String processInstanceId, @RequestParam String taskId,@RequestParam String itembox,Model model) {
		model.addAttribute("processSerialNumber", processSerialNumber);
		model.addAttribute("processInstanceId", processInstanceId);
		model.addAttribute("taskId", taskId);
		model.addAttribute("itembox", itembox);
		return "tags/webOffice4PDF";
	}
	
	@ResponseBody
	@RequestMapping(value = "/undoPDF")
	public Map<String,Object> undoPDF(@RequestParam String processSerialNumber,Model model) {
		Map<String,Object> map=new HashMap<String, Object>();
		try {
			Integer i=fileDocumentService.deletePDFByProcessSerialNumber(processSerialNumber);
			if(i==1){
				map.put("success",true);
			}else{
				map.put("success",false);
			}
		} catch (Exception e) {
			map.put("success",false);
			e.printStackTrace();
		}
		return map;
	}
}
