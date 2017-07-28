<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<title>表单与流程任务的绑定</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery/qtip2/jquery.qtip.min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/risesoft/css/fileflow.css" />

<script type="text/javascript" src="${ctx}/static/jquery/qtip2/jquery.qtip.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/tools/jquery.outerhtml.js"></script>
<script type="text/javascript" src="${ctx}/static/risesoft/js/activiti/workflow.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/tools/jquery.json-2.4.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/form/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/layer/layer.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/tools/jquery.json-2.4.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/form/jquery.loadJSON.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/form/jquery.form.min.js"></script>

</head>
<body>
	<div class="easyui-layout" data-options="fit:true,border:true">
		<div data-options="region:'center',title:'表单字段绑定',border:true">
			<div id="toolbar" class="toolbar"  align="left" style="width:100%; background-color:#CCEEFF;">
					<table>
						<tr>
							<td><a href="javascript:void(0);" class="easyui-linkbutton"
								data-options="iconCls:'icon-add',plain:true" id="button01" >增加绑定</a>
							</td>
							<td><a href="javascript:void(0);" class="easyui-linkbutton"
								data-options="iconCls:'icon-back',plain:true" id="back01" >返回</a>
							</td>
							<td>&nbsp;&nbsp;&nbsp;</td>
						</tr>
					</table>
			</div>
			<table id="list1"></table>
		</div>
	</div>
	<div id="dialog-delete">
		<p>确定删除当前事项?</p>
	</div>
</body>
<script type="text/javascript">
	var grid = $("#list1");
	var formId="${formId}";
	$(document).ready(function(){
		grid.datagrid({
			url : '${ctx}/form/dBTableBind/getList?formId='+formId,
			fit : true,
			fitColumns : true,
			striped : true,
			singleSelect:true,
			rownumbers : true,
			pagination : true,//分页控件，要分页时设置为true
			pageSize : 30,
			border : true,
			nowrap : false,
			columns : [[{
				title : '表单id',
				field : 'formId',
				width : 40,
				align : "center",
				editable: true,
				hidden : true
			},{
				title : '表单字段英文名称',
				field : 'fieldAlias',
				width : 40,
				align : "center",
				editable: true,
				hidden : false
			},{
				title : '表单字段中文名称',
				field : 'fieldName',
				width : 40,
				align : "center",
				editable: true,
				hidden : false
			},{
				title : '表名称',
				field : 'tableName',
				width : 40,
				align : "center",
				editable: true,
				hidden : false
			},{
				title : '对应字段名称',
				field : 'columnName',
				width : 40,
				align : "center",
				editable: true,
				hidden : false
			},{
				title : '对应字段类型',
				field : 'columnType',
				width : 40,
				align : "center",
				editable: true,
				hidden : false
			},{
				title : '是否是对应关系类型',
				field : 'isSubTable',
				width : 40,
				align : "center",
				editable: true,
				hidden : false,
				formatter : function(cellvalue, rowObject, rowIndex) {
					var s = '';
					if(rowObject.isSubTable==true){
						s="有";
					}else{
						s="无";
					}
					return s;
				}
			},{
				title : '对应关系字段名称',
				field : 'tableAlias',
				width : 40,
				align : "center",
				editable: true,
				hidden : false
			},{
				title : '操作',
				field : 'id',
				width : 240,
				fixed : true,
				align : "center",
				hidden : false,
				formatter : function(cellvalue, rowObject, rowIndex) {
					var s = '';
					s += '<a href="javascript:void(0);" onclick="formDBTBDelete(\'' + rowObject.id + '\');">删除</a>';
					s += '&nbsp;|&nbsp;<a href="javascript:void(0);" onclick="formDBTBEdit(\'' + rowObject.id + '\');">修改</a>';
					return s;
				}
			} ]]
		});
		
		$('#button01').click(function() {
			formDBTBEdit('');
		});
		
		$('#back01').click(function() {
			history.back();
		});
	});

	function formDBTBDelete(id) {
		$( "#dialog-delete" ).dialog({
			title:"确定删除",
			resizable: false,
			width : 200,
			height:130,
			left:400,
			top:150,
			modal: true,
			buttons:[{
				text:'确定',
				handler:function() {
					$.getJSON('${ctx}/form/dBTableBind/delete?id=' + id, function(data, textStatus, jqXHR) {
						alert(data.msg);
						reloadgrid();
					});
					$("#dialog-delete").dialog( "close" );
				}
			},
			{
				text:'取消',
				handler: function() {
					$("#dialog-delete").dialog( "close" );
				}
			}]
		});
	}
	
	var frameID = newGuid();
	function formDBTBEdit(id) {
		openCurWindow({
			id : frameID,
			src : '${ctx}/form/dBTableBind/edit?formId='+formId+'&id=' + id,
			destroy : true,
			title : '表单字段绑定',
			width : 350,
			height : 250,
			modal : true
		});
	}

	//重新加载jqgrid
	function reloadgrid()
	{
		grid.datagrid({url:'${ctx}/form/dBTableBind/getList',queryParams:{
				'formId':formId
	 		}
		});
	}
</script>
</html>
