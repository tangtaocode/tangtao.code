/**
 * @Project Name:risenet-sp-approve
 * @File Name: TogetherWindowServiceImpl.java
 * @Package Name: net.risesoft.approve.service.impl
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2016,RiseSoft  All Rights Reserved.
 * @date 2016年5月9日 下午3:21:37
 */
package net.risesoft.approve.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import net.risesoft.approve.controller.TogetherController;
import net.risesoft.approve.entity.base.Pager;
import net.risesoft.approve.entity.jpa.OfficeSpiDeclareinfo;
import net.risesoft.approve.entity.jpa.together.SpmApproveShort;
import net.risesoft.approve.entity.jpa.together.XzqlXzspListspoint;
import net.risesoft.approve.entity.jpa.together.XzqlXzspListsps;
import net.risesoft.approve.repository.jpa.OfficeSpiDeclareinfoRepository;
import net.risesoft.approve.repository.jpa.SpApproveRepository;
import net.risesoft.approve.repository.jpa.together.SpmApproveShortRepository;
import net.risesoft.approve.repository.jpa.together.XzqlXzspListspointRepository;
import net.risesoft.approve.repository.jpa.together.XzqlXzspListspsRepository;
import net.risesoft.approve.service.TogetherWindowService;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.GUID;
import net.risesoft.utilx.StringX;
import net.sf.json.JSONObject;

/**
 * @ClassName: TogetherWindowServiceImpl.java
 * @Description: 综合窗口service实现类
 *
 * @author chenbingni
 * @date 2016年5月9日 下午3:21:37
 * @version 
 * @since JDK 1.7
 */
@Service(value = "togetherWindowService")
public class TogetherWindowServiceImpl implements TogetherWindowService {
	
	protected Logger log = LoggerFactory.getLogger(TogetherController.class);
	
	@Resource(name = "routerDataSource")
	private DataSource routerDataSource;

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private SpApproveRepository spApproveRepository;
	
	@Autowired
	private OfficeSpiDeclareinfoRepository declareinfoRepository;
	
	@PostConstruct
	private void afterIoc() {
		jdbcTemplate = new JdbcTemplate(this.routerDataSource);
	}

	@Autowired
	private SpmApproveShortRepository spmApproveShortRepository;
	
	@Autowired
	private XzqlXzspListspointRepository xzqlXzspListspointRepository;
	
	@Autowired
	private XzqlXzspListspsRepository xzqlXzspListspsRepository;
	
	@Override
	public Pager findApproveShortList(HttpServletRequest request, Pager pager) {
		int pageNum = pager.getPageNo();
		int pageSize= pager.getPageSize();
		
		List<String> params = new ArrayList<String>();
		String sql="select tt.bureauguid, tt.bureauname, tt.itemid, tt.approveitemname, tt.bureausn, s.shortcode "
				+ "from (select bu.bureauguid, bu.bureauname, bu.bureausn, s.approveitemguid itemid, s.approveitemname "
				+ "from spm_approveitem s, spm_bureau bu "
				+ "where s.approveitemstatus='运行' "
				+ "and s.approveplace='0' "
				+ "and s.adminbureauguid=bu.bureauguid ";//仅显示使用审批平台的事项
		
		sql += ") tt left join spm_approve_short s on tt.itemid=s.itemid where 1=1";
		String bureauGUID = request.getParameter("bureauGUID");
		if(!StringX.isBlank(bureauGUID)) {
			sql += "and tt.bureauguid=? ";
			params.add(bureauGUID);
		}
		String approveItemName = request.getParameter("approveItemName");
		if(!StringX.isBlank(approveItemName)) {
			sql += "and tt.approveitemname like ? ";
			params.add("%"+approveItemName+"%");
		}
		String shortCode = request.getParameter("shortCode");
		if(!StringX.isBlank(shortCode)) {
			sql += "and s.shortCode like ? ";
			params.add("%"+shortCode+"%");
		}
		sql +=" order by tt.bureausn ";
		try {
			List<Map<String, Object>> approveItems = new ArrayList<Map<String, Object>>();
			approveItems = jdbcTemplate.queryForList(sql, params.toArray());
			pager.setTotalRows(approveItems.size());
			pager.setPageList(jdbcTemplate.queryForList(pager.setPageSql(sql, pageNum, pageSize),params.toArray()));
		}catch(Exception e) {
			e.printStackTrace();
			log.error("搜索综合窗口事项简码失败！", e.getMessage());
		}

		return pager;
	}

