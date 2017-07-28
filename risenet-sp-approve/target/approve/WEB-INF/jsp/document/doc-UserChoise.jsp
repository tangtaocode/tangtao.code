<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv=Content-Type content="text/html;charset=utf-8">
<meta http-equiv=X-UA-Compatible content="IE=edge">
<title>文档编辑-用户选取</title>
<%@ include file="/static/common/taglib.jsp"%>
<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<style>
#container { width:95%; padding:0; margin:0 auto; height:95%;}
#leftDiv { width:40%;height:85%;float:left; text-align:center; display:block; /* background-color:#ccc; */ margin-left:10px; color:#f00;}
#rightDiv { width:40%;height:85%;float:right; text-align:center; display:block; /* background-color:#ccc; */ margin-right:10px; color:#fff;}
#centerDiv{width:5%;height:85%;margin:0 auto;}
#rightDiv * {font-size:16px;}
</style>
</head>
<body>
	<div id="container">	
	<div style="height:5%;" align="center">
		<input type="hidden" id="processDefinitionId" value="${processDefinitionId}"></input>
		<input type="hidden" id="existPerson" value="${existPerson}"></input>
		<input type="hidden" id="existDepartment" value="${existDepartment}"></input>
		<input type="hidden" id="existGroup" value="${existGroup}"></input>
		<input type="hidden" id="existPosition" value="${existPosition}"></input>
		<input type="button" id="submit1" name="submit1" value="确 定 "
			style="border: 1px solid #669999; background-color: #FFFFFF; font: Menu" />
		&nbsp;&nbsp; <input type="button" name="closeDlg" id="closeDlg"
			value=" 关 闭 "
			style="border: 1px solid #669999; background-color: #FFFFFF; font: Menu" />
	</div>
	</br>
	<div id="leftDiv">
		<div id="tabdiv" class="easyui-tabs"></div>
	</div>

	<div id="rightDiv">
		<table id="receiveObj"></table>
	</div>
	<div id="centerDiv">
		<table style="width: 5%;height: 350px; ">
			<tr>
				<td style="height: 40%;" align="center" valign="bottom"><img
					id="addOperation" name="addOperation"
					src="${ctx}/static/images/arrow_right.gif" width="20px"
					 alt="添加" /></td>
			</tr>
			<tr>
				<td height="1px;"></td>
			</tr>
			<tr>
				<td  style="height: 40%;" align="center" valign="top"><img
					id="removeOperation" name="removeOperation"
					src="${ctx}/static/images/arrow_left.gif" width="20px"
					 alt="移除" /></td>
				</tr>
			</table>
		</div>
	</div>
