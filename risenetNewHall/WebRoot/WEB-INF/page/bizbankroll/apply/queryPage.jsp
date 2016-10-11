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
<link href="/css/bizbankroll.css" type="text/css" rel="stylesheet" />
<link href="/css/login-box.css" type="text/css" rel="stylesheet" />
<script src="/js/Scripts/jquery.min.js" type="text/javascript" ></script>
<script src="/js/businessJS/bizbankroll.js" type="text/javascript" ></script>
</head>
<body>
<jsp:include page="/WEB-INF/page/public/topMenu.jsp">
<jsp:param name="menu" value="3"/>
</jsp:include>
<div class="BS_wrap">
<div class="BS_content" id="BS_content_div">
<div class="clean" style="height:10px;"></div>
<jsp:include page="/WEB-INF/page/public/menu.jsp">
<jsp:param name="menu" value="4"/>
</jsp:include>
   </div>
   <!-- 查询 -->
    <table border="0" cellpadding="0" cellspacing="15px" style="width: 98%;">
		<tr>
			<td align="left"  onclick="aa('aaaa')">
				<span style="font-size: 18pt;">在线申报</span>
			</td>
			<td align="right">
				&nbsp;
			</td>
		</tr>
	</table>
  	<div class="busi_dotop">&nbsp;</div>
      <div class="busi_domiddle">
       <div class="more_domiddlein">
		<div id="resultDiv" >
			<table cellpadding="0" cellspacing="0" border="0" width="100%" 
						class="BS_list">
						<tr>
			                 <th style="text-align: center">扶持类型名称</th>
			                 <th width="15%" style="text-align: center">更新时间</th>
			                 <th width="15%" style="text-align: center">在线申报</th>
						</tr>
						
				<s:if test="projectList">
         			<s:iterator value="projectList" status="entity">
         			<s:if test="(#entity.Index+1)%2==0"><tr class="odd" onmouseout="this.className='odd'" onmouseover="this.className='clickHand'"></s:if>
         			<s:else><tr class="even" onmouseout="this.className='even'" onmouseover="this.className='clickHand'"></s:else>
         			
			           <td isOpen="0" retract="3" rootGuid="<s:property value="guid"/>" onclick="searchProType('<s:property value="guid"/>',this)">
				            	<img class="ProTypeImg" src="/images/bizbankroll/folder_c.png"/>
				            	&nbsp;&nbsp;<s:property value="name"/>
			            	</td>
			         
         			<td><s:date name="uptime" format="yyyy-MM-dd"/> </td>
         			<td>
         				<s:if test="status==0">
							<a target="_blank" href="<s:property value="applicationformurl"/>" class="BS_btnblue" >在线申报</a>
						</s:if>
						<s:else>&nbsp;</s:else>
         			</td>
         		</tr>
          			</s:iterator>
				</s:if>
				<s:else>
					<tr>
         				<td colspan="6" class="noDateText">无记录！</td></tr>
				</s:else>
					</table>
		</div>
       	</div>
      </div>
	<div class="busi_dobottom">&nbsp;</div>
  	</div>
   <div class="clean" style="height:10px;"></div>
   <jsp:include page="/WEB-INF/page/public/bottom.jsp"></jsp:include>    
</body>
</html>

  

