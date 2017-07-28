<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/bpmTaglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<title>工作流中间件-动态角色</title>
</head>
<body>
		<div class="easyui-layout" data-options="fit:true,border:false">
				<div id="toolbar" class="toolbar" style="background-color:#CCEEFF;">
					<table border="0" cellpadding="0" cellspacing="0" style="width: auto">
						<tr>
							<td>
								<a id="addDynamicRole" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" href="javascript:void(0);">新增</a>
								<a id="modifyDynamicRole" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" href="javascript:void(0);">编辑</a>
								<a id="removeDynamicRole" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true"  href="javascript:void(0);">删除</a>
								<!-- <a id="orderDynamicRole" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true"  href="javascript:void(0);">排序</a> -->
							</td>
						</tr>
					</table>
				</div>
				<table id="dynamicRoleListId"> </table>
		</div>
</body>
	<script type="text/javascript">
	
	var grid = $('#dynamicRoleListId');
	//初始化
	$(document).ready(function() {
		grid.datagrid({
			url: '${ctx}/dynamicRole/dynamicRoleList',  
			toolbar : '#toolbar',
			//view : detailview,
			fit : true,
			singleSelect : false,
			remoteSort:false,
			fitColumns : true,
			striped : true,
			rownumbers : true,
			//pagination : true,//分页控件，要分页时设置为true
			pageSize : 30,
			border : true,
			nowrap : false,
            columns:[[  
            	{
					title : "",
					field : "id",
					checkbox : true
				},{
					title : '名称',
					field : 'name',
					width : 50,
					align : "center"
				}, {
					title : '类型',
					field : 'type',
					width : 40,
					align : "center",
					formatter: function(value, row){
						var str = '';
						if(value=='0'){
							str = '类路径';
						}else{
							str = '查询sql语句';
						}
                         return str;
        		    }
				},{
					title : '类全路径',
					field : 'classPath',
					width : 100,
					align : "center"
				},{
					title : '查询人员sql',
					field : 'personWhere',
					width : 100,
					align : "center"
				},{
					title : '查询部门sql',
					field : 'departmentWhere',
					width : 100,
					align : "center"
				},{
					title : '查询岗位sql',
					field : 'positionWhere',
					width : 100,
					align : "center"
				},{
					title : '查询用户组sql',
					field : 'groupWhere',
					width : 100,
					align : "center"
				},{
					title : '描述',
					field : 'description',
					width : 100,
					align : "center"
				}
            ]]  
          });
       });
		
		var frameID = newGuid();
			//添加动态角色
			$('#addDynamicRole').click(function(){
				openCurWindow({
					id : frameID,
					src : ctx+'/dynamicRole/newOrModify',
					destroy : true,
					title : '添加',
					width : ($(window).width())*0.8 + 'px',
					height : ($(window).height())*0.95 + 'px',
					modal : true
				});
			});
			
			//修改动态角色
			$('#modifyDynamicRole').click(function(){
				var checkRows = $('#dynamicRoleListId').datagrid('getChecked');
				var l = checkRows.length ;
				if(l==0){
					$.messager.alert('提示', "请先勾选要修改的动态角色");
					return ;
				}
				if(l>1){
					$.messager.alert('提示', "只能选择一个动态角色进行修改");
					return ;
				}
				openCurWindow({
					id : frameID,
					src : ctx+'/dynamicRole/newOrModify?id='+checkRows[0].id,
					destroy : true,
					title : '编辑',
					width : ($(window).width())*0.8 + 'px',
					height : ($(window).height())*0.95 + 'px',
					modal : true
				});
			});
			
			//排序
			$('#orderDynamicRole').click(function(){
				openCurWindow({
					id : frameID,
					src : ctx+'/dynamicRole/orderDynamicRole',
					destroy : true,
					title : '排序',
					width : 200,
					height : 300,
					modal : true
				});
			});
			
			//移除动态角色
			$("#removeDynamicRole").click(function(){
				var checkRows = $('#dynamicRoleListId').datagrid('getChecked');
				var l = checkRows.length ;
				if(l==0){
					$.messager.alert('提示', "请先勾选要移除的动态角色");
					return ;
				}
				$.messager.confirm('提示','确定要移除选中的动态角色?',
						function(r){
							   if(r){
									   var params ="?";
									   for(var i=0;i<l;i++){
										   params+=('&dynamicRoleIds='+checkRows[i].id);
									   }
									   $.ajax({
										       async : false,  
										       cache : false,  
										       type: 'POST',
										       url:  ctx+'/dynamicRole/removeDynamicRoles'+params,
										       error: function () {
										           alert('删除失败！');  
										       },
										       success:function(data){
										       		if(data.success==true||data.success=='true'){
										    	   		alert("删除成功");
										    	   		grid.datagrid("reload");
										    	   }else{
										    	   		alert("删除失败");
										    	   }
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