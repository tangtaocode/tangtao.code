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
				<form id="owForm" name="owForm" method="post" action="${ctx}/rejectReason/saveOrUpdate">
					<input  type = "hidden" name="id"  id="id" value="${specialCompleteReason != null ? specialCompleteReason.id:''}" />
					<input  type = "hidden" name="actionSign"  id="actionSign" value="${actionSign}" />
					<input  type = "hidden" name="processInstanceId"  id="processInstanceId" value="${processInstanceId}" />
					<input  type = "hidden" name="documentTitle"  id="documentTitle" value="${documentTitle}" />
					<table border="0" cellpadding="0" cellspacing="1" class="table">
						<tr>
							<td class="lefttd lbl-must" style="width: 25%">办结结果</td>
							<td class="rigthtd" style="width: 75%">
								<select id="type" name="type" class="easyui-combobox" data-options="panelHeight:'auto'" style="width: 99%">
									<option value="1" <c:if test="${specialCompleteReason.type == 1}">selected</c:if>>退回办结(退回或者驳回的办结)</option>
									<option value="2" <c:if test="${specialCompleteReason.type == 2}">selected</c:if>>作废办结(指业务处理上无效的记录)</option>
									<option value="3" <c:if test="${specialCompleteReason.type == 3}">selected</c:if>>删除办结(指录入错误、操作错误等技术上的无效记录)</option>
									<option value="4" <c:if test="${specialCompleteReason.type == 4}">selected</c:if>>转报办结(指转报其他单位或者上级单位的办结情况)</option>
									<option value="5" <c:if test="${specialCompleteReason.type == 5}">selected</c:if>>补交不来办结(指出现补交告知时,通知之后,申请人长期不来补交材料的办结)</option>
								</select>
							</td>
						</tr>
						<tr>
						    <td class="lefttd lbl-must" style="width: 25%">办结结果理由</td>
							<td class="rigthtd" style="width: 75%" colspan="3">
								<textarea id="rejectReason" name="rejectReason" class="easyui-validatebox textboxArea">${specialCompleteReason != null ? specialCompleteReason.rejectReason : '' }</textarea>
							</td>
						</tr>
						<tr >
							<td colspan="4" align="center">
								<input type="submit" value="保存理由并办结" />
								<input type="button" value="取消" id="cancel"/>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</body>
	
	<script type="text/javascript" src="${ctx}/static/jquery/form/jquery.form.js"></script>
	<script type="text/javascript">
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
						specialComplete();
					}else{
						alert("保存特殊办结原因失败！");
					}
				}
		 	};
		
		$("form[name=owForm]").submit(function(){
			if($(this).form('validate')){
				 $(this).ajaxSubmit(options);
			}
			return false;
		}) ;
		
		$("#cancel").click(function(){
			closeCurWindow(parent.frameID,'close');
		});
		
		function specialComplete(){
			var taskId="${taskId}";
			$.ajax({
				async : false,
				type : 'POST',
				dataType:'JSON',
				data : {
					taskId:taskId
				},
				url :ctx + '/sp/buttonOperation/specialComplete',
				success : function(data) {
					if(data.success){
						alert("特殊办结成功！");
						parent.window.parent.refreshMenu();
					}
				}
			});
		}
	</script>
</html>