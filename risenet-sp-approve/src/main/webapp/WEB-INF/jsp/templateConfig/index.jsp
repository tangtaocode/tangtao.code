<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<title></title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery/qtip2/jquery.qtip.min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/risesoft/css/fileflow.css" />

<script type="text/javascript" src="${ctx}/static/jquery/qtip2/jquery.qtip.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/tools/jquery.outerhtml.js"></script>
<script type="text/javascript" src="${ctx}/static/risesoft/js/activiti/workflow.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/tools/jquery.json-2.4.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/form/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/static/js/msg.js"></script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true,border:true">
			<div id="toolbar" class="toolbar" style="background-color:#CCEEFF;">
					<table border="0" cellpadding="0" cellspacing="0" style="width: auto">
						<tr>
							<td>
								<a id="addSpmTemplate" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" href="javascript:void(0);">新增</a>
								<a id="modifySpmTemplate" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" href="javascript:void(0);">编辑</a>
								<a id="removeSpmTemplate" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true"  href="javascript:void(0);">删除</a>
							</td>
						</tr>
					</table>
				</div>
			<div style="height: 95%">
				<table id="list1"></table>
			</div>
			
	</div>

	<div id="dialog-uploadfiles">
	</div>

</body>
<script type="text/javascript">
	var grid = $("#list1");
	$(document).ready(function(){
		
		grid.datagrid({
			url : '${ctx}/templateConfig/getList',
			fit : true,
			fitColumns : true,
			striped : true,
			rownumbers : true,
			singleSelect:false,
			//pagination : true,//分页控件，要分页时设置为true
			pageSize : 30,
			border : true,
			nowrap : false,
			columns : [[
				{
					title : "",
					field : "templateGuid",
					checkbox : true
				}, {
					title : '模版名称',
					field : 'templateName',
					width : 40,
					align : "center",
					editable: true,
					hidden : false,
					formatter : function(cellvalue, rowObject, rowIndex) {
						var s = '';
						/* s = '<a href="javascript:void(0);" onclick="editPermission(\'' + rowObject.id + '\',\'' + rowObject.principalName + '\',\'' + rowObject.permission + '\');">编辑</a>'; */
						s += '<a href="${ctx}/templateConfig/download?templateGuid=' + rowObject.templateGuid + '">' + rowObject.templateName + '</a>';
						return s;
					}
			},{
				title : '证照名称',
				field : 'certificateName',
				width : 40,
				align : "center",
				editable: true,
				hidden : false
			},{
				title : '事项名称',
				field : 'approveItemName',
				width : 40,
				align : "center",
				editable: true,
				hidden : false
			},{
				title : '模版类型',
				field : 'templateType',
				width : 40,
				align : "center",
				editable: true,
				hidden : false,
				formatter : function(cellvalue, rowObject, rowIndex) {
					var s = '未知';
					if(cellvalue == '1'){ s = '打印办结单'; }
					if(cellvalue == '2'){ s = '打印受理单'; }
					if(cellvalue == '3'){ s = '文件'; }
					return s;
				}
			}]]
		});
		
		
	});
	
	
	//重新加载jqgrid
	function reloadgrid()
	{
		grid.datagrid({url:'${ctx}/templateConfig/getList'
			//,queryParams:{'formId':formId,}
		});
	}
	
	var frameID = newGuid();
	//添加
	$('#addSpmTemplate').click(function(){
		openCurWindow({
			id : frameID,
			src : ctx+'/templateConfig/newOrModify',
			destroy : true,
			title : '添加',
			width : 500,
			height : 300,
			modal : true
		});
	});
	
	//修改
	$('#modifySpmTemplate').click(function(){
		var checkRows = grid.datagrid('getChecked');
		var l = checkRows.length ;
		if(l==0){
			$.messager.alert('提示', "请先勾选要修改的模板");
			return ;
		}
		if(l>1){
			$.messager.alert('提示', "只能选择一个模板进行修改");
			return ;
		}
		openCurWindow({
			id : frameID,
			src : ctx+'/templateConfig/newOrModify?id='+checkRows[0].templateGuid,
			destroy : true,
			title : '编辑',
			width : 500,
			height : 300,
			modal : true
		});
	});
	
	//排序
	$('#orderTaoHongTemplate').click(function(){
		openCurWindow({
			id : frameID,
			src : ctx+'/taoHongTemplate/orderTaoHongTemplate',
			destroy : true,
			title : '排序',
			width : 200,
			height : 300,
			modal : true
		});
	});
	
	//移除
	$("#removeSpmTemplate").click(function(){
		var checkRows = grid.datagrid('getChecked');
		var l = checkRows.length ;
		if(l==0){
			$.messager.alert('提示', "请先勾选要移除的模板");
			return ;
		}
		$.messager.confirm('Confirm','确定要移除选中的模板?',
				function(r){
					   if(r){
							   var params ="?";
							   for(var i=0;i<l;i++){
								   params+=('&ids='+checkRows[i].templateGuid);
							   }
							   $.ajax({
								       async : false,  
								       cache : false,  
								       type: 'POST',
								       url:  ctx+'/templateConfig/removeSpmTemplate'+params,
								       error: function () {
								           alert('删除失败！');  
								       },
								       success:function(data){
								    	   if(data == '1'){
								    		   alert("删除成功！");
									    	   grid.datagrid('reload');
								    	   }else{
								    		   alert('删除失败！');
								    	   }
								       }
							   });
					   }
				}
		);
	});
	
	//套红模板管理
	$('#managerTemplateType').click(function(){
		openCurWindow({
			id : frameID,
			src : ctx+'/taoHongTemplateType/managerTaoHongTemplateType',
			destroy : true,
			title : '排序',
			width : 400,
			height : 300,
			modal : true
		});
	});
	
</script>
</html>
