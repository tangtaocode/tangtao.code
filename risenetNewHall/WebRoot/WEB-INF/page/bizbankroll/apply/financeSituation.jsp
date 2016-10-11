<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../share/taglib.jsp"%>
<html>
<head>
<title>深圳市龙岗区住房与建设局产业转型升级专项资金网上申报</title>

<link href="/css/appGuide.css" rel="stylesheet" type="text/css"/>
<script src="/js/Scripts/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript" src="/js/zDialog/risenetDialog.js"></script>
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript" src="/dwr/interface/BizApplyJs.js"></script>
<script type="text/javascript" src="/js/technology.js"></script>
<script type="text/javascript" src="/js/calendar.js"></script>
<script type="text/javascript" src="/js/validation.js"></script>
<style type="text/css">
body{ margin: 1;
	  padding: 1; 
	  }

</style>
</head>
<body>
<form action="/bizApply/saveFinanceSituation.html" method="post" id="FinanceFormId">
		<table width="1175" border="0" align="center" cellpadding="0" cellspacing="0" class="table_leftop">
		<tr><td class="top" > </td> </tr>
		<tr><th class="title" style="height:30px;">单位近三年财务状况</th></tr>
		<tr><td >（以审计后的数据为准，未经审计的应注明，单位：万元）
		<s:hidden name="appGUID"></s:hidden>
		<s:hidden name="financeList[0].guid" id="financeguid1"></s:hidden>
		<s:hidden name="financeList[1].guid" id="financeguid2"></s:hidden>
		<s:hidden name="financeList[2].guid" id="financeguid3"></s:hidden>
		<s:hidden name="financeList[0].financeindex" id="financeindex1"></s:hidden>
		<s:hidden name="financeList[1].financeindex" id="financeindex2"></s:hidden>
		<s:hidden name="financeList[2].financeindex" id="financeindex3"></s:hidden>
		</td></tr>
		<tr><td>
		<div style="overflow: scroll;height:460px;width:1190px;scrollbar-face-color:#E0F2FF;">
		<table width="1175" border="0" align="center" cellpadding="0" cellspacing="0" class="table_leftop">
		<TR>
			<TD  class="table_td_center" colspan="2" width="15%" nowrap="nowrap">指标/时间</TD>
		    <TD class="table_td_center" width="28%" nowrap="nowrap">前年<s:textfield type="text"  readonly="readonly" onclick="calendar.show(this);" id="audittime1" name="financeList[0].audittime" cssClass="zc_input02" size="10"></s:textfield>审计<a href="javaScript:getShareFinance('1')" title="点击选择"><img style="border:0" src="/images/page/add.png"></a></TD>
		    <TD  class="table_td_center" width="28%" nowrap="nowrap">上一年<s:textfield type="text"  readonly="readonly" onclick="calendar.show(this);" id="audittime2" name="financeList[1].audittime" cssClass="zc_input02" size="10"></s:textfield>审计<a href="javaScript:getShareFinance('2')" title="点击选择"><img style="border:0" src="/images/page/add.png"></a></TD>
		    <TD  class="table_td_center" width="28%" nowrap="nowrap">本年度（至申报日止）<s:textfield type="text"  readonly="readonly" onclick="calendar.show(this);" id="audittime3" name="financeList[2].audittime" cssClass="zc_input02" size="10"></s:textfield>审计<a href="javaScript:getShareFinance('3')" title="点击选择"><img style="border:0" src="/images/page/add.png"></a></TD>
		</TR>
		
		<TR>
			<TD  class="title_center" colspan="2" nowrap="nowrap">总收入：</TD>
			<TD  class="td_left"><s:textfield  verify="前年总收入|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="generalincome1" name="financeList[0].generalincome" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="上一年总收入|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="generalincome2" name="financeList[1].generalincome" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="本年度总收入|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="generalincome3" name="financeList[2].generalincome" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
		</TR>
		<TR>
			<TD  class="title_center" colspan="2" nowrap="nowrap">总收入同期增长率（%）：</TD>
			<TD  class="td_left"><s:textfield  verify="前年总收入同期增长率|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="7" id="generalrise1" name="financeList[0].generalrise" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="上一年总收入同期增长率|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="7"  id="generalrise2" name="financeList[1].generalrise" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="本年度总收入同期增长率|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="7" id="generalrise3" name="financeList[2].generalrise" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
		</TR>
		<tr>
			<TD  class="title_center" rowspan="2" nowrap="nowrap">其中</TD>
			<TD  class="title_center" nowrap="nowrap">主营业务收入</TD>
			<TD  class="td_left"><s:textfield  verify="前年主营业务收入|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="businessincome1" name="financeList[0].businessincome" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="上一年主营业务收入|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="businessincome2" name="financeList[1].businessincome" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="本年度主营业务收入|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="businessincome3" name="financeList[2].businessincome" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
		</tr>
		<tr>
			<TD  class="title_center" nowrap="nowrap">软件产品销售收入（软件企业填）</TD>
			<TD  class="td_left"><s:textfield  verify="前年软件产品销售收入|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="softwareincome1" name="financeList[0].softwareincome" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="上一年软件产品销售收入|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="softwareincome2" name="financeList[1].softwareincome" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="本年度软件产品销售收入|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="softwareincome3" name="financeList[2].softwareincome" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
		</tr>
		<TR>
			<TD  class="title_center" colspan="2" nowrap="nowrap">主营业务收入增长率（%）</TD>
			<TD  class="td_left"><s:textfield  verify="前年主营业务收入增长率|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="7" id="businessrise1" name="financeList[0].businessrise" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="上一年主营业务收入增长率|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="7" id="businessrise2" name="financeList[1].businessrise" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="本年度主营业务收入增长率|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="7" id="businessrise3" name="financeList[2].businessrise" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
		</TR>
		<TR>
			<TD  class="title_center" colspan="2" nowrap="nowrap">净利润</TD>
			<TD  class="td_left"><s:textfield  verify="前年净利润|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="clearprofit1" name="financeList[0].clearprofit" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="上一年净利润|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10"  id="clearprofit2" name="financeList[1].clearprofit" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="本年度净利润|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10"  id="clearprofit3" name="financeList[2].clearprofit" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
		</TR>
		<TR>
			<TD  class="title_center" colspan="2" nowrap="nowrap">其中主营业务利润</TD>
			<TD  class="td_left"><s:textfield  verify="前年其中主营业务利润|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="businessprofit1" name="financeList[0].businessprofit" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="上一年其中主营业务利润|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="businessprofit2" name="financeList[1].businessprofit" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="本年度其中主营业务利润|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="businessprofit3" name="financeList[2].businessprofit" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
		</TR>
		<TR>
			<TD  class="title_center" colspan="2" nowrap="nowrap">主营业务利润增长率（%）</TD>
			<TD  class="td_left"><s:textfield  verify="前年主营业务利润增长率|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="7" id="profitrise1" name="financeList[0].profitrise" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="上一年主营业务利润增长率|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="7"  id="profitrise2" name="financeList[1].profitrise" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="本年度主营业务利润增长率|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="7"  id="profitrise3" name="financeList[2].profitrise" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
		</TR>
		<TR>
			<TD  class="title_center" colspan="2" nowrap="nowrap">产品销售总额</TD>
			<TD  class="td_left"><s:textfield  verify="前年产品销售总额|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="grosssales1" name="financeList[0].grosssales" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="上一年产品销售总额|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="grosssales2" name="financeList[1].grosssales" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="本年度产品销售总额|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="grosssales3" name="financeList[2].grosssales" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
		</TR>
		<TR>
			<TD  class="title_center" colspan="2" nowrap="nowrap">经营活动现金流量净额</TD>
			<TD  class="td_left"><s:textfield  verify="前年经营活动现金流量净额|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="business1" name="financeList[0].business" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="上一年经营活动现金流量净额|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="business2" name="financeList[1].business" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="本年度经营活动现金流量净额|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="business3" name="financeList[2].business" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
		</TR>
		<TR>
			<TD  class="title_center" colspan="2" nowrap="nowrap">总现金流量净额</TD>
			<TD  class="td_left"><s:textfield  verify="前年总现金流量净额|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="cash1" name="financeList[0].cash" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="上一年总现金流量净额|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="cash2" name="financeList[1].cash" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="本年度总现金流量净额|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="cash3" name="financeList[2].cash" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
		</TR>
		<TR>
			<TD  class="title_center" colspan="2" nowrap="nowrap">销售商品、提供劳务收到的现金</TD>
			<TD  class="td_left"><s:textfield  verify="前年销售商品、提供劳务收到的现金|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="sell1" name="financeList[0].sell" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="上一年销售商品、提供劳务收到的现金|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="sell2" name="financeList[1].sell" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="本年度销售商品、提供劳务收到的现金|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="sell3" name="financeList[2].sell" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
		</TR>
		<TR>
			<TD  class="title_center" colspan="2" nowrap="nowrap">经营活动现金流入总额</TD>
			<TD  class="td_left"><s:textfield  verify="前年经营活动现金流入总额|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="businessinflow1" name="financeList[0].businessinflow" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="上一年经营活动现金流入总额|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="businessinflow2" name="financeList[1].businessinflow" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="本年度经营活动现金流入总额|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="businessinflow3" name="financeList[2].businessinflow" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
		</TR>
		<TR>
			<TD  class="title_center" colspan="2" nowrap="nowrap">购进商品、接受劳务付出的现金</TD>
			<TD  class="td_left"><s:textfield  verify="前年购进商品、接受劳务付出的现金|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="commodity1" name="financeList[0].commodity" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="上一年购进商品、接受劳务付出的现金|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="commodity2" name="financeList[1].commodity" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="本年度购进商品、接受劳务付出的现金|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="commodity3" name="financeList[2].commodity" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
		</TR>
		<TR>
			<TD  class="title_center" colspan="2" nowrap="nowrap">经营活动现金流出总额</TD>
			<TD  class="td_left"><s:textfield  verify="前年经营活动现金流出总额|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="businessoutflow1" name="financeList[0].businessoutflow" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="上一年经营活动现金流出总额|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="businessoutflow2" name="financeList[1].businessoutflow" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="本年度经营活动现金流出总额|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="businessoutflow3" name="financeList[2].businessoutflow" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
		</TR>
		<TR>
			<TD  class="title_center" colspan="2" nowrap="nowrap">纳税总额</TD>
			<TD  class="td_left"><s:textfield  verify="前年纳税总额|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="paytaxes1" name="financeList[0].paytaxes" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="上一年纳税总额|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="paytaxes2" name="financeList[1].paytaxes" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="本年度纳税总额|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="paytaxes3" name="financeList[2].paytaxes" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
		</TR>
		<TR>
			<TD  class="title_center" colspan="2" nowrap="nowrap">纳税总额同期增长率（%）</TD>
			<TD  class="td_left"><s:textfield  verify="前年纳税总额同期增长率|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="7" id="taxesrise1" name="financeList[0].taxesrise" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="上一年纳税总额同期增长率|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="7" id="taxesrise2" name="financeList[1].taxesrise" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="本年度纳税总额同期增长率|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="7" id="taxesrise3" name="financeList[2].taxesrise" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
		</TR>
		<TR>
			<TD  class="title_center" rowspan="11" nowrap="nowrap">其中</TD>
			<TD  class="title_center"  nowrap="nowrap">企业所得税</TD>
			<TD  class="td_left"><s:textfield  verify="前年企业所得税|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="incometax1" name="financeList[0].incometax" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="上一年企业所得税|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="incometax2" name="financeList[1].incometax" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="本年度企业所得税|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="incometax3" name="financeList[2].incometax" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
		</TR>
		<TR>
			<TD  class="title_center"  nowrap="nowrap">增值税</TD>
			<TD  class="td_left"><s:textfield  verify="前年增值税|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="appreciationduty1" name="financeList[0].appreciationduty" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="上一年增值税|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="appreciationduty2" name="financeList[1].appreciationduty" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="本年度增值税|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="appreciationduty3" name="financeList[2].appreciationduty" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
		</TR>
		<TR>
			<TD  class="title_center"  nowrap="nowrap">营业税</TD>
			<TD  class="td_left"><s:textfield  verify="前年营业税|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="businesstax1" name="financeList[0].businesstax" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="上一年营业税|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="businesstax2" name="financeList[1].businesstax" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="本年度营业税|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="businesstax3" name="financeList[2].businesstax" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
		</TR>
		<TR>
			<TD  class="title_center"  nowrap="nowrap">个人所得税</TD>
			<TD  class="td_left"><s:textfield  verify="前年个人所得税|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="individualtax1" name="financeList[0].individualtax" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="上一年个人所得税|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10"  id="individualtax2" name="financeList[1].individualtax" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="本年度个人所得税|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10"  id="individualtax3" name="financeList[2].individualtax" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
		</TR>
		<TR>
			<TD  class="title_center"  nowrap="nowrap">建设税</TD>
			<TD  class="td_left"><s:textfield  verify="前年建设税|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="constructiontax1" name="financeList[0].constructiontax" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="上一年建设税|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="constructiontax2" name="financeList[1].constructiontax" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="本年度建设税|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="constructiontax3" name="financeList[2].constructiontax" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
		</TR>
		<TR>
			<TD  class="title_center"  nowrap="nowrap">教育费附加收入</TD>
			<TD  class="td_left"><s:textfield  verify="前年教育费附加收入|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="education1" name="financeList[0].education" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="上一年教育费附加收入|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="education2" name="financeList[1].education" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="本年度教育费附加收入|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="education3" name="financeList[2].education" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
		</TR>
		<TR>
			<TD  class="title_center"  nowrap="nowrap">印花税</TD>
			<TD  class="td_left"><s:textfield  verify="前年印花税|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="stampduty1" name="financeList[0].stampduty" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="上一年印花税|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="stampduty2" name="financeList[1].stampduty" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="本年度印花税|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="stampduty3" name="financeList[2].stampduty" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
		</TR>
		<TR>
			<TD  class="title_center"  nowrap="nowrap">房产税</TD>
			<TD  class="td_left"><s:textfield  verify="前年房产税|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="buildingtax1" name="financeList[0].buildingtax" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="上一年房产税|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="buildingtax2" name="financeList[1].buildingtax" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="本年度房产税|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="buildingtax3" name="financeList[2].buildingtax" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
		</TR>
		<TR>
			<TD  class="title_center"  nowrap="nowrap">城镇土地使用税</TD>
			<TD  class="td_left"><s:textfield  verify="前年城镇土地使用税|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10"  id="noyalty1" name="financeList[0].noyalty" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="上一年城镇土地使用税|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="noyalty2" name="financeList[1].noyalty" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="本年度城镇土地使用税|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="noyalty3" name="financeList[2].noyalty" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
		</TR>
		<TR>
			<TD  class="title_center"  nowrap="nowrap">文化事业建设费收入</TD>
			<TD  class="td_left"><s:textfield  verify="前年文化事业建设费收入|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="civilization1" name="financeList[0].civilization" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="上一年文化事业建设费收入|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="civilization2" name="financeList[1].civilization" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="本年度文化事业建设费收入|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="civilization3" name="financeList[2].civilization" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
		</TR>
		<TR>
			<TD  class="title_center"  nowrap="nowrap">地方教育附加收入</TD>
			<TD  class="td_left"><s:textfield  verify="前年地方教育附加收入|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="localeducation1" name="financeList[0].localeducation" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="上一年地方教育附加收入|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="localeducation2" name="financeList[1].localeducation" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="本年度地方教育附加收入|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="localeducation3" name="financeList[2].localeducation" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
		</TR>
		<TR>
			<TD  class="title_center" colspan="2" nowrap="nowrap">创汇总额（万美元）</TD>
			<TD  class="td_left"><s:textfield  verify="前年创汇总额|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="obtain1" name="financeList[0].obtain" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="上一年创汇总额|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="obtain2" name="financeList[1].obtain" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="本年度创汇总额|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="obtain3" name="financeList[2].obtain" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
		</TR>
		<TR>
			<TD  class="title_center" colspan="2" nowrap="nowrap">研发经费支出总额</TD>
			<TD  class="td_left"><s:textfield  verify="前年研发经费支出总额|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="funds1" name="financeList[0].funds" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="上一年研发经费支出总额|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="funds2" name="financeList[1].funds" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="本年度研发经费支出总额|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="funds3" name="financeList[2].funds" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
		</TR>
		<TR>
			<TD  class="title_center" colspan="2" nowrap="nowrap">总支出</TD>
			<TD  class="td_left"><s:textfield  verify="前年总支出|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="aggregate1" name="financeList[0].aggregate" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="上一年总支出|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="aggregate2" name="financeList[1].aggregate" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="本年度总支出|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="aggregate3" name="financeList[2].aggregate" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
		</TR>
		<TR>
			<TD  class="title_center" colspan="2" nowrap="nowrap">固定资产总额</TD>
			<TD  class="td_left"><s:textfield  verify="前年固定资产总额|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="fixedassets1" name="financeList[0].fixedassets" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="上一年固定资产总额|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="fixedassets2" name="financeList[1].fixedassets" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="本年度固定资产总额|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="fixedassets3" name="financeList[2].fixedassets" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
		</TR>
		<TR>
			<TD  class="title_center" colspan="2" nowrap="nowrap">总资产</TD>
			<TD  class="td_left"><s:textfield  verify="前年总资产|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="totalassets1" name="financeList[0].totalassets" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="上一年总资产|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="totalassets2" name="financeList[1].totalassets" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="本年度总资产|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="totalassets3" name="financeList[2].totalassets" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
		</TR>
		<TR>
			<TD  class="title_center" colspan="2" nowrap="nowrap">负债总额</TD>
			<TD  class="td_left"><s:textfield  verify="前年负债总额|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="grossliabilities1" name="financeList[0].grossliabilities" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="上一年负债总额|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="grossliabilities2" name="financeList[1].grossliabilities" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="本年度负债总额|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="grossliabilities3" name="financeList[2].grossliabilities" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
		</TR>
		<TR>
			<TD  class="title_center" colspan="2" nowrap="nowrap">资产负债率（%）</TD>
			<TD  class="td_left"><s:textfield  verify="前年资产负债率|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="7" id="liabilities1" name="financeList[0].liabilities" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="上一年资产负债率|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="7" id="liabilities2" name="financeList[1].liabilities" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="本年度资产负债率|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="7" id="liabilities3" name="financeList[2].liabilities" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
		</TR>
		<TR>
			<TD  class="title_center" colspan="2" nowrap="nowrap">产权比率（%）</TD>
			<TD  class="td_left"><s:textfield  verify="前年产权比率|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="7" id="propertyright1" name="financeList[0].propertyright" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="上一年产权比率|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="7" id="propertyright2" name="financeList[1].propertyright" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="td_left"><s:textfield  verify="本年度产权比率|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="7" id="propertyright3" name="financeList[2].propertyright" cssClass="zc_input02" size="20"></s:textfield>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
		</TR>
		
 	<tr>
		<td colspan="5">
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" id="initDateTableId" class="table_leftop">
		<tr>
		<s:iterator value="productList" status="pl">
		<td><input type="checkbox" onclick="selectProduct(this)" id="<s:property value="guid"/>checkBox" value="<s:property value="guid"/>"><s:property value="productname"/> </td>
		<s:if test="(#pl.index+1)%5==0"><tr></s:if>
		</s:iterator>
		</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="table_leftop" id="productTableId">
		<tr><th colspan="4" style="text-align:center;font-size: 20px;font-family:宋体">主要产品情况</th></tr>
		<tr id="productTrId">
		<td class="table_td_center" width="50%">主要产品名称</td>
		<td class="table_td_center" >是否具有自主知识产权</td>
		<td class="table_td_center" >占企业销售收入总额比例（%）</td>
		<td class="table_td_center" >删除</td>
		</tr>
		<s:iterator value="subProductList" status="pl">
				<tr id="<s:property value="guid"/>">
					<td class="td_left">
		 				<input type="hidden" value="<s:property value="guid"/>" name="productBean.guid" id="guid"/>
		 				<input type="text" verify="主要产品名称|NotNull" maxlength="500" name="productBean.productname" id="productname" value="<s:property value="productname"/>" css="zc_input02" size="50"/> </td>
		 			<td class="td_left">
		 				<s:select list="%{#{'1':'是','0':'否'}}" name="productBean.isautonomously" ></s:select></td>
					<td class="td_left"><input type="text" verify="占企业销售收入总额比例|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="7" name="productBean.scale" id="scale" value="<s:property value="scale"/>" css="zc_input02" size="20"/> </td>
					<td class="td_left" align="center"><a href="#"><img  src="/images/page/cancel.png" style="border:0" onclick=""></a></td>
				</tr>
			</s:iterator>
		<tr><td colspan="4" class="addnew" width="100%" ><a href="javaScript:addProduct('')" title="点击选择"><img style="border:0" src="/images/page/add.png">新增产品</a>&nbsp;&nbsp;</td> </tr>
		</table>
		</td>
		</tr>
		<TR>
			<TD  class="title_center" rowspan="2" >上年度具有自主知识产权产品经营情况（单位：万元）</TD>
			<TD  class="title_center"  nowrap="nowrap">产品销售收入<s:hidden name="productSituation.guid"></s:hidden> </TD>
			<TD  class="td_left" ><s:textfield  verify="产品销售收入|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="productionmarketing" name="productSituation.productionmarketing" cssClass="zc_input02" size="20"/>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="title_center"  nowrap="nowrap">净利润</TD>
			<TD  class="td_left"><s:textfield  verify="净利润|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="duty" name="productSituation.duty" cssClass="zc_input02" size="20"/>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
		</TR>
		<TR>
			<TD  class="title_center"  nowrap="nowrap">纳税总额</TD>
			<TD  class="td_left" ><s:textfield  verify="纳税总额|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="profit" name="productSituation.profit" cssClass="zc_input02" size="20"/>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
			<TD  class="title_center"  nowrap="nowrap">其中增值税总额
			</TD>
			<TD  class="td_left"><s:textfield  verify="其中增值税总额|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="10" id="appreciation" name="productSituation.appreciation" cssClass="zc_input02" size="20"/>&nbsp;&nbsp;<span style="color:red">* </span> </TD>
		</TR>
		</table>
		</div>
		</td></tr>
		<tr><td class="title_center" style="text-align:center" colspan="5"><input type="button" onclick="finDoSubmit()" value="保 存" class="buttonClass"> &nbsp;&nbsp;
		<input type="button" onclick="finDoSubmit()" value="暂 存" class="buttonClass"></td></tr>
		</table>
	</form>
</body>
</html>
