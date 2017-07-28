<%@ page contentType="text/html;charset=utf-8" %>
<%
String msg = new net.business.db.OfficeManager().saveOfficeFile(request, application);
out.print(msg);
%>