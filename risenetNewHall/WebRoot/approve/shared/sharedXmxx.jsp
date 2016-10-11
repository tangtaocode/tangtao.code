<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="net.risesoft.beans.share.FormInfo"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%response.setHeader("cache-control","public"); %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@page import="net.risesoft.dwr.SearchSharedData"%>
<html> 
<head>
<title>建设单位选择</title> 
<base target="_self" />
<link href="/css/emp.css" type="text/css" rel="stylesheet"/>
<link href="/css/login-box.css" type="text/css" rel="stylesheet" />

<script src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script
	src='<%=request.getContextPath()%>/dwr/interface/SearchSharedData.js'></script>
<%SearchSharedData ssd = new SearchSharedData();
ssd.findProjectInfo(request);%>
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
		SearchSharedData.findShared(sharedGuid, guid,insGuid,initData);
	}
	
	function initData(data){
		for(var key in data){
			if(key!="isSubmitForm"){
				if(window.dialogArguments.document.getElementsByName(key)[0].type=="checkbox")
				{
					if(data[key]!=null && data[key]!="")
					{
						window.dialogArguments.document.getElementsByName(key)[0].checked=true;
					}
					else
					{
						window.dialogArguments.document.getElementsByName(key)[0].checked=false;
					}
				}
				else
				{
					window.dialogArguments.document.getElementsByName(key)[0].value=data[key];	
					window.dialogArguments.document.getElementsByName(key)[0].fireEvent('onchange');
				}
			}
		}
		if(data["isSubmitForm"]=="1"){
			window.dialogArguments.document.getElementsByName("button")[0].onclick();
		}
		window.close();
	}
	function dbselect(obj){
		var insGuid = "<%=request.getParameter("insGuid")%>";
		var sharedGuid = "<%=request.getParameter("sharedGuid")%>";
		var idtd = obj.childNodes;
		var guid = idtd[idtd.length-1].childNodes[0].value;
		SearchSharedData.findShared(sharedGuid, guid,insGuid,initData);
	}
	
	function submitData(){
		window.name="myself";
		document.forms[0].submit();
		return false;
	}
</script>
</head>

<body >
<form name="SharedConfigForm" method="post" action="/approve/shared/sharedXmxx.jsp" target="myself" >
<input type="hidden" name="sharedGuid" value="<%=request.getAttribute("sharedGuid")%>" id="sharedGuid" class="queryinput" size="30">
<input type="hidden">
  <table width="98%"  align="center" cellpadding="0" cellspacing="0" class="table1 table1-bordered">  	
	<%
	List<FormInfo> query_list =(List<FormInfo>)request.getAttribute("queryformList");
	for(int i=0;i<query_list.size();i++)
	{
		%>
  		<tr>
	<th><%=query_list.get(i).getDisplaychname() %></th>
	<td>&nbsp;<input class="input1_query" type="text" name="<%=query_list.get(i).getDisplayenname() %>" value="<%=(String)request.getAttribute(query_list.get(i).getDisplayenname()) %>" id="<%=query_list.get(i).getDisplayenname() %>">				
	</td> 
    </tr>
  	<%
	}
	%>
	<tr>
		<td colspan="4">
			<input type="button" class="buttonClass_4" text="确定" value="确定" onclick="saveData();"/> 
			<input type="button" class="buttonClass_4" text="查询" value="查询" onclick="submitData();"/>
		</td>
	</tr>
</table>
<br>
</form>

<form method="post" >	
	<table class="table1 table1-bordered">
	<thead>
	
		<tr>
		<th nowrap="nowrap">选择</th>
		<%
  	
  		List<FormInfo> list =(List<FormInfo>)request.getAttribute("formList");
  		for(int i=1;i<list.size();i++)
  		{
  		%>
  		<th nowrap="nowrap"><%=list.get(i).getDisplaychname() %></th>
  		<%
  		}
  		 %>
		
		</tr>
		</thead>
		<%
		Object showlist = request.getAttribute("result");
		if(!showlist.toString().equals("noresult")){
  		List result =(List)request.getAttribute("result");
  		for(int a=0;a<result.size();a++)
  		{
  		%>
  		<tr ondblclick="dbselect(this)">
  		<td nowrap="nowrap"><input type="radio" name="selectGuid" value=<%=((Map)result.get(a)).get("GUID") %> /></td>
  		<%
  		
  		for(int i=1;i<list.size();i++)
  		{
  		
  		%>
  		<td nowrap="nowrap"><%Object obj = ((Map)result.get(a)).get(list.get(i).getDisplayenname().toUpperCase());
  		if(obj==null){
  			out.print("");
  		}else if(obj instanceof Date){
			String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(obj);
			if(time.contains("00:00:00")){
				time = time.substring(0,10);
			}
			out.print(time);
		}else out.print(obj); 
  		
  		%></td>
  		<%
  		
  		}
  		 %>
  		  
  		</tr>
  		
  		<% 
  		}
		}
  		%>
  		</table>
</form>

</body>
</html>
