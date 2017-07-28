<%
/**
  ×Ö¶ÎÖµÖØ¸´¼ÇÂ¼
**/
%><%@ page contentType="text/plain; charset=GBK"%><%@ page language="java" %><%@ page 
import="net.business.engine.TemplateEngine"%><%
response.setHeader("Pragma","no-cache");
response.setHeader("Expires","0");
response.setHeader("CacheControl","max-age=0");
response.setHeader("Cache-Control","no-store");

TemplateEngine te = TemplateEngine.getInstance();
String message = te.isUniqueRecord(request);
if(message != null) out.print(message);
%>