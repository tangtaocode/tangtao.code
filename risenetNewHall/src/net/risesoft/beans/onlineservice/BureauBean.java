package net.risesoft.beans.onlineservice;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 
  * @ClassName: BureauBean
  * @Description: 部门信息
  * @author Comsys-zhangkun
  * @date Jun 24, 2013 9:32:35 AM
  *
 */
@Entity
@Table(name="SPM_BUREAU")
public class BureauBean implements Serializable{
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = -4955056053395748709L;
	private String bureauguid;  //委办局GUID
	private String department_guids;  //委办局下面所有部门departmentguid
	private Integer bureausn;  //委办局编号
	private String bureauname;  //委办局名称
	private String bureaucnshortname;  //委办局中文简称
	private String bureaucnfullname;  //委办局中文全称
	private String bureauenshortname;  //委办局英文简称
	private String bureauenfullname;  //委办局英文全称
	private String bureauconsultingtel;  //委办局咨询电话
	private String bureauaddress;  //委办局办公地址
	private String bureauzip;  //委办局邮编
	private String bureautel;  //委办局办公电话
	private String bureaufax;  //委办局办公传真
	private String bureauemail;  //委办局电子邮箱
	private Integer bureautabindex;  //委办局排序号
	private String serviceboardguid;  //
	private String flag;  //是否入驻大厅
	private String principal;  //
	private String windowsnum;  //服务窗口号
	private Integer senatestar;  //服务评价星级
	private String isstreet;  //街道:1,委办局:0
	private String orderpermission;  //
	private Integer ordernum;  //
	private Integer isexternal;  //外部审批单位（比如市规划局）置为1
	private Integer isstatic;  //是否事项统计
	private String leaderguids;  //部门领导GUID
	private String leadernames;  //部门领导
	private String institutioncode;  //组织机构代码
	private Date createdate;  //入驻时间
	private Date updatetime;  //最后更新时间
	private String districtcode;  //行政区划代码
	private String superiorinstituioncode;  //上级部门组织机构代码
	private String superiorname;  //上级部门名称
	private String ispublic;  //0外网不公示，1外网公示
	@Id
	public String getBureauguid() {
		return bureauguid;
	}
	public void setBureauguid(String bureauguid) {
		this.bureauguid = bureauguid;
	}
	@Column
	public String getDepartment_guids() {
		return department_guids;
	}
	public void setDepartment_guids(String department_guids) {
		this.department_guids = department_guids;
	}
	@Column
	public Integer getBureausn() {
		return bureausn;
	}
	public void setBureausn(Integer bureausn) {
		this.bureausn = bureausn;
	}
	@Column
	public String getBureauname() {
		return bureauname;
	}
	public void setBureauname(String bureauname) {
		this.bureauname = bureauname;
	}
	@Column
	public String getBureaucnshortname() {
		return bureaucnshortname;
	}
	public void setBureaucnshortname(String bureaucnshortname) {
		this.bureaucnshortname = bureaucnshortname;
	}
	@Column
	public String getBureaucnfullname() {
		return bureaucnfullname;
	}
	public void setBureaucnfullname(String bureaucnfullname) {
		this.bureaucnfullname = bureaucnfullname;
	}
	@Column
	public String getBureauenshortname() {
		return bureauenshortname;
	}
	public void setBureauenshortname(String bureauenshortname) {
		this.bureauenshortname = bureauenshortname;
	}
	@Column
	public String getBureauenfullname() {
		return bureauenfullname;
	}
	public void setBureauenfullname(String bureauenfullname) {
		this.bureauenfullname = bureauenfullname;
	}
	@Column
	public String getBureauconsultingtel() {
		return bureauconsultingtel;
	}
	public void setBureauconsultingtel(String bureauconsultingtel) {
		this.bureauconsultingtel = bureauconsultingtel;
	}
	@Column
	public String getBureauaddress() {
		return bureauaddress;
	}
	public void setBureauaddress(String bureauaddress) {
		this.bureauaddress = bureauaddress;
	}
	@Column
	public String getBureauzip() {
		return bureauzip;
	}
	public void setBureauzip(String bureauzip) {
		this.bureauzip = bureauzip;
	}
	@Column
	public String getBureautel() {
		return bureautel;
	}
	public void setBureautel(String bureautel) {
		this.bureautel = bureautel;
	}
	@Column
	public String getBureaufax() {
		return bureaufax;
	}
	public void setBureaufax(String bureaufax) {
		this.bureaufax = bureaufax;
	}
	@Column
	public String getBureauemail() {
		return bureauemail;
	}
	public void setBureauemail(String bureauemail) {
		this.bureauemail = bureauemail;
	}
	@Column
	public Integer getBureautabindex() {
		return bureautabindex;
	}
	public void setBureautabindex(Integer bureautabindex) {
		this.bureautabindex = bureautabindex;
	}
	@Column
	public String getServiceboardguid() {
		return serviceboardguid;
	}
	public void setServiceboardguid(String serviceboardguid) {
		this.serviceboardguid = serviceboardguid;
	}
	@Column
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	@Column
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	@Column
	public String getWindowsnum() {
		return windowsnum;
	}
	public void setWindowsnum(String windowsnum) {
		this.windowsnum = windowsnum;
	}
	@Column
	public Integer getSenatestar() {
		return senatestar;
	}
	public void setSenatestar(Integer senatestar) {
		this.senatestar = senatestar;
	}
	@Column
	public String getIsstreet() {
		return isstreet;
	}
	public void setIsstreet(String isstreet) {
		this.isstreet = isstreet;
	}
	@Column
	public String getOrderpermission() {
		return orderpermission;
	}
	public void setOrderpermission(String orderpermission) {
		this.orderpermission = orderpermission;
	}
	@Column
	public Integer getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(Integer ordernum) {
		this.ordernum = ordernum;
	}
	@Column
	public Integer getIsexternal() {
		return isexternal;
	}
	public void setIsexternal(Integer isexternal) {
		this.isexternal = isexternal;
	}
	@Column
	public Integer getIsstatic() {
		return isstatic;
	}
	public void setIsstatic(Integer isstatic) {
		this.isstatic = isstatic;
	}
	@Column
	public String getLeaderguids() {
		return leaderguids;
	}
	public void setLeaderguids(String leaderguids) {
		this.leaderguids = leaderguids;
	}
	@Column
	public String getLeadernames() {
		return leadernames;
	}
	public void setLeadernames(String leadernames) {
		this.leadernames = leadernames;
	}
	@Column
	public String getInstitutioncode() {
		return institutioncode;
	}
	public void setInstitutioncode(String institutioncode) {
		this.institutioncode = institutioncode;
	}
	@Column
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	@Column
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	@Column
	public String getDistrictcode() {
		return districtcode;
	}
	public void setDistrictcode(String districtcode) {
		this.districtcode = districtcode;
	}
	@Column
	public String getSuperiorinstituioncode() {
		return superiorinstituioncode;
	}
	public void setSuperiorinstituioncode(String superiorinstituioncode) {
		this.superiorinstituioncode = superiorinstituioncode;
	}
	@Column
	public String getSuperiorname() {
		return superiorname;
	}
	public void setSuperiorname(String superiorname) {
		this.superiorname = superiorname;
	}
	@Column
	public String getIspublic() {
		return ispublic;
	}
	public void setIspublic(String ispublic) {
		this.ispublic = ispublic;
	}
	

}
