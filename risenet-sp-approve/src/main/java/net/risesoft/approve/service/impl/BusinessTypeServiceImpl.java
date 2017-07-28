package net.risesoft.approve.service.impl;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.json.Json;
import javax.sql.DataSource;

import org.activiti.engine.impl.util.json.JSONArray;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import net.risesoft.approve.entity.jpa.RisenetEmployeeUnits;
import net.risesoft.approve.entity.jpa.XzqlXzspLists;
import net.risesoft.approve.service.BusinessTypeService;
import net.risesoft.model.Person;
import net.risesoft.util.ConmonUtil;
import net.risesoft.util.GUID;
import net.risesoft.util.ServiceUtil;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
@Service(value="businessTypeServiceImpl")
public class BusinessTypeServiceImpl implements BusinessTypeService{
	
	@Resource(name = "routerDataSource")
	private DataSource routerDataSource;

	private JdbcTemplate jdbcTemplate;
	@PostConstruct
	private void afterIoc() {
		jdbcTemplate = new JdbcTemplate(this.routerDataSource);
	}
   
	
	/*
     * 单位名树列表
    */
	@Override
	public String selectTree() {
		StringBuilder param=new StringBuilder();
		//String sql="select distinct m.guid as id,t.superior_guid as BGID,t.department_guid as gid,m.doctypename as name,m.doctypecode as code  from RISENET_DEPARTMENT t right join T_BDEX_DOCTYPE m on m.bguid=t.department_guid  where 1=1 order by name";
		//String sql="select t.ID as Id ,t.PID as pid ,t.NAME as name from VIEW_XZSP_ITEMTREEDIR t where t.ID not in (select h.guid from xzql_hide h where h.status='-1') order by t.ORDERNO ";
	    String sql="select ID,NAME,PID,TYPE,ORDERNO,CODE from  (select s.bureauguid id,s.bureauname name,'999999999' pid,1 type,s.bureausn orderno,'' code from spm_bureau s  union all  select i.itemid id,i.approveitemname name, nvl(i.catalogsguid,i.adminorgid) pid,3 type,i.orderno,i.approveitembm code   from (select bl.approveitemguid itemid,bl.approveitemname, '' catalogsguid, bl.sxbm approveitembm,bl.adminbureauguid adminorgid, 0 orderno  from spm_approveitem bl) i ) order by type, pid,ORDERNO ";
		List<Map<String, Object>> mapList=jdbcTemplate.queryForList(sql);
		int i=0;
		param.append("[");
		for(Map<String,Object> e : mapList){
			if(i==mapList.size()-1){
				param.append("{id:'"+e.get("ID")+"',");
				param.append("parentId:'"+e.get("PID")+"',");
				//param.append("docutypeGid:'"+e.get("ID")+"',");
				param.append("name:'"+e.get("NAME")+"'}]");
				//param.append("click:'clickNode();'}]");
			}else{
				param.append("{id:'"+e.get("ID")+"',");
				param.append("parentId:'"+e.get("PID")+"',");
				//param.append("docutypeGid:'"+e.get("ID")+"',");
				param.append("name:'"+e.get("NAME")+"'},");
				//param.append("click:'clickNode(this);'},");
				i++;
			}
		}
		JSONObject  json= new JSONObject();
		json.put("treeNodes", param.toString());
		return json.toString();
	}
	
    /*
     * 根据选择的业务类型 部门的id来查找对呀的部门
    */
	@Override
	public List getDeptInfo(String deptId,String docutypeGid) {
		String sql="select DISTINCT m.guid as id, t.department_guid as gid,m.doctypename as name,t.department_name as deptName from RISENET_DEPARTMENT t left join T_BDEX_DOCTYPE m on m.bguid=t.department_guid where t.department_guid=? and  m.guid=?";
		List<Map<String,Object>> a=jdbcTemplate.queryForList(sql, deptId,docutypeGid);
		List b=new ArrayList();
		for(Map e: a){
			e.put("'ID'","'"+e.get("ID")+"'");
			e.put("'GID'","'"+e.get("GID")+"'");
			e.put("'NAME'","'"+e.get("NAME")+"'");
			e.put("'DEPTNAME'","'"+e.get("DEPTNAME")+"'");
			e.remove("GID");
			e.remove("NAME");
			e.remove("DEPTNAME");
			e.remove("ID");
			b.add(e);
		}
		return b;
	}

	
	

	/*
     * 保存
    */
	public String save(String name, String itemId,Person person,String materialDis) {
		String result="SUCCESS";
		//String id=ConmonUtil.createId(person, jdbcTemplate, itemId);
		 GUID gid1 = new GUID();
		String insert="insert into XZQL_XZSP_TYPEINFO(id,ITEMID,DESCRIBE,MATERIALNAME) values ('"+gid1.toString()+"','"+itemId+"','"+materialDis+"','"+name+"')";
		try{
		jdbcTemplate.execute(insert);
		}catch(Exception e){
			e.getStackTrace();
			result="ERROR";
		}
		return result;
	}

}
