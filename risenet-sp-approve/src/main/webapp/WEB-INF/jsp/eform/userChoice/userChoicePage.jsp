<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<title>工作流中间件-权限设置</title>
<link type="text/css" rel="stylesheet" href="${ctx}/static/jquery/jquery-ui/css/redmond/jquery-ui-1.10.3.custom.css" />
<link rel="stylesheet" href="${ctx}/static/jquery/tree/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="${ctx}/static/jquery/tree/ztree/css/myZtreeStyle.css" type="text/css">

<script type="text/javascript" src="${ctx}/static/jquery/jquery-ui/js/jquery-ui-1.10.3.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/form/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/databind/DataBind.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/tree/ztree/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/tree/js/jquery.ztree.excheck-3.5.js"></script>

<script type="text/javascript">
var title="${title1}";
/* zTree setting */
var setting = {
	async: {
		enable: true,//是否开启异步加载
		url: "${ctx }/${url}",
		autoParam:["id"]
	},
	check: {
		enable: true,
		chkStyle: "${chkStyle}",
		radioType: "all"/* ,
		chkboxType: { "Y" : "", "N" : "" } *///勾选时父子不关联，取消勾选时父子不关联
	},
	view: {
		dblClickExpand: false
	},
	edit: {
		enable: false
	},
	callback:{onAsyncSuccess:function(event, treeId, treeNode, msg){
				var treeObj=$.fn.zTree.getZTreeObj("tree");
				var ParentNode = treeNode.getParentNode();
				if(treeNode!=undefined&&ParentNode==null){
					if(title=="部门"||title=="用户"){
						treeNode.icon=ctx+'/static/icons/admin/chart_organization.png';
					}
					treeObj.updateNode(treeNode);
					var children = treeNode.children ;
					if(children!=null && children!=undefined && children.length>0){
						for(var i=0,l=children.length;i<l;i++){
							if(children[i].isParent!=true){
								if(title=="角色"||title=="动态角色"){
									children[i].icon=ctx+'/static/icons/admin/user_gray.png';
								}
							}
							if(title=="部门"){
								children[i].icon=ctx+'/static/icons/admin/folder_user.png';
							}
							if(title=="用户"){
								if(children[i].orgType=="Person"){
									if(children[i].sex==0){
										children[i].icon=ctx+'/static/icons/admin/user_female.png';
									}else{
										children[i].icon=ctx+'/static/icons/admin/user_male.png';
									}
								}else{
									children[i].icon=ctx+'/static/icons/admin/folder_user.png';
								}
							}
							treeObj.updateNode(children[i]);
						}
					}
				}else{
					var children = treeNode.children ;
					if(children!=null && children!=undefined && children.length>0){
						for(var i=0,l=children.length;i<l;i++){
							if(title=="部门"){
								children[i].icon=ctx+'/static/icons/admin/folder_user.png';
							}
							if(title=="角色"||title=="动态角色"){
								if(children[i].isParent!=true){
									children[i].icon=ctx+'/static/icons/admin/user_gray.png';
								}
							}
							if(title=="用户"){
								if(children[i].orgType=="Person"){
									if(children[i].sex==0){
										children[i].icon=ctx+'/static/icons/admin/user_female.png';
									}else{
										children[i].icon=ctx+'/static/icons/admin/user_male.png';
									}
								}else{
									children[i].icon=ctx+'/static/icons/admin/folder_user.png';
								}
							}
							treeObj.updateNode(children[i]);
						}
					}
				}
			}
	},
	data: {
		simpleData: {
			enable: true
		}
	}
};

 var zNodes =[
             { id:"${rootGuid}", pId:"${rootGuid}", name:"${name}",isParent:true,nocheck:true,open:true},
 ];

 function init(){//初始化树
	 $.ajax({
		async : false,  
       	cache : false,  
       	type: 'POST',
       	dataType:"html",
		url:"${ctx}/role/rootNode",//暂时写为角色的 固定url
		error: function () {
	           alert('获取数据失败！');  
	    },
	    success:function(orgNodes){
	    	ztree = $.fn.zTree.init($("#tree"), setting,orgNodes);
	    }
	 });
 }
 
