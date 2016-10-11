package net.risesoft.beans.bizbankroll;

import java.io.Serializable;
/**
 * @统计分析
 * 
 * @author 张坤
 * @Date 2013-1-24 上午10:50:24
 */
public class Statistical implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1340766360865881587L;
	private String todayReceive;
	private String todayFinish;
	private String monthReceive;
	private String monthFinish;
	private String countReceive;
	private String transactCount;
	private String finishCount;
	public String getTodayReceive() {
		return todayReceive;
	}
	public void setTodayReceive(String todayReceive) {
		this.todayReceive = todayReceive;
	}
	public String getTodayFinish() {
		return todayFinish;
	}
	public void setTodayFinish(String todayFinish) {
		this.todayFinish = todayFinish;
	}
	public String getMonthReceive() {
		return monthReceive;
	}
	public void setMonthReceive(String monthReceive) {
		this.monthReceive = monthReceive;
	}
	public String getMonthFinish() {
		return monthFinish;
	}
	public void setMonthFinish(String monthFinish) {
		this.monthFinish = monthFinish;
	}
	public String getCountReceive() {
		return countReceive;
	}
	public void setCountReceive(String countReceive) {
		this.countReceive = countReceive;
	}
	public String getTransactCount() {
		return transactCount;
	}
	public void setTransactCount(String transactCount) {
		this.transactCount = transactCount;
	}
	public String getFinishCount() {
		return finishCount;
	}
	public void setFinishCount(String finishCount) {
		this.finishCount = finishCount;
	}
	
	
}
