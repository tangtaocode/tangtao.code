package net.risesoft.beans.base;

import java.io.Serializable;
import java.sql.Blob;
/**
 * 
  * @ClassName: DownFile
  * @Description: 文件下载公用类
  * @author Comsys-zhangkun
  * @date Jun 1, 2013 5:00:04 PM
  *
 */

public class DownFile implements Serializable{
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 1985652618033357609L;
	private String fileName;
	private String filePath;
	private Blob blobColmon;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public Blob getBlobColmon() {
		return blobColmon;
	}
	public void setBlobColmon(Blob blobColmon) {
		this.blobColmon = blobColmon;
	}
	
	
}
