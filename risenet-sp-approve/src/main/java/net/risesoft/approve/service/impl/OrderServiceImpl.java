/**
 * @Project Name:risenet-sp-approve
 * @File Name: OrderServiceImpl.java
 * @Package Name: net.risesoft.approve.service.impl
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2015,RiseSoft  All Rights Reserved.
 * @date 2015年12月24日 下午4:19:52
 */
package net.risesoft.approve.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import net.risesoft.approve.entity.base.Pager;
import net.risesoft.approve.entity.jpa.OrderManager;
import net.risesoft.approve.entity.jpa.RisenetEmployeeUnits;
import net.risesoft.approve.repository.jpa.OrderManagerRepository;
import net.risesoft.approve.repository.jpa.SpmApproveItemRepository;
import net.risesoft.approve.service.OrderService;
import net.risesoft.approve.service.SharestuffService;
import net.risesoft.model.Person;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.ServiceUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @ClassName: OrderServiceImpl.java
 * @Description: TODO
 *
 * @author chenbingni
 * @date 2015年12月24日 下午4:19:52
 * @version 
 * @since JDK 1.7
 */
@Service("orderService")
@Transactional
public class OrderServiceImpl implements OrderService {
	
	protected Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Resource(name = "routerDataSource")
	private DataSource routerDataSource;
	
	private JdbcTemplate jdbctemplate;
	
	@Resource(name="sharestuffService")
	private SharestuffService sharestuffService;
	
	@PostConstruct
	private void afterIoc() {
		jdbctemplate = new JdbcTemplate(this.routerDataSource);
	} 
	
	@Autowired
	private OrderManagerRepository orderManagerRepository;
	
	@Autowired
	private SpmApproveItemRepository spmApproveItemRepository;

