package net.risesoft.approve.risefile.manager;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import net.risesoft.approve.controller.SysAbstractFileManager;
import net.risesoft.approve.risefile.RiseFile;
import net.risesoft.approve.risefile.config.RiseFileConfig;
import net.risesoft.approve.risefile.content.RiseFileItem;
import net.risesoft.approve.risefile.exception.RiseFileException;
import net.risesoft.approve.risefile.handler.RiseFileInputStream;
import net.risesoft.model.Person;

import org.hibernate.Hibernate;
import org.hibernate.Session;

public class SysDBRiseFileManager extends SysAbstractFileManager{

	private static org.apache.log4j.Logger log = net.risesoft.approve.risefile.commons.LogFactory
	.getLog(SysDBRiseFileManager.class);
	
	

	public RiseFileInputStream getContent(String fileGUID) throws RiseFileException {
		RiseFile riseFile=get(fileGUID);
		//获得文件流Imp
		RiseFileInputStream fis=new RiseFileInputStream();
		try {
			fis.size=riseFile.getContent().length();
			fis.fileFullName="RiseNet_File.Content";
			fis.inputStream=riseFile.getContent().getBinaryStream();
		} catch (SQLException e) {
			log.info(e,e);
			throw new RiseFileException("获得内容失败");
		}
		return fis;
	}

	

	// 将文件写入数据库
	protected void updateContent(RiseFile riseFile,RiseFileItem fileItem, RiseFileConfig config,InputStream is, Person user) throws SQLException, IOException{
		
//		session.flush();
		//riseFile.setContent(Hibernate.createBlob(is)); 
//		session.flush();
//		session.flush();
//		session.refresh(riseFile, LockMode.UPGRADE);//����
//		//OutputStream os = riseFile.getContent().setBinaryStream(0);
//		//is=new FileInputStream("D:\\������\\10Talismans.exe");
//		OutputStream os = new FileOutputStream("D:\\todo.txt");
//		
//		int bytesRead = 0;
//	    byte[] buffer = new byte[1026];
//	    while ((bytesRead = is.read(buffer, 0, 1026)) != -1) {
//	    	os.write(buffer, 0, bytesRead);
//	    }
//	    //
//	    //is.close();
//	    //
//	    os.close();
	}
	
	protected void deleteImp(String appSaveRoot,RiseFile riseFile){
		// 删除文件内容
		//session.delete(riseFile);
	}

}
