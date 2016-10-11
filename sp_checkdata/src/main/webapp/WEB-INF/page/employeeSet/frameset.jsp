<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="language" content="en" />
<meta http-equiv="X-UA-Compatible"content="IE=9; IE=8; IE=7; IE=EDGE">
<title>深圳市龙岗区住房和建设局综合办公平台</title>
<!-- <link href="/css/employeeSet.css" rel="stylesheet" type="text/css" /> -->
<link href="/css/mstyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../../js/jquery-1.8.1.min.js"></script>
   <script type="text/javascript" src="/js/layer/layer.min.js"></script>
<script type="text/javascript" src="/js/portal.js"></script>
</head>
<script type="text/javascript">
$(document).ready(function(){
	var showmess = $("#curentshow").val();
	changtype(showmess);
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
/**隐藏左侧栏目*/
function hidenLeft(){
	var display = $("#tree_left").css("display");
	
	if(display=='block'){
		$("#tree_left").css('display','none');
		$(".grsz_right").css('margin-left','0px');
		$(".set_close").css('left','0px');
		$(".jbxx_text").css('width','98%');
	}else{
		$("#tree_left").css('display','block');
		$(".grsz_right").css('margin-left','220px');
		$(".set_close").css('left','200px');
		$(".jbxx_text").css('width','auto');
	}
}

</script>
<body>
<!-- 头部配置 start-->
<div class="banner"><img src="/images/newimages/logo.png" alt="龙岗区住建局综合办公平台" width="670" height="100" /></div>
<div class="nav">
     <div class="nav_left">当前用户：${userName}&nbsp;&nbsp;&nbsp;所属部门：${bureauName}</div>
	 <div class="nav_right"><a href="javascript:void(0)" onclick="javascript:rebackhome();" id="homepage" class="icon_cz03">首页</a><a href="/employeeSet/frameset" class="icon_cz02">个人设置</a><a href="/oneHome/helpDocDown" class="icon_cz01">使用指南</a></div>
	 <input type="hidden" id="curentshow" value="updatePerson">
</div>
<!-- 头部配置 end-->
<!--------------------------------------首页  主体开始------------------------->
<div class="set_index">
     <!---------------------------------左侧导航开始------------------>
	 <div class="grsz_left" id="tree_left">
	   <ul>
	     <li class="grsz_left01"><span>个人设置</span></li>
	     <li class="grsz_link01"><a href="#" onclick="javascript:changtype('updatePerson');" class="grsz_a2">基本信息</a></li>
         <li class="grsz_link01"><a href="#" onclick="javascript:changtype('market');" class="grsz_a1">应用市场</a></li>
	     <li class="grsz_link01"><a href="#" onclick="javascript:changtype('editpass');" class="grsz_a3">密码修改</a></li>
	     <li class="grsz_link01"><a href="#" onclick="javascript:changtype('editsms');" class="grsz_a4">短信设置</a></li>
	   </ul>
  </div>
  <!---------------------------------左侧导航结束------------------>
    <div class="set_close"><a href="javascript:hidenLeft();"><img src="/images/newimages/cloes01.jpg" alt="关闭" width="10" height="88" border="0" /></a></div>
    <!---------------------------------右侧开始------------------>
  <div class="grsz_right" id="righthtml" align="center">
  </div>
</div>
<div class="copy">版权所有：深圳市龙岗区住房和建设局&nbsp;&nbsp;&nbsp;建议最佳分辨率：1440*900</div>
</body>
</html>
