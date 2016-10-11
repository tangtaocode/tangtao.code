<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<title>广东省网上办事大厅深圳市龙岗区住房与建设局-在线申报</title>
<meta name="keywords" content="深圳市龙岗区住房与建设局网上办事大厅-在线申报,深圳市龙岗区住房与建设局办事大厅在线申报,在线申报,深圳市龙岗区住房与建设局在线申报"></meta>
<meta name="description" content="广东省网上办事大厅深圳市龙岗区住房与建设局-在线申报首页"></meta>
<meta name="robots" content="all" />
<head>
<link href="/css/main.css" type="text/css" rel="stylesheet" />
<link href="/css/initialize.css" type="text/css" rel="stylesheet" />
<link href="/css/font.css" type="text/css" rel="stylesheet" />
<script src="/js/Scripts/jquery.min.js" type="text/javascript" class="jqueryhigh" id="jqueryhigh"></script>
<style type="text/css">
#leveOneDiv ul li a:hover{
	text-decoration: none;
}

#leveOneDiv ul li a:visited{
	text-decoration: none;
	color: #2B76CC;  
}
#levelTowDIV ul li a:hover{
	text-decoration: none;
}

#levelTowDIV ul li a:visited{
	text-decoration: none;
	color: #2B76CC;  
}
.BS_dotop{
	
}
</style>
<script type="text/javascript">
function changeSerach(id,name){
      			$("#sxss").attr("class","");
      			$("#bgss").attr("class","");
      			$("#bjcx").attr("class","");
      			$("#xklx").attr("class","");
      			$("#sxssForm").attr("style","display:none");
      			$("#bgssForm").attr("style","display:none");
      			$("#bjcxForm").attr("style","display:none");
      			$("#xklxForm").attr("style","display:none");
      			$("#"+id).attr("class","BS_c blue");
      			$("#"+id+"Form").attr("style","display:block");
      			
      		}

function refrashResult(page,appType){
	$.post('/approve/approveQueryResult.html',
		{'departGUID':$("#departGUID").val(),
		 'approveName':$("#approveName").val(),
		 'isOnlineApply':$('input[name=isOnlineApply]:checked').val(),
		 'page':page,
		 'approveType':appType},
		function(data){
			$("#approveItemDIV").html(data);
		});
}
function loadAppGUID(guid,street){

	$.post('/approve/findAppGuideInfo.html',
		{'approveItemGUID':guid,
		 'streetApp':street},
		function(data){
			$("#BS_content_div").html(data);
	});
}
function topage(page,fun){
	$("#pageCount").attr("value",page);
	fun(page);
}

 
</script>
</head>
<body style="background-color:#fff;background-image:url('/images/govpublic/background.jpg');background-position:center top;background-repeat:repeat-y; background-attachment:fixed;">
<jsp:include page="/WEB-INF/page/public/topMenu.jsp">
<jsp:param name="menu" value="3"/>
</jsp:include>
<div class="BS_wrap">
<div class="BS_content" id="BS_content_div">
<div class="BS_wrap2" id="mainCount">
<div class="clean">&nbsp;</div>
<!--快捷办事 start-->
    <!--搜索事项 start-->
   </div>
   <!-- ----------- -->
  <div class="BS_docontent"> 
  <div class="BS_dotops">&nbsp;</div>
      <!--middle start-->
      <div class="BS_domiddle"> 
        <!--middlein start-->
        <div class="BS_domiddlein"> 
	<!--2级导航 start-->
			<!--do header start-->
			 <div class="BS_doheader" id="searchParerment">
			<!--标题 start-->
			<div class="BS_title l" id="itemTypeText">
				街道专栏
			</div>
            <!--标题 end--> 
            <!--导航 start-->
            <div class="BS_nav3 l" id="leveOneDiv">
					<ul>
						<li class="BS_rline">
							<a href="#" class="BS_c">首页</a>
						</li>
						<li class="BS_rline">
							<a href="#" >网上办事</a>
						</li>
						<li class="BS_rline">
							<a href="#" >办事查询</a>
						</li>
						<li class="BS_rline">
							<a href="#" >网上咨询</a>
						</li>
						<li class="BS_rline">
							<a href="#" >网上投诉</a>
						</li>
						<li class="BS_rline">
							<a href="#" >政策法规</a>
						</li>
						<li class="BS_rline">
							<a href="#" >社区专栏</a>
						</li>
					</ul>
					<div class="clean"></div>
			</div>
			 <!--导航 end--> 
          </div>
          <!--do header end--> 
	<!--2级导航 end-->
	<div id="resultListDIV">
				<!--导航 start-->
				<div class="BS_subnav2" id="levelTowDIV">
					
				</div>
				<!--导航 end-->
				
			<div class="clean"></div>
			<!--do header end-->
			<!--todo start-->

			<div id="approveItemDIV">
				<div class="BS_todos">
				<font color="#b2b2b2" style="font-size:40px;">栏目建设中</font>
	<!--<s:iterator value="bureauList" status="item">
	<div class=bs_dep style="">
		<a href="javascript:searchAppList('<s:property value="code"/>');"><s:property value="value"/></a><font color="#b2b2b2" style="font-size:11px;">(建设中)</font></div>
	</s:iterator>
--></div>
			</div>
		</div>
			<div class="clean"></div>
	</div>
	</div>
	</div>
	<div class="BS_dobottom">&nbsp;</div>
     <!-- ----------- -->
   </div>
   
   </div>
   <div class="clean" style="height:10px;"></div>
   <jsp:include page="/WEB-INF/page/public/bottom.jsp"></jsp:include>    
</body>
</html>

  