	@Override
	public int saveShortCode(String itemid, String shortcode, String method) {
		int result = 0;
		try {
			if(method.equals("add")) {
				SpmApproveShort appShort = new SpmApproveShort();
				appShort.setItemid(itemid);
				appShort.setShortcode(shortcode);
				spmApproveShortRepository.save(appShort);
				result = 1;
			}else {
				result = spmApproveShortRepository.updateByItemid(itemid, shortcode);
			}
		}catch(Exception e) {
			e.printStackTrace();
			log.error("保存事项简码失败！", e.getMessage());
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> findClList(HttpServletRequest request) {
		
		List<String> params = new ArrayList<String>();
		String sql="";
		String itemid = request.getParameter("itemid");
		params.add(itemid);
				
		try {
			List<Map<String, Object>> clLists = new ArrayList<Map<String, Object>>();
			clLists = jdbcTemplate.queryForList(sql, params.toArray());
			return clLists;
		}catch(Exception e) {
			e.printStackTrace();
			log.error("搜索事项的材料列表失败！", e.getMessage());
			return null;
		}

		
	}

	@Override
	public List<Map<String, Object>> findClPoint(HttpServletRequest request) {
		List<String> params = new ArrayList<String>();
		String sql="select sl.approveitemguid itemid, st.declareannextypeguid, st.declareannextypename, l.declareannexguid  id, l.declareannexname materialname, l.declareannextabindex, s.*, "
				+" (case when instr(l.declareannexguid,o.declareannexguids2)>0 then 'checked' end) submission,"
				+" (case when instr(l.declareannexguid,o.notdeclareannexguids2)>0 then 'checked' end) NOTSUBMISSION,"
				+" (case when instr(l.declareannexguid,o.bhgsbcl2)>0 then 'checked' end) REJIGGER "
				+ "from spm_declareannexrelation sl, spm_declareannextype st, spm_declareannex l,office_spi_declareinfo o, "
				+ "(select p.listsguid, p.situation, p.reqirements, p.standards, p.points,"
				+ "replace(wm_concat(decode(t.tjlx, '0','其他','1','原件正本','2','原件副本','3','电子件','4','复印件','其他')||' '"
				+ "||case when instr(t.tjfs, '1')>0 then '校验' end||' '||case when instr(t.tjfs, '2')>0 then '校验' end"
				+ "||' '||t.tjsl||'份'),',','<br/>') tjfs "
				+ "from xzql_xzsp_listspoint p, xzql_xzsp_listsps t "
				+ "where p.listsguid=t.listsguid "
				+ "group by p.listsguid, p.situation, p.reqirements, p.standards, p.points) s "
				+ "where sl.approveitemguid=? "
				+ "and sl.declareannextypeguid=st.declareannextypeguid "
				+ "and sl.declareannexguid=l.declareannexguid "
				+ "and l.declareannexguid=s.listsguid(+) "
				+" and o.approveitemguid=sl.approveitemguid and o.WORKFLOWINSTANCE_GUID=?"
				+ "order by l.declareannextabindex ";
		params.add(request.getParameter("itemid"));		
		params.add(request.getParameter("instanceGuid"));
		try {
			List<Map<String, Object>> pointLists = new ArrayList<Map<String, Object>>();
			pointLists = jdbcTemplate.queryForList(sql, params.toArray());
			return pointLists;
		}catch(Exception e) {
			e.printStackTrace();
			//log.error("编辑收件要点失败！", e.getMessage());
			return null;
		}
	}

	@Override
	public XzqlXzspListspoint findThePoint(String id) {
		return xzqlXzspListspointRepository.findByListsguid(id);
	}

	@Override
	public List<XzqlXzspListsps> findPsList(String id) {
		
		return xzqlXzspListspsRepository.findByListsguid(id);
	}

	/**
	 * 保存材料要点
	 */
	@Override
	public XzqlXzspListspoint savePoint(XzqlXzspListspoint initPoint) {
		// TODO Auto-generated method stub
		XzqlXzspListspoint point = findThePoint(initPoint.getListsguid());
		if(point==null){
			point = new XzqlXzspListspoint();
			point.setListsguid(initPoint.getListsguid());
		}
		point.setClbh(initPoint.getClbh());
		point.setListsname(initPoint.getListsname());
		point.setReqirements(initPoint.getReqirements());
		point.setSituation(initPoint.getSituation());
		point.setStandards(initPoint.getStandards());
		return  xzqlXzspListspointRepository.save(point);
	}

	/**
	 * 保存提交方式
	 */
	@Override
	public int saveListsType(String listguid,HttpServletRequest request) {
		// TODO Auto-generated method stub
		try {
			String[] lxArr = request.getParameter("lxArr").split(":");
			String[] fsArr = request.getParameter("fsArr").split(":");
			String[] desArr = request.getParameter("slArr").split(":");
			//先删除提交方式
			String del = "delete from xzql_xzsp_listsps t where t.listsguid=?";
			jdbcTemplate.update(del, new Object[]{listguid});
			for(int i=0;i<lxArr.length;i++){
				if(!StringX.isBlank(lxArr[i])){
					int y = (i+1)*2;
					String tjfs = fsArr[y-2]+";"+fsArr[y-1];
					XzqlXzspListsps listps = new XzqlXzspListsps();
					listps.setGuid(new GUID().toString());
					listps.setListsguid(listguid);
					listps.setTjlx(lxArr[i]);
					listps.setTjfs(tjfs);
					listps.setTjsl(desArr[i]);
					xzqlXzspListspsRepository.save(listps);
				}
			}
			return 1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public Map<String, Object> findListType(String listguid) {
		// TODO Auto-generated method stub
		try {
			String sql = " select wm_concat(t.tjlx) tjlx,wm_concat(t.tjfs) tjfs,wm_concat(t.tjsl) tjsl "
					+ "from xzql_xzsp_listsps t where t.listsguid=?";
			Map<String,Object> map = jdbcTemplate.queryForMap(sql, new Object[]{listguid});
			return map;
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public OfficeSpiDeclareinfo saveTogetherReceive(OfficeSpiDeclareinfo office) {
		//向office_spi_declareinfo表插入数据
		office.setEmployeeGuid(ThreadLocalHolder.getPerson().getID());
		try {
			return spApproveRepository.save(office);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Pager findReceiveList(HttpServletRequest request, Pager pager,String status) {
		int pageNum = pager.getPageNo();
		int pageSize= pager.getPageSize();
		
		List<String> params = new ArrayList<String>();
		String sql="select t.*,to_char(t.declaredatetime,'yyyy-mm-dd') dateTime from office_spi_declareinfo t where t.datafromtype='2' and t.handlestatus=? and t.employeeguid=?";
		params.add(status);
		params.add(ThreadLocalHolder.getPerson().getID());
		String approveItemName = request.getParameter("approveItemName");
		String declaresn = request.getParameter("declaresn");
		if(!StringX.isBlank(approveItemName)) {
			sql += "and t.approveitemname like ? ";
			params.add("%"+approveItemName+"%");
		}
		if(!StringX.isBlank(declaresn)){
			sql += "and t.declaresn = ? ";
			params.add(declaresn);
		}
		sql +="  order by t.declaredatetime desc";
		try {
			List<Map<String, Object>> approveItems = new ArrayList<Map<String, Object>>();
			approveItems = jdbcTemplate.queryForList(sql, params.toArray());
			pager.setTotalRows(approveItems.size());
			pager.setPageList(jdbcTemplate.queryForList(pager.setPageSql(sql, pageNum, pageSize),params.toArray()));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return pager;
	}

	@Override
	public List<JSONObject> findItemSuggestion(String inp) {
		List<String> params = new ArrayList<String>();
		/////
		String personId = ThreadLocalHolder.getPerson().getID();
		String sql="select distinct h.shortcode||'|'||a.approveitemname key,'' suggest ,a.approveitemguid value "
				+ "from spm_approveitem a, xzql_xzsp_windowofitem s, xzql_xzsp_window w, spm_bureau d, spm_approve_short h "
				+ "where instr(w.windowusers, ?)>0 and s.windowguid=w.guid  and a.approveitemstatus='运行' "
				+ "and a.approveitemguid=s.itemid and a.adminbureauguid=d.bureauguid and a.approveitemguid=h.itemid and h.shortcode like ?";
		params.add(personId);
		/////
		/*String sql="select s.shortcode||'|'||a.approveitemname key, '' suggest, a.approveitemguid value "
				+ "from spm_approve_short s, spm_approveitem a "
				+ "where s.itemid=a.approveitemguid "
				+ "and s.shortcode like ? ";*/
		params.add(inp + "%");	
		try {
			List<Map<String, Object>> suggestionLists = new ArrayList<Map<String, Object>>();
			suggestionLists = jdbcTemplate.queryForList(sql, params.toArray());

			List<JSONObject> jsonList = new ArrayList<JSONObject>();
			for(int i=0; i<suggestionLists.size(); i++) {
				JSONObject json = new JSONObject();
				json.put("key", suggestionLists.get(i).get("KEY"));
				json.put("suggest",  suggestionLists.get(i).get("SUGGEST"));
				json.put("value", suggestionLists.get(i).get("VALUE"));
				jsonList.add(json);
			}
			
			return jsonList;
		}catch(Exception e) {
			e.printStackTrace();
			//log.error("编辑收件要点失败！", e.getMessage());
			return null;
		}
	}

	@Override
	public Pager getLingzhengList(HttpServletRequest request, Pager pager) {
		int pageNum = pager.getPageNo();
		int pageSize= pager.getPageSize();
		
		List<String> params = new ArrayList<String>();
		String sql="select * from ( "
				+ "select b.bureauguid, b.bureauname, a.approveitemguid, a.approveitemname, "
				+ "case when a.approveplace='1' then 1 else 0 end flag, b.bureautabindex, a.approveitemtabindex "
				+ "from spm_approveitem a, spm_bureau b "
				+ "where a.approveitemstatus='运行' "
				+ "and a.adminbureauguid=b.bureauguid "
				+ "and a.doctypeguid is not null "
//				+ "union all "
//				+ "select b.bureauguid, b.bureauname, a.approveitemname, t.yxtywlsh, d.declarerperson sqr, "
//				+ "'1' flag, j.isreceive, b.bureautabindex, a.approveitemtabindex "
//				+ "from t_banjie t, office_spi_declareinfo d, spm_approveitem a, spm_bureau b, office_spi_banjiejilu j "
//				+ "where t.yxtywlsh=d.declaresn "
//				+ "and d.approveitemguid=a.approveitemguid "
//				+ "and a.adminbureauguid=b.bureauguid "
//				+ "and a.doctypeguid is not null "
//				+ "and d.workflowinstance_guid=j.workflowinstance_guid "
				+ ")t where 1=1 ";
		
		if(!StringX.isBlank(request.getParameter("bureauGUID"))) {
			sql += "and t.bureauguid = ? ";
			params.add(request.getParameter("bureauGUID"));
		}
		if(!StringX.isBlank(request.getParameter("approveItemName"))) {
			sql += "and t.approveitemname like ? ";
			params.add("%"+request.getParameter("approveItemName")+"%");
		}
//		if(!StringX.isBlank(request.getParameter("yxtywlsh"))) {
//			sql += "and t.yxtywlsh like ? ";
//			params.add("%"+request.getParameter("yxtywlsh")+"%");
//		}
//		if(!StringX.isBlank(request.getParameter("sqrxm"))) {
//			sql += "and t.sqr like ? ";
//			params.add("%"+request.getParameter("sqrxm")+"%");
//		}
//		if(!StringX.isBlank(request.getParameter("isreceive"))) {
//			sql += "and t.isreceive = ? ";
//			params.add(request.getParameter("isreceive"));
//		}
		sql +="  order by bureautabindex, approveitemtabindex";
		try {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			list = jdbcTemplate.queryForList(sql, params.toArray());
			pager.setTotalRows(list.size());
			pager.setPageList(jdbcTemplate.queryForList(pager.setPageSql(sql, pageNum, pageSize),params.toArray()));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return pager;
	}

	@Override
	public Pager getScanList(HttpServletRequest request, Pager pager) {
		int pageNum = pager.getPageNo();
		int pageSize= pager.getPageSize();
		
		List<String> params = new ArrayList<String>();
		String sql="select b.bureauname, a.approveitemname, f.declaresn yxtywlsh, f.declarerperson sqr, "
				+ "case when t.state is null then '2' else t.state end state, "
				+ "case when o.isreceive is null then '2' else o.isreceive end isreceive, "
				+ "t.id, t.producedate as chengnuoriqi, f.workflowinstance_guid as instanceguid, "
				+ "t.producedate, t.validityperiod, t.ztxm, substr(a.doctypeguid, 0, 38) doctypeguid "
				+ "from office_spi_declareinfo f, spm_approveitem a, spm_bureau b, office_spi_banjiejilu o, t_bdex_docinfo t "
				+ "where f.approveitemguid=a.approveitemguid "
				+ "and a.adminbureauguid=b.bureauguid "
				+ "and a.doctypeguid is not null "
				+ "and f.workflowinstance_guid=o.workflowinstance_guid(+) "
				+ "and f.workflowinstance_guid=t.instanceguid(+) ";
		
		if(!StringX.isBlank(request.getParameter("bureauGUID"))) {
			sql += "and b.bureauguid = ? ";
			params.add(request.getParameter("bureauGUID"));
		}
		if(!StringX.isBlank(request.getParameter("approveitemname"))) {
			sql += "and a.approveitemname like ? ";
			params.add("%"+request.getParameter("approveItemName")+"%");
		}
		if(!StringX.isBlank(request.getParameter("approve"))) {
			sql += "and a.approveitemname like ? ";
			params.add("%"+request.getParameter("approve")+"%");
		}
		if(!StringX.isBlank(request.getParameter("yxtywlsh"))) {
			sql += "and f.declaresn like ? ";
			params.add("%"+request.getParameter("yxtywlsh")+"%");
		}
		if(!StringX.isBlank(request.getParameter("sqrxm"))) {
			sql += "and f.declarerperson like ? ";
			params.add("%"+request.getParameter("sqrxm")+"%");
		}
		if(!StringX.isBlank(request.getParameter("iscan"))) {
			sql += "and t.state = ? ";
			params.add(request.getParameter("iscan"));
		}
		if(!StringX.isBlank(request.getParameter("isreceive"))) {
			sql += "and o.isreceive = ? ";
			params.add(request.getParameter("isreceive"));
		}
		sql +="  order by t.state, o.isreceive, b.bureautabindex, a.approveitemtabindex";
		try {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			list = jdbcTemplate.queryForList(sql, params.toArray());
			pager.setTotalRows(list.size());
			pager.setPageList(jdbcTemplate.queryForList(pager.setPageSql(sql, pageNum, pageSize),params.toArray()));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return pager;
	}

	@Override
	public List<Map<String, Object>> loadZjlx(String type) {
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		String sql="select t.value key, t.code value from xzql_codemap t where t.type=? order by t.orderno";
		List<Object> params = new ArrayList<Object>();
		params.add(type);
		listmap = jdbcTemplate.queryForList(sql, params.toArray());
		return listmap;
	}

	@Override
	public int saveDeclareInfo(String guid, HttpServletRequest request) {
		OfficeSpiDeclareinfo osd = new OfficeSpiDeclareinfo();
		osd.setGuid(guid);
		osd.setApproveitemguid(request.getParameter("approveitemguid"));
		osd.setApproveItemName(request.getParameter("approveItemName"));
		osd.setXiangmumingcheng(request.getParameter("projectName"));
		osd.setDeclaresn("QT"+request.getParameter("yxtywlsh"));
		osd.setDeclareType(request.getParameter("sqrType"));
		osd.setDeclarerPerson(request.getParameter("sqr"));
		osd.setZhengjiandaima(request.getParameter("cardId"));
		osd.setDeclarerlxr(request.getParameter("lxr"));
		osd.setDeclarerlxrIdtype(request.getParameter("lxrType"));
		osd.setDeclarerlxrid(request.getParameter("lxrId"));
		osd.setDeclarerMobile(request.getParameter("mobile"));
		declareinfoRepository.save(osd);
		
		try {
			String insBjjlSql = "insert into office_spi_banjiejilu(workflowinstance_guid, isreceive) values (?, ?) ";
			List<String> params = new ArrayList<String>();
			params.add(guid);
			params.add("0");
			jdbcTemplate.update(insBjjlSql,params.toArray());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("插入办结记录表失败", e.getMessage());
		}
		
		
		return 1;
	}

//	@Override
//	public Pager getLingquList(HttpServletRequest request, Pager pager) {
//		int pageNum = pager.getPageNo();
//		int pageSize= pager.getPageSize();
//		
//		List<String> params = new ArrayList<String>();
//		String sql="select b.bureauname, o.approveitemname, o.declaresn yxtywlsh, isreceive,o.declaredatetime declaredatetime1, "
//				+ "to_char(o.declaredatetime,'yyyy-mm-dd') declaredatetime,to_char(t.enddate,'yyyy-mm-dd') enddate, "
//				+ "to_char(receivedate,'yyyy-mm-dd') RECEIVEDATE,t.workflowinstance_guid,o.zhengjiandaima, o.declarerperson sqr "
//				+ "from office_spi_banjiejilu  t,office_spi_declareinfo o, spm_approveitem a, spm_bureau b "
//				+ "where o.workflowinstance_guid = t.workflowinstance_guid "
//				+ "and (trim(t.docway) is not null or trim(t.certifyway) is not null) "
//				+ "and o.approveitemguid=a.approveitemguid "
//				+ "and a.adminbureauguid=b.bureauguid ";
//		
//		if(!StringX.isBlank(request.getParameter("bureauGUID"))) {
//			sql += "and t.bureauguid = ? ";
//			params.add(request.getParameter("bureauGUID"));
//		}
//		if(!StringX.isBlank(request.getParameter("approveItemName"))) {
//			sql += "and t.approveitemname like ? ";
//			params.add("%"+request.getParameter("approveItemName")+"%");
//		}
//		if(!StringX.isBlank(request.getParameter("yxtywlsh"))) {
//			sql += "and t.yxtywlsh like ? ";
//			params.add("%"+request.getParameter("yxtywlsh")+"%");
//		}
//		if(!StringX.isBlank(request.getParameter("sqrxm"))) {
//			sql += "and t.sqr like ? ";
//			params.add("%"+request.getParameter("sqrxm")+"%");
//		}
//		if(!StringX.isBlank(request.getParameter("isreceive"))) {
//			sql += "and t.isreceive = ? ";
//			params.add(request.getParameter("isreceive"));
//		}
//		sql +="  order by t.isreceive, b.bureautabindex, a.approveitemtabindex, o.declaredatetime desc";
//		try {
//			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//			list = jdbcTemplate.queryForList(sql, params.toArray());
//			pager.setTotalRows(list.size());
//			pager.setPageList(jdbcTemplate.queryForList(pager.setPageSql(sql, pageNum, pageSize),params.toArray()));
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//		return pager;
//	}
//
//	@Override
//	public String scanOrLingqu(String yxtywlsh) {
//		String sql = "select d.workflowinstance_guid from office_spi_declareinfo d where d.declaresn=? ";
//		List<Map<String, Object>> back;
//		List<String> params = new ArrayList<String>();
//		params.add(yxtywlsh);
//		try {
//			List<Map<String, Object>> temp = jdbcTemplate.queryForList(sql, params.toArray());
//			if(temp == null || temp.size()==0 ) {
//				return "0";//找不到该笔业务信息
//			}
//			
//			sql = "select t.ywlsh from t_banjie t where t.yxtywlsh=? ";
//			back = jdbcTemplate.queryForList(sql, params.toArray());
//			if(back == null || back.size()==0 ) {
//				return "1";//该业务尚未办结
//			}
//			
//			sql = "select t.id from t_bdex_docinfo t where t.businessid=? ";
//			back = jdbcTemplate.queryForList(sql, params.toArray());
//			if(back == null || back.size()==0 ) {
//				return "2";//该业务尚未录入证照信息
//			}
//			
//			sql = "select t.id from t_bdex_docinfo t where t.businessid=? and t.state='0' ";
//			back = jdbcTemplate.queryForList(sql, params.toArray());
//			if(back != null && back.size()!=0 ) {
//				return "3";//待扫描
//			}
//			
//			sql = "select t.isreceive from office_spi_banjiejilu t where t.workflowinstance_guid=? ";
//			params.clear();
//			params.add((String)temp.get(0).get("workflowinstance_guid"));
//			back = jdbcTemplate.queryForList(sql, params.toArray());
//			if(back == null || back.size()==0 ) {
//				return "4";////该业务尚未办结(应该是一种异常的状态：已经有办结数据，但是办结记录的表没有数据)
//			}else {
//				if(back.get(0).get("isreceive").equals("0")){//未领取
//					return "5";//未领取
//				}else if(back.get(0).get("isreceive").equals("1")) {//已领取
//					return "6";//已领取
//				}
//			}
//		} catch (Exception e ) {
//			e.printStackTrace();
//			log.error("判断某业务的办结状态、扫描状态、领取状态失败！", e.getMessage());
//			
//		}
//		return null;
//	}


}

