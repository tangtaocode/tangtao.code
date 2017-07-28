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
		<title>套红模板类型管理</title>
		<%@ include file="/static/common/head.jsp"%>
		<%@ include file="/static/common/common.jsp"%>
	</head>
		
	<body>
		<div class="easyui-layout" data-options="fit:true,border:false">
			<div data-options="region:'center',border:false">
				<form id="thForm" name="thForm" method="post" action="${ctx}/taoHongTemplateType/saveOrUpdate">
					<input  type = "hidden" name="id"  id="id" value="${taoHongTemplateType != null ? taoHongTemplateType.id:''}" />
					<table border="0" cellpadding="0" cellspacing="1" class="table">
						<tr>
						    <td class="lefttd lbl-must" style="width: 35%">类型<font color="red">*</font>：</td>
							<td class="rigthtd" style="width: 65%">
								<input  name="typeName" value="${taoHongTemplateType != null ? taoHongTemplateType.typeName : '' }" class="easyui-validatebox textbox" data-options="required:true" />
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
		var operationType = '${not empty taoHongTemplateType ? "modify":"create"}';
		var options = { 
				async : false,  
				cache : false,
				dataType:"json",
				type:'POST', 
				error:function(data){
					alert("出现异常,此次操作可能失败");
				},
				success:function(result){
					var taoHongTemplateType = result;
					top.$.messager.alert('提示', "保存成功！" , "info" , function(){
					 	if(operationType == 'create'){
				 	    	parent.$('#taoHongTemplateType').datagrid('appendRow',taoHongTemplateType);
				 	    	parent.$('#taoHongTemplateType').datagrid('acceptChanges');
					 		closeCurWindow(parent.frameID,'close');
						}else if(operationType == 'modify'){
							var index = parent.$('#taoHongTemplateType').datagrid('getRowIndex',taoHongTemplateType.id);
							parent.$('#taoHongTemplateType').datagrid('updateRow',{
								index: index,
								row: taoHongTemplateType
							});
							parent.$('#taoHongTemplateType').datagrid('acceptChanges');
							closeCurWindow(parent.frameID,'close');
					 	}
					});
				}
		 	};
		
		$("form[name=thForm]").submit(function(){
			if($(this).form('validate')){
				 $(this).ajaxSubmit(options);
			}
			return false;
		}) ;
	</script>
</html>