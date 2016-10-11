<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<title>广东省网上办事大厅深圳市龙岗区住房与建设局-在线申报</title>
<meta name="keywords" content="深圳市住房与建设局网上办事大厅-在线申报,深圳市住房与建设局办事大厅在线申报,在线申报,深圳市住房与建设局在线申报"></meta>
<meta name="description" content="广东省网上办事大厅深圳市住房与建设局-在线申报首页"></meta>
<meta name="robots" content="all" />
<head>
<link href="/css/main.css" type="text/css" rel="stylesheet" />
<link href="/css/initialize.css" type="text/css" rel="stylesheet" />
<link href="/css/font.css" type="text/css" rel="stylesheet" />
<script src="/js/Scripts/jquery.min.js" type="text/javascript"></script>
<script src="/js/zDialog/risenetDialog.js" type="text/javascript"></script>
<script src="/js/businessJS/onlineService.js" type="text/javascript"></script>
<style type="text/css">
#leveOneDiv ul li a:hover{
	text-decoration: none;
}

#levelTowDIV ul li a:hover{
	text-decoration: none;
}
 .BS_list tr td a{
 	text-decoration: none;
 }
 .BS_list tr td a:hover{
 	text-decoration: none;
 }
</style>
<script type="text/javascript">
//切换功能查询栏

 $(document).ready(function(){
 	<s:if test="method=='departBack'">
 		changeClassify('','','department');
 		changeText('部门服务导航');
 		changeClass('oneMenuA',2,4,'BS_c','');
 	</s:if>
 //	<s:if test="method=='streeBack'">
 //		changeClassify('','','street');
 //		changeText('街道办服务导航');
 //		changeClass('oneMenuA',3,4,'BS_c','');
// 	</s:if>
 	<s:if test="method==null||method==''">
		changeClassify('<s:property value="oneLevel"/>','2','images');
		<s:if test="oneLevel=='{7F000001-FFFF-FFFF-AC41-AFE400000001}'">
			changeClass('oneMenuA',0,4,'BS_c','');
			changeText("办事导航");
		</s:if>
		<s:if test="oneLevel=='{7F000001-FFFF-FFFF-AC41-AFE700000002}'">
			changeClass('oneMenuA',1,4,'BS_c','');
			changeText("企业办事导航");
		</s:if>
	</s:if>
 });
 
</script>
</head>
<body style="background-color:#fff;background-image:url('/images/govpublic/background.jpg');background-position:center top;background-repeat:repeat-y; background-attachment:fixed;">
<jsp:include page="/WEB-INF/page/public/topMenu.jsp">
<jsp:param name="menu" value="3"/>
</jsp:include>
<s:hidden name="twoLevel" id="twoLevel"></s:hidden>
<div class="BS_wrap">
<div class="BS_content" id="BS_content_div">
<div class="BS_wrap2" id="mainCount">
<div class="clean">&nbsp;</div>
<!--快捷办事 start-->
	<!-- 
    <div class="r BS_fastitem">
	 -->
      <!--外框 start -->
      <!-- 
      <div class="BS_fastitemm">
		<a href="javascript:searchApp();changeSerach('sxss','事项搜索');"><img src="/images/lineservice/ico_item01.png" width="32" height="32" alt="办事指南" />办事指南</a>
  		<a href="javascript:searchTable();changeSerach('bgss','表格搜索')"><img src="/images/lineservice/ico_item02.png" width="32" height="32" alt="表格下载" />表格下载</a>
		<a href="javascript:searchApp('','0');"><img src="/images/lineservice/ico_item03.png" width="32" height="32" alt="网上办理" />网上办理</a>
		<a href="javascript:void(0)" onclick="changeSerach('bjcx','办件查询')"><img src="/images/lineservice/ico_item04.png" width="32" height="32" alt="进度查询" />办理查询</a> 
	  </div>
	  -->
	  <!-- 星级评定start -->
	  <!-- 
	  <table width="100%">
	  <tr>
        <td height="25" align="right" valign="bottom"><font style="font-size:11pt; color: #314e80; font-weight:bold; font:'微软雅黑'" >星级评定：</font></td>
        <td align="left" style="width:70%" valign="bottom" onmouseover="rate(this,event)">
        	<img src="/images/share/star.png" alt="" width="17" height="16" vspace="4" />
        	<img src="/images/share/star.png" alt="" width="17" height="16" vspace="4" />
        	<img src="/images/share/star.png" alt="" width="17" height="16"  vspace="4"/>
        	<img src="/images/share/star.png" alt="" width="17" height="16" vspace="4" />
        	<img src="/images/share/star.png" alt="" width="17" height="16"  vspace="4"/></td>
      </tr>
      </table>
    -->
    <!-- 星级评定end -->
    <!--
    </div>
	-->
