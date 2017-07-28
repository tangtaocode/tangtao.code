package net.risesoft.approve.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import net.risesoft.approve.entity.base.Pager;
import net.risesoft.approve.entity.jpa.RisenetEmployeeUnits;
import net.risesoft.approve.service.MaterialShareService;


import net.risesoft.model.Person;
import net.risesoft.util.GUID;
import net.risesoft.util.ServiceUtil;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("materialShareService")
@Transactional
public class MaterialShareServiceImpl implements MaterialShareService {

	@Resource(name = "routerDataSource")
	private DataSource routerDataSource;

	private JdbcTemplate jdbcTemplate;
	 
	@PostConstruct
	private void afterIoc() {
		jdbcTemplate = new JdbcTemplate(this.routerDataSource);
	}
	
	/**
	 * 材料定义列表
	 */
	@Override
	public Pager findByUserID(Person person, String CODE,
			String STUFFDEFINENAME, Pager pager) {
		int pageNum = pager.getPageNo();
		int pageSize= pager.getPageSize();
		List<String> params = new ArrayList<String>();
		List<Map<String, Object>> returnData = null;

		StringBuilder sql=new StringBuilder();
		sql.append("select t.stuffdefineid,t.code,t.stuffdefinename,t.ischeckvalid,t.tabindex,to_char(t.definetime,'yyyy-mm-dd') definetime,t.state from ss_stuffdefine t where 1=1");
		if(CODE!=null&& CODE !=""){
			params.add("%"+CODE+"%");
			sql.append("  and t.CODE like ?");
		}
		if(STUFFDEFINENAME!=null && STUFFDEFINENAME !=""){
			params.add("%"+STUFFDEFINENAME+"%");
			sql.append("  and t.STUFFDEFINENAME like ?");
		}
		
		sql.append(" order by t.tabindex ");
	
		returnData = jdbcTemplate.queryForList(sql.toString(),params.toArray());
		pager.setTotalRows(returnData.size());
		pager.setPageList(jdbcTemplate.queryForList(pager.setPageSql(sql.toString(),
				pageNum, pageSize),params.toArray()));
		return pager;
	}
	
	/**
	 * 材料定义修改
	 */
	@Override
	public int update(String ID, String CODE, String STUFFDEFINENAME,
			String ISCHECKVALID, String TABINDEX, String STATE,
			String DEFINETIME) {
		List<Object> params = new ArrayList<Object>();
		String sql = "update ss_stuffdefine set CODE=?,STUFFDEFINENAME=?,ISCHECKVALID=?,TABINDEX=?,STATE=?,DEFINETIME=to_date( ? ,'yyyy-MM-dd') where stuffdefineid=?";
		params.add(CODE);
		params.add(STUFFDEFINENAME);
		params.add(ISCHECKVALID);
		params.add(TABINDEX);
		params.add(STATE);
		params.add(DEFINETIME);
		params.add(ID);
		return jdbcTemplate.update(sql,params.toArray());
	}

	/**
	 * 新增材料
	 */
	@Override
	public int insert(String ID,String CODE, String STUFFDEFINENAME, String ISCHECKVALID,
			String TABINDEX, String STATE, String DEFINETIME) {
		List<Object> params = new ArrayList<Object>();
		String sql = "insert into ss_stuffdefine (stuffdefineid,CODE,STUFFDEFINENAME,ISCHECKVALID,TABINDEX,STATE,DEFINETIME) values(?,?,?,?,?,?,SYSDATE)";
		params.add(ID);
		params.add(CODE);
		params.add(STUFFDEFINENAME);
		params.add(ISCHECKVALID);
		params.add(TABINDEX);
		params.add(STATE);
		
		return jdbcTemplate.update(sql,params.toArray());
	}

	/**
	 * 删除材料
	 */
	@Override
	public int delete(String ID) {
		List<Object> params = new ArrayList<Object>();
		String sql = "delete from ss_stuffdefine where stuffdefineid=?";
		params.add(ID);
		return jdbcTemplate.update(sql,params.toArray());
	}
	
	
	
}
