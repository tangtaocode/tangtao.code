package net.risesoft.beans.onlineservice;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import net.risesoft.utils.base.WebUtil;
/**
 * 
  * @ClassName: ApproveItemExtend
  * @Description: 事项扩展信息
  * @author Comsys-zhangkun
  * @date Jun 17, 2013 4:59:53 PM
  *
 */
@Entity
@Table(name="XZQL_XZSP_EXTEND")
public class ApproveItemExtend implements Serializable{
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 7472048420303234457L;
	private String itemid;  //事项ID
	private String iszdsx;  //是否为重大事项：0否；1是；
	private String itemclass;  //事项类别：生育（个人）、土地房产（个人）等
	private String approveitemdesc;  //事项描述
	private String timelimitdesc;  //审批时限说明
	private String address;  //审批办理地点
	private String flag;  //是否发布到外网：1是、0否
	private String servicetype;  //业务分类：0全套服务、1只打印、2打印、申请、反馈
	private String forwardurl;  //URL
	private String approveitemstatus;  //运行状态：运行、停止
	private String approveplace;  //审批系统：0在本系统审批；1在外系统审批；
	private String accepttime;  //受理时间段
	private String ftcode;  //统一编码
	private String ftsubcode;  //子编码
	private String insystem;  //是否系统办理：是、否；
	private String linktype;  //联系方式
	private String doctypeguid;  //证照类型guid
	private String isinhall;  //是否入驻大厅：0否；1是；
	private String iszz;  //是否出具相关文件或证件：1是，0否；
	private String frameflowname;  //业务框架流程图名称
	private String twoflow;  //二级流程
	private String twoflowdes;  //二级流程条件
	private String threeflow;  //三级流程
	private String threeflowdes;  //三级流程条件
	private String oneflow;  //独立流程
	private String oneflowdes;  //独立流程条件
	private String isrereq;//办事指南申请材料是否自动生成
	private String rereq;//申请材料内容
	@Id
	public String getItemid() {
		return itemid;
	}
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	@Column
	public String getIszdsx() {
		return iszdsx;
	}
	public void setIszdsx(String iszdsx) {
		this.iszdsx = iszdsx;
	}
	@Column
	public String getItemclass() {
		return itemclass;
	}
	public void setItemclass(String itemclass) {
		this.itemclass = itemclass;
	}
	@Column
	public String getApproveitemdesc() {
		return approveitemdesc;
	}
	public void setApproveitemdesc(String approveitemdesc) {
		this.approveitemdesc = approveitemdesc;
	}
	@Column
	public String getTimelimitdesc() {
		return timelimitdesc;
	}
	public void setTimelimitdesc(String timelimitdesc) {
		this.timelimitdesc = timelimitdesc;
	}
	@Column
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	@Column
	public String getServicetype() {
		return servicetype;
	}
	public void setServicetype(String servicetype) {
		this.servicetype = servicetype;
	}
	@Column
	public String getForwardurl() {
		return forwardurl;
	}
	public void setForwardurl(String forwardurl) {
		this.forwardurl = forwardurl;
	}
	@Column
	public String getApproveitemstatus() {
		return approveitemstatus;
	}
	public void setApproveitemstatus(String approveitemstatus) {
		this.approveitemstatus = approveitemstatus;
	}
	@Column
	public String getApproveplace() {
		return approveplace;
	}
	public void setApproveplace(String approveplace) {
		this.approveplace = approveplace;
	}
	@Column
	public String getAccepttime() {
		return accepttime;
	}
	public void setAccepttime(String accepttime) {
		this.accepttime = accepttime;
	}
	@Column
	public String getFtcode() {
		return ftcode;
	}
	public void setFtcode(String ftcode) {
		this.ftcode = ftcode;
	}
	@Column
	public String getFtsubcode() {
		return ftsubcode;
	}
	public void setFtsubcode(String ftsubcode) {
		this.ftsubcode = ftsubcode;
	}
	@Column
	public String getInsystem() {
		return insystem;
	}
	public void setInsystem(String insystem) {
		this.insystem = insystem;
	}
	@Column
	public String getLinktype() {
		return linktype;
	}
	public void setLinktype(String linktype) {
		this.linktype = linktype;
	}
	@Column
	public String getDoctypeguid() {
		return doctypeguid;
	}
	public void setDoctypeguid(String doctypeguid) {
		this.doctypeguid = doctypeguid;
	}
	@Column
	public String getIsinhall() {
		return isinhall;
	}
	public void setIsinhall(String isinhall) {
		this.isinhall = isinhall;
	}
	@Column
	public String getIszz() {
		return iszz;
	}
	public void setIszz(String iszz) {
		this.iszz = iszz;
	}
	@Column
	public String getFrameflowname() {
		return frameflowname;
	}
	public void setFrameflowname(String frameflowname) {
		this.frameflowname = frameflowname;
	}
	@Column
	public String getTwoflow() {
		return twoflow;
	}
	public void setTwoflow(String twoflow) {
		this.twoflow = twoflow;
	}
	@Column
	public String getTwoflowdes() {
		return twoflowdes;
	}
	public void setTwoflowdes(String twoflowdes) {
		this.twoflowdes = twoflowdes;
	}
	@Column
	public String getThreeflow() {
		return threeflow;
	}
	public void setThreeflow(String threeflow) {
		this.threeflow = threeflow;
	}
	@Column
	public String getThreeflowdes() {
		return threeflowdes;
	}
	public void setThreeflowdes(String threeflowdes) {
		this.threeflowdes = threeflowdes;
	}
	@Column
	public String getOneflow() {
		return oneflow;
	}
	public void setOneflow(String oneflow) {
		this.oneflow = oneflow;
	}
	@Column
	public String getOneflowdes() {
		return oneflowdes;
	}
	public void setOneflowdes(String oneflowdes) {
		this.oneflowdes = oneflowdes;
	}
	@Column
	public String getIsrereq() {
		return isrereq;
	}
	public void setIsrereq(String isrereq) {
		this.isrereq = isrereq;
	}
	@Column
	public String getRereq() {
		if(rereq!=null&rereq!=""){
			return WebUtil.filter(rereq);
		}else{
			return rereq;
		}
	}
	public void setRereq(String rereq) {
		this.rereq = rereq;
	}

	
}
