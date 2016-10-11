/**
 * 在线申报
 */
/**
 * 切换搜索区的选项卡，
 * @param {Object} id form的ID
 * @param {Object} name  选项卡名称
 */
function changeSerach(id,name){
     $("#approveName").val("");
     $("#tableTempName").val("");
     $("#checkCode").val("");
     $("#yxtywlsh").val("");
     $("#wsapproveName").val("");
     $("#jggsywlsh").val("");
     $("#jggsCheckCode").val("");
     $("#departGUID").val("");
      
     $('input[name=approveType]').attr("checked",false);
     //$("#cksy").attr("class","");
     $("#sxss").attr("class","");
     $("#bgss").attr("class","");
     $("#bjcx").attr("class","");
     $("#xklx").attr("class","");
     $("#wsbl").attr("class","");
     $("#jggs").attr("class","");
     //$("#cksyForm").attr("style","display:none");
     $("#sxssForm").attr("style","display:none");
     $("#bgssForm").attr("style","display:none");
     $("#bjcxForm").attr("style","display:none");
     $("#xklxForm").attr("style","display:none");
     $("#wsblForm").attr("style","display:none");
     $("#jggsForm").attr("style","display:none");
     $("#jggsSearchApp").attr("style","display:none");
     $("#"+id).attr("class","BS_c blue");
     $("#"+id+"Form").attr("style","display:block");
     //if("cksy"==id){
     //	window.location="/onlineService/initOnlineService.html";
     //}
}
/**
 * 点击切换样式，根据父级分类查找子及分类
 * @param {Object} classify  父级分类
 * @param {Object} level  级别
 * @param {Object} type  类型
 */
function changeClassify(classify,level,type){
	$.post('/OnlineService/findClassifyByPid.html',
		{pid:classify,
		 levels:level,
		 method:type,
		 twoLevel:$("#twoLevel").val()},
		function(data){
		/*if(level=="2"){
			$("#levelTowDIV").html(data);
		}else if(level=="3"){
			$("#approveItemDIV").html(data);*/
		if(level=="3"||level=="2"){
			$("#approveItemDIV").html(data);
		}else{
			$("#levelTowDIV").html('');
			$("#approveItemDIV").html(data);
		}
	});
}
/**
 * 修改类型提示
 * @param {Object} text
 */
function changeText(text){
	$("#itemTypeText").html(text);
}
/**
 * 修改第二级菜单初始值
 */
function setTwo(){
	$("#twoLevel").attr("value","");
}
/**
 * 关键字热词查询
 * @param {Object} type  关键词类型
 * @param {Object} world  关键词
 */
function searchK(type,world){
	if(type=="sxss"){
		document.getElementById("approveName").value=world;
		searchApp();
	}else if(type=="bgss"){
		document.getElementById("tableTempName").value=world;
		searchTable();
	}
}

/**
 * 搜索区：办事指南，事项查询，网上申报
 * @param {Object} appType 事项类型，行政许可，非行政许可等
 * @param {Object} isOnline  是否可在线申报
 * @param {Object} pageNum 页数
 */
function searchApp(appType,isOnline,pageNum){
	var approveName;
	if("0"==isOnline){
		approveName = $("#wsapproveName").val();
	}else{
		approveName = $("#approveName").val();
	}
	//$.post('/approve/approveQueryResult.html',
	//$.post('/approve/approveQueryResultDX.html',
	$.post('/approve/queryOnlineItems.html',
		{'departGUID':$("#departGUID").val(),
		 'approveName':approveName,
		 'isOnlineApply':isOnline,
		 'page':pageNum,
		 'approveType':appType,
		 'orderType':$("#orderType").val(),
		 'ascOrDesc':$("#ascOrDesc").val(),
		 'streetApp':'true'},
		function(data){
			$("#searchAppDiv").html(data);
		});
}


/**
 * 判断当申报人类型为“个人”时
 * 不能填写申报信息
 */
