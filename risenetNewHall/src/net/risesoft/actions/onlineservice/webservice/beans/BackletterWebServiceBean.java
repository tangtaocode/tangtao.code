package net.risesoft.actions.onlineservice.webservice.beans;

import java.io.Serializable;

/**
 * @description 获取保函信息webServices接口访问返回值
 * @author HJL
 * @date 2013-10-09
 */
public class BackletterWebServiceBean  implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8350650732072249926L;
	
	private String gcmc;//工程名称
	private String zfbhbh;//支付保函编号
	private String yzmc;//业主名称
	private String cbsmc;//承包商名称
	private String rq;//日期
	private String lybhbh;//履约保函编号
	
	
	
	public String getGcmc() {
		return gcmc;
	}
	public void setGcmc(String gcmc) {
		this.gcmc = gcmc;
	}
	public String getZfbhbh() {
		return zfbhbh;
	}
	public void setZfbhbh(String zfbhbh) {
		this.zfbhbh = zfbhbh;
	}
	public String getYzmc() {
		return yzmc;
	}
	public void setYzmc(String yzmc) {
		this.yzmc = yzmc;
	}
	public String getCbsmc() {
		return cbsmc;
	}
	public void setCbsmc(String cbsmc) {
		this.cbsmc = cbsmc;
	}
	public String getRq() {
		return rq;
	}
	public void setRq(String rq) {
		this.rq = rq;
	}
	public String getLybhbh() {
		return lybhbh;
	}
	public void setLybhbh(String lybhbh) {
		this.lybhbh = lybhbh;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
	
}
