package net.risesoft.services.fileUtil.impl;

import org.springframework.stereotype.Service;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;

import net.risesoft.beans.risefile.FtpUpFileLog;
import net.risesoft.daos.base.impl.BaseDaoImpl;
import net.risesoft.services.fileUtil.IFtpUpFileLogService;
import net.risesoft.utils.base.GUID;

@Service
public class FtpUpFileLogServiceImpl extends BaseDaoImpl<FtpUpFileLog>
		implements IFtpUpFileLogService {
	private static final String ip = "10.161.109.83";
	private static final String userId = "root";
	private static final String pwd = "12345678";
	private static final int prot = 21;
	private static final int BUFFER_SIZE = 2 * 1024;
	private final Logger log = Logger.getLogger(FtpUpFileLogServiceImpl.class);

	@Override
	public FTPClient getFTPClient() {
		FTPClient ftpClient = new FTPClient();
		ftpClient.setControlEncoding("GBK"); // 设置编码为中文否则文件测试错误
		try {
			// 连接主机
			ftpClient.connect(ip, prot);
			ftpClient.setBufferSize(BUFFER_SIZE);
			// 连接成功后用指定帐号密码登录到该主机
			if (ftpClient.login(userId, pwd)) {
				// 无此句，传输非txt文件会变大
				ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
				ftpClient.setRemoteVerificationEnabled(false);
				ftpClient.enterLocalPassiveMode();
				ftpClient.setDataTimeout(6000000);
				if (log.isDebugEnabled())
					log.debug("FTP服务器登陆成功\n" + ftpClient.getStatus());
				return ftpClient;
			} else {
				if (log.isDebugEnabled())
					log.debug("FTP服务器登陆失败\n" + ftpClient.getStatus());
				return ftpClient;
			}
			// 上传成功后退出系统
		} catch (Exception e) {
			if (log.isDebugEnabled())
				log.debug("FTP服务器登陆失败:" + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public void LogoutFTP(FTPClient ftClient) {
		try {
			if (ftClient != null)
				ftClient.logout();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean ftpDeleteFile(String linuxPath) {
		FTPClient ftpClient = getFTPClient();
		try {
			return ftpClient.deleteFile(linuxPath);
		} catch (Exception e) {
			if (log.isDebugEnabled())
				log.error("删除文件失败:" + e.getMessage());
			e.printStackTrace();
			return false;
		} finally {
			LogoutFTP(ftpClient);
		}
	}

	/***************************************************************************
	 * FTP工具，将文件推送到指定的FTP服务器
	 **************************************************************************/
	public boolean ftpUpFile(FTPClient ftpClient, String linuxPath,
			String windowPath) {
		try {
			File file1 = new File(windowPath);
			// 创建并切换目录
			createDirecroty(linuxPath, ftpClient);
			return upFile(ftpClient, file1);
		} catch (Exception e) {
			if (log.isDebugEnabled())
				log.error("推送文件失败：" + e.getMessage());
			FtpUpFileLog logFile = findBeanBypro("filepath", windowPath);
			if (logFile == null) {
				logFile(linuxPath, windowPath);
			}
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * FTP上传文件
	 */
	public boolean upFile(FTPClient ftpClient, File file1) {
		try {
			ftpClient.enterLocalPassiveMode();
			if (ftpClient.storeFile(file1.getName(), new BufferedInputStream(
					new FileInputStream(file1)))) {
				if (log.isDebugEnabled())
					log.debug("文件：" + file1.getName() + "上传至FTP服务器成功！");
				return true;
			} else {
				if (log.isDebugEnabled())
					log.debug("文件：" + file1.getName()
							+ "上传至FTP服务器失败！此目录下该用户无权上传文件！");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (log.isDebugEnabled())
				log.debug("文件：" + file1.getName() + "上传至FTP服务器失败！"
						+ e.getMessage());
			return false;
		}

	}

	/***************************************************************************
	 * 记录FTP上传失败的文件路径信息
	 **************************************************************************/
	public void logFile(String linuxPath, String windowPath) {
		FtpUpFileLog ffl = new FtpUpFileLog();
		ffl.setGuid(GUID.getGUID());
		ffl.setFilepath(windowPath);
		ffl.setUppath(linuxPath);
		ffl.setIsUpload("0");
		save(ffl);
	}

	/***************************************************************************
	 * 检测是否存在历史FTP上传出错的文件，如果有则上传
	 **************************************************************************/
	public void upOldFile() {
		FTPClient ftpClient = getFTPClient();
		try {
			List<FtpUpFileLog> fflList = getScrollData().getResultList();
			if (fflList != null && fflList.size() > 0) {
				for (FtpUpFileLog ffl : fflList) {
					if (ftpUpFile(ftpClient, ffl.getUppath(), ffl.getFilepath())) {
						delete(ffl.getGuid());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			LogoutFTP(ftpClient);
		}
	}

	/***************************************************************************
	 * FTP创建并切换目录
	 **************************************************************************/
	public void createDirecroty(String dirpath, FTPClient ftpClient) {
		try {
			ftpClient.changeWorkingDirectory("/");
			String directory = dirpath.substring(0,
					dirpath.lastIndexOf("/") + 1);
			if (!directory.equalsIgnoreCase("/")
					&& !ftpClient.changeWorkingDirectory(directory)) {
				// 如果远程目录不存在，则递归创建远程服务器目录
				int start = 0;
				int end = 0;
				if (directory.startsWith("/")) {
					start = 1;
				} else {
					start = 0;
				}
				end = directory.indexOf("/", start);
				while (true) {
					String subDirectory = new String(dirpath.substring(start,
							end));
					if (!ftpClient.changeWorkingDirectory(subDirectory)) {
						if (ftpClient.makeDirectory(subDirectory)) {
							ftpClient.changeWorkingDirectory(subDirectory);
						} else {
							log.error("FTP服务器创建目录：" + subDirectory + " 失败！");
						}
					}
					start = end + 1;
					end = directory.indexOf("/", start);
					// 检查所有目录是否创建完毕
					if (end <= start) {
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("FTP服务器创建目录失败！  错误:" + e.getMessage());
		}

	}

	/**
	 * Java文件操作 获取文件扩展名
	 */
	public String getExtName(String fileName) {
		if ((fileName != null) && (fileName.length() > 0)) {
			int dot = fileName.lastIndexOf('.');
			if ((dot > -1) && (dot < (fileName.length() - 1))) {
				return fileName.substring(dot + 1);
			}
		}
		return fileName;
	}

	/**
	 * Java文件操作 获取不带扩展名的文件名
	 */
	public String getFileNameNoEx(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length()))) {
				return filename.substring(0, dot);
			}
		}
		return filename;
	}

	public boolean moiveFile(String fromPath, String toPath) {
		File fromFile = new File(fromPath);
		File toFile = new File(toPath);
		//return copy(fromFile,toFile);
		File ParentFile = toFile.getParentFile();
		if (!ParentFile.exists()) {
			ParentFile.mkdirs();
		}
		return fromFile.renameTo(toFile);
	}

	/***************************************************************************
	 * 文件拷贝,将空间上传的临时文件拷贝到上传目录
	 * 
	 **************************************************************************/
	public boolean copy(File src, File dst) {
		InputStream in = null;
		OutputStream out = null;
		try {
			// 得到文件目录，如果目录不存在则创建目录
			File pFile = dst.getParentFile();
			if (!pFile.exists()) {
				pFile.mkdirs();
			}
			if (dst.exists()) {
				out = new BufferedOutputStream(new FileOutputStream(dst, true),
						BUFFER_SIZE);
			} else {
				// 如果文件不存在则创建空文件
				dst.createNewFile();
				out = new BufferedOutputStream(new FileOutputStream(dst),
						BUFFER_SIZE);
			}
			in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
			byte[] buffer = new byte[BUFFER_SIZE];
			int len = 0;
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
			out.flush();
			out.close();
			in.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
