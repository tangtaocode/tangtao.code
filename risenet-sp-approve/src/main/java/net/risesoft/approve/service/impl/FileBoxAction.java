package net.risesoft.approve.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*import net.risesoft.approve2.helper.ProjectInfoStateHelper;*/





import javax.sql.DataSource;

import net.risesoft.approve.controller.OnlineApproveController;
import net.risesoft.approve.entity.jpa.RisenetEmployeeUnits;
import net.risesoft.approve.risefile.FileBoxFactory;
import net.risesoft.approve.risefile.RiseFile;
import net.risesoft.approve.risefile.RiseFileConst;
import net.risesoft.approve.risefile.RiseFileManagerProxy;
import net.risesoft.approve.risefile.commons.StringUtil;
import net.risesoft.approve.risefile.config.RiseFileConfig;
import net.risesoft.approve.risefile.config.RiseFileConfigManager;
import net.risesoft.approve.risefile.content.RiseFileItem;
import net.risesoft.approve.risefile.content.RiseFileItemFactoryCache;
import net.risesoft.approve.risefile.content.RiseFileItemFactoryIODirect;
import net.risesoft.approve.risefile.content.RiseFileItemImp;
import net.risesoft.approve.risefile.exception.RiseFileException;
import net.risesoft.approve.risefile.filter.RiseFileContext;
import net.risesoft.approve.risefile.handler.RiseFileInputStream;
import net.risesoft.approve.risefile.manager.MeetingTopicFileManager;
import net.risesoft.approve.risefile.manager.SysFSRiseFileManager;
import net.risesoft.approve.risefile.tag.RiseFileTag;
import net.risesoft.approve.service.RisenetFileService;
import net.risesoft.common.util.ContextUtil;
import net.risesoft.model.OrgUnit;
import net.risesoft.model.Person;
/*import net.risesoft.repository.dao.ArchAttachmentDao;
import net.risesoft.repository.model.ArchAttachment;
import net.risesoft.risemeeting.MeetingConst;
import net.risesoft.risemeeting.MeetingFactory;
import net.risesoft.risemeeting.MeetingUser;
import net.risesoft.risemeeting.topic.Topic;
import net.risesoft.risemeeting.topic.TopicConst;
import net.risesoft.risemeeting.topic.TopicFactory;
import net.risesoft.taskperform.dao.TaskFeedbackDao;
import net.risesoft.taskperform.model.TaskFeedback;
import net.risesoft.workflow.core.Workflow;
import net.risesoft.workflow.core.WorkflowFactory;*/



import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.GUID;
import net.risesoft.util.GUIDUtil;
import net.risesoft.util.RisesoftUtil;
import net.risesoft.util.ServiceUtil;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;


public class FileBoxAction {
	protected Logger log = LoggerFactory.getLogger(FileBoxAction.class);

	
	private RiseFileConfig config=null;
	private RiseFileContext context=null;
	
