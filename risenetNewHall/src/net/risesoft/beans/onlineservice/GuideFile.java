package net.risesoft.beans.onlineservice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;
/**
 * 
  * @ClassName: GuideFile
  * @Description: 审批材料清单
  * @author Comsys-zhangkun
  * @date Jun 1, 2013 5:03:17 PM
  *
 */
@Entity
@Table(name="XZQL_XZSP_LISTS")
public class GuideFile implements Serializable{
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 5093411935053963603L;
	private String id;  //主键ID
	private String itemid;  //事项ID
	private Integer orderno;  //序号：用于排序
	private String type;  //材料分类
	private String describe;  //材料描述
	private String materialname;  //材料名称
	private String doctypeguid;  //材料对应证照guid串。1对多关系。对应t_bdex_doctype表
	private String materiallx;  //材料类型：0扫描件；1申请表格；
	private String doctypename;  //材料对应证照NAME串。1对多关系。对应t_bdex_doctype表
	private String online_essential;  //网上申报是否必填项：0否、1是
	
	//瞬态
	private List<WebApplyUpFile> fileList = new ArrayList<WebApplyUpFile>();
	private List<WebApplyFileDoctype> doctypeList;
	private String code;//材料编码：按相应规则编码(材料分类表:XZQL_XZSP_DOCTYPE)
	private String name;//分类名称：身份证、营业许可证等(材料分类表:XZQL_XZSP_DOCTYPE)
	private String share_code;//提供相关编号：多个编号用半角逗号隔开;如“个人社保号，社保电脑号”(材料分类表:XZQL_XZSP_DOCTYPE)
	private String code_model;//编号样例：深建节验[2013]065号等(材料分类表:XZQL_XZSP_DOCTYPE)
	private String guid;//材料分类表主键
	private String cardid;//证件编号
	private String isok;//是否已实现接口：0否、1是(材料分类表:XZQL_XZSP_DOCTYPE)
	private String bzyj;//补正意见(SPM_BZYJ_LOG)
	private String bz;//是否被标识为已提交 0：未被标志，1：已被标识
	

	@Transient
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	@Transient
	public String getBzyj() {
		return bzyj;
	}
	public void setBzyj(String bzyj) {
		this.bzyj = bzyj;
	}
	@Transient
	public String getIsok() {
		return isok;
	}
	public void setIsok(String isok) {
		this.isok = isok;
	}
	@Transient
	public String getCardid() {
		return cardid;
	}
	public void setCardid(String cardid) {
		this.cardid = cardid;
	}
	@Transient
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Transient
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Transient
	public String getShare_code() {
		return share_code;
	}
	public void setShare_code(String share_code) {
		this.share_code = share_code;
	}
	@Transient
	public String getCode_model() {
		return code_model;
	}
	public void setCode_model(String code_model) {
		this.code_model = code_model;
	}
	@Transient
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	@Transient
	public List<WebApplyFileDoctype> getDoctypeList() {
		return doctypeList;
	}
	public void setDoctypeList(List<WebApplyFileDoctype> doctypeList) {
		this.doctypeList = doctypeList;
	}
	@Transient
	public List<WebApplyUpFile> getFileList() {
		return fileList;
	}
	public void setFileList(List<WebApplyUpFile> fileList) {
		this.fileList = fileList;
	}
	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column
	public String getItemid() {
		return itemid;
	}
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	@Column
	public Integer getOrderno() {
		return orderno;
	}
	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}
	@Column
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Column
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	@Column
	public String getMaterialname() {
		return materialname;
	}
	public void setMaterialname(String materialname) {
		this.materialname = materialname;
	}
	@Column
	public String getMateriallx() {
		return materiallx;
	}
	public void setMateriallx(String materiallx) {
		this.materiallx = materiallx;
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
	
	@Lob
	@Basic(fetch = FetchType.EAGER)
	@Column(name="doctypeguid", columnDefinition="CLOB", nullable=true) 
	public String getDoctypeguid() {
		return doctypeguid;
	}
	public void setDoctypeguid(String doctypeguid) {
		this.doctypeguid = doctypeguid;
	}
	public String getOnline_essential() {
		return online_essential;
	}
	public void setOnline_essential(String online_essential) {
		this.online_essential = online_essential;
	}
	
}
