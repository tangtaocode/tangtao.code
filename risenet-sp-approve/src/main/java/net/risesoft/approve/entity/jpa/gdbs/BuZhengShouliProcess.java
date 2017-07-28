package net.risesoft.approve.entity.jpa.gdbs;

import java.io.Serializable;
import java.util.Date;

public class BuZhengShouliProcess implements Serializable{

	private static final long serialVersionUID = 3918710120935077302L;
	private String guid;
	private String SBLSH_SHORT;//市级统一申办流水号
	private String SXBM_SHORT;//深圳市权责清单事项编码
	private String sblsh;//申办流水号
	private String sxbm;//事项编码
	private String bzslblrxm;//补正受理办理人姓名
	private String bzclqd;//补正材料清单
	private Date bzsj;//补正受理时间
	private String xzqhdm;//行政区划代码
	private String bzsljtdd;//补正受理具体地点
	
	private String bz;//备注
	private String f_xzqhdw;//分发至下级部门的行政区划代码
	private String projectCode;//投资项目统一编码
	/*数据版本号默认为1，如果报送数据后发生改变需要重报（误操作或者数据修改）的，以2、3、4……每次加1的方式填写版本号；前置机方式对接时需要使*/
	private String version;
	/*是否已接收成功，成功：1前置机方式对接时需要使用。*/
	private int rec_flag;
	/*申报业务信息组成的XML字符串。前置机方式对接时需要使用*/
	private String d_zzjgdm;//组织机构代码

	private Date inserttime;//入库时间
	private Date responsetime;//接口调用成功返回时间
	
	
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getSblsh() {
		return sblsh;
	}
	public void setSblsh(String sblsh) {
		this.sblsh = sblsh;
	}
	public String getSxbm() {
		return sxbm;
	}
	public void setSxbm(String sxbm) {
		this.sxbm = sxbm;
	}
	public String getBzslblrxm() {
		return bzslblrxm;
	}
	public void setBzslblrxm(String bzslblrxm) {
		this.bzslblrxm = bzslblrxm;
	}
	public String getBzclqd() {
		return bzclqd;
	}
	public void setBzclqd(String bzclqd) {
		this.bzclqd = bzclqd;
	}
	public Date getBzsj() {
		return bzsj;
	}
	public void setBzsj(Date bzsj) {
		this.bzsj = bzsj;
	}
	public String getXzqhdm() {
		return xzqhdm;
	}
	public void setXzqhdm(String xzqhdm) {
		this.xzqhdm = xzqhdm;
	}
	public String getBzsljtdd() {
		return bzsljtdd;
	}
	public void setBzsljtdd(String bzsljtdd) {
		this.bzsljtdd = bzsljtdd;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getF_xzqhdw() {
		return f_xzqhdw;
	}
	public void setF_xzqhdw(String f_xzqhdw) {
		this.f_xzqhdw = f_xzqhdw;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public int getRec_flag() {
		return rec_flag;
	}
	public void setRec_flag(int rec_flag) {
		this.rec_flag = rec_flag;
	}
	public String getD_zzjgdm() {
		return d_zzjgdm;
	}
	public void setD_zzjgdm(String d_zzjgdm) {
		this.d_zzjgdm = d_zzjgdm;
	}
	public Date getInserttime() {
		return inserttime;
	}
	public void setInserttime(Date inserttime) {
		this.inserttime = inserttime;
	}
	public Date getResponsetime() {
		return responsetime;
	}
	public void setResponsetime(Date responsetime) {
		this.responsetime = responsetime;
	}
	
	

}
