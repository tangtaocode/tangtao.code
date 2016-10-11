<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="net.risesoft.util.ValidatorUtil"%>
<%@ include file="../share/taglib.jsp"%>
<html>
<head>
 <title>深圳市龙岗区住房与建设局网上行政服务大厅</title>
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
	<script type="text/javascript">
		$(document).ready(function(){
			openServiceItems();
		});
	</script>
	<script type="text/javascript">
		function do_jpassword(){
			var obj  = document.getElementById("j_password");
			obj.value=obj.value.replace(/./g,'*');	
			var pass=document.getElementById("password");
			pass.value = pass.value.substring(0,obj.value.length);
		}
		function hiddenPass(e){
			var keynum;
			var keychar;
			var pass=document.getElementById("password");
			var j_pass=document.getElementById("j_password");
			if(window.event) // IE
			{
				keynum = e.keyCode;
			}
			else if(e.which) // Netscape/Firefox/Opera
			{
				keynum = e.which;
			}
			keychar = String.fromCharCode(keynum);
			pass.value = pass.value+keychar;
		}
		
	</script>
</head>

<body>
<s:form method="post" id="submitForm">
<s:hidden name="method" value="add"></s:hidden>
<s:hidden name="userInfo.guid"></s:hidden>
<s:hidden name="userInfo.state"></s:hidden>
<s:hidden name="userInfo.creattime"></s:hidden>
	 <div id="registbefore" align=center style="display:none">
<table width="947px" border="0" align="center" cellpadding="0" cellspacing="0" class="table_line">
<tr><th class="top2"></th></tr>
    <tr>
    <td class="title" style="height:34px;text-align:center;">
    <b>服务条款和声明</b>
    </td>
    </tr>
    <tr>
    <td class="td_left" align=left>
<font color=#000000>
     <b>继续注册前请先阅读注册协议</b><p>
欢迎您加入本站点参加交流和讨论，本站点为公共论坛，为维护网上公共秩序和社会稳定，请您自觉遵守以下条款：<br/><br/>
一、不得利用本站危害国家安全、泄露国家秘密，不得侵犯国家社会集体的和公民的合法权益，不得利用本站制作、复制和传播下列信息： <br/>
　（一）煽动抗拒、破坏宪法和法律、行政法规实施的；<br/>
　（二）煽动颠覆国家政权，推翻社会主义制度的；<br/>
　（三）煽动分裂国家、破坏国家统一的；<br/>
　（四）煽动民族仇恨、民族歧视，破坏民族团结的；<br/>
　（五）捏造或者歪曲事实，散布谣言，扰乱社会秩序的；<br/>
　（六）宣扬封建迷信、淫秽、色情、赌博、暴力、凶杀、恐怖、教唆犯罪的；<br/>
　（七）公然侮辱他人或者捏造事实诽谤他人的，或者进行其他恶意攻击的；<br/>
　（八）损害国家机关信誉的；<br/>
　（九）其他违反宪法和法律行政法规的；<br/>
　（十）进行商业广告行为的。<br/>
二、互相尊重，对自己的言论和行为负责。 <br/>
<p/></font>
    </td>
    </tr>
    <tr>
    <td align=center>
    <center><input type="button" id="regButton" class="buttonClass_1" onclick="showRegistDiv();"  value="我同意" /></center>
    </td></tr></table>
</div>

<div id="registNext">
<table width="947px" border="0" align="center" cellpadding="0" cellspacing="0">
<tr> <td class="title" style="height:34px;text-align:center;">
    <b>用户注册信息</b>
    </td></tr>
