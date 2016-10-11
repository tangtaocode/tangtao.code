package net.risesoft.beans.onlineservice;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="T_SHENBAN")
@IdClass(ApproveItemApplyPK.class)
public class ApproveItemApply implements Serializable{
	
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = -5039085104766549155L;
	private String ywlsh;  //主键，调用函数createYWLSH()生成
	private Integer sjbbh;  //数据的版本号，默认为1，如果报送数据后数据发生改变需要重报的，以2、3、4…每次加1的方式填写版本号，务必谨慎使用。
	private String spsxbh;  //行政区划+单位组织机构代码+3位流水号。审批事项编号由监察部门统一编号，不可为空
	private String spsxzxbh;  //许可事项子项编号，由监察部门统一编号，不可空
	private String yxtywlsh;  //本业务在原系统中的业务流水号，必须是唯一的，不可空
	private String spsxmc;  //本审批事项的名称，假如审批事项有子项，指审批事项子项名称，不可空
	private String sqrlx;  //申请人类型代码。参照附录5.4广东省网上办事大厅信息分类与代码。"
	private String sqrmc;  //申请人为个人时，填写姓名；	申请人为企业（机关单位）时，填写单位名称。"
	private String sqrzjhm;  //申请人为个人时，填写身份证件号码；	申请人为企业（机关单位）时，填写组织机构代码或注册登记号。
								//注册登记号是指由法律、法规规定的组织机构登记机关或批准机关核发的有效证照或批文上的注册号、登记号或文号。预留公民网页使用。
	private String lxrxm;  //如果是本人办理，联系人即为申请人；	如果是企业（机关单位）办理，联系人为申请单位经办人。"
	private String lxrzjlx;  //证件类型代码。	参照附录5.4广东省网上办事大厅信息分类与代码。"
	private String lxrsfzjhm;  //联系人身份证件号码。预留公民网页使用。
	private String lxrsj;  //联系人手机号码。用于向经办人发送短信回执。
	private String lxryx;  //联系人邮箱
	private String sbxmmc;  //如果是行政审批事项，填写申请审批的项目具体名称；如果是社会事务服务事项，填写事项名称。"
	private String sbclqd;  //申请时提交材料清单。多个材料时，可用半角分号隔开。
	private String tjfs;  //申办资料提交方式，填写代码。	参照附录5.4广东省网上办事大厅信息分类与代码。"
	private String sbhzh;  //申请人提出申办后获取的回执号。申办回执号可用于查询办事进度和结果，可以和申请信息编号一样。"
	private Timestamp sbsj;  //申请人提交申请时间
	private String sbjtwd;  //网上申请时，对应申请人提出申办的网上办事窗口名称，包括：广东省网上办事大厅深圳市罗湖分厅，XX部门窗口，XX分厅。
							//实体办理时，对应实体窗口名称。实体窗口申请时，对应受理具体地点。"
	private String xzqhdm;  //申办业务发生所在行政区划代码。	参照GBT 2260。"
	private String ysblsh;  //如果是补交补正，则对应原申办流水号。（预留，有待商榷）
	private String bz;  //备注信息
	private String byzd;  //备用字段，用于扩展使用
	private String departid;  //组织机构代码
	private String datasource;  //数据来源:如人力资源局为RLZYJ
	private String sblsh;//市里统一申报流水号
	@Id
	public String getYwlsh() {
		return ywlsh;
	}
	public void setYwlsh(String ywlsh) {
		this.ywlsh = ywlsh;
	}
	@Id
	public Integer getSjbbh() {
		return sjbbh;
	}
	public void setSjbbh(Integer sjbbh) {
		this.sjbbh = sjbbh;
	}
	@Column
	public String getSpsxbh() {
		return spsxbh;
	}
	public void setSpsxbh(String spsxbh) {
		this.spsxbh = spsxbh;
	}
	@Column
	public String getSpsxzxbh() {
		return spsxzxbh;
	}
	public void setSpsxzxbh(String spsxzxbh) {
		this.spsxzxbh = spsxzxbh;
	}
	@Column
	public String getYxtywlsh() {
		return yxtywlsh;
	}
	public void setYxtywlsh(String yxtywlsh) {
		this.yxtywlsh = yxtywlsh;
	}
	@Column
	public String getSpsxmc() {
		return spsxmc;
	}
	public void setSpsxmc(String spsxmc) {
		this.spsxmc = spsxmc;
	}
	@Column
	public String getSqrlx() {
		return sqrlx;
	}
	public void setSqrlx(String sqrlx) {
		this.sqrlx = sqrlx;
	}
	@Column
	public String getSqrmc() {
		return sqrmc;
	}
	public void setSqrmc(String sqrmc) {
		this.sqrmc = sqrmc;
	}
	@Column
	public String getSqrzjhm() {
		return sqrzjhm;
	}
	public void setSqrzjhm(String sqrzjhm) {
		this.sqrzjhm = sqrzjhm;
	}
	@Column
	public String getLxrxm() {
		return lxrxm;
	}
	public void setLxrxm(String lxrxm) {
		this.lxrxm = lxrxm;
	}
	@Column
	public String getLxrzjlx() {
		return lxrzjlx;
	}
	public void setLxrzjlx(String lxrzjlx) {
		this.lxrzjlx = lxrzjlx;
	}
	@Column
	public String getLxrsfzjhm() {
		return lxrsfzjhm;
	}
	public void setLxrsfzjhm(String lxrsfzjhm) {
		this.lxrsfzjhm = lxrsfzjhm;
	}
	@Column
	public String getLxrsj() {
		return lxrsj;
	}
	public void setLxrsj(String lxrsj) {
		this.lxrsj = lxrsj;
	}
	@Column
	public String getLxryx() {
		return lxryx;
	}
	public void setLxryx(String lxryx) {
		this.lxryx = lxryx;
	}
	@Column
	public String getSbxmmc() {
		return sbxmmc;
	}
	public void setSbxmmc(String sbxmmc) {
		this.sbxmmc = sbxmmc;
	}
	@Column
	public String getSbclqd() {
		return sbclqd;
	}
	public void setSbclqd(String sbclqd) {
		this.sbclqd = sbclqd;
	}
	@Column
	public String getTjfs() {
		return tjfs;
	}
	public void setTjfs(String tjfs) {
		this.tjfs = tjfs;
	}
	@Column
	public String getSbhzh() {
		return sbhzh;
	}
	public void setSbhzh(String sbhzh) {
		this.sbhzh = sbhzh;
	}
	@Column
	public Timestamp getSbsj() {
		return sbsj;
	}
	public void setSbsj(Timestamp sbsj) {
		this.sbsj = sbsj;
	}
	@Column
	public String getSbjtwd() {
		return sbjtwd;
	}
	public void setSbjtwd(String sbjtwd) {
		this.sbjtwd = sbjtwd;
	}
	@Column
	public String getXzqhdm() {
		return xzqhdm;
	}
	public void setXzqhdm(String xzqhdm) {
		this.xzqhdm = xzqhdm;
	}
	@Column
	public String getYsblsh() {
		return ysblsh;
	}
	public void setYsblsh(String ysblsh) {
		this.ysblsh = ysblsh;
	}
	@Column
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	@Column
	public String getByzd() {
		return byzd;
	}
	public void setByzd(String byzd) {
		this.byzd = byzd;
	}
	@Column
	public String getDepartid() {
		return departid;
	}
	public void setDepartid(String departid) {
		this.departid = departid;
	}
	@Column
	public String getDatasource() {
		return datasource;
	}
	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}
	@Column
	public String getSblsh() {
		return sblsh;
	}
	public void setSblsh(String sblsh) {
		this.sblsh = sblsh;
	}
	
}
