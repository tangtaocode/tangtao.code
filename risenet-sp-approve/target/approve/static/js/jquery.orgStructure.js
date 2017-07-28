/**
 * 此插件依赖frameUtil.js
 * organization structure tree plugin depends on jquery
 * treeType			    //组织机构树，显示机构，部门，用户组，角色，岗位，人员
						 	tree_type_org 
						//委办局树   显示机构，委办局
							tree_type_bureau
						//部门树    显示机构，部门
						 	tree_type_dept
						//用户组树   显示机构，部门  用户组
							tree_type_group
						//岗位树   显示机构，部门   岗位
							tree_type_position
						//人员树，显示机构，部门，人员
							tree_type_person
 * callback           	回调函数
 */
$.fn.extcloudTreeStructure=function(options) {
	 var defaultOptions = {
			 treeType         : 'tree_type_dept',
			 title            : '组织机构树',
			 selectedMulti    : true,
			 orgCheckSetting: {
									orgNoCheck:true,
									deptNoCheck:true,
									groupNoCheck:false,
									positionNoCheck:false,
									personNoCheck:false
							  },
			 callback		  : {
				 					onSelectedConfirm:null,
				 					afterConfirm:null,
				 					onCheckedConfirm:null
				 				}
	 };
	 var opts = $.extend(defaultOptions,options);
	 
	 var treeFrameID = newGuid() ;
	 
	 openCurWindow({
			id : treeFrameID,
			src : ctx+'/app/org/orgStructure?treeType='+opts.treeType,
			destroy : true,
			title : opts.title,
			width : 300,
			height : 350,
			modal : true
	 });
	 
	 frames[treeFrameID+'Frame'].onload=function(){
		 frames[treeFrameID+'Frame'].orgStructureTreeSetting.view.selectedMulti = opts.selectedMulti ;
		 frames[treeFrameID+'Frame'].orgStructureTreeSetting.orgCheckSetting.orgNoCheck = opts.orgCheckSetting.orgNoCheck ;
		 frames[treeFrameID+'Frame'].orgStructureTreeSetting.orgCheckSetting.deptNoCheck = opts.orgCheckSetting.deptNoCheck ;
		 frames[treeFrameID+'Frame'].orgStructureTreeSetting.orgCheckSetting.groupNoCheck = opts.orgCheckSetting.groupNoCheck ;
		 frames[treeFrameID+'Frame'].orgStructureTreeSetting.orgCheckSetting.positionNoCheck = opts.orgCheckSetting.positionNoCheck ;
		 frames[treeFrameID+'Frame'].orgStructureTreeSetting.orgCheckSetting.personNoCheck = opts.orgCheckSetting.personNoCheck ;
		 frames[treeFrameID+'Frame'].initTree();
		 frames[treeFrameID+'Frame'].$("#confirm").click(function(){
			var selectedNodes = frames[treeFrameID+'Frame'].orgStructureTree.getSelectedNodes();
			 if(opts.callback.onSelectedConfirm){
				 opts.callback.onSelectedConfirm(selectedNodes);
			 }
			 var checkedNodes = frames[treeFrameID+'Frame'].orgStructureTree.getCheckedNodes(true);
			 if(opts.callback.onCheckedConfirm){
				 opts.callback.onCheckedConfirm(checkedNodes);
			 }
			 if(opts.callback.afterConfirm && (opts.callback.afterConfirm(checkedNodes,selectedNodes)===false)){
				 return ;
			 }
			 $('#' + treeFrameID).window('close');
		 });
		 
		 frames[treeFrameID+'Frame'].$("#cancle").click(function(){
			 frames[treeFrameID+'Frame'].orgStructureTree.destroy();
			 $('#' + treeFrameID).window('close');
		 });
	 };
	 
};