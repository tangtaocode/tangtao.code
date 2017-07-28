<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>

<script type="text/javascript" src="${ctx}/static/js/msg.js"></script>
<title>模版类型</title>

</head>
<body>



	<div style="border-left: 1px dotted ThreedShadow; border-right: 1px dotted ThreedShadow;border-bottom: 1px dotted ThreedShadow;border-top: 0px;">

		<div style="margin-top:10px;" align="center">
			<input type="button" id="submit1" name="submit1" value="确 定" style="border:1px solid #669999; background-color:#FFFFFF;font:Menu"/>
			&nbsp;&nbsp;
			<input type="button" name="closeDlg" id="closeDlg" value=" 关 闭 " style="border:1px solid #669999; background-color:#FFFFFF;font:Menu"/>
		</div>
		
		<div id="cc" class="easyui-layout" style="width:633px;height:380px;">   
		    <div data-options="region:'west',title:'模版类型目录',split:true" style="width:150px;">
		    	<ul id="templateTypeId"></ul> 
		    </div>   
		    <div data-options="region:'center',title:'编辑模版类型'" style="padding:5px;background:#eee;">
		    	<div id="div1">
		    		<form id="templateType_formId" action="">
			    		<table border="1px">
				    		<tr>
				    			<td>模版类型名称:</td>
				    			<td>
				    				<input type="text" id="typeName" name="typeName"/>
				    			</td>
				    		</tr>
				    		
				    	</table>
			    	</form>
			    	<div align="right">
			    		<input type="button" id="button1" value="确定"/>
			    	</div>
		    	</div>
			</div> 
		</div>
	
	</div> 

	
	<div id="mm" class="easyui-menu" style="width:120px;">
		<div onclick="addTemplateType()" data-options="iconCls:'icon-add'">增加子节点</div>
		<div onclick="removeTemplateType()" data-options="iconCls:'icon-remove'">删除节点</div>
	</div>
	
</body>
<script type="text/javascript">
var objTree=$('#templateTypeId');

$(document).ready(function(){
	$("#div1").hide();
	objTree.tree({
		checkbox:true,
		dnd:true,
	    url: '${ctx}/templateType/getRootJson?id='+'000',    
	    onBeforeExpand:function(node,param){
            objTree.tree('options').url="${ctx}/templateType/getTreeJson?parentId="+node.id;  
        }, 
        onContextMenu: function(event, node){
    		event.preventDefault();
    		// 查找节点
    		objTree.tree('select', node.target);//选中节点
    		// 显示快捷菜单
    		$('#mm').menu('show', {
    			left: event.pageX,
    			top: event.pageY
    		});
    	},
    	onClick: function (node) {
            if (node){
                        objTree.tree('beginEdit', node.target);
            }
		},
		onAfterEdit:function(node){
			editTemplateType();
			
		}

	});
	
	$("#closeDlg").click(function(){
		window.close();
	});
	
	$("#submit1").click(function(){
		var typeNameStr="";
		var nodes = objTree.tree('getChecked');
		
		for(var i=0;i<nodes.length;i++){
			if(i==0){
				typeNameStr=nodes[i].text;
			}else{
				typeNameStr+=","+nodes[i].text;
			}
		}
		window.returnValue=typeNameStr;
		window.close();
	});
});


function addTemplateType(){
	$("#typeName").val("");
	$("#div1").show();
	var node = objTree.tree('getSelected');
	var parentId=node.id;
	
	$("#button1").click(function(){
		var typeName=$("#typeName").val();
		if(typeName==""){
			alert("模版类型名称不能为空");
			return false;
		}
		$.ajax({
	    	type : "POST",
			url : "${ctx}/templateType/save",
			dataType:'JSON',
			data : {
				typeName:typeName,
				parentId:parentId
			},
			beforeSend : function() {
			},
			error : function() {
				showMSG2("保存失败");
			},
			success : function(state) {
				showMSG2("保存成功");
				objTree.tree('append', {
					parent: node.target,
					data: {
						id: 21,
						text:typeName
					}
				});
				$("#typeName").val("");//新增输入框置为空，下次新增就可以重新输入
				//$("#div1").innerHTML("");
			}
		});
	});
}

function editTemplateType(){
	var node = objTree.tree('getSelected');
	var id=node.id;
	var typeName=node.text;
	if(typeName==""){
		alert("模版类型名称不能为空");
		return false;
	}
	$.ajax({
	    type : "POST",
		url : "${ctx}/templateType/update",
		dataType:'JSON',
		data : {
			typeName:typeName,
			id:id,
		},
		beforeSend : function() {
		},
		error : function() {
			showMSG2("更新失败");
		},
		success : function(state) {
			showMSG2("更新成功");
			$("#typeName").val("");
		}
	});
}


function removeTemplateType(){
    var node = objTree.tree('getSelected');
    var id=node.id;
    var parentNode=objTree.tree('getParent',node.target);
    $.ajax({
    	type : "POST",
		url : "${ctx}/templateType/delete",
		dataType:'JSON',
		data : {
			id:id
		},
		beforeSend : function() {
		},
		error : function() {
			showMSG2("删除失败");
		},
		success : function(state) {
			showMSG2("删除成功");
			objTree.tree('reload',parentNode.target);
		}
	});
}

</script>
</html>
