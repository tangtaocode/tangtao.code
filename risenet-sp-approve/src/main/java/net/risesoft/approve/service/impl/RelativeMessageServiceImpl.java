//package net.risesoft.approve.service.impl;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.Resource;
//import javax.sql.DataSource;
//
//import org.apache.cxf.endpoint.Client;
//import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Service;
//
//import net.risesoft.approve.entity.base.CompanyUser;
//import net.risesoft.approve.entity.base.PersonUser;
//import net.risesoft.approve.entity.base.UserInfo;
//import net.risesoft.approve.entity.jpa.OfficeSpiDeclareinfo;
//import net.risesoft.approve.service.OfficeSpiDeclareinfoService;
//import net.risesoft.approve.service.RelativeMessageService;
//import net.risesoft.common.util.ContextUtil;
//import net.sf.json.JSONObject;
//
//@Service(value="relativeMessageService")
//public class RelativeMessageServiceImpl implements RelativeMessageService{
//
//	public static JdbcTemplate zwgcJdbcTemplate = ContextUtil.getBean("lhjhptJdbcTemplate");
//	
//	@Resource(name = "routerDataSource")
//	private DataSource routerDataSource;
//
//	private JdbcTemplate luohuJdbcTemplate;
//
//	@PostConstruct
//	private void afterIoc() {
//		luohuJdbcTemplate = new JdbcTemplate(this.routerDataSource);
//	}
//
//	
//	@Autowired
//	private OfficeSpiDeclareinfoService officeSpiDeclareinfoService;
//	
//	
//	@Override
//	public List<Map<String, Object>> getZhiWangGongCheng(String  SPinstanceId) {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		OfficeSpiDeclareinfo officeSpiDeclareinfo = officeSpiDeclareinfoService.findByGuid(SPinstanceId);
//		List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();
//		String sql1 = "select * from dwr_mdm_ppu_resident_dim_in where CARD_NO =? ORDER BY DW_UPDATE_TIME DESC";//个人
//		String sql2 = "select * from dwr_mdm_legal_person_dim_in where CARD_NO =? ORDER BY DW_UPDATE_TIME DESC";//企业
//		List<String> param = new ArrayList<String>();
//		//param.add("H1004133800");//企业
//		//param.add("110101196307012095");//个人
//		param.add(officeSpiDeclareinfo.getZhengjiandaima());
//		if("1".equals(officeSpiDeclareinfo.getDeclareType())){//个人
//			listMap =zwgcJdbcTemplate.queryForList(sql1, param.toArray());
//		}else if("2".equals(officeSpiDeclareinfo.getDeclareType())){//企业
//			listMap =zwgcJdbcTemplate.queryForList(sql2, param.toArray());
//		}else{//窗口收件一开始证件类型为空,无法确定个人还是企业
//			listMap =zwgcJdbcTemplate.queryForList(sql1, param.toArray());
//			if(listMap.size()==0){
//				listMap =zwgcJdbcTemplate.queryForList(sql2, param.toArray());
//			}
//		}
//		//数据处理
//		if(listMap.size()>0){
//			//处理出生日期格式
//			for(int i = 0;i<listMap.size();i++){
//				Date BIRTH_DATE = (Date) listMap.get(i).get("BIRTH_DATE");//出生日期
//				if(BIRTH_DATE!=null){
//					listMap.get(i).put("BIRTH_DATE", sdf.format(BIRTH_DATE));
//				}
//			}
//		}
//		return listMap;
//	}
//
//
//	@Override
//	public List<Map<String, Object>> getZhengZhaoKu(String SPinstanceId) {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		OfficeSpiDeclareinfo officeSpiDeclareinfo = officeSpiDeclareinfoService.findByGuid(SPinstanceId);
//		List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();
//		String sql = "select * from T_BDEX_DOCINFO t where t.ORGANCODE =? ORDER BY t.PRODUCEDATE DESC";//证照库信息
//		List<String> param = new ArrayList<String>();
//		//param.add("445224198709066926");//个人
//		param.add(officeSpiDeclareinfo.getZhengjiandaima());
//		listMap =luohuJdbcTemplate.queryForList(sql, param.toArray());
//		//数据处理
//		if(listMap.size()>0){
//			//处理日期格式
//			for(int i = 0;i<listMap.size();i++){
//				String PRODUCEDATE = (String) listMap.get(i).get("PRODUCEDATE");//发证日期
//				try {
//					if(PRODUCEDATE!=null){
//						listMap.get(i).put("PRODUCEDATE", sdf.format(sdf.parse(PRODUCEDATE)));
//					}
//				} catch (ParseException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//		return listMap;
//	}
//
//
//	@Override
//	public List<Map<String, Object>> getZiLiaoKu(String SPinstanceId) {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		OfficeSpiDeclareinfo officeSpiDeclareinfo = officeSpiDeclareinfoService.findByGuid(SPinstanceId);
//		List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();
//		String sql = "select * from ss_stuffdata t where t.OWNERGUID =? ORDER BY t.GXTIME DESC";//证照库信息
//		List<String> param = new ArrayList<String>();
//		//param.add("122121212");//个人
//		param.add(officeSpiDeclareinfo.getZhengjiandaima());
//		listMap =luohuJdbcTemplate.queryForList(sql, param.toArray());
//		//数据处理
//		if(listMap.size()>0){
//			//处理日期格式
//			for(int i = 0;i<listMap.size();i++){
//				Date LIMITBEGIN = (Date) listMap.get(i).get("LIMITBEGIN");//有效开始日期
//				Date LIMITEND = (Date) listMap.get(i).get("LIMITEND");//有效结束日期
//				Date CERTIFYTIME = (Date) listMap.get(i).get("CERTIFYTIME");//认证时间
//				if(LIMITBEGIN!=null){
//					listMap.get(i).put("LIMITBEGIN", sdf.format(LIMITBEGIN));
//				}
//				if(LIMITEND!=null){
//					listMap.get(i).put("LIMITEND", sdf.format(LIMITEND));
//				}
//				if(CERTIFYTIME!=null){
//					listMap.get(i).put("CERTIFYTIME", sdf.format(CERTIFYTIME));
//				}
//			}
//		}
//		return listMap;
//	}
//
//
//	@Override
//	public List<Map<String, Object>> getZhuCeXinXi(String SPinstanceId) {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		OfficeSpiDeclareinfo officeSpiDeclareinfo = officeSpiDeclareinfoService.findByGuid(SPinstanceId);
//		List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();
//		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance(); 
//		String url = "http://10.169.3.30:8088/lhhall/hallService?wsdl"; 
//		Client client = dcf.createClient(url);
//		List<Object> params = new ArrayList<Object>();
//		params.add("lhbsdt###zfzx");
//		//params.add("15820440742");
//		params.add(officeSpiDeclareinfo.getDeclarerMobile());
//		Object[] result = null;
//		try{
//			result = client.invoke("findPerson",params.toArray());
//			JSONObject obj = JSONObject.fromObject(result[0]);
//			UserInfo userinfo = (UserInfo) JSONObject.toBean(obj, UserInfo.class);
//			Map<String, Object> map = new HashMap<String, Object>();
//			if(userinfo!=null){
//				if("1".equals(userinfo.getUsertype())){
//					PersonUser personUser = userinfo.getPersonUser();
//					map.put("true_name", personUser.getTrue_name());		//真实姓名
//					map.put("sex", personUser.getSex());					//性别
//					map.put("age", personUser.getAge());					//年龄
//					map.put("native_add", personUser.getNative_add());		//现居住地址
//					map.put("nation", personUser.getNation());				//民族
//					map.put("metier", personUser.getMetier());				//职业
//					map.put("birth_date", personUser.getBirth_date()!=null?sdf.format(personUser.getBirth_date()):null);		//出生日期
//					map.put("polity", personUser.getPolity());				//政治面貌
//					map.put("ifmarry", personUser.getIfmarry());			//婚姻状况
//					map.put("idcard_type", personUser.getIdcard_type());	//证件类型
//					map.put("idcard_code", personUser.getIdcard_code());	//证件号码
//					map.put("grad_chool", personUser.getGrad_chool());		//毕业学校
//					
//					map.put("grad_date", personUser.getGrad_date()!=null?sdf.format(personUser.getGrad_date()):null);		//毕业时间
//					map.put("schoolage", personUser.getSchoolage());		//学历
//					map.put("reg_add", personUser.getReg_add());			//户口所在地
//					map.put("home_add", personUser.getHome_add());			//籍贯
//					map.put("mobile", personUser.getMobile());				//手机号码
//					map.put("postcode", personUser.getPostcode());			//邮编
//					map.put("email", personUser.getEmail());				//邮箱
//					map.put("work_company", personUser.getWork_company());	//工作单位
//					map.put("work_add", personUser.getWork_add());			//单位地址
//					map.put("work_title", personUser.getWork_title());  	//职称
//				}else{
//					CompanyUser companyUser = userinfo.getCompanyUser();
//					map.put("ename", companyUser.getAccountname());			//单位名称
//					map.put("address", companyUser.getAddress());			//注册地址
//					map.put("kind", companyUser.getAccountname());			//企业性质/组织性质
//					map.put("industry", companyUser.getIndustry());			//传真
//					map.put("orgcode", companyUser.getOrgcode());			//组织机构代码
//					map.put("lawperson", companyUser.getLawperson());		//法定代表人 
//					map.put("regcode", companyUser.getRegcode());			//登记证号/注册号
//					map.put("regdate", companyUser.getRegdate()!=null?sdf.format(companyUser.getRegdate()):null);			//成立时间
//					map.put("regmoney", companyUser.getRegmoney());			//注册资金
//					map.put("limit", companyUser.getLimit());				//经营范围
//					map.put("contactphone", companyUser.getContactphone());	//联系人电话
//					map.put("contactmobile", companyUser.getContactmobile());//联系人手机号码
//					
//					map.put("email", companyUser.getEmail());				//联系人邮箱
//					map.put("postcode", companyUser.getPostcode());			//邮编
//					map.put("register_date", companyUser.getRegister_date()!=null?sdf.format(companyUser.getRegister_date()):null);//注册时间
//					map.put("totalpeop", companyUser.getTotalpeop());		//公司人数
//					map.put("truename", companyUser.getTruename());			//联系人姓名
//					map.put("openadd", companyUser.getOpenadd());			//营业地址
//					map.put("personcardid", companyUser.getPersoncardid());	//联系人身份证
//					map.put("companyabout", companyUser.getCompanyabout());	//公司简介
//					
//				//	map.put("work_add", personUser.getWork_add());			//单位地址
//					//map.put("work_title", personUser.getWork_title());  	//职称
//				}
//				map.put("userType", userinfo.getUsertype());	//类型
//			}
//			listMap.add(map);
//		}catch(Exception ex){
//			ex.printStackTrace();
//		}
//		return listMap;
//	}
//
//}
