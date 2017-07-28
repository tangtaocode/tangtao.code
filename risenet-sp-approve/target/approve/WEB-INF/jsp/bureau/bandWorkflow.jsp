<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/static/common/taglib.jsp"%>
<title>委办局事项</title>
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/framework.js"></script>
</head>
<body>
	<form action="${ctx}/bureau/bandWorkflow" id="form">
	<input type="hidden" name="approveitemguid"  value="${approveItem.approveitemguid}" />
	
	<div>
		<table align="center">
			<tr>
				<td colspan="2" align="center"><h2>审批事项与流程关联</h2></td>
			</tr>
			<tr>
				<td>审批事项名称</td>
				<td><input type="text" name="approveitemname" style="width:250px" readonly value="${approveItem.approveitemname}" />
				
				</td>
			</tr>
			<tr>
				<td>定义的流程</td>
				<td>
					<select name="workflowguid" id="workflowguid" prompt="" url="${ctx}/bureau/workFlowSelect" boxHeight="200" selWidth="200" selectedValue="${approveItem.workflowguid}"></select>
				</td>
			</tr>
			<tr>
			<td>盖章类型:</td>
			<td>
				<select name="sealType" id="sealType" prompt="" data='{"list":[{"value":"1","key":"局章"},{"value":"2","key":"部门章"}]}' boxHeight="100" selWidth="100" selectedValue="${approveItem.sealType}"></select>
			</td>
		</tr>
			<tr style="height:80px">
				<th colspan="2" align="left"><input type="button" onclick="save();" value="保存"/></th>
				
			</tr>
		</table>
		</div>
	</form>	
</body>
<script type="text/javascript">


	function save(){
		 if ($("#workflowguid").val()==""){
		  	  	alert("请选择流程");
		  	  	$("#workflowGUID").focus();
		  	  	return false;
		 }
		 if ($("#sealType").val()==""){
		  	  	alert("请选择盖章类型");
		  	  	$("#sealType").focus();
		  	  	return false;
		 }
		 $("#form").ajaxSubmit({
				type : 'POST',
				dataType : 'json', 
				url : '${ctx}/bureau/bandWorkflow',
				success : function(responseText, statusText, xhr, $form) {
					//reloadGrid();
					alert(responseText.message);
					parent.Dialog.close();
				}
			});
	
	}
	
</script>
</html>