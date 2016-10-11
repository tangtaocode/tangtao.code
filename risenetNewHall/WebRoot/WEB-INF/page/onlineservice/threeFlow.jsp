<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>kjt3</title>
<style type="text/css">
#couent1{
	text-align:center;
	margin-left:26%;
	margin-top:50px;
	}
.td_11{
	FONT-SIZE:34px;
	font-family:Verdana, Geneva, sans-serif;
	font-weight:bold;
    background-image:url( "/images/flow/threeFlow/kjt3_01.gif ");
	background-repeat:no-repeat;
	height:89px;
	width:611px;
	text-align:center;
} 
.td_21{
    background-image:url( "/images/flow/threeFlow/kjt3_02.gif ");  
	background-repeat:no-repeat;
	height:80px;
	width:611px;
	text-align:center;
} 
.td_31{
	FONT-SIZE:26px;
	font-family:Verdana, Geneva, sans-serif;
    background-image:url( "/images/flow/threeFlow/kjt3_03.gif ");   
	background-repeat:no-repeat;
	height:49px;
	width:611px;
	text-align:center;
} 
.td_41{
    background-image:url( "/images/flow/threeFlow/kjt3_04.gif ");  
	background-repeat:no-repeat; 
	height:116px;
	width:611px;
	text-align:center;
} 
.td_51{
	FONT-SIZE:26px;
	font-family:Verdana, Geneva, sans-serif;
    background-image:url( "/images/flow/threeFlow/kjt3_05.gif ");  
	background-repeat:no-repeat; 
	height:46px;
	width:611px;
	text-align:center;
} 
.td_61{
    background-image:url( "/images/flow/threeFlow/kjt3_06.gif ");  
	background-repeat:no-repeat; 
	height:111px;
	width:611px;
	text-align:center;
} 
.td_71{
	FONT-SIZE:26px;
	font-family:Verdana, Geneva, sans-serif;
    background-image:url( "/images/flow/threeFlow/kjt3_07.gif ");  
	background-repeat:no-repeat; 
	height:42px;
	width:611px;
	text-align:center;
} 
</style>
<%String appName = request.getParameter("appName");
String bureauName = request.getParameter("bureauName"); 
%>
</head>
<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" style="text-align:center">
<div id="couent1">
<table id="kjtid" width="611" height="100%" border="0" cellpadding="0" cellspacing="0" style="text-align:center">
	<tr>
		<td class="td_11">
        《<%=appName %>》业务流程框架图</td>
	</tr>
	<tr>
		<td class="td_21">
			&nbsp;</td>
	</tr>
	<tr>
		<td class="td_31">
			<%=bureauName %>审核审批</td>
	</tr>
	<tr>
		<td class="td_41">
			&nbsp;</td>
	</tr>
	<tr>
		<td class="td_51">
			街道审核</td>
	</tr>
	<tr>
		<td class="td_61">
			&nbsp;</td>
	</tr>
	<tr>
		<td class="td_71">
			社区受理</td>
	</tr>
</table>
</div>
</body>
</html>
