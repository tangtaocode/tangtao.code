<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<html>
<head>
<title>深圳市龙岗区住房与建设局网上行政服务大厅</title>
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript" src="/dwr/interface/UserDwrJs.js"></script>
<script type="text/javascript">
function editPwd(){
	 var data = $("#submitForm").serializeArray(); 
		  $.post("/userService/editPassword.html", data,function(data){
		  	Dialog.alert(data.message);
		  },"json");
}
</script>
</head>
<body>
<s:form action="#" method="post" id="submitForm">
<s:hidden name="method" value="edit"></s:hidden>
<s:hidden name="guid" id="guid"></s:hidden>
<table width="99%" cellpadding="0" cellspacing="0">
<tr> 
<td class="title1">修改密码</td>
</tr>
<tr><td class="td_left1">
<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" id="userInfoTab">
	<tr><th style="width:15%">
		原密码:
	</th>
	<td style="width:85%">
		<s:password maxlength="16" verify="原密码|NotNull"  cssClass="zc_input02" size="20"  id="oldPassword" onblur="checkPassword()"></s:password>
		<span style="color:red">*</span>&nbsp;&nbsp;&nbsp;
	</td>
	</tr>
	<tr><th style="width:15%">
		新密码:
	</th>
	<td style="width:85%">
		<s:password maxlength="16" verify="新密码|NotNull"  cssClass="zc_input02" size="20" name="newPassword" id="password" onblur="checkPwdIsNull()"></s:password>
		<span style="color:red">*</span><span id="passMessage"></span>&nbsp;&nbsp;&nbsp;<span class="messinfo">6～16个字符（字母、数字、特殊符号）,区分大小写</span>
	</td>
	<tr><th style="width:15%">
		再次输入:
	</th>
	<td style="width:85%">
		<s:password maxlength="16" verify="重复密码|NotNull" cssClass="zc_input02" size="20" id="passwordRepeat" onblur="checkPasswd();"></s:password>
		<span style="color:red">*</span><span id="rpassMessage"></span>&nbsp;&nbsp;&nbsp;<span class="messinfo">两次密码输入必须一致</span>
	</td>
	</tr>
	<tr><td colspan="2" style="height:30px;text-align:center;"><input type="button" onclick="editPwd()" value="修 改" class="buttonClass_1"> &nbsp;&nbsp;
			</tr>
	</table>
	</td></tr></table>
	</s:form>
	
</body>
</html>
