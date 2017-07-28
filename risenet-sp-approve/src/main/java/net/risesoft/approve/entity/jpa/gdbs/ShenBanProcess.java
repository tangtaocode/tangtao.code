package net.risesoft.approve.entity.jpa.gdbs;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.util.StringUtils;

/**
 * 申办信息实体类
 * @author shenqiang
 *
 */
@Entity
@Table(name="EX_GDBS_SB")//申办数据表
public class ShenBanProcess implements Serializable{
	
	private static final long serialVersionUID = 5723361704561048667L;
	
	
	@Column(name = "SBLSH")
	private String sblsh;//申办流水号
	
	@Id
	@Column(name = "SBLSH_SHORT")
	private String sblshShort;//省统一申办流水号
	
	@Column(name = "SXBM")
	private String sxbm;//事项编码
	
	@Column(name = "SXBM_SHORT")
	private String sxbmShort;//事项编码
	
	@Column(name = "SXMC")
	private String sxmc;//事项名称
	
	@Column(name = "SXQXBM")
	private String sxqxbm;//事项情形编码
	
	@Column(name = "FSXBM")
	private String fsxbm;//父事项编码
	
	@Column(name = "FSXMC")
	private String fsxmc;//父事项名称
	
	@Column(name = "SQRLX")
	private String sqrlx;//申请人类型 1个人  2企业 3其他
	
	@Column(name = "SQRMC")
	private String sqrmc;//申请人名称
	
	@Column(name = "SQRZJLX")
	private String sqrzjlx;//申请人证件类型
	
	@Column(name = "SQRZJHM")
	private String sqrzjhm;//申请人证件号码
	
	@Column(name = "LXRXM")
	private String lxrxm;//联系人姓名
	
	@Column(name = "LXRZJLX")
	private String lxrzjlx;//联系人证件类型
	
	@Column(name = "LXRSFZJHM")
	private String lxrsfzjhm;//联系人身份证件号码
	
	@Column(name = "LXRSJ")
	private String lxrsj;//联系人时间
	
	@Column(name = "LXRYX")
	private String lxryx;//联系人邮箱
	
	@Column(name = "SBXMMC")
	private String sbxmmc;//申办项目名称
	
	@Column(name = "SBCLQD")
	private String sbclqd;//申报材料清单
	
	@Column(name = "TJFS")
	private String tjfs;//提交方式
	
	@Column(name = "SBHZH")
	private String sbhzh;//申办回执号
	
	@Column(name = "SBSJ")
	private Date sbsj;//申报时间
	
	@Column(name = "SBJTWD")
	private String sbjtwd;//申办具体网店
	
	@Column(name = "XZQHDM")
	private String xzqhdm;//业务发生所在地区编号
	
	@Column(name = "YSBLSH")
	private String ysblsh;//原申办流水号
	
	@Column(name = "BZ")
	private String bz;//备注
	
	@Column(name = "F_XZQHDM")
	private String f_xzqhdw;//分发至下级部门的行政区划代码

	@Column(name = "USERIDCODE")
	private String userIDcode;//网厅统一身份认证平台账号，没有对接该平台则不填。
	
	@Column(name = "PROJECT_CODE")
	private String projectCode;//投资项目统一编码
	/*办事人网上申办渠道0：PC 端  1：手机版客户端 2：手机WAP 厅 3：微信公众号 4：支付宝服务窗 9：其他*/
	
	@Column(name = "WSSBQD")
	private String wssbqd;
	
	
	//0：自取出证结果，1：邮寄出证结果
	@Column(name = "OBTAIN_PAPER_WAY")
	private String obtain_paper_way;
	
	//0：窗口递交；1：邮递递交
	@Column(name = "OBTAIN_DELIVER_WAY")
	private String obtain_deliver_way;
	
	/*数据版本号默认为1，如果报送数据后发生改变需要重报（误操作或者数据修改）的，以2、3、4……每次加1的方式填写版本号；前置机方式对接时需要使*/
	@Column(name = "VERSION")
	private String version;
	
	/*是否已接收成功，成功：1前置机方式对接时需要使用。*/
	@Column(name = "REC_FLAG")
	private int rec_flag;
	
	/*申报业务信息组成的XML字符串。前置机方式对接时需要使用*/
	@Column(name = "XML_DATA")
	private String xml_data;
	
	@Column(name = "D_ZZJGDM")
	private String d_zzjgdm;//组织机构代码
	
	@Column(name = "INSERTTIME")
	private Date inserttime;//入库时间
	
	@Column(name = "RESPONSETIME")
	private Date responsetime;//接口调用成功返回时间
	
	
	
