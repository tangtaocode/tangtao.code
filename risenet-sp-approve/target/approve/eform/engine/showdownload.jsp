<%
/**
 ���ܣ�������ͼƬ������
 ������
     fieldalis�������ֶεı����.�ֶ���
     querrystring��ģ�����
**/
%><%@ page language="java" %><%@ page 
import="net.business.engine.TemplateEngine" %><%
TemplateEngine te = TemplateEngine.getInstance();

te.showBinaryContent(application, request, response, true, TemplateEngine.BINARY_DOWNLOAD);
%>