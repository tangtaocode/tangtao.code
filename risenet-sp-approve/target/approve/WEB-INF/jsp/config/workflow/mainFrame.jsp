<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<title>工作流中间件</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery/qtip2/jquery.qtip.min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/risesoft/css/fileflow.css" />

<script type="text/javascript" src="${ctx}/static/jquery/qtip2/jquery.qtip.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/tools/jquery.outerhtml.js"></script>
<script type="text/javascript" src="${ctx}/static/risesoft/js/activiti/workflow.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/tools/jquery.json-2.4.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/form/jquery.form.js"></script>
</head>
<body>
     
	 <div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'west',split:true,title:'菜单目录'" style="overflow: auto;width:250px;">
			<iframe src="${ctx}/worklist/config/leftFrameMenu/show?procDefKey=${procDefKey}&processDefinitionId=${processDefinitionId}&procDefName=${procDefName}" id="leftframemenu" name="leftframemenu" width="90%" height="95%" frameborder="0" scrolling="auto"></iframe>
		</div>
		<div data-options="region:'center',border:false" style="overflow: hidden;">
			<iframe src="" id="rightframemenu" name="rightframemenu" width="100%" height="100%" frameborder="0" scrolling="auto"></iframe>
		</div>
	</div>
</body>
</html>