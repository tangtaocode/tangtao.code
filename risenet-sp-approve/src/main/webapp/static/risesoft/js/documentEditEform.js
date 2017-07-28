var frameID = newGuid();
var documentTitle = "";

//表单字段输入校验，如果有一个表单校验不通过，则都不保存。
function validInput() {
	var formId = formIds.split(",");
	var msg = true;
	//表单字段输入校验，如果有一个表单校验不通过，则都不保存。
	for (var i = 1; i <= formId.length; i++) {
		var obj = document.getElementById('form'+i).contentWindow.validInput();
		if(obj==false){
			msg = false;
		}
	}
	return msg;
}

//保存电子表单数据
function saveForm(objForm,i,action_Type) {
	var dtd = $.Deferred(); // 在函数内部，新建一个Deferred对象
	var formLength = formIds.split(",").length;
	var itembox = document.getElementById("itembox").value;
	//alert(itembox);
	//alert(taskDefKey);
	var isDisabled=(itembox == 'doing' || itembox == 'done'||itembox == 'bqbz'||itembox == 'pause'||itembox == 'history'||!(taskDefKey == "dengji"));
	
	$('input,select,textarea',objForm).removeAttr('disabled');
	objForm.ajaxSubmit({
		async:false, 
		cache:false,
        type: 'post', 
    	url : ctx +"/eform/engine/saveForm?action_Type="+action_Type,
        dataType : 'json',
        success: function(data) { // data 保存提交后返回的数据，一般为 json 数据
			dtd.resolve(); // 改变deferred对象的执行状态
        },
        error:function(data){ 
        	alert("保存失败");
    		dtd.reject(); // 改变deferred对象的执行状态
        },
        complete :function(){
        	//再次使表单元素不可用
        	if (isDisabled) {
        		$('input,select,textarea',objForm).attr('disabled','disabled');
			}
        }
    });
	
	
	return dtd.promise();
 }

//提交表单
function submitForm(){
	var guid;
	var formDatas = [];
	var formData;
	var formId = formIds.split(",");
	var formEdittype = formEdittypes.split(",");//表单模板模式
	for (var i = 1; i <= formId.length; i++) {
		var action_Type = formEdittype[i-1];
		var temp_Id = formId[i-1];
		if(formEdittype[i-1]=="0"){//根据流程实例processInstanceId，查询绑定的表是否已经存在数据，如果存在，模板为编辑模式
			$.ajax({
				async:false, 
				cache:false,
				type : "POST",
				url : ctx + "/sp/document/getData",
				data : {
					tempId : temp_Id,
					processInstanceId : $("#processInstanceId").val()
				},
				success : function(data) {
					action_Type = data.edittype;
				}
			});
		}
		var objForm = $("iframe[name='eform']").contents().find("form[name='frm_Edit_"+temp_Id+"']");//表单form对象
		var objProcess = objForm.contents().find("input[name='processInstanceId']");
		var objGuid = objForm.contents().find("input[name='guid']");
		objProcess.val($("#processInstanceId").val());
		objGuid.val($("#processInstanceId").val());
		formData = saveForm(objForm,i,action_Type);
	}
	formDatas.push(formData);
	return formDatas;
}

//保存标题，流程启动后再保存或发送时，有可能标题已经被修改，所以还需要再保存一次
function saveTitle() {
	var dtd = $.Deferred(); // 在函数内部，新建一个Deferred对象
	$.ajax({
		type : "POST",
		url : ctx + "/buttonOperation/saveTitle",
		data : {
			taskId : $('#taskId').val(),
			documentTitle : documentTitle
		},
		error : function() {
			alert("保存失败");
			dtd.reject(); // 改变deferred对象的执行状态
		},
		success : function(data) {
			if (data.success == true) {
				dtd.resolve(); // 改变deferred对象的执行状态
			} else {
				alert("保存失败!");
				dtd.reject(); // 改变deferred对象的执行状态
			}
		}
	});
	return dtd.promise();
}


//启动流程
function startProcess() {
	var dtd = $.Deferred(); // 在函数内部，新建一个Deferred对象
	$.ajax({
		async:false, 
		cache:false,
		type : "POST",
		url : ctx + "/sp/document/startProcess",
		dataType:'JSON',
		data : {
			processDefinitionKey : $('#processDefinitionKey').val(),
			processSerialNumber : $('#processSerialNumber').val(),
			tenantId : $('#tenantId').val(),
			activitiUser : $('#activitiUser').val(),
			SPinstanceId : SPinstanceId,
			status : status,
			documentTitle : documentTitle
		},
		error : function() {
			alert("网络错误!"); // 改变deferred对象的执行状态
			dtd.reject(); // 改变deferred对象的执行状态
		},
		success : function(data) {
			if (data.success == true) {
				$("#taskId").val(data.taskId);
				$("#processInstanceId").val(data.processInstanceId);
				if ($.trim($("#taskId").val()).length == 0) {
					alert('流程启动出错');// taskId为空
					dtd.reject(); // 改变deferred对象的执行状态
				} else {
					dtd.resolve(); // 改变deferred对象的执行状态
				}
			} else {
				alert("流程启动失败!");
				dtd.reject(); // 改变deferred对象的执行状态
			}
		}
	});
	return dtd.promise();
}

