package net.risesoft.approve.service.impl.supervise;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.risesoft.api.SendDataManager;
import net.risesoft.approve.entity.jpa.FtSupervise;
import net.risesoft.approve.entity.jpa.TTebiechengxujieguo;
import net.risesoft.approve.entity.jpa.gdbs.ShouliProcess;
import net.risesoft.approve.entity.jpa.gdbs.TbcxjgProcess;
import net.risesoft.approve.entity.jpa.gdbs.TbcxsqProcess;
import net.risesoft.approve.entity.jpa.gdbs.TebiechengxujieguoUnits;
import net.risesoft.approve.entity.jpa.gdbs.TebiechengxushenqingUnits;
import net.risesoft.approve.repository.jpa.gdbs.TebiechengxujieguoRepository;
import net.risesoft.approve.repository.jpa.gdbs.TebiechengxushenqingRepository;
import net.risesoft.approve.service.OfficeSpiDeclareinfoService;
import net.risesoft.approve.service.supervise.TeBieChengXuShenQingService;
import net.risesoft.fileflow.service.ReminderDefineService;
import net.risesoft.fileflow.service.WorkflowTaskService;
import net.risesoft.model.Person;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.RisesoftUtil;
import net.risesoft.util.SysVariables;
import net.risesoft.utilx.FTSuperviseUtil;
import net.risesoft.utilx.StringX;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service(value="teBieChengXuShenQingService")
public class TeBieChengXuShenQingServiceImpl implements TeBieChengXuShenQingService {


	@Autowired
	protected TaskService taskService;
	
	@Autowired
	protected RuntimeService runtimeService;
	
	@Resource(name="reminderDefineService")
	private ReminderDefineService reminderDefineService;

	@Autowired
	private WorkflowTaskService workflowTaskService;
	
	@Resource(name="officeSpiDeclareinfoService")
	private OfficeSpiDeclareinfoService officeSpiDeclareinfoService;
	@Resource
	private TebiechengxushenqingRepository tebiechengxushenqingRepository;
	
