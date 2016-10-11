<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>kjt</title>
<style type="text/css">
#couent{
	text-align:center;
	margin-left:26%;
	margin-top:50px;
	}
.td_1{
	FONT-SIZE:34px;
	font-family:Verdana, Geneva, sans-serif;
	font-weight:bold;
    background-image:url( "/images/flow/twoFlow/kjt_01.gif ");
	background-repeat:no-repeat;
	height:123px;
	width:512px;
	text-align:center;
} 
.td_2{
    background-image:url( "/images/flow/twoFlow/kjt_02.gif ");  
	background-repeat:no-repeat;
	height:124px;
	width:512px;
	text-align:center;
} 
.td_3{
	FONT-SIZE:26px;
	font-family:Verdana, Geneva, sans-serif;
    background-image:url( "/images/flow/twoFlow/kjt_03.gif ");   
	background-repeat:no-repeat;
	height:40px;
	width:512px;
	text-align:center;
} 
.td_4{
    background-image:url( "/images/flow/twoFlow/kjt_04.gif ");  
	background-repeat:no-repeat; 
	height:112px;
	width:512px;
	text-align:center;
} 
.td_5{
	FONT-SIZE:26px;
	font-family:Verdana, Geneva, sans-serif;
    background-image:url( "/images/flow/twoFlow/kjt_05.gif ");  
	background-repeat:no-repeat; 
	height:34px;
	width:512px;
	text-align:center;
} 
</style>
<%String appName = request.getParameter("appName");
String bureauName = request.getParameter("bureauName"); 
String streetOrQu = request.getParameter("streetOrQu");
%>
</head>
<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" style="text-align:center" >
<div id="couent">
<table id="litableid" width="512px" height="100%" border="0" cellpadding="0" cellspacing="0" style="text-align:center">
	<tr >
		<td class="td_1">
        《<%=appName %>》业务流程框架图
			</td>
	</tr>
	<tr>
		<td class="td_2">&nbsp;
			</td>
	</tr>
	<tr>
		<td class="td_3">
		<%if("1".equals(streetOrQu)){
			out.print(bureauName+"审批办结");
		}else if("2".equals(streetOrQu)||"3".equals(streetOrQu)){
			out.print(bureauName+"审批决定");
		} %>
			</td>
	</tr>
	<tr>
		<td class="td_4">&nbsp;
			</td>
	</tr>
	<tr>
		<td class="td_5">
		<%if("1".equals(streetOrQu)){
			out.print("社区受理");
		}else if("2".equals(streetOrQu)){
			out.print("街道受理");
		}else if("3".equals(streetOrQu)){
			out.print(bureauName+"窗口受理");
		} %>
		</td>
	</tr>
	<tr>
		<td>
			<img src="/images/flow/twoFlow/kjt_06.gif" width="512" height="12" alt="" /></td>
	</tr>
</table>
</div>

</body>
</html>
