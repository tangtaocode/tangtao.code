package net.risesoft.approve.risefile.content;

import javax.servlet.http.HttpServletRequest;

import net.risesoft.approve.risefile.config.RiseFileConfig;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;

/**
 * @author wangdong
 *  通常的文件储存方式，缓存在硬盘的特定路径下，并用系统的命名规则。
 */
public class RiseFileItemFactoryCache extends DiskFileItemFactory{

	public RiseFileConfig config;
	
	public RiseFileItemFactoryCache(RiseFileConfig config,HttpServletRequest request) {
		// 如果需要修改参数，请修改RiseFileItemConst.riseRepository、RiseFileItemConst.riseSizeThreshold
		super(RiseFileItemConst.riseSizeThreshold,
				RiseFileItemConst.getInstance(request).getFile());
		this.config=config;
	}
	
    public FileItem createItem(
            String fieldName,
            String contentType,
            boolean isFormField,
            String fileName
            ) {
        return new RiseFileItemCache(fieldName, contentType,
                isFormField, fileName, getSizeThreshold(), getRepository(),config);
    }
}
