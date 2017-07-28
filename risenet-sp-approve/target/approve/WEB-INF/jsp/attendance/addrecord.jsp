<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理员添加考勤记录</title>
<script type="text/javascript">
	function save(){	
		var enrollnumber=$('#enrollnumber').val().trim();
		var time=$('#time').val().trim();		
		var windowhallname=$(window.parent.document).find('#sel').val();
		if(enrollnumber==""||time==""){
			Dialog.alert("请填写完整信息");
		}else{
			$('#form').ajaxSubmit({
				type:'POST',
				dataType:'json',
				data:{enrollnumber:enrollnumber,time:time,windowhallname:windowhallname},
				url:'${ctx}/zkAttendance/addtime',
				success: function(responseText, statusText, xhr, $form){
					Dialog.alert(responseText.message,function(){
						parent.location.reload();
						parent.Dialog.close();
					});
				}
			});
		}		
	}
		
</script>
<style type="text/css">

</style>
</head>
<body>
	<div>
		<form action="" method="post" id="form">
			<table class="tableStyle" formMode="line">
			<tr>
				<td>工号</td>
				<td><input id="enrollnumber"/></td>
			</tr>
			<tr>
				<td>考勤时间</td>
				<td><input id="time" type="text" class="date" dateFmt="yyyy-MM-dd HH:mm:ss" style="width:150px"/></td>
			</tr>			
			<tr>
				<td colspan="2"><button type="button" onclick="save()">保存</button></td> 								 
			</tr>
			</table>
		</form>
	</div>
</body>
</html>