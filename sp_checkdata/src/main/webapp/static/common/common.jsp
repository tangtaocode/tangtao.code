<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<%
	String themes="default";
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path+"/";
	String contextPath = request.getContextPath();
%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery/easyui/themes/<%=themes%>/easyui.css" id="themes">
<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/risesoft/css/all.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/risesoft/css/icon-all.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/risesoft/css/easyui-ext.css">

<script type="text/javascript" src="${ctx}/static/risesoft/js/myCore.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/easyui/datagrid-detailview.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/jquery.json.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/easyui-ext/jquery.easyui.extend.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/easyui-ext/jquery.euploadify.js"></script>
<script type="text/javascript">
(function($){
	$.getRootPath=function(){
		return "<%=basePath%>";
	}
	$.getContextPath=function(){
		return "<%=contextPath%>";
	}
	$.getThemes=function(){
		return "<%=themes%>";
	}
})(jQuery);
</script>