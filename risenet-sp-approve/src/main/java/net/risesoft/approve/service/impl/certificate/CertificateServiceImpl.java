package net.risesoft.approve.service.impl.certificate;
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
import net.risesoft.approve.entity.certificate.CertificateBean;
import net.risesoft.approve.entity.jpa.OfficeSpiDeclareinfo;
import net.risesoft.approve.entity.jpa.RisenetEmployeeUnits;
import net.risesoft.approve.entity.jpa.SpmApproveitem;
import net.risesoft.approve.repository.jpa.OfficeSpiDeclareinfoRepository;
import net.risesoft.approve.repository.jpa.SpmApproveItemRepository;
import net.risesoft.approve.repository.jpa.SpmBureauRepository;
import net.risesoft.approve.service.OfficeSpiDeclareinfoService;
import net.risesoft.approve.service.WindowApproveService;
import net.risesoft.approve.service.certificate.CertificateService;
import net.risesoft.approve.service.senate.SenateService;
import net.risesoft.commons.util.GUID;
import net.risesoft.model.Person;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.ConmonUtil;
import net.risesoft.util.SendSms;
import net.risesoft.util.ServiceUtil;
import net.risesoft.utilx.StringX;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 证照领取实现类
 */
@Service(value="certificateService")
public class CertificateServiceImpl implements CertificateService{
	
	@Resource(name = "routerDataSource")
	private DataSource routerDataSource;
	
	private JdbcTemplate jdbctemplate;
	
	@PostConstruct
	private void afterIoc() {
		jdbctemplate = new JdbcTemplate(this.routerDataSource);
	} 
	@Autowired
	private SpmApproveItemRepository spmApproveItemRepository;
	
	@Resource(name = "officeSpiDeclareinfoRepository")
	private OfficeSpiDeclareinfoRepository officeSpiDeclareinfoRepository;
	
	@Autowired
	private SenateService senateService;
	
	@Autowired
	private OfficeSpiDeclareinfoService officeSpiDeclareinfoService;
	
	@Override
	public Pager findAllList(HttpServletRequest request,Pager pager) {
		int pageNum = pager.getPageNo();
		int pageSize= pager.getPageSize();
		String approveItemName = request.getParameter("approveitemname");
		String declaresn = request.getParameter("declaresn");
		String isreceive = request.getParameter("isreceive");
		String getpersoncard = request.getParameter("getpersoncard");
		String start_time = request.getParameter("start_time");
		String end_time = request.getParameter("end_time");
		Person person  =  ThreadLocalHolder.getPerson();
		//查询到当前用户在rc7中的id,
		String userID = person.getID();
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		try{
			List<Map<String, Object>> listmap=new ArrayList<Map<String, Object>>();
			//RisenetEmployeeUnits 为当前用户的信息（包括人员id,部门id,科室id）,通过rc7的id查询到该用户在新工程中的个人信息
			//RisenetEmployeeUnits risenetInfo = ServiceUtil.riseInfo(jdbctemplate,userID);
			//查询证照领取列表
			String sql="select distinct o.declaresn,o.approveitemname,ISRECEIVE,o.declaredatetime declaredatetime1,to_char(o.declaredatetime,'yyyy-mm-dd') declaredatetime,BANJIEUNIT,to_char(t.enddate,'yyyy-mm-dd') enddate,to_char(RECEIVEDATE,'yyyy-mm-dd') RECEIVEDATE,t.workflowinstance_guid,o.zhengjiandaima "
					+ "from office_spi_banjiejilu  t,office_spi_declareinfo o,xzql_xzsp_windowofitem sw,xzql_xzsp_window w "
					+ "where o.workflowinstance_guid = t.workflowinstance_guid "
					+ "and o.approveitemguid = sw.itemid and sw.windowguid = w.guid "
					+ "and instr(w.windowusers,?)>0 "
					+ "and (trim(t.docway) is not null or trim(t.certifyway) is not null) ";
			List<Object> params=new ArrayList<Object>();
			params.add(userID);
			if(!StringX.isBlank(approveItemName)){	//事项名称			
				sql += " and o.approveItemName like ?";
				params.add("%"+approveItemName+"%");			
			}
			if(!StringX.isBlank(declaresn)){	//事项名称			
				sql += " and o.declaresn = ?";
				params.add(declaresn);			
			}
			if(!StringX.isBlank(isreceive)){	//事项名称			
				sql += " and t.isreceive = ?";
				params.add(isreceive);			
			}
			if(!StringX.isBlank(getpersoncard)){	//事项名称			
				sql += " and t.getpersoncard = ?";
				params.add(getpersoncard);			
			}
			if(!StringX.isBlank(start_time)){	//事项名称			
				sql += " and o.declaredatetime >=to_date(?,'yyyy-MM-dd') ";
				params.add(start_time);
			}
			if(!StringX.isBlank(end_time)){	//事项名称			
				sql += " and o.declaredatetime =to_date(?,'yyyy-MM-dd') ";
				params.add(end_time);		
			}
			
			sql +="order by to_char(t.enddate, 'yyyy-mm-dd') desc";
						
			listmap =jdbctemplate.queryForList(sql,params.toArray());	
			pager.setTotalRows(listmap.size());
			list.addAll(listmap);
			pager.setPageList(jdbctemplate.queryForList(pager.setPageSql(sql.toString(),
					pageNum, pageSize),params.toArray()));
		}catch(Exception e){
			e.printStackTrace();
		}
		return pager;
	}
	
	
	//获取证照领取详细信息
	@Override
	public Map<String, Object> getDetailByWorkflowinstance_guid(String workflowinstance_guid) {
		CertificateBean  cb = new CertificateBean();
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		Map<String, Object> beanMap = new HashMap<String, Object>();
		Map<String, Object> yjMap = new HashMap<String, Object>();
		try{
			String sql = "select distinct t.workflowinstance_guid,t.docway,t.certifyway,t.doctitle,t.docnumber,to_char(t.senddate,'yyyy-mm-dd') senddate,t.certifyname,t.certifynumber,"
					+ "to_char(t.enddate,'yyyy-mm-dd') enddate,t.banjieunit,t.isreceive,to_char(t.receivedate,'yyyy-mm-dd') receivedate,t.statusperson,t.getpersoncard,t.getpersonphone,t.processinstanceid,d.declarerperson,"
					+ "d.declarermobile,d.isyj,d.yjrname,d.yjwp,d.kddh,d.yjrtel,d.yjrdz," +
    				"d.approveitemname,to_char(d.declaredatetime,'yyyy-mm-dd') declaredatetime,d.zhengjiandaima,d.declaresn" +
    				" from office_spi_banjiejilu t," +
    				"office_spi_declareinfo d where d.workflowinstance_guid=t.workflowinstance_guid and t.workflowinstance_guid=?";
			List<Object> param=new ArrayList<Object>();
			param.add(workflowinstance_guid);
			list=jdbctemplate.queryForList(sql, param.toArray());
			beanMap=list.get(0);
			//查询该申报事项的邮政速递信息
	  		sql="select distinct t.linkman YJRNAME, t.linkphone YJRTEL, t.linkmanid YJRSFZ, t.linkaddress YJRDZ from sendmail t where t.sb_guid=? and t.issend='1'";
	  		list=jdbctemplate.queryForList(sql, param.toArray());
	  		if(list.size()>0){
	  			yjMap=list.get(0);
	  			beanMap.putAll(yjMap);
	  		}
		}catch(Exception e){
			e.printStackTrace();
		}		
		return beanMap;
	}
	
