var frameID = newGuid();
// 验证数据
function validateData() {
	if (typeof ($('#form1 #title').val()) == "undefined") {
		alert("未定义事项名称");
		return false;
	}
	if ($('#form1 #title').val() == "") {
		alert("请输入事项名称");
		return false;
	}
	if ($('#form1 #xiangmumingcheng').val() == "") {
		alert("请输入项目名称");
		return false;
	}
	if ($('#form1 #DeclarerPerson').val() == "") {
		alert("请输入申请人");
		return false;
	}
	if ($('#form1 #ZHENGJIANDAIMA').val() == "") {
		alert("请输入证件代码");
		return false;
	}
	if ($('#form1 #DeclarerLxr').val() == "") {
		alert("请输入联系人");
		return false;
	}
	if ($('#form1 #DeclarerMobile').val() == "") {
		alert("请输入手机号码");
		return false;
	}
	if (!isChinaOrLett($('#form1 #DeclarerPerson').val())) {
		alert("申请人不能填写数字或特殊符号");
		return false;
	}

	$('#documentTitle').val($('#form1 #title').val());
	return true;
}

//判断是否是汉字、字母组成 
function isChinaOrLett(s){
	var regu = "^[a-zA-Z\u4e00-\u9fa5]+$"; 
	var re = new RegExp(regu); 
	if (re.test(s)) { 
		return true;
	}else{ 
		return false;
	} 
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
			documentTitle : $('#form1 #title').val()
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

//验证是否签写意见
function checkSignOpinion(){
	if(isSignOpinion=="false")return true;//当前任务节点配置是否必签意见，当为false时,表示不检验是否签意见。
	var checkSignOpinion;
	$.ajax({
	    async:false,
	    cache: false,
      type: "GET",
      url: ctx + "/opinion/checkSignOpinion",
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

//验证是否签写意见
function signOpinion(){
	var checkSignOpinion;
	$.ajax({
	    async:false,
	    cache: false,
      type: "GET",
      url: ctx + "/opinion/checkSignOpinion",
      data: {taskId:$('#taskId').val(),processSerialNumber:$('#processSerialNumber').val()},
      dataType: "json",
      success: function(data){
      	checkSignOpinion=data.checkSignOpinion;
      }
	});
	return checkSignOpinion;
}

//签写默认意见
function writeOpinion(routeToTaskId){
		$( "#dialog-confirmdelete" ).dialog({
			title:'确认发送',
			resizable: false,
			width : 200,
			height:150,
			modal: true,
			buttons:[{
				text:'确定',
				handler:function() {
					var taskId = $('#taskId').val();
					if (taskId == "") {
						startProcess()// 启动流程时已经保存标题了
						.done(function() {
								$.when.apply($, executeSave()).done(
										function() {
											$.ajax({
												async:false,
												type:"POST",
												dataType:'JSON',
												url:ctx+"/opinion/save",
												data: "opinion=已阅。&guids="+str+"&isAgent=0&agentUserDeptId=&opinionDate="+str+"&agentUserId="+str+"&agentUserName="+str+"&opinionType=1&category=chushen&taskId="+$('#taskId').val()+"&activitiUser="+activitiUser+"&processSerialNumber="+processSerialNumber+"&processInstanceId="+$('#processInstanceId').val(),
											})
											
											if(routeToTaskId=="cailiaochushen"||routeToTaskId=="chuangkourenyuanbanjie"){
												executeSend(routeToTaskId,"sendToStartUser","");
											}else if(routeToTaskId=="buyushouli"){
												reajectReason("buYuShouLi","不予受理");
											}else if(routeToTaskId=="16"){
												buQiBuZheng4GaoZhi();
											}else if(routeToTaskId=="19"){
												buQiBuZheng4ShouLi();
											}else{
												docUserChoise(routeToTaskId);
											}
										}).fail(
											function() {
												alert("保存失败");
											});
							});
					}else{
						saveTitle().done(function() {
									$.when.apply($, executeSave()).done(
											function() {
												if(routeToTaskId=="cailiaochushen"||routeToTaskId=="chuangkourenyuanbanjie"){
													executeSend(routeToTaskId,"sendToStartUser","");
												}else if(routeToTaskId=="buyushouli"){
													reajectReason("buYuShouLi","不予受理");
												}else if(routeToTaskId=="16"){
													buQiBuZheng4GaoZhi();
												}else if(routeToTaskId=="19"){
													buQiBuZheng4ShouLi();
												}else{
													docUserChoise(routeToTaskId);
												}
											}).fail(
												function() {
													alert("保存失败");
												});
								});
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

// 选择任务路径和接收人
function getRouteToTasks(sendDirectlyStr) {
	$.ajax({
		type : "POST",
		url : ctx + "/document/actRuTaskUserCount",
		data : {processDefinitionId:processDefinitionId,
				taskId:$('#taskId').val()},
		async: false,
		success : function(data) {
					if(data.result == true){
						alert("其他协办人员还未办理完成，暂时不能发送！");
					}else{
						showGetRouToTask(sendDirectlyStr);
					}
				},
		error: function(){
			alert("错误");
		}
	});
}

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
					src : ctx + "/document/docUserAndRoutaskChoise?&processInstanceId=" + $('#processInstanceId').val() + "&taskId=" + $('#taskId').val()
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
function choiceDept(userArray,deptNamesArray,taskId){
	for(var i=0;i<userArray.length;i++){
		$('#tableDept').append('<tr><td align="right"><input  type="radio" id="deptRadio'+i+'" name="deptRadio" value="'+userArray[i]+'">'+
				'</td><td align="left">'+deptNamesArray[i]+'</td></tr>');
	}
	$( "#dialog-choiceDept" ).dialog({
		title:"部门选择",
		resizable: false,
		width : 200,
		height:250,
		left:300,
		top:200,
		modal: true,
		buttons:[{
			text:'确定',
			handler:function() {
				var user=$("input[name='deptRadio']:checked").val();
				if(typeof(user)!="undefined" && user!=""){
					$.ajax({
						type : "POST",
						url :ctx+'/buttonOperation/claim/' + taskId+'/'+user,
						success : function(data) {
							alert(data.msg);
							if (data.success == true) {
								window.parent.openTab("待办件",ctx + '/sp/worklist/todo'); 
							}
						}
					});
				}else{
					alert("请选取部门");
				}
				$("#dialog-choiceDept").dialog( "close" );
			}
		},
		{
			text:'取消',
			handler: function() {
				$("#dialog-choiceDept").dialog( "close" );
			}
		}]
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

// 初始化表单数据，由于knockout的数据绑定要求绑定的元素在都要有初始化数据，所以这里进行初始化
function initFormModel(formIds) {
	var formId = formIds.split(",");
	for (var i = 0; i < formId.length; i++) {
		$('#form' + (i + 1) + ' input').each(function() {
			var tempName = $(this).attr("name");
			if (!jQuery.isEmptyObject(tempName)) {
				if (jQuery.isEmptyObject(formModel[tempName])) {
					formModel[tempName] = ko.observable("");
				}
			}
		});
		$('#form' + (i + 1) + ' select').each(function() {
			var tempName = $(this).attr("name");
			if (!jQuery.isEmptyObject(tempName)) {
				if (jQuery.isEmptyObject(formModel[tempName])) {
					formModel[tempName] = ko.observable("");
				}
			}
		});
		$('#form' + (i + 1) + ' textarea').each(function() {
			var tempName = $(this).attr("name");
			if (!jQuery.isEmptyObject(tempName)) {
				if (jQuery.isEmptyObject(formModel[tempName])) {
					formModel[tempName] = ko.observable("");
				}
			}
		});
	}
}

function getFormHTMLAndData(formId){
	for (var i = 0; i < formId.length; i++) {
		if (itembox == 'doing' || itembox == 'done'||itembox == 'pause'||itembox == 'history') {
			$('#form' + (i + 1) + ' select').attr("disabled", "disabled");
			$('#form' + (i + 1) + ' input').attr("readonly", "readonly");
			$('#form' + (i + 1) + ' input').attr("disabled", "disabled");
			$('#form' + (i + 1) + ' textarea').attr("readonly", "readonly");
		}
	}
	
	if (itembox == 'add'){// 新件文档时，需要初始化表单
		$.ajax({
             type: "GET",
             async:false,
             cache:false,
             dataType: "json",
             url: ctx + "/sp/form/run/getInitFormData",
             data: {SPinstanceId:SPinstanceId},
             success: function(data){
						$.each(data, function(i, item) {
							$.each(item, function(key, value) {
								formModel[key] = ko.observable(value);
							});
						});
						initFormModel(formIds);// 初始化表单
						ko.applyBindings(formModel);
             }
         });
	} else {
         $.ajax({
             type: "GET",
             async:false,
             cache:false,
             dataType: "json",
             url: ctx + "/sp/form/run/getFormData",
             data: {formIds:formIds, processInstanceId:$("#processInstanceId").val()},
             success: function(data){
		            	 $.each(data, function(i, item) {
								if (!jQuery.isEmptyObject(item)) {// 如果item为空或者不存在，则初始化
									$.each(item, function(key, value) {
										formModel[key] = ko.observable(value);
									});
								}
							});
							initFormModel(formIds);// 初始化表单
							ko.applyBindings(formModel);
                      	  }
         });
	}
}
$(function() {
	var formId = formIds.split(",");
	var formName = formNames.split(",");// 表单名称数组
	for (var i = 1; i <= formId.length; i++) {
		$('#easyui-tabs').tabs('add', {
			id : 'tab' + i,
			title : formName[i - 1],
			content : '<div class="mydiv"><form id=\"form' + i + '" style="text-align:center"></form></div>',
			closable : false
		});
	}
	if (showOtherFlag.indexOf("showFileTab") >= 0) {
		$('#easyui-tabs').tabs('add', {
			id : 'tab_file',
			title : '材料',
			content : '<iframe id=\"uploadfilesId\" name=\"uploadfilesId\" src=\"about:blank\" frameborder=\"0\" scrolling=\"no\" style=\'background-color:#7abdf2;\'></iframe>',
			closable : false
		});
	}
//	if (showOtherFlag != "") {
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
//		
//	}

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
	
	$('#easyui-tabs').tabs('add', {
		id : 'tab_materials',
		title : '相关信息',
		content : '<iframe id=\"relativeMsg\" src=\"about:blank\" frameborder=\"0\" scrolling=\"no\" style=\'background-color:white;\'></iframe>',
		closable : false
	});
	
	// 获取表单和表单数据
	$.when(
			// 先获取表单
			$.ajax({
		         type: 'GET',
		         cache:'false',
		         dataType:'json',
		         url: ctx + '/sp/form/run/formContent',
		         data: {formIds:formIds,
						activitiUser:activitiUser,
						itembox:itembox,
						processSerialNumber:processSerialNumber,
						taskId:$('#taskId').val(),
						processInstanceId:$('#processInstanceId').val()
				 },
		         success: function(data){
						$.each(data, function(i, str) {
							$('#form' + (i + 1)).html(str);
						});
		         }
			})).then(function(){
					getFormHTMLAndData(formId);
				});

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
									ctx + '/wssbcl/forward?SPinstanceId=' +SPinstanceId+'&itembox='+itembox);
							
						}
					}
					
					if (title == '历程') {
						var src = $('#history').attr('src');
						if (src == 'about:blank') {
							resize();
							$('#history').attr(
									'src',
									ctx + '/sp/worklist/history/show?processInstanceId='+$('#processInstanceId').val());
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
									ctx + '/sp/worklist/relativeMessage');
						}
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
				switch (this.id) {
				case 'button01':// 保存
					if (!validateData()) {
						return false;
					}
					var taskId = $('#taskId').val();
					if (taskId == "") {
						startProcess()// 启动流程时已经保存标题了
						.done(function() {
							$.when.apply($, executeSave()).done(function(data) {
								alert("保存成功");
							}).fail(function() {
								alert("保存失败");
							});
						});
					} else {
						saveTitle()// taskId存在，说明流程已经启动，保存标题即可
						.done(function() {
							$.when.apply($, executeSave()).done(function(data) {
								alert("保存成功");
							}).fail(function() {
								alert("保存失败");
							});
						});
					}
					break;
				case 'button03':// 返回
					switch (itembox) {
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
					saveTitle()// taskId存在，说明流程已经启动，保存标题即可
					.done(reAssign());
					break;
				case 'button06':// 协办
					if(!checkSignOpinion()){
						return false;
					}
					saveTitle()// taskId存在，说明流程已经启动，保存标题即可
					.done(consult());
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
					$('#documentTitle').val($('#form1 #title').val());
					$.ajax({
						type : "POST",
						url : ctx + "/buttonOperation/handleSerial",
						data : {
							taskId : $('#taskId').val(),
							processDefinitionId : processDefinitionId,
							documentTitle : $('#form1 #title').val(),
							processInstanceId:processInstanceId
						},
						error : function(data) {
							alert("发送失败");
						},
						success : function(data) {
							alert(data.msg);
							window.parent.initMenu();
							//url = ctx + '/sp/worklist/todo';
							//window.parent.openTab("待办件",url);
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
				case 'button11':// 拒收按钮
					unclaim();
					break;
				case 'button12':// 最终的公文办结功能
					if(!checkSignOpinion()){
						return false;
					}
					if (typeof ($('#form1 #title').val()) == "undefined") {
						alert("未定义事项名称");
						return;
					}
					if ($('#form1 #title').val() == "") {
						alert("请输入事项名称");
						return;
					}
					$('#documentTitle').val($('#form1 #title').val());
					
					$.ajax({
						type : "POST",
						url : ctx + "/sp/document/getBanjieType",
						data : {
							processInstanceId : processInstanceId
						},
						success : function(data) {
							var type = data.type;
							if(type=="证照"){
								if(!$("input[name='certifyWay']").is(':checked')){
									alert("该件为出证办结件，请选择“证照”");
									return false;
								}
								chuZhengBanJie();
							}else{
								executeSave().done(function() {
									$("#documentForm").ajaxSubmit({
										type : 'POST',
										dataType : 'json',
										url : ctx + '/sp/document/complete',
										success : function(responseText, statusText, xhr, $form) {
											alert(responseText.msg);
											if (responseText.success == true) {
												window.parent.initMenu();
											} 
											if(!$("input[name='certifyWay']").is(':checked')){
												$.post(ctx+'/certificate/sendSms?processInstanceId='+processInstanceId, '', function(senateResult) {
									    			
									    		},"text");
											}
										}
									});
								}).fail(
									function() {
										alert("保存失败");
								});
							}
						}
					});
					break;
				case 'button14':// 收回按钮
					drawTask();
					break;
				case 'button15':// 拒签按钮
					toRefuseClaim();
					break;
				case 'button16':// 补齐补正告知
					//if(!checkSignOpinion()){
					//	return false;
					//}
					buQiBuZheng4GaoZhi();
					break;
				case 'button17':// 批准
					if(!checkSignOpinion()){
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
					if(!checkSignOpinion()){
						return false;
					}
					buQiBuZheng4ShouLi();
					break;
				case 'button20'://	特别程序申请
					teBieChengXuShenQing();
					break;
				case 'button21'://	特别程序申请处理
					teBieChengXuShenQingChuLi();
					break;
				case 'button22'://	特殊办结
					if(!checkSignOpinion()){
						return false;
					}
					specialComplete();
					break;
				case 'button23'://	特别程序结束
					teBieChengXuJieShu();
					break;
				}
				return false;// 避免提交两次。
	});
	
	$("div[name='easyuiLinkButton']").click(
		function(event) {
			if (!validateData()) {
				return false;
			}
			var routeToTaskId=this.id;
		
			if(!signOpinion()){
				writeOpinion(routeToTaskId);
				//checkSignOpinion
				//return false;
			}else{
				var taskId = $('#taskId').val();
				if (taskId == "") {
					startProcess()// 启动流程时已经保存标题了
					.done(function() {
						$.when.apply($, executeSave()).done(
								function() {
									if(routeToTaskId=="cailiaochushen"||routeToTaskId=="chuangkourenyuanbanjie"){
										//alert("banjie");return false;
										executeSend(routeToTaskId,"sendToStartUser","");
									}else if(routeToTaskId=="buyushouli"){
										reajectReason("buYuShouLi","不予受理");
									}else if(routeToTaskId=="16"){
										buQiBuZheng4GaoZhi();
									}else if(routeToTaskId=="19"){
										buQiBuZheng4ShouLi();
									}else{
										docUserChoise(routeToTaskId);
									}
								}).fail(
									function() {
										alert("保存失败");
									});
						})
				}else{
					saveTitle().done(
							function() {
								$.when.apply($, executeSave()).done(
									function() {
										if(routeToTaskId=="cailiaochushen"||routeToTaskId=="chuangkourenyuanbanjie"){
											executeSend(routeToTaskId,"sendToStartUser","");
										}else if(routeToTaskId=="buyushouli"){
											reajectReason("buYuShouLi","不予受理");
										}else if(routeToTaskId=="16"){
											buQiBuZheng4GaoZhi();
										}else if(routeToTaskId=="19"){
											buQiBuZheng4ShouLi();
										}else{
											docUserChoise(routeToTaskId);
										}
									}).fail(
										function() {
											alert("保存失败");
										});
							});
				}
			}
	})
	
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
		var width = $(window).width();
		var height = $(window).height() - 10;
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
		
	}

	//新建办文的时候，默认选择详细信息表，当从待办、在办、办结打开的时候选择的是当前节点的表单
	if(itembox=="add"){
		$('#easyui-tabs').tabs('select', 0);
	}else{
		if(formId.length>1){
			$('#easyui-tabs').tabs('select', 1);
		}else{
			$('#easyui-tabs').tabs('select', 0);
		}
	}
});
