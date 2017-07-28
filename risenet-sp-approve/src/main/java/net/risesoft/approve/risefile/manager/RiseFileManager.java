package net.risesoft.approve.risefile.manager;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;

import net.risesoft.approve.risefile.RiseFile;
import net.risesoft.approve.risefile.config.RiseFileConfig;
import net.risesoft.approve.risefile.content.RiseFileItem;
import net.risesoft.approve.risefile.exception.RiseFileException;
import net.risesoft.approve.risefile.filter.RiseFileContext;
import net.risesoft.approve.risefile.handler.RiseFileInputStream;
import net.risesoft.model.Person;

/**
 * 操作文件接口
 */
public interface RiseFileManager {

	// 通过文件的guid获得文件对象
	public RiseFile get(String fileGUID) throws RiseFileException;
	
	public RiseFileInputStream getContent(String fileGUID)throws RiseFileException;

	// 通过条件获得文件集合
	public List getRiseFiles(RiseFileContext context) throws RiseFileException;

	// 保存文件属性，及文件内容
	public RiseFile save(RiseFileItem fileItem, RiseFileConfig config, InputStream is, Person user)
			throws RiseFileException;

	//public RiseFile save(RiseFileItem fileItem, RiseFileConfig config, InputStream is,Session session, Person user) throws RiseFileException;
	// 更新文件属性
	public RiseFile update(RiseFileItem fileItem, RiseFileConfig config, InputStream is ,Person user) throws RiseFileException;

	// 删除指定文件
	public void delete(RiseFileItem fileItem,RiseFileConfig config) throws RiseFileException;
	
	public void deleteBatch(String appInstGUID)throws RiseFileException;
    //更灵活的更新文件属性
	public RiseFile update(RiseFileItem fileItem, RiseFileConfig config, InputStream is, Person user, HttpServletRequest request) throws RiseFileException;

}
