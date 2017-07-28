<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="net.risesoft.approve.service.impl.FileBoxAction" %>

<%
		//防止360浏览器的session丢失
		response.encodeURL("/approve/static/js/ntko/fileboxAction.jsp");	
    	FileBoxAction fileboxAction=new FileBoxAction();
		String actionType = request.getParameter("action");
		String isArchManager = request.getParameter("isArchManager");
		String isShareArchManager = (String)session.getAttribute("isShareArchManager");
		request.setAttribute("isShareArchManager",isShareArchManager);
		String msg="";
		if (actionType.equals("download")) {
			//fileboxAction.downloadFile(request, response);
			request.getRequestDispatcher("/servlet/RisefileViewData").forward(request,response);
			return;
		} else if (actionType.equals("save")||actionType.equals("update")) {			
			msg=fileboxAction.saveUpdateFile(request, response);
		}else if(actionType.equals("delete")){
			if(isArchManager.equals("true")){
				msg=fileboxAction.deleteFile(request, response);
			}
		} else if(actionType.equals("showVersion")){
			msg=fileboxAction.getFilesXML(request, response);
		} 
		out.print(msg);
		System.out.println(msg);
%>
