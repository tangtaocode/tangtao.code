package net.risesoft.approve.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import net.business.engine.common.I_TemplateAction;
import net.business.engine.common.TemplateContext;
import net.risesoft.approve.service.OfficeSpiDeclareinfoService;
import net.risesoft.common.util.ContextUtil;

/**
 * 1、基本信息表数据提交后的校验
* 2、数据的校验和提交线程同步
 * 3、添加在模板的数据提交后action中
 * 4、不适合包含部件（如：在线编辑、不定长编辑）处理的表单
 * @author Administrator
 *
 */
public class BanLiDanAfterDataSubmitAction implements I_TemplateAction{
	
	private OfficeSpiDeclareinfoService officeSpiDeclareinfoService = ContextUtil.getBean("officeSpiDeclareinfoService");;
	
	public static JdbcTemplate jdbcTemplate = ContextUtil.getBean("routerJdbcTemplate");
	
	public int execute(TemplateContext context) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//流程实例ID
		String processInstanceId = context.getRequest().getParameter("guid");
		//审批业务id
		String workflowinstance_guid = officeSpiDeclareinfoService.getGuidByProcessInstanceId(processInstanceId);
		//OfficeSpiDeclareinfo officeSpiDeclareinfo  = officeSpiDeclareinfoService.findByProcessInstanceId(processInstanceId);
	
		String bureauname = context.getRequest().getParameter("jbxx_bureauname");//受理单位
		//String acceptanceTime = context.getRequest().getParameter("jbxx_acceptanceTime");//受理日期	
		String chengnuoriqi = context.getRequest().getParameter("jbxx_chengnuoriqi");//承诺日期
		String limiTime = context.getRequest().getParameter("jbxx_limiTime");//承诺时限
		String employeedeptname = context.getRequest().getParameter("jbxx_employeedeptname");//经办人
		String zixundianhua = context.getRequest().getParameter("jbxx_zixundianhua");//咨询电话
		
		Date date = null;
		if(chengnuoriqi!=null&&chengnuoriqi!=""){
			date = sdf.parse(chengnuoriqi);
		}
		
		try {
			List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
			String selSql = "select * from OFFICE_SPI_DECLAREINFO t where t.workflowinstance_guid = ?";
			List<Object> param = new ArrayList<Object>();
			param.add(workflowinstance_guid);
			listMap = jdbcTemplate.queryForList(selSql,param.toArray());
			if(listMap.size()>0){//更新
				String updateSql = "update OFFICE_SPI_DECLAREINFO t set t.BUREAUNAME=?,t.LIMITIME=?,t.ZIXUNDIANHUA=?,t.EMPLOYEEDEPTNAME=?,"
						+ "t.CHENGNUORIQI=? where t.WORKFLOWINSTANCE_GUID=?";
				List<Object> params = new ArrayList<Object>();
				params.add(bureauname);
				params.add(limiTime);
				params.add(zixundianhua);
				params.add(employeedeptname);	
				params.add(date);
				params.add(workflowinstance_guid);
				//jdbcTemplate.update(updateSql,params.toArray());
			}
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
		return SUCCESS;
	}
		

	public String getErrorMessage() {
		return null;
	}
}
