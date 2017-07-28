package net.risesoft.approve.risefile;

import java.util.List;

/**
 * 文件框
 */
public class FileBox extends RiseFileConst {
	
	private String appName;

	public String appInstanceGUID;

	public String fileBoxName;

	public int fileBoxPerm=PERMISSION_READWRITE;

	public List fileBeams;//存放FileBoxBeam的集合
	
	public String getAppInstanceGUID() {
		return appInstanceGUID;
	}

	public void setAppInstanceGUID(String appInstanceGUID) {
		this.appInstanceGUID = appInstanceGUID;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getFileBoxName() {
		return fileBoxName;
	}

	public void setFileBoxName(String fileBoxName) {
		this.fileBoxName = fileBoxName;
	}

	public int getFileBoxPerm() {
		return fileBoxPerm;
	}

	public void setFileBoxPerm(int fileBoxPerm) {
		this.fileBoxPerm = fileBoxPerm;
	}

	public List getFileBeams() {
		return fileBeams;
	}

	public void setFileBeams(List fileBeams) {
		this.fileBeams = fileBeams;
	}

	

}
