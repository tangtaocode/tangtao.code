<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>
<body>
		<table border="0" cellpadding="0" cellspacing="0" style="width: 100%;">
			<tr>
				<td align="left" style="padding: 20px;">
				<table border="0" cellpadding="0" cellspacing="0" style="width: 100%;">
					<tr>
						<td style="font-size: 18pt; color: #858585">欢迎您来<span style="color:#ffc125">深圳市住房与建设局</span>投资</td>
						
						<td align="right">&nbsp;</td>
					</tr>
				</table>
			</td>
			</tr>
		</table>
		<table border="0" cellpadding="0" cellspacing="0" style="width: 100%;">
		 <tr>
			<td style="text-align:center;">
	<table border="0" cellpadding="0" cellspacing="0" style="width: 99%;">
		<tr>
			<td style="text-align:left;">
				<table id="investTabTable" border="0" cellpadding="0" cellspacing="0" style="width: 300px;">
					<tr>
						<td align="center" width="150px">
							<div class="investTabSelected"  onclick="tabSelected(this,'investTabTable','investTab','investTabSelected');qyInvestStepChange('qyInvestStep1');" >
								<div style="padding-top:8px">备案流程</div>
							</div>
						</td>
						<td align="center" width="150px">
							<div class="investTab" onclick="tabSelected(this,'investTabTable','investTab','investTabSelected');qyInvestStepChange('qyInvestStep2');" >
								<div style="padding-top:8px">办事指南</div>
							</div>
						</td>
					</tr>
				</table>
			</td>
			<td>
				<!--<span style="float:right;padding-right:20px;"><a class="buttonOnline" href="/onlineService/initOnlineApply/approveItemGUID/<s:property value="approveItem.itemid"/>.html">在线申报</a></span>
			--></td>
			</tr>
			<tr>
				<td width="100%" valign="top" align="center" colspan="2">
				<div id="qyInvestStep">
				<!--阶段一-->
				<div id="qyInvestStep1">
	<table border="0" cellpadding="0" cellspacing="0px" style="width: 100%;">
		<tr>
			<td align="left" style="padding-left:70px;padding-top:20px">
				<img src="/images/investment/tab_invest_arrow.png" width="23px" height="10px" border="0" />
			</td>
		</tr>
		<tr>
			<td>
				<img src="/images/investment/line_invest_split.png" width="956px" height="22px" border="0" />
			</td>
		</tr>
		<tr>
			<td style="text-align:center;">
			<img src="/images/investment/flow/qytz.png" border="0" usemap="#zftz1" />
			
			</td>
		</tr>
	</table></div>
	<!--阶段二-->
				<div id="qyInvestStep2" style="display: none">

	<table border="0" cellpadding="0" cellspacing="0px" style="width: 100%;">
		<tr> 
			<td align="left" style="padding-left:225px;padding-top:20px">
				<img src="/images/investment/tab_invest_arrow.png" width="23px" height="10px" border="0" /> 
			</td>
		</tr>
		<tr>
			<td>
				<img src="/images/investment/line_invest_split.png" width="956px" height="22px" border="0" />
			</td>
		</tr>
		<tr>
			<td style="text-align:center;">
				<jsp:include page="/WEB-INF/page/investment/appGuideInfo.jsp"></jsp:include>
			</td>
		</tr>
	</table></div>
	
				</div>
				</td>
			</tr>
		</table>
</body>
</html>