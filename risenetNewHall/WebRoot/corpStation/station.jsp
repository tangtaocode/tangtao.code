<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String insGuid= request.getParameter("insGuid");
%>
<HTML>
<META >
<head>
<script type="text/javascript">
function loadFormAction(){
	//alert(1);
	var url='/onlineService/doCorpStationAction.html?insGuid=<%=insGuid%>';
	//alert(url);
	window.location = url;
}
</script>
</head>
  <body onload="loadFormAction()"> 
  </body>
  </HTML>