package net.risesoft.approve.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import net.risesoft.approve.service.RisenetDepartmentService;

@Service(value="risenetDepartmentService")
public class RisenetDepartmentServiceImpl implements RisenetDepartmentService{
	@Resource(name = "routerDataSource")
	private DataSource routerDataSource;
	private JdbcTemplate jdbcTemplate;

	@PostConstruct
	private void afterIoc() {
		jdbcTemplate = new JdbcTemplate(this.routerDataSource);
	}
	
	/*
	 * SPM委办局列表，按照BUREAUTABINDEX排序
	 * 
	 */
	public List<Map<String, Object>> findAll() {
		String sql = "select d.BUREAUNAME key,d.BUREAUGUID value from SPM_BUREAU d where d.ISSTREET= '0' order by d.BUREAUSN";
		return jdbcTemplate.queryForList(sql);
	}
	
	
	/*
	 * 查询所有证照类型
	 * 
	 */
	public List<Map<String, Object>> findAllZZType() {
		String sql = "select t.DOCTYPENAME key,t.GUID value from t_bdex_doctype t order by t.GUID";
		return jdbcTemplate.queryForList(sql);
	}
	
	/*
	 * 带条件查询所有证照类型
	 * 
	 */
	public List<Map<String, Object>> findAllZZType(String produceOrgan) {
		List<String> params = new ArrayList<String>();
		params.add(produceOrgan);
		String sql = "select t.DOCTYPENAME key,t.GUID value from t_bdex_doctype t where rtrim(t.BGUID)=? order by t.GUID";
		return jdbcTemplate.queryForList(sql,params.toArray());
	}

}
