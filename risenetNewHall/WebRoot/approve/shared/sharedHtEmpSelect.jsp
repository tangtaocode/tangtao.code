<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="net.risesoft.approve.webservice.inShared.SearchSharedData"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="net.risesoft.xzql.util.CodeMapUtil"%>
<%@ page import="net.risesoft.commons.risemenu.MenuBar"%>
<%@ page import="net.risesoft.commons.risemenu.Menu"%>
<%@ page import="net.risesoft.commons.RequestConst"%>
<%@ page import="net.risesoft.workflow.ui.menu.MenuFactory"%>
<%@page import="java.util.List"%>
<%@page import="net.risesoft.approve.webservice.inShared.FormInfo"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%response.setHeader("cache-control","public"); %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<html>
<head>
<title>数据查询</title> 
<base target="_self" />
<title>工程信息</title>
<script type="text/javascript" src="<%=request.getContextPath() %>/xzql/scripts/worklist.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/xzql/css/queryTable.css" type="text/css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/xzql/css/query.css" type="text/css" />
<link rel="stylesheet" href="/xzql/css/submission.css" type="text/css" />
<link type="text/css" rel="StyleSheet" href="/commons/popupmenu/skins/winxp.css" />
<link rel="stylesheet" href="/xzql/css/query.css" type="text/css" />
<link rel="stylesheet" href="/xzql/css/queryTable.css" type="text/css" />



<script src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script
	src='<%=request.getContextPath()%>/dwr/interface/SearchSharedData.js'></script>
	<%
		MenuBar menubar = MenuFactory.createEmptyMenuBar();
	
		Menu save = MenuFactory.getRiseMenu().createMenu("save");
		menubar.addMenu(save, "确定", "javascript:saveData()",
				"/xzql/images/button/save.png", 90);
		
		Menu search = MenuFactory.getRiseMenu().createMenu("search");
		menubar.addMenu(search, "查询", "javascript:submitData();",
				"/xzql/images/button/search.png", 90);
		Menu cancel = MenuFactory.getRiseMenu().createMenu("cancel");
		menubar.addMenu(search, "取消", "javascript:cancelSelectData();",
				"/xzql/images/button/cancel.png", 90);
		request.setAttribute(RequestConst.MENUBAR, menubar);
		SearchSharedData.findEmployeeInfo(request);
		
	%>
	<script type="text/javascript">
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
		if(data["flag"] == "1"){
			var guids = document.getElementsByName("selectGuid");
			var guid = "";
			for(var i=0;i<guids.length;i++){
	            if(guids[i].checked){
	            	guid = guids[i].value;
	                break;
	            }
	        }
	        
			var type = document.getElementById("type").value;
			var url = "/approve/shared/selectedHtEmp.jsp?guid="+guid+"&type="+type;
			window.showModalDialog(url,window,"dialogWidth:400px;dialogHeight:250px;center:yes;status:no;help:no;");
			return;
		}else{
			for(var key in data){
				if(key!="isSubmitForm" && key!="flag"){
				
					window.dialogArguments.document.getElementById(key).value=data[key];
					
				}
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
<body>
<table width="98%" border="1" align="center" cellpadding="0"
		cellspacing="0">
		<tr bgcolor="#FFFFFF">
			<td width="100%" height=40 class="menutitle"><risehtml:menubar />
			</td>
		</tr>

	</table>
<form name="SharedConfigForm" method="get" action="/approve/shared/sharedHtEmpSelect.jsp" target="myself" >
<input type="hidden" name="sharedGuid" value="<%=request.getAttribute("sharedGuid")%>" id="sharedGuid" class="queryinput" size="30">
<input type="hidden" name="gcxh" value="<%=request.getAttribute("gcxh")%>" id="gcxh" class="queryinput"/>
<input type="hidden" name="type" value="<%=request.getAttribute("type")%>" id="type" class="queryinput"/>
  <table width="100%" align="center" cellpadding="0" cellspacing="0" id="table_content2">  	
	<%
	List<FormInfo> query_list =(List<FormInfo>)request.getAttribute("queryformList");
	for(int i=0;i<query_list.size();i++)
	{
		%>
  		<tr>
			<th><%=query_list.get(i).getDisplaychname() %></th>
			<td>&nbsp;
			<input type="text" name="<%=query_list.get(i).getDisplayenname() %>" 
			 value="<%=(String)request.getAttribute(query_list.get(i).getDisplayenname()) %>"
			 id="<%=query_list.get(i).getDisplayenname() %>" class="queryinput" size="30"/>				
	</td> 
    </tr>
  		<%
	}
	%>
	
	</table>
	<br>
</form>

<form name="RiseTableFrom" id="RiseTableFrom" method="post" target="myself">	
	<table id="todoTable" class="simple">
	<thead>
		<tr>
		<td nowrap="nowrap">序号</td>
		<%
  	
  		List<FormInfo> list =(List<FormInfo>)request.getAttribute("formList");
  		for(int i=1;i<list.size();i++)
  		{
  		%>
  			<td nowrap="nowrap"><%=list.get(i).getDisplaychname() %></td>
  		<%
  		}
  		 
  		 %>
		<td nowrap="nowrap">选择</td>
		</tr>
		</thead>
		<% 
			List result =(List)request.getAttribute("result");
			if(null != result && result.size() >0){
				for(int a=0;a<result.size();a++)
		  		{
				%>
					<tr><td nowrap="nowrap"> <%= (a+1)%></td>	
				<%
					for(int i=1;i<list.size();i++){
						%>
							<td nowrap="nowrap">
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
					<td nowrap="nowrap">
						<input type="radio" name="selectGuid" value=<%=((Map)result.get(a)).get("ID_NUMBER") %> /></td> 
  					</tr>
				<%
		  		}
			}
		%>
 </table>
  			
</form>
</body>
</html>