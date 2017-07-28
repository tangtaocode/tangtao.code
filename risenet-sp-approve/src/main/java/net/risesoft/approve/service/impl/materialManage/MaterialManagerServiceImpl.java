package net.risesoft.approve.service.impl.materialManage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import net.risesoft.approve.entity.base.Pager;
import net.risesoft.approve.entity.jpa.RisenetEmployeeUnits;
import net.risesoft.approve.service.MaterialShareService;


import net.risesoft.approve.service.materialManage.MaterialManageService;
import net.risesoft.model.OrgUnit;
import net.risesoft.model.Person;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.GUID;
import net.risesoft.util.RisesoftUtil;
import net.risesoft.util.ServiceUtil;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("materialManageService")
@Transactional
public class MaterialManagerServiceImpl implements MaterialManageService {

	@Resource(name = "routerDataSource")
	private DataSource routerDataSource;

	private JdbcTemplate jdbcTemplate;
	 
	@PostConstruct
	private void afterIoc() {
		jdbcTemplate = new JdbcTemplate(this.routerDataSource);
	}
	
	/**
	 * 资产列表
	 */
	@Override
	public Pager materialList(Person person, String material_code,
			String material_name, Pager pager) {
		int pageNum = pager.getPageNo();
		int pageSize= pager.getPageSize();
		List<String> params = new ArrayList<String>();
		List<Map<String, Object>> returnData = null;

		StringBuilder sql=new StringBuilder();
		sql.append("select t.materialguid,t.material_code,t.material_name,t.material_num,t.unit,t.tabindex,to_char(t.definetime,'yyyy-mm-dd') definetime,t.defineperson from MATERIAL_DEFINE t where 1=1");
		if(material_code!=null&& material_code !=""){
			params.add("%"+material_code+"%");
			sql.append("  and t.material_code like ?");
		}
		if(material_name!=null && material_name !=""){
			params.add("%"+material_name+"%");
			sql.append("  and t.material_name like ?");
		}
		sql.append(" order by t.tabindex ");
	
		returnData = jdbcTemplate.queryForList(sql.toString(),params.toArray());
		pager.setTotalRows(returnData.size());
//		List<Map<String,Object>> dateList = new ArrayList<Map<String,Object>>();
//		dateList=jdbcTemplate.queryForList(pager.setPageSql(sql.toString(),pageNum, pageSize),params.toArray());
//		OrgUnit org = RisesoftUtil.getPersonManager().getBureau(ThreadLocalHolder.getTenantId(), ThreadLocalHolder.getPerson().getID());
		
		pager.setPageList(jdbcTemplate.queryForList(pager.setPageSql(sql.toString(),
				pageNum, pageSize),params.toArray()));
		return pager;
	}
	
	/**
	 * 物料修改
	 */
	@Override
	public int update(Person person,String materialguid, String material_code, String material_name,
			String material_num, String unit, String tabindex,
			String definetime) {
		List<Object> params = new ArrayList<Object>();
		String sql = "update MATERIAL_DEFINE set updateperson=?,material_num=?,tabindex=?,definetime=to_date( ? ,'yyyy-MM-dd'),updatetime=sysdate where materialguid=?";
		params.add(person.getName());
//		params.add(material_code);
//		params.add(material_name);
		params.add(material_num);
//		params.add(unit);
		params.add(tabindex);
		params.add(definetime);
		params.add(materialguid);
		return jdbcTemplate.update(sql,params.toArray());
	}
	
	/**
	 * 物料修改
	 */
	@Override
	public int updateMaterialNum(Person person,String ID,String material_num) {
		List<Object> params = new ArrayList<Object>();
		String sql = "update MATERIAL_DEFINE set updateperson=?,material_num=material_num+?,updatetime=sysdate where materialguid=?";
		params.add(person.getName());
		params.add(material_num);
		params.add(ID);
		return jdbcTemplate.update(sql,params.toArray());
	}

