package net.risesoft.approve.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import net.risesoft.approve.entity.base.Pager;
import net.risesoft.approve.entity.jpa.TBdexBureacode;
import net.risesoft.approve.service.BureaCodeService;
import net.risesoft.model.Person;
import net.risesoft.utilx.StringX;
import net.sf.json.JSONObject;

@Service(value="bureaCodeServiceImpl")
public class BureaCodeServiceImpl implements BureaCodeService{

	@Resource(name = "routerDataSource")
	private DataSource routerDataSource;

	private JdbcTemplate jdbcTemplate;

	@PostConstruct
	private void afterIoc() {
		jdbcTemplate = new JdbcTemplate(this.routerDataSource);
	}
	
	@Override
	public List findAll() {
		String sql = "select t.DEPARTMENT_NAME,b.BUREACODE,b.BUREAGUID from T_BDEX_BUREACODE b,RISENET_DEPARTMENT t where b.BUREAGUID = t.DEPARTMENT_GUID order by t.TABINDEX";
		return jdbcTemplate.queryForList(sql);
	}

	
	/**
	 * 查询列表
	 */
	@Override
	public Pager findByUserID(Person person, String BUREANAME,
			String BUREACODE, Pager pager) {
		int pageNum = pager.getPageNo();
		int pageSize= pager.getPageSize();
		List<String> params = new ArrayList<String>();
		List<Map<String, Object>> returnData = null;

		StringBuilder sql=new StringBuilder();
		sql.append("select distinct t.department_name as BUREAUNAME,b.BUREACODE,b.BUREAGUID,b.RC8_DEPARTMENT_ID,b.RC8_DEPARTMENT_NAME,t.tabindex from T_BDEX_BUREACODE b ,risenet_department t where b.BUREAGUID = t.department_guid   ");
		if(BUREACODE!=null&& BUREACODE !=""){
			params.add("%"+BUREACODE+"%");
			sql.append("  and b.BUREACODE like ?");
		}
		if(BUREANAME!=null && BUREANAME !=""){
			params.add("%"+BUREANAME+"%");
			sql.append("  and t.department_name like ?");
		}
		sql.append("  order by t.tabindex ");
		
		returnData = jdbcTemplate.queryForList(sql.toString(),params.toArray());
		pager.setTotalRows(returnData.size());
		pager.setPageList(jdbcTemplate.queryForList(pager.setPageSql(sql.toString(),
				pageNum, pageSize),params.toArray()));
		return pager;
	}

	/**
	 * 修改
	 */
	@Override
	public int update(String BUREAGUID, String BUREACODE, String RC8_DEPATMENT_ID) {
		List<Object> params = new ArrayList<Object>();
		String sql = "update t_bdex_bureacode  set BUREACODE=?,RC8_DEPARTMENT_ID=? where BUREAGUID=?";
		
		params.add(BUREACODE);
		params.add(RC8_DEPATMENT_ID);
		params.add(BUREAGUID);
		return jdbcTemplate.update(sql,params.toArray());
	}


	/**
	 * 新增
	 */
	@Override
	public int insert(String BUREAGUID, String BUREACODE, String RC8_DEPATMENT_ID) {
		List<Object> params = new ArrayList<Object>();
		String sql = "insert into t_bdex_bureacode (BUREAGUID,BUREACODE,RC8_DEPATMENT_ID) values(?,?,?)";
		params.add(BUREAGUID);
		params.add(BUREACODE);
		params.add(RC8_DEPATMENT_ID);

		
		return jdbcTemplate.update(sql,params.toArray());
	}

	/**
	 * 删除
	 */
	@Override
	public int delete(String BUREAGUID) {
		List<Object> params = new ArrayList<Object>();
		String sql = "delete from t_bdex_bureacode where BUREAGUID=?";
		params.add(BUREAGUID);
		return jdbcTemplate.update(sql,params.toArray());
	}

	
	/**
	 * RC8部门单选框列表
	 * 
	 */
	@Override
	public String finddeptTree() {
		StringBuilder param=new StringBuilder();
		param.append("[");
		String sql="select t.ID as Id ,t.PARENT_ID as pid ,t.NAME as name from RC8_ORG_DEPARTMENT t where t.DELETED = '0' and t.bureau ='0' order by t.TABINDEX ";
	//	String sql="select t.superior_guid as pid,t.department_guid as gid,m.doctypename as name  from RISENET_DEPARTMENT t  right join T_BDEX_DOCTYPE m on m.bguid=t.department_guid start with  t.department_guid='{BFA7BA9B-FFFF-FFFF-AD1B-40DE0000000F}' connect by t.superior_guid=prior t.department_guid";
		List<Map<String, Object>> mapList=jdbcTemplate.queryForList(sql);
		int i=0;
		for(Map<String,Object> e : mapList){
			String pids = "" ; 
			//父节点添加nocheck状态
			if(e.get("PID")!=""&&e.get("PID")!=null&&e.get("PID").equals("999999999")){	
				pids = "parentId:'"+e.get("PID")+"', nocheck: true,";
			}else{	
				pids = "parentId:'"+e.get("PID")+"',";
			}
			if(i==mapList.size()-1){
				param.append("{id:'"+e.get("ID")+"',");
				param.append(pids);
				//param.append("docutypeGid:'"+e.get("ID")+"',");
				param.append("name:'"+e.get("NAME")+"'}]");
				//param.append("click:'clickNode();'}]");
			}else{
				param.append("{id:'"+e.get("ID")+"',");
				param.append(pids);
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

	@Override
	public int savedept(String ID,String ALLNAME,String ALLID) {
		List<Object> params = new ArrayList<Object>();
		int b = 0 ;
		//判断不为空项
		if(!StringX.isBlank(ID)){
			String sql = "UPDATE  t_bdex_bureacode t set  t.RC8_DEPARTMENT_NAME = ? ,t.RC8_DEPARTMENT_ID = ? where t.BUREAGUID = ? ";
            params.add(ALLNAME);
			params.add(ALLID);
			params.add(ID);
         b = jdbcTemplate.update(sql,params.toArray());
		}	
		return b;
	}

	
	
	
	
/*	@Override
	public void update(String bureacode,String bureaguid     ) {
		List<String> list = new ArrayList<String>();
		String sql = "update T_BDEX_BUREACODE set bureacode=? where bureaguid=?";
		list.add(bureacode);
		list.add(bureaguid);
		jdbcTemplate.update(sql, list.toArray());
	}*/
	
	
}
