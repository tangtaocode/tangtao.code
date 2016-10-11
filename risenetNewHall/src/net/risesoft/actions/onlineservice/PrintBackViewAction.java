package net.risesoft.actions.onlineservice;

import javax.annotation.Resource;

import net.risesoft.actions.base.BaseActionSupport;
import net.risesoft.beans.onlineservice.Approveinstance;
import net.risesoft.daos.base.ISimpleJdbcDao;
import net.risesoft.utils.annotation.LoginRequired;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

/**
 * @TODO 打印受理回执
 * @author ZK
 *
 */
@Controller
@ParentPackage("default")//继承json包，用于返回json
@InterceptorRefs( { @InterceptorRef("isLoginStack") })//权限拦截
public class PrintBackViewAction  extends BaseActionSupport {
	private String sbguid; //申报GUID
	private String approveguid;
	@Resource
	private ISimpleJdbcDao<Approveinstance > simpleJdbcDao;
	private Approveinstance app;
	/**
	 * 
	 */
	private static final long serialVersionUID = -391068156117300891L;
	private static String zbgg = "'{0A009FA8-FFFF-FFFF-8CDA-148A0000004B}','{0A009FA8-FFFF-FFFF-8CCA-A01400000030}','{0A0102A1-0000-0000-6A01-9A85FFFFFF8E}','{0A009FA8-0000-0000-72DA-20CF000003A3}','{0A0102A1-0000-0000-68FD-F8BAFFFFFF8D}','{7F000001-FFFF-FFFF-A669-F731FFFFFF88}'";
	private static String zbqk = "'{0A009FA8-FFFF-FFFF-8CEF-5B70FFFFFF88}','{0A0102A1-0000-0000-68F9-BCD4FFFFFF8C}','{7F000001-FFFF-FFFF-A669-F7190000007F}','{AC1E060B-FFFF-FFFF-A889-921DFFFFFFF5}'";
	private static String zbwj = "'{0A009FA8-FFFF-FFFF-8CE7-CD220000006B}','{0A009FA8-0000-0000-68C9-3CDB0000004C}','{7F000001-FFFF-FFFF-A669-F75700000079}','{0A009FA8-FFFF-FFFF-8836-09C6000004FF}'";
	/**
	 *@TODO 打印招标公告预受理回执
	 *@author  ZK
	 */
	@Action(value = "/printBackView/printZbgg", 
			results = { @Result(name = "success", location = "/WEB-INF/page/onlineservice/printview/comonreceipt.jsp")})
	@LoginRequired(module="approveItem")
	public String printZbgg(){
		//根据申报的GUID 在申报表中 查询 工程名称，申请编号，申报时间
			String sql = "select s.gcmc as gcmc,s.declaresn,to_char(s.feedbacktime,'yyyy') ytjids,to_char(s.feedbacktime,'MM') xbqids," +
			" to_char(s.feedbacktime,'dd') xbzids,s.approveguid,s.formname from sb_approveinstance s where s.guid=?";
			Approveinstance app = simpleJdbcDao.getBean(sql, new Object[]{sbguid,"-1","-1"}, Approveinstance.class);
			setApp(app);
//		if(zbgg.contains(app.getApproveguid())){
//			return "zbgg";
//		}else if(zbqk.contains(app.getApproveguid())){
//			return "zbqk"; 
//			return "zbwj";
//		}else{
			return SUCCESS;
	}

	public String getSbguid() {
		return sbguid;
	}

	public void setSbguid(String sbguid) {
		this.sbguid = sbguid;
	}

	public Approveinstance getApp() {
		return app;
	}

	public void setApp(Approveinstance app) {
		this.app = app;
	}

	
	
}
