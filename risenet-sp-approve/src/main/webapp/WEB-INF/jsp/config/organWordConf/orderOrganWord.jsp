<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="Content-Language" content="utf-8"/>
		<meta http-equiv="pragma" content="no-cache">   
   	 	<meta http-equiv="cache-control" content="no-cache">
    	<meta http-equiv="expires" content="0">
		<title>有生软件组织架构平台</title>
		<%@ include file="/static/common/head.jsp"%>
		<%@ include file="/static/common/common.jsp"%>
	</head>
	<body>
		<div class="easyui-layout" data-options="fit:true,border:false">
			<div data-options="region:'north',border:false" class="toolbar" style="background-color:#CCEEFF;">
				<table border="0" cellpadding="0" cellspacing="0" style="width: auto">
					<tr>
						<td>
							<a id="moveUp" class="easyui-linkbutton" data-options="iconCls:'icon-arrow-up',plain:true" href="javascript:void(0);">上移</a>
							<a id="moveDown" class="easyui-linkbutton" data-options="iconCls:'icon-arrow-down',plain:true" href="javascript:void(0);">下移</a>
							<a id="saveOrder" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" href="javascript:void(0);">保存</a>
						</td>
					</tr>
				</table>
			</div>
			<div data-options="region:'center',border:false" style="overflow:auto;">
				<table id="organWordList" class="easyui-datagrid"
					   data-options="toolbar:'#toolbar',idField:'id',checkOnSelect:false,selectOnCheck:false,singleSelect:true">
						<thead>
							<tr>
								<th data-options="field:'id',title:'id',hidden:true">id</th>
								<th data-options="field:'characterValue',width:'180'">机关名称</th>
								<th data-options="field:'tabIndex',title:'排序',hidden:true">排序</th>
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
		</div>
	</body>
	<script type="text/javascript">
		var parentID = '${parentID}' ;
		$(function() {
			$("#moveUp").click(function(){
				var selectedRow = $('#organWordList').datagrid('getSelected');
				if(!selectedRow){
					return ;
				}
				var currentIndex = $('#organWordList').datagrid('getRowIndex',selectedRow);
				if(currentIndex==0){
					return;
				}
				var allRows = $('#organWordList').datagrid('getRows');
				var targetRow = allRows[currentIndex-1];
				//先交换序号tabIndex
				var tempIndex = selectedRow.tabIndex;
				selectedRow.tabIndex = targetRow.tabIndex;
				targetRow.tabIndex = tempIndex ;
				$('#organWordList').datagrid('deleteRow',currentIndex);
				$('#organWordList').datagrid('insertRow',{
														index:currentIndex-1,
														row:selectedRow
														}
										 );
				$('#organWordList').datagrid('selectRow',currentIndex-1);
				$('#organWordList').datagrid('refreshRow',currentIndex);
			});
			
			$("#moveDown").click(function(){
				var selectedRow = $('#organWordList').datagrid('getSelected');
				if(!selectedRow){
					return ;
				}
				var currentIndex = $('#organWordList').datagrid('getRowIndex',selectedRow);
				var allRows = $('#organWordList').datagrid('getRows');
				if(currentIndex==(allRows.length-1)){
					return;
				}
				var targetRow = allRows[currentIndex+1];
				//先交换序号tabIndex
				var tempIndex = selectedRow.tabIndex;
				selectedRow.tabIndex = targetRow.tabIndex;
				targetRow.tabIndex = tempIndex ;
				$('#organWordList').datagrid('deleteRow',currentIndex);
				$('#organWordList').datagrid('insertRow',{
														index:currentIndex+1,
														row:selectedRow
														}
										 );
				$('#organWordList').datagrid('selectRow',currentIndex+1);
				$('#organWordList').datagrid('refreshRow',currentIndex);
			});
			
			$("#saveOrder").click(function(){
				var allRows = $('#organWordList').datagrid('getRows');
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
				       url:  ctx+'/organWord/saveOrder'+params,
				       error: function () {
				           top.$.messager.alert('提示', "保存失败");
				           $('#organWordList').datagrid('rejectChanges');
				       },
				       success:function(data){
				    	   $('#organWordList').datagrid('acceptChanges');
				    	   parent.$('#organWordListId').datagrid('loadData',allRows);
				    	   top.$.messager.alert('提示', "保存成功");
				       }
			   });
			});
			
		});
	</script>
</html>