<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<title>深圳市龙岗区住房与建设局产业转型升级专项资金申报系统</title>
	<meta name="keywords"
			content="深圳市龙岗区住房与建设局产业转型升级专项资金申报系统,产业转型,专项资金,产业服务,深圳市龙岗区住房与建设局产业服务,深圳市龙岗区住房与建设局" />
		<meta name="description" content="产业转型首页" />
<meta name="robots" content="all" />
<head>
<link href="/css/main.css" type="text/css" rel="stylesheet" />
<link href="/css/initialize.css" type="text/css" rel="stylesheet" />
<link href="/css/bizbankroll.css" type="text/css" rel="stylesheet" />
<script src="/js/Scripts/jquery.min.js" type="text/javascript" ></script>
<script type="text/javascript">

 
</script>
</head>
<body>
<jsp:include page="/WEB-INF/page/public/topMenu.jsp">
<jsp:param name="menu" value="3"/>
</jsp:include>
<div class="BS_wrap">
<div class="BS_content" id="BS_content_div">
<div class="clean" style="height:10px;"></div>
<jsp:include page="/WEB-INF/page/public/menu.jsp">
<jsp:param name="menu" value="1"/>
</jsp:include>
   </div>
   <!-- 扶持政策开始 -->
  	<div class="biz_l">
  	<div class="BIZ_dotop">&nbsp;</div>
      <div class="BIZ_domiddle">
       <div class="BIZ_domiddlein">
       <div class="biz_title">
       	<div class="biz_l" style="padding-top:5px;">扶持政策</div><div class="biz_more"><a href="/bizNotice/queryPage/ntype/1.html" class="more_img"> </a></div>
       </div>
       	<ul>
     	 <s:iterator value="bizPolicyList" status="law">
     	 	<li><a href="">
	     	 	<s:if test="%{title!=null&&title.length()>27}">
					<s:property value="%{title.substring(0,27)}" />...
				</s:if>
				<s:else>
					<s:property value="title" />
				</s:else>
     	 	 </a> </li>
     	 </s:iterator>
     	 </ul>
       	</div>
      </div>
	<div class="BIZ_dobottom">&nbsp;</div>
  	</div>
  	
  	 <!-- 公告开始 -->
  	 <div class="biz_r">
  	<div class="BIZ_dotop">&nbsp;</div>
      <div class="BIZ_domiddle">
       <div class="BIZ_domiddlein">
       <div class="biz_title">
       	<div class="biz_l" style="padding-top:5px;">通知公告</div><div class="biz_more"><a href="/bizNotice/queryPage/ntype/0.html" class="more_img"></a></div>
       </div>
       	<ul>
     	 <s:iterator value="bizNoticeList" status="law">
     	 	<li><a href="">
	     	 	<s:if test="%{title!=null&&title.length()>27}">
					<s:property value="%{title.substring(0,27)}" />...
				</s:if>
				<s:else>
					<s:property value="title" />
				</s:else>
     	 	 </a> </li>
     	 </s:iterator>
     	 </ul>
       	</div>
      </div>
	<div class="BIZ_dobottom">&nbsp;</div>
  	</div>
  	
  	<!-- 办理动态开始 -->
  	 <div class="biz_l">
  	<div class="busi_dotop">&nbsp;</div>
      <div class="busi_domiddle">
       <div class="busi_domiddlein">
       <div class="biz_title">
       	<div class="biz_l" style="padding-top:5px;">办理动态</div><div class="biz_more"><a href="#" class="more_img">&nbsp;</a></div>
       </div>
       
       	<table cellpadding="0" cellspacing="0" border="0" width="99%"
			class="BS_listroll">
			<tr>
				<th width="16%" style="text-align: center">
					受理编号
				</th>
				<th  style="text-align: center">
					项目名称
				</th>
				<th width="18%" style="text-align: center">
					主管单位
				</th>
				<th width="15%" style="text-align: center">
					申报时间
				</th>
				<th width="10%" style="text-align: center">
					业务状态
				</th>
			</tr>
			<tr><td colspan="5">
			<div id="scrollDiv" style="overflow: hidden;height:240px;width:100%">
					<div id="scrollText">
			<marquee behavior="scroll" onmouseover="this.stop();" onmouseout="this.start();" direction="up" scrollAmount="3" width="100%">
			<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<s:iterator value="bizApplicationList" status="busin">
					<s:if test="(#busin.Index+1)%2==0">
						<tr class="odd">
					</s:if>
					<s:else>
						<tr class="even">
					</s:else>
					<td class="font_right_li_center02" width="16%" style="text-align: center">
						<s:property value="slbh" />
					</td>
					<td class="font_right_li_left02">
						<s:if test="%{pro_name!=null&&pro_name.length()>24}">
							<s:property value="%{pro_name.substring(0,24)}" />...
                  		</s:if>
						<s:else>
							<s:property value="pro_name" />
						</s:else>
					</td>
					
					<td class="font_right_li_center02" width="18%">
						<s:if test="%{zgbmid!=null&&zgbmid.length()>8}">
							<s:property value="%{zgbmid.substring(0,8)}" />...
                  		</s:if>
						<s:else>
							<s:property value="zgbmid" />
						</s:else>

					</td>
					<td class="font_right_li_left02" style="text-align:center" width="15%">
						<s:date name="createtime" format="yyyy-MM-dd"/>
					</td>
					<td class="font_right_li_center02" width="10%">
						<s:property value="pressor_state" />
					</td>
					</tr>
				</s:iterator>
				</table>
				</marquee>
			</div>
					<div id="printDiv"></div>
				</div>
			
			</td></tr>
		</table>
       	
       	</div>
      </div>
	<div class="busi_dobottom">&nbsp;</div>
  	</div>
  	
   </div>
   <div class="clean" style="height:10px;"></div>
   <jsp:include page="/WEB-INF/page/public/bottom.jsp"></jsp:include>    
</body>
</html>

  

