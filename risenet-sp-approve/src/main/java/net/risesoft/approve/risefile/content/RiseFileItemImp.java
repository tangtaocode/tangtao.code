package net.risesoft.approve.risefile.content;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class RiseFileItemImp implements RiseFileItem{
	
	public String fileGUID;// 主键，生成。
	
	public String fileNameExt;

	public String titile;
	
	public String currentUser;
	
	public String fileName;
	
	public String fileType="";
	
	public double fileSize;
	
	public boolean isManager;
	
	
	public boolean isManager() {
		return isManager;
	}

	public void setManager(boolean isManager) {
		this.isManager = isManager;
	}

	public double getFileSize() {
		return fileSize;
	}

	public void setFileSize(double fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}

	public String getFileGUID() {
		return fileGUID;
	}

	public void setFileGUID(String fileGUID) {
		this.fileGUID = fileGUID;
	}

	public String getFileNameExt() {
		return fileNameExt;
	}

	public void setFileNameExt(String fileNameExt) {
		this.fileNameExt = fileNameExt;
	}

	public String getTitile() {
		return titile;
	}

	public void setTitile(String titile) {
		this.titile = titile;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getInputStream() throws IOException {
		return null;
	}

	public OutputStream getOutputStream() throws IOException {
		return null;
	}

	
	
}
