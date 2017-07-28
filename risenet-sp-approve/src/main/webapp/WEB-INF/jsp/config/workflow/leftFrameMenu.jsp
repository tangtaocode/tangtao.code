<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/bpmTaglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/static/common/head.jsp"%>
<title>工作流中间件-工作流配置左侧菜单页面</title>
<link type="text/css" rel="stylesheet" href="${ctx}/static/jquery/tree/ztree/css/zTreeStyle/zTreeStyle.css" />
<script type="text/javascript" src="${ctx}/static/jquery/tree/ztree/js/jquery.ztree.all-3.5.min.js"></script>

<style type="text/css">
.ztree * {
	font-size: 12px;
	font-family: "宋体", "黑体"
}

.ztree li ul {
	margin: 0;
	padding: 0
}

.ztree li {
	line-height: 30px;
}

.ztree li a {
	width: 200px;
	height: 30px;
	padding-top: 0px;
}

.ztree li a:hover {
	text-decoration: none;
	background-color: #E7E7E7;
}

.ztree li a span.button.switch {
	visibility: hidden
}

.ztree.showIcon li a span.button.switch {
	visibility: visible
}

.ztree li a.curSelectedNode {
	background-color: #D4D4D4;
	border: 0;
	height: 30px;
}

.ztree li span {
	line-height: 30px;
}

.ztree li span.button {
	margin-top: -7px;
}

.ztree li span.button.switch {
	width: 16px;
	height: 16px;
}

.ztree li a.level0 span {
	/* font-size: 150%; */
	font-weight: bold;
}

.ztree li span.button {
	background-image: url("${ctx}/static/jquery/tree/ztree/css/zTreeStyle/img/left_menuForOutLook.png");
	*background-image: url("${ctx}/static/jquery/tree/ztree/css/zTreeStyle/img/left_menuForOutLook.gif");
}

.ztree li span.button.switch.level0 {
	width: 20px;
	height: 20px
}

.ztree li span.button.switch.level1 {
	width: 20px;
	height: 20px
}

.ztree li span.button.noline_open {
	background-position: 0 0;
}

.ztree li span.button.noline_close {
	background-position: -18px 0;
}

.ztree li span.button.noline_open.level0 {
	background-position: 0 -18px;
}

.ztree li span.button.noline_close.level0 {
	background-position: -18px -18px;
}
</style>
<script type="text/javascript">
	var zTreeObj = null;
	var curExpandedTopNode = null;
	var setting = {
		view : {
			expandSpeed : "fast",
			nameIsHTML : false,
			showIcon : false,
			showLine : false,
			dblClickExpand : false,
			showTitle : true,
			selectedMulti : false,
			addDiyDom : addDiyDom,
			txtSelectedEnable : true
		},
		edit : {
			enable : false
		},
		callback : {
			//onClick: onClick,
			beforeClick : beforeClick,
			beforeRemove : beforeRemove
		}
	};
	
	var zNodes =[
				{ id:"3", pId:"rootGuid", name:"任务基本配置",isParent:false,url:"${ctx}/taskConf/show?procDefKey=${procDefKey}&processDefinitionId=${processDefinitionId}&procDefName=${procDefName}",target:"rightframemenu"},
				{ id:"1", pId:"rootGuid", name:"权限配置",isParent:false,url:"${ctx}/objPerm/workflow/taskList?procDefKey=${procDefKey}&processDefinitionId=${processDefinitionId}&procDefName=${procDefName}",target:"rightframemenu"},
				//{ id:"2", pId:"rootGuid", name:"催办配置",isParent:false,url:"${ctx}/reminderconf/show?procDefKey=${procDefKey}&processDefinitionId=${processDefinitionId}&procDefName=${procDefName}",target:"rightframemenu"},
				{ id:"4", pId:"rootGuid", name:"表单与流程绑定",isParent:false,url:"${ctx}/form/bind/taskListShow?procDefKey=${procDefKey}&processDefinitionId=${processDefinitionId}&procDefName=${procDefName}",target:"rightframemenu"},
				{ id:"5", pId:"rootGuid", name:"过程数据配置",isParent:false,url:"${ctx}/procData/show?procDefKey=${procDefKey}&processDefinitionId=${processDefinitionId}&procDefName=${procDefName}",target:"rightframemenu"}
				 
	];

	function addDiyDom(treeId, treeNode) {
		var spaceWidth = 5;
		var switchObj = $("#" + treeNode.tId + "_switch");
		var icoObj = $("#" + treeNode.tId + "_ico");
		switchObj.remove();
		icoObj.before(switchObj);

		if (treeNode.level > 1) {
			var spaceStr = "<span style='display: inline-block;width:" + (spaceWidth * treeNode.level) + "px'></span>";
			switchObj.before(spaceStr);
		}
	}

	function beforeClick(treeId, treeNode) {
		if (treeNode.isParent && !treeNode.zAsync) {
			zTreeObj.expandNode(treeNode);
		}
		if (treeNode.level == 0) {
			zTreeObj.expandNode(treeNode);
			if (curExpandedTopNode !== treeNode) {
				//关闭其他路径，只打开一条路径
				zTreeObj.expandNode(curExpandedTopNode, false);
				curExpandedTopNode = treeNode;
			}
			//return false;//返回false表示不触发onclick事件，也就是不能选择本节点
		}
		return true;
	}

	function beforeRemove(treeId, treeNode) {
		zTreeObj.selectNode(treeNode);
		if (treeNode.level == 0) {
			return false;
		} else {
			return confirm("确认删除 节点 -- " + treeNode.name + " 吗？");
		}
	}

	function onClick(e, treeId, treeNode) {
		//zTreeObj.expandNode(treeNode, null, null, null, true);
	}
	
	$(document).ready(function() {
		var treeObj = $("#tree");
		zTreeObj = $.fn.zTree.init(treeObj, setting, zNodes);
		treeObj.hover(function() {
			if (!treeObj.hasClass("showIcon")) {
				treeObj.addClass("showIcon");
			}
		}, function() {
			treeObj.removeClass("showIcon");
		});
	});
</script>
</head>
<body style="margin: 0; padding: 0">
	<div>
		<ul id="tree" class="ztree"></ul>
	</div>
</body>
</html>