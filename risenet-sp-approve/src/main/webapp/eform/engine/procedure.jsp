<%/*
ִ�в������ֶε�ģ���ύ,һ��Ϊ����
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
