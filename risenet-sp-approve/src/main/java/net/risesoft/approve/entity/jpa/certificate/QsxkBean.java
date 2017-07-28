package net.risesoft.approve.entity.jpa.certificate;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="T_SWJ_QSXK")//取水许可证照
public class QsxkBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -718445388137336067L;
	@Id
	@Column(name = "U_ID", nullable = false)
	private String uId;
	@Column(name = "U_MLBH")
	private String uMlbh;
	@Column(name = "U_LXBH")
	private String uLxbh;
	@Column(name = "U_SBLSH")
	private String uSblsh;
	@Column(name = "U_SXID")
	private String uSxid;
	@Column(name = "U_SXMC")
	private String uSxmc;
	@Column(name = "U_ZZBH")
	private String uZzbh;
	@Column(name = "U_ZTLB")
	private String uZtlb;
	@Column(name = "U_ZTMC")
	private String uZtmc;
	@Column(name = "U_ZTDM")
	private String uZtdm;
	@Column(name = "U_FRXM")
	private String uFrxm;
	@Column(name = "U_FRSFZ")
	private String uFrsfz;
	@Column(name = "U_FZRQ")
	private Date uFzrq;
	@Column(name = "U_SXRQ")
	private Date uSxrq;
	@Column(name = "U_JZRQ")
	private Date uJzrq;
	@Column(name = "U_JGDM")
	private String uJgdm;
	@Column(name = "U_FZJG")
	private String uFzjg;
	@Column(name = "U_XZQH")
	private String uXzqh;
	@Column(name = "U_CA")
	private String uCa;
	@Column(name = "U_ZZZM")
	private byte[] uZzzm;
	@Column(name = "U_BY1")
	private String uBy1;
	@Column(name = "U_BY2")
	private String uBy2;
	@Column(name = "U_ZZZT")
	private String uZzzt;
	
	@Column(name = "NID")
	private Integer nid;
	@Column(name = "ZZBH")
	private String zzbh;
	@Column(name = "QSQRMC")
	private String qsqrmc;
	@Column(name = "FDDBR")
	private String fddbr;
	@Column(name = "QSDD")
	private String qsdd;
	@Column(name = "QSFS")
	private String qsfs;
	@Column(name = "QSL")
	private String qsl;
	@Column(name = "QSYT")
	private String qsyt;
	@Column(name = "SYLX")
	private String sylx;
	@Column(name = "TSDD")
	private String tsdd;
	@Column(name = "TSFS")
	private String tsfs;
	@Column(name = "TSL")
	private String tsl;
	@Column(name = "TSSZ")
	private String tssz;
	@Column(name = "ZZYXQ")
	private String zzyxq;
	@Column(name = "PZRQ")
	private Date pzrq;
	
	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public String getuMlbh() {
		return uMlbh;
	}

	public void setuMlbh(String uMlbh) {
		this.uMlbh = uMlbh;
	}

	public String getuLxbh() {
		return uLxbh;
	}

	public void setuLxbh(String uLxbh) {
		this.uLxbh = uLxbh;
	}

	public String getuSblsh() {
		return uSblsh;
	}

	public void setuSblsh(String uSblsh) {
		this.uSblsh = uSblsh;
	}

	public String getuSxid() {
		return uSxid;
	}

	public void setuSxid(String uSxid) {
		this.uSxid = uSxid;
	}

	public String getuSxmc() {
		return uSxmc;
	}

	public void setuSxmc(String uSxmc) {
		this.uSxmc = uSxmc;
	}

	public String getuZzbh() {
		return uZzbh;
	}

	public void setuZzbh(String uZzbh) {
		this.uZzbh = uZzbh;
	}

	public String getuZtlb() {
		return uZtlb;
	}

	public void setuZtlb(String uZtlb) {
		this.uZtlb = uZtlb;
	}

	public String getuZtmc() {
		return uZtmc;
	}

	public void setuZtmc(String uZtmc) {
		this.uZtmc = uZtmc;
	}

	public String getuZtdm() {
		return uZtdm;
	}

	public void setuZtdm(String uZtdm) {
		this.uZtdm = uZtdm;
	}

	public String getuFrxm() {
		return uFrxm;
	}

	public void setuFrxm(String uFrxm) {
		this.uFrxm = uFrxm;
	}

	public String getuFrsfz() {
		return uFrsfz;
	}

	public void setuFrsfz(String uFrsfz) {
		this.uFrsfz = uFrsfz;
	}

	public Date getuFzrq() {
		return uFzrq;
	}

	public void setuFzrq(Date uFzrq) {
		this.uFzrq = uFzrq;
	}

	public Date getuSxrq() {
		return uSxrq;
	}

	public void setuSxrq(Date uSxrq) {
		this.uSxrq = uSxrq;
	}

	public Date getuJzrq() {
		return uJzrq;
	}

	public void setuJzrq(Date uJzrq) {
		this.uJzrq = uJzrq;
	}

	public String getuJgdm() {
		return uJgdm;
	}

	public void setuJgdm(String uJgdm) {
		this.uJgdm = uJgdm;
	}

	public String getuFzjg() {
		return uFzjg;
	}

	public void setuFzjg(String uFzjg) {
		this.uFzjg = uFzjg;
	}

	public String getuXzqh() {
		return uXzqh;
	}

	public void setuXzqh(String uXzqh) {
		this.uXzqh = uXzqh;
	}

	public String getuCa() {
		return uCa;
	}

	public void setuCa(String uCa) {
		this.uCa = uCa;
	}

	public String getuBy1() {
		return uBy1;
	}

	public void setuBy1(String uBy1) {
		this.uBy1 = uBy1;
	}

	public String getuBy2() {
		return uBy2;
	}

	public void setuBy2(String uBy2) {
		this.uBy2 = uBy2;
	}

	public String getuZzzt() {
		return uZzzt;
	}

	public void setuZzzt(String uZzzt) {
		this.uZzzt = uZzzt;
	}

	public Integer getNid() {
		return nid;
	}

	public void setNid(Integer nid) {
		this.nid = nid;
	}

	public String getZzbh() {
		return zzbh;
	}

	public void setZzbh(String zzbh) {
		this.zzbh = zzbh;
	}

	public String getQsqrmc() {
		return qsqrmc;
	}

	public void setQsqrmc(String qsqrmc) {
		this.qsqrmc = qsqrmc;
	}

	public String getFddbr() {
		return fddbr;
	}

	public void setFddbr(String fddbr) {
		this.fddbr = fddbr;
	}

	public String getQsdd() {
		return qsdd;
	}

	public void setQsdd(String qsdd) {
		this.qsdd = qsdd;
	}

	public String getQsfs() {
		return qsfs;
	}

	public void setQsfs(String qsfs) {
		this.qsfs = qsfs;
	}

	public String getQsl() {
		return qsl;
	}

	public void setQsl(String qsl) {
		this.qsl = qsl;
	}

	public String getQsyt() {
		return qsyt;
	}

	public void setQsyt(String qsyt) {
		this.qsyt = qsyt;
	}

	public String getSylx() {
		return sylx;
	}

	public void setSylx(String sylx) {
		this.sylx = sylx;
	}

	public String getTsdd() {
		return tsdd;
	}

	public void setTsdd(String tsdd) {
		this.tsdd = tsdd;
	}

	public String getTsfs() {
		return tsfs;
	}

	public void setTsfs(String tsfs) {
		this.tsfs = tsfs;
	}

	public String getTsl() {
		return tsl;
	}

	public void setTsl(String tsl) {
		this.tsl = tsl;
	}

	public String getTssz() {
		return tssz;
	}

	public void setTssz(String tssz) {
		this.tssz = tssz;
	}

	public String getZzyxq() {
		return zzyxq;
	}

	public void setZzyxq(String zzyxq) {
		this.zzyxq = zzyxq;
	}

	public Date getPzrq() {
		return pzrq;
	}

	public void setPzrq(Date pzrq) {
		this.pzrq = pzrq;
	}

	public byte[] getuZzzm() {
		return uZzzm;
	}

	public void setuZzzm(byte[] uZzzm) {
		this.uZzzm = uZzzm;
	}

	public QsxkBean(String uId, String uMlbh, String uLxbh, String uSblsh,
			String uSxid, String uSxmc, String uZzbh, String uZtlb,
			String uZtmc, String uZtdm, String uFrxm, String uFrsfz,
			Date uFzrq, Date uSxrq, Date uJzrq, String uJgdm, String uFzjg,
			String uXzqh, String uCa, String uBy1, String uBy2, String uZzzt,
			Integer nid, String zzbh, String qsqrmc, String fddbr, String qsdd,
			String qsfs, String qsl, String qsyt, String sylx, String tsdd,
			String tsfs, String tsl, String tssz, String zzyxq, Date pzrq,
			byte[] uZzzm) {
		super();
		this.uId = uId;
		this.uMlbh = uMlbh;
		this.uLxbh = uLxbh;
		this.uSblsh = uSblsh;
		this.uSxid = uSxid;
		this.uSxmc = uSxmc;
		this.uZzbh = uZzbh;
		this.uZtlb = uZtlb;
		this.uZtmc = uZtmc;
		this.uZtdm = uZtdm;
		this.uFrxm = uFrxm;
		this.uFrsfz = uFrsfz;
		this.uFzrq = uFzrq;
		this.uSxrq = uSxrq;
		this.uJzrq = uJzrq;
		this.uJgdm = uJgdm;
		this.uFzjg = uFzjg;
		this.uXzqh = uXzqh;
		this.uCa = uCa;
		this.uBy1 = uBy1;
		this.uBy2 = uBy2;
		this.uZzzt = uZzzt;
		this.nid = nid;
		this.zzbh = zzbh;
		this.qsqrmc = qsqrmc;
		this.fddbr = fddbr;
		this.qsdd = qsdd;
		this.qsfs = qsfs;
		this.qsl = qsl;
		this.qsyt = qsyt;
		this.sylx = sylx;
		this.tsdd = tsdd;
		this.tsfs = tsfs;
		this.tsl = tsl;
		this.tssz = tssz;
		this.zzyxq = zzyxq;
		this.pzrq = pzrq;
		this.uZzzm = uZzzm;
	}

	public QsxkBean() {
		super();
	}
}