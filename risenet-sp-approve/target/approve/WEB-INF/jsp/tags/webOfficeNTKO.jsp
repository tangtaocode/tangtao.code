<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/static/common/bpmTaglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/head.jsp"%>
<title></title>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body topmargin=0 leftmargin=0 rightmargin=0 bottommargin=0
	marginwidth=0 marginheight=0 onload='initDocument();' Onunload='checkSaved(2);'>

	<table cellspacing="0" cellpadding="0" bordercolor="#D4D0C8" border="0"
		width="100%" bgcolor="#D4D0C8" style="border-bottom-style: groove"
		bordercolorlight="#EEEEEE" bordercolordark="#B1AEA9">
		<tbody>
			<tr>
				<td align="left" style="font: Menu;">
					<button onclick="saveDocument(2);" class="op1" id="saveDocument">
						<font color="red">上传word</font>
					</button>
					<button onclick="openTaoHong();" class="op1" id="openTaoHong">选择套红</button>
					<button onclick="undoTaoHong(2);" class="op1" id="undoTaoHong">撤销红头</button>
					<button onclick="saveEFileAndTopdf(2);" class="op1" id="saveEFileAndTopdf">转PDF并上传</button>
					<button onclick="showRevisions(this);" class="op1" id="showRevisions">隐藏修订</button>
					<button onclick="showToolbars(this);" class="op1" id="showToolbars">显示工具栏</button>
				</td>
				<td align="right" style="font: Menu;">&nbsp;</td>
				<td align="right" style="font: Menu;"><b>上次修改人: </b>${userName}&nbsp;&nbsp;<b>上次修改时间:
				</b>${saveDate}&nbsp;</td>
			</tr>
		</tbody>
	</table>
	</td>
	</tr>
	</tbody>
	</table>

	<object id="riseOffice"
		classid="clsid:C9BC4DFF-4248-4a3c-8A49-63A7D317F404"
		codeBase="${ctx}/static/tags/ocx/OfficeControl.cab#version=5,0,2,4"
		width="100%" height="100%">
		<param name="BorderStyle" value="0">
		<param name="BorderColor" value="14402205">
		<param name="TitlebarColor" value="53668">
		<param name="TitlebarTextColor" value="0">
		<param name="MenubarColor" value="13160660">
		<param name="Caption" value="欢迎使用！">
		<param name="Titlebar" value="0">
		<param name="MaxUploadSize" value="10000000">
		<param name="CustomMenuCaption" value="辅助选项">
		<param name="ProductCaption" value="深圳市罗湖区信息中心">
		<param name="ProductKey" value="CF5FA619AD4331233437B6AE9E9137C09D5347BF">
		<!-- <param name="ProductCaption" value="深圳市水务局">
