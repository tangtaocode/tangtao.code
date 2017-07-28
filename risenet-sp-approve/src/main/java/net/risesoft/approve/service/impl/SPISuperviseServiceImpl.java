package net.risesoft.approve.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import net.risesoft.approve.entity.jpa.RisenetEmployeeUnits;
import net.risesoft.approve.service.SPISuperviseService;
import net.risesoft.model.Person;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.ServiceUtil;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * 审批办理--待办件实现类
 * @author Administrator
 *
 */
@Service
public class SPISuperviseServiceImpl implements SPISuperviseService{
	@Resource(name = "routerDataSource")
	private DataSource routerDataSource;
	
	private JdbcTemplate jdbctemplate;
	
	@PostConstruct
	private void afterIoc() {
		jdbctemplate = new JdbcTemplate(this.routerDataSource);
	} 
	

	@Override
	public List<Map<String, Object>> findAll() {
		Person person=ThreadLocalHolder.getPerson();
		String userid=person.getID();
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try{
			List<Map<String,Object>> listmap=new ArrayList<Map<String,Object>>();
			RisenetEmployeeUnits risenetInfo=ServiceUtil.riseInfo(jdbctemplate, userid);
			
			
			
			String sql="select a.approveItemName,a.estimateEndDate,a.declaresn from SPI_Supervise a";
			listmap=jdbctemplate.queryForList(sql);
			list.addAll(listmap);
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}

}
