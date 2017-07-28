<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<title>工作流中间件-流程列表页面</title>
<link href="${ctx}/static/jquery/jgrid/css/ui.multiselect.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery/qtip2/jquery.qtip.min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/risesoft/css/fileflow.css" />

<script type="text/javascript" src="${ctx}/static/jquery/qtip2/jquery.qtip.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/tools/jquery.outerhtml.js"></script>
<script type="text/javascript" src="${ctx}/static/risesoft/js/activiti/workflow.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/tools/jquery.json-2.4.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/form/jquery.form.js"></script>

</head>
<body>
	<div class="easyui-layout" data-options="fit:true,border:true">
		<table id="list1"></table>
	</div>
	<br>
	<div id="searchBar" align="center">
	<table>
		<tr>
			<td align="left" style="text-align:left">
				流程名称：
				<input type="text" id="procDefName" name="procDefName" value=""/>
				&nbsp;&nbsp;
				流程Key：
				<input type="text" id="procDefKey" name="procDefKey" value=""/>
				&nbsp;&nbsp;
				<input type="button" id="procSelect" name="procSelect" onclick="selectproc();" value=" 查 询 ">
			</td>
		</tr>
	</table>
	
	</div>
	
</body>
<script type="text/javascript">
	var grid = $('#list1');

	$(document).ready(function() {
		
		grid.datagrid({
			url : '${ctx}/workflow/process/list',
			toolbar : '#toolbar',
			//view : detailview,
			fit : true,
			singleSelect : true,
			fitColumns : true,
			striped : true,
			rownumbers : true,
			pagination : true,//分页控件，要分页时设置为true
			pageSize : 30,
			border : true,
			nowrap : false,
			columns : [[ {
				title : '名称',
				field : 'name',
				width : 60,
				align : "left"
			}, {
				title : 'key',
				field : 'key',
				width : 70,
				align : "left"
			},{
				title : 'id',
				field : 'id',
				width : 90,
				align : "left",
				hidden : false
			}, {
				title : '部署ID',
				field : 'deploymentId',
				width : 40,
				align : "center",
				hidden : false
			},  {
				title : '版本',
				field : 'version',
				width : 20,
				align : "center"
			}, {
				title : 'XML',
				field : 'resourceName',
				width : 90,
				align : "center"/* ,
				formatter : 'showlink',
				formatoptions : {
					target : '_blank',
					baseLinkUrl : '${ctx}/workflow/resource/read',
					addParam : '&resourceType=xml',
					idName : 'processDefinitionId'
				} */
			}, {
				title : '图片',
				field : 'diagramResourceName',
				width : 80,
				align : "center"/* ,
				formatter : 'showlink',
				formatoptions : {
					target : '_blank',
					baseLinkUrl : '${ctx}/workflow/resource/read',
					addParam : '&resourceType=image',
					idName : 'processDefinitionId'
				} */
			}, {
				title : '部署时间',
				field : 'deploymentTime',
				width : 30,
				align : "center"
			}, {
				title : '是否挂起',
				field : 'suspended',
				width : 20,
				align : "center",
				formatter : function(cellvalue, rowObject, rowIndex) {
					if (cellvalue) {
						return '已挂起';
					} else {
						return '未挂起';
					}
				}
			}, {
				title : '操作',
				field : 'opt',
				width : 60,
				fixed : true,
				align : "center",
				formatter : function(cellvalue, rowObject, rowIndex) {
					var s = '<a href="javascript:void(0);" onclick="configBpm(\'' + rowObject.key + '\',\'' + rowObject.id + '\',\'' + rowObject.name + '\');">配置</a>';
					return s;
				}
			} ]]
		});
	});
	//配置流程
	function configBpm(key,id,name)
	{
		window.location.href='${ctx}/worklist/config/mainFrame/show?procDefKey='+key+'&processDefinitionId='+id+'&procDefName='+name;
	}
	//删除流程
	function deleteProcessDefinition(deploymentId) {
		$.getJSON('${ctx}/workflow/process/delete?deploymentId=' + deploymentId, function(data, textStatus, jqXHR) {			
			grid.datagrid('reload');
			alert(data.msg);
		});
	}

	//挂起激活流程
	function switchSuspendOrActive(state, processDefinitionId) {
		$.getJSON('${ctx}/workflow/switchSuspendOrActive', {
			'state' : state,
			'processDefinitionId' : processDefinitionId
		}, function(data, textStatus, jqXHR) {			
			grid.datagrid('reload');
			alert(data.msg);
		});
	}
</script>
</html>