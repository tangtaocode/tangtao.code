<%@ page language="java"%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="net.business.engine.DataEngine"%>
<%request.setCharacterEncoding("GBK");%>
<%
DataEngine de = null;
de = DataEngine.getInstance();
String styleSheet = de.getQueryStylesheet(request);

if(styleSheet != null && styleSheet.trim().equals(""))
{
    styleSheet = null;
}
%>
<html>
<head>
<script src="sinc/query.js" language = "JavaScript"></script>
<%if(styleSheet != null){
    out.println(styleSheet);}else{%>
<link href="scss/query.css" rel="stylesheet" type="text/css">
<%}%>
</head>
<body>
<%            
out.print(de.doQuery(application, request, response).getMessage());
%>
</body>
</html>