<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="egov.appservice.ac.model.Resource" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>修改应用屏名称</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript">
//关闭窗口
function closewindow()
{
	this.window.opener = null;
	window.open("","_self");
	window.close();
}
//提交返回值到主体页面
function sumbform()
{
	var chec=document.getElementById("name");
	if(chec==null || chec.value=="" || chec.value=="undefined")
	{
		alert("应用屏名称不能为空！");
		chec.focus();
		return ;
	}
	window.returnValue= chec.value;
	closewindow();
}
</script>
  </head>
  <body style="BACKGROUND-COLOR:#F0F7FF">
    <br><br>
    <div style="width:100%;float:clear;" align="center">
	    <table align="center" sytle="width:100%">
	    	<tr><td align="center"><span>请输入修改名称：<input type="text" id="name" name="name" size="15" /></span></td></tr>
	    </table>
	    <br>
	    <center><input type="button" value="确认" onclick="sumbform();" />&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="关闭" onclick="closewindow();"  /></center>
    </div>
  </body>
</html>
