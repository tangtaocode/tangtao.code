<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>   
    <title></title>
<style>
table{
border-top:#9DC3DA solid 1px;
border-left:#9DC3DA solid 1px;
text-align: center;

}
td{
border-bottom:#9DC3DA solid 1px;
border-right:#9DC3DA solid 1px;
font:12px; 宋体;
height:40px;
}
th{
border-bottom:#9DC3DA solid 1px;
border-right:#9DC3DA solid 1px;
}
</style>


  </head>
  
  <body>
  <% Map map = (Map)request.getAttribute("YyzzMap"); %>
   			<div align="center">
   				<% if(map.get("state").equals("1")){ %>
   				<div style="width:529px;border:2px solid #9DC3DA;">
   						<br/>
   						<br/>
   						<h4>营业许可证信息</h4>
   						<table width="529" cellpadding="0" cellspacing="0" style="margin:5px;">
						  <tr>
						    <td width="122">组织机构代码</td>
						    <td width="118">
						    	<input type="hidden" id="EntOrgCode" value="<%=map.get("EntOrgCode") %>"><%=map.get("EntOrgCode") %>
						    </td>
						    <td width="111">经营执照</td>
						    <td width="150">
						    	<input type="hidden" id="EntRegNO" value="<%=map.get("EntRegNO") %>"><%=map.get("EntRegNO") %>
						    </td>
						  </tr>
						  <tr>
						    <td>法定代表人</td>
						    <td>&nbsp;
						    	<input type="hidden" id="LeRepName" value="<%=map.get("LeRepName") %>"><%=map.get("LeRepName") %>
						    </td>
						    <td>所属行政区划</td>
						    <td>&nbsp;
						    	<input type="hidden" id="DistCode" value="<%=map.get("DistCode") %>"><%=map.get("DistCode") %>
						    </td>
						  </tr>
						  <tr>
						    <td>注册资本</td>
						    <td>&nbsp;
						    	<input type="hidden" id="RegCap" value="<%=map.get("RegCap") %>"><%=map.get("RegCap") %>
						    </td>
						    <td>经营场所</td>
						    <td>&nbsp;
						    	<input type="hidden" id="BizAddr" value="<%=map.get("BizAddr") %>"><%=map.get("BizAddr") %>
						    </td>
						  </tr>
						  <tr>
						    <td>企业名称</td>
						    <td colspan="3">
						    	<input type="hidden" id="EntName" value="<%=map.get("EntName") %>"><%=map.get("EntName") %>
						    </td>
						  </tr>
						  <tr>
						    <td>企业类型</td>
						    <td colspan="3">
						    	<input type="hidden" id="EntTypeCode" value="<%=map.get("EntTypeCode") %>"><%=map.get("EntTypeCode") %>
						    </td>
						  </tr>
						  <tr>
						    <td>经营范围</td>
						    <td colspan="3">
						    	<input type="hidden" id="CBuItem" value="<%=map.get("CBuItem") %>"><%=map.get("CBuItem") %>
						    </td>
						  </tr>
						  <tr>
						    <td height="51">注销原因</td>
						    <td colspan="3">
						    	<input type="hidden" id="DeregReasonCode" value="<%=map.get("DeregReasonCode") %>"><%=map.get("DeregReasonCode") %>
						    </td>
						  </tr>
						</table>

   						<br/>
   				</div>
   				<%}else if(map.get("state").equals("2")){ %>
   				<div style="width:380px;border:1px solid red;">
   				<br><br><img src="/images/share/error.png" ><br><br>
   				<font color='red' size="5"><%=map.get("ErrorMessage") %></font>
   				</div>
   				<%}else{ %>
   				<div style="width:380px;border:1px solid red;">
   				<br><br><img src="/images/share/error.png" ><br><br>
   				<font color='red' size="5"><%=map.get("ErrorMessage") %></font>
   				</div>
   				<%} %>
					 
   			</div>
  </body>
</html>
