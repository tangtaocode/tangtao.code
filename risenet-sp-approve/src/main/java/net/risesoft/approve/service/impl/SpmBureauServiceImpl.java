package net.risesoft.approve.service.impl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import net.risesoft.approve.entity.base.Pager;
import net.risesoft.approve.entity.jpa.SpmBureau;
import net.risesoft.approve.repository.jpa.SpmApproveItemRepository;
import net.risesoft.approve.service.SpmBureauService;
import net.risesoft.utilx.StringX;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * 
 * 委办局事项实现类
 * @author Administrator
 *
 */
@Service(value="spmBureauService")
public class SpmBureauServiceImpl implements SpmBureauService{
	
/*	@Autowired
	@Qualifier("defaultDataSource")
	private DataSource defaultDataSource;
*/	@Resource(name = "routerDataSource")
	private DataSource routerDataSource;
	
	private JdbcTemplate jdbcTemplate;
	
	@PostConstruct
	private void afterIoc() {
		jdbcTemplate = new JdbcTemplate(this.routerDataSource);
	} 
	@Autowired
	private SpmApproveItemRepository spmApproveItemRepository;
	

	
	
	@Override
	public Pager findAll(String approveItemType,String bureauGuid,String approveName,String approveStatus,Pager pager) {
		//this.jdbcTemplate = new JdbcTemplate(this.defaultDataSource);
		// TODO Auto-generated method stub
		int pageNum = pager.getPageNo();
		int pageSize= pager.getPageSize();
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try{
			List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
			//查询委办局事项列表
			String sql="select distinct sp.approveItemStatus,sp.approveItemType,"
					+ "case "
					+ " when sp.timeLimit='-1' then '视情况而定' "
					+ " when sp.timeLimit='0' then '即来即办' "
					+ " else sp.timeLimit "
					+ " end as timeLimit "
					+ ",sp.approveItemName,sb.bureauGUID,sb.bureauname,"
					+ "sp.approveitemguid, sb.bureauname department_name,sb.bureautabindex, t.departmentguid,sp.approveitemtabindex "
					+ " from spm_approveitem sp, spm_approveitem_bureau ri, spm_bureau sb,spm_approveitem_depart t "
					+ "where sp.approveitemguid=ri.approveitemguid and trim(sb.bureauguid)=trim(ri.bureauguid) and trim(ri.bureauguid)=trim(t.bureauguid) "
					+ " and trim(sp.adminbureauguid)=trim(ri.bureauguid) and ri.approveitemguid=t.approveitemguid "
					//+ "and sp.bureauorstreet='0' "
					//+ "and sp.isproject=0  "
					;
			List<Object> params = new ArrayList<Object>();
			if(!StringX.isBlank(bureauGuid)){//主管部门
				sql += " and sb.bureauGUID=?";
				params.add(bureauGuid);
			}
			if(!StringX.isBlank(approveName)){//事项名称
				sql += " and sp.approveItemName like ?";
				params.add("%"+approveName+"%");
			}
			if(!StringX.isBlank(approveStatus)){//事项运行状态
				sql += " and sp.approveItemStatus=?";
				params.add(approveStatus);
			}
			if(!StringX.isBlank(approveItemType)){//事项运行状态
				sql += " and sp.approveItemType=?";
				params.add(approveItemType);
			}
			sql += " order by sb.bureautabindex, t.departmentguid, sp.approveitemtabindex, sp.approveitemname";
			listmap= jdbcTemplate.queryForList(sql,params.toArray());
			if (listmap.contains("timeLimit")) {
				
			}
			pager.setTotalRows(listmap.size());
			pager.setPageList(jdbcTemplate.queryForList(pager.setPageSql(sql, pageNum, pageSize),params.toArray()));
		}catch(Exception e){
			e.printStackTrace();
		}
		return pager;
		
	}
	
