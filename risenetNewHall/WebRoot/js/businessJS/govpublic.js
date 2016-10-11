/**
 * 政务公开
 */
/**
 * 根据部门查询事项列表
 * @param {Object} departGUID  部门GUID
 * @param {Object} isOnline  是否可以在线申报
 * @param {Object} departName  部门名称
 * @param {Object} pageNum 页数
 */
function searchAppList(departGUID,isOnline,departName,pageNum){
	$.post('/approve/approveQueryResult.html',
			{'departGUID':departGUID,
			 'module':'zwgk',
			 'isOnlineApply':isOnline,
			 'departmentName':departName,
			 'approveName':$("#approveName").val(),
			 'orderType':$("#orderType").val(),
			 'ascOrDesc':$("#ascOrDesc").val(),
			 'page':pageNum},
			function(data){
				$("#Portal_Content").html(data);
			});
	}
/**
 * 根据点击表格列进行表格数据排序
 * @param {Object} orderType 排序类型
 */
function changeOrder(orderType){
	$("#orderType").attr("value",orderType);
	var ad = $("#ascOrDesc").val();
	if(ad==""||ad==null||ad=="desc"){
		$("#ascOrDesc").attr("value","asc");
	}else{
		$("#ascOrDesc").attr("value","desc");
	}
	searchAppList($("#departGUID").val(),$("#isOnlineApply").val(),$("#departmentName").val(),$("#page").val());
}
/**
 * 根据事项GUID查询办事指南
 * @param {Object} guid 事项GUID
 * @param {Object} dName 部门名
 * @param {Object} dId 部门ID
 */
function loadAppGUID(guid,dName,dId){
	$.post('/approve/findAppGuideInfo.html',
		{'approveItemGUID':guid,'module':'zwgk','departmentName':dName,'departGUID':dId},
		function(data){
			$("#Portal_Content").html(data);
	});
}
/**
 * 根据部门GUID查询办事窗口和局信息
 * @param {Object} guid 部门GUID
 */
function findwindows(guid){
	$.post('/govpublic/findWindows.html',
		{'departGUID':guid},
		function(data){
			$("#windowsCount").html(data);
	});
}
/**
 * 根据部门查询事项相关的法律法规
 * @param {Object} guid 部门GUID
 * @param {Object} pageNum 页数
 */
function findLawByDepartGUID(guid,pageNum){
	$.post('/govpublic/findLawByDepartGUID.html',
		{'departGUID':guid,
		 'lawName':$("#lawName").val(),
		 'page':pageNum},
		function(data){
			$("#windowsCount").html(data);
		});
}

/**
 * 改变点击后的样式
 * @param {Object} id table的ID
 * @param {Object} index 要切换的选项卡的索引
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
 * 根据地图ID显示图片
 * @param {Object} mapId
 */
function showMap(mapId){
	$("#"+mapId).slideToggle(700);
}