<tr><td class="td_left1">
<table width="945px" border="0" align="center" cellpadding="0" cellspacing="0" id="regUserTab">
<tr><th>
		用户登录名称：
	</th>
	<td>
		<s:textfield  verify="用户登录名|NotNull"  id="loginname" name="userInfo.loginname" cssClass="zc_input02" size="20" maxlength="18" onblur="checkUserName('2');"></s:textfield>
		<span style="color:red">*</span><span id="userMessage"></span>&nbsp;&nbsp;&nbsp;<span class="messinfo">3~18个字符，包括字母、数字、下划线</span>
	</td>
	</tr>
	<tr><th>
		密  码：
	</th>
	<td>
		<s:hidden  id="rpassword" name="rpassword" /> 
		<s:hidden  id="password" name="userInfo.password" /> 
		<input type="text" id="j_password" name="j_password"  class="zc_input02" size="20" verify="密码|NotNull" onblur="checkPwdIsNull()"
			onkeypress="hiddenPass(event);" onkeyup="do_jpassword();"
			oncopy="return false" onpaste="return false" value=""/>
			    									
		<!--<s:password maxlength="16" verify="密码|NotNull"  cssClass="zc_input02" size="20" name="userInfo.password" id="password" onblur="checkPwdIsNull()"></s:password>-->
		
		<span style="color:red">*</span><span id="passMessage"></span>&nbsp;&nbsp;&nbsp;<span class="messinfo">6～16个字符（字母、数字、特殊符号）,区分大小写</span>
	</td>
	<tr><th>
		再次输入密码：
	</th>
	<td>
		<s:password maxlength="16" verify="重复密码|NotNull" cssClass="zc_input02" size="20" id="passwordRepeat" onblur="checkPasswd();"></s:password>
		<span style="color:red">*</span><span id="rpassMessage"></span>&nbsp;&nbsp;&nbsp;<span class="messinfo">两次密码输入必须一致</span>
	</td>
	</tr>
	<tr><th>用户类型：</th>
	<td>
		<s:radio list="#{'4':'行政机关','5':'事业单位','7':'国有企业','2':'企业','6':'社会组织','1':'个人','3':'其它'}" onclick="changeCardByUser(this)" id="userType" name="userInfo.usertype" cssClass="zc_input02"></s:radio><span style="color:red">*</span>
	</td>
	</tr>
	<tr><th>
		证件类型：
	</th>
	<td>
		<s:radio list="#{'1':'身份证号','2':'护照号码','3':'组织机构代码'}" onclick="chageCardText(this)" cssClass="zc_input02" name="userInfo.cardtype"></s:radio>
		<span style="color:red">*</span>
	</td>
	</tr>
	
	<tr><th id="nameTypeText">
		企业/单位名称：
	</th>
	<td>
	<s:textfield   id="comuser" name="userInfo.comuser" cssClass="zc_input02" size="20" onblur="checkNameType();"></s:textfield>
		<span style="color:red">*</span><span id="comuserMessage"></span>
	</td>
	</tr>
	
	
	<tr><th id="cardTypeText">
		证件号码：
	</th>
	<td>
	<s:textfield  verify="证件号码|NotNull"  onblur="checkCard()" id="cardid" name="userInfo.cardid" cssClass="zc_input02" size="20"></s:textfield>
		<span style="color:red">*</span><span id="cardidMessage"></span>&nbsp;&nbsp;&nbsp;<span class="messinfo">绑定当前账号，不可修改</span>
	</td>
	</tr>
	<tr><th>
		手机号码：
	</th>
	<td>
	<s:textfield  verify="手机号码|NotNull" maxlength="11" onkeyup="restrictNumber(this,'1',11)" id="mobile" name="userInfo.mobile" cssClass="zc_input02" size="20"></s:textfield>
		<span style="color:red">*</span> 
		<input type="button" onclick="getCheckCode();" value="获取验证码" class="buttonClass_1">&nbsp;&nbsp;&nbsp;
		<span class="messinfo">号码绑定当前账号，用于验证身份、找回密码</span>
	</td>
	
	</tr>
	<tr><th>
		验证码：
	</th>
	<td>
	<s:textfield  verify="手机验证码|NotNull" maxlength="6" onkeyup="restrictNumber(this,'1',6)" onblur="checkMobileNum()" id="phoneCheckNum" name="phoneCheckNum" cssClass="zc_input02" size="20"></s:textfield>
		<span style="color:red">*</span><span class="messinfo">系统以短信形式发送至对应手机号码</span>
	</td>
	</tr>
	
	<tr>
	<th>
		服务条款：
	</th>
	<td>
		<s:checkbox name="serviceCheckBox" cssClass="zc_input02"></s:checkbox>我已阅读并接受<a href="javascript:openServiceItems();">"服务条款"</a>
	</td>
	
	</tr>
	<tr><td colspan="2" style="height:30px;text-align:center;">
	<input type="button" onclick="submitUserInfo()" id="submitUser" value="注 册" class="buttonClass_1"/> &nbsp;&nbsp;
			<input type="reset" value="重 置" class="buttonClass_1"/> &nbsp;&nbsp;
			</td></tr>
	</table>
</td></tr>
</table>
</div>
</s:form>
</body>
</html>
