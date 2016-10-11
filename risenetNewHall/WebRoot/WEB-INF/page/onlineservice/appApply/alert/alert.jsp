<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<head>
	<link href="/css/main.css" type="text/css" rel="stylesheet" />
</head>
<body align="center">
	<table cellpadding="0" cellspacing="0" border="0" width="100%" id="cardTab1"
		class="BS_infolist" style="font-family:微软雅黑;display:">
		<c:forEach items="${dataList}" var="data">
			<tr>
				<c:if test="${data.key=='服务端查询出错描述'}">
					<c:set var="count" value="1" scope="page"/>
				</c:if>
				<c:if test="${data.key=='<span>注册专业</span>'}">
					<c:set var="count" value="0" scope="page"/> 
				</c:if>
				<td style="width:25%;text-align:center;">${data.key}</td>
				<td>${data.value}&nbsp;</td>
			</tr>
		</c:forEach>
	</table>
	<div>
		<c:if test="${count==0}">
		该人员信息无“<span style="color:red;">注册专业</span>”，请，请按“<span style="color:red;">确定</span>”按钮上传“<span style="color:red;">执业注册证书</span>”<br/>
		如果需要输入新的身份证号，请按“<span style="color:red;">取消</span>”按钮
		</c:if>
		<c:if test="${count!=1&&count!=0}">
		如果信息正确，请按“<span style="color:red;">确定</span>”按钮<br/>
		如果信息不正确，请按“<span style="color:red;">取消</span>”按钮，并输入<span style="color:red;">新编号</span>
		</c:if>
	</div>
</body>