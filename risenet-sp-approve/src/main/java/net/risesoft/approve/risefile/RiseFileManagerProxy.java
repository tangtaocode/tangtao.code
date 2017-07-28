package net.risesoft.approve.risefile;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.risesoft.model.Person;
import net.risesoft.approve.risefile.config.RiseFileConfig;
import net.risesoft.approve.risefile.config.RiseFileConfigManager;
import net.risesoft.approve.risefile.content.RiseFileItem;
import net.risesoft.approve.risefile.exception.RiseFileException;
import net.risesoft.approve.risefile.filter.RiseFileContext;
import net.risesoft.approve.risefile.handler.RiseFileInputStream;
import net.risesoft.approve.risefile.manager.RiseFileManager;

/**
 * @author wangdong
 * 同过配置获得相应app的manager,然后调用具体manager的操作。
 */
public class RiseFileManagerProxy{
	
	//通过文件的guid获得文件对象
	public static RiseFile get(String appName,String fileGUID) throws RiseFileException {
		return getManager(appName).get(fileGUID);
	}
	
	//通过条件获得文件集合
	public static List getRiseFiles(RiseFileConfig config,RiseFileContext context) throws RiseFileException {
		 return config.getFileManager().getRiseFiles(context);
	}
	
	public static RiseFileInputStream getContent(RiseFileConfig config,String fileGUID) throws RiseFileException {
		return config.getFileManager().getContent(fileGUID);
	}

	
	//保存文件属性，及文件内容
	public static RiseFile save(RiseFileItem fileItem,RiseFileConfig config, InputStream is, Person user) throws RiseFileException {
		return config.getFileManager().save(fileItem,config,is,user);
	}
	 
	//更新文件
	public static RiseFile update(RiseFileItem fileItem,RiseFileConfig config, InputStream is, Person user) throws RiseFileException {
   		return config.getFileManager().update(fileItem,config,is,user);
	} 
	
	//更新文件
	public static RiseFile update(RiseFileItem fileItem,RiseFileConfig config, InputStream is, Person user,HttpServletRequest request) throws RiseFileException {
		return config.getFileManager().update(fileItem,config,is,user,request);
	}

	//删除指定文件
		public static void delete(RiseFileItem fileItem,RiseFileConfig config) throws RiseFileException {
			config.getFileManager().delete(fileItem,config);
		}
		//删除当前实例的所有文件.
		//在删除一个实例的时候,会调用此方法;
		public static void deleteByAppInst(RiseFileConfig config,String appInstGUID) throws RiseFileException {
			config.getFileManager().deleteBatch(appInstGUID);
		}

		//从risefile.config.xml中,读取该app相应的manager.
	private static RiseFileManager getManager(String appName){
		return RiseFileConfigManager.getAppFileConfig(appName).getFileManager();
	}
}
