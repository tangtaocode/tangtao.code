<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9" ></meta>
<title>关联材料</title>
<!-- 布局 -->
<script type="text/javascript"	src="${ctx}/static/QUI/libs/js/nav/layout.js"></script>
<!--树组件start -->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/tree/ztree/ztree.js"></script>
<link href="${ctx}/static/QUI/libs/js/tree/ztree/ztree.css" rel="stylesheet" type="text/css" />
<!-- 树形下拉框start -->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/form/selectTree.js"></script>
<!-- 树形下拉框end -->
<!--树组件end -->
<!-- 选项卡-->
<script type="text/javascript"	src="${ctx}/static/QUI/libs/js/nav/basicTab.js"></script>
<!-- 选项卡end-->
<script type="text/javascript">
var setting1 = {
	    check: {
        //默认选择状态
	        enable: true
	    }
	};

    //初始化得到菜单树
	function initComplete() {
		var layout = $("#layout1").layout({
			leftWidth : 200,
			rightWidth : 150
		});
		layout.setRightCollapse(true);
		//远程得到树
		$.post("${ctx}/relationmaterial/stufftreelist", {}, function(result) {
			//alert(result.treeNodes);
			$.fn.zTree.init($("#tree-1"), setting1, result.treeNodes);
		}, "json");
	}
	var setting1 = {
		callback : {
			onClick : clickNode
		}
	};
	
	
	function customHeightSet(contentHeight){

	    $(".layout_content").height(contentHeight-70)

	}
	
	function getSelectValue(){
		
	    //获取zTree对象
	    var zTree = $.fn.zTree.getZTreeObj("tree-1");
	    //得到选中的数据集
	    var checkedNodes = zTree.getCheckedNodes(true);
	    //得到未选中的数据集
	    var checkedNodes2 = zTree.getCheckedNodes(false);
	    var allid = "";
	    var allname = "";
	    var x = 0;
	    for(var i = 0; i < checkedNodes.length;i++){	
	    	if(x==checkedNodes.length-1){
	    		allid += checkedNodes[i].id;
		        allname += checkedNodes[i].name ;	
	    	}else{
	    	allid += checkedNodes[i].id + ",";
	        allname += checkedNodes[i].name + "," ;
	        x++
	    	}
	    	
	    }

       
	    $.post("${ctx}/relationmaterial/savestufftype?LISTID=${ID}&ALLID="+allid+"&CLTYPEID=${CLTYPEID}",function(result){
	    	   //提交成功，返回结果
	    		alert(result.msg);
	    		parent.g.loadData();
	    		parent.Dialog.close();
	
		       },"json"); 

	}

	function open1(){

		parent.Dialog.close();

	}
</script>

<body>
 <div class = "box1" >
	<div id="layout1">

		<div style="" panelTitle="材料列表">
			<div class="layout_content" panelWidth="520">
				<div>
				<legend>请选择关联材料</legend>
					<div id="tree-1" class="ztree"></div>
				</div>
			</div>
		</div>
		<div align = "center"><a onclick='getSelectValue()'><span class='icon_ok'>提交</span></a>&nbsp;<a onclick='open1()'><span class='icon_delete'>关闭</span></a></div>
     </div>
	</body>
</html>