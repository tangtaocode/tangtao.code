package net.risesoft.approve.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import net.risesoft.approve.entity.jpa.SpmTemplate;
import net.risesoft.approve.repository.jpa.SpmTemplateRepository;
import net.risesoft.approve.service.SpmTemplateService;
import net.risesoft.common.util.GuidUtil;
import net.risesoft.utilx.StringX;

@Service(value="spmTemplateService")
public class SpmTemplateServiceImpl implements SpmTemplateService {

	@Resource(name = "routerDataSource")
	private DataSource routerDataSource;
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public SpmTemplateRepository templateRepository;
	
	@PostConstruct
	private void afterIoc() {
		jdbcTemplate = new JdbcTemplate(this.routerDataSource);
	}
	
	@Override
	public SpmTemplate findOne(String guid) {
		//String sql="select * from spm_template where templateguid=? ";
		//SpmTemplate spmTemplate = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<SpmTemplate>(SpmTemplate.class), guid);
		SpmTemplate spmTemplate = templateRepository.findOne(guid);
		return spmTemplate;
	}

	@Override
	public List<SpmTemplate> findByApproveItemGuid(String approveItemGuid) {
		//String sql="select * from spm_template where approveitemguid=? ";
		//List<SpmTemplate> spmTemplates = jdbcTemplate.query(sql, new BeanPropertyRowMapper<SpmTemplate>(SpmTemplate.class), approveItemGuid);
		List<SpmTemplate> spmTemplates = templateRepository.findByapproveitemGuid(approveItemGuid);
		return spmTemplates;
	}
	
	@Override
	public List<SpmTemplate> findAll() {
		//String sql="select * from spm_template ";
		//List<SpmTemplate> spmTemplates = jdbcTemplate.query(sql, new BeanPropertyRowMapper<SpmTemplate>(SpmTemplate.class));
		return templateRepository.findAll();
	}

	@Override
	public SpmTemplate saveOrUpdate(SpmTemplate spmTemplate) {
		if(StringX.isBlank(spmTemplate.getTemplateGuid())){
			spmTemplate.setTemplateGuid(GuidUtil.genGuid());
		}
		return templateRepository.save(spmTemplate);
	}

	@Override
	public void delete(String guid) {
		//String sql = " delete from spm_template t where t.templateguid=?";
		//int result = jdbcTemplate.update(sql, guid);
		templateRepository.delete(guid);
	}
	
}
