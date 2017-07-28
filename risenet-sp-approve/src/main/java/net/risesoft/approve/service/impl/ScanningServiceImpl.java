package net.risesoft.approve.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import net.risesoft.approve.entity.base.Pager;
import net.risesoft.approve.entity.jpa.OfficeSpiDeclareinfo;
import net.risesoft.approve.entity.jpa.RisenetEmployeeUnits;
import net.risesoft.approve.entity.jpa.TBdexDoctype;
import net.risesoft.approve.entity.jpa.certificate.CspsxuBean;
import net.risesoft.approve.entity.jpa.certificate.QsxkBean;
import net.risesoft.approve.entity.jpa.certificate.XzxkjdsBean;
import net.risesoft.approve.repository.jpa.certificate.CspsxuRepository;
import net.risesoft.approve.repository.jpa.certificate.QsxkRepository;
import net.risesoft.approve.repository.jpa.certificate.XzxkjdsRepository;
import net.risesoft.approve.service.OfficeSpiDeclareinfoService;
import net.risesoft.approve.service.ScanningService;
import net.risesoft.model.OrgUnit;
import net.risesoft.model.Person;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.GUID;
import net.risesoft.util.RisesoftUtil;
import net.risesoft.util.ServiceUtil;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service(value="scanningServiceImpl")
public class ScanningServiceImpl implements ScanningService{
	@Resource(name = "routerDataSource")
	private DataSource routerDataSource;

	private JdbcTemplate jdbcTemplate;

	@PostConstruct
	private void afterIoc() {
		jdbcTemplate = new JdbcTemplate(this.routerDataSource);
	}
	/*@Autowired
	private XzxkjdsRepository xzxkjdsRepository;*/
	
	@Autowired
	private QsxkRepository qsxkRepository;
	
	@Autowired
	private CspsxuRepository cspsxuRepository;
	
	@Autowired
	private OfficeSpiDeclareinfoService officeSpiDeclareinfoService;
	
	private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 
	 * @param 按照登录人权限获取待扫描信息列表
	 * @return
	 */
	public Pager findByUserID(Person person,String param,Pager page,String code,String applyName) {
		int pageNum = page.getPageNo();
		int pageSize= page.getPageSize();
		List<String> params = new ArrayList<String>();
		String userid = person.getID();
		OrgUnit org = RisesoftUtil.getPersonManager().getBureau(ThreadLocalHolder.getTenantId(), userid);
		
		//查询属于该科室或者部门的事项
//		String sql = "select distinct t.approveitemguid as ID  from SPM_APPROVEITEM t, spm_approveitem_depart d "
//				+ "where t.approveitemguid = d.approveitemguid and RTRIM(d.departmentguid)=? and RTRIM(d.bureauguid)=?";
//		params.add(person.getParentID());
		String sql = "select distinct a.approveitemguid as ID from spm_approveitem        a, xzql_xzsp_windowofitem s, "
				+ "xzql_xzsp_window       w, spm_bureau             d "
				+ "where a.approveitemguid = s.itemid and a.adminbureauguid = d.bureauguid "
				+ "and w.guid = s.windowguid and instr(w.windowusers, ?) > 0";
		params.add(person.getID());
//		params.add(org.getID());
		List<Map<String, Object>> strList = jdbcTemplate.queryForList(sql,params.toArray());
		StringBuilder strParam=new StringBuilder();
		strParam.append("( ");
		int i=0;
		for(Map e : strList){
			if(i==strList.size()-1){
				strParam.append("'"+e.get("ID")+"' )");
			}else{
				strParam.append("'"+e.get("ID")+"',");
				i++;
			}
		}

		List<Map<String, Object>> returnData = null;
		List<String> params2 = new ArrayList<String>();
		StringBuilder sql2=new StringBuilder();
		sql2.append("select distinct t.Id as id,f.DECLARESN as DECLARESN,f.APPROVEITEMNAME as APPROVEITEMNAME, "
				+ "f.DECLARERPERSON as DECLARERPERSON, t.PRODUCEDATE as CHENGNUORIQI,f.workflowinstance_guid as INSTANCEGUID, "
				+ "t.PRODUCEDATE,t.VALIDITYPERIOD,t.ZTXM from T_BDEX_DOCINFO t left join OFFICE_SPI_DECLAREINFO f "
				+ "on t.instanceguid=f.workflowinstance_guid where t.state ='0' and  f.approveitemguid in "
				+ strParam.toString());
		if(param!=null&& param !=""){
			params2.add("%"+param+"%");
			sql2.append("  and f.APPROVEITEMNAME like ?");
		}
		if(code!=null && code !=""){
			params2.add("%"+code+"%");
			sql2.append("  and f.DECLARESN like ?");
		}
		if(applyName!=null&& applyName !=""){
			params2.add("%"+applyName+"%");
			sql2.append("  and f.DECLARERPERSON like ?");
		}
		sql2.append("  order by t.PRODUCEDATE desc ");
		returnData = jdbcTemplate.queryForList(sql2.toString(),params2.toArray());
		page.setTotalRows(returnData.size());
		page.setPageList(jdbcTemplate.queryForList(page.setPageSql(sql2.toString(),
				pageNum, pageSize),params2.toArray()));
		return page;
	}

