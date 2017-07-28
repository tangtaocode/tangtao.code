package net.risesoft.approve.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import net.business.engine.common.I_TemplateAction;
import net.business.engine.common.TemplateContext;
import net.risesoft.approve.entity.jpa.OfficeSpiDeclareinfo;
import net.risesoft.approve.service.OfficeSpiDeclareinfoService;
import net.risesoft.common.util.ContextUtil;

/**
 * 1、数据提交后的校验
* 2、数据的校验和提交线程同步
 * 3、添加在模板的数据提交后action中
 * 4、不适合包含部件（如：在线编辑、不定长编辑）处理的表单
 * @author Administrator
 *
 */
public class AfterDataSubmitAction implements I_TemplateAction{
	
	
	private OfficeSpiDeclareinfoService officeSpiDeclareinfoService = ContextUtil.getBean("officeSpiDeclareinfoService");;
	
	
	public static JdbcTemplate jdbcTemplate = ContextUtil.getBean("routerJdbcTemplate");
	
	public int execute(TemplateContext context) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//流程实例ID
		String processInstanceId = context.getRequest().getParameter("guid");
		//审批业务id
		String workflowinstance_guid = officeSpiDeclareinfoService.getGuidByProcessInstanceId(processInstanceId);
		OfficeSpiDeclareinfo officeSpiDeclareinfo  = officeSpiDeclareinfoService.findByProcessInstanceId(processInstanceId);
		Date sendDate = null;
		Date endDate = null;
		String docTitle = officeSpiDeclareinfo.getApproveItemName(); //文件标题
		//String title = context.getRequest().getParameter("title"); //文件标题
		String docWay = context.getRequest().getParameter("bjd_docWay");//文件方式办结
		if(docWay!=null&&docWay.equals("文件")){
			docWay = "√";
		}
		String certifyWay = context.getRequest().getParameter("bjd_certifyWay");//证照方式办结
		if(certifyWay!=null&&certifyWay.equals("证照")){
			certifyWay = "√";
		}
		String docNumber = context.getRequest().getParameter("bjd_docNumber");//文号
		String sendTime = context.getRequest().getParameter("bjd_sendDate");//发文时间
		if(sendTime!=null&&sendTime!=""){
			sendDate = sdf.parse(sendTime);
		}
		String certifyName = context.getRequest().getParameter("bjd_certifyName");//证照名
		String certifyNumber = context.getRequest().getParameter("bjd_certifyNumber");//证照编号
		String bureauName = context.getRequest().getParameter("bjd_bureauName");//办结单位名称
		String endTime = context.getRequest().getParameter("bjd_endDate");//办结时间
		if(endTime!=null&&endTime!=""){
			endDate = sdf.parse(endTime);
		}
		try {
			List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
			String selSql = "select * from office_spi_banjiejilu t where t.workflowinstance_guid = ?";
			List<Object> param = new ArrayList<Object>();
			param.add(workflowinstance_guid);
			listMap = jdbcTemplate.queryForList(selSql,param.toArray());
			if(listMap.size()>0){//更新
				String updateSql = "update office_spi_banjiejilu t set t.docway=?,t.certifyway=?,t.doctitle=?,t.docnumber=?,"
						+ "t.senddate=?,t.certifyname=?,t.certifynumber=?,t.banjieunit=?,t.enddate=? where t.workflowinstance_guid=?";
				List<Object> params = new ArrayList<Object>();
				params.add(docWay);
				params.add(certifyWay);
				params.add(docTitle);
				params.add(docNumber);
				params.add(sendDate);
				params.add(certifyName);
				params.add(certifyNumber);	
				params.add(bureauName);
				params.add(endDate);	
				params.add(workflowinstance_guid);
				jdbcTemplate.update(updateSql,params.toArray());
			}else{//插入
				String insertSql = "insert into office_spi_banjiejilu(workflowinstance_guid,docway,certifyway,doctitle,docnumber,"
						+ "senddate,certifyname,certifynumber,banjieunit,enddate,processinstanceid) values (?,?,?,?,?,?,?,?,?,?,?)";
				List<Object> params = new ArrayList<Object>();
				params.add(workflowinstance_guid);
				params.add(docWay);
				params.add(certifyWay);
				params.add(docTitle);
				params.add(docNumber);
				params.add(sendDate);
				params.add(certifyName);
				params.add(certifyNumber);	
				params.add(bureauName);
				params.add(endDate);	
				params.add(processInstanceId);	
				jdbcTemplate.update(insertSql,params.toArray());
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
