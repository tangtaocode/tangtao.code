<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.List"%>
<%@page import="net.risesoft.beans.share.FormInfo"%>
<%@page import="net.risesoft.dwr.SearchSharedData"%>
<%@page import="java.util.Map"%>
<%response.setHeader("cache-control","public"); %>
<html>
<head>
<title>工程信息</title>
<base target="_self" /> 
<link href="/css/login-box.css" type="text/css" rel="stylesheet" />
<link href="/css/emp.css" type="text/css" rel="stylesheet"/>

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
	
	function cancelSelectData(){
		var guids = document.getElementsByName("selectGuid"); 
		var insGuid = "<%=request.getParameter("insGuid")%>";
		var sharedGuid = "<%=request.getParameter("sharedGuid")%>";
		var guid = "";
		SearchSharedData.cancelSelect(sharedGuid, guid,insGuid,initData);
	}
	function initData(data){
	for ( var key in data) {
			if (key != "isSubmitForm") {
				var queryParam = window.dialogArguments.document.getElementById(key);
				if (queryParam != null) {
					if (queryParam.type == "checkbox") {
						if (data[key] != null && data[key] != "") {
							queryParam.checked = true;
						} else {
							queryParam.checked = false;
						}
					} else {
						queryParam.value = data[key];
						queryParam.fireEvent('onchange');
					}
				}
			}
		}

		if (data["isSubmitForm"] == "1") {
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
<body>
<div class="div1_query">
<form name="SharedConfigForm" method="post" action="/approve/shared/sharedSelect.jsp" target="myself" >
<input type="hidden" name="sharedGuid" value="<%=request.getAttribute("sharedGuid")%>" id="sharedGuid">
  <table width="100%"  align="center" cellpadding="0" cellspacing="0" class="table1 table1-bordered">
   	<c:forEach items="${queryformList}" var ="query" varStatus="status">
   		<tr>
   			<th><c:out value="${query.displaychname }"></c:out></th>
   			<td>
   				<input class="input1_query" type="text" name="${query.displayenname }"
   					value="<%=request.getAttribute("${query.displayenname}")==null ?"":request.getAttribute("${query.displayenname}")  %>" 
   					id="${query.displayenname }"/>
   			</td>
   		</tr>
   	</c:forEach>
	<tr>
		<td colspan="4">
			<input type="button" class="buttonClass_4" text="确定" value="确定" onclick="saveData();"/> 
			<input type="button" class="buttonClass_4"  text="查询" value="查询" onclick="submitData();"/>
			<input type="button"  class="buttonClass_4"  text="取消" value="取消" onclick="cancelSelectData();"/>
		</td>
	</tr>
</table>
</form>
</div>
<div class="div1_result">
<form method="post">	
<table class="table1 table1-bordered">
	<thead>
		<tr>
			<th nowrap="nowrap">选择</th>
		<%
	  		List<FormInfo> list =(List<FormInfo>)request.getAttribute("formList");
	  		for(int i=1;i<list.size();i++){
  		%>
  			<th>
  				<%=list.get(i).getDisplaychname() %>
  			</th>
  		<%
  			}
  		%>
		</tr>
		</thead>
		<%
	  		List result =(List)request.getAttribute("result");
			if(null != result && result.size() >0){
	  			for(int a=0;a<result.size();a++){
  		%>
	  		<tr>
		  		<td nowrap="nowrap">
		  			<input type="radio" name="selectGuid" value=<%=((Map)result.get(a)).get("GUID") %> />
		  		</td>
		  		<%
		  		for(int i=1;i<list.size();i++){
		  		%>
		  			<td style="width:200px;">
		  				<%=((Map)result.get(a)).get(list.get(i).getDisplayenname())==null ? "" :((Map)result.get(a)).get(list.get(i).getDisplayenname())%>
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