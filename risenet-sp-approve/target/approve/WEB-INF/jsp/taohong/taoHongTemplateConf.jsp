<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<title>工作流中间件-权限设置</title>
</head>
<body>
		<div class="easyui-layout" data-options="fit:true,border:false">
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
			<table id="taoHongTemplate" class="easyui-datagrid"
					   data-options="toolbar:'#toolbar',fitColumns:true,idField:'template_guid',checkOnSelect:false,selectOnCheck:false,singleSelect:true">
						<thead>
							<tr>
								<th data-options="field:'template_guid',checkbox:true">id</th>
								<th data-options="field:'template_fileName',width:'30%',align:'center'">名称</th>
								<th data-options="field:'bureau_name',width:'30%',align:'center'">委办局</th>
								<th data-options="field:'template_type',width:'20%',align:'center'">模板类型</th>
								<th data-options="field:'tabIndex',width:'10%',align:'center'">序号</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${taoHongTemplateList}" var="taoHongTemplate" varStatus="status">
								<tr>
									<td>${taoHongTemplate.template_guid}</td>
									<td><a href='${ctx}/taoHongTemplate/download?template_guid=${taoHongTemplate.template_guid}'>${taoHongTemplate.template_fileName}</td>
									<td>${taoHongTemplate.bureau_name}</td>
									<td>${taoHongTemplate.template_type}</td>
									<td>${taoHongTemplate.tabIndex}</td>
								</tr>
							</c:forEach>
						</tbody>
				</table>
		</div>
</body>
	<script type="text/javascript">
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
				var checkRows = $('#taoHongTemplate').datagrid('getChecked');
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
				var checkRows = $('#taoHongTemplate').datagrid('getChecked');
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
										    	   for(var i=0;i<l;i++){
													   var index = $('#taoHongTemplate').datagrid('getRowIndex',checkRows[0]) ;
													   $('#taoHongTemplate').datagrid('deleteRow',index) ;
												   }
										    	   $('#taoHongTemplate').datagrid('acceptChanges');
										    	   $.messager.alert('提示', "删除成功");
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