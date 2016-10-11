<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../share/taglib.jsp"%>
<html>
<head>
<title>深圳市龙岗区住房与建设局产业转型升级专项资金网上申报</title>

<link href="/css/appGuide.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<s:form action="/bizApply/shareFinanceSituation">
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="table_leftop">
		<s:iterator value="shareFinanceList" status="pl">
			<td class="table_td_center">
			<input type="radio" name="shareFinance"  id="<s:property value="guid"/>" value="<s:property value="guid"/>"><s:property value="audittime"/> </td>
			<s:if test="(#pl.index+1)%2==0"><tr></s:if>
		</s:iterator>
		</table>
</s:form>
</body>
</html>
