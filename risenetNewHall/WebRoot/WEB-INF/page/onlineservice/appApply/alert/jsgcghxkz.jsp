<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.util.Map"%>
<%@page import="net.risesoft.utils.base.WebUtil"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>

<head>
	<link href="/css/main.css" type="text/css" rel="stylesheet" />
	<style type="text/css">
		th{text-align:center;}
		td.center{text-align:center;}
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
				<th colspan="6" style="font-size:22px;height:40px;">深圳市建设工程规划许可证</th>
			</tr>
			<tr>
				<th width="20%">工程许可证编号</th>
				<td colspan="2"><%=WebUtil.changeNull(map.get("LICENSE_NO").toString()) %>&nbsp;</td>
				<th width="15%">发证日期</th>
				<td colspan="2"><%=WebUtil.changeNull(map.get("LICENSE_DATA").toString()) %>&nbsp;</td>
			</tr>
			<tr>
				<th>用地单位</th>
				<td colspan="5"><%=WebUtil.changeNull(map.get("UNIT_NAME").toString()) %>&nbsp;</td>
			</tr>
			<tr>
				<th>用地项目名称</th>
				<td colspan="5"><%=WebUtil.changeNull(map.get("PROJ_NAME").toString()) %>&nbsp;</td>
			</tr>
			<tr>
				<th>用地位置</th>
				<td colspan="2"><%=WebUtil.changeNull(map.get("LOCATION").toString()) %>&nbsp;</td>
				<th>宗地号</th>
				<td colspan="2"><%=WebUtil.changeNull(map.get("PARCEL_NO").toString()) %>&nbsp;</td>
			</tr>
			<tr>
				<th>子项名称</th>
				<th>建筑性质</th>
				<th>栋数</th>
				<th>层数</th>
				<th>计容积率面积</th>
				<th>不计容积率面积</th>
			</tr>
			<tr>
				<td class="center"><%=WebUtil.changeNull(map.get("BLDG_ITEM_NAME").toString()) %>&nbsp;</td>
				<td class="center"><%=WebUtil.changeNull(map.get("BLDG_FUNC").toString()) %>&nbsp;</td>
				<td class="center"><%=WebUtil.changeNull(map.get("BLDG_NUM").toString()) %>&nbsp;</td>
				<td class="center"><%=WebUtil.changeNull(map.get("FLOOR_NUM").toString()) %>&nbsp;</td>
				<td class="center"><%=WebUtil.changeNull(map.get("PR_AREA").toString()) %>&nbsp;</td>
				<td class="center"><%=WebUtil.changeNull(map.get("NO_PR_AREA").toString()) %>&nbsp;</td>
			</tr>
			<tr>
				<th rowspan="4">计容积率建筑面积分项指标</th>
				<th width="15%">规定建筑面积</th>
				<td colspan="1"><%=WebUtil.changeNull(map.get("AREA_RULE").toString()) %>&nbsp;</td>
				<th>核增建筑面积</th>
				<td colspan="2"><%=WebUtil.changeNull(map.get("AREA_ADD").toString()) %>&nbsp;</td>
			</tr>
			<tr>
				<th>规定建筑功能</th>
				<td colspan="1"><%=WebUtil.changeNull(map.get("RULE_FUNC").toString()) %>&nbsp;</td>
				<th>建筑功能</th>
				<td colspan="2"><%=WebUtil.changeNull(map.get("ADD_FUNC").toString()) %>&nbsp;</td>
			</tr>
			<tr>
				<th>核减建筑面积</th>
				<td colspan="1"><%=WebUtil.changeNull(map.get("AREA_REDUCE").toString()) %>&nbsp;</td>
				<th>奖励建筑面积</th>
				<td colspan="2"><%=WebUtil.changeNull(map.get("AREA_AWARD").toString()) %>&nbsp;</td>
			</tr>
			<tr>
				<th>核减建筑功能</th>
				<td colspan="1"><%=WebUtil.changeNull(map.get("REDUCE_FUNC").toString()) %>&nbsp;</td>
				<th>奖励建筑功能</th>
				<td colspan="2"><%=WebUtil.changeNull(map.get("AWARD_FUNC").toString()) %>&nbsp;</td>
			</tr>
			<tr>
				<th>停车位</th>
				<th>地上</th>
				<td colspan="1"><%=WebUtil.changeNull(map.get("PUB_PARK_SEATS").toString()) %>&nbsp;</td>
				<th>地下</th>
				<td colspan="2"><%=WebUtil.changeNull(map.get("PRIV_PARK_SEATS").toString()) %>&nbsp;</td>
			</tr>
			<tr>
				<th>备注</th>
				<td colspan="5"><%=WebUtil.filter(WebUtil.changeNull(map.get("REMARK").toString())) %>&nbsp;</td>
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