<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查看信息</title>
<link href="/css/login-box.css" rel="stylesheet" type="text/css" />
<link href="/css/UxStyle.css" rel="stylesheet" type="text/css" />
<link href="/css/main.css" type="text/css" rel="stylesheet" />
<link href="/css/initialize.css" type="text/css" rel="stylesheet" />
<script src="/js/Scripts/jquery.min.js" type="text/javascript"></script>
<script src="/js/businessJS/onlineApply.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function(){
	changeCardIn("forms");
});
</script>
</head>
<body >
<s:hidden name="approveinstance.guid" id="appInstanceGuid"></s:hidden>	
<s:hidden name="approveinstance.approveguid" id="approveItemGUID"></s:hidden>
<s:hidden name="state" value="1"></s:hidden>
		<div id="Portal_Content">
<div class="BS_dotop1">&nbsp;</div>
  <div class="BS_domiddle">
  <div class="BS_domiddlein">
			<!--depmiddle start-->
			<div class="BS_depm">
			<div style="text-align:left;padding-left:20px;">
				<div class="BS_title2">
					<span class="BS_titletxt"><s:property value="approveItem.approveitemname"/>在线申报</span>
				</div> 
				<div class="BS_title6" style="color:#b2b2b2">申请受理机关：<s:property value="approveItem.acceptjiguan"/></div>
				<div class="noborder" style="text-align:right;font-size:16px;color:#b2b2b2;padding-right:20px;">
					<s:if test="bureauList.size>1">
					<div style="float:left">
					<s:select cssClass="loginInput" list="bureauList" listKey="code" listValue="value" onchange="findCommunity()" name="departGUID" id="departGUID"  value="departGUID"></s:select>
						<div id="communityDiv" style="float:right"></div>
					</div>
					</s:if>
					<s:else>
					<s:hidden name="departGUID" id="departGUID"></s:hidden>
					<s:hidden name="communityGUID" id="communityGUID"></s:hidden>
					</s:else>
					申请人：<s:property value="#session.loginUser.personUser.true_name"/>
							<s:property value="#session.loginUser.companyUser.ename"/>
				</div>
			</div>
				<div class="clean"></div>
				<!--材料开始 start-->
				<table cellpadding="0" cellspacing="0" border="0" width="98%">
					<tr><td width="150px;"><a class="tdCardOn" id="card0" href="javascript:changeCardIn('forms');"><span>申请表</span></a></td>
						<td width="150px;"><a class="tdCardOff" id="card1" href="javascript:changeCardIn('fileTable');"><span>申请材料</span></a></td>
						<td>&nbsp;</td>
						</tr>
				</table>
				<!-- 模板下载 -->
				<div id="countMainInfo1" style="display:">
				</div>
				<div id="countMainInfo2" style="display:none">
					
				</div>
																			
					<!--材料结束 end-->
				<div class="clean"></div>
			</div>
			<!--depmiddle end-->
		</div>
		<!--middlein end-->
		</div>
		<div class="BS_dobottom">&nbsp;</div>
						</div>

</body>
</html>