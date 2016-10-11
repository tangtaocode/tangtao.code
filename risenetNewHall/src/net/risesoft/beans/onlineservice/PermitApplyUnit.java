package net.risesoft.beans.onlineservice;

import java.io.Serializable;

/**
 * @description 施工许可表单申请单位（不定长部分）
 * @author HJL
 *
 */
public class PermitApplyUnit implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1405808614052718924L;
	private String sqdw;//申请单位
	private String fddbr;//法定代表人
	private String sfzh;//身份证号
	
	public String getSqdw() {
		return sqdw;
	}

	public void setSqdw(String sqdw) {
		this.sqdw = sqdw;
	}

	public String getFddbr() {
		return fddbr;
	}

	public void setFddbr(String fddbr) {
		this.fddbr = fddbr;
	}

	public String getSfzh() {
		return sfzh;
	}

	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
	}
}
