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
		<title>模板设置</title>
		<%@ include file="/static/common/head.jsp"%>
		<%@ include file="/static/common/common.jsp"%>
	</head>
		
	<body>
		<div class="easyui-layout" data-options="fit:true,border:false">
			<div data-options="region:'center',border:false">
				<form id="owForm" name="owForm" method="post" enctype="multipart/form-data" action="${ctx}/templateConfig/saveOrUpdate">
					<input type = "hidden" name="templateGuid"  id="templateGuid" value="${template != null ? template.templateGuid:''}" />
					<input type="hidden" name="approveItemName" id="approveItemName" value="${template != null ? template.approveItemName:''}"/>
					<table border="0" cellpadding="0" cellspacing="1" class="table">
						<tr>
						    <td class="lefttd lbl-must" style="width: 35%">证照名称<font color="red">*</font>：</td>
							<td class="rigthtd" style="width: 65%">
								<input class="easyui-validatebox" id="certificateName" name="certificateName" value="${template != null ? template.certificateName:''}" style="width: 99%" />
							</td>
						</tr>
						<tr>
						    <td class="lefttd lbl-must" style="width: 35%">事项名称<font color="red">*</font>：</td>
							<td class="rigthtd" style="width: 65%">
								<select id="approveItemGuid" name="approveItemGuid" class="easyui-combobox"  data-options="panelHeight:'110px'" style="width: 99%" data-options="required:true">
								<option value="">请选择</option>
								<%-- <c:forEach items="${approveItemList}" var="approve" varStatus="status">
										<option value="${approve.approveitemGuid}" ${approve.approveitemGuid == template.approveItemGuid ? "selected" : "" }>${approve.approveitemName}</option>
									</c:forEach> --%>
								</select>
							</td>
						</tr>
						<tr>
						    <td class="lefttd lbl-must" style="width: 35%">模板类型<font color="red">*</font>：</td>
							<td class="rigthtd" style="width: 65%">
								<select id="templateType" name="templateType" class="easyui-combobox"  data-options="panelHeight:'90px'" style="width: 99%" data-options="required:true">
								<option value="">请选择</option>
								<c:forEach items="${typeList}" var="type" varStatus="status">
										<option value="${type.typeID}" ${type.typeID == template.templateType ? "selected" : "" }>${type.typeName}</option>
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
							<td class="lefttd lbl-must" style="width: 35%">执行sql<font color="red">*</font>：</td>
							<td class="rigthtd" style="width: 65%">
								<textarea id="executeSql" name="executeSql" style="width: 99%; height: 50px;" >${template != null ? template.executeSql:''}</textarea>
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
		var operationType = '${not empty template ? "modify":"create"}';
		
		$(document).ready(function(){
			var approveItemJson = [];
			approveItemJson.push({id:"",text:"请选择"});
			<c:forEach items="${approveItemList}" var="approveItem" varStatus="status">
				var id = "${approveItem.approveitemGuid}";
				var name = "${approveItem.approveitemName}";
				var isSelect = "${approveItem.approveitemGuid == template.approveItemGuid}";
				var option;
				if(isSelect == 'true'){
					option = {
						id : id,
						text : name,
						selected : true,
					}
				}else{
					option = {
						id : id,
						text : name,
					}
				}
				approveItemJson.push(option);
			</c:forEach>
			$('#approveItemGuid').combobox({
				data : approveItemJson,
				valueField : "id",
				textField : "text",
				onSelect : function(param){
					$("#approveItemName").val(param.text);
				},
			})
		})
		
		var options = { 
				async : false,  
				cache : false,
				dataType:"json",
				type:'POST', 
				error:function(data){
					alert("出现异常,此次操作可能失败");
				},
				success:function(result){
					if(result){
						top.$.messager.alert('提示', "保存成功！" , "info" , function(){
							parent.reloadgrid();
						 	closeCurWindow(parent.frameID,'close');
						});
					}else{
						top.$.messager.alert('提示', "保存失败！" , "info" , function(){
						});
					}
				}
		 	};
		
		$("form[name=owForm]").submit(function(){
			var templateType=$('#templateType').combobox('getValue');
			if(templateType.length==0){
				top.$.messager.alert('警告','<font color="red">请选择一个模板类型！！！</font>'); 
				return false;
			}
			if(templateType == '3'){
				var approveItemGuid=$('#approveItemGuid').combobox('getValue');
				$("#approveItemName").val($('#approveItemGuid').combobox('getText'));
				if(approveItemGuid.length==0){
					top.$.messager.alert('警告','<font color="red">请选择一个事项！！！</font>'); 
					return false;
				}
				var certificateName=$('#certificateName').val();
				if(certificateName.length==0){
					top.$.messager.alert('警告','<font color="red">请填写证照名称！！！</font>');
					return false;
				}
				var file=$('#attachmentFile').val();
				var fileArr=file.split('\\');
				var filename=fileArr[fileArr.length-1];
				var filenameArr = filename.split('\.');
				var filetype = filenameArr[filenameArr.length-1];
				if(file == '' || filename == ''){
					top.$.messager.alert('警告','<font color="red">请选择一个文件！！！</font>'); 
					return false;
				}
				if(filetype!='doc' && filetype != 'docx'){
					top.$.messager.alert('警告','<font color="red">请选择doc，docx的文件！！！</font>'); 
					return false;
				}
				var executeSQL=$('#executeSql').val();
				if(executeSQL.length==''){
					top.$.messager.alert('警告','<font color="red">请填写执行sql！！！</font>'); 
					return false;
				}
				
			}
			if($(this).form('validate')){
				$(this).ajaxSubmit(options);
			}
			return false;
		}) ;
	</script>
</html>