<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/static/include/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!--框架必需start-->
<script type="text/javascript" src="${ctx}/static/Qui/libs/js/jquery.js"  debug=true></script>
<script type="text/javascript" src="${ctx}/static/Qui/libs/js/language/cn.js"></script>
<script type="text/javascript" src="${ctx}/static/Qui/libs/js/framework.js"></script>
<link href="${ctx}/static/Qui/libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="${ctx}/static/Qui/" scrollerY="false"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->

<!-- 树型抽屉导航start-->
<script type="text/javascript" src="${ctx}/static/Qui/libs/js/tree/ztree/ztree.js"></script>
<link href="${ctx}/static/Qui/libs/js/tree/ztree/ztree.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${ctx}/static/Qui/libs/js/nav/treeAccordion_normal.js"></script>
<!-- 树型抽屉导航end -->
<style>
	.ztree li span.zbutton.diy01_ico_open, .ztree li span.zbutton.diy01_ico_close, .ztree li span.zbutton.diy01_ico_docu{width:19px!important;height:27px!important;padding-top:0;}
</style>
<script type="text/javascript">
var setting= {
	    async: {
	        enable: true,
	        dataType: 'JSON',
	        dataName: '',
	        url: '${ctx}/main/getPermMenus',
	        autoParam: ["id"]
	    },
	    view: {
	    	showLine: false,
	        dblClickExpand: false
	    },
	    callback: {
	        onClick: onNodeClick
	    }
	};
/**
 * 初始化树形结构
 */
function initComplete(){
    $.fn.zTree.init($("#treeDemo"), setting);
}
/**
 * 双击事件改为单击事件
 */
function onNodeClick(event, treeId, treeNode){
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    zTree.expandNode(treeNode);
}  
</script>
</head>

<body leftFrame="true">
	<div>
		<ul id="treeDemo" class="ztree ztree_accordition">
		</ul>
	</div>				
</body>
</html>