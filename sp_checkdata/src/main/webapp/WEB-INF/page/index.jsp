<%@ page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>深圳市水务局审批数据校验系统</title>
<link href="/css/mstyle.css" rel="stylesheet" type="text/css" />
<link href="/css/floattip.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/js/jquery-1.8.1.min.js"></script>
<script type="text/javascript" src="/js/layer/layer.min.js"></script>
<script type="text/javascript" src="/js/json.js"></script>
<script type="text/javascript" src="/js/portal.js"></script>
<script type="text/javascript" src="/js/floattip.js"></script>

<script type="text/javascript">
	$(document).ready(function(){
		//页面加载弹出提示框
		poptip();
		openMenu('_A0Eu8M8SEeGr8b3x8l9znA');
	});
	
	function getSessionRandom(){
		return "<%=session.getAttribute("randomId")%>";
	}
	 function MM_swapImgRestore() { //v3.0
		  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
		}

		function MM_preloadImages() { //v3.0
		  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
		    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
		    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
		}

		function MM_findObj(n, d) { //v4.01
		  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
		    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
		  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
		  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
		  if(!x && d.getElementById) x=d.getElementById(n); return x;
		}

		function MM_swapImage() { //v3.0
		  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
		   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
		} 	
</script>
</head>

<body onload="MM_preloadImages('/images/newimages/yy03.gif')">
<!-- 头部 -->
<div class="banner"><img src="/images/newimages/logo.png" alt="深圳市水务局审批数据校验系统" width="670" height="100" /></div>
<div class="nav">
	 <div class="nav_right"><a href="javascript:closehome()" class="icon_cz03">退出</a><a href="/oneHome/helpDocDown" class="icon_cz01">使用指南</a></div>
</div>

<!--------------------------------------首页  主体开始------------------------->
<div class="index">
<!---------------------------------左侧导航开始------------------>
	<div class="index_left" id="leftTree">
		<ul>
<!-- 		<li class="index_left_li"> <a href="javascript:void(0)" onclick="javascript:changetable('checkhour');"  id="_A0Eu8M8SEeGr8b3x8l9znA" title="超时监察" level="1" onmouseover="openMenu('_A0Eu8M8SEeGr8b3x8l9znA')" target="_self" class="index_nav2">超时监察</a></li> -->
		<li class="index_left_li"> <a href="javascript:void(0)" onclick="javascript:changetable('checkdata');" id="_x5SfwM8TEeGr8b3x8l9znA" title="规则监察" level="1" onmouseover="openMenu('_x5SfwM8TEeGr8b3x8l9znA')" target="_self" class="index_nav3">规则监察</a></li>
		<li class="index_left_li"> <a href="javascript:handwordCheck();" id="_1LXmcM8REeGr8b3x8l9znA" title="手动校验" level="1" onmouseover="openMenu('_1LXmcM8REeGr8b3x8l9znA')" target="_self" class="index_nav1">手动校验</a></li>
		<li class="index_left_li"> <a href="javascript:void(0)" onclick="javascript:changetable('sms');" id="_j_kaYBMbEeWWspLPdxku6A" title="短信设置" level="1" onmouseover="openMenu('_j_kaYBMbEeWWspLPdxku6A')" target="_self" class="index_nav4">短信设置</a></li>
		</ul>
  </div>
 <!---------------------------------左侧导航结束------------------>	
   <div class="index_close"><a href="javascript:hidenleft();"><img src="/images/newimages/cloes01.jpg" alt="关闭" width="10" height="88" border="0" /></a></div>
<!---------------------------------右侧开始------------------>
<div class="index_right" id="right_container">
<iframe src="/checkData/jianchaHtml" height="100%" width="99%" style="margin: 0px;padding: 0px;" frameborder="0" id="tablecontent" scrolling="no"></iframe>
</div>			

<!-- 提示信息弹出层 开始 -->
<div id="pop" style="display:none;" class="tixing">
 <div class="tixing_bt" id="popClose"><span>超期提醒</span><a href="#"></a></div>
  </div>
<!--30分钟自动刷新一次  -->
<script>
var t2 = window.setInterval("poptip()",30*60*1000); 
</script>
<!-- 提示信息弹出层 结束 -->
<div class="copy">版权所有：深圳市水务局审批数据校验系统&nbsp;&nbsp;&nbsp;建议最佳分辨率：1440*900</div>
</body>
</html>