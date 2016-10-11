<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%String path = request.getContextPath(); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>应用分屏</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="应用分屏">
	<!-- <link href="/css/applyMarket.css" rel="stylesheet" type="text/css" /> -->
	<link href="/css/mstyle.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<%=path%>/js/jquery-1.8.2.js"></script>
	
<script type="text/javascript">
$(document).ready(function(){
	$.ajax({
		url:"/employeeSet/getApplyList",
		type:"post",
		cache:false,
		dataType:"text",
		success:function(data){
			$("#bigdiv").html(data);
			getziyuanwindow('0');
		},
		error:function(XMLHttpRequest,textStatus,errorThrown){
		       alert("错提示"+textStatus);
		}
	});
	
});

//行上移
function trup(dthis)
{
	var iconguid = $("#inputfenping0").val();
	var guid1 = $(dthis).parent().parent().attr("id");
	var guid2 = $(dthis).parent().parent().prev().attr("id");
	if(guid2=='yuansunum0'){
		alert("这是最顶行了！");
	}else{
		$(dthis).parent().parent().prev().before($(dthis).parent().parent());
		SwapTrRow(guid1,guid2,iconguid);
	}
}
//行下移
function trdown(dthis)
{
	var iconguid = $("#inputfenping0").val();
	var guid1 = $(dthis).parent().parent().attr("id");
	var guid2 = $(dthis).parent().parent().next().attr("id");
	if(guid2==""||guid2=="undfine"||null==guid2){
		alert("这是最底行了！");
	}else{
		$(dthis).parent().parent().next().after($(dthis).parent().parent());
		SwapTrRow(guid1,guid2,iconguid); 
	}      
}
//删除子屏资源
function delziyuan(dthis,ziguid,icon_guid,m)
{
	$.post("/employeeSet/deleteIcon",{'guid':ziguid,'icon_guid':icon_guid}, function(data){
		var temp = eval("("+data+")");
		if(temp.outcome=="1")
		{
		$(dthis).parent().parent().remove();
			//dthis.parentNode.parentNode.parentNode.removeChild(dthis.parentNode.parentNode);
			var nu=document.getElementById("yuansunum0");
			nu.value=parseInt(nu.value)-1;
		}
	} );
}
//添加元素列表行
function docreateRow(checkid,checkname,icon_guid,num) {

	var trHtml="<LI id='"+checkid+"' onmouseout='javascript:hidenOper(this)' onmouseover='javascript:showOper(this)'><img src='/images/newimages/icon"+num+".png' border='0' width='72' height='72' /><br />";
	trHtml+="<span>"+checkname+"</span>";
	trHtml+="<div class=\"yytj2_cz\" style='display: none;'>";
	trHtml+=" <a href='javascript:void(0)' onclick='javascript:trup(this);'><img  src='/images/newimages/sz_01.gif' alt='前移' width='15' height='15' border='0' /></a>&nbsp;&nbsp;";
	trHtml+=" <a href='javascript:void(0)' onclick='javascript:trdown(this);'><img  src='/images/newimages/sz_02.gif' alt='后移' width='15' height='15' border='0' /></a>&nbsp;&nbsp;";
	trHtml+="<a href='javascript:void(0)' onclick=\"javascript:delziyuan(this,'"+checkid+"','"+icon_guid+"','0');\"><img  src='/images/newimages/sz_03.gif' alt='移除' width='15' height='15' border='0' /></a></div></LI>";
	$("#bigdiv").append(trHtml);
	var nu=document.getElementById("yuansunum0");
	nu.value=parseInt(nu.value)+1;
}
//提交数据交换表格中的行
function SwapTrRow(guid1,guid2,iconguid)
{
	$.post("/employeeSet/swapziyuan",{guid1:guid1,guid2:guid2,iconguid:iconguid},function(data){
	var result = eval("("+data+")");
	if(result.outcome!="1")
	alert("后台提交数据失败！");
	}
	);
}

//打开资源列表窗体
function getziyuanwindow(ta)
{
	var zi=document.getElementById("yuansunum"+ta);
	var znum=parseInt(zi.value);
	if(znum<=24)
	{
		//主屏guid
		var guid=document.getElementById("inputfenping"+ta).value;
		//请求栏目树
		$.post("/employeeSet/getFolderView","",function(data){
			$("#treecontain").html(data);
		});
	}else{
		alert("每屏最多有24个应用！");
	} 
}
//子分屏添加
function addsubscreen(subguids){
	var guid = $("#inputfenping0").val(); 
	if(subguids!=null && subguids!="" && subguids!="undefined"){
	$.post("/employeeSet/addIcon",
	{'fenpingguid':guid,'subguids':subguids},
	function(data){
		var result = eval("("+data+")");
		if(result.outcome=='1'){
			getziyuanwindow('0');
		}
	});
 } 
}
//显示操作
function showOper(op){
	$(op).find("div").css("display","block");
}
//隐藏操作
function hidenOper(op){
	$(op).find("div").css("display","none");
}
</script>
  </head>
  <body>
  <!---------------------------------右侧开始------------------>
  <div class="yytj">
	       <div class="grsz_title"><span>&nbsp;&nbsp;应用市场快捷方式</span></div>
		   <div class="yytj2">
		      <UL id="bigdiv">
	     </UL>
	     </div>
	   </div>
	   <div class="qbyy"> 
	      <div class="grsz_title"><span>&nbsp;&nbsp;基本信息</span><a href="javascript:void(0);" onclick="javascript:comfirForlder();" class="grsz_title_a">保存</a></div>
	    <div class="jbxx_text" id="treecontain"></div>
	</div>
  </body>
</html>
