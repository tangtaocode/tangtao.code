package net.risesoft.beans.civilbankroll;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="MSXM_TYPE_PROJECT")
public class CivilProjectType implements Serializable{
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = -1706500760911036154L;
	private String id;  //guid
	private String typeguid;  //分类guid
	private String name;  //项目名称
	private String content;  //项目内容
	private String beizhu;  //备注
	private String createperson;  //创建人
	private Date createtime;  //创建时间
	private String status;  //是否有效
	private Integer orderno;  //排序号
	private String onlinelists;  //网上申报需提交材料
	private String windowlists;  //窗口受理需提交材料
	private String year;  //年份
	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column
	public String getTypeguid() {
		return typeguid;
	}
	public void setTypeguid(String typeguid) {
		this.typeguid = typeguid;
	}
	@Column
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column
	public String getBeizhu() {
		return beizhu;
	}
	public void setBeizhu(String beizhu) {
		this.beizhu = beizhu;
	}
	@Column
	public String getCreateperson() {
		return createperson;
	}
	public void setCreateperson(String createperson) {
		this.createperson = createperson;
	}
	@Column
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	@Column
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column
	public Integer getOrderno() {
		return orderno;
	}
	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}
	@Column
	public String getOnlinelists() {
		return onlinelists;
	}
	public void setOnlinelists(String onlinelists) {
		this.onlinelists = onlinelists;
	}
	@Column
	public String getWindowlists() {
		return windowlists;
	}
	public void setWindowlists(String windowlists) {
		this.windowlists = windowlists;
	}
	@Column
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}

}