	/**
	 * 新增物料
	 */
	@Override
	public int insert(Person person,String ID,String material_code,String material_name,String material_num,String unit,String tabindex) {
		List<Object> params = new ArrayList<Object>();
		String sql = "insert into MATERIAL_DEFINE (materialguid,material_code,material_name,material_num,unit,tabindex,defineperson,definetime) values(?,?,?,?,?,?,?,SYSDATE)";
		params.add(ID);
		params.add(material_code);
		params.add(material_name);
		params.add(material_num);
		params.add(unit);
		params.add(tabindex);
		params.add(person.getName());
		return jdbcTemplate.update(sql,params.toArray());
	}
	
	/**
	 * 新增物料日志
	 */
	@Override
	public int insertLog(Person person,String material_code,String material_name,String material_num,String unit) {
		List<Object> params = new ArrayList<Object>();
		String sql = "insert into material_define_log (material_code,material_name,material_num,unit,updateperson,updatetime) values(?,?,?,?,?,SYSDATE)";
		params.add(material_code);
		params.add(material_name);
		params.add(material_num);
		params.add(unit);
		params.add(person.getName());
		return jdbcTemplate.update(sql,params.toArray());
	}

	/**
	 * 删除材料
	 */
	@Override
	public int delete(String ID) {
		List<Object> params = new ArrayList<Object>();
		String sql = "delete from MATERIAL_DEFINE where materialguid=?";
		params.add(ID);
		return jdbcTemplate.update(sql,params.toArray());
	}
	
	/**
	 * 资产领用列表
	 */
	@Override
	public Pager materialUsingList(Person person, String material_code,
			String material_name, Pager pager) {
		int pageNum = pager.getPageNo();
		int pageSize= pager.getPageSize();
		List<String> params = new ArrayList<String>();
		List<Map<String, Object>> returnData = null;

		StringBuilder sql=new StringBuilder(); 
		sql.append("select u.guid,t.material_code,t.material_name,u.using_num,to_char(u.using_time,'yyyy-mm-dd') using_time,u.description,u.using_name,u.defineperson from MATERIAL_DEFINE t,material_using_define u where t.materialguid=u.material_guid and 1=1");
		if(material_code!=null&& material_code !=""){
			params.add("%"+material_code+"%");
			sql.append("  and t.material_code like ?");
		}
		if(material_name!=null && material_name !=""){
			params.add("%"+material_name+"%");
			sql.append("  and t.material_name like ?");
		}
		sql.append(" order by u.using_time desc ");
	
		returnData = jdbcTemplate.queryForList(sql.toString(),params.toArray());
		pager.setTotalRows(returnData.size());
//		List<Map<String,Object>> dateList = new ArrayList<Map<String,Object>>();
//		dateList=jdbcTemplate.queryForList(pager.setPageSql(sql.toString(),pageNum, pageSize),params.toArray());
//		OrgUnit org = RisesoftUtil.getPersonManager().getBureau(ThreadLocalHolder.getTenantId(), ThreadLocalHolder.getPerson().getID());
		
		pager.setPageList(jdbcTemplate.queryForList(pager.setPageSql(sql.toString(),
				pageNum, pageSize),params.toArray()));
		return pager;
	}
	
	/**
	 * 领用修改
	 */
	@Override
	public int usingUpdate(Person person,String guid, String using_num, String description,String using_name) {
		List<Object> params = new ArrayList<Object>();
		String sql = "update MATERIAL_USING_DEFINE set using_num=?,using_name=?,description=?,updateperson=?,updatetime=sysdate where guid=?";
		params.add(using_num);
		params.add(using_name);
		params.add(description);
		params.add(person.getName());
		params.add(guid);
		return jdbcTemplate.update(sql,params.toArray());
	}
	
