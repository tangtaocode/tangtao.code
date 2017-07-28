package net.risesoft.approve.risefile.content;

import java.io.File;

import net.risesoft.approve.risefile.config.RiseFileConfig;
import net.risesoft.approve.risefile.config.RiseFileConfigManager;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;

/**
 * @author wangdong 直接通过riseconfig存到指定的目录中. 
 * 文件名称采用risefile中的命名规则：文件名+guid+版本号.
 * 如果在riseconfig中对文件有stramHandle,在存储过程中完成streamHandle.
 */
public class RiseFileItemFactoryIODirect extends DiskFileItemFactory {
	
	public RiseFileConfig config;
	
	public RiseFileItemFactoryIODirect(RiseFileConfig config) {
		super(RiseFileConfigManager.getThreadMemorySize(),
				new File(config.getFileRoot()));
		this.config=config;
	}

	public FileItem createItem(String fieldName, String contentType,
			boolean isFormField, String fileName) {
		return new RiseFileItemIODirect(fieldName, contentType, isFormField,
				fileName, getSizeThreshold(), getRepository() ,config);
	}
}