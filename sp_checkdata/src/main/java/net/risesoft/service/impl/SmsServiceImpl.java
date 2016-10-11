/**
 * @Project Name:LGOneHome
 * @File Name: SmsServiceImpl.java
 * @Package Name: net.risesoft.service.impl
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2015,RiseSoft  All Rights Reserved.
 * @date 2015年12月9日 下午1:35:25
 */

package net.risesoft.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.risesoft.common.base.Pager;
import net.risesoft.database.simplejdbc.SimpleJdbcCRUD;
import net.risesoft.model.RiseEmployee;
import net.risesoft.model.personset.SmsModel;
import net.risesoft.service.ISmsService;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * @ClassName: SmsServiceImpl.java
 * @Description: TODO
 *
 * @author tt
 * @date 2015年12月9日 下午1:35:25
 * @version 
 * @since JDK 1.6
 */
@Service
public class SmsServiceImpl implements ISmsService {
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public int createInsertSms(Map rowData) {
		return SimpleJdbcCRUD.createOperate(jdbcTemplate, "SMS_PERSON", rowData).execute();
	}

	/* (非 Javadoc) 
	* <p>Title: updateSms</p> 
	* <p>Description: </p> 
	* @param rowdata
	* @return 
	* @see net.risesoft.service.ISmsService#updateSms(java.util.Map) 
	*/
	@Override
	public void updateSms(Map rowdata) {
		
	}

	/* (非 Javadoc) 
	* <p>Title: deleteSms</p> 
	* <p>Description: </p> 
	* @param rowdata 
	* @see net.risesoft.service.ISmsService#deleteSms(java.util.Map) 
	*/
	@Override
	public int deleteSms(Map rowdata) {
		String sql = "delete from SMS_PERSON where guid=?";
		return jdbcTemplate.update(sql, new Object[]{rowdata.get("guid")});
	}

	/* (非 Javadoc) 
	* <p>Title: getSmsDataList</p> 
	* <p>Description: </p> 
	* @return 
	* @see net.risesoft.service.ISmsService#getSmsDataList() 
	*/
	@Override
	public Pager getSmsDataList(Pager pager,SmsModel smsmodel) {
		String sql = "select guid,MOBILE_PHONE,NAME,DEPTNAME,to_char(CREATETIME,'yyyy-mm-dd ') as CREATETIME,to_char(UPDATETIME,'yyyy-mm-dd ') as UPDATETIME from SMS_PERSON where 1=1  ";
		List<Object> params = new ArrayList<Object>();
		if(!StringUtils.isBlank(smsmodel.getName())){
			sql+= " and name like ?";
			params.add("%"+smsmodel.getName()+"%");
		}
		sql+= " order by createtime desc ";
		pager.setTotalRows(jdbcTemplate.queryForInt("select count(guid) from ("+sql+")",params.toArray()));
		pager.setPageList(jdbcTemplate.queryForList(pager.setPageSql(sql, pager.getPageNo(), pager.getPageSize()),params.toArray()));
		return pager;
	}

	/* (非 Javadoc) 
	* <p>Title: getSmsByid</p> 
	* <p>Description: </p> 
	* @param guid
	* @return 
	* @see net.risesoft.service.ISmsService#getSmsByid(java.lang.String) 
	*/
	@Override
	public Map getSmsByid(String guid) {
		String sql = "select * from SMS_PERSON where guid=?  ";
		List<Map<String,Object>> temp = jdbcTemplate.queryForList(sql,new Object[]{guid});
		if(temp.size()>0){
			return temp.get(0);
		}else{
			return null;
		}
		
	}

	/* (非 Javadoc) 
	* <p>Title: getSmsPersonList</p> 
	* <p>Description: </p> 
	* @return 
	* @see net.risesoft.service.ISmsService#getSmsPersonList() 
	*/
	@Override
	public List<Map<String, Object>> getSmsPersonList() {
		String sql = "select * from SMS_PERSON ";
		List<Map<String,Object>> temp = jdbcTemplate.queryForList(sql);
		return temp;
	}


}