	@Override
	public List<Map<String, Object>> findByUserID(Person person, String orderPerson, String orderId, String ishf,
			String beginDate, String endDate, String slstate) {
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		List<String> params = new ArrayList<String>();
		try {			
			String sql = "select a.approveitemname, a.approveitemguid, o.code, o.orderperson, o.orderid, o.orderpersonguid,  "
					+ "to_char(o.ordertime, 'yyyy-mm-dd')||' '||"
					+ "decode(o.type,'1','09:00-09:59','2','10:00-10:59','3','11:00-11:59','4', "
					+ "'14:00-14:59','5','15:00-15:59','6','16:00-16:59','7','17:00-17:30','任意时间') ordertime, " 
					+ "o.ordertel, "
					+ "to_char(o.hfsj, 'yyyy-mm-dd hh24:mi:ss') hfsj, "
					+ "o.ispass, o.slstate "
					+ "from spm_approveitem_depart sad, spm_approveitem a, spm_approveitem_orderperson o  "
					+ "where sad.departmentguid =?  "
					+ "and sad.approveitemguid=a.approveitemguid  "
					+ "and a.approveitemstatus = '运行'  "
					+ "and a.orderpermission='1'  "
					+ "and o.approveitem=a.approveitemguid  ";
			RisenetEmployeeUnits risenetInfo = ServiceUtil.riseInfo(jdbctemplate, person.getID());
			params.add(risenetInfo.getDepartmentGuid());
			
			if(orderPerson != null && orderPerson != "") {
				sql += "and o.orderperson like ? ";
				params.add("%" + orderPerson.trim() + "%");
			}
			if(orderId != null && orderId != "") {
				sql += "and o.orderid like ? ";
				params.add("%" + orderId.trim() + "%");
			}
			if(beginDate != null && beginDate != "") {
				sql += "and to_char(o.ordertime, 'yyyy-mm-dd') >= ? ";
				params.add(beginDate);
			}
			if(endDate != null && endDate != ""){
				sql += "and to_char(o.ordertime, 'yyyy-mm-dd') <= ? ";
				params.add(endDate);
			}
			if(slstate != null && slstate != "") {
				sql += "and o.slstate = ? ";
				params.add(slstate);
			}
			if(ishf != null && ishf != "") {
				sql += "and o.ispass = ? ";
				params.add(ishf);
			}
			
			sql += "order by o.hfsj desc, o.type ";
			
			listmap = jdbctemplate.queryForList(sql, params.toArray());
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return listmap;
	}

	@Override
	public List<Map<String, Object>> findOrderByGuid(String orderGuid) {
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		List<String> params = new ArrayList<String>();
		try {
			String sql = "select t.orderpersonguid, t.ispass, t.hfnr, t.hftime, e.employee_name hfry from spm_approveitem_orderperson t left join risenet_employee e on t.hfry=e.employee_guid where t.orderpersonguid = ? ";
			params.add(orderGuid);
			listmap = jdbctemplate.queryForList(sql, params.toArray());
		} catch (DataAccessException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return listmap;
	}

	@Override
	public boolean saveHuifu(Person person, String orderGuid, String hfnr) {
		int res=0;
//		RisenetEmployeeUnits risenetInfo = ServiceUtil.riseInfo(jdbctemplate, person.getID());
		String usql = "update spm_approveitem_orderperson t set t.ispass='1', t.hfnr=?, t.hfry=?, t.hftime=sysdate where t.orderpersonguid=? ";
		List<String> params = new ArrayList<String>();
		params.add(hfnr);
		params.add(SharestuffServiceImpl.getEmployeeInfo());
		params.add(orderGuid);
		try {
			res = jdbctemplate.update(usql, params.toArray());			
		} catch (DataAccessException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		if(res != 1 ) {
			return false;
		}
		sharestuffService.tongbu("CHANGE_ORDERS", orderGuid);
		return true;
	}

	@Override
	public int saveSlstate(String orderGuid) {
		int res = 0;
		String usql = "update spm_approveitem_orderperson t set t.slstate='1' where t.orderpersonguid=? ";
		List<String> params = new ArrayList<String>();
		params.add(orderGuid);
		try {
			res = jdbctemplate.update(usql, params.toArray());			
		} catch (DataAccessException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return res;
	}

	@Override
	public Pager findOrderManage(String bureau,
			String approve, Pager pager) {
		int pageNum = pager.getPageNo();
		int pageSize= pager.getPageSize();
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		List<String> params = new ArrayList<String>();
		try {
			String sql = "select b.bureauname, a.approveitemguid, a.approveitemname, "
					+ "(case when a.orderpermission=null or a.orderpermission='0' then 0 else 1 end) isOrder "
					+ "from spm_approveitem a, spm_bureau b where a.approveitemstatus='运行' "
					+ "and a.adminbureauguid=b.bureauguid and b.isstreet='0' ";
			if(bureau != null && bureau != "") {
				sql += "and b.bureauguid=? ";
				params.add(bureau);
			}
			if(approve != null && approve != "") {
				sql += "and a.approveitemname like ? ";
				params.add("%" + approve.trim() + "%");
			}
			sql += "order by b.bureautabindex ";
			listmap = jdbctemplate.queryForList(sql, params.toArray());
			pager.setTotalRows(listmap.size());
			pager.setPageList(jdbctemplate.queryForList(pager.setPageSql(sql, pageNum, pageSize),params.toArray()));
		} catch (DataAccessException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return pager;
	}

	@Override
	public Map<String, Object> findManageByGuid(String itemguid) {		
		Map<String, Object> orderpermission = new HashMap<String, Object>();
		List<String> params = new ArrayList<String>();
		params.add(itemguid);
		try {
			String sql = "select t.approveitemguid,t.approveitemname, t.orderpermission from spm_approveitem t where t.approveitemguid=? ";
			orderpermission = jdbctemplate.queryForMap(sql, params.toArray());
		} catch (DataAccessException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return orderpermission;
	}

	@Override
	public List<Map<String, Object>> searchManageByGuid(String itemguid) {
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		List<String> params = new ArrayList<String>();
		String sql = "select t.approveitemguid, t.type, t.limit from spm_ordermanager t where t.approveitemguid=? order by t.type ";
		params.add(itemguid);
		try {
			listmap = jdbctemplate.queryForList(sql, params.toArray());
		} catch (DataAccessException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return listmap;
	}

	@Override
	public boolean saveManage(OrderManager manager) {
		try {
			orderManagerRepository.save(manager);
		} catch(Exception e) {
			e.printStackTrace();
			log.debug("保存ordermanager失败！", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean changePermission(String itemguid, String orderpermission) {
		try {		
			spmApproveItemRepository.updateOrderpermission(orderpermission, itemguid);	
		} catch(Exception e) {
			e.printStackTrace();
			log.debug("更新事项的预约许可失败！", e);
			return false;
		}
		return true;
	}
}

