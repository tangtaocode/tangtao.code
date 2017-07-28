package net.risesoft.approve.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;



import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.risesoft.approve.risefile.RiseFile;
import net.risesoft.approve.risefile.RiseFileBeam;
import net.risesoft.approve.risefile.commons.StringUtil;


import net.risesoft.approve.risefile.commons.data.ListInMap;
import net.risesoft.approve.risefile.config.RiseFileConfig;
import net.risesoft.approve.risefile.config.RiseFileConfigManager;
import net.risesoft.approve.risefile.content.RiseFileItem;
import net.risesoft.approve.risefile.exception.RiseFileException;
import net.risesoft.approve.risefile.filter.RiseFileContext;
import net.risesoft.approve.risefile.manager.RiseFileManager;
import net.risesoft.approve.service.RisenetFileService;
import net.risesoft.common.util.ContextUtil;
import net.risesoft.model.OrgUnit;
import net.risesoft.model.Person;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.GUID;
import net.risesoft.util.RisesoftUtil;



public abstract class SysAbstractFileManager implements RiseFileManager{
	protected Logger log = LoggerFactory.getLogger(SysAbstractFileManager.class);

	private boolean b = true;
	
	private RisenetFileService risenetFileService;
	
	
	public RiseFile get(String fileGUID) throws RiseFileException {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		this.risenetFileService = ContextUtil.getBean(RisenetFileService.class);
		RiseFile riseFile=new RiseFile();
		List riseFiles = risenetFileService.findToDelete(fileGUID);
		for (Iterator iter = riseFiles.iterator(); iter.hasNext();) {
			Map map = (Map) iter.next();
			String guid = map.get("fileguid").toString();
			riseFile.setFileGUID(guid);
			riseFile.setFilename(map.get("FILENAME").toString());
			riseFile.setFileNameExt(map.get("FILENAMEEXT").toString());
			riseFile.setTitile(map.get("TITILE").toString());
			riseFile.setMajorName(map.get("MAJORNAME").toString());
			riseFile.setMajorVersion(Integer.parseInt(map.get("MAJORVERSION").toString()));
			riseFile.setMinVersion(Integer.parseInt(map.get("MINVERSION").toString()));
			riseFile.setAppName(map.get("APPNAME").toString());
			riseFile.setFileboxName(map.get("FILEBOXNAME").toString());
			riseFile.setAppInstGUID(map.get("APPINSTGUID").toString());
			riseFile.setCreateDate(sdf.format(map.get("CREATEDATE")));
			riseFile.setLastModified(map.get("LASTMODIFIED").toString());
			riseFile.setRealFullPath(map.get("REALFULLPATH").toString());
			riseFile.setSaveType(map.get("SAVETYPE").toString());
			riseFile.setHandles(map.get("HANDLES").toString());
			riseFile.setFileSize(Double.parseDouble(map.get("FILESIZE").toString()));
		}
		return riseFile;
	}
	
