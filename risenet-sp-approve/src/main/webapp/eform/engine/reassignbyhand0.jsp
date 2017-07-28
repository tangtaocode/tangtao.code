<%
/**
  潜在客户分配
**/
%><%@ page contentType="text/html; charset=GBK"%><%@ page import="net.sysplat.common.Operator"%><%@ page 
import="net.sysmain.common.ConnectionManager"%><%@ page import="net.sysplat.access.Authentication"%><%
response.setHeader("Pragma","no-cache");
response.setHeader("Expires","0");
response.setHeader("CacheControl","max-age=0");
response.setHeader("Cache-Control","no-store");

net.chysoft.action.CustomerAction.reAssignByHand(request);System.out.print(request.getParameter("type"));
%>
<script>location = "../engine/gettemplate.jsp?temp_Id=603&type=<%=request.getParameter("type")%>"</script>