	//加载委办局
	@Override
	public List<Map<String, Object>> loadDepartMent(String isStreet,String isExtent) {
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		String sql="select b.bureauname key,b.bureauguid value from spm_bureau b order by b.bureautabindex";
//		if("1".equals(isStreet)){
//			sql = "select BureauName key, BureauGUID value from SPM_Bureau where IsStreet='1' order by BureauTabIndex";
//		}else if("1".equals(isExtent)){
//			sql="select b.bureauname key,b.bureauguid value from spm_bureau b  order by b.bureautabindex";
//		}
		listmap = jdbcTemplate.queryForList(sql);
		return listmap;
	}
	 
	public SpmBureau findDepartMentById(String bureauId ) {
		//List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		String sql="select * from spm_bureau where BUREAUGUID=? and rownum=1";

		//SpmBureau bureau = jdbcTemplate.queryForObject(sql, SpmBureau.class,bureauId);
		//jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<SysUser>(SysUser.class));
		SpmBureau bureau = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<SpmBureau>(SpmBureau.class), bureauId);
		return bureau;
	}
	@Override
	public int bandWorkFlow(String workflowguid,String approveitemguid,String sealType) {
		List<String> list = new ArrayList<String>();
		String sql = "update spm_approveitem t set t.workflowguid=?,t.sealType=? where t.approveitemguid=?";
		list.add(workflowguid);
		list.add(sealType);
		list.add(approveitemguid);
		return jdbcTemplate.update(sql, list.toArray());
	}

	@Override
	public Pager eformfindAll(String eformname,Pager pager) {
		String sql="select t.TEMP_ID,t.TEMPLATENAME,t.ACCESSMETHOD from TEMPLATEDEFINE_SYS t";
		List<Object> params=new ArrayList<Object>();
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		int pageNum = pager.getPageNo();
		int pageSize= pager.getPageSize();
		if(!StringX.isBlank(eformname)){
			sql+=" where t.TEMPLATENAME like ?";
			params.add("%"+eformname+"%");
		}
		//list= jdbcTemplate.queryForList(sql,params.toArray());
		list=jdbcTemplate.queryForList(sql,params.toArray());
		pager.setTotalRows(list.size());
		pager.setPageList(jdbcTemplate.queryForList(pager.setPageSql(sql, pageNum, pageSize),params.toArray()));
		return pager;
	}

	@Override
	public int insert(String guid, String approveitemguid, String formname,String url,String typename,String typeguid,String accessmethod,String listguid) {
		String sql="insert into PROCEEDINGFORMS (GUID,APPROVEITEMGUID,FORMNAME,URL,TYPENAME,TYPEGUID,ACCESSMETHOD,LISTGUID) values (?,?,?,?,?,?,?,?)";
		List<Object> params=new ArrayList<Object>();
		params.add(guid);
		params.add(approveitemguid);
		params.add(formname);
		params.add(url);
		params.add(typename);
		params.add(typeguid);
		if(accessmethod.equals("v")){
			//该表单为显示表单
			params.add("0");
		}else{
			//该表单为编辑表单
			params.add("1");
		}
		params.add(listguid);
		try{
			int isresult=jdbcTemplate.update(sql,params.toArray());
			return isresult;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	/*@Override
	public void find(String url, String typeguid, String approveitemguid) {
		String sql="select count(*) from PROCEEDINGFORMS t where t.url=? and t.typeguid=? and t.approveitemguid=?";
		List<Object> params=new ArrayList<Object>();
		params.add(url);
		params.add(typeguid);
		params.add(approveitemguid);	
			
			int isresult=jdbcTemplate.queryForInt(sql,params.toArray());
			//代表之前已经更新绑定。现在只需更新即可
			if(isresult==1){
				String desql="delete PROCEEDINGFORMS t where t.url=? and t.typeguid=? and t.approveitemguid=?";
				List<Object> par=new ArrayList<Object>();
				par.add(url);
				par.add(typeguid);
				par.add(approveitemguid);	
				int result=jdbcTemplate.update(desql,par.toArray());	
			}						
	}*/

	
}
