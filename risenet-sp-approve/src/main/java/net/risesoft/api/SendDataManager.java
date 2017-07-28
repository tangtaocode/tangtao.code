package net.risesoft.api;

/**
 * 向市接口统一推送数据接口类
 * @author shenqiang
 *
 */
public interface SendDataManager {

	//推送 审批过程数据
	public void sendShenpiguocheng(String sblsh_short);
	
	//推送办结数据
	public void sendBanjie(String sblsh_short);
	
	//推送特别程序数据
	public void sendTebieChengxu(int type,String sblsh_short,String xh);
	
	//推送补交告知数据
	public void sendBujiaogaozhi(String sblsh_short);
}