	@Resource
	private TebiechengxujieguoRepository tebiechengxujieguoRepository;
	@Resource(name="sendDataManager")
	private SendDataManager sendDataManager;
	@Resource(name = "routerJdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	//保存特别程序申请信息
	@Override
	public Map<String, Object> saveOrUpdate(TbcxsqProcess tebiechengxushenqing,String instanceId,String taskId) {
		Map<String,Object> map=new HashMap<String, Object>();
		Person person = ThreadLocalHolder.getPerson();
		String sblsh = officeSpiDeclareinfoService.findByGuid(instanceId).getDeclaresn();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ShouliProcess shouli = FTSuperviseUtil.findBySblsh(sblsh);
		TbcxsqProcess bean = new TbcxsqProcess();
		try {
			if(("B").equals(tebiechengxushenqing.getTbcxzl())){
				tebiechengxushenqing.setTbcxzl("B");
			}
			if(("A").equals(tebiechengxushenqing.getTbcxzl())){
				tebiechengxushenqing.setTbcxzl("A");
			}
			// 获取公共信息
			TebiechengxushenqingUnits id = new TebiechengxushenqingUnits();
			id.setSblsh_short(sblsh);
			id.setXh("1");
			bean.setId(id);
			bean.setSblsh(shouli.getSblsh());
			bean.setSxbm(shouli.getSxbm());
			bean.setSxbm_short(shouli.getSxbmShort());
			bean.setSxbm_qx(shouli.getSxqxbm());
			bean.setTbcxzl(tebiechengxushenqing.getTbcxzl());//特别程序种类
			bean.setTbcxksrq(sdf.parse(sdf.format(new Date())));//特别程序开始日期
			bean.setTbcxpzr(person.getName());//特别程序批准人
			bean.setTbcxqdlyhyj(tebiechengxushenqing.getTbcxqdlyhyj());//特别程序启动理由或依据
			bean.setAuditPersonId(tebiechengxushenqing.getAuditPersonId());//特别程序审核人员ID
			bean.setAuditPerson(tebiechengxushenqing.getAuditPerson());//特别程序审核人员
			bean.setSqnr(tebiechengxushenqing.getSqnr());//申请内容
			bean.setTbcxsx(tebiechengxushenqing.getTbcxsx());//	特别程序时限
			bean.setTbcxsxdw(tebiechengxushenqing.getTbcxsxdw()==null||tebiechengxushenqing.getTbcxsxdw()==""?"G":tebiechengxushenqing.getTbcxsxdw());//特别程序时限单位
			bean.setXzqhdm(shouli.getXzqhdm());
			bean.setInserttime(new Date());
			tebiechengxushenqingRepository.save(bean);
			//推送特别程序数据
			sendDataManager.sendTebieChengxu(1,sblsh,"1");
			TaskEntity task=workflowTaskService.getTaskByTaskId(taskId);
			String deptID = RisesoftUtil.getPersonManager().getParent(ThreadLocalHolder.getTenantId(), tebiechengxushenqing.getAuditPersonId()).getID();
			taskService.setVariable(taskId, SysVariables.TEBIECHENGXU, 1);//控制“特别程序处理”按钮，0为不显示，1位显示。
			
			String userId = ThreadLocalHolder.getPerson().getID();
			String deptId = RisesoftUtil.getPersonManager().getParent(ThreadLocalHolder.getTenantId(), userId).getID();

			taskService.getVariable(taskId, SysVariables.TASKSENDERID);
			//taskService.setAssignee(taskId, tt.getAuditPersonId()+":"+deptID);
			Map<String, Object> vars = taskService.getVariables(task.getId());

			//特别程序申请人
			taskService.setVariable(taskId, SysVariables.TEBIECHENGXUSENDERID,userId+":"+deptId);
			taskService.setVariable(taskId, SysVariables.TEBIECHENGXUSENDERNAME,ThreadLocalHolder.getPerson().getName());
			
			//特别程序审核人
			taskService.setVariable(taskId, SysVariables.TEBIECHENGXUAUDITID,tebiechengxushenqing.getAuditPersonId()+":"+deptID);
			taskService.setVariable(taskId, SysVariables.TEBIECHENGXUAUDITNAME,tebiechengxushenqing.getAuditPerson());
			//特别程序状态：特别程序待审、特别程序
			taskService.setVariable(taskId, SysVariables.TEBIECHENGXUSTATE,"特别程序待审");
			//taskService.delegateTask(taskId, tt.getAuditPersonId()+":"+deptID);//协商，发送给特别程序批准人
			runtimeService.suspendProcessInstanceById(task.getProcessInstanceId());
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public Map<String, Object> loadTbcxsq(String instanceGuid) {
		//instanceGuid = "{BFA78901-0000-0000-0CCE-494F00000001}";
		Map<String,Object> resMap = new HashMap<String, Object>();
		String date=new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
		Map<String,Object> sqMap = new HashMap<String, Object>();
		try {
			String sql = "select distinct t.bureautype sxlx,t.timelimit,t.legaltimelimit from spm_approveitem t ,office_spi_declareinfo s where t.approveitemguid=s.approveitemguid and s.workflowinstance_guid=?";
			resMap = jdbcTemplate.queryForMap(sql,instanceGuid);
			sql="select s.tbcxzl,nvl(s.sjbbh,'0') sjbbh from office_spi_declareinfo t,t_tebiechengxushenqing s where t.declaresn=s.yxtywlsh(+) and t.workflowinstance_guid=? order by s.tbcxksrq desc";
			sqMap = jdbcTemplate.queryForMap(sql,instanceGuid);
			
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
		}
		if(sqMap != null){
			resMap.put("tbcxzl",StringX.getNullString(sqMap.get("tbcxzl")));
			resMap.put("sjbbh",StringX.getNullString(sqMap.get("sjbbh")));
		}
		resMap.put("tbcxksrq", date);
		return resMap;
	}

	@Override
	public TTebiechengxujieguo findTbcxjg(String instanceId) {
		try {
			String sql = "select s.* from office_spi_declareinfo t,t_tebiechengxushenqing s where t.declaresn=s.yxtywlsh(+) and t.workflowinstance_guid=? order by s.tbcxksrq desc";
			return jdbcTemplate.queryForObject(sql, new String[]{instanceId}, new BeanPropertyRowMapper<TTebiechengxujieguo>(TTebiechengxujieguo.class));
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new TTebiechengxujieguo();
		}
	}

	@Override
	public Map<String, Object> findTbcxsq(String instanceId) {
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			String sblsh = officeSpiDeclareinfoService.findByGuid(instanceId).getDeclaresn();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sql = "select t.*,t.tbcxqdly tbcxqdlyhyj from ex_gdbs_tbcxsq t where t.sblsh_short=? and t.xh=? ";
			List<Object> params=new ArrayList<Object>();
			params.add(sblsh);
			params.add("1");
			List<Map<String, Object>> ttList = jdbcTemplate.queryForList(sql, params.toArray());
			map = ttList.get(0);//获取最新版本号的数据
			map.put("TBCXKSRQ", new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ttList.get(0).get("TBCXKSRQ")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public Map<String, Object> saveTeBieChengXuShenHe(String sPinstanceId,
			String taskId, String type) {
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			TaskEntity task=workflowTaskService.getTaskByTaskId(taskId);
			Map<String, Object> vars = taskService.getVariables(task.getId());
			/**
			 * 控制特别程序按钮，0为不显示特别程序按钮，1为显示“特别程序处理”按钮，2为显示“特别程序结果”按钮（在暂停件才显示“特别程序结果”按钮）
			 */
			if(type.equals("1")){//特别程序申请审核通过，修改状态。申请人暂停件显示“特别程序结果”按钮
				//特别程序状态：特别程序待审、特别程序
				runtimeService.activateProcessInstanceById(task.getProcessInstanceId());//激活流程
				taskService.setVariable(taskId,SysVariables.TEBIECHENGXUSTATE, "特别程序");
				taskService.setVariable(taskId,SysVariables.TEBIECHENGXU, 2);
				runtimeService.suspendProcessInstanceById(task.getProcessInstanceId());//挂起流程
			}else{//特别程序申请审核不通过，激活流程，回到特别程序申请人待办。
				runtimeService.activateProcessInstanceById(task.getProcessInstanceId());//激活流程
			}
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}
		return map;
	}
	
	@Override
	public Map<String, Object> saveTeBieChengXuJieGuo(String instanceId,String sjbbh,String tbcxjg,String jgcsrq,String tbcxjsrq,String tbcxsfje,String bz,String taskId,String processInstanceId) {
		Map<String,Object> map = new HashMap<String, Object>();

		String sblsh = officeSpiDeclareinfoService.getDeclaresnByProcessInstanceId(processInstanceId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ShouliProcess shouli = FTSuperviseUtil.findBySblsh(sblsh);
		try {
			TbcxjgProcess bean = new TbcxjgProcess();

			TebiechengxujieguoUnits id = new TebiechengxujieguoUnits();
			id.setSblsh_short(sblsh);
			id.setXh("1");
			//TTebiechengxujieguo jieguo = tebiechengxujieguoRepository.findOne(id);
			bean.setId(id);
			bean.setSblsh(shouli.getSblsh());
			bean.setSxbm(shouli.getSxbm());
			bean.setSxbm_short(shouli.getSxbmShort());
			bean.setSxqxbm(shouli.getSxqxbm());
			bean.setTbcxjg(tbcxjg);//特别程序结果
			bean.setTbcxsfje(tbcxsfje);//特别程序收费金额
			bean.setJgcsrq(sdf.parse(jgcsrq));//结果产生日期
			bean.setTbcxjsrq(sdf.parse(tbcxjsrq));//特别程序结束日期
			bean.setBz(bz);
			bean.setTbcxsfje("10");
			bean.setInserttime(new Date());
			bean.setJedwdm("人民币");
			bean.setXzqhdm(shouli.getXzqhdm());
			//保存特别程序结果
			tebiechengxujieguoRepository.save(bean);
			//推送特别程序数据
			sendDataManager.sendTebieChengxu(2,sblsh,"1");
			runtimeService.activateProcessInstanceById(processInstanceId);//结束特别程序，激活流程
			/*TaskEntity task=workflowTaskService.getTaskByTaskId(taskId);
			Map<String, Object> vars = taskService.getVariables(task.getId());
			String userId = (String) vars.get(SysVariables.TASKSENDERID);
			String user = (String) vars.get(SysVariables.SENDERUSER);
			*//**
			 * 控制特别程序按钮，0为不显示特别程序按钮，1为显示“特别程序处理”按钮，2为显示“特别程序结束”按钮（在暂停件才显示“特别程序结束”按钮）
			 *//*
			taskService.setVariable(taskId, SysVariables.TEBIECHENGXU, 2);
			taskService.delegateTask(taskId, userId);//协商,返回给特别程序申请人，如果是不通过则回到特别程序申请人待办件。
			
			if(type.equals("1")){//特别程序申请审核通过，挂起流程，回到特别程序申请人暂停件。暂停件显示“特别程序结束”按钮
				runtimeService.suspendProcessInstanceById(task.getProcessInstanceId());
			}*/
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public Map<String, Object> teBieChengXuJieShu(String processInstanceId,String taskId) {
		Map<String,Object> map=new HashMap<String, Object>();
		try {
			Task task = workflowTaskService.getTaskByTaskId(taskId);
			runtimeService.activateProcessInstanceById(task.getProcessInstanceId());//结束特别程序，激活流程
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}
		
		return map;
	}

	

}
