<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>广东省网上办事大厅深圳市龙岗区住房与建设局-政民互动</title>
<meta name="keywords" content="深圳市龙岗区住房与建设局网上办事大厅-政民互动,深圳市龙岗区住房和建设局办事大厅政民互动,政民互动,深圳市龙岗区住房和建设局政民互动"></meta>
<meta name="description" content="广东省网上办事大厅深圳市龙岗区住房与建设局-政民互动首页"></meta>
<meta name="robots" content="all" />
<script src="/js/Scripts/jquery.min.js" type="text/javascript"></script>
<style type="text/css">
	
    .orange
    {
        color: #FF5500;
    }
    .simple-autocomplete-container
    {
        border: 1px solid #ddd;
        box-shadow: 1px 1px 3px rgba(200,200,200,0.8);
        background: #fff;
    }
    .simple-autocomplete-select
    {
        background: #f1f1f1;
    }
    .simple-autocomplete-container li
    {
        padding: 3px 10px;
    }
</style>
<link href="/css/bsdtZmhdIndex.css" type="text/css" rel="stylesheet" />
</head>
<body style="background-color:#fff;background-image:url('/images/govpublic/background.jpg');background-position:center top;background-repeat:repeat-y; background-attachment:fixed;">
    <div id="pagecontainer" style="width:1000px;margin:0 auto">
        <div id="we7layout_13452862291999" class="sf_cols">
 <div style="float: none; width: 100%; margin: 0; ;" class="sf_colsOut"><div class="sf_colsIn ">

<div class="SD_Top ">
    <jsp:include page="/WEB-INF/page/public/topMenu.jsp">
    <jsp:param name="menu" value="4"/>
    </jsp:include>
</div>
</div></div>
<div style= "clear:both;height:0;font-size:1px;"></div></div><div id="we7layout_134528622578068" class="sf_cols">
 <div style="float: none; width: 100%; margin: 0; ;" class="sf_colsOut"><div class="sf_colsIn ">