	public List getRiseFiles(RiseFileContext context) throws RiseFileException {
		//得到所有文件
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	List riseFiles=null;
	ListInMap riseFileMap=new ListInMap();
	List tempList=new ArrayList();
	List riseFileBeams=new ArrayList();
	this.risenetFileService = ContextUtil.getBean(RisenetFileService.class);
	riseFiles = risenetFileService.findByAppguidAndcheckBoxName(context.getFileboxName(), context.getAppInstanceGUID());
	//把数据库中的结果集按顺序载入入FileBoxBeam，并保存到List中
	for (Iterator iter = riseFiles.iterator(); iter.hasNext();) {
	 	Map map = (Map) iter.next();
		RiseFile riseFile = new RiseFile();
		riseFile.setFileGUID(map.get("fileguid").toString());
		riseFile.setFilename(map.get("FILENAME").toString());
		riseFile.setFileNameExt(map.get("FILENAMEEXT").toString());
		riseFile.setTitile(map.get("TITILE").toString());
		riseFile.setMajorName(map.get("MAJORNAME").toString());
		riseFile.setMajorVersion(Integer.parseInt(map.get("MAJORVERSION").toString()));
		riseFile.setMinVersion(Integer.parseInt(map.get("MINVERSION").toString()));
		riseFile.setAppName(map.get("APPNAME").toString());
		riseFile.setFileboxName(map.get("FILEBOXNAME").toString());
		riseFile.setAppInstGUID(map.get("APPINSTGUID").toString());
		riseFile.setCreateDate(sdf.format(map.get("CREATEDATE")));
		riseFile.setLastModified(map.get("LASTMODIFIED").toString());
		riseFile.setRealFullPath(map.get("REALFULLPATH").toString());
		riseFile.setSaveType(map.get("SAVETYPE").toString());
		riseFile.setHandles(map.get("HANDLES")==null?"":map.get("HANDLES").toString());
		riseFile.setFileSize(Double.parseDouble(map.get("FILESIZE").toString()));
		riseFileMap.put(riseFile.getFileGUID(),riseFile);
		if(!tempList.contains(riseFile.getFileGUID())){
			tempList.add(riseFile.getFileGUID());
		}
	}
	for (Iterator iter = tempList.iterator(); iter.hasNext();) {
		String filename = (String) iter.next();
		RiseFileBeam riseFileBeam=new RiseFileBeam();
		riseFileBeam.setFileName(filename);
		riseFileBeam.setRiseFiles((List)riseFileMap.get(filename));
		riseFileBeams.add(riseFileBeam);
	}

	return riseFileBeams;
}
		
