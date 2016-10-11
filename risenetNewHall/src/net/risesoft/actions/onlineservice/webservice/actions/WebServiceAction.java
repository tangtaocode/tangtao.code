package net.risesoft.actions.onlineservice.webservice.actions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.risesoft.actions.base.BaseActionSupport;
import net.risesoft.actions.onlineservice.webservice.beans.DecisionWebServiceBean;
import net.risesoft.actions.onlineservice.webservice.beans.WebServiceBean;
import net.risesoft.beans.base.KeyValue;
import net.risesoft.daos.base.ISimpleJdbcDao;
import net.risesoft.services.webservice.jShare.RequestService;
import net.risesoft.services.webservice.jShare.jyxt.JyxtDeal;
import net.risesoft.services.webservice.sShare.Request;
import net.risesoft.util.ValidatorUtil;

import org.apache.soap.SOAPException;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;

/**
 * WebService接口访问Action
 * @author HJL
 * @date 2013-10-10
 *
 */
@Controller
@ParentPackage("default")//继承json包，用于返回json
@InterceptorRefs( { @InterceptorRef("isLoginStack") })//权限拦截
public class WebServiceAction extends BaseActionSupport
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String qualifiedbookid;//审查合格书编号
	
	private String strGcbh;//工程编号
	
	private String zjbh;//证照编号
	
	private String category;//职务，2：建造师，3：项目总监
	private String sfzhm;//身份证号码
	private String xmbh;//项目编号
	private String isBig;//是否重大项目
	
	private String bwbh;//直接发包审批决定书文号
	private DecisionWebServiceBean decisionWebServiceBean;//材料共享（直接发包审批决定书文号）WebService访问返回值
	private List<KeyValue> dataList;//返回值
	private Map<Object,Object> dataMap;//返回值
	
	@Resource
	private RequestService service;//局WebService接口
	@Resource
	private Request rq;//市WebService接口
	
	
	public Map<Object, Object> getDataMap() {
		return dataMap;
	}
	public void setDataMap(Map<Object, Object> dataMap) {
		this.dataMap = dataMap;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSfzhm() {
		return sfzhm;
	}
	public void setSfzhm(String sfzhm) {
		this.sfzhm = sfzhm;
	}
	public String getXmbh() {
		return xmbh;
	}
	public void setXmbh(String xmbh) {
		this.xmbh = xmbh;
	}
	public String getIsBig() {
		if(isBig.equals("是")){
			return "1";
		}else{
			return "0";
		}
	}
	public void setIsBig(String isBig) {
		if(isBig.equals("1")){
			this.isBig = "是";
		}else{
			this.isBig = "否";
		}
	}
	public List<KeyValue> getDataList() {
		return dataList;
	}
	public void setDataList(List<KeyValue> dataList) {
		this.dataList = dataList;
	}
	public DecisionWebServiceBean getDecisionWebServiceBean() {
		return decisionWebServiceBean;
	}
	public void setDecisionWebServiceBean(
			DecisionWebServiceBean decisionWebServiceBean) {
		this.decisionWebServiceBean = decisionWebServiceBean;
	}
	public String getBwbh() {
		return bwbh;
	}
	public void setBwbh(String bwbh) {
		this.bwbh = bwbh;
	}
	public String getQualifiedbookid() {
		return qualifiedbookid;
	}
	public void setQualifiedbookid(String qualifiedbookid) {
		this.qualifiedbookid = qualifiedbookid;
	}
	public String getStrGcbh() {
		return strGcbh;
	}
	public void setStrGcbh(String strGcbh) {
		this.strGcbh = strGcbh;
	}
	public String getZjbh() {
		return zjbh;
	}
	public void setZjbh(String zjbh) {
		this.zjbh = zjbh;
	}
	
	
	
	/**
	 * @description 执行审查合格书webServices接口访问
	 * @date 2013-10-10
	 * @return 
	 */
	@Action(value="/onlineService/doInvestigateWebService" , results = {@Result(name = "success" , location = "/WEB-INF/page/onlineservice/appApply/alert/alert.jsp")})	
	public String doInvestigateWebService(){
//		RequestService service = new RequestServiceImpl();
//		outJson(service.investigateWebService(getQualifiedbookid()), null);
		setDataList(service.investigateWebService(getQualifiedbookid()));
		if(dataList!=null&&dataList.size()>0&&dataList.get(dataList.size()-1).getKey().equals("服务端查询出错描述")){
			outJson("{'error':'0','msg':'"+dataList.get(dataList.size()-1).getValue()+"'}",null);
			return ERROR;
		}
		dataList.remove(0);
		return SUCCESS;
	}
	
	/**
	 * @description 执行获取保函信息webServices接口访问
	 * @date 2013-10-10
	 * @return 
	 */
	@Action(value="/onlineService/doBackletterWebService" , results = {@Result(name = "success" , location = "/WEB-INF/page/onlineservice/appApply/alert/alert.jsp")})	
	public String doBackletterWebService(){
//		RequestService service = new RequestServiceImpl();
//		outJson(service.backletterWebService(getStrGcbh()), null);
		setDataList(service.backletterWebService(getStrGcbh()));
		if(dataList!=null&&dataList.size()>0&&dataList.get(dataList.size()-1).getKey().equals("服务端查询出错描述")){
			outJson("{'error':'0','msg':'"+dataList.get(dataList.size()-1).getValue()+"'}",null);
			return ERROR;
		}
		return SUCCESS;
	}
	
	
	/**
	 * @description 证照材料共享(深圳市民用建筑施工图设计文件抽查（备案）意见书)
	 * @date 2013-10-11
	 */
	@Action(value="/onlineService/doLicenseWebService" , results = {@Result(name = "success" , location = "/WEB-INF/page/onlineservice/appApply/alert/alert.jsp")})
	public String doLicenseWebService(){
//		RequestService service = new RequestServiceImpl();
//		outJson(service.licenseWebService(getZjbh()), null);
		setDataList(service.licenseWebService(getZjbh()));
		if(dataList!=null&&dataList.size()>0&&dataList.get(dataList.size()-1).getKey().equals("服务端查询出错描述")){
			outJson("{'error':'0','msg':'"+dataList.get(dataList.size()-1).getValue()+"'}",null);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * @description 证照材料共享(施工企业资质证书)
	 * @date 2013-10-11
	 */
	@Action(value="/onlineService/checkSgqyzzzs" , results = {@Result(name = "success" , location = "/WEB-INF/page/onlineservice/appApply/alert/alert.jsp")})
	public String checkSgqyzzzs(){
		setDataList(service.getSgqyzzzs(getZjbh()));
		if(dataList!=null&&dataList.size()>0&&dataList.get(dataList.size()-1).getKey().equals("服务端查询出错描述")){
			outJson("{'error':'0','msg':'"+dataList.get(dataList.size()-1).getValue()+"'}",null);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * @description 证照材料共享(监理单位资质证书)
	 * @date 2013-10-11
	 */
	@Action(value="/onlineService/checkJldwzzzs" , results = {@Result(name = "success" , location = "/WEB-INF/page/onlineservice/appApply/alert/alert.jsp")})
	public String checkJldwzzzs(){
//		RequestService service = new RequestServiceImpl();
//		outJson(service.licenseWebService(getZjbh()), null);
		setDataList(service.getJldwzzzs(getZjbh()));
		if(dataList!=null&&dataList.size()>0&&dataList.get(dataList.size()-1).getKey().equals("服务端查询出错描述")){
			outJson("{'error':'0','msg':'"+dataList.get(dataList.size()-1).getValue()+"'}",null);
			return ERROR;
		}
		return SUCCESS;
	}
	
	
	/**
	 * @description 材料共享（直接发包审批决定书文号）WebService访问
	 * @author HJL
	 * 
	 */
	@Action(value="/onlineService/doDecisionWebService" , results = { @Result(name = "success", location = "/WEB-INF/page/onlineservice/appApply/alert/alert.jsp")})
	public String doDecisionWebService(){
//		RequestService service = new RequestServiceImpl();
		//outJson(service.decisionWebService(getBwbh()), null);
		setDataList(service.decisionWebService(getBwbh()));
		if(dataList!=null&&dataList.size()>0&&dataList.get(dataList.size()-1).getKey().equals("服务端查询出错描述")){
			outJson("{'error':'0','msg':'"+dataList.get(dataList.size()-1).getValue()+"'}",null);
			return ERROR;
		}
		return SUCCESS;
	}
	

	/**
	 * @description 建设用地规划许可证
	 * @author HJL
	 * @date 2013-10-18
	 */
	@Action(value="/onlineService/GetJSYDGHXKZDataById" , results = { @Result(name = "success", location = "/WEB-INF/page/onlineservice/appApply/alert/jsydghxkz.jsp")})
	public String GetJSYDGHXKZDataById(){
//		Request rq = new Request();
//		outJson(rq.GetJSYDGHXKZDataById(getRequest().getParameter("jsydghxkzh")),null);
		Map m = rq.GetJSYDGHXKZDataById(getRequest().getParameter("jsydghxkzh"));
		if(m.get("state").equals("2")){//查无记录或出错
			outJson("{'error':'0','msg':'"+m.get("ErrorMessage").toString().replace("\n", "<br/>")+"'}",null);
			return ERROR;
		}else{//查到正确数据
			setDataMap(m);
		}
//		m.remove("state");
//		List lt = new ArrayList();
//		KeyValue kv = null;
//		Iterator it = m.keySet().iterator();   
//	    while (it.hasNext()) {   
//	    	String key = it.next().toString();   
//	    	kv = new KeyValue();
//	    	kv.setKey(key);
//	    	kv.setValue(m.get(key).toString());
//	    	lt.add(kv);
//	    }
//	    setDataList(lt);
		return SUCCESS;
	}

	/**
	 * @description 建设工程规划许可证
	 * @author HJL
	 * @date 2013-10-18
	 */
	@Action(value="/onlineService/GetJSGCGHXKZDataById" , results = { @Result(name = "success", location = "/WEB-INF/page/onlineservice/appApply/alert/jsgcghxkz.jsp")})
	public String GetJSGCGHXKZDataById(){
//		Request rq = new Request();
//		outJson(rq.GetJSGCGHXKZDataById(getRequest().getParameter("jsgcghxkzh")),null);
		Map m = rq.GetJSGCGHXKZDataById(getRequest().getParameter("jsgcghxkzh"));
		if(m.get("state").equals("2")){//查无记录或出错
			outJson("{'error':'0','msg':'"+m.get("ErrorMessage").toString().replace("\n", "<br/>")+"'}",null);
			return ERROR;
		}else{//查到正确数据
//			m.remove("state");
//			List lt = new ArrayList();
//			KeyValue kv = null;
//			Iterator it = m.keySet().iterator();   
//			while (it.hasNext()) {   
//				String key = it.next().toString();   
//				kv = new KeyValue();
//				kv.setKey(key);
//				kv.setValue(m.get(key).toString());
//				lt.add(kv);
//			}
//			setDataList(lt);
			setDataMap(m);
		}
		return SUCCESS;
	}
	
	/**
	 * @description 营业许可证
	 * @author HJL
	 * @date 2013-10-18
	 */
	@Action(value="/onlineService/GetYYXKZDataById" , results = { @Result(name = "success", location = "/WEB-INF/page/onlineservice/appApply/alert/alert.jsp")})
	public String GetYYXKZDataById(){
//		Request rq = new Request();
//		outJson(rq.GetYYXKZDataById(getRequest().getParameter("yyxkzh")),null);
		Map m = rq.GetYYXKZDataById(getRequest().getParameter("yyxkzh"));
		if(m.get("state").equals("2")){
			outJson("{'error':'0','msg':'"+m.get("ErrorMessage").toString().replace("\n", "<br/>")+"'}",null);
			return ERROR;
		}
		m.remove("state");
		List lt = new ArrayList();
		KeyValue kv = null;
		Iterator it = m.keySet().iterator();   
	    while (it.hasNext()) {   
	    	String key = it.next().toString();   
	    	kv = new KeyValue();
	    	kv.setKey(key);
	    	kv.setValue(m.get(key).toString());
	    	lt.add(kv);
	    }
	    setDataList(lt);
		return SUCCESS;
	}
	
	/**
	 * @description 营业许可证
	 * @author HJL
	 * @throws ParseException 
	 * @date 2013-10-18
	 */
	@Action(value="/onlineService/GetYYZZById" , results = { @Result(name = "success", location = "/WEB-INF/page/onlineservice/appApply/alert/alert.jsp")})
	public String GetYYZZById() throws ParseException{
//		Request rq = new Request();
//		outJson(rq.GetYYXKZDataById(getRequest().getParameter("yyxkzh")),null);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Map m = rq.GetYYXKZDataById(getRequest().getParameter("yyxkzh"));
		if(m.get("state").equals("2")){
			outJson("{'error':'0','msg':'"+m.get("ErrorMessage").toString().replace("\n", "<br/>")+"'}",null);
			return ERROR;
		}
		List lt = new ArrayList();
		KeyValue kv = null;
		kv = new KeyValue();
	    kv.setKey("组织机构代码");
	    kv.setValue(ValidatorUtil.filter(m.get("EntOrgCode").toString()));
	    lt.add(kv);
	    kv = new KeyValue();
	    kv.setKey("注册号");
	    kv.setValue(ValidatorUtil.filter(m.get("EntRegNO").toString()));
	    lt.add(kv);
	    kv = new KeyValue();
	    kv.setKey("企业名称");
	    kv.setValue(ValidatorUtil.filter(m.get("EntName").toString()));
	    lt.add(kv);
	    kv = new KeyValue();
	    kv.setKey("注册地址");
	    kv.setValue(ValidatorUtil.filter(m.get("Addr").toString()));
	    lt.add(kv);
	    kv = new KeyValue();
	    kv.setKey("经营场所");
	    kv.setValue(ValidatorUtil.filter(m.get("BizAddr").toString()));
	    lt.add(kv);
	    /*kv = new KeyValue();
	    kv.setKey("法定代表人身份编码");
	    kv.setValue(ValidatorUtil.filter(m.get("LeRepCode").toString()));
	    lt.add(kv);*/
	    kv = new KeyValue();
	    kv.setKey("法定代表人");
	    kv.setValue(ValidatorUtil.filter(m.get("LeRepName").toString()));
	    lt.add(kv);
	    kv = new KeyValue();
	    kv.setKey("经营范围");
	    kv.setValue(ValidatorUtil.filter(m.get("CBuItem").toString()));
	    lt.add(kv);
	    kv = new KeyValue();
	    kv.setKey("经营方式");
	    kv.setValue(ValidatorUtil.filter(m.get("BizMethod").toString()));
	    lt.add(kv);
	    kv = new KeyValue();
	    kv.setKey("设立日期");
	    kv.setValue(sdf.format(sdf.parse(ValidatorUtil.filter(m.get("EstDate").toString()))));
	    lt.add(kv);
	    kv = new KeyValue();
	    kv.setKey("核准日期");
	    kv.setValue(sdf.format(sdf.parse(ValidatorUtil.filter(m.get("AuthDate").toString()))));
	    lt.add(kv);
	    kv = new KeyValue();
	    kv.setKey("经营期限起");
	    kv.setValue(sdf.format(sdf.parse(ValidatorUtil.filter(m.get("OpFromDate").toString()))));
	    lt.add(kv);
	    kv = new KeyValue();
	    kv.setKey("经营期限止");
	    kv.setValue(sdf.format(sdf.parse(ValidatorUtil.filter(m.get("OpToDate").toString()))));
	    lt.add(kv);
	    kv = new KeyValue();
	    kv.setKey("执照有效期");
	    if(m.get("LicExpDate").equals("1900-01-01 00:00:00.0")){
	    	kv.setValue("");
	    }else{
	    	kv.setValue(sdf.format(sdf.parse(ValidatorUtil.filter(m.get("LicExpDate").toString()))));
	    }
	    lt.add(kv);
	    
	    /*kv = new KeyValue();
	    kv.setKey("企业类型");
	    kv.setValue(ValidatorUtil.filter(m.get("EntTypeCode").toString()));
	    lt.add(kv);
	    kv = new KeyValue();
	    kv.setKey("行业类别");
	    kv.setValue(ValidatorUtil.filter(m.get("IndClassCode").toString()));
	    lt.add(kv);*/
	    kv = new KeyValue();
	    kv.setKey("注册资本");
	    kv.setValue(ValidatorUtil.filter(m.get("RegCap").toString())+"万元");
	    lt.add(kv);
	    kv = new KeyValue();
	    kv.setKey("注销原因");
	    kv.setValue(ValidatorUtil.filter(m.get("DeregReasonCode").toString()));
	    lt.add(kv);
	    kv = new KeyValue();
	    kv.setKey("补充经济性质");
	    kv.setValue(ValidatorUtil.filter(m.get("EntTypeExt").toString()));
	    lt.add(kv);
	    kv = new KeyValue();
	    kv.setKey("分支机构");
	    kv.setValue(ValidatorUtil.filter(m.get("Branch").toString()));
	    lt.add(kv);
	    
	    setDataList(lt);
		return SUCCESS;
	}
	
	/**
	 * @description 建造师、项目总监注册证书共享
	 * @author hjl
	 * @date 2013-12-19
	 */
	@Action(value="/WebService/GetJZSZCZS", results={@Result(name="success",location="/WEB-INF/page/onlineservice/appApply/alert/alert.jsp")})
	public String GetJZSZCZS(){
//		Map<Object, Object> m = null;
		List<WebServiceBean> list = service.backfill(getRequest());
		if (list!=null&&list.size() == 0) {// 查不到数据
			outJson("{'error':'<br/><div style=\"font-size:20px;font-weight:bold;\">该身份证号无相应记录，请先进行人员信息备案！</div><br/><div style=\"font-size:16px;\">点击“<span style=\"color:red;\">上传材料</span>”按钮上传相应扫描材料</div><div style=\"font-size:16px;\">点击“<span style=\"color:red;\">重新输入</span>”按钮重新输入身份证号</div>'}",null);
			return ERROR;
		} else {//查到正确数据
			boolean haveQualification = true;//是否有任职资格(建造师)
			if(category.equals("2")){
				try {
					haveQualification = JyxtDeal.checkIsCanRZ(sfzhm, xmbh, isBig, strGcbh);//查询是否有任职资格(建造师)
				} catch (SOAPException e) {
					outJson("{'error':'查询失败，稍后再试'}",null);
					e.printStackTrace();
				}
			}
			if(haveQualification){//有资格
//				m = new HashMap<Object, Object>();
//				m.put("姓名", list.get(0).getName());
//				m.put("身份证号码", list.get(0).getIdCard());
//				m.put("证书编号", list.get(0).getCertId());
//				m.put("有效期", list.get(0).getValidDate());
//				setDataMap(m);
				
				List lt = new ArrayList();
				KeyValue kv = new KeyValue();
				kv.setKey("姓名");
				kv.setValue(list.get(0).getName());
		    	lt.add(kv);
				
				kv = new KeyValue();
				kv.setKey("身份证号码");
				kv.setValue(list.get(0).getIdCard());
		    	lt.add(kv);
		    	
		    	kv = new KeyValue();
		    	if(list.get(0).getChange_cause()==null||list.get(0).getChange_cause().replace(" ","").equals("")){
		    		kv.setKey("<span>注册专业</span>");
		    	}else{
		    		kv.setKey("注册专业");
		    	}
		    	kv.setValue(list.get(0).getChange_cause());
		    	lt.add(kv);
				
		    	kv = new KeyValue();
				kv.setKey("资格等级");
				kv.setValue(list.get(0).getAlt_qual_lv());
		    	lt.add(kv);
		    	
				kv = new KeyValue();
				kv.setKey("证书编号");
				kv.setValue(list.get(0).getCertId());
		    	lt.add(kv);
				
				kv = new KeyValue();
				kv.setKey("有效期");
				kv.setValue(list.get(0).getValidDate());
		    	lt.add(kv);
				
				kv = new KeyValue();
				kv.setKey("企业名称");
				kv.setValue(list.get(0).getCorp_name());
		    	lt.add(kv);
				
			    setDataList(lt);
			}else{//无资格
				outJson("{'error':'该人员没有任职此项目的“建造师”资格'}",null);
			}
		}
		
		return SUCCESS;
	} 
	
	
	/**
	 * @Description 项目总监、建造师信息回填
	 * @Author hjl
	 * @Date 2013-11-05
	 */
	@Action(value="/onlineService/backfill")
	public void backfill(){
		List<WebServiceBean> list = service.backfill(getRequest());
		if (list!=null&&list.size() == 0) {// 查不到数据
			outJson("{'error':'该身份证号无相应记录，请先进行人员信息备案！'}",null);
		} else {//查到正确数据
			boolean haveQualification = true;//是否有任职资格(建造师)
			if(category.equals("2")){
				try {
					haveQualification = JyxtDeal.checkIsCanRZ(sfzhm, xmbh, isBig, strGcbh);//查询是否有任职资格(建造师)
				} catch (SOAPException e) {
					outJson("{'error':'查询失败，稍后再试'}",null);
					e.printStackTrace();
				}
			}
			if(haveQualification){//有资格
				outJson(list.get(0),null);
			}else{//无资格
				outJson("{'error':'该人员没有任职此项目的“建造师”资格'}",null);
			}
		}
	}
	
	/**
	 * 组织机构代码-查询
	 * @return
	 */
	@Action(value="/onlineService/doSelectWYbuban" , results = {@Result(name = "success" , location = "/WEB-INF/page/onlineservice/appApply/selectWYbuban.jsp")})	
	public String QueryInfo() {
		String s = getRequest().getParameter("ORG_CODE");
		Map map = rq.GetYYXKZDataById(getRequest().getParameter("ORG_CODE"));
//		Map map =new HashMap();
		//System.out.println(map.get("state"));
//		map.put("state", "1");
//		map.put("EntOrgCode", "66477");//组织机构代码
//		map.put("EntRegNO", "88888");//经营执照
//		map.put("EntName", "测试企业");//企业名称
//		map.put("CBuItem", "测试经营");//经营范围
//		
//		map.put("LeRepName", "法定代表人");//法定代表人
//		map.put("DistCode", "所属行政区划");//所属行政区划
//		map.put("BizAddr", "经营场所");//经营场所
//		map.put("RegCap", "注册资本");//注册资本
//		map.put("DeregReasonCode", "注销原因");//注销原因
//		map.put("EntTypeCode", "企业类型222"); //企业类型
		
		map.put("state", map.get("state"));
		map.put("EntOrgCode", map.get("EntOrgCode"));//组织机构代码
		map.put("EntRegNO", map.get("EntRegNO"));//经营执照
		map.put("EntName", map.get("EntName"));//企业名称
		map.put("CBuItem", map.get("CBuItem"));//经营范围
		
		map.put("LeRepName", map.get("LeRepName"));//法定代表人
		map.put("DistCode", map.get("DistCode"));//所属行政区划
		map.put("BizAddr", map.get("BizAddr"));//经营场所
		map.put("RegCap", map.get("RegCap"));//注册资本
		map.put("DeregReasonCode", map.get("DeregReasonCode"));//注销原因
		map.put("EntTypeCode", map.get("EntTypeCode")); //企业类型
		
//	
		getRequest().setAttribute("YyzzMap", map);
		return "success";
	}
	
	/**
	 * 企业信息查询---用于企业信息变更
	 * @return
	 */
	
	@Resource
	private ISimpleJdbcDao<WebServiceAction> iSimpleJdbcDao;
	
	@Action(value="/onlineService/doQueryGasInfo" , results = {@Result(name = "success" , location = "/WEB-INF/page/onlineservice/appApply/queryGasInfo.jsp")})	
	public String QueryGasInfo(){
		String orgCode =getRequest().getParameter("ORG_CODE");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; 
		Map map =new HashMap();
		try {
			conn = iSimpleJdbcDao.getNativeConn();
			String sql ="select distinct a.organization_no," +
					" a.name qyname, a.adress, a.register_capital, " +
					"a.service_phone, a.contactor,a.contactor_phone," +
					" b.employee_type employeetype," +
					"b.name ryname from corp_gas_info a, corp_gas_employee b" +
					" where a.guid = b.sb_guid " +
					"and  b.employee_type in( '法定代表人',  '主要负责人', '安全负责人', '技术负责人', '企业负责人')" +
					" and a.organization_no = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, orgCode);
			rs = pstmt.executeQuery();
			boolean b=false;
			while(rs.next()){				
				map.put("organization_no", rs.getString("organization_no"));
				map.put("qyname", rs.getString("qyname"));
				map.put("adress", rs.getString("adress"));
				map.put("register_capital", rs.getString("register_capital"));
				map.put("service_phone", rs.getString("service_phone"));
				map.put("contactor", rs.getString("contactor"));
				map.put("contactor_phone", rs.getString("contactor_phone"));
				//map.put("employeetype", rs.getString("employeetype"));
				if(rs.getString("employeetype").equals("法定代表人")){
					map.put("fddbrname", rs.getString("ryname"));
				}else
				if(rs.getString("employeetype").equals("主要负责人")){
					map.put("zyfzrname", rs.getString("ryname"));
				}else
				if(rs.getString("employeetype").equals("安全负责人")){
					map.put("aqfzrname", rs.getString("ryname"));
				}else
				if(rs.getString("employeetype").equals("技术负责人")){
					map.put("jsfzrname", rs.getString("ryname"));
				}else
				if(rs.getString("employeetype").equals("企业负责人")){
					map.put("qyfzrname", rs.getString("ryname"));
				}
				if(rs.getString("organization_no")==null&&rs.getString("organization_no").equals("null")){
					map=new HashMap();

				}
				b=true;
			}
			if(b){
				map.put("state", "1");
				getRequest().setAttribute("GasInfoMap", map);
			}else{
				map.put("state", "2");
				getRequest().setAttribute("GasInfoMap", map);
			}
			pstmt.close();
			rs.close();
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return "success";
	}
//	public static void main(String[] args){
//		WebServiceAction wsa = new WebServiceAction();
//		wsa.setStrGcbh("44030420130020001");//44030420040094003 由市建设工程交易服务中心出具的履约保函收讫证明
//		wsa.doBackletterWebService();
//		
//		wsa.setQualifiedbookid("SCBGBA20130043");//具备相应资质的施工图审查单位出具的施工图设计文件审查合格报告
//		wsa.doInvestigateWebService();
//		wsa.setZjbh("A0220060200");//深圳市民用建筑施工图设计文件抽查（备案）意见书
//		wsa.doLicenseWebService();
//		setBwbh("深建住保[2013]65号");//直接发包审批决定书
//		wsa.doDecisionWebService();
//		340504610405021//建造师 
//		510103691007103//项目总监 
//	}
}
