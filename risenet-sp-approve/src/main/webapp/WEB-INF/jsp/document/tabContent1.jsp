<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/static/common/taglib.jsp"%>
<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link type="text/css" rel="stylesheet" href="${ctx}/static/jquery/tree/ztree/css/zTreeStyle/zTreeStyle.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/static/jquery/tree/ztree/css/myZtreeStyle.css" />

<script type="text/javascript" src="${ctx}/static/jquery/tree/ztree/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/tree/ztree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/tree/ztree/js/jquery.ztree.exhide-3.5.js"></script>

<style>
#treeId { width:90%;height:90%;float:left; text-align:center; display:block; margin-left:10px; margin-right:5px;/* background-color:#ccc; */ color:#f00;}
.ztree * {font-size:14px;}
</style>

</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false">
		<div id="treeId" class="ztree" style="overflow:auto;"></div>
	</div>
</div>
</body>
<script type="text/javascript">
	var zTreeObj=null;
	var grid = parent.$('#receiveObj');
	var tab = parent.$('#tabdiv').tabs('getSelected');
	var processDefinitionId = parent.$("#processDefinitionId").val();
	var taskDefKey = null;
	var setting = {
			async: {
				enable: true,
				url:"${ctx}/role/findPermUser?processDefinitionId="+processDefinitionId+"&taskDefKey="+taskDefKey+"&principalType="+${principalType},
				autoParam:["id","deptIdHierarchy","isPerm"]
			},
			view:{enable : true,fontCss: getFont,dblClickExpand: false, selectedMulti: false},
			data:{simpleData: {enable: true,idKey: "id",pIdKey: "parentID",rootPId: null}},
			callback:{onDblClick: onDblClick,
				onAsyncSuccess:function(event, treeId, treeNode, msg){
					if(treeNode==undefined){
						var title = tab.panel('options').title;//获取面板标题
						var nodes=zTreeObj.getNodes();
						for(var i=0;i<nodes.length;i++){
							if(title=="人员"){
								if(nodes[i].sex==0){
									nodes[i].icon=ctx+'/static/icons/admin/user_female.png';
								}else{
									nodes[i].icon=ctx+'/static/icons/admin/user_male.png';
								}
							}if(title=="部门"){
								nodes[i].icon=ctx+'/static/icons/admin/folder_user.png';
							}if(title=="用户组"){
								nodes[i].icon=ctx+'/static/icons/admin/group.png';
							}if(title=="岗位"){
								nodes[i].icon=ctx+'/static/icons/admin/report_user.png';
							}
							zTreeObj.updateNode(nodes[i]);
						}
					}else{
						var children = treeNode.children ;
						if(children!=null && children!=undefined && children.length>0){
							for(var i=0,l=children.length;i<l;i++){
								if(children[i].orgType=='Department'){
									children[i].icon=ctx+'/static/icons/admin/folder_user.png';
									children[i].isParent = true;
									children[i].zAsync = false;
								}else if(children[i].orgType=='Group'){
									children[i].icon=ctx+'/static/icons/admin/group.png';
								}else if(children[i].orgType=='Position'){
									children[i].icon=ctx+'/static/icons/admin/report_user.png';
								}else if(children[i].orgType=='Person'){
									if(children[i].duty!=null && children[i].duty.trim().length>0){
										children[i].name=children[i].name+"("+children[i].duty+")";
									}
									if(children[i].sex==0){
										children[i].icon=ctx+'/static/icons/admin/user_female.png';
									}else{
										children[i].icon=ctx+'/static/icons/admin/user_male.png';
									}
								}
								zTreeObj.updateNode(children[i]);
							}
						}
					}
				}
			}
		};
	
	$(function(){
		initTree();
	});
	
	function initTree(){
		zTreeObj = $.fn.zTree.init($("#treeId"), setting, null);
		var treeObj=$.fn.zTree.getZTreeObj("treeId");
		var nodes=treeObj.getNodes();
		if(nodes.length>0){
			treeObj.expandNode(nodes1[0],true,true,true);
		}
	}
	
	//双击添加部门/人员，由于存在一人多岗问题，所以人员选取是需要加上部门guid
	function onDblClick(e, treeId, treeNode) {
		if(treeNode.isParent){
			if(treeNode.isPerm){
				addRow(treeNode);
			}else{
				if(treeNode.open){
					console.log(zTreeObj);
					zTreeObj.expandNode(treeNode, false, null, null, false);
				}else{
					zTreeObj.expandNode(treeNode, true, null, null, false);
				}
			}
		}else{
			addRow(treeNode);
		}
	}
	
	//向jqgrid中添加数据
	function addRow(treeNode){
		if(!traversalGrid(treeNode.id)&&treeNode.color!="blue"){
			if("${isSingle}"=="true"){//true为只能选取单个人，false为可以选取多个人
				grid.datagrid('load',[]);//当为true时，清空grid
			}
			var deptGuid="";
			//角色/用户组/动态角色用到deptGuid，因为选取人员的时候，需要知道人员所在的部门
			if(treeNode.principalType==1 || treeNode.principalType==4 || treeNode.principalType==5){
				if(typeof treeNode.deptId!="undefined"){
					deptGuid=treeNode.deptId;
				}
			}
			grid.datagrid('appendRow',{principalType:treeNode.principalType,principalTypeName:treeNode.principalType,principalGuid:treeNode.id,tId:treeNode.tId,principalName:treeNode.name,parentGuid:treeNode.pId,deptGuid:deptGuid,taskOpt:treeNode.id});
			var ztreeNode =zTreeObj.getNodes();
			/*由于部门下的人员与部门下用户组、岗位下的人员可能相同，
			所以添加其中一个下面的人员，另一个有相同人员的也要改变为已添加的颜色*/
			if((ztreeNode[0].orgType=="Organization"||ztreeNode[0].orgType=="Department")&&treeNode.orgType=="Person"){
				treeNode.color="blue";
				zTreeObj.updateNode(treeNode);
				for(var i=0;i<ztreeNode.length;i++){
					AddChildren(ztreeNode[i],treeNode);
				}
			}else{
				ChangeColor(treeNode);
			}
		}else{
			alert(treeNode.name+"已添加");
		}
	}
	
	//添加部门下的人员时，同时将部门下的用户组，岗位下的相关人员改变颜色
	function AddChildren(ztreeNode,treeNode){
		var children = ztreeNode.children ;
		if(children!=null && children!=undefined && children.length>0){
			for(var i=0;i<children.length;i++){
				if(children[i].orgType=="Person"&&children[i].id==treeNode.id){
					children[i].color="blue";
					zTreeObj.updateNode(children[i]);
				}else{
					AddChildren(children[i],treeNode);
				}
			}
		}
	}
	
	//选择后改变字体颜色
	function ChangeColor(treeNode){
		var children = treeNode.children ;
		if(children!=null && children!=undefined && children.length>0){
			for(var i=0,l=children.length;i<l;i++){
				children[i].color = "blue" ;
				if(zTreeObj!=null){
					zTreeObj.updateNode(children[i]);
				}
				ChangeColor(children[i]);
			}
		}
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
				if(node.id==data[i].principalGuid&&node.color==undefined){
					node.color = "blue" ;
				}
			}
			return node.color ? {'color':node.color,'font-weight':'bold'}: {'color':'#000000','font-weight':'bold'};
		}else{
			var data=grid.datagrid('getRows');
			for(var i=0;i<data.length;i++){
				if(node.id==data[i].principalGuid&&node.color==undefined){
					node.color = "blue" ;
				}
			}
			return node.color ? {'color':node.color,'font-weight':'bold'} : {'color':'#000000','font-weight':'bold'};
		}
	}
	
	//遍历grid找出指定项
	function traversalGrid(principalGuid){
		var data = grid.datagrid('getRows');
		for(var i=0;i<data.length;i++){
			if(data[i].principalGuid==principalGuid){
				return true;
			}
		}
	}
	
</script>
</html>