<param name="ProductKey" value="B033D5F2FEE8CF084E41AA2CB582DBC60F5F0BDB"> -->
		<SPAN STYLE="color: #3366cc">不能装载文档控件。请在检查浏览器的选项中检查浏览器的安全设置。</SPAN>
	</object>
	<script language="javascript">
		String.prototype.trim = function() {
			return this.replace(/(^\s*)|(\s*$)/g, "");
		};
		var fileType="";
		var isSaveAs = false;
		var wordTitle;
		var NTKO = document.getElementById("riseOffice");
		var processSerialNumber = '${processSerialNumber}';
		var userName = '${userName}';
		var fileDocumentId = '${fileDocumentId}';
		var openWordOrPDF = '${openWordOrPDF}';
		//var permission = '${permission}';
		var permission = 'YES';
		var taskId;
		var documenttitle;
		var processInstanceId;
		var itembox="${itembox}";
		var saveDocumentBtn=$('#saveDocument');
		var openTaoHongBtn=$('#openTaoHong');
		var undoTaoHongBtn=$('#undoTaoHong');
		var saveEFileAndTopdfBtn=$('#saveEFileAndTopdf');
		//var revokepdfBtn=$('#revokepdf');
		var showRevisionsBtn=$('#showRevisions');
		var showToolbarsBtn=$('#showToolbars');
		var wordReadOnly='${wordReadOnly}';
		var taoHongWordReadOnly=false;//从待办进入正文的时候，如果是套红的word，只读
		function initDocument() {
			var isIE=checkIE();
			if(!isIE){
				alert("当前正文控件只支持IE浏览器,请使用IE浏览器打开正文！");
				return false;
			}
			if (fileDocumentId != "") {
				if(openWordOrPDF=="openPDF"){
					openPDF();
				}else if(openWordOrPDF=="openTaoHongWord"){
					openDocument(1);
					taoHongWordReadOnly=true;
				}else{
					openDocument(0);
				}
				AddMyMenuItems();
			} else {
				newDocument();
				AddMyMenuItems();
			}
			SetDocUser("");
			SetDocUser(userName);
			//设置word控件自身按钮
			sysFileCtr(false);
			//处理正文word是否只读
			if(wordReadOnly=="YES"){
				NTKO.SetReadOnly(true,"");
				btnPermissionAll("NO");
			}else{
				if(taoHongWordReadOnly==true){
					NTKO.SetReadOnly(true,"");
					NTKO.Toolbars = false;
					btnPermission(permission);
				}else{
					NTKO.SetReadONly(false,"");
					NTKO.Toolbars = false;
					btnPermission(permission);
					setMarkModify(true);
				}
			}
		}
		$(document).ready(function() {
			$(window).on('resize', function() {
				resize();
			});
		});
		//处理显示套红和转PDF按钮
		function btnPermission(str){
			if(str=="NO"){
				openTaoHongBtn.css('display', 'none');
				undoTaoHongBtn.css('display', 'none');
				saveEFileAndTopdfBtn.css('display', 'none');
				showRevisionsBtn.css('display', 'none');
			}else{
				openTaoHongBtn.css('display', 'inline');
				undoTaoHongBtn.css('display', 'inline');
				saveEFileAndTopdfBtn.css('display', 'inline');
				showRevisionsBtn.css('display', 'inline');
			}
		}
		//所有可以编辑正文的操作
		function btnPermissionAll(str){
			if(str=="NO"){
				saveDocumentBtn.css('display','none');
				openTaoHongBtn.css('display', 'none');
				undoTaoHongBtn.css('display', 'none');
				saveEFileAndTopdfBtn.css('display', 'none');
				showRevisionsBtn.css('display', 'none');
				showToolbarsBtn.css('display', 'none');
				showMenubar(false);
				showStatusbar(false);
				NTKO.Toolbars = false;
			}else{
				saveDocumentBtn.css('display','inline');
				openTaoHongBtn.css('display', 'inline');
				undoTaoHongBtn.css('display', 'inline');
				saveEFileAndTopdfBtn.css('display', 'inline');
				showRevisionsBtn.css('display', 'inline');
				showToolbarsBtn.css('display', 'inline');
				showMenubar(true);
				showStatusbar(true);
				NTKO.Toolbars = true;
			}
		}
		
		//判断是不是IE
		function checkIE(){
	            if (!!window.ActiveXObject || "ActiveXObject" in window){
	                return true;
	            }else{
	                return false;
	            }
		}
		
		function checkWps(){
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
		
		function checkWord(){
		    try{
		    	new ActiveXObject("Word.Application");}//创建word新应用程序
		    catch(x){
		    	return null;
		    }
		    return true;
	    }
		
		function resize() {
			var winWidth = $(window).width();
			var winHeight = $(window).height();
			$(NTKO).css({
				"width" : (winWidth) + "px",
				"height" : (winHeight) + "px"
			});
		}

		function checkSaved(istaohong) {
			taskId = parent.$("#taskId").val();
			if (itembox=='add'||itembox=='todo') {
				if (!NTKO.ActiveDocument.Saved) {
					if (confirm("是否保存对文档的修改？")){
						saveDocument(istaohong);
					}
				}
			}
		}

		function newDocument() {
			resize();
			isInstallWps=checkWps();//检查是否安装了wps
		    switch(isInstallWps){
			    case true:
			    NTKO.CreateNew("WPS.Document");
			    fileType=".wps";
			     break;
			    case null:
			     isInstallWord=checkWord();//检查是否安装了word
				    switch(isInstallWord){
					    case true:
					    NTKO.CreateNew("Word.Document");
					    fileType=".word";
					     break;
					    case null:
					     alert("当前机器没有安装Wps和Word,请选择一个进行安装");
				    }
			     break;
			    case false:
			     alert("当前机器的ActiveX被禁用,请启用IE的ActiveX相关设置");
		    }
		}

		function openDocument(openFlag) {
			resize();
			try {
				NTKO.OpenFromURL(
								"${ctx}/ntko/openDocument?processSerialNumber="
										+ processSerialNumber + "&openFlag="
										+ openFlag, false);
				if(openFlag==1){//套红后的word,设置只读
					NTKO.SetReadOnly(true,"");
				}
			} catch (e) {
				alert(e.name + ": " + e.message+"，请联系管理员");
			}
		}
		
		function openPDF(){
			processInstanceId = parent.$("#processInstanceId").val();
			documenttitle = parent.$('#form1 #title').val();
			taskId = parent.$("#taskId").val();
			window.location.href="${ctx}/ntko/forwardReadPDF?processSerialNumber="+ processSerialNumber + "&processInstanceId="+ processInstanceId +"&taskId=" + taskId+"&itembox="+itembox;
		}
		
		function setFont() {
			var fonts = NTKO.ActiveDocument.Application.FontNames;
			var flag = 0;
			for ( var i = 1; i <= fonts.Count; i++) {
				if (fonts(i) == '华文中宋') {
					flag = 1;
					break;
				}
			}
			if (flag == 0) {
				NTKO.ActiveDocument.Content.Font.Name = '宋体';
			}
		}

		function saveDocument(istaohong) {//istaohong=0:word  =1:套红word   =2:pdf   =3
			var info="";
			if(istaohong==3){
				istaohong=0;
			}
			processInstanceId = parent.$("#processInstanceId").val();
			documenttitle = parent.$('#form1 #title').val();
			taskId = parent.$("#taskId").val();
			try {
				info = NTKO.SaveToURL(
							"${ctx}/ntko/saveDocument?processSerialNumber="
									+ processSerialNumber + "&istaohong="
									+ istaohong + "&processInstanceId="
									+ processInstanceId + "&taskId=" + taskId
									+ "&documenttitle=" + documenttitle+"&fileType="+fileType,
							"currentDoc", "", "huifuzhengwen/FileName", 0);
				
				if (info == "success:true") {
					if(istaohong==2){
						alert("保存文件成功！");
					}
					NTKO.ActiveDocument.Saved = true;
				} else {
					alert("保存文件错误，请联系管理员！");
					return;
				}
			} catch (e) {
				alert('无法保存文件！');
			}
		}
		function saveEFileAndTopdf(saveflag) {
			var isPDFCreatorInstalled=NTKO.IsPDFCreatorInstalled();
			if(isPDFCreatorInstalled){
				wordTopdf(saveflag);
			}else{
				alert("本机没有安装PDFCreator(1.2.3版本)转换器！");
				return false;
			}
		}

		function wordTopdf(saveflag) {
			try {
				var fileType=".pdf";
				processInstanceId = parent.$("#processInstanceId").val();
				taskId = parent.$("#taskId").val();
				NTKO.PublishAsPDFToURL("${ctx}/ntko/saveAsPDFFile?processSerialNumber="+ processSerialNumber + "&processInstanceId="
								+ processInstanceId +"&taskId=" + taskId+ "&istaohong=" + saveflag+"&fileType="+fileType, "currentDoc");
			} catch (e) {
				alert('转换失败！');
				return;
			}
		}
		//撤销pdf:删除PDF,加载转PDF之前的word文档
		function revokepdf(openFlag) {
			try {
				var docType = NTKO.DocType;
				if(docType!=51){
					alert("当前非PDF文档,不可以执行撤销PDF操作!");
					return false;
				}
				NTKO.OpenFromURL("${ctx}/ntko/openDocument?processSerialNumber="+ processSerialNumber + "&openFlag="+ openFlag, false);
				
				saveDocumentBtn.css('display','inline');
				if(taoHongWordReadOnly==true){
					NTKO.SetReadOnly(true,"");
					NTKO.Toolbars = false;
					btnPermission(permission);
				}else{
					NTKO.SetReadONly(false,"");
					NTKO.Toolbars = false;
					btnPermission(permission);
					setMarkModify(true);
				}
			} catch (e) {
				alert('转换失败！');
				return;
			}
		}
		function showMenubar(bool) {
			NTKO.Menubar = bool;
		}

		function showStatusbar(bool) {
			NTKO.Statusbar = bool;
		}

		function showToolbars(da) {
			var s = da.innerHTML;
			if (s == '显示工具栏') {
				NTKO.Toolbars = true;
				da.innerHTML = '隐藏工具栏';
			} else {
				NTKO.Toolbars = false;
				da.innerHTML = '显示工具栏';
			}
		}

		function showRevisions(da) {
			var s = da.innerHTML;
			if (s == '显示修订') {
				NTKO.ActiveDocument.ShowRevisions = true;
				da.innerHTML = '隐藏修订';
			} else {
				NTKO.ActiveDocument.ShowRevisions = false;
				da.innerHTML = '显示修订';
			}
		}
		
		
		
		function acceptAllRevisions() {
			NTKO.ActiveDocument.AcceptAllRevisions();
		}

		function rejectAllRevisions() {
			NTKO.ActiveDocument.RejectAllRevisions();
		}
		
		//进入或退出强制痕迹保留状态:就是打开或者关闭修订模式以及wps本身可操作的工具栏
		function setMarkModify(bool) {
			trackRevisions(bool);
			enableReviewBar(bool);
		}
		//打开或者关闭修订模式
		function trackRevisions(bool) {
			NTKO.ActiveDocument.TrackRevisions = bool;
		}
		//允许或禁止显示修订工具栏和工具菜单（保护修订,用户不能更改当前修订状态）
		function enableReviewBar(bool) {
			try {
				NTKO.ActiveDocument.CommandBars("Reviewing").Enabled = bool;
				NTKO.ActiveDocument.CommandBars("Track Changes").Enabled = bool;
				NTKO.IsShowToolMenu = bool;
			} catch (e) {
				
				var doc = NTKO.ActiveDocument;
				var app = doc.Application;
				var doctype = NTKO.DocType;
				if (6 != doctype||1!=doctype) {
					return;
				}
				var cmdbars = app.CommandBars;
				NTKO.IsShowToolMenu = !bool;
				doc.TrackRevisions = bool;
				cmdbars("Reviewing").Enabled = !bool;
				cmdbars("Reviewing").Visible = !bool;
				cmdbars(40).Enabled = !bool;
				cmdbars(40).Visible = !bool;
			}
		}
		
		function MyDoHandSign() {
			try {
				NTKO.DoHandSign2("刘翔","{BFA80B14-0000-0000-444A-9EEE00000061}");
			} catch (e) {
				alert(e);
			}
		}
		//接受所有修订
		function acceptAllRevisions(){
			NTKO.ActiveDocument.AcceptAllRevisions();
		}
		//拒绝所有修订
		function rejectAllRevisions(){
			NTKO.ActiveDocument.RejectAllRevisions ();
		}
		
		//用来显示文档中的签名印章信息
		function MyCheckSign() {
			var result = NTKO.DoCheckSign(true,"SignKey4456");
			alert(trimResult(result));
		}
		
		//隐藏电子印章
		function hideSign(){
			NTKO.SetSignsVisible("*",false,"SignKey4456",0);
		}

		function trimResult(info) {
			var Rstr = "";
			Rstr = info.replace(/#|\s使用者:手写签名/g, '');
			Rstr = Rstr.replace(/印章:/g, '操作:');
			Rstr = Rstr.replace(/使用者/g, '印章隶属');
			return (Rstr);
		}
		
		//从URL增加印章到当前光标所在的段落的指定位置。
		function MyAddSignFromURL() {
			NTKO.AddSignFromURL("刘翔","${ctx}/static/signature/risesoft.esp", 50, 50,"SignKey4456",1,100,0);
		}

		function SetDocUser(userName) {
			with (NTKO.ActiveDocument.Application) {
				UserName = userName;
			}
		}

		function executeAction(openFlag) {//openFlag=2
			try {
				setMarkModify(false);
				var BookMarkName = "RiseOffice_body";
				var bmObj = NTKO.ActiveDocument.BookMarks(BookMarkName);
				var curSel = NTKO.ActiveDocument.Application.Selection;
				try {
					NTKO.ActiveDocument.Shapes(1).Select();//选中图片
					curSel.ShapeRange.Delete();//将图片删除，避免后面复制到图片
				} catch (e) {
				}
				var saverange = bmObj.Range;
				bmObj.Delete();
				saverange.Copy();
				curSel.WholeStory();
				curSel.Delete();
				curSel.HomeKey(6, 0);
				try {
					NTKO.OpenFromURL(
							"${ctx}/ntko/openDocument?processSerialNumber="
									+ processSerialNumber + "&openFlag="
									+ openFlag, false);
					
				} catch (e) {
				}
				saverange.Paste();
				//saveDocument(openFlag);
				if (true) {
					setMarkModify(true);
				} else {
					setMarkModify(false);
				}
			} catch (err) {
				if (true) {
					setMarkModify(true);
				} else {
					setMarkModify(false);
				}
			}
		}

		function undoTaoHong(openFlag) {
			var BookMarkName = "RiseOffice_body";
			if (!NTKO.ActiveDocument.BookMarks.Exists(BookMarkName)) {
				alert("没有发现套红模板或者模板中不存在RiseOffice_body书签。");
				return;
			}
			executeAction(openFlag);
			
		}
		/*
			套红前要接受所有的修订，且不可以打开修订，之后不能修改正文的内容
		*/
		function DoTaoHong(guid) {
			try {
				setMarkModify(false);
				var BookMarkName = "RiseOffice_body";
				var bkCount = NTKO.ActiveDocument.BookMarks.Count;//书签数量
				var curSel = NTKO.ActiveDocument.Application.Selection;//选取word当前所有的文本
				var saverange;
				var bmObj;
				curSel.WholeStory();
				curSel.Cut();
				NTKO.AddTemplateFromURL("${ctx}/ntko/openTaohongTemplate?templateGUID="+ guid);
				if (!NTKO.ActiveDocument.BookMarks.Exists(BookMarkName)) {
					alert("套红模板中不存在RiseOffice_body书签,请联系管理员。");
				} else {
					bmObj = NTKO.ActiveDocument.BookMarks(BookMarkName);
					saverange = bmObj.Range;
					saverange.Paste();
					NTKO.ActiveDocument.Bookmarks.Add(BookMarkName, saverange);
				}
				setFont();
			} catch (err) {
				alert("套红发生异常");
			}
		}

		function openTaoHong() {
			var activitiUser = parent.$("#activitiUser").val();
			var BookMarkName = "RiseOffice_body";
			var bkCount = NTKO.ActiveDocument.BookMarks.Count;//书签数量
			if (bkCount != 0
					&& NTKO.ActiveDocument.BookMarks.Exists(BookMarkName)) {
				alert('当前模板已套红!');
				return false;
			}
			saveDocument(0);//保存套红前的word
			acceptAllRevisions();
			var rval = window.showModalDialog(
					'${ctx}/ntko/openTaoHong?activitiUser=' + activitiUser,
					'null', 'dialogHeight:400px;dialogWidth:500px;center:1');
			if (!rval){
				return;
			}
			DoTaoHong(rval.guid);
			saveDocument(1);//套红后保存有套红模版的word
			
			setMarkModify(false);
			NTKO.SetReadOnly(true,"");
			
		}
		function AddMyMenuItems() {
			//return;
			try {
				NTKO.AddCustomMenuItem('接受所有修订', false, false, 1);
				NTKO.AddCustomMenuItem('拒绝所有修订', false, false, 2);
				NTKO.AddCustomMenuItem('');
				NTKO.AddCustomMenuItem('手写签名', false, false, 3);
				NTKO.AddCustomMenuItem('电子印章', false, false, 5);
				NTKO.AddCustomMenuItem('');
				NTKO.AddCustomMenuItem('签章验证', false, false, 4);
			} catch (err) {
				alert("不能创建新对象：" + err.number + ":" + err.description);
			} finally {
			}
		}
		
		function sysFileCtr(boolvalue){
			//允许或禁止文件－>新建菜单
			NTKO.FileNew = boolvalue;
			//打开菜单
			NTKO.FileOpen  = boolvalue;
			//关闭菜单
			NTKO.FileClose = boolvalue;
			//保存菜单
			NTKO.FileSave = boolvalue;
			//另存为菜单
			NTKO.FileSaveAs = boolvalue;
			//打印菜单
			NTKO.FilePrint = boolvalue;
			//打印预览菜单
			NTKO. FilePrintPreview = boolvalue;
		}
		
</script>
<script language="JScript" for="riseOffice"
		event="AfterPublishAsPDFToURL(ret,code)">
  if(code!=0){
	  
  }else{
    /* if(confirm('是否要保存word原稿？')){
    	SaveAsEFile();
    } */
	  openPDF();
  }
</script>

<script language="JScript" for="riseOffice"
		event="OnCustomMenuCmd2(menuPos,submenuPos,subsubmenuPos,menuCaption,menuID)">	
  switch(menuPos) {
    case 0:
  		switch(menuID) {
      	}
      	break;
    case 1:
      	switch(menuID) {
      	}
      	break;
  }

	</script>

	<script language="JScript" for="riseOffice"
		event="OnFileCommand(cmd,canceled)">	
  if(cmd == 3) {
    saveDocument(0);
    if(!isSaveAs) {
      NTKO.CancelLastCommand = true;
    }
  }
  else if(cmd == 2) {
    window.close();
    NTKO.CancelLastCommand = true;
  }
  else if(cmd == 4) {
    isSaveAs = true;
  }
	</script>

	<script language="JScript" for="riseOffice"
		event="OnCustomMenuCmd(menuIndex,menuCaption,menuID)">
			switch(menuID) {
			    case 1:
			    	acceptAllRevisions();
			      break;
			    case 2:
			    	rejectAllRevisions();
			      break;
			    case 3:
			    	MyDoHandSign();
				  break;
			    case 4:
			    	MyCheckSign();
				  break;
			    case 5:
			    	MyAddSignFromURL();
				  break;
			  }
	</script>

</body>
</html>
