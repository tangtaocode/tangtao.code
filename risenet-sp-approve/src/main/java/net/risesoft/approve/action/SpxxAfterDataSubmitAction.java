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

public class SpxxAfterDataSubmitAction implements I_TemplateAction  {

	private OfficeSpiDeclareinfoService officeSpiDeclareinfoService = ContextUtil.getBean("officeSpiDeclareinfoService");;

	public static JdbcTemplate jdbcTemplate = ContextUtil.getBean("routerJdbcTemplate");

	@Override
	public int execute(TemplateContext context) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//流程实例ID
		String processInstanceId = context.getRequest().getParameter("guid");
		//审批业务id
		String workflowinstance_guid = officeSpiDeclareinfoService.getGuidByProcessInstanceId(processInstanceId);

		String declaresn = context.getRequest().getParameter("jbxx_declaresn");//业务编号
		String approveitemname = context.getRequest().getParameter("jbxx_approveitemname");//审批事项名称	
		String xiangmumingcheng = context.getRequest().getParameter("jbxx_xiangmumingcheng");//项目名称
		String declarerperson = context.getRequest().getParameter("jbxx_declarerperson");//申请单位/人
		String jbxx_bureauname = context.getRequest().getParameter("jbxx_bureauname");//单位地址
		String zhengjiandaima = context.getRequest().getParameter("spxx_guid");//审批信息GUID
		String declarertype = context.getRequest().getParameter("spxx_processInstanceId");//流程实例ID
		String declarerlxr = context.getRequest().getParameter("spxx_lwzh");//来文字号
		String declarerlxridtype = context.getRequest().getParameter("spxx_filetype");//文件类型
		String declarerlxrid = context.getRequest().getParameter("spxx_bureau_NW");//拟文单位
		String declarertel = context.getRequest().getParameter("spxx_audit_info");//处室审核
		String declarermobile = context.getRequest().getParameter("spxx_person_NG");//拟稿人
		String employeedeptname = context.getRequest().getParameter("spxx_title_HF");//回复标题
		String declaredatetime = context.getRequest().getParameter("jbxx_declaredatetime");//申请日期
		String nsjysl = context.getRequest().getParameter("spxx_wenhao");//文号

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
				String updateSql = "update EFORM_SB_LCBD_SPXX t set t.DECLARESN=?,t.APPROVEITEMNAME=?,t.XIANGMUMINGCHENG=?,t.DECLARERPERSON=?,"
						+ "t.ADDRESS=?,t.ZHENGJIANDAIMA=?,t.DECLARERTYPE=?,t.DECLARERLXR=?,t.DECLARERLXRIDTYPE=?,"
						+ "t.DECLARERLXRID=?,t.DECLARERTEL=?,t.DECLARERMOBILE=?,t.DECLARERFAX=?,"
						+ "t.EMPLOYEEDEPTNAME=?,t.DECLAREDATETIME=?,t.NSJYSL=? where t.WORKFLOWINSTANCE_GUID=?";
				List<Object> params = new ArrayList<Object>();
				params.add(declaresn);
				params.add(approveitemname);
				params.add(xiangmumingcheng);
				params.add(declarerperson);
				params.add(zhengjiandaima);
				params.add(declarertype);
				params.add(declarerlxr);
				params.add(declarerlxridtype);
				params.add(declarerlxrid);
				params.add(declarertel);
				params.add(declarermobile);
				params.add(employeedeptname);
				params.add(date);
				params.add(nsjysl);
				params.add(workflowinstance_guid);
				//jdbcTemplate.update(updateSql,params.toArray());
			}
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
		return SUCCESS;
	}

	@Override
	public String getErrorMessage() {
		return null;
	}

}
