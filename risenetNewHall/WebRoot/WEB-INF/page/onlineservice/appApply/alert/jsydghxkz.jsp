<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.util.Map"%>
<%@page import="net.risesoft.utils.base.WebUtil"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>

<head>
	<link href="/css/main.css" type="text/css" rel="stylesheet" />
	<style type="text/css">
		th{text-align:center;}
	</style>
</head>
<body align="center">
	<table cellpadding="0" cellspacing="0" border="0" width="100%" id="cardTab1"
		class="BS_infolist" style="font-family:微软雅黑;display:">
			<%
			Map map = (Map)request.getAttribute("dataMap");
			if(map.get("state").equals("1")){
			%>
			<tr>
				<th colspan="4" style="font-size:22px;height:40px;">深圳市建设用地规划许可证</th>
			</tr>
			<tr>
				<th>规划许可证号</th>
				<td><%=WebUtil.changeNull(map.get("LU_PERMISSION_NO").toString()) %>&nbsp;</td>
				<th>发证日期</th>
				<td><%=WebUtil.changeNull(map.get("ISSUE_DATA").toString()) %>&nbsp;</td>
			</tr>
			<tr>
				<th>用地单位</th>
				<td colspan="3"><%=WebUtil.changeNull(map.get("UNIT_NAME").toString()) %>&nbsp;</td>
			</tr>
			<tr>
				<th>用地位置</th>
				<td><%=WebUtil.changeNull(map.get("LU_LOCATION").toString()) %>&nbsp;</td>
				<th>地块编号</th>
				<td><%=WebUtil.changeNull(map.get("RL_REDLIEN_NO").toString()) %>&nbsp;</td>
			</tr>
			<tr>
				<th>用地项目名称</th>
				<td><%=WebUtil.changeNull(map.get("LU_PROJ_TITLE").toString()) %>&nbsp;</td>
				<th>用地性质</th>
				<td><%=WebUtil.changeNull(map.get("LU_FUNCTION").toString()) %>&nbsp;</td>
			</tr>
			<tr>
				<th>总用地面积</th>
				<td colspan="3"><%=WebUtil.changeNull(map.get("TOTAL_LU_AREA").toString()) %>&nbsp;</td>
			</tr>
			<tr>
				<th>建设用地面积</th>
				<td><%=WebUtil.changeNull(map.get("DEVEL_LU_AREA").toString()) %>&nbsp;</td>
				<th>绿地面积</th>
				<td><%=WebUtil.changeNull(map.get("GREEN_AREA").toString()) %>&nbsp;</td>
			</tr>
			<tr>
				<th>道路用地面积</th>
				<td><%=WebUtil.changeNull(map.get("ROAD_AREA").toString()) %>&nbsp;</td>
				<th>其他用地面积</th>
				<td><%=WebUtil.changeNull(map.get("OTHER_LU_AREA").toString()) %>&nbsp;</td>
			</tr>
			<%
			}else if(map.get("state").equals("2")){
			%>
			<tr>
				<th>错误信息</th>
				<td><%=WebUtil.changeNull(map.get("ErrorMessage").toString()) %>&nbsp;</td>
			</tr>
			<%
			}
			%>
	</table>
</body>