<div class="GovOpenIndex ">
    <div>
        <ul >
            <div class="content">
             <div style="width: 90%; margin: 10px auto;">
                    <img src="/images/govpublic/hr.png" width="100%" height="10px" /></div>
                <div class="content_DIV">
                    <table border="0" cellspacing="0" cellpadding="0" width="95%">
                        <tr>
                            <td rowspan="3" style="width:60%;text-align:center;background:url(/images/interaction/bg_plateitem.jpg)">
							<img src="/images/interaction/zmhd.png"  usemap="#interaction" border="0">    
								<map id="interaction" name="interaction">
									<area shape="circle" coords="190,47,39" href="/interaction/appraiseAction.html" title="网上评议" />
									<area shape="circle" coords="342,48,41" href="http://www.szlh.gov.cn/main/zmhd/jyxc/index.shtml" target="_blank" title="民意征集" />
									<area shape="circle" coords="462,170,49" href="/interaction/queryResult.html" title="我要查询" />
									<area shape="circle" coords="337,278,51" href="http://www.szlh.gov.cn/talk/" target="_blank" title="在线访谈" />
									<area shape="circle" coords="181,280,53" href="http://service.szlh.gov.cn:8088/zxts.jsp" target="_blank" title="网上咨询" />
									<area shape="circle" coords="77,156,46" href="http://service.szlh.gov.cn:8088/ywts.jsp" target="_blank" title="网上投诉" />
									<!--<area shape="rect" coords="208,102,317,221" href="#" title="政民互动" />
								--></map>
                            </td>
                            <td>
                          
              <table width="400px" border="0" cellspacing="0" cellpadding="0" style="border:1px #ccc solid">
              	<tr>
                	<td height="32" background="/images/interaction/bsdt_new6.jpg">
                	<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
	                  <tr>
	                    <td width="30%"  class="index_text2">网上投诉</td>
	                    <td  align="right" valign="bottom">&nbsp;</td>
	                    <td width="20%"  class="index_text3"><a href="/interaction/queryComplain.html">更多>></a> </td>
	                  </tr>
                	</table></td>
              </tr>
              <tr>
                <td valign="top" style="padding:8px">
								<div id="scrollDiv" style="overflow: hidden;height:120px;">
									<div class="scrollText1" id="scrollText">
										<table cellspacing="0" cellpadding="0" border="0" width="99%">
										<s:iterator value="compList" status="comp">
										   <tr>
										      <td width="80%" align="left" style="font-size:14px;line-height:25px;">
										      <a href="/interaction/queryComplain/guid/<s:property value="complainguid"/>.html" title='<s:property value="title"/>'>
										      <s:if test="%{title!=null&&title.length()>20}">
										      	<s:property value="%{title.substring(0,20)}"/>. . .
										      </s:if>
										      <s:else>
										      	<s:property value="title"/>
										      </s:else>
										      </a></td>
										      <td align="right" style="font-size:12px;line-height:25px;"><s:date name="complaindate" format="yyyy-MM-dd"/> </td>
										   </tr>
										  </s:iterator>
									</table>	
									</div>
									<div id="printDiv"></div>
								</div>				
				</td>
              </tr>
            </table>
      
                          </td>
                         </tr>
                            <tr><td style="height:10px;">&nbsp; </td> </tr>
                            
                            <tr>
                             <td >
               <table width="400px" border="0" cellspacing="0" cellpadding="0" style="border:1px #ccc solid">
              	<tr>
                	<td height="32" background="/images/interaction/bsdt_new6.jpg">
                	<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
	                  <tr>
	                    <td width="30%" class="index_text2">网上咨询</td>
	                    <td  align="right" valign="bottom">&nbsp;</td>
	                    <td width="20%" class="index_text3"><a href="/interaction/queryConsult.html">更多>></a></td>
	                  </tr>
                	</table></td>
              </tr>
              <tr>
                <td valign="top" style="padding:8px">
								<div id="scrollDiv1" style="overflow: hidden;height:120px;">
									<div class="scrollText1" id="scrollText1">
										<table cellspacing="0" cellpadding="0" border="0" width="99%">
										  <s:iterator value="consList" status="conp">
										   <tr>
										      <td width="80%" align="left" style="font-size:14px;line-height:25px;">
										      <a href="/interaction/queryConsult/guid/<s:property value="consultationguid"/>.html" title='<s:property value="title"/>'>
										      <s:if test="%{title!=null&&title.length()>20}">
										      	<s:property value="%{title.substring(0,20)}"/>. . .
										      </s:if>
										      <s:else>
										      	<s:property value="title"/>
										      </s:else>
										      </a></td>
										      <td align="right" style="font-size:12px;line-height:25px;"><s:date name="consultationdate" format="yyyy-MM-dd"/> </td>
										   </tr>
										  </s:iterator>
									</table>	
									</div>
									<div id="printDiv1"></div>
								</div>				
				</td>
              </tr>
            </table>
                            </td>
                            </tr>
                    </table>
                </div>
                 <div style="width: 90%; margin: 10px auto;">
                    <img src="/images/govpublic/hr.png" width="100%" height="10px" /></div>
                 <div class="top_title">
                 &nbsp;
                    </div>
            </div>
        </ul>
    </div>
</div>
</div></div>
<div style= "clear:both;height:0;font-size:1px;"></div></div><div id="we7layout_134528622069339" class="sf_cols">
 <div style="float: none; width: 100%; margin: 0; ;" class="sf_colsOut"><div class="sf_colsIn ">

<jsp:include page="/WEB-INF/page/public/bottom.jsp"></jsp:include>    
</div></div>
<div style= "clear:both;height:0;font-size:1px;"></div></div>
    </div>
</body>
</html>
<script type="text/javascript">
var speed=40 //调整滚动速度
printDiv.innerHTML=scrollText.innerHTML;
function Marquee(){
	if(printDiv.offsetTop-scrollDiv.scrollTop<=0){
		scrollDiv.scrollTop-=scrollText.offsetHeight;
	}else{
		scrollDiv.scrollTop++;
	}
}
var MyMar=setInterval(Marquee,speed);
scrollDiv.onmouseover=function() {
	clearInterval(MyMar);
}
scrollDiv.onmouseout=function() {
	MyMar=setInterval(Marquee,speed);
}
////////////////////////
printDiv1.innerHTML=scrollText1.innerHTML;
function Marquee1(){
	if(printDiv1.offsetTop-scrollDiv1.scrollTop<=0){
		scrollDiv1.scrollTop-=scrollText1.offsetHeight;
	}else{
		scrollDiv1.scrollTop++;
	}
}
var MyMar1=setInterval(Marquee1,speed);
scrollDiv1.onmouseover=function() {
	clearInterval(MyMar1);
}
scrollDiv1.onmouseout=function() {
	MyMar1=setInterval(Marquee1,speed);
}
</script>