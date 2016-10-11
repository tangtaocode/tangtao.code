<%@ page language="java" contentType="text/html; charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>首页统计</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="/js/jquery-1.8.1.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$.post('/oneHome/getCounts',function(date){
		var counts = eval("("+date+")");
		$('#bureauCount').html(counts.bureauCount);
		$('#departMentCount').html(counts.departMentCount);
		$('#dayCount').html(counts.dayCount);
		$('#todoCount').html(counts.todoCount);
		$('#doingCount').html(counts.doingCount);
		$('#donePercent').html(counts.donePercent);
		var myDate = new Date();
		var year=myDate.getFullYear();
		var month = myDate.getMonth()+1;
		var day = myDate.getDate();
		$("#titletime").html("统计"+year+"年"+month+"月"+day+"日");
	});
	
});
</script>
</head>
<body>
<div class="index_rc01">
     <div class="index_rc_top"><span class="index_rc_tit01" id="titletime">统计</span></div>
	 <div class="index_rc02">
		  <div>
		    <ul >
		      <li>局办件总数:<span id="bureauCount"></span>(件)</li>
		      <li>部门业务量:<span id="departMentCount"></span>(件)</li>
		       <li>部门在办:<span id="todoCount"></span>(件)</li>
		       <li>部门已办:<span id="doingCount"></span>(件)</li>
		      <li>部门办结率:<span id="donePercent"></span></li>
		      <li>当天办件量:<span id="dayCount"></span>(件)</li>
		      
		    </ul>
	      </div>
	 </div>
	 <div class="index_rc_bot"></div>
</div>
</body>
</html>