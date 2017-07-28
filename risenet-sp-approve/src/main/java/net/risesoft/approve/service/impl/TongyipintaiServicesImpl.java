package net.risesoft.approve.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import net.risesoft.approve.entity.jpa.OfficeSpiDeclareinfo;
import net.risesoft.approve.entity.jpa.SpmApproveitem;
import net.risesoft.approve.entity.jpa.gdbs.ShenBanProcess;
import net.risesoft.approve.repository.jpa.OfficeSpiDeclareinfoRepository;
import net.risesoft.approve.repository.jpa.SpmApproveItemRepository;
import net.risesoft.approve.service.TongyipintaiServices;
import net.risesoft.model.Person;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.utilx.StringX;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
@Service("tongyipintaiServices")
public class TongyipintaiServicesImpl implements TongyipintaiServices {

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
	public List<Map<String, Object>> findAll(String approveItemName,String status) {
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		try{
			
			List<Map<String, Object>> listmap=new ArrayList<Map<String, Object>>();
			//查询窗口收件列表
			String sql="select t.sblsh_short,t.sxmc,t.sqrmc,to_char(t.sbsj,'yyyy-mm-dd hh24:mi:ss') sbsj,to_char(s.slsj,'yyyy-mm-dd hh24:mi:ss') slsj from ex_gdbs_sl s,ex_gdbs_sb t where t.sblsh_short=s.sblsh_short and s.status=?";
			List<Object> params=new ArrayList<Object>();
			if(StringX.isBlank(status)){
				params.add("待处理");
			}else{
				params.add("已分发");
			}
			if(!StringX.isBlank(approveItemName)){	//事项名称			
				sql += " and t.sxmc like ?";
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
	public SpmApproveitem findApproveItemByName(String name) {
		// TODO Auto-generated method stub
		return spmApproveItemRepository.findByapproveitemname(name);
	}
	@Override
	public SpmApproveitem findApproveItemByGuid(String guid) {
		return spmApproveItemRepository.findByapproveitemguid(guid);
	}
	@Override
	public void save(OfficeSpiDeclareinfo office) {
		officeSpiDeclareinfoRepository.save(office);
	}

	@Override

	public ShenBanProcess findShenbanBySblsh(String sblsh) {
		// TODO Auto-generated method stub
		try {
			String sql = "select * from ex_gdbs_sb t where t.sblsh_short=?";
			ShenBanProcess shenban =  (ShenBanProcess) jdbctemplate.queryForObject(sql, new String[]{sblsh}, new BeanPropertyRowMapper<ShenBanProcess>(ShenBanProcess.class));
			return shenban;
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ShenBanProcess();
		}
	}
	

}
