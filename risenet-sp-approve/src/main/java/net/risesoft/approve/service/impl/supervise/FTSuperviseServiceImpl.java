package net.risesoft.approve.service.impl.supervise;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import net.risesoft.api.SendDataManager;
import net.risesoft.approve.entity.jpa.FtSupervise;
import net.risesoft.approve.entity.jpa.OfficeSpiDeclareinfo;
import net.risesoft.approve.entity.jpa.TShenban;
import net.risesoft.approve.entity.jpa.TShenbanUnits;
import net.risesoft.approve.entity.jpa.TWangshangyushouli;
import net.risesoft.approve.entity.jpa.TWangshangyushouliUnits;
import net.risesoft.approve.entity.jpa.gdbs.BanjieProcess;
import net.risesoft.approve.entity.jpa.gdbs.ShenpichuliProcess;
import net.risesoft.approve.entity.jpa.gdbs.ShenpiguochengUnits;
import net.risesoft.approve.entity.jpa.gdbs.ShouliProcess;
import net.risesoft.approve.entity.jpa.gdbs.SpjgData;
import net.risesoft.approve.repository.jpa.OfficeSpiDeclareinfoRepository;
import net.risesoft.approve.repository.jpa.gdbs.ShenPiJieGuoRespository;
import net.risesoft.approve.repository.jpa.gdbs.ShouliRepository;
import net.risesoft.approve.repository.jpa.supervise.BanjieRepository;
import net.risesoft.approve.repository.jpa.supervise.SenateRepository;
import net.risesoft.approve.repository.jpa.supervise.ShenbanRepository;
import net.risesoft.approve.repository.jpa.supervise.ShenpichuliRepository;
import net.risesoft.approve.repository.jpa.supervise.TShenpiguochengRepository;
import net.risesoft.approve.repository.jpa.supervise.WangshangyushouliRepository;
import net.risesoft.approve.service.OfficeSpiDeclareinfoService;
import net.risesoft.approve.service.SpmApproveItemService;
import net.risesoft.approve.service.SpmBureauService;
import net.risesoft.approve.service.gdbs.IShouLiService;
import net.risesoft.approve.service.senate.SenateService;
import net.risesoft.approve.service.supervise.FTSuperviseService;
import net.risesoft.fileflow.entity.jpa.OpinionNew;
import net.risesoft.fileflow.service.OpinionNewService;
import net.risesoft.fileflow.service.ProcessDataService;
import net.risesoft.fileflow.service.ReminderDefineService;
import net.risesoft.fileflow.service.WorkflowProcessDefinitionService;
import net.risesoft.fileflow.service.WorkflowTaskService;
import net.risesoft.model.OrgUnit;
import net.risesoft.model.Person;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.GUID;
import net.risesoft.util.RisesoftUtil;
import net.risesoft.utilx.FTSuperviseUtil;

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * 审批环节数据接口实现类
 * 
 * @author Administrator
 *
 */
@Service(value="fTSuperviseService")
public class FTSuperviseServiceImpl implements FTSuperviseService {

	@Autowired
	private WangshangyushouliRepository wangshagnyushouliRepository;// 网上预受理repository

	@Autowired
	private ShenbanRepository shenbanRepository;// 申办repository

	@Autowired
	private ShouliRepository shouliRepository;// 受理repository
	
	@Autowired
	private ShenPiJieGuoRespository shenPiJieGuoRespository;//审批结果
	
	@Autowired
	private SenateRepository senateRepository;// 事项评议senateRepository

	@Autowired
	private OfficeSpiDeclareinfoRepository officeSpiDeclareinfoRepository;
	
	@Autowired
	private ShenpichuliRepository shenpichuliRepository;// 审批处理repository

	
	@Autowired
	private BanjieRepository banjieRepository;// 办结repository*/
	
	@Autowired
	private TShenpiguochengRepository tShenpiguochengRepository; //过程数据Repository

	@Resource(name = "reminderDefineService")
	private ReminderDefineService reminderDefineService;
	
	@Autowired
	private IShouLiService shouliService;
	
	@Resource(name = "fTSuperviseService")
	private FTSuperviseService fTSuperviseService;
	
	@Autowired
	private WorkflowTaskService workflowTaskService;
	
	@Autowired
	private ProcessDataService processDataService;
	
	@Resource(name="opinionNewService")
	private OpinionNewService opinionNewService;
	
	@Autowired
	private SenateService senateService;
	
