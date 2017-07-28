<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/static/common/bpmTaglib.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="Content-Language" content="utf-8"/>
		<meta http-equiv="pragma" content="no-cache">   
   	 	<meta http-equiv="cache-control" content="no-cache">
    	<meta http-equiv="expires" content="0">
		<title>模板类型管理</title>
		<%@ include file="/static/common/head.jsp"%>
		<%@ include file="/static/common/common.jsp"%>
	</head>
	<body>
		<div class="easyui-layout" data-options="fit:true,border:false">
			<div data-options="region:'north',border:false" class="toolbar" style="background-color:#CCEEFF;">
				<table border="0" cellpadding="0" cellspacing="0" style="width: auto">
					<tr>
						<td>
							<a id="addType" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" href="javascript:void(0);">新增</a>
							<a id="modifyType" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" href="javascript:void(0);">编辑</a>
							<a id="removeType" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" href="javascript:void(0);">删除</a>
							<a id="moveUp" class="easyui-linkbutton" data-options="iconCls:'icon-arrow-up',plain:true" href="javascript:void(0);">上移</a>
							<a id="moveDown" class="easyui-linkbutton" data-options="iconCls:'icon-arrow-down',plain:true" href="javascript:void(0);">下移</a>
							<a id="saveOrder" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" href="javascript:void(0);">保存</a>
						</td>
					</tr>
				</table>
			</div>
			<div data-options="region:'center',border:false" style="overflow:auto;">
				<table id="taoHongTemplateType" class="easyui-datagrid"
					   data-options="toolbar:'#toolbar',idField:'id',checkOnSelect:false,selectOnCheck:false,singleSelect:true">
						<thead>
							<tr>
								<th data-options="field:'id',title:'id',checkbox:true">id</th>
								<th data-options="field:'typeName',width:'80%'">模板类型名称</th>
								<th data-options="field:'tabIndex',width:'15%'">序号</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${typeList}" var="type" varStatus="status">
								<tr>
									<td>${type.id}</td>
									<td>${type.typeName}</td>
									<td>${type.tabIndex}</td>
								</tr>
							</c:forEach>
						</tbody>
				</table>
			</div>
		</div>
	</body>
	<script type="text/javascript">
		var frameID = newGuid();
		//添加
		$('#addType').click(function(){
			openCurWindow({
				id : frameID,
				src : ctx+'/taoHongTemplateType/newOrModify',
				destroy : true,
				title : '添加',
				width : 350,
				height : 150,
				modal : true
			});
		});
		
		//修改
		$('#modifyType').click(function(){
			var checkRows = $('#taoHongTemplateType').datagrid('getChecked');
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
				src : ctx+'/taoHongTemplateType/newOrModify?id='+checkRows[0].id,
				destroy : true,
				title : '编辑',
				width : 350,
				height : 150,
				modal : true
			});
		});
		
		//移除
		$("#removeType").click(function(){
			var checkRows = $('#taoHongTemplateType').datagrid('getChecked');
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
									   params+=('&ids='+checkRows[i].id);
								   }
								   $.ajax({
									       async : false,  
									       cache : false,  
									       type: 'POST',
									       url:  ctx+'/taoHongTemplateType/removeTaoHongTemplateType'+params,
									       error: function () {
									           alert('删除失败！');  
									       },
									       success:function(data){
									    	   for(var i=0;i<l;i++){
												   var index = $('#taoHongTemplateType').datagrid('getRowIndex',checkRows[0]) ;
												   $('#taoHongTemplateType').datagrid('deleteRow',index) ;
											   }
									    	   $('#taoHongTemplateType').datagrid('acceptChanges');
									    	   $.messager.alert('提示', "删除成功");
									       }
								   });
						   }
					}
			);
		});
		
		
		$(function() {
			$("#moveUp").click(function(){
				var selectedRow = $('#taoHongTemplateType').datagrid('getSelected');
				if(!selectedRow){
					return ;
				}
				var currentIndex = $('#taoHongTemplateType').datagrid('getRowIndex',selectedRow);
				if(currentIndex==0){
					return;
				}
				var allRows = $('#taoHongTemplateType').datagrid('getRows');
				var targetRow = allRows[currentIndex-1];
				//先交换序号tabIndex
				var tempIndex = selectedRow.tabIndex;
				selectedRow.tabIndex = targetRow.tabIndex;
				targetRow.tabIndex = tempIndex ;
				$('#taoHongTemplateType').datagrid('deleteRow',currentIndex);
				$('#taoHongTemplateType').datagrid('insertRow',{
														index:currentIndex-1,
														row:selectedRow
														}
										 );
				$('#taoHongTemplateType').datagrid('selectRow',currentIndex-1);
				$('#taoHongTemplateType').datagrid('refreshRow',currentIndex);
			});
			
			$("#moveDown").click(function(){
				var selectedRow = $('#taoHongTemplateType').datagrid('getSelected');
				if(!selectedRow){
					return ;
				}
				var currentIndex = $('#taoHongTemplateType').datagrid('getRowIndex',selectedRow);
				var allRows = $('#taoHongTemplateType').datagrid('getRows');
				if(currentIndex==(allRows.length-1)){
					return;
				}
				var targetRow = allRows[currentIndex+1];
				//先交换序号tabIndex
				var tempIndex = selectedRow.tabIndex;
				selectedRow.tabIndex = targetRow.tabIndex;
				targetRow.tabIndex = tempIndex ;
				$('#taoHongTemplateType').datagrid('deleteRow',currentIndex);
				$('#taoHongTemplateType').datagrid('insertRow',{
														index:currentIndex+1,
														row:selectedRow
														}
										 );
				$('#taoHongTemplateType').datagrid('selectRow',currentIndex+1);
				$('#taoHongTemplateType').datagrid('refreshRow',currentIndex);
			});
			
			$("#saveOrder").click(function(){
				var allRows = $('#taoHongTemplateType').datagrid('getRows');
				var l=allRows.length;
				if(l==0)
					return ;
				var params ='?';
				for(var i=0;i<l;i++){
					   params+=('&idAndTabIndexs='+allRows[i].id+":"+allRows[i].tabIndex);
				}
				$.ajax({
				       async : false,  
				       cache : false,  
				       type: 'POST',
				       url:  ctx+'/taoHongTemplateType/saveOrder'+params,
				       error: function () {
				           top.$.messager.alert('提示', "保存失败");
				           $('#taoHongTemplateType').datagrid('rejectChanges');
				       },
				       success:function(data){
				    	   $('#taoHongTemplateType').datagrid('acceptChanges');
				    	   top.$.messager.alert('提示', "保存成功");
				       }
			   });
			});
			
		});
	</script>
</html>