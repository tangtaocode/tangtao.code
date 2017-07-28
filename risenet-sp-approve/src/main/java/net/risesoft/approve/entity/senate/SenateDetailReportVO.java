package net.risesoft.approve.entity.senate;

import net.risesoft.utilx.StringX;

public class SenateDetailReportVO {
	private String bureauname;//受理单位
	private String declaresn;//受理号
	private String approveitemname;//事项名称
	private String employeedeptname;//服务人员
	private String declaredatetime;//受理时间
	private String satisfaction_sl;//受理评价情况
	private String satisfaction;//办结评价情况
	private String senatedatetime_sl;//受理评价时间
	private String senatedatetime;//办结评价时间
	private String type_sl;//受理评价方式
	private String type;//办结评价方式
	private String from;//业务来源
	public String getBureauname() {
		return bureauname;
	}
	public void setBureauname(String bureauname) {
		this.bureauname = bureauname;
	}
	public String getDeclaresn() {
		return declaresn;
	}
	public void setDeclaresn(String declaresn) {
		this.declaresn = declaresn;
	}
	public String getApproveitemname() {
		return approveitemname;
	}
	public void setApproveitemname(String approveitemname) {
		this.approveitemname = approveitemname;
	}
	public String getEmployeedeptname() {
		return employeedeptname;
	}
	public void setEmployeedeptname(String employeedeptname) {
		this.employeedeptname = employeedeptname;
	}
	public String getDeclaredatetime() {
		return declaredatetime;
	}
	public void setDeclaredatetime(String declaredatetime) {
		this.declaredatetime = declaredatetime;
	}
	public String getSatisfaction_sl() {
		return satisfaction_sl;
	}
	public void setSatisfaction_sl(String satisfaction_sl) {
		this.satisfaction_sl = satisfaction_sl;
	}
	public String getSatisfaction() {
		String s="";
		if("1".equalsIgnoreCase(satisfaction)){
			s="满意";
		}
		if("2".equalsIgnoreCase(satisfaction)){
			s="一般";
		}
		if("3".equalsIgnoreCase(satisfaction)){
			s="不满意";
		}
		return s;
	}
	public void setSatisfaction(String satisfaction) {
		this.satisfaction = satisfaction;
	}
	public String getSenatedatetime_sl() {
		String sl="";
		if("1".equalsIgnoreCase(senatedatetime_sl)){
			sl="满意";
		}
		if("2".equalsIgnoreCase(senatedatetime_sl)){
			sl="一般";
		}
		if("3".equalsIgnoreCase(senatedatetime_sl)){
			sl="不满意";
		}
		return sl;
	}
	public void setSenatedatetime_sl(String senatedatetime_sl) {
		this.senatedatetime_sl = senatedatetime_sl;
	}
	public String getSenatedatetime() {
		return senatedatetime;
	}
	public void setSenatedatetime(String senatedatetime) {
		this.senatedatetime = senatedatetime;
	}
	public String getType_sl() {
		String tl ="评价器";
		if("no".equalsIgnoreCase(satisfaction_sl)||"".equalsIgnoreCase(satisfaction_sl)){
			tl="";
		}
		if("3".equalsIgnoreCase(type_sl)){
			tl="网上大厅";
		}
		if("4".equalsIgnoreCase(type_sl)){
			tl="手机短信";
		}
		return tl;
	}
	public void setType_sl(String type_sl) {
		this.type_sl = type_sl;
	}
	public String getType() {
		String t ="评价器";
		if("no".equalsIgnoreCase(satisfaction)||"".equalsIgnoreCase(satisfaction)){
			t="";
		}
		if("3".equalsIgnoreCase(type)){
			t="网上大厅";
		}
		if("4".equalsIgnoreCase(type)){
			t="手机短信";
		}
		return t;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFrom() {
		String f ="无业务来源";
		if("1".equalsIgnoreCase(type)||"1".equalsIgnoreCase(type_sl)){
			f="审批与服务平台";
		}
		if("2".equalsIgnoreCase(type)||"2".equalsIgnoreCase(type_sl)){
			f="窗口咨询";
		}
		if("5".equalsIgnoreCase(type)||"5".equalsIgnoreCase(type_sl)){
			f="无业务来源";
		}
		return f;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	
	
}
