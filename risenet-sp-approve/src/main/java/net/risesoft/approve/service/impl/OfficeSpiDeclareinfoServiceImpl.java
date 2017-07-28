package net.risesoft.approve.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import net.risesoft.approve.entity.jpa.OfficeSpiDeclareinfo;
import net.risesoft.approve.repository.jpa.OfficeSpiDeclareinfoRepository;
import net.risesoft.approve.service.OfficeSpiDeclareinfoService;

@Service(value="officeSpiDeclareinfoService")
public class OfficeSpiDeclareinfoServiceImpl implements OfficeSpiDeclareinfoService{

	@Autowired
	private OfficeSpiDeclareinfoRepository officeSpiDeclareinfoRepository;
	@Resource(name = "routerJdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	
	@Override
	public void updateOfficeSpiDeclareinfo(String guid, String processInstanceId) {
		OfficeSpiDeclareinfo osd=officeSpiDeclareinfoRepository.findOne(guid);
		if(osd!=null){
			osd.setProcessInstanceId(processInstanceId);
		}
		officeSpiDeclareinfoRepository.save(osd);
	}

	@Override
	public String getDeclaresnByProcessInstanceId(String processInstanceId) {
		return officeSpiDeclareinfoRepository.getDeclaresnByProcessInstanceId(processInstanceId);
	}

	@Override
	public OfficeSpiDeclareinfo findByGuid(String guid) {
		return officeSpiDeclareinfoRepository.findByGuid(guid);
	}
	
	@Override
	public String getGuidByProcessInstanceId(String processInstanceId) {
		return officeSpiDeclareinfoRepository.getGuidByProcessInstanceId(processInstanceId);
	}

	@Override
	public String findApproveitemguidByProcessInstanceId(String processInstanceId) {
		return officeSpiDeclareinfoRepository.findApproveitemguidByProcessInstanceId(processInstanceId);
	}

	@Override
	public OfficeSpiDeclareinfo findByProcessInstanceId(String processInstanceId) {
		return officeSpiDeclareinfoRepository.findByProcessInstanceId(processInstanceId);
	}

	//补交告知通知单
	@Override
	public Map<String, Object> getAdviceForm(String instanceId) {
		try{
			String sql = " select distinct s.approveitemname itemname,s.approveitemname title,t.approveitemguid itemguid,t.declarerperson person,t.declarermobile mobile,t.declaresn,nvl(t.listtype,'无') typeguid,t.datafromtype from office_spi_declareinfo t,spm_approveitem s where "
					+ "t.approveitemguid=s.approveitemguid(+) and t.workflowinstance_guid=?";
			Map<String,Object> map = jdbcTemplate.queryForMap(sql, new String[]{instanceId});
			return map;
		}catch(EmptyResultDataAccessException ex){
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	//审批受理单
	@Override
	public Map<String, Object> getBanliDanForm(String processInstanceId) {
		try{
			String sql = " select distinct t.XIANGMUMINGCHENG,t.DECLARERPERSON,"
					+ "t.ADDRESS ,t.DECLARERLXR ,t.DECLARERTEL ,"
					+ "t.BUREAUNAME ,t.DECLAREDATETIME,t.DECLARESN,"
					+ "t.EMPLOYEEDEPTNAME ,t.ZIXUNDIANHUA,t.LIMITIME,t.CHENGNUORIQI"
					+ " from office_spi_declareinfo t where t.processInstanceId=?";
			Map<String,Object> map = new HashMap<String, Object>();
			map = jdbcTemplate.queryForMap(sql, new String[]{processInstanceId});
			return map;
		}catch(EmptyResultDataAccessException ex){
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	//审批办结单
	@Override
	public Map<String, Object> getBanJieDanForm(String guid) {
		try{
			String sql = " select distinct t.docway,t.certifyway,"
					+ "t.doctitle ,t.docnumber ,t.senddate ,"
					+ "t.certifyname ,t.certifynumber,t.banjieunit,"
					+ "t.enddate from office_spi_banjiejilu t where t.workflowinstance_guid=?";
			Map<String,Object> map = new HashMap<String, Object>();
			map = jdbcTemplate.queryForMap(sql, new String[]{guid});
			return map;
		}catch(EmptyResultDataAccessException ex){
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}


}