<!--快捷办事 end--> 
    
    <!--搜索事项 start-->
    <div class="l BS_search2"> 
      <!--外框 start-->
      <div class="BS_search2m"> 
        <!--搜索导航 start-->
        <div class="BS_subnavs">
          <ul>
          	<!-- 
          	<li><a href="javascript:void(0)" class="BS_c blue" id="cksy" onclick="changeSerach('cksy','窗口首页')">窗口首页</a></li>
          	-->
            <li><a href="javascript:void(0)" id="sxss" onclick="changeSerach('sxss','事项搜索');searchApp();changeHidden();">办事指南</a></li>
            <li><a href="javascript:void(0)" id="bgss" onclick="changeSerach('bgss','表格下载');searchTable();changeHidden();">表格下载</a></li>
            <li><a href="javascript:void(0)" id="wsbl" onclick="changeSerach('wsbl','网上申报');searchApp('','0');changeHidden();">网上申报</a></li>
            <li><a href="javascript:void(0)" id="bjcx" onclick="changeSerach('bjcx','办理查询');searchAtran(0);">办理查询</a></li>
            <!-- 若需要显示则还需要修改该查询
             <li><a href="javascript:void(0)" id="xklx" onclick="changeSerach('xklx','许可类型');searchApp();changeHidden();">许可类型</a></li>
             -->
            <li><a href="javascript:void(0)" id="jggs" onclick="changeSerach('jggs','结果公示');searchResult();changeHidden();">结果公示</a></li>
            <li><a href="/correction/correctionDefault.html" id="bqbz" >补齐补正</a></li>
          <!--清除浮动 start--><!--清除浮动 end-->
          </ul>
        </div>
        <!--搜索导航 end--> 
        <!--搜索栏 start-->
        <!-- 事项搜索 -->
        <div class="BS_searchbg" id="sxss">
        <form name="sxssForm" id="sxssForm" method="post" onsubmit="return false;">
          <div class="l"><img src="/images/lineservice/bg_inputl.png" /></div>
          <div class="BS_searchin l">
            	<s:textfield name="approveName" id="approveName" onkeypress="if(isSearch()){searchApp('');};"></s:textfield>
          </div>
          <div class="l"><img src="/images/lineservice/bg_inputr.png" /> </div>
          <!--搜索栏 end-->
          <div class="BS_search01 l BS_marginl10"><a href="javascript:searchApp('')">办事指南</a></div>
          <!--清除浮动 start-->
          <div class="clean"></div>
          <!--清除浮动 end--> 
          <!--BS_words start-->
          <!-- 
          <div class="BS_words"><span>热门搜索词：</span>
	          <s:iterator value="keyWordList" status="kw">
	          	<s:if test="type==1">
	          	<a href="javascript:void(0)" onclick="searchK('sxss','<s:property value="keyword"/>')"><s:property value="keyword"/></a>
	          	</s:if>
	          </s:iterator>
          </div>
           -->
          <!--BS_words end--> 
         </form>
         <form name="bgssForm" id="bgssForm" method="post" style="display:none" action="" onsubmit="return false;">
         <div class="l"><img src="/images/lineservice/bg_inputl.png" /></div>
          <div class="BS_searchin l">
            <input type="text" name="tableTempName" id="tableTempName" onkeypress="if(isSearch()){searchTable();}"/>
          </div>
          <div class="l"><img src="/images/lineservice/bg_inputr.png" /> </div>
          <!--搜索栏 end-->
          <div class="BS_search01 l BS_marginl10"><a href="javascript:void(0)" onclick="searchTable('0');">表格下载</a></div>
          <!--清除浮动 start-->
          <div class="clean"></div>
          <!--清除浮动 end--> 
          <!--BS_words start-->
          <!-- <div class="BS_words" >
          <span>热门搜索词：</span>
          	 <s:iterator value="keyWordList" status="kw">
	          	<s:if test="type==2">
	          	<a href="javascript:void(0)" onclick="searchK('bgss','<s:property value="keyword"/>')"><s:property value="keyword"/></a>
	          	</s:if>
	          </s:iterator>
          </div> -->
          <!--BS_words end--> 
         </form>
         <form name="wsblForm" id="wsblForm" method="post" style="display:none" action="" onsubmit="return false;">
         <div class="l"><img src="/images/lineservice/bg_inputl.png" /></div>
          <div class="BS_searchin l">
            <s:textfield name="wsapproveName" id="wsapproveName" onkeypress="if(isSearch()){searchApp('','0');}"></s:textfield>
          </div>
          <div class="l"><img src="/images/lineservice/bg_inputr.png" /> </div>
          <!--搜索栏 end-->
          <div class="BS_search01 l BS_marginl10"><a href="javascript:void(0)" onclick="searchApp('','0');">网上申报</a></div>
          <!--清除浮动 start-->
          <div class="clean"></div>
          <!--清除浮动 end--> 
          <!--BS_words start-->
          <!-- <div class="BS_words" >
          <span>热门搜索词：</span>
          	 <s:iterator value="keyWordList" status="kw">
	          	<s:if test="type==2">
	          	<a href="javascript:void(0)" onclick="searchK('wsbl','<s:property value="keyword"/>')"><s:property value="keyword"/></a>
	          	</s:if>
	          </s:iterator>
          </div> -->
          <!--BS_words end--> 
         </form>
         <form name="bjcxForm" id="bjcxForm" method="post" style="display:none" action="" onsubmit="return false;">
         <div class="BS_search01 r BS_marginlr10"><a href="javascript:void(0)" onclick="searchBusi('true')">办理查询</a></div>
         <span class="l BS_marginl10 BS_smargin" style="padding-top:3px;">业务受理号：</span>
          <div class="l"><img src="/images/lineservice/bg_inputl.png" /></div>
          <div class="BS_searchin l" style="width:170px;">
            <input type="text" style="width:130px;" name="yxtywlsh" id="yxtywlsh" onkeypress="if(isSearch()){searchBusi('true')}"/>
          </div>
          <div class="l"><img src="/images/lineservice/bg_inputr.png" /> </div>
                  <span class="l BS_marginl10 BS_smargin" style="padding-top:3px;">验证码：</span>
          <div class="l"><img src="/images/lineservice/bg_inputl.png" /></div>
          <div class="BS_searchin l" style="width:50px;">
            <input type="text" style="width:50px;" name="checkCode" id="checkCode" onkeypress="if(isSearch()){searchBusi('true')}"/>
          </div>
          <div class="l"><img src="/images/lineservice/bg_inputr.png" /> </div>
		  <span class="l BS_marginl5 BS_smargin"><img id="bjcxRand" style="cursor:hand" name="imgRand" src="/servlet/CheckCodeServlet?<%=Math.random()%>" width="80"height="28" onclick="this.src='/servlet/CheckCodeServlet?'+Math.random()" title="点击换一张"/></span>
          <!--搜索栏 end-->
          <!--清除浮动 start-->
          <!--<div class="clean"></div> -->
          <!--清除浮动 end--> 
          <!--BS_words start-->
          <!-- 
          <div class="BS_words">您可以使用事项办理的统一查询号，进行事项办理情况的查询。</div>
           -->
          <!--BS_words end--> 
          </form>
          <div  id="xklxForm" style="display:none;text-align:left;" >
          <!-- <div class="BS_search01 r BS_marginlr10"><a href="javascript:void(0)">许可搜索</a></div> -->
	        <span class="l BS_marginl10 BS_smargin">许可类型：
	         <s:radio list="appTypeMap" listKey="key" listValue="value" onclick="searchApp(this.value)" name="approveType" ></s:radio>
	        </span>
          <!--清除浮动 start-->
          <!-- <div class="clean"></div> -->
          <!--清除浮动 end--> 
          <!--搜索栏 end-->
          <!--BS_words start-->
          <!-- <div class="BS_words">您可以通过不同的许可类型进行查询事项。</div> -->
          <!--BS_words end--> 
          </div>
         <form name="jggsForm" id="jggsForm" method="post" style="display:none" action="" onsubmit="return false;">
         <div class="BS_search01 r BS_marginlr10"><a href="javascript:void(0);" onclick="jggsSearchBusi('true')">结果公示</a></div>
         <span class="l BS_marginl10 BS_smargin" style="padding-top:3px;">业务受理号：</span>
          <div class="l"><img src="/images/lineservice/bg_inputl.png" /></div>
          <div class="BS_searchin l" style="width:130px;">
            <input type="text" style="width:170px;" name="jggsywlsh" id="jggsywlsh" onkeypress="if(isSearch()){jggsSearchBusi('true')}"/>
          </div>
          <div class="l"><img src="/images/lineservice/bg_inputr.png" /> </div>
                  <span class="l BS_marginl10 BS_smargin" style="padding-top:3px;">验证码：</span>
          <div class="l"><img src="/images/lineservice/bg_inputl.png" /></div>
          <div class="BS_searchin l" style="width:50px;">
            <input type="text" style="width:50px;" name="jggsCheckCode" id="jggsCheckCode" onkeypress="if(isSearch()){jggsSearchBusi('true')}"/>
          </div>
          <div class="l"><img src="/images/lineservice/bg_inputr.png" /> </div>
		  <span class="l BS_marginl5 BS_smargin"><img id="jggsRand" style="cursor:hand" name="imgRand" src="/servlet/CheckCodeServlet?<%=Math.random()%>" width="80"height="28" onclick="this.src='/servlet/CheckCodeServlet?'+Math.random()" title="点击换一张"/></span>
          <!--搜索栏 end-->
          <!--清除浮动 start-->
          <!--<div class="clean"></div> -->
          <!--清除浮动 end--> 
          <!--BS_words start-->
          <!-- 
          <div class="BS_words">您可以使用事项办理的统一查询号，进行事项办理情况的查询。</div>
           -->
          <!--BS_words end--> 
          </form>
        </div>
        </div>
        <!--外框 end--> 
      </div>
     
   </div>
   
   <!-- ----------- -->
  <div class="BS_docontent" id="BS_docontent"> 
	  <div class="clean" style="margin-top:65px;">&nbsp;</div>

	  <!-- 结果公示查询搜索结果 -->
	  <div class="BS_domiddle" id="jggsSearchApp" style="display:none;">
	  	
	  	<!--top start-->
		    <div class="BS_dotop" style="margin-top: 0px;">&nbsp;</div>
	    <!--top end-->
	    
	    <!--middle start-->
	    <div class="BS_domiddle">
	    	<!--middlein start-->
	  		<div class="BS_domiddlein" id="jggsSearchAppDiv"></div>
	  		<!--middlein end-->
	  	</div>
		<!--middle end-->
	  	
	  	<!-- bottom start -->
		<div class="BS_dobottom">&nbsp;</div>
		<!-- bottom end -->
	  </div>
	  
	  <!-- 其他 -->
      <!--top start-->
      <div class="BS_dotop" style="margin-top:0px;">&nbsp;</div>
      <!--top end-->
      
      <!--middle start-->
      <div class="BS_domiddle"> 
        <!--middlein start-->
        <div class="BS_domiddlein" id="searchAppDiv"> 
	<!--2级导航 start-->
			<!--do header start-->
			<!--<div class="BS_doheader" id="searchParerment">-->
			<!--标题 start-->
			<!--
			<div class="BS_title l" id="itemTypeText">
				办事导航
			</div>
			-->
            <!--标题 end--> 
            <!--导航 start-->
            <!--
            <div class="BS_nav3 l" id="leveOneDiv">
					<ul>
			-->
						<!--住建局合并个人办事、企业办事 <li class="BS_rline">
							<a id="oneMenuA0" onclick="setTwo();changeClass('oneMenuA',0,4,'BS_c','');" href="javascript:changeClassify('{7F000001-FFFF-FFFF-AC41-AFE400000001}','2','images');changeText('个人办事导航');" class="BS_c">个人办事</a>
						</li> 
						<li class="BS_rline">
							<a id="oneMenuA1" onclick="setTwo();changeClass('oneMenuA',1,4,'BS_c','');" href="javascript:changeClassify('{7F000001-FFFF-FFFF-AC41-AFE700000002}','2','images');changeText('企业办事导航');" >企业办事</a>
						</li>-->
						<!-- 
						<li class="BS_rline">
							<a id="oneMenuA0" class="BS_c" onclick="setTwo();changeClass('oneMenuA',0,2,'BS_c','');" href="javascript:changeClassify('{7F000001-FFFF-FFFF-AC41-AFE700000002}','2','images');changeText('办事导航');" >办事导航</a>
						</li>
						<li class="BS_rline">
							<a id="oneMenuA1" onclick="setTwo();changeClass('oneMenuA',1,2,'BS_c','');" href="javascript:changeClassify('','','department');changeText('部门服务');" >部门服务</a>
						</li>
						 -->
						<!--<li class="BS_rline">
							<a id="oneMenuA3" onclick="setTwo();changeClass('oneMenuA',3,4,'BS_c','');" href="javascript:changeClassify('','','street');changeText('街道办服务导航');" >街道办服务</a>
						</li>
					-->
					<!--
					</ul>
					<div class="clean"></div>
			</div>
			-->
			 <!--导航 end--> 
          <!-- </div> -->
          <!--do header end--> 
	<!--2级导航 end-->
	<div id="resultListDIV">
				<!--导航 start-->
				<!-- <div class="BS_subnav2" id="levelTowDIV">
					
				</div> -->
				<!--导航 end-->
				
			<div class="clean">&nbsp;</div>
			<!--do header end-->
			<!--todo start-->

			<div id="approveItemDIV">
		
			</div>
	</div>
	<div class="clean"></div>
	</div>
	</div>
	<!-- bottom start -->
	<div class="BS_dobottom">&nbsp;</div>
	<!-- bottom end -->
	</div>

   <!-- 业务开始----------- -->
   <div class="hiddenDiv" id="hiddenDiv">
   <div class="clean" style="height:16px;"></div>
   <!--
   <br/><br/><br/><br/><br/>
   -->
   <div class="BS_dotop1">&nbsp;</div>
      <!--top end--> 
      <!--middle start-->
      <div class="BS_domiddle">
       <div class="BS_domiddlein1">
     	 <jsp:include page="/WEB-INF/page/onlineservice/rollBusi.jsp"></jsp:include>
       	</div>
      </div>
	<div class="BS_dobottom">&nbsp;</div>
   </div>
<!-- 业务结束----------- -->
   </div>
   
   </div>
   <div class="clean" style="height:8px;"></div>
   <jsp:include page="/WEB-INF/page/public/bottom.jsp"></jsp:include>    
</body>
</html>

  

