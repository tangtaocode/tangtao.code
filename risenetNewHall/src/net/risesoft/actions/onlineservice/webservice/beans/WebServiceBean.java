package net.risesoft.actions.onlineservice.webservice.beans;

import java.io.Serializable;

/**
 * WebService 值对象
 * 
 * @author Administrator
 * 
 */
public class WebServiceBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6550783899626770167L;

	private String name; // 姓名
	private String category; // 个人资质
	private String certId; // 证书号码
	private String validDate; // 有效期
	private String idCard;// 身份证号码
	private String mobile;//手机
	private String change_cause;//专业
	private String corp_name;//企业名称
	private String alt_qual_lv;//资质等级

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCertId() {
		return certId;
	}

	public void setCertId(String certId) {
		this.certId = certId;
	}

	public String getValidDate() {
		return validDate;
	}

	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCorp_name() {
		return corp_name;
	}

	public void setCorp_name(String corp_name) {
		this.corp_name = corp_name;
	}

	public String getAlt_qual_lv() {
		return alt_qual_lv;
	}

	public void setAlt_qual_lv(String alt_qual_lv) {
		this.alt_qual_lv = alt_qual_lv;
	}

	public String getChange_cause() {
		return change_cause;
	}

	public void setChange_cause(String change_cause) {
		this.change_cause = change_cause;
	}

}
