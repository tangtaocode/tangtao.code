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
		<title>套红模板设置</title>
		<%@ include file="/static/common/head.jsp"%>
		<%@ include file="/static/common/common.jsp"%>
	</head>
		
	<body>
		<div class="easyui-layout" data-options="fit:true,border:false">
			<div data-options="region:'center',border:false">
				<form id="owForm" name="owForm" method="post" action="${ctx}/taoHongTemplate/saveOrUpdate">
					<input  type = "hidden" name="template_guid"  id="template_guid" value="${taoHongTemplate != null ? taoHongTemplate.template_guid:''}" />
					<table border="0" cellpadding="0" cellspacing="1" class="table">
						<tr>
						    <td class="lefttd lbl-must" style="width: 35%">部门<font color="red">*</font>：</td>
							<td class="rigthtd" style="width: 65%">
								<select id="bureau_guid" name="bureau_guid" class="easyui-combobox"  data-options="panelHeight:'110px'" style="width: 99%" data-options="required:true">
								<option value="">请选择</option>
								<c:forEach items="${bureauList}" var="bureau" varStatus="status">
										<option value="${bureau.bureau_guid}" ${bureau.bureau_guid == taoHongTemplate.bureau_guid ? "selected" : "" }>${bureau.bureau_name}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
						    <td class="lefttd lbl-must" style="width: 35%">模板类型<font color="red">*</font>：</td>
							<td class="rigthtd" style="width: 65%">
								<select id="template_type" name="template_type" class="easyui-combobox"  data-options="panelHeight:'90px'" style="width: 99%" data-options="required:true">
								<option value="">请选择</option>
								<c:forEach items="${typeList}" var="type" varStatus="status">
										<option value="${type.typeName}" ${type.typeName == taoHongTemplate.template_type ? "selected" : "" }>${type.typeName}</option>
									</c:forEach>
								</select>
								
							</td>
						</tr>
						<tr >
							<td colspan="4" align="center">
								<input type="file" name="attachmentFile" id="attachmentFile" style="width: 420px; height: 26px" />
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
		var operationType = '${not empty taoHongTemplate ? "modify":"create"}';
		var options = { 
				async : false,  
				cache : false,
				dataType:"json",
				type:'POST', 
				error:function(data){
					alert("出现异常,此次操作可能失败");
				},
				success:function(result){
					var taoHongTemplate = result;
					top.$.messager.alert('提示', "保存成功！" , "info" , function(){
					 	/*if(operationType == 'create'){
				 	    	parent.$('#taoHongTemplate').datagrid('appendRow',taoHongTemplate);
				 	    	parent.$('#taoHongTemplate').datagrid('acceptChanges');
					 		closeCurWindow(parent.frameID,'close');
						}else if(operationType == 'modify'){
							var index = parent.$('#taoHongTemplate').datagrid('getRowIndex',taoHongTemplate.template_guid);
							parent.$('#taoHongTemplate').datagrid('updateRow',{
								index: index,
								row: taoHongTemplate
							});
							parent.$('#taoHongTemplate').datagrid('acceptChanges');
							closeCurWindow(parent.frameID,'close');
					 	}*/
						parent.reloadgrid();
					 	closeCurWindow(parent.frameID,'close');
					});
				}
		 	};
		
		$("form[name=owForm]").submit(function(){
			var bureau_guid=$('#bureau_guid').combobox('getValue');
			if(bureau_guid.length==0){
				top.$.messager.alert('警告','<font color="red">请选择一个委办局！！！</font>'); 
				return false;
			}
			var template_type=$('#template_type').combobox('getValue');
			if(template_type.length==0){
				top.$.messager.alert('警告','<font color="red">请选择一个模板类型！！！</font>'); 
				return false;
			}
			if($(this).form('validate')){
				 $(this).ajaxSubmit(options);
			}
			return false;
		}) ;
	</script>
</html>