function checkUserType(approveItemguid,to_location){
/*===施工许可，施工许可变更，竣工验收弹出提示框使用市局系统===*/
	var approveItemguids = "{7F000001-0000-0000-7185-AA7900000004},{7F000001-FFFF-FFFF-A669-F876FFFFFFA0},{7F000001-0000-0000-7185-AA8F00000013},{7F000001-FFFF-FFFF-A669-F729FFFFFF85},{7F000001-FFFF-FFFF-A669-F720FFFFFF82},{7F000001-0000-0000-7185-AAA400000022}";
	var createxmbh = "{0A009FA8-0000-0000-72DA-20CF000003A3},{7F000001-FFFF-FFFF-A669-F731FFFFFF88},{0A009FA8-FFFF-FFFF-8CCA-A01400000030},{0A009FA8-FFFF-FFFF-8CDA-148A0000004B},{0A0100A8-0000-0000-1E36-19A800004D95}";
	var mess = "【温馨提示】此事项即日起使用深圳市住房和建设局外网申报窗口进行申报。";
	var messxmtip = "【温馨提示】请用户确认所申报事项是否已建立项目，如未建项请到深圳市住房和建设局外网申报窗口建立项目。";
	if(approveItemguids.indexOf(approveItemguid)>=0){
	var diagapp = new Dialog({
			Width: 340,
			Height: 110,
			Title:"温馨提示",
			OkButtonText:"确定",
			CancelButtonText:"取消"
		});
	showConfirmtip(diagapp,mess,function(){
		window.open('http://www.szjs.gov.cn/xxgk/ywgz/jsgcgl/index_18661.htm','_blank');
	});
	}else{
	if(createxmbh.indexOf(approveItemguid)>=0){
		var diag = new Dialog({
			Width: 340,
			Height: 110,
			Title:"温馨提示",
			OkButtonText:"  是  ",
			CancelButtonText:"  否  "
		});
	
		showConfirmtip(diag,messxmtip,function(){
		$.post('/approve/checkUserType.html', {
		'approveItemguid' : approveItemguid
	}, 
	function(data) {
		if(data.message=="1"){
			//showInfo("当申请人为个人、社会组织机构、其他时，不能填报信息！");
			showInfo("您使用的账户类型，不能在线申报此事项！");
		}else{
			window.open(to_location,'_blank');
		}
	});
		},function(){
		window.open('http://projreg.szjs.gov.cn/','_blank');});
	}else{
	$.post('/approve/checkUserType.html', {
		'approveItemguid' : approveItemguid
	}, 
	function(data) {
		if(data.message=="1"){
			//showInfo("当申请人为个人、社会组织机构、其他时，不能填报信息！");
			showInfo("您使用的账户类型，不能在线申报此事项！");
		}else{
			window.open(to_location,'_blank');
		}
	});
	}
	}
}

/**
 * 根据咧更新数据排序
 * @param {Object} orderType 排序类型
 * @param {Object} searchType 倒叙还是顺序
 */
function changeOrder(orderType,searchType){
	$("#orderType").attr("value",orderType);
	var ad = $("#ascOrDesc").val();
	if(ad==""||ad==null||ad=="desc"){
		$("#ascOrDesc").attr("value","asc");
	}else{
		$("#ascOrDesc").attr("value","desc");
	}
	if(searchType=="app"){
		searchApp($("#approveType").val(),$("#isOnlineApply").val(),$("#pageCount").val());
	}else if(searchType=="img"){
		searchAppByImage($("#approveItemType").val(),$("#pageCount").val());
	}else if(searchType=="depart"){
		searchAppByDepart($("#departGUID").val(),$("#departGUID option:selected").text(),$("#pageCount").val());
	}
	
}
/**
 * 表格下载搜索
 * @param {Object} pageNum 页数
 */
function searchTable(pageNum){
	$.post('/approve/downAppItemFileTemp.html',
		{'page':pageNum,
		 'tableTempName':$("#tableTempName").val(),
		 'approveName':$("#tabAppName").val()},
		function(data){
			$("#searchAppDiv").html(data);
	});
}


function searchBusi(isCheck,pageNum){
	var ys = $("#yxtywlsh").val();
	var cc = $("#checkCode").val();
		
	if(ys==""||ys==null){
		alert("请输入受理编号");
		$("#yxtywlsh").focus();
		return false;
	}
	if(isCheck=="true"){
		if(cc==""||cc==null){
			alert("请输入验证码");
			$("#checkCode").focus();
			return false;
		}
	}
	$("#checkCode").attr("value","");
	$.post('/interaction/queryResult.html',
		{'yxtywlsh':ys,
		 'checkCode':cc,
		 'isCheck':isCheck,
		 'page':pageNum,
		 'method':'query'},
		function(data){
			if(data.message=="0"){
				showInfo("验证码输入错误");
				$("#bjcxRand").attr("src","/servlet/CheckCodeServlet?"+Math.random());
			}else{
				$("#searchAppDiv").html(data);
				if(isCheck=="true"){$("#bjcxRand").attr("src","/servlet/CheckCodeServlet?"+Math.random());}
				searchAtran();
			}
		});
}


/**
 * 初始化：结果公示
 */
function searchResult(pageNum){
	$.post('/onlineService/ResultPublicity.html',
		{'page':pageNum},
		function(data){
			$("#searchAppDiv").html(data);
		}
	);
}


/**
 * 搜索：我要查询（业务状态）
 * @param {Object} isCheck  是否验证验证码
 * @param {Object} pageNum  页数
 */
