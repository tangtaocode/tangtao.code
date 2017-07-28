package net.risesoft.approve.risefile.content;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import org.apache.commons.fileupload.disk.DiskFileItem;

public interface RiseFileItem {
	
	public String getFileGUID();// 主键，生成。
	
	public String getFileNameExt();

	public String getTitile();
	
	public String getFileName();
	
	public String getFileType();
	
	public String getCurrentUser();
	
	public void setFileSize(double fileSize);
	
	public double getFileSize();
	
	public void setCurrentUser(String currentUser);
	
	public boolean isManager();
	
	public InputStream getInputStream()throws IOException ;
	
	public OutputStream getOutputStream()throws IOException ;

}
