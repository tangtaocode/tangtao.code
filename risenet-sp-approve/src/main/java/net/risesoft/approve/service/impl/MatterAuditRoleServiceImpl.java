package net.risesoft.approve.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import net.risesoft.approve.entity.base.Pager;
import net.risesoft.approve.entity.jpa.MatterAuditRole;
import net.risesoft.approve.entity.jpa.SpmApproveitem;
import net.risesoft.approve.repository.jpa.MatterAuditRoleRepository;
import net.risesoft.approve.service.MatterAuditRoleService;
import net.risesoft.approve.service.SpmApproveItemService;
import net.risesoft.common.util.GuidUtil;
import net.risesoft.util.SysVariables;
import net.risesoft.model.OrgUnit;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.RisesoftUtil;

@Service(value="matterAuditRoleService")
public class MatterAuditRoleServiceImpl implements MatterAuditRoleService{

	
	@Autowired
	private MatterAuditRoleRepository matterAuditRoleRepository;
	
	@Autowired
	private SpmApproveItemService spmApproveItemService;
	
	@Resource(name = "routerDataSource")
	private DataSource routerDataSource;

	private JdbcTemplate jdbcTemplate;
	
	@PostConstruct
	private void afterIoc() {
		jdbcTemplate = new JdbcTemplate(this.routerDataSource);
	}
	
	@Override
	public Map<String, Object> getRoleList(String approveItemGuid,Pager pager) {
		Map<String, Object> ret_map = new HashMap<String, Object>();
		int pageNum = pager.getPageNo();
		int pageSize= pager.getPageSize();
		List<Map<String, Object>> matterAuditRole = new ArrayList<Map<String, Object>>();
		String sql="select * from MATTER_AUDIT_ROLE m where m.approveItemGuid = ? ORDER BY m.createTime desc";
		List<String> params = new ArrayList<String>();
		params.add(approveItemGuid);
		try {
			matterAuditRole = jdbcTemplate.queryForList(sql, params.toArray());
			pager.setTotalRows(matterAuditRole.size());
			pager.setPageList(jdbcTemplate.queryForList(pager.setPageSql(sql, pageNum, pageSize),params.toArray()));
		}catch(Exception e) {
			e.printStackTrace();
		}
		List<Map<String, Object>> manageMap = pager.getPageList();
		ret_map.put("pager.pageNo", pager.getPageNo());
		ret_map.put("pageSize", pager.getPageSize());
		ret_map.put("pager.totalRows", pager.getTotalRows());
		ret_map.put("rows", manageMap);
		ret_map.put("success", true);
		return ret_map;
	}

	@Override
	public void saveRole(String personGuids, String personNames,String approveItemGuids) {
		String[] personGuid = StringUtils.split(personGuids, SysVariables.COMMA);
		String[] personName = StringUtils.split(personNames, SysVariables.COMMA);
		String[] approveItemGuid = StringUtils.split(approveItemGuids, SysVariables.COMMA);
		//if(approveItemGuid.length>1){//多个事项批量绑定多个人员
			for(int j = 0;j<approveItemGuid.length;j++){
				for(int i = 0;i<personGuid.length;i++){
					MatterAuditRole matterAuditRole = matterAuditRoleRepository.findByApproveItemGuidAndPersonGuid(approveItemGuid[j],personGuid[i]);
					if(matterAuditRole==null){
						OrgUnit org = RisesoftUtil.getPersonManager().getBureau(ThreadLocalHolder.getTenantId(), personGuid[i]);
						MatterAuditRole mar = new MatterAuditRole();
						mar.setGuid(GuidUtil.genGuid());
						mar.setPersonGuid(personGuid[i]);
						mar.setPersonName(personName[i]);
						mar.setBureauGuid(org.getID());
						mar.setBureauName(org.getName());
						mar.setApproveItemGuid(approveItemGuid[j]);
						SpmApproveitem  spmApproveitem=spmApproveItemService.findByApproveitemguid(approveItemGuid[j]);
						mar.setApproveItemName(spmApproveitem.getApproveitemname());
						mar.setCreateTime(new Date());
						mar.setAuthorGuid(ThreadLocalHolder.getPerson().getID());
						mar.setAuthorName(ThreadLocalHolder.getPerson().getName());
						matterAuditRoleRepository.save(mar);
					}
				}
			}
	/*	}else{//单个事项绑定多个人员
			for(int i = 0;i<personGuid.length;i++){
				MatterAuditRole matterAuditRole = matterAuditRoleRepository.findByApproveItemGuidAndPersonGuid(approveItemGuid[0],personGuid[i]);
				if(matterAuditRole==null){
					MatterAuditRole mar = new MatterAuditRole();
					mar.setGuid(GuidUtil.genGuid());
					mar.setPersonGuid(personGuid[i]);
					mar.setPersonName(personName[i]);
					mar.setBureauGuid(org.getID());
					mar.setApproveItemGuid(approveItemGuid[0]);
					mar.setApproveItemName(null);
					matterAuditRoleRepository.save(mar);
				}
			}
		}*/
	}

	@Override
	public void deleteRole(String personGuids,String approveItemGuid) {
		String[] personGuid = StringUtils.split(personGuids, SysVariables.COMMA);
		for(int i = 0;i<personGuid.length;i++){
			matterAuditRoleRepository.deleteByPersonGuidAndApproveItemGuid(personGuid[i],approveItemGuid);
		}
	}
}