</body>
<script type="text/javascript">
	var sponsor="主办";
	var coSponsor="协办";
	var multiInstance="";
	var tenantId='${tenantId}';
	var zTreeObj=null;
	var grid = $('#receiveObj');
	var processDefinitionId = $("#processDefinitionId").val();
	var taskDefKey="${taskDefKey}";
	
	$(function(){
		mydata= [];
		addTab();
		initDatagrid();
	})
	
	function initDatagrid(){
		grid.datagrid({
			width:($(window).width() - 100)*0.42+ 'px',
			height:($(window).height() - 10)*0.84 + 'px',
			singleSelect:true,
			fitColumns:true,
			data : mydata,
			columns : [ [{
					title : '类型',
					field : 'principalType',
					width : 1,
					align : "center",
					hidden : true
				},{
					title : '类型',
					field : 'principalTypeName',
					width : 12,
					align : "center",
					hidden : false,
					formatter : function(cellvalue, rowObject, rowIndex) {
						if (cellvalue == 1) {
							return "角色";
						}
						if (cellvalue == 2) {
							return "部门";
						}
						if (cellvalue == 3) {
							return "人员";
						}
						if (cellvalue == 4) {
							return "动态角色";
						}
						if (cellvalue == 5) {
							return "用户组";
						}
						if (cellvalue == 6){
							return "岗位";
						}
						return "未定义";
					}
				},{
					title : '主体Guid',
					field : 'principalGuid',
					width : 4,
					align : "center",
					hidden : true
				},{
					title : 'tId',
					field : 'tId',
					width : 4,
					align : "center",
					hidden : true
				}, {
					title : '名称',
					field : 'principalName',
					width : 12,
					align : "center",
					hidden : false
				},{
					title : '父节点Guid',
					field : 'parentGuid',
					width : 4,
					align : "center",
					hidden : true
				}, {
					title : '部门Guid',
					field : 'deptGuid',
					width : 4,
					align : "center",
					hidden : true
				}, {
					title : '主协办',
					field : 'isSponsorStatus',
					width : 3,
					align : "center",
					hidden : true,				
					formatter : function(cellvalue, rowObject, rowIndex) {
						var s=sponsor;
						if(typeof(cellvalue)=="undefined"){//新增的时候，cellvalue为undefined，所以第一个是主办，其它都是协办
							/*var count=jQuery("#list1").getGridParam('reccount');
							if(count>0)//由于在数据处理阶段判断行数，所以这里的行数比添加后少1，例如添加第一行之前这里进行行数行数判断，所以行数为0
							{
								s=coSponsor;
							}*/
						}else if(cellvalue>0){//当cellvalue为0的时候是主办
							s=coSponsor;
						}
						return s;
					}
				},{
					title : '操作',
					field : 'taskOpt',
					width : 12,
					align : "center",
					formatter : function(cellvalue, rowObject, rowIndex) {
						var s = '<a href="javascript:void(0);" onclick="deleteReceiveObj(\'' + rowObject.principalGuid + '\',\''+rowIndex+'\');">删除</a>';
						return s;
					}
				}
			] ]
		});  
	}
	
	function getSetting(principalType) {
		var setting = {//人员
			async : {
				enable : true,
				url : "${ctx}/role/findPermUser?processDefinitionId=${processDefinitionId}&principalType="
						+ principalType + "&taskDefKey="+taskDefKey,
				autoParam : [ "id", "deptIdHierarchy", "isPerm" ]
			},
			view : {
				fontCss : getFont,
				dblClickExpand : false
			},
			data : {
				simpleData : {
					enable : true
				}
			},
			callback : {
				onDblClick : onDblClick
			}
		};
		return setting;
	}
	
	//添加页签
	function addTab(){
		var existPerson = $("#existPerson").val();
		var existDepartment = $("#existDepartment").val();
		var existGroup = $("#existGroup").val();
		var existPosition = $("#existPosition").val();
		
		if(existPerson=="true"){$('#tabdiv').tabs('add',{title:'人员'});};
		if(existDepartment=="true"){$('#tabdiv').tabs('add',{title:'部门'});};
		if(existGroup=="true"){$('#tabdiv').tabs('add',{title:'用户组'});};
		if(existPosition=="true"){$('#tabdiv').tabs('add',{title:'岗位'});};
		
		$('#tabdiv').tabs('select',0);
	}
	//初始化页面内容，并默认选中第一个
	$('#tabdiv').tabs({    
	    onSelect:function(){
	    	var tab = $('#tabdiv').tabs('getSelected');  // 获取选择的面板
	    	var title = tab.panel('options').title;//获取面板标题 
	    	var tabType='';
	    	if(title=="人员"){principalType=3;}
	    	if(title=="部门"){principalType=2;}
	    	if(title=="用户组"){principalType=5;}
	    	if(title=="岗位"){principalType=6;}
	    	$('#tabdiv').tabs('update', {
	    		tab: tab,
	    		options: {
	    			content: '<iframe id="iframeId'+principalType+'" name="iframeName'+principalType+'" scrolling="auto" frameborder="0"  src="${ctx}/document4Eform/choiceTab?principalType='+principalType+'" style="width:80%;height:347px"></iframe>'  // 新内容的URL
	    		}
	    	});
	    }    
	});
	
	//关闭人员选取界面
	$('#closeDlg').click(function(){
		closeCurWindow(parent.frameID,'close');
	});
	
	//中间按钮添加
	$('#addOperation').click(function(){
		addSelectedNodes();
	});
	//获取树上选取的节点
	function addSelectedNodes(){
		var tab = $('#tabdiv').tabs('getSelected');
		var title = tab.panel('options').title;//获取面板标题
		var child=null;
		if(title=="人员"){zTreeObj=window.frames["iframeName3"].zTreeObj;child=iframeName3;}
		if(title=="部门"){zTreeObj=window.frames["iframeName2"].zTreeObj;child=iframeName2;}
		if(title=="用户组"){zTreeObj=window.frames["iframeName5"].zTreeObj;child=iframeName5;}
		if(title=="岗位"){zTreeObj=window.frames["iframeName6"].zTreeObj;child=iframeName6;}
		var nodes = zTreeObj.getSelectedNodes();
		if(nodes.length>0){
			for (var i=0; i<nodes.length; i++) {
				child.window.addRow(nodes[i]);
			}
		}else{
			alert("未选择节点");
		}
	}
	
	//中间按钮的移除
	$('#removeOperation').click(function(){
		var rowObject=$("#receiveObj").datagrid('getSelected');
		var index=$("#receiveObj").datagrid('getRowIndex',rowObject);
		deleteReceiveObj(rowObject.principalGuid,index);
	});
		
	//删除jqgrid中选中的数据行
	function deleteReceiveObj(principalGuid,rowIndex){
		$("#receiveObj").datagrid('deleteRow',rowIndex);
		var rows = $('#receiveObj').datagrid("getRows");
		$('#receiveObj').datagrid("loadData", rows);

		var tab = $('#tabdiv').tabs('getSelected');
		var title = tab.panel('options').title;//获取面板标题
		if(title=="人员"){zTreeObj=window.frames["iframeName3"].zTreeObj;}
		if(title=="部门"){zTreeObj=window.frames["iframeName2"].zTreeObj;}
		if(title=="用户组"){zTreeObj=window.frames["iframeName5"].zTreeObj;}
		if(title=="岗位"){zTreeObj=window.frames["iframeName6"].zTreeObj;}
		var ztreeNode =zTreeObj.getNodes();
		//var node1 =zTreeObj.getNodeByTId(tId);
		var node = zTreeObj.getNodeByParam("id", principalGuid, null);
		if(node!=null){
			/*由于部门下的人员与部门下用户组、岗位下的人员可能相同，
			所以删除其中一个下面的人员，另一个有相同人员的也要改变颜色*/
			if((ztreeNode[0].orgType=="Organization"||ztreeNode[0].orgType=="Department")&&node.orgType=="Person"){
				for(var i=0;i<ztreeNode.length;i++){
					DeleteChildren(ztreeNode[i],principalGuid);
				}
			}
			var ParentNode = node.getParentNode();
			if(ParentNode!=null&&ParentNode.color=="blue"){
			}else{
				ChangeDeleteColor(node);					
			}							
		}
		//主协办
		/*if(multiInstance == 'parallel'){
			var ids=$("#list1").jqGrid('getDataIDs');
			if(ids.length>0){
				var currentRowData=$("#list1").jqGrid("getRowData",ids[0]);
				var principalGuid=currentRowData.principalGuid;
				$('#list1').setRowData( principalGuid, {isSponsorStatus:0 });
			}
		}*/
	}
	
	/*由于部门下的人员与部门下用户组、岗位下的人员可能相同，
	所以删除其中一个下面的人员，另一个有相同人员的也要改变颜色*/
	function DeleteChildren(ztreeNode,principalGuid){
		var children = ztreeNode.children ;
		if(children!=null && children!=undefined && children.length>0){
			for(var i=0;i<children.length;i++){
				if(children[i].orgType=="Person"&&children[i].id==principalGuid&&ztreeNode.color!="blue"){
					children[i].color="#000000";
					zTreeObj.updateNode(children[i]);
				}else{
					DeleteChildren(children[i],principalGuid);
				}
			}
		}
	}
	
	//删除后改变字体颜色
	function ChangeDeleteColor(treeNode){
		treeNode.color = "#000000" ;
		if(zTreeObj!=null){
			zTreeObj.updateNode(treeNode);	
		}
		var children = treeNode.children ;
		if(children!=null && children!=undefined && children.length>0){
			for(var i=0;i<children.length;i++){		
				var data=grid.datagrid('getRows');
				var str=false;
				for(var j=0;j<data.length;j++){
					if(children[i].id==data[j].principalGuid){
						str=true;
					}					
				}
				if(str==true){
				}else{
					children[i].color = "#000000" ;
					if(zTreeObj!=null){
						zTreeObj.updateNode(children[i]);
					}
					ChangeDeleteColor(children[i]);
				}					
			}
		}
	}
		
	$('#submit1').click(function() {
		var userChoice = genUserChoice();
		if (typeof (userChoice) == "undefined" || userChoice == "") {
			alert("请选择用户");
			return;
		}
		if ($.trim(userChoice).length == 0) {
			alert('请选择一个用户。');
			return;
		}
		parent.result4UserChoice(userChoice,taskDefKey);
		closeCurWindow(parent.frameID,'close');
	});
	
	function genUserChoice(){
		var result="";
		var data = grid.datagrid('getRows');
		for(var i=0;i<data.length;i++){
			if(data[i].principalType==3){//人员
				if(result==""){
					result=data[i].principalType+":"+data[i].principalGuid+":"+data[i].parentGuid;
				}else{
					result=result+";"+data[i].principalType+":"+data[i].principalGuid+":"+data[i].parentGuid;
				}
			}else if(data[i].principalType==2){//部门
				if(result==""){
					result=data[i].principalType+":"+data[i].principalGuid;
				}else{
					result=result+";"+data[i].principalType+":"+data[i].principalGuid;
				}
			}else{//角色/用户组/动态用户组
				var temp=data[i].principalType;
				if(data[i].parentGuid=="rootGuid"){//如果父节点是最高节点，说明选取的是角色/用户组/动态用户组
					temp=temp+":"+data[i].principalGuid;
				}else{//如果不是角色/用户组/动态用户组，那么选取的可能是部门，也可能是人员
					if(data[i].deptGuid==""){//没有值，说明选取的是部门
						temp=temp+":"+data[i].parentGuid+":"+data[i].principalGuid;
					}else{
						temp=temp+":"+data[i].parentGuid+":"+data[i].principalGuid+":"+data[i].deptGuid;
					}
				}
				if(result==""){
					result=temp;
				}else{
					result=result+";"+temp;
				}
			}
		}
		return result;
	}
		
</script>
</html>