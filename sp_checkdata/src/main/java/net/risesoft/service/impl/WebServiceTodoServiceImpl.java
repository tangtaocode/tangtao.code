/**
 * @ProjectName:zjjHome
 * @FileName: WebServiceTodoServiceImpl.java
 * @PackageName: net.risesoft.service.impl
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2015,RiseSoft  All Rights Reserved.
 * @date May 9, 2015 5:35:57 PM
 */
package net.risesoft.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import net.risesoft.database.simplejdbc.SimpleJdbcCRUD;
import net.risesoft.model.RiseEmployee;
import net.risesoft.service.IWebServiceTodoService;
import net.risesoft.webservice.client.OAGtasksClient;

/**
 * @ClassName: WebServiceTodoServiceImpl.java
 * @Description: TODO
 *
 * @author kun
 * @date May 9, 2015 5:35:57 PM
 * @version 
 * @since JDK 1.6
 */
@Service
public class WebServiceTodoServiceImpl implements IWebServiceTodoService {
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public boolean oaTodoList(RiseEmployee em) {
		try {
			List<Map<String, Object>> serviceList = new ArrayList<Map<String,Object>>();
			if(WSisOK("{AC100A59-0000-0000-620D-F9DF00003572}")){
				serviceList = OAGtasksClient.getOAGtasks(em.getEmployee_guid(),em.getEmployee_loginname());
			}
			jdbcTemplate.update("delete from T_OA_TODO t where t.employee_guid=?",em.getEmployee_guid());
			for(Map<String, Object> map:serviceList){
				SimpleJdbcCRUD.createInsert(jdbcTemplate, "T_OA_TODO", map).execute();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean WSisOK(String wsdlGUID){
		List<Map<String, Object>> list = jdbcTemplate.queryForList("select t.* from T_ONEHOME_SERVICE t where t.guid=?",wsdlGUID);
		if(list!=null&&list.size()>0){
			Map<String, Object> map = list.get(0);
			if("1".equals((String)map.get("ISOPEN"))){
				System.out.println((String)map.get("NAME")+"  服务开启");
				return true;
			}else{
				System.out.println((String)map.get("NAME")+"  服务关闭");
				return false;
			}
		}else{
			return false;
		}
	}

	@Override
	public String WSisOK() {
		List<Map<String, Object>> list = jdbcTemplate.queryForList("select t.* from T_ONEHOME_SERVICE t order by ISOPEN desc");
		StringBuffer sbBuffer = new StringBuffer("");
		for(Map<String, Object> map:list){
			if("1".equals((String)map.get("ISOPEN"))){
				sbBuffer.append("<span style='color:green'>"+map.get("NAME")+"成功获取待办</span>");
			}else{
				sbBuffer.append("<span style='color:red'>"+map.get("NAME")+"获取待办失败</span>");
			}
		}
		return sbBuffer.toString();
	}

	@Override
	public List<Map<String, Object>> getAllWebService() {
		return jdbcTemplate.queryForList("select t.* from T_ONEHOME_SERVICE t");
	}

	@Override
	public void updateService(Map<String, Object> webMap) {
		SimpleJdbcCRUD.createUpdate(jdbcTemplate,"T_ONEHOME_SERVICE", webMap).execute();
	}
}

