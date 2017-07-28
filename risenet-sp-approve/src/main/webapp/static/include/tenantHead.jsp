<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String themes = "default";
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
	String contextPath = request.getContextPath();
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/js/easyui/themes/<%=themes%>/easyui.css" id="themes">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/icons/icon-all.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/all.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/easyui-ext.css">
<script type="text/javascript" src="<%=contextPath%>/static/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/easyui/datagrid-detailview.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/jquery.json.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/easyui-ext/jquery.easyui.extend.js"></script>
<%-- <script type="text/javascript"	src="<%=contextPath%>/static/js/ajax.config.js"></script> --%>
<script type="text/javascript" src="<%=contextPath%>/static/js/tenant/core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/easyui-ext/jquery.my97.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/easyui/plugins/jquery.fullcalendar.js"></script>
<script type="text/javascript">
var ctx = "<%=contextPath%>";
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