	public String getSxbmShort() {
		return sxbmShort;
	}
	public void setSxbmShort(String sxbmShort) {
		this.sxbmShort = sxbmShort;
	}
	public String getSblsh() {
		return sblsh;
	}
	public void setSblshShort(String sblshShort) {
		this.sblshShort = sblshShort;
	}
	public String getSblshShort() {
		return sblshShort;
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
	public String getSxmc() {
		return sxmc;
	}
	public void setSxmc(String sxmc) {
		this.sxmc = sxmc;
	}
	public String getSxqxbm() {
		return sxqxbm;
	}
	public void setSxqxbm(String sxqxbm) {
		this.sxqxbm = sxqxbm;
	}
	public String getFsxbm() {
		return fsxbm;
	}
	public void setFsxbm(String fsxbm) {
		this.fsxbm = fsxbm;
	}
	public String getFsxmc() {
		return fsxmc;
	}
	public void setFsxmc(String fsxmc) {
		this.fsxmc = fsxmc;
	}
	public String getSqrlx() {
		return sqrlx;
	}
	public void setSqrlx(String sqrlx) {
		this.sqrlx = sqrlx;
	}
	public String getSqrmc() {
		return sqrmc;
	}
	public void setSqrmc(String sqrmc) {
		this.sqrmc = sqrmc;
	}
	public String getSqrzjlx() {
		return sqrzjlx;
	}
	public void setSqrzjlx(String sqrzjlx) {
		if(StringUtils.isEmpty(sqrzjlx)){
			sqrzjlx = "1";
		}
		this.sqrzjlx = sqrzjlx;
	}
	public String getSqrzjhm() {
		return sqrzjhm;
	}
	public void setSqrzjhm(String sqrzjhm) {
		this.sqrzjhm = sqrzjhm;
	}
	public String getLxrxm() {
		return lxrxm;
	}
	public void setLxrxm(String lxrxm) {
		this.lxrxm = lxrxm;
	}
	public String getLxrzjlx() {
		return lxrzjlx;
	}
	public void setLxrzjlx(String lxrzjlx) {
		this.lxrzjlx = lxrzjlx;
	}
	public String getLxrsfzjhm() {
		return lxrsfzjhm;
	}
	public void setLxrsfzjhm(String lxrsfzjhm) {
		this.lxrsfzjhm = lxrsfzjhm;
	}
	public String getLxrsj() {
		return lxrsj;
	}
	public void setLxrsj(String lxrsj) {
		this.lxrsj = lxrsj;
	}
	public String getLxryx() {
		return lxryx;
	}
	public void setLxryx(String lxryx) {
		this.lxryx = lxryx;
	}
	public String getSbxmmc() {
		return sbxmmc;
	}
	public void setSbxmmc(String sbxmmc) {
		this.sbxmmc = sbxmmc;
	}
	public String getSbclqd() {
		return sbclqd;
	}
	public void setSbclqd(String sbclqd) {
		this.sbclqd = sbclqd;
	}
	public String getTjfs() {
		return tjfs;
	}
	public void setTjfs(String tjfs) {
		this.tjfs = tjfs;
	}
	public String getSbhzh() {
		return sbhzh;
	}
	public void setSbhzh(String sbhzh) {
		this.sbhzh = sbhzh;
	}
	public Date getSbsj() {
		return sbsj;
	}
	public void setSbsj(Date sbsj) {
		this.sbsj = sbsj;
	}
	public String getSbjtwd() {
		return sbjtwd;
	}
	public void setSbjtwd(String sbjtwd) {
		this.sbjtwd = sbjtwd;
	}
	public String getXzqhdm() {
		return xzqhdm;
	}
	public void setXzqhdm(String xzqhdm) {
		this.xzqhdm = xzqhdm;
	}
	public String getYsblsh() {
		return ysblsh;
	}
	public void setYsblsh(String ysblsh) {
		this.ysblsh = ysblsh;
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
	public String getUserIDcode() {
		return userIDcode;
	}
	public void setUserIDcode(String userIDcode) {
		this.userIDcode = userIDcode;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getWssbqd() {
		return wssbqd;
	}
	public void setWssbqd(String wssbqd) {
		this.wssbqd = wssbqd;
	}
	public String getObtain_paper_way() {
		return obtain_paper_way;
	}
	public void setObtain_paper_way(String obtain_paper_way) {
		this.obtain_paper_way = obtain_paper_way;
	}
	public String getObtain_deliver_way() {
		return obtain_deliver_way;
	}
	public void setObtain_deliver_way(String obtain_deliver_way) {
		this.obtain_deliver_way = obtain_deliver_way;
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
	public String getXml_data() {
		return xml_data;
	}
	public void setXml_data(String xml_data) {
		this.xml_data = xml_data;
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
