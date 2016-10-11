/**
 * 修改申报步骤标示图片
 * @param {Object} step 步骤号
 */
function changeStep(step){
		$(".f").removeClass("f");
		for(var i=1;i<=5;i++){
			if(i!=step)$("#step"+i+" > img").attr("src","/images/lineservice/img_process0"+i+".png");
		}
		$("#step"+step).addClass("f");
		$("#step"+step+" > img").attr("src","/images/lineservice/img_process0"+step+"b.png");
	}
/**
 * 申报、附件、指南选项卡切换样式
 * @param {Object} indx
 */
	function changeC(indx){
		for(var i=0;i<4;i++){
			if(indx==i){
				$("#card"+i).removeClass("tdCardOff");
				$("#card"+i).addClass("tdCardOn");
			}else{
				$("#card"+i).removeClass("tdCardOn");
				$("#card"+i).addClass("tdCardOff");
			}
		}
	}
	/**
	 * 切换申报、附件、指南选项卡内容
	 * @param {Object} type 选项卡类型
	 */
	function changeCardInfo(type){
		if(type=="apply"){
			$("#countMainInfo2").hide(0,function(){
				$("#countMainInfo2").html("");
				$("#countMainInfo1").slideDown(700);
			});
		}else{
			$.post("/onlineService/findInfo.html",
				{'method':type,'approveItemGUID':$("#approveItemGUID").val()},
				function(data){
					$("#countMainInfo1").hide(0,function(){
						$("#countMainInfo2").css("display","none");
						$("#countMainInfo2").html(data);
						$("#countMainInfo2").slideDown(700);
					});
			});
		}
		
	}
	
	function changeCardIn(type){
		if(type=="forms"){
			$.post("/onlineService/getViewforms.html",
				{appModel:'approveItem',
				'guids':$("#guids").val(),
				'appInstanceGuid':$("#appInstanceGuid").val(),
				'approveItemGUID':$("#approveItemGUID").val()},
				function(data){
					$("#countMainInfo1").hide(0,function(){
						$("#countMainInfo2").css("display","none");
						$("#countMainInfo2").html(data);
						$("#countMainInfo2").slideDown(700);
					});
			});
		}else{
			$.post("/onlineService/findFileByAppGuid.html",
					{'approveItemGUID':$("#approveItemGUID").val(),
					 'appInstanceGuid':$("#appInstanceGuid").val()},
					 function(data){
						 $("#countMainInfo1").hide(0,function(){
								$("#countMainInfo2").css("display","none");
								$("#countMainInfo2").html(data);
								$("#countMainInfo2").slideDown(700);
						 });
					});
		}
	}
	
	/**
	 * 根据事项ID街道ID查找社区信息
	 */
	function findCommunity(){
		$.post("/onlineService/findCommunity.html",
			{'approveItemGUID':$("#approveItemGUID").val(),
			 'departGUID':$("#departGUID").val()},
			 function(data){
			 	var shtml=$('<select>',{"name":"communityGUID","id":"communityGUID","class":"loginInput" });
				for(var i=0;i<data.length;i++){
				 	$('<option>',{val:data[i].code,text:data[i].value}).appendTo(shtml);
				}
				$("#communityDiv").empty();
				$("#communityDiv").append(shtml);
			},"json");
	}
	
	/**
	 * 根据事项ID和申报ID查找材料清单和已上传的材料
	 */
	function findFile(){
		$.post("/onlineService/findFileByAppGuid.html",
				{
				'guids':$("#guids").val(),
				'approveItemGUID':$("#approveItemGUID").val(),
				 'appInstanceGuid':$("#appInstanceGuid").val()},
				 function(data){
					$("#stepPageCount").html(data);
			});
	}
	
	/**
	 * 申报步骤执行
	 * type 类型
	 * step 需要跳转的步骤
	 * current 当前步骤
	 * status 保存状态 0 暂存 1 提交 liuhua
	 */
	function doStep(type,step,current,status){
		//alert('type:'+type);alert('step:'+step);alert('current：'+current);alert('status:'+status);
		$("#light").css("display","block");
		$("#fade").css("display","block");
		var message = "<span style='text-align:left;'>申请人应对填报、上传的资料真实性、合法性负责，</span>" +
		"<span style='text-align:left;'>我局发现或通过举报投诉发现上述</span>" +
		"<span style='text-align:left;'>资料存在违法违规内容的，有权责令申请人改正并依</span>" +
		"<span style='text-align:left;'>法作出行政处罚，由此造成的一切后果由申请人自行</span>" +
		"<span style='text-align:left;'>承担</span>";
		
		if(type=="1"){
			if(step=="1"){
				$.post("/userService/viewUserByType.html",
					{appModel:'approveItem'},
					function(data){
						$("#stepPageCount").html(data);
						$("#prefixStepBut").remove();
						if(current==undefined||current==''){
							$("#nextStepBut").unbind('click').removeAttr('onclick').click(function(){doStep('1','2');});
						}
						else{
							$("#saveCurrent").remove();							
							$("#submitCurrent").before('<input id="nextStepBut" type="button" value="下一步" class="stepButton" onclick="doStep(\'1\',\'2\');"/>');
							$("#submitCurrent").remove();
						}
						changeStep(1);
				});
			}else if(step=="2"){
				//企业名称/单位名称/真实姓名为空时，跳转到修改企业信息
				if(current==undefined||current==''){
					if(!companyNull()){
						return false;	
					}
					$("#light").css("display","none");
					$("#fade").css("display","none");
					showMaxConfirm(message,function(){
						$.post("/onlineService/findFileByAppGuid.html",
								{'approveItemGUID':$("#approveItemGUID").val(),
								 'appInstanceGuid':$("#appInstanceGuid").val()},
								function(data){
									$("#stepPageCount").html(data);
									$("#prefixStepBut").remove();
									$("#nextStepBut").before('<input id="prefixStepBut" type="button" value="上一步" class="stepButton" onclick="doStep(\'1\',\'1\',\'2\');"/>');
									$("#nextStepBut").remove();
									$("#prefixStepBut").after('<input type="button" id="saveCurrent" value="暂 存" class="stepButton" onclick="doStep(\'1\',\'3\',\'\',\'0\')"/>'
									+'<input id="submitCurrent" type="button" value="提交" class="stepButton" onclick="doStep(\'1\',\'3\',\'\',\'1\');"/>');
									//$("#nextStepBut").unbind('click').removeAttr('onclick').click(function(){doStep('1','3');});
									changeStep(2);
							});
					});
				}else{
					$.post("/onlineService/findFileByAppGuid.html",
							{'approveItemGUID':$("#approveItemGUID").val(),
							 'appInstanceGuid':$("#appInstanceGuid").val()},
							function(data){
								$("#stepPageCount").html(data);
								$("#prefixStepBut").remove();
								$("#nextStepBut").before('<input id="prefixStepBut" type="button" value="上一步" class="stepButton" onclick="doStep(\'1\',\'1\',\'2\');"/>');
								$("#nextStepBut").remove();
								$("#prefixStepBut").after('<input type="button" id="saveCurrent" value="暂 存" class="stepButton" onclick="doStep(\'1\',\'3\',\'\',\'0\')"/>'
								+'<input id="submitCurrent" type="button" value="提交" class="stepButton" onclick="doStep(\'1\',\'3\',\'\',\'1\');"/>');
								
								//$("#nextStepBut").unbind('click').removeAttr('onclick').click(function(){doStep('1','3');});
								changeStep(2);
						});
				}
			}else if(step=="3"){
				$.post("/onlineService/isUpFile.html",
						{'appInstanceGuid':$("#appInstanceGuid").val(),'approveItemGUID':$("#approveItemGUID").val(),'issubmit':status},
						function(data){
							if (data.message == '0'&&status!=0) {
								showInfo("带&nbsp;<span style='color:red;'>*</span>&nbsp;的材料为必备材料，请确保都已上传或填写！");
							}else{
								//var flag = validateZbwj();
								//暂存
								if(status==0){
									$("#issubmit").attr("value",status);
									$("#status").attr("value","未受理(暂存)");
								}
								else{
									$("#issubmit").attr("value",status);
									$("#status").attr("value","未受理");
								}
								doAddApply();
								changeStep(3);
							}
				});
					
			}
		}else if(type=="2"){
			if(step=="1"){
				$.post("/userService/viewUserByType.html",
					{appModel:'approveItem'},
					function(data){
						$("#stepPageCount").html(data);						
						//$("#nextStepBut").unbind('click').removeAttr('onclick').click(function(){doStep('2','2');});						
						if(current==undefined||current==''){
							$("#nextStepBut").unbind('click').removeAttr('onclick').click(function(){doStep('2','2');});
						}
						//从第2步按上一步
						else{
							$("#nextStepBut").remove();							
							$("#prefixStepBut").before('<input id="nextStepBut" type="button" value="下一步" class="stepButton" onclick="doStep(\'2\',\'2\');"/>');
							
						}
						$("#prefixStepBut").remove();
						changeStep(1);
				});
			}else if(step=="2"){				
				//企业名称/单位名称/真实姓名为空时，跳转到修改企业信息
				if(current==undefined||current==''){
					if(!companyNull()){
						return false;
					}
					$("#light").css("display","none");
					$("#fade").css("display","none");
					
					showMaxConfirm(message,function(){
						$.post("/onlineService/getforms.html",
								{appModel:'approveItem',
								'guids':$("#guids").val(),
								'appInstanceGuid':$("#appInstanceGuid").val(),
								'approveItemGUID':$("#approveItemGUID").val()},
								function(data){
									$("#stepPageCount").html(data);
									$("#prefixStepBut").remove();
									$("#nextStepBut").before('<input id="prefixStepBut" type="button" value="上一步" class="stepButton" onclick="doStep(\'2\',\'1\',\'2\');"/>');
									$("#nextStepBut").remove();
									//地质灾害治理工程开工批复申领、地质灾害治理工程竣工验收备案审核
									//if("{7F000001-FFFF-FFFF-A669-F720FFFFFF82}"==$("#approveItemGUID").val()||"{7F000001-FFFF-FFFF-A669-F729FFFFFF85}"==$("#approveItemGUID").val()){
									//	$("#prefixStepBut").after('<input id="nextStepBut" type="button" value="下一步" class="stepButton" onclick="doStep(\'2\',\'4\');"/>');
									//}else{
										$("#prefixStepBut").after('<input id="nextStepBut" type="button" value="下一步" class="stepButton" onclick="doStep(\'2\',\'3\');"/>');
									//}
									changeStep(2);
						});
					});
				}else{
					$.post("/onlineService/getforms.html",
							{appModel:'approveItem',
							'guids':$("#guids").val(),
							'appInstanceGuid':$("#appInstanceGuid").val(),
							'approveItemGUID':$("#approveItemGUID").val()},
							function(data){
								$("#stepPageCount").html(data);							
								
								//$("#nextStepBut").remove();
								//地质灾害治理工程开工批复申领、地质灾害治理工程竣工验收备案审核
								//if("{7F000001-FFFF-FFFF-A669-F720FFFFFF82}"==$("#approveItemGUID").val()||"{7F000001-FFFF-FFFF-A669-F729FFFFFF85}"==$("#approveItemGUID").val()){
								//	$("#prefixStepBut").after('<input id="nextStepBut" type="button" value="下一步" class="stepButton" onclick="doStep(\'2\',\'4\');"/>');
								//}else{
									$("#prefixStepBut").after('<input id="nextStepBut" type="button" value="下一步" class="stepButton" onclick="doStep(\'2\',\'3\');"/>');
								//}
									$("#prefixStepBut").remove();
									$("#saveCurrent").remove();
									$("#submitCurrent").remove();
									$("#nextStepBut").before('<input id="prefixStepBut" type="button" value="上一步" class="stepButton" onclick="doStep(\'2\',\'1\',\'2\');"/>');
								changeStep(2);
					});
				}
			}else if(step=="3"){
				//填完申请表才能去上传材料
				$.post("/onlineService/isCommitForms.html",{
					'guids':$("#guids").val(),
					'approveItemGUID':$("#approveItemGUID").val(),
					'appInstanceGuid':$("#appInstanceGuid").val()},
					function(data){
						if(data.message==1){
							isFilledForm=false;
							showInfo(data.msg);
								return false;
							}else{
								refFileListStep();
							}
						}
				);
				
			}else if(step=="4"){
				$.post("/onlineService/isUpFile.html",
						{'appInstanceGuid':$("#appInstanceGuid").val()},
						function(data){
							if (data.message == '0'&&status!=0) {
								showInfo("带&nbsp;<span style='color:red;'>*</span>&nbsp;的材料为必备材料，请确保都已上传或填写！");
							}else{
								//暂存
								if(status==0){
									$("#issubmit").attr("value",status);
									$("#status").attr("value","未受理(暂存)");
								}
								else{
									$("#issubmit").attr("value",status);
									$("#status").attr("value","未受理");
								}
								doAddApply();
								changeStep(4);
							}
				});
			}
		}else if(type=="3"){
			if(step=="1"){
				$.post("/userService/viewUserByType.html",
					{appModel:'approveItem'},
					function(data){
						$("#stepPageCount").html(data);
						//$("#prefixStepBut").remove();
						//$("#nextStepBut").unbind('click').removeAttr('onclick').click(function(){doStep('3','2');});
						if(current==undefined||current==''){
							$("#nextStepBut").unbind('click').removeAttr('onclick').click(function(){doStep('3','2');});
						}
						//从第2步按上一步
						else{
							$("#nextStepBut").remove();							
							$("#prefixStepBut").before('<input id="nextStepBut" type="button" value="下一步" class="stepButton" onclick="doStep(\'3\',\'2\');"/>');
							
						}
						$("#prefixStepBut").remove();
						
						changeStep(1);
				});
			}else if(step=="2"){
				
				//企业名称/单位名称/真实姓名为空时，跳转到修改企业信息
				if(current==undefined||current==''){
					if(!companyNull()){
						return false;
					}
					$("#light").css("display","none");
					$("#fade").css("display","none");
					showMaxConfirm(message,function(){
						$.post("/onlineService/gettypes.html",
								{appModel:'approveItem',
								'guids':$("#guids").val(),
								'appInstanceGuid':$("#appInstanceGuid").val(),
								'approveItemGUID':$("#approveItemGUID").val()},
								function(data){
									$("#stepPageCount").html(data);
									$("#prefixStepBut").remove();
									$("#nextStepBut").before('<input id="prefixStepBut" type="button" value="上一步" class="stepButton" onclick="doStep(\'3\',\'1\',\'2\');"/>');
									$("#nextStepBut").remove();
									$("#prefixStepBut").after('<input id="nextStepBut" type="button" value="下一步" class="stepButton" onclick="doStep(\'3\',\'3\');"/>');
									changeStep(2);
						});
					});
				}else{
					$.post("/onlineService/gettypes.html",
							{appModel:'approveItem',
							'guids':$("#guids").val(),
							'appInstanceGuid':$("#appInstanceGuid").val(),
							'approveItemGUID':$("#approveItemGUID").val()},
							function(data){
								$("#stepPageCount").html(data);
								$("#prefixStepBut").remove();
								$("#nextStepBut").before('<input id="prefixStepBut" type="button" value="上一步" class="stepButton" onclick="doStep(\'3\',\'1\',\'2\');"/>');
								$("#nextStepBut").remove();
								$("#prefixStepBut").after('<input id="nextStepBut" type="button" value="下一步" class="stepButton" onclick="doStep(\'3\',\'3\');"/>');
								changeStep(2);
					});
				}
			}else if(step=="3"){
				var guids = $("#guids").val();
				var approveItemGUID = $("#approveItemGUID").val();
				//施工许可、提前介入做的特殊处理
				if($.trim(guids)==""){
					showInfo("请勾选材料情形");
					return;
				}
				if(($.trim(approveItemGUID)=='{7F000001-0000-0000-7185-AA7900000004}' || $.trim(approveItemGUID)=='{0A0100A8-FFFF-FFFF-D635-E37A0000008C}') 
						&& $.trim(guids)=='{7F000001-FFFF-FFFF-FB7F-856400000001}'){
					showInfo("请勾选材料情形");
					return;
				}
				
				$.post("/onlineService/getforms.html",
						{appModel:'approveItem',
						'guids':$("#guids").val(),
						'stepFlag':'1',
						'appInstanceGuid':$("#appInstanceGuid").val(),
						'approveItemGUID':$("#approveItemGUID").val()},
						function(data){
							$("#stepPageCount").html(data);
							//$("#prefixStepBut").remove();
							//$("#nextStepBut").before('<input id="prefixStepBut" type="button" value="上一步" class="stepButton" onclick="doStep(\'3\',\'2\',\'3\');"/>');
							//$("#nextStepBut").remove();
							
							if($.trim(guids)!="" && guids.indexOf("{7F000001-FFFF-FFFF-FB7F-856400000001}") !=-1){
								$("#prefixStepBut").remove();
								$("#nextStepBut").before('<input id="prefixStepBut" type="button" value="上一步" class="stepButton" onclick="doStep(\'3\',\'2\',\'3\');"/>');
								$("#nextStepBut").remove();							
								$("#prefixStepBut").after('<input id="nextStepBut" type="button" value="下一步" class="stepButton" onclick="doStep(\'3\',\'4\');"/>');
							}else{								
								if(current==undefined||current==''){
									$("#prefixStepBut").remove();
									$("#nextStepBut").before('<input id="prefixStepBut" type="button" value="上一步" class="stepButton" onclick="doStep(\'3\',\'2\',\'3\');"/>');
									$("#nextStepBut").remove();	
									$("#prefixStepBut").after('<input id="nextStepBut" type="button" value="下一步" class="stepButton" onclick="doStep(\'3\',\'5\');"/>');
								}
								//从第5步按上一步
								else{									
									$("#prefixStepBut").after('<input id="nextStepBut" type="button" value="下一步" class="stepButton" onclick="doStep(\'3\',\'5\');"/>');
									$("#prefixStepBut").remove();
									$("#saveCurrent").remove();
									$("#submitCurrent").remove();
									$("#nextStepBut").before('<input id="prefixStepBut" type="button" value="上一步" class="stepButton" onclick="doStep(\'3\',\'2\',\'3\');"/>');
								}
							}
							changeStep(3);
				});
			}else if(step=="4"){
				var guids = $("#guids").val();
				$.ajax({
					url:"/onlineService/getforms.html",		
					async:false,
					type:"post",
					data:{appModel:'approveItem',
						'guids':$("#guids").val(),
						'stepFlag':"2",
						'appInstanceGuid':$("#appInstanceGuid").val(),
						'approveItemGUID':$("#approveItemGUID").val()},
					success:function(data){
						$("#stepPageCount").html(data);	
						if(current==undefined||current==''){
							$("#nextStepBut").remove();
							$("#prefixStepBut").after('<input id="nextStepBut" type="button" value="下一步" class="stepButton" onclick="doStep(\'3\',\'5\');"/>');
							$("#prefixStepBut").remove();							
							$("#nextStepBut").before('<input id="prefixStepBut" type="button" value="上一步" class="stepButton" onclick="doStep(\'3\',\'3\',\'4\');"/>');
						
						}
						//从第5步按上一步
						else{
							$("#prefixStepBut").after('<input id="nextStepBut" type="button" value="下一步" class="stepButton" onclick="doStep(\'3\',\'5\');"/>');
							$("#prefixStepBut").remove();
							$("#saveCurrent").remove();
							$("#submitCurrent").remove();
							$("#nextStepBut").before('<input id="prefixStepBut" type="button" value="上一步" class="stepButton" onclick="doStep(\'3\',\'3\',\'4\');"/>');
						
						}					
						
						changeStep(3);
					}
				});
			}else if(step=="5"){
				var guids = $("#guids").val();
				//填完申请表才能去上传材料
				$.post("/onlineService/isCommitForms.html",{
					'guids':$("#guids").val(),
					'approveItemGUID':$("#approveItemGUID").val(),
					'appInstanceGuid':$("#appInstanceGuid").val()},
					function(data){
						if(data.message==1){
							isFilledForm=false;
							showInfo(data.msg);
								return false;
							}else{
								refFileListStep1();
							}
						 });
				
			}else if(step=="6"){
				var guids = $("#guids").val();
				$.post("/onlineService/isUpFile.html",
						{
						'guids':$("#guids").val(),
						'approveItemGUID':$("#approveItemGUID").val(),
						'appInstanceGuid':$("#appInstanceGuid").val()},
						function(data){
							if (data.message == '0'&&status!=0) {
								showInfo("带&nbsp;<span style='color:red;'>*</span>&nbsp;的材料为必备材料，请确保都已上传或填写！");
							}else{
								//暂存
								if(status==0){
									$("#issubmit").attr("value",status);
									$("#status").attr("value","未受理(暂存)");
								}
								else{
									$("#issubmit").attr("value",status);
									$("#status").attr("value","未受理");
								}
								doAddApply();
								changeStep(5);
							}
				});
			}
			
		}
		
	}

	
	function refFileListStep(){
		//刷新材料
		$.post("/onlineService/findFileByAppGuid.html",
			{'approveItemGUID':$("#approveItemGUID").val(),
			 'appInstanceGuid':$("#appInstanceGuid").val()},
			 function(data){
				$("#stepPageCount").html(data);
				$("#prefixStepBut").remove();
				$("#nextStepBut").before('<input id="prefixStepBut" type="button" value="上一步" class="stepButton" onclick="doStep(\'2\',\'2\',\'3\');"/>');
				$("#nextStepBut").remove();
				$("#prefixStepBut").after('<input type="button" id="saveCurrent" value="暂 存" class="stepButton" onclick="doStep(\'2\',\'4\',\'\',\'0\');"/>'
				+'<input id="submitCurrent" type="button" value="提交" class="stepButton" onclick="doStep(\'2\',\'4\',\'\',\'1\');"/>');
				//$("#prefixStepBut").after('<input id="nextStepBut" type="button" value="下一步" class="stepButton" onclick="doStep(\'2\',\'4\');"/>');
				changeStep(3);
		});
	}
	function refFileListStep1(){
		//刷新材料
		var guids = $("#guids").val();
		$.post("/onlineService/findFileByAppGuid.html",
			{
			'guids':guids,
			'approveItemGUID':$("#approveItemGUID").val(),
			 'appInstanceGuid':$("#appInstanceGuid").val()},
			 function(data){
				$("#stepPageCount").html(data);
				$("#prefixStepBut").remove();
				if($.trim(guids)!="" && guids.indexOf("{7F000001-FFFF-FFFF-FB7F-856400000001}") !=-1){
					$("#nextStepBut").before('<input id="prefixStepBut" type="button" value="上一步" class="stepButton" onclick="doStep(\'3\',\'4\',\'5\');"/>');
				}else{
					$("#nextStepBut").before('<input id="prefixStepBut" type="button" value="上一步" class="stepButton" onclick="doStep(\'3\',\'3\',\'5\');"/>');
				}
				$("#nextStepBut").remove();
				$("#prefixStepBut").after('<input type="button" id="saveCurrent" value="暂 存" class="stepButton" onclick="doStep(\'3\',\'6\',\'\',\'0\')"/>'
				+'<input id="submitCurrent" type="button" value="提交" class="stepButton" onclick="doStep(\'3\',\'6\',\'\',\'1\');"/>');
				//$("#prefixStepBut").after('<input id="nextStepBut" type="button" value="下一步" class="stepButton" onclick="doStep(\'3\',\'6\');"/>');
				changeStep(4);
		});
	}
	
	/**
	 * 申报提交数据
	 */
	function doAddApply(){
		var isFilledForm=true;
		//此处是未加提交表单判断前的保存执行代码
		$("#bureauguid").attr("value", $("#departGUID").val());
		$("#deptguid").attr("value", $("#communityGUID").val());
		var formData = $("#appInstanceForm").serializeArray();
		$.post("/onlineService/saveApply.html", formData, function(data){
		if (data.message == "0") {
			showInfo("申报失败！");
		}else {
			$("#stepPageCount").html(data);
			$("#saveCurrent").remove();
			$("#prefixStepBut").remove();
			$("#submitCurrent").attr("value","关闭");
			$("#submitCurrent").unbind('click').removeAttr('onclick').click(function(){window.close();});
		}
		});		
		
		
		
	}
	
	function showMap(mapId){
		$("#"+mapId).slideToggle(700);
	}
	
	/**
	 * 168。。测试数据共享
	 * update Jon
	 */		
	/*function selectGcInfo(){
		var approveItemGUID = $("#approveItemGUID").val();
		var shareId="";
		//建设工程服务（货物）招标文件备案
	   if(approveItemGUID=="{7F000001-FFFF-FFFF-A669-F75700000079}"){shareId="{0A009FA8-FFFF-FFFF-CC81-E2D000000582}";}
       //深圳市前期物业管理招标文件备案（内容与前期物业招标公告和招标组织形式备案一致）
       if(approveItemGUID=="{0A009FA8-FFFF-FFFF-8CE7-CD220000006B}"){shareId="{0A009FA8-FFFF-FFFF-CC85-E76700000583}";} 
       //深圳市物业管理招标文件备案 
       if(approveItemGUID=="{0A009FA8-0000-0000-68C9-3CDB0000004C}"){shareId="{0A009FA8-FFFF-FFFF-CC8D-955F00000585}";} 
       //建设工程施工（监理）招标文件备案
	   if(approveItemGUID=="{0A009FA8-FFFF-FFFF-8836-09C6000004FF}"){shareId="{0A009FA8-FFFF-FFFF-CC89-E3E600000584}";}  
	     
	   var url = "/approve/shared/sharedSelect.jsp?sharedGuid="+shareId
	   window.showModalDialog(url,window,"dialogWidth:750px;dialogHeight:450px;center:yes;status:no;help:no;");
	}
	 */
			
	
	
	
	/*function validateZbwj(){
		//var hall_gcmc = $("#GONGCHENGMINGCHENG");
		//var hall_gcbh = $("#GONGCHENGBIANHAO");
		//var hall_xmmc = $("#XIANGMUMINGCHENG");
		//var span_gcmc = $("#span_gcmc");
		//var span_gcbh = $("#span_gcbh");
		//var span_xmmc = $("#span_xmmc");
		var flag = true;
		
		if(hall_gcmc.val() == "undefined" || $.trim(hall_gcmc.val())==""){
			span_gcmc.html("请填写工程名称！");
			flag = false;
		}else{
			span_gcmc.html("*");
		}
		
		if(hall_gcbh.val() == "undefined" || $.trim(hall_gcbh.val())==""){
			span_gcbh.html("请填写工程编号！");
			flag = false;
		}else{
			span_gcbh.html("*");
		}
		
		if(hall_xmmc.val() == "undefined" || $.trim(hall_xmmc.val())==""){
			span_xmmc.html("请填写项目名称！");
			flag = false;
		}else{
			span_xmmc.html("*");
		}
		return flag;
	}*/
	
	function showFile(id){
		var para = 'fileGuid='+id;
		    para = para+ '&method=approveItem';
		    para = para+ '&appInstanceGuid='+$("#appInstanceGuid").val();
		    para = para+ '&approveItemGUID='+$("#approveItemGUID").val();
		    para = para+ '&departGUID='+$("#departGUID").val();
		    para = para+ '&communityGUID='+$("#communityGUID").val();
		    showUpFilePanel('文件上传','/onlineService/initFileUpPanel.html?'+para,'approveItem');
	}
	
	function deleteFile(fileGuid,type,needDel){
		$.post("/riseFile/deleteFile.html",
			{'fileGUID':fileGuid,'modeType':'approveItem'},
			function(data){
				if(data.message=="1"){
					//showInfo("删除材料成功！");
					needDel.parent().parent().remove();
				}else{
					showInfo("删除材料失败！");
				}
			});
	}
	
	function deleteDoc(fileGuid,needDel){
		$.post("/onlineService/deleteDocType.html",{'fileGuid':fileGuid},function(data){
			if(data.message=="1"){
				//Dialog.alert("删除成功！",function(){
				needDel.parent().parent().remove();
				//});
			}else{
				showInfo("删除失败！");
			}
		});
	}
	/*
	function judgeFill(itemId){
		if(itemId=='{7F000001-FFFF-FFFF-A669-F75700000079}'||itemId=='{0A009FA8-FFFF-FFFF-8836-09C6000004FF}')
		{
			 if($("#SGGK_xmmc").val()==null||$("#SGGK_xmmc").val()==""){showInfo('请填写项目名称！');return false;}
			 if($("#SGGK_xmbh").val()==null||$("#SGGK_xmbh").val()==""){showInfo('请填写项目编号！');return false;}
			 if($("#SGGK_zbr").val()==null||$("#SGGK_zbr").val()==""){showInfo('请填写建设单位！');return false;}
			 if($("#GONGCHENGMINGCHENG").val()==null||$("#GONGCHENGMINGCHENG").val()==""){showInfo('请填写工程名称！');return false;}
			 //if($("#GONGCHENGBIANHAO").val()==null||$("#GONGCHENGBIANHAO").val()==""){showInfo('请填写工程编号！');return false;}	 
		 }
		if(itemId=='{0A009FA8-FFFF-FFFF-8CE7-CD220000006B}'||itemId=='{0A009FA8-0000-0000-68C9-3CDB0000004C}')
		{
			 if($("#SGGK_xmmc").val()==null||$("#SGGK_xmmc").val()==""){showInfo('请填写项目名称！');return false;}
			 //if($("#SGGK_xmbh").val()==null||$("#SGGK_xmbh").val()==""){showInfo('请填写项目编号！');return false;}
			 if($("#SGGK_zbr").val()==null||$("#SGGK_zbr").val()==""){showInfo('请填写建设单位！');return false;}
			 if($("#GONGCHENGMINGCHENG").val()==null||$("#GONGCHENGMINGCHENG").val()==""){showInfo('请填写工程名称！');return false;}
			 //if($("#GONGCHENGBIANHAO").val()==null||$("#GONGCHENGBIANHAO").val()==""){showInfo('请填写工程编号！');return false;}	 
		 }
		 return true;
	}
	*/
	//企业名称/单位名称/真实姓名为空时，跳转到修改企业信息
	function companyNull(){
		var $ename = $("#ename");
		if($ename.text()==null||$ename.text().replace(" ","")==""){
			showInfo("请先填写 用户基本信息");
			editUser('approveItem');
			return false;
		}
		return true;
	}