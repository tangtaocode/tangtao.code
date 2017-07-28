package net.risesoft.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.risesoft.approve.entity.jpa.RisenetEmployeeUnits;
import net.risesoft.model.OrgUnit;
import net.risesoft.model.Person;
import net.risesoft.tenant.pojo.ThreadLocalHolder;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public  class ServiceUtil {

	//获取人员的guid,部门guid,科室guid(参数为rc7上主键)

	public static RisenetEmployeeUnits riseInfo(JdbcTemplate jdbcTemplate,String rcid){
//		List<String> params = new ArrayList<String>();
		RisenetEmployeeUnits risenetInfo= new RisenetEmployeeUnits();
//		//查询人员部门和科室信息
//		String sql = " select e.employee_guid employeeGuid,e.department_guid departmentGuid,t.superior_guid bureauGuid from risenet_employee e,risenet_department t "
//				+ "where e.department_guid=t.department_guid and e.rcid_oa=?";
//		params.add(rcid);
//		try{
//			//RowMapper<RisenetEmployeeUnits> rm = ParameterizedBeanPropertyRowMapper.newInstance(RisenetEmployeeUnits.class);
//			risenetInfo = jdbcTemplate.queryForObject(sql, params.toArray(),new BeanPropertyRowMapper<RisenetEmployeeUnits>(RisenetEmployeeUnits.class));
//		}catch(Exception e){
//			e.printStackTrace();
//		}
		//获取当前登录用户信息，拼接为验证者信息
				Person person = ThreadLocalHolder.getPerson();
				OrgUnit org = RisesoftUtil.getPersonManager().getBureau(ThreadLocalHolder.getTenantId(), ThreadLocalHolder.getPerson().getID());
				String bureau = org.getID();		
				String depart = person.getOrganization().getID();
				
				risenetInfo.setBureauGuid(bureau);
				risenetInfo.setDepartmentGuid(depart);
				
		return risenetInfo;
	}
	
	/**
	 * 生成id  格式为 组织机构代码+docid+‘440303’+docinfoid+目前时间年月日时分秒。
	 * code:审批件基本信息表 的业务编号
	 */
	public static String createId(Person person,JdbcTemplate jdbcTemplate,String code){
		String rcid = person.getID();
		RisenetEmployeeUnits risenetInfo = ServiceUtil.riseInfo(jdbcTemplate,rcid);	String sql1="select t.doctypecode as doctypecode from t_bdex_doctype t where t.bguid ='"+risenetInfo.getBureauGuid()+"'";
		List<Map<String,Object>> codeList=jdbcTemplate.queryForList(sql1);
		  String sq2="select t.bureacode as bureacode from t_bdex_bureacode t where t.bureaguid ='"+risenetInfo.getBureauGuid()+"'";
		  List<Map<String,Object>> bureacodeList=jdbcTemplate.queryForList(sq2);
		   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		   String ly_time = sdf.format(new java.util.Date());	
		   ly_time=ly_time.replaceAll("-", "");
		   String one=bureacodeList.isEmpty()?"":bureacodeList.get(0).get("BUREACODE").toString();
		   String two=codeList.isEmpty()?"":codeList.get(0).get("DOCTYPECODE").toString();
		   String id=one+two+"440303"+code+ly_time;
		return id;
	}
}
