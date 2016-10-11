package net.risesoft.actions.civilbankroll.organization;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.risesoft.actions.base.BaseActionSupport;
import net.risesoft.beans.civilbankroll.CivilOrgFile;
import net.risesoft.beans.civilbankroll.CivilOrganization;
import net.risesoft.beans.risefile.UpFileBean;
import net.risesoft.services.civilbankroll.organization.ICivilOrgFileService;
import net.risesoft.services.civilbankroll.organization.ICivilOrganizationService;
import net.risesoft.services.fileUtil.IFileUtileService;
import net.risesoft.utils.base.GUID;
import net.risesoft.utils.base.ICodeMapUtil;
import net.risesoft.utils.base.WebUtil;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.stereotype.Controller;

@Controller
@ParentPackage("json-default")
public class OrganizationAction extends BaseActionSupport {

	/**
	 * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	 */

	private static final long serialVersionUID = 154431354819175883L;
	private String method;
	private String orgCode;
	private String key;
	private String message;
	private String url;
	private String orgType;
	private String guid;
	private String modeType;
	private CivilOrganization organization;
	private CivilOrgFile civilOrgFile;
	private String correctionGUID;
	private String projectFK;
	private List<UpFileBean> upFileList; //上传文件列表
	@Resource
	private ICivilOrganizationService organizationService;
	@Resource
	private ICivilOrgFileService civilOrgFileService;
	@Resource
	private IFileUtileService fileUtileService;
	@Resource
	private ICodeMapUtil codeMapUtil;
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public CivilOrganization getOrganization() {
		return organization;
	}

	public void setOrganization(CivilOrganization organization) {
		this.organization = organization;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getOrgType() {
		return orgType;
	}

	public CivilOrgFile getCivilOrgFile() {
		return civilOrgFile;
	}

	public void setCivilOrgFile(CivilOrgFile civilOrgFile) {
		this.civilOrgFile = civilOrgFile;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	@JSON
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<UpFileBean> getUpFileList() {
		return upFileList;
	}

	public void setUpFileList(List<UpFileBean> upFileList) {
		this.upFileList = upFileList;
	}

	public String getModeType() {
		return modeType;
	}

	public void setModeType(String modeType) {
		this.modeType = modeType;
	}

	public String getCorrectionGUID() {
		return correctionGUID;
	}

	public void setCorrectionGUID(String correctionGUID) {
		this.correctionGUID = correctionGUID;
	}

	public String getProjectFK() {
		return projectFK;
	}

	public void setProjectFK(String projectFK) {
		this.projectFK = projectFK;
	}

	@Action(value = "/civilApply/initOrg", results = { @Result(name = "success", location = "/WEB-INF/page/civilbankroll/organization/initOrg.jsp") })
	public String initOrg() {
		return SUCCESS;
	}

	@Action(value = "/civilApply/saveOrg", results = {
			@Result(name = "success", location = "/WEB-INF/page/civilbankroll/organization/organization.jsp"),
			@Result(name = "view", location = "/WEB-INF/page/civilbankroll/organization/viewOrganization.jsp"),
			@Result(name = "message", location = "/WEB-INF/page/share/message.jsp") })
	public String saveOrg() {
		try {
			if ("add".equals(getMethod())) {
				CivilOrganization org = new CivilOrganization();
				org.setGuid(GUID.getGUID());
				setOrganization(org);
				return SUCCESS;
			} else if ("toEdit".equals(getMethod())) {
				CivilOrganization org = organizationService.findOrg(orgCode, key);
				if (org == null) {
					setMessage("0");
					return "message";
				} else if("0".equals(org.getStatus())||"1".equals(org.getStatus())){
					org.setCompanydes(WebUtil.filter(org.getCompanydes()));
					org.setOldproject(WebUtil.filter(org.getOldproject()));
					setOrganization(org);
					return "view";
				}else{
					setOrganization(org);
					return SUCCESS;
				}
			} else {
				CivilOrganization org = getOrganization();
				try {
					if (org.getCreatedate()==null) {
						org.setCreatedate(new Date(System.currentTimeMillis()));
					} else {
						org.setUpdatedate(new Date(System.currentTimeMillis()));
					}
					organizationService.saveOrUpdate(org);
					setMessage("申请成功");
					return "message";
				} catch (Exception e) {
					setMessage("申请失败");
					setUrl("/civilApply/saveOrg.YS?orgCode=" + org.getOrganizationcode() + "&key="
							+ org.getPassword());
					return "message";
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	@Action(value = "/civilApply/findOrgFile", results = { @Result(name = "success", location = "/WEB-INF/page/civilbankroll/organization/orgFile.jsp") })
	public String findOrgFile() {
		try {
			CivilOrgFile orgFile = civilOrgFileService.findByProperty("organizationtype", getOrgType()).get(0);
			if(orgFile!=null){
				orgFile.setOrganizationtype(codeMapUtil.getMapByType("民生创新-机构类别").get(orgFile.getOrganizationtype()));	
				setCivilOrgFile(orgFile);
				List<UpFileBean> fileList = new ArrayList<UpFileBean>();
				UpFileBean file = new UpFileBean();
				file.setFileGUID(orgFile.getGuid());
				file.setFileName("申请评估机构材料");
				file.setModeType("civilOrgApply");
				file.setUrl("/civilApply/initOrgFileUpPanel");
				file.setProjectFK(getGuid());
				String sql = "select t.attachmentguid fileGUID,t.file_name filename,'civilOrgApply' modeType from msxm_attachment t where t.xmguid=? and t.cllxguid=?";
				file.setAlreadyUpFile(fileUtileService.getFileBeanList(sql,
						new String[]{getGuid(),file.getFileGUID(),"-1","-1"}));
				fileList.add(file);
				setUpFileList(fileList);
			}
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	@Action(value = "/civilApply/initOrgFileUpPanel", results = { @Result(name = "success", location = "/WEB-INF/page/share/upFilePanel.jsp") })
	public String initFileUpPanel() {
		return SUCCESS;
	}
}
