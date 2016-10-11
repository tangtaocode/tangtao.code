<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>导航栏</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<link rel="stylesheet" type="text/css" href="/css/portal.css">
<style type="text/css">
#title{
	font-size:18px;
	font-family:黑体,sans-serif;
	/*background-color:#95CB51;*/
	text-align: left;
	padding:5px 0px 3px 20px;
	margin:0;
	height:30px;
	background:url(/images/portal/greenbox_title_bg.gif) no-repeat;
}
.daohang li{
	margin-top:25px;
}
.daohang li a{
	font-size:16px;
	font-family:宋体,serif;
}

</style>
</head>

<body>
	<div class="">
		<ul class="daohang">
			<li id="title">导航栏</li>
			<li><a href="javascript:changePage(1);">应用分屏</a></li>
			<li><a href="javascript:changePage(2);">通知公告</a></li>
		</ul>
	</div>
</body>
<script type="text/javascript" src="/js/portal.js"></script>
<script type="text/javascript">
window.onload = changeColor();
</script>
</html>
