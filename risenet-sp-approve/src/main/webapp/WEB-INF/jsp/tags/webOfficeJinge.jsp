<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/head.jsp"%>
<title></title>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
</head>
<body topmargin=0 leftmargin=0 rightmargin=0 bottommargin=0
	marginwidth=0 marginheight=0 width='100%' height='100%'>
	<!-- onunload='checkSave();' -->
	
	<object id='WebOffice'
		classid='clsid:8B23EA28-2009-402F-92C4-59BE0E063499'
		codebase='${ctx}/static/tags/ocx/iWebOffice2009.cab#version=10,7,2,4'></object>
	<script language='javascript'>
		var oWebOffice = document.getElementById('WebOffice');
		var wordReadOnly='${wordReadOnly}';
		var permission='${permission}';
		var fileType ="";
		
		$(document).ready(function() {
			resize();
			var isIE=checkIE();
			if(isIE){
				load();
			}else{
				alert("当前正文控件只支持IE浏览器,请使用IE浏览器打开正文！");
			}
		});
		function resize() {
			var winWidth = $(window).width();
			var winHeight = $(window).height();
			$(oWebOffice).css({
				"width" : (winWidth) + "px",
				"height" : (winHeight) + "px"
			});
		}
		//定时保存
		//window.setInterval('uploadDocument(2)',60 *60* 1000);
		
		//var obj=window.setInterval('uploadDocument(2);', 5 * 1000);
		//window.clearInterval(obj);

		//判断是不是IE
		function checkIE(){
	            if (!!window.ActiveXObject || "ActiveXObject" in window){
	                return true;
	            }else{
	                return false;
	            }
		}
		
		function CheckWps(){
		    try{
		    	new ActiveXObject("WScript.Shell");}   //创建新控件
		    catch(x) {
		    	return false;
		    }
		    try{new ActiveXObject("Wps.Application");}//创建word新应用程序
		    catch(x){
		    	return null;
		    }
		    return true;
	    }
		
		function CheckWord(){
		    try{
		    	new ActiveXObject("Word.Application");}//创建word新应用程序
		    catch(x){
		    	return null;
		    }
		    return true;
	    }
		
		function getFileType(){
			isInstallWps=CheckWps();//检查是否安装了word
		    switch(isInstallWps){
			    case true:
			     fileType =".wps";
			     break;
			    case null:
			     isInstallWord=CheckWord();//检查是否安装了word
				    switch(isInstallWord){
					    case true:
					     fileType =".doc";
					     break;
					    case null:
					     alert("当前机器没有安装Wps和Word,请选择一个进行安装");
				    }
			     break;
			    case false:
			     alert("当前机器的ActiveX被禁用,请启用IE的ActiveX相关设置");
		    }
		}
		
		function load() {
			try {
				getFileType()
				oWebOffice.WebUrl = '${ctx}/iWebOfficeServlet';
				oWebOffice.RecordID = '${processSerialNumber}';
				oWebOffice.FileName = '';
				oWebOffice.Compatible = true;
				oWebOffice.FileType = fileType;
				oWebOffice.UserName = '${userName}';
				oWebOffice.EditType = '3,0';
				oWebOffice.MaxFileSize = 100 * 1024;
				oWebOffice.Language = 'CH';
				oWebOffice.PenColor = '#000000';
				oWebOffice.PenWidth = '1';
				oWebOffice.Print = '1';
				
				if(wordReadOnly=="YES"){
					oWebOffice.ShowToolBar =2;
				}else{
					oWebOffice.ShowToolBar =1;
				}
				
				oWebOffice.AppendTools('9', '签名', '9');
				oWebOffice.AppendTools('7', '显示修订', '9');
				oWebOffice.AppendTools('8', '隐藏修订', '9');
				oWebOffice.AppendTools('3', '转PDF上传', '9');
				oWebOffice.AppendTools('6', '撤销套红', '20');
				oWebOffice.AppendTools('4', '套-红', '21');
				oWebOffice.AppendTools('5', '上传word', '2');
				
				oWebOffice.VisibleTools("撤销套红",false);
				oWebOffice.VisibleTools("新建文件",false);
				oWebOffice.VisibleTools("打开文件",false);
				oWebOffice.VisibleTools("保存文件",false);
				oWebOffice.VisibleTools("文字批注",false);
				oWebOffice.VisibleTools("手写批注",false);
				oWebOffice.VisibleTools("重新批注",false);
				oWebOffice.VisibleTools("文档清稿",false);
				oWebOffice.VisibleTools("显示修订",false);//默认打开word时为显示修订的
				oWebOffice.VisibleTools("隐藏工具栏",false);
				

				oWebOffice.WebSetMsgByName('processSerialNumber',
						'${processSerialNumber}');
				oWebOffice.WebOpen(false);//OPTION=LOADFILE
				if(wordReadOnly=="YES"){
					oWebOffice.EditType = '0,0';
					permissionAllBtn("NO");
				}else{
					if(oWebOffice.Status==1){//oWebOffice.Status为后台loadFile时候返回的值，空：为新建空白文档，0为没有套红的文档，1为套红文档，套红文档不可以只读
						oWebOffice.EditType = '0,0';//文档保护状态
						oWebOffice.VisibleTools("撤销套红",true);
						oWebOffice.VisibleTools("套-红",false);
						oWebOffice.VisibleTools("显示修订",false);
						oWebOffice.VisibleTools("隐藏修订",false);
					}else{
						oWebOffice.EditType = '3,0';
						permissionBtn(permission);
						ShowRevision(true);
					}
				}
				oWebOffice.ShowType = 1;//设置载入文档后的界面模式：1文字批注；2手写批注；0文档核稿。
				oWebOffice.WebObject.Application.ActiveWindow.ActivePane.View.Zoom.percentage = 100;
			} catch (e) {
				alert(e);
			}
		}
		
		function permissionBtn(str){
			if(permission=='NO'){
				oWebOffice.VisibleTools("套-红",false);
				oWebOffice.VisibleTools("转PDF上传",false);
			}
		}
		
		function permissionAllBtn(str){
			if(permission=='NO'){
				oWebOffice.VisibleTools("套-红",false);
				oWebOffice.VisibleTools("转PDF上传",false);
				oWebOffice.VisibleTools("上传word",false);
				oWebOffice.VisibleTools("隐藏修订",false);
			}
		}
		
		function uploadDocument(istaohong) {
			var processInstanceId = parent.$("#processInstanceId").val();
			var taskId = parent.$("#taskId").val();
			var tenantId = parent.$("#tenantId").val();
			var activitiUser = parent.$("#activitiUser").val();
			var documenttitle = parent.$('#form1 #title').val();
			oWebOffice.WebSetMsgByName('processSerialNumber',
					'${processSerialNumber}');
			oWebOffice.WebSetMsgByName('processInstanceId', processInstanceId);
			oWebOffice.WebSetMsgByName('taskId', taskId);

			oWebOffice.WebSetMsgByName('RECORDID', '${processSerialNumber}');
			oWebOffice.WebSetMsgByName('documenttitle', documenttitle);
			oWebOffice.WebSetMsgByName('activitiUser', activitiUser);
			oWebOffice.WebSetMsgByName('FILETYPE', fileType);
			oWebOffice.WebSetMsgByName('UserGuid', 'kong');
			oWebOffice.WebSetMsgByName('tenantId', tenantId);
			oWebOffice.WebSetMsgByName('istaohong', istaohong);
			if (oWebOffice.Modify) {
				if (oWebOffice.WebSave(true)) {
					return true;
				} else {
					return false;
				}
			}
			return true;
		}

		function checkSave() {
			try {
				if (oWebOffice.Modify) {
					if (confirm('正文已修改，是否保存？')) {
						uploadDocument(2);
					}
				}

			} catch (e) {
				alert(e.description);
			}
		}
		
		//设置显示或隐藏痕迹
		function ShowRevision(mValue) {
			if (mValue) {
				oWebOffice.WebShow(true);
			} else {
				oWebOffice.WebShow(false);
			}
		}
		
		//打印文档
		function WebOpenPrint() {
			try {
				oWebOffice.WebOpenPrint(); 
			} catch (e) {
				alert(e.description);
			}
		}
		//文档保存到本地（有对话框）
		function WebSaveLocal() {
			try {
				oWebOffice.WebSaveLocal();
			} catch (e) {
				alert(e.description);
			}
		}
		function openTaoHong() {
			var activitiUser = parent.$("#activitiUser").val();
			
			var mDialogUrl = "${ctx}/jinge/openTaoHong?activitiUser="
					+ activitiUser;
			var rval = window.showModalDialog(mDialogUrl, 'null',
					'dialogHeight:450px;dialogWidth:550px;center:1');
			var template_guid = rval.guid;
			if (template_guid == "") {
				alert("传入模版Id为空！");
				return false;
			} else {
				uploadDocument(0);//套红前保存word
				WebOffice.WebSetMsgByName("template_guid", template_guid);
				WebOffice.WebSetMsgByName("COMMAND", "LOADTEMPLATE"); //设置变量 COMMAND="INSERTFILE"
				if (oWebOffice.WebLoadTemplate()) {
					oWebOffice.WebSetMsgByName("RECORDID",'${processSerialNumber}');
					WebOffice.WebSetMsgByName("COMMAND", "INSERTFILE");
					if (WebOffice.WebInsertFile()) { //填充公文正文交互OfficeServer的OPTION="INSERTFILE"
						uploadDocument(1);
					} else {
						alert(oWebOffice.Status);
					}
				} else {
					alert(oWebOffice.Status);
				}
			}
		}
		function unDoTaoHong() {
			if (confirm('是否撤销套红？')) {
				//撤销套红就等于打开未套红的word,istaohong=0
				oWebOffice.WebSetMsgByName('operation', "unDoTaohong");
				oWebOffice.WebSetMsgByName("RECORDID", '${processSerialNumber}');
				if (WebOffice.WebOpen(false)) { //打开该文档
					alert("撤销成功");
				} else {
					alert("撤销失败");
				}
			}
		}
		function myWebSavePDF(){
			var processSerialNumber='${processSerialNumber}';
			var tenantId = parent.$("#tenantId").val();
			var documenttitle = parent.$('#form1 #title').val();
			fileType=".pdf";
			oWebOffice.WebSetMsgByName('processSerialNumber',processSerialNumber);

			oWebOffice.WebSetMsgByName('RECORDID',processSerialNumber);
			oWebOffice.WebSetMsgByName('documenttitle', documenttitle);
			oWebOffice.WebSetMsgByName('tenantId', tenantId);
			try{
				if(oWebOffice.WebSavePDF()){
					alert("word转pdf文件成功");
				}else{
					alert("word转pdf文件失败");
				}
			}catch(e){
				alert(e.description);
			}
			window.location.href="${ctx}/jinge/forwardReadPDF?recordId="+processSerialNumber;
			
		}
	</script>
	<script language='javascript' for='WebOffice'
		event='OnMenuClick(vIndex,vCaption)'>
		if (vIndex == 2) {
			WebSaveLocal();
		}
		if (vIndex == 88) {
			if (uploadDocument(2) == true) {
				alert('保存成功');
			} else {
				alert('保存失败');
			}
		}
		if (vIndex == 8) {
			ShowRevision(true);
		}
		if (vIndex == 9) {
			ShowRevision(false);
		}
		

		if (vIndex == 13) {
			WebOpenPrint();
		}
	</script>

	<script language='javascript' for=WebOffice
		event='OnToolsClick(vIndex,vCaption)'>
		if (vIndex == 2) {
			WebSaveLocal();
		}
		if (vIndex == 3) {
			myWebSavePDF();
		}
		//套红
		if (vIndex == 4) {
			openTaoHong();
			oWebOffice.VisibleTools("撤销套红",true);
			oWebOffice.VisibleTools("套-红",false);
			oWebOffice.EditType = '0,0';//文档保护状态
			oWebOffice.VisibleTools("显示修订",false);
			oWebOffice.VisibleTools("隐藏修订",false);
		}
		//撤销套红
		if (vIndex == 6) {
			unDoTaoHong();
			oWebOffice.VisibleTools("套-红",true);
			oWebOffice.VisibleTools("撤销套红",false);
			oWebOffice.EditType = '3,0';//撤销文档保护状态
			ShowRevision(true);//显示修订
			oWebOffice.VisibleTools("隐藏修订",true);
		}
		if (vIndex == 5) {
			if (uploadDocument(2) == true) {
				alert('保存成功');
			} else {
				alert('保存失败');
			}
		}
		if (vIndex == 7) {
			ShowRevision(true);
			oWebOffice.VisibleTools("显示修订",false);
			oWebOffice.VisibleTools("隐藏修订",true);
		}
		if (vIndex == 8) {
			ShowRevision(false);
			oWebOffice.VisibleTools("隐藏修订",false);
			oWebOffice.VisibleTools("显示修订",true);
		}
		
		if(vIndex == 9){
			oWebOffice.WebOpenSignature(4);
		}
		
		if (vIndex == 13) {
			WebOpenPrint();
		}
	</script>
</body>
</html>
