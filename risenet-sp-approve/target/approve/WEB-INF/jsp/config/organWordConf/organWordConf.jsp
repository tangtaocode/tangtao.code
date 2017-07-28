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
								<a id="addOrganWord" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" href="javascript:void(0);">新增机关代字</a>
								<a id="modifyOrganWord" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" href="javascript:void(0);">编辑机关代字</a>
								<a id="removeOrganWord" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true"  href="javascript:void(0);">删除机关代字</a>
								<a id="orderOrganWord" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true"  href="javascript:void(0);">机关代字排序</a>
								<a id="setupDocumentNumberDetail" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true"  href="javascript:void(0);">文号配置</a>
							</td>
						</tr>
					</table>
				</div>
			<table id="organWordListId" class="easyui-datagrid"
					   data-options="toolbar:'#toolbar',fitColumns:true,idField:'id',checkOnSelect:false,selectOnCheck:false,singleSelect:true">
						<thead>
							<tr>
								<th data-options="field:'id',checkbox:true">id</th>
								<th data-options="field:'characterValue',width:'45%',align:'center'">机关代字</th>
								<th data-options="field:'tabIndex',width:'45%',align:'center'">序号</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${organWordList}" var="organWord" varStatus="status">
								<tr>
									<td>${organWord.id}</td>
									<td>${organWord.characterValue}</td>
									<td>${organWord.tabIndex}</td>
								</tr>
							</c:forEach>
						</tbody>
				</table>
		</div>
</body>
	<script type="text/javascript">
		var frameID = newGuid();
			//添加机关代字
			$('#addOrganWord').click(function(){
				openCurWindow({
					id : frameID,
					src : ctx+'/organWord/newOrModify',
					destroy : true,
					title : '添加',
					width : 500,
					height : 200,
					modal : true
				});
			});
			
			//修改机关代字
			$('#modifyOrganWord').click(function(){
				var checkRows = $('#organWordListId').datagrid('getChecked');
				var l = checkRows.length ;
				if(l==0){
					$.messager.alert('提示', "请先勾选要修改的机关代字");
					return ;
				}
				if(l>1){
					$.messager.alert('提示', "只能选择一个机关代字进行修改");
					return ;
				}
				openCurWindow({
					id : frameID,
					src : ctx+'/organWord/newOrModify?id='+checkRows[0].id,
					destroy : true,
					title : '编辑',
					width : 500,
					height : 200,
					modal : true
				});
			});
			
			//设置文号年份
			$('#setupDocumentNumberDetail').click(function(){
				openCurWindow({
					id : frameID,
					src : ctx+'/organWord/setupDocumentNumberDetail',
					destroy : true,
					title : '设置',
					width : 500,
					height : 200,
					modal : true
				});
			});
			
			//排序
			$('#orderOrganWord').click(function(){
				openCurWindow({
					id : frameID,
					src : ctx+'/organWord/orderOrganWord',
					destroy : true,
					title : '排序',
					width : 200,
					height : 300,
					modal : true
				});
			});
			
			//移除机关代字
			$("#removeOrganWord").click(function(){
				var checkRows = $('#organWordListId').datagrid('getChecked');
				var l = checkRows.length ;
				if(l==0){
					$.messager.alert('提示', "请先勾选要移除的机关代字");
					return ;
				}
				$.messager.confirm('Confirm','确定要移除选中的机关代字?',
						function(r){
							   if(r){
									   var params ="?";
									   for(var i=0;i<l;i++){
										   params+=('&organWordIds='+checkRows[i].id);
									   }
									   $.ajax({
										       async : false,  
										       cache : false,  
										       type: 'POST',
										       url:  ctx+'/organWord/removeOrganWords'+params,
										       error: function () {
										           alert('删除失败！');  
										       },
										       success:function(data){
										    	   for(var i=0;i<l;i++){
													   var index = $('#organWordListId').datagrid('getRowIndex',checkRows[0]) ;
													   $('#organWordListId').datagrid('deleteRow',index) ;
												   }
										    	   $('#organWordListId').datagrid('acceptChanges');
										    	   $.messager.alert('提示', "删除成功");
										       }
									   });
							   }
						}
				);
			});
			//在执行code钱需要等待的毫秒数
			//setTimeout("parent.$('#tabs').tabs('select','基本信息');",10);
	</script>
</html>