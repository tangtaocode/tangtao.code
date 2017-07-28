package net.risesoft.approve.entity.jpa.certificate;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="T_SWJ_CSPSXU")//城市排水许可证照
public class CspsxuBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2617324719587800446L;
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
	
	@Column(name = "PSXKNR")
	private byte[] psxknr;
	@Column(name = "PSYHMC")
	private String psyhmc;
	@Column(name = "PSYHGLDW")
	private String psyhgldw;
	@Column(name = "XXPSDZ")
	private String xxpsdz;
	@Column(name = "PSYHLX")
	private String psyhlx;
	@Column(name = "BGDJ")
	private byte[] bgdj;
	
	@Column(name = "NID")
	private Integer nid;
	@Column(name = "PSXKDW")
	private String psxkdw;
	@Column(name = "FZRQ")
	private Date fzrq;
	@Column(name = "ZZBH")
	private String zzbh;
	@Column(name = "PSZL")
	private String pszl;
	@Column(name = "PSKSL")
	private String psksl;
	@Column(name = "JRLDA")
	private String jrlda;
	@Column(name = "QSLDA")
	private String qslda;
	@Column(name = "JRGJA")
	private String jrgja;
	@Column(name = "SZGJA")
	private String szgja;
	@Column(name = "JRLDB")
	private String jrldb;
	@Column(name = "QSLDB")
	private String qsldb;
	@Column(name = "JRGJB")
	private String jrgjb;
	@Column(name = "SZGJB")
	private String szgjb;
	@Column(name = "JRLDC")
	private String jrldc;
	@Column(name = "QSLDC")
	private String qsldc;
	@Column(name = "JRGJC")
	private String jrgjc;
	@Column(name = "SZGJC")
	private String szgjc;
	@Column(name = "JRLDD")
	private String jrldd;
	@Column(name = "QSLDD")
	private String qsldd;
	@Column(name = "JRGJD")
	private String jrgjd;
	@Column(name = "SZGJD")
	private String szgjd;
	@Column(name = "JRLDE")
	private String jrlde;
	@Column(name = "QSLDE")
	private String qslde;
	@Column(name = "JRGJE")
	private String jrgje;
	@Column(name = "SZGJE")
	private String szgje;
	@Column(name = "JRLDF")
	private String jrldf;
	@Column(name = "QSLDF")
	private String qsldf;
	@Column(name = "JRGJF")
	private String jrgjf;
	@Column(name = "SZGJF")
	private String szgjf;
	@Column(name = "JRLDG")
	private String jrldg;
	@Column(name = "QSLDG")
	private String qsldg;
	@Column(name = "JRGJG")
	private String jrgjg;
	@Column(name = "SZGJG")
	private String szgjg;
	@Column(name = "JRLDH")
	private String jrldh;
	@Column(name = "QSLDH")
	private String qsldh;
	@Column(name = "JRGJH")
	private String jrgjh;
	@Column(name = "SZGJH")
	private String szgjh;
	@Column(name = "BGJLA")
	private String bgjla;
	@Column(name = "BGJLB")
	private String bgjlb;
	@Column(name = "BGJLC")
	private String bgjlc;
	@Column(name = "BGJLD")
	private String bgjld;
	@Column(name = "BGJLE")
	private String bgjle;
	@Column(name = "BGJLF")
	private String bgjlf;
	@Column(name = "BGJLG")
	private String bgjlg;
	@Column(name = "BGJLH")
	private String bgjlh;

	
	public CspsxuBean() {
		super();
	}

	public CspsxuBean(String uId, String uMlbh, String uLxbh, String uSblsh,
			String uSxid, String uSxmc, String uZzbh, String uZtlb,
			String uZtmc, String uZtdm, String uFrxm, String uFrsfz,
			Date uFzrq, Date uSxrq, Date uJzrq, String uJgdm, String uFzjg,
			String uXzqh, String uCa, String uBy1, String uBy2, String uZzzt,
			String psyhmc, String psyhgldw, String xxpsdz, String psyhlx,
			Integer nid, String psxkdw, Date fzrq, String zzbh, String pszl,
			String psksl, String jrlda, String qslda, String jrgja,
			String szgja, String jrldb, String qsldb, String jrgjb,
			String szgjb, String jrldc, String qsldc, String jrgjc,
			String szgjc, String jrldd, String qsldd, String jrgjd,
			String szgjd, String jrlde, String qslde, String jrgje,
			String szgje, String jrldf, String qsldf, String jrgjf,
			String szgjf, String jrldg, String qsldg, String jrgjg,
			String szgjg, String jrldh, String qsldh, String jrgjh,
			String szgjh, String bgjla, String bgjlb, String bgjlc,
			String bgjld, String bgjle, String bgjlf, String bgjlg, String bgjlh) {
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
		this.psyhmc = psyhmc;
		this.psyhgldw = psyhgldw;
		this.xxpsdz = xxpsdz;
		this.psyhlx = psyhlx;
		this.nid = nid;
		this.psxkdw = psxkdw;
		this.fzrq = fzrq;
		this.zzbh = zzbh;
		this.pszl = pszl;
		this.psksl = psksl;
		this.jrlda = jrlda;
		this.qslda = qslda;
		this.jrgja = jrgja;
		this.szgja = szgja;
		this.jrldb = jrldb;
		this.qsldb = qsldb;
		this.jrgjb = jrgjb;
		this.szgjb = szgjb;
		this.jrldc = jrldc;
		this.qsldc = qsldc;
		this.jrgjc = jrgjc;
		this.szgjc = szgjc;
		this.jrldd = jrldd;
		this.qsldd = qsldd;
		this.jrgjd = jrgjd;
		this.szgjd = szgjd;
		this.jrlde = jrlde;
		this.qslde = qslde;
		this.jrgje = jrgje;
		this.szgje = szgje;
		this.jrldf = jrldf;
		this.qsldf = qsldf;
		this.jrgjf = jrgjf;
		this.szgjf = szgjf;
		this.jrldg = jrldg;
		this.qsldg = qsldg;
		this.jrgjg = jrgjg;
		this.szgjg = szgjg;
		this.jrldh = jrldh;
		this.qsldh = qsldh;
		this.jrgjh = jrgjh;
		this.szgjh = szgjh;
		this.bgjla = bgjla;
		this.bgjlb = bgjlb;
		this.bgjlc = bgjlc;
		this.bgjld = bgjld;
		this.bgjle = bgjle;
		this.bgjlf = bgjlf;
		this.bgjlg = bgjlg;
		this.bgjlh = bgjlh;
	}

	public CspsxuBean(String uId, String uMlbh, String uLxbh, String uSblsh,
			String uSxid, String uSxmc, String uZzbh, String uZtlb,
			String uZtmc, String uZtdm, String uFrxm, String uFrsfz,
			Date uFzrq, Date uSxrq, Date uJzrq, String uJgdm, String uFzjg,
			String uXzqh, String uCa, String uBy1, String uBy2, String uZzzt,
			String psyhmc, String psyhgldw, String xxpsdz, String psyhlx,
			Integer nid, String psxkdw, Date fzrq, String zzbh, String pszl,
			String psksl, String jrlda, String qslda, String jrgja,
			String szgja, String jrldb, String qsldb, String jrgjb,
			String szgjb, String jrldc, String qsldc, String jrgjc,
			String szgjc, String jrldd, String qsldd, String jrgjd,
			String szgjd, String jrlde, String qslde, String jrgje,
			String szgje, String jrldf, String qsldf, String jrgjf,
			String szgjf, String jrldg, String qsldg, String jrgjg,
			String szgjg, String jrldh, String qsldh, String jrgjh,
			String szgjh, String bgjla, String bgjlb, String bgjlc,
			String bgjld, String bgjle, String bgjlf, String bgjlg,
			String bgjlh, byte[] uZzzm, byte[] psxknr, byte[] bgdj) {
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
		this.psyhmc = psyhmc;
		this.psyhgldw = psyhgldw;
		this.xxpsdz = xxpsdz;
		this.psyhlx = psyhlx;
		this.nid = nid;
		this.psxkdw = psxkdw;
		this.fzrq = fzrq;
		this.zzbh = zzbh;
		this.pszl = pszl;
		this.psksl = psksl;
		this.jrlda = jrlda;
		this.qslda = qslda;
		this.jrgja = jrgja;
		this.szgja = szgja;
		this.jrldb = jrldb;
		this.qsldb = qsldb;
		this.jrgjb = jrgjb;
		this.szgjb = szgjb;
		this.jrldc = jrldc;
		this.qsldc = qsldc;
		this.jrgjc = jrgjc;
		this.szgjc = szgjc;
		this.jrldd = jrldd;
		this.qsldd = qsldd;
		this.jrgjd = jrgjd;
		this.szgjd = szgjd;
		this.jrlde = jrlde;
		this.qslde = qslde;
		this.jrgje = jrgje;
		this.szgje = szgje;
		this.jrldf = jrldf;
		this.qsldf = qsldf;
		this.jrgjf = jrgjf;
		this.szgjf = szgjf;
		this.jrldg = jrldg;
		this.qsldg = qsldg;
		this.jrgjg = jrgjg;
		this.szgjg = szgjg;
		this.jrldh = jrldh;
		this.qsldh = qsldh;
		this.jrgjh = jrgjh;
		this.szgjh = szgjh;
		this.bgjla = bgjla;
		this.bgjlb = bgjlb;
		this.bgjlc = bgjlc;
		this.bgjld = bgjld;
		this.bgjle = bgjle;
		this.bgjlf = bgjlf;
		this.bgjlg = bgjlg;
		this.bgjlh = bgjlh;
		this.uZzzm = uZzzm;
		this.psxknr = psxknr;
		this.bgdj = bgdj;
	}


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

	public String getPsyhmc() {
		return psyhmc;
	}

	public void setPsyhmc(String psyhmc) {
		this.psyhmc = psyhmc;
	}

	public String getPsyhgldw() {
		return psyhgldw;
	}

	public void setPsyhgldw(String psyhgldw) {
		this.psyhgldw = psyhgldw;
	}

	public String getXxpsdz() {
		return xxpsdz;
	}

	public void setXxpsdz(String xxpsdz) {
		this.xxpsdz = xxpsdz;
	}

	public String getPsyhlx() {
		return psyhlx;
	}

	public void setPsyhlx(String psyhlx) {
		this.psyhlx = psyhlx;
	}

	public Integer getNid() {
		return nid;
	}

	public void setNid(Integer nid) {
		this.nid = nid;
	}

	public String getPsxkdw() {
		return psxkdw;
	}

	public void setPsxkdw(String psxkdw) {
		this.psxkdw = psxkdw;
	}

	public Date getFzrq() {
		return fzrq;
	}

	public void setFzrq(Date fzrq) {
		this.fzrq = fzrq;
	}

	public String getZzbh() {
		return zzbh;
	}

	public void setZzbh(String zzbh) {
		this.zzbh = zzbh;
	}

	public String getPszl() {
		return pszl;
	}

	public void setPszl(String pszl) {
		this.pszl = pszl;
	}

	public String getPsksl() {
		return psksl;
	}

	public void setPsksl(String psksl) {
		this.psksl = psksl;
	}

	public String getJrlda() {
		return jrlda;
	}

	public void setJrlda(String jrlda) {
		this.jrlda = jrlda;
	}

	public String getQslda() {
		return qslda;
	}

	public void setQslda(String qslda) {
		this.qslda = qslda;
	}

	public String getJrgja() {
		return jrgja;
	}

	public void setJrgja(String jrgja) {
		this.jrgja = jrgja;
	}

	public String getSzgja() {
		return szgja;
	}

	public void setSzgja(String szgja) {
		this.szgja = szgja;
	}

	public String getJrldb() {
		return jrldb;
	}

	public void setJrldb(String jrldb) {
		this.jrldb = jrldb;
	}

	public String getQsldb() {
		return qsldb;
	}

	public void setQsldb(String qsldb) {
		this.qsldb = qsldb;
	}

	public String getJrgjb() {
		return jrgjb;
	}

	public void setJrgjb(String jrgjb) {
		this.jrgjb = jrgjb;
	}

	public String getSzgjb() {
		return szgjb;
	}

	public void setSzgjb(String szgjb) {
		this.szgjb = szgjb;
	}

	public String getJrldc() {
		return jrldc;
	}

	public void setJrldc(String jrldc) {
		this.jrldc = jrldc;
	}

	public String getQsldc() {
		return qsldc;
	}

	public void setQsldc(String qsldc) {
		this.qsldc = qsldc;
	}

	public String getJrgjc() {
		return jrgjc;
	}

	public void setJrgjc(String jrgjc) {
		this.jrgjc = jrgjc;
	}

	public String getSzgjc() {
		return szgjc;
	}

	public void setSzgjc(String szgjc) {
		this.szgjc = szgjc;
	}

	public String getJrldd() {
		return jrldd;
	}

	public void setJrldd(String jrldd) {
		this.jrldd = jrldd;
	}

	public String getQsldd() {
		return qsldd;
	}

	public void setQsldd(String qsldd) {
		this.qsldd = qsldd;
	}

	public String getJrgjd() {
		return jrgjd;
	}

	public void setJrgjd(String jrgjd) {
		this.jrgjd = jrgjd;
	}

	public String getSzgjd() {
		return szgjd;
	}

	public void setSzgjd(String szgjd) {
		this.szgjd = szgjd;
	}

	public String getJrlde() {
		return jrlde;
	}

	public void setJrlde(String jrlde) {
		this.jrlde = jrlde;
	}

	public String getQslde() {
		return qslde;
	}

	public void setQslde(String qslde) {
		this.qslde = qslde;
	}

	public String getJrgje() {
		return jrgje;
	}

	public void setJrgje(String jrgje) {
		this.jrgje = jrgje;
	}

	public String getSzgje() {
		return szgje;
	}

	public void setSzgje(String szgje) {
		this.szgje = szgje;
	}

	public String getJrldf() {
		return jrldf;
	}

	public void setJrldf(String jrldf) {
		this.jrldf = jrldf;
	}

	public String getQsldf() {
		return qsldf;
	}

	public void setQsldf(String qsldf) {
		this.qsldf = qsldf;
	}

	public String getJrgjf() {
		return jrgjf;
	}

	public void setJrgjf(String jrgjf) {
		this.jrgjf = jrgjf;
	}

	public String getSzgjf() {
		return szgjf;
	}

	public void setSzgjf(String szgjf) {
		this.szgjf = szgjf;
	}

	public String getJrldg() {
		return jrldg;
	}

	public void setJrldg(String jrldg) {
		this.jrldg = jrldg;
	}

	public String getQsldg() {
		return qsldg;
	}

	public void setQsldg(String qsldg) {
		this.qsldg = qsldg;
	}

	public String getJrgjg() {
		return jrgjg;
	}

	public void setJrgjg(String jrgjg) {
		this.jrgjg = jrgjg;
	}

	public String getSzgjg() {
		return szgjg;
	}

	public void setSzgjg(String szgjg) {
		this.szgjg = szgjg;
	}

	public String getJrldh() {
		return jrldh;
	}

	public void setJrldh(String jrldh) {
		this.jrldh = jrldh;
	}

	public String getQsldh() {
		return qsldh;
	}

	public void setQsldh(String qsldh) {
		this.qsldh = qsldh;
	}

	public String getJrgjh() {
		return jrgjh;
	}

	public void setJrgjh(String jrgjh) {
		this.jrgjh = jrgjh;
	}

	public String getSzgjh() {
		return szgjh;
	}

	public void setSzgjh(String szgjh) {
		this.szgjh = szgjh;
	}

	public String getBgjla() {
		return bgjla;
	}

	public void setBgjla(String bgjla) {
		this.bgjla = bgjla;
	}

	public String getBgjlb() {
		return bgjlb;
	}

	public void setBgjlb(String bgjlb) {
		this.bgjlb = bgjlb;
	}

	public String getBgjlc() {
		return bgjlc;
	}

	public void setBgjlc(String bgjlc) {
		this.bgjlc = bgjlc;
	}

	public String getBgjld() {
		return bgjld;
	}

	public void setBgjld(String bgjld) {
		this.bgjld = bgjld;
	}

	public String getBgjle() {
		return bgjle;
	}

	public void setBgjle(String bgjle) {
		this.bgjle = bgjle;
	}

	public String getBgjlf() {
		return bgjlf;
	}

	public void setBgjlf(String bgjlf) {
		this.bgjlf = bgjlf;
	}

	public String getBgjlg() {
		return bgjlg;
	}

	public void setBgjlg(String bgjlg) {
		this.bgjlg = bgjlg;
	}

	public String getBgjlh() {
		return bgjlh;
	}

	public void setBgjlh(String bgjlh) {
		this.bgjlh = bgjlh;
	}
	public byte[] getuZzzm() {
		return uZzzm;
	}

	public void setuZzzm(byte[] uZzzm) {
		this.uZzzm = uZzzm;
	}

	public byte[] getPsxknr() {
		return psxknr;
	}

	public void setPsxknr(byte[] psxknr) {
		this.psxknr = psxknr;
	}

	public byte[] getBgdj() {
		return bgdj;
	}
	public void setBgdj(byte[] bgdj) {
		this.bgdj = bgdj;
	}
	@Override
	public String toString() {
		return "CspsxuBean [uId=" + uId + ", uMlbh=" + uMlbh + ", uLxbh="
				+ uLxbh + ", uSblsh=" + uSblsh + ", uSxid=" + uSxid
				+ ", uSxmc=" + uSxmc + ", uZzbh=" + uZzbh + ", uZtlb=" + uZtlb
				+ ", uZtmc=" + uZtmc + ", uZtdm=" + uZtdm + ", uFrxm=" + uFrxm
				+ ", uFrsfz=" + uFrsfz + ", uFzrq=" + uFzrq + ", uSxrq="
				+ uSxrq + ", uJzrq=" + uJzrq + ", uJgdm=" + uJgdm + ", uFzjg="
				+ uFzjg + ", uXzqh=" + uXzqh + ", uCa=" + uCa + ", uZzzm="
				+ Arrays.toString(uZzzm) + ", uZzzt=" + uZzzt + ", psxknr="
				+ Arrays.toString(psxknr) + ", psyhmc=" + psyhmc
				+ ", psyhgldw=" + psyhgldw + ", xxpsdz=" + xxpsdz + ", psyhlx="
				+ psyhlx + ", bgdj=" + Arrays.toString(bgdj) + ", nid=" + nid
				+ ", psxkdw=" + psxkdw + ", fzrq=" + fzrq + ", zzbh=" + zzbh
				+ ", pszl=" + pszl + ", psksl=" + psksl + ", jrlda=" + jrlda
				+ ", qslda=" + qslda + ", jrgja=" + jrgja + ", szgja=" + szgja
				+ "]";
	}
}