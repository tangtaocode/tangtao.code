package net.risesoft.actions.correction;

import java.util.List;

import javax.annotation.Resource;

import net.risesoft.actions.base.BaseActionSupport;
import net.risesoft.beans.correction.CorrectionBean;
import net.risesoft.beans.onlineservice.BusinessInfo;
import net.risesoft.beans.risefile.UpFileBean;
import net.risesoft.services.correction.ICorrectionService;
import net.risesoft.services.fileUtil.IFileUtileService;
import net.risesoft.services.interaction.IDynamicStateService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

/*******************************************************************************
 * 补齐补正表
 ******************************************************************************/
@Controller
@ParentPackage("default")
public class CorrectionAction extends BaseActionSupport {

	/**
	 * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	 */

	private static final long serialVersionUID = 5155404206408076989L;
	private BusinessInfo businessInfo;
	private String declaresn;
	private String zhengjiandaima;
	private String method;
	private String correctionGUID;
	private String modeType;
	private List<UpFileBean> upFileList;
	@Resource
	private IDynamicStateService dynamicStateService;
	@Resource
	private IFileUtileService fileUtileService;
	@Resource
	ICorrectionService correctionService;//补齐补正服务类
	public String getCorrectionGUID() {
		return correctionGUID;
	}

	public void setCorrectionGUID(String correctionGUID) {
		this.correctionGUID = correctionGUID;
	}

	public String getDeclaresn() {
		return declaresn;
	}

	public void setDeclaresn(String declaresn) {
		this.declaresn = declaresn;
	}

	public String getZhengjiandaima() {
		return zhengjiandaima;
	}

	public void setZhengjiandaima(String zhengjiandaima) {
		this.zhengjiandaima = zhengjiandaima;
	}

	public BusinessInfo getBusinessInfo() {
		return businessInfo;
	}

	public void setBusinessInfo(BusinessInfo businessInfo) {
		this.businessInfo = businessInfo;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getModeType() {
		return modeType;
	}

	public void setModeType(String modeType) {
		this.modeType = modeType;
	}

	public List<UpFileBean> getUpFileList() {
		return upFileList;
	}

	public void setUpFileList(List<UpFileBean> upFileList) {
		this.upFileList = upFileList;
	}

	@Action(value = "/correction/correctionDefault", results = { @Result(name = "success", location = "/WEB-INF/page/correction/queryPage.jsp"),
			@Result(name = "bqbz", location = "/WEB-INF/page/correction/resultPage.jsp")})
	public String defaultPage() {
		try {
			if(StringUtils.isBlank(getMethod())){
				return SUCCESS;
			}else{
				String[] param = new String[]{getDeclaresn(),getZhengjiandaima(),"-1","-1"};
				String sql = "select d.approveitemname spsxmc,d.declaresn yxtywlsh,d.declarerperson sqdwhsqrxm,b.bureauname sljgmc,"
					+ "s.statedescription blzt,to_char(s.isfinished) isfinished,d.approveitemguid,d.zhengjiandaima cardId,d.employeedeptname,to_char(d.declaredatetime,'yyyy-MM-dd') slsj "
					+ " from office_spi_declareinfo d,spi_supervise s,spm_bureau b "
					+ " where d.declaresn=s.declaresn and s.bureauguid=b.bureauguid and s.state not in('0','20') "
					+ " and d.declaresn=? and d.zhengjiandaima=?";
				BusinessInfo busi = dynamicStateService.getBusinessInfo(sql,param);
				if(busi==null){
					outJson("{'message':'1'}", null);//未找到业务
					return null;
				}else{
					setBusinessInfo(busi);
					// 查询需上传的材料清单
					sql = " select distinct d.fileName,d.fileGUID,d.fileType,d.isPass,'/correction/initFileUpPanel' url,'correction' modeType from (select s.declareannexname fileName,"
							+ " o.guid fileGUID,o.declareannexguid fileType,to_char(o.issubmit) isPass,o.declaresn,o.idnumber zhengjiandaima from spm_declareannex s,"
							+ " SPM_ONLINESUBMITDECLAREANNEX o where s.declareannexguid = o.declareannexguid "
							+ " ) d where d.declaresn=? and d.zhengjiandaima=?";
					List<UpFileBean> upFileList = fileUtileService.getFileBeanList(sql,
							param);
					// 查询材料清单中每项材料已上传的材料
					String upedSql = "select d.filename,d.fileguid,'correction' modeType from ( select distinct f.filename,f.fileguid,"
							+ " decode(t.issubmit,'1','待审核','2','审核通过') as isPass, "
							+ " d.declareannexguid,t.idnumber zhengjiandaima,t.declaresn from SPM_ONLINESUBMITDECLAREANNEX t, "
							+ " RISENET_FILE f, spm_declareannex d where t.workflowinstance_guid = f.appinstguid "
							+ " and t.declareannexguid = d.declareannexguid "
							+ " and d.approveitemguid = t.approveitemguid and f.corrguid = t.guid"
							+ " and t.employeeguid = f.creatorguid ) d where d.declaresn=? and d.zhengjiandaima=? "
							+ " and declareannexguid=?";
					for (UpFileBean file : upFileList) {
						file.setAlreadyUpFile(fileUtileService.getFileBeanList(upedSql,
								new String[]{getDeclaresn(),getZhengjiandaima(),file.getFileType(),"-1","-1"}));
					}
					setUpFileList(upFileList);
					return "bqbz";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			outJson("{'message':'0'}", null);//系统错误
			return null;
		}
		
	}

	@Action(value = "/correction/updateCorrection", results = { @Result(type="json") })
	public String updateCorrection() {
		try {
			List<CorrectionBean> corr = correctionService.findByProperty("declaresn", getDeclaresn());
			for(CorrectionBean c:corr){
				c.setIssubmit(1);
				correctionService.update(c);
			}
			outJson("{'message':'1'}", null);
		} catch (Exception e) {
			e.printStackTrace();
			outJson("{'message':'0'}", null);
		}
		return null;
	}
	
	@Action(value = "/correction/initFileUpPanel", results = { @Result(name = "success", location = "/WEB-INF/page/share/upFilePanel.jsp") })
	public String initFileUpPanel() {
		return SUCCESS;
	}

}
