package net.risesoft.approve.entity.jpa.gdbs;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
/**
 * 补齐补正告知
 * @author huangtianci
 *
 */
@Entity
@Table(name="EX_GDBS_BZGZ")
public class BuZhengGaoZhiProcess implements Serializable{

	private static final long serialVersionUID = -3907799245402686893L;
	@EmbeddedId
	private BuZhengGaoZhiUnits id;
	public BuZhengGaoZhiUnits getId() {
		return id;
	}
	public void setId(BuZhengGaoZhiUnits id) {
		this.id = id;
	}
	@Column(name = "SBLSH")
	private String sblsh;//申办流水号
	
	@Column(name = "SXBM")
	private String sxbm;//事项编码
	@Column(name = "SXBM_SHORT")
	private String sxbm_short;//事项编码简码
	@Column(name = "SXQXBM")
	private String sxqxbm;//事项情形
	@Column(name = "BZGZFCRXM")
	private String bzgzfcrxm;//补正告知发出人姓名
	@Column(name = "BZGZYY")
	private String bzgzyy;//补正告知原因
	@Column(name = "BZCLQD")
	private String bzclqd;//补正材料清单
	@Column(name = "BQBZCLBM")
	private String bjgzclbm;//补交告知材料编码
	@Column(name = "BZGZSJ")
	private Date bzgzsj;//补正告知时间
	@Column(name = "XZQHDM")
	private String xzqhdm;//行政区划代码
	@Column(name = "BZ")
	private String bz;//备注
	@Column(name = "F_XZQHDM")
	private String f_xzqhdm;//分发至下级部门的行政区划代码
	@Column(name = "PROJECT_CODE")
	private String projectCode;//投资项目统一编码
	
	/*是否已接收成功，成功：1前置机方式对接时需要使用。*/
	@Column(name = "REC_FLAG")
	private int rec_flag;
	/*申报业务信息组成的XML字符串。前置机方式对接时需要使用*/
	@Column(name = "D_ZZJGDM")
	private String d_zzjgdm;//组织机构代码
	

	@Column(name = "INSERTTIME")
	private Date inserttime;//入库时间
	@Column(name = "RESPONSETIME")
	private Date responsetime;//接口调用成功返回时间
/*	@Column(name = "NID")
	private String nid;//
	@Id
	@Column(name = "GUID")
	private String guid;//主键
*/	
	
	/*public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getNid() {
		return nid;
	}
	public void setNid(String nid) {
		this.nid = nid;
	}*/
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
	public String getBzgzfcrxm() {
		return bzgzfcrxm;
	}
	public void setBzgzfcrxm(String bzgzfcrxm) {
		this.bzgzfcrxm = bzgzfcrxm;
	}
	public String getBzgzyy() {
		return bzgzyy;
	}
	public void setBzgzyy(String bzgzyy) {
		this.bzgzyy = bzgzyy;
	}
	public String getBzclqd() {
		return bzclqd;
	}
	public void setBzclqd(String bzclqd) {
		this.bzclqd = bzclqd;
	}
	public String getBjgzclbm() {
		return bjgzclbm;
	}
	public void setBjgzclbm(String bjgzclbm) {
		this.bjgzclbm = bjgzclbm;
	}
	public Date getBzgzsj() {
		return bzgzsj;
	}
	public void setBzgzsj(Date bzgzsj) {
		this.bzgzsj = bzgzsj;
	}
	public String getXzqhdm() {
		return xzqhdm;
	}
	public void setXzqhdm(String xzqhdm) {
		this.xzqhdm = xzqhdm;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getF_xzqhdm() {
		return f_xzqhdm;
	}
	public void setF_xzqhdm(String f_xzqhdm) {
		this.f_xzqhdm = f_xzqhdm;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
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

	public String getSxbm_short() {
		return sxbm_short;
	}
	public void setSxbm_short(String sxbm_short) {
		this.sxbm_short = sxbm_short;
	}
	public String getSxqxbm() {
		return sxqxbm;
	}
	public void setSxqxbm(String sxqxbm) {
		this.sxqxbm = sxqxbm;
	}
	
}
