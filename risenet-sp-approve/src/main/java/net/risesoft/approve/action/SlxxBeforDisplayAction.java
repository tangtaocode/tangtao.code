package net.risesoft.approve.action;

import java.text.SimpleDateFormat;
import java.util.UUID;

import net.business.engine.common.I_TemplateAction;
import net.business.engine.common.TemplateContext;
import net.risesoft.approve.entity.jpa.SpmApproveitem;
import net.risesoft.approve.entity.jpa.gdbs.ShenBanProcess;
import net.risesoft.approve.entity.jpa.gdbs.ShouliProcess;
import net.risesoft.approve.service.OfficeSpiDeclareinfoService;
import net.risesoft.approve.service.SpmApproveItemService;
import net.risesoft.common.util.ContextUtil;
import net.risesoft.model.OrgUnit;
import net.risesoft.model.Person;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.RisesoftUtil;
import net.risesoft.utilx.FTSuperviseUtil;
import net.risesoft.utilx.StringX;

import org.springframework.jdbc.core.JdbcTemplate;

public class SlxxBeforDisplayAction implements I_TemplateAction{

private OfficeSpiDeclareinfoService officeSpiDeclareinfoService = ContextUtil.getBean("officeSpiDeclareinfoService");;
	
	private SpmApproveItemService spmApproveItemService = ContextUtil.getBean("spmApproveItemService");
	
	public static JdbcTemplate jdbcTemplate = ContextUtil.getBean("routerJdbcTemplate");

	@Override
	public int execute(TemplateContext context) throws Exception {
		//真实的事项guid
		String processInstanceId = context.getRequest().getParameter("guid");
		SpmApproveitem spmApproveitem = null;
		if(processInstanceId!=null&&processInstanceId!=""){
			String approveitemguid=officeSpiDeclareinfoService.findApproveitemguidByProcessInstanceId(processInstanceId);//获取审批事项id
			spmApproveitem = spmApproveItemService.findByApproveitemguid(approveitemguid);//根据事项id查询事项
		}
		String sblsh = officeSpiDeclareinfoService.getDeclaresnByProcessInstanceId(processInstanceId);
		//获取公共信息
		ShouliProcess shouli=null;
		ShenBanProcess shenban=null;
		try {
			shouli = FTSuperviseUtil.findBySblsh(sblsh);
			shenban=FTSuperviseUtil.findShenBanProcess(sblsh);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (null!=shouli) {
			context.put("basepath", context.getRequest().getContextPath());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
			//String temp=UUID.randomUUID().toString().replaceAll("-", "");
			context.put("guid",processInstanceId);	
			context.put("yslbh",shenban.getYsblsh());
			context.put("sblshshort",shenban.getSblshShort());	
			context.put("sxbm",shenban.getSxbmShort());	
			context.put("spsxmc",shenban.getSxmc());
			context.put("sxlx",spmApproveitem.getIsproject()==0?"普通项目":"重大项目");
			context.put("chargeinfo",StringX.isBlank(spmApproveitem.getChargeinfo())?"本事项不收费":spmApproveitem.getChargeinfo().toString());//"CHARGEINFO" VARCHAR2(20 BYTE) NULL ,
			context.put("ywlsh",shenban.getSblshShort());
			context.put("tjfs",shenban.getTjfs());
			context.put("sqxmmc",shenban.getSbxmmc());
			context.put("slbmmc",shouli.getSlbmmc());
			context.put("slbmzzjgdm",shouli.getSlbmzzjgdm());
			context.put("xzqhdm",shouli.getXzqhdm());
			context.put("xzqhmc",shouli.getXzqhmc());
			context.put("slsj",sdf.format(shouli.getSlsj()));
			context.put("blrxm",shouli.getBlrxm());
			context.put("blrgh",shouli.getBlrgh());
			context.put("bslyy",shouli.getBslyy());
			context.put("bz",shouli.getBz());
			context.put("clqd",shenban.getSbclqd());
			context.put("sqrxm",shenban.getSqrmc());
			context.put("sqrlx",shenban.getSqrlx());
			context.put("sqrzjlx",shenban.getSqrzjlx());
			context.put("sqrzjhm",shenban.getSqrzjhm());
			context.put("address","地址");
			context.put("declarertype",shenban.getSqrlx());
			context.put("sbsj",sdf.format(shenban.getSbsj()));
			context.put("lxrxm",shenban.getLxrxm());
			context.put("lxrdh",shenban.getLxrsj());
			context.put("lxrsjh",shenban.getLxrsj());
			context.put("sblsh",shenban.getSblshShort());
			context.put("zch",shenban.getUserIDcode());
			context.put("sfkd","是");
			context.put("kddz","快递地址");
			String spsx="";
			if (spmApproveitem.getTimelimit()==-1) {
				spsx="无法定时限";
			}else if (spmApproveitem.getTimelimit()==0) {
				spsx="即来即办";
			}else {
				spsx=spmApproveitem.getTimelimit()+"个工作日";
			}
			context.put("spsx",spsx);
		}
		return SUCCESS; 
	}

	@Override
	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return null;
	}

}
