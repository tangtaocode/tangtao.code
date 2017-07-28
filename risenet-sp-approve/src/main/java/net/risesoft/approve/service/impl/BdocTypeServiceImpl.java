package net.risesoft.approve.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import net.risesoft.approve.entity.base.Pager;
import net.risesoft.approve.entity.jpa.TBdexDoctype;
import net.risesoft.approve.service.BdocTypeService;
import net.risesoft.utilx.StringX;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service(value="bdocTypeServiceImpl")
public class BdocTypeServiceImpl implements BdocTypeService{

	@Resource(name = "routerDataSource")
	private DataSource routerDataSource;

	private JdbcTemplate jdbcTemplate;

	@PostConstruct
	private void afterIoc() {
		jdbcTemplate = new JdbcTemplate(this.routerDataSource);
	}

	@Override
	public Pager findAll(String departmentGuid, String departmentName,
			Pager page) {
		int pageNum = page.getPageNo();
		int pageSize= page.getPageSize();
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try{
			List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
			//查询委办局事项列表
			String sql="select d.*,(SELECT COUNT(*)	FROM T_BDEX_Doctypeinfo a where a.doctypeguid = d.GUID) as typenum,s.BUREAUNAME from T_BDEX_DOCTYPE d,SPM_BUREAU s where d.BGUID =rtrim(s.BUREAUGUID) " ;
			List<Object> params = new ArrayList<Object>();
			if(!StringX.isBlank(departmentGuid)){//出证局Id
				sql += " and d.bguid= '"+departmentGuid+"'";
				//params.add(departmentGuid);
			}
			if(!StringX.isBlank(departmentName)){//证照名称
				sql += " and d.doctypename like ?";
				params.add("%"+departmentName+"%");
			}
			sql += " ORDER BY D.GUID desc " ;
			listmap= jdbcTemplate.queryForList(sql,params.toArray());
			page.setTotalRows(listmap.size());
			page.setPageList(jdbcTemplate.queryForList(page.setPageSql(sql, pageNum, pageSize),params.toArray()));
		}catch(Exception e){
			e.printStackTrace();
		}
		return page;
	}

	@Override
	public int delete(String guid) {
		List<Object> params = new ArrayList<Object>();
		String sql = "delete from T_BDEX_DOCTYPE where guid=?";
		params.add(guid);
		return jdbcTemplate.update(sql,params.toArray());
	}
   
	@Override
	public TBdexDoctype findByid(String guid) {
		List<Object> params = new ArrayList<Object>();	
		params.add(guid);
		TBdexDoctype bdexDoctype =  this.jdbcTemplate.queryForObject("select * from T_BDEX_DOCTYPE d where d.guid=?",params.toArray(), new BeanPropertyRowMapper<TBdexDoctype>(TBdexDoctype.class));
	
		return bdexDoctype;
	}

	@Override
	public int insert(String guid,String doctypecode,String doctypename,String bguid,String memo,Date date) {
		List<Object> params = new ArrayList<Object>();
		String sql = "insert into T_BDEX_DOCTYPE(guid,bguid,doctypecode,doctypename,memo,xgdate,xxnum) values(?,?,?,?,?,?,null)";
		params.add(guid);
		params.add(bguid);
		params.add(doctypecode);
		params.add(doctypename);
		params.add(memo);
		params.add(date);
		return jdbcTemplate.update(sql,params.toArray());
	}

	@Override
	public int update(String guid, String doctypecode, String doctypename,String bguid, String memo) {
		List<Object> params = new ArrayList<Object>();
		String sql = "update T_BDEX_DOCTYPE set doctypecode=?,doctypename=?,bguid=?,memo=? where guid=?";	
		params.add(doctypecode);
		params.add(doctypename);
		params.add(bguid);
		params.add(memo);
		params.add(guid);
		return jdbcTemplate.update(sql,params.toArray());
	}
	
	

}
