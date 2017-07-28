package net.risesoft.approve.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import net.risesoft.approve.entity.jpa.TBdexDocinfo;
import net.risesoft.approve.service.BdexDocInfoService;

@Service(value="bdexDocInfoService")
public class BdexDocInfoServiceImpl implements BdexDocInfoService{

	@Resource(name = "routerDataSource")
	private DataSource routerDataSource;

	private JdbcTemplate jdbcTemplate;
	
	@PostConstruct
	private void afterIoc() {
		jdbcTemplate = new JdbcTemplate(this.routerDataSource);
	}

	
	@Override
	public String findById(String id) {
		List<String> list = new ArrayList<String>();
		String sql = "select  * from T_BDEX_DOCINFO where guid=?";
		list.add(id);
		List<Map<String,Object>> result = jdbcTemplate.queryForList(sql,list.toArray());
		String docname="";
		for (Map<String, Object> map : result) {
			docname = (String) map.get("docname");
		}
		return docname;
	}
	
}
