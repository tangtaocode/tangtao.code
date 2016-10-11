package net.risesoft.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.risesoft.dao.IEmployeeDao;
import net.risesoft.model.RiseEmployee;
import net.risesoft.service.IEmployeeService;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements IEmployeeService{

	@Resource
	private IEmployeeDao employeeDao;
	
	@Resource 
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public Map<String, Object> getUserBureau(String userDepartGUID) {
		String sql = "SELECT t.department_guid,t.department_name,t.isbureau FROM risenet_department t where t.isbureau='1' "
				+ "START WITH t.department_guid = ? CONNECT BY NOCYCLE PRIOR t.superior_guid = t.department_guid";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, userDepartGUID);
		if(list!=null&&list.size()>0)
			return list.get(0);
		else
			return null;
	}

	@Override
	public RiseEmployee getEmployeebyGuid(String employee_guid){
		return employeeDao.findOne(employee_guid);
	}

}
