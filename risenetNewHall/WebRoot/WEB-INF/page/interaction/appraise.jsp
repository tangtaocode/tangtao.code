<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>广东省网上办事大厅深圳市龙岗区住房与建设局-我要评议</title>
<meta name="keywords" content="深圳市龙岗区住房与建设局网上办事大厅-我要评议,深圳市龙岗区住房与建设局办事大厅我要评议,我要评议,深圳市龙岗区住房与建设局我要评议"></meta>
<meta name="description" content="广东省网上办事大厅深圳市龙岗区住房与建设局-我要评议"></meta>
<meta name="robots" content="all" />
<link href="/css/qyInvestIndex.css" type="text/css" rel="stylesheet" />
<link href="/css/login-box.css" type="text/css" rel="stylesheet" />
<script src="/js/Scripts/jquery.min.js" type="text/javascript"></script>
<script src="/js/validation.js" type="text/javascript"></script>
<script src="/js/businessJS/interaction.js" type="text/javascript"></script>
</head>
<body style="background-color:#fff;background-image:url('/images/govpublic/background.jpg');background-position:center top;background-repeat:repeat-y; background-attachment:fixed;">
    <div id="pagecontainer" style="width:1000px;margin:0 auto">
        <div id="we7layout_13452862291999" class="sf_cols">
 <div style="float: none; width: 100%; margin: 0; ;" class="sf_colsOut"><div class="sf_colsIn ">

<div class="SD_Top ">
    <jsp:include page="/WEB-INF/page/public/topMenu.jsp">
    <jsp:param name="menu" value="4"/>
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
				<span style="font-size: 18pt;">网上评议</span>
			</td>
			<td align="right">
				&nbsp;
			</td>
		</tr>
	</table></div>
						<!-- end: 标题面板 --> <!-- begin: 内容面板 -->
						<div id="Portal_Content">
						
						<table style="width: 960px;" cellpadding="0px" cellspacing="0px" border="0px">
							<tr>
								<td colspan="3"><img src="/images/govpublic/shadow_T.png" /></td>
							</tr>
							<tr>
								<td width="5px" height="300px" style="background-image: url(/images/govpublic/shadow_R.png); background-repeat: repeat-y;">
								</td>
								<td valign="top" style="width: 956px;" id="mainCount">
		<table border="0" cellpadding="0" cellspacing="0" style="width: 100%;">
		 <tr>
			<td style="text-align:center;">

	<table border="0" cellpadding="0" cellspacing="0" style="width: 100%;">
		<tr>
			<td style="text-align:left;padding:10px;" width="90%">
				<table id="userInfoTab" border="0" cellpadding="0" cellspacing="0" style="width: 80%">
					<tr>
						<th >
							受理编号：
						</th>
						<td >
							<s:textfield name="declaresn" verify="受理编号|NotNull" id="declaresn" cssClass="zc_input02"></s:textfield>
						</td>
					<th >
							证件号码：
						</th>
						<td >
							<s:textfield name="cardId"  verify="证件号码|NotNull" id="cardId" cssClass="zc_input02"></s:textfield>
						</td>
						<td >
							<input type="button" class="searchButton" value="查 询" onclick="searchAppraise()">
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table></td>
			</tr>
			<tr>
				<td width="100%" valign="top" align="center">
				<div id="qyInvestStep">
				<!--阶段一-->
				<div id="qyInvestStep1">
	<table border="0" cellpadding="0" cellspacing="0px" style="width: 100%;">
		<tr>
			<td>
				<img src="/images/investment/line_invest_split.png" width="956px" height="22px" border="0" />
			</td>
		</tr>
		<tr>
			<td id="pageCountTd">
			<jsp:include page="/WEB-INF/page/interaction/resultPage.jsp"></jsp:include>
			</td>
		</tr>
	</table></div>
	
				</div>
				</td>
			</tr>
		</table>
								 </td>
								<td width="2px" height="300px" style="background-image: url(/images/govpublic/shadow_R.png); background-repeat: repeat-y;"><img alt="" class="rich-spacer " height="1" id="form:j_id59" src="/a4j/g/3_3_2.SR1images/spacer.gif" width="2px" /></td>
								
							</tr>
							<tr>
								<td colspan="3"><img src="/images/govpublic/shadow_B.png" /></td>
							</tr>
						</table>
						
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

<!-- Mirrored from 61.144.227.250:8081/zwgk/ by HTTrack Website Copier/3.x [XR&CO'2013], Tue, 07 May 2013 02:46:14 GMT -->
</html>