package net.risesoft.beans.interaction;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
/**
 * 
  * @ClassName: ComplainRes
  * @Description: 投诉回复
  * @author Comsys-zhangkun
  * @date Jun 21, 2013 6:55:35 PM
  *
 */
@Entity
@Table(name="SPM_COMPLAIN_RESPONSE")
public class ComplainRes {
	private String id;  //主键ID
	private String departmentid;  //回复部门id
	private String redepartmentid;  //转办部门id
	private String content;  //回复内容
	private Date createtime;  //创建时间
	private String relabel;  //回复描述
	private Date reverttime;  //转发时间
	private Complain complain;
	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@ManyToOne(cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name="complainguid", referencedColumnName="complainguid")//外键为sut_id，与student中的id关联
	public Complain getComplain() {
		return complain;
	}
	public void setComplain(Complain complain) {
		this.complain = complain;
	}
	@Column
	public String getDepartmentid() {
		return departmentid;
	}
	public void setDepartmentid(String departmentid) {
		this.departmentid = departmentid;
	}
	@Column
	public String getRedepartmentid() {
		return redepartmentid;
	}
	public void setRedepartmentid(String redepartmentid) {
		this.redepartmentid = redepartmentid;
	}
	@Column
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	@Column
	public String getRelabel() {
		return relabel;
	}
	public void setRelabel(String relabel) {
		this.relabel = relabel;
	}
	@Column
	public Date getReverttime() {
		return reverttime;
	}
	public void setReverttime(Date reverttime) {
		this.reverttime = reverttime;
	}

}
