	
	var frameID = newGuid();
	
	//启动流程
	function startProcess(activitiUser) {
		var dtd = $.Deferred(); // 在函数内部，新建一个Deferred对象
		$.ajax({
			async:false, 
			cache:false,
			type : "POST",
			url : ctx + "/sp/document/startProcess",
			dataType:'JSON',
			data : {
				processDefinitionKey : processDefinitionKey,
				processSerialNumber : processSerialNumber,
				tenantId : tenantId,
				activitiUser : activitiUser,
				SPinstanceId : instanceGuid,
				status : '',
				documentTitle : documentTitle
			},
			error : function() {
				alert("网络错误!"); // 改变deferred对象的执行状态
				dtd.reject(); // 改变deferred对象的执行状态
			},
			success : function(data) {
				if (data.success == true) {
					if ($.trim(data.taskId).length == 0) {
						alert('流程启动出错');// taskId为空
						dtd.reject(); // 改变deferred对象的执行状态
					} else {
						dtd.resolve(); // 改变deferred对象的执行状态
					}
				} else {
					dtd.reject(); // 改变deferred对象的执行状态
				}
			}
		});
		return dtd.promise();
	}
	
	//确定发送
	function send(result){
		var userIds = result;
		//获取表单的标题
		documentTitle = document.getElementById('base').contentWindow.document.getElementById('approveItemName').value; 
			$.ajax({
			async:false, 
			cache:false,
			type : "POST",
			url : ctx + "/togetherWindow/getActivitiUser",
			data : {
				userChoice : userIds
			},
			success : function(data) {
				startProcess(data.activitiUser[0]).done(function() {//这里流程启动人取的是局收发员中的第一人。如果收发员有多个~~~~
						alert("发送成功");
						window.parent.refreshMenu();
					}).fail(function() {
						alert("流程启动失败");
					});
			}
		});
	}
	
	$(function() {
		 $('#easyui-tabs').tabs('add', {
			id : 'tab_base',
			title : '基本信息',
			content : '<iframe id=\"base\" name=\"base\" src=\"about:blank\" frameborder=\"0\" scrolling=\"yes\" style=\'background-color:#7abdf2;\'></iframe>',
			closable : false
		});  
		$('#easyui-tabs').tabs('add', {
			id : 'tab_file',
			title : '材料',
			content : '<iframe id=\"uploadfilesId\" name=\"uploadfilesId\" src=\"about:blank\" frameborder=\"0\" scrolling=\"no\" style=\'background-color:#7abdf2;\'></iframe>',
			closable : false
		});
	
		$('#easyui-tabs').tabs({
			onSelect : function(title) {
				  if (title == '基本信息') {
					var src = $('#base').attr('src');
					if (src == 'about:blank') {
						resize();
						$('#base').attr(
								'src',
								ctx+'/togetherWindow/editBase?itemid='+guid+'&instanceGuid='+instanceGuid+'&code='+code);
					}
				} 
				  
				if (title == '材料') {
					var src = $('#uploadfilesId').attr('src');
					if (src == 'about:blank') {
						resize();
						$('#uploadfilesId').attr(
								'src',
								ctx+'/togetherWindow/editPoint?itemid='+guid+'&instanceGuid='+instanceGuid+'&method=add');
					}
				}
			}
		});
		if(status!='0'){
			$('#easyui-tabs').tabs('select', 1);
		}
		
		$("#waidanweibanli").click(function() {//点击外单位办理时，保存基本信息
			document.getElementById('base').contentWindow.saveForm('send');
		});
	});
	
	//打开外单位选择框
	function departmentChoise() {
		documentTitle = document.getElementById('base').contentWindow.document.getElementById('approveItemName').value; 
		openCurWindow({
			id : frameID,
			src : ctx + "/togetherWindow/departmentChoise?instanceGuid="+instanceGuid+"&guid="+guid+"&documentTitle="+documentTitle,
			destroy : true,
			title : "部门选择",
			width : ($(window).width() - 100) + 'px',
			height : ($(window).height() - 10) + 'px',
			modal : true
		});
	}
	
	function saveReceive(){
		document.getElementById('base').contentWindow.saveForm('save');
	}
	
	function done(){
		document.getElementById('base').contentWindow.saveForm('done');
	}
	function resize() {
		var width = $(window).width() - 5;
		var height = $(window).height() - 30;
		 $('#base').css({
			"width" : (width) + "px",
			"height" : (height) + "px"
		}); 

		$('#uploadfilesId').css({
			"width" : (width) + "px",
			"height" : (height) + "px"
		});
	}
