package net.risesoft.approve.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.risesoft.approve.risefile.filter.RiseFileContext;

public interface FileBoxActionService {

	public String getMeetTopicFilesXML(HttpServletRequest request, HttpServletResponse response);
	
	
	
	public String downloadFile(HttpServletRequest request, HttpServletResponse response);
	
	
	
	public String saveUpdateFile(HttpServletRequest request, HttpServletResponse response);
	
	
	
	public String saveUpdateMeetTopicFile(HttpServletRequest request, HttpServletResponse response);
	
	
	
	public String deleteFile(HttpServletRequest request,HttpServletResponse response);
	
	
	public String deleteTopicFiles(HttpServletRequest request,HttpServletResponse response);
	
	
	
	public int getTopicFileCount(HttpServletRequest request,HttpServletResponse response);



	public String deleteTopicFile(HttpServletRequest request,
			HttpServletResponse response);
	
		
}
