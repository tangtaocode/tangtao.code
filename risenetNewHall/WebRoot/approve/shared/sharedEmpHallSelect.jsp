<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="net.risesoft.beans.share.FormInfo"%>
<%@page import="net.risesoft.dwr.SearchSharedData"%>
<%@page import="java.util.Map"%>
<%response.setHeader("cache-control","public"); %>
<html> 
<head>
<base target="_self" />
<title>人员选定</title> 
<link href="/css/qyInvestIndex.css" type="text/css" rel="stylesheet" />
<link href="/css/login-box.css" type="text/css" rel="stylesheet" />
<link href="/css/emp.css" type="text/css" rel="stylesheet"/>

<script src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script
	src='<%=request.getContextPath()%>/dwr/interface/SearchSharedData.js'></script>
	<script type="text/javascript">
<%
	SearchSharedData sharedData = new SearchSharedData();
	sharedData.findEmployeeInfo(request);
%>
	function saveData(){
	var gcxh = document.getElementById("gcxh").value;
		var insGuid = "<%=request.getParameter("insGuid")%>";
		var sharedGuid = "<%=request.getParameter("sharedGuid")%>";
		var guids = document.getElementsByName("selectGuid"); 
		var type = document.getElementById("type").value;
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
		SearchSharedData.findEmpShared(sharedGuid,guid,insGuid,gcxh,type,initData);
	}
	
	function cancelSelectData(){
		var guids = document.getElementsByName("selectGuid"); 
		var insGuid = "<%=request.getParameter("insGuid")%>";
		var sharedGuid = "<%=request.getParameter("sharedGuid")%>";
		var guid = "";
		SearchSharedData.cancelSelect(sharedGuid, guid,insGuid,initData);
	}
	
	function initData(data){
			for(var key in data){
				if(key!="isSubmitForm" && key!="flag"){
				
					window.dialogArguments.document.getElementById(key).value=data[key];
					
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
<div class="div1_query">
<form name="SharedConfigForm" method="post" action="/approve/shared/sharedEmpSelect.jsp" target="myself" >
	<input type="hidden" name="sharedGuid" value="<%=request.getAttribute("sharedGuid")%>" id="sharedGuid"/>
	<input type="hidden" name="gcxh" value="<%=request.getAttribute("gcxh")%>" id="gcxh"/>
	<input type="hidden" name="type" value="<%=request.getAttribute("type")%>" id="type"/>
  <table width="100%" align="center" cellpadding="0" cellspacing="0" class="table1 table1-bordered">  	
	<%
	List<FormInfo> query_list =(List<FormInfo>)request.getAttribute("queryformList");
	for(int i=0;i<query_list.size();i++){
	%>
  		<tr>
			<th><%=query_list.get(i).getDisplaychname() %></th>
			<td>&nbsp;
			<input class="input1_query" type="text" name="<%=query_list.get(i).getDisplayenname() %>" 
			 value="<%=(String)request.getAttribute(query_list.get(i).getDisplayenname()) %>"
			 id="<%=query_list.get(i).getDisplayenname() %>" class="queryinput" size="30"/>				
			</td> 
			<% 
			i++;
			if(i<query_list.size()){
				%>
					<th><%=query_list.get(i).getDisplaychname() %></th>
					<td>&nbsp;
					<input class="input1_query" type="text" name="<%=query_list.get(i).getDisplayenname() %>" 
					 value="<%=(String)request.getAttribute(query_list.get(i).getDisplayenname()) %>"
					 id="<%=query_list.get(i).getDisplayenname() %>"/>				
					</td>
				<%
			}
			%>
    	</tr>
  		<%
	}
	%>
	<tr>
		<td colspan="4">
			<input type="button"  class="buttonClass_4"text="确定" value="确定" onclick="saveData();";/> 
			<input type="button" class="buttonClass_4" text="查询" value="查询" onclick="submitData();"/>
			<input type="button" class="buttonClass_4" text="取消" value="取消" onclick="cancelSelectData();"/>
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
		<th nowrap="nowrap">选择</th>
		<%
  		List<FormInfo> list =(List<FormInfo>)request.getAttribute("formList");
  		for(int i=1;i<list.size();i++)
  		{
  			if(list.get(i).getDisplaychname().equals("姓名")){
  				%>
  					<th nowrap="nowrap" style="width:60px;">
  						<%=list.get(i).getDisplaychname() %>
  					</th>
  				<%
  			}else{
  				%>
  					<th >
  						<%=list.get(i).getDisplaychname() %>
  					</th>
  				<%
  			}
  		}
  		 %>
		</tr>
		</thead>
		<% 
			List result =(List)request.getAttribute("result");
			if(null != result && result.size() >0){
				for(int a=0;a<result.size();a++)
		  		{
					%>
						<tr>
							<td nowrap="nowrap">
								<input type="radio" name="selectGuid" value=<%=((Map)result.get(a)).get("ID_NUMBER") %> />
							</td> 
					<%
					for(int i=1;i<list.size();i++){
						%>
							<td>
							<%Object obj = ((Map)result.get(a)).get(list.get(i).getDisplayenname().toUpperCase());
						  		if(obj==null){
						  			out.print("");
						  		}else if(obj instanceof Date){
									String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(obj);
									if(time.contains("00:00:00")){
										time = time.substring(0,10);
									}
									out.print(time);
								}else out.print(obj); 
						  		
						  		%>
						  	</td>
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
</div>
</body>
</html>
