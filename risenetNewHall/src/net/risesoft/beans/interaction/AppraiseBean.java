package net.risesoft.beans.interaction;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SPM_SENATE")
public class AppraiseBean implements Serializable{
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 4185606534897041524L;
	private String senateguid;  //主键，评议ID
	private String declaresn;  //审批业务编号
	private String satisfaction;  //评议，1满意，2一般，3不满意"
	private Date senatedatetime;  //评议时间
	private String problem;  //建议
	private String yawp;  //不满意原因，1态度不好，2业务不熟，3时间太长，4其他
	private String yawpdescribe;  //其他原因描述
	private String statedescription;  //事项办理状态
	private String zhengjiandaima;  //证件代码
	private String issenate;  //是否评议，默认0未评议，1已评议
	private String type;  //评价类型:1，窗口业务评价；2，窗口咨询评价；3，网上评价；4，短信评价；5，其他审批系统的评价
	@Id
	public String getSenateguid() {
		return senateguid;
	}
	public void setSenateguid(String senateguid) {
		this.senateguid = senateguid;
	}
	@Column
	public String getDeclaresn() {
		return declaresn;
	}
	public void setDeclaresn(String declaresn) {
		this.declaresn = declaresn;
	}
	
	
	@Column
	public String getSatisfaction() {
		return satisfaction;
	}
	public void setSatisfaction(String satisfaction) {
		this.satisfaction = satisfaction;
	}
	
	@Column
	public Date getSenatedatetime() {
		return senatedatetime;
	}
	public void setSenatedatetime(Date senatedatetime) {
		this.senatedatetime = senatedatetime;
	}
	
	@Column
	public String getProblem() {
		return problem;
	}
	public void setProblem(String problem) {
		this.problem = problem;
	}
	
	@Column
	public String getYawp() {
		return yawp;
	}
	public void setYawp(String yawp) {
		this.yawp = yawp;
	}
	@Column
	public String getYawpdescribe() {
		return yawpdescribe;
	}
	public void setYawpdescribe(String yawpdescribe) {
		this.yawpdescribe = yawpdescribe;
	}
	@Column
	public String getStatedescription() {
		return statedescription;
	}
	public void setStatedescription(String statedescription) {
		this.statedescription = statedescription;
	}
	@Column
	public String getZhengjiandaima() {
		return zhengjiandaima;
	}
	public void setZhengjiandaima(String zhengjiandaima) {
		this.zhengjiandaima = zhengjiandaima;
	}
	@Column
	public String getIssenate() {
		return issenate;
	}
	public void setIssenate(String issenate) {
		this.issenate = issenate;
	}
	@Column
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	
}
