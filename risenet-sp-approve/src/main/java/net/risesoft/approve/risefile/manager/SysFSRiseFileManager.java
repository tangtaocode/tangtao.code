package net.risesoft.approve.risefile.manager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import net.risesoft.approve.controller.SysAbstractFileManager;
import net.risesoft.approve.risefile.RiseFile;
import net.risesoft.approve.risefile.config.RiseFileConfig;
import net.risesoft.approve.risefile.content.RiseFileItem;
import net.risesoft.approve.risefile.content.RiseFileItemIODirect;
import net.risesoft.approve.risefile.exception.RiseFileException;
import net.risesoft.approve.risefile.exception.RiseFileSystemException;
import net.risesoft.approve.risefile.handler.HandleManager;
import net.risesoft.approve.risefile.handler.RiseFileInputStream;
import net.risesoft.approve.risefile.handler.sourcehandler.FileSourceOutputStreamHandle;
import net.risesoft.model.Person;



public class SysFSRiseFileManager extends SysAbstractFileManager {
	
	public RiseFileInputStream getContent(String fileGUID) throws RiseFileSystemException, RiseFileException{
			RiseFile riseFile = get(fileGUID);
			return HandleManager.getRiseFileInputStream(riseFile);
	
	}
	
	protected void deleteImp(String appSaveRoot,RiseFile riseFile){
		//删除文件属性
//		File oldFile=new File(appSaveRoot+File.separator+riseFile.getRealFullPath());
		File oldFile=new File(riseFile.getRealFullPath());
		oldFile.delete();
		// 删除文件内容
		//session.delete(riseFile);
	}
	
	protected void updateContent(RiseFile riseFile,RiseFileItem fileItem, RiseFileConfig config,InputStream is, Person user) throws SQLException, IOException{
		//正常的通过API的方式调用
		if(is!=null){
			OutputStream os=null;
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar calendar = Calendar.getInstance();
			try {
				calendar.setTime(sdf.parse(riseFile.getCreateDate()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			StringBuffer filePath = new StringBuffer().append(riseFile.realFullPath);
		/*	StringBuffer filePath=new StringBuffer()
			.append(config.getFileRoot()).append(File.separatorChar)
			.append(user.).append(File.separatorChar)
			.append(user.getLoginName()).append(File.separatorChar)
			.append(calendar.get(Calendar.YEAR)).append(calendar.get(Calendar.MONTH)+1)
			.append(File.separatorChar)
			.append(riseFile.getTitile()).append(".")
			.append(riseFile.getFileGUID()).append(".")
			.append(riseFile.getMajorVersion()+"."+riseFile.getMinVersion()).append(".")
			.append(riseFile.getFileNameExt());*/
			FileSourceOutputStreamHandle fHandle=new FileSourceOutputStreamHandle();
			
			os=fHandle.getSourceOutputStream(filePath.toString()).outupStream;
			int bytesRead = 0;
		    byte[] buffer = new byte[1026];
		    while ((bytesRead = is.read(buffer, 0, 1026)) != -1) {
		    	os.write(buffer, 0, bytesRead);
		    }
		    os.close();
			
		}else{//调用fileboxAction通过http方式上传的文件
			//存入新的riseFile对象
			if(riseFile.getFileGUID().equals(fileItem.getFileGUID())){
				//填入相对路径名称
				fillFilePath(fileItem,riseFile,config);
			}else{//修改旧的riseFile对象
				//删除的旧文件
				//注:必须把原文件先删了
				String appSaveRoot=((RiseFileConfig)config.getParent()).getFileRoot();
				File oldFile=new File(appSaveRoot+File.separator+riseFile.getRealFullPath());
				oldFile.delete();
			}
			//更换文件名称
			renameFormalName(fileItem,riseFile,config);
		}
	}

	// 填入相对路径名称
	private void fillFilePath(RiseFileItem fileItem,RiseFile riseFile,RiseFileConfig config){
		String realPath=((RiseFileItemIODirect)fileItem).getFileFullName();
		String abstractPath=realPath.substring(realPath.indexOf(config.fileRoot+File.separator)
								,realPath.length());
		// 填充版本
		abstractPath=abstractPath.replaceAll("tempRiseVersion",riseFile.getMajorVersion()+"."+riseFile.getMinVersion());
		riseFile.setRealFullPath(abstractPath);
	}

	// 更换文件名称
	private void renameFormalName(RiseFileItem fileItem,RiseFile riseFile,RiseFileConfig config){
		String appSaveRoot=((RiseFileConfig)config.getParent()).getFileRoot();
		File originFile=new File(((RiseFileItemIODirect)fileItem).getFileFullName());
		File destFile=new File(appSaveRoot+File.separator+riseFile.getRealFullPath());	
		originFile.renameTo(destFile);
	}	

	

}
