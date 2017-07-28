package net.risesoft.api.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import net.risesoft.api.ApproveManager;
import net.risesoft.common.util.ContextUtil;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.SysVariables;
import net.risesoft.fileflow.service.SpWorklistService;
import net.risesoft.model.OrgUnit;
import net.risesoft.util.RisesoftCommonUtil;
import net.risesoft.util.RisesoftUtil;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;

@Service(value="approveManager")
public class ApproveManagerImpl implements ApproveManager{

	@Resource(name="spWorklistService")
	private SpWorklistService spWorklistService;
	
	
	@Autowired
	protected TaskService taskService;
	
	@Resource(name = "routerDataSource")
	private DataSource routerDataSource;

	private JdbcTemplate jdbcTemplate;

	@PostConstruct
	private void afterIoc() {
		jdbcTemplate = new JdbcTemplate(this.routerDataSource);
	}
	
	@Override
	public Map<String,Object> todoListByUserId(String userId) {
		Map<String, Object> ret_map = new HashMap<String, Object>();
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		System.out.println("------------------用户id："+userId+"------------------------");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			String orgId = "";
			OrgUnit org = RisesoftUtil.getPersonManager().getParent("", userId);
			if(org!=null){
				orgId = org.getID();
			}
			String user = userId + ":" + orgId;
			List<Task> todoList = taskService.createTaskQuery().active().or().taskInvolvedUser(user).taskAssigneeLike("%" + userId + "%").endOr().orderByTaskCreateTime().desc().listPage(0, 6);
			for (Task task : todoList) {
				Map<String, Object> map = new HashMap<String, Object>();
				Map<String, Object> vars = taskService.getVariables(task.getId());
				String documentTitle = Strings.nullToEmpty((String) vars.get(SysVariables.DOCUMENTTITLE));
				String url = RisesoftCommonUtil.todoTaskURLPrefix+ContextUtil.getContextPath() + "/sp/document/edit?itembox=todo&taskId=" + task.getId();
				String taskCreateTime = task.getCreateTime() == null ? "" : sdf.format(task.getCreateTime());
				map.put("documentTitle", documentTitle);//标题
				map.put("url", url);//办件打开路径
				map.put("taskCreateTime", taskCreateTime);//时间
				items.add(map);
			}
			ret_map.put("success", true);
			ret_map.put("rows", items);
		} catch (Exception e) {
			e.printStackTrace();
			ret_map.put("success", false);
			ret_map.put("msg", "待办件列表异常");
		}
		return ret_map;
	}

	@Override
	public Map<String, Object> lineChart(String userId, String tenantId) {
		Map<String, Object> ret_map = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		List<Map<String,Object>> querterList = new ArrayList<Map<String,Object>>();
		List<Object> param = new ArrayList<Object>();
		ThreadLocalHolder.setTenantId(tenantId);
		//数据
		List<Integer> data = new ArrayList<Integer>();
		//获取当前年份
		Date date = new Date();
		String year = sdf.format(date);
		param.add(year);
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append("SUM(CASE when annual.TIME = '01' then annual.COUNT else 0 end) 一月, ");
		sql.append("SUM(CASE when annual.TIME = '02' then annual.COUNT else 0 end) 二月, ");
		sql.append("SUM(CASE when annual.TIME = '03' then annual.COUNT else  0 end) 三月,");
		sql.append("SUM(CASE when annual.TIME = '04' then annual.COUNT else 0 end) 四月, ");
		sql.append("SUM(CASE when annual.TIME = '05' then annual.COUNT else 0 end) 五月, ");
		sql.append("SUM(CASE when annual.TIME = '06' then annual.COUNT else 0 end) 六月, ");
		sql.append("SUM(CASE when annual.TIME = '07' then annual.COUNT else  0 end) 七月,");
		sql.append("SUM(CASE when annual.TIME = '08' then annual.COUNT else 0 end) 八月, ");
		sql.append("SUM(CASE when annual.TIME = '09' then annual.COUNT else 0 end) 九月, ");
		sql.append("SUM(CASE when annual.TIME = '10' then annual.COUNT else 0 end) 十月, ");
		sql.append("SUM(CASE when annual.TIME = '11' then annual.COUNT else  0 end) 十一月, ");
		sql.append("SUM(CASE when annual.TIME = '12' then annual.COUNT else 0 end) 十二月 ");
		
		sql.append("FROM (SELECT TO_CHAR(T.Slsj, 'YYYY') YEAR, TO_CHAR(T.Slsj, 'mm') TIME,COUNT(*) COUNT ");
		sql.append("FROM t_shouli T,spm_bureau b where T.sljgzzjgdm=b.institutioncode(+) and TO_CHAR(T.Slsj, 'YYYY')=? ");
		sql.append("GROUP BY TO_CHAR(T.Slsj, 'mm'), TO_CHAR(T.Slsj, 'YYYY')) annual");
		try{
			querterList = jdbcTemplate.queryForList(sql.toString(),param.toArray());
			for(Map<String,Object> str:querterList){
				//循环遍历list讲数据填充到横纵坐标
				data.add(Integer.parseInt(str.get("一月").toString()));
				data.add(Integer.parseInt(str.get("二月").toString()));
				data.add(Integer.parseInt(str.get("三月").toString()));
				data.add(Integer.parseInt(str.get("四月").toString()));
				data.add(Integer.parseInt(str.get("五月").toString()));
				data.add(Integer.parseInt(str.get("六月").toString()));
				data.add(Integer.parseInt(str.get("七月").toString()));
				data.add(Integer.parseInt(str.get("八月").toString()));
				data.add(Integer.parseInt(str.get("九月").toString()));
				data.add(Integer.parseInt(str.get("十月").toString()));
				data.add(Integer.parseInt(str.get("十一月").toString()));
				data.add(Integer.parseInt(str.get("十二月").toString()));
			}
			System.out.println("#####################获取成功！#########################");
			ret_map.put("data", data);
			ret_map.put("success", true);
		}catch(Exception e){
			System.out.println("#####################获取失败！#########################");
			ret_map.put("success", false);
			ret_map.put("msg", "获取失败");
			e.printStackTrace();
		}
		return ret_map;
	}

}
