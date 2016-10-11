<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link href="/css/login-box.css" type="text/css" rel="stylesheet" />
<style type="text/css">
body{
font-family: "Microsoft YaHei", "΢微软雅黑", "Segoe UI", Arial;
}
</style>
</head>
<body> 
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="monitor_table" >
<tr> 
	<th colspan="2" style="text-align:center;font-size:18px;color:#0F64A7">
		咨询部门：<s:property value="consult.bureauname"/> &nbsp; &nbsp;咨询时间：<s:date name="consult.consultationdate" format="yyyy-MM-dd"/>
		<span style="padding-top:15px;padding-right:10px;float:right"><input type="button" class="buttonClass_2" value="返 回" onclick="searchConsult('<s:property value="page"/>')"></span>
	</th>
</tr>
  			<tr>
      			<th width="20%" style="text-align:right">咨询标题：</th>
      			<td width="80%" style="text-align:left"> 
      			<s:property value="consult.title" ></s:property></td>
  			</tr>
  			<tr>
  				<th width="20%" style="text-align:right">咨询内容：</th>
      			<td width="80%" style="text-align:left"><s:property value="consult.content"></s:property>
				</td>
  			</tr>
  			<tr>
  				<th width="20%" style="text-align:right">
  				回复内容：
  				</th>
      			<td width="80%" style="text-align:left"> <s:property value="consult.recontent" escape="flase"></s:property>
				</td>
  			</tr>
  			<tr>
  				<th width="20%" style="text-align:right">回复时间：</th>
      			<td width="80%" style="text-align:left"> 
      			<s:date name="consult.responsedate" format="yyyy-MM-dd"/>
      			</td>
  			</tr>
		  </table>
</body>
</html>
