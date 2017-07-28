<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<title>工作流中间件-权限配置的任务选取列表</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/risesoft/css/fileflow.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery/qtip2/jquery.qtip.min.css" />

<script type="text/javascript" src="${ctx}/static/jquery/qtip2/jquery.qtip.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/tools/jquery.outerhtml.js"></script>
<script type="text/javascript" src="${ctx}/static/risesoft/js/activiti/workflow.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/tools/jquery.json-2.4.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/form/jquery.form.js"></script>

</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:true">
		<div data-options="region:'center',title:'权限配置列表(${procDefName})',border:true">
			<table id="list1"></table>
		</div>
</div>
<div align="center">
<table>
	<tr>
		<td align="left" style="text-align:left">
			<input type="hidden" id="procDefKey" name="procDefKey" value="${procDefKey}"/>
			<input type="hidden" id="processDefinitionId" name="processDefinitionId" value="${processDefinitionId}"/>
			&nbsp;&nbsp;
		</td>
	</tr>
</table>
</div>
</body>
<script type="text/javascript">
var grid=$("#list1");
$(document).ready(function(){
	
	grid.datagrid({
		url : '${ctx}/procDef/getBpmList?processDefinitionId=${processDefinitionId}&isFilter='+'true',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		singleSelect:true,
		pagination : true,//分页控件，要分页时设置为true
		pageSize : 30,
		border : true,
		nowrap : false,
		columns : [[ {
			title : 'taskDefKey',
			field : 'taskDefKey',
			width : 90,
			align : "center",
			hidden : true
		},{
			title : '名称',
			field : 'taskDefName',
			width : 300,
			align : "left",
			hidden : false
		},{
			title : '操作',
			field : 'Opt',
			width : 200,
			fixed : true,
			align : "center",
			formatter : function(cellvalue, rowObject, rowIndex) {
				var s = '';
				s = '<a href="javascript:void(0);" onclick="permManage(\'' + rowObject.taskDefKey +  '\',\'' + rowObject.taskDefName + '\');">权限管理</a>';
				return s;
			}
		} ]]
	});
});

function permManage(taskDefKey,taskDefName)
{
	window.location.href="${ctx}/objPerm/workflow/permissionList?procDefKey=${procDefKey}&processDefinitionId=${processDefinitionId}&procDefName=${procDefName}&taskDefKey="+taskDefKey+"&taskDefName="+taskDefName;
}
</script>
</html>