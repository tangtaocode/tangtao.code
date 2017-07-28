package net.risesoft.approve.service.gdbs.impl;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import net.risesoft.approve.entity.jpa.gdbs.ShenBanProcess;
import net.risesoft.approve.service.gdbs.ShenBanService;

@Service
public class ShenBanServiceImpl implements ShenBanService{

	@Resource(name = "routerJdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public ShenBanProcess findShenBanProcess(String sblsh) {
		// TODO Auto-generated method stub
		String sql = "select * from ex_gdbs_sb t where t.sblsh_short=?";
		return jdbcTemplate.queryForObject(sql, new Object[]{sblsh},new BeanPropertyRowMapper<ShenBanProcess>(ShenBanProcess.class));
	}


	
}
