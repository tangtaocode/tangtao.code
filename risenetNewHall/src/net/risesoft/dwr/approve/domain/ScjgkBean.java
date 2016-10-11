package net.risesoft.dwr.approve.domain;

import java.math.BigDecimal;
import java.util.Date;

public class ScjgkBean {
	private String guid="";     //主键
	private String xmbh="";    //项目编号
	private String gcxh ="" ;  //工程序号
	private String xmmc="" ;  //项目名称
	private String zbgcmc=""; //招标工程名称
	private String gcmc="";  //工程名称
	private Date sqrq  ;     //申请日期
	private Date tbrq ;      //填表日期
	private String xmlx="" ;//项目类型
	private String tbr_sbdw="" ;//申报单位（业主委员会）名称 
	private String tbr_lxr="" ;//投标人——联系人
	private Date jgsj;         //竣工时间
	private Date rhsj ;       //入伙时间
	private Date inputdate ;  //录入时间
	//以下为项目信息 
    private String jsdw ;   //建设单位
    private String gcdz;     //工程地址
	private BigDecimal xmztz ;   //项目总投资
	private String jhlxwh ;  //计划立项文号
	private String ghxkzh ;   //规划许可证号
	private String jgdm; //企业机构代码
	private String  sq="" ;
	private String  tq="" ;
	 private String username;//用户名称
	   private String longingname;//登陆名称
	private String  method="";
	
	//小型工程抽签结果登记单
	private String  zbr;  //招标人
	private String zbdljg; //招标代理机构
	
	//以下为施工直接发包工程字段
    private Date slrq ;//受理日期

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getXmbh() {
		return xmbh;
	}

	public void setXmbh(String xmbh) {
		this.xmbh = xmbh;
	}

	public String getGcxh() {
		return gcxh;
	}

	public void setGcxh(String gcxh) {
		this.gcxh = gcxh;
	}

	public String getXmmc() {
		return xmmc;
	}

	public void setXmmc(String xmmc) {
		this.xmmc = xmmc;
	}

	public String getZbgcmc() {
		return zbgcmc;
	}

	public void setZbgcmc(String zbgcmc) {
		this.zbgcmc = zbgcmc;
	}

	public String getGcmc() {
		return gcmc;
	}

	public void setGcmc(String gcmc) {
		this.gcmc = gcmc;
	}

	public Date getSqrq() {
		return sqrq;
	}

	public void setSqrq(Date sqrq) {
		this.sqrq = sqrq;
	}

	public Date getTbrq() {
		return tbrq;
	}

	public void setTbrq(Date tbrq) {
		this.tbrq = tbrq;
	}

	public String getXmlx() {
		return xmlx;
	}

	public void setXmlx(String xmlx) {
		this.xmlx = xmlx;
	}

	public String getTbr_sbdw() {
		return tbr_sbdw;
	}

	public void setTbr_sbdw(String tbr_sbdw) {
		this.tbr_sbdw = tbr_sbdw;
	}

	public String getTbr_lxr() {
		return tbr_lxr;
	}

	public void setTbr_lxr(String tbr_lxr) {
		this.tbr_lxr = tbr_lxr;
	}

	public Date getJgsj() {
		return jgsj;
	}

	public void setJgsj(Date jgsj) {
		this.jgsj = jgsj;
	}

	public Date getRhsj() {
		return rhsj;
	}

	public void setRhsj(Date rhsj) {
		this.rhsj = rhsj;
	}

	public Date getInputdate() {
		return inputdate;
	}

	public void setInputdate(Date inputdate) {
		this.inputdate = inputdate;
	}

	public String getJsdw() {
		return jsdw;
	}

	public void setJsdw(String jsdw) {
		this.jsdw = jsdw;
	}

	public String getGcdz() {
		return gcdz;
	}

	public void setGcdz(String gcdz) {
		this.gcdz = gcdz;
	}

	public BigDecimal getXmztz() {
		return xmztz;
	}

	public void setXmztz(BigDecimal xmztz) {
		this.xmztz = xmztz;
	}

	public String getJhlxwh() {
		return jhlxwh;
	}

	public void setJhlxwh(String jhlxwh) {
		this.jhlxwh = jhlxwh;
	}

	public String getGhxkzh() {
		return ghxkzh;
	}

	public void setGhxkzh(String ghxkzh) {
		this.ghxkzh = ghxkzh;
	}

	public String getJgdm() {
		return jgdm;
	}

	public void setJgdm(String jgdm) {
		this.jgdm = jgdm;
	}

	public String getSq() {
		return sq;
	}

	public void setSq(String sq) {
		this.sq = sq;
	}

	public String getTq() {
		return tq;
	}

	public void setTq(String tq) {
		this.tq = tq;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getZbr() {
		return zbr;
	}

	public void setZbr(String zbr) {
		this.zbr = zbr;
	}

	public String getZbdljg() {
		return zbdljg;
	}

	public void setZbdljg(String zbdljg) {
		this.zbdljg = zbdljg;
	}

	public Date getSlrq() {
		return slrq;
	}

	public void setSlrq(Date slrq) {
		this.slrq = slrq;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLongingname() {
		return longingname;
	}

	public void setLongingname(String longingname) {
		this.longingname = longingname;
	}
	
}
