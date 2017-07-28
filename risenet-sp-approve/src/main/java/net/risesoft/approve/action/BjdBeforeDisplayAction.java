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
import net.risesoft.approve.entity.jpa.SpmApproveitem;
import net.risesoft.approve.entity.jpa.SpmBureau;
import net.risesoft.approve.entity.jpa.gdbs.ShenBanProcess;
import net.risesoft.approve.entity.jpa.gdbs.ShouliProcess;
import net.risesoft.approve.repository.jpa.supervise.ShenbanRepository;
import net.risesoft.approve.service.OfficeSpiDeclareinfoService;
import net.risesoft.approve.service.SpmApproveItemService;
import net.risesoft.approve.service.SpmBureauService;
import net.risesoft.common.util.ContextUtil;
import net.risesoft.model.OrgUnit;
import net.risesoft.model.Person;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.RisesoftUtil;
import net.risesoft.utilx.FTSuperviseUtil;
import net.risesoft.utilx.StringX;

public class BjdBeforeDisplayAction implements I_TemplateAction{

private OfficeSpiDeclareinfoService officeSpiDeclareinfoService = ContextUtil.getBean("officeSpiDeclareinfoService");;
	
	
	private SpmApproveItemService spmApproveItemService = ContextUtil.getBean("spmApproveItemService");
	private SpmBureauService spmBureauService =ContextUtil.getBean("spmBureauService");
	
	public static JdbcTemplate jdbcTemplate = ContextUtil.getBean("routerJdbcTemplate");
	@Override
	public int execute(TemplateContext context) throws Exception {
		//真实的事项guid
		String processInstanceId = context.getRequest().getParameter("guid");
		String sql="select count(1) from EFORM_SB_LCBD_BJD where guid =?";
		int count =jdbcTemplate.queryForObject(sql, Integer.class, processInstanceId);
		/*if (count>0) {
			return SUCCESS;
		}*/
		String SPinstanceId = context.getRequest().getParameter("SPinstanceId");
		Person person = ThreadLocalHolder.getPerson();
		OrgUnit dept = RisesoftUtil.getPersonManager().getBureau(ThreadLocalHolder.getTenantId(), person.getID());
		OfficeSpiDeclareinfo officeSpiDeclareinfo=null;
		SpmApproveitem spmApproveitem=null;
		SpmBureau spmBureau=null;
		if(processInstanceId!=null&&processInstanceId!=""){
			officeSpiDeclareinfo=officeSpiDeclareinfoService.findByGuid(SPinstanceId);
			String approveitemguid=officeSpiDeclareinfoService.findApproveitemguidByProcessInstanceId(processInstanceId);//获取审批事项id
			spmApproveitem = spmApproveItemService.findByApproveitemguid(approveitemguid);//根据事项id查询事项
			spmBureau =spmBureauService.findDepartMentById(officeSpiDeclareinfo.getBureauguid());
			/*String doctypeguid = "";
			if(spmApproveitem!=null){
				doctypeguid = spmApproveitem.getDoctypeguid();//获取证照类型guidER
			}
			if(doctypeguid!=null&&doctypeguid!=""){
				context.put("certifyWay", "证照");//办结方式
			}else{
				context.put("docWay", "文件");//办结方式
			}*/
			String approveitemname="";
			if (spmApproveitem!=null) {
				approveitemname=spmApproveitem.getApproveitemname();
			}
			if (approveitemname.equals("取水许可")||approveitemname.equals("城市排水许可")) {
				context.put("certifyWay", "证照");//办结方式
			}else{
				context.put("docWay", "文件");//办结方式
			}
			ShenBanProcess shenBanProcess=FTSuperviseUtil.findShenBanProcess(officeSpiDeclareinfo.getSblsh());
			ShouliProcess shouliProcess=FTSuperviseUtil.findBySblsh(officeSpiDeclareinfo.getSblsh());
			context.put("WORKFLOWINSTANCE_GUID", SPinstanceId);
			context.put("approveitemname", spmApproveitem.getApproveitemname());	//事项名称
			context.put("bureauname", spmBureau.getBureauname());					//办结单位
			SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
			context.put("endDate", sdf.format(new Date()));			//办结时间
			context.put("sendDate", sdf.format(new Date()));			//出证（发文）日期
			context.put("sblsh", officeSpiDeclareinfo.getSblsh());	//申办流水号
			context.put("sxid", shenBanProcess.getSxbmShort());//事项编码
			context.put("zzzt", "0"); //证照状态
			
			context.put("ztmc", shenBanProcess.getSqrmc());//证照主体名
			context.put("ztlb", shenBanProcess.getSqrlx());//证照主体类型
			context.put("ztdm", shenBanProcess.getSqrzjhm());//证照主体代码
			context.put("frxm", shenBanProcess.getLxrxm());//法人姓名
			context.put("frsfz", shenBanProcess.getLxrsfzjhm());//法人身份证
			context.put("sxrq", sdf.format(new Date()));//证件生效时间
			//context.put("jzrq", sdf.format(new Date()));//证件失效时间
			context.put("jgdm", shouliProcess.getSlbmzzjgdm());//发证机关代码
			context.put("fzjg", shouliProcess.getSlbmmc());//发证机关名称
			context.put("xzqh", shouliProcess.getXzqhdm());//发证机关行政区划
			context.put("ca", "");//CA序列号
		}
		
		return SUCCESS;
	}

	@Override
	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return null;
	}

}
