<%
/**
 功能：打印提交的空白页面信息
**/
%><%@ page language="java" %><%@ page contentType="text/html; charset=gb2312" %><%
request.setCharacterEncoding("GBK");
out.print(request.getParameter("content"));
%>