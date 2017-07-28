<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html>
<html class="no-js">
<head>
<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<title>工作流中间件-流程模型列表</title>
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
		<div id="toolbar" class="toolbar" align="left" style="width:100%; background-color:#CCEEFF;">
			<table border="0" cellpadding="0" cellspacing="0" style="width:100%; background-color:#CCEEFF;">
				<tr>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton"	data-options="iconCls:'icon-add',plain:true" id="create"">创建</a>
					</td>
					<td>
						&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</div>
		<table id="list1"></table>
</div>
<div id="createModelTemplate" title="创建模型" class="easyui-dialog" data-options="modal:true,closed:true">
	<form id="modelForm" action="${ctx}/workflow/model/create" target="_blank" method="post">
		<table>
			<tr>
				<td>名称：</td>
				<td><input id="name" name="name" type="text" /></td>
			</tr>
			<tr>
				<td>KEY：</td>
				<td><input id="key" name="key" type="text" /></td>
			</tr>
			<tr>
				<td>描述：</td>
				<td><textarea id="description" name="description" style="width: 300px; height: 50px;"></textarea></td>
			</tr>
		</table>
	</form>
</div>
</body>
<script type="text/javascript">
	var grid = $('#list1');
	
	$(document).ready(function() {
		
		initCreateDialog();
		
		grid.datagrid({
			url : '${ctx}/workflow/model/list/json',
			toolbar : '#toolbar',
			//view : detailview,
			fit : true,
			//singleSelect : true,
			fitColumns : true,
			striped : true,
			rownumbers : true,
			pagination : true,//分页控件，要分页时设置为true
			pageSize : 30,
			border : true,
			nowrap : false,
			columns : [[ {
				title : 'id',
				field : 'id',
				width : 125,
				align : "center",
				hidden : false
			}, {
				title : 'key',
				field : 'key',
				width : 55,
				align : "center"
			}, {
				title : '名称',
				field : 'name',
				width : 55,
				align : "center"
			}, {
				title : '版本',
				field : 'version',
				width : 85,
				align : "center"
			}, {
				title : '创建时间',
				field : 'createTime',
				width : 80,
				align : "center"
			}, {
				title : '修改时间',
				field : 'lastUpdateTime',
				width : 80,
				align : "center"
			}, {
				title : '操作',
				field : 'opt',
				width : 200,
				fixed : true,
				sortable : false,
				resize : false,
				align : "center",
				formatter : function(cellvalue, rowObject, rowIndex) {
					var s = '<a href="javascript:void(0);" onclick="editModel(\'' + rowObject.id + '\');">编辑</a>';
					s += '&nbsp;|&nbsp;<a href="${ctx}/workflow/model/deploy/' + rowObject.id + '">部署</a>';
					s += '&nbsp;|&nbsp;<a href="${ctx}/workflow/model/export/' + rowObject.id + '" target="_blank">导出</a>';
					s += '&nbsp;|&nbsp;<a href="${ctx}/workflow/model/delete/' + rowObject.id + '">删除</a>';
					return s;
				}
			} ]]
		});

		$('#create').click(function() {
			$('#createModelTemplate').dialog('open');
		});
	});
	
	function editModel(modelId) {
		window.open('${ctx}/modeler.html?modelId=' + modelId);

		/* var dlg = window.parent.$('<div/>');
		dlg.dialog({
			href : '${ctx}/editor?id=' + modelId,
			width : 400,
			height : 400,
			modal : true,
			title : '流程模型'
		}); */
	}

	function initCreateDialog()
	{
		var dlg=$('#createModelTemplate');
		dlg.dialog({
			width : 500,
			height : 240,
			modal : true,
			title : '创建模板',
			buttons : [{
				text : '保存',
				iconCls : 'icon-save',
				handler : function() {
					if (!$('#name').val()) {
						alert('请填写名称！');
						$('#name').focus();
						return;
					}
					setTimeout(function() {
						location.reload();
					}, 1000);
					$('#modelForm').submit();
				}
			}, {
				text : '关闭',
				iconCls : 'icon-cancel',
				handler : function() {
					dlg.dialog('close');
				}
			} ],
			onClose : function() {
				//$(this).remove();//移除窗体，新建临时窗口时使用，这里不使用
			}
		});
	}
</script>

</html>