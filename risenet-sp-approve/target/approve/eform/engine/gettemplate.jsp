<%/*
�ó�����ģ���������ڳ���
*/
%><%@ page language="java" %><%@ page 
import="net.business.engine.TemplateEngine"%><%
TemplateEngine te = null;
//long t1 = System.currentTimeMillis();
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
//System.out.println(System.currentTimeMillis()-t1);
%>