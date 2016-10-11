package net.risesoft.beans.bizbankroll;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.risesoft.utils.base.DateFormatUtil;

import org.directwebremoting.annotations.DataTransferObject;
/**
 * 
  * @ClassName: BizHistorySubsidize
  * @Description: 科技创新扶持申请单位基本信息历史资助情况
  * @author Comsys-zhangkun
  * @date Apr 10, 2013 4:30:34 PM
  *
 */
@Entity
@Table(name="ZJFC_KJ_SUBSIDIZE")
@DataTransferObject
public class BizHistorySubsidize implements Serializable{
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	private static final long serialVersionUID = 5502760248813574981L;
	private String guid = "";  //
	private String basicguid = "";  //
	private String projectname = "";  //项目名称
	private String subsidizename = "";  //资助部门
	private Date subsidizedate;  //资助时间
	private String subsidizemoney = "";  //资助金额
	private String acceptance = "";  //项目验收情况
	private Date acceptancedate;  //验收时间
	private String subsidizetype = "";  //资助形式
	private String repayment = "";  //借款偿还情况
	private Date repaymentdate;  //还款时间
	private String userguid = "";  //操作用户
	private Date createtime;  //提交时间
	private String oldguid;
	@SuppressWarnings("unused")
	private String subsidizedateStr = "";
	@SuppressWarnings("unused")
	private String acceptancedateStr = "";
	@SuppressWarnings("unused")
	private String repaymentdateStr = "";
	
	@Transient
	public String getOldguid() {
		return oldguid;
	}
	public void setOldguid(String oldguid) {
		this.oldguid = oldguid;
	}
	@Transient
	public String getSubsidizedateStr() {
		return DateFormatUtil.parseToStrMin(subsidizedate);
	}
	public void setSubsidizedateStr(String subsidizedateStr) {
		this.subsidizedateStr = subsidizedateStr;
	}
	@Transient
	public String getAcceptancedateStr() {
		 return DateFormatUtil.parseToStrMin(acceptancedate);
	}
	public void setAcceptancedateStr(String acceptancedateStr) {
		this.acceptancedateStr = acceptancedateStr;
	}
	@Transient
	public String getRepaymentdateStr() {
		return DateFormatUtil.parseToStrMin(repaymentdate);
	}
	public void setRepaymentdateStr(String repaymentdateStr) {
		this.repaymentdateStr = repaymentdateStr;
	}
	@Id
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	@Column
	public String getBasicguid() {
		return basicguid;
	}
	public void setBasicguid(String basicguid) {
		this.basicguid = basicguid;
	}
	@Column
	public String getProjectname() {
		return projectname;
	}
	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	@Column
	public String getSubsidizename() {
		return subsidizename;
	}
	public void setSubsidizename(String subsidizename) {
		this.subsidizename = subsidizename;
	}
	@Column
	public Date getSubsidizedate() {
		return subsidizedate;
	}
	public void setSubsidizedate(Date subsidizedate) {
		this.subsidizedate = subsidizedate;
	}
	@Column
	public String getSubsidizemoney() {
		return subsidizemoney;
	}
	public void setSubsidizemoney(String subsidizemoney) {
		this.subsidizemoney = subsidizemoney;
	}
	@Column
	public String getAcceptance() {
		return acceptance;
	}
	public void setAcceptance(String acceptance) {
		this.acceptance = acceptance;
	}
	@Column
	public Date getAcceptancedate() {
		return acceptancedate;
	}
	public void setAcceptancedate(Date acceptancedate) {
		this.acceptancedate = acceptancedate;
	}
	@Column
	public String getSubsidizetype() {
		return subsidizetype;
	}
	public void setSubsidizetype(String subsidizetype) {
		this.subsidizetype = subsidizetype;
	}
	@Column
	public String getRepayment() {
		return repayment;
	}
	public void setRepayment(String repayment) {
		this.repayment = repayment;
	}
	@Column
	public Date getRepaymentdate() {
		return repaymentdate;
	}
	public void setRepaymentdate(Date repaymentdate) {
		this.repaymentdate = repaymentdate;
	}
	@Column
	public String getUserguid() {
		return userguid;
	}
	public void setUserguid(String userguid) {
		this.userguid = userguid;
	}
	@Column
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	

}
