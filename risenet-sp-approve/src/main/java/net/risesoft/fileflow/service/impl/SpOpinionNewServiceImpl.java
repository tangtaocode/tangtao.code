package net.risesoft.fileflow.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import sun.misc.BASE64Decoder;
import net.risesoft.approve.entity.jpa.OfficeSpiDeclareinfo;
import net.risesoft.approve.service.OfficeSpiDeclareinfoService;
import net.risesoft.common.util.GuidUtil;
import net.risesoft.common.util.SysVariables;
import net.risesoft.fileflow.entity.jpa.OpinionNew;
import net.risesoft.fileflow.repository.jpa.OpinionNewRepository;
import net.risesoft.fileflow.service.SpOpinionNewService;
import net.risesoft.model.OrgUnit;
import net.risesoft.model.Person;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.RisesoftUtil;

import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szca.caau.webservice.SZCASafeService;
import com.szca.caau.webservice.SZCASafeServiceService;

@Service(value = "spOpinionNewService")
public class SpOpinionNewServiceImpl implements SpOpinionNewService {

	@Autowired
	private OpinionNewRepository opinionRepository;

	@Autowired
	protected TaskService taskService;

	@Autowired
	protected HistoryService historyService;

	@Resource(name = "officeSpiDeclareinfoService")
	private OfficeSpiDeclareinfoService officeSpiDeclareinfoService;

	@Override
	public OpinionNew saveOrUpdate(OpinionNew entity) {
		Person person = ThreadLocalHolder.getPerson();
		String tenantId = ThreadLocalHolder.getTenantId();
		String departmentId = person.getParentID();
		OrgUnit orgUnit = RisesoftUtil.getOrgUnitManager().getOrgUnit(departmentId);
		String userName = person.getName();
		String id = entity.getId();
		Integer type = entity.getOpinionType();
		if (StringUtils.isBlank(id)) {
			OpinionNew o = new OpinionNew();
			o.setId(GuidUtil.genGuid());
			o.setUserId(person.getID());
			o.setUserName(userName);
			o.setDepartmentId(departmentId);
			o.setDepartmentName(orgUnit.getName());
			String agentUserId = entity.getAgentUserId();
			if (StringUtils.isNotBlank(agentUserId)) {
				String agentUserDeptId = entity.getAgentUserDeptId();
				OrgUnit ou = RisesoftUtil.getOrgUnitManager().getOrgUnit(agentUserDeptId);
				Person po = RisesoftUtil.getPersonManager().getPerson(tenantId, agentUserId);
				o.setAgentUserId(agentUserId);
				o.setAgentUserName(po.getName());
				o.setAgentUserDeptId(agentUserDeptId);
				o.setAgentUserDeptName(ou!=null?ou.getName():"");
				o.setRealityDate(entity.getRealityDate() != null ? entity.getRealityDate() : new Date());
				o.setIsAgent(1);
			} else {
				o.setRealityDate(entity.getRealityDate() != null ? entity.getRealityDate() : new Date());
			}
			o.setProcessSerialNumber(entity.getProcessSerialNumber());
			o.setProcessInstanceId(entity.getProcessInstanceId());
			o.setTaskId(entity.getTaskId());
			o.setCategory(entity.getCategory());
			if (type == 2) {
				o.setSignatureName(userName);
			}
			o.setOpinionType(entity.getOpinionType());
			o.setTenantId(tenantId);
			o.setContent(entity.getContent());
			o.setCreateDate(new Date());
			o.setModifyDate(new Date());
			o.setGuids(entity.getGuids());
			//保存签名信息
			o.setCertTxt(entity.getCertTxt());
			o.setSignTxt(entity.getSignTxt());
			o.setDataTxt(entity.getDataTxt());
			Integer tabIndex = opinionRepository.getMaxTabIndex(entity.getProcessSerialNumber());
			o.setTabIndex(tabIndex==null?1:tabIndex+1);
			opinionRepository.save(o);
			return o;
		}
		OpinionNew opinion = opinionRepository.findOne(id);
		opinion.setUserId(person.getID());
		opinion.setUserName(userName);
		opinion.setDepartmentId(departmentId);
		opinion.setDepartmentName(orgUnit.getName());
		String agentUserId = entity.getAgentUserId();
		if (StringUtils.isNotBlank(agentUserId)) {
			String agentUserDeptId = entity.getAgentUserDeptId();
			OrgUnit ou = RisesoftUtil.getOrgUnitManager().getOrgUnit( agentUserDeptId);
			Person po = RisesoftUtil.getPersonManager().getPerson(tenantId, agentUserId);
			opinion.setAgentUserId(agentUserId);
			opinion.setAgentUserName(po.getName());
			opinion.setAgentUserDeptId(agentUserDeptId);
			opinion.setAgentUserDeptName(ou.getName());
			opinion.setIsAgent(1);
			opinion.setRealityDate(entity.getRealityDate());
		} else {
			opinion.setRealityDate(entity.getRealityDate() != null ? entity.getRealityDate() : new Date());
		}
		opinion.setModifyDate(new Date());
		// signatureName=signatureName.contains(SysVariables.COMMA)?signatureName+SysVariables.COMMA+person.getName():person.getName();
		String signatureName = opinion.getSignatureName();
		if (type == 2) {
			if (signatureName.contains(SysVariables.COMMA)) {
				if (!signatureName.endsWith(userName))
					signatureName = signatureName + SysVariables.COMMA + person.getName();
			} else {
				if (!signatureName.equals(userName))
					signatureName = signatureName + SysVariables.COMMA + person.getName();
			}
		}
		opinion.setSignatureName(signatureName);
		opinion.setContent(entity.getContent());
		//保存签名信息
		opinion.setCertTxt(entity.getCertTxt());
		opinion.setSignTxt(entity.getSignTxt());
		opinion.setDataTxt(entity.getDataTxt());
		Integer tabIndex = opinionRepository.getMaxTabIndex(entity.getProcessSerialNumber());
		opinion.setTabIndex(tabIndex==null?1:tabIndex+1);
		opinionRepository.save(opinion);
		return opinion;
	}


