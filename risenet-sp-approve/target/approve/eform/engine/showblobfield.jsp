<%@ page language="java" %><%@ page 
import="net.business.engine.TemplateEngine" %><%
TemplateEngine te = TemplateEngine.getInstance();

te.showBlobContent(application, request, response);
%>