//页面加载完成执行该方法



	//点击弹出人员树
$(function(){
	 $("#person").click(function(){
			$.fn.extcloudTreeStructure(personTreeOptions);
		});
})
	 




var personTreeOptions = {
treeType:'tree_type_person',
title:'选取人员',
orgCheckSetting: {
	orgNoCheck:true,
	deptNoCheck:true,
	groupNoCheck:true,
	positionNoCheck:true,
	personNoCheck:true
},
selectedMulti:false,
callback: {
			afterConfirm:function(checkedNodes,selectedNodes){
								if(selectedNodes.length>0){											
									if(selectedNodes[0].orgType=="Person"){
										var userids=selectedNodes[0].ID;
										var names=selectedNodes[0].name
										var deptIds=selectedNodes[0].parentID;
										$('#personid').val(userids);
										document.getElementById("name").value=names;
									 	$.ajax({
											type : 'POST',
											url : ctx+'/hallmanager/userinfo?deptIds='+deptIds,
											success : function(data){												
												document.getElementById("dept").value=data.dept;
											}
										});
										$('#drafter').val(selectedNodes[0].name);
									}else{
										$.messager.alert('提示', "只能点选人员","info");
										return false;
									}
								}
						 }
		  	}
};






