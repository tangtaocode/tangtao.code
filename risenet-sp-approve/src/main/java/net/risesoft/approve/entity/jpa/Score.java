package net.risesoft.approve.entity.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="RC_KPSJ")
public class Score implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String scoreguid;//主键GUID
	private String userguid;//用户GUID
	private String name;//名字
	private String department;//部门
	private String enrollnumber;//编号
	private String time;//时间
	private int nwzjqk;//NWZJQK 内务整洁情况分数
	private int yxltksj;//YXLTKSJ 游戏、聊天、看手机分数
	private int cdzt;//CDZT迟到、早退
	private int mdgp;//MDGP没带工牌
	private int zztfx;//ZZTFX着装，头发，鞋
	private int pyq;//PYQ评议器
	private int lmwmyy;//LMWMYY礼貌文明用语
	private int rewards;//奖励
	private int punishment;//惩罚
	private int complaints;//投诉
	private int praise;//表扬
	private String remarks;//REMARKS备注
	@Id
 	@Column(name = "GUID", length = 38,unique=true, nullable = false)
	public String getScoreguid() {
		return scoreguid;
	}
	public void setScoreguid(String scoreguid) {
		this.scoreguid = scoreguid;
	}
	
	@Column(name="USERGUID")
	public String getUserguid() {
		return userguid;
	}
	public void setUserguid(String userguid) {
		this.userguid = userguid;
	}
	@Column(name="NAME")
	public String getName() {
		return name;
	}
	@Column(name="ENROLLNUMBER")
	public String getEnrollnumber() {
		return enrollnumber;
	}
	public void setEnrollnumber(String enrollnumber) {
		this.enrollnumber = enrollnumber;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name="DEPARTMENT")
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	@Column(name="NWZJQK")
	public int getNwzjqk() {
		return nwzjqk;
	}
	public void setNwzjqk(int nwzjqk) {
		this.nwzjqk = nwzjqk;
	}
	@Column(name="YXLTKSJ")
	public int getYxltksj() {
		return yxltksj;
	}
	public void setYxltksj(int yxltksj) {
		this.yxltksj = yxltksj;
	}
	@Column(name="CDZT")
	public int getCdzt() {
		return cdzt;
	}
	public void setCdzt(int cdzt) {
		this.cdzt = cdzt;
	}
	@Column(name="MDGP")
	public int getMdgp() {
		return mdgp;
	}
	public void setMdgp(int mdgp) {
		this.mdgp = mdgp;
	}
	@Column(name="ZZTFX")
	public int getZztfx() {
		return zztfx;
	}
	public void setZztfx(int zztfx) {
		this.zztfx = zztfx;
	}
	@Column(name="PYQ")
	public int getPyq() {
		return pyq;
	}
	public void setPyq(int pyq) {
		this.pyq = pyq;
	}
	@Column(name="LMWMYY")
	public int getLmwmyy() {
		return lmwmyy;
	}
	public void setLmwmyy(int lmwmyy) {
		this.lmwmyy = lmwmyy;
	}

	@Column(name="REMARKS")
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	@Column(name="TIME")
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Column(name="REWARDS")
	public int getRewards() {
		return rewards;
	}
	public void setRewards(int rewards) {
		this.rewards = rewards;
	}
	@Column(name="PUNISHMENT")
	public int getPunishment() {
		return punishment;
	}
	public void setPunishment(int punishment) {
		this.punishment = punishment;
	}
	@Column(name="COMPLAINTS")
	public int getComplaints() {
		return complaints;
	}
	public void setComplaints(int complaints) {
		this.complaints = complaints;
	}
	@Column(name="PRAISE")
	public int getPraise() {
		return praise;
	}
	public void setPraise(int praise) {
		this.praise = praise;
	}
	
	
	
	
	
	
	
}
