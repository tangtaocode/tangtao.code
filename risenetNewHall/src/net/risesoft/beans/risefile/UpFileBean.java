package net.risesoft.beans.risefile;

import java.io.Serializable;
import java.util.List;

public class UpFileBean implements Serializable{
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 9082624545448105164L;
	private String fileGUID; //文件GUID
	private String fileName; //文件名称
	private String projectFK; //文件关联项目的外键GUID
	private String isPass;//审核是否通过，有些地方会使用到
	private String fileType;//材料类型GUID
	private String url;//初始化上传面板action
	private String modeType;
	private String hasTemp;//是否有模板
	private List<UpFileBean> alreadyUpFile; //已上传文件结果集

	public String getFileGUID() {
		return fileGUID;
	}

	public void setFileGUID(String fileGUID) {
		this.fileGUID = fileGUID;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public List<UpFileBean> getAlreadyUpFile() {
		return alreadyUpFile;
	}

	public void setAlreadyUpFile(List<UpFileBean> alreadyUpFile) {
		this.alreadyUpFile = alreadyUpFile;
	}

	public String getProjectFK() {
		return projectFK;
	}

	public void setProjectFK(String projectFK) {
		this.projectFK = projectFK;
	}

	public String getIsPass() {
		return isPass;
	}

	public void setIsPass(String isPass) {
		this.isPass = isPass;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getModeType() {
		return modeType;
	}

	public void setModeType(String modeType) {
		this.modeType = modeType;
	}

	public String getHasTemp() {
		return hasTemp;
	}

	public void setHasTemp(String hasTemp) {
		this.hasTemp = hasTemp;
	}
	
}