	/**
	 * 保存
	 * 
	 * @param id
	 *            是审批件基本信息表的主键
	 * @return
	 */
	public String save(String id) {
//		Person person = ThreadLocalHolder.getPerson();
//		RisenetEmployeeUnits risenetInfo = ServiceUtil.riseInfo(jdbcTemplate,
//				person.getID());
//		String sql = "select t.*  from OFFICE_SPI_DECLAREINFO t where t.WORKFLOWINSTANCE_GUID=?";
		String result = "SUCCESS";
		
//		String code = data.get(0).get("DECLARESN").toString().trim();
//		String itemId = data.get(0).get("APPROVEITEMGUID").toString().trim();
//		String sql1 = "select t.guid as gid from t_bdex_doctype t where t.bguid ='"
//				+ risenetInfo.getBureauGuid() + "'";
//		// String
//		// sql1="select t.ID as Id ,t.PID as pid ,t.NAME as name from VIEW_XZSP_ITEMTREEDIR t where t.ID=?";
//		List<Map<String, Object>> docIdList = jdbcTemplate.queryForList(sql1);
//		StringBuilder idStr=new StringBuilder();
//		idStr.append("( ");
//		int i=0;
//		for(Map e : docIdList){
//			String gid=e.get("GID")!=null?e.get("GID").toString():"";
//			if(i==docIdList.size()-1){
//				idStr.append("'"+gid+"' )");
//			}else{
//				idStr.append("'"+gid+"',");
//			}
//			i++;
//		}
		//先判断T_BDEX_DOCINFO表里是否已经生成证照
		List<String> params = new ArrayList<String>();
		String sql2="select distinct t.*  from T_BDEX_DOCINFO t where t.state = '0' and t.id= ? ";
		if(id!=null&& id !=""){
			params.add(id);
			
		}
		List<Map<String, Object>> docInfos = jdbcTemplate.queryForList(sql2,params.toArray());
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now=sdf.format(new Date());
			
			
		//已经生成，就执行添加修改时间	
		if (docInfos.isEmpty()) {
			
			String updatesql = " update T_BDEX_CERTIFIATEINFO t set t.MODIFYDATE=to_date('" + sdf.format(new Date())+ "','yyyy-MM-dd hh24:mi:ss') where t.id=? ";
			try{
				jdbcTemplate.update(updatesql,params.toArray());
			}catch(Exception e){
				e.getStackTrace();
				result="ERROR";
			}
			
			return result;
		}else{
			//已经生成，就执行插入数据到T_BDEX_CERTIFIATEINFO	
			String docinfoId = docInfos.get(0).get("DOCINFOID") != null ? docInfos.get(0).get("DOCINFOID")
					.toString() : "";
			String docid = docInfos.get(0).get("DOCID") != null ? docInfos.get(0).get("DOCID")
							.toString() : "";
			String businessid = docInfos.get(0).get("BUSINESSID") != null ? docInfos.get(0).get(
					"BUSINESSID").toString() : "";
			String admindivision = docInfos.get(0).get("ADMINDIVISION") != null ? docInfos.get(0).get(
					"ADMINDIVISION").toString() : "";
			String organcode = docInfos.get(0).get("ORGANCODE") != null ? docInfos.get(0).get("ORGANCODE")
					.toString() : "";
			String docstatus = docInfos.get(0).get("DOCSTATUS") != null ? docInfos.get(0).get("DOCSTATUS")
					.toString() : "";
			String docname = docInfos.get(0).get("DOCNAME") != null ? docInfos.get(0).get("DOCNAME")
					.toString() : "";
			String docno = docInfos.get(0).get("DOCNO") != null ? docInfos.get(0).get("DOCNO").toString()
					: "";
			String validityperiod = docInfos.get(0).get("VALIDITYPERIOD") != null ? docInfos.get(0).get(
					"VALIDITYPERIOD").toString() : "";
			String producedate = docInfos.get(0).get("PRODUCEDATE") != null ? docInfos.get(0).get(
					"PRODUCEDATE").toString() : "";
			String produceorgan = docInfos.get(0).get("PRODUCEORGAN") != null ? docInfos.get(0).get(
					"PRODUCEORGAN").toString() : "";
			String docowner = docInfos.get(0).get("DOCOWNER") != null ? docInfos.get(0).get("DOCOWNER")
					.toString() : "";

			String content = docInfos.get(0).get("CONTENT") != null ? docInfos.get(0).get("CONTENT")
					.toString() : "";
			String othercontents = docInfos.get(0).get("OTHERCONTENTS") != null ? docInfos.get(0).get(
					"OTHERCONTENTS").toString() : "";
			String isexistattachment = docInfos.get(0).get("ISEXISTATTACHMENT") != null ? docInfos.get(0)
					.get("ISEXISTATTACHMENT").toString() : "";
			String remark = docInfos.get(0).get("REMARK") != null ? docInfos.get(0).get("REMARK")
					.toString() : "";
			String by1 = docInfos.get(0).get("BY1") != null ? docInfos.get(0).get("BY1").toString() : "";
			String by2 = docInfos.get(0).get("BY2") != null ? docInfos.get(0).get("BY2").toString() : "";
			String by3 = docInfos.get(0).get("BY3") != null ? docInfos.get(0).get("BY3").toString() : "";
			String querystatus = docInfos.get(0).get("QUERYSTATUS") != null ? docInfos.get(0).get(
					"QUERYSTATUS").toString() : "";
			//存入自己的库当然是已导出证照了
			String state = "1";
			//GUID主键
			String guid = new GUID().toString();
			String doctypeguid = docInfos.get(0).get("DOCTYPEGUID") != null ? docInfos.get(0).get(
					"DOCTYPEGUID").toString() : "";
			String jkstate = docInfos.get(0).get("JKSTATE") != null ? docInfos.get(0).get("JKSTATE")
					.toString() : "";
			String instanceguid = docInfos.get(0).get("INSTANCEGUID") != null ? docInfos.get(0).get(
					"INSTANCEGUID").toString() : "";
			String ztxm = docInfos.get(0).get("ZTXM") != null ? docInfos.get(0).get("ZTXM").toString() : "";
			String zcaddr = docInfos.get(0).get("ZCADDR") != null ?docInfos.get(0).get("ZCADDR")
					.toString() : "";
			String jyaddr = docInfos.get(0).get("JYADDR") != null ? docInfos.get(0).get("JYADDR")
					.toString() : "";
			String zzlx = docInfos.get(0).get("ZZLX") != null ? docInfos.get(0).get("ZZLX").toString() : "";
			String issenttotj = docInfos.get(0).get("ISSENTTOTJ") != null ? docInfos.get(0).get(
					"ISSENTTOTJ").toString() : "";	
 

		Person person  =  ThreadLocalHolder.getPerson();

		String insert = "insert into T_BDEX_CERTIFIATEINFO(ID,DOCTYPEGUID,GUID,DOCINFOID,BUSINESSID"
				+ ",ADMINDIVISION,ORGANCODE,DOCSTATUS,DOCNAME,DOCNO,VALIDITYPERIOD,PRODUCEDATE,PRODUCEORGAN,DOCOWNER,CONTENT,"
				+ "OTHERCONTENTS,ISEXISTATTACHMENT,REMARK,BY1,BY2,BY3,QUERYSTATUS,STATE,JKSTATE,INSTANCEGUID,ZTXM,ZCADDR,JYADDR,ZZLX,ISSENTTOTJ,DOCID,ZSSCRYID,ADDDATE)"
				+ " values('"
				+ id
				+ "','"
				+ doctypeguid
				+ "','"
				+ new GUID().toString()
				+ "','"
				+ docinfoId
				+ "','"
				+ businessid
				+ "','"
				+ admindivision
				+ "'"
				+ ",'"
				+ organcode
				+ "','"
				+ docstatus
				+ "','"
				+ docname
				+ "','"
				+ docno
				+ "','"
				+ validityperiod
				+ "','"
				+ producedate
				+ "','"
				+ produceorgan
				+ "','"
				+ docowner
				+ "','"
				+ content
				+ "','"
				+ othercontents
				+ "','"
				+ isexistattachment
				+ "','"
				+ remark
				+ "','"
				+ by1
				+ "','"
				+ by2
				+ "','"
				+ by3
				+ "','"
				+ querystatus
				+ "','"
				+ state
				+ "','"
				+ jkstate
				+ "','"
				+ instanceguid
				+ "','"
				+ ztxm
				+ "','"
				+ zcaddr
				+ "','" + jyaddr + "','" + zzlx + "','" + issenttotj +"','" + docid +"','" + person.getID() +"',to_date('" + now+ "','yyyy-MM-dd hh24:mi:ss'))";
		try{
			jdbcTemplate.execute(insert);
			//改变成已发
			String changeStateSql="update T_BDEX_DOCINFO t set t.state='1' where t.instanceguid=?";
			int a=jdbcTemplate.update(changeStateSql, instanceguid);
			if (a!=1) {
				result = "ERROR";
				return result;
			}

		}catch(Exception e){
			e.getStackTrace();
			result="ERROR";
		}
		}
		return result;
	}

