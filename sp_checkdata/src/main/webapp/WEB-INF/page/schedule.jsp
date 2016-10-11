<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String aa="1111";
%>

<style type="text/css">
scroll { 
	width: 100%; /*宽度*/ 
	height: 100%; /*高度*/ 
	color:; /*颜色*/ 
	font-family:; /*字体*/ 
	padding-left: 10px; /*层内左边距*/ 
	padding-right: 10px; /*层内右边距*/ 
	padding-top: 10px; /*层内上边距*/ 
	padding-bottom: 10px; /*层内下边距*/ 
	overflow-x: scroll; /*横向滚动条(scroll:始终出现;auto:必要时出现;具体参考CSS文档)*/ 
	overflow-y: scroll; /*竖向滚动条*/ 
	scrollbar-face-color: #D4D4D4; /*滚动条滑块颜色*/ 
	scrollbar-hightlight-color: #ffffff; /*滚动条3D界面的亮边颜色*/ 
	scrollbar-shadow-color: #919192; /*滚动条3D界面的暗边颜色*/ 
	scrollbar-3dlight-color: #ffffff; /*滚动条亮边框颜色*/ 
	scrollbar-arrow-color: #919192; /*箭头颜色*/ 
	scrollbar-track-color: #ffffff; /*滚动条底色*/ 
	scrollbar-darkshadow-color: #ffffff; /*滚动条暗边框颜色*/
}
</style>
<script type="text/javascript">
function tabSelect3(div1, div2,obj,obj1) {
	document.getElementById(div1).style.display = "";
	document.getElementById(div2).style.display = "none";
	$("#"+obj1).removeClass("index_title2_hover");
	$("#" + obj).addClass("index_title2_hover");

}
</script>

<div class="index_rc01">
     <div class="index_rc_top"><span class="index_rc_tit01">日程安排</span></div>
	 <div class="index_rc02">
	      <div><%@ include file="XRiLi.jsp"%></div>
		  <div>
		    <ul>
		      
		    </ul>
	      </div>
	 </div>
	 <div class="index_rc04">
	      <div class="index_rc_li" id="richangli">
	        <ul id="jsul">
	        </ul>
          </div>
	 </div>
	 <div class="index_rc_bot"></div>
</div>
