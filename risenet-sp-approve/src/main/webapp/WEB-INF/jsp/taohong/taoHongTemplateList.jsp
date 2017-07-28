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
								<a id="addTaoHongTemplate" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" href="javascript:void(0);">新增</a>
								<a id="modifyTaoHongTemplate" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" href="javascript:void(0);">编辑</a>
								<a id="removeTaoHongTemplate" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true"  href="javascript:void(0);">删除</a>
								<a id="managerTemplateType" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true"  href="javascript:void(0);">模板类型管理</a>
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
			url : '${ctx}/taoHongTemplate/getList',
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
					field : "template_guid",
					checkbox : true
				}, {
					title : '模版名称',
					field : 'template_fileName',
					width : 40,
					align : "center",
					editable: true,
					hidden : false,
					formatter : function(cellvalue, rowObject, rowIndex) {
						var s = '';
						/* s = '<a href="javascript:void(0);" onclick="editPermission(\'' + rowObject.id + '\',\'' + rowObject.principalName + '\',\'' + rowObject.permission + '\');">编辑</a>'; */
						s += '<a href="${ctx}/taoHongTemplate/download?template_guid=' + rowObject.template_guid + '">' + rowObject.template_fileName + '</a>';
						return s;
					}
			},{
				title : '委办局名称',
				field : 'bureau_name',
				width : 40,
				align : "center",
				editable: true,
				hidden : false
			},{
				title : '模版类型',
				field : 'template_type',
				width : 40,
				align : "center",
				editable: true,
				hidden : false
			},{
				title : '序号',
				field : 'tabIndex',
				width : 40,
				align : "center",
				editable: true,
				hidden : false
			}]]
		});
		
		
	});
	
	
	//重新加载jqgrid
	function reloadgrid()
	{
		grid.datagrid({url:'${ctx}/taoHongTemplate/getList'
			//,queryParams:{'formId':formId,}
		});
	}
	
	var frameID = newGuid();
	//添加
	$('#addTaoHongTemplate').click(function(){
		openCurWindow({
			id : frameID,
			src : ctx+'/taoHongTemplate/newOrModify',
			destroy : true,
			title : '添加',
			width : 500,
			height : 200,
			modal : true
		});
	});
	
	//修改
	$('#modifyTaoHongTemplate').click(function(){
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
			src : ctx+'/taoHongTemplate/newOrModify?id='+checkRows[0].template_guid,
			destroy : true,
			title : '编辑',
			width : 500,
			height : 200,
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
	$("#removeTaoHongTemplate").click(function(){
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
								   params+=('&ids='+checkRows[i].template_guid);
							   }
							   $.ajax({
								       async : false,  
								       cache : false,  
								       type: 'POST',
								       url:  ctx+'/taoHongTemplate/removeTaoHongTemplate'+params,
								       error: function () {
								           alert('删除失败！');  
								       },
								       success:function(data){
								    	   alert("删除成功！")
								    	   grid.datagrid('reload')
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
