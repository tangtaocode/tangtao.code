var userguid='';
var departid ='';
var zTree, rMenu;
var setting = {	
	async: {
		enable: true //是否开启异步加载
	},
	view: {
		dblClickExpand: false,
		showLine: false
	},	
	edit:{
		enable: false
	},
	callback: {
		onRightClick: onRightClick,				
		onClick: onClick
	},
	data: {
		simpleData: {
			enable: true
		}
	}
};




function filter(treeId, parentNode, childNodes) {
	if (!childNodes) return null;
	for (var i=0, l=childNodes.length; i<l; i++) {
		childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
	}
	return childNodes;
}
//点击加载显示事项信息		
function onClick(event, treeId, treeNode, clickFlag) {	
	if(treeNode.menuType=="3"){	
		var url = ctx+"/itemTree/showItemView";
		$.post(url,{itemId:treeNode.id,version:treeNode.version},function(data){
			$("#welcome").html(data);
			return;
		});
	}														
}		
//添加事项
function addItem(){
	var url = ctx+"/itemTree/addItemForm";			
	$.post(url,{method:'add',catalogsguid:zTree.getSelectedNodes()[0].id,type:zTree.getSelectedNodes()[0].menuType},function(data){
		$("#welcome").html(data);
		return;
	});
	hideRMenu();
}	

//修改事项
function editItem(){
	if(zTree.getSelectedNodes()[0].editUrl && zTree.getSelectedNodes()[0].editUrl!=""){
		var url=zTree.getSelectedNodes()[0].editUrl;
		$.post(ctx+url,{method:'edit'},function(data){
			$("#welcome").html(data);
			return;
		});
	}else{
		alert('该事项目前尚在审核中，需审核完毕后才能进行调整！');
	}				
	hideRMenu();
}		
//复制事项
function copyItem(){
	if(zTree.getSelectedNodes()[0].editUrl && zTree.getSelectedNodes()[0].editUrl!=""){
		var url=zTree.getSelectedNodes()[0].editUrl;
		$.post(ctx+url,{method:'copy'},function(data){
			$("#welcome").html(data);
			return;
		});
	}else{
		alert('该事项目前尚在审核中，需审核完毕后才能进行调整！');
	}				
	hideRMenu();
}		
//右击事件
	function onRightClick(event, treeId, treeNode) {
		if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
			zTree.cancelSelectedNode();				
			showRMenu("root", event.clientX, event.clientY);
		} else if (treeNode && !treeNode.noR) {
			zTree.selectNode(treeNode);				
			showRMenu(treeNode.menuType, event.clientX, event.clientY);
		}
	}
	//控制右击节点显示具体可操作项
	function showRMenu(type, x, y) {
		$("#rMenu").show();			
		if (type=="root") {//根目录
			$("#m_addCatalogs").hide();
			$("#m_editCatalogs").hide();
			$("#m_removeCatalogs").hide();
			$("#m_addItem").hide();
			$("#m_editItem").hide();
			$("#m_resetTree").show();
			$("#m_copyItem").hide();
		} else if(type=="1" ){//部门
			$("#m_addCatalogs").show();
			$("#m_editCatalogs").hide();
			$("#m_removeCatalogs").hide();	
			$("#m_addItem").show();
			$("#m_editItem").hide();
			$("#m_resetTree").hide();
			$("#m_copyItem").hide();
		}else if(type=="2"){//大类
			$("#m_addCatalogs").show();
			$("#m_editCatalogs").show();
			$("#m_removeCatalogs").show();
			$("#m_addItem").show();
			$("#m_editItem").hide();
			$("#m_resetTree").hide();
			$("#m_copyItem").hide();
		}else if(type=="3"){//事项
			$("#m_addCatalogs").hide();
			$("#m_editCatalogs").hide();
			$("#m_removeCatalogs").hide();
			$("#m_addItem").hide();
			$("#m_editItem").show();
			$("#m_resetTree").hide();
			$("#m_copyItem").show();
		}
		rMenu.css({"top":y+"px", "left":x+"px", "visibility":"visible"});
		$("body").bind("mousedown", onBodyMouseDown);
	}
	//隐藏右击菜单
	function hideRMenu() {
		if (rMenu) rMenu.css({"visibility": "hidden"});
		$("body").unbind("mousedown", onBodyMouseDown);
	}
	function onBodyMouseDown(event){
		if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
			rMenu.css({"visibility" : "hidden"});
		}
	}
	//隐藏右击菜单
	function hideRMenu() {
		if (rMenu) rMenu.css({"visibility": "hidden"});
		$("body").unbind("mousedown", onBodyMouseDown);
	}
	function onBodyMouseDown(event){
		if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
			rMenu.css({"visibility" : "hidden"});
		}
	}
	var addCount = 1;
	//增加大项
	function addCatalogs() {
		if(zTree.getSelectedNodes()[0]){
				//非根目录创建目录
				var pid = zTree.getSelectedNodes()[0].id;
				Dialog.open({
					URL:ctx+"/itemTree/addCatalog?handletype=add&pid="+pid,
					Title:"添加",Width:500,Height:400});
				//window.parent.document.getElementById('mainFrame_item').src='/xzql/xzsp/shenbaoByTree/CatalogsAction.do?handletype=add&catalogspid='+pid;		
				//window.parent.mainFrame_item.addCatalogs(pid,userguid);
		}
		hideRMenu();				
	}		
	
	//编辑大项
	function editCatalogs() {				
		if(zTree.getSelectedNodes()[0]){
				var id = zTree.getSelectedNodes()[0].id;
				var pid = zTree.getSelectedNodes()[0].parentId;
				var name = zTree.getSelectedNodes()[0].name;
				var code = zTree.getSelectedNodes()[0].code;
				var orderno = zTree.getSelectedNodes()[0].orderno;
				Dialog.open({
					URL:ctx+"/itemTree/addCatalog?handletype=edit&pid="+pid+"&name="+name+"&orderNo="+orderno+"&guid="+id,
					Title:"修改",Width:500,Height:400});
		}	
		hideRMenu();					
	}
	//删除大类
	function removeCatalogs(){
		hideRMenu();
		if(zTree.getSelectedNodes()[0]){
			var nodeList = zTree.getNodesByParam("parentId", zTree.getSelectedNodes()[0].id);
			if(nodeList.length>0){
				alert('无法删除，请确保节点下无子节点后才能进行删除操作！');	
			}else{
				//XzspDwrDeal.removeCatalogs(zTree.getSelectedNodes()[0].id,backRemoveCatalogs);
				$.post(ctx+"/itemTree/deleteCatalog",{guid:zTree.getSelectedNodes()[0].id},
						function(data){
						if(data=="1"){
							alert("删除成功");
							reload();
						}else{
							alert("删除失败");
						}
				});
			}
		}
	}
	//重新加载
	function reload(){
		Dialog.close();
		window.location.href=ctx+"/itemTree/itemTreeIndex";
	}	