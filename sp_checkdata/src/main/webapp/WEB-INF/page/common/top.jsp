<%@page import="net.risesoft.common.util.ConstUtils"%>
<%@page import="net.risesoft.model.RiseEmployee"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<html>
<head>
<title>头部</title>
<link href="../css/index_style.css" rel="stylesheet" type="text/css" />
</head>
<%RiseEmployee user=(RiseEmployee)session.getAttribute(ConstUtils.SESSION_USER); %>
<body>
	<div class="top">
	    <div class="top_top">
			<div class="top_but"><a href="/oneHome/Logout" class="top_but01">退出</a><a href="<%= request.getContextPath() %>/systemmanage/icon/frameset.jsp" class="top_but02">个人设置</a></div>
			<div class="top_text"><span>您好：<%=user.getEmployee_name() %>同志 当前绑定手机号码为：<%=user.getEmployee_mobile()%> </span></div>	
	    </div>
	</div>
	<div class="nav">
		<div class="nav_div02"></div>
	</div>
</body>
</html>
