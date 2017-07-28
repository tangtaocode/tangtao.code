package net.risesoft.approve.service.impl.senate;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.risesoft.api.org.GroupManager;
import net.risesoft.approve.entity.base.Pager;
import net.risesoft.approve.entity.jpa.FtSupervise;
import net.risesoft.approve.entity.jpa.OfficeSpiDeclareinfo;
import net.risesoft.approve.entity.jpa.Senate;
import net.risesoft.approve.entity.jpa.SmsConfig;
import net.risesoft.approve.entity.jpa.SpmApproveitem;
import net.risesoft.approve.entity.senate.ItemsSenateReportVO;
import net.risesoft.approve.entity.senate.PersonnalSenateReportVO;
import net.risesoft.approve.entity.senate.SenateDetailReportVO;
import net.risesoft.approve.entity.senate.SenateReportVO;
import net.risesoft.approve.entity.senate.WindowsSenateReportVO;
import net.risesoft.approve.repository.jpa.SmsConfigRepository;
import net.risesoft.approve.repository.jpa.supervise.SenateRepository;
import net.risesoft.approve.service.OfficeSpiDeclareinfoService;
import net.risesoft.approve.service.certificate.CertificateService;
import net.risesoft.approve.service.senate.SenateService;
import net.risesoft.approve.service.supervise.FTSuperviseService;
import net.risesoft.commons.util.GUID;
import net.risesoft.fileflow.service.ProcessDataService;
import net.risesoft.fileflow.service.ReminderDefineService;
import net.risesoft.fileflow.service.WorkflowProcessDefinitionService;
import net.risesoft.fileflow.service.WorkflowTaskService;
import net.risesoft.model.Department;
import net.risesoft.model.Group;
import net.risesoft.model.OrgUnit;
import net.risesoft.model.Person;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.ReportUtil;
import net.risesoft.util.RisesoftUtil;
import net.risesoft.util.SendSms;
import net.risesoft.utilx.FTSuperviseUtil;
import net.risesoft.utilx.StringX;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * 审批环节数据接口实现类
 * 
 * @author Administrator
 *
 */
@Service(value = "senateService")
public class SenateServiceImpl implements SenateService {

	@Autowired
	private SenateRepository senateRepository;// 事项评议senateRepository

	@Autowired
	private SmsConfigRepository smsConfigRepository;// 短信配置smsConfigRepository

	@Resource(name = "reminderDefineService")
	private ReminderDefineService reminderDefineService;

	@Resource(name = "fTSuperviseService")
	private FTSuperviseService fTSuperviseService;

	@Autowired
	private WorkflowTaskService workflowTaskService;

	@Autowired
	private ProcessDataService processDataService;

	@Resource(name = "officeSpiDeclareinfoService")
	private OfficeSpiDeclareinfoService officeSpiDeclareinfoService;

	@Autowired
	private WorkflowProcessDefinitionService workflowProcessDefinitionService;

	@Autowired
	private CertificateService certificateService;

	@Resource(name = "routerDataSource")
	private DataSource routerDataSource;

	private JdbcTemplate jdbcTemplate;

	@PostConstruct
	private void afterIoc() {
		jdbcTemplate = new JdbcTemplate(this.routerDataSource);
	}

	@Override
	public void saveSenate(String processDefinitionId, String taskId,
			String processInstanceId, String routeToTaskId) {
		Senate senate = new Senate();
		String guid = new GUID().toString();
		senate.setSenateguid(guid);
		senate.setSatisfaction("no");
		senateRepository.save(senate);
	}

	@Override
	public String getOneInt(String fromPage, String processInstanceId) {
		List<Object> params = new ArrayList<Object>();
		String result = "";
		String sql = "select issenate from spm_senate where declaresn=? ";
		if ("kaishishouli".equalsIgnoreCase(fromPage)) {
			sql = "select issenate_sl from spm_senate where declaresn=? ";
		}
		String declareSN = officeSpiDeclareinfoService
				.getDeclaresnByProcessInstanceId(processInstanceId);
		params.add(declareSN);
		try {
			result = jdbcTemplate.queryForObject(sql.toString(),
					params.toArray(), String.class);
		} catch (DataAccessException e) {
			e.printStackTrace();
			result = "0";
		}
		return result;
	}

