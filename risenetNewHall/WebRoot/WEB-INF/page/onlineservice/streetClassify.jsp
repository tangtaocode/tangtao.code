<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>
<body>
<div class="BS_todos">
	<s:iterator value="bureauList" status="item">
	<div class=bs_dep style="">
		<a href="/onlineService/initStreetSpecial/pid/<s:property value="code"/>.html"><s:property value="value"/></a></div>
	</s:iterator>
</div>
</body>
</html>

  

