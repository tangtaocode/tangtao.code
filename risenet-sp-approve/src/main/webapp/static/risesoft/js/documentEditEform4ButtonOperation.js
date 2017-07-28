var reAssignOrConsult = "";
var sponsorGuid = "";
//发送
function executeSend(routeToTaskId, userIds) {
	$("#documentForm").ajaxSubmit({
		type : 'POST',
		dataType : 'json',
		data : {
			'routeToTaskId' : routeToTaskId,
			'userChoice' : userIds,
			'sponsorGuid' : sponsorGuid
		},
		url : ctx + '/sp/document/forwarding',
		success : function(responseText, statusText, xhr, $form) {
			alert(responseText.msg);
			var locationurl= window.location.search;
			if (responseText.success == true) {
				if(routeToTaskId=="cailiaochushen"){
					var nextTaskId = responseText.nextTaskId;
					var documentTitle = $('#form1').contents().find("input[name='title']").val();
					process(processSerialNumber,nextTaskId,routeToTaskId,processDefinitionKey,processDefinitionId,processInstanceId,documentTitle,activitiUser);
				}else{
					window.parent.refreshMenu();
					//window.parent.closeCurrent();
					/*if(taskDefKey=="dengji"){//评价器评价
						//slurl=tourlprocess(processSerialNumber,nextTaskId,routeToTaskId,processDefinitionKey,processDefinitionId,$('#processInstanceId').val(),documentTitle,activitiUser);
						//initTourl();
						$('#fromPage').val("kaishishouli");
						//validateForm();
					}else{
						window.parent.refreshMenu();
					}*/
					
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
	
function end(result,taskDefKey,sponsorguid) {
	var userIds = result;
	var routeToTaskId = taskDefKey;
	sponsorGuid = sponsorguid;
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
				window.parent.refreshMenu();
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
				window.parent.refreshMenu();
			}
			alert(data.msg);
		}
	});
}

//完成,在协办的情况下
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
				window.parent.refreshMenu();
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
		url :ctx + '/sp/buttonOperation/handleParallel',
		success : function(data) {
			if(data.success) {
				alert(data.msg);
				window.parent.refreshMenu();
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
		url :ctx+'/document4Eform/claim/' + taskId,
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
							window.parent.refreshMenu();
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
	//	href:ctx+'/buttonOperation/rejectReason',   
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
								window.parent.refreshMenu();
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
				window.parent.refreshMenu();
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
	
	if(window.frames["uploadfilesId"].document.getElementById("ytjids")==null){
		alert("请先查看材料清单！")
		return;
	}
	window.frames["uploadfilesId"].getCheckedFile();
	var ytjids = window.frames["uploadfilesId"].document.getElementById("ytjids").value;
	var xbqids = window.frames["uploadfilesId"].document.getElementById("xbqids").value;
	var xbzids = window.frames["uploadfilesId"].document.getElementById("xbzids").value;
	/*if(xbqids=='' && xbzids==''){
		alert("请勾选需补齐补正的材料!");
		return false;
	}*/
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

//补正告知说明
function buQiBuZhengGaoZhiRemark(){
	var url = ctx+"/bjgz/adviceRemark?approveitemguid="+SPinstanceId+"&method=instanceId";
	openCurWindow({
		id : frameID,
        src : url,
		destroy : true,
		title : '一次性补交告知说明',
		width : $(window).width()+'px',
		height : ($(window).height() - 10) + 'px',
		modal : true
	});
  	//Dialog.open({URL:url,Title:"一次性补交告知说明",Width:"800",Height:"500"});
}
//补齐补正告知确定
function buQiBuZheng4GaoZhiOk(){
	if(window.frames["uploadfilesId"].document.getElementById("ytjids")==null){
		alert("请先查看材料清单！")
		return;
	}
	window.frames["uploadfilesId"].getCheckedFile();
	var ytjids = window.frames["uploadfilesId"].document.getElementById("ytjids").value;
	var xbqids = window.frames["uploadfilesId"].document.getElementById("xbqids").value;
	var xbzids = window.frames["uploadfilesId"].document.getElementById("xbzids").value;
	//alert("369");
	/*if(xbqids=='' && xbzids==''){
		alert("请勾选需补齐补正的材料!");
		return false;
	}*/
	$.ajax({
		async : false,
		type : 'POST',
		dataType:'JSON',
		data : {
			taskId:taskId,
			ytjids:ytjids,
			xbqids:xbqids,
			xbzids:xbzids
		},
		url :ctx + '/sp/buttonOperation/buQiBuZheng/gaoZhi',
		success : function(data) {
			alert(data.msg);
			if(data.success) {
				window.parent.refreshMenu();
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
				window.parent.refreshMenu();
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
				window.parent.refreshMenu();
				//window.parent.openTab("待办件",ctx + '/sp/worklist/todo');
			}else{
				alert(data.msg);
			}
		}
	});
}

//批准
function piZhun(){
	var str;
	$.ajax({
	    async:false,
	    cache: false,
        type: "POST",
        url:ctx+"/sp/buttonOperation/saveData4PiZhun",
        data: {pizhun:"ok",taskId:taskId},
        dataType: "json",
        success: function(data){
              if(data.success){
            	  str = true;
            	  //executeSend(data.routeToTaskId,"sendToStartUser","");
              }else{
            	  str = false;
            	  alert("保存批准状态到数据库失败");
              }
        }
	});
	return str;
}

//不予批准
function buYuPiZhun(){
	var str;
	$.ajax({
	    async:false,
	    cache: false,
      type: "POST",
      url:ctx+"/sp/buttonOperation/saveData4BuYuPiZhun",
      data: {pizhun:"ok",taskId:taskId},
      dataType: "json",
      success: function(data){
            if(data.success){
          	  //executeSend(data.routeToTaskId,"sendToStartUser","");
          	  str = true;
            }else{
          	  str = false;
          	  alert("保存批准状态到数据库失败");
            }
      }
	});
	return str;
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
function teBieChengXuShenQingShenHe(){
	openCurWindow({
		id : frameID,
		src : ctx + "/sp/teBieChengXuShenQing/teBieChengXuShenQingShenHe?processInstanceId="+processInstanceId+"&taskId="+taskId,
		destroy : true,
		title : "特别程序审核",
		width : ($(window).width()*0.7) + 'px',
		height : ($(window).height()) + 'px',
		modal : true
	});
	
}

//特别程序结果
function teBieChengXuJieGuo(){
		openCurWindow({
			id : frameID,
			src:ctx + "/sp/teBieChengXuShenQing/teBieChengXuJieGuo?processInstanceId="+processInstanceId+"&taskId="+taskId,
			destroy : true,
			title : "特别程序结果",
			width : ($(window).width()*0.5) + 'px',
			height : ($(window).height()*0.8) + 'px',
			modal : true
		});
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
	var declarerperson = "";	//证照主体姓名
	var address = "";	//申请单位地址
	var certifyNumber = "";     //证照编号
	var bureauName = "";       //办结单位，发证单位
	var jzrq="";			//证件有效期
	if(document.getElementById('form1')!=null){
		var obj = document.getElementById('form1').contentWindow.document.getElementById('jbxx_declarerperson');
		var obj1 = document.getElementById('form1').contentWindow.document.getElementById('jbxx_address');
		if(obj!=null){
			declarerperson = obj.value;
		}
		if(obj1!=null){
			address = obj1.value;
		}
	}
	if(document.getElementById('form2')!=null){
		var obj1 = document.getElementById('form2').contentWindow.document.getElementById('bjd_certifyNumber'); 
		var obj2 = document.getElementById('form2').contentWindow.document.getElementById('bjd_fzjg'); 
		var obj3 = document.getElementById('form2').contentWindow.document.getElementById('bjd_jzrq');
		if(obj1!=null){
			certifyNumber = obj1.value;
		}
		if(obj2!=null){
			bureauName = obj2.value;
		}
		if(obj3!=null){
			jzrq = obj3.value;
		}
	}
	openCurWindow({
		id : frameID,
		src : ctx + "/scanning/goscan?instanceGuid="+SPinstanceId+"&doctypeguid=" + doctypeguid+"&certifyNumber="+certifyNumber+"&bureauName="+bureauName+'&declarerperson='+declarerperson+'&address='+address+"&jzrq="+jzrq,
		destroy : true,
		title : "出证办结",
		width : ($(window).width()-10)+'px',
		height : ($(window).height() - 10) + 'px',
		modal : true
	});
}

//不予批准、受理原因
function reajectReason(actionSign,actionSignName){
	openCurWindow({
		id : frameID,
		src : ctx + "/rejectReason/show?processInstanceId="+processInstanceId+"&taskId="+taskId+"&actionSign="+actionSign+"&actionSignName="+actionSignName,
		destroy : true,
		title : "请填写"+actionSignName+"原因",
		width : 600,
		height : 300,
		modal : true
	});
}


//不予受理
function buYuShouLi(){
	if (!validateData()) {
		return false;
	}
	executeSend("buyushouli","sendToStartUser");
}