package net.risesoft.beans.interaction;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
/**
 * 
  * @ClassName: Complain
  * @Description: 投诉
  * @author Comsys-zhangkun
  * @date Jun 21, 2013 6:51:24 PM
  *
 */
@Entity
@Table(name="SPM_COMPLAIN")
public class Complain implements Serializable{
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = -301046700143203696L;
	private String complainguid;  //投诉ID
	private String username;  //投诉人姓名
	private String telephone;  //投诉人电话
	private String address;  //投诉人地址
	private String email;  //投诉人邮箱
	private String title;  //投诉标题
	private String bureauguid;  //部门编号
	private String bureauname;  //被投诉部门名称
	private String content;  //投诉内容
	private String complaintype;  //投诉类型
	private Date complaindate;  //投诉时间
	private String mobile;  //投诉人手机号
	private String approveitem;  //事项编号
	private Date responsedate;  //回复时间
	private String approveitemname;  //事项名称
	private String isresponse;  //是否处理
	private List<ComplainRes> comResList = new ArrayList<ComplainRes>();
	@OneToMany(mappedBy = "complain", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	public List<ComplainRes> getComResList() {
		return comResList;
	}
	public void setComResList(List<ComplainRes> comResList) {
		this.comResList = comResList;
	}
	@Id
	public String getComplainguid() {
		return complainguid;
	}
	public void setComplainguid(String complainguid) {
		this.complainguid = complainguid;
	}
	@Column
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Column
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	@Column
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Column
	public String getBureauguid() {
		return bureauguid;
	}
	public void setBureauguid(String bureauguid) {
		this.bureauguid = bureauguid;
	}
	@Column
	public String getBureauname() {
		return bureauname;
	}
	public void setBureauname(String bureauname) {
		this.bureauname = bureauname;
	}
	@Column
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column
	public String getComplaintype() {
		return complaintype;
	}
	public void setComplaintype(String complaintype) {
		this.complaintype = complaintype;
	}
	@Column
	public Date getComplaindate() {
		return complaindate;
	}
	public void setComplaindate(Date complaindate) {
		this.complaindate = complaindate;
	}
	
	@Column
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Column
	public String getApproveitem() {
		return approveitem;
	}
	public void setApproveitem(String approveitem) {
		this.approveitem = approveitem;
	}
	@Column
	public Date getResponsedate() {
		return responsedate;
	}
	public void setResponsedate(Date responsedate) {
		this.responsedate = responsedate;
	}
	@Column
	public String getApproveitemname() {
		return approveitemname;
	}
	public void setApproveitemname(String approveitemname) {
		this.approveitemname = approveitemname;
	}
	@Column
	public String getIsresponse() {
		return isresponse;
	}
	public void setIsresponse(String isresponse) {
		this.isresponse = isresponse;
	}

}
