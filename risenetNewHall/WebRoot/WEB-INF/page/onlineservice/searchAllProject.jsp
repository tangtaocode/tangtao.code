<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base target="_self"/>
<link href="/css/main.css" type="text/css" rel="stylesheet" />
<script src="/js/Scripts/jquery.min.js"></script>
<script type="text/javascript">
  //function changeType(){
  //	changeOrder('orderCode','app');
  //	searchApp($("#approveType").val(),$("#isOnlineApply").val());
  //}
  function topage(pageNum){
	searchResult(pageNum);
  }
  
  function searchResult(pageNum){
	document.forms[0].page.value=pageNum;
	document.forms[0].action='/onlineService/searchAllProject.html';
	document.forms[0].submit();
 }
 function selectProjectInfo(pro_id,pro_name,pro_type){
 	var ret = new Object();
 	ret.pro_id = pro_id;
 	ret.pro_name = pro_name;
 	ret.pro_type = pro_type;
 	window.returnValue = ret;
	window.close();
 }
</script>
<style type="text/css">
<!--
-->
</style>
</head>
	<body>
	<form action="/onlineService/searchAllProject.html">
	<br/>
	&nbsp;项目名称:<input type="text" name="projectName" value="<%=request.getAttribute("projectName")!=null?request.getAttribute("projectName"):"" %>"/>
	&nbsp;项目编号:<input type="text" name="projectId" value="<%=request.getAttribute("projectId")!=null?request.getAttribute("projectId"):"" %>"/>
	&nbsp;项目类型:
	<select name="projectType">
	<option value="">--请选择--</option>
	<option value="0" <%=request.getAttribute("projectType")!=null&&request.getAttribute("projectType").equals("0")?"selected":"" %>>一般项目</option>
	<option value="1" <%=request.getAttribute("projectType")!=null&&request.getAttribute("projectType").equals("1")?"selected":"" %>>重大项目</option>
	</select>
	<input type="button" onclick="searchResult(1);" value=" 查 询 ">
	<br/>
	<s:hidden name="page" id="pageCount"></s:hidden>
  	<s:hidden name="orderType" id="orderType"></s:hidden>
 	<s:hidden name="ascOrDesc" id="ascOrDesc"></s:hidden>
 	<s:hidden name="approveType" id="approveType"></s:hidden>
  <div class="BS_depm">
  		  	
		<table cellpadding="0" cellspacing="0" border="0" width="99%"
			class="BS_list">
			<tr>
				<th width="16%" style="text-align: center">
					项目编号
				</th>
				<th width="32%" style="text-align: center">
					项目名称
				</th>
				<th style="text-align: center">
					建设单位
				</th>
				<th width="12%" style="text-align: center">
					项目类型
				</th>
				<th width="8%" style="text-align: center">
					操作
				</th>
			</tr>
				<s:iterator value="projectList" status="busin">
					<s:set name="list_count" value="1"/>
					<s:if test="(#busin.Index+1)%2==0">
						<tr class="odd">
					</s:if>
					<s:else>
						<tr class="even">
					</s:else>
					<td class="font_right_li_center02" style="text-align: center">
						<s:property value="item_id" />						
					</td>
					<td class="font_right_li_left02">
						<s:property value="item_name" />
					</td>
					
					<td class="font_right_li_left02">					
						<s:property value="const_org" />						
					</td>
					<td class="font_right_li_left02" style="text-align:center">
						<s:if test="item_type==0">
							一般项目
	                  	</s:if>
	                  	<s:if test="item_type==1">
							重大项目
	                  	</s:if>
					</td>
					<td class="font_right_li_center02">
						<a href="#" onclick="selectProjectInfo('<s:property value="item_id" />','<s:property value="item_name" />','<s:property value="item_type" />')">选择</a>
					</td>
				</s:iterator>
				<s:if test="#list_count!=1">
					<td colspan="5" class="font_right_li_left02">根据项目名称/项目编号/项目类型，交易系统内未找到相关项目信息，如未在交易系统内报建项目信息，请先报建项目信息。【<a href="javascript:void(0);">项目报建</a>】</td>
				</s:if>
				<tr>
	  				<td align="center" colspan="5" class="pageLinkTd">
						<%@ include file="/WEB-INF/page/share/page.jsp"%>
					</td>
				</tr>
		</table>
		</form>
	</body>
</html>