	/**
	 * 获取资产列表
	 */
	@Override
	public List<Map<String, Object>> getMaterialList() {
		List<Map<String, Object>> returnData = null;
		StringBuilder sql=new StringBuilder();
		sql.append("select t.material_name key,t.materialguid value from MATERIAL_DEFINE t where 1=1");
		sql.append(" order by t.definetime desc ");
		returnData = jdbcTemplate.queryForList(sql.toString());
		//pager.setPageList(jdbcTemplate.queryForList(pager.setPageSql(sql.toString(),1, 10000)));
		return returnData;
	}
	
	/**
	 * 获取数据字典列表
	 */
	@Override
	public List<Map<String, Object>> getDictionaryList() {
		List<Map<String, Object>> returnData = null;
		StringBuilder sql=new StringBuilder();
		sql.append("select t.material_name key,t.guid value from MATERIAL_DICTIONARY t where 1=1");
		sql.append(" order by t.create_time desc ");
		returnData = jdbcTemplate.queryForList(sql.toString());
		//pager.setPageList(jdbcTemplate.queryForList(pager.setPageSql(sql.toString(),1, 10000)));
		return returnData;
	}
	
	/**
	 * 新增领用
	 */
	@Override
	public int usingInsert(Person person,String ID,String materialguid,String using_num,String using_name,String description) {
		List<Object> params = new ArrayList<Object>();
		String sql = "insert into MATERIAL_USING_DEFINE (guid,material_guid,using_num,using_name,description,defineperson,using_time,updatetime) values(?,?,?,?,?,?,sysdate,sysdate)";
		params.add(ID);
		params.add(materialguid);
		params.add(using_num);
		params.add(using_name);
		params.add(description);
		params.add(person.getName());
		return jdbcTemplate.update(sql,params.toArray());
	}
	
	/**
	 * 删除领用
	 */
	@Override
	public int usingDelete(String ID) {
		List<Object> params = new ArrayList<Object>();
		String sql = "delete from MATERIAL_USING_DEFINE where guid=?";
		params.add(ID);
		return jdbcTemplate.update(sql,params.toArray());
	}
	
	/**
	 * 资产维修列表
	 */
	@Override
	public Pager materialRepairList(Person person, String material_code,
			String material_name, Pager pager) {
		int pageNum = pager.getPageNo();
		int pageSize= pager.getPageSize();
		List<String> params = new ArrayList<String>();
		List<Map<String, Object>> returnData = null;

		StringBuilder sql=new StringBuilder(); 
		sql.append("select u.guid,t.material_code,t.material_name,u.REPAIR_NUM,u.REPAIR_FUNDS,u.REPAIR_PERSON,u.REPAIR_REASON,to_char(u.REPAIR_TIME_START,'yyyy-mm-dd') repair_time_start,to_char(u.repair_time_done,'yyyy-mm-dd') repair_time_done,u.CHARGE_PERSON,u.CREATE_PERSON from MATERIAL_DEFINE t,material_repair_define u where t.materialguid=u.material_guid and 1=1");
		if(material_code!=null&& material_code !=""){
			params.add("%"+material_code+"%");
			sql.append("  and t.material_code like ?");
		}
		if(material_name!=null && material_name !=""){
			params.add("%"+material_name+"%");
			sql.append("  and t.material_name like ?");
		}
		sql.append(" order by u.create_time desc ");
		
		returnData = jdbcTemplate.queryForList(sql.toString(),params.toArray());
		pager.setTotalRows(returnData.size());
		
		pager.setPageList(jdbcTemplate.queryForList(pager.setPageSql(sql.toString(),
				pageNum, pageSize),params.toArray()));
		return pager;
	}
	
