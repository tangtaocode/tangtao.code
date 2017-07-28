<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/static/common/head.jsp"%>
<title>工作流中间件-内置用户页面</title>
<link type="text/css" rel="stylesheet" href="${ctx}/static/jquery/jquery-ui/css/redmond/jquery-ui-1.10.3.custom.css" />

<script type="text/javascript" src="${ctx}/static/jquery/jquery-ui/js/jquery-ui-1.10.3.custom.min.js"></script>

<script type="text/javascript">

$(document).ready(function() {
	
	$('#submit1').click(function(){
		var buildInUser=$("input[name='buildInUser']:checked").val();
		if(buildInUser=="" || buildInUser.length==0)
		{
			alert("请选取内置用户");
			return;
		}
		window.returnValue=buildInUser;
		window.close();
	});
	
	$('#closeDlg').click(function(){
		window.close();
	});
	
});

</script>
</head>
<body>
<div style="border-left: 1px dotted ThreedShadow; border-right: 1px dotted ThreedShadow;border-bottom: 1px dotted ThreedShadow;border-top: 0px;">
	<div style="margin-top:10px;" align="center">
		<input type="button" id="submit1" name="submit1" value="确 定" style="border:1px solid #669999; background-color:#FFFFFF;font:Menu"/>
		&nbsp;&nbsp;
		<input type="button" name="closeDlg" id="closeDlg" value=" 关 闭 " style="border:1px solid #669999; background-color:#FFFFFF;font:Menu"/>
	</div>
	<div align="center">
		<table style="width:100%;">
			<tr>
				<td colspan=2 style="text-align:right;height:30px;">
					
				</td>
			</tr>
			<tr>
				<td style="text-align:right;width:45%;height:30px;">
					<input type="radio" id="buildInUser1" name="buildInUser" value="drafter"/>
				</td>
				<td style="text-align:left">
					起草人
				</td>
			</tr>
			<tr>
				<td style="text-align:right;height:30px;">
					<input type="radio" id="buildInUser2" name="buildInUser" value="senderUser"/>
				</td>
				<td style="text-align:left">
					上一发送人
				</td>
			</tr>
			<tr>
				<td style="text-align:right;height:30px;">
					<input type="radio" id="buildInUser3" name="buildInUser" value="currentUser"/>
				</td>
				<td style="text-align:left">
					当前用户
				</td>
			</tr>
			<tr>
				<td style="text-align:right;height:30px;">
					<input type="radio" id="buildInUser4" name="buildInUser" value="anyUser"/>
				</td>
				<td style="text-align:left">
					任意用户
				</td>
			</tr>
		</table>
	</div>
</div>
<div>
</div>
</body>
</html>