	/**
	 * 页面初始化
	 * 
	 * @return
	 */
	public String getSelecction() {
		Person person  =  ThreadLocalHolder.getPerson();
		RisenetEmployeeUnits risenetInfo = ServiceUtil.riseInfo(jdbcTemplate,person.getID());
		String sql="select distinct t.guid as id,t.DOCTYPENAME as name from T_BDEX_DOCTYPE t  where bguid = '"+risenetInfo.getDepartmentGuid()+"'";
		//String sql="select distinct t.guid as value,t.DOCTYPENAME as key from T_BDEX_DOCTYPE t  where bguid = '{BFA7BA9B-FFFF-FFFF-AD1A-B27A00000014}'";
		List<Map<String,Object>> selectData=jdbcTemplate.queryForList(sql);//用来选择
		JSONObject json=new JSONObject();
//		List<String> ids=new ArrayList<String>();
//		List<String> names=new ArrayList<String>();
		for(Map e : selectData){
			e.put("key", e.get("KEY").toString());
			//e.remove("KEY");
			e.put("value", e.get("VALUE").toString());
			//e.remove("VALUE");
		}
		json.put("list", selectData);
		return json.toString();
	}

	/**
	 * 根据approveitemname查询到当前事项基本信息
	 * 
	 * @return
	 */
	@Override
	public TBdexDoctype findBybasicifnoID(String APPROVEITEMNAME) {
		List<String> params = new ArrayList<String>();
		TBdexDoctype doctype=null;
		//查询当前事项基本信息项
		String sql = "select * from t_bdex_doctype t  where  t.guid = ?";
		params.add(APPROVEITEMNAME);
		try{
			doctype = jdbcTemplate.queryForObject(sql, params.toArray(),new BeanPropertyRowMapper<TBdexDoctype>(TBdexDoctype.class));
		}catch(EmptyResultDataAccessException ex){
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return doctype;
	}

	
	/**
	 * 根据doctypeguid查询到当前事项基本信息项
	 * 
	 * @return
	 */
	@Override
	public List<Map<String, Object>> findbasicinfo(String doctypeguid) {
		List<String> params = new ArrayList<String>();
		List<Map<String,Object>> listtemp=new ArrayList<Map<String,Object>>();
		String sql = "select t.infocode,t.infomemo from t_bdex_doctypeinfo t   where  t.doctypeguid =? and glguid is null";
		params.add(doctypeguid);
		try{
			
			listtemp = jdbcTemplate.queryForList(sql, params.toArray());
		}catch(Exception e){
			e.printStackTrace();
		}
		return listtemp;
	}


	/**
	 * 根据instanceGuid查询到当前证照编号
	 * 
	 * @return
	 */
	@Override
	public String findByinstanceID(String instanceGuid) {
		String BUREAUGUID ="";
		String sql = "select distinct t.BANJIEUNIT from OFFICE_SPI_BANJIEJILU t   where  t.PROCESSINSTANCEID =? ";
		
		try{
			 BUREAUGUID=jdbcTemplate.queryForObject(sql, String.class,new Object[]{instanceGuid});
		}catch(Exception e){
			e.printStackTrace();
		}
		return BUREAUGUID;
	}

	
	/**
	 * 保存当前事项必须信息项
	 * 
	 * @return
	 */
	@Override
	public void savedocinfodate(Map<String, Object> infomap) {
		Map<String,Object> listmap = new HashMap<>();
		String instanceGuid = infomap.get("instanceGuid").toString();
		String doctypeguid = infomap.get("doctypeguid").toString();
		String bbzzbh = infomap.get("zhengzbh").toString();
		String bbzzyxq = infomap.get("zhengzyxq").toString();
		String bbfzrq = infomap.get("fazrq").toString();
		String bbfzdw = infomap.get("fazdw").toString();
		String ztxm = infomap.get("zhengzztxm").toString();
		String zcaddr = infomap.get("zhucdz").toString();
		String jyaddr = infomap.get("jingydz").toString();
		String content = infomap.get("zhengznr").toString();
		String zzlx = infomap.get("zhengzlx").toString();
		String zzxx = infomap.get("zzxx").toString();
		
		List<String> zzxxlt=new ArrayList<String>();
		   int zzxxlength=((zzxx.length()-1)/2000)+1;
		   for(int i=0;i<zzxxlength;i++){
			   if(zzxx.length()<(2000*(i+1))){
				   zzxxlt.add(zzxx.substring(2000*i, zzxx.length()));
			   }else{
				   zzxxlt.add(zzxx.substring(2000*i, (2000*(i+1)-1)));
			   }
		   }			   
		//加入关联字段的内容
		//受理业务唯一流水号
		   String sql1 = "select distinct t.declaresn from office_spi_declareinfo t where RTRIM(t.workflowinstance_guid)= ? ";
		   String declarsn= "";
		   try{
			   declarsn=   jdbcTemplate.queryForObject(sql1, String.class,new Object[]{instanceGuid});   
			}catch(Exception e){
				e.printStackTrace();
			}
		   //申报主体机构代码或个人身份证号码
		   String sql2 ="select distinct t.zhengjiandaima from office_spi_declareinfo t where RTRIM(t.workflowinstance_guid)= ?";
		   String zhengjiandaima="";	
		   try{
			   zhengjiandaima=   jdbcTemplate.queryForObject(sql2, String.class,new Object[]{instanceGuid});   
			}catch(Exception e){
				e.printStackTrace();
			}
		   //检索流水号生成。检索流水号，生成方式为：组织机构代码+docid+‘440303’+docinfoid+目前时间年月日时分秒。
		   //如：'455758387','sjj_zz_001', '440303','深审政投报[2005]295'，'20120321165013'合并一起。
		   //docinfoid证照信息唯一id。不知道生成规则默认为"深罗"+bbzzbh
		   String sql3 = "select distinct t.bguid from t_bdex_doctype t where t.guid= ?";
		   String bguid="";
		   try{
			   bguid=   jdbcTemplate.queryForObject(sql3, String.class,new Object[]{doctypeguid});   
			}catch(Exception e){
				e.printStackTrace();
			}
		   
		   String sql4 = "select distinct t.doctypecode from t_bdex_doctype t where t.guid= ?";
		   String docid= "";
		   try{
			   docid=   jdbcTemplate.queryForObject(sql4, String.class,new Object[]{doctypeguid});   
			}catch(Exception e){
				e.printStackTrace();
			}
		   
		   
		   String sql5 = "select t.institutioncode from spm_bureau t where t.bureauguid= ? ";
		   String bcode="";
		   try{
			   bcode=   jdbcTemplate.queryForObject(sql5, String.class,new Object[]{bguid});   
			}catch(Exception e){
				e.printStackTrace();
			}
		   
		   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		   String ly_time = sdf.format(new java.util.Date());	
		   ly_time=ly_time.replaceAll("-", "");
		   //BdexConst.LHCODE 暂时直接写成440303
		   String LHCODE = "440303";
		   String id=bcode+docid+LHCODE+bbzzbh+ly_time;
		   String docinfoid=bbzzbh;
		   String businessid=declarsn;
		   String admindivision=LHCODE;
		   String OrganCode=zhengjiandaima;
		   String docStatus="V";
		   
		   String sql6 = "select distinct t.doctypename from t_bdex_doctype t where t.guid= ?";
		   String DocName="";
		   try{
			   DocName=   jdbcTemplate.queryForObject(sql6, String.class,new Object[]{doctypeguid});   
			}catch(Exception e){
				e.printStackTrace();
			}
		   
		   String DocNO=bbzzbh;
		   String validityperiod=bbzzyxq;
		   String producedate=bbfzrq;
		   String produceorgan=bbfzdw;
		   
		   String sql7 = "select distinct t.declarerperson from office_spi_declareinfo t where RTRIM(t.workflowinstance_guid)= ?";
		   String DocOwner="";
		   try{
			   DocOwner=   jdbcTemplate.queryForObject(sql7, String.class,new Object[]{instanceGuid});   
			}catch(Exception e){
				e.printStackTrace();
			}
		   //String DocOwner=jdbcTemplate.queryForObject(sql7, String.class,new Object[]{instanceGuid});
		   String Othercontents=zzxxlt.size()+"";
		   
		   String IsExistattachment="N";
		   String remark="";
		   String By1=bcode;
		   String By2="";
		   String By3="";
		   String QueryStatus="";
		   String username="lhspxt";
		   String password="123456";
				   Object [] paras = {
						   id,//重复抛错
						   docinfoid,//不应该重复
						   docid, 
						   businessid, //不应该重复
						   admindivision, 
						   OrganCode, //证照主体代码 重复抛错
						   docStatus, 
						   DocName, 
						   DocNO, 
						   validityperiod, 
						   producedate, 
						   produceorgan, 
						   DocOwner, 
						   content, 
                        //new BigDecimal("1"), 
						   Othercontents,
						   IsExistattachment,
						   remark, 
						   By1, 
						   By2, 
						   By3, 
						   QueryStatus, 
						   username, 
						   password	
				   };	
				   
				   //是否提交到太极，暂时默认不提交
				   int issendtotj= 0;

				  /* String sql= "insert into t_bdex_docinfo (ID,DOCINFOID,DOCID,BUSINESSID,ADMINDIVISION,ORGANCODE,DOCSTATUS,DOCNAME,DOCNO,VALIDITYPERIOD," +
				   		" PRODUCEDATE,PRODUCEORGAN,DOCOWNER,CONTENT,OTHERCONTENTS,ISEXISTATTACHMENT,REMARK,BY1,BY2,BY3,QUERYSTATUS,STATE,GUID,DOCTYPEGUID,INSTANCEGUID,ZTXM,ZCADDR,JYADDR,ZZLX,ISSENTTOTJ) " +
				   		" values ('"+id+"','"+docinfoid+"','"+docid+"','"+businessid+"','"+admindivision+"','"+OrganCode+"','"+docStatus+"','"+DocName+"','"+DocNO+"','"+validityperiod+"'," +
				   				"'"+producedate+"','"+produceorgan+"','"+DocOwner+"','"+content+"','"+Othercontents+"','"+IsExistattachment+"','"+remark+"','"+By1+"','"+By2+"','"+By3+"'," +
				   						"'"+QueryStatus+"','0','"+new GUID().toString()+"','"+doctypeguid+"','"+instanceGuid+"','"+ztxm+"','"+zcaddr+"','"+jyaddr+"','"+zzlx+"','"+issendtotj+"') ";
                  */	
				   String upsql1 = "insert into t_bdex_docinfo (ID,DOCINFOID,DOCID,BUSINESSID,ADMINDIVISION,ORGANCODE,DOCSTATUS,DOCNAME,DOCNO,VALIDITYPERIOD," 
                              +	 " PRODUCEDATE,PRODUCEORGAN,DOCOWNER,CONTENT,OTHERCONTENTS,ISEXISTATTACHMENT,REMARK,BY1,BY2,BY3,QUERYSTATUS,STATE,GUID,DOCTYPEGUID,INSTANCEGUID,ZTXM,ZCADDR,JYADDR,ZZLX,ISSENTTOTJ) " 
						      + " values (?,?,?,?,?,?,?,?,?,?," 
                              +  		"?,?,?,?,?,?,?,?,?,?,"
						      + 		"?,'0',?,?,?,?,?,?,?,?) ";
						   
				   jdbcTemplate.update(upsql1, new Object[]{id,docinfoid,docid,businessid,admindivision,OrganCode,docStatus,DocName,DocNO,validityperiod,producedate,produceorgan,DocOwner,content,Othercontents,IsExistattachment,remark,By1,By2,By3,QueryStatus,new GUID().toString(),doctypeguid,instanceGuid,ztxm,zcaddr,jyaddr,zzlx,issendtotj});	

				   for(int i=0;i<zzxxlt.size();i++){	
					   String upsql2=" insert into t_bdex_docinfoother(ID,DOCINFOID,NO,OTHERCONTENT,ADMINDIVISION,ZJJGDM,GUID) values"
					   + " ( ? , ? , ? , ? , ? , ? , ?)";
					   //String rmsql="insert into t_bdex_docinfoother(ID,DOCINFOID,NO,OTHERCONTENT,ADMINDIVISION,ZJJGDM,GUID) values" +
					   //		" ('"+id+"','"+docinfoid+"','"+(i+1)+"','"+zzxxlt.get(i)+"','"+LHCODE+"','"+bcode+"','"+new GUID().toString()+"')";
					   jdbcTemplate.update(upsql2, new Object[]{id,docinfoid,(i+1),zzxxlt.get(i),LHCODE,bcode,new GUID().toString()});
			   }					  		   
	}
	@Override
	public void savedocdateNew(HttpServletRequest req) {
		//从办结单查询公用信息
		String sql="select * from EFORM_SB_LCBD_BJD where PROCESSINSTANCEID=?";
		
		String instanceGuid = req.getParameter("instanceGuid");
		OfficeSpiDeclareinfo  officeSpiDeclareinfo=officeSpiDeclareinfoService.findByGuid(instanceGuid);
		String approveitemname=req.getParameter("approveitemname");
		String processInsntanceId=officeSpiDeclareinfo.getProcessInstanceId();
		Map<String,Object> gyzd=jdbcTemplate.queryForMap(sql, processInsntanceId);
		
		//保存
		try {
			if (approveitemname.equals("城市排水许可")) {
				CspsxuBean bean=new CspsxuBean();
				bean.setuSblsh(officeSpiDeclareinfo.getSblsh());
				//避免重复提交
				if (!cspsxuRepository.exists(Example.of(bean))) {
					cspsxuRepository.save(parseToCspsxuBean(gyzd,req));
				}
			}else if (approveitemname.equals("取水许可")) {
				QsxkBean bean = new QsxkBean();
				bean.setuSblsh(officeSpiDeclareinfo.getSblsh());
				if (qsxkRepository.exists(Example.of(bean))) {
					qsxkRepository.save(parseToQsxkBean(gyzd,req));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private QsxkBean parseToQsxkBean(Map<String,Object> gyzd, HttpServletRequest req){
		QsxkBean bean = new QsxkBean();
		//共有字段
		bean.setuId(GUID.getGUID());
		bean.setuMlbh((String)gyzd.get("mlbh"));
		bean.setuLxbh((String)gyzd.get("lxbh"));
		bean.setuSblsh((String)gyzd.get("sblsh"));
		bean.setuSxid((String)gyzd.get("sxid"));
		bean.setuSxmc(req.getParameter("approveitemname"));
		bean.setuZzbh((String)gyzd.get("certifyNumber"));
		bean.setuZtlb((String)gyzd.get("ztlb"));
		bean.setuZtmc((String)gyzd.get("ztmc"));
		bean.setuZtdm((String)gyzd.get("ztdm"));
		bean.setuFrxm((String)gyzd.get("frxm"));
		bean.setuFrsfz((String)gyzd.get("frsfz"));
		bean.setuFzrq((Date)gyzd.get("sendDate"));
		bean.setuSxrq((Date)gyzd.get("sxrq"));
		bean.setuJzrq((Date)gyzd.get("jzrq"));
		bean.setuJgdm((String)gyzd.get("jgdm"));
		bean.setuFzjg((String)gyzd.get("fzjg"));
		bean.setuXzqh((String)gyzd.get("xzqh"));
		bean.setuCa((String)gyzd.get("ca"));
		//bean.setuZzzm((byte[]) gyzd.get("zzzm"));
		//bean.setuBy1((String)gyzd.get(""));
		//bean.setuBy2((String)gyzd.get(""));
		bean.setuZzzt("zzzt");
		
		//私有字段
		bean.setNid(Integer.parseInt(req.getParameter("nid")));
		bean.setZzbh(req.getParameter("zzbh"));
		bean.setQsqrmc(req.getParameter("qsqrmc"));
		bean.setFddbr(req.getParameter("fddbr"));
		bean.setQsdd(req.getParameter("qsdd"));
		bean.setQsfs(req.getParameter("qsfs"));
		bean.setQsl(req.getParameter("qsl"));
		bean.setQsyt(req.getParameter("qsyt"));
		bean.setSylx(req.getParameter("sylx"));
		bean.setTsdd(req.getParameter("tsdd"));
		bean.setTsfs(req.getParameter("tsfs"));
		bean.setTsl(req.getParameter("tsl"));
		bean.setTssz(req.getParameter("tssz"));
		bean.setZzyxq(req.getParameter("zzyxq"));
		try {
			bean.setPzrq(sdf.parse(req.getParameter("pzrq")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bean;
	}
	private CspsxuBean parseToCspsxuBean(Map<String,Object> gyzd, HttpServletRequest req){
		CspsxuBean bean = new CspsxuBean();
		
		//共有字段
		bean.setuId(GUID.getGUID());
		bean.setuMlbh((String)gyzd.get("mlbh"));
		bean.setuLxbh((String)gyzd.get("lxbh"));
		bean.setuSblsh((String)gyzd.get("sblsh"));
		bean.setuSxid((String)gyzd.get("sxid"));
		bean.setuSxmc(req.getParameter("approveitemname"));
		bean.setuZzbh((String)gyzd.get("certifyNumber"));
		bean.setuZtlb((String)gyzd.get("ztlb"));
		bean.setuZtmc((String)gyzd.get("ztmc"));
		bean.setuZtdm((String)gyzd.get("ztdm"));
		bean.setuFrxm((String)gyzd.get("frxm"));
		bean.setuFrsfz((String)gyzd.get("frsfz"));
		bean.setuFzrq((Date)gyzd.get("sendDate"));
		bean.setuSxrq((Date)gyzd.get("sxrq"));
		bean.setuJzrq((Date)gyzd.get("jzrq"));
		bean.setuJgdm((String)gyzd.get("jgdm"));
		bean.setuFzjg((String)gyzd.get("fzjg"));
		bean.setuXzqh(gyzd.get("xzqh").toString());
		bean.setuCa((String)gyzd.get("ca"));
		bean.setuZzzm((byte[]) gyzd.get("zzzm"));
		//bean.setuBy1((String)gyzd.get(""));
		//bean.setuBy2((String)gyzd.get(""));
		bean.setuZzzt((String)gyzd.get("zzzt"));
		//私有字段
		bean.setPsxknr(null);
		bean.setPsyhmc(req.getParameter("psyhmc"));
		bean.setPsyhgldw(req.getParameter("psyhgldw"));
		bean.setXxpsdz(req.getParameter("xxpsdz"));
		bean.setPsyhlx(req.getParameter("psyhlx"));
		//bean.setBgdj(req.getParameter("bgdj"));
		String aa=req.getParameter("nid");
		bean.setNid(Integer.parseInt(req.getParameter("nid")));
		bean.setPsxkdw(req.getParameter("psxkdw"));
		Date fzrq=null;
		try {
			fzrq=sdf.parse(req.getParameter("fzrq"));
		} catch (Exception e) {
			// TODO: handle exception
			fzrq=new Date();
		}
		bean.setFzrq(fzrq);
		bean.setZzbh(req.getParameter("zzbh"));
		bean.setPszl(req.getParameter("pszl"));
		bean.setPsksl(req.getParameter("psksl"));
		bean.setJrlda(req.getParameter("jrld1"));
		bean.setQslda(req.getParameter("qsld1"));
		bean.setJrgja(req.getParameter("jrgj1"));
		bean.setSzgja(req.getParameter("szgj1"));
		
		bean.setJrldb(req.getParameter("jrld2"));
		bean.setQsldb(req.getParameter("qsld2"));
		bean.setJrgjb(req.getParameter("jrgj2"));
		bean.setSzgjb(req.getParameter("szgj2"));
		
		bean.setJrldc(req.getParameter("jrld3"));
		bean.setQsldc(req.getParameter("qsld3"));
		bean.setJrgjc(req.getParameter("jrgj3"));
		bean.setSzgjc(req.getParameter("szgj3"));
		
		bean.setJrldd(req.getParameter("jrld4"));
		bean.setQsldd(req.getParameter("qsld4"));
		bean.setJrgjd(req.getParameter("jrgj4"));
		bean.setSzgjd(req.getParameter("szgj4"));
		
		bean.setJrlde(req.getParameter("jrld5"));
		bean.setQslde(req.getParameter("qsld5"));
		bean.setJrgje(req.getParameter("jrgj5"));
		bean.setSzgje(req.getParameter("szgj5"));
		
		bean.setJrldf(req.getParameter("jrld6"));
		bean.setQsldf(req.getParameter("qsld6"));
		bean.setJrgjf(req.getParameter("jrgj6"));
		bean.setSzgjf(req.getParameter("szgj6"));
		
		bean.setJrldg(req.getParameter("jrld7"));
		bean.setQsldg(req.getParameter("qsld7"));
		bean.setJrgjg(req.getParameter("jrgj7"));
		bean.setSzgjg(req.getParameter("szgj7"));
		
		bean.setJrldh(req.getParameter("jrld8"));
		bean.setQsldh(req.getParameter("qsld8"));
		bean.setJrgjh(req.getParameter("jrgj8"));
		bean.setSzgjh(req.getParameter("szgj8"));
		
		bean.setBgjla(req.getParameter("bgjl1"));
		bean.setBgjlb(req.getParameter("bgjl2"));
		bean.setBgjlc(req.getParameter("bgjl3"));
		bean.setBgjld(req.getParameter("bgjl4"));
		bean.setBgjle(req.getParameter("bgjl5"));
		bean.setBgjlf(req.getParameter("bgjl6"));
		bean.setBgjlg(req.getParameter("bgjl7"));
		bean.setBgjlh(req.getParameter("bgjl8"));
		
		return bean;
	}

	/**
	 * 保存当前事项基本信息项
	 * 
	 * @return
	 */
	@Override
	public void savedocdate(String instanceGuid, String infocode, String infocon) {
				
		//避免出现重复数据，先执行查询有重复的执行UPDATE，没有就直接INSERT
		int count = 0;
		List<String> params = new ArrayList<String>();
		if(!StringUtils.isBlank(instanceGuid)){
			params.add(instanceGuid);
		 }
		if(!StringUtils.isBlank(infocode)){
				params.add(infocode);
			 }
         try{
        	 count = jdbcTemplate.queryForObject("select count(*) from T_bdex_docbasicInfo t where t.INSTANCEGUID =? and t.INFOCODE = ?", Integer.class, params.toArray());
			}catch(Exception e){
				e.printStackTrace();
			}
        if(count>0){
			List<String> list = new ArrayList<String>();
			String sql = "update T_bdex_docbasicInfo t set t.INFOCON = ? where INSTANCEGUID=? and t.INFOCODE = ?";
			list.add(infocon);
			list.add(instanceGuid);
			list.add(infocode);
			jdbcTemplate.update(sql, list.toArray());
			
		}else{
			List<String> list = new ArrayList<String>();
			String sql = "insert into  T_bdex_docbasicInfo (GUID,INSTANCEGUID,INFOCODE,INFOCON ) values( ?,?,?,?)";
			String guid = new GUID().toString(); 
			
			list.add(guid);
			list.add(instanceGuid);
			list.add(infocode);
			list.add(infocon);
			jdbcTemplate.update(sql, list.toArray());	
			
		}
	}


	
	
	
}