//最终的公文办结功能
function banjie(documentTitle){
	$('#documentTitle').val(documentTitle);
	$.ajax({
		type : "POST",
		url : ctx + "/sp/document/getBanjieType",
		data : {
			processInstanceId : processInstanceId
		},
		success : function(data) {
			//var value = document.getElementById('form2').contentWindow.document.getElementById('bjd_certifyWay').value;
			var type = data.type;
			if(type=="证照"&&data.PiZhun=="yes"){//当为出证办结件，且批准通过时，才出证办结，否则直接办结
				/*if(!(value=="证照")){
					alert("该件为出证办结件，请选择“证照”");
					return false;
				}*/
				chuZhengBanJie();
			}else{
				$("#documentForm").ajaxSubmit({
					type : 'POST',
					dataType : 'json',
					url : ctx + '/sp/document/complete',
					success : function(responseText, statusText, xhr, $form) {
						alert(responseText.msg);
						if (responseText.success == true) {
							window.parent.refreshMenu();
						} 
					}
				});
			}
		}
	});
}

//验证是否签写意见
function checkSignOpinion(){
	if(isSignOpinion=="false")return true;//当前任务节点配置是否必签意见，当为false时,表示不检验是否签意见。
	var checkSignOpinion;
	$.ajax({
	    async:false,
	    cache: false,
      type: "GET",
      url: ctx + "/opinion4New/checkSignOpinion",
      data: {taskId:$('#taskId').val(),processSerialNumber:$('#processSerialNumber').val()},
      dataType: "json",
      success: function(data){
      	checkSignOpinion=data.checkSignOpinion;
      	if(!checkSignOpinion){
      		alert("请签写个人意见或者领导意见！");
      	}
      }
	});
	return checkSignOpinion;
}

//验证是否签写意见，签写默认意见
function signOpinion(){
	var checkSignOpinion;
	$.ajax({
	    async:false,
	    cache: false,
      type: "GET",
      url: ctx + "/opinion4New/checkSignOpinion",
      data: {taskId:$('#taskId').val(),processSerialNumber:$('#processSerialNumber').val()},
      dataType: "json",
      success: function(data){
      	checkSignOpinion=data.checkSignOpinion;
      }
	});
	return checkSignOpinion;
}

