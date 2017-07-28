package net.risesoft.approve.entity.jpa.gdbs;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 受理信息实体类
 * @author shenqiang
 *
 */
@Entity
@Table(name="EX_GDBS_SL")//申办数据表
public class ShouliProcess implements Serializable{

	private static final long serialVersionUID = 6575634241771614695L;
	
	@Column(name = "SBLSH")
	private String sblsh;//申办流水号
	
	
	@Column(name = "SBLSH_SHORT")
	private String sblshShort;//申办流水号
	
	@Column(name = "SXBM")
	private String sxbm;//事项编码
	
	@Column(name = "SXBM_SHORT")
	private String sxbmShort;//事项编码
	
	@Id
	@Column(name = "YWLSH")
	private String ywlsh;//业务流水号
	
	@Column(name = "YWCXMM")
	private String ywcxmm;//业务查询密码
	
	@Column(name = "CKCLBM")
	private String ckclbm;//出库材料密码
	
	@Column(name = "SLBMMC")
	private String slbmmc;//受理部门名称
	
	@Column(name = "SLBMZZJGDM")
	private String slbmzzjgdm;//受理部门组织机构代码
	
	@Column(name = "XZQHDM")
	private String xzqhdm;//行政区划代码
	
	@Column(name = "XZQHMC")
	private String xzqhmc;//行政区划名称
	
	@Column(name = "BLRXM")
	private String blrxm;//办理人姓名
	
	@Column(name = "BLRGH")
	private String blrgh;//办理人工号
	
	@Column(name = "SLZTDM")
	private String slztdm;//受理状态代码 1受理 2不受理 3补齐补正材料 4不予受理
	
	@Column(name = "BSLYY")
	private String bslyy;//不受理原因
	
	@Column(name = "BJCKMC")
	private String bjckmc;//办件窗口名称
	
	@Column(name = "SLHZH")
	private String slhzh;//受理回执号
	
	@Column(name = "SLSJ")
	private Date slsj;
	
	@Column(name = "SXQXBM")
	private String sxqxbm;//事项情形编码
	
	@Column(name = "BJCKBM")
	private String bjckbm;//办件窗口编码
	
	@Column(name = "BJDTBM")
	private String bjdtbm;//办件大厅编码
	
	@Column(name = "BMDTMC")
	private String bmdtmc;//实际办件所在大厅的大厅名称
	
	@Column(name = "GXDXZQHDM")
	private String gxdxzqhdm;//管辖地行政区划代码
	
	@Column(name = "BZ")
	private String bz;//备注
	
	@Column(name = "F_XZQHDM")
	private String f_xzqhdw;//分发至下级部门的行政区划代码
	
	@Column(name = "PROJECT_CODE")
	private String projectCode;//投资项目统一编码
	
	@Column(name = "STATUS")
	private String 	status;//投资项目统一编码
	
	/*数据版本号默认为1，如果报送数据后发生改变需要重报（误操作或者数据修改）的，以2、3、4……每次加1的方式填写版本号；前置机方式对接时需要使*/
	@Column(name = "VERSION")
	private String version;
	
	/*是否已接收成功，成功：1前置机方式对接时需要使用。*/
	@Column(name = "REC_FLAG")
	private int rec_flag;

	@Column(name = "INSERTTIME")
	private Date inserttime;//入库时间
	
	@Column(name = "RESPONSETIME")
	private Date responsetime;//接口调用成功返回时间
	
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getYwlsh() {
		return ywlsh;
	}
	public void setYwlsh(String ywlsh) {
		this.ywlsh = ywlsh;
	}
	public String getYwcxmm() {
		return ywcxmm;
	}
	public void setYwcxmm(String ywcxmm) {
		this.ywcxmm = ywcxmm;
	}
	public String getCkclbm() {
		return ckclbm;
	}
	public void setCkclbm(String ckclbm) {
		this.ckclbm = ckclbm;
	}
	public String getSlbmmc() {
		return slbmmc;
	}
	public void setSlbmmc(String slbmmc) {
		this.slbmmc = slbmmc;
	}
	public String getSlbmzzjgdm() {
		return slbmzzjgdm;
	}
	public void setSlbmzzjgdm(String slbmzzjgdm) {
		this.slbmzzjgdm = slbmzzjgdm;
	}
	public String getXzqhdm() {
		return xzqhdm;
	}
	public void setXzqhdm(String xzqhdm) {
		this.xzqhdm = xzqhdm;
	}
	public String getXzqhmc() {
		return xzqhmc;
	}
	public void setXzqhmc(String xzqhmc) {
		this.xzqhmc = xzqhmc;
	}
	public String getBlrxm() {
		return blrxm;
	}
	public void setBlrxm(String blrxm) {
		this.blrxm = blrxm;
	}
	public String getBlrgh() {
		return blrgh;
	}
	public void setBlrgh(String blrgh) {
		this.blrgh = blrgh;
	}
	public String getSlztdm() {
		return slztdm;
	}
	public void setSlztdm(String slztdm) {
		this.slztdm = slztdm;
	}
	public String getBslyy() {
		return bslyy;
	}
	public void setBslyy(String bslyy) {
		this.bslyy = bslyy;
	}
	public String getSlhzh() {
		return slhzh;
	}
	public void setSlhzh(String slhzh) {
		this.slhzh = slhzh;
	}
	public Date getSlsj() {
		return slsj;
	}
	public void setSlsj(Date slsj) {
		this.slsj = slsj;
	}
	public String getSxqxbm() {
		return sxqxbm;
	}
	public void setSxqxbm(String sxqxbm) {
		this.sxqxbm = sxqxbm;
	}

	
	public String getSblshShort() {
		return sblshShort;
	}
	public void setSblshShort(String sblshShort) {
		this.sblshShort = sblshShort;
	}
	public String getSxbmShort() {
		return sxbmShort;
	}
	public void setSxbmShort(String sxbmShort) {
		this.sxbmShort = sxbmShort;
	}
	public String getBjckmc() {
		return bjckmc;
	}
	public void setBjckmc(String bjckmc) {
		this.bjckmc = bjckmc;
	}
	public String getBjckbm() {
		return bjckbm;
	}
	public void setBjckbm(String bjckbm) {
		this.bjckbm = bjckbm;
	}
	public String getBjdtbm() {
		return bjdtbm;
	}
	public void setBjdtbm(String bjdtbm) {
		this.bjdtbm = bjdtbm;
	}
	public String getBmdtmc() {
		return bmdtmc;
	}
	public void setBmdtmc(String bmdtmc) {
		this.bmdtmc = bmdtmc;
	}
	public String getGxdxzqhdm() {
		return gxdxzqhdm;
	}
	public void setGxdxzqhdm(String gxdxzqhdm) {
		this.gxdxzqhdm = gxdxzqhdm;
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