	private RisenetFileService risenetFileService;
	/**
	 * @param request
	 * @param response
	 * @return
	 * 返回数据为<root>...</root>
	 * 如果报错返回数据为error:xxx
	 */
	public String getFilesXML(HttpServletRequest request, HttpServletResponse response){
		Person person = ThreadLocalHolder.getPerson();
//		this.risenetFileService = ContextUtil.getBean(RisenetFileService.class);
//		String deptname = risenetFileService.returnDeptName(person.getID());
		OrgUnit org = RisesoftUtil.getPersonManager().getBureau(ThreadLocalHolder.getTenantId(), person.getID());
		String deptname = org.getName();
		String isAllVersionStrg=request.getParameter("isAllVersion");
		boolean isAllVersion=false;
		if(isAllVersionStrg!=null&&isAllVersionStrg.equals("true")){
			isAllVersion=true;
		}
		if(config==null){
			makeConfig(request);
		}
		context=makeContext(request);
		String xml=null;
		try {
			xml=FileBoxFactory.getFileBoxXML(config,context,isAllVersion,deptname);
		} catch (Exception e) {
			xml="error:"+e.getMessage();
		}
		return xml;
	}
	
	
	/**会议通用调用返回方法,可以根据条件选择性获取目标数据(金平宣加2007.10.11)
	 * @param request
	 * @param response
	 * @return
	 * 返回数据为<root>...</root>
	 * 如果报错返回数据为error:xxx
	 */
	/*public String getMeetTopicFilesXML(HttpServletRequest request, HttpServletResponse response){
		String isAllVersionStrg=request.getParameter("isAllVersion");
		boolean isAllVersion=false;
		if(isAllVersionStrg!=null&&isAllVersionStrg.equals("true")){
			isAllVersion=true;
		}
		if(config==null){
			makeConfig(request);
		}
		context=makeContext(request);
		
		String xml=null;
		
		try {
			String meetGuids="";
			if("3".equals(request.getParameter("topicType")))//会议加载议题情况
			{
				//meetGuids=MeetingFactory.getInstance().getMeetTopicGuids(context.getAppInstanceGUID());
				context.setAppInstanceGUIDs(meetGuids);
			}
			context.setWhere(request.getParameter("where"));
			xml=FileBoxFactory.getFileBoxXML(config,context,isAllVersion);
		} catch (Exception e) {
			log.error(e.getMessage());
			xml="error:"+e.getMessage();
		}
		return xml;
	}*/
	
	
	public String downloadFile(HttpServletRequest request, HttpServletResponse response){
		String error=null;
		try{
			long time1 = 0, time2 = 0;
			time1 = System.currentTimeMillis();
			String fileGUID=request.getParameter("fileGUID");
			String fileName=request.getParameter("fileName");
			if(GUIDUtil.inValidate(fileGUID)){
				throw new IllegalArgumentException("没有传入正确的fileGUID");
			}
			makeConfig(request);
			RiseFileInputStream fileis=RiseFileManagerProxy.getContent(config,fileGUID);

			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition",
							"attachment;filename=\"" + fileName
							+ "\"");
			response.setContentLength((int) fileis.size);
			
			ServletOutputStream sos = response.getOutputStream();
			byte buffer[] = new byte[8 * 1024];
			int b=0;
			while ((b = fileis.inputStream.read(buffer)) != -1) {
				sos.write(buffer, 0, b);
			}
			sos.flush();
			fileis.inputStream.close();
			if(fileis.sourceInputStream!=null){
				fileis.sourceInputStream.close();
			}
			sos.close();
			time2 = System.currentTimeMillis();
			log.debug("文件搜索耗时： " + (time2 - time1) + "ms");
		}catch (RiseFileException e) {
			log.info("文件系统出现异常",e);
		}catch(IllegalArgumentException e){
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		} catch(Exception e){
			log.error(e.getMessage());
		}
		return error;
		
	}

	/**
	 * @param request
	 * @param response
	 * @return
	 * 返回数据为fileBoxid;<root>...</root>
	 * 如果报错返回数据为fileBoxid;error:xxx
	 */
	public String saveUpdateFile(HttpServletRequest request, HttpServletResponse response){
		String msg=null;
		long time1 = 0, time2 = 0;
		double d;
		time1 = System.currentTimeMillis();
		try {
			String actionType = request.getParameter("action");
			makeConfig(request);
			FileItemFactory itemfactory =null;
			msg=config.getFileboxName()+";";
			//是否系统的io存储方式
			
			boolean isdefaultio =(config.getFileManager() instanceof SysFSRiseFileManager)
													&&config.getSaveMode().equals("fs");
			String ss = config.getFileRoot();
			//对于使用系统的io存储方式（manager），直接保存到指定目录
			//否则缓存到硬盘
			if(isdefaultio){
				itemfactory = new RiseFileItemFactoryIODirect(config);
			}else{ 
				itemfactory=new RiseFileItemFactoryCache(config,request);
			}
			ServletFileUpload fu = new ServletFileUpload(itemfactory);
			//upload.setHeaderEncoding("gbk");
			fu.setSizeMax(1000 * 1024 * 1024);
			List fileItems = fu.parseRequest(request);
/*			Process process = null;
			String s = "cmd.exe /c net use " + "\\"+ "\\" + "10.192.203.7" + "\\"+"ipc$ " + "1 " + "/user:guest";
			System.out.println(s);
			try{
				process = Runtime.getRuntime().exec(s);     //调用doc命令
	        }catch(Exception e){
	        	e.printStackTrace();   
	        }*/
			for (int i = 0; i < fileItems.size(); i++) {
				FileItem fi = (FileItem) fileItems.get(i);
				if (fi.isFormField()) {
				}else {
					
					RiseFileItem fileItem=(RiseFileItem)fi;
					//填入正是的CurrentUser
					//Person user = (Person) request.getSession().getAttribute("loginPerson");
					Person person  =  ThreadLocalHolder.getPerson();
					//查询到当前用户在rc7中的id,
					String userID = person.getID();
					d = Double.valueOf(fi.getSize()+"").doubleValue();
					d = d/1024/1024;
					fileItem.setFileSize(d);
					fileItem.setCurrentUser(userID);
					InputStream is=fileItem.getInputStream();
					//RiseFileManagerProxy.update(fileItem,config,is,person);
					//统一采用update方式，通过版本删除需要支持版本号
					if(actionType.equalsIgnoreCase("save")){
						RiseFileManagerProxy.save(fileItem, config, is, person);
					}else if(actionType.equalsIgnoreCase("update")){
						RiseFileManagerProxy.update(fileItem,config,is,person);
					}
					if(is!=null){
						is.close();
					}
				}
			}
			//更新数据
			msg+=getFilesXML(request,response);
			time2 = System.currentTimeMillis();
			log.debug("文件存储耗时： " + (time2 - time1) + "ms");
			Runtime rt = Runtime.getRuntime();
			NumberFormat nf = NumberFormat.getNumberInstance();
			double total = (double)rt.totalMemory() / (1024*1024);
			double free = (double)rt.freeMemory() / (1024*1024);	
			log.debug("Total Memory=" + nf.format(total) + "m    Free Memory=" + nf.format(free) + "m"); 
		} catch (Exception ex) {
			log.error(ex.getMessage());
			msg+="error:文件保存失败!请重试.如果还不成功请联系管理员";
		} finally {
		}
		
		return msg;
	}
	
	/**
	 * @param request
	 * @param response
	 * @return
	 * 返回数据为fileBoxid;<root>...</root>
	 * 如果报错返回数据为fileBoxid;error:xxx
	 */
	public String saveUpdateMeetTopicFile(HttpServletRequest request, HttpServletResponse response){
		String msg=null;
		long time1 = 0, time2 = 0;
		double d;
		time1 = System.currentTimeMillis();
		try {
			String actionType = request.getParameter("action");
			makeConfig(request);
			FileItemFactory itemfactory =null;
			msg=config.getFileboxName()+";";
			//是否系统的io存储方式
			String topicType=request.getParameter("topicType");
			//topicType类型：1、议题材料；2、展现材料；3、在会议理直接加议题材料。
			boolean isdefaultio =(config.getFileManager() instanceof SysFSRiseFileManager)
													&& config.getSaveMode().equals("fs");
			String ss = config.getFileRoot();
			//对于使用系统的io存储方式（manager），直接保存到指定目录
			//否则缓存到硬盘
			if(isdefaultio){
				itemfactory = new RiseFileItemFactoryIODirect(config);
			}else{ 
				itemfactory=new RiseFileItemFactoryCache(config,request);
			}
			ServletFileUpload fu = new ServletFileUpload(itemfactory);

			List fileItems = fu.parseRequest(request);
			for (int i = 0; i < fileItems.size(); i++) {
				FileItem fi = (FileItem) fileItems.get(i);
				if (fi.isFormField()) {
				}else {
					
					RiseFileItem fileItem=(RiseFileItem)fi;
					//填入正是的CurrentUser
					Person user = (Person) request.getSession().getAttribute("loginPerson");
					d = Double.valueOf(fi.getSize()+"").doubleValue();
					d = d/1024/1024;
					fileItem.setFileSize(d);
					fileItem.setCurrentUser(user.getIDNum());
					InputStream is=fileItem.getInputStream();
					String meetingGuid="";
					/*Topic topic=null;
					if("3".equals(topicType))
					{
						MeetingUser meetingUser=(MeetingUser)request.getSession().getAttribute(MeetingConst.SESSION_MEETINGUSER);
						meetingGuid=config.getAppInstGUID();
						config.setAppInstGUID(new GUID().toString());
						
						topic = TopicFactory.getInstance().creator(config.getAppInstGUID());
			          	topic.setAppServiceID(meetingUser.getAppServiceID());
			          //System.out.println("stuff name is ..... "+StringI18.a2u(mySmartUpload.getFiles().getFile(iFile).getFileName()));
					    topic.setTopicName(fileItem.getTitile());
			          	topic.setCreatedDate(new java.sql.Date(System.currentTimeMillis()));
			          	topic.setSubmitDate(new java.sql.Date(System.currentTimeMillis()));
			          	topic.setTabIndex(i);
			          	topic.setTopicCreator(meetingUser.getUserGUID());
			          	topic.setTopicDepartment(meetingUser.getUnitsGUID());
			          	topic.setTopicReportUnits(user.getID());
			          	topic.setTopicMeetingSort(request.getParameter("meetSort"));//@TODO
			          	topic.setTopicSort(request.getParameter("meetSort"));
			          	//附件临时转化过来的议题直接被采用。
			          	topic.setTopicStatus(TopicConst.TOPIC_STATUS_ACCEPT);			      		
					}*/
					RiseFileManagerProxy.update(fileItem,config,is,user,request);
					/*if("3".equals(topicType) && topic!=null)
					{
						MeetingFactory.getInstance().updateMeetingTopicData(topic,meetingGuid); //创建议题数据和关系
						config.setAppInstGUID(meetingGuid);
					}*/
					/*if(is!=null){
						is.close();
					}*/
				}
			}
			//更新数据
			//msg+=getMeetTopicFilesXML(request,response);
			time2 = System.currentTimeMillis();
			log.debug("文件存储耗时： " + (time2 - time1) + "ms");
		} catch (Exception ex) {
			log.error(ex.getMessage());
			msg+="error:文件保存失败!请重试.如果还不成功请联系管理员";
		} finally {
		}
		
		return msg;
	}
	
	/**
	 * 根据附件的appInstGUIDs删除其下面的所以附件
	 * @param guids
	 * @return
	 */
	/*public boolean deleteFiles(String[] guids) {
		boolean b = false;
		List list = null;
		File file = null;
		ArchAttachmentDao archAttachmentDao = new ArchAttachmentDao();
		ArchAttachment archAttachment = null;
		try {
			list = archAttachmentDao.getArchattachmentList(guids);
			if(list != null && list.size() > 0){
				b = archAttachmentDao.deleteArchAttachments(list);
				for(int j = 0; j < list.size(); j++){
					archAttachment = (ArchAttachment)list.get(j);
					file = new File(archAttachment.getRealfullpath());
					boolean isexists = file.exists();
					if(isexists){
						file.delete();
					}
				}
			}
		} catch (Exception ex) {
			log.info(ex, ex);
		} 
		return b;
	}
	*/
	/**
	 * @param request
	 * @param response
	 * @return
	 * 返回数据为fileBoxid;<root>...</root>
	 * 如果报错返回数据为fileBoxid;error:xxx
	 */
	public String deleteFile(HttpServletRequest request,HttpServletResponse response) {
		String msg = null;
		try {
			//String fileName = request.getParameter("fileName");
			String fileGuid = request.getParameter("fileGuid");
			String isShareArchManager = (String)request.getAttribute("isShareArchManager");
			boolean isManager = false;
			Person user = (Person) request.getSession().getAttribute("loginPerson");
			makeConfig(request);
			msg=config.getFileboxName()+";";	
			RiseFileItemImp fileItem=new RiseFileItemImp();
			fileItem.fileGUID = fileGuid;
			//fileItem.fileName = fileName;
			//fileItem.titile = fileName.substring(0,fileName.lastIndexOf(".")>-1 ? fileName.lastIndexOf(".") : fileName.length());//modify by liujun 2010-12-16
			fileItem.fileNameExt = RiseFileConst.RISEFILEEXTNAME_RISECALLBACK;
			//fileItem.currentUser =user.getIDNum();
			if(isShareArchManager != null && isShareArchManager.equals("true")){
				isManager = true;
			}
			/**
			 * add by liujun 流程管理员添加删除附件的权限
			 */
			/*Workflow wf=null;
			wf=getWorkflowFormConfig(config);
			if(wf!=null && wf.getWorkflowMetaData().isWorkflowManager(user)){
				isManager=true;
			}*/			
			fileItem.setManager(isManager);
			RiseFileManagerProxy.delete(fileItem, config);
			
			msg+="ok:文件删除成功";
		} catch (Exception ex) {
			log.error(ex.getMessage());
			msg+= "error:文件删除失败!请重试.如果还不成功请联系管理员";
		} finally {

		}
		return msg;
	}
	
	/**
	 * 根据会议id删除只有单个议题的议题
	 * @param request
	 * @param response
	 * @return
	 */
	public String deleteTopicFile(HttpServletRequest request,HttpServletResponse response) {
		String msg = null;
		String fileGUID = request.getParameter("fileGuid");
		String appname = request.getParameter("appname");
		RiseFile riseFile = null;
		try {
			riseFile = RiseFileManagerProxy.get(appname,fileGUID);
			new MeetingTopicFileManager().deleteMeetTopicFile(riseFile);
			msg+="ok:文件删除成功";
		} catch (Exception ex) {
			log.error(ex.getMessage());
			msg+= "error:文件删除失败!请重试.如果还不成功请联系管理员";
		}
		return msg;
	}
	
	/*public Workflow getWorkflowFormConfig(RiseFileConfig config){
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		String sql="select t.workflow_guid from office_workflowinstance t where t.workflowinstance_guid='"+config.getAppInstGUID()+"'";
		try{
			conn=Conn.getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			if(rs.next()){
				return WorkflowFactory.lookupWorkflow(rs.getString("workflow_guid"));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			Conn.closeConnection(conn, stmt, null, rs);
		}
		return null;
	}*/
	
	/**
	 * 根据会议id删除有多个议题的单个议题
	 * @param request
	 * @param response
	 * @return
	 */
	public String deleteTopicFiles(HttpServletRequest request,HttpServletResponse response) {
		String msg = null;
		String fileGUID = request.getParameter("fileGuid");
		RiseFile riseFile = null;
		MeetingTopicFileManager meetingTopicFileManager = new MeetingTopicFileManager();
		try {
			riseFile = meetingTopicFileManager.getTopicFile(fileGUID);
			meetingTopicFileManager.deleteTopicFile(riseFile);
			msg+="ok:文件删除成功";
		} catch (Exception ex) {
			log.error(ex.getMessage());
			msg+= "error:文件删除失败!请重试.如果还不成功请联系管理员";
		}
		return msg;
	}
	
	public int getTopicFileCount(HttpServletRequest request,HttpServletResponse response) {
		int count = 0;
		String fileGUID = request.getParameter("fileGuid");
		try {
			count = new MeetingTopicFileManager().getMeetTopicFiles(fileGUID);
		} catch (Exception ex) {
			log.error(ex.getMessage());
		} 
		return count;
	}
	
	private void makeConfig(HttpServletRequest request){
		
		String appname=request.getParameter("appname");
		if(appname==null){
			throw new IllegalArgumentException("没有传入正确的APPNAME");
		}
		RiseFileConfig config=RiseFileConfigManager.getExtendedFileConfig(appname);
		config.setFileboxName(StringUtil.parseNull(request.getParameter("fileboxname")));
		config.setSaveMode(StringUtil.parseNull(request.getParameter("savemode")));
		config.setFileRoot(StringUtil.parseNull(request.getParameter("fileroot")));
		if(!request.getParameter("majorversion").equals("")){
			config.setMajorVersion(request.getParameter("majorversion"));
		}
		config.setAppInstGUID(request.getParameter("appInstGUID"));
		config.setMajorVersionName(StringUtil.parseNull(request.getParameter("majorversionname")));
		config.setKeepMinimalVersion(Boolean.valueOf(request.getParameter("keepminimalversion")).booleanValue());
		
		config.setHandleExtends(Boolean.valueOf(request.getParameter("ishandleextends")).booleanValue());
		if(!request.getParameter("streamhandles").equals("")){
			config.setStreamHandles(Arrays.asList(request.getParameter("streamhandles").split(";")));
		}
		this.config=config;
	}
	
	private RiseFileContext makeContext(HttpServletRequest request){
		String context=request.getParameter("context");
		return RiseFileContext.getFileContext(context);
	}
}
