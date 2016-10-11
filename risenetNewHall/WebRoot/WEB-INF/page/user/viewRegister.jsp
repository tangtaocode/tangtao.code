<%@ page contentType="text/html; charset=UTF-8" %>
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
<script type="text/javascript">
$(document).ready(function(){
	openServiceItems();
});
</script>
</head>

<body>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="table_line">
<tr><th colspan="2" class="title title_center" style="height:30px;text-align:center;">账号信息</th></tr>
	<tr><td class="title_center" width="20%">
		用户登录名称:
	</td>
	<td class="td_left" width="80%">
		<s:property value="userInfo.loginname"/>
	</td>
	</tr>
	<tr><td class="title_center">系统类型:</td>
	<td class="td_left">
	<s:if test="userInfo.sxlx==1">行政服务事项</s:if>
	<s:if test="userInfo.sxlx==2">产业扶持专项资金申请</s:if>
	<s:if test="userInfo.sxlx==3">社会建设民生创新专项资金申请</s:if>
	</td>
	</tr>
	<tr><td class="title_center">用户类型:</td>
	<td class="td_left" id="approveItem">
	<s:if test="userInfo.usertype==1">个人</s:if>
	<s:if test="userInfo.usertype==2">企业</s:if>
	<s:if test="userInfo.usertype==3">其它社会组织机构</s:if>
	<s:if test="userInfo.usertype==4">行政机关</s:if>
	<s:if test="userInfo.usertype==5">事业单位</s:if>
	<s:if test="userInfo.usertype==6">社会组织</s:if>
	</td>
	</tr>
	<tr><td class="title_center">
		证件类型:
	</td>
	<td class="td_left">
	<s:if test="userInfo.usertype==1">身份证号</s:if>
	<s:if test="userInfo.usertype==2">护照号码</s:if>
	<s:if test="userInfo.usertype==3">组织机构代码</s:if>
	</td>
	</tr>
	<tr><td class="title_center" id="cardTypeText">
		证件号码:
	</td>
	<td class="td_left">
	<s:property value="userInfo.cardid"/>
	</td>
	</tr>
	<tr><td class="title_center">
		手机号码:
	</td>
	<td class="td_left">
	<s:property value="userInfo.mobile"/>
	</td>
	</tr>
	</table>
</body>
</html>
