<%@ page language="java"%>
<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="net.business.engine.DataEngine"%>
<%@ page import="net.business.engine.ListObject"%>
<%@ page import="net.business.engine.common.MessageObject"%>
<%request.setCharacterEncoding("gb2312");%>
<%
DataEngine de = null;
de = DataEngine.getInstance();
String styleSheet = de.getQueryStylesheet(request);
MessageObject mo = de.publishQuery(request);
int temp_Id = -1;
if(mo.getCode() != -1)
{
    Object[]  o = mo.getStorage();
    ListObject list = (ListObject)o[0];
    temp_Id = list.getTemp_Id();
}
if(styleSheet != null && styleSheet.trim().equals(""))
{
    styleSheet = null;
}
%>
<html>
<head>
<script src="sinc/query.js" language = "JavaScript"></script>
<style type="text/css">
<!--
A:active 
{
  COLOR: #FF0000; text-decoration: none;
}
A:link 
{
  COLOR: #0000EF; TEXT-DECORATION: none
}
A:visited 
{
  COLOR: #0000EF; TEXT-DECORATION: none
}
A:hover 
{
  COLOR: #FF0000; TEXT-DECORATION: none
}
.selectedRow{background-color:#AAAAAA}
-->
</style>
<%if(styleSheet != null){
    out.println(styleSheet);}else{%>
<link href="scss/query.css" rel="stylesheet" type="text/css">
<%}%>
</head>
<body<%if(temp_Id > 0){%> oncontextmenu="window.showModalDialog('<%=request.getContextPath()%>/sysadmin/publish/publishsetting.jsp?temp_Id=<%=temp_Id%>',document,'dialogTop:100px;dialogLeft:200px;dialogWidth:410px;dialogHeight:410px;help:no;status:no;'); return false;"<%}%>>
<%            
out.print(mo.getMessage());
%>
</body>
</html>