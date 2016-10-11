<%@ page contentType="text/html; charset=gbk" %> 
<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<link rel="stylesheet" href="/css/ztree/demo.css" type="text/css">
	<link rel="stylesheet" href="/css/ztree/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="/js/ztree/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="/js/ztree/jquery.ztree.excheck-3.5.js"></script>
<style type="text/css">
.selectMenu{
	font-size: 14px;
    font-family: "黑体";
    background: url(/images/investment/btn_stepe.gif) no-repeat;
    border: 0px;
    width: 125px;
    height: 27px;
    cursor: hand;
    color: #FFF;
    text-align: center;
   
}
.selectMenud{
	font-size: 14px;
    font-family: "黑体";
    background: url(/images/investment/btn_stepd.gif) no-repeat;
    border: 0px;
    width: 125px;
    height: 27px;
    cursor: hand;
    color: #FFF;
    text-align: center;
   
}
.ztree li a.level0 span {font-size: 130%;font-weight: bold}
</style>
<script type="text/javascript">
var zNodes =${typejsonString};
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
<body>
	<table cellpadding="0" cellspacing="0" border="0" width="100%" id="cardTab1"
			class="BS_list" style="font-family:微软雅黑;display:">
			<tr>
				<th>选择情形</th>
			</tr>
			<tr>
			<td>
				<div class="zTreeDemoBackground left">
					<ul id="treeDemo" class="ztree"></ul>
					<input id="guids" type="hidden" value="<s:property value="guids"/>"> 
				</div>
			</td>
			</tr>
	</table>
</body>
</html>
