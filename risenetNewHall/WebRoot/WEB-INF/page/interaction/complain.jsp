<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<style type="text/css">
body{
font-family: "Microsoft YaHei", "΢微软雅黑", "Segoe UI", Arial;
}
</style>
</head>
<body> 
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="monitor_table" >
<tr> 
	<th colspan="2">
		<span style="padding-top:15px;padding-right:10px;float:right">
		<input type="button" class="buttonClass_2" value="返 回" onclick="searchComplain('<s:property value="page"/>')"></span>
	</th>
</tr>
  			<tr>
      			<th width="20%" style="text-align:right">投诉标题：</th>
      			<td width="80%" style="text-align:left"> 
      			<s:property value="complain.title" ></s:property></td>
  			</tr>
  			<tr>
  				<th width="20%" style="text-align:right">投诉内容：</th>
      			<td width="80%" style="text-align:left"><s:property value="complain.content"></s:property>
				</td>
  			</tr>
  			<tr>
  				<th width="20%" style="text-align:right">投诉时间：</th>
      			<td width="80%" style="text-align:left"> 
      			<s:date name="complain.complaindate" format="yyyy-MM-dd"/>
      			</td>
  			</tr>
  			<s:iterator value="complain.comResList" status="comp">
  			<tr>
  			<th width="20%" style="text-align:right">回复内容：</th>
  			<td width="80%" style="text-align:left">
  			<s:property value="content"/>
				<span style="color:red">回复时间：<s:date name="createtime" format="yyyy-MM-dd"/></span>				
				</td>
  			</tr>
  			</s:iterator>
		  </table>
</body>
</html>
