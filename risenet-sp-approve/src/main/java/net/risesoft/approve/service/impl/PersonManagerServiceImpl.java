package net.risesoft.approve.service.impl;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import net.risesoft.approve.entity.jpa.WindowPersonSet;
import net.risesoft.approve.repository.jpa.WindowPersonRepository;
import net.risesoft.approve.service.PersonManagerService;

@Service(value="personManagerService")
public class PersonManagerServiceImpl implements PersonManagerService{

	@Autowired
	private WindowPersonRepository windowPersonRepository;
	
	@Resource(name = "routerDataSource")
	private DataSource routerDataSource;	
	private JdbcTemplate jdbctemplate;
	
	@PostConstruct
	private void afterIoc() {
		jdbctemplate = new JdbcTemplate(this.routerDataSource);
	} 
	@Override
	public List<WindowPersonSet> findAll(String usernameid,String windowname){
		if(null==windowname||windowname==""){
			return windowPersonRepository.findmanager(usernameid);
		}else{
			return windowPersonRepository.findlistone(usernameid,windowname);
		}
		
	}
	
	//修改手机号码
	@Override
	public String edit(String employeeid,String value) {
		String message="";
		try {
			windowPersonRepository.edit(employeeid,value);
			message="更新成功";
		} catch (Exception e) {			
			e.printStackTrace();
			message="更新失败";
		}
		return message;
	}
	
	//判断当前用户是否为分厅管理员
	@Override
	public int sense(String usernameid) {
		String sql="select count(*) from DT_CKGL_ADDMANAGER where employeeid=?";		
		int	size = jdbctemplate.queryForObject(sql,new String[]{usernameid},Integer.class);
		return size;
	}

	//查询所有窗口人员信息
	@Override
	public List<WindowPersonSet> findAllList(String windowname) {		
		return windowPersonRepository.findAlllist(windowname);
	}
	@Override
	public List<WindowPersonSet> findAllList() {
		return windowPersonRepository.findAlllist();
	}

}
