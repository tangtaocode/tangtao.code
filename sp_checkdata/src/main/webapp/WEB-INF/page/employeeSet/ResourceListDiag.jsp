<%@page import="net.risesoft.model.personset.Icosubscrmodel"%>
<%@page import="net.risesoft.common.util.ConstUtils"%>
<%@page import="net.risesoft.model.RiseEmployee"%>
<%@page import="net.risesoft.model.personset.IconScreenModel"%>
<%@page import="net.risesoft.service.impl.IconScreenServiceImpl"%>
<%@page import="net.risesoft.service.IconScreenService"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="egov.appservice.ac.model.Resource" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>应用列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/demo.css" type="text/css">
	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-1.8.2.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/zTree/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/zTree/js/jquery.ztree.excheck-3.5.js"></script>
	    <%
	    RiseEmployee user = (RiseEmployee) request.getSession().getAttribute(ConstUtils.SESSION_USER);
		IconScreenService iss=new IconScreenServiceImpl();
		String fenpingguid=request.getParameter("fenpingguid");
	    String znum=request.getParameter("znum");
	    String rootResourceUID = ConstUtils.rootResourceUID;
	    List<Resource> relist=iss.getUserNotInSubResources(user,rootResourceUID);//父节点资源
	    StringBuffer sb=new StringBuffer();
		sb.append("[");
    	for(int i=0;i<relist.size();i++)
    	{
    		Resource re=(Resource)relist.get(i);
    		sb.append("{id:\""+re.getUID());
			sb.append("\",pId:\"");
			sb.append("\",name:\""+re.getName());
			sb.append("\",nocheck:\"true");
			sb.append("\"");
			sb.append("},");
			List<Resource> clist=iss.getUserNotInSubResources(user,re.getUID());//子节点资源
			for(int a=0;a<clist.size();a++)
	    	{
	    		Resource cre=(Resource)clist.get(a);
	    		sb.append("{id:\""+cre.getUID());
				sb.append("\",pId:\""+re.getUID());
				sb.append("\",name:\""+cre.getName());
				sb.append("\"");
				sb.append("},");
	    	}
    	}
    	sb.append("]");
    	%>
<script type="text/javascript">
  //检测是否超过12个
function checknum()
{
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = zTree.getCheckedNodes(true);
	var nvs=document.getElementById("znum");
	var num=parseInt(nodes.length);
	var ynum=parseInt(nvs.value);
	num=num+ynum;
	if(num>18){
		if(ynum==0){
			alert("每个分屏最多只能添加18个应用！");
		}else{
			alert("您的该分屏中已经存在"+ynum+"个应用，每个分屏最多只能添加18个应用！所以您这次只能添加"+(18-ynum)+"个应用！");
		}
		return false;
	}else{
		return true;
	}
}

//关闭窗口
function closewindow()
{
	this.window.opener = null;
	window.open("","_self");
	window.close();
}
//提交返回值到主体页面
function sumbform()
{
	if(checknum()){
		var str="";
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = zTree.getCheckedNodes(true);
		for (var i=0;i<nodes.length; i++) {
			str=str+nodes[i].id+";"+nodes[i].name;
			if(i!=nodes.length-1){
				str+=",";
			}
		}
		window.returnValue= str;
		closewindow();
	}
}
var zNodes =<%=sb.toString() %>;
var setting = {
		view:{
			showIcon: false
			//fontCss : {'font-weight': 'bold'}			
		},				
		check: {
			enable: true,
			chkboxType : { "Y" : "p", "N" : "s" }			
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			onCheck: onCheck			
		}		
	};
function onCheck(e, treeId, treeNode){
	var guids="";
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	if (treeNode.isParent&&treeNode.checked) {
		zTree.expandNode(treeNode,true);
	}else{
		zTree.expandNode(treeNode,false);
	}
	var nodes = zTree.getCheckedNodes(true);
	for (var i=0, l=nodes.length; i<l; i++) {
		guids=guids+nodes[i].id;
		if(i!=l-1){
			guids=guids+",";
		}
	}
	$("#guids").val(guids,"value");
}

$(document).ready(function(){
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);	
});
</script>
  </head>
  
  <body style="BACKGROUND-COLOR:#F0F7FF">
    <br>
    <h3 align="center">应用列表</h3>
    <div style="width:100%;" align='center'>
    <input type="hidden" id="fenpingguid" name="fenpingguid" value="${fenpingguid }" />
    <input type="hidden" id="znum" name="znum" value="<%=znum %>" />
    <hr>
    <table width="100%" align='center'>
    	<tr>
    		<td style="padding-left: 18px;">
    			<div class="zTreeDemoBackground center">
					<ul id="treeDemo" class="ztree" style="width: 300px;"></ul>
				</div>
    		</td>
    	</tr>
    </table>
    <br>
    <table align="center">
    	<tr><td><input type="button" value="确认" onclick="sumbform();" /></td><td>&nbsp;&nbsp;</td><td><input type="button" value="关闭" onclick="closewindow();"  /></td></tr>
    </table>
    <hr>
    <input type="hidden" id="trnum" name="trnum" value="<%=relist==null?0:relist.size() %>">
    </div>
  </body>
</html>
