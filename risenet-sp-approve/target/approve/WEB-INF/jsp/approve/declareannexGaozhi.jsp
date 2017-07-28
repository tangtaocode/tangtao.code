<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="${ctx }/static/risesoft/css/riseform.css" rel="stylesheet" type="text/css" ></link>
<script language="JavaScript"> 
	  self.moveTo(0,0);
	  self.resizeTo(screen.availWidth,screen.availHeight);
</script>
 
<title>一次性补交告知单</title>

</head>
<body style="font-family: '微软雅黑';font-size: 15px;">
 
 
<OBJECT classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2 height=0 id=WB width=0></OBJECT>
<script language="JavaScript"> 

function print()
{
	var id = $("#instanceId").val();
	window.open("${ctx}/onlineApprove/adviceForm?method=printAnnex&instanceId="+id,"打印一次性补交告知单","800","500");
}
</SCRIPT>
 
<div id=actionbar align=center >
<INPUT type="button" value="我要打印" onclick="print()" style="border:none" class="button orange">
</div>
	<div style="height: 10px;"></div>
	<table  width="90%" align="center"  cellspacing="0" cellpadding="0" class="guide_table MT15" border="1" bordercolor="#dedede" style="border-collapse: collapse;">
		<tr><th colspan="4"><font id="title2">申请材料告知单</font></th>
		<tr>
			
			<td width="20%" align="center">事项名称：</td>
			<td colspan="3">${map.person }</td>
		</tr>
		<tr>
			<td align="center">办理类型：</td><td id="lxdh2">${map.itemname }</td>
			<td align="center" width="20%">告知时间：</td><td id="gzsj2">${adviceTime }</td>
		</tr>
		<tr>
		<td align="left" colspan="4">材料列表:</td>
		</tr>
		<tr>
			<td align="center">已提交</td>
			<td colspan="3">
			<ul>
			<c:if test="${!empty list }">
				<c:forEach items="${list }" var="materail" varStatus="s">
					<c:if test="${material.type==1 }">
						<li>${s.index+1 }、${material.materialname };</li>
					</c:if>
				</c:forEach>
			</c:if>
			</ul>
			</td>
		</tr>
				
		<tr>
			<td align="center">需补齐</td>
			<td colspan="3">
			<ul>
			<c:if test="${!empty list }">
				<c:forEach items="${list }" var="materail" varStatus="s">
				<c:if test="${material.type==0 }">
					<li>${s.index+1 }、${material.materialname };</li>
				</c:if>
				</c:forEach>
			</c:if>
			</ul>
			</td>
		</tr>
		<tr>
			<td colspan="4">注意：当事人承诺所递交的材料真实有效。如提供虚假材料的，将被列入问题名单，一年内不能办理。 </td>
		</tr>
		<tr>
			<td align="center" colspan="4">工作人员：${name }
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			咨询电话：${tel }
			</td>
		</tr>
	</table>
</body>
</html>
