package net.risesoft.actions.risefile;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

import net.risesoft.actions.base.BaseActionSupport;
import net.risesoft.beans.bizbankroll.BizUpFile;
import net.risesoft.beans.civilbankroll.CivilUpFile;
import net.risesoft.beans.correction.CorrectionBean;
import net.risesoft.beans.onlineservice.WebApplyUpFile;
import net.risesoft.beans.risefile.RisenetFile;

import net.risesoft.common.Common;
import net.risesoft.services.bizbankroll.apply.IBizUpFileService;
import net.risesoft.services.civilbankroll.apply.ICivilUpFileService;
import net.risesoft.services.correction.ICorrectionService;
import net.risesoft.services.fileUtil.IFtpUpFileLogService;
import net.risesoft.services.fileUtil.IRisenetFileService;
import net.risesoft.services.onlineservice.IWebApplyUpFileService;
import net.risesoft.utils.base.DateFormatUtil;
import net.risesoft.utils.base.GUID;
import net.risesoft.utils.base.ICodeMapUtil;

/*******************************************************************************
 * 文件上传
 ******************************************************************************/
@Controller
@ParentPackage("json-default")
public class FileAction extends BaseActionSupport {

	/**
	 * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 2546214478078374012L;

	private static final String tempPath = "tempFile";
	private final Log log = LogFactory.getLog(getClass());
	private File file; // 上传的分段文件
	private String name; // 上传的唯一文件名，UUID
	private String trueName; // 上传的真实文件名
	private int chunk;// 当前块
	private int chunks;// 多少块
	private String modeType;// 上传模块
	private String correctionGUID;// 补齐补正上传用到
	private String fileGUID;// 文件GUID，删除时使用
	private String message;// 返回JSON信息，删除时使用。
	private String projectFK;//项目GUID
	private String approveItemGUID;//事项GUID
	private String departGUID;//部门或街道GUID
	private String communityGUID;//社区GUID
	
	@Resource
	ICodeMapUtil codeMapUtil;//数据字典服务类
	@Resource
	IFtpUpFileLogService ftpFileLogService;//FTP工具类
	@Resource
	ICorrectionService correctionService;//补齐补正服务类
	@Resource
	IRisenetFileService risenetFileService;//补齐补正文件服务类
	@Resource
	IBizUpFileService bizUpFileService;//资金扶持文件服务类
	@Resource
	ICivilUpFileService civilUpFileService;//民生创新文件服务类
	@Resource
	IWebApplyUpFileService webApplyUpFileService;//网上申报服文件务类
	public String getApproveItemGUID() {
		return approveItemGUID;
	}

	public void setApproveItemGUID(String approveItemGUID) {
		this.approveItemGUID = approveItemGUID;
	}

	public String getDepartGUID() {
		return departGUID;
	}

	public void setDepartGUID(String departGUID) {
		this.departGUID = departGUID;
	}

	public String getCommunityGUID() {
		return communityGUID;
	}

	public void setCommunityGUID(String communityGUID) {
		this.communityGUID = communityGUID;
	}

	public File getFile() {
		return file;
	}

	public String getModeType() {
		return modeType;
	}

	public void setModeType(String modeType) {
		this.modeType = modeType;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getChunk() {
		return chunk;
	}

	public void setChunk(int chunk) {
		this.chunk = chunk;
	}

	public int getChunks() {
		return chunks;
	}

	public void setChunks(int chunks) {
		this.chunks = chunks;
	}

	public String getCorrectionGUID() {
		return correctionGUID;
	}

	public void setCorrectionGUID(String correctionGUID) {
		this.correctionGUID = correctionGUID;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getFileGUID() {
		return fileGUID;
	}

	public void setFileGUID(String fileGUID) {
		this.fileGUID = fileGUID;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 
	 * 
	 * @Title: deleteFile
	 * @Description: 统一删除文件action
	 * @param
	 * @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@Action(value = "/riseFile/deleteFile", results = { @Result(name = "success", type = "json") }, params = {
			"contentType", "text/html" })
	public String deleteFile() {
		try {
			TreeMap<String, String> codeMap = codeMapUtil
					.getMapByType("downFileTypeData.properties");
			if ("correction".equals(getModeType())) {
				setMessage(deleteCorrectionFile(codeMap));
			} else if ("bizApply".equals(getModeType())) {
				setMessage(deleteBizApplyFile());
			}else if("civilApply".equals(getModeType())||"pubInfoApply".equals(getModeType())||"civilOrgApply".equals(getModeType())){
				setMessage(deleteCivilApplyFile());
			}else if("approveItem".equals(getModeType())){
				deleteApproveItem();
			}
			setMessage("1");
			return SUCCESS;
		} catch (Exception e) {
			setMessage("0");
			e.printStackTrace();
			return SUCCESS;
		}

	}

	/**
	 * 
	 * @Title: upload
	 * @Description: 网站统一文件上传action
	 * @param
	 * @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@Action(value = "/riseFile/uploadFileAction", results = { @Result(name = "success", type="json") })
	public String upload() {
		// 临时文件存放路径
		try {
			
			String dstPath = ServletActionContext.getServletContext().getRealPath(
					tempPath)
					+ "/" + this.getName();
			File dstFile = new File(dstPath);
			if (chunk == 0 && dstFile.exists()) {
				dstFile.delete();
			}			
			// 拷贝文件
			if (ftpFileLogService.copy(this.file, dstFile)) {
				// 判断整个文件是否被上传完成,如果完成则进行FTP上传
				if (chunk == chunks - 1||chunks==0) {
					TreeMap<String, String> dataMap = codeMapUtil
							.getMapByType("downFileTypeData.properties");
					// 补齐补正保存
					if ("correction".equals(getModeType())) {
						if(saveCorrectionFile(dataMap, dstPath)) return null;
						else return ERROR;
					} else if ("bizApply".equals(getModeType())) {
						return saveBizApplyFile(dataMap,dstPath);
					}else if("civilApply".equals(getModeType())){
						return saveCivilApplyFile(dataMap, dstPath,"0");
					}else if("pubInfoApply".equals(getModeType())){
						return saveCivilApplyFile(dataMap, dstPath,"X");
					}else if("civilOrgApply".equals(getModeType())){
						return saveCivilApplyFile(dataMap, dstPath,"O");
					}else if("approveItem".equals(getModeType())){
						return saveApproveItWebApply(dataMap,dstPath);
					}else{
						if (log.isDebugEnabled())
							log.error(" 未找到对应的上传模块的代码：ModeType=== " + getModeType());
						return ERROR;
					}
				}
				return null;
				
			} else {
				if (log.isDebugEnabled())
					log.error(" 上传文件 " + getTrueName() + " 失败");
				return ERROR;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	private String saveApproveItWebApply(TreeMap<String, String> dataMap,
			String tempPath){
		try {
			String windowPath = dataMap.get(Common.windowsPrefix)
			+ dataMap.get(modeType + Common.fileMode);
			WebApplyUpFile wFile =  new WebApplyUpFile();
			wFile.setGuid(GUID.getGUID());
			wFile.setWorkflowinstance_guid(getProjectFK());
			wFile.setApproveitemguid(getApproveItemGUID());
			wFile.setDeclareannexguid(getFileGUID());
			wFile.setFilename(getTrueName());
			wFile.setFile_name(this.getName());
			wFile.setUpdatetime(new Date(System.currentTimeMillis()));
			wFile.setUpuser(getUserGUID());
			windowPath = windowPath+getProjectFK()+"\\"+getFileGUID()+"\\";
			wFile.setRootpath(windowPath);
			String linuxPath = dataMap.get(Common.linuxPrefix)
			+ windowPath.substring(
					dataMap.get(Common.windowsPrefix).length()).replace(
					"\\", "/");
			wFile.setLinuxpath(linuxPath);
			//此处是将临时上传文件移动至上传目录，如果是在windows下 则此处写windowPath 如果是则换成linuxPath
			Map<String,String> map =codeMapUtil.getMapByType("downFileTypeData.properties");
			if(map.get("systemType")!=null && map.get("systemType").toLowerCase().equals("windows")){
				ftpFileLogService.moiveFile(tempPath, windowPath+this.getName());//windows下
			}else if(map.get("systemType")!=null && map.get("systemType").toLowerCase().equals("linux")){
				ftpFileLogService.moiveFile(tempPath, linuxPath+this.getName());	//linux下
			}else
				return ERROR;
			
			webApplyUpFileService.save(wFile);
			/*暂时停用
			ftpFileLogService.logFile(linuxPath, windowPath);
			*/
			return SUCCESS;
			//return null;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		
	}
	/**
	 * 
	 * 
	 * @Title: getFilePath
	 * @Description: 补齐补正模块上传附件组装文件信息
	 * @param
	 * @param dataMap
	 *            数据字典map
	 * @param
	 * @param fileName
	 *            文件真实名
	 * @param
	 * @return 返回RisenetFile javaBean
	 * @return RisenetFile 返回类型
	 * @throws
	 */
	private RisenetFile getFilePath(TreeMap<String, String> dataMap,
			String fileName) {
		try {
			String windowPath = dataMap.get(Common.windowsPrefix)
					+ dataMap.get(modeType + Common.fileMode);
			RisenetFile riseFile = new RisenetFile();
			CorrectionBean corr = correctionService.find(getCorrectionGUID());
			windowPath += corr.getBureauguid() + "\\"
					+ corr.getEmployeeloginname() + "\\" + getNowTime() + "\\";
			String linuxPath = dataMap.get(Common.linuxPrefix)
					+ windowPath.substring(
							dataMap.get(Common.windowsPrefix).length()).replace(
							"\\", "/");
			String guid = GUID.getGUID();
			riseFile.setFileguid(guid);
			riseFile.setCorrguid(corr.getGuid());
			riseFile.setFilename(fileName);
			riseFile.setFilenameext(ftpFileLogService.getExtName(fileName));
			riseFile.setTitile(ftpFileLogService.getFileNameNoEx(fileName));
			riseFile.setMajorname("6");
			riseFile.setMajorversion(6);
			riseFile.setMinversion(1);
			riseFile.setAppname("workflow");
			riseFile.setFileboxname("workflow_fujian");
			riseFile.setAppinstguid(corr.getWorkflowinstance_guid());
			riseFile.setCreatorguid(corr.getEmployeeguid());
			riseFile.setCreatedate(new Date(System.currentTimeMillis()));
			riseFile.setLastmodified(new Date(System.currentTimeMillis()));
			riseFile.setNewName(ftpFileLogService.getFileNameNoEx(fileName)
					+ "." + guid + ".6.1."
					+ ftpFileLogService.getExtName(fileName));
			riseFile.setRealfullpath(linuxPath + riseFile.getNewName());
			riseFile.setWindowPath(windowPath + riseFile.getNewName());
			return riseFile;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * 
	 * 
	 * @Title: getNowTime
	 * @Description: 提供给补齐补正文件上传，生成上传目录的时间方法
	 * @param
	 * @return 返回时间字符串 格式yyyym
	 * @return String 返回类型
	 * @throws
	 */
	private String getNowTime() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-M");
		String time = df.format(date);
		return time.replaceAll("-", "");
	}

	/**
	 * 
	 * saveCorrectionFile 保存补齐补正文件信息到数据库 TODO(这里描述这个方法适用条件 – 只有在保存补齐补正文件上传时调用)
	 * TODO(这里描述这个方法的执行流程 – 文件上传至windows服务器硬盘后执行)
	 * 
	 * @Title: saveCorrectionFile
	 * @Description: TODO
	 * @param
	 * @param dataMap
	 *            数据字典
	 * @param
	 * @param tempPath
	 *            临时文件路径
	 * @return boolean 返回类型 保存成功返回true 失败返回false
	 * @throws
	 */
	private boolean saveCorrectionFile(TreeMap<String, String> dataMap,
			String tempPath) {
		try {
			RisenetFile risenetFile = getFilePath(dataMap, getTrueName());
			if(risenetFile!=null){
				String windowPath = risenetFile.getWindowPath();
				ftpFileLogService.moiveFile(tempPath, windowPath);
				// 保存文件信息
				DecimalFormat df = new DecimalFormat("#.000");
				risenetFile.setFilesize(Double.valueOf(df.format((double) new FileInputStream(new File(windowPath)).available() / 1048576)));
				Map<String,String> map =codeMapUtil.getMapByType("downFileTypeData.properties");
				String systemType = map.get("systemType").toLowerCase();
				if(systemType!=null&&systemType.equals("windows")){//如果为windows系统
					risenetFile.setRealfullpath(windowPath);
				}
				risenetFileService.save(risenetFile);
				// 更新补齐补正表材料提交状态
				CorrectionBean corr = correctionService.find(getCorrectionGUID());
				corr.setIssubmit(1);
				corr.setRootpath(risenetFile.getWindowPath());
				correctionService.update(corr);
//				ftpFileLogService.logFile(risenetFile.getRealfullpath(),risenetFile.getWindowPath());
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		
	}
	/**
	 * 
	  *
	  * @Title: saveBizApplyFile
	  * @Description: 产业扶持保存材料
	  * @param @param dataMap
	  * @param @param tempPath
	  * @param @return    设定文件
	  * @return boolean    返回类型
	  * @throws
	 */
	private String saveBizApplyFile(TreeMap<String, String> dataMap,String tempPath){
		try {
			BizUpFile bf = new BizUpFile();
			bf.setGuid(GUID.getGUID());
			bf.setFilename(getTrueName());
			//bf.setUptime(new Date(System.currentTimeMillis()));
			bf.setUpuser(getUserGUID());
			bf.setXmlxguid(getProjectFK());
			bf.setClguid(getCorrectionGUID());
			String windowPath = dataMap.get(Common.windowsPrefix)+dataMap.get(getModeType()+Common.fileMode)+getUserGUID()+"\\"+getProjectFK()+"\\"+DateFormatUtil.parseToString(new java.util.Date(System.currentTimeMillis()))+"\\"; 
			bf.setWindowspath(windowPath);
			bf.setLinuxpath( dataMap.get(Common.linuxPrefix)
						+ windowPath.substring(
								dataMap.get(Common.windowsPrefix).length()).replace(
								"\\", "/"));
			ftpFileLogService.moiveFile(tempPath, windowPath+getTrueName());
			bizUpFileService.save(bf);
//			ftpFileLogService.logFile(bf.getLinuxpath(), windowPath);
			return null;
		} catch (Exception e) {
			return "error";
		}
	}
	/**
	 * 
	  * @Title: saveCivilApplyFile
	  * @Description: 民生创新保存材料
	  * @param @param dataMap
	  * @param @param tempPath
	  * @param @return    设定文件
	  * @return boolean    返回类型
	  * @throws
	 */
	private String saveCivilApplyFile(TreeMap<String, String> dataMap,String tempPath,String type){
		try {
			CivilUpFile bf = new CivilUpFile();
			bf.setAttachmentguid(GUID.getGUID());
			bf.setFile_name(getTrueName());
			//bf.setCreatetime(new Date(System.currentTimeMillis()));
			bf.setXmguid(getProjectFK());
			bf.setCllxguid(getCorrectionGUID());
			bf.setType(type);
			String windowPath = "";
			if("O".equals(type)||"X".equals(type)){
				bf.setUpuserguid(getProjectFK());
				setModeType("civilApply");
				windowPath = dataMap.get(Common.windowsPrefix)+dataMap.get(getModeType()+Common.fileMode)+getProjectFK()+"\\"+getProjectFK()+"\\"+DateFormatUtil.parseToString(new java.util.Date(System.currentTimeMillis()))+"\\";
			}else{
				bf.setUpuserguid(getUserGUID());
				windowPath = dataMap.get(Common.windowsPrefix)+dataMap.get(getModeType()+Common.fileMode)+getUserGUID()+"\\"+getProjectFK()+"\\"+DateFormatUtil.parseToString(new java.util.Date(System.currentTimeMillis()))+"\\";
			}
			 
			bf.setWindowspath(windowPath);
			bf.setLinuxpath( dataMap.get(Common.linuxPrefix)
						+ windowPath.substring(
								dataMap.get(Common.windowsPrefix).length()).replace(
								"\\", "/"));
			ftpFileLogService.moiveFile(tempPath, windowPath+getTrueName());
			civilUpFileService.save(bf);
//			ftpFileLogService.logFile(bf.getLinuxpath(), windowPath);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	/**
	 * 
	  * @Title: deleteBizApplyFile
	  * @Description: 产业扶持删除材料
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	private String deleteBizApplyFile(){
		try {
			BizUpFile bf = bizUpFileService.find(getFileGUID());
//			ftpFileLogService.ftpDeleteFile(bf.getLinuxpath()+bf.getFilename());
			File file = new File(bf.getWindowspath()+bf.getFilename());
			if(file.exists())file.delete();
			bizUpFileService.delete(bf);
			return "1";
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.error("删除文件失败！");
			}
			return "0";
		}
	}
	/**
	 * 
	  * @Title: deleteApproveItem
	  * @Description: 删除行政审批外网申报材料(住建局全部用这个删除?)
	  * @return String    返回类型
	  * @throws 把错误信息传回给调用者
	 */
	private String deleteApproveItem() throws Exception{
		try {
			WebApplyUpFile bf = webApplyUpFileService.find(getFileGUID());
//			ftpFileLogService.ftpDeleteFile(bf.getLinuxpath()+bf.getFilename());
			File file = new File(bf.getRootpath()+bf.getFilename());
			if(file.exists())file.delete();
			webApplyUpFileService.delete(bf);
			return "1";
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.error("删除文件失败！");
			}
			throw e;
			//return "0";
		}
	}
	/**
	 * 
	  *
	  * @Title: deleteCivilApplyFile
	  * @Description: 民生创新删除材料
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	private String deleteCivilApplyFile(){
		try {
			CivilUpFile bf = civilUpFileService.find(getFileGUID());
//			ftpFileLogService.ftpDeleteFile(bf.getLinuxpath()+bf.getFile_name());
			File file = new File(bf.getWindowspath()+bf.getFile_name());
			if(file.exists())file.delete();
			civilUpFileService.delete(bf);
			return "1";
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.error("删除文件失败！");
			}
			return "0";
		}
	}
	
	/**
	 * 
	 * 
	 * @Title: deleteCorrectionFile
	 * @Description: 删除补齐补正文件
	 * @param
	 * @param codeMap
	 *            数据字典map
	 * @param
	 * @return 是否成功 1 成功 0不成功
	 * @return String 返回类型
	 * @throws
	 */
	private String deleteCorrectionFile(TreeMap<String, String> codeMap) {
		try {
			// 根据ID查找文件
			RisenetFile riseFile = risenetFileService.find(fileGUID);
			// 删除远程linux服务器文件
			ftpFileLogService.ftpDeleteFile(riseFile.getRealfullpath());
			File file = new File(codeMap.get(Common.windowsPrefix)
					+ riseFile.getRealfullpath().substring(
							codeMap.get(Common.linuxPrefix).length()).replace("/",
							"\\"));
			// 删除windows文件路径
			if (file.exists())
				file.delete();
			// 删除数据库文件信息
			risenetFileService.delete(riseFile);
			// 查询材料对应已上传文件
			List<RisenetFile> riseFileList = risenetFileService.getScrollData(
					-1,
					-1,
					new String[] { "o.corrguid=?", "o.appinstguid=?" },
					new String[] { riseFile.getCorrguid(),
							riseFile.getAppinstguid() }).getResultList();
			// 全部删除时才更新上传状态
			if (riseFileList.size() == 0) {
				CorrectionBean corr = correctionService.find(riseFile
						.getCorrguid());
				corr.setIssubmit(0);
				correctionService.update(corr);
			}
			return "1";
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.error("删除文件失败！");
			}
			return "0";
		}
	}

	public String getProjectFK() {
		return projectFK;
	}

	public void setProjectFK(String projectFK) {
		this.projectFK = projectFK;
	}
}
