/** 引入外部JS */
document.write('<script src="/js/zDialog/risenetDialog.js"></script>');
$(function(){
	/**
	 *	施工工程的施工图设计文件审查合格意见书共享
	 */
	$("#fwsq_sgthgshm").blur(function(){
		if($("#fwsq_sgthgshm").val()==null||$("#fwsq_sgthgshm").val()==""){
			$("#sgthgshm_checked").remove();
			$("#fwsq_sgthgshm").parent().append("<img src='/engine/jquery/themes/icons/no.png' id='sgthgshm_checked'/>");
			alert("“本次施工工程的施工图设计文件审查合格意见书编号”不能为空");
		}else{
			$.post("/onlineService/doInvestigateWebService.html",
				{"qualifiedbookid":$("#fwsq_sgthgshm").val()},
				function(data){
					if(data!=null&&data.queryresult!=null&&data.queryresult==2){
						$("#sgthgshm_checked").remove();
						$("#fwsq_sgthgshm").parent().append("<img src='/engine/jquery/themes/icons/no.png' id='sgthgshm_checked'/>");
						alert(data.errorinfo);
					}else if(data!=null&&data.queryresult!=null&&data.queryresult==1){
						$("#sgthgshm_checked").remove();
						$("#fwsq_sgthgshm").parent().append("<img src='/engine/jquery/themes/icons/checked.gif' id='sgthgshm_checked'/>");
						var message = "审查合格书编号："+data.bacode+"\n工程名称："+data.prjname+"\n工程地址："+data.prjadd+"\n工程类别："+data.prjtype
							+"\n工程等级："+data.prjlevel+"\n工程规模："+data.prjscale+"\n建设单位："+data.jsdw+"\n勘察单位："+data.kcdw+"\n设计单位："+data.sjdw
							+"\n审查机构："+data.scdw;
						Dialog.confirm(message,function(){},function(){
							$("#fwsq_sgthgshm").attr("value","");
						});
					}else{
						$("#sgthgshm_checked").remove();
						$("#fwsq_sgthgshm").parent().append("<img src='/engine/jquery/themes/icons/no.png' id='sgthgshm_checked'/>");
						alert("查无记录");
						$("#fwsq_sgthgshm").attr("value","");
					}
			});
		}
	});
	
	
	/**
	 *	证照共享（深圳市民用建筑施工图设计文件抽查（备案）意见书）
	 */
	$("#fwsq_sgtccyjshm").blur(function(){
		if($("#fwsq_sgtccyjshm").val()==null||$("#fwsq_sgtccyjshm").val()==""){
			$("#sgtccyjshm_checked").remove();
			$("#fwsq_sgtccyjshm").parent().append("<img src='/engine/jquery/themes/icons/no.png' id='sgtccyjshm_checked'/>");
			alert("“深圳市民用建筑施工图设计文件抽查（备案）意见书编号”不能为空");
		}else{
			$.post("/onlineService/doLicenseWebService.html",
				{"zjbh":$("#fwsq_sgtccyjshm").val()},
				function(data){
					if(data!=null&&data.zjbh!=null&&data.zjbh!=""){
						$("#sgtccyjshm_checked").remove();
						$("#fwsq_sgtccyjshm").parent().append("<img src='/engine/jquery/themes/icons/checked.gif' id='sgtccyjshm_checked'/>");
						var message = "证照编号："+data.zjbh+"\n证照名称："+data.zjmc+"\n工程名称："+data.app_info+"\n建设单位："+data.app_unit
							+"\n发证时间："+data.bjsj+"\n证照有效期限："+data.zjyxqx;
						Dialog.confirm(message,function(){},function(){
							$("#fwsq_sgtccyjshm").attr("value","");
						});
					}else{
						$("#sgtccyjshm_checked").remove();
						$("#fwsq_sgtccyjshm").parent().append("<img src='/engine/jquery/themes/icons/no.png' id='sgtccyjshm_checked'/>");
						alert("查无记录");
						$("#fwsq_sgtccyjshm").attr("value","");
					}
			});
		}
	});
	
	
	//保函材料共享监听
	$(".third_yes").click(function(){
		if(!backletterWebService())return false;
	});
	$(".third_no").click(function(){
		if(!backletterWebService())return false;
	});
	$(".investigate_yes").click(function(){
		if(!backletterWebService())return false;
	});
	$(".investigate_no").click(function(){
		if(!backletterWebService())return false;
	});
	
	/**
	 *	材料共享（直接发包审批决定书文号）
	 */
	$("#fwsq_zjfbwh").blur(function(){
		if($("#fwsq_zjfbwh").val()==null||$("#fwsq_zjfbwh").val()==""){
			$("#decition_checked").remove();
			$("#fwsq_zjfbwh").parent().append("<img src='/engine/jquery/themes/icons/no.png' id='decition_checked'/>");
			alert("“直接发包审批决定书文号”不能为空");
		}else{
			$.post("/onlineService/doDecisionWebService.html",
			{"bwbh":$("#fwsq_zjfbwh").val()},
			function(data){
				if(data!=null&&data.wjbt!=null&&data.wjbt!=""){
					$("#decition_checked").remove();
					$("#fwsq_zjfbwh").parent().append("<img src='/engine/jquery/themes/icons/checked.gif' id='decition_checked'/>");
					var message = "文件标题："+data.wjbt+"\n\n申请单位："+data.sqdw+"\n\n办结时间："+data.bjsj;
					Dialog.confirm(message,function(){},function(){
							$("#fwsq_zjfbwh").attr("value","");
					});
				}else{
					$("#decition_checked").remove();
					$("#fwsq_zjfbwh").parent().append("<img src='/engine/jquery/themes/icons/no.png' id='decition_checked'/>");
					alert("查无记录");
					$("#fwsq_zjfbwh").attr("value","");
				}
			});
		}
	});
});