	@Resource(name="officeSpiDeclareinfoService")
	private OfficeSpiDeclareinfoService officeSpiDeclareinfoService;
	
	@Autowired
	private WorkflowProcessDefinitionService workflowProcessDefinitionService;

	@Resource(name = "routerDataSource")
	private DataSource routerDataSource;
	@Autowired
	private SpmApproveItemService spmApproveItemService;
	@Autowired
	private SpmBureauService spmBureauServices;

	@Resource(name="sendDataManager")
	private SendDataManager sendDataManager;
	private JdbcTemplate jdbcTemplate;

	@PostConstruct
	private void afterIoc() {
		jdbcTemplate = new JdbcTemplate(this.routerDataSource);
	}

	/**
	 * 保存网上预受理数据
	 * 
	 * @return
	 */
	@Override
	public void saveWangshangyushouli(String instanceId) {
		FtSupervise ftSupervise;
		try {
			// 获取公共信息
			ftSupervise = FTSuperviseUtil.getFTCode(jdbcTemplate, instanceId);
			Date date = new Date();
			// SimpleDateFormat sdf = new
			// SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
			TWangshangyushouli bean = new TWangshangyushouli();
			TWangshangyushouliUnits unit = new TWangshangyushouliUnits();
			unit.setYwlsh(ftSupervise.getYwlsh());
			unit.setSjbbh("1");
			bean.setId(unit);
			bean.setSpsxbh(ftSupervise.getSpsxbh());
			bean.setSpsxzxbh(ftSupervise.getSpsxzxbh());
			bean.setIsexchangebsdt(ftSupervise.getIsexchangebsdt());
			bean.setYxtywlsh(ftSupervise.getYxtywlsh());
			bean.setSpsxmc(ftSupervise.getSpsxmc());
			bean.setDatasource(ftSupervise.getDatasource());
			bean.setSerialnum(ftSupervise.getSerialnum());
			bean.setYslbmzzjgdm("666666");
			bean.setXzqhdm("666666");
			bean.setYslztdm("1");
			bean.setYslsj(date);
			// 获取当前人员的信息
			Person person = ThreadLocalHolder.getPerson();
			bean.setBlrxm(person.getName());
			bean.setYslbmmc(person.getParentID());  //网上预受理表插入数据暂时屏蔽
			wangshagnyushouliRepository.save(bean); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 保存申办数据
	 * 
	 * @return
	 */
	@Override
	public void saveShenBan(String processDefinitionId, String taskId,String processInstanceId) {
	
		Task task = workflowTaskService.getTaskByTaskId(taskId);
		Person person = ThreadLocalHolder.getPerson();
		try {
			// 获取公共信息
			FtSupervise ftSupervise=null;
			String instanceguid = officeSpiDeclareinfoService.getGuidByProcessInstanceId(processInstanceId);
			ftSupervise = FTSuperviseUtil.getFTCode(jdbcTemplate, instanceguid);
			//根据原系统业务流水号查找申办数据,申办数据为null才插入数据
			TShenban shenban = shenbanRepository.findByYxtywlsh(ftSupervise.getYxtywlsh());
			if(shenban==null){
				TShenbanUnits id = new TShenbanUnits();
				id.setYwlsh(ftSupervise.getYwlsh());//业务流水号
				id.setSjbbh((long)1);//数据版本号
				OfficeSpiDeclareinfo officeSpiDeclareinfo = officeSpiDeclareinfoService.findByProcessInstanceId(processInstanceId);
				
				Calendar   calendar   =   new   GregorianCalendar(); 
			    calendar.setTime(new Date()); 
			    calendar.add(calendar.MINUTE,-2);//把日期往前减少2分钟
			    Date date=calendar.getTime();
				
				TShenban tShenban = new TShenban();
				tShenban.setId(id);
				tShenban.setSpsxbh(ftSupervise.getSpsxbh());//审批事项编号
				tShenban.setSpsxzxbh(ftSupervise.getSpsxzxbh());//审批事项子项编号
				tShenban.setYxtywlsh(ftSupervise.getYxtywlsh());//原系统业务流水号
				tShenban.setSpsxmc(ftSupervise.getSpsxmc());//审批事项名称
				tShenban.setSqrmc(officeSpiDeclareinfo.getDeclarerPerson()==null?"无":officeSpiDeclareinfo.getDeclarerPerson());//申请人(单位)名称
				tShenban.setSbxmmc(ftSupervise.getSpsxmc());//如果是行政审批事项，填写申请审批的项目具体名称，如果是社会事务服务事项，填写事项名称。
				tShenban.setSbclqd("无");//申请时提交材料清单。多个材料时，可用半角分号隔开。
				tShenban.setSbhzh(ftSupervise.getYxtywlsh());//申请人提出申办后获取的回执号，申办回执号可用于查询办事进度和结果，可以和申请信息编号一样。
				tShenban.setSqrlx("1");//申请人类型代码
				tShenban.setSqrzjhm(officeSpiDeclareinfo.getZhengjiandaima()==null?"无":officeSpiDeclareinfo.getZhengjiandaima());//申请人证件号码
				tShenban.setSbsj(date);//申请人提交申请时间
				tShenban.setXzqhdm("440303");	// 申办业务发生所在行政区划代码
				tShenban.setLxrxm(officeSpiDeclareinfo.getDeclarerPerson()==null?"无":officeSpiDeclareinfo.getDeclarerPerson());//联系人姓名
				tShenban.setLxryx("");//联系人邮箱
				tShenban.setLxrsj(officeSpiDeclareinfo.getDeclarerTel()==null?"无":officeSpiDeclareinfo.getDeclarerTel());//联系人电话
				tShenban.setLxrzjlx("");//证件类型
				tShenban.setLxrsfzjhm(officeSpiDeclareinfo.getZhengjiandaima()==null?"无":officeSpiDeclareinfo.getZhengjiandaima());//证件号码
				tShenban.setTjfs("0");//提交方式
				tShenban.setDatasource("XZSP");
				tShenban.setIsget("0");
				tShenban.setIsexchangebsdt("0");
				tShenban.setSerialnum(ftSupervise.getSerialnum());
				shenbanRepository.save(tShenban);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存受理数据
	 * 
	 * @return
	 */
	@Override
	public void saveShouli(String processDefinitionId, String taskId,String processInstanceId,String routeToTaskId) {
		/*Task task = workflowTaskService.getTaskByTaskId(taskId);
		//获取当前任务（task）的前一个任务（task）
		HistoricTaskInstance historictask = workflowTaskService.getPreviousTask(taskId);
		//获取流程的启动人
		String startProUser = workflowTaskService.startProUser(processDefinitionId, processInstanceId);
		Person person = ThreadLocalHolder.getPerson();
		OrgUnit org = RisesoftUtil.getPersonManager().getBureau(ThreadLocalHolder.getTenantId(), person.getID());
		OrgUnit parentOrg = RisesoftUtil.getPersonManager().getParent(ThreadLocalHolder.getTenantId(), person.getID());
		FtSupervise ftSupervise=null;
		try {
			//ProcessDefinitionEntity processDefinitionEntity = workflowProcessDefinitionService.findProcessDefinitionByPid(processInstanceId);
			String instanceguid = officeSpiDeclareinfoService.getGuidByProcessInstanceId(processInstanceId);
			// 获取公共信息
			ftSupervise = FTSuperviseUtil.getFTCode(jdbcTemplate, instanceguid);
			SpmApproveitem item = FTSuperviseUtil.getSpmFiled(ftSupervise.getYxtywlsh());
			
			OfficeSpiDeclareinfo officeSpiDeclareinfo = officeSpiDeclareinfoService.findByProcessInstanceId(processInstanceId);
			TShouliUnits id = new TShouliUnits();
			id.setYwlsh(ftSupervise.getYwlsh());//业务流水号
			id.setSjbbh((long)1);//数据版本号
			
			//保存受理数据
			TShouli tShouli = new TShouli();
			tShouli.setId(id);
			tShouli.setSpsxbh(ftSupervise.getSpsxbh());//审批事项编号
			tShouli.setSpsxzxbh(ftSupervise.getSpsxzxbh());//审批事项子项编号
			tShouli.setYxtywlsh(ftSupervise.getYxtywlsh());//原系统业务流水号
			tShouli.setSpsxmc(ftSupervise.getSpsxmc());//审批事项名称
			tShouli.setSqdwhsqrxm(officeSpiDeclareinfo.getDeclarerPerson()==null?"无":officeSpiDeclareinfo.getDeclarerPerson());	//申请单位或申请人名称
			tShouli.setSqdwjbrxm(officeSpiDeclareinfo.getDeclarerPerson()==null?"无":officeSpiDeclareinfo.getDeclarerPerson());//申请单位经办人姓名
			tShouli.setSqdwjbrsj(officeSpiDeclareinfo.getDeclarerMobile()==null?"无":officeSpiDeclareinfo.getDeclarerMobile());//申请单位经办人手机
			tShouli.setFlgdgxd("440303");//法律规定管辖地	
			tShouli.setFsywgxd("440303");//发生业务管辖地
			tShouli.setGdblsx((long)item.getLegaltimelimit());//规定办理时限
			tShouli.setGdblsxdw("G");//规定办理时限的单位
			tShouli.setGdsf(item.getChargeinfo()==null?"0":item.getChargeinfo().toString());//规定收费
			tShouli.setHzbh(ftSupervise.getYxtywlsh());//回执编号
			tShouli.setSlzlqd("无");	//受理资料清单
			tShouli.setSlyf(routeToTaskId.equals("buyushouli")?"1":"0");//受理与否
			tShouli.setTjfs("0");//提交方式
			tShouli.setXmmc(ftSupervise.getSpsxmc());//项目名称
			tShouli.setSldwcbrxm(person.getName());//受理单位承办人姓名
			tShouli.setSldwcbrgh("");//受理单位承办人工号
			tShouli.setSljgzzjgdm("无");//受理机关组织机构代码
			tShouli.setSljgmc(org.getName());//受理机关名称
			tShouli.setSlsj(new Date());//受理时间
			tShouli.setZrdept(org.getName());//责任单位:监察对象
			tShouli.setSyhjmc(historictask==null?"无":historictask.getName());//上一环节名称
			tShouli.setDqhjmc(task==null?"":task.getName());//当前环节名称
			tShouli.setDatasource("XZSP");
			tShouli.setIsget("0");
			tShouli.setIsexchangebsdt("0");
			tShouli.setSerialnum(ftSupervise.getSerialnum());
			shouliRepository.save(tShouli);
			
			//保存受理评价数据（未评价）
			Senate senate = new Senate();
			String guid = new GUID().toString();
			List<Object> params = new ArrayList<Object>();
			params.add(person.getID());
			String windowInfo = senateService.getOneString("select w.windowguid||','||w.entitywindowname from DT_CKGL_WINDOWPERSONADD w where w.employeeid = ? ", params);
			if(!"".equalsIgnoreCase(windowInfo)){
				senate.setWindowid(windowInfo.split(",")[0]);
				senate.setWindowname(windowInfo.split(",")[1]);
			}
			senate.setSenateguid(guid);
			senate.setSatisfaction_sl("no");
			senate.setSatisfaction("no");
			senate.setWorkflowguid(processDefinitionId);
			senate.setWorkflowinstance_guid(officeSpiDeclareinfo.getGuid());
			senate.setDeclaresn(officeSpiDeclareinfo.getDeclaresn());
			senate.setApproveitemguid(item.getApproveitemguid());
			senate.setApproveitemname(item.getApproveitemname());
			senate.setEmployeedeptname(person.getName());
			senate.setEmployeeguid(person.getID());
			senate.setDepartment_id(person.getParentID());
			senate.setDeclaredatetime(new java.sql.Date(new Date().getTime()));
			senate.setBureauguid(org.getID());
			senate.setBureauname(org.getName());
			if(org.getDN().indexOf("社区工作站")>-1){
				senate.setBureauguid(parentOrg.getID());
				senate.setBureauname(parentOrg.getName());
			}
			senate.setType("1");
			senate.setType_sl("1");
			senate.setIssenate_sl("0");
			senate.setIssenate("0");
			senate.setProblem("0");
			senateRepository.save(senate);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

	/**
	 * 保存办结过程数据
	 * 
	 * @return
	 */
	@Override
	public void saveBanjie(String processDefinitionId, String taskId,String processInstanceId) {
		Task task = workflowTaskService.getTaskByTaskId(taskId);
		//获取当前任务（task）的前一个任务（task）
		HistoricTaskInstance historictask = workflowTaskService.getPreviousTask(taskId);
		Person person = ThreadLocalHolder.getPerson();
		OrgUnit orgUnit = RisesoftUtil.getPersonManager().getBureau(ThreadLocalHolder.getTenantId(), person.getID());
		String sblsh = officeSpiDeclareinfoService.getDeclaresnByProcessInstanceId(processInstanceId);
		ShouliProcess shouli = FTSuperviseUtil.findBySblsh(sblsh);
		try {
			
			BanjieProcess banjie = new BanjieProcess();
			banjie.setSblsh_short(sblsh);
			banjie.setSblsh(shouli.getSblsh());
			banjie.setSxbm(shouli.getSxbm());
			banjie.setSxbm_qx(shouli.getSxqxbm());
			banjie.setSxbm_short(shouli.getSxbmShort());
			banjie.setBjbmmc(orgUnit.getName());
			banjie.setBjbmzzjgdm("440300");
			banjie.setXzqhdm(shouli.getXzqhdm());
			banjie.setBjjgdm("6");
			banjie.setBjjgms("通过");
			banjie.setZjgzmc("");
			banjie.setZjbh("123");
			banjie.setZjyxqx("");
			banjie.setFzgzdw("");
			banjie.setSfje(0.00);
			banjie.setJedwdm("人民币");
			banjie.setBjsj(new Date());
			banjie.setBjsj(new Date());
			banjie.setInserttime(new Date());
			//保存办结数据
			banjieRepository.save(banjie);
			//推送办结数据
			sendDataManager.sendBanjie(sblsh);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

	@Override
	public FtSupervise getSuperviseData() {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public void saveShenpiguocheng(String processInstanceId,String taskId,String sphjdm,String sphjmc) {
		Person person = ThreadLocalHolder.getPerson();
		OrgUnit org = RisesoftUtil.getPersonManager().getBureau(ThreadLocalHolder.getTenantId(), person.getID());
		String sblsh = officeSpiDeclareinfoService.getDeclaresnByProcessInstanceId(processInstanceId);
		ShouliProcess shouli = FTSuperviseUtil.findBySblsh(sblsh);

		try {
			//ftSupervise = FTSuperviseUtil.getFTCode(jdbcTemplate, instanceguid);
			/*id = new TShenpiguochengUnits();
			id.setYwlsh(ftSupervise.getYwlsh());//业务流水号
			id.setSjbbh((long)1);//数据版本号*/			
			ShenpiguochengUnits unit = new ShenpiguochengUnits(sblsh, sphjdm);
			ShenpichuliProcess shenpichuli = new ShenpichuliProcess();
			shenpichuli.setId(unit);
			shenpichuli.setSblsh(shouli.getSblsh());
			//shenpichuli.setSblsh_short(shouli.getSblshShort());;//审批事项编号
			shenpichuli.setSxbm(shouli.getSxbm());
			shenpichuli .setSxbm_short(shouli.getSxbmShort());
			shenpichuli.setSxqxbm(shouli.getSxqxbm());
			//shenpichuli.setSphjdm(sphjdm);
		
			shenpichuli.setSphjmc(sphjmc);
			
			shenpichuli.setSpbmmc("深圳市水务局");
			shenpichuli.setSpbmzzjgdm("440300");
			shenpichuli.setXzqhdm("440300");
			shenpichuli.setSprxm(person.getName());
			shenpichuli.setSprzwdm(String.valueOf(person.getDutyLevel()));
			shenpichuli.setSprzwmc(person.getDutyLevelName()==null?"无":person.getDutyLevelName());

			shenpichuli.setSphjztdm("1");
			List<Integer> opinionTypeList = new ArrayList<Integer>();
			opinionTypeList.add(0, 0);//个人意见
			List<OpinionNew> opinionNew = opinionNewService.findByTaskIdAndUserIdAndOpinionType(taskId, person.getID(), opinionTypeList);
			
			shenpichuli.setSpyj(opinionNew.size()>0?opinionNew.get(0).getContent():"无");//审批意见
			shenpichuli.setSpsj(new Date());//审批时间
			shenpichuli.setBz("");
			shenpichuli.setInserttime(new Date());
			shenpichuliRepository.save(shenpichuli);
			if("3".equals(sphjdm)){
				sendDataManager.sendShenpiguocheng(sblsh);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * 保存审批结果数据
	 */
	@Override
	public void saveShenpiJieguo(String processDefinitionId, String taskId,
			String processInstanceId) {
		// TODO Auto-generated method stub
		String sblsh = officeSpiDeclareinfoService.getDeclaresnByProcessInstanceId(processInstanceId);
		ShouliProcess shouli = FTSuperviseUtil.findBySblsh(sblsh);
		SpjgData shenpiJieguo = new SpjgData();
		shenpiJieguo.setSeq(new GUID().getUUID());
		
	}

}
