<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../share/taglib.jsp"%>
<html>
<head>
<title>深圳市龙岗区住房与建设局网上行政服务大厅</title>
<link href="/css/appGuide.css" rel="stylesheet" type="text/css"/>
<script src="/js/Scripts/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript" src="/js/zDialog/risenetDialog.js"></script>
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript" src="/dwr/interface/UserDwrJs.js"></script>
<script type="text/javascript" src="/js/userService.js"></script>
<script type="text/javascript" src="/js/validation.js"></script>
<style type="text/css">
body{ margin: 1;
	  padding: 1; 
	  }
</style>
</head>
<body>
<s:form action="/userService/findPassword.html" method="post" id="submitForm">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="table_line">
<tr><th colspan="2" class="title title_center" style="height:30px;text-align:center;">找回密码</th></tr>
	<tr><td class="title_center">
		用户登录名:
	</td>
	<td class="td_left">
		<s:textfield  verify="用户登录名|NotNull"  id="loginname" name="loginname" cssClass="zc_input02" size="20" onblur="checkUserName('1');"></s:textfield>
	</td>
	</tr>
	<tr><td class="title_center">
		联系人手机号码:
	</td>
	<td class="td_left">
		<s:textfield  maxlength="11" onkeyup="restrictNumber(this,'1',11)" id="mobile" name="phone" cssClass="zc_input02" size="20"></s:textfield>
		<input type="button" onclick="getCheckCode();" value="获取验证码" class="buttonClass_1">
	</td>
	<tr><td class="title_center">
		手机验证码:
	</td>
	<td class="td_left">
		<s:textfield  verify="手机验证码|NotNull" onblur="checkMobileNum()" maxlength="4" onkeyup="restrictNumber(this,'1',4)" id="phoneCheckNum" name="phoneCheckNum" cssClass="zc_input02" size="20"></s:textfield>
	</td>
	</tr>
	<tr><td colspan="2" class="td_left" style="height:30px;text-align:center;"><input type="button" onclick="doSubmit()" value="找回密码" class="buttonClass"> &nbsp;&nbsp;
			<input type="button" onclick="ownerDialog.close();" value="关闭" class="buttonClass"> 
			</s:if>
	</table>
	</s:form>
	<div id="overDiv" style="display:none;" ></div>
</body>
</html>
