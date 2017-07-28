/**
 * @Project Name:risenet-sp-approve
 * @File Name: StuffdataDel.java
 * @Package Name: net.risesoft.approve.entity.jpa
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2016,RiseSoft  All Rights Reserved.
 * @date 2016年4月1日 上午11:10:36
 */
package net.risesoft.approve.entity.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName: StuffdataDel.java
 * @Description: TODO
 *
 * @author chenbingni
 * @date 2016年4月1日 上午11:10:36
 * @version 
 * @since JDK 1.7
 */
@Entity
@Table(name = "SS_STUFFDATA_DEL")
public class StuffdataDel implements Serializable {

	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 1L;
	private String guid;//主键
	private String stuffdefineid;//材料定义类型主键
	private String stuffdataname;//材料数据名称，如张三的身份证
	private String ownertype;//所属主体类型，1个人，2企业，3机关事业单位
	private String ownerguid;//主体主键
	private String state;//材料数据状态，1待认证、2认证有效、3无效
	private Date limitbegin;//有效期开始日期
	private Date limitend;//有效期结束日期
	private String limitforever;//永久有效，1表示永久有效，0和空表示非永久有效
	private Date gxtime;//更新日期
	private Integer tabindex;//排序号
	private String certifyperson;//认证人
	private Date certifytime;//认证时间
	private String remark;//无效原因
	private String operater;//删除操作人
	private Date deltime;//删除时间
	
	public StuffdataDel() {
		
	}
	
	public StuffdataDel(Stuffdata data, String person, Date time) {
		this.guid = data.getGuid();
		this.stuffdefineid = data.getStuffdefineid();
		this.stuffdataname = data.getStuffdataname();
		this.ownertype = data.getOwnertype();
		this.ownerguid = data.getOwnerguid();
		this.state = data.getState();
		this.limitbegin = data.getLimitbegin();
		this.limitend = data.getLimitend();
		this.limitforever = data.getLimitforever();
		this.gxtime = data.getGxtime();
		this.tabindex = data.getTabindex();
		this.certifyperson = data.getCertifyperson();
		this.certifytime = data.getCertifytime();
		this.remark = data.getRemark();
		this.operater = person;
		this.deltime = time;
	}
	
	@Id
	@Column(name="GUID",length=38,nullable=false)
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	@Column(name="STUFFDEFINEID",length=38,nullable=false)
	public String getStuffdefineid() {
		return stuffdefineid;
	}
	public void setStuffdefineid(String stuffdefineid) {
		this.stuffdefineid = stuffdefineid;
	}
	@Column
	public String getStuffdataname() {
		return stuffdataname;
	}
	public void setStuffdataname(String stuffdataname) {
		this.stuffdataname = stuffdataname;
	}
	@Column
	public String getOwnertype() {
		return ownertype;
	}
	public void setOwnertype(String ownertype) {
		this.ownertype = ownertype;
	}
	@Column
	public String getOwnerguid() {
		return ownerguid;
	}
	public void setOwnerguid(String ownerguid) {
		this.ownerguid = ownerguid;
	}
	@Column
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Column
	public Date getLimitbegin() {
		return limitbegin;
	}
	public void setLimitbegin(Date limitbegin) {
		this.limitbegin = limitbegin;
	}
	@Column
	public Date getLimitend() {
		return limitend;
	}
	public void setLimitend(Date limitend) {
		this.limitend = limitend;
	}
	@Column
	public String getLimitforever() {
		return limitforever;
	}
	public void setLimitforever(String limitforever) {
		this.limitforever = limitforever;
	}
	@Column
	public Date getGxtime() {
		return gxtime;
	}
	public void setGxtime(Date gxtime) {
		this.gxtime = gxtime;
	}
	@Column
	public Integer getTabindex() {
		return tabindex;
	}
	public void setTabindex(Integer tabindex) {
		this.tabindex = tabindex;
	}
	@Column
	public String getCertifyperson() {
		return certifyperson;
	}
	public void setCertifyperson(String certifyperson) {
		this.certifyperson = certifyperson;
	}
	@Column
	public Date getCertifytime() {
		return certifytime;
	}
	public void setCertifytime(Date certifytime) {
		this.certifytime = certifytime;
	}
	@Column
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column
	public String getOperater() {
		return operater;
	}
	public void setOperater(String operater) {
		this.operater = operater;
	}
	@Column
	public Date getDeltime() {
		return deltime;
	}
	public void setDeltime(Date deltime) {
		this.deltime = deltime;
	}

}

