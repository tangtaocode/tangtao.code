package net.risesoft.approve.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import net.risesoft.approve.controller.OrderController;
import net.risesoft.approve.entity.base.Pager;
import net.risesoft.approve.entity.jpa.SpmApproveitem;
import net.risesoft.approve.repository.jpa.SpmApproveItemRepository;
import net.risesoft.approve.service.SpmApproveItemService;
import net.risesoft.utilx.StringX;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service(value="spmApproveItemService")
public class SpmApproveItemServiceImpl implements SpmApproveItemService{
	
	protected Logger log = LoggerFactory.getLogger(OrderController.class);
	
	@Resource(name = "routerDataSource")
	private DataSource routerDataSource;

	private JdbcTemplate jdbcTemplate;
	
	@PostConstruct
	private void afterIoc() {
		jdbcTemplate = new JdbcTemplate(this.routerDataSource);
	}
	
	@Autowired
	private SpmApproveItemRepository spmApproveItemRepository;
	
	
	//根据事项id获取一个对象
	@Override
	public SpmApproveitem findByApproveitemguid(String approveitemguid){
		return spmApproveItemRepository.findByapproveitemguid(approveitemguid);
	}
	
//	@Override
//	public Pager findByWindowGuid(String windowGuid, Pager pager) {
//		int pageNum = pager.getPageNo();
//		int pageSize= pager.getPageSize();
//		List<Map<String, Object>> approveItems = new ArrayList<Map<String, Object>>();
//		String sql="select distinct a.approveitemguid,a.approveitemtype,a.approveitemname,d.bureauname,a.bureautype,a.timelimit, a.approveplace, h.shortcode "
//				+ "from spm_approveitem a, xzql_xzsp_windowofitem s, spm_bureau d, spm_approve_short h where instr(?,s.windowguid)>0  and a.approveitemstatus='运行' "
//				+ "and a.approveitemguid=s.itemid and a.adminbureauguid=d.bureauguid and a.approveitemguid=h.itemid";
//		List<String> params = new ArrayList<String>();
//		params.add(windowGuid);
//		try {
//			approveItems = jdbcTemplate.queryForList(sql, params.toArray());
//			pager.setTotalRows(approveItems.size());
//			pager.setPageList(jdbcTemplate.queryForList(pager.setPageSql(sql, pageNum, pageSize),params.toArray()));
//		}catch(Exception e) {
//			e.printStackTrace();
//			log.error(e.getMessage());
//		}
//
//		return pager;
//	}
	
	@Override
	public Pager findByWindowGuid(String personId, Pager pager, String approvename) {
		int pageNum = pager.getPageNo();
		int pageSize= pager.getPageSize();
		List<Map<String, Object>> approveItems = new ArrayList<Map<String, Object>>();
		String sql="select a.approveitemguid,a.approveitemtype,decode(a.approveplace,0,'审批平台',a.systemname) systemname,a.approveitemname,d.bureauname,a.bureautype,a.timelimit, a.approveplace, h.shortcode "
				+ "from spm_approveitem a, xzql_xzsp_windowofitem s, xzql_xzsp_window w, spm_bureau d, spm_approve_short h "
				+ "where instr(w.windowusers, ?)>0 and s.windowguid=w.guid  and a.approveitemstatus='运行' "
				+ "and a.approveitemguid=s.itemid and a.adminbureauguid=d.bureauguid and a.approveitemguid=h.itemid ";
		
		List<String> params = new ArrayList<String>();
		params.add(personId);
		if(!StringX.isBlank(approvename)) {
			sql +="and a.approveitemname like ? ";
			params.add("%" + approvename + "%");
		}
		
		sql += "order by d.bureautabindex, a.approveitemtabindex ";
		try {
			approveItems = jdbcTemplate.queryForList(sql, params.toArray());
			pager.setTotalRows(approveItems.size());
			pager.setPageList(jdbcTemplate.queryForList(pager.setPageSql(sql, pageNum, pageSize),params.toArray()));
		}catch(Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}

		return pager;
	}

	@Override
	public String getDoctypeByGuid(String guid) {
		String sql = "select t.doctypeguid from spm_approveitem t where t.approveitemguid=? ";
		List<String> params = new ArrayList<String>();
		params.add(guid);
		String doctypeguid = "";
		try {
			doctypeguid = jdbcTemplate.queryForObject(sql, params.toArray(), String.class);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("通过主键获取事项证照类型失败", e.getMessage());
		}
		return doctypeguid;
	}

	@Override
	public List<SpmApproveitem> findAll() {
		String sql = "select t.* from spm_approveitem t";
		List<SpmApproveitem> items = jdbcTemplate.query(sql, new BeanPropertyRowMapper<SpmApproveitem>(SpmApproveitem.class));
		return items;
	}
}
