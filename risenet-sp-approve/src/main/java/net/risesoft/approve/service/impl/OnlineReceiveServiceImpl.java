package net.risesoft.approve.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.risesoft.approve.entity.base.Pager;
import net.risesoft.approve.entity.jpa.OfficeSpiDeclareinfo;
import net.risesoft.approve.entity.jpa.RisenetEmployeeUnits;
import net.risesoft.approve.entity.jpa.StuffdataXt;
import net.risesoft.approve.repository.jpa.OfficeSpiDeclareinfoRepository;
import net.risesoft.approve.repository.jpa.SpApproveRepository;
import net.risesoft.approve.service.OnlineReceiveService;
import net.risesoft.common.util.ContextUtil;
import net.risesoft.common.util.RiseBpmUtil;
import net.risesoft.model.Person;
import net.risesoft.util.ServiceUtil;
import net.risesoft.utilx.StringX;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("onlineReceiveService")
@Transactional
public class OnlineReceiveServiceImpl implements OnlineReceiveService {

	@Resource(name = "routerDataSource")
	private DataSource routerDataSource;

	private JdbcTemplate jdbcTemplate;
	 
	@PostConstruct
	private void afterIoc() {
		jdbcTemplate = new JdbcTemplate(this.routerDataSource);
	}
	
	@Autowired
	private SpApproveRepository spApproveRepository;
	