function jggsSearchBusi_stus(isCheck,pageNum){
	ys = $("#jggsywlsh").val();
	cc = $("#jggsCheckCode").val();		
	if(isCheck=="true"){
		if(cc==""||cc==null){
			showInfo("请输入受理编号");
			$("#jggsCheckCode").focus();
			return false;
		}
	}
	$("#jggsCheckCode").attr("value","");
	$.post('/onlineService/ResultPublicity.html',
		{'yxtywlsh':ys,
		 'checkCode':cc,
		 'page':pageNum},
		function(data){
			if(data.message=="0"){
				showInfo("验证码输入错误");
				$("#jggsRand").attr("src","/servlet/CheckCodeServlet?"+Math.random());
			}else{
				$("#jggsSearchApp").attr("style","display:block");
				$("#pageCountTd").html(data);
				if(isCheck=="true"){$("#jggsRand").attr("src","/servlet/CheckCodeServlet?"+Math.random());}
			}
	});
}




/**
 * 搜索：结果公示
 * @param {Object} isCheck  是否验证验证码
 * @param {Object} pageNum  页数
 */
function jggsSearchBusi(isCheck,pageNum){
	ys = $("#jggsywlsh").val();
	cc = $("#jggsCheckCode").val();	
	if(ys==""||ys==null){
		showInfo("请输入受理编号");
		$("#jggsywlsh").focus();
		return false;
	}
	if(isCheck=="true"){
		if(cc==""||cc==null){
			showInfo("请输入受理编号");
			$("#jggsCheckCode").focus();
			return false;
		}
	}
	$("#jggsCheckCode").attr("value","");
	$.post('/onlineService/ResultPublicity.html',
		{'yxtywlsh':ys,
		 'checkCode':cc,
		 'page':pageNum},
		function(data){
			if(data.message=="0"){
				showInfo("验证码输入错误");
				$("#jggsRand").attr("src","/servlet/CheckCodeServlet?"+Math.random());
			}else{
				$("#jggsSearchApp").attr("style","display:block");
				$("#pageCountTd").html(data);
				if(isCheck=="true"){$("#jggsRand").attr("src","/servlet/CheckCodeServlet?"+Math.random());}
			}
	});
}

/**
 * 根据图片分类查找事项
 * @param {Object} appClassify 分类类型
 * @param {Object} pageNum  页数
 */
function searchAppByImage(appClassify,pageNum){
	$.post('/approve/approveQueryResult.html',
		{'departGUID':$("#departGUID").val(),
		 'approveName':$("#approveName").val(),
		 'isOnlineApply':$("#isOnlineApply").val(),
		 'page':pageNum,
		 'streetApp':'true',
		 'approveItemType':appClassify,
		 'orderType':$("#orderType").val(),
		 'ascOrDesc':$("#ascOrDesc").val(),
		 'module':'imageSerch'},
		function(data){
			$("#BS_content_div").html(data);
		});
}
/**
 * 根据部门查找事项
 * @param {Object} departId  //部门ID
 * @param {Object} departName 部门名称
 * @param {Object} pageNum  页数
 */
function searchAppByDepart(departId,departName,pageNum){
	$.post('/approve/approveQueryResult.html',
		{'departGUID':departId,
		 'approveName':$("#approveName").val(),
		 'isOnlineApply':$("#isOnlineApply").val(),
		 'page':pageNum,
		 'streetApp':'true',
		 'module':'depart',
		 'orderType':$("#orderType").val(),
		 'ascOrDesc':$("#ascOrDesc").val(),
		 'departmentName':departName},
		function(data){
			$("#BS_content_div").empty();
			$("#BS_content_div").html(data);
		});
}
/**
 * 街道服务事项查新
 * @param {Object} page 页数
 */
function searchStreet(page){
	$("#levelTowDIV").html('');
	$.post('/approve/approveQueryResult.html',
		{'departGUID':'{0A150154-0000-0000-1037-AD6600000018}',
		 'streetApp':'true',
		 'page':page},
		function(data){
			$("#approveItemDIV").html(data);
	});
}
/**
 * 修改选项卡样式
 * @param {Object} id table的ID
 * @param {Object} index 要修改的选项卡序号
 * @param {Object} count 选项卡总数
 * @param {Object} inClass 选中的样式
 * @param {Object} outCalss 未选中的样式
 */
function changeClass(id,index,count,inClass,outCalss){
	for(var i=0;i<count;i++){
		if(i==index){
			$("#"+id+i).attr("class",inClass);
		}else{
			$("#"+id+i).attr("class",outCalss);
		}
	}
}
/**
 * 改变隐藏项
 */
function searchAtran(status){
	if("0"==status){
		$("#BS_docontent").addClass("hiddenDiv");
		$("#hiddenDiv").removeClass("hiddenDiv");
	}else{
		$("#BS_docontent").removeClass("hiddenDiv");
		$("#hiddenDiv").removeClass("hiddenDiv");
	}
}
/**
 * 隐藏滚屏
 */
function changeHidden(){
	$("#BS_docontent").removeClass("hiddenDiv");
	$("#hiddenDiv").addClass("hiddenDiv");
}
/**
 * 按回车执行搜索
 */
function isSearch(){
	if(event.keyCode == 13){
		return true;  
	}else{
		return false;
	}
}

