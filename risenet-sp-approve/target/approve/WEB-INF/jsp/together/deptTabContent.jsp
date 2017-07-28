<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv=X-UA-Compatible content="IE=edge">
<title>Insert title here</title>
<%@ include file="/static/common/taglib.jsp"%>
<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<link type="text/css" rel="stylesheet" href="${ctx}/static/jquery/tree/ztree/css/zTreeStyle/zTreeStyle.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/static/jquery/tree/ztree/css/myZtreeStyle.css" />

<script type="text/javascript" src="${ctx}/static/jquery/tree/ztree/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/tree/ztree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/tree/ztree/js/jquery.ztree.exhide-3.5.js"></script>

<style>
#treeId { width:95%;height:90%;float:left; text-align:center; display:block; margin-left:10px; margin-right:5px;/* background-color:#ccc; */ color:#f00;}
.ztree * {font-size:12px;font-family:Verdana, Geneva, Arial, Helvetica, sans-serif;}
</style>

</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" style="height: 90%; width: 100%;">
		<div id="treeId" class="ztree" style="overflow:auto;"></div>
	</div>
</div>
</body>
<script type="text/javascript">
	var zTreeObj=null;
	var grid = parent.$('#receiveObj');
	var tab = parent.$('#tabdiv').tabs('getSelected');
	var setting = {
			async: {
				enable: true,
				url : getUrl
			//	autoParam:["ID","deptIdHierarchy","isPerm"]
			},
			view:{enable : true,fontCss: getFont,dblClickExpand: false, selectedMulti: false},
			data:{simpleData: {enable: true,idKey: "ID",pIdKey: "parentID",rootPId: null}},
			callback:{onClick: onClick,
				onAsyncSuccess:function(event, treeId, treeNode, msg){
					if(treeNode==undefined){
						var title = tab.panel('options').title;//获取面板标题
						var nodes=zTreeObj.getNodes();
						for(var i=0;i<nodes.length;i++){
							if(title=="部门"){
								nodes[i].icon=ctx+'/static/icons/admin/folder_user.png';
							}
							zTreeObj.updateNode(nodes[i]);
						}
					}else{
						var children = treeNode.children ;
						if(children!=null && children!=undefined && children.length>0){
							for(var i=0,l=children.length;i<l;i++){
								if(children[i].orgType=='Department'&&children[i].description=="parent"){
									children[i].icon=ctx+'/static/icons/admin/folder_user.png';
									children[i].isParent = true;
									children[i].zAsync = false;
								}
								zTreeObj.updateNode(children[i]);
							}
						}
					}
				}
			}
		};
	
	function getUrl(treeId) {
		return "${ctx}/app/org/getDeptTree";
	}
	
	$(function(){
		initTree();
	});
	
	function initTree(){
		$.ajax({
		       async : false,  
		       cache : false,  
		       type: 'POST',
		       dataType:"json",
		       url: '${ctx}'+'/app/org/getDeptTree',
		       error: function () {
		           alert('获取数据失败！');  
		       },
		       success:function(orgNodes){
		    	   for(var i=0,l=orgNodes.length;i<l;i++){
		    		   if(orgNodes[i].orgType=='Department'&&orgNodes[i].description=="parent"){
		    			   orgNodes[i].icon=ctx+'/static/icons/admin/folder_user.png';
		    			   orgNodes[i].isParent = true;
		    		   }
		    		   if(orgNodes[i].orgType=='Organization'){
			    		   orgNodes[i].icon = '${ctx}'+'/static/common/icon/org/chart_organization.png';
			    		   orgNodes[i].isParent = true;
		    		   }
		    		   orgNodes[i].open=true;
		    	   }
		    	   	zTreeObj = $.fn.zTree.init($("#treeId"), setting, orgNodes);
			   		/*var treeObj=$.fn.zTree.getZTreeObj("treeId");
			   		var nodes=treeObj.getNodes();
			   		if(nodes.length>0){
			   			treeObj.expandNode(nodes[0],true,false,true);
			   		}*/
		       }
		});
	}
	
	//双击添加部门/人员，由于存在一人多岗问题，所以人员选取是需要加上部门guid
	function onClick(e, treeId, treeNode) {
		if(treeNode.isParent){
			if(treeNode.open){
				zTreeObj.expandNode(treeNode, false, null, null, false);
			}else{
				zTreeObj.expandNode(treeNode, true, null, null, false);
			}
		}else{
			addRow(treeNode);
		}
	}
	
	//向jqgrid中添加数据
	function addRow(treeNode){
		if(!traversalGrid(treeNode.ID)&&treeNode.color!="blue"){
			var deptGuid="";
			//********************
			var isSponsorStatus = "主办";//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			var data=grid.datagrid('getRows');
			if(data.length==0){
				isSponsorStatus = "主办";
			}
			//********************
			var data = grid.datagrid('getRows');
			if(data.length==1){//只添加一个
				//grid.datagrid('deleteRow',0);
				parent.deleteReceiveObj(data[0].principalGuid,0);
			}
			grid.datagrid('appendRow',{isSponsorStatus:isSponsorStatus,principalType:2,principalTypeName:2,principalGuid:treeNode.ID,tId:treeNode.tId,principalName:treeNode.name,parentGuid:treeNode.pId,deptGuid:deptGuid,taskOpt:treeNode.ID});
			grid.datagrid('selectRow',0);
			ChangeColor(treeNode);
		}else{
			//alert(treeNode.name+"已添加");
		}
	}
	
	//选择后改变字体颜色
	function ChangeColor(treeNode){
		treeNode.color = "blue";
		if(zTreeObj!=null){
			zTreeObj.updateNode(treeNode);
		}
	}
		
	//设置树的字体
	function getFont(treeId, node) {
		var ParentNode = node.getParentNode();
		if(ParentNode!=null&&ParentNode.color=="blue"){
			if(node.color==undefined){
				node.color=ParentNode.color;
			}
			return node.color ? {'color':node.color,'font-weight':'bold'}:{'color':ParentNode.color,'font-weight':'bold'};
		}else if(ParentNode!=null&&ParentNode.color==undefined){
			var data=grid.datagrid('getRows');
			for(var i=0;i<data.length;i++){
				if(node.ID==data[i].principalGuid&&node.color==undefined){
					node.color = "blue" ;
				}
			}
			return node.color ? {'color':node.color,'font-weight':'bold'}: {'color':'#000000','font-weight':'bold'};
		}else{
			var data=grid.datagrid('getRows');
			for(var i=0;i<data.length;i++){
				if(node.ID==data[i].principalGuid&&node.color==undefined){
					node.color = "blue" ;
				}
			}
			return node.color ? {'color':node.color,'font-weight':'bold'} : {'color':'#000000','font-weight':'bold'};
		}
	}
	
	//遍历grid找出指定项
	function traversalGrid(principalGuid){
		var data = grid.datagrid('getRows');
		for(var i=0;i<data.length;i++)
		{
			if(data[i].principalGuid==principalGuid)
			{
				return true;
			}
		}
	}
	
</script>
</html>