<%@ page language="java" %><%@ page 
import="net.business.engine.TemplateEngine" %><%
TemplateEngine te = TemplateEngine.getInstance();
te.createPdfByTemplate(request, response);
%>