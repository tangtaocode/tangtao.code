<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="net.risesoft.utils.base.DateFormatUtil"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>广东省网上办事大厅深圳市龙岗区住房与建设局-办事跟踪</title>
<link href="/css/login-box.css" rel="stylesheet" type="text/css" />
<link href="/css/UxStyle.css" rel="stylesheet" type="text/css" />
<link href="/css/main.css" type="text/css" rel="stylesheet" />
<link href="/css/initialize.css" type="text/css" rel="stylesheet" />
<script src="/js/Scripts/jquery.min.js" type="text/javascript"></script>
<script src="/js/zDialog/risenetDialog.js" type="text/javascript"></script>
<script src="/js/businessJS/onlineApply.js" type="text/javascript"></script>
<script src="/js/userService.js" type="text/javascript"></script>
<script type="text/javascript" src="/js/validation.js"></script>
<script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>


<style type="text/css">
body{
	font-family: "Microsoft YaHei", "΢微软雅黑", "Segoe UI", Arial;
}
table{
	line-height:25px;
	font-size:15px;
}
</style>
<script type="text/javascript">
	
	$(document).ready(function(){
		changeCardInfo('apply',$("#approveItemGUID").val());
	});
</script>
</head>
<body style="background-color:#fff;background-image:url('/images/govpublic/background.jpg');background-position:center top;background-repeat:repeat-y; background-attachment:fixed;">
    <div id="pagecontainer" style="width:1000px;margin:0 auto">
        <div id="we7layout_13452862291999" class="sf_cols">
 <div style="float: none; width: 100%; margin: 0; ;" class="sf_colsOut"><div class="sf_colsIn ">
<s:form id="appInstanceForm">
<s:hidden name="approveinstance.guid" id="appInstanceGuid"></s:hidden>	
<s:hidden name="approveinstance.approveguid" id="approveItemGUID"></s:hidden>
<s:hidden name="approveinstance.status" id="status" value="未受理"></s:hidden>
<s:hidden name="approveinstance.issubmit" id="issubmit" value="1"></s:hidden>
<s:hidden name="approveinstance.hfstate" value="0"></s:hidden>
<s:hidden name="approveinstance.deptguid" id="deptguid"></s:hidden>
<s:hidden name="approveinstance.bureauguid" id="bureauguid"></s:hidden>
<s:hidden name="approveinstance.formname" id="formname"></s:hidden>
<s:hidden name="#session.loginUser.usertype" id="loginUser"></s:hidden>
<s:hidden name="approveinstance.dataform" id="dataform"></s:hidden>
<%--
<s:hidden name="approveinstance.xmmc" id="xmmc"></s:hidden>
<s:hidden name="approveinstance.xmbh" id="xmbh"></s:hidden>
<s:hidden name="approveinstance.gcmc" id="gcmc"></s:hidden>
<s:hidden name="approveinstance.gcbh" id="gcbh"></s:hidden>
<s:hidden name="approveinstance.jsdw" id="jsdw"></s:hidden> --%>
<s:hidden name="approveinstance.sjtybh" id="sjtybh"></s:hidden>

</s:form>
<div class="SD_Top ">
    <jsp:include page="/WEB-INF/page/public/topMenu.jsp">
<jsp:param name="menu" value="3"/>
</jsp:include>
</div>
</div></div>
<div style= "clear:both;height:0;font-size:1px;"></div></div><div id="we7layout_134528622578068" class="sf_cols">
 <div style="float: none; width: 100%; margin: 0; ;" class="sf_colsOut"><div class="sf_colsIn ">
<div class="GovOpenIndex ">
    <table border="0" cellpadding="0" cellspacing="0" style="width: 1000px; heigth: 100%" align="center">
			<tr>
				<td valign="top" align="center" height="155px"><!-- begin: 顶部面板 -->
				<div id="Portal_Banner">
	<table border="0" cellpadding="0" cellspacing="20px" style="width: 100%;">
		<tr>
			<td align="left">
				<span style="font-size: 18pt;">在线申报</span>
			</td>
			<td align="right">
				&nbsp;
			</td>
		</tr>
	</table></div>
						<!-- end: 标题面板 --> <!-- begin: 内容面板 -->
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
				<div class="BS_title6" style="color:#b2b2b2">申请受理机关：<s:property value="approveItem.acceptjiguan"/>&nbsp;&nbsp;&nbsp;&nbsp;
					</div>
				<div class="BS_titleass r" style="font-size:16px;color:#b2b2b2;padding-right:20px;padding-top:50px;">申请时间:<%=DateFormatUtil.parseToString(new Date()) %>  </div>
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
					<tr><td width="150px;"><a class="tdCardOn" id="card0" href="javascript:changeCardInfo('apply');changeC(0);"><span>在线申报</span></a></td>
						<td width="150px;"><a class="tdCardOff" id="card1" href="javascript:changeCardInfo('fileTableTemp');changeC(1);"><span>模板下载</span></a></td>
						<td width="150px;"><a class="tdCardOff" id="card2" href="javascript:changeCardInfo('appGuideInfo');changeC(2);"><span>办事指南</span></a></td>
						<td width="150px;"><a class="tdCardOff" href="/download/shiyongzhinan.doc" target="_blank" title="深圳市住房和建设局行政审批事项网上申报使用指南V1.0"><span>使用指南</span></a></td>
						<td>&nbsp;</td>
						</tr>
				</table>
				<!-- 模板下载 -->
				<div id="countMainInfo1" style="display:">
					<!-- 用户基本信息 start -->
					<jsp:include page="/WEB-INF/page/onlineservice/appApply/Apply.jsp"></jsp:include>
					<!-- 用户基本信息 end -->
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
						<!-- end: 内容面板 --></td>
					</tr>
					<tr>
						<td align="left"></td>
					</tr>
		
				</table>
</div>
</div></div>
<div style= "clear:both;height:0;font-size:1px;"></div></div><div id="we7layout_134528622069339" class="sf_cols">
 <div style="float: none; width: 100%; margin: 0; ;" class="sf_colsOut"><div class="sf_colsIn ">

<jsp:include page="/WEB-INF/page/public/bottom.jsp"></jsp:include>    
</div></div>
<div style= "clear:both;height:0;font-size:1px;"></div></div>
    </div>
</body>

</html>