	/**
	 * 资产维修信息修改
	 */
	@Override
	public int repairUpdate(Person person,String guid,String repair_num,String repair_funds,String repair_person,String repair_reason,String repair_time_start,String repair_time_done,String charge_person) {
		List<Object> params = new ArrayList<Object>();
		String sql = "update MATERIAL_REPAIR_DEFINE set repair_num=?,repair_funds=?,repair_person=?,repair_reason=?,repair_time_start=to_date(?,'yyyy-mm-dd'),repair_time_done=to_date(?,'yyyy-mm-dd'),charge_person=?,update_person=?,update_time=sysdate where guid=?";
		params.add(repair_num);
		params.add(repair_funds);
		params.add(repair_person);
		params.add(repair_reason);
		params.add(repair_time_start);
		params.add(repair_time_done);
		params.add(charge_person);
		params.add(person.getName());
		params.add(guid);
		return jdbcTemplate.update(sql,params.toArray());
	}
	
	/**
	 * 新增资产维修记录
	 */
	@Override
	public int repairInsert(Person person,String ID,String materialguid,String repair_num,String repair_funds,String repair_person,String repair_reason,String repair_time_start,String repair_time_done,String charge_person) {
		List<Object> params = new ArrayList<Object>();
		String sql = "insert into MATERIAL_REPAIR_DEFINE (guid,material_guid,repair_num,repair_funds,repair_person,repair_reason,repair_time_start,repair_time_done,charge_person,create_person,create_time) values(?,?,?,?,?,?,to_date(?,'yyyy-MM-dd'),to_date(?,'yyyy-MM-dd'),?,?,sysdate)";
		params.add(ID);
		params.add(materialguid);
		params.add(repair_num);
		params.add(repair_funds);
		params.add(repair_person);
		params.add(repair_reason);
		params.add(repair_time_start);
		params.add(repair_time_done);
		params.add(charge_person);
		params.add(person.getName());
		return jdbcTemplate.update(sql,params.toArray());
	}
	
	/**
	 * 删除领用
	 */
	@Override
	public int repairDelete(String ID) {
		List<Object> params = new ArrayList<Object>();
		String sql = "delete from MATERIAL_REPAIR_DEFINE where guid=?";
		params.add(ID);
		return jdbcTemplate.update(sql,params.toArray());
	}
	
	//===================数据字典=========================
	/**
	 * 获取数据字典列表
	 */
	@Override
	public Pager dictionaryList(Person person, String material_code,
			String material_name, Pager pager) {
		int pageNum = pager.getPageNo();
		int pageSize= pager.getPageSize();
		List<String> params = new ArrayList<String>();
		List<Map<String, Object>> returnData = null;

		StringBuilder sql=new StringBuilder();
		sql.append("select t.* from MATERIAL_DICTIONARY t where 1=1");
		if(material_code!=null&& material_code !=""){
			params.add("%"+material_code+"%");
			sql.append("  and t.material_code like ?");
		}
		if(material_name!=null && material_name !=""){
			params.add("%"+material_name+"%");
			sql.append("  and t.material_name like ?");
		}
		sql.append(" order by t.material_code ");
	
		returnData = jdbcTemplate.queryForList(sql.toString(),params.toArray());
		pager.setTotalRows(returnData.size());
		pager.setPageList(jdbcTemplate.queryForList(pager.setPageSql(sql.toString(),
				pageNum, pageSize),params.toArray()));
		return pager;
	}
	
	/**
	 * 数据字典修改
	 */
	@Override
	public int dictionaryUpdate(Person person,String materialguid, String material_name,String unit) {
		List<Object> params = new ArrayList<Object>();
		String sql = "update MATERIAL_DICTIONARY set material_name=?,unit=? where guid=?";
		params.add(material_name);
		params.add(unit);
		params.add(materialguid);
		return jdbcTemplate.update(sql,params.toArray());
	}

	/**
	 * 新增数据字典
	 */
	@Override
	public int dictionaryInsert(Person person,String ID,String material_code,String material_name,String unit) {
		List<Object> params = new ArrayList<Object>();
		String sql = "insert into MATERIAL_DICTIONARY (guid,material_code,material_name,unit) values(?,?,?,?)";
		params.add(ID);
		params.add(material_code);
		params.add(material_name);
		params.add(unit);
		return jdbcTemplate.update(sql,params.toArray());
	}

