<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%><%@ page 
import="net.business.engine.TemplateEngine,java.sql.*"%><%@ page import="net.risesoft.daos.base.impl.SimpleJdbcDaoImpl,net.risesoft.utils.base.DatabaseUtil"%><%
TemplateEngine te = null;
//long t1 = System.currentTimeMillis();
//1¡¢·µ»ØÄ£°åÒýÇæÊµÀý

boolean needTurnToOther = false;
String newUrl = null;
/*
String url = request.getScheme()+"://"+ request.getServerName()+
	request.getRequestURI()+"?"+request.getQueryString(); 
String t2 = request.getServerName()+request.getRequestURI()+"?"+request.getQueryString(); 
System.out.println(t1+"\r\n"+t2);
*/
String url = request.getRequestURI()+"?"+request.getQueryString(); 
if(url.indexOf("edittype=1")!=-1){
	String guid = request.getParameter("insGuid");
	Connection conn = null;
	try{
		SimpleJdbcDaoImpl sdi = new SimpleJdbcDaoImpl();
		conn = sdi.getConn(this.getServletContext());
		guid = DatabaseUtil.getOneString(conn,"select guid from sb_approveinstance where guid=? ",
				new Object[]{guid});
		if(null==guid||"".equals(guid)){
			 newUrl = url.replace("edittype=1","edittype=0");
			 needTurnToOther = true;
		}
	}catch(Exception ex){
	    ex.printStackTrace();
	    out.println(ex.getMessage());
	}finally{
		DatabaseUtil.closeConn(conn);		
	}
}
if(!needTurnToOther){
		te = TemplateEngine.getInstance(); 
		try
		{
		    te.showTemplate(application, request, response);
		}
		catch(Exception ex)
		{
		    ex.printStackTrace();
		    out.println(ex.getMessage());
		}
}else{
	response.sendRedirect(newUrl);
	//return;
	//request.getRequestDispatcher(newUrl).forward(request,response);
	//out.print("<script>window.location.href='"+newUrl+"';</script>");
}

%>