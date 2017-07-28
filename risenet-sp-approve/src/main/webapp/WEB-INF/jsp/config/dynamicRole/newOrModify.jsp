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
			<div data-options="region:'center',border:false">
				<form id="owForm" name="owForm" method="post" action="${ctx}/dynamicRole/saveOrUpdate">
					<input  type = "hidden" name="id"  id="id" value="${dynamicRole != null ? dynamicRole.id:''}" />
					<table border="0" cellpadding="0" cellspacing="1" class="table">
						<tr>
						    <td class="lefttd lbl-must" style="width: 15%">名称：</td>
							<td class="rigthtd" style="width: 35%">
								<input  name="name" value="${dynamicRole != null ? dynamicRole.name : '' }" class="easyui-validatebox textbox"/>
							</td>
							<td class="lefttd lbl-must" style="width: 15%">类型：</td>
							<td class="rigthtd" style="width: 35%">
								<select id="type" name="type" class="easyui-combobox" data-options="panelHeight:'auto'" style="width: 99%">
									<option value="0" <c:if test="${dynamicRole.type == 0}">selected</c:if>>动态角色类</option>
									<option value="1" <c:if test="${dynamicRole.type == 1}">selected</c:if>>动态角色查询sql</option>
								</select>
							</td>
						</tr>
						<tr>
						    <td class="lefttd lbl-must" style="width: 25%">类的全路径：</td>
							<td class="rigthtd" style="width: 75%" colspan="3">
								<input  name="classPath" value="${dynamicRole != null ? dynamicRole.classPath : '' }" class="easyui-validatebox textbox"/>
							</td>
						</tr>
						<tr>
						    <td class="lefttd lbl-must" style="width: 25%">查询人员sql：</td>
							<td class="rigthtd" style="width: 75%" colspan="3">
								<textarea id="personWhere" name="personWhere" class="easyui-validatebox textboxArea">${dynamicRole != null ? dynamicRole.personWhere :''}</textarea>
							</td>
						</tr>
						<tr>
						    <td class="lefttd lbl-must" style="width: 25%">查询部门sql：</td>
							<td class="rigthtd" style="width: 75%" colspan="3">
								<textarea id="departmentWhere" name="departmentWhere" class="easyui-validatebox textboxArea">${dynamicRole != null ? dynamicRole.departmentWhere : '' }</textarea>
							</td>
						</tr>
						<tr>
						    <td class="lefttd lbl-must" style="width: 25%">查询岗位sql：</td>
							<td class="rigthtd" style="width: 75%" colspan="3">
								<textarea id="positionWhere" name="positionWhere" class="easyui-validatebox textboxArea">${dynamicRole != null ? dynamicRole.positionWhere : '' }</textarea>
							</td>
						</tr>
						<tr>
						    <td class="lefttd lbl-must" style="width: 25%">查询用户组sql：</td>
							<td class="rigthtd" style="width: 75%" colspan="3">
							<textarea id="groupWhere" name="groupWhere" class="easyui-validatebox textboxArea">${dynamicRole != null ? dynamicRole.groupWhere : '' }</textarea>
							</td>
						</tr>
						<tr>
						    <td class="lefttd lbl-must" style="width: 25%">描述：</td>
							<td class="rigthtd" style="width: 75%" colspan="3">
								<textarea id="description" name="description" class="easyui-validatebox textboxArea">${dynamicRole != null ? dynamicRole.description : '' }</textarea>
							</td>
						</tr>
						<tr >
							<td colspan="4" align="center">
								<input type="submit" value="提交" />
								<input type="reset" value="重置" />
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</body>
	
	<script type="text/javascript" src="${ctx}/static/jquery/form/jquery.form.js"></script>
	<script type="text/javascript">
		var operationType = '${not empty dynamicRole ? "modify":"create"}';
		var options = { 
				async : false,  
				cache : false,
				dataType:"json",
				type:'POST', 
				error:function(data){
					alert("出现异常,此次操作可能失败");
				},
				success:function(result){
					if(result.success){
						alert("保存成功");
						parent.grid.datagrid("reload");
						closeCurWindow(parent.frameID,'close');
					}else{
						alert("保存失败");
					}
				}
		 	};
		
		$("form[name=owForm]").submit(function(){
			if($(this).form('validate')){
				 $(this).ajaxSubmit(options);
			}
			return false;
		}) ;
	</script>
</html>