	@Autowired
	private OfficeSpiDeclareinfoRepository officeSpiDeclareinfoRepository;
	//加载网上收件列表
	@Override
	public Pager findByUserID(Person person,String ishf,String status,String approveName,Pager pager) {
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		List<String> params = new ArrayList<String>();
		String rcid = person.getID();
		int pageNum = pager.getPageNo();
		int pageSize= pager.getPageSize();
		try {
			//RisenetEmployeeUnits risenetInfo = ServiceUtil.riseInfo(jdbcTemplate,rcid);
			//查询属于该科室或者部门的事项
			String sql="select wm_concat(distinct a.approveitemguid) guid"					
					+ " from spm_approveitem a,xzql_xzsp_windowofitem s,xzql_xzsp_window w ,spm_bureau d"
					+ " where  a.approveitemguid=s.itemid and trim(a.adminbureauguid) = trim(d.bureauguid) and w.guid=s.windowguid and instr(w.windowusers,?)>0"
					+ " and a.approveitemstatus='运行'";
			params.clear();
			params.add(rcid);
			//params.add(risenetInfo.getBureauGuid());
			String guid = jdbcTemplate.queryForObject(sql, params.toArray(),String.class);
			String bureau = person.getOrganization().getName();
			//查询属于该科室或者部门的业务
			
			sql="select t.declaresn,t.approveitemname FORMNAME,t.declarerperson UNAME,sp.approveitemguid,nvl(b.bureaucnshortname,'"+bureau+"') WORKNAME,sp.workflowguid,to_char(t.declaredatetime,'yyyy-mm-dd hh24:mi:ss') submittime,t.handlestatus,t.workflowinstance_guid guid "
					+ " from office_spi_declareinfo t,spm_approveitem sp,spm_bureau b"
					+ " where t.approveitemguid=sp.approveitemguid and instr(?,t.approveitemguid)>0 "
					+ " and t.handlestatus=? and t.bureauguid=b.bureauguid(+) and t.datafromtype='0'";
			
			params.clear();
			
			params.add(guid);
			if(StringX.isBlank(ishf) || "0".equals(ishf)){
				sql += " and (t.replystatus=0 or t.replystatus=2)";
			}else if("1".equals(ishf)){
				sql += " and t.replystatus=1";
			}
			
			if(StringX.isBlank(status)){
				params.add("未受理");
			}else{
				params.add(status);
			}
			if(!StringX.isBlank(approveName)){
				sql += " and t.approveitemname like ?";
				params.add("%"+approveName+"%");
			}
			sql += "  order by t.replystatus,t.handlestatus  ,t.declaredatetime desc";
			listmap = jdbcTemplate.queryForList(sql, params.toArray());
			pager.setTotalRows(listmap.size());
			pager.setPageList(jdbcTemplate.queryForList(pager.setPageSql(sql, pageNum, pageSize),params.toArray()));
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return pager;
	}
	
	public Pager findByUserIDNew(Person person,String ishf,String status,String approveName,Pager pager) {
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		List<String> params = new ArrayList<String>();
		String rcid = person.getID();
		int pageNum = pager.getPageNo();
		int pageSize= pager.getPageSize();
		try {
			//RisenetEmployeeUnits risenetInfo = ServiceUtil.riseInfo(jdbcTemplate,rcid);
			//查询属于该科室或者部门的事项
			String sql="select wm_concat(distinct a.APPROVEITEMGUID) guid"					
					+ " from spm_approveitem a,xzql_xzsp_windowofitem s,xzql_xzsp_window w ,spm_bureau d"
					+ " where  a.approveitemguid=s.itemid and trim(a.adminbureauguid) = trim(d.bureauguid) and w.guid=s.windowguid and instr(w.windowusers,?)>0"
					+ " and a.approveitemstatus='运行'";
			params.clear();
			params.add(rcid);
			//params.add(risenetInfo.getBureauGuid());
			String guid = jdbcTemplate.queryForObject(sql, params.toArray(),String.class);
			String bureau = person.getOrganization().getName();
			//查询属于该科室或者部门的业务
			
			//sql="select t.declaresn,t.approveitemname FORMNAME,t.declarerperson UNAME,sp.approveitemguid,nvl(b.bureaucnshortname,'"+bureau+"') WORKNAME,sp.workflowguid,to_char(t.declaredatetime,'yyyy-mm-dd hh24:mi:ss') submittime,t.handlestatus,t.workflowinstance_guid guid "
			//		+ " from office_spi_declareinfo t,spm_approveitem sp,spm_bureau b"
			//		+ " where t.approveitemguid=sp.approveitemguid and instr(?,t.approveitemguid)>0 "
			//		+ " and t.handlestatus=? and t.bureauguid=b.bureauguid(+) and t.datafromtype='0'";
			params.clear();
			sql="select s.sxbm APPROVEITEMGUID,s.sblsh DECLARESN,s.sxmc FORMNAME,b.bureauname WORKNAME,s.SQRMC UNAME,s.SBSJ SUBMITTIME,s.sxqxbm HANDLESTATUS"
					+ " from ex_gdbs_sb s,spm_approveitem a,spm_bureau b"
					+ " where s.sxbm=a.approveitemguid and a.BUREAUGUID=b.BUREAUGUID "
					+ " and instr(?,s.sxbm)>0 and s.sxqxbm=?";
			params.add(guid);
			if(StringX.isBlank(ishf) || "0".equals(ishf)){
				//sql += " and (t.replystatus=0 or t.replystatus=2)";
			}else if("1".equals(ishf)){
				//sql += " and t.replystatus=1";
			}
			
			if(StringX.isBlank(status)){
				params.add("未受理");
			}else{
				params.add(status);
			}
			if(!StringX.isBlank(approveName)){
				//sql += " and t.approveitemname like ?";
				//params.add("%"+approveName+"%");
			}
			//sql += "  order by t.replystatus,t.handlestatus  ,t.declaredatetime desc";
			sql += "  order by s.sxqxbm  ,s.SBSJ desc";
			listmap = jdbcTemplate.queryForList(sql, params.toArray());
			pager.setTotalRows(listmap.size());
			pager.setPageList(jdbcTemplate.queryForList(pager.setPageSql(sql, pageNum, pageSize),params.toArray()));
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return pager;
	}

	//修改网上预受理状态
	@Override
	public void updateStatus(String status, String guid) {
		officeSpiDeclareinfoRepository.updateStatus(status, guid);
		
	}

	@Override
	public void insertSpiDeclareInfo(String guid,String ytjs,String xbqs,String xbzs) {
		// 从申办表中查询所需信息
		//String sql = "select * from office_spi_declareinfo t where t.workflowinstance_guid=?";
		try {
//			OfficeSpiDeclareinfo officeSpi = jdbcTemplate.queryForObject(sql,new String[]{guid},new BeanPropertyRowMapper<OfficeSpiDeclareinfo>(OfficeSpiDeclareinfo.class));
			OfficeSpiDeclareinfo officeSpi = spApproveRepository.findByGuid(guid);
			officeSpi.setDeclareAnnexGuids2(ytjs);
			officeSpi.setNotDeclareAnnexGuids2(xbqs);
			officeSpi.setBhgsbcl2(xbzs);
			officeSpi.setGuid(guid);
			officeSpi.setHandleStatus("预受理");
			//向office_spi_declareinfo表插入数据
			spApproveRepository.save(officeSpi);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
		
	}
	
	/*public Map getUserInfo(String userType,String guid){
		String sql="";
		if("1".equals(userType)){
			sql = "select t.true_name lxr,t.native_add address,t.idcard_code code,t.mobile,"
					+ "t.contact_tel phone from t_out_personuser t where t.logonguid=?";
			
		}else{
			sql="select t.ename name,t.address address,t.orgcode code,t.lawperson,t.contactphone phone,"
					+ "t.contactmobile mobile,t.email,t.truename lxr from T_OUT_COMPANYUSER t where t.logonguid=?";
		}
		return jdbcTemplate.queryForMap(sql, new String[]{guid});
	}*/

	@Override
	public OfficeSpiDeclareinfo isYushouli(String yxtywlsh) {
		String sql = "select * from office_spi_declareinfo t where trim(t.declaresn)=?";
		return jdbcTemplate.queryForObject(sql, new String[]{yxtywlsh},new BeanPropertyRowMapper<OfficeSpiDeclareinfo>(OfficeSpiDeclareinfo.class));
	}

	@Override
	public int undoYslCount(Person person) {
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		List<String> params = new ArrayList<String>();
		String rcid = person.getID();
		try {
			//查询属于该科室或者部门的事项
			String sql="select distinct a.approveitemguid guid"
					+ " from spm_approveitem a,xzql_xzsp_windowofitem s,xzql_xzsp_window w ,spm_bureau d"
					+ " where  a.approveitemguid=s.itemid and a.adminbureauguid=d.bureauguid and w.guid=s.windowguid and instr(w.windowusers,?)>0";
			params.clear();
			params.add(rcid);
			//params.add(risenetInfo.getBureauGuid());
			List<String> guids = jdbcTemplate.queryForList(sql, params.toArray(),String.class);
			//查询属于该科室或者部门的业务
			sql="select count(1) from office_spi_declareinfo t,spm_approveitem sp,spm_bureau b"
					+ " where t.approveitemguid=sp.approveitemguid and instr(?,t.approveitemguid)>0 and (t.replystatus='0' or t.replystatus='2')"
					+ " and t.handlestatus='未受理' and t.bureauguid=b.bureauguid(+) and t.datafromtype='0'";
			params.clear();
			params.add(RiseBpmUtil.join(guids, ","));
			sql += "  order by t.replystatus,t.handlestatus  ,t.declaredatetime desc";
			listmap = jdbcTemplate.queryForList(sql, params.toArray());
			return jdbcTemplate.queryForObject(sql,params.toArray(),Integer.class);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
	public List<Map<String, Object>> getDeclarerperson(String userid){
		String sql = "select CARDID from sp_approveweb_user where GUID='"+userid+"'";
		List<Map<String, Object>> declarerpersonList = jdbcTemplate.queryForList(sql);
		return declarerpersonList;
	}
	
	public List<Map<String, Object>> getGXCaiLiaoList(String cardId){
		String sql=" select DOCNAME,to_char(to_date(PRODUCEDATE,'YYYY-MM-DD HH24:MI:SS'),'YYYY-MM-DD') as PRODUCEDATE,to_char(ZZYXQ,'YYYY-MM-DD') as ZZYXQ,INSTANCEGUID from T_BDEX_CERTIFIATEINFO where INSTANCEGUID in (select WORKFLOWINSTANCE_GUID from OFFICE_SPI_DECLAREINFO where ZHENGJIANDAIMA='"+cardId+"')   ";
		List<Map<String, Object>> gxZhengZhaoList = jdbcTemplate.queryForList(sql);
		return gxZhengZhaoList;
	}
	
	public List<Map<String, Object>> getFujianList(String instanceguid){
		String sql=" select FILENAME,to_char(CREATEDATE,'YYYY-MM-DD') as CREATEDATE,FILEGUID from RISENET_FILE where APPINSTGUID='"+instanceguid+"'  ";
		List<Map<String, Object>> getFujianList = jdbcTemplate.queryForList(sql);
		return getFujianList;
	}
	
	public int deleteGXZZ(String instanceguid){
		String sql = "delete from T_BDEX_CERTIFIATEINFO where INSTANCEGUID = '"+instanceguid+"'";
		int a = 0;
		a=jdbcTemplate.update(sql);
		return a;
	}
	
	public int onDeleteFJ(String fileguid){
		String sql = "delete from RISENET_FILE where FILEGUID = '"+fileguid+"'";
		int a = 0;
		a=jdbcTemplate.update(sql);
		return a;
	}
	
	public int updateGXZZ(String instanceguid,String docname,String producedate,String zzyxq){
		String sql="update T_BDEX_CERTIFIATEINFO set DOCNAME='"+docname+"',PRODUCEDATE='"+producedate+"',ZZYXQ=to_date('"+zzyxq+"','YYYY-MM-DD') where INSTANCEGUID='"+instanceguid+"'";
		
		return jdbcTemplate.update(sql);
	}
	//根据材料类型查找材料
	@Override
	public List<Map<String, Object>> findListsByTypeGuid(String typeGuid,String instanceId,String itemId) {
		try {
			String sql ="select d.declareannexname, decode(w.declareannexguid,null,'0','1') type "
					+ "from spm_declareannex d, spm_declareannexrelation r, spm_wssbcl w "
					+ "where d.declareannexguid=r.declareannexguid and r.declareannextypeguid=? "
					+ "and w.declareannexguid(+) = d.declareannexguid and d.approveitemguid=? and w.workflowinstance_guid(+)=?";
			List<String> params = new ArrayList<String>();
			params.add(typeGuid);
			params.add(instanceId);
			params.add(itemId);
			return jdbcTemplate.queryForList(sql, params.toArray());
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	//查找材料类型
	@Override
	public String getTypeName(String typeGuid, String itemId) {
		// TODO Auto-generated method stub
		return null;
	}
	public List<Map<String, Object>> getPanDuanList(String declareannexguid){
		String sql = "select * from spm_declareannex t where t.declareannexguid= '"+declareannexguid+"' ";
		return jdbcTemplate.queryForList(sql);
	}
	
	@Override
	public void download(String id, HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		String filename="";
		String rootpath ="";
		String linuxpath = "";
		String fileName="";
		File file = null;
		String sql = " select REALFULLPATH from risenet_file where FILEGUID='"+id+"' ";
		rootpath = jdbcTemplate.queryForList(sql).get(0).get("REALFULLPATH").toString();
		rootpath = rootpath.substring(2);
		String sql2 = " select REALFULLPATH from risenet_file where FILEGUID='"+id+"' ";
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sql2);
		
		if(jdbcTemplate.queryForList(sql2).get(0).get("LINUXPATH")==null){
			linuxpath="";
		}else{
			linuxpath = jdbcTemplate.queryForList(sql2).get(0).get("LINUXPATH").toString();

		}
		String sql3 = " select FILENAME from risenet_file where FILEGUID='"+id+"' ";
		fileName = jdbcTemplate.queryForList(sql3).get(0).get("FILENAME").toString();
		if(rootpath!=null && rootpath.length()>0){
			file = new File(rootpath);
		}else{
			file = new File(linuxpath);
		}
		InputStream input = new FileInputStream(file);  
	    byte[] data = IOUtils.toByteArray(input); 
	    if(request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0){
	    	filename = URLEncoder.encode(fileName, "UTF-8");//IE浏览器
	    }else{//request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0
	    	filename = new String(fileName.getBytes("UTF-8"), "ISO8859-1");//火狐浏览器
	    }
	    response.setContentType("application/octet-stream");
		response.setHeader("Content-disposition", "attachment; filename=\""+filename+"\"" );
		response.setHeader("Content-Length", String.valueOf(file.length()));
		IOUtils.write(data, response.getOutputStream());
		response.flushBuffer();		
				
	}
}
