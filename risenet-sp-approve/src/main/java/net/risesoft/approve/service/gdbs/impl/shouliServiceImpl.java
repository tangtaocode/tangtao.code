package net.risesoft.approve.service.gdbs.impl;

import javax.annotation.Resource;

import net.risesoft.approve.entity.jpa.gdbs.ShenBanProcess;
import net.risesoft.approve.entity.jpa.gdbs.ShouliProcess;
import net.risesoft.approve.repository.jpa.gdbs.ShouliRepository;
import net.risesoft.approve.service.gdbs.IShouLiService;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class shouliServiceImpl implements IShouLiService{
	
	@Resource
	private ShouliRepository shouliRepository;

	@Resource(name = "routerJdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public int updateShouliStatus(String status, String sblsh_short) {
		// TODO Auto-generated method stub
		return shouliRepository.updateStatus(status, sblsh_short);
	}

	@Override
	public ShouliProcess findBySblsh(String sblsh) {
		String sql = "select * from ex_gdbs_sl t where t.sblsh_short=?";
		return jdbcTemplate.queryForObject(sql, new Object[]{sblsh},new BeanPropertyRowMapper<ShouliProcess>(ShouliProcess.class));
	}


}
