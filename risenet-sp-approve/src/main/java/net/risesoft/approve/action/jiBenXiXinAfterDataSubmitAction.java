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
public class jiBenXiXinAfterDataSubmitAction implements I_TemplateAction{
	
	private OfficeSpiDeclareinfoService officeSpiDeclareinfoService = ContextUtil.getBean("officeSpiDeclareinfoService");;
	
	public static JdbcTemplate jdbcTemplate = ContextUtil.getBean("routerJdbcTemplate");
	
	public int execute(TemplateContext context) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//流程实例ID
		String processInstanceId = context.getRequest().getParameter("guid");
		//审批业务id
		String workflowinstance_guid = officeSpiDeclareinfoService.getGuidByProcessInstanceId(processInstanceId);
		//OfficeSpiDeclareinfo officeSpiDeclareinfo  = officeSpiDeclareinfoService.findByProcessInstanceId(processInstanceId);
	
		String declaresn = context.getRequest().getParameter("jbxx_declaresn");//业务编号
		String approveitemname = context.getRequest().getParameter("title");//审批事项名称	
		String xiangmumingcheng = context.getRequest().getParameter("jbxx_xiangmumingcheng");//项目名称
		String declarerperson = context.getRequest().getParameter("jbxx_declarerperson");//申请单位/人
		String address = context.getRequest().getParameter("jbxx_address");//单位地址
		String zhengjiandaima = context.getRequest().getParameter("jbxx_zhengjiandaima");//证件代码
		String declarertype = context.getRequest().getParameter("jbxx_declarertype");//申请人类型
		String declarerlxr = context.getRequest().getParameter("jbxx_declarerlxr");//联系人
		String declarerlxridtype = context.getRequest().getParameter("jbxx_declarerlxridtype");//联系人证件类型
		String declarerlxrid = context.getRequest().getParameter("jbxx_declarerlxrid");//联系人证件号码
		String declarertel = context.getRequest().getParameter("jbxx_declarertel");//联系电话
		String declarermobile = context.getRequest().getParameter("jbxx_declarermobile");//手机
		String declarerfax = context.getRequest().getParameter("jbxx_declarerfax");//传真
		String employeedeptname = context.getRequest().getParameter("jbxx_employeedeptname");//经办人
		String declaredatetime = context.getRequest().getParameter("jbxx_declaredatetime");//申请日期
		String nsjysl = context.getRequest().getParameter("jbxx_nsjysl");//年设计用水量
		
		Date date = null;
		if(declaredatetime!=null&&declaredatetime!=""){
			date = sdf.parse(declaredatetime);
		}
		
		try {
			List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
			String selSql = "select * from OFFICE_SPI_DECLAREINFO t where t.workflowinstance_guid = ?";
			List<Object> param = new ArrayList<Object>();
			param.add(workflowinstance_guid);
			listMap = jdbcTemplate.queryForList(selSql,param.toArray());
			if(listMap.size()>0){//更新
				String updateSql = "update OFFICE_SPI_DECLAREINFO t set t.DECLARESN=?,t.APPROVEITEMNAME=?,t.XIANGMUMINGCHENG=?,t.DECLARERPERSON=?,"
						+ "t.ADDRESS=?,t.ZHENGJIANDAIMA=?,t.DECLARERTYPE=?,t.DECLARERLXR=?,t.DECLARERLXRIDTYPE=?,"
						+ "t.DECLARERLXRID=?,t.DECLARERTEL=?,t.DECLARERMOBILE=?,t.DECLARERFAX=?,"
						+ "t.EMPLOYEEDEPTNAME=?,t.DECLAREDATETIME=?,t.NSJYSL=? where t.WORKFLOWINSTANCE_GUID=?";
				List<Object> params = new ArrayList<Object>();
				params.add(declaresn);
				params.add(approveitemname);
				params.add(xiangmumingcheng);
				params.add(declarerperson);
				params.add(address);
				params.add(zhengjiandaima);
				params.add(declarertype);	
				params.add(declarerlxr);
				params.add(declarerlxridtype);	
				params.add(declarerlxrid);
				params.add(declarertel);	
				params.add(declarermobile);
				params.add(declarerfax);
				params.add(employeedeptname);	
				params.add(date);
				params.add(nsjysl);
				params.add(workflowinstance_guid);
				jdbcTemplate.update(updateSql,params.toArray());
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
