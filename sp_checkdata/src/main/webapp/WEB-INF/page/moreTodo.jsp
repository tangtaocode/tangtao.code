<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>深圳市龙岗区住房和建设局综合办公平台</title>
<link href="/css/mstyle.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="/js/jquery.confirm/jquery.confirm.css" />
<script type="text/javascript" src="/js/jquery-1.8.1.min.js"></script>
<script type="text/javascript" src="/js/jquery.confirm/jquery.confirm.js"></script>
<script type="text/javascript" src="/js/scrollPic.js"></script>
<script type="text/javascript" src="/js/layer/layer.min.js"></script>
<script type="text/javascript" src="/js/json.js"></script>
<script type="text/javascript" src="/js/portal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/calendar/WdatePicker.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		var guids="${guids}";
		homeTodoPage(1,9,guids);
	});
	function clear(){
		$("#s_date").attr("value","");
		$("#e_date").attr("value","");
		$("#title").attr("value","");
	}

	function getSessionRandom(){
		return "<%=session.getAttribute("randomId")%>";
	}
</script>
</head>
<body>
<!-- 头部 -->
<div class="banner"><img src="/images/newimages/logo.png" alt="龙岗区住建局综合办公平台" width="670" height="100" /></div>
<div class="nav">
     <div class="nav_left">${userName }您好！ 当前绑定手机号码为：${userMobile }</div>
	 <div class="nav_right"><a href="javascript:void(0)" onclick="javascript:rebackhome();" class="icon_cz03">首页</a><a href="/employeeSet/frameset" class="icon_cz02">个人设置</a><a href="/oneHome/helpDocDown" class="icon_cz01">使用指南</a></div>
</div>
<div class="index_tc">
  <!---------------待办事项开始-------------------->
  <div class="index_tc2">
       <div class="tjbt">
	           <div class="tjbt_bt">&nbsp;&nbsp;待办事项</div>
      </div>
	  <div class="index_tc4">
	  <div class="list_ss">
	  <input type="hidden" name="page" id="page" value="0">
	  <input type="hidden" name="todoType" id="todoType" value=""><!--事项类型  -->
	  	<span>事项名称：<input name="title" type="text" class="list_input01" size="40" id="title"/></span>
	  	<span>时间：从&nbsp;<input type="text" size="20" class="list_input01" name="s_date" id="s_date" readonly="readonly" onclick="WdatePicker();">&nbsp;
	       			      到&nbsp;<input type="text" size="20" class="list_input01" name="e_date" id="e_date" readonly="readonly" onclick="WdatePicker();"></span>
	    <span> <a href="javascript:homeTodoPage(1,9,'${guids}')" class="list_but1">查询</a> <a href="javascript:clear();" class="list_but2">重置</a></span>
      </div>
      </div>
	  <div class="index_tc3">
	    <ul id="gtasks">
	    </ul>
    </div>
      <div class="list_page">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" id="pageNum">       
  </table>
  </div>
  </div>
  <!---------------待办事项2结束-------------------->
</div>
<!---------------版权------------------>
<div class="copy">版权所有：深圳市龙岗区住房和建设局&nbsp;&nbsp;&nbsp;建议最佳分辨率：1440*900</div>
</body>
</html>