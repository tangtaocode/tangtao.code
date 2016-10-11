package net.risesoft.beans.govpublic;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 
  * @ClassName: WebStarRrating
  * @Description: 网站星级评价
  * @author Comsys-zhangkun
  * @date Jul 1, 2013 2:53:19 PM
  *
 */
@Entity
@Table(name="T_OUT_WEBSTARRRATING")
public class WebStarRrating implements Serializable{
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = -6479050515251658630L;
	private String guid;  //主键
	private String ip;  //访问IP
	private String appraise;  //评价，总共5颗星，评价等级由1到5递增
	private Date appraisetime;  //评价时间
	@Id
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	@Column
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	@Column
	public String getAppraise() {
		return appraise;
	}
	public void setAppraise(String appraise) {
		this.appraise = appraise;
	}
	@Column
	public Date getAppraisetime() {
		return appraisetime;
	}
	public void setAppraisetime(Date appraisetime) {
		this.appraisetime = appraisetime;
	}

}
