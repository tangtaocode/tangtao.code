<%@ page contentType="text/html;"%><%@ page import="net.sysmain.common.I_UserConstant"%><%@ page 
import="net.sysmain.common.I_CommonConstant"%><%@ page import="net.business.engine.DataEngine"%><%@ page 
import="net.business.engine.common.MessageObject"%><%
DataEngine de = null;
MessageObject mo = null;
String backUrl = null;

de = DataEngine.getInstance();
mo = de.doPost(application, request, response);

if(mo.getMessage() != null && request.getAttribute(I_UserConstant.MESSAGE_INFO) == null)
{//��ʾ��Ϣ
    request.setAttribute(I_UserConstant.MESSAGE_INFO, mo.getMessage());
}
if(mo.getCode() == 0 && (backUrl=((String)request.getAttribute(I_CommonConstant.FORWARD_URL))) != null)
{//�������ض����ַ
    request.getRequestDispatcher(backUrl).forward(request, response); 
    return;
}
request.getRequestDispatcher("message.jsp").forward(request, response); 
%>