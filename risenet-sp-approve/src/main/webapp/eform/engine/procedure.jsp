<%/*
执行不包含字段的模板提交,一般为部件
*/
%>
<%@ page language="java" %>
<%@ page contentType="text/html; charset=gb2312"%>
<%@ page import="net.business.engine.DataEngine"%>
<%request.setCharacterEncoding("gb2312");%>
<%
DataEngine de = null;

de = DataEngine.getInstance();
try
{
    de.doPostWithNoField(application, request, response);
}
catch(Exception ex)
{
    ex.printStackTrace();
}
%>
