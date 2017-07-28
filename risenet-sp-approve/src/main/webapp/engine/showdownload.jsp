<%
/**
 功能：附件、图片的下载
 参数：
     fieldalis：附件字段的表别名.字段名
     querrystring：模板参数
**/
%><%@ page language="java" %><%@ page 
import="net.business.engine.TemplateEngine" %><%
TemplateEngine te = TemplateEngine.getInstance();

te.showBinaryContent(application, request, response, true, TemplateEngine.BINARY_DOWNLOAD);
%>