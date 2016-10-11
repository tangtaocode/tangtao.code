<%@page import="net.risesoft.common.util.ConstUtils"%>
<%@page import="net.risesoft.model.RiseEmployee"%>
<%@ page contentType="text/html;charset=utf-8" %>
<%
RiseEmployee user=(RiseEmployee)session.getAttribute(ConstUtils.SESSION_USER);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>个人设置</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="language" content="en" />
<link href="/css/leftTree.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../../js/jquery-1.8.1.min.js"></script>
</head>
<script type="text/javascript">
$.post('/employeeSet/applyMarket',{},function (data){
	$("#righthtml").html(data);
});
function changtype(type){
	if(type=='editpass'){
		$.post('/person/passwordhtml',{},function (data){
			$("#righthtml").html(data);
		});
	}else if(type=='updatePerson'){
		$.post('/person/showPerson',{},function (data){
			$("#righthtml").html(data);
		});
	}else if(type=="market"){
		$.post('/employeeSet/applyMarket',{},function (data){
			$("#righthtml").html(data);
		});
	}else if(type=="editsms"){
		$.post('/Sms/toSmsPage',{},function (data){
			$("#righthtml").html(data);
		});
	}else{
		$.post('/person/iconhtml',{},function (data){
			$("#righthtml").html(data);
		});
	}
}

</script>
<body>
 <div class="div_title"><span>个人设置</span></div>
 <div class="div_list_ul">
 <span class="span_01"><a href="#" onclick="javascript:changtype('market');">应  用  市  场</a></span><br>
 <span class="span_01"><a href="#" onclick="javascript:changtype('updatePerson');">基本信息修改</a></span><br>
 <span class="span_01"><a href="#" onclick="javascript:changtype('editpass');">密  码  修  改</a></span><br>
 <span class="span_01"><a href="#" onclick="javascript:changtype('editsms');">短  信  设  置</a></span>
 </div>	   
</body>
</html>
