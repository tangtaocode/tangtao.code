<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="net.risesoft.beans.share.FormInfo"%>
<%@page import="net.risesoft.dwr.SearchSharedData"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%response.setHeader("cache-control","public"); %>

<html> 
<head>

<link href="/css/qyInvestIndex.css" type="text/css" rel="stylesheet" />
<link href="/css/login-box.css" type="text/css" rel="stylesheet" />
<link href="/css/main.css" type="text/css" rel="stylesheet" />
<LINK href="../../css/main.css" type="text/css" rel="stylesheet"/>
<link href="/css/emp.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/Scripts/jquery-1.7.1.min.js"></script>

<title>中标人信息查询</title> 
<base target="_self" />
<%
SearchSharedData sharedData = new SearchSharedData();
sharedData.findMoreSelects(request);
%>

<script src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script src='<%=request.getContextPath()%>/dwr/interface/SearchSharedData.js'></script>
<script type="text/javascript">
function saveData(){
	var guids = $("input[name='selectGuid']:checked");

	if(guids.length==0){
		alert("请勾选所需数据");
		return;
	}else{
		var insGuid = "<%=request.getParameter("insGuid")%>";
		var sharedGuid = "<%=request.getParameter("sharedGuid")%>";
		var strguids = "";
		guids.each(function(){
			strguids = strguids + $(this).val() + ":";
		});
		SearchSharedData.findMoreSelects(sharedGuid, strguids, insGuid,initData);
		}
	}

function initData(data) {
	for ( var key in data) {
		if (key != "isSubmitForm") {
			if (window.dialogArguments.document.getElementById(key).type == "checkbox") {
				if (data[key] != null && data[key] != "") {
					window.dialogArguments.document.getElementById(key).checked = true;
				} else {
					window.dialogArguments.document.getElementById(key).checked = false;
				}
			} else {
				var str = data[key].substring(0, data[key].length - 1);
				window.dialogArguments.document.getElementById(key).value = str;
				window.dialogArguments.document.getElementById(key).fireEvent('onchange');
			}
		}
	}
	if (data["isSubmitForm"] == "1") {
		window.dialogArguments.document.getElementById("button").onclick();
	}
	window.close();
}


</script>
	
</head>
<body >
<div class="div1_query">
<form name="SharedConfigForm" method="post" action="/approve/shared/sharedMoreSelect.jsp" target="myself" >
<input type="hidden" name="sharedGuid" value="<%=request.getParameter("sharedGuid")%>" id="sharedGuid"  size="30">
<input type="hidden" name="idnumber" value="<%=request.getParameter("idnumber") %>" id="idnumber" size="30">
  <table width="100%" align="center" cellpadding="0" cellspacing="0" class="table1 table1-bordered">  
	<%
	List<FormInfo> query_list =(List<FormInfo>)request.getAttribute("queryformList");
	for(int i=0;i<query_list.size();i++){
		%>
  		<tr>
	<th><%=query_list.get(i).getDisplaychname() %></th>
	<td>&nbsp;<input type="text" name="<%=query_list.get(i).getDisplayenname() %>" value="<%=(String)request.getAttribute(query_list.get(i).getDisplayenname()) %>" id="<%=query_list.get(i).getDisplayenname() %>" size="30">				
	</td> 
    </tr>
  	<%
	}
	%>
	<tr>
		<td colspan="2">
			<input type="button"  class="buttonClass_4" text="确定" value="确定" onclick="saveData();";/> 
			<input type="button" class="buttonClass_4" text="查询" value="查询" onclick="submitData();"/>
		</td>
	</tr>
	</table>
</form>
</div>

<div class="div1_result">
<form method="get" target="myself">	
	<table class="table1 table1-bordered" style="line-height:100%;width:99%;">
	<thead>
		<tr>
		<td nowrap="nowrap">选择</td>
		<%
  		List<FormInfo> list =(List<FormInfo>)request.getAttribute("formList");
  		for(int i=1;i<list.size();i++)
  		{
  		%>
  		<td nowrap="nowrap"><%=list.get(i).getDisplaychname() %></td>
  		<%
  		}
  		 %>
		</tr>
		</thead>
		<%
		Object showlist = request.getAttribute("result");
		if(!showlist.toString().equals("noresult"))
		{
  		List result =(List)request.getAttribute("result");
  		for(int a=0;a<result.size();a++)
  		{
  		%>
  		<tr >
  		<td nowrap="nowrap"><input type="checkbox" name="selectGuid" value=<%=((Map)result.get(a)).get("GUID") %> /></td>
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
<script type="text/javascript">
function submitData(){
	window.name="myself";
	document.forms[0].submit();
	return false;
}


var guids="<%=request.getParameter("idnumber")%>";
if(null!=guids){
var selectids= document.getElementsByName("selectGuid");
var ids = guids.split("/");
	for(var i=0;i<selectids.length;i++){
		for(var j=0;j<ids.length;j++){
			if(selectids[i].value==ids[j]){
				selectids[i].checked=true;
				selectids[i].parentNode.parentNode.className="selectstyle";
			}
		}
	}
}
</script>
</html>
