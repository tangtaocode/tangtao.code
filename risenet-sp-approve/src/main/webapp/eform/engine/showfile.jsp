<%
/**
 功能：附件、图片的直接打开/显示
 参数：
      fieldalis：附件字段的表别名.字段名
      querrystring：模板参数
**/
%><%@ page language="java" %><%@ page 
import="net.business.engine.TemplateEngine" %><%
TemplateEngine te = TemplateEngine.getInstance();

te.showBinaryContent(application, request, response, true, TemplateEngine.BINARY_SHOWFILE);
%>