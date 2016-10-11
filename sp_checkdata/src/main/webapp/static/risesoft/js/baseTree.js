var setting1 = {
		callback : {
			onClick : clickNode
		}
	};
var setting2 = {
		check: {
	        enable: true
	    },
		callback : {
			onClick : okClick
		}
	};
//所属目录
function catalogSelect(){
	var adminGuid = $("#adminorgid").val();
	$.post(ctx+"/resource/catalogSelect?adminOrgid="+adminGuid, {}, function(result){
	    openCatalog(result.treeNodes);
	}, "json");
}
function openCatalog(nodes1){
	 Dialog.open({
	      InnerHtml: $("#catalogLay").html(),
	      Title:"目录",
	      ID:"c1",
	      Width:240,
	      Height:300
	    });
	    var $container=$(document.getElementById("_Container_c1"));
	    var $tree=$(' <ul id="tree-1" class="ztree"></ul>');
	    $.fn.zTree.init($tree, setting1, nodes1);
	    $container.append($tree)
}
//事项分类
function itemClassSelect(){
	$.post(ctx+"/resource/itemClassSelect", {}, function(result){
	    openItemClass(result.treeNodes);
	}, "json");
}
function openItemClass(nodes1){
	 Dialog.open({
	      InnerHtml: $("#itemClassLay").html(),
	      Title:"事项分类",
	      ShowButtonRow:true,
	      ShowOkButton:true,
	      ID:"c1",
	      Width:300,
	      Height:500,
	      OKEvent:okClick
	    });
	    var $container=$(document.getElementById("_Container_c1"));
	    var $tree=$(' <ul id="tree-1" class="ztree"></ul>');
	    $.fn.zTree.init($tree, setting2, nodes1);
	    $container.append($tree);
	    addScroll();
}
function addScroll(){
	//添加滚动条
    $("#_Container_c1").css("height","350px");
    $("#_Container_c1").css("width","300px");
    $("#_Container_c1").css("overflow","scroll");
}
//办理主体
function departmentSelect(){
	var itemId = $("#itemId").val();
	var version = $("#version").val();
	$.post(ctx+"/resource/departmenSelect?itemId="+itemId+"&version="+version+"", {}, function(result){
		openDepartment(result.treeNodes);
	}, "json");
}
function openDepartment(nodes1){
	 Dialog.open({
	      InnerHtml: $("#departLay").html(),
	      Title:"办理主体",
	      ID:"c1",
	      Width:240,
	      Height:300,
	      OKEvent:okClick
	    });
	    var $container=$(document.getElementById("_Container_c1"));
	    var $tree=$(' <ul id="tree-1" class="ztree"></ul>');
	    $.fn.zTree.init($tree, setting2, nodes1);
	    $container.append($tree)
	    addScroll();
}
//办理科室
function keshiSelect(){
	var departId = $("#departmentid").val();
	var keshiId = $("#keshiId").val();
	$.post(ctx+"/resource/keshiSelect?departId="+departId+"&keshiId="+keshiId, {}, function(result){
		openkeshi(result.treeNodes);
	}, "json");
}
function openkeshi(nodes1){
	 Dialog.open({
	      InnerHtml: $("#keshiLay").html(),
	      Title:"科室",
	      ID:"c1",
	      Width:240,
	      Height:300,
	      OKEvent:okClick
	    });
	    var $container=$(document.getElementById("_Container_c1"));
	    var $tree=$(' <ul id="tree-1" class="ztree"></ul>');
	    $.fn.zTree.init($tree, setting2, nodes1);
	    $container.append($tree)
	    addScroll();
}
//审批环节
function stepSelect(){
	$.post(ctx+"/resource/stepSelect", {}, function(result){
		openStep(result.treeNodes);
	}, "json");
}
function openStep(nodes1){
	 Dialog.open({
	      InnerHtml: $("#stepLay").html(),
	      Title:"投资项目审批环节",
	      ID:"c1",
	      Width:240,
	      Height:300,
	      OKEvent:okClick
	    });
	    var $container=$(document.getElementById("_Container_c1"));
	    var $tree=$(' <ul id="tree-1" class="ztree"></ul>');
	    $.fn.zTree.init($tree, setting2, nodes1);
	    $container.append($tree)
	    addScroll();
}
//所出证照
function doctypeSelect(){
	var bureau = $("#adminorgid").val();
	$.post(ctx+"/resource/doctypeSelect?bureauIds="+bureau+"&doctypeIds=", {}, function(result){
		if(result.treeNodes==''){
			alert("当前部门下无证照！");
		}else{
			openDoctype(result.treeNodes);
		}
	}, "json");
}
function openDoctype(nodes1){
	 Dialog.open({
	      InnerHtml: $("#doctypeLay").html(),
	      Title:"证照列表",
	      ID:"c1",
	      Width:240,
	      Height:300,
	      OKEvent:okClick
	    });
	    var $container=$(document.getElementById("_Container_c1"));
	    var $tree=$(' <ul id="tree-1" class="ztree"></ul>');
	    $.fn.zTree.init($tree, setting2, nodes1);
	    $container.append($tree)
	    addScroll();
}
//办事窗口
function windowSelect(){
	$.post(ctx+"/resource/windowSelect?windowGuid=", {}, function(result){
		openWindow(result.treeNodes);
	}, "json");
}
function openWindow(nodes1){
	 Dialog.open({
	      InnerHtml: $("#windowLay").html(),
	      Title:"办事窗口",
	      ID:"c1",
	      Width:300,
	      Height:350,
	      OKEvent:okClick
	    });
	    var $container=$(document.getElementById("_Container_c1"));
	    var $tree=$(' <ul id="tree-1" class="ztree"></ul>');
	    $.fn.zTree.init($tree, setting2, nodes1);
	    $container.append($tree)
	    addScroll();
}
//点击处理目录		
function clickNode(event, treeId, treeNode, type) {	
	if(treeNode.type=='1'){
		$("#catalogsguid").val(treeNode.id);
		$("#catalogName").val(treeNode.name);
	}
	Dialog.close();
}	