//签写默认意见
function writeOpinion(routeToTaskId,text){
		$( "#dialog-confirmdelete" ).dialog({
			title:'确认发送',
			resizable: false,
			width : 200,
			height:150,
			modal: true,
			buttons:[{
				text:'确定',
				handler:function() {
					documentTitle = document.getElementById('form1').contentWindow.document.getElementById('title').value; 
					$('#documentTitle').val(documentTitle);
					var taskId = $('#taskId').val();
					if (taskId == "") {
						startProcess().done(function() {// 启动流程时已经保存标题了
							$.when.apply($, submitForm()).done(
									function() {
										//填写意见
										document.getElementById('form2').contentWindow.writeOpinion();
										if(routeToTaskId=="cailiaochushen"||routeToTaskId=="chuangkourenyuanbanjie"){
											executeSend(routeToTaskId,"sendToStartUser","");
										}else if(routeToTaskId=="buyushouli"){
											reajectReason("buYuShouLi","不予受理");
										}else if(routeToTaskId=='waidanweibanli'){// 转外单位办理
											choiceDept("","",routeToTaskId);
										}else{
											//docUserChoise(routeToTaskId);
											getRouteToTasks("");
										}
									}).fail(
										function() {
											alert("保存失败");
									});
							});
					}else{
						if(routeToTaskId=='21'){// 特别程序审核 ，在暂停件里，所以不能保存标题
							teBieChengXuShenQingShenHe();
						}else{
							saveTitle().done(function() {
								$.when.apply($, submitForm()).done(
										function() {
											//填写意见
											document.getElementById('form2').contentWindow.writeOpinion();
											if(routeToTaskId=="chengbanrenbanjie"){
												if(text.indexOf("不与批准")!=-1){
													 executeSend(routeToTaskId,"chengbanrenUser","");
												}else{
													//批准
													executeSend(routeToTaskId,"chengbanrenUser","");
												}							
												/*$.ajax({
													type : "POST",
													url : ctx + "/sp/document/getPiZhun",
													data : {
														processInstanceId:processInstanceId
													},
													success : function(data) {
														if(data.PiZhun!=null&&data.PiZhun!=undefined&&data.PiZhun!="undefined"){
															//executeSend(routeToTaskId,"sendToStartUser","");
														}
													}
												});*/
											}else if(routeToTaskId=="buyushouli"){
												reajectReason("buYuShouLi","不予受理");
											}else if(routeToTaskId=="09"){// 办理完成，并行状态下，当多人并行处理且不是最后一个处理人或不需要人员选取时使用
												handleParallel();
											}else if(routeToTaskId=='17'){// 批准
												piZhun();
											}else if(routeToTaskId=='18'){// 不予批准
												reajectReason("buYuPiZhun","不予批准");
											}else if(routeToTaskId=='12'){// 最终的公文办结功能
												banjie(documentTitle);
											}else if(routeToTaskId=='20'){// 特别程序申请
												teBieChengXuShenQing();
											}else if(routeToTaskId=='waidanweibanli'){// 转外单位办理
												choiceDept("","",routeToTaskId);
											}else if(routeToTaskId=='16'){// 补齐补正告知
												buQiBuZheng4GaoZhiOk();
											}else if(routeToTaskId=='08'){// 发送下一人，串行时使用
												$('#documentTitle').val(documentTitle);
												$.ajax({
													type : "POST",
													url : ctx + "/sp/buttonOperation/handleSerial",
													data : {
														taskId : $('#taskId').val(),
														processDefinitionId : processDefinitionId,
														documentTitle : documentTitle,
														processInstanceId:processInstanceId
													},
													error : function(data) {
														alert("发送失败");
													},
													success : function(data) {
														alert(data.msg);
														window.parent.refreshMenu();
													}
												});
											}else{
												//docUserChoise(routeToTaskId);
												getRouteToTasks("");
											}
										}).fail(
											function() {
												alert("保存失败");
										});
								});
							}
						}
					$("#dialog-confirmdelete").dialog( "close" );
				}
			},
			{
				text:'取消',
				handler: function() {
					$("#dialog-confirmdelete").dialog( "close" );
				}
			}]
		});
	}

//判定协办人是否已经办理
function getRouteToTasks(sendDirectlyStr) {
	showGetRouToTask(sendDirectlyStr);
	/*$.ajax({
		type : "POST",
		url : ctx + "/document4Eform/actRuTaskUserCount",
		data : {processDefinitionId:processDefinitionId,
				taskId:$('#taskId').val()},
		async: false,
		success : function(data) {
					if(data.result == true){
						$("#users").text(data.users);
						$( "#dialog-users" ).dialog({
							title:"提示",
							resizable: false,
							width : 200,
							height:180,
							modal: true,
							buttons:[{
								text:'确定',
								handler:function() {
									showGetRouToTask(sendDirectlyStr);
									$("#dialog-users").dialog( "close" );
								}
							},{
								text:'取消',
								handler: function() {
									$("#dialog-users").dialog( "close" );
								}
							}]
						});
					}else{
						showGetRouToTask(sendDirectlyStr);
					}
				},
		error: function(){
			alert("错误");
		}
	});*/
}

//选择任务路径和接收人
function showGetRouToTask(sendDirectlyStr){
	$('#easyui-tabs').tabs('select', 0);//当在正文tab的时候，tab正文控件会影响easyUi-dialog的弹出，这里更改选中第一个tab
	$.ajax({
		type : "POST",
		url : ctx + "/sp/document/getRouteToTasks",
		data : {taskId:$('#taskId').val(),sendDirectlyStr:sendDirectlyStr},
		error : function() {
		},
		success : function(data) {
			if (data.sendFlag=="senderUser"||data.sendFlag=="sendToStartUser"){// 判断是否是直接发送，如果不是则选人
				executeSend("", "",data.sendFlag);
			}else{
				if (processDefinitionId == "" || processDefinitionId == null) {
					processDefinitionId = $('#processDefinitionId').val();
				}
				openCurWindow({
					id : frameID,
					src : ctx + "/sp/document/docUserAndRoutaskChoise?&processInstanceId=" + $('#processInstanceId').val() + "&taskId=" + $('#taskId').val()
					+ "&processDefinitionId=" + processDefinitionId,
					destroy : true,
					title : "选择人员",
					width : ($(window).width() - 100) + 'px',
					height : ($(window).height() - 10) + 'px',
					modal : true
				});
			}
		}
	});
}

