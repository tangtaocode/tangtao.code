<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<title>工作流中间件-催办信息</title>
<style>
</style>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'center',border:false">
			<form id="reminderForm" name="reminderForm" method="post" action="${ctx}/reminder/saveOrUpdate">
				<input  type = "hidden" name="id"  id="id" value="${reminder != null ? reminder.id:''}" />
				<input  type = "hidden" name="taskId"  id="taskId" value="${taskId}" />
				<table border="0" cellpadding="0" cellspacing="1" class="table">
					<tr>
					    <td class="lefttd lbl-must" style="width: 25%;height:10%">催办信息：</td>
						<td class="rigthtd" style="width: 75%" colspan="3">
							<textarea id="msgContent" name="msgContent" class="easyui-validatebox textboxArea">${reminder != null ? reminder.msgContent : '' }</textarea>
						</td>
					</tr>
					<tr >
						<td colspan="4" align="center" style="width: 25%;height:10%">
							<input type="submit" value="保存" />
							<input type="button" value="关闭" id="close"/>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
<script type="text/javascript" src="${ctx}/static/jquery/form/jquery.form.js"></script>
<script type="text/javascript">
	var operationType = '${not empty reminder ? "modify":"create"}';
	var options = { 
			async : false,  
			cache : false,
			dataType:"json",
			type:'POST', 
			error:function(data){
				alert("出现异常,此次操作可能失败");
			},
			success:function(data){
				alert(data.msg);
				if(data.success){
					if(operationType=='create'){
						var doinglist=parent.doinglist;
						var length=doinglist.length;
						for(var i=0;i<length;i++){
							var rowObj = parent.$('#maingrid').quiGrid('getRowObj',doinglist[i]);
							doinglist[i].taskId4Reminder=doinglist[i].taskId;
							parent.g.updateRow(doinglist[i],rowObj);
						}
					}
					parent.Dialog.close();
				}
				
			}
	 	};
	
	$("form[name=reminderForm]").submit(function(){
		if($(this).form('validate')){
			 $(this).ajaxSubmit(options);
		}
		return false;
	}) ;
	
	$('#close').click(function(){
		parent.Dialog.close();
		//closeCurWindow(parent.frameID,'close');
	});
	
</script>
</html>
