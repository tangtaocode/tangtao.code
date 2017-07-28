package net.risesoft.approve.service.impl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import net.risesoft.approve.entity.jpa.OfficeSpiDeclareinfo;
import net.risesoft.approve.entity.jpa.RisenetEmployeeUnits;
import net.risesoft.approve.entity.jpa.SpmApproveitem;
import net.risesoft.approve.repository.jpa.OfficeSpiDeclareinfoRepository;
import net.risesoft.approve.repository.jpa.SpmApproveItemRepository;
import net.risesoft.approve.repository.jpa.SpmBureauRepository;
import net.risesoft.approve.service.WindowApproveService;
import net.risesoft.model.Person;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.ServiceUtil;
import net.risesoft.utilx.StringX;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


/**
 * 窗口收件实现类
 */
@Service(value="windowApprove")
public class WindowApproveImpl implements WindowApproveService{
	
	@Resource(name = "routerDataSource")
	private DataSource routerDataSource;
	
	private JdbcTemplate jdbctemplate;
	
	@PostConstruct
	private void afterIoc() {
		jdbctemplate = new JdbcTemplate(this.routerDataSource);
	} 
	@Autowired
	private SpmApproveItemRepository spmApproveItemRepository;
	
	@Resource(name = "officeSpiDeclareinfoRepository")
	private OfficeSpiDeclareinfoRepository officeSpiDeclareinfoRepository;
	@Override
	public List<Map<String, Object>> findAll(String approveItemName) {
		Person person  =  ThreadLocalHolder.getPerson();
		//查询到当前用户在rc7中的id,
		String userID = person.getID();
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		try{
			
			List<Map<String, Object>> listmap=new ArrayList<Map<String, Object>>();
			//RisenetEmployeeUnits 为当前用户的信息（包括人员id,部门id,科室id）,通过rc7的id查询到该用户在新工程中的个人信息
			//RisenetEmployeeUnits risenetInfo = ServiceUtil.riseInfo(jdbctemplate,userID);
			//查询窗口收件列表
			String sql="select distinct a.approveitemguid,a.APPROVEITEMTYPE,a.APPROVEITEMNAME,d.BUREAUNAME,a.BUREAUTYPE,"
					+ "CASE "
					+ " WHEN a.TIMELIMIT=-1 THEN '视情况而定' "
					+ " WHEN a.TIMELIMIT=0 THEN '即来即办' "
					+ " ELSE a.TIMELIMIT "
					+ " END AS TIMELIMIT "
					+ " from spm_approveitem a,xzql_xzsp_windowofitem s,xzql_xzsp_window w ,spm_bureau d"
					+ " where  a.approveitemguid=s.itemid "
					+ " and trim(a.adminbureauguid) = trim(d.bureauguid) "
					+ "and a.approveplace='0' "
					+ "and a.workflowguid is not null "
					+ "and a.approveitemstatus='运行' "
					+ "and w.guid=s.windowguid and instr(w.windowusers,?)>0";
			
			List<Object> params=new ArrayList<Object>();
			params.add(userID);
			if(!StringX.isBlank(approveItemName)){	//事项名称			
				sql += " and a.approveItemName like ?";
				params.add("%"+approveItemName+"%");			
			}	
						
			listmap =jdbctemplate.queryForList(sql,params.toArray());			
			list.addAll(listmap);
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public SpmApproveitem findApproveItemByGuid(String guid) {
		return spmApproveItemRepository.findByapproveitemguid(guid);
	}
	@Override
	public void save(OfficeSpiDeclareinfo office) {
		officeSpiDeclareinfoRepository.save(office);
	}

	
}
