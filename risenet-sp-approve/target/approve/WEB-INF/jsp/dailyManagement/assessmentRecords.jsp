<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ include file="/static/common/util.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>奖惩记录表</title>
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/framework.js"></script>
</head>

<script type="text/javascript">
	function  savejcjl(){
		
		 var scoreGuid=$("#sg").val();
		 alert(scoreGuid);
		 var rewards=$("#rewards").val();
		 var punishment=$("#punishment").val();
		 var complaints=$("#complaints").val();
		 var praise=$("#praise").val(); 
	  $("#form").ajaxSubmit({
			type : 'POST',
			dataType : 'json', 
			data:{scoreGuid:scoreGuid,rewards:rewards,punishment:punishment,complaints:complaints,praise:praise},
			url : '${ctx}/dailymanagement/savejcjl',
			success : function(data) {
				//reloadGrid();
					alert(data.message,function(){window.location.reload()});
					parent.afterFormSubmit();
					parent.Dialog.close();   
			}   
			}) 
		}
</script>

<body>
<div onload="getDate();" class="box1" id="formContent" whiteBg="true">
<form id="form" action=""   method="post"><input type="hidden" id="sg" value="${score.scoreguid }"/>
	<table class="tableStyle"  formMode="transparent" border="0">
		<tr>
			<td>姓名:</td>
			<td>${score.name}<td>
		<td></td>
		</tr>
		<tr>
			<td>工号:</td>
			<td>${score.enrollnumber }</td>
		<td></td>
		</tr>
		<tr>
			<td>奖励</td>
			<td><input type="text" id="rewards" value="${score.rewards }" disabled="true" /></td>
			<td>惩罚</td>
			<td><input type="text" id="punishment" value="${score.punishment }"  disabled="true" /></td>
		</tr>
		
		<tr>
			<td>投诉</td>
			<td><input type="text" id="complaints" value="${score.complaints }"  disabled="true"/></td>
			<td>表扬</td>
			<td><input type="text" id="praise"  value="${score.praise }" disabled="true" /></td>
		</tr>
		<tr>
			<td><input type="button" value="确定" onclick="parent.Dialog.close()"/></td>
		</tr>
		
	</table>
</form>
</div>
</body>
</html>