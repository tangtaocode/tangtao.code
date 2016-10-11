/**
 * @Project Name:LGOneHome
 * @File Name: JianchaModel.java
 * @Package Name: net.risesoft.model
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2015,RiseSoft  All Rights Reserved.
 * @date 2015年12月9日 下午4:09:59
 */

package net.risesoft.model;

/**
 * @ClassName: JianchaModel.java
 * @Description: 检查数据情况模型
 *
 * @author tt
 * @date 2015年12月9日 下午4:09:59
 * @version 
 * @since JDK 1.6
 */
public class JianchaModel {
	private String guid;
	private String yxtywlsh;//原系统业务流水号
	private int syts;//剩余天数
	private String sfbj;//是否办结
	private String state;//是否暂停
	private String spsxmc;//审批事项名称
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getYxtywlsh() {
		return yxtywlsh;
	}
	public void setYxtywlsh(String yxtywlsh) {
		this.yxtywlsh = yxtywlsh;
	}
	public int getSyts() {
		return syts;
	}
	public void setSyts(int syts) {
		this.syts = syts;
	}
	public String getSfbj() {
		return sfbj;
	}
	public void setSfbj(String sfbj) {
		this.sfbj = sfbj;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getSpsxmc() {
		return spsxmc;
	}
	public void setSpsxmc(String spsxmc) {
		this.spsxmc = spsxmc;
	}
	
}