//外单位办理，选择部门
function choiceDept(userArray,deptNamesArray,taskDefKey){
	$.ajax({
		type : "POST",
		url : ctx + "/document4Eform/actRuTaskUserCount",
		data : {processDefinitionId:processDefinitionId,
				taskId:$('#taskId').val()},
		async: false,
		success : function(data) {
					if(data.result == true){
						$("#users").text(data.users);
						$( "#dialog-users" ).dialog({
							title:"提示",
							resizable: false,
							width : 200,
							height:180,
							modal: true,
							buttons:[{
								text:'确定',
								handler:function() {
									openCurWindow({
										id : frameID,
										src : ctx + "/sp/document/departmentChoise?&processInstanceId=" + $('#processInstanceId').val() + "&taskId=" + $('#taskId').val()
										+ "&processDefinitionId=" + processDefinitionId +"&taskDefKey="+taskDefKey,
										destroy : true,
										title : "部门选择",
										width : ($(window).width() - 100) + 'px',
										height : ($(window).height() - 10) + 'px',
										modal : true
									});
									$("#dialog-users").dialog( "close" );
								}
							},{
								text:'取消',
								handler: function() {
									$("#dialog-users").dialog( "close" );
								}
							}]
						});
					}else{
						openCurWindow({
							id : frameID,
							src : ctx + "/sp/document/departmentChoise?&processInstanceId=" + $('#processInstanceId').val() + "&taskId=" + $('#taskId').val()
							+ "&processDefinitionId=" + processDefinitionId +"&taskDefKey="+taskDefKey,
							destroy : true,
							title : "部门选择",
							width : ($(window).width() - 100) + 'px',
							height : ($(window).height() - 10) + 'px',
							modal : true
						});
					}
				},
		error: function(){
			alert("错误");
		}
	});
}
function docUserChoise(routeToTaskId){
	if (processDefinitionId == "" || processDefinitionId == null) {
		processDefinitionId = $('#processDefinitionId').val();
	}
	openCurWindow({
		id : frameID,
		src : ctx + "/sp/document/docUserChoise?processInstanceId=" + $('#processInstanceId').val() + "&taskId=" + $('#taskId').val()
		+ "&processDefinitionId=" + processDefinitionId+"&taskDefKey="+routeToTaskId,
		destroy : true,
		title : "选择人员",
		width : ($(window).width() - 100) + 'px',
		height : ($(window).height() - 10) + 'px',
		modal : true
	});
}
function result4UserChoice(userChoice,routeToTaskId) {
	if(reAssignOrConsult=='reAssign'){
		url=ctx + '/buttonOperation/reAssign';
		doReAssignOrConsult(url);
	}else if(reAssignOrConsult=='consult'){
		url=ctx + '/buttonOperation/consult';
		doReAssignOrConsult(url);
	}else{
		executeSend(routeToTaskId,userChoice,"");
	}
}

