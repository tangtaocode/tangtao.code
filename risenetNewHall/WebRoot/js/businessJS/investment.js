/**
 * ����Ͷ��
 */
/**
 * �л�ѡ�
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
 * �л��׶�ѡ�
 * @param {Object} showId
 */
function qyInvestStepChange(showId){
		var qyInvestSteps = $("#qyInvestStep > div").slideUp(600);
		$("#"+showId).slideDown(600);
	}
/**
 * �ܾݻ���ID ���Ҷ�Ӧ����
 * @param {Object} link ����ID
 */
function queryApprove(link){
	$.post('/investment/findApproveItemByLinkCode.html',{linkGUID:link},function(data){
		$("#Portal_Content").html(data);
	});
}
/**
 * �л�Ͷ������ѡ�
 * @param {Object} type Ͷ������
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