	/**
	 * 删除数组字典
	 */
	@Override
	public int dictionaryDelete(String ID) {
		List<Object> params = new ArrayList<Object>();
		String sql = "delete from MATERIAL_DICTIONARY where guid=?";
		params.add(ID);
		return jdbcTemplate.update(sql,params.toArray());
	}
	
	/**
	 * 获取单位
	 */
	@Override
	public String getUnit(String guid) {
		List<Object> params = new ArrayList<Object>();
		String result="";
		String sql = "select t.unit||','||t.material_code from MATERIAL_DICTIONARY t where guid=?";
		params.add(guid);
		result = jdbcTemplate.queryForObject(sql.toString(),params.toArray(),String.class);
		return result;
	}
	
	/**
	 * 查询物料是否存在
	 */
	@Override
	public int getMaterialCount(String guid) {
		List<Object> params = new ArrayList<Object>();
		String result="";
		int count=0;
		String sql = "select count(*) count from material_define t where materialguid=?";
		params.add(guid);
		result = jdbcTemplate.queryForObject(sql.toString(),params.toArray(),String.class);
		count = Integer.parseInt(result);
		return count;
	}
	
	public int logDelete(String code){
		List<Object> params = new ArrayList<Object>();
		String sql = "delete from material_define_log where material_code=?";
		params.add(code);
		return jdbcTemplate.update(sql,params.toArray());
	}
	
	/**
	 * 综合查询
	 */
	@Override
	public Pager materialMixQuery(Person person, String startTime,String endTime, String material_code,String material_name, Pager pager) {
		int pageNum = pager.getPageNo();
		int pageSize= pager.getPageSize();
		List<String> params = new ArrayList<String>();
		List<Map<String, Object>> returnData = null;
		String u_condition="";
		String r_condition="";
		String l_condition="";
		String m_condition="";
		if(startTime!=null&& startTime !=""){
			u_condition=" and u.using_time>=to_date('"+startTime+"','yyyy-MM-dd') ";
			r_condition=" and r.create_time>=to_date('"+startTime+"','yyyy-MM-dd') ";
			l_condition=" and l.updatetime>=to_date('"+startTime+"','yyyy-MM-dd') ";
		}
		if(endTime!=null&& endTime !=""){
			u_condition+=" and u.using_time<=to_date('"+endTime+"','yyyy-MM-dd') ";
			r_condition+=" and r.create_time<=to_date('"+endTime+"','yyyy-MM-dd') ";
			l_condition+=" and l.updatetime<=to_date('"+endTime+"','yyyy-MM-dd') ";
		}
		
		if(material_code!=null&& material_code !=""){
			m_condition=" and m.material_code like ?";
			params.add("%"+material_code+"%");
		}
		
		if(material_name!=null&& material_name !=""){
			m_condition+=" and m.material_name like ?";
			params.add("%"+material_name+"%");
		}
		StringBuilder sql=new StringBuilder(); 
		sql.append("select m.material_code,m.material_name,nvl(sum(l.material_num),0) material_num,nvl(sum(u.using_num),0) using_num,nvl(sum(r.repair_num),0) repair_num "
				+ "from material_define m,"
				+ "(select * from material_using_define u where 1=1 "+u_condition+") u, "
				+ "(select * from material_repair_define r where 1=1 "+r_condition+") r,"
				+ "(select * from material_define_log l where 1=1 "+l_condition+") l where "
				+ "m.material_code=l.material_code(+) and m.materialguid=u.material_guid(+) and m.materialguid=r.material_guid(+) "
				+ m_condition+" group by m.material_code,m.material_name order by m.material_code");
		
		returnData = jdbcTemplate.queryForList(sql.toString(),params.toArray());
		pager.setTotalRows(returnData.size());
		pager.setPageList(jdbcTemplate.queryForList(pager.setPageSql(sql.toString(),
				pageNum, pageSize),params.toArray()));
		return pager;
	}
}
