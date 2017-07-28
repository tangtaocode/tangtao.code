package net.risesoft.approve.controller;


import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.risesoft.approve.risefile.RiseFile;
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
import net.risesoft.approve.risefile.RiseFileConst;
import net.risesoft.approve.service.impl.FileBoxAction;
import net.risesoft.model.Person;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.GUID;
import net.risesoft.util.GUIDUtil;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/fileBoxController")
public class FileBoxController {
	
	
	protected final Logger log = LoggerFactory.getLogger(FileBoxController.class);			
			private RiseFileConfig config=null;
			private RiseFileContext context=null;
			

			/**
			 * @param request
			 * @param response
			 * @return
			 * 返回数据为<root>...</root>
			 * 如果报错返回数据为error:xxx
			 */

			
			
			
			
			
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
					log.info("文件下载出错",e);
				} catch (IOException e) {
					log.info("文件下载出错",e);
				} catch(Exception e){
					log.info("文件下载出错",e);
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
			@RequestMapping(value="/save",method = RequestMethod.POST)
			@ResponseBody
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
							RiseFileManagerProxy.update(fileItem,config,is,person);
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
					//msg+=getFilesXML(request,response);
					time2 = System.currentTimeMillis();
					log.debug("文件存储耗时： " + (time2 - time1) + "ms");
					Runtime rt = Runtime.getRuntime();
					NumberFormat nf = NumberFormat.getNumberInstance();
					double total = (double)rt.totalMemory() / (1024*1024);
					double free = (double)rt.freeMemory() / (1024*1024);	
					log.debug("Total Memory=" + nf.format(total) + "m    Free Memory=" + nf.format(free) + "m"); 
				} catch (Exception ex) {
					log.info("文件保存出错",ex);
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
					String fileName = request.getParameter("fileName");
					String isShareArchManager = (String)request.getAttribute("isShareArchManager");
					boolean isManager = false;
					Person user = (Person) request.getSession().getAttribute("loginPerson");
					makeConfig(request);
					msg=config.getFileboxName()+";";
					
					RiseFileItemImp fileItem=new RiseFileItemImp();
					fileItem.fileGUID = new GUID().toString();
					fileItem.fileName = fileName;
					fileItem.titile = fileName.substring(0,fileName.lastIndexOf(".")>-1 ? fileName.lastIndexOf(".") : fileName.length());//modify by liujun 2010-12-16
					fileItem.fileNameExt = RiseFileConst.RISEFILEEXTNAME_RISECALLBACK;
					fileItem.currentUser =user.getIDNum();
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
					}			
					fileItem.setManager(isManager);
					RiseFileManagerProxy.delete(fileItem, config);*/
					
					msg+="ok:文件删除成功";
				} catch (Exception ex) {
					log.info("删除文件出错",ex);
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
				/*	riseFile = RiseFileManagerProxy.get(appname,fileGUID);
					new MeetingTopicFileManager().deleteMeetTopicFile(riseFile);*/
					msg+="ok:文件删除成功";
				} catch (Exception ex) {
					log.info("删除议题文件出错",ex);
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
					/*riseFile = meetingTopicFileManager.getTopicFile(fileGUID);
					meetingTopicFileManager.deleteTopicFile(riseFile);*/
					msg+="ok:文件删除成功";
				} catch (Exception ex) {
					log.info("删除多个议题文件出错",ex);
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
					log.info("获取议题文件数量出错",ex);
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
