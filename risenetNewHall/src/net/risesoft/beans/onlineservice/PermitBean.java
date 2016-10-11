package net.risesoft.beans.onlineservice;

import java.io.Serializable;
import java.util.List;

/**
 * @description 施工许可表单信息bean
 * @author HJL
 * @date 2013-12-04
 *
 */
public class PermitBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2387041923038703794L;
	
	private String guid;  //主键 @Table(name="ZJSB_ZA_FWPTSQB")
	private String xmmc;  //项目名称
	private String gcmc;  //工程名称
	private String dycsb;  //第一次申办
	private String bsdycsb;  //不是第一次申办
	private String jsdwxmfzr;  //建设单位项目负责人
	private String lxdh;  //联系电话及移动电话
	private String sqjbrxm;  //申请经办人姓名
	private String sqjbrlxdh;  //申请经办人联系电话
	private String sqjbryddh;  //申请经办人移动电话
	private String sqjbrcz;  //申请经办人传真
	private String cqzm_fcz;  //产权证明_房产证
	private String cqzm_tdsyht;  //产权证明_土地使用合同
	private String fdczhm;  //房地产证号码
	private String fdczzdh;  //房地产证宗地号
	private String tdhth;  //土地使用权出让合同号
	private String jsydghxk;  //规划许可_建设用地规划许可证
	private String jsgcghxk;  //建设工程规划许可证
	private String zjbjzms;  //桩基报建证明书
	private String zjbjzmshm;  //桩基报建证明书号码
	private String jsgcghxkhm;  //建设工程规划许可证号码
	private String jsydghxkzhm;  //建设用地规划许可证号码
	private String shyjs;  //建设消防审核意见书
	private String fsgzqd;  //建设工程消防监督管理方式告知单
	private String sjshyjshm;  //建设工程消防设计审核意见书号码
	private String fsgzdhm;  //建设工程消防监督管理方式告知单号码
	private String sgthgyjs;  //本次施工工程的施工图设计文件审查合格意见书
	private String sgthgshm;  //审查合格意见书号码
	private String rqzyhgz;  //燃气专业工程施工图审查合格意见书
	private String rqzyhgshm;  //燃气专业工程施工图审查合格意见书号码
	private String zhsjyjs;  //支护设计文件审查合格意见书
	private String zfyjhgshm;  //支护设计文件审查合格意见书号码
	private String sjwjccyjs;  //设计文件抽查（备案）意见书
	private String sgtccyjshm;  //施工设计文件抽查（备案）意见书号码
	private String zjfbwh;  //直接发包审批决定书文号
	private String xdsfdb;  //需第三方担保
	private String bxdsfdb;  //不需第三方担保
	private String schg;  //审查合格
	private String scbhg;  //审查不合格
	private String jsdwxm;  //建设单位姓名
	private String jsdwsfz;  //建设单位身份证号
	private String jsdwyddh;  //建设单位移动电话
	private String jszqzrxm;  //建设单位安全主任姓名
	private String jsaqzrsfzh;  //建设单位安全主任身份证号
	private String jsaqzryddh;  //建设单位安全主任移动电话
	private String sgzlxm;  //施工单位质量主人姓名
	private String sgzlsfz;  //施工单位质量主人身份证号
	private String sgzlyddh;  //施工单位质量主人移动电话
	private String sgaqxm;  //施工单位安全主任姓名
	private String sgaqsfz;  //施工单位安全主任身份证号
	private String szaqyddh;  //施工单位安全主任移动电话
	private String sgdw;  //施工单位
	private String lxr;  //联系人
	private String sglxrdh;  //施工单位移动电话
	private String jzsmc;  //建造师
	private String jzszch;  //建造师执业注册号
	private String jzsdh;  //建造师移动电话
	private String jldw;  //监理单位
	private String jllxr;  //监理单位联系人
	private String jldwdh;  //监理单位移动电话
	private String xmzj;  //项目总监
	private String zmzjzch;  //项目总监执业注册号
	private String xmzjdh;  //项目总监移动电话
	private String kcdw;  //勘察单位
	private String kcdwlxr;  //勘察单位联系人
	private String kcdwlxrdh;  //勘察单位联系人移动电话
	private String sjdw;  //设计单位
	private String sjdwlxr;  //设计单位联系人
	private String sjdwlxrdh;  //设计单位联系人电话
	private String stjg;  //审图机构
	private String stjglxr;  //审图机构联系人
	private String stjgdh;  //审图机构联系电话
	private String gcdz;  //工程地址
	private String ydmj;  //用地红线面积
	private String lgq;  //龙岗区
	private String ytq;  //盐田区
	private String lhq;  //罗湖区
	private String ftq;  //福田区
	private String nsq;  //南山区
	private String baq;  //宝安区
	private String gmxq;  //光明新区
	private String psxq;  //坪山新区
	private String dpxq;  //大鹏新区
	private String lhxq;  //龙华新区
	private String zfzj;  //政府
	private String gyzj;  //国有资金
	private String jtzj;  //集体资金
	private String syzj;  //私营资金
	private String wzzj;  //外资私营
	private String qtzj;  //其他私营
	private String bjrjlmjzh;  //不计容积率建筑面积总和
	private String cgc;  //超高层
	private String gc;  //高层
	private String dc;  //多层
	private String jzcscgc;  //建筑层数超高层
	private String jzcsgc;  //建筑层数高层
	private String jzcsdc;  //建筑层数多层
	private String jzgd;  //建筑高度
	private String dxsmj;  //地下室面积
	private String dxscs;  //地下室层数
	private String jc;  //基本
	private String zt;  //主体
	private String jkkwsd;  //基坑开挖深度
	private String jkkwmj;  //基坑开外面积
	private String gcjbyj;  //工程级别一级
	private String gcjbej;  //工程级别二级
	private String gcjbsj;  //工程级别三级
	private String bcsgmj;  //本次施工建筑面积
	private String htzj;  //合同造价
	private String htkgrq;  //合同开工日期
	private String htjgrq;  //合同竣工日期
	private String sspf;  //是商品房
	private String bsspf;  //不是商品房
	private String bcsgzzmj;  //本次施工住宅总面积
	private String cgd;  //超过90的合计
	private String bzd;  //不足的合计
	private String sgxk;  //施工许可
	private String tqkg;  //提前开工核准
	private String wenhao;  //应急工程批复文件文号
	private String tsfgc;  //土石方工程
	private String jkzfgc;  //基坑支护工程
	private String jjc;  //桩基础
	private String trjc;  //天然基础
	private String qt;  //其他
	private String gjhnt;  //钢筋混凝土
	private String qtqt;  //砌体
	private String gjg;  //钢结构
	private String wj;  //网架
	private String smjg;  //索膜结构
	private String zszx;  //装饰装修
	private String jsmcgc;  //金属门窗工程
	private String mqmq;  //幕墙
	private String mqpfm;  //幕墙平方米
	private String wmfsgc;  //房屋防水工程
	private String tfgc;  //通风工程
	private String ktgc;  //空调工程
	private String zjjpsgc;  //建筑给排水工程
	private String dtgc;  //电梯工程
	private String jzdqgc;  //建筑电气工程
	private String xfgc;  //消防工程
	private String znhgc;  //智能化工程
	private String rqgchs;  //燃气工程户数
	private String rcgctyg;  //燃气工程庭院管
	private String hushu;  //户数
	private String tym;  //庭院管米
	private String swgc;  //室外工程
	private String qita;  //其他
	private String bh;  //编号
	private String gcbh;  //工程编号
	private String leixing;  //办理类型
	private String jzssfzhm;  //建造师身份证号码
	private String xmzjsfzhm;  //项目总监身份证号码
	private String xmbh;  //项目编号
	private String szdxm;  //是总大项目
	private String fzdxm;  //不是总大项目
	private String ztz;  //总投资
	private String jg;  //结构
	private String sz_qtypgc;  //七通一平工程
	private String sz_dxgdgc;  //电信管道工程
	private String sz_dqhpgc_l;  //挡坡护墙长度
	private String sz_dqhpgc_w;  //挡坡护墙宽度
	private String sz_dqhpgc_h;  //挡坡护墙高度
	private String sz_dlgdgc;  //电力管道工程
	private String sz_rjclgc;  //软基处理工程
	private String sz_wscl;  //污水处理及配套工程
	private String sz_wncl;  //污泥处理及配套工程
	private String sz_scptgc;  //水厂及配套工程
	private String sz_qt;  //其他
	private String zszx_gzzmj;  //装修改造总面积
	private String zszx_tze;  //总计投资额
	private String zszx_kgmj;  //本次开工面积
	private String zszx_nbzs_pfm;  //内部装饰_平方米
	private String zszx_wbzs_pfm;  //外部装饰_平方米
	private String zszx_zmdq;  //照明电器
	private String zszx_sngps;  //室内给排水
	private String zszx_xfgc;  //消防工程
	private String zszx_tfgc;  //通风工程
	private String zszx_dtgc;  //电梯工程
	private String zszx_rqgc;  //燃气工程
	private String zszx_znhgc;  //智能化工程
	private String zszx_fsgc;  //防水工程
	private String zszx_qt;  //其他
	private String zszx_htzj;  //装饰装修合同造价
	private String jssg_type;  //建设施工许可申请表类别(1、市政公用及配套专业工程 2、房屋建筑及配套专业工程 3、装饰装修改造工程)
	
	private String zjbh;//原施工许可证序号@Table(name="t_banjie")
	
	
	private String queryId;//查询id
	private String error;//错误信息
	private List<PermitApplyUnit> applyUnit;//申请单位
	
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	public String getXmmc() {
		return xmmc;
	}
	public void setXmmc(String xmmc) {
		this.xmmc = xmmc;
	}
	
	public String getGcmc() {
		return gcmc;
	}
	public void setGcmc(String gcmc) {
		this.gcmc = gcmc;
	}
	
	public String getDycsb() {
		return dycsb;
	}
	public void setDycsb(String dycsb) {
		this.dycsb = dycsb;
	}
	
	public String getBsdycsb() {
		return bsdycsb;
	}
	public void setBsdycsb(String bsdycsb) {
		this.bsdycsb = bsdycsb;
	}
	
	public String getJsdwxmfzr() {
		return jsdwxmfzr;
	}
	public void setJsdwxmfzr(String jsdwxmfzr) {
		this.jsdwxmfzr = jsdwxmfzr;
	}
	
	public String getLxdh() {
		return lxdh;
	}
	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}
	
	public String getSqjbrxm() {
		return sqjbrxm;
	}
	public void setSqjbrxm(String sqjbrxm) {
		this.sqjbrxm = sqjbrxm;
	}
	
	public String getSqjbrlxdh() {
		return sqjbrlxdh;
	}
	public void setSqjbrlxdh(String sqjbrlxdh) {
		this.sqjbrlxdh = sqjbrlxdh;
	}
	
	public String getSqjbryddh() {
		return sqjbryddh;
	}
	public void setSqjbryddh(String sqjbryddh) {
		this.sqjbryddh = sqjbryddh;
	}
	
	public String getSqjbrcz() {
		return sqjbrcz;
	}
	public void setSqjbrcz(String sqjbrcz) {
		this.sqjbrcz = sqjbrcz;
	}
	
	public String getCqzm_fcz() {
		return cqzm_fcz;
	}
	public void setCqzm_fcz(String cqzm_fcz) {
		this.cqzm_fcz = cqzm_fcz;
	}
	
	public String getCqzm_tdsyht() {
		return cqzm_tdsyht;
	}
	public void setCqzm_tdsyht(String cqzm_tdsyht) {
		this.cqzm_tdsyht = cqzm_tdsyht;
	}
	
	public String getFdczhm() {
		return fdczhm;
	}
	public void setFdczhm(String fdczhm) {
		this.fdczhm = fdczhm;
	}
	
	public String getFdczzdh() {
		return fdczzdh;
	}
	public void setFdczzdh(String fdczzdh) {
		this.fdczzdh = fdczzdh;
	}
	
	public String getTdhth() {
		return tdhth;
	}
	public void setTdhth(String tdhth) {
		this.tdhth = tdhth;
	}
	
	public String getJsydghxk() {
		return jsydghxk;
	}
	public void setJsydghxk(String jsydghxk) {
		this.jsydghxk = jsydghxk;
	}
	
	public String getJsgcghxk() {
		return jsgcghxk;
	}
	public void setJsgcghxk(String jsgcghxk) {
		this.jsgcghxk = jsgcghxk;
	}
	
	public String getZjbjzms() {
		return zjbjzms;
	}
	public void setZjbjzms(String zjbjzms) {
		this.zjbjzms = zjbjzms;
	}
	
	public String getZjbjzmshm() {
		return zjbjzmshm;
	}
	public void setZjbjzmshm(String zjbjzmshm) {
		this.zjbjzmshm = zjbjzmshm;
	}
	
	public String getJsgcghxkhm() {
		return jsgcghxkhm;
	}
	public void setJsgcghxkhm(String jsgcghxkhm) {
		this.jsgcghxkhm = jsgcghxkhm;
	}
	
	public String getJsydghxkzhm() {
		return jsydghxkzhm;
	}
	public void setJsydghxkzhm(String jsydghxkzhm) {
		this.jsydghxkzhm = jsydghxkzhm;
	}
	
	public String getShyjs() {
		return shyjs;
	}
	public void setShyjs(String shyjs) {
		this.shyjs = shyjs;
	}
	
	public String getFsgzqd() {
		return fsgzqd;
	}
	public void setFsgzqd(String fsgzqd) {
		this.fsgzqd = fsgzqd;
	}
	
	public String getSjshyjshm() {
		return sjshyjshm;
	}
	public void setSjshyjshm(String sjshyjshm) {
		this.sjshyjshm = sjshyjshm;
	}
	
	public String getFsgzdhm() {
		return fsgzdhm;
	}
	public void setFsgzdhm(String fsgzdhm) {
		this.fsgzdhm = fsgzdhm;
	}
	
	public String getSgthgyjs() {
		return sgthgyjs;
	}
	public void setSgthgyjs(String sgthgyjs) {
		this.sgthgyjs = sgthgyjs;
	}
	
	public String getSgthgshm() {
		return sgthgshm;
	}
	public void setSgthgshm(String sgthgshm) {
		this.sgthgshm = sgthgshm;
	}
	
	public String getRqzyhgz() {
		return rqzyhgz;
	}
	public void setRqzyhgz(String rqzyhgz) {
		this.rqzyhgz = rqzyhgz;
	}
	
	public String getRqzyhgshm() {
		return rqzyhgshm;
	}
	public void setRqzyhgshm(String rqzyhgshm) {
		this.rqzyhgshm = rqzyhgshm;
	}
	
	public String getZhsjyjs() {
		return zhsjyjs;
	}
	public void setZhsjyjs(String zhsjyjs) {
		this.zhsjyjs = zhsjyjs;
	}
	
	public String getZfyjhgshm() {
		return zfyjhgshm;
	}
	public void setZfyjhgshm(String zfyjhgshm) {
		this.zfyjhgshm = zfyjhgshm;
	}
	
	public String getSjwjccyjs() {
		return sjwjccyjs;
	}
	public void setSjwjccyjs(String sjwjccyjs) {
		this.sjwjccyjs = sjwjccyjs;
	}
	
	public String getSgtccyjshm() {
		return sgtccyjshm;
	}
	public void setSgtccyjshm(String sgtccyjshm) {
		this.sgtccyjshm = sgtccyjshm;
	}
	
	public String getZjfbwh() {
		return zjfbwh;
	}
	public void setZjfbwh(String zjfbwh) {
		this.zjfbwh = zjfbwh;
	}
	
	public String getXdsfdb() {
		return xdsfdb;
	}
	public void setXdsfdb(String xdsfdb) {
		this.xdsfdb = xdsfdb;
	}
	
	public String getBxdsfdb() {
		return bxdsfdb;
	}
	public void setBxdsfdb(String bxdsfdb) {
		this.bxdsfdb = bxdsfdb;
	}
	
	public String getSchg() {
		return schg;
	}
	public void setSchg(String schg) {
		this.schg = schg;
	}
	
	public String getScbhg() {
		return scbhg;
	}
	public void setScbhg(String scbhg) {
		this.scbhg = scbhg;
	}
	
	public String getJsdwxm() {
		return jsdwxm;
	}
	public void setJsdwxm(String jsdwxm) {
		this.jsdwxm = jsdwxm;
	}
	
	public String getJsdwsfz() {
		return jsdwsfz;
	}
	public void setJsdwsfz(String jsdwsfz) {
		this.jsdwsfz = jsdwsfz;
	}
	
	public String getJsdwyddh() {
		return jsdwyddh;
	}
	public void setJsdwyddh(String jsdwyddh) {
		this.jsdwyddh = jsdwyddh;
	}
	
	public String getJszqzrxm() {
		return jszqzrxm;
	}
	public void setJszqzrxm(String jszqzrxm) {
		this.jszqzrxm = jszqzrxm;
	}
	
	public String getJsaqzrsfzh() {
		return jsaqzrsfzh;
	}
	public void setJsaqzrsfzh(String jsaqzrsfzh) {
		this.jsaqzrsfzh = jsaqzrsfzh;
	}
	
	public String getJsaqzryddh() {
		return jsaqzryddh;
	}
	public void setJsaqzryddh(String jsaqzryddh) {
		this.jsaqzryddh = jsaqzryddh;
	}
	
	public String getSgzlxm() {
		return sgzlxm;
	}
	public void setSgzlxm(String sgzlxm) {
		this.sgzlxm = sgzlxm;
	}
	
	public String getSgzlsfz() {
		return sgzlsfz;
	}
	public void setSgzlsfz(String sgzlsfz) {
		this.sgzlsfz = sgzlsfz;
	}
	
	public String getSgzlyddh() {
		return sgzlyddh;
	}
	public void setSgzlyddh(String sgzlyddh) {
		this.sgzlyddh = sgzlyddh;
	}
	
	public String getSgaqxm() {
		return sgaqxm;
	}
	public void setSgaqxm(String sgaqxm) {
		this.sgaqxm = sgaqxm;
	}
	
	public String getSgaqsfz() {
		return sgaqsfz;
	}
	public void setSgaqsfz(String sgaqsfz) {
		this.sgaqsfz = sgaqsfz;
	}
	
	public String getSzaqyddh() {
		return szaqyddh;
	}
	public void setSzaqyddh(String szaqyddh) {
		this.szaqyddh = szaqyddh;
	}
	
	public String getSgdw() {
		return sgdw;
	}
	public void setSgdw(String sgdw) {
		this.sgdw = sgdw;
	}
	
	public String getLxr() {
		return lxr;
	}
	public void setLxr(String lxr) {
		this.lxr = lxr;
	}
	
	public String getSglxrdh() {
		return sglxrdh;
	}
	public void setSglxrdh(String sglxrdh) {
		this.sglxrdh = sglxrdh;
	}
	
	public String getJzsmc() {
		return jzsmc;
	}
	public void setJzsmc(String jzsmc) {
		this.jzsmc = jzsmc;
	}
	
	public String getJzszch() {
		return jzszch;
	}
	public void setJzszch(String jzszch) {
		this.jzszch = jzszch;
	}
	
	public String getJzsdh() {
		return jzsdh;
	}
	public void setJzsdh(String jzsdh) {
		this.jzsdh = jzsdh;
	}
	
	public String getJldw() {
		return jldw;
	}
	public void setJldw(String jldw) {
		this.jldw = jldw;
	}
	
	public String getJllxr() {
		return jllxr;
	}
	public void setJllxr(String jllxr) {
		this.jllxr = jllxr;
	}
	
	public String getJldwdh() {
		return jldwdh;
	}
	public void setJldwdh(String jldwdh) {
		this.jldwdh = jldwdh;
	}
	
	public String getXmzj() {
		return xmzj;
	}
	public void setXmzj(String xmzj) {
		this.xmzj = xmzj;
	}
	
	public String getZmzjzch() {
		return zmzjzch;
	}
	public void setZmzjzch(String zmzjzch) {
		this.zmzjzch = zmzjzch;
	}
	
	public String getXmzjdh() {
		return xmzjdh;
	}
	public void setXmzjdh(String xmzjdh) {
		this.xmzjdh = xmzjdh;
	}
	
	public String getKcdw() {
		return kcdw;
	}
	public void setKcdw(String kcdw) {
		this.kcdw = kcdw;
	}
	
	public String getKcdwlxr() {
		return kcdwlxr;
	}
	public void setKcdwlxr(String kcdwlxr) {
		this.kcdwlxr = kcdwlxr;
	}
	
	public String getKcdwlxrdh() {
		return kcdwlxrdh;
	}
	public void setKcdwlxrdh(String kcdwlxrdh) {
		this.kcdwlxrdh = kcdwlxrdh;
	}
	
	public String getSjdw() {
		return sjdw;
	}
	public void setSjdw(String sjdw) {
		this.sjdw = sjdw;
	}
	
	public String getSjdwlxr() {
		return sjdwlxr;
	}
	public void setSjdwlxr(String sjdwlxr) {
		this.sjdwlxr = sjdwlxr;
	}
	
	public String getSjdwlxrdh() {
		return sjdwlxrdh;
	}
	public void setSjdwlxrdh(String sjdwlxrdh) {
		this.sjdwlxrdh = sjdwlxrdh;
	}
	
	public String getStjg() {
		return stjg;
	}
	public void setStjg(String stjg) {
		this.stjg = stjg;
	}
	
	public String getStjglxr() {
		return stjglxr;
	}
	public void setStjglxr(String stjglxr) {
		this.stjglxr = stjglxr;
	}
	
	public String getStjgdh() {
		return stjgdh;
	}
	public void setStjgdh(String stjgdh) {
		this.stjgdh = stjgdh;
	}
	
	public String getGcdz() {
		return gcdz;
	}
	public void setGcdz(String gcdz) {
		this.gcdz = gcdz;
	}
	
	public String getYdmj() {
		return ydmj;
	}
	public void setYdmj(String ydmj) {
		this.ydmj = ydmj;
	}
	
	public String getLgq() {
		return lgq;
	}
	public void setLgq(String lgq) {
		this.lgq = lgq;
	}
	
	public String getYtq() {
		return ytq;
	}
	public void setYtq(String ytq) {
		this.ytq = ytq;
	}
	
	public String getLhq() {
		return lhq;
	}
	public void setLhq(String lhq) {
		this.lhq = lhq;
	}
	
	public String getFtq() {
		return ftq;
	}
	public void setFtq(String ftq) {
		this.ftq = ftq;
	}
	
	public String getNsq() {
		return nsq;
	}
	public void setNsq(String nsq) {
		this.nsq = nsq;
	}
	
	public String getBaq() {
		return baq;
	}
	public void setBaq(String baq) {
		this.baq = baq;
	}
	
	public String getGmxq() {
		return gmxq;
	}
	public void setGmxq(String gmxq) {
		this.gmxq = gmxq;
	}
	
	public String getPsxq() {
		return psxq;
	}
	public void setPsxq(String psxq) {
		this.psxq = psxq;
	}
	
	public String getDpxq() {
		return dpxq;
	}
	public void setDpxq(String dpxq) {
		this.dpxq = dpxq;
	}
	
	public String getLhxq() {
		return lhxq;
	}
	public void setLhxq(String lhxq) {
		this.lhxq = lhxq;
	}
	
	public String getZfzj() {
		return zfzj;
	}
	public void setZfzj(String zfzj) {
		this.zfzj = zfzj;
	}
	
	public String getGyzj() {
		return gyzj;
	}
	public void setGyzj(String gyzj) {
		this.gyzj = gyzj;
	}
	
	public String getJtzj() {
		return jtzj;
	}
	public void setJtzj(String jtzj) {
		this.jtzj = jtzj;
	}
	
	public String getSyzj() {
		return syzj;
	}
	public void setSyzj(String syzj) {
		this.syzj = syzj;
	}
	
	public String getWzzj() {
		return wzzj;
	}
	public void setWzzj(String wzzj) {
		this.wzzj = wzzj;
	}
	
	public String getQtzj() {
		return qtzj;
	}
	public void setQtzj(String qtzj) {
		this.qtzj = qtzj;
	}
	
	public String getBjrjlmjzh() {
		return bjrjlmjzh;
	}
	public void setBjrjlmjzh(String bjrjlmjzh) {
		this.bjrjlmjzh = bjrjlmjzh;
	}
	
	public String getCgc() {
		return cgc;
	}
	public void setCgc(String cgc) {
		this.cgc = cgc;
	}
	
	public String getGc() {
		return gc;
	}
	public void setGc(String gc) {
		this.gc = gc;
	}
	
	public String getDc() {
		return dc;
	}
	public void setDc(String dc) {
		this.dc = dc;
	}
	
	public String getJzcscgc() {
		return jzcscgc;
	}
	public void setJzcscgc(String jzcscgc) {
		this.jzcscgc = jzcscgc;
	}
	
	public String getJzcsgc() {
		return jzcsgc;
	}
	public void setJzcsgc(String jzcsgc) {
		this.jzcsgc = jzcsgc;
	}
	
	public String getJzcsdc() {
		return jzcsdc;
	}
	public void setJzcsdc(String jzcsdc) {
		this.jzcsdc = jzcsdc;
	}
	
	public String getJzgd() {
		return jzgd;
	}
	public void setJzgd(String jzgd) {
		this.jzgd = jzgd;
	}
	
	public String getDxsmj() {
		return dxsmj;
	}
	public void setDxsmj(String dxsmj) {
		this.dxsmj = dxsmj;
	}
	
	public String getDxscs() {
		return dxscs;
	}
	public void setDxscs(String dxscs) {
		this.dxscs = dxscs;
	}
	
	public String getJc() {
		return jc;
	}
	public void setJc(String jc) {
		this.jc = jc;
	}
	
	public String getZt() {
		return zt;
	}
	public void setZt(String zt) {
		this.zt = zt;
	}
	
	public String getJkkwsd() {
		return jkkwsd;
	}
	public void setJkkwsd(String jkkwsd) {
		this.jkkwsd = jkkwsd;
	}
	
	public String getJkkwmj() {
		return jkkwmj;
	}
	public void setJkkwmj(String jkkwmj) {
		this.jkkwmj = jkkwmj;
	}
	
	public String getGcjbyj() {
		return gcjbyj;
	}
	public void setGcjbyj(String gcjbyj) {
		this.gcjbyj = gcjbyj;
	}
	
	public String getGcjbej() {
		return gcjbej;
	}
	public void setGcjbej(String gcjbej) {
		this.gcjbej = gcjbej;
	}
	
	public String getGcjbsj() {
		return gcjbsj;
	}
	public void setGcjbsj(String gcjbsj) {
		this.gcjbsj = gcjbsj;
	}
	
	public String getBcsgmj() {
		return bcsgmj;
	}
	public void setBcsgmj(String bcsgmj) {
		this.bcsgmj = bcsgmj;
	}
	
	public String getHtzj() {
		return htzj;
	}
	public void setHtzj(String htzj) {
		this.htzj = htzj;
	}
	
	public String getHtkgrq() {
		return htkgrq;
	}
	public void setHtkgrq(String htkgrq) {
		this.htkgrq = htkgrq;
	}
	
	public String getHtjgrq() {
		return htjgrq;
	}
	public void setHtjgrq(String htjgrq) {
		this.htjgrq = htjgrq;
	}
	
	public String getSspf() {
		return sspf;
	}
	public void setSspf(String sspf) {
		this.sspf = sspf;
	}
	
	public String getBsspf() {
		return bsspf;
	}
	public void setBsspf(String bsspf) {
		this.bsspf = bsspf;
	}
	
	public String getBcsgzzmj() {
		return bcsgzzmj;
	}
	public void setBcsgzzmj(String bcsgzzmj) {
		this.bcsgzzmj = bcsgzzmj;
	}
	
	public String getCgd() {
		return cgd;
	}
	public void setCgd(String cgd) {
		this.cgd = cgd;
	}
	
	public String getBzd() {
		return bzd;
	}
	public void setBzd(String bzd) {
		this.bzd = bzd;
	}
	
	public String getSgxk() {
		return sgxk;
	}
	public void setSgxk(String sgxk) {
		this.sgxk = sgxk;
	}
	
	public String getTqkg() {
		return tqkg;
	}
	public void setTqkg(String tqkg) {
		this.tqkg = tqkg;
	}
	
	public String getWenhao() {
		return wenhao;
	}
	public void setWenhao(String wenhao) {
		this.wenhao = wenhao;
	}
	
	public String getTsfgc() {
		return tsfgc;
	}
	public void setTsfgc(String tsfgc) {
		this.tsfgc = tsfgc;
	}
	
	public String getJkzfgc() {
		return jkzfgc;
	}
	public void setJkzfgc(String jkzfgc) {
		this.jkzfgc = jkzfgc;
	}
	
	public String getJjc() {
		return jjc;
	}
	public void setJjc(String jjc) {
		this.jjc = jjc;
	}
	
	public String getTrjc() {
		return trjc;
	}
	public void setTrjc(String trjc) {
		this.trjc = trjc;
	}
	
	public String getQt() {
		return qt;
	}
	public void setQt(String qt) {
		this.qt = qt;
	}
	
	public String getGjhnt() {
		return gjhnt;
	}
	public void setGjhnt(String gjhnt) {
		this.gjhnt = gjhnt;
	}
	
	public String getQtqt() {
		return qtqt;
	}
	public void setQtqt(String qtqt) {
		this.qtqt = qtqt;
	}
	
	public String getGjg() {
		return gjg;
	}
	public void setGjg(String gjg) {
		this.gjg = gjg;
	}
	
	public String getWj() {
		return wj;
	}
	public void setWj(String wj) {
		this.wj = wj;
	}
	
	public String getSmjg() {
		return smjg;
	}
	public void setSmjg(String smjg) {
		this.smjg = smjg;
	}
	
	public String getZszx() {
		return zszx;
	}
	public void setZszx(String zszx) {
		this.zszx = zszx;
	}
	
	public String getJsmcgc() {
		return jsmcgc;
	}
	public void setJsmcgc(String jsmcgc) {
		this.jsmcgc = jsmcgc;
	}
	
	public String getMqmq() {
		return mqmq;
	}
	public void setMqmq(String mqmq) {
		this.mqmq = mqmq;
	}
	
	public String getMqpfm() {
		return mqpfm;
	}
	public void setMqpfm(String mqpfm) {
		this.mqpfm = mqpfm;
	}
	
	public String getWmfsgc() {
		return wmfsgc;
	}
	public void setWmfsgc(String wmfsgc) {
		this.wmfsgc = wmfsgc;
	}
	
	public String getTfgc() {
		return tfgc;
	}
	public void setTfgc(String tfgc) {
		this.tfgc = tfgc;
	}
	
	public String getKtgc() {
		return ktgc;
	}
	public void setKtgc(String ktgc) {
		this.ktgc = ktgc;
	}
	
	public String getZjjpsgc() {
		return zjjpsgc;
	}
	public void setZjjpsgc(String zjjpsgc) {
		this.zjjpsgc = zjjpsgc;
	}
	
	public String getDtgc() {
		return dtgc;
	}
	public void setDtgc(String dtgc) {
		this.dtgc = dtgc;
	}
	
	public String getJzdqgc() {
		return jzdqgc;
	}
	public void setJzdqgc(String jzdqgc) {
		this.jzdqgc = jzdqgc;
	}
	
	public String getXfgc() {
		return xfgc;
	}
	public void setXfgc(String xfgc) {
		this.xfgc = xfgc;
	}
	
	public String getZnhgc() {
		return znhgc;
	}
	public void setZnhgc(String znhgc) {
		this.znhgc = znhgc;
	}
	
	public String getRqgchs() {
		return rqgchs;
	}
	public void setRqgchs(String rqgchs) {
		this.rqgchs = rqgchs;
	}
	
	public String getRcgctyg() {
		return rcgctyg;
	}
	public void setRcgctyg(String rcgctyg) {
		this.rcgctyg = rcgctyg;
	}
	
	public String getHushu() {
		return hushu;
	}
	public void setHushu(String hushu) {
		this.hushu = hushu;
	}
	
	public String getTym() {
		return tym;
	}
	public void setTym(String tym) {
		this.tym = tym;
	}
	
	public String getSwgc() {
		return swgc;
	}
	public void setSwgc(String swgc) {
		this.swgc = swgc;
	}
	
	public String getQita() {
		return qita;
	}
	public void setQita(String qita) {
		this.qita = qita;
	}
	
	public String getBh() {
		return bh;
	}
	public void setBh(String bh) {
		this.bh = bh;
	}
	
	public String getGcbh() {
		return gcbh;
	}
	public void setGcbh(String gcbh) {
		this.gcbh = gcbh;
	}
	
	public String getLeixing() {
		return leixing;
	}
	public void setLeixing(String leixing) {
		this.leixing = leixing;
	}
	
	public String getJzssfzhm() {
		return jzssfzhm;
	}
	public void setJzssfzhm(String jzssfzhm) {
		this.jzssfzhm = jzssfzhm;
	}
	
	public String getXmzjsfzhm() {
		return xmzjsfzhm;
	}
	public void setXmzjsfzhm(String xmzjsfzhm) {
		this.xmzjsfzhm = xmzjsfzhm;
	}
	
	public String getXmbh() {
		return xmbh;
	}
	public void setXmbh(String xmbh) {
		this.xmbh = xmbh;
	}
	
	public String getSzdxm() {
		return szdxm;
	}
	public void setSzdxm(String szdxm) {
		this.szdxm = szdxm;
	}
	
	public String getFzdxm() {
		return fzdxm;
	}
	public void setFzdxm(String fzdxm) {
		this.fzdxm = fzdxm;
	}
	
	public String getZtz() {
		return ztz;
	}
	public void setZtz(String ztz) {
		this.ztz = ztz;
	}
	
	public String getJg() {
		return jg;
	}
	public void setJg(String jg) {
		this.jg = jg;
	}
	
	public String getSz_qtypgc() {
		return sz_qtypgc;
	}
	public void setSz_qtypgc(String sz_qtypgc) {
		this.sz_qtypgc = sz_qtypgc;
	}
	
	public String getSz_dxgdgc() {
		return sz_dxgdgc;
	}
	public void setSz_dxgdgc(String sz_dxgdgc) {
		this.sz_dxgdgc = sz_dxgdgc;
	}
	
	public String getSz_dqhpgc_l() {
		return sz_dqhpgc_l;
	}
	public void setSz_dqhpgc_l(String sz_dqhpgc_l) {
		this.sz_dqhpgc_l = sz_dqhpgc_l;
	}
	
	public String getSz_dqhpgc_w() {
		return sz_dqhpgc_w;
	}
	public void setSz_dqhpgc_w(String sz_dqhpgc_w) {
		this.sz_dqhpgc_w = sz_dqhpgc_w;
	}
	
	public String getSz_dqhpgc_h() {
		return sz_dqhpgc_h;
	}
	public void setSz_dqhpgc_h(String sz_dqhpgc_h) {
		this.sz_dqhpgc_h = sz_dqhpgc_h;
	}
	
	public String getSz_dlgdgc() {
		return sz_dlgdgc;
	}
	public void setSz_dlgdgc(String sz_dlgdgc) {
		this.sz_dlgdgc = sz_dlgdgc;
	}
	
	public String getSz_rjclgc() {
		return sz_rjclgc;
	}
	public void setSz_rjclgc(String sz_rjclgc) {
		this.sz_rjclgc = sz_rjclgc;
	}
	
	public String getSz_wscl() {
		return sz_wscl;
	}
	public void setSz_wscl(String sz_wscl) {
		this.sz_wscl = sz_wscl;
	}
	
	public String getSz_wncl() {
		return sz_wncl;
	}
	public void setSz_wncl(String sz_wncl) {
		this.sz_wncl = sz_wncl;
	}
	
	public String getSz_scptgc() {
		return sz_scptgc;
	}
	public void setSz_scptgc(String sz_scptgc) {
		this.sz_scptgc = sz_scptgc;
	}
	
	public String getSz_qt() {
		return sz_qt;
	}
	public void setSz_qt(String sz_qt) {
		this.sz_qt = sz_qt;
	}
	
	public String getZszx_gzzmj() {
		return zszx_gzzmj;
	}
	public void setZszx_gzzmj(String zszx_gzzmj) {
		this.zszx_gzzmj = zszx_gzzmj;
	}
	
	public String getZszx_tze() {
		return zszx_tze;
	}
	public void setZszx_tze(String zszx_tze) {
		this.zszx_tze = zszx_tze;
	}
	
	public String getZszx_kgmj() {
		return zszx_kgmj;
	}
	public void setZszx_kgmj(String zszx_kgmj) {
		this.zszx_kgmj = zszx_kgmj;
	}
	
	public String getZszx_nbzs_pfm() {
		return zszx_nbzs_pfm;
	}
	public void setZszx_nbzs_pfm(String zszx_nbzs_pfm) {
		this.zszx_nbzs_pfm = zszx_nbzs_pfm;
	}
	
	public String getZszx_wbzs_pfm() {
		return zszx_wbzs_pfm;
	}
	public void setZszx_wbzs_pfm(String zszx_wbzs_pfm) {
		this.zszx_wbzs_pfm = zszx_wbzs_pfm;
	}
	
	public String getZszx_zmdq() {
		return zszx_zmdq;
	}
	public void setZszx_zmdq(String zszx_zmdq) {
		this.zszx_zmdq = zszx_zmdq;
	}
	
	public String getZszx_sngps() {
		return zszx_sngps;
	}
	public void setZszx_sngps(String zszx_sngps) {
		this.zszx_sngps = zszx_sngps;
	}
	
	public String getZszx_xfgc() {
		return zszx_xfgc;
	}
	public void setZszx_xfgc(String zszx_xfgc) {
		this.zszx_xfgc = zszx_xfgc;
	}
	
	public String getZszx_tfgc() {
		return zszx_tfgc;
	}
	public void setZszx_tfgc(String zszx_tfgc) {
		this.zszx_tfgc = zszx_tfgc;
	}
	
	public String getZszx_dtgc() {
		return zszx_dtgc;
	}
	public void setZszx_dtgc(String zszx_dtgc) {
		this.zszx_dtgc = zszx_dtgc;
	}
	
	public String getZszx_rqgc() {
		return zszx_rqgc;
	}
	public void setZszx_rqgc(String zszx_rqgc) {
		this.zszx_rqgc = zszx_rqgc;
	}
	
	public String getZszx_znhgc() {
		return zszx_znhgc;
	}
	public void setZszx_znhgc(String zszx_znhgc) {
		this.zszx_znhgc = zszx_znhgc;
	}
	
	public String getZszx_fsgc() {
		return zszx_fsgc;
	}
	public void setZszx_fsgc(String zszx_fsgc) {
		this.zszx_fsgc = zszx_fsgc;
	}
	
	public String getZszx_qt() {
		return zszx_qt;
	}
	public void setZszx_qt(String zszx_qt) {
		this.zszx_qt = zszx_qt;
	}
	
	public String getZszx_htzj() {
		return zszx_htzj;
	}
	public void setZszx_htzj(String zszx_htzj) {
		this.zszx_htzj = zszx_htzj;
	}
	
	public String getJssg_type() {
		return jssg_type;
	}
	public void setJssg_type(String jssg_type) {
		this.jssg_type = jssg_type;
	}
	public String getQueryId() {
		return queryId;
	}
	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getZjbh() {
		return zjbh;
	}
	public void setZjbh(String zjbh) {
		this.zjbh = zjbh;
	}
	public List<PermitApplyUnit> getApplyUnit() {
		return applyUnit;
	}
	public void setApplyUnit(List<PermitApplyUnit> applyUnit) {
		this.applyUnit = applyUnit;
	}
}
