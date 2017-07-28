<%@ page contentType="text/html;"%>
<%@ page import="net.sysmain.common.I_UserConstant"%>
<%@ page import="net.sysmain.common.I_CommonConstant"%>
<%@ page import="net.business.engine.DataEngine"%>
<%@ page import="net.business.engine.common.MessageObject"%>
<%@ page import="javax.swing.JOptionPane"%>
<%
DataEngine de = null;
MessageObject mo = null;
String backUrl = "";

de = DataEngine.getInstance();
//request.setAttribute("scriptCode",  "alert(\"成功保存数据\")");
mo = de.doPost(application, request, response);

 if(mo.getMessage() != null && request.getAttribute(I_UserConstant.MESSAGE_INFO) == null)
{//提示信息
	 
    request.setAttribute(I_UserConstant.MESSAGE_INFO, mo.getMessage());
    //backUrl="/eform/engine/getTemplate";
   // request.getRequestDispatcher(backUrl).forward(request, response); 
   // request.setAttribute(I_CommonConstant.JAVA_SCRIPT_CODE,  "alert(\"成功保存数据\")");
   // context.getRequest().setAttribute(I_CommonConstant.JAVA_SCRIPT_CODE, back());
	//JOptionPane.showMessageDialog(null,mo.getMessage());
}
 if(mo.getCode() == 0 && (backUrl=((String)request.getAttribute(I_CommonConstant.FORWARD_URL))) != null)
{//设置了重定向地址
    request.getRequestDispatcher(backUrl).forward(request, response); 
    return;
}
 request.getRequestDispatcher("message.jsp").forward(request, response); 
%>
