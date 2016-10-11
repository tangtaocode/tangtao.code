/**
 * 政府投资
 */
/**
 * 切换选项卡
 * @param {Object} obj 
 * @param {Object} tableId
 * @param {Object} defaultClass
 * @param {Object} selectedClass
 */
function tabSelected(obj,tableId,defaultClass,selectedClass){
		var $tabs = $("#"+tableId+" td > div");//document.getElementById(tableId);
		$tabs.removeClass();
		$tabs.addClass(defaultClass);
		$(obj).removeClass();
		$(obj).addClass(selectedClass);
	}
/**
 * 切换阶段选项卡
 * @param {Object} showId
 */
function qyInvestStepChange(showId){
		var qyInvestSteps = $("#qyInvestStep > div").slideUp(600);
		$("#"+showId).slideDown(600);
	}
/**
 * 很据环节ID 查找对应事项
 * @param {Object} link 环节ID
 */
function queryApprove(link){
	$.post('/investment/findApproveItemByLinkCode.html',{linkGUID:link},function(data){
		$("#Portal_Content").html(data);
	});
}
/**
 * 切换投资类型选项卡
 * @param {Object} type 投资类型
 */
function changeTzType(type){
	$.post("/investment/investmentInitAction.html",
	{'method':type,
	 'approveItemGUID':'{09A16D4E-FFFF-FFFF-B1F8-358400002EB4}'},
	function(data){
		$("#mainCount").css("display","none");
		$("#mainCount").html(data);
		$("#mainCount").show(700);
	});
	
}