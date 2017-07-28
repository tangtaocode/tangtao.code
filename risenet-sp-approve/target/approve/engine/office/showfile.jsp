<%@ page contentType="application/octet-stream;charset=gbk"%><%@ page language="java" 
import="java.io.*,java.sql.*"%><%@ page import="net.sysmain.common.Operator"%><%@ page 
import="net.sysmain.common.I_UserConstant"%><%
Operator op = (Operator)request.getSession().getAttribute(I_UserConstant.USER_INFO);
if(op == null)
{
    response.setContentType("text/html");
    request.setAttribute(I_UserConstant.MESSAGE_INFO, "系统可能超时，请重新登录访问");
    try
    {
        request.getRequestDispatcher("/engine/message.jsp").forward(request,response);
    }
    catch(Exception ex){}
    return;
}

new net.business.db.OfficeManager().showFile(request, response);
%>