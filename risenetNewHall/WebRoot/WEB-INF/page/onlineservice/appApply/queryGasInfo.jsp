<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>My JSP 'queryGasInfo.jsp' starting page</title>

		<style>
table {
	border-top: #9DC3DA solid 1px;
	border-left: #9DC3DA solid 1px;
	text-align: center;
}

td {
	border-bottom: #9DC3DA solid 1px;
	border-right: #9DC3DA solid 1px;
	font: 12px; 宋体;
	height: 40px;
}
</style>

	</head>

	<body>
		  <% Map map = (Map)request.getAttribute("GasInfoMap"); %>
		<div align="center">
			   				<% if(map.get("state").equals("1")){ %>
			<div style="width: 529px; border: 2px solid #9DC3DA;">
				<br />
				<br />
				<h4>
					企业信息
				</h4>
				<table width="529" cellpadding="0" cellspacing="0"style="margin: 5px;">
					<tr>
						<td width="101">组织机构代码<%=map.get("state")%> </td>
						<td width="127">
							<input type="hidden" id="organization_no" value="<%=map.get("organization_no") %>"><%=map.get("organization_no") %>
						</td>
						<td width="129">法定代表人</td>
						<td width="152">	
							<input type="hidden" id="fddbr" value="<%=map.get("fddbrname")==null||map.get("fddbrname").equals("null")?"":map.get("fddbrname") %>">
							<%=map.get("fddbrname")==null||map.get("aqfzrname").equals("null")?"":map.get("aqfzrname") %>				
						</td>
					</tr>
					<tr>
						<td>联系人</td>
						<td>
							<input type="hidden" id="contactor" value="<%=map.get("contactor") %>"><%=map.get("contactor") %>
						</td>
						<td>联系电话</td>
						<td>
							<input type="hidden" id="contactor_phone" value="<%=map.get("contactor_phone") %>"><%=map.get("contactor_phone") %>
						</td>
					</tr>
					<tr>
						<td>主要负责人</td>
						<td>				
							<input type="hidden" id="zyfzr" value="<%=map.get("zyfzrname")==null||map.get("zyfzrname").equals("null")?"":map.get("zyfzrname") %>">
							<%=map.get("zyfzrname")==null||map.get("zyfzrname").equals("null")?"":map.get("zyfzrname") %>					
						</td>
						<td>企业负责人</td>
						<td>						
							<input type="hidden" id="qyfzr" value="<%=map.get("qyfzrname")==null||map.get("qyfzrname").equals("null")?"":map.get("qyfzrname")%>">
							<%=map.get("qyfzrname")==null||map.get("qyfzrname").equals("null")?"":map.get("qyfzrname")%>						
						</td>
					</tr>
					<tr>
						<td>技术负责人</td>
						<td>				
							<input type="hidden" id="jsfzrname" value="<%=map.get("jsfzrname") ==null||map.get("jsfzrname").equals("null")?"":map.get("jsfzrname")%>">
							<%=map.get("jsfzrname")==null||map.get("jsfzrname").equals("null")?"":map.get("jsfzrname") %>					
						</td>
						<td>安全负责人</td>
						<td>					
							<input type="hidden" id="aqfzrname" value="<%=map.get("aqfzrname")==null||map.get("aqfzrname").equals("null")?"":map.get("aqfzrname")%>">
							<%=map.get("aqfzrname")==null||map.get("aqfzrname").equals("null")?"":map.get("aqfzrname") %>	
						</td>
					</tr>
					<tr>
						<td>注册资本</td>
						<td>
							<input type="hidden" id="register_capital" value="<%=map.get("register_capital") %>"><%=map.get("register_capital") %>
						</td>
						<td>服务电话</td>
						<td>
							<input type="hidden" id="service_phone" value="<%=map.get("service_phone") %>"><%=map.get("service_phone") %>
						</td>
					</tr>
					<tr>
						<td>申请单位</td>
						<td colspan="3">
							<input type="hidden" id="qyname" value="<%=map.get("qyname") %>"><%=map.get("qyname") %>
						</td>
					</tr>
					<tr>
						<td>企业名称</td>
						<td colspan="3">
							<input type="hidden" id="qyname" value="<%=map.get("qyname") %>"><%=map.get("qyname") %>
						</td>
					</tr>
					<tr>
						<td>注册地址</td>
						<td colspan="3">
							<input type="hidden" id="adress" value="<%=map.get("adress") %>"><%=map.get("adress") %>
						</td>
					</tr>
				</table>

				<br />
			</div>
   				<%}else{ %>
   				<div>
   				<br><br><img src="/images/share/error.png" ><br><br>
   				<font color='red' size="5">未找到相关信息</font>
   				</div>
   				<%} %>

		</div>
	</body>
</html>
