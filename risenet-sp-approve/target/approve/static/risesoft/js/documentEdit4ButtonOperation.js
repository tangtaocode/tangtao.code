var reAssignOrConsult="";

function executeSave() {
	var formIds = $('#formIds').val();
	var formDatas = [];
	var formData = saveFormData(formIds, formNames, tableNames);
	formDatas.push(formData);
	return formDatas;
}

function saveFormData(formIds, formNames, tableNames) {
	var dtd = $.Deferred(); // 在函数内部，新建一个Deferred对象
	//var formJsonData = JSON.stringify(formModel);
	var formJsonData = ko.toJSON(formModel);
	$.ajax({
		type : "POST",
		url : ctx + "/sp/form/run/saveFormData",
		data : {
			formJsonData : formJsonData,
			'processInstanceId' : $('#processInstanceId').val(),
			'formIds' : formIds,
			'formNames' : formNames
		},
		success : function(data) {
			if (data.success == true) {
				dtd.resolve();
			} else {
				dtd.reject("");
			}
		}
	});
	return dtd.promise();
}

// 保存标题，流程启动后再保存或发送时，有可能标题已经被修改，所以还需要再保存一次
function saveTitle() {
	var dtd = $.Deferred(); // 在函数内部，新建一个Deferred对象
	$.ajax({
		type : "POST",
		url : ctx + "/buttonOperation/saveTitle",
		data : {
			taskId : $('#taskId').val(),
			documentTitle : $('#form1 #title').val()
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

//发送
function executeSend(routeToTaskId, userIds) {
	$("#documentForm").ajaxSubmit({
		type : 'POST',
		dataType : 'json',
		data : {
			'routeToTaskId' : routeToTaskId,
			'userChoice' : userIds
		},
		url : ctx + '/sp/document/forwarding',
		success : function(responseText, statusText, xhr, $form) {
			alert(responseText.msg);
			var locationurl= window.location.search;
			if (responseText.success == true) {
				//判断是否来自超级待办
				var locationurl= window.location.search;
				if(locationurl.indexOf("fromTodoList")>0){
					window.opener.location.reload();
					window.close();
				}else{
					if(routeToTaskId=="kaishishouli"||routeToTaskId=="cailiaochushen"){
						var nextTaskId = responseText.nextTaskId;
						var documentTitle = $('#documentTitle').val();
						if(routeToTaskId=="kaishishouli"){
							slurl=tourlprocess(processSerialNumber,nextTaskId,routeToTaskId,processDefinitionKey,processDefinitionId,$('#processInstanceId').val(),documentTitle,activitiUser);
							initTourl();
							$('#fromPage').val("kaishishouli");
							validateForm();
						}else{
							process(processSerialNumber,nextTaskId,routeToTaskId,processDefinitionKey,processDefinitionId,$('#processInstanceId').val(),documentTitle,activitiUser);
						}
						
					}else{
						window.parent.initMenu();
					}
				}
			}
		}
	});
}

//办理页面
function process(processSerialNumber,taskId,taskDefinitionKey,processDefinitionKey,processDefinitionId,processInstanceId,documentTitle,assignee) {
	var url = ctx+'/sp/document/edit?itembox=todo&processDefinitionKey='+processDefinitionKey+'&processDefinitionId='+processDefinitionId +'&processSerialNumber=' + processSerialNumber +'&taskId=' + taskId + '&taskDefinitionKey=' + taskDefinitionKey +'&processInstanceId=' + processInstanceId +'&activitiUser='+activitiUser;
	window.parent.updateTab(documentTitle,url);
}

function tourlprocess(processSerialNumber,taskId,taskDefinitionKey,processDefinitionKey,processDefinitionId,processInstanceId,documentTitle,assignee) {
	return ctx+'/sp/document/edit?itembox=todo&processDefinitionKey='+processDefinitionKey+'&processDefinitionId='+processDefinitionId +'&processSerialNumber=' + processSerialNumber +'&taskId=' + taskId + '&taskDefinitionKey=' + taskDefinitionKey +'&processInstanceId=' + processInstanceId +'&activitiUser='+activitiUser;
}
	
function end(result,taskDefKey) {
	var returnResult = result;
	var userIds = returnResult;
	var routeToTaskId=taskDefKey;
	if (typeof (userIds) == "undefined" || userIds == "") {
		alert("请选择用户");
		return;
	}
	if ($.trim(userIds).length == 0) {
		alert('请选择一个用户。');
		return;
	}
	if ($.trim(routeToTaskId).length == 0) {
		alert('请选择一个任务。');
		return;
	}
	executeSend(routeToTaskId, userIds);
}

//转办
function reAssign(){
	reAssignOrConsult='reAssign';
	docUserChoise(taskDefKey);
}

//协办
function consult(){
	reAssignOrConsult='consult';
	docUserChoise(taskDefKey);
}

function doReAssignOrConsult(url){
	$.ajax({
		async : false,
		type : 'POST',
		dataType:'JSON',
		data : {
			taskId:taskId,
			routeToTaskId: routeToTaskId,
			processDefinitionId:processDefinitionId,
			userChoice:userChoice
		},
		url :url,
		success : function(data) {
			if(data.success) {
				alert(data.msg);
				//window.parent.openTab("待办件",ctx + '/sp/worklist/todo');
				window.parent.initMenu();
			}else{
				alert(data.msg);
			}
			
		}
	});
}

//退回
function rollback(){
	$.ajax({
		async : false,
		type : 'POST',
		data : {
			taskId:taskId
		},
		url :ctx + '/buttonOperation/rollback',
		success : function(data) {
			if(data.success) {
				window.parent.initMenu();
			}
			alert(data.msg);
		}
	});
}

// 完成,在协办的情况下
function completeTask(){
	$.ajax({
		async : false,
		type : 'POST',
		data : {
			taskId:taskId
		},
		url :ctx + '/buttonOperation/completeTask',
		success : function(data) {
			alert(data.msg);
			if(data.success) {
				window.parent.initMenu();
				//window.parent.openTab("待办件",ctx + '/sp/worklist/todo');
			}
		}
	});
}

//办理完成，并行状态下
function handleParallel(){
	$.ajax({
		async : false,
		type : 'POST',
		data : {
			taskId:taskId
		},
		url :ctx + '/buttonOperation/handleParallel',
		success : function(data) {
			if(data.success) {
				alert(data.msg);
				window.parent.initMenu();
				//window.parent.openTab("待办件",ctx + '/sp/worklist/todo');
			}else{
				alert(data.msg);
			}
		}
	});
}

//签收页面
function claim() {
	$.ajax({
		type : "GET",
		url :ctx+'/document/claim/' + taskId,
		success : function(data) {
			var usersStr=data.usersStr;
			var deptNames=data.deptNames;
			if(usersStr.indexOf(",")<0){//一人多岗为一的情况，直接签收
				var user=usersStr;
				$.ajax({
					type : "POST",
					url :ctx+'/buttonOperation/claim/' + taskId+'/'+user,
					success : function(data) {
						alert(data.msg);
						if (data.success == true) {
							window.parent.initMenu();
							//window.parent.openTab("待办件",ctx + '/sp/worklist/todo');
						}
					}
				});
			}else{//一人多岗为多的情况，跳转到岗位选择页面、选择一个岗位进行签收
				var userArray= new Array();
				userArray=usersStr.split(',');
				var deptNamesArray= new Array();
				deptNamesArray=deptNames.split(',');
				choiceDept(userArray,deptNamesArray,taskId);
			}
		}
	});
}
//使得拒签下面的textarea输入框可以输入
function selectR(obj){
	 document.getElementById("infoo").disabled = !obj.checked;
}
//拒签
function unclaim() {
	$( "#dialog-rejectReason" ).dialog({
		title:"填写拒签原因",
		href:ctx+'/buttonOperation/rejectReason',   
		resizable: false,
		width : 670,
		height:400,
		left:320,
		top:100,
		modal: true,
		buttons:[{
			text:'确定',
			handler:function() {
				var backValue = false;
				var d = 1;
				var info="";
				for(var i=1;i<12;i++){
					if(i<11){
						if(document.getElementById("checkbox"+i).checked){
							backValue = true;
							if(info==""){
								info = d +"、"+document.getElementById("checkbox"+i).value+"<br>";
							}else{
								info = info + d +"、"+document.getElementById("checkbox"+i).value+"<br>";
							}
							d= d + 1;
						}
					}else{
						if(document.getElementById("infoo").value.trim()!=""&&document.getElementById("checkbox11").checked){
							backValue = true;
							if(info!=""){
								info = info + d +"、"+document.getElementById("infoo").value+"<br>";
							}else{
								info = d +"、"+document.getElementById("infoo").value+"<br>";
							}
						}
					}
				}
				if(!backValue){
					alert("退文原因不能为空！");
					return;
				} else {
					var rejectPersonName=$("#employeeName").val();
					var rejectPersonMobile=$("#employeeMobile").val();
					var documentTitle=$('#form1 #title').val();
					$.ajax({
						type : "POST",
						url :ctx+'/buttonOperation/unclaim/' + taskId+'/'+info+'/'+documentTitle,
						success : function(data) {
							alert(data.msg);
							if (data.success == true) {
								window.parent.initMenu();
								//window.parent.openTab("待办件",ctx + '/sp/worklist/todo');
							}
						}
					});
					$("#dialog-rejectReason").dialog( "close" );
				}
			}
		},
		{
			text:'取消',
			handler: function() {
				$("#dialog-rejectReason").dialog( "close" );
			}
		}]
	});
}

//收回
function drawTask(){
	$.ajax({
		async : false,
		type : 'POST',
		data : {
			taskId:taskId,
			processDefinitionKey:processDefinitionKey
		},
		url :ctx + '/buttonOperation/drawTask',
		success : function(data) {
			if(data.success) {
				alert(data.msg);
				window.parent.initMenu();
				//window.parent.openTab("待办件",ctx + '/sp/worklist/todo');
			}else{
				alert(data.msg);
			}
		}
	});
}

//拒签
function toRefuseClaim(){
	if(refuseClaimTip==""){
		refuseClaim(false);
	}else{
		$( "#dialog-refuseClaimTip" ).dialog({
			title:"是否拒签",
			resizable: false,
			width : 310,
			height:150,
			left:480,
			top:200,
			modal: true,
			buttons:[{
				text:'拒签',
				handler:function() {
					refuseClaim(true);
					$("#dialog-refuseClaimTip").dialog( "close" );
				}
			},
			{
				text:'取消',
				handler: function() {
					$("#dialog-refuseClaimTip").dialog( "close" );
				}
			}]
		});
	}
	
}

//补齐补正
var frameID = newGuid();
function buQiBuZheng4GaoZhi(){
	uploadfilesId.window.getCheckedFile();//
	var ytjids = window.frames["uploadfilesId"].document.getElementById("ytjids").value;
	var xbqids = window.frames["uploadfilesId"].document.getElementById("xbqids").value;
	var xbzids = window.frames["uploadfilesId"].document.getElementById("xbzids").value;
	if(xbqids=='' && xbzids==''){
		if(window.confirm('还未选中任何材料，是否确认发起补齐补正？')){
			openCurWindow({
        		id : frameID,
                src : ctx+'/bjgz/adviceForm?method=&instanceId='+SPinstanceId+'&ytjids='+ytjids+'&xbqids='+xbqids+'&xbzids='+xbzids,
        		destroy : true,
        		title : '补齐补正告知单',
        		width : $(window).width()+'px',
        		height : ($(window).height() - 10) + 'px',
        		modal : true
        	});
         }else{
            return false;
        }
	}
	openCurWindow({
		id : frameID,
        src : ctx+'/bjgz/adviceForm?method=&instanceId='+SPinstanceId+'&ytjids='+ytjids+'&xbqids='+xbqids+'&xbzids='+xbzids,
		destroy : true,
		title : '补齐补正告知单',
		width : $(window).width()+'px',
		height : ($(window).height() - 10) + 'px',
		modal : true
	});
	
}
//补齐补正告知确定
function buQiBuZheng4GaoZhiOk(){
	$.ajax({
		async : false,
		type : 'POST',
		dataType:'JSON',
		data : {
			taskId:taskId
		},
		url :ctx + '/sp/buttonOperation/buQiBuZheng/gaoZhi',
		success : function(data) {
			alert(data.msg);
			if(data.success) {
				window.parent.initMenu();
				//window.parent.openTab("待办件",ctx + '/sp/worklist/todo');
			}
		}
	});
}

//补齐补正受理
function buQiBuZheng4ShouLi(){
	$.ajax({
		async : false,
		type : 'POST',
		dataType:'JSON',
		data : {
			taskId:taskId
		},
		url :ctx + '/sp/buttonOperation/buQiBuZheng/shouLi',
		success : function(data) {
			alert(data.msg);
			if(data.success) {
				window.parent.initMenu();
				//window.parent.openTab("待办件",ctx + '/sp/worklist/todo');
			}
		}
	});
}

//拒签
function refuseClaim(b){
	$.ajax({
		async : false,
		type : 'POST',
		data : {
			taskId:taskId,
			isLastPerson:b
		},
		url :ctx + '/buttonOperation/refuseClaim',
		success : function(data) {
			if(data.success) {
				alert(data.msg);
				window.parent.initMenu();
				//window.parent.openTab("待办件",ctx + '/sp/worklist/todo');
			}else{
				alert(data.msg);
			}
		}
	});
}

//批准
function piZhun(){
	if (!validateData()) {
		return false;
	}
	$.ajax({
	    async:false,
	    cache: false,
        type: "POST",
        url:ctx+"/sp/buttonOperation/saveData4PiZhun",
        data: {pizhun:"ok",taskId:taskId},
        dataType: "json",
        success: function(data){
              if(data.success){
            	  executeSend(data.routeToTaskId,"sendToStartUser");
              }else{
            	  alert("保存批准状态到数据库失败");
              }
        }
	});
}

//特别程序申请
function teBieChengXuShenQing(){
	openCurWindow({
		id : frameID,
		src : ctx + "/sp/teBieChengXuShenQing/show?processInstanceId="+processInstanceId+"&taskId="+taskId,
		destroy : true,
		title : "特别程序申请",
		width : ($(window).width()*0.7) + 'px',
		height : ($(window).height()) + 'px',
		modal : true
	});
}

//特别程序处理
function teBieChengXuShenQingChuLi(){
	openCurWindow({
		id : frameID,
		src : ctx + "/sp/teBieChengXuShenQing/teBieChengXuShenQingChuLi?processInstanceId="+processInstanceId+"&taskId="+taskId,
		destroy : true,
		title : "特别程序审核",
		width : ($(window).width()*0.7) + 'px',
		height : ($(window).height()) + 'px',
		modal : true
	});
	
}

//特别程序结束
function teBieChengXuJieShu(){
	if(confirm("确认结束特别程序？")){
		$.ajax({
		    async:false,
		    cache: false,
	        type: "POST",
	        url:ctx + "/sp/teBieChengXuShenQing/teBieChengXuJieShu?processInstanceId="+processInstanceId+"&taskId="+taskId,
	        dataType: "json",
	        success: function(data){
	              if(data.success==true){
	            	  alert("结束特别程序成功");
	            	  window.parent.initMenu();
	              }else{
	            	  alert("结束特别程序失败");
	              }
	        }
		});
	}
}

//特殊办结
function specialComplete(){
	openCurWindow({
		id : frameID,
		src : ctx + "/sp/buttonOperation/specialComplete/show?taskId=" + $('#taskId').val(),
		destroy : true,
		title : "特殊办结",
		width : ($(window).width()*0.45) + 'px',
		height : ($(window).height()*0.4) + 'px',
		modal : true
	});
}

//出证办结
function chuZhengBanJie(){
	var certifyNumber = $('#form2 #certifyNumber').val();//证照编号
	openCurWindow({
		id : frameID,
		src : ctx + "/scanning/goscan?instanceGuid="+SPinstanceId+"&doctypeguid=" + doctypeguid+"&certifyNumber="+certifyNumber,
		destroy : true,
		title : "出证办结",
		width : ($(window).width()*0.7) + 'px',
		height : ($(window).height()*0.8) + 'px',
		modal : true
	});
}

//不予批准、受理原因
function reajectReason(actionSign,actionSignName){
	openCurWindow({
		id : frameID,
		src : ctx + "/rejectReason/show?processInstanceId="+$('#processInstanceId').val()+"&taskId="+$('#taskId').val()+"&actionSign="+actionSign+"&actionSignName="+actionSignName,
		destroy : true,
		title : "请填写"+actionSignName+"原因",
		width : 600,
		height : 300,
		modal : true
	});
}

//不予批准
function buYuPiZhun(){
	if (!validateData()) {
		return false;
	}
	$.ajax({
	    async:false,
	    cache: false,
        type: "POST",
        url:ctx+"/sp/buttonOperation/saveData4BuYuPiZhun",
        data: {pizhun:"ok",taskId:taskId},
        dataType: "json",
        success: function(data){
              if(data.success){
            	  executeSend(data.routeToTaskId,"sendToStartUser");
              }else{
            	  alert("保存批准状态到数据库失败");
              }
        }
	});
}

//不予受理
function buYuShouLi(){
	if (!validateData()) {
		return false;
	}
	executeSend("buyushouli","sendToStartUser");
}