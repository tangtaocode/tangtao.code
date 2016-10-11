<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<title>深圳市龙岗区住房与建设局产业转型升级专项资金申报系统</title>
	<meta name="keywords"
			content="深圳市龙岗区住房与建设局产业转型升级专项资金申报系统,产业转型,专项资金,产业服务,深圳市龙岗区住房与建设局产业服务,深圳市龙岗区住房与建设局" />
		<meta name="description" content="产业转型首页" />
<meta name="robots" content="all" />
<head>
<link href="/css/main.css" type="text/css" rel="stylesheet" />
<link href="/css/bizbankroll.css" type="text/css" rel="stylesheet" />
<link href="/css/login-box.css" type="text/css" rel="stylesheet" />
<script src="/js/Scripts/jquery.min.js" type="text/javascript" ></script>
<script type="text/javascript">
	function searchNotice(pageNum){
		$.post("/bizNotice/resultPage.html",{title:$("#title").val(),page:pageNum,ntype:'0'},function(data){
			$("#resultDiv").html(data);
		});
	}
 	$(document).ready(function(){
 		searchNotice();
 	});
</script>
</head>
<body>
<jsp:include page="/WEB-INF/page/public/topMenu.jsp">
<jsp:param name="menu" value="3"/>
</jsp:include>
<div class="BS_wrap">
<div class="BS_content" id="BS_content_div">
<div class="clean" style="height:10px;"></div>
<jsp:include page="/WEB-INF/page/public/menu.jsp">
<jsp:param name="menu" value="3"/>
</jsp:include>
   </div>
   <!-- 查询 -->
    <table border="0" cellpadding="0" cellspacing="15px" style="width: 98%;">
		<tr>
			<td align="left">
				<span style="font-size: 18pt;">通知公告</span>
			</td>
			<td align="right">
				&nbsp;
			</td>
		</tr>
	</table>
  	<div class="busi_dotop">&nbsp;</div>
      <div class="busi_domiddle">
       <div class="more_domiddlein">
       	<table id="userInfoTab" border="0" cellpadding="0" cellspacing="0" style="width: 90%">
	       	<tr>
				<th>公告名称：	</th>
				<td >
					<s:textfield name="title" id="title" size="30" cssClass="zc_input02"></s:textfield></td>
				<td><input type="button" class="searchButton" value="查 询" onclick="searchNotice()"></td>
			</tr>
       	</table>
       	
		<div id="resultDiv" >
			<jsp:include page="/WEB-INF/page/bizbankroll/notice/resultPage.jsp"></jsp:include>
		</div>
       	</div>
       	
      </div>
	<div class="busi_dobottom">&nbsp;</div>
  	</div>
   <div class="clean" style="height:10px;"></div>
   <jsp:include page="/WEB-INF/page/public/bottom.jsp"></jsp:include>    
</body>
</html>

  

