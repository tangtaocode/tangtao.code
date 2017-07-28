package net.risesoft.approve.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import net.risesoft.approve.service.MaterialListService;
import net.risesoft.approve.service.OfficeSpiDeclareinfoService;
import net.risesoft.approve.service.SharestuffService;
import net.risesoft.utilx.StringX;


/**
 * 
 * 网上收件材料清单实现类
 * 
 */

@Service(value = "materialListService")
public class MaterialListServiceImpl implements MaterialListService {

	@Resource(name = "routerDataSource")
	private DataSource routerDataSource;

	private JdbcTemplate jdbcTemplate;
	
	@PostConstruct
	private void afterIoc() {
		jdbcTemplate = new JdbcTemplate(this.routerDataSource);
	} 
	
	@Resource(name = "officeSpiDeclareinfoService")
	private OfficeSpiDeclareinfoService officeSpiDeclareinfoService;
	
	@Autowired
	private SharestuffService sharestuffService;
	
	
	@Override
	public List<Map<String, Object>> findAll(String approveItemType,
			String bureauGuid, String approveName, String approveStatus) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/**
	 * 
	 * 网上收件材料清单
	 * 
	 */
	@Override
	public List<Map<String, Object>> findMaterialByOffice(String instanceId) {
		
		 //开始查询材料清单
		List<Map<String, Object>> reList =new ArrayList<Map<String, Object>>();
		List<Object> params = new ArrayList<Object>();
		try {
			/*String sql = "select lists.*, (case when length(wssbcl.type)>0 then 'checked' end) submission, "
					+ "(case when instr(lists.notdeclareannexguids2, lists.declareannexguid, 1, 1) > 0  then 'checked' end) notsubmission, "
					+ "(case when instr(lists.bhgsbcl2, lists.declareannexguid, 1, 1) > 0  then 'checked' end) rejigger,"
					+ "(case when instr(wssbcl.type, '0', 1, 1) > 0 then 'checked' end) putong, "
					+ "(case when instr(wssbcl.type, '1', 1, 1) > 0 then 'checked' end) zhengzhao, "
					+ "(case when instr(wssbcl.type, '2', 1, 1) > 0 then 'checked' end) stuffdata, "
					+ "(case when instr(wssbcl.type, '3', 1, 1) > 0 then 'checked' end) eform,wssbcl.workflowinstance_guid "
					+ "from ( select sd.declareannexname,sp.workflowguid,to_char(sd.declareannexdesc) as declareannexdesc, "
					+ "sd.declareannexguid,st.declareannextypename,t.approveitemguid approveguid,st.typeorder,"
					+ "sd.declareannextabindex, t.notdeclareannexguids2, t.bhgsbcl2 "
					+ "from spm_declareannex  sd, office_spi_declareinfo t,spm_approveitem sp, spm_declareannextype st, "
					+ "spm_declareannexrelation sl where sd.declareannexguid=sl.declareannexguid and sp.approveitemguid=t.approveitemguid "
					+ "and sd.approveitemguid = sl.approveitemguid "
					+ "and sl.approveitemguid=t.approveitemguid  and sl.declareannextypeguid=st.declareannextypeguid "
					+ "and t.workflowinstance_guid=?";
			//判断是否选中情形
			String tsql = "select t.listtype from office_spi_declareinfo t where t.workflowinstance_guid=?";
			String typeGuid = jdbcTemplate.queryForObject(tsql, String.class, instanceId);
			if(!StringX.isBlank(typeGuid)){
				sql += " and st.declareannextypeguid=t.listtype";
				params.add(instanceId);
			}
			sql += ") lists, (select w.workflowinstance_guid,w.approveitemguid, w.declareannextypeguid, w.declareannexguid, wm_concat(w.type) type "
			+ "from spm_wssbcl w where w.workflowinstance_guid=? group by w.workflowinstance_guid,w.approveitemguid, w.declareannextypeguid, w.declareannexguid) wssbcl "
			+ "where lists.declareannexguid=wssbcl.declareannexguid(+)"
			+ "order by lists.typeorder,lists.declareannextabindex";	
			
			 
		  if(!StringUtils.isBlank(instanceId)){
			  params.add(instanceId);
			  params.add(instanceId);
		  }
		  reList = jdbcTemplate.queryForList(sql,params.toArray());  
		  //加载共享材料
		  String shareStuffCountSql = "select count(t.guid) from spm_wssbcl t where t.workflowinstance_guid=? and t.type='2' ";
		  params.clear();
		  params.add(instanceId);
		  int ssCount = jdbcTemplate.queryForObject(shareStuffCountSql,Integer.class, params.toArray());
		  if(ssCount <= 0) { 
			  for(int i=0; i<reList.size(); i++) {
				  sharestuffService.copyFromStuffToWssbcl(instanceId, (String)reList.get(i).get("DECLAREANNEXGUID"));
			  }
		  }*/
			String sql = "select * from ex_gdbs_sbcl cl where cl.sblsh_short ="+
						"(select declaresn from OFFICE_SPI_DECLAREINFO o where o.workflowinstance_guid=?)"
						+" and cl.version=(select MAX(version) FROM EX_GDBS_SBCL) order by stuff_seq";
			params.add(instanceId);
			reList = jdbcTemplate.queryForList(sql,params.toArray());  
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reList;
	}

	/**
	 * 
	 * 网上收件办事指南
	 * 
	 */
	@Override
	public Map<String,Object> findByBszn(String approveItemGuid) {

//		List<Map<String, Object>> reList =new ArrayList<Map<String, Object>>();
		Map<String,Object> deptMap = new HashMap<>();
		try {
		String sql = "select t.DeclareUnitType,t.DeclareCondition,t.ApproveItemType,t.bureautype,t.ChargeInfo,t.TimeLimit,"
				+ " t.SuperviseUnit,t.TimeLimitDesc,t.DeclareDesc,t.approveitemDesc,t.ApproveItemName "
				+ " from SPM_APPROVEITEM t where ApproveItemGUID = ?";
		
		  List<Object> params = new ArrayList<Object>();
		if(!StringUtils.isBlank(approveItemGuid)){
			params.add(approveItemGuid);
			
		   }
		deptMap = jdbcTemplate.queryForMap(sql.toString(),params.toArray());  

		} catch (Exception e) {
					e.printStackTrace();
	    }
				return deptMap;
	}
		

	
	/**
	 * 
	 * 材料回复补齐补正
	 */
	@Override
	public int updateReply(String feedback, String ytjids, String xbqids,
			String xbzids, String guid) {
		List<String> list = new ArrayList<String>();
		
		String sql = "update office_spi_declareinfo set DECLAREANNEXGUIDS2=?, NOTDECLAREANNEXGUIDS2=?, BHGSBCL2=?, feedback=?,replystatus='1',handlestatus='补齐补正' where workflowinstance_guid=?";
		list.add(ytjids);
		list.add(xbqids);
		list.add(xbzids);
		list.add(feedback);
		list.add(guid);
		return jdbcTemplate.update(sql, list.toArray());
		
	}


	/**
	 * 
	 * 根据instanceId获取approveItemGuid
	 * 
	 */
	@Override
	public Map<String, Object> findByapproveItemGuid(String instanceId) {
		
		List<String> params = new ArrayList<String>();
		String sql = "select t.approveitemguid approveguid from office_spi_declareinfo t where t.workflowinstance_guid= ?";
		Map<String, Object> Listmap =new HashMap<>();
		try{
		  
			if(!StringUtils.isBlank(instanceId)){
				params.add(instanceId);
				
			   }
			Listmap = jdbcTemplate.queryForMap(sql,params.toArray());

			} catch (Exception e) {
						e.printStackTrace();
		    }
		return Listmap;
	}

	/**
	 * 
	 * 根据instanceId获取办事流程
	 * 
	 */
	@Override
	public Map<String, Object> findByapproveServiceProcess(String instanceId) {
		List<String> params = new ArrayList<String>();
		String sql = "select approveitemname,thefilename from SPM_Approveitem where APPROVEITEMGUID =  ?";
		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> Listmap = new ArrayList<Map<String, Object>>();
		String thefilename ="";
		try{
		  	if(!StringUtils.isBlank(instanceId)){
				params.add(instanceId);
			 }
			Listmap = jdbcTemplate.queryForList(sql,params.toArray());
			Iterator<Map<String, Object>> it = Listmap.iterator();
    		while(it.hasNext()){
    		Map<String, Object> rece = (Map<String, Object>) it.next(); 
    		 thefilename += (String)rece.get("thefilename") +";<br>";
    		 
    		} 
            map.put("thefilename", thefilename);
			} catch (Exception e) {
						e.printStackTrace();
		    }
		 
		return map;
	}

	/**
	 * 
	 * 根据approveItemGuid获取材料列表
	 * 
	 */
	@Override
	public Map<String, Object> findByapproveMaterial(String approveItemGuid) {
		List<String> params = new ArrayList<String>();
		String sql = "SELECT A .DeclareAnnexName FROM SPM_DeclareAnnex A,SPM_DeclareAnnexType b "
				+ "WHERE A .DECLAREANNEXTYPEGUID = b.DECLAREANNEXTYPEGUID (+)AND b.APPROVEITEMGUID = ? ORDER BY b.typeorder, declareannextabindex";
        Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> Listmap = new ArrayList<Map<String, Object>>();
		String DECLAREANNEXNAME ="";
		String DECLAREANNEXDESC = "";
		try{
		  	if(!StringUtils.isBlank(approveItemGuid)){
				params.add(approveItemGuid);
			 }
			Listmap = jdbcTemplate.queryForList(sql,params.toArray());
			if(Listmap.size()>0){
			Iterator<Map<String, Object>> it = Listmap.iterator();
    		while(it.hasNext()){
    		Map<String, Object> rece = (Map<String, Object>) it.next(); 
    		DECLAREANNEXDESC += (String)rece.get("DECLAREANNEXDESC")+";<br>";
    		 } 
    		map.put("DECLAREANNEXNAME", DECLAREANNEXDESC);
    		}else{
    			sql = "select DeclareAnnexName from SPM_DeclareAnnex where ApproveItemGUID = ?  order by declareannextabindex";	
    			Listmap = jdbcTemplate.queryForList(sql,params.toArray());
    			Iterator<Map<String, Object>> it = Listmap.iterator();
        		while(it.hasNext()){
        		Map<String, Object> res = (Map<String, Object>) it.next(); 
        		DECLAREANNEXNAME += (String)res.get("DECLAREANNEXNAME")+";<br>";
        		 } 
    			 map.put("DECLAREANNEXNAME", DECLAREANNEXNAME);	
    		}
           
			} catch (Exception e) {
						e.printStackTrace();
		    }
		 
		return map;
	}

	/**
	 * 
	 * 根据approveItemGuid获取材料说明
	 * 
	 */
	@Override
	public Map<String, Object> findByapproveMaterialdescription(
			String approveItemGuid) {
		List<String> params = new ArrayList<String>();
		String sql = "select DECLAREANNEXDESC from SPM_DeclareAnnex where ApproveItemGUID =  ?";
		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> Listmap = new ArrayList<Map<String, Object>>();
		String DECLAREANNEXDESC ="";
		try{
		  	if(!StringUtils.isBlank(approveItemGuid)){
				params.add(approveItemGuid);
			 }
			Listmap = jdbcTemplate.queryForList(sql,params.toArray());
			Iterator<Map<String, Object>> it = Listmap.iterator();
    		while(it.hasNext()){
    		Map<String, Object> rece = (Map<String, Object>) it.next(); 
    		DECLAREANNEXDESC += (String)rece.get("DECLAREANNEXDESC") +";<br>";
    		} 
            map.put("DECLAREANNEXDESC", DECLAREANNEXDESC);
			} catch (Exception e) {
						e.printStackTrace();
		    }
		 
		return map;
	}

	/**
	 * 
	 * 根据approveItemGuid获取法律、法规
	 * 
	 */
	@Override
	public Map<String, Object> findByapproveLaws(String approveItemGuid) {
		List<String> params = new ArrayList<String>();
		String sql = "Select APPROVEITEMPOLICYNAME,APPROVEITEMPOLICYGUID,URL from SPM_ApproveItemPolicy where ApproveItemGUID = ?";
		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> Listmap = new ArrayList<Map<String, Object>>();
		String APPROVEITEMPOLICYNAME ="";
		try{
		  	if(!StringUtils.isBlank(approveItemGuid)){
				params.add(approveItemGuid);
			 }
			Listmap = jdbcTemplate.queryForList(sql,params.toArray());
			Iterator<Map<String, Object>> it = Listmap.iterator();
    		while(it.hasNext()){
    		Map<String, Object> rece = (Map<String, Object>) it.next(); 
    		APPROVEITEMPOLICYNAME += (String)rece.get("APPROVEITEMPOLICYNAME") +";<br>";
    		} 
            map.put("APPROVEITEMPOLICYNAME", APPROVEITEMPOLICYNAME);
			} catch (Exception e) {
						e.printStackTrace();
		    }
		return map;
	}


	/*
	 * 查看附件明细
	 * (non-Javadoc)
	 * @see net.risesoft.approve.service.MaterialListService#findAttachMentByInstanceId(java.lang.String)
	 */
	@Override
	public List<Map<String, Object>> findAttachMentByInstanceId(String instanceId,String declareGuid) {
		List<Map<String, Object>> Listmap = new ArrayList<Map<String, Object>>();
		try{
			String sql = "select distinct t.guid id,s.declareannexname materialname,t.declareannexguid, "
					+ "t.filename, t.rootpath, to_char(t.updatetime,'yyyy-mm-dd') updatetime "
					+ "from spm_wssbcl t, spm_declareannex s "
					+ "where t.declareannexguid = s.declareannexguid  and t.approveitemguid=s.approveitemguid and t.workflowinstance_guid =? and t.declareannexguid=? "
					+ "and t.type='0' ";
			Listmap = jdbcTemplate.queryForList(sql,new String[]{instanceId,declareGuid});
		}catch(Exception e){
			e.printStackTrace();
		}
		return Listmap;
	}


	/*
	 * 窗口收件材料清单(non-Javadoc)
	 * @see net.risesoft.approve.service.MaterialListService#findMaterialByWindow(java.lang.String)
	 
	@Override
	public List<Map<String, Object>> findMaterialByWindow(String processInstanceId) {

		 //开始查询材料清单
		List<Map<String, Object>> reList =new ArrayList<Map<String, Object>>();
		try {
			String instanceId = officeSpiDeclareinfoService.getGuidByProcessInstanceId(processInstanceId);
			String sql = "select distinct sd.declareannexname,sp.workflowguid,TO_CHAR(sd.declareannexdesc) as declareannexdesc,sd.declareannexguid,st.declareannextypename,osd.approveitemguid approveguid,st.typeorder,sd.declareannextabindex, "
					+ " decode(v1.onlineannex,1,'checked','') SUBMISSION, "
					+ " (case when instr(osd.notdeclareannexguids2, sl.declareannexguid, 1, 1) > 0  then 'checked' end) NOTSUBMISSION,"
					+ " (case when instr(osd.bhgsbcl2, sl.declareannexguid, 1, 1) > 0  then 'checked' end) REJIGGER  "
					+ " from spm_declareannex  sd, office_spi_declareinfo osd,spm_approveitem sp, spm_declareannextype st, SPM_DECLAREANNEXRELATION sl, "
					+ " (select sw.declareannextypeguid,sw.declareannexguid,sw.filename,sw.ROOTPATH, count(*) onlineannex from spm_wssbcl sw where sw.workflowinstance_guid = ? group by sw.declareannextypeguid, sw.declareannexguid,sw.filename,sw.ROOTPATH) v1 "
					+ " where sd.declareannexguid=sl.declareannexguid and sp.approveitemguid=osd.approveitemguid and sl.approveitemguid=osd.approveitemguid and sl.declareannexguid=v1.declareannexguid(+) and sl.declareannextypeguid=st.declareannextypeguid(+) "
					+ " and osd.workflowinstance_guid= ? order by st.typeorder,sd.declareannextabindex ";	
			  List<Object> params = new ArrayList<Object>();
			  if(!StringUtils.isBlank(instanceId)){
				  params.add(instanceId);
				  params.add(instanceId);
			  
			  }
			  reList = jdbcTemplate.queryForList(sql,params.toArray());
			  
			 
			  
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reList;
	}*/
	
}
