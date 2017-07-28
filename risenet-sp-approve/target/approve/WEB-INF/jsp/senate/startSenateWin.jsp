<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>审批服务评价</title>
<link type="text/css" rel="stylesheet" href="${ctx}/static/risesoft/css/code39.css" />
<%@ include file="/static/common/clientactivex.jsp"%>
<script type="text/javascript">
var ctx="${ctx}";
$(function(){
	
});
function startSenate(){
	spApproveItemId="${approveItemId}";
// 	$("#remindTd").html("正在提交请稍后......"); 
// 	$("#remindTd").css('color','red'); 
// 	$("#startbut").attr("disabled",true);
	PJQ_Open();
	PJQ_Evaluate();
}
</script>
</head>
<body>
<div class="" id="searchPanel">
		<form action="" id="form1" method="post" align="center">
		<input type="hidden" name="fromPage" id="fromPage" value="spfwpj"/>
		<table style="height:70%">
			<tr>
				<td>业务编号：</td>
				<td><input type="text" name="declaresn"  id="declaresn"  style="width:200px;height:25px;"/></td>
				<td>&nbsp;&nbsp;&nbsp;<button id="startbut" type="button" onclick="startSenate()"><span class="icon_save">启动评价</span></button></td>
			</tr>
			<tr>
				<td id="remindTd" colspan="3" style="font-size:13;display: " align="center">提示：请输入已有业务编号，如无业务编号，系统将自动产生。</td>
				<td>&nbsp;</td>
			</tr>
		</table>
		</form>
</body>
</html>