	public void delete(RiseFileItem fileItem,RiseFileConfig config) throws RiseFileException {
		try {
			boolean isInitAppSaveRoot = false;
			this.risenetFileService = ContextUtil.getBean(RisenetFileService.class);
			List riseFiles = risenetFileService.findToDelete(fileItem.getFileGUID());
			for (Iterator iter = riseFiles.iterator(); iter.hasNext();) {
					Map map = (Map) iter.next();
					File f = new File(map.get("REALFULLPATH").toString());
					f.delete();
					isInitAppSaveRoot =true;
			}
			if(isInitAppSaveRoot){
				int i = risenetFileService.delete(fileItem.getFileGUID());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new RiseFileException("删除文件失败"+e.getMessage());
		}
	}
	
	private void deleteDirect(RiseFileItem  fileItem,RiseFileConfig config)throws RiseFileException {
			
		String appSaveRoot = null;
		boolean isInitAppSaveRoot = false;
		
		String sql = "from RiseFile where " 
 			+" appName='"+config.getAppName()+"'"
 			+" and appInstGUID='"+config.getAppInstGUID()+"'"
 			+" and filename='"+fileItem.getFileName()+"'";
		//List riseFiles = (List)session.createQuery(sql).list();
		List riseFiles = new ArrayList();
		for (Iterator iter = riseFiles.iterator(); iter.hasNext();) {
			RiseFile riseFile = (RiseFile) iter.next();
			if(!isInitAppSaveRoot){
				appSaveRoot=RiseFileConfigManager.getAppFileConfig(riseFile.getAppName()).getFileRoot(); 
				isInitAppSaveRoot=true;
			}
			deleteImp(appSaveRoot,riseFile);
		}
	}
	
	
	private void delete4keepDeleteVesion(RiseFileItem  fileItem,RiseFileConfig config) throws RiseFileException {
		
		//采用记版本的方式对删除的附件做标识
		String sql = "select max(risefile.minVersion) from RiseFile risefile where "
			+"  filename='"+fileItem.getFileName()+"'"+" and majorVersion="+config.getMajorVersion()+""
			+" and appInstGUID='"+config.getAppInstGUID()+"'";
		int maxMinversion=1;
		//Integer mms=(Integer)session.createQuery(sql).uniqueResult();
		Integer mms = 0;
		if(mms!=null){
			maxMinversion=mms.intValue()+1;
		}
		
		RiseFile riseFile;
		try {
			riseFile = newRiseFile(fileItem,config);
			riseFile.setMinVersion(maxMinversion);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void deleteBatch(String appInstGUID)throws RiseFileException{
		try {
			// 删除文件属性
			//List riseFiles=session.createQuery("from net.risesoft.components.risefile.RiseFile as risefile where risefile.appInstGUID='"+appInstGUID+"' ").list();
			List riseFiles = new ArrayList();
			String appSaveRoot=null;
			boolean isInitAppSaveRoot=false;
			for (Iterator iter = riseFiles.iterator(); iter.hasNext();) {
				RiseFile riseFile = (RiseFile) iter.next();
				if(!isInitAppSaveRoot){
					appSaveRoot=RiseFileConfigManager.getAppFileConfig(riseFile.getAppName()).getFileRoot(); 
					isInitAppSaveRoot=true;
				}
				deleteImp(appSaveRoot,riseFile);
			}
		
			
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new RiseFileException("删除文件失败"+e.getMessage());
		}
	}
	
	public RiseFile save(RiseFileItem fileItem, RiseFileConfig config, InputStream is, Person user) throws RiseFileException {
		this.risenetFileService = ContextUtil.getBean(RisenetFileService.class);
		RiseFile riseFile=null;	
		try {
			OrgUnit org = RisesoftUtil.getPersonManager().getBureau(ThreadLocalHolder.getTenantId(), user.getID());
			String deptname = org.getParentID();
			// 保存文件属性
			riseFile = newRiseFile(fileItem, config);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(sdf.parse(riseFile.getCreateDate())); 
			StringBuffer filePath = new StringBuffer()
			.append(config.getFileRoot()).append(File.separatorChar)
			.append(deptname)
//			.append(risenetFileService.returnDeptid(user.getID()))
			.append(File.separatorChar)
			.append(user.getLoginName()).append(File.separatorChar)
			.append(calendar.get(Calendar.YEAR)).append(calendar.get(Calendar.MONTH)+1)
			.append(File.separatorChar)
			.append(riseFile.getTitile()).append(".")
			.append(riseFile.getFileGUID()).append(".")
			.append(riseFile.getMajorVersion()+"."+riseFile.getMinVersion()).append(".")
			.append(riseFile.getFileNameExt());
			riseFile.setRealFullPath(filePath.toString());
//			this.risenetFileService = ContextUtil.getBean(RisenetFileService.class);
			risenetFileService.save(riseFile);
			//保存内容
			updateContent(riseFile,fileItem,config,is,user);
			
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new RiseFileException("保存文件失败"+e.getMessage());
		}finally{
		}
		return riseFile;
	}
	
	
	public RiseFile update(RiseFileItem fileItem, RiseFileConfig config, InputStream is, Person user) throws RiseFileException {
		RiseFile riseFile = null;
		List riseFiles = null;
		RiseFile oldRiseFile = null;
		this.risenetFileService = ContextUtil.getBean(RisenetFileService.class);
		try {
			if (config.keepMinimalVersion) {
				// 保存文件属性
				riseFile=newRiseFile(fileItem,config);
				riseFiles=risenetFileService.find(fileItem.getFileName(), config.getMajorVersion(), config.getAppInstGUID());
				//把数据库中的结果集按顺序载入入FileBoxBeam，并保存到List中
				if(riseFiles.size() > 0){
					for (Iterator iter = riseFiles.iterator(); iter.hasNext();) {
						Map map = (Map) iter.next();
						oldRiseFile = new RiseFile();
						oldRiseFile.setFileGUID(map.get("fileguid").toString());
						oldRiseFile.setFilename(map.get("FILENAME").toString());
						oldRiseFile.setFileNameExt(map.get("FILENAMEEXT").toString());
						oldRiseFile.setTitile(map.get("TITILE").toString());
						oldRiseFile.setMajorName(map.get("MAJORNAME").toString());
						oldRiseFile.setMajorVersion(Integer.parseInt(map.get("MAJORVERSION").toString()));
						oldRiseFile.setMinVersion(Integer.parseInt(map.get("MINVERSION").toString()));
						oldRiseFile.setAppName(map.get("APPNAME").toString());
						oldRiseFile.setFileboxName(map.get("FILEBOXNAME").toString());
						oldRiseFile.setAppInstGUID(map.get("APPINSTGUID").toString());
						oldRiseFile.setCreateDate((map.get("CREATEDATE").toString()));
						oldRiseFile.setLastModified(map.get("LASTMODIFIED").toString());
						oldRiseFile.setRealFullPath(map.get("REALFULLPATH").toString());
						oldRiseFile.setSaveType(map.get("SAVETYPE").toString());
						oldRiseFile.setHandles(map.get("HANDLES").toString());	
					}
					deleteImp("",oldRiseFile);
				}
				Integer maxMinversion = new Integer(0);
				if(maxMinversion == null){
					maxMinversion = new Integer(0);
				}
				riseFile.setMinVersion(maxMinversion.intValue()+1);
				Calendar calendar = Calendar.getInstance();
				StringBuffer filePath = new StringBuffer()
					.append(config.getFileRoot()).append(File.separatorChar)
					.append(risenetFileService.returnDeptid(user.getID())).append(File.separatorChar)
					.append(user.getLoginName()).append(File.separatorChar)
					.append(calendar.get(Calendar.YEAR)).append(calendar.get(Calendar.MONTH)+1)
					.append(File.separatorChar)
					.append(riseFile.getTitile()).append(".")
					.append(riseFile.getFileGUID()).append(".")
					.append(riseFile.getMajorVersion()+"."+riseFile.getMinVersion()).append(".")
					.append(riseFile.getFileNameExt());
				riseFile.setRealFullPath(filePath.toString());
				// 保存内容
				updateContent(riseFile,fileItem,config,is,user);
			} else {
			
				String sql = "from RiseFile where " 
							+"  filename='"+fileItem.getFileName()+"'"
				 			+" and majorVersion="+config.getMajorVersion()+""
				 			+" and appInstGUID='"+config.getAppInstGUID()+"'";
				//riseFile=(RiseFile)session.createQuery(sql).uniqueResult();
				if(riseFile==null){
					//还没此文件
					riseFile=newRiseFile(fileItem,config);
					//保存内容
					updateContent(riseFile,fileItem,config,is,user);
				}else{
					//以有文件,则更新文件属性
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String s = sdf.format(new Date());
					riseFile.setLastModified(s);
					riseFile.setHandles(StringUtil.list2String(config.getPlusList("streamHandles","isHandleExtends")));
					//更新文件内容
					updateContent(riseFile,fileItem,config,is,user);
				}		
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new RiseFileException("更新文件失败"+e.getMessage());
		}
		return riseFile;
	}
	
	/**
	 * 灵活传入其它参数
	 * @param fileItem
	 * @param config
	 * @param is
	 * @param user
	 * @param request
	 * @return
	 * @throws RiseFileException
	 */
	public RiseFile update(RiseFileItem fileItem, RiseFileConfig config, InputStream is, Person user,HttpServletRequest request) throws RiseFileException {
		//Session session = HibernateFactory.getSessionFactoty().openSession();
		RiseFile riseFile = null;
		List riseFiles = null; 
		RiseFile oldRiseFile = null;
		try { 
			//Transaction tx = session.beginTransaction();
			String topicType=request.getParameter("topicType");
			if(topicType==null)topicType="";
			if("3".equals(topicType))topicType="1";
			if (config.keepMinimalVersion) {
				// 保存文件属性
				riseFile=newRiseFile(fileItem,config);
				String sql="from RiseFile where filename='"+fileItem.getFileName()+"' and majorVersion="+config.getMajorVersion()+
						   " and appInstGUID='"+config.getAppInstGUID()+"'";
//				Integer maxMinversion=(Integer)session.createQuery(sql).uniqueResult();
//				riseFiles=(List)session.createQuery(sql).list();
				//把数据库中的结果集按顺序载入入FileBoxBeam，并保存到List中
/*				if(riseFiles.size() > 0){
					for (Iterator iter = riseFiles.iterator(); iter.hasNext();) {
						oldRiseFile = (RiseFile) iter.next();
					}
					deleteImp("",oldRiseFile,session);
				}*/
				Integer maxMinversion = new Integer(0);
				if(maxMinversion == null){
					maxMinversion = new Integer(0);
				}
				riseFile.setMinVersion(maxMinversion.intValue()+1);
				Calendar calendar = Calendar.getInstance();
				//calendar.setTime(riseFile.getCreateDate()); 
				StringBuffer filePath = new StringBuffer()
					.append(config.getFileRoot()).append(File.separatorChar)
					.append(user.getBureauId()).append(File.separatorChar)
					.append(user.getLoginName()).append(File.separatorChar)
					.append(calendar.get(Calendar.YEAR)).append(calendar.get(Calendar.MONTH)+1)
					.append(File.separatorChar)
					.append(riseFile.getTitile()).append(".")
					.append(riseFile.getFileGUID()).append(".")
					.append(riseFile.getMajorVersion()+"."+riseFile.getMinVersion()).append(".")
					.append(riseFile.getFileNameExt());
				riseFile.setRealFullPath(filePath.toString());
				riseFile.setFileType(topicType);
//				session.save(riseFile);
				// 保存内容
				updateContent(riseFile,fileItem,config,is,user);
			} else {
				String sql = "from RiseFile where " 
							+"  filename='"+fileItem.getFileName()+"'"
				 			+" and majorVersion="+config.getMajorVersion()+""
				 			+" and appInstGUID='"+config.getAppInstGUID()+"'";
//				riseFile=(RiseFile)session.createQuery(sql).uniqueResult();
				if(riseFile==null){
					//还没此文件
					riseFile=newRiseFile(fileItem,config);
					riseFile.setFileType(topicType);
//					session.save(riseFile);
					//保存内容
					updateContent(riseFile,fileItem,config,is,user);
				}else{
					//以有文件,则更新文件属性
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String s = sdf.format(new Date());
					riseFile.setLastModified(s);					
					riseFile.setHandles(StringUtil.list2String(config.getPlusList("streamHandles","isHandleExtends")));
					//更新文件内容
					updateContent(riseFile,fileItem,config,is,user);
				}
				
			}
		//	tx.commit();
			
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new RiseFileException("更新文件失败"+e.getMessage());
		}finally{
			/*if(session!=null){
				session.close();
			}*/
		}
		return riseFile;
	}
	protected abstract void updateContent(RiseFile riseFile,RiseFileItem fileItem, RiseFileConfig config,InputStream is,Person user) throws SQLException, IOException;
	
	protected abstract void deleteImp(String appSaveRoot,RiseFile riseFile);
	
	protected RiseFile newRiseFile(RiseFileItem fileItem, RiseFileConfig config) throws ParseException{
		RiseFile riseFile=new RiseFile();
		if(fileItem.getFileGUID()==null){
			riseFile.setFileGUID(new GUID().toString());
		}else{
			riseFile.setFileGUID(fileItem.getFileGUID());
		}
		Calendar today=Calendar.getInstance();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = sdf.format(new Date());
		String handles=StringUtil.list2String(config.getPlusList("streamHandles","isHandleExtends"));
		
		riseFile.setFilename(fileItem.getFileName());
		riseFile.setFileboxName(config.getFileboxName());
		riseFile.setFileNameExt(fileItem.getFileNameExt());
		riseFile.setFileType(fileItem.getFileType());
		riseFile.setTitile(fileItem.getTitile());
		riseFile.setMajorName(config.getMajorVersionName());
		riseFile.setMajorVersion(Integer.parseInt(config.getMajorVersion()));
		riseFile.setMinVersion(1);
		riseFile.setAppName(config.getAppName());
		if(config.getFileboxName().equals("TANGER_OCX")){
			String[] appInstGUIDs = config.getAppInstGUID().split("[|]");
			riseFile.setAppInstGUID(appInstGUIDs[0]);
		}else{
			riseFile.setAppInstGUID(config.getAppInstGUID());
		}
		riseFile.setCreatorGUID(fileItem.getCurrentUser());
		//TODO;换成deptguid
		riseFile.setDeptGUID(fileItem.getCurrentUser());
		riseFile.setCreateDate(s);
		riseFile.setLastModified(s);
		riseFile.setSaveType(config.getSaveMode());
		riseFile.setHandles(handles);
		riseFile.setFileSize(fileItem.getFileSize());
		return riseFile;
	}
	
}
