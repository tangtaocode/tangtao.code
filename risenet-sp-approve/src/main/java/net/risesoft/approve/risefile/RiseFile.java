package net.risesoft.approve.risefile;

import java.io.OutputStream;
import java.sql.Blob;

/**
 * 有生文件
 */
public class RiseFile {

    public String fileGUID;// 主键，生成。

	
	public String filename;// 用户上传的文件名，如：关于xxx的报告.doc。不含路径。

	public String fileNameExt;

	public String titile;

	public String majorName;

	public int majorVersion;

	public int minVersion;

	public String appName;
	
	public String fileboxName;
	
	public String appInstGUID;
	
	public String creatorGUID;

	public String deptGUID;
	
	public String createDate;

	public String lastModified;

	public String realFullPath; // ���·�� riseoffice/shouwen/200612/AAAA.{GUID}.1.doc
								//�ļ�ʹ�����·��,��֤�ļ������ƶ�.ֻҪ��risefile.Config.xml�����ü���

	public String contentUniqueCode;

	public String fileType;//��ͨ/����/ָ��

	public String saveType;//db/fs

	public RiseFile preFile;
	
	public String handles="";
	
	public Blob content;
	
	public double fileSize;
	

	/**
	 * @roseuid 4507BCAA01E1
	 */
	public RiseFile() {

	}

	/**
	 * ����saver.save()
	 * 
	 * @param tagFileConfig
	 * @return Void
	 * @roseuid 45004E2303AA
	 */
	public Void saveContent(String tagFileConfig) {
		return null;
	}

	/**
	 * @return Void
	 * @roseuid 45004E33012C
	 */
	public Void getInpuStream() {
		return null;
	}

	public String getContentUniqueCode() {
		return contentUniqueCode;
	}

	public void setContentUniqueCode(String contentUniqueCode) {
		this.contentUniqueCode = contentUniqueCode;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCreatorGUID() {
		return creatorGUID;
	}

	public void setCreatorGUID(String creatorGUID) {
		this.creatorGUID = creatorGUID;
	}
	
	public OutputStream getFileContent() {
		return null;
	}


	public String getFileGUID() {
		return fileGUID;
	}

	public void setFileGUID(String fileGUID) {
		this.fileGUID = fileGUID;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFileNameExt() {
		return fileNameExt;
	}

	public void setFileNameExt(String fileNameExt) {
		this.fileNameExt = fileNameExt;
	}

	public String getAppInstGUID() {
		return appInstGUID;
	}

	public void setAppInstGUID(String appInstGUID) {
		this.appInstGUID = appInstGUID;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getLastModified() {
		return lastModified;
	}

	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}

	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	public int getMajorVersion() {
		return majorVersion;
	}

	public void setMajorVersion(int majorVersion) {
		this.majorVersion = majorVersion;
	}

	public int getMinVersion() {
		return minVersion;
	}

	public void setMinVersion(int minVersion) {
		this.minVersion = minVersion;
	}

	public RiseFile getPreFile() {
		return preFile;
	}

	public void setPreFile(RiseFile preFile) {
		this.preFile = preFile;
	}

	public String getRealFullPath() {
		return realFullPath;
	}

	public void setRealFullPath(String realFullPath) {
		this.realFullPath = realFullPath;
	}

	public String getSaveType() {
		return saveType;
	}

	public void setSaveType(String saveType) {
		this.saveType = saveType;
	}

	public String getTitile() {
		return titile;
	}

	public void setTitile(String titile) {
		this.titile = titile;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getFileboxName() {
		return fileboxName;
	}

	public void setFileboxName(String tagName) {
		this.fileboxName = tagName;
	}
	
	
	

	public Blob getContent() {
		return content;
	}

	public void setContent(Blob content) {
		this.content = content;
	}

	public String getHandles() {
		if(handles==null){
			return "";
		}
		return handles;
	}

	public void setHandles(String handles) {
		this.handles = handles;
	}

	public String getDeptGUID() {
		return deptGUID;
	}

	public void setDeptGUID(String deptGUID) {
		this.deptGUID = deptGUID;
	}

	public double getFileSize() {
		return fileSize;
	}

	public void setFileSize(double fileSize) {
		this.fileSize = fileSize;
	}


}