	@Override
	public String getOneString(String sql, List<Object> params) {
		String result = "";
		try {
			result = jdbcTemplate.queryForObject(sql.toString(),
					params.toArray(), String.class);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean insertSenate(String fromPage, String processInstanceId,
			HttpServletRequest request) throws Exception {
		String svalue = request.getParameter("svalue");
		String userMacAddr = request.getParameter("userMacAddr");
		Person person = ThreadLocalHolder.getPerson();
		OrgUnit org = RisesoftUtil.getPersonManager().getBureau(
				ThreadLocalHolder.getTenantId(), person.getID());
		FtSupervise ftSupervise = null;
		try {
			// ProcessDefinitionEntity processDefinitionEntity =
			// workflowProcessDefinitionService.findProcessDefinitionByPid(processInstanceId);
			String instanceguid = officeSpiDeclareinfoService
					.getGuidByProcessInstanceId(processInstanceId);
			// 获取公共信息
			ftSupervise = FTSuperviseUtil.getFTCode(jdbcTemplate, instanceguid);
			SpmApproveitem item = FTSuperviseUtil.getSpmFiled(ftSupervise
					.getYxtywlsh());

			OfficeSpiDeclareinfo officeSpiDeclareinfo = officeSpiDeclareinfoService
					.findByProcessInstanceId(processInstanceId);
			String declareSN = officeSpiDeclareinfoService
					.getDeclaresnByProcessInstanceId(processInstanceId);
			Senate senate = null;
			String guid = null;
			boolean isexist = false;
			String sql = "select senateguid from spm_senate where declaresn=? ";
			List<Object> params = new ArrayList<Object>();
			params.add(declareSN);
			guid = jdbcTemplate.queryForObject(sql.toString(),
					params.toArray(), String.class);
			if (guid == null || guid.equals("")) {
				guid = new GUID().toString();
				senate = new Senate();
				if ("zzlq".equalsIgnoreCase(fromPage)) {// 证照领取(办结)评价
					senate.setSatisfaction(svalue);
					senate.setSenatedatetime(new java.sql.Date(new Date()
							.getTime()));
					senate.setIssenate("1");
					senate.setUsermacaddr(userMacAddr);
				} else {// 受理评价
					senate.setSatisfaction_sl(svalue);
					senate.setSenatedatetime_sl(new java.sql.Date(new Date()
							.getTime()));
					senate.setIssenate_sl("1");
					senate.setUsermacaddr_sl(userMacAddr);
				}
				senate.setApproveitemguid(item.getApproveitemguid());
				senate.setDeclaresn(declareSN);
				senate.setDeclaredatetime(new java.sql.Date(new Date()
						.getTime()));
				senate.setBureauguid(person.getBureauId());
				senate.setWorkflowinstance_guid(processInstanceId);
				senate.setProcessinstanceid(processInstanceId);
				senate.setBureauname(org.getName());
				senate.setBureauguid(org.getID());
				senate.setSenateguid(guid);
				senate.setEmployeedeptname(person.getName());
				senate.setEmployeeguid(person.getID());

			} else {
				senate = senateRepository.findOne(guid);
				if ("kaishishouli".equalsIgnoreCase(fromPage)) {// 证照领取评价
					senate.setSatisfaction_sl(svalue);
					senate.setSenatedatetime_sl(new java.sql.Date(new Date()
							.getTime()));
					senate.setIssenate_sl("1");
					senate.setType_sl("1");
					senate.setUsermacaddr_sl(userMacAddr);
				} else {
					senate.setSatisfaction(svalue);
					senate.setSenatedatetime(new java.sql.Date(new Date()
							.getTime()));
					senate.setIssenate("1");
					senate.setType("1");
					senate.setUsermacaddr(userMacAddr);
				}
				senate.setApproveitemguid(item.getApproveitemguid());
				senate.setDeclaresn(declareSN);
				senate.setDeclaredatetime(new java.sql.Date(new Date()
						.getTime()));
				senate.setBureauguid(person.getBureauId());
				senate.setWorkflowinstance_guid(processInstanceId);
				senate.setProcessinstanceid(processInstanceId);
				senate.setBureauname(org.getName());
				senate.setBureauguid(org.getID());
				senate.setEmployeedeptname(person.getName());
				senate.setEmployeeguid(person.getID());
				isexist = true;
			}
			senateRepository.save(senate);
			// 不满意的评价给监察组发提示短信
			if (null != svalue && "3".equals(svalue)) {
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("zxCode", declareSN);
				paramMap.put("approveItemName", item.getApproveitemname());
				this.notSatSMS(routerDataSource, paramMap);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Pager findAllList(String approveItemName, Pager pager) {
		int pageNum = pager.getPageNo();
		int pageSize = pager.getPageSize();
		Person person = ThreadLocalHolder.getPerson();
		OrgUnit org = RisesoftUtil.getPersonManager().getBureau(
				ThreadLocalHolder.getTenantId(), person.getID());
		// 查询到当前用户在rc7中的id,
		String userID = person.getID();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {

			List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
			// RisenetEmployeeUnits
			// 为当前用户的信息（包括人员id,部门id,科室id）,通过rc7的id查询到该用户在新工程中的个人信息
			// RisenetEmployeeUnits risenetInfo =
			// ServiceUtil.riseInfo(jdbctemplate,userID);
			// 查询窗口收件列表
			String sql = "select distinct a.approveitemguid,a.APPROVEITEMTYPE,a.APPROVEITEMNAME,d.BUREAUNAME,a.BUREAUTYPE,a.TIMELIMIT "
					+ " from spm_approveitem a,xzql_xzsp_province p,spm_bureau d"
					+ " where  a.approveitemguid=p.itemid and a.adminbureauguid=d.bureauguid  and d.bureauguid= ? ";
			// 是否在本系统审批，是否与罗湖审批平台对接 两个条件 ：and p.abutment='1' and
			// p.approveplace='1'
			List<Object> params = new ArrayList<Object>();
			params.add(org.getID());
			if (!StringX.isBlank(approveItemName)) { // 事项名称
				sql += " and a.approveItemName like ?";
				params.add("%" + approveItemName + "%");
			}
			listmap = jdbcTemplate.queryForList(sql, params.toArray());
			pager.setTotalRows(listmap.size());
			list.addAll(listmap);
			pager.setPageList(jdbcTemplate.queryForList(
					pager.setPageSql(sql.toString(), pageNum, pageSize),
					params.toArray()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pager;
	}

	/**
	 * 保存审批服务评价
	 */
	@Override
	public boolean saveSpItemSenate(HttpServletRequest request) {
		boolean result = false;
		String declaresn = request.getParameter("declaresn");
		List<Object> params = new ArrayList<Object>();
		String sql = "";
		try {
			if (null != declaresn && !"".equalsIgnoreCase(declaresn)) {
				// 输入业务编号评价
				sql = "update spm_senate set issenate=1,SATISFACTION=?,SENATEDATETIME=sysdate where declaresn=? and type=?";
				params.add(request.getParameter("svalue"));
				params.add(request.getParameter("declaresn"));
				params.add(request.getParameter("type"));
				jdbcTemplate.update(sql, params.toArray());
			} else {
				// 没有输入业务编号评价
				String nextNum = null;
				String svalue = null == request.getParameter("svalue") ? ""
						: request.getParameter("svalue");
				String zxCode = null == request.getParameter("declaresn") ? ""
						: request.getParameter("declaresn");
				String type = null == request.getParameter("type") ? ""
						: request.getParameter("type");
				String userMacAddr = request.getParameter("userMacAddr");
				String approveItemName = null == request
						.getParameter("approveItemName") ? "" : request
						.getParameter("approveItemName");
				String approveitemguid = null == request
						.getParameter("spApproveItemId") ? "" : request
						.getParameter("spApproveItemId");
				Person person = ThreadLocalHolder.getPerson();
				OrgUnit org = RisesoftUtil.getPersonManager().getBureau(
						ThreadLocalHolder.getTenantId(), person.getID());
				if ("".equals(zxCode)) {
					params.clear();
					String dateStr = new SimpleDateFormat("yyyyMMdd").format(
							new Date()).substring(0, 4);
					params.add(dateStr);
					nextNum = this
							.getOneString(
									"select (case when nub < 10 then '00000' when nub < 100 then '0000' when nub < 1000 then '000' when nub < 10000 then '00' when nub < 100000 then '0' end) || nub from (select nvl(max(sa.serialnumber),0) + 1 nub from spi_year_serialnumber sa where sa.yearnumber = ?)",
									params);
					if ("2".equals(type)) {
						zxCode = "ZX" + dateStr + nextNum;
					} else {
						zxCode = "QT" + dateStr + nextNum;
					}
					params.clear();
					if ("000001".equals(nextNum)) {
						sql = "insert into spi_year_serialnumber(yearnumber,serialnumber) values(?,?)";
						params.add(dateStr);
						params.add(nextNum);
					} else {
						sql = "update spi_year_serialnumber set serialnumber = ? where yearnumber = ? ";
						params.add(nextNum);
						params.add(dateStr);
					}
					jdbcTemplate.update(sql, params.toArray());
				}
				if ("".equals(approveItemName)) {
					params.clear();
					params.add(approveitemguid);
					approveItemName = this
							.getOneString(
									"select t.approveitemname from spm_approveitem t where t.approveitemguid=?",
									params);
				}
				Senate senate = new Senate();
				senate.setSenateguid(new GUID().toString());
				senate.setSatisfaction(svalue);
				senate.setApproveitemguid(approveitemguid);
				senate.setDeclaredatetime(new java.sql.Date(new Date()
						.getTime()));
				senate.setSenatedatetime(new java.sql.Date(new Date().getTime()));
				senate.setDeclaresn(zxCode);
				senate.setEmployeeguid(person.getID());
				senate.setEmployeedeptname(person.getName());
				senate.setBureauguid(person.getBureauId());
				senate.setBureauname(org.getName());
				senate.setApproveitemname(approveItemName);
				senate.setDepartment_id(person.getParentID());
				senate.setIssenate("1");
				if (!"".equals(type))
					senate.setType(type);
				if (!"".equals(userMacAddr)) {
					senate.setUsermacaddr(userMacAddr);
				}
				// 保存评价信息
				senateRepository.save(senate);
				// 不满意的评价给监察组发提示短信
				if (null != svalue && "3".equals(svalue)) {
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("zxCode", zxCode);
					paramMap.put("approveItemName", approveItemName);
					this.notSatSMS(routerDataSource, paramMap);
				}
			}
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;

	}

	/**
	 * 不满意评价发送短信
	 */
	@Override
	public boolean notSatSMS(DataSource ds, Map<String, Object> paramMap) {
		
		boolean result = false;
		/*try {
			// 获取短信配置内容
			SmsConfig smsConfig = smsConfigRepository
					.findOne("{09A16D4E-0000-0000-1399-7BD700000E7F}");
			if (smsConfig != null && "1".equals(smsConfig.getFlag())) {
				Person person = ThreadLocalHolder.getPerson();
				OrgUnit org = RisesoftUtil.getPersonManager().getBureau(
						ThreadLocalHolder.getTenantId(), person.getID());
				GroupManager groupManager = RisesoftUtil.getGroupManager();
				// 根据dn获取用户组
				Group group0 = null;
				Group group1 = null;
				Group group2 = null;
				Group group3 = null;
				List<Group> group0List = groupManager.getByDN(
						"cn=测试用户组,o=罗湖区");
				List<Group> group1List = groupManager.getByDN(
						"cn=全区组,ou=不满意评价短信,ou=系统,o=罗湖区");
				List<Group> group2List = groupManager.getByDN(
						"cn=委办局组,ou=不满意评价短信,ou=系统,o=罗湖区");
				List<Group> group3List = groupManager.getByDN(t
						"cn=部门组,ou=不满意评价短信,ou=系统,o=罗湖区");
				// 获取用户组下所有人员
				List<Person> list0 = new ArrayList<Person>();
				List<Person> list1 = new ArrayList<Person>();
				List<Person> list2 = new ArrayList<Person>();
				List<Person> list3 = new ArrayList<Person>();
				if (group0List.size() > 0) {
					group0 = group0List.get(0);
					list0 = groupManager.getPersons(tenantId, group0.getID());
				}
				if (group1List.size() > 0) {
					group1 = group1List.get(0);
					list1 = groupManager.getPersons(tenantId, group1.getID());
				}
				if (group2List.size() > 0) {
					group2 = group2List.get(0);
					list2 = groupManager.getPersons(tenantId, group2.getID());
				}
				if (group3List.size() > 0) {
					group3 = group3List.get(0);
					groupManager.getPersons(tenantId, group3.getID());
				}
				// 过滤委办局组，获取本部门人员（人员不多，使用for循环）
				List<Person> list22 = new ArrayList<Person>();
				for (Person p : list2) {
					if (person.getBureauId().equalsIgnoreCase(p.getBureauId())) {
						list22.add(p);
					}
				}
				// 过滤部门组，获取本部门人员（人员不多，使用for循环）
				List<Person> list33 = new ArrayList<Person>();
				for (Person p : list3) {
					if (person.getBureauId().equalsIgnoreCase(p.getBureauId())) {
						list33.add(p);
					}
				}
				// personList.addAll(list0);//测试组
				personList.addAll(list1);// 全局组，不需要过滤
				personList.addAll(list22);
				personList.addAll(list33);
				// 获取手机号码list
				List<String> mobileList = new ArrayList<String>();
				for (Person p : personList) {
					mobileList.add(p.getMobile());
				}
				mobileList.add("18503063762");
				String smsContent = smsConfig.getSmscontent();
				String mobilePhone = person.getMobile();
				String phone = person.getOfficePhone();
				if (!"".equals(mobilePhone)) {
					smsContent = smsContent.replace("*****", mobilePhone);
				} else if (!"".equals(phone)) {
					smsContent = smsContent.replace("*****", phone);
				} else {
					smsContent = smsContent.replace("*****", "无");
				}
				smsContent = smsContent.replace("*****", "无");
				smsContent = smsContent.replace("****", paramMap.get("zxCode")
						.toString());
				smsContent = smsContent.replace("***",
						paramMap.get("approveItemName").toString());
				smsContent = smsContent.replace("**", person.getName());
				smsContent = smsContent.replace("*", org.getName());
				System.out.println("不满意评价" + smsContent + "&"
						+ mobilePhone.toString());
				// 不满意的评价给监察组发提示短信
				SendSms.smsList(routerDataSource, mobileList, smsContent,
						smsConfig.getOri_tel());
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return result;
	}

	/*
	 * 
	 * 发送评价短信
	 */
	@Override
	public boolean sendSenateSms(Map<String, Object> paramMap) {
		// 执行发送评价短信动作。先操作sms_pjrelation表，再发送。
		boolean result = false;
		String processInstanceId = paramMap.get("processInstanceId").toString();
		try {
			OfficeSpiDeclareinfo officeSpiDeclareinfo = officeSpiDeclareinfoService
					.findByProcessInstanceId(processInstanceId);
			String workflowinsguid = officeSpiDeclareinfo.getGuid();
			List<Object> params = new ArrayList<Object>();
			params.clear();
			params.add(officeSpiDeclareinfo.getGuid());
			String mobile = this
					.getOneString(
							"select distinct t.declarermobile from office_spi_declareinfo t where t.workflowinstance_guid=? ",
							params);
			String approveitemname = this
					.getOneString(
							"select distinct t.approveitemname from office_spi_declareinfo t where t.workflowinstance_guid=? ",
							params);
			String[] str = this
					.getOneString(
							"select distinct t.declaresn||','||t.bureauname from office_spi_declareinfo t where t.workflowinstance_guid=? ",
							params).split(",");
			String reno = certificateService.getreno();
			params.clear();
			params.add(reno);
			int renum = Integer.parseInt(this.getOneString(
					"select count(*) from sms_pjrelation t where t.reno=? ",
					params));
			if (renum > 0) {
				// 删除一条数据
				String delsql = "delete from sms_pjrelation t where t.reno=? ";
				jdbcTemplate.update(delsql, params.toArray());
			}
			// 再插入一条新数据
			String insersql = "insert into sms_pjrelation t (guid,instanceguid,reno,totel,flag) values (?,?,?,?,1)";
			params.clear();
			params.add(new GUID().toString());
			params.add(workflowinsguid);
			params.add(reno);
			params.add(mobile);
			jdbcTemplate.update(insersql, params.toArray());
			// 执行短信发送
			// boolean bl=SendSms.smsjk(mobile,"MYDDC",reno);
			SendSms.jksendsms(routerDataSource, mobile, "MYDDC", reno,
					"罗湖行政审批与服务平台提醒：感谢您在罗湖区" + str[1] + "办理了" + approveitemname
							+ "业务，受理回执号为：" + str[0]
							+ "请您对我们的服务进行评价，回复数字：“1为满意，2为一般，3为不满意“，谢谢您！");
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
			System.out
					.println("SenateServiceImpl.sendSenateSms(...)---短信发送失败!");
		}
		return result;
	}

	// ============评价统计====================
	//评价统计
	@Override
	public Pager senateList(HttpServletRequest request, Pager pager) {
		int pageNum = pager.getPageNo();
		int pageSize = pager.getPageSize();
		String streetguid = StringX.getNullString(request.getParameter("streetguid"));
		String resultType = StringX.getNullString(request.getParameter("resultType"));
		String classify = StringX.getNullString(request.getParameter("classify"));
		String employee_name = StringX.getNullString(request.getParameter("employee_name"));
		String bureauguid = StringX.getNullString(request
				.getParameter("bureauguid"));
		String senatedatetime_start = StringX.getNullString(request
				.getParameter("senatedatetime_start"));
		String senatedatetime_end = StringX.getNullString(request
				.getParameter("senatedatetime_end"));
		String declaredatetime_start = StringX.getNullString(request
				.getParameter("declaredatetime_start"));
		String declaredatetime_end = StringX.getNullString(request
				.getParameter("declaredatetime_end"));
		Person person = ThreadLocalHolder.getPerson();
		OrgUnit org = RisesoftUtil.getPersonManager().getBureau(
				ThreadLocalHolder.getTenantId(), person.getID());
		// 查询到当前用户在rc8中的id,
		String userID = person.getID();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			String senate_condition = " and 1=1 ";
			List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
			List<Object> params = new ArrayList<Object>();
			if (!StringX.isBlank(senatedatetime_start)) {
				senate_condition += " and (trunc(t1.senatedatetime) >=to_date(?,'yyyy-mm-dd') or t1.senatedatetime_sl >=to_date(?,'yyyy-mm-dd')) ";
				params.add(senatedatetime_start);
				params.add(senatedatetime_start);
			}
			if (!StringX.isBlank(senatedatetime_end)) {
				senate_condition += " and (trunc(t1.senatedatetime) <=to_date(?,'yyyy-mm-dd') or t1.senatedatetime_sl <=to_date(?,'yyyy-mm-dd')) ";
				params.add(senatedatetime_end);
				params.add(senatedatetime_end);
			}
			if (!StringX.isBlank(declaredatetime_start)) {
				senate_condition += " and trunc(t1.declaredatetime) >=to_date(?,'yyyy-mm-dd') ";
				params.add(declaredatetime_start);
			}
			if (!StringX.isBlank(declaredatetime_end)) {
				senate_condition += " and trunc(t1.declaredatetime) <=to_date(?,'yyyy-mm-dd') ";
				params.add(declaredatetime_end);
			}
			String sql ="";
			if("district".equalsIgnoreCase(classify)){//区级部门
				sql = "SELECT B.BUREAUTABINDEX,B.BUREAUNAME ,B.BUREAUGUID ,NVL(TOTAL,0) TOTAL,NVL(S.BESTNUM,0) BESTNUM,NVL(S.GOODNUM,0) GOODNUM,NVL(S.BADNUM,0) BADNUM,NVL(PJL,0) PJL,(CASE  WHEN TOTAL=0 OR TOTAL IS NULL THEN '0.00%' ELSE TO_CHAR(ROUND(NVL(PJL,0)/TOTAL*100,2),'FM9999990.00') || '%' END)  PJLV,(CASE  WHEN PJL=0 OR PJL IS NULL THEN '0.00%' ELSE TO_CHAR(ROUND(BESTNUM/PJL*100,2),'FM9999990.00') || '%' END)  MYLV,(CASE WHEN PJL = 0 OR PJL IS NULL THEN '0.00%' ELSE TO_CHAR(ROUND(BADNUM/PJL*100,2),'FM9999990.00') || '%' END)  BMYLV,(CASE  WHEN TOTAL=0 OR TOTAL IS NULL THEN 0 ELSE ROUND(NVL(PJL,0)/TOTAL*100,2) END) INT6,(CASE  WHEN PJL=0 OR PJL IS NULL THEN 0 ELSE ROUND(BESTNUM/PJL*100,2) END) INT7,(CASE WHEN PJL = 0 OR PJL IS NULL THEN 0 ELSE ROUND(BADNUM/PJL*100,2) END) INT8 FROM "
						+ " (select * from (select bureauguid,bureauname,BUREAUTABINDEX from spm_bureau b where b.flag='1' and b.isstreet=0 union select 'ALL','合计',1999 from dual) ) B "
						+ " LEFT OUTER JOIN "
						+ " (SELECT nvl(bureauGUID,'ALL') bureauGUID, "
						+ " COUNT(issenate) total,"
						+ " COUNT(CASE (to_number(issenate)+to_number(issenate_sl)) WHEN 1 THEN 1 when 2 then 1 END) pjl,"
						+ " COUNT(case when (decode(satisfaction,'no',0,null,0,to_number(satisfaction))+decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl)))=1 then 1 when ((decode(satisfaction,'no',0,null,0,to_number(satisfaction))+decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl)))=2 and (decode(satisfaction,'no',0,null,0,to_number(satisfaction))*decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl)))!=0) then 1 end)AS bestnum,"
						+ " COUNT(case when (decode(satisfaction,'no',0,null,0,to_number(satisfaction))=2 or decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl))=2) and (decode(satisfaction,'no',0,null,0,to_number(satisfaction))!=3 and decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl))!=3) then 1 end)AS goodnum,"
						+ " COUNT(case when (decode(satisfaction,'no',0,null,0,to_number(satisfaction))=3 or decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl))=3) then 1 end)AS badnum"
						+ " FROM view_spm_senate t1 where t1.type in ('1','2','3','4','5') and t1.bureauguid in (select bureauguid from spm_bureau b where b.flag = '1' and b.isstreet = 0 )"
						+ senate_condition
						+ " GROUP BY rollup(bureauGUID)) s"
						+ " ON  b.bureauGUID=s.bureauGUID where 1=1 ";
			}
			if("street".equalsIgnoreCase(classify)){//街道
				sql = "SELECT B.BUREAUTABINDEX,B.BUREAUNAME ,B.BUREAUGUID ,NVL(TOTAL,0) TOTAL,NVL(S.BESTNUM,0) BESTNUM,NVL(S.GOODNUM,0) GOODNUM,NVL(S.BADNUM,0) BADNUM,NVL(PJL,0) PJL,(CASE  WHEN TOTAL=0 OR TOTAL IS NULL THEN '0.00%' ELSE TO_CHAR(ROUND(NVL(PJL,0)/TOTAL*100,2),'FM9999990.00') || '%' END)  PJLV,(CASE  WHEN PJL=0 OR PJL IS NULL THEN '0.00%' ELSE TO_CHAR(ROUND(BESTNUM/PJL*100,2),'FM9999990.00') || '%' END)  MYLV,(CASE WHEN PJL = 0 OR PJL IS NULL THEN '0.00%' ELSE TO_CHAR(ROUND(BADNUM/PJL*100,2),'FM9999990.00') || '%' END)  BMYLV,(CASE  WHEN TOTAL=0 OR TOTAL IS NULL THEN 0 ELSE ROUND(NVL(PJL,0)/TOTAL*100,2) END) INT6,(CASE  WHEN PJL=0 OR PJL IS NULL THEN 0 ELSE ROUND(BESTNUM/PJL*100,2) END) INT7,(CASE WHEN PJL = 0 OR PJL IS NULL THEN 0 ELSE ROUND(BADNUM/PJL*100,2) END) INT8 FROM" 
						+" (select * from (select bureauguid,bureauname,BUREAUTABINDEX from spm_bureau b where b.flag='1' and b.isstreet=1 and b.bureauname like '%街道%' union select 'ALL','合计',999 from dual) ) B"
						+" LEFT OUTER JOIN" 
						+" (SELECT nvl(bureauGUID,'ALL') bureauGUID," 
						+" COUNT(issenate) total,"
						+" COUNT(CASE (to_number(issenate)+to_number(issenate_sl)) WHEN 1 THEN 1 when 2 then 1 END) pjl,"
						+" COUNT(case when (decode(satisfaction,'no',0,null,0,to_number(satisfaction))+decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl)))=1 then 1 when ((decode(satisfaction,'no',0,null,0,to_number(satisfaction))+decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl)))=2 and (decode(satisfaction,'no',0,null,0,to_number(satisfaction))*decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl)))!=0) then 1 end)AS bestnum,"
						+" COUNT(case when (decode(satisfaction,'no',0,null,0,to_number(satisfaction))=2 or decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl))=2) and (decode(satisfaction,'no',0,null,0,to_number(satisfaction))!=3 and decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl))!=3) then 1 end)AS goodnum,"
						+" COUNT(case when (decode(satisfaction,'no',0,null,0,to_number(satisfaction))=3 or decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl))=3) then 1 end)AS badnum"
						+" FROM view_spm_senate t1 where t1.type in ('1','2','3','4','5') and t1.bureauguid in (select bureauguid from spm_bureau b where b.flag = '1' and b.isstreet = 1 )" 
						+ senate_condition
						+" GROUP BY rollup(bureauGUID)) s" 
						+" ON  b.bureauGUID=s.bureauGUID where 1=1 ";
			}
			if("community".equalsIgnoreCase(classify)){//社区
				sql = "SELECT B.BUREAUTABINDEX,B.BUREAUNAME ,B.BUREAUGUID ,NVL(TOTAL,0) TOTAL,NVL(S.BESTNUM,0) BESTNUM,NVL(S.GOODNUM,0) GOODNUM,NVL(S.BADNUM,0) BADNUM,NVL(PJL,0) PJL,(CASE  WHEN TOTAL=0 OR TOTAL IS NULL THEN '0.00%' ELSE TO_CHAR(ROUND(NVL(PJL,0)/TOTAL*100,2),'FM9999990.00') || '%' END)  PJLV,(CASE  WHEN PJL=0 OR PJL IS NULL THEN '0.00%' ELSE TO_CHAR(ROUND(BESTNUM/PJL*100,2),'FM9999990.00') || '%' END)  MYLV,(CASE WHEN PJL = 0 OR PJL IS NULL THEN '0.00%' ELSE TO_CHAR(ROUND(BADNUM/PJL*100,2),'FM9999990.00') || '%' END)  BMYLV,(CASE  WHEN TOTAL=0 OR TOTAL IS NULL THEN 0 ELSE ROUND(NVL(PJL,0)/TOTAL*100,2) END) INT6,(CASE  WHEN PJL=0 OR PJL IS NULL THEN 0 ELSE ROUND(BESTNUM/PJL*100,2) END) INT7,(CASE WHEN PJL = 0 OR PJL IS NULL THEN 0 ELSE ROUND(BADNUM/PJL*100,2) END) INT8 FROM" 
						+" (select * from (select bureauguid,bureauname,BUREAUTABINDEX from spm_bureau b where b.flag='1' and b.bureauname like '%社区工作站%' and b.bureauname <> '社区工作站' union select 'ALL','合计',999 from dual) ) B"
						+" LEFT OUTER JOIN" 
						+" (SELECT nvl(bureauGUID,'ALL') bureauGUID," 
						+" COUNT(issenate) total,"
						+" COUNT(CASE (to_number(issenate)+to_number(issenate_sl)) WHEN 1 THEN 1 when 2 then 1 END) pjl,"
						+" COUNT(case when (decode(satisfaction,'no',0,null,0,to_number(satisfaction))+decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl)))=1 then 1 when ((decode(satisfaction,'no',0,null,0,to_number(satisfaction))+decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl)))=2 and (decode(satisfaction,'no',0,null,0,to_number(satisfaction))*decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl)))!=0) then 1 end)AS bestnum,"
						+" COUNT(case when (decode(satisfaction,'no',0,null,0,to_number(satisfaction))=2 or decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl))=2) and (decode(satisfaction,'no',0,null,0,to_number(satisfaction))!=3 and decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl))!=3) then 1 end)AS goodnum,"
						+" COUNT(case when (decode(satisfaction,'no',0,null,0,to_number(satisfaction))=3 or decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl))=3) then 1 end)AS badnum"
						+" FROM spm_senate t1 where t1.type in ('1','2','3','4','5') and t1.bureauguid in (select bureauguid from spm_bureau b where b.flag = '1' and b.bureauname like '%社区工作站%' and b.bureauname <> '社区工作站' )" 
						+ senate_condition
						+" GROUP BY rollup(bureauGUID)) s" 
						+" ON  b.bureauGUID=s.bureauGUID where 1=1 ";
			}
			if("itemSenate".equalsIgnoreCase(classify)){//事项评价统计
				sql = "SELECT B.BUREAUTABINDEX,B.BUREAUNAME ,B.BUREAUGUID ,NVL(APPROVEITEMNAME,'') APPROVEITEMNAME,NVL(APPROVEITEMGUID,'') APPROVEITEMGUID,NVL(TDBH,0) TDBH,NVL(YWBS,0) YWBS,NVL(SJTC,0) SJTC,NVL(QT,0) QT,NVL(TOTAL,0) TOTAL,NVL(S.BESTNUM,0) BESTNUM,NVL(S.GOODNUM,0) GOODNUM,NVL(S.BADNUM,0) BADNUM,NVL(PJL,0) PJL,(CASE  WHEN TOTAL=0 OR TOTAL IS NULL THEN '0.00%' ELSE TO_CHAR(ROUND(NVL(PJL,0)/TOTAL*100,2),'FM9999990.00') || '%' END)  PJLV,(CASE  WHEN PJL=0 OR PJL IS NULL THEN '0.00%' ELSE TO_CHAR(ROUND(BESTNUM/PJL*100,2),'FM9999990.00') || '%' END)  MYLV,(CASE WHEN PJL = 0 OR PJL IS NULL THEN '0.00%' ELSE TO_CHAR(ROUND(BADNUM/PJL*100,2),'FM9999990.00') || '%' END)  BMYLV,(CASE  WHEN TOTAL=0 OR TOTAL IS NULL THEN 0 ELSE ROUND(NVL(PJL,0)/TOTAL*100,2) END) INT6,(CASE  WHEN PJL=0 OR PJL IS NULL THEN 0 ELSE ROUND(BESTNUM/PJL*100,2) END) INT7,(CASE WHEN PJL = 0 OR PJL IS NULL THEN 0 ELSE ROUND(BADNUM/PJL*100,2) END) INT8 FROM" 
					+"( select * from (select sb.bureauguid,sb.bureauname,sb.BUREAUTABINDEX from spm_bureau sb where sb.flag='1'  union select 'ALL','合计',999 from dual) ) B"
					+" INNER JOIN" 
					+" (SELECT bureauGUID,approveitemguid,max(ba.approveitemname) approveitemname,"
					+" COUNT(issenate) total,count(decode(yawp,'1',1)) tdbh,count(decode(yawp,'2',1)) ywbs,count(decode(yawp,'3',1)) sjtc,count(decode(yawp,'4',1)) qt,"
					+" COUNT(CASE (to_number(issenate)+to_number(issenate_sl)) WHEN 1 THEN 1 when 2 then 1 END) pjl,"
					+" COUNT(case when (decode(satisfaction,'no',0,null,0,to_number(satisfaction))+decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl)))=1 then 1 when ((decode(satisfaction,'no',0,null,0,to_number(satisfaction))+decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl)))=2 and (decode(satisfaction,'no',0,null,0,to_number(satisfaction))*decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl)))!=0) then 1 end)AS bestnum,"
					+" COUNT(case when (decode(satisfaction,'no',0,null,0,to_number(satisfaction))=2 or decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl))=2) and (decode(satisfaction,'no',0,null,0,to_number(satisfaction))!=3 and decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl))!=3) then 1 end)AS goodnum,"
					+" COUNT(case when (decode(satisfaction,'no',0,null,0,to_number(satisfaction))=3 or decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl))=3) then 1 end)AS badnum"
					+" FROM spm_senate t1 ,xzql_xzsp_base ba where t1.approveitemguid=ba.itemid  " 
					+ senate_condition 
					+" GROUP BY approveitemguid,bureauGUID"
					+" UNION all "
					+" SELECT 'ALL' bureauGUID,'' approveitemguid,'' approveitemname, "
					+" COUNT(issenate) total,count(decode(yawp,'1',1)) tdbh,count(decode(yawp,'2',1)) ywbs,count(decode(yawp,'3',1)) sjtc,count(decode(yawp,'4',1)) qt,"
					+" COUNT(CASE (to_number(issenate)+to_number(issenate_sl)) WHEN 1 THEN 1 when 2 then 1 END) pjl,"
					+" COUNT(case when (decode(satisfaction,'no',0,null,0,to_number(satisfaction))+decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl)))=1 then 1 when ((decode(satisfaction,'no',0,null,0,to_number(satisfaction))+decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl)))=2 and (decode(satisfaction,'no',0,null,0,to_number(satisfaction))*decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl)))!=0) then 1 end)AS bestnum,"
					+" COUNT(case when (decode(satisfaction,'no',0,null,0,to_number(satisfaction))=2 or decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl))=2) and (decode(satisfaction,'no',0,null,0,to_number(satisfaction))!=3 and decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl))!=3) then 1 end)AS goodnum,"
					+" COUNT(case when (decode(satisfaction,'no',0,null,0,to_number(satisfaction))=3 or decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl))=3) then 1 end)AS badnum"
					+" FROM spm_senate t1 where 1=1 "
					+" ) s "
					+" ON  b.bureauGUID=s.bureauGUID where 1=1 ";
			}
			if("personnalSenate".equalsIgnoreCase(classify)){//个人评价统计
				sql = "SELECT B.BUREAUTABINDEX,B.BUREAUNAME ,DEPARTMENT_NAME,EMPLOYEENAME,EMPLOYEEGUID,B.BUREAUGUID ,NVL(TDBH,0) TDBH,NVL(YWBS,0) YWBS,NVL(SJTC,0) SJTC,NVL(QT,0) QT,NVL(TOTAL,0) TOTAL,NVL(S.BESTNUM,0) BESTNUM,NVL(S.GOODNUM,0) GOODNUM,NVL(S.BADNUM,0) BADNUM,NVL(PJL,0) PJL,(CASE  WHEN TOTAL=0 OR TOTAL IS NULL THEN '0.00%' ELSE TO_CHAR(ROUND(NVL(PJL,0)/TOTAL*100,2),'FM9999990.00') || '%' END)  PJLV,(CASE  WHEN PJL=0 OR PJL IS NULL THEN '0.00%' ELSE TO_CHAR(ROUND(BESTNUM/PJL*100,2),'FM9999990.00') || '%' END)  MYLV,(CASE WHEN PJL = 0 OR PJL IS NULL THEN '0.00%' ELSE TO_CHAR(ROUND(BADNUM/PJL*100,2),'FM9999990.00') || '%' END)  BMYLV,(CASE  WHEN TOTAL=0 OR TOTAL IS NULL THEN 0 ELSE ROUND(NVL(PJL,0)/TOTAL*100,2) END) INT6,(CASE  WHEN PJL=0 OR PJL IS NULL THEN 0 ELSE ROUND(BESTNUM/PJL*100,2) END) INT7,(CASE WHEN PJL = 0 OR PJL IS NULL THEN 0 ELSE ROUND(BADNUM/PJL*100,2) END) INT8 FROM" 
					+"( select * from (select sb.bureauguid,sb.bureauname,sb.BUREAUTABINDEX from spm_bureau sb where sb.flag='1'  union select 'ALL','合计',999 from dual) ) B"
					+" INNER JOIN" 
					+" (SELECT t1.bureauGUID,max(t1.bureauname) bureauname,max(d.department_name) department_name,max(t1.employeedeptname) employeename,t1.employeeguid,"
					+" COUNT(issenate) total,count(decode(yawp,'1',1)) tdbh,count(decode(yawp,'2',1)) ywbs,count(decode(yawp,'3',1)) sjtc,count(decode(yawp,'4',1)) qt,"
					+" COUNT(CASE (to_number(issenate)+to_number(issenate_sl)) WHEN 1 THEN 1 when 2 then 1 END) pjl,"
					+" COUNT(case when (decode(satisfaction,'no',0,null,0,to_number(satisfaction))+decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl)))=1 then 1 when ((decode(satisfaction,'no',0,null,0,to_number(satisfaction))+decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl)))=2 and (decode(satisfaction,'no',0,null,0,to_number(satisfaction))*decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl)))!=0) then 1 end)AS bestnum,"
					+" COUNT(case when (decode(satisfaction,'no',0,null,0,to_number(satisfaction))=2 or decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl))=2) and (decode(satisfaction,'no',0,null,0,to_number(satisfaction))!=3 and decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl))!=3) then 1 end)AS goodnum,"
					+" COUNT(case when (decode(satisfaction,'no',0,null,0,to_number(satisfaction))=3 or decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl))=3) then 1 end)AS badnum"
					+" FROM spm_senate t1 left join rc8_org_department d on t1.department_id = d.id  "  
					+ senate_condition
					+" GROUP BY t1.employeeguid, t1.bureauGUID,d.id "
					+" UNION all "
					+" SELECT 'ALL' bureauGUID,'' bureauname,N'' department_name,'' employeename,'' employeeguid, "
					+" COUNT(issenate) total,count(decode(yawp,'1',1)) tdbh,count(decode(yawp,'2',1)) ywbs,count(decode(yawp,'3',1)) sjtc,count(decode(yawp,'4',1)) qt,"
					+" COUNT(CASE (to_number(issenate)+to_number(issenate_sl)) WHEN 1 THEN 1 when 2 then 1 END) pjl,"
					+" COUNT(case when (decode(satisfaction,'no',0,null,0,to_number(satisfaction))+decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl)))=1 then 1 when ((decode(satisfaction,'no',0,null,0,to_number(satisfaction))+decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl)))=2 and (decode(satisfaction,'no',0,null,0,to_number(satisfaction))*decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl)))!=0) then 1 end)AS bestnum,"
					+" COUNT(case when (decode(satisfaction,'no',0,null,0,to_number(satisfaction))=2 or decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl))=2) and (decode(satisfaction,'no',0,null,0,to_number(satisfaction))!=3 and decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl))!=3) then 1 end)AS goodnum,"
					+" COUNT(case when (decode(satisfaction,'no',0,null,0,to_number(satisfaction))=3 or decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl))=3) then 1 end)AS badnum"
					+" FROM spm_senate t1 where 1=1 "
					+" ) s "
					+" ON  b.bureauGUID=s.bureauGUID where 1=1 ";
			}
			if("windowsSenate".equalsIgnoreCase(classify)){//窗口评价统计
				sql = "SELECT B.BUREAUGUID ,B.windowname,NVL(TDBH,0) TDBH,NVL(YWBS,0) YWBS,NVL(SJTC,0) SJTC,NVL(QT,0) QT,NVL(TOTAL,0) TOTAL,NVL(S.BESTNUM,0) BESTNUM,NVL(S.GOODNUM,0) GOODNUM,NVL(S.BADNUM,0) BADNUM,NVL(PJL,0) PJL,(CASE  WHEN TOTAL=0 OR TOTAL IS NULL THEN '0.00%' ELSE TO_CHAR(ROUND(NVL(PJL,0)/TOTAL*100,2),'FM9999990.00') || '%' END)  PJLV,(CASE  WHEN PJL=0 OR PJL IS NULL THEN '0.00%' ELSE TO_CHAR(ROUND(BESTNUM/PJL*100,2),'FM9999990.00') || '%' END)  MYLV,(CASE WHEN PJL = 0 OR PJL IS NULL THEN '0.00%' ELSE TO_CHAR(ROUND(BADNUM/PJL*100,2),'FM9999990.00') || '%' END)  BMYLV,(CASE  WHEN TOTAL=0 OR TOTAL IS NULL THEN 0 ELSE ROUND(NVL(PJL,0)/TOTAL*100,2) END) INT6,(CASE  WHEN PJL=0 OR PJL IS NULL THEN 0 ELSE ROUND(BESTNUM/PJL*100,2) END) INT7,(CASE WHEN PJL = 0 OR PJL IS NULL THEN 0 ELSE ROUND(BADNUM/PJL*100,2) END) INT8 FROM" 
					+"( select * from (select w.windowguid BUREAUGUID,w.entitywindowname windowname,1 BUREAUTABINDEX from DT_CKGL_WINDOWPERSONADD w union select 'ALL','合计',999 BUREAUTABINDEX from dual) ) B " 
					+" LEFT JOIN" 
					+" (SELECT NVL(t1.windowid,'ALL') windowid,max(t1.windowname) windowname,"
					+" COUNT(issenate) total,count(decode(yawp,'1',1)) tdbh,count(decode(yawp,'2',1)) ywbs,count(decode(yawp,'3',1)) sjtc,count(decode(yawp,'4',1)) qt,"
					+" COUNT(CASE (to_number(issenate)+to_number(issenate_sl)) WHEN 1 THEN 1 when 2 then 1 END) pjl,"
					+" COUNT(case when (decode(satisfaction,'no',0,null,0,to_number(satisfaction))+decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl)))=1 then 1 when ((decode(satisfaction,'no',0,null,0,to_number(satisfaction))+decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl)))=2 and (decode(satisfaction,'no',0,null,0,to_number(satisfaction))*decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl)))!=0) then 1 end)AS bestnum,"
					+" COUNT(case when (decode(satisfaction,'no',0,null,0,to_number(satisfaction))=2 or decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl))=2) and (decode(satisfaction,'no',0,null,0,to_number(satisfaction))!=3 and decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl))!=3) then 1 end)AS goodnum,"
					+" COUNT(case when (decode(satisfaction,'no',0,null,0,to_number(satisfaction))=3 or decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl))=3) then 1 end)AS badnum"
					+" FROM spm_senate t1 left join rc8_org_department d on t1.department_id = d.id  " 
					+ senate_condition
					+" GROUP BY rollup(t1.windowid)) s "
					+" ON  B.BUREAUGUID=s.windowid where 1=1 ";
			}

			if (!StringX.isBlank(bureauguid)) {// 受理部门
				sql += " and B.BUREAUGUID = ?";
				params.add(bureauguid);
			}
			if (!StringX.isBlank(streetguid)) {
				sql += " and instr((select b.department_guids from spm_bureau b where b.flag = '1' and b.isstreet='1' and b.bureauname like '%街道%' and b.bureauguid=?),b.BUREAUGUID)>0 ";
				params.add(streetguid);
			}
			if (!StringX.isBlank(employee_name)) {
				sql += " and EMPLOYEENAME like ? ";
				params.add("%"+employee_name+"%");
			}
			sql += " order by NVL(BUREAUTABINDEX,0) ";
			listmap = jdbcTemplate.queryForList(sql, params.toArray());
			//查询完成后，将查询结果复制给报表工具类 结果集 参数reportDataList，该参数全局共享，导出报表时不用重新查询结果，提升效率。
			ReportUtil.reportDataListMap.put(resultType, listmap);
			pager.setTotalRows(listmap.size());
			list.addAll(listmap);
			pager.setPageList(jdbcTemplate.queryForList(
					pager.setPageSql(sql.toString(), pageNum, pageSize),
					params.toArray()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pager;
	}
	
	/*
	 * 评价详细信息列表
	 */
	@Override
	public Pager detailList(HttpServletRequest request, Pager pager) {
		int pageNum = pager.getPageNo();
		int pageSize = pager.getPageSize();
		String resultType = StringX.getNullString(request.getParameter("resultType"));
		String classify = StringX.getNullString(request.getParameter("classify"));
		String fromPage = StringX.getNullString(request.getParameter("fromPage"));
		String bureauGuid = StringX.getNullString(request
				.getParameter("bureauGuid"));
		String queryType = StringX.getNullString(request.getParameter("type"));
		String approveitemname = StringX.getNullString(request
				.getParameter("approveitemname"));
		String senatedatetime_start = StringX.getNullString(request
				.getParameter("senatedatetime_start"));
		String senatedatetime_end = StringX.getNullString(request
				.getParameter("senatedatetime_end"));
		String declaredatetime_start = StringX.getNullString(request
				.getParameter("declaredatetime_start"));
		String declaredatetime_end = StringX.getNullString(request
				.getParameter("declaredatetime_end"));
		String declaresn = StringX.getNullString(request
				.getParameter("declaresn"));
		String employee_name = StringX.getNullString(request
				.getParameter("employee_name"));
		String issenate = StringX.getNullString(request
				.getParameter("issenate"));
		String senate_type = StringX.getNullString(request
				.getParameter("senate_type"));
		String employeeid = StringX.getNullString(request.getParameter("employeeid"));
		String itemguid = StringX.getNullString(request.getParameter("itemguid"));
		Person person = ThreadLocalHolder.getPerson();
		OrgUnit org = RisesoftUtil.getPersonManager().getBureau(
				ThreadLocalHolder.getTenantId(), person.getID());
		String userID = person.getID();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {

			List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
			List<Object> params = new ArrayList<Object>();
			String senate_condition = " and 1=1 ";
			if (!StringX.isBlank(senatedatetime_start)) {
				senate_condition += " and (trunc(t.senatedatetime) >=to_date(?,'yyyy-mm-dd') or t.senatedatetime_sl >=to_date(?,'yyyy-mm-dd')) ";
				params.add(senatedatetime_start);
				params.add(senatedatetime_start);
			}
			if (!StringX.isBlank(senatedatetime_end)) {
				senate_condition += " and (trunc(t.senatedatetime) <=to_date(?,'yyyy-mm-dd') or t1.senatedatetime_sl <=to_date(?,'yyyy-mm-dd')) ";
				params.add(senatedatetime_end);
				params.add(senatedatetime_end);
			}
			if (!StringX.isBlank(declaredatetime_start)) {
				senate_condition += " and trunc(t.declaredatetime) >=to_date(?,'yyyy-mm-dd') ";
				params.add(declaredatetime_start);
			}
			if (!StringX.isBlank(declaredatetime_end)) {
				senate_condition += " and trunc(t.declaredatetime) <=to_date(?,'yyyy-mm-dd') ";
				params.add(declaredatetime_end);
			}

			if (!StringX.isBlank(declaresn)) {
				senate_condition += " and t.declaresn =? ";
				params.add(declaresn);
			}
			if (!StringX.isBlank(issenate)) {
				if ("0".equalsIgnoreCase(issenate)) {
					senate_condition += " and (t.issenate =? and t.issenate_sl=?) ";
					params.add(issenate);
					params.add(issenate);
				}
				if ("1".equalsIgnoreCase(issenate)) {
					senate_condition += " and (t.issenate =? or t.issenate_sl=?) ";
					params.add(issenate);
					params.add(issenate);
				}

			}
			if ("pjq".equalsIgnoreCase(senate_type)) {
				senate_condition += " and ((t.type !='3' and t.type !='4') or (t.type_sl !='3' and t.type_sl !='4')) ";
			} else if (!StringX.isBlank(senate_type)) {
				senate_condition += " and (t.type =? or t.type_sl =?) ";
				params.add(senate_type);
				params.add(senate_type);
			}
			String sql ="";
			if("district".equalsIgnoreCase(classify)){
				//区级部门评价详细页面sql
				sql = "SELECT * FROM ( "
						+ "SELECT T.APPROVEITEMGUID,T.DECLARESN,T.BUREAUGUID ,SB3.BUREAUNAME,SB3.bureautabindex ,T.EMPLOYEEGUID ,NVL(RE.EMPLOYEE_NAME,EMPLOYEEDEPTNAME) EMPLOYEEDEPTNAME,"
						+ "NVL(SA.APPROVEITEMNAME,T.APPROVEITEMNAME) APPROVEITEMNAME,T.SATISFACTION ,to_char(T.DECLAREDATETIME,'yyyy-mm-dd') DECLAREDATETIME,to_char(T.SENATEDATETIME,'yyyy-mm-dd hh24:mi:ss') SENATEDATETIME ,T.TYPE ,T.ISSENATE ,"
						+ "RE.DEPARTMENT_GUID,SA.ADMINORGID,T.SATISFACTION_SL,to_char(T.SENATEDATETIME_SL,'yyyy-mm-dd hh24:mi:ss') SENATEDATETIME_SL,T.TYPE_SL,T.ISSENATE_SL FROM VIEW_SPM_SENATE T LEFT JOIN RISENET_EMPLOYEE RE ON T.EMPLOYEEGUID=RE.EMPLOYEE_GUID LEFT JOIN XZQL_XZSP_BASE SA "
						+ "ON T.APPROVEITEMGUID=SA.ITEMID,SPM_BUREAU SB3 WHERE T.BUREAUGUID=SB3.BUREAUGUID AND SB3.FLAG='1' and SB3.ISSTREET='0' "
						+ senate_condition 
						+ " ) T1 where  T1.ADMINORGID=? ";
				//窗口评价统计 详细页面 与 区级部门统计公用一个页面，再次判断请求是否来自窗口统计
				if("windowsSenate".equalsIgnoreCase(fromPage)){
					sql = "SELECT * FROM ( "
							+ "SELECT T.APPROVEITEMGUID,T.DECLARESN,T.BUREAUGUID ,w.windowguid,SB3.BUREAUNAME,SB3.bureautabindex ,T.EMPLOYEEGUID , EMPLOYEEDEPTNAME,"
							+ "NVL(SA.APPROVEITEMNAME,T.APPROVEITEMNAME) APPROVEITEMNAME,T.SATISFACTION ,to_char(T.DECLAREDATETIME,'yyyy-mm-dd') DECLAREDATETIME,to_char(T.SENATEDATETIME,'yyyy-mm-dd hh24:mi:ss') SENATEDATETIME ,T.TYPE ,T.ISSENATE ,"
							+ " SA.ADMINORGID,T.SATISFACTION_SL,to_char(T.SENATEDATETIME_SL,'yyyy-mm-dd hh24:mi:ss') SENATEDATETIME_SL,T.TYPE_SL,T.ISSENATE_SL FROM SPM_SENATE T LEFT JOIN DT_CKGL_WINDOWPERSONADD W ON T.windowid = w.windowguid LEFT JOIN XZQL_XZSP_BASE SA ON T.Approveitemguid = SA.Itemid, SPM_BUREAU SB3 "
							+ " WHERE T.BUREAUGUID=SB3.BUREAUGUID AND SB3.FLAG='1' and SB3.ISSTREET='0' "
							+ senate_condition 
							+ " ) T1 where  T1.windowguid=? ";
				}
			}
			//街道评价详细页面sql
			if("street".equalsIgnoreCase(classify)){
				sql = "select * from (SELECT T.APPROVEITEMGUID,T.DECLARESN,T.BUREAUGUID,B.BUREAUNAME,B.bureautabindex, T.EMPLOYEEGUID,e.employee_name EMPLOYEEDEPTNAME,ba.approveitemname APPROVEITEMNAME,T.SATISFACTION,to_char(T.DECLAREDATETIME, 'yyyy-mm-dd') DECLAREDATETIME,"
						+ "to_char(T.SENATEDATETIME, 'yyyy-mm-dd hh24:mi:ss') SENATEDATETIME,T.TYPE,T.ISSENATE,T.SATISFACTION_SL,to_char(T.SENATEDATETIME_SL, 'yyyy-mm-dd hh24:mi:ss') SENATEDATETIME_SL,T.TYPE_SL,T.ISSENATE_SL FROM "
						+ "(select * from (select bureauguid, bureauname, BUREAUTABINDEX from spm_bureau b where b.flag = '1' and b.isstreet = 1 and b.bureauname like '%街道%' "
						+ "union select 'ALL', '合计', 999 from dual)) B LEFT OUTER JOIN spm_senate T ON b.bureauGUID = T.bureauGUID,risenet_employee e,xzql_xzsp_base ba where e.employee_guid=t.employeeguid and ba.itemid=t.approveitemguid and 1=1 "
						+ senate_condition
						+ " and T.BUREAUGUID =? ) T1 where 1=1 ";
			}
			//社区评价详细页面sql
			if("community".equalsIgnoreCase(classify)){
				sql = "select * from (SELECT T.APPROVEITEMGUID,T.DECLARESN,T.BUREAUGUID,B.BUREAUNAME,B.bureautabindex, T.EMPLOYEEGUID,e.employee_name EMPLOYEEDEPTNAME,ba.approveitemname APPROVEITEMNAME,T.SATISFACTION,to_char(T.DECLAREDATETIME, 'yyyy-mm-dd') DECLAREDATETIME,"
						+ "to_char(T.SENATEDATETIME, 'yyyy-mm-dd hh24:mi:ss') SENATEDATETIME,T.TYPE,T.ISSENATE,T.SATISFACTION_SL,to_char(T.SENATEDATETIME_SL, 'yyyy-mm-dd hh24:mi:ss') SENATEDATETIME_SL,T.TYPE_SL,T.ISSENATE_SL FROM "
						+ "(select * from (select bureauguid, bureauname, BUREAUTABINDEX from spm_bureau b where b.flag = '1' and b.bureauname like '%社区工作站%' and b.bureauname <> '社区工作站' "
						+ "union select 'ALL', '合计', 999 from dual)) B LEFT OUTER JOIN spm_senate T ON b.bureauGUID = T.bureauGUID,risenet_employee e,xzql_xzsp_base ba where e.employee_guid=t.employeeguid and ba.itemid=t.approveitemguid and 1=1 "
						+ senate_condition
						+ " and T.BUREAUGUID =? ) T1 where 1=1 ";
			}
			params.add(bureauGuid);
			// 查询业务总量，不加过滤条件
			if ("ywzl".equalsIgnoreCase(queryType)) {
			}
			// 评价总量
			if ("pjzl".equalsIgnoreCase(queryType)) {
				sql += " and (T1.ISSENATE='1' or T1.ISSENATE_SL='1') ";
			}
			// 满意
			if ("my".equalsIgnoreCase(queryType)) {
				sql += " and ((decode(satisfaction,'no',0,null,0,to_number(satisfaction))+decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl)))=1 or ((decode(satisfaction,'no',0,null,0,to_number(satisfaction))+decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl)))=2 and (decode(satisfaction,'no',0,null,0,to_number(satisfaction))*decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl)))!=0)) ";
			}
			// 一般
			if ("yb".equalsIgnoreCase(queryType)) {
				sql += " and (decode(satisfaction,'no',0,null,0,to_number(satisfaction))=2 or decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl))=2) and (decode(satisfaction,'no',0,null,0,to_number(satisfaction))!=3 and decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl))!=3)  ";
			}
			// 不满意
			if ("bmy".equalsIgnoreCase(queryType)) {
				sql += " and (decode(satisfaction,'no',0,null,0,to_number(satisfaction))=3 or decode(satisfaction_sl,'no',0,null,0,to_number(satisfaction_sl))=3) ";
			}
			if(!"".equalsIgnoreCase(itemguid)){
				sql += " and APPROVEITEMGUID = ? ";
				params.add(itemguid);
			}
			
			if(!"".equalsIgnoreCase(employeeid)){
				sql += " and EMPLOYEEGUID = ? ";
				params.add(employeeid);
			}
			
			if (!StringX.isBlank(employee_name)) {
				sql += " and EMPLOYEEDEPTNAME =? ";
				params.add("%" + employee_name + "%"); 
			}
			if (!StringX.isBlank(approveitemname)) {
				sql += " and approveitemname like ? ";
				params.add("%" + approveitemname + "%");
			}
			
			sql += " order by DECLAREDATETIME desc ";
			listmap = jdbcTemplate.queryForList(sql, params.toArray());
			//查询完成后，将查询结果复制给报表工具类 结果集 参数reportDataList，改参数全局共享，导出报表时不用重新查询结果，提升效率。
			ReportUtil.reportDataListMap.put(resultType, listmap);
			pager.setTotalRows(listmap.size());
			list.addAll(listmap);
			pager.setPageList(jdbcTemplate.queryForList(
					pager.setPageSql(sql.toString(), pageNum, pageSize),
					params.toArray()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pager;
	}
	
	/**
	 * 获取受理单位下拉列表
	 */
	@Override
	public List<Map<String, Object>> getSpmBureauList() {
		List<Map<String, Object>> returnData = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select t.BUREAUNAME key,t.BUREAUGUID value from spm_bureau t where 1=1 and t.FLAG='1' and t.ISSTREET='0'");
		sql.append(" order by t.CREATEDATE desc ");
		returnData = jdbcTemplate.queryForList(sql.toString());
		return returnData;
	}
	
	/**
	 * 获取受理单位下拉列表
	 */
	@Override
	public List<Map<String, Object>> getStreetList() {
		List<Map<String, Object>> returnData = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select t.BUREAUNAME key,t.BUREAUGUID value from spm_bureau t where 1=1 and t.FLAG='1' and t.ISSTREET='1' and t.bureauname like '%街道%' ");
		sql.append(" order by t.CREATEDATE desc ");
		returnData = jdbcTemplate.queryForList(sql.toString());
		return returnData;
	}
	
	/**
	 * 获取社区下拉列表
	 */
	@Override
	public List<Map<String, Object>> getCommunityList() {
		List<Map<String, Object>> returnData = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select t.BUREAUNAME key,t.BUREAUGUID value from spm_bureau t where 1=1 and t.FLAG='1' and t.bureauname like '%社区%' and t.bureauname <> '社区工作站' ");
		sql.append(" order by t.CREATEDATE desc ");
		returnData = jdbcTemplate.queryForList(sql.toString());
		return returnData;
	}
	
	/**
	 * 获取社区下拉列表
	 */
	@Override
	public List<Map<String, Object>> getAllBureauList() {
		List<Map<String, Object>> returnData = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select t.BUREAUNAME key,t.BUREAUGUID value from spm_bureau t where 1=1 and t.FLAG='1' and t.bureauname  <> '社区工作站' ");
		sql.append(" order by t.bureautabindex ");
		returnData = jdbcTemplate.queryForList(sql.toString());
		return returnData;
	}
	
	/**
	 * 获取窗口下拉列表
	 */
	@Override
	public List<Map<String, Object>> getWindowsList() {
		List<Map<String, Object>> returnData = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct t.entitywindowname key,t.windowguid value from DT_CKGL_WINDOWPERSONADD t where 1=1 ");
		returnData = jdbcTemplate.queryForList(sql.toString());
		return returnData;
	}
	
	
	//==============================导出excel===============================
	/*
	 * 服务评价列表导出
	 * */
	@Override
	public void exportDataForExcel(HttpServletRequest req,HttpServletResponse response) {
		String resultType = StringX.getNullString(req.getParameter("resultType"));
		String filename = StringX.getNullString(req.getParameter("filename"));
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		try {
			mapList=ReportUtil.reportDataListMap.get(resultType);
			response.reset();
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-disposition","attachment; filename="+new String((filename+".xls").getBytes("GBK"),"iso8859-1"));
			OutputStream out = response.getOutputStream();
			if(resultType.toLowerCase().indexOf("detail")>-1){
				//服务评价详细情况列表导出
				//构造excel列名称
				//评价统计列表
				String[] headers = {"序号","受理单位","受理号","事项名称 ","服务人员","受理时间","受理评价情况","办结评价情况","受理评价时间","办结评价时间","受理评价方式","办结评价方式","业务来源"};
				ReportUtil<SenateDetailReportVO> ru = new ReportUtil<SenateDetailReportVO>();
				ru.exportData(filename, headers,mapList, out, new SenateDetailReportVO());
			}else if(resultType.indexOf("itemSenate")>-1){
				//按事项统计导出
				String[] headers = {"序号","事项名称","受理单位","业务总量","评价总量","评价率","满意率","不满意率","满意","一般","不满意","态度不好","时间太长","业务不熟","其他"};
				ReportUtil<ItemsSenateReportVO> ru = new ReportUtil<ItemsSenateReportVO>();
				ru.exportData(filename, headers,mapList, out,new ItemsSenateReportVO());
			}else if(resultType.indexOf("personnalSenate")>-1){
				//按个人统计导出
				String[] headers = {"序号","委办局名称","单位","窗口人员","业务总量","评价总量","评价率","满意率","不满意率","满意","一般","不满意","态度不好","时间太长","业务不熟","其他"};
				ReportUtil<PersonnalSenateReportVO> ru = new ReportUtil<PersonnalSenateReportVO>();
				ru.exportData(filename, headers,mapList, out,new PersonnalSenateReportVO());
			}else if(resultType.indexOf("windowsSenate")>-1){
				//按窗口统计导出
				String[] headers = {"序号","窗口名称","业务总量","评价总量","评价率","满意率","不满意率","满意","一般","不满意","态度不好","时间太长","业务不熟","其他"};
				ReportUtil<WindowsSenateReportVO> ru = new ReportUtil<WindowsSenateReportVO>();
				ru.exportData(filename, headers,mapList, out,new WindowsSenateReportVO());
			}else{
				//通用用列结构导出
				//构造excel列名称
				//评价统计列表
				String[] headers = {"序号","受理单位","业务总量","评价总量","评价率","满意率","不满意率","满意","一般","不满意"};
				ReportUtil<SenateReportVO> ru = new ReportUtil<SenateReportVO>();
				ru.exportData(filename, headers,mapList, out,new SenateReportVO());
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
