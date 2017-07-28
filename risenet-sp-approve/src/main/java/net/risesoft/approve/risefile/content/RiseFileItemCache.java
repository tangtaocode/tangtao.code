package net.risesoft.approve.risefile.content;

import java.io.File;

import net.risesoft.approve.risefile.config.RiseFileConfig;
import net.risesoft.commons.util.GUID;

import org.apache.commons.fileupload.disk.DiskFileItem;

public class RiseFileItemCache extends DiskFileItem implements RiseFileItem{

	
	public String fileGUID;// 主键，生成。
	
	public String fileNameExt;

	public String titile;
	
	public String currentUser;
	
	public String fileType;
	
	public double fileSize;
	
	public boolean isManager;
	

	public double getFileSize() {
		return fileSize;
	}

	public void setFileSize(double fileSize) {
		this.fileSize = fileSize;
	}

	public RiseFileItemCache(String fieldName, String contentType,
            boolean isFormField, String fileName, int sizeThreshold,
            File repository,RiseFileConfig config) {
    	super(fieldName,contentType,isFormField,fileName,sizeThreshold,repository);
    	if(isFormField){
        	return;
        }
    	fileGUID=new GUID().toString();
    	int i=fileName.lastIndexOf(".");
    	if(i>0){//modify by liujun 防止用户提交没有后缀的文件
    		titile=fileName.substring(0,i);
        	fileNameExt=fileName.substring(i+1,fileName.length());
    	}
    	
    }
    
    public String getFileGUID(){
    	return fileGUID;
    }
	
	public String getFileNameExt(){
		return fileNameExt;
	}

	public String getTitile(){
		return titile;
	}
	
	public String getFileName(){
		return getName();
	}
	
	public String getCurrentUser(){
		return currentUser;
	}
	
	public void setCurrentUser(String currentUser){
		this.currentUser=currentUser;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public boolean isManager() {
		return isManager;
	}

	public void setManager(boolean isManager) {
		this.isManager = isManager;
	}
}