/**
 * 保函材料共享执行
 */
function backletterWebService(){
	if($(".investigate_yes").attr("checked")==$(".third_yes").attr("checked")&&$(".investigate_yes").attr("checked")=="checked"){
		if($("#gcbh").val()==null||$("#gcbh").val()==""){
			$("#third_checked").remove();
			//$(".third_yes").parent().parent().append("<img src='/engine/jquery/themes/icons/no.png' id='third_checked'/>");
			$("#investigate_checked").remove();
			//$(".investigate_yes").parent().parent().append("<img src='/engine/jquery/themes/icons/no.png' id='investigate_checked'/>");
			alert("“工程编号”不能为空");
		}else{
			$.post("/onlineService/doBackletterWebService.html",
			{"strGcbh":$("#gcbh").val()},
			function(data){
				if(data!=null&&data.gcmc!=null&&data.gcmc!=""){
					$("#third_checked").remove();
					$(".third_yes").parent().parent().append("<img src='/engine/jquery/themes/icons/checked.gif' id='third_checked'/>");
					$("#investigate_checked").remove();
					$(".investigate_yes").parent().parent().append("<img src='/engine/jquery/themes/icons/checked.gif' id='investigate_checked'/>");
					var message = "工程名称："+data.gcmc+"\n\n支付保函编号："+data.zfbhbh+"\n\n业主名称："+data.yzmc+"\n\n承包商名称："+data.cbsmc
						+"\n\n日期："+data.rq+"\n\n履约保函编号："+data.lybhbh;
					Dialog.confirm(message,function(){},function(){
							$("#gcbh").attr("value","");
					});
				}else{
					$("#third_checked").remove();
					$(".third_yes").parent().parent().append("<img src='/engine/jquery/themes/icons/no.png' id='third_checked'/>");
					$("#investigate_checked").remove();
					$(".investigate_yes").parent().parent().append("<img src='/engine/jquery/themes/icons/no.png' id='investigate_checked'/>");
					alert("查无记录");
					$("#gcbh").attr("value","");
				}
			});
		}
	}else if($(".investigate_yes").attr("checked")!=$(".third_yes").attr("checked")&&($(".investigate_yes").attr("checked")!=null||$(".investigate_no").attr("checked")!=null)&&($(".third_yes").attr("checked")!=null||$(".third_no").attr("checked")!=null)){
		$("#third_checked").remove();
		$("#investigate_checked").remove();
		alert("请保持“履约保函、支付保函是否需第三方担保”与“履约保函、支付保函合规性审查是否合格”的一致性");
		return false;
	}
	return true;
}