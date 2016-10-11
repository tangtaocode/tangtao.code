<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<style type="text/css">
 .serviceCatalogImg A {
 	font-family: "Microsoft YaHei", "΢微软雅黑", "Segoe UI", Arial;
    FONT-SIZE: 14px;
    HEIGHT: 100px;
    VERTICAL-ALIGN: middle;
    COLOR: black;
    TEXT-ALIGN: left;
    TEXT-DECORATION: none;
    DISPLAY: block;
    WIDTH: 100px
} .serviceCatalogImg A:hover {
	font-family: "Microsoft YaHei", "΢微软雅黑", "Segoe UI", Arial;
    FONT-SIZE: 14px;
    HEIGHT: 100px;
    VERTICAL-ALIGN: top;
    BACKGROUND-IMAGE: url(/images/lineservice/scroll_item_hover.png);
    BACKGROUND-REPEAT: repeat-x;
    TEXT-DECORATION: none;
    WIDTH: 100px
}
.itemType{
	text-align:left;
	font-family: "Microsoft YaHei", "΢微软雅黑", "Segoe UI", Arial;
    FONT-SIZE: 20px;
    HEIGHT: 10px;
}
hr{
	height:1px;border:none;border-top:1px dashed #C6C6C6;
}
.valhr{
	border-left:solid 1px silver;
	height:90px;
    float: left;
}
</style>
</head>
<body>
<div class="BS_todos serviceCatalogImg">
	<div class="itemType" style="padding-right:200px;float:left;padding-left:25px;">个人办事：</div>
	<div class="itemType" style="float:left;">企业办事：</div><br/><br/>
	<hr>
	<div class="clean"></div>
	<s:iterator value="classifyList" status="item">
	<s:if test="pid=='{7F000001-FFFF-FFFF-AC41-AFE400000001}'">
		<div class="BS_todo" style="padding-right:200px;padding-left:25px;">
		<s:if test="createuser==0">
		<a href="javascript:void(0);"> 
			<img src="/images/approveitem<s:property value="imagename"/>.png" width="60" height="60" />
				<span><s:property value="name"/></span> </a>
		</s:if>
		<s:else>
		<a href="javascript:searchAppByImage('<s:property value="guid"/>');"> 
			<img src="/images/approveitem<s:property value="imagename"/>.png" width="60" height="60" />
				<span><s:property value="name"/></span> </a>
		</s:else>
			
		</div>
	</s:if>
	</s:iterator>
	
	<div class="valhr"></div>
	
	<s:iterator value="classifyList" status="item">
	<s:if test="pid=='{7F000001-FFFF-FFFF-AC41-AFE700000002}'">
		<div class="BS_todo">
		
		<s:if test="createuser==0">
		<a href="javascript:void(0);"> 
			<img src="/images/approveitem<s:property value="imagename"/>.png" width="60" height="60" />
				<span><s:property value="name"/></span> </a>
		</s:if>
		<s:else>
		<a href="javascript:searchAppByImage('<s:property value="guid"/>');"> 
			<img src="/images/approveitem<s:property value="imagename"/>.png" width="60" height="60" />
				<span><s:property value="name"/></span> </a>
		</s:else>
			
		</div>		
	</s:if>
	</s:iterator>
	
	<!-- <div class="clean"></div>
	<div class="itemType">企业办事</div>
	<hr>
	<div class="clean"></div> -->
	
</div>


</body>
</html>

  

