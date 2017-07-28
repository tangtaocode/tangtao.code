package net.risesoft.approve.risefile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RiseFileBeam extends RiseFileConst {

	private String fileName = null;
	
	private List riseFiles = new ArrayList();// 所有版本File集合

	private int permission = PERMISSION_READWRITE;// 权限

	// 最高版本File
	public RiseFile getFile() {
		if(riseFiles.size()==0){
			return null;
		}
		return (RiseFile)riseFiles.get(riseFiles.size()-1);
	}
	//获得文件束创建的日期
	public Date getCreateDate() throws ParseException{
		if(riseFiles.size()==0){
			return null;
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.parse(((RiseFile)riseFiles.get(0)).getCreateDate());
	}

	public List getRiseFiles() {
		return riseFiles;
	}

	public void setRiseFiles(List riseFiles) {
		this.riseFiles = riseFiles;
	}

	public int getPermission() {
		return permission;
	}

	public void setPermission(int permission) {
		this.permission = permission;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
