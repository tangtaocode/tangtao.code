package net.risesoft.actions.supervise;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.risesoft.actions.base.BaseActionSupport;
import net.risesoft.beans.supervise.StatisticsBean;
import net.risesoft.daos.base.ISimpleJdbcDao;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

/**
 * 
 * @ClassName: SuperviseAction
 * @Description: 效能监察
 * @author Comsys-zhangkun
 * @date May 22, 2013 9:32:34 AM
 * 
 */

@Controller
public class SuperviseAction extends BaseActionSupport {

	/**
	 * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	 */

	private static final long serialVersionUID = 3537346754314706158L;
	@Resource
	private ISimpleJdbcDao<StatisticsBean> simpleJdbcDao;
	
	private List<StatisticsBean> statList = new ArrayList<StatisticsBean>();//统计公共javabean集合
	private String type;
	
	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public List<StatisticsBean> getStatList() {
		return statList;
	}


	public void setStatList(List<StatisticsBean> statList) {
		this.statList = statList;
	}


	/**
	 * 
	 * 
	 * @Title: superviseDefault
	 * @Description: 效能监察首页
	 * @param method： wsdj网上等，ywpj业务评价，ywtj业务统计，null事项进驻统计
	 * @throws Exception
	 *             设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@Action(value = "/supervise/superviseDefault", results = { @Result(name = "success", location = "/WEB-INF/page/supervise/index.jsp"),
			@Result(name = "wsdj", location = "/WEB-INF/page/supervise/wsdj.jsp"),
			@Result(name = "ywpj", location = "/WEB-INF/page/supervise/ywpj.jsp"),
			@Result(name = "ywtj", location = "/WEB-INF/page/supervise/ywtj.jsp")})
	public String superviseDefault() throws Exception {
		  String sql = "";
		  try {
			  if(StringUtils.isNotBlank(getType())&&"wsdj".equals(getType())){
				  sql = "select b.bureaucnfullname column1, "+
				  		" to_char(count(distinct p.itemid)) int1, "+
				  		" to_char(count(distinct p1.itemid)) int2, "+
				  		" to_char(count(distinct p.itemid)) int3, "+
				  		" to_char(count(distinct case when p.Approveplace='0' or p.Applicationformurl is not null or p.Applicationform='1' then p.itemid else null end)) int4, "+
				  		" to_char(count(distinct case when p.Approveplace='0' or p.Abutment='1' then p.itemid else null end)) int5 "+
				  		" from (select distinct a.itemid, a.adminorgid,e.Approveplace,e.Applicationformurl,e.Applicationform,e.Abutment "+
				  		" from xzql_xzsp_base a, xzql_xzsp_extend e "+
				  		" where e.isprovince = '1' "+
				  		" and e.approveitemstatus = '运行' "+
				  		" and a.itemid = e.itemid(+)) p, "+
				  		" (select distinct a.itemid, a.adminorgid "+
				  		" from xzql_xzsp_base a, xzql_xzsp_extend e "+
				  		" where e.isprovince = '1' "+
				  		" and e.approveitemstatus = '运行' "+
				  		" and a.itemid = e.itemid(+) "+
				  		" and exists (select 1 "+
				  		" from xzql_xzsp_attachment t "+
				  		" where t.itemid = a.itemid)) p1,spm_bureau b "+
				  		" where p.adminorgid = b.bureauguid "+
				  		" and p1.adminorgid(+) = b.bureauguid "+
				  		" and b.isstreet = '0' "+
				  		" group by b.bureauguid, b.bureaucnfullname, b.bureautabindex "+
				  		" order by b.bureautabindex";
				  setStatList(simpleJdbcDao.queryForRow(sql, StatisticsBean.class));
				  return "wsdj";
			  }else if(StringUtils.isNotBlank(getType())&&"ywpj".equals(getType())){
				  sql = "select b.bureaucnfullname column1, "+
				  		" to_char(count(distinct p.itemid)) int1, "+
				  		" to_char(count(distinct case when p.Approveplace='0' or p.Applicationformurl is not null " +
				  		"or p.Applicationform='1' then p.itemid else null end)) int2, "+
				  		" to_char(count(distinct case when p.Bureautype in ('003','004') then p.itemid else null end )) int3, "+
				  		" to_char(count(distinct case when p.Bureautype in ('003','004') and (p.Approveplace='0' " +
				  		"or p.Applicationformurl is not null or p.Applicationform='1') then p.itemid else null end )) int4, "+
				  		" ceil(count(distinct case when p.Approveplace='0' or p.Applicationformurl is not null " +
				  		"or p.Applicationform='1' then p.itemid else null end)/count(distinct p.itemid)*100)||'%' column2, "+
				  		" to_char(ceil(count(distinct case when p.Approveplace='0' or p.Applicationformurl is not null " +
				  		"or p.Applicationform='1' then p.itemid else null end)/count(distinct p.itemid)*100)) int5 "+
				  		" from (select distinct a.itemid, a.adminorgid,e.Approveplace,e.Applicationformurl," +
				  		"e.Applicationform,e.Abutment,a.Bureautype "+
				  		" from xzql_xzsp_base a, xzql_xzsp_extend e "+
				  		" where e.isprovince = '1' "+
				  		" and e.approveitemstatus = '运行' "+
				  		" and a.itemid = e.itemid(+)) p,spm_bureau b "+
				  		" where p.adminorgid = b.bureauguid "+
				  		" and b.isstreet = '0' "+
				  		" group by b.bureauguid, b.bureaucnfullname, b.bureautabindex "+
				  		" order by b.bureautabindex";
				  setStatList(simpleJdbcDao.queryForRow(sql, StatisticsBean.class));
				  for(StatisticsBean sb:statList){
					  if("0".equals(sb.getInt1())){
						  sb.setStar(new String[]{"0","0","0","0","0"});
					  }else{
						  int per = Integer.valueOf(sb.getInt5());
						 if(per<20){
							  sb.setStar(new String[]{"0","0","0","0"});
						  }else if(per>=20&&per<40){
							  sb.setStar(new String[]{"1","0","0","0"});
						  }else if(per>=40&&per<60){
							  sb.setStar(new String[]{"1","1","0","0"});
						  }else if(per>=60&&per<80){
							  sb.setStar(new String[]{"1","1","1","0"});
						  }else{
							  sb.setStar(new String[]{"1","1","1","1"});
						  }
					  }
				  }
				  return "ywpj";
			  }else if(StringUtils.isNotBlank(getType())&&"ywtj".equals(getType())){
				  sql = "select field1 column1,field2 column2,to_char(nvl(int1,0)) int1,to_char(nvl(int2,0)) int2,to_char(nvl(int3,0)) int3 ,to_char(nvl(int4,0)) int4,to_char(nvl(int5,0)) int5,field3 column3,field4 column4 from ( "+
				  		" select bureautabindex,field1,field2,int1,int2,int1-int2 int3 ,tqbj int4,tsbj int5, "+
				  		" to_char(round(nvl(tqbj,0) /  decode(nvl(int2, 1),0,1,nvl(int2, 1)) * 100, 2), 'fm990.00') || '%' as field3,  "+
				  		" to_char(round( nvl(tsbj,0) / decode(nvl(int2, 1),0,1,nvl(int2, 1)) * 100, 2), 'fm990.00') || '%' as field4  from  "+
				  		" (SELECT  -1 bureauTabIndex,'合计' field1,'' field2, "+
				  		" COUNT(CASE WHEN s.state NOT in('0','20') and i.workflow_guid!='{BFA7B3FB-FFFF-FFFF-AB48-7A5A000002D1}' THEN 1 END)+nvl(sum(case when i.workflow_guid='{BFA7B3FB-FFFF-FFFF-AB48-7A5A000002D1}' then nvl(jiejianx,0) end),0) int1, "+
				  		" COUNT(CASE WHEN i.workflow_guid!='{BFA7B3FB-FFFF-FFFF-AB48-7A5A000002D1}' and s.isfinished ='1' and s.state NOT in('0','20') THEN 1 END)+nvl(sum(case when s.isfinished ='1' and i.workflow_guid='{BFA7B3FB-FFFF-FFFF-AB48-7A5A000002D1}' then nvl(jiejianx,0) end),0) int2 , "+
				  		" sum(case when s.enddate < s.estimateenddate then 1 end) as tqbj, "+ 
				  		" sum(decode(s.state,'45',1,0)) as tsbj  "+
				  		" FROM spi_supervise s,office_spi_declareinfo d,spm_approveitem m,spm_bureau b,office_workflowinstance i " +
				  		" where  s.workflowinstance_guid=i.workflowinstance_guid and b.isstreet='0' and s.bureauguid=b.bureauguid " +
				  		" and b.flag='1' and m.approveitemstatus='运行' and s.approveitemguid=m.approveitemguid and s.workflowinstance_guid=d.workflowinstance_guid ) "+
				  		" union "+
				  		" select b.bureauTabIndex,b.bureaucnfullname field1,b.bureauGUID field2,int1,int2,int1-int2 int3 , "+
				  		" nvl(t.tqbj,0) int4, "+
				  		" nvl(t.tsbj,0) int5, "+
				  		" to_char(round(nvl(t.tqbj,0) /  decode(nvl(t.int2, 1),0,1,nvl(t.int2, 1)) * 100, 2), 'fm990.00') || '%' as field3, "+ 
				  		" to_char(round( nvl(t.tsbj,0) / decode(nvl(t.int2, 1),0,1,nvl(t.int2, 1)) * 100, 2), 'fm990.00') || '%' as field4 " +
				  		"from (select distinct b.bureauguid,b.bureaucnfullname,b.bureautabindex from spm_bureau b,xzql_item_depart_org o,xzql_xzsp_extend e "+
				  		" where b.bureauguid=o.departid and o.itemid=e.itemid and e.isprovince='1' and e.approveitemstatus='运行' and b.isstreet='0') b left outer join (select s.bureauGUID, "+
				  		" COUNT(CASE WHEN s.state NOT in('0','20') and i.workflow_guid!='{BFA7B3FB-FFFF-FFFF-AB48-7A5A000002D1}' THEN 1 END)+nvl(sum(case when i.workflow_guid='{BFA7B3FB-FFFF-FFFF-AB48-7A5A000002D1}' then nvl(jiejianx,0) end),0) int1, "+
				  		" COUNT(CASE WHEN i.workflow_guid!='{BFA7B3FB-FFFF-FFFF-AB48-7A5A000002D1}' and s.isfinished ='1' and s.state NOT in('0','20') THEN 1 END)+nvl(sum(case when s.isfinished ='1' and i.workflow_guid='{BFA7B3FB-FFFF-FFFF-AB48-7A5A000002D1}' then nvl(jiejianx,0) end),0) int2, "+
				  		" sum(case when s.enddate < s.estimateenddate then 1 end) as tqbj, "+ 
				  		" sum(decode(s.state,'45',1,0)) as tsbj  "+
				  		" FROM spi_supervise s,office_spi_declareinfo d,spm_approveitem m ,spm_bureau b,office_workflowinstance i where  "+
				  		" s.workflowinstance_guid=i.workflowinstance_guid and m.approveitemstatus='运行' and b.isstreet='0' and  b.flag='1' and s.bureauguid=b.bureauguid " +
				  		" and s.approveitemguid=m.approveitemguid and  s.workflowinstance_guid=d.workflowinstance_guid  " +
				  		" GROUP BY  s.bureauGUID) t on b.bureauGUID=t.bureauGUID "+ 
				  		" order by bureauTabIndex)";
				  setStatList(simpleJdbcDao.queryForRow(sql, StatisticsBean.class));
				  return "ywtj";
			  }else{
				   sql = "select p.bureaucnfullname column1, "+
				   		" to_char(count(p.itemid)) int1, "+
				   		" to_char(sum(case p.approveitemstatus  "+
				   		" when '运行' then 1 else 0 end)) int2, "+
				   		" to_char(sum(case p.bureautype "+
				   		" when '001' then 1 else 0 end )) int3, "+
				   		" to_char(sum(case p.bureautype "+
				   		" when '002' then 1 else 0 end )) int4, "+
				   		" to_char(sum(case p.bureautype "+
				   		" when '003' then 1  when '004' then 1 else 0 end )) int5"+
				   		" from (select distinct a.itemid,d.bureaucnfullname, "+
				   		" e.approveitemstatus,a.bureautype,d.bureautabindex "+
				   		" from xzql_xzsp_base a, spm_bureau d,xzql_xzsp_extend e "+
				   		" where  e.isprovince = '1' and a.itemid=e.itemid(+) "+
				   		" and a.adminorgid=d.bureauguid and d.isstreet='0' and e.approveitemstatus='运行') p "+
				   		" group by p.bureaucnfullname,p.bureautabindex "+
				   		" order by p.bureautabindex";
				   setStatList(simpleJdbcDao.queryForRow(sql, StatisticsBean.class));
				   return SUCCESS;
			  }
		} catch (Exception e) {
			e.printStackTrace();
			return NONE;
		}
	}

}
