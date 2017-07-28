<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<title>工作流中间件-催办信息</title>
<style>
</style>
</head>
<body>
	<div class="easyui-layout">
		<table id="list1"></table>
	</div>
</body>

<script type="text/javascript">
	$(document).ready(function() {
		$("#list1").datagrid({
			url : '${ctx}/reminder/reminderList/${taskId}',
			singleSelect : true,
			fitColumns : true,
			striped : true,
			rownumbers : true,
			pagination : false,
			//pageSize : 10,
			border : true,
			nowrap : false,
			columns : [[
			{
				title : '催办人姓名',
				field : 'senderName',
				width : 40,
				align : "center"
			}, {
				title : '催办时间',
				field : 'createTime',
				width : 40,
				align : "center"
			}, {
				title : '内容',
				field : 'msgContent',
				width : 40,
				align : "center"
			} ]]
		});
	});
</script>
</html>
