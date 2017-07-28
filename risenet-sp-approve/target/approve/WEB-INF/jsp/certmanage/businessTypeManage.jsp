<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>业务类型管理</title>
<!--框架必需start-->

<script type="text/javascript" src="${ctx}/static/QUI/libs/js/framework.js"></script>
<!--框架必需end-->
<!-- 布局 -->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/nav/layout.js"></script>
<!--树组件start -->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/tree/ztree/ztree.js"></script>
<link href="${ctx}/static/QUI/libs/js/tree/ztree/ztree.css" rel="stylesheet" type="text/css"/>
<!--树组件end -->
<!-- 双向选择器start -->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/form/lister.js"></script>
<!-- 双向选择器end -->


<script type="text/javascript">
function initComplete(){
    var layout=$("#layout1").layout({ leftWidth: 200,rightWidth:150}); 

    layout.setRightCollapse(true);
    //远程得到树
    $.post("${ctx}/certmanage/businessTree", {}, function(result){
        $.fn.zTree.init($("#tree-1"), setting1, result.treeNodes);
    }, "json");
 
}
var setting1 = {
	    callback: {
	        onClick: clickNode
	    }
	};




function clickNode(event, treeId, treeNode){
    var zTree = $.fn.zTree.getZTreeObj("tree-1");
	    //加载双向列表
	    $.post("${ctx}/certmanage/createDoubleSelect",{id:treeNode.id},function(result){
	    	if(result.deptId =="0"){
	    		$("#form")[0].reset();
	    		$("#form .lister").resetValue(); 
	    	}else{
			    	$("#docutypeId").val(treeNode.id);
			    	$("#docutypeName").val(treeNode.name);
    		        $("#lister5").data("data",result.data);
    		        $("#lister5").render(); 
    		        $("#deptId").val(result.deptId);
    		        $("#deptName").val(result.deptName);
    		        $("#add").show();
	    	}
    		   },"json");
			

    //zTree.expandNode(treeNode);

}  

function customHeightSet(contentHeight){

    $(".layout_content").height(contentHeight-30)

}
//保存
function submit(){
	var ids=$("#lister5").attr("relValue");
	var itemId=$("#docutypeId").val();
	$.post("${ctx}/certmanage/saveData",{ids:ids,itemId:itemId},function(result){
		//console.info(result.info);
		$.messager.show(0,result.info);
 		   },"json");
}
//弹出框
function addItem(){
	var itemId=$("#docutypeId").val();
	//var name = prompt("请输入您添加的材料名称:","");
	Dialog.open({URL:"${ctx}/certmanage/window?itemId="+itemId,Title:"增加材料名称",Width:350,Height:200});
}
//弹出信息  关闭dialog刷新页面
function add(info){
	 Dialog.close();
	 $.post("${ctx}/certmanage/businessTree", {}, function(result){
        //console.info(result.treeNodes);
        $.fn.zTree.init($("#tree-1"), setting1, result.treeNodes);
        Dialog.alert(info,function(){
			//history.go(0);
		 location.replace(location.href);
		   },"json");
    }, "json");
	// window.location.reload()
}
//只能删掉没有保存的材料名称  保存后的  则不能删掉
function delItem(){
	$("#lister11").removeItem("2");
}
</script>
</head>
<body>	
	<div id="layout1">

    <div  position="left" style="" panelTitle="业务类别">

    <div class="layout_content">

	    <div>
	   		 <ul id="tree-1" class="ztree"></ul>
	
		</div>

     </div>

    </div>

    <div position="center" style="">
	    <form id="info" action="${ctx}/certmanage/createDoubleSelect">
				<input id="docutypeId" type="text" hidden="true"/>
				<input id="deptId" type="text" hidden="true"/>
		    <table class="tableStyle" formMode="line" >
		
		        <tr><th colspan="2">业务明细展示</th></tr>
		        <tr><td>所属部门:</td><td><input id="deptName" type="text" required="true" readonly="true"/></td></tr>
		
		        <tr><td>业务名称:</td><td><input id="docutypeName" type="text" required="true" readonly="true"/></td></tr>    
		       <%-- <tr><td>资格审查：</td><td><textarea></textarea></td></tr> --%>
				<tr ><td>材料名称:</td><td><div class="lister" listerHeight=200  id="lister5"></div></td></tr>
				<%--<tr><td colspan="2"><input type="button" value="保存" onclick="submit();"/>&nbsp;<input type="reset" value="重置"/></td></tr>--%>
		    </table>
		</form>
		   <div style="text-align:center"><input  type="button" id="add" value="添加材料名称" style="display:none" onclick="addItem();"/><input  type="hidden" value="删除材料名称" onclick="delItem();"/><input  type="button" value="保存" onclick="submit();"/> </div>	
    </div>

    <%--<div position="right" style="" panelTitle="便捷工具">

    这是右侧区域

    </div> --%>
</div>


</body>
</html>
