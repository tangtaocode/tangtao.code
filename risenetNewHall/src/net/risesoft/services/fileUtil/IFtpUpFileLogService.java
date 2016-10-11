package net.risesoft.services.fileUtil;

import java.io.File;

import org.apache.commons.net.ftp.FTPClient;

import net.risesoft.beans.risefile.FtpUpFileLog;
import net.risesoft.daos.base.IBaseDao;

public interface IFtpUpFileLogService extends IBaseDao<FtpUpFileLog> {
	public boolean ftpDeleteFile(String linuxPath);
	/***************************************************************************
	 * FTP工具，将文件推送到指定的FTP服务器
	 **************************************************************************/
	public boolean ftpUpFile(FTPClient ftpClient,String linuxPath, String windowPath) ;
	
	/**
	 * 
	  * @Title: getFTPClient
	  * @Description: 获取FTP链接
	  * @return FTPClient    FTP链接
	  * @throws
	 */
	public FTPClient getFTPClient();
	
	public void LogoutFTP(FTPClient ftClient);
	/**
	 * FTP上传文件
	 */
	public boolean upFile(FTPClient ftpClient, File file1);

	/***************************************************************************
	 * 记录FTP上传失败的文件路径信息
	 **************************************************************************/
	public void logFile(String linuxPath, String windowPath) ;

	/***************************************************************************
	 * 检测是否存在历史FTP上传出错的文件，如果有则上传
	 **************************************************************************/
	public void upOldFile();

	/***************************************************************************
	 * FTP创建并切换目录
	 **************************************************************************/
	public void createDirecroty(String dirpath, FTPClient ftpClient);
	/**
	 * Java文件操作 获取文件扩展名
	 */
	public String getExtName(String fileName) ;

	/**
	 * Java文件操作 获取不带扩展名的文件名
	 */
	public String getFileNameNoEx(String filename);
	public boolean moiveFile(String fromPath, String toPath);
	/***************************************************************************
	 * 文件拷贝,将空间上传的临时文件拷贝到上传目录
	 * 
	 **************************************************************************/
	public boolean copy(File src, File dst);
}
