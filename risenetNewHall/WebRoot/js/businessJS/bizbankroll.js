/**
 * 异步获取扶持类别并展开
 * @param {Object} pid 节点GUID 
 * @param {Object} obj 事件源
 */
function searchProType(pid,obj){
		$.post("/bizApply/asyncProjectType.html",{id:pid},function(data){
			var isOpen = $(obj).attr('isOpen');//获取目录打开状态,0为关闭，1为打开
			var $ptr = $(obj).parent();//获取父级节点tr
			if(isOpen=='0'){
				var retract = parseInt($(obj).attr("retract"));//获取锁进度，每次在上一次的缩进度上加3
				var rootGuid = $(obj).attr("rootGuid");//获取root节点
				var $tr,$td,$table,$innerTr,$nameTd,$dateTd,$applyTd; //定义行列并组装，区分样式和是否根节点
				$tr = $('<tr style="display:none" class="odd"></tr>');
				$td = $('<td colspan="3" style="border-top:0px;border-right:0px;width:100%;padding-left:0px;padding-right:0px"></td>');
				$table = $('<table border="0" cellpadding="0" cellspacing="0px" style="width:100%;"></table>')
				for(var i=0;i<data.length;i++){
					if(i%2==0){
						$innerTr = $('<tr class="odd" onmouseout="this.className=\'odd\'" onmouseover="this.className=\'clickHand\'"></tr>');
					}else{
						$innerTr = $('<tr class="even" onmouseout="this.className=\'even\'" onmouseover="this.className=\'clickHand\'"></tr>');
					}
					if(data[i].status=='0'){
						$nameTd = $('<td style="padding-left:'+retract+'0px;" retract="'+(retract+3)+'"><img class="ProTypeImg" src="/images/bizbankroll/files.png"/>&nbsp;&nbsp;<a href="/bizApply/findApplyGuide/id/'+data[i].guid+'/rootGuid/'+rootGuid+'/action.html" target="_blank">'+data[i].name+'</a></td>');
						$applyTd = '<td  width="15%"><a target="_blank" href="/bizApply/initApplyAdd/id/'+data[i].guid+'/rootGuid/'+rootGuid+'/action.html" class="BS_btnblue" >在线申报</a></td>';
					}else if(data[i].status=='1'){
						$nameTd = $('<td style="padding-left:'+retract+'0px;" rootGuid="'+rootGuid+'" retract="'+(retract+3)+'" isOpen="0" onclick="searchProType(\''+data[i].guid+'\',this)"><img class="ProTypeImg" src="/images/bizbankroll/folder_c.png"/>&nbsp;&nbsp;'+data[i].name+'</td>');
						$applyTd = '<td  width="15%">&nbsp;</td>';
					}
					$dateTd = '<td width="15%"> '+data[i].uptime+' </td>';
					$table.append($innerTr.append($nameTd).append($dateTd).append($applyTd));
				}
				$ptr.after($tr.append($td.append($table)));
				$ptr.next().show(500);
				$(obj).children('img').attr('src','/images/bizbankroll/folder_o.png');//更新目录标示图片的打开状态
				$(obj).attr('isOpen','1');//更新目录打开状态
			}else{
				$ptr.next().hide(500,function(){
					$ptr.next().remove();//动画完成时删除上次打开的数据行
				});
				$(obj).children('img').attr('src','/images/bizbankroll/folder_c.png');//更新目录标示图片的打开状态
				$(obj).attr('isOpen','0');//更新目录打开状态
			}
		});
	}
	/**
	 * 切换选项卡
	 * @param {Object} count 选项卡个数
	 * @param {Object} indx 点击事件选型卡索引
	 */
	function changeCard(count,indx){
		for(var i=1;i<=count;i++){
			if(i==indx){
				$("#card"+indx).removeClass();
				$("#card"+indx).addClass("tdCardOn");
				$("#qyInvestStep"+indx).slideDown(600);
			}else{
				$("#card"+i).removeClass();
				$("#card"+i).addClass("tdCardOff");	
				$("#qyInvestStep"+i).slideUp(600);
			}
		}
	}
	
/**
 * 修改申报步骤标示图片
 * @param {Object} step 步骤号
 */
	function changeStep(step){
		$(".f").removeClass("f");
		for(var i=1;i<=3;i++){
			if(i!=step)$("#step"+i+" > img").attr("src","/images/lineservice/img_process0"+i+".png");
		}
		$("#step"+step).addClass("f");
		$("#step"+step+" > img").attr("src","/images/lineservice/img_process0"+step+"b.png")
	}
	
	/**
	 * 申报上一步或者下一步
	 */
	function doStep(step,pon){
		var setpCount = $("#setpCount").val();
		if(step=="1"){
					$("#stepPageCount").hide();
					$("#userInfoCount").show(100,function(){
						$("#prefixStepBut").remove();
						$("#nextStepBut").unbind('click').removeAttr('onclick').click(function(){doStep('2');});
						changeStep(1);
					});
					
		}else if(step=="2"){
			var rootGuid = $("#rootGuid").val();
			var id = $("#id").val();
			var guid = $("#guid").val();
			$.post("/bizApply/applyAdd.html",{'rootGuid':rootGuid,'id':id,'guid':guid},function(data){
				$("#stepPageCount").html(data);
				if(pon=="p"){
					$("#prefixStepBut").unbind('click').removeAttr('onclick').click(function(){doStep('1');});
					$("#temporaryBut").remove()
					$("#submitBut").remove();
					$("#prefixStepBut").after('<input id="nextStepBut" type="button" value="下一步" class="stepButton" onclick="doStep(\'3\');"/>');
				}else{
					$("#userInfoCount").hide();
					$("#stepPageCount").show();
					$("#nextStepBut").before('<input id="prefixStepBut" type="button" value="上一步" class="stepButton" onclick="doStep(\'1\');"/>');
				}
				$("#nextStepBut").unbind('click').removeAttr('onclick').click(function(){doStep('3');});				
				changeStep(2);
			});
		}else if(step=="3"){
			if(!validations("submitForm"))return false;
			$("#enterprises_affirms").attr("value",getEditorValue());
			var formData = $("#submitForm").serializeArray();
			$.post("/bizApply/saveApply.html",formData,function(data){
				if(data.message=="1"){
					Dialog.alert("信息暂存成功，请上传材料！",function(){
						refashFile(3);
					});
				}else if(data.message="0"){
					Dialog.alert("暂存失败！");
				}
			});
		}else if(step="4"){
			if(setpCount=="4"){
				
			}
		}
		
	}
	function updateApp(status){
		$.post("/bizApply/saveApply.html",{'method':'update','status':status},function(data){
				
		});
	}
	function refashFile(step){
		$.post("/bizApply/initFileUpload.html",
			{'guid':$("#guid").val(),'id':$("#id").val()},
			function(data){
				$("#stepPageCount").html(data);
				if(step=="3"){
					$("#prefixStepBut").unbind('click').removeAttr('onclick').click(function(){doStep('2','p');});
					$("#nextStepBut").remove();
					$("#prefixStepBut").after('<input id="temporaryBut" type="button" value="暂存" class="stepButton" onclick="doStep(\'4\');"/>');
					$("#temporaryBut").after('<input id="submitBut" type="button" value="提交" class="stepButton" onclick="doStep(\'4\');"/>');
					changeStep(3);
				}
		});
	}
	function loadApply(){
		$.post("/bizApply/applyAdd.html",{},function(data){
			
		});
	}