	@Override
	public Map<String, Object> declareInfo(String workflowinstance_guid) {
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		Map<String, Object> beanMap = new HashMap<String, Object>();
		String sql = "select t.declarerlxr,t.declarermobile,t.declarerlxridtype,t.declarerlxrid,t.posttype from office_spi_declareinfo t where t.workflowinstance_guid=?";
		try {
			List<Object> param=new ArrayList<Object>();
			param.add(workflowinstance_guid);
			list=jdbctemplate.queryForList(sql, param.toArray());
			beanMap=list.get(0);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return beanMap;
	}
	
	//证照领取
	@Override
	public boolean doEdit(HttpServletRequest request){
    	String LQRZJLX = request.getParameter("idtype");
    	String LQFS = request.getParameter("gettype");
    	Map row = new HashMap();
    	List<Object> params=new ArrayList<Object>();
    	String time =""; 
    	boolean result=false;
		try {
			//OfficeSpiDeclareinfo officeSpiDeclareinfo = officeSpiDeclareinfoService.findByProcessInstanceId(processInstanceId);
			time = ConmonUtil.getSysDateFromDB(routerDataSource); 
			List<Object> param=new ArrayList<Object>();
			String upSql = "update OFFICE_SPI_BANJIEJILU "+
						" set ISRECEIVE = '1', "+
						" RECEIVEDATE = sysdate,"+
						" STATUSPERSON = ?, "+
						" GETPERSONCARD = ?, "+
						" GETPERSONPHONE = ? "+
						" where WORKFLOWINSTANCE_GUID = ?";
			
			param.add(request.getParameter("statusperson"));
			param.add(request.getParameter("getpersoncard"));
			param.add(request.getParameter("getpersonphone"));
			param.add(request.getParameter("workflowinstance_guid"));
			System.out.println("WORKFLOWINSTANCE_GUID=="+request.getParameter("workflowinstance_guid"));
			jdbctemplate.update(upSql, param.toArray());
			
			upSql="update office_spi_declareinfo t set t.isyj=?, t.yjrname=?, t.yjrtel=?,t.yjrdz=?,t.kddh=?,t.yjwp=? where t.workflowinstance_guid=?";
			param.clear();
			param.add(request.getParameter("isyj"));
			param.add(request.getParameter("yjrname"));
			param.add(request.getParameter("yjrtel"));
			param.add(request.getParameter("yjrdz"));
			param.add(request.getParameter("kddh"));
			param.add(request.getParameter("yjwp"));
			param.add(request.getParameter("workflowinstance_guid"));
			jdbctemplate.update(upSql, param.toArray());
			
			params.clear();
			params.add(request.getParameter("workflowinstance_guid"));
			String dn = this.getOneString("select distinct t.declaresn declaresn from office_spi_declareinfo t where t.workflowinstance_guid=?",params.toArray());
			String sblsh = this.getOneString("select distinct t.SBLSH SBLSH from office_spi_declareinfo t where t.workflowinstance_guid=?",params.toArray());
			params.clear();
			params.add(dn);
			Map data = null;
			try {
				data = jdbctemplate.queryForMap("select YWLSH,SPSXBH,SPSXZXBH,DEPARTID from t_shouli where yxtywlsh=?", params.toArray());
			} catch (Exception e) {
				e.printStackTrace();
			}
			//存放到省厅的中间库表
			if(data != null){
				String sql = "insert into T_LINGQUDENGJI (YWLSH,SJBBH,SPSXBH,SPSXZXBH,YXTYWLSH,LQRXM,LQRZJLX,LQRSFZJHM,LQFS,LQSJ,XZQHDM,"
						+ "DEPARTID,DATASOURCE,SERIALNUM) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				params.clear();
				params.add(data.get("YWLSH"));
				params.add("1");
				params.add(data.get("SPSXBH"));
				params.add(data.get("SPSXZXBH"));
				params.add(dn);
				params.add(data.get("statusperson"));
				params.add(LQRZJLX);
				params.add(request.getParameter("getpersoncard"));
				params.add(LQFS);
				params.add(time);
				params.add("440303");
				params.add(data.get("DEPARTID"));
				params.add("XZSP");
				params.add(sblsh);
				jdbctemplate.update(sql, params.toArray());
			}
			
			//邮政速递短信
			String workflowinsguid=request.getParameter("workflowinstance_guid");
			params.clear();
			params.add(workflowinsguid);
			String approveitemname=this.getOneString("select distinct t.approveitemname from office_spi_declareinfo t where t.workflowinstance_guid=? ", params.toArray());
			String [] str = this.getOneString("select distinct t.declaresn||','||t.bureauname from office_spi_declareinfo t where t.workflowinstance_guid=? ", params.toArray()).split(",");
			String reno=this.getreno();
			params.clear();
			params.add(workflowinsguid);
			String[] isEms = this.getOneString("select distinct t.isyj||','||t.kddh from office_spi_declareinfo t where t.workflowinstance_guid=? ",params.toArray()).split(",");
			if(isEms.length>1&&"是".equals(isEms[0]) && isEms[1]!=null) {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd(HH:mm)");//设置日期格式
				String yjrtel = this.getOneString("select t.yjrtel from office_spi_declareinfo t where t.workflowinstance_guid=? ",params.toArray());
				SendSms.jksendsms(routerDataSource,yjrtel,"MYDDC",reno,"罗湖行政审批与服务平台提醒：您在罗湖区"+ str[1] + "办理编号"+str[0] +"的"+approveitemname+"业务已出证，并于"+df.format(new Date())+"经深圳邮政快递给您，快递单号："+isEms[1]+"。投递员上门时需核对本短信所示的业务编号或手机号码是否与快递详情单所示一致，请您妥善保存该信息。");
			}
			result=true;
		    } catch (Exception e) {
			e.printStackTrace();
			result=false;
		}
		return result;
    }
    
	@Override
	public String getOneString(String sql,Object[] param){
		String result="";
		try {
			result = jdbctemplate.queryForObject(sql.toString(),param,String.class);
		} catch (DataAccessException e) {
			//e.printStackTrace();
			System.out.println("获取数据失败：===记录不存在===");
		}
		return result;
	}
	
	/**
	 * 跟踪评价回复关系生成类。
	 * 循环生成0000-9999之间数字。用完后删掉0000重新生成0000，循环使用。
	 *
	 */
	@Override
	public String getreno(){
		String reno="";
		String treno=this.getOneString("select max(t.reno) reno from sms_pjrelation t where t.flag='1' ",new Object[]{});
		int numreno=0;
		if(!treno.equals("")){
			numreno=Integer.parseInt(treno);
		}else{
			return "0000";
		}
		if(numreno<9999){
			numreno=numreno+1;
		}else{
			//置所有flag为0
			String sql="update sms_pjrelation t set t.flag=0";
			jdbctemplate.update(sql);
			numreno=0;
		}
		reno=numreno+"";
		if(reno.length()==1){
			reno="000"+reno;
		}else if(reno.length()==2){
			reno="00"+reno;
		}else if(reno.length()==3){
			reno="0"+reno;
		}
		return reno;
	}
	
	@Override
	public SpmApproveitem findApproveItemByGuid(String guid) {
		return spmApproveItemRepository.findByapproveitemguid(guid);
	}
	@Override
	public void save(OfficeSpiDeclareinfo office) {
		officeSpiDeclareinfoRepository.save(office);
	}
	
}