	@Override
	public List<Map<String, Object>> personCommentList(String SPinstanceId,String processSerialNumber, String processInstanceId, String taskId, String itembox, String activitiUser, Integer mainAndSub, String category, String readOnly) {
		List<Map<String, Object>> ret_list = new ArrayList<Map<String, Object>>();
		Map<String, Object> addableMap = new HashMap<String, Object>();
		addableMap.put("addable", true);
		addableMap.put("addAgent", true);
		addableMap.put("category", category);
		boolean msg = (boolean) this.getSpmApproveitem(SPinstanceId).get("msg");//判定是否需要意见验签
		String opinionCheck = "";//意见验签结果
		List<OpinionNew> list = new ArrayList<OpinionNew>();
		if (mainAndSub == 0) {
			list = opinionRepository.findByPSNAndCGAndOT(processSerialNumber, category, 1);
		} else if (mainAndSub == 1) {
			// 对于父流程，子流程
			String subProcessSerialNumbers = "";
			if ((itembox.equalsIgnoreCase("todo") || itembox.equalsIgnoreCase("add")) && StringUtils.isNotBlank(taskId)) {
				subProcessSerialNumbers = (String) taskService.getVariable(taskId, "subProcessSerialNumbers");
			} else if ((itembox.equalsIgnoreCase("done")||itembox.equalsIgnoreCase("history")||itembox.equalsIgnoreCase("bqbz")||itembox.equalsIgnoreCase("doing")|| itembox.equalsIgnoreCase("pause")) && StringUtils.isNotBlank(taskId)) {
				HistoricProcessInstance hi = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).includeProcessVariables().singleResult();
				Map<String, Object> map2 = hi.getProcessVariables();
				subProcessSerialNumbers = (String) map2.get("subProcessSerialNumbers");
			}
			if (StringUtils.isNotBlank(subProcessSerialNumbers)) {
				String subPSNStr[] = subProcessSerialNumbers.split(SysVariables.COMMA);
				List<String> subPSNList = new ArrayList<String>();
				for (String s : subPSNStr) {
					subPSNList.add(s);
				}
				list = opinionRepository.findByPSNSAndCGandOT(subPSNList, category, 1);
			}
		} else if (mainAndSub == 2) {
			String parentProcessSerialNumber = "";
			if (itembox.equalsIgnoreCase("todo") || itembox.equalsIgnoreCase("add")||itembox.equalsIgnoreCase("bqbz")) {
				parentProcessSerialNumber = (String) taskService.getVariable(taskId, "parentProcessSerialNumber");
			} else {
				HistoricProcessInstance hi = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).includeProcessVariables().singleResult();
				Map<String, Object> map2 = hi.getProcessVariables();
				parentProcessSerialNumber = (String) map2.get("parentProcessSerialNumber");
			}
			if (StringUtils.isNotBlank(parentProcessSerialNumber)) {
				list = opinionRepository.findByPSNAndCGAndOT(parentProcessSerialNumber, category, 1);
			}
		}

		if (itembox.equalsIgnoreCase("todo") || itembox.equalsIgnoreCase("add")||itembox.equalsIgnoreCase("bqbz")) {
			// 用户未签收前打开公文时(办理人为空)，只读所有意见
			if (StringUtils.isBlank(activitiUser)) {
				addableMap.put("addable", false);
				if (list.size() > 0) {
					for (OpinionNew opinion : list) {
						if(msg){//当前事项是无纸化事项时，需要意见验签
							opinionCheck = this.opinionCheck(opinion.getCertTxt(), opinion.getSignTxt(), opinion.getDataTxt());
						}
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("opinionCheck", opinionCheck);
						map.put("opinion", opinion);
						map.put("date", new SimpleDateFormat("yyyy-MM-dd").format(opinion.getRealityDate()));
						map.put("editable", false);
						ret_list.add(map);
					}
				}
				addableMap.put("addAgent", false);
				ret_list.add(addableMap);
				return ret_list;
			}

			String[] currentUserIdAnddeptId = activitiUser.split(SysVariables.COLON);
			String currentUserId = currentUserIdAnddeptId[0];
			if (readOnly.equalsIgnoreCase("YES")) {
				addableMap.put("addable", false);
				addableMap.put("addAgent", false);
				if (list.size() > 0) {
					for (OpinionNew opinion : list) {
						if(msg){//当前事项是无纸化事项时，需要意见验签
							opinionCheck = this.opinionCheck(opinion.getCertTxt(), opinion.getSignTxt(), opinion.getDataTxt());
						}						
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("opinionCheck", opinionCheck);
						map.put("opinion", opinion);
						map.put("date", new SimpleDateFormat("yyyy-MM-dd").format(opinion.getRealityDate()));
						map.put("editable", false);
						ret_list.add(map);
					}
				}
			} else if (readOnly.equalsIgnoreCase("NO")) {
				if (list.size() > 0) {
					for (OpinionNew opinion : list) {
						if(msg){//当前事项是无纸化事项时，需要意见验签
							opinionCheck = this.opinionCheck(opinion.getCertTxt(), opinion.getSignTxt(), opinion.getDataTxt());
						}						
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("opinionCheck", opinionCheck);
						map.put("opinion", opinion);
						map.put("date", new SimpleDateFormat("yyyy-MM-dd").format(opinion.getRealityDate()));
						map.put("editable", false);
						if (opinion.getIsAgent() == 0) {
							if (StringUtils.isBlank(taskId)) {
								map.put("editable", true);
								addableMap.put("addable", false);
							} else {
								if (taskId.equals(opinion.getTaskId())) {
									if (currentUserId.equals(opinion.getUserId())) {
										map.put("editable", true);
										addableMap.put("addable", false);
									}
								}
							}
						} else {
							if (StringUtils.isBlank(taskId)) {
								map.put("editable", true);
							} else {
								if (taskId.equals(opinion.getTaskId())) {
									if (currentUserId.equals(opinion.getUserId())) {
										map.put("editable", true);
									}
								}
							}
						}
						ret_list.add(map);
					}
				}
			}
		} else if (itembox.equalsIgnoreCase("done") ||itembox.equalsIgnoreCase("history")|| itembox.equalsIgnoreCase("doing")|| itembox.equalsIgnoreCase("pause")||itembox.equalsIgnoreCase("teBieChengXu")) {
			addableMap.put("addable", false);
			addableMap.put("addAgent", false);
			if (list.size() > 0) {
				for (OpinionNew opinion : list) {
					if(msg){//当前事项是无纸化事项时，需要意见验签
						opinionCheck = this.opinionCheck(opinion.getCertTxt(), opinion.getSignTxt(), opinion.getDataTxt());
					}					
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("opinionCheck", opinionCheck);
					map.put("opinion", opinion);
					map.put("date", new SimpleDateFormat("yyyy-MM-dd").format(opinion.getRealityDate()));
					map.put("editable", false);
					ret_list.add(map);
				}
			}
		}
		ret_list.add(addableMap);
		return ret_list;
	}

	@Override
	public Integer getCount4Personal(String processSerialNumber, String taskId, String category, String userId) {
		return opinionRepository.getCount4Personal(processSerialNumber, taskId, category, userId);
	}
	
	@Override
	public Integer getCount4Personal(String processSerialNumber,String category, String userId) {
		return opinionRepository.getCount4Personal(processSerialNumber,category, userId);
	}

	@Override
	public OpinionNew findOne(String id) {
		return opinionRepository.findOne(id);
	}


	@SuppressWarnings("restriction")
	@Override
	public boolean opinionSign(String CertTxt, String SignTxt, String DataTxt) {
		boolean opinionSign = false;
		try{
			BASE64Decoder decoder = new BASE64Decoder();
			SZCASafeServiceService ss = new SZCASafeServiceService();
			SZCASafeService service = ss.getSZCASafeServicePort();
			//验证签名（用公钥验证）
			String result = service.szcaWSSignatureValidatePkcs7String(SignTxt);
			String decoderResult = new String(decoder.decodeBuffer(result));
			if(decoderResult.equals("1")) {
				//验证证书 .1 证书有效, -1 证书无效，不是所信任的根 -2 证书无效，超过有效期 -3 证书无效，已加入黑名单
				String cert64 = service.szcaWSCertificateValidateString(CertTxt);
				if(cert64.equals("E405")) {
					System.out.print("#######################根证书或吊销列表为空#######################");
					return opinionSign;
				}else if(cert64.equals("E404")){
					System.out.print("#######################参数为空#######################");
					return opinionSign;
				}else if(cert64.equals("E403")){
					System.out.print("#######################验签失败#######################");
					return opinionSign;
				}else{
					String cert = new String(decoder.decodeBuffer(cert64)); //转码
					String[] arrResult=cert.split(",");
					if(arrResult[0].equals("1")){
						opinionSign = true;//签名成功
					} else if(arrResult[0].equals("-1")) {
						System.out.print("#######################证书无效，不是所信任的根#######################");
						return opinionSign;
					} else if(arrResult[0].equals("-2")) {
						System.out.println("#######################证书无效，超过有效期#######################");
						return opinionSign;
					} else if(arrResult[0].equals("-3")) {
						System.out.println("#######################证书无效，已加入黑名单#######################");
						return opinionSign;
					}
				}
			} else {
				System.out.println("#######################验证失败，非法的签名#######################");
				return opinionSign;
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("#######################异常："+e.getMessage());
			return opinionSign;
		}
		return opinionSign;
	}

	@SuppressWarnings("restriction")
	@Override
	public String opinionCheck(String CertTxt, String SignTxt, String DataTxt) {
		String opinionCheck = "false";
		try{
			BASE64Decoder decoder = new BASE64Decoder();
			SZCASafeServiceService ss = new SZCASafeServiceService();
			SZCASafeService service = ss.getSZCASafeServicePort();
			if(!("".equals(SignTxt))&&SignTxt!=null){
				//验证签名（用公钥验证）
				String result = service.szcaWSSignatureValidatePkcs7String(SignTxt);
				String decoderResult = new String(decoder.decodeBuffer(result));
				if(decoderResult.equals("1")) {
					opinionCheck = "true";//意见验签成功
				} else {
					return opinionCheck;
				}
			}else{
				opinionCheck = "noSign";//意见未签名
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return opinionCheck;
	}


	@Override
	public Map<String, Object> getSpmApproveitem(String sPinstanceId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("msg", false);
		String[] approveitemguids = {"{0A150158-FFFF-FFFF-D2BB-773BFFFFFFAB}","{0A150158-FFFF-FFFF-D2CB-2C24FFFFFFB7}","{0A0A017E-FFFF-FFFF-93A2-96A400000012}","{09A16D4E-0000-0000-7499-9B4F0000074A}","{09A16D4E-FFFF-FFFF-A9C0-79E1000000DA}","{09A16D48-FFFF-FFFF-AB06-0939FFFFFFB5}","{09A16D4E-FFFF-FFFF-C4FF-992800000A7F}","{0A0A017E-FFFF-FFFF-93FB-66AF00000266}","{09A16D48-0000-0000-0E1E-087000000280}","{09A16D4E-FFFF-FFFF-C3EE-E33F00000A5B}","{09A16D4E-FFFF-FFFF-C3E3-580200000A4F}"};
		OfficeSpiDeclareinfo officeSpiDeclareinfo = officeSpiDeclareinfoService.findByGuid(sPinstanceId);
		String approveitemguid = "";
		if(officeSpiDeclareinfo!=null){
			approveitemguid = officeSpiDeclareinfo.getApproveitemguid();
		}
		for (String approveitemGuid : approveitemguids) {
			if(approveitemGuid.equals(approveitemguid)){
				map.put("msg", true);
				break;
			}
		}
		return map;
	}

}
