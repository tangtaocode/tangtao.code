package net.risesoft.beans.onlineservice;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import antlr.StringUtils;
import net.risesoft.beans.govpublic.WindowsInfo;
/**
 * @事项信息
 * 
 * @author 张坤
 * @Date 2013-1-24 上午10:50:24
 */
@Entity
@Table(name="XZQL_XZSP_BASE")
public class ApproveItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5286211860031824281L;
	private String itemid;  //事项ID:用于串联整个业务
	private String approveitemname;  //行政审批事项名称
	private Integer orderno;  //序号:用于显示排序
	private String departmentid;  //部门ID(暂时不用，有关系表关联)
	private String approveitembm;  //行政审批编码:机构代码+种类码+三位行政审批事项编号 (种类码：1=行政许可，2=非行政许可，4=行政服务；事项编码：3位，如001号、002号等)
	private String approveitemcontent;  //行政审批内容:对审批事项的具体细化，体现其实质内容。如无特别内容，可填写该事项名称。
	private String approveitembasis;  //行政审批设定依据
	private String quantitylimit;  //行政审批数量及方式:注明行政审批有无数量限制，同时注明申请审批的方式。有数量限制的，应当采用公开、公平的方式予以审批。
	private String declarecondition;  //行政审批条件：填写行政机关准予申请审批的法定条件或标准
	private String rereq;  //申请材料：填写说明：1、材料类别分为：A格式化数据、B电子版材料、C纸质材料、D外部数据;2、数据关联是指：材料类别为外部数据时，应注明来源于何种系统。
	private byte[] reform;  //申请表格:附件，多个表格整成一个word附件
	private String acceptjiguan;  //申请受理机关
	private String decideorg;  //决定机关
	private String process;  //行政审批程序:填写申请和作出行政审批决定的主要步骤、环节。采用告知承诺制方式或网上审批方式实施审批的，应在此予以说明。
	private String innerflow;  //行政审批内部流程
	private byte[]flowcharts;  //申请流程图
	private String declaredesc;  //申办流程图说明
	private String timelimit;  //法定办理时限
	private String promiselimittime;  //承诺办理时限
	private String zhengjianmingcheng;  //行政审批证件及有效期限
	private String xiaoli;  //行政审批的法律效力
	private String legaltype;  //法规类型：法律、国家部位、地方法规
	private String chargeinfo;  //收费
	private String nianshen;  //年审
	private String legalremedy;  //法律救济
	private String bureautype;  //事项类型：行政许可、非行政许可、行政服务
	private String approveobject;  //行政审批对象：个人、企业、团体
	private String approveitemtype;  //行政审批类型：承诺件（承诺一定时间办完）、即办件（即时办完）
	private String remark;  //备注
	private Date handletime;  //操作时间
	private String handleperson;  //操作人
	private String status;  //状态：1有效；0无效；2待确认
	private String handlestate;  //操作状态：0作废；1已提交；2暂存；
	private String reformname;  //申请表格附件名称
	private String flowchartsname;  //申请流程图附件名称
	private String doctypeguid;  //材料对应证照guid串。1对多关系。对应t_bdex_doctype表
	private String doctypename;  //材料对应证照NAME串。1对多关系。对应t_bdex_doctype表
	private String ischangetoxzsp;  //是否搬迁至行政审批；0未搬迁，1搬迁成功；2搬迁失败；
	private String ischangetospjc;  //是否搬迁至审批监察前置机；0未搬迁，1搬迁成功；2搬迁失败；
	private byte[] basisfile;  //权力依据附件
	private String basisname;  //权力依据附件名称
	private String handlepersonguid;  //操作人GUID
	private String itemclass;  //事项类别：生育（个人）、土地房产（个人）等
	private String address;  //审批办理地点
	private String accepttime;  //受理时间段
	private String linktype;  //联系方式
	private String catalogsguid;  //父节点大类GUID，如果为空则直接对应部门下
	private String adminorgid;  //主管部门
	private String bureauName;//部门名称
	private String timelimitdesc;//法定时限描述
	private String timelimitunit;//法定时限单位：G工作日、Z自然日
	private String promiselimittimedesc;//承诺时限描述
	private String promiselimittimeunit;//承诺时限单位：G工作日、Z自然日
	private String approveplace;//是否在审批系统办理
	private List<GuideFileType> guideFilTypeeList;//材料类型
	private List<LawsBean> lawAppList;//依据法律法规
	private List<ApproveItemTabTemp> appTabTempList;//表格下载
	private List<GuideFile> guideFileList;//存放没有材料类型的材料
	private ApproveItemProvince itemProvince;//事项省部分扩展表
	private int windowFlow;//窗口办理流程
	private ApproveItemExtend extend;
	private String applicationform;//网上申报位置：1、罗湖审批平台；2、其他系统；
	private String applicationformurl;//如果为其他系统，其他系统申报网址
	private String onlineapplyingservice;//是否提供在线申报0不提供，1提供
	private String type;//1:大项；2：小项
	
	private List<WindowsInfo> windList;
	private String materialStr;          //所需材料-Jon
	
	//lusihui
	private String gkType="";//公开类型
	private String jdjg="";//监督关机
	private String reformnameBc;//申请表格补充
	private int powerFlow;//（权力运行流程）
	
	@Transient
	public int getPowerFlow() {
		return powerFlow;
	}
	public void setPowerFlow(int powerFlow) {
		this.powerFlow = powerFlow;
	}
	@Column
	public String getReformnameBc() {
		return reformnameBc;
	}
	public void setReformnameBc(String reformnameBc) {
		this.reformnameBc = reformnameBc;
	}
	@Column
	public String getGkType() {
		return gkType;
	}
	public void setGkType(String gkType) {
		this.gkType = gkType;
	}
	@Column
	public String getJdjg() {
		return jdjg;
	}
	public void setJdjg(String jdjg) {
		this.jdjg = jdjg;
	}
	@Transient
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Transient
	public String getOnlineapplyingservice() {
		return onlineapplyingservice;
	}
	public void setOnlineapplyingservice(String onlineapplyingservice) {
		this.onlineapplyingservice = onlineapplyingservice;
	}
	@Transient
	public List<WindowsInfo> getWindList() {
		return windList;
	}
	public void setWindList(List<WindowsInfo> windList) {
		this.windList = windList;
	}
	@Transient
	public String getApplicationform() {
		return applicationform;
	}
	public void setApplicationform(String applicationform) {
		this.applicationform = applicationform;
	}
	@Transient
	public String getApplicationformurl() {
		return applicationformurl;
	}
	public void setApplicationformurl(String applicationformurl) {
		this.applicationformurl = applicationformurl;
	}
	@Transient
	public ApproveItemExtend getExtend() {
		return extend;
	}
	public void setExtend(ApproveItemExtend extend) {
		this.extend = extend;
	}
	@Transient
	public int getWindowFlow() {
		return windowFlow;
	}
	public void setWindowFlow(int windowFlow) {
		this.windowFlow = windowFlow;
	}
	@Transient
	public ApproveItemProvince getItemProvince() {
		return itemProvince;
	}
	public void setItemProvince(ApproveItemProvince itemProvince) {
		this.itemProvince = itemProvince;
	}
	@Transient
	public String getApproveplace() {
		return approveplace;
	}
	public void setApproveplace(String approveplace) {
		this.approveplace = approveplace;
	}
	@Transient
	public String getBureauName() {
		return bureauName;
	}
	public void setBureauName(String bureauName) {
		this.bureauName = bureauName;
	}
	@Transient
	public List<GuideFile> getGuideFileList() {
		return guideFileList;
	}
	public void setGuideFileList(List<GuideFile> guideFileList) {
		this.guideFileList = guideFileList;
	}
	@Transient
	public List<GuideFileType> getGuideFilTypeeList() {
		return guideFilTypeeList;
	}
	public void setGuideFilTypeeList(List<GuideFileType> guideFilTypeeList) {
		this.guideFilTypeeList = guideFilTypeeList;
	}
	@Transient
	public List<LawsBean> getLawAppList() {
		return lawAppList;
	}
	public void setLawAppList(List<LawsBean> lawAppList) {
		this.lawAppList = lawAppList;
	}
	@Transient
	public List<ApproveItemTabTemp> getAppTabTempList() {
		return appTabTempList;
	}
	public void setAppTabTempList(List<ApproveItemTabTemp> appTabTempList) {
		this.appTabTempList = appTabTempList;
	}
	@Id
	public String getItemid() {
		return itemid;
	}
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	@Column
	public String getApproveitemname() {
		return approveitemname;
	}
	public void setApproveitemname(String approveitemname) {
		this.approveitemname = approveitemname;
	}
	@Column
	public Integer getOrderno() {
		return orderno;
	}
	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}
	@Column
	public String getDepartmentid() {
		return departmentid;
	}
	public void setDepartmentid(String departmentid) {
		this.departmentid = departmentid;
	}
	@Column
	public String getApproveitembm() {
		return approveitembm;
	}
	public void setApproveitembm(String approveitembm) {
		this.approveitembm = approveitembm;
	}
	@Column
	public String getApproveitemcontent() {
		return approveitemcontent;
	}
	public void setApproveitemcontent(String approveitemcontent) {
		this.approveitemcontent = approveitemcontent;
	}
	@Column
	public String getApproveitembasis() {
		return approveitembasis;
	}
	public void setApproveitembasis(String approveitembasis) {
		this.approveitembasis = approveitembasis;
	}
	@Column
	public String getQuantitylimit() {
		return quantitylimit;
	}
	public void setQuantitylimit(String quantitylimit) {
		this.quantitylimit = quantitylimit;
	}
	@Column
	public String getDeclarecondition() {
		return declarecondition;
	}
	public void setDeclarecondition(String declarecondition) {
		this.declarecondition = declarecondition;
	}
	@Column
	public String getRereq() {
		return rereq;
	}
	public void setRereq(String rereq) {
		this.rereq = rereq;
	}
	@Lob 
	@Basic(fetch = FetchType.LAZY) 
	@Column(name = "reform", columnDefinition = "BLOB",nullable=true) 
	public byte[] getReform() {
		return reform;
	}
	public void setReform(byte[] reform) {
		this.reform = reform;
	}
	@Column
	public String getAcceptjiguan() {
		return acceptjiguan;
	}
	public void setAcceptjiguan(String acceptjiguan) {
		this.acceptjiguan = acceptjiguan;
	}
	@Column
	public String getDecideorg() {
		return decideorg;
	}
	public void setDecideorg(String decideorg) {
		this.decideorg = decideorg;
	}
	@Column
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
	@Column
	public String getInnerflow() {
		return innerflow;
	}
	public void setInnerflow(String innerflow) {
		this.innerflow = innerflow;
	}
	@Lob 
	@Basic(fetch = FetchType.LAZY) 
	@Column(name = "flowcharts", columnDefinition = "BLOB",nullable=true) 
	public byte[] getFlowcharts() {
		return flowcharts;
	}
	public void setFlowcharts(byte[] flowcharts) {
		this.flowcharts = flowcharts;
	}
	@Column
	public String getDeclaredesc() {
		return declaredesc;
	}
	public void setDeclaredesc(String declaredesc) {
		this.declaredesc = declaredesc;
	}
	@Column
	public String getTimelimit() {
		return timelimit;
	}
	public void setTimelimit(String timelimit) {
		this.timelimit = timelimit;
	}
	@Column
	public String getPromiselimittime() {
		return promiselimittime;
	}
	public void setPromiselimittime(String promiselimittime) {
		this.promiselimittime = promiselimittime;
	}
	@Column
	public String getZhengjianmingcheng() {
		return zhengjianmingcheng;
	}
	public void setZhengjianmingcheng(String zhengjianmingcheng) {
		this.zhengjianmingcheng = zhengjianmingcheng;
	}
	@Column
	public String getXiaoli() {
		return xiaoli;
	}
	public void setXiaoli(String xiaoli) {
		this.xiaoli = xiaoli;
	}
	@Column
	public String getLegaltype() {
		return legaltype;
	}
	public void setLegaltype(String legaltype) {
		this.legaltype = legaltype;
	}
	@Column
	public String getChargeinfo() {
		return chargeinfo;
	}
	public void setChargeinfo(String chargeinfo) {
		this.chargeinfo = chargeinfo;
	}
	@Column
	public String getNianshen() {
		return nianshen;
	}
	@Transient
	public String getNianshenStr() {
		if("0".equals(nianshen)){
			return "否";
		}else if("1".equals(nianshen)){
			return "是";
		}
		return nianshen;
	}
	
	public void setNianshen(String nianshen) {
		this.nianshen = nianshen;
	}
	@Column
	public String getLegalremedy() {
		return legalremedy;
	}
	public void setLegalremedy(String legalremedy) {
		this.legalremedy = legalremedy;
	}
	@Column
	public String getBureautype() {
		return bureautype;
	}
	public void setBureautype(String bureautype) {
		this.bureautype = bureautype;
	}
	@Column
	public String getApproveobject() {
		return approveobject;
	}
	public void setApproveobject(String approveobject) {
		this.approveobject = approveobject;
	}
	@Column
	public String getApproveitemtype() {
		return approveitemtype;
	}
	public void setApproveitemtype(String approveitemtype) {
		this.approveitemtype = approveitemtype;
	}
	@Column
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column
	public Date getHandletime() {
		return handletime;
	}
	public void setHandletime(Date handletime) {
		this.handletime = handletime;
	}
	@Column
	public String getHandleperson() {
		return handleperson;
	}
	public void setHandleperson(String handleperson) {
		this.handleperson = handleperson;
	}
	@Column
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column
	public String getHandlestate() {
		return handlestate;
	}
	public void setHandlestate(String handlestate) {
		this.handlestate = handlestate;
	}
	@Column
	public String getReformname() {
		return reformname;
	}
	public void setReformname(String reformname) {
		this.reformname = reformname;
	}
	@Column
	public String getFlowchartsname() {
		return flowchartsname;
	}
	public void setFlowchartsname(String flowchartsname) {
		this.flowchartsname = flowchartsname;
	}
	@Lob 
	@Basic(fetch = FetchType.EAGER) 
	@Column(name="doctypeguid", columnDefinition="CLOB", nullable=true) 
	public String getDoctypeguid() {
		return doctypeguid;
	}
	public void setDoctypeguid(String doctypeguid) {
		this.doctypeguid = doctypeguid;
	}
	@Lob 
	@Basic(fetch = FetchType.EAGER) 
	@Column(name="doctypename", columnDefinition="CLOB", nullable=true) 
	public String getDoctypename() {
		return doctypename;
	}
	public void setDoctypename(String doctypename) {
		this.doctypename = doctypename;
	}
	@Column
	public String getIschangetoxzsp() {
		return ischangetoxzsp;
	}
	public void setIschangetoxzsp(String ischangetoxzsp) {
		this.ischangetoxzsp = ischangetoxzsp;
	}
	@Column
	public String getIschangetospjc() {
		return ischangetospjc;
	}
	public void setIschangetospjc(String ischangetospjc) {
		this.ischangetospjc = ischangetospjc;
	}
	@Lob 
	@Basic(fetch = FetchType.LAZY) 
	@Column(name = "basisfile", columnDefinition = "BLOB",nullable=true) 
	public byte[] getBasisfile() {
		return basisfile;
	}
	public void setBasisfile(byte[] basisfile) {
		this.basisfile = basisfile;
	}
	@Column
	public String getBasisname() {
		return basisname;
	}
	public void setBasisname(String basisname) {
		this.basisname = basisname;
	}
	@Column
	public String getHandlepersonguid() {
		return handlepersonguid;
	}
	public void setHandlepersonguid(String handlepersonguid) {
		this.handlepersonguid = handlepersonguid;
	}
	@Column
	public String getItemclass() {
		return itemclass;
	}
	public void setItemclass(String itemclass) {
		this.itemclass = itemclass;
	}
	@Column
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column
	public String getAccepttime() {
		return accepttime;
	}
	public void setAccepttime(String accepttime) {
		this.accepttime = accepttime;
	}
	@Column
	public String getLinktype() {
		return linktype;
	}
	public void setLinktype(String linktype) {
		this.linktype = linktype;
	}
	@Column
	public String getCatalogsguid() {
		return catalogsguid;
	}
	public void setCatalogsguid(String catalogsguid) {
		this.catalogsguid = catalogsguid;
	}
	@Column
	public String getAdminorgid() {
		return adminorgid;
	}
	public void setAdminorgid(String adminorgid) {
		this.adminorgid = adminorgid;
	}
	@Column
	public String getTimelimitdesc() {
		return timelimitdesc;
	}
	public void setTimelimitdesc(String timelimitdesc) {
		this.timelimitdesc = timelimitdesc;
	}
	@Column
	public String getTimelimitunit() {
		return timelimitunit;
	}
	public void setTimelimitunit(String timelimitunit) {
		this.timelimitunit = timelimitunit;
	}
	@Column
	public String getPromiselimittimedesc() {
		return org.apache.commons.lang.StringUtils.trim(promiselimittimedesc);
	}
	public void setPromiselimittimedesc(String promiselimittimedesc) {
		this.promiselimittimedesc = promiselimittimedesc;
	}
	@Column
	public String getPromiselimittimeunit() {
		return org.apache.commons.lang.StringUtils.trim(promiselimittimeunit);
	}
	public void setPromiselimittimeunit(String promiselimittimeunit) {
		this.promiselimittimeunit = promiselimittimeunit;
	}
	@Column
	public String getMaterialStr() {
		return materialStr;
	}
	public void setMaterialStr(String materialStr) {
		this.materialStr = materialStr;
	}
}
