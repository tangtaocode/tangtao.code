package net.risesoft.approve.risefile.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.risesoft.approve.risefile.commons.ComparatorTabIndex;
import net.risesoft.approve.risefile.commons.FieldPlusObject;
import net.risesoft.approve.risefile.exception.RiseFileException;
import net.risesoft.approve.risefile.exception.RiseFileSystemException;
import net.risesoft.approve.risefile.exchange.RiseFileExchange;
import net.risesoft.approve.risefile.manager.RiseFileManager;
import net.risesoft.approve.risefile.manager.SysFSRiseFileManager;

/**
 * RiseFileConfig为配置叠加，根为risefile.config.xml中的系统级配置；
 * 再上级为risefile.config.xml中app的配置；最上层为Tag中的配置。 
 */
public class RiseFileConfig extends FieldPlusObject {

	/**
	 * if (this.fileRoot != null){ return this.fileRoot; } else if (this.parent !=
	 * null){ return this.parent.getFileRoot(); } else { return defaultValue;
	 * //have default return null; //nullable throw new
	 * illeageConfigException("adf") //Not null }
	 */
	
	
	public String appName;
	
	public String fileboxName;
	
	public String appInstGUID;

	public String saveMode;

	public String uploadMode;

	public String fileRoot;

	public boolean keepMinimalVersion=false;

	public String minimalVersion;

	public String majorVersion="0";

	public String majorVersionName;

	public boolean isFilterExtends=false;

	public boolean isHandleExtends=false;
	
	public List filters=new ArrayList();

	public List fileHandles=new ArrayList();
	
	public List streamHandles=new ArrayList();
	
	public String fileManager;
	
	public boolean showAllVersion=false;
	
	



	public RiseFileConfig() {

	}

	public RiseFileConfig(String appName) {
		this.appName = appName;
	}
	
	public String getFileboxName() {
		return fileboxName;
	}

	public void setFileboxName(String fileboxName) {
		this.fileboxName = fileboxName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	// 文件名为sys/app/subapp/
	public String getFileRoot() {
		
		String thisRoot=File.separator+ fileRoot;
		if (fileRoot == null||fileRoot.equals("")) {//如果为空不添加路径
			thisRoot="";
		}else if(fileRoot.startsWith("direct:")){//如果为direct直接返回，不在向上叠加
			return fileRoot.substring(8,fileRoot.length());
		}else{//普通情况，需要叠加
			thisRoot=File.separator+ fileRoot;
		}

		if(parent!=null){
			return ((RiseFileConfig)parent).getFileRoot() +thisRoot;
		}else{
			return fileRoot;
		}
	}
	

	public void setFileRoot(String fileRoot) {
		this.fileRoot = fileRoot;
	}

	// 叠加
	public boolean getKeepMinimalVersion() {
		return ((Boolean) getPlusObject("keepMinimalVersion")).booleanValue();
	}

	// 叠加
	public void setKeepMinimalVersion(boolean keepMinimalVersion) {
		this.keepMinimalVersion = keepMinimalVersion;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}
	// 叠加
	public String getSaveMode() {
		return (String) getPlusObject("saveMode");
	}

	public void setUploadMode(String uploadMode) {
		this.uploadMode = uploadMode;
	}
	// 叠加
	public String getUploadMode() {
		return (String) getPlusObject("uploadMode");
	}

	public void setMajorVersion(String majorVersion) {
		this.majorVersion = majorVersion;
	}
	public String getMajorVersion() {
		return (String) getPlusObject("majorVersion");
	}


	public String getMajorVersionName() {
		return (String) getPlusObject("majorVersionName");
	}

	public void setMajorVersionName(String majorVersionName) {
		this.majorVersionName = majorVersionName;
	}


	public List getFilters() throws RiseFileSystemException{
		List l = getPlusList("filters","isFilterExtends");
		l=RiseFileConfigManager.getMapObjectBatch(l,RiseFileConfigManager.FILEHANDLE);
		Collections.sort(l, new ComparatorTabIndex());
		return l;
	}
	
	public RiseFileConfig getParentConfig() {
		return (RiseFileConfig)parent;
	}

	public void setFilters(List filters) {
		this.filters = filters;
	}

	public void setParentConfig(RiseFileConfig parentConfig) {
		parent = parentConfig;
	}
	


	public void setFileHandles(List fileHandles) {
		this.fileHandles = fileHandles;
	}

	public void setStreamHandles(List streamHandles) {
		this.streamHandles = streamHandles;
	}
	
	public RiseFileManager getFileManager(){
		RiseFileManager fileManager;
		try {
			//自定义manager
			String managerStr=(String) getPlusObject("fileManager");
			if(managerStr==null||managerStr.equals("")){
			// 系统manager
				//TODO:应该换为加载形式
				if("db".equals(getSaveMode())){
					managerStr="net.risesoft.approve.risefile.manager.SysDBRiseFileManager";
				}else{
					managerStr="net.risesoft.approve.risefile.manager.SysFSRiseFileManager";
				}
				//managerStr="net.risesoft.approve.risefile.manager.SysFSRiseFileManager";
			}
			fileManager = (RiseFileManager)Class.forName(managerStr).newInstance();
		} catch (Throwable e) {
			log.error("装载fileManager异常", e);
			throw new IllegalArgumentException("装载过滤器异常：" + this.fileManager);
		}
		return fileManager;
	}


	public void setFileManager(String fileManager) {
		this.fileManager = fileManager;
	}
	public String getMinimalVersion() {
		return minimalVersion;
	}

	public void setMinimalVersion(String minimalVersion) {
		this.minimalVersion = minimalVersion;
	}
	protected static Logger log = LoggerFactory.getLogger(RiseFileConfigManager.class);



	public boolean isFilterExtends() {
		return isFilterExtends;
	}

	public void setFilterExtends(boolean isFilterExtends) {
		this.isFilterExtends = isFilterExtends;
	}

	public boolean isHandleExtends() {
		return isHandleExtends;
	}

	public void setHandleExtends(boolean isHandleExtends) {
		this.isHandleExtends = isHandleExtends;
	}

	public String getAppInstGUID() {
		return appInstGUID;
	}

	public void setAppInstGUID(String appInstGUID) {
		this.appInstGUID = appInstGUID;
	}
	
	public static void main(String[] args){
		SysFSRiseFileManager fileManager=new SysFSRiseFileManager();
		try {
			fileManager = (SysFSRiseFileManager)Class.forName("net.risesoft.approve.risefile.manager.SysDBRiseFileManager").newInstance();
			//fileManager = (RiseFileManager)Class.forName((String)getPlusObject("fileManager")).newInstance();
		} catch (Exception e) {
			log.error("装载fileManager异常", e);
			throw new IllegalArgumentException("装载过滤器异常：");
		}
		try {
			System.out.println(fileManager.get("d"));
		} catch (RiseFileException e) {
			e.printStackTrace();
		}
	}
}
