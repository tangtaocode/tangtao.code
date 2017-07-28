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
				<form id="buyupizhunForm" name="buyupizhunForm" method="post" action="${ctx}/sp/rejectReason/saveOrUpdate">
					<input  type = "hidden" name="id"  id="id" value="${rejectReasonEntity != null ? rejectReasonEntity.id:''}" />
					<input  type = "hidden" name="actionSign"  id="actionSign" value="${actionSign}" />
					<input  type = "hidden" name="processInstanceId"  id="processInstanceId" value="${processInstanceId}" />
					<input  type = "hidden" name="taskId"  id="taskId" value="${taskId}" />
					<table border="0" cellpadding="0" cellspacing="1" class="table">
						<tr>
						    <td class="lefttd lbl-must" style="width: 25%">${actionSignName}原因：</td>
							<td class="rigthtd" style="width: 75%" colspan="3">
								<textarea id="rejectReason" name="rejectReason" class="easyui-validatebox textboxArea">${rejectReasonEntity != null ? rejectReasonEntity.rejectReason :''}</textarea>
							</td>
						</tr>
						
						<tr >
							<td colspan="4" align="center">
								<input id="send" type="button" value="发送" />
								<input type="submit" value="保存" />
								<input id="cancel" type="button" value="取消" />
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</body>
	
	<script type="text/javascript" src="${ctx}/static/jquery/form/jquery.form.js"></script>
	<script type="text/javascript">
		var actionSign="${actionSign}";
		var functionStr="";
		var options = { 
				async : false,  
				cache : false,
				dataType:"json",
				type:'POST', 
				error:function(data){
					alert("出现异常,此次操作可能失败");
				},
				success:function(data){
					if(data.success){
						alert("保存成功");
					};
				}
		 	};
		
		$("form[name=buyupizhunForm]").submit(function(){
			if($(this).form('validate')){
				 $(this).ajaxSubmit(options);
			}
			return false;
		}) ;
	
		$("#cancel").click(function(){
			closeCurWindow(parent.frameID,'close');
		});
		if(actionSign=="buYuPiZhun"){
			functionStr=parent.buYuPiZhun;
		}else if(actionSign=="buYuShouLi"){
			functionStr=parent.buYuShouLi;
		}else{
			alert("发送出错,没有动作标识！");
		}
		$("#send").click(functionStr);
	</script>
</html>