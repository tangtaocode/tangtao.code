<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>基本表格模板</title>
<!--框架必需start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/language/cn.js"></script>
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/framework.js"></script>
<link href="${ctx}/static/QUI/libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="${ctx}/static/QUI/" scrollerY="false"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->
<!--数据表格start-->
<script src="${ctx}/static/QUI/libs/js/table/quiGrid.js" type="text/javascript"></script>
<!--数据表格end-->
<!--表单异步提交start-->
<script src="${ctx}/static/QUI/libs/js/form/form.js" type="text/javascript"></script>
<!--表单异步提交end-->
<!-- 日期控件start -->
<script type="text/javascript" src="${ctx}/static/js/My97DatePicker/WdatePicker.js"></script>
<!-- 日期控件end -->
<!--树形表格start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/table/detailTable.js"></script>
<!--树形表格end-->
<!--弹窗组件start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/popup/drag.js"></script>
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/popup/dialog.js"></script>
<!--弹窗组件end-->
<!--弹出式提示框start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/popup/messager.js"></script>
<!--弹出式提示框end-->
<!--框架必需start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/language/cn.js"></script>
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/framework.js"></script>
<link href="${ctx}/static/QUI/libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="${ctx}/static/QUI/" scrollerY="false"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->
<!--数据表格start-->
<script src="${ctx}/static/QUI/libs/js/table/quiGrid.js" type="text/javascript"></script>
<!--数据表格end-->
<!--表单异步提交start-->
<script src="${ctx}/static/QUI/libs/js/form/form.js" type="text/javascript"></script>
<!--表单异步提交end-->
<!-- 日期控件start -->
<script type="text/javascript" src="${ctx}/static/js/My97DatePicker/WdatePicker.js"></script>
<!-- 日期控件end -->
<!--箭头分页start-->

<script type="text/javascript" src="${ctx}/static/QUI/libs/js/nav/pageArrow.js"></script>
<!--箭头分页end-->
<!--箭头分页start-->

<script type="text/javascript" src="${ctx}/static/QUI/libs/js/table/detailTable.js"></script>
<!--箭头分页end-->

<script type="text/javascript">
var id='${guid}';
console.info(id);
$(function(){
	$.post("${ctx}/history/read",{gid:id},function(result){
		console.info(result.info);
		console.info(result.names);
		var arr=result.info;
		 var Con = document.getElementById("iden");
		var map=result.names;
		
		for(var c in map){
			console.info(map[c]+c);
			var tr = document.createElement("tr");
			Con.appendChild(tr);
			var td = document.createElement("td");
			tr.appendChild(td);
			td.innerHTML = "<span onclick=getBigImage('"+c+"')><img src='/file/"+c+"' style='width:50px;heigth:50px;'/>"+map[c]+"</span>";
			}
 		 },"json");  
})

function getBigImage(param){
	console.info(param);
	$("#img").attr("src","/file/"+param);
}

</script>
	
<title>证照扫描</title>
</head>
<body>
  <div class="box2" panelTitle="证照录入" id="searchPanel">
	<div class="box_tool_min padding_top2 padding_bottom2 padding_right5">
    <div class="center">
    <div class="left">
    <div class="right">
        <div class="padding_top5 padding_left10">
        <a href="javascript:void(0);" title="历史信息" onclick="onbszn('${instanceId}');"><span class="icon_view">历史信息</span></a>
        <div class="box_tool_line"></div>
        <a href="javascript:;"></a>
        
    </div>      
    </div>  
    </div>
    </div>
	</div>

	<div class="padding_right5" id="scrollContent">
  
		<form action="${ctx}/sbApprove/approveMaterialList" id="queryForm" method="post" align="center">
			<div id="maingrid"></div>
			<input  type="hidden" name="gid" id="gid" value="${id }"/>
			<input type="hidden" name="processDefinitionId" id="processDefinitionId" value="${id }"/>
			<table class="tableStyle" formMode="view">

		        <tr><th colspan="2">历史信息</th></tr>
		
		        <tr><td>业务类型：</td><td>${approvname}</td></tr>
		
		        <tr><td>申请人姓名：</td><td>${name}</td></tr>
		
		        <tr><td>经办人：</td><td>${adddate}</td></tr>
		
		        <tr><td>经办部门：</td><td>${tel}</td></tr>
		    </table>
		  		<div class="box1" style="width: 70%;margin: 0 auto;"><div class="box_topcenter"><div class="box_topleft"><div class="box_topright">查看证照</div></div></div><div class="box_middlecenter"><div class="box_middleleft"><div class="box_middleright"><div class="boxContent" style="height: 70%; overflow: visible;">

					<div class="box1" style="width: 30%;margin: 0 auto;float:left;"><div class="box_middlecenter"><div class="box_middleleft"><div class="box_middleright"><div class="boxContent" style="height: 100%; overflow: visible;">
	
							  <table  id="iden">
							  <%--动态添加图片的id RISENET_FILE储存图片的表--%>
							  </table>
	
						</div></div></div></div><div class="box_bottomcenter"><div class="box_bottomleft"><div class="box_bottomright"></div></div></div></div>
					
						
						<div  class="box1" style="width: 70%;margin: 0 auto;float:right;"><div class="box_middlecenter"><div class="box_middleleft"><div class="box_middleright"><div class="boxContent" style="height: 100%; overflow: visible;">
							<div id="view" style="text-align:center;margin:0px auto">
									<%--<img src="file:///E|/photos/01.jpg" width="400" height="100"/> 
									<img src="/file/01.jpg" style="margin:0px auto;padding-top: 10%;"/>--%>
									<img id="img" style="margin:0px auto;padding-top: 10%;width:40%;"/>
							</div>
						</div></div></div></div><div class="box_bottomcenter"><div class="box_bottomleft"><div class="box_bottomright"></div></div></div></div>
					
				</div></div></div></div><div class="box_bottomcenter"><div class="box_bottomleft"><div class="box_bottomright"></div></div></div></div>
		  		
			</form>
			</div>
			</div>
			</body>
</html>