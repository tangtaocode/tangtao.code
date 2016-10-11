<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="net.risesoft.beans.share.FormInfo"%>
<%@page import="net.risesoft.dwr.SearchSharedBdxData"%>
<%@page import="java.util.Map"%>

<html> 
<head>
<title>系统内</title> 
<base target="_self" /> 
<%SearchSharedBdxData ssd = new SearchSharedBdxData();
request.setCharacterEncoding("UTF-8");
ssd.findProjectInfo(request);%>


<script src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script
	src='<%=request.getContextPath()%>/dwr/interface/SearchSharedBdxData.js'></script>
<link href="/css/qyInvestIndex.css" type="text/css" rel="stylesheet" />
<link href="/css/login-box.css" type="text/css" rel="stylesheet" />

<script type="text/javascript">
	function saveData(){
		var guids = document.getElementsByName("selectGuid"); 
		var insGuid = "<%=request.getParameter("insGuid")%>";
		var sharedGuid = "<%=request.getParameter("sharedGuid")%>";
		var guid = "";
		for(var i=0;i<guids.length;i++){
            if(guids[i].checked){
            	guid = guids[i].value;
                break;
            }
        }
		if(guid==""){
			alert("请选择一行数据");			
			return false;
		}
		
		SearchSharedBdxData.findShared(sharedGuid, guid,insGuid,initData);
	}
	function initData(data){
		for(var key in data){
			if(key!="isSubmitForm"){
			
			var temp = document.getElementById("tempnum").value+key;
				var rowNum = document.getElementById("rowNum").value;
			
				window.dialogArguments.document.getElementsByName(temp)[rowNum-1].value=data[key];	
			}
		}
		if(data["isSubmitForm"]=="1"){
			window.dialogArguments.document.getElementById("button").onclick();
		}
		window.close();
	}
	
	function submitData(){
		
		window.name="myself";
		document.forms[0].submit();
		return false;
	}
</script>
</head>

<body >

<table class="BS_list" style="line-height:100%;width:99%">
<thead>
<tr>

<th><input type="button"  class="buttonClass_4"text="确定" value="确定" onclick="saveData();";/> <input type="button" class="buttonClass_4" text="查询" value="查询" onclick="submitData();"/></th>


</tr>
</thead>

</table>

<table width="98%" border="1" align="center" cellpadding="0"
		cellspacing="0">
		<tr bgcolor="#FFFFFF">
			<td width="100%" height=40 class="menutitle"><risehtml:menubar />
			</td>
		</tr>

	</table>
<form name="SharedConfigForm" method="get" action="/approve/shared/sharedbdxSelect.jsp" target="myself" >

<input type="hidden" name="tempnum" value="<%=(String)request.getAttribute("tempnum") %>" id="tempnum" class="queryinput" size="30">	
<input type="hidden" name="rowNum" value="<%=(String)request.getAttribute("rowNum") %>" id="rowNum" class="queryinput" size="30">	


<input type="hidden" name="sharedGuid" value="<%=request.getAttribute("sharedGuid")%>" id="sharedGuid" class="queryinput" size="30">
<input type="hidden">
<table width="100%"  align="center" cellpadding="0" cellspacing="0" id="userInfoTab">  	
	<%
	List<FormInfo> query_list =(List<FormInfo>)request.getAttribute("queryformList");
	for(int i=0;i<query_list.size();i++)
	{
		if(i/2==0){
			out.print("<tr>");
		}
		%>
	<th><%=query_list.get(i).getDisplaychname() %></th>
	<td>&nbsp;<input type="text" name="<%=query_list.get(i).getDisplayenname() %>" value="<%=(String)request.getAttribute(query_list.get(i).getDisplayenname()) %>" id="<%=query_list.get(i).getDisplayenname() %>" class="queryinput" size="30">				
	</td> 
    
  		<%
	}
	%>
	
	</table>
	<br>
</form>

<form name="RiseTableFrom" id="RiseTableFrom" method="get" target="myself">	
	
	<table class="BS_list" style="line-height:100%;width:99%">
	<thead>
	
		<tr>
		<th nowrap="nowrap">序号</th>
		<%
  	
  		List<FormInfo> list =(List<FormInfo>)request.getAttribute("formList");
  		for(int i=1;i<list.size();i++)
  		{
  		%>
  		<th nowrap="nowrap"><%=list.get(i).getDisplaychname() %></th>
  		<%
  		}
  		 
  		 %>
		<th nowrap="nowrap">选择</th>
		</tr>
		</thead>
		<%
  		
  		List result =(List)request.getAttribute("result");
  		for(int a=0;a<result.size();a++)
  		{
  		%>
  		<tr><td nowrap="nowrap"> <%= (a+1)%></td>
  		
  		<%
  		
  		for(int i=1;i<list.size();i++)
  		{
  		
  		%>
  		<td nowrap="nowrap"><%=((Map)result.get(a)).get(list.get(i).getDisplayenname().toUpperCase())  %></td>
  		<%
  		
  		}
  		 %>
  		
  		 <td nowrap="nowrap"><input type="radio" name="selectGuid" value=<%=((Map)result.get(a)).get("GUID") %> /></td> 
  		</tr>
  		
  		<% 
  		}
  		%>
  		
 		
  		</table>
  			
</form>
</body>
</html>