$(document).ready(function(){
	var rootGuid="${rootGuid}";
	var name="${name}";
	//init();
	zTreeObj = $.fn.zTree.init($("#tree"), setting, zNodes);
	//zTreeObj.setting.check.chkboxType = { "Y" : "", "N" : "" };
	//初始化页面后自动展开节点，当使用异步加载时，则只会展开当前异步加载的节点（即第一级节点）
	//从这里开始
	var treeObj=$.fn.zTree.getZTreeObj("tree");
   	if("${urd}"==2){//选取的是部门的时候，不能父关联子勾选或者子关联父勾选
   		zTreeObj.setting.check.chkboxType = { "Y" : "", "N" : "" };
   	}
   	var nodes=treeObj.getNodes();
   	if(nodes.length>0){
   		treeObj.expandNode(nodes[0],true,true,true);
   	}
   	//到这里结束
	
	$('#submit1').click(function(){
		//var treeObj = $.fn.zTree.getZTreeObj("tree");
		var ids="";//记录选中用户的guid
		var names="";//记录选中用户的中文姓名
		var depts="";//记录选中用户所在的部门
		var nodes = treeObj.getCheckedNodes(true);//获取选中的节点
		if(nodes.length==0){
			alert("请选取用户");
			return;
		}
		if("${urd}"==2 || "${urd}"==3 || "${urd}"==4){//选取的是部门、人员、动态角色时，勾选那个就将那个部门保存起来，不做过滤
			for (var i=0, l=nodes.length; i<l; i++) {
				if(ids==""){
					ids=nodes[i].id;
					names=nodes[i].name;
					depts=nodes[i].pId;
				}else{
					ids=ids+";"+nodes[i].id;
					names=names+";"+nodes[i].name;
					depts=depts+";"+nodes[i].pId;
				}
			}
		}else{//角色和用户组
			var roleNode="";//记录子节点全选角色节点
			for (var i=0, l=nodes.length; i<l; i++) {
				if(nodes[i].check_Child_State=="2"){//如果子节点全选，那么只保存父节点即可，所以先查询一边，找出所有子节点全选的角色/用户组节点
					if(ids==""){
						ids=nodes[i].id;
						names=nodes[i].name;
						depts=nodes[i].pId;
						roleNode=nodes[i].id;
					}else{
						ids=ids+";"+nodes[i].id;
						names=names+";"+nodes[i].name;
						depts=depts+";"+nodes[i].pId;
						roleNode=roleNode+";"+nodes[i].id;
					}
				}
			}
			for (var i=0, l=nodes.length; i<l; i++) {
				if(nodes[i].check_Child_State=="-1" || nodes[i].check_Child_State=="0"){
					var curNodePId=nodes[i].pId;
					if(roleNode.indexOf(curNodePId)<0){//当前面的已经选取了角色/用户组后，其下的子节点将不再选取
						if(ids==""){
							ids=nodes[i].id;
							names=nodes[i].name;
							//当配置的是角色/用户组下的人员时，需要记录用户所在部门nodes[i].deptId（用于一人多岗）和用户所在的角色nodes[i].pId（用于标识权限表中用户属于哪个角色）
							//将用户组Id/角色Id和部门一起放在user_departmentGuids[i]中，其格式为“部门:用户组Id/角色Id”
							depts=nodes[i].deptId+":"+nodes[i].pId;
						}else{
							ids=ids+";"+nodes[i].id;
							names=names+";"+nodes[i].name;
							depts=depts+";"+nodes[i].deptId+":"+nodes[i].pId;
						}
					}
				}
			}
		}
		var returnvalue= [];
		returnvalue[0]=ids;
		returnvalue[1]=names;
		returnvalue[2]=depts;
		returnvalue[3]="3";//保存权限，3即读写权限，因为在权限表ff_objectPermission中需要记录权限信息，而当前界面没有权限选取，所以默认为3
		
		window.returnValue=returnvalue;
		window.close();
		//parent.choice(returnvalue,'${urd}');
		//closeCurWindow(parent.frameID,'close');
	});
	
	$('#closeDlg').click(function(){
		window.close();
		//closeCurWindow(parent.frameID,'close');
	});
});

</script>
</head>
<body bgcolor="white" leftmargin="0" topmargin="0" marginheight="0" marginwidth="0">
<div style="border-left: 1px dotted ThreedShadow; border-right: 1px dotted ThreedShadow;border-bottom: 1px dotted ThreedShadow;border-top: 0px;">

	<div style="margin-top:10px;" align="center">
		<input type="button" id="submit1" name="submit1" value="确 定" style="border:1px solid #669999; background-color:#FFFFFF;font:Menu"/>
		&nbsp;&nbsp;
		<input type="button" name="closeDlg" id="closeDlg" value=" 关 闭 " style="border:1px solid #669999; background-color:#FFFFFF;font:Menu"/>
	</div>
	<div align="center">
		<div>
			<ul id="tree" class="ztree"></ul>
		</div>
	</div>
</div>
</body>
</html>