package net.risesoft.actions.onlineservice.webservice.beans;

import java.io.Serializable;

/**
 * 材料共享（直接发包审批决定书文号）WebService访问返回值
 * @author HJL
 * @date 2013-10-12
 *
 */
public class DecisionWebServiceBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 572344033031533152L;
	
	private String wjbt;//文件标题
	private String sqdw;//申请单位
	private String bjsj;//办结时间
	
	
	public String getWjbt() {
		return wjbt;
	}
	public void setWjbt(String wjbt) {
		this.wjbt = wjbt;
	}
	public String getSqdw() {
		return sqdw;
	}
	public void setSqdw(String sqdw) {
		this.sqdw = sqdw;
	}
	public String getBjsj() {
		return bjsj;
	}
	public void setBjsj(String bjsj) {
		this.bjsj = bjsj;
	}
}