$(function() {
	var formId = formIds.split(",");
	var formName = formNames.split(",");// 表单名称数组
	for (var i = 1; i <= formId.length; i++) {
		$('#easyui-tabs').tabs('add', {
			id : 'tab' + i,
			title : formName[i - 1],
			content : '<iframe id=\"form' + i + '" name="eform" src=\"about:blank\" frameborder=\"0\" scrolling=\"yes\"  style=\'background-color:#7abdf2;width:99%\'></iframe>',
			closable : false
		});
	}
	if (showOtherFlag != "") {
//		if (showOtherFlag.indexOf("showDocumentTab") >= 0) {
//			$('#easyui-tabs').tabs('add', {
//				id : 'tab_document',
//				title : '拟稿',
//				width : 400 + "px",
//				height : 400 + "px",
//				content : '<iframe id=\"ntko\" src=\"about:blank\" frameborder=\"0\" scrolling=\"no\" style=\'background-color:#7abdf2;\'></iframe>',
//				closable : false
//			});
//		}
		if (showOtherFlag.indexOf("showFileTab") >= 0) {
			$('#easyui-tabs').tabs('add', {
				id : 'tab_file',
				title : '材料',
				content : '<iframe id=\"uploadfilesId\" name=\"uploadfilesId\" src=\"about:blank\" frameborder=\"0\" scrolling=\"no\" style=\'background-color:#7abdf2;\'></iframe>',
				closable : false
			});
		}
	}

	$('#easyui-tabs').tabs('add', {
		id : 'tab_history',
		title : '历程',
		content : '<iframe id=\"history\" src=\"about:blank\" frameborder=\"0\" scrolling=\"no\" style=\'background-color:white;\'></iframe>',
		closable : false
	});
	
	if(itembox!="history"){
		$('#easyui-tabs').tabs('add', {
			id : 'tab_historyDocument',
			title : '历史办件',
			content : '<iframe id=\"historyDocument\" src=\"about:blank\" frameborder=\"0\" scrolling=\"no\" style=\'background-color:white;\'></iframe>',
			closable : false
		});
	}
	
	//$('#easyui-tabs').tabs('add', {
	//	id : 'tab_materials',
	//	title : '相关信息',
	//	content : '<iframe id=\"relativeMsg\" src=\"about:blank\" frameborder=\"0\" scrolling=\"no\" style=\'background-color:white;\'></iframe>',
	//	closable : false
	//});
	


	$('body').layout({
		spacing_open : 0,
		spacing_closed : 0,
		center__maskContents : true,
		center__maskObjects : true
	});

	$('#easyui-tabs').tabs(
			{
				onSelect : function(title) {
					if (title == '拟稿') {
						var src = $('#ntko').attr('src');
						if (src == 'about:blank') {
							resize();
							$('#ntko').attr('src', ctx + '/ntko/display?processSerialNumber=' + processSerialNumber+'&activitiUser='+activitiUser+'&itembox='+itembox+'&processDefinitionId='+processDefinitionId+'&taskId='+taskId);
							//$('#ntko').attr('src',ctx+'/jinge/display?processSerialNumber='+processSerialNumber+'&activitiUser='+activitiUser+'&itembox='+itembox+'&taskId='+taskId);
						}
					}

					if (title == '材料') {
						var src = $('#uploadfilesId').attr('src');
						if (src == 'about:blank') {
							resize();
							$('#uploadfilesId').attr(
								'src',
								ctx + '/wssbcl/forward?SPinstanceId=' + SPinstanceId+'&itembox='+itembox);
						}
					}
					
					if (title == '历程') {
						var src = $('#history').attr('src');
						if (src == 'about:blank') {
							resize();
							$('#history').attr(
									'src',
									ctx + '/sp/worklist/history/show?processInstanceId='+$('#processInstanceId').val()+'&SPinstanceId='+SPinstanceId);
						}
					}
					
					if (title == '历史办件') {
						var src = $('#historyDocument').attr('src');
						if (src == 'about:blank') {
							resize();
							$('#historyDocument').attr(
									'src',
									ctx + '/sp/worklist/historyDocument/show');
						}
					}
					
					if (title == '相关信息') {
						var src = $('#relativeMsg').attr('src');
						if (src == 'about:blank') {
							resize();
							$('#relativeMsg').attr(
									'src',
									ctx + '/sp/relativeMessage/showRelativeMessage?SPinstanceId='+SPinstanceId);
						}
					}
					
					var formId = formIds.split(",");
					var formEdittype = formEdittypes.split(",");//表单模板模式
					for (var i = 1; i <= formId.length; i++) {
						if (title == formName[i-1]) {
							var tab = $('#easyui-tabs').tabs('getSelected');  // 获取选择的面板
							var guid = processInstanceId;
							var src = $('#form'+i).attr('src');
							/*if(itembox=="add"){
								submitForm();
								resize();
								$('#form'+i).attr(
										'src',
										ctx +'/eform/engine/getTemplate?temp_Id='+formId[i-1]+'&edittype='+formEdittype[i-1]+'&guid='+guid+'&SPinstanceId='+SPinstanceId);
							}else{*/
								
								if (src == 'about:blank') {
									resize();
									$('#form'+i).attr(
											'src',
											ctx +'/eform/engine/getTemplate?temp_Id='+formId[i-1]+'&edittype='+formEdittype[i-1]+'&guid='+guid+'&SPinstanceId='+SPinstanceId);
								}
							//}
						}
						shezhi();
					}
				}
			});

	$("#button02").mouseover(function(event) {
		var currTabtitle = $('#easyui-tabs').tabs('getSelected').panel('options').title;
		if(currTabtitle=='正文'){
			$('#easyui-tabs').tabs('select', 0);
		}
	});
	
	
	$("a[name='easyuiLinkButton']").click(
			function(event) {
				//获取第一个表单的标题
				documentTitle = document.getElementById('form1').contentWindow.document.getElementById('title').value; 
				$('#documentTitle').val(documentTitle);
				switch (this.id) {
				case 'button01':// 保存
					if(!validInput()){//表单字段校验都通过时保存
						return false;
					}
					var taskId = $('#taskId').val();
					if (taskId == "") {
						startProcess().done(function() {
							$.when.apply($, submitForm()).done(function() {
										alert("保存成功");
									}).fail(function() {
										alert("保存失败");
									});
							}).fail(function() {
								alert("流程启动失败");
							});
					}else{
						saveTitle().done(function() {
							$.when.apply($, submitForm()).done(function() {
								alert("保存成功");
							}).fail(function() {
								alert("保存失败");
							});
						}).fail(function() {
							alert("保存标题失败");
						});
					}
					window.parent.refreshMenu("save");//保存时刷新待办件数据，不需要打开待办件。
					break;
				case 'button03':// 返回
					switch (itembox) {
					case 'teBieChengXu':
						window.parent.openTab("特别程序审核",ctx + '/sp/worklist/teBieChengXu');
						break;
					case 'todo':
						window.parent.openTab("待办件",ctx + '/sp/worklist/todo');
						break;
					case 'doing':
						window.parent.openTab("在办件",ctx + '/sp/worklist/doing');
						break;
					case 'done':
						window.parent.openTab("办结件",ctx + '/sp/worklist/done');
						break;
					case 'pause':
						window.parent.openTab("暂停件",ctx + '/sp/worklist/pause');
						break;
					case 'bqbz':
						window.parent.openTab("补齐补正件",ctx + '/bjgz/index');
						break;
					default:
						window.location.href = document.referrer;
					}
					break;
				case 'button04':// 退回
					if(!checkSignOpinion()){
						return false;
					}
					rollback();
					break;
				case 'button05':// 转办
					if(!checkSignOpinion()){
						return false;
					}
					saveTitle().done(reAssign());// taskId存在，说明流程已经启动，保存标题即可
					break;
				case 'button06':// 协办
					if(!checkSignOpinion()){
						return false;
					}
					saveTitle().done(consult());// taskId存在，说明流程已经启动，保存标题即可
					break;
				case 'button07':// 完成,在协办的情况下显示的按钮
					if(!checkSignOpinion()){
						return false;
					}
					completeTask();
					break;
				case 'button08':// 发送下一人，串行时使用
					if(!checkSignOpinion()){
						return false;
					}
					$('#documentTitle').val(documentTitle);
					$.ajax({
						type : "POST",
						url : ctx + "/sp/buttonOperation/handleSerial",
						data : {
							taskId : $('#taskId').val(),
							processDefinitionId : processDefinitionId,
							documentTitle : documentTitle,
							processInstanceId:processInstanceId
						},
						error : function(data) {
							alert("发送失败");
						},
						success : function(data) {
							alert(data.msg);
							window.parent.refreshMenu();
						}
					});
					break;
				case 'button09':// 办理完成，并行状态下，当多人并行处理且不是最后一个处理人或不需要人员选取时使用
					if(!checkSignOpinion()){
						return false;
					}
					handleParallel();
					break;
				case 'button10':// 签收按钮
					claim();
					break;
				case 'button11':// 撤销签收按钮
					unclaim();
					break;
				case 'button12':// 最终的公文办结功能
					if(!checkSignOpinion()){
						return false;
					}
					if (typeof (documentTitle) == "undefined") {
						alert("未定义事项名称");
						return;
					}
					if (documentTitle == "") {
						alert("请输入事项名称");
						return;
					}
					banjie(documentTitle);
					break;
				case 'button14':// 收回按钮
					drawTask();
					break;
				case 'button15':// 拒签按钮
					toRefuseClaim();
					break;
				case 'button16':// 补齐补正告知
					if(!checkSignOpinion()){
						return false;
					}
					buQiBuZheng4GaoZhi();
					break;
				case 'button17':// 批准
					if(!checkSignOpinion()){
						return false;
					}
					if(!validInput()){//表单字段校验都通过时保存
						return false;
					}
					piZhun();
					break;
				case 'button18':// 不予批准
					if(!checkSignOpinion()){
						return false;
					}
					reajectReason("buYuPiZhun","不予批准");
					break;
				case 'button19':// 补齐补正受理
					if(bqbzStatus!="1"){
						alert("请等待材料补齐之后再进行补齐补正受理操作!");
						return false;
					}
					if(!checkSignOpinion()){
						return false;
					}
					buQiBuZheng4ShouLi();
					break;
				case 'button20'://	特别程序申请
					teBieChengXuShenQing();
					break;
				case 'button21'://	特别程序审核
					teBieChengXuShenQingShenHe();
					break;
				case 'button22'://	特殊办结
					if(!checkSignOpinion()){
						return false;
					}
					specialComplete();
					break;
				case 'button23'://	特别程序结束
					teBieChengXuJieGuo();
					break;
				}
				return false;// 避免提交两次。
	});
	
	$("div[name='easyuiLinkButton']").click(
		function(event) {
			var routeToTaskId=this.id;
			var formId = formIds.split(",");
			for (var i = 1; i <= formId.length; i++) {
				var temp_Id = formId[i-1];
				var objForm = $("iframe[name='eform']").contents().find("form[name='frm_Edit_"+temp_Id+"']");//表单form对象
				objForm.ajaxSubmit({
					async:false, 
					cache:false,
			        type: 'post', // 提交方式 get/post
			    	url : ctx +"/sp/document/saveShenpichuli?routeToTaskId="+routeToTaskId,
			        dataType : 'json',
			        success: function(data) {
			        },
			        error:function(data){
			        	alert("保存失败!");
			        }
			    });
			}
			if(routeToTaskId=='25'){// 补齐补正告知说明
				buQiBuZhengGaoZhiRemark();
				return;
			}else if(routeToTaskId=='19'){// 补齐补正受理
				if(bqbzStatus!="1"){
					alert("请等待材料补齐之后再进行补齐补正受理操作!");
					return false;
				}
				buQiBuZheng4ShouLi();
				return;
			}
			if (!validInput()) {
				return false;
			}
			//获取第一个表单的标题
			documentTitle = document.getElementById('form1').contentWindow.document.getElementById('title').value; 
			$('#documentTitle').val(documentTitle);
			
			var text = this.innerHTML;//获取按钮名称
			
			var taskId = $('#taskId').val();
			
			if(routeToTaskId=='10'){// 签收按钮
				claim();
			}else if(routeToTaskId=='11'){// 撤销签收按钮
				unclaim();
			}else if(routeToTaskId=='15'){// 拒签按钮
				toRefuseClaim();
			}else if(routeToTaskId=='21'){// 特别程序审核 ，在暂停件里，所以不能保存标题
				teBieChengXuShenQingShenHe();
			}else if(routeToTaskId=='23'){// 特别程序结果 ，在暂停件里，所以不能保存标题
				teBieChengXuJieGuo();
			}else if(routeToTaskId=="wenmishenhe"){
				executeSend(routeToTaskId,"wenmishenheUser","");  //文秘审核
			}else if(routeToTaskId=="gaizhang"){
				executeSend(routeToTaskId,"gaizhangUser","");  //盖章用户组
			}
			else{//以上操作不需要填写意见
				if(!signOpinion()){//默认签意见
					writeOpinion(routeToTaskId,text);
				}else{
					if(!checkSignOpinion()){
						return false;
					}
					
					if (taskId == "") {
						startProcess().done(function() {
							$.when.apply($, submitForm()).done(
									function() {
										if(routeToTaskId=="cailiaochushen"||routeToTaskId=="chuangkourenyuanbanjie"){
											executeSend(routeToTaskId,"sendToStartUser","");
										}else if(routeToTaskId=="buyushouli"){
											reajectReason("buYuShouLi","不予受理");
										}else if(routeToTaskId=='waidanweibanli'){// 转外单位办理
											choiceDept("","",routeToTaskId);
										}else{
											//docUserChoise(routeToTaskId);
											getRouteToTasks("");
										}
									}).fail(function() {
											alert("保存失败");
									});
							}).fail(function() {
								alert("流程启动失败");
							});
					} else {
						saveTitle().done(function() {
							$.when.apply($,submitForm()).done(function() {
								if(routeToTaskId=="chengbanrenbanjie"){
									if(text.indexOf("不与批准")!=-1){
										 executeSend(routeToTaskId,"chengbanrenUser","");
									}else{
										//批准
										executeSend(routeToTaskId,"chengbanrenUser","");
									}							
									/*$.ajax({
										type : "POST",
										url : ctx + "/sp/document/getPiZhun",
										data : {
											processInstanceId:processInstanceId
										},
										success : function(data) {
											if(data.PiZhun!=null&&data.PiZhun!=undefined&&data.PiZhun!="undefined"){
												//executeSend(routeToTaskId,"sendToStartUser","");
											}
										}
									});*/
								}
								else if(routeToTaskId=="wenmishenhe"){
									executeSend(routeToTaskId,"wenmishenheUser","");  //文秘审核
								}else if(routeToTaskId=="gaizhang"){
									executeSend(routeToTaskId,"gaizhangUser","");  //盖章用户组
								}
								else if(routeToTaskId=="buyushouli"){
									reajectReason("buYuShouLi","不予受理");
								}else if(routeToTaskId=="09"){// 办理完成，并行状态下，当多人并行处理且不是最后一个处理人或不需要人员选取时使用
									handleParallel();
								}else if(routeToTaskId=='17'){// 批准
									piZhun();
								}else if(routeToTaskId=='18'){// 不批准
									buYuPiZhun();
								}else if(routeToTaskId=='12'){// 最终的公文办结功能
									banjie(documentTitle);
								}else if(routeToTaskId=='16'){// 补齐补正告知
									buQiBuZheng4GaoZhiOk();
								}else if(routeToTaskId=='20'){// 特别程序申请
									teBieChengXuShenQing();
								}else if(routeToTaskId=='waidanweibanli'){// 转外单位办理
									choiceDept("","",routeToTaskId);
								}else if(routeToTaskId=='08'){// 发送下一人，串行时使用
									$('#documentTitle').val(documentTitle);
									$.ajax({
										type : "POST",
										url : ctx + "/buttonOperation/handleSerial",
										data : {
											taskId : $('#taskId').val(),
											processDefinitionId : processDefinitionId,
											documentTitle : documentTitle,
											processInstanceId:processInstanceId
										},
										error : function(data) {
											alert("发送失败");
										},
										success : function(data) {
											alert(data.msg);
											window.parent.refreshMenu();
										}
									});
								}else if(routeToTaskId=='19'){// 补齐补正受理
									if(bqbzStatus!="1"){
										alert("请等待材料补齐之后再进行补齐补正受理操作!");
										return false;
									}
									buQiBuZheng4ShouLi();
								}else{
									//docUserChoise(routeToTaskId);
									getRouteToTasks("");
								}
							}).fail(function() {
								alert("保存失败");
							});
						});
					}
				}
			}
	});
	

	
	$('button').click(function(event) {
		switch (this.id) {
		case 'uploadfiles':
			clearUpload();
			openUploadFilesDialog(event);
			break;
		}
		return false;// 避免提交两次。
	});

	function resize() {
		var width = $(window).width()-5;
		var height = $(window).height()-30;
		$('#ntko').css({
			"width" : (width) + "px",
			"height" : (height) + "px"
		});

		$('#uploadfilesId').css({
			"width" : (width) + "px",
			"height" : (height) + "px"
		});
		
		$('#history').css({
			"width" : (width) + "px",
			"height" : (height) + "px"
		});
		
		$('#historyDocument').css({
			"width" : (width) + "px",
			"height" : (height) + "px"
		});
		
		$('#relativeMsg').css({
			"width" : (width) + "px",
			"height" : (height) + "px"
		});
		
		var formId = formIds.split(",");
		for (var i = 1; i <= formId.length; i++) {
			$('#form'+i).css({
				"width" : (width) + "px",
				"height" : (height) + "px"
			});
		}
		
	}

	//新建办文的时候，默认选择详细信息表，当从待办、在办、办结打开的时候选择的是当前节点的表单
	if(itembox=="add11111"){//暂时不用
		$('#easyui-tabs').tabs('select', 0);
	}else{
		if(formId.length>1){
			$('#easyui-tabs').tabs('select', 1);
		}else{
			$('#easyui-tabs').tabs('select', 0);
		}
	}
	
	//设置表单同一字段改变，写死的~~~~~~
	function shezhi(){
		if(document.getElementById('form1')!=null){
			var xiangmumingcheng1 = document.getElementById('form1').contentWindow.document.getElementById('jbxx_xiangmumingcheng'); 
			var title = document.getElementById('form1').contentWindow.document.getElementById('title'); 
			var declarerperson1 = document.getElementById('form1').contentWindow.document.getElementById('jbxx_declarerperson'); 
			var address1 = document.getElementById('form1').contentWindow.document.getElementById('jbxx_address'); 
			var declarerlxr1 = document.getElementById('form1').contentWindow.document.getElementById('jbxx_declarerlxr'); 
			var declarertel1 = document.getElementById('form1').contentWindow.document.getElementById('jbxx_declarertel'); 
		}
		if(document.getElementById('form2')!=null){
			var xiangmumingcheng2 = document.getElementById('form2').contentWindow.document.getElementById('jbxx_xiangmumingcheng'); 
			var title2 = document.getElementById('form2').contentWindow.document.getElementById('title'); 
			var declarerperson2 = document.getElementById('form2').contentWindow.document.getElementById('jbxx_declarerperson'); 
			var address2 = document.getElementById('form2').contentWindow.document.getElementById('jbxx_address'); 
			var declarerlxr2 = document.getElementById('form2').contentWindow.document.getElementById('jbxx_declarerlxr'); 
			var declarertel2 = document.getElementById('form2').contentWindow.document.getElementById('jbxx_declarertel'); 
		}
		if(title!=null && title2!=null){
			title2.value=title.value;
		}
		if(xiangmumingcheng1!=null&&xiangmumingcheng2!=null){//项目名称
			xiangmumingcheng2.value=xiangmumingcheng1.value;
		}
		if(declarerperson1!=null&&declarerperson2!=null){//申请单位人
			declarerperson2.value=declarerperson1.value;
		}
		if(address1!=null&&address2!=null){//地址
			address2.value=address1.value;
		}
		if(declarerlxr1!=null&&declarerlxr2!=null){//联系人
			declarerlxr2.value=declarerlxr1.value;
		}
		if(declarertel1!=null&&declarertel2!=null){//联系电话
			declarertel2.value=declarertel1.value;
		}
	}
});