//确定
function okClick(){
	var type="";
	//获取zTree对象
    var zTree = $.fn.zTree.getZTreeObj("tree-1");
    //得到选中的数据集
    var nodes = zTree.getCheckedNodes(true);
    var ids = "";
    var names=""
    var checknames='';
	var checkids='';	
    for(var i = 0; i < nodes.length; i++){
    	type=nodes[0].type;
    	var pid=nodes[i].parentId;
		var classifyName = nodes[i].name;
		//存在父节点，并且父节点不为部门，一直向上找
		while(zTree.getNodesByParam("id", pid).length>0){
			classifyName =  zTree.getNodeByParam("id", pid).name +'->'+classifyName;
			pid = zTree.getNodeByParam("id", pid).parentId;
		}
		
    	if(!(nodes[i].isParent)){
    		if(checknames=='')	{
    			checknames = classifyName;
    		}else{
    			checknames += ';'+classifyName;
    		}	
    		if(checkids==''){
    			checkids = nodes[i].id;
    		}else{
    			checkids += ','+nodes[i].id;
    		}
			//办理科室
			if(i==nodes.length-1){
				ids += nodes[i].id;
				names += nodes[i].name;
			}else{
				ids += nodes[i].id + ",";
				names += nodes[i].name+",";
			}
		}
    }
    //事项分类
    if(type==2){
    	$("#itemClass").val(checkids);
    	$("#itemClassName").val(checknames);
    }
    if(type==3){
	//办事窗口
		$("#windowGuid").val(ids);
		$("#windowName").val(names);
	}
	if(type==4){
		//证照
		$("#doctypeGuid").val(ids);
		$("#doctypeName").val(names);
	}
	if(type==5){
		//审批环节
		$("#stepId").val(ids);
		$("#stepName").val(names);
	}
	if(type==6){
		//办理科室
		$("#keshiId").val(ids);
		$("#keshi").val(names);
	}
    if(type==7){
		//办理主体
		$("#departmentid").val(ids);
		$("#departmentName").val(names);
    }
	Dialog.close();
}

