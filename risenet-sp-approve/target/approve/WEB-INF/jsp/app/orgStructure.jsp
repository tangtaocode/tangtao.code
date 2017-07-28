<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="Content-Language" content="utf-8"/>
		<meta http-equiv="pragma" content="no-cache">   
   	 	<meta http-equiv="cache-control" content="no-cache">
    	<meta http-equiv="expires" content="0">
		<title>组织机构</title>
		<%@ include file="/static/common/bpmTaglib.jsp"%>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/common/js/plugins/zTree-3.5.18/css/zTreeStyle/zTreeStyle.css"" >
		<link rel="stylesheet" type="text/css" href="${ctx}/static/common/js/plugins/jquery-easyui-1.4.3/themes/color.css" >
		<link rel="stylesheet" type="text/css" href="${ctx}/static/common/js/plugins/jquery-easyui-1.4.3/themes/icon.css"" >
		<link rel="stylesheet" type="text/css" href="${ctx}/static/common/js/plugins/jquery-easyui-1.4.3/themes/extcloud/easyui.css" id="themes">
		<script type="text/javascript" src="${ctx}/static/common/js/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="${ctx}/static/common/js/plugins/zTree-3.5.18/js/jquery.ztree.all-3.5.min.js" ></script>
		<script type="text/javascript" src="${ctx}/static/common/js/plugins/jquery-easyui-1.4.3/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${ctx}/static/common/js/plugins/jquery-easyui-1.4.3/locale/easyui-lang-zh_CN.js"></script>
	</head>
	<body>
		<div class="easyui-layout" data-options="fit:true,border:false">
			<div data-options="region:'north',border:false" class="toolbar">
				<table border="0" cellpadding="0" cellspacing="0" style="width: auto">
					<tr>
						<td>
							搜索：<input type="text" id="name" name="name"/>
							<a id="search" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" href="javascript:void(0);" title="点击搜索"></a>
							<a id="refresh" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" href="javascript:void(0);" title="重新载入"></a>
						</td>
					</tr>
				</table>
			</div>
			
			<div data-options="region:'center',border:false,toolbar:'#toolbar'" style="overflow:auto;">
				<div id="tree" class="ztree" style="overflow:auto;"></div>
			</div>
			
			<div data-options="region:'south',split:false,minHeight:40">
				<div id="toolbar" class="toolbar">
					<table border="0" cellpadding="0" cellspacing="0" style="width: 100%">
						<tr>
							<td align="center">
								<a id="confirm" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0);">确定</a>
								<a id="cancle" class="easyui-linkbutton" data-options="iconCls:'icon-no'" href="javascript:void(0);">取消</a>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript">
		var treeType = '${treeType}';
		var orgStructureTree = null ; 
		var orgStructureTreeSetting = {
			orgCheckSetting:{
				orgNoCheck:true,
				deptNoCheck:true,
				groupNoCheck:false,
				positionNoCheck:false,
				personNoCheck:false,
			},
			async : {
				enable : true,
				url : getUrl
			},
			data : {
				simpleData: {
					enable: true,
					idKey: "ID",
					pIdKey: "parentID",
					rootPId: null
				}
			},
			view : {
				enable : true,
				showIcon : true,// 是否显示节点图标
				expandSpeed : "slow", // 节点展开效果
				selectedMulti: true
			},
			check: {
				enable: true,
				chkboxType: { "Y": "", "N": "" }
			},
			callback : {
				onClick : function(event, treeId, treeNode){
				},
				onAsyncError: function(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown){
					top.$.messager.alert('提示', "获取数据失败");
				},
				onAsyncSuccess:function(event, treeId, treeNode, msg){
					var children = treeNode.children ;
					if(children!=null && children!=undefined && children.length>0){
						for(var i=0,l=children.length;i<l;i++){
							if(children[i].orgType=='Department'){
								children[i].icon='${ctx}'+'/static/common/icon/org/folder_user.png';
								if(treeType=='tree_type_bureau'){
									children[i].zAsync = true;
								}else{
									children[i].isParent = true;
									children[i].zAsync = false;
								}
								children[i].nocheck=orgStructureTreeSetting.orgCheckSetting.deptNoCheck ;
							}else if(children[i].orgType=='Group'){
								children[i].icon='${ctx}'+'/static/common/icon/org/group.png';
								children[i].nocheck=orgStructureTreeSetting.orgCheckSetting.groupNoCheck ;
							}else if(children[i].orgType=='Position'){
								children[i].icon='${ctx}'+'/static/common/icon/org/report_user.png';
								children[i].nocheck=orgStructureTreeSetting.orgCheckSetting.positionNoCheck ;
							}else if(children[i].orgType=='Person'){
								if(children[i].duty!=null && children[i].duty.trim().length>0){
									children[i].name=children[i].name+"("+children[i].duty+")";
								}
								if(children[i].sex==0){
									children[i].icon='${ctx}'+'/static/common/icon/org/user_female.png';
								}else{
									children[i].icon='${ctx}'+'/static/common/icon/org/user_male.png';
								}
								children[i].nocheck=orgStructureTreeSetting.orgCheckSetting.personNoCheck ;
							}
							orgStructureTree.updateNode(children[i]);
						}
					}
				}
			},
			edit : {
				enable : false
			}
		};
		
		function getUrl(treeId, treeNode) {
			return  '${ctx}' + '/app/org/getTree?treeType='+treeType+'&ID='+treeNode.ID;
		}
		
		function initTree(){
			$.ajax({
			       async : false,  
			       cache : false,  
			       type: 'POST',
			       dataType:"json",
			       url: '${ctx}'+'/app/org/list',
			       error: function () {
			           alert('获取数据失败！');  
			       },
			       success:function(orgNodes){
			    	   for(var i=0,l=orgNodes.length;i<l;i++){
			    		   orgNodes[i].icon = '${ctx}'+'/static/common/icon/org/chart_organization.png';
			    		   orgNodes[i].isParent = true;
			    		   orgNodes[i].nocheck=orgStructureTreeSetting.orgCheckSetting.orgNoCheck ;
			    	   }
			    	   orgStructureTree =  $.fn.zTree.init($("#tree"),orgStructureTreeSetting,orgNodes);
			    	   var rootNodes = orgStructureTree.getNodes();
			    	   for(var i=0,l=rootNodes.length;i<l;i++){
			    		   orgStructureTree.reAsyncChildNodes(rootNodes[i], "add");
			    	   }
			       }
			});
		}
		
		$(function() {
			
			$("#search").click(function(){
				var name = $.trim($("#name").val());
				if(name==''){
					top.$.messager.alert('提示', "搜索内容不能为空");
					return ;
				}
				if(treeType=='tree_type_bureau'){
					var searchResultnodes = orgStructureTree.getNodesByParamFuzzy("name", name, null);
					if(searchResultnodes==null || searchResultnodes.length<=0){
						top.$.messager.alert('提示', "无匹配结果");
					}else{
						if(orgStructureTree!=null){
			    		   orgStructureTree.destroy();
				   	    }
				    	orgStructureTree = $.fn.zTree.init($("#tree"), orgStructureTreeSetting,searchResultnodes);
					}
					return ;
				}
				$.ajax({
				       async : false,  
				       cache : false,  
				       type: 'POST',
				       dataType:"json",
				       data:{treeType:treeType,name:name},
				       url: '${ctx}'+'/app/org/treeSearch',
				       error: function () {
				           alert('获取数据失败！');  
				       },
				       success:function(nodes){
				    	   if(nodes.length==0){
				    		   top.$.messager.alert('提示', "无匹配结果");
				    		   return ;
				    	   }
				    	   for(var i=0,l=nodes.length;i<l;i++){
				    		   if(nodes[i].orgType=='Organization'){
				    			   nodes[i].icon = '${ctx}'+'/static/common/icon/org/chart_organization.png';
				    			   nodes[i].nocheck=orgStructureTreeSetting.orgCheckSetting.orgNoCheck ; ;
				    		   }
				    		   if(nodes[i].orgType=='Department'){
				    			   nodes[i].icon='${ctx}'+'/static/common/icon/org/folder_user.png';
				    			   nodes[i].nocheck=orgStructureTreeSetting.orgCheckSetting.deptNoCheck ;
				    		   }else if(nodes[i].orgType=='Group'){
									nodes[i].icon='${ctx}'+'/static/common/icon/org/group.png';
									nodes[i].nocheck=orgStructureTreeSetting.orgCheckSetting.groupNoCheck ;
				    		   }else if(nodes[i].orgType=='Position'){
									nodes[i].icon='${ctx}'+'/static/common/icon/org/report_user.png';
									nodes[i].nocheck=orgStructureTreeSetting.orgCheckSetting.positionNoCheck ;
				    		   }else if(nodes[i].orgType=='Person'){
				    			    if(nodes[i].duty!=null && nodes[i].duty.trim().length>0){
										nodes[i].name=nodes[i].name+"("+nodes[i].duty+")";
									}
									if(nodes[i].sex==0){
										nodes[i].icon='${ctx}'+'/static/common/icon/org/user_female.png';
									}else{
										nodes[i].icon='${ctx}'+'/static/common/icon/org/user_male.png';
									}
									nodes[i].nocheck=orgStructureTreeSetting.orgCheckSetting.personNoCheck ;
				    		   }
				    		   nodes[i].open=true;
				    	   }
				    	   if(orgStructureTree!=null){
				    		   orgStructureTree.destroy();
					   	   }
				    	   orgStructureTree = $.fn.zTree.init($("#tree"), orgStructureTreeSetting,nodes);
				       }
				});
			});
			
			$("#refresh").click(function(){
				if(orgStructureTree!=null){
					orgStructureTree.destroy();
				}
				initTree();
			});
			
		});
	</script>
</html>