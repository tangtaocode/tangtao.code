﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/static/common/bpmTaglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/head.jsp"%>
<title></title>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=10"/>
<script type="text/javascript" src="${ctx}/static/risesoft/js/addSeal.js"></script>
<script type="text/javascript" src="${ctx}/static/risesoft/js/ntkoObj.js"></script>


</head>
<body topmargin=0 leftmargin=0 rightmargin=0 bottommargin=0
	marginwidth=0 marginheight=0 >

	<table cellspacing="0" cellpadding="0" style="border-color: #D4D0C8;border-bottom-style: groove;" border="0"
		width="100%" bgcolor="#D4D0C8" style="" bordercolorlight="#EEEEEE" bordercolordark="#B1AEA9">
		<tbody>
			<tr>
				<td align="left" style="font: Menu;">
					<input type="button" onclick="showToolbars(this);" class="op1" value="显示工具栏"/>
					<button onclick="openTaoHong();" class="op1" id="openTaoHong">选择套红</button>
					<button onclick="undoTaoHong(2);" class="op1" id="undoTaoHong">撤销红头</button>
					<button onclick="saveEFileAndTopdf(2);" class="op1" id="saveEFileAndTopdf">转PDF并盖章</button>
				</td>
			</tr>
		</tbody>
	</table>
	<object id="riseOffice" classid="clsid:01DFB4B4-0E07-4e3f-8B7A-98FD6BFF153E" codeBase="${ctx }/commons/ocx/ofctnewclsid.cab#version=5,0,2,6" width="100%" height="100%">
<param name="BorderStyle" value="0"/>
<param name="BorderColor" value="14402205"/>
<param name="TitlebarColor" value="42768"/>
<param name="TitlebarTextColor" value="0"/>
<param name="MenubarColor" value="13160660"/>
<param name="Caption" value="欢迎使用！"/>
<param name="Titlebar" value="0"/>
<param name="MaxUploadSize" value="8000000"/>
<param name="CustomMenuCaption" value="辅助选项"/>
<param name="MakerCaption" value="北京有生博大软件技术有限公司"/>
<param name="MakerKey" value="7CAA90E9B33637E9A390DDD12B9149C3FBC57687"/>
<param name="ProductCaption" value="深圳市水务局"/>
<param name="ProductKey" value="B033D5F2FEE8CF084E41AA2CB582DBC60F5F0BDB"/>
<span style="color:red">不能装载文档控件。请在检查浏览器的选项中检查浏览器的安全设置。</span>
</object>
<script language="javascript">
var printType = "${printType}";
var processInstanceId = "${processInstanceId}";
var processSerialNumber = "${processSerialNumber}";
var SPinstanceId = "${SPinstanceId}";
var declaresn = "${declaresn}";
var taskId = "${taskId}";
var jsonObj= '${map}';
jsonObj = JSON.parse(jsonObj);
var NTKO_OBJ = document.getElementById("riseOffice");
var openWordOrPDF = "${openWordOrPDF}";
var filePath = "${filePath}";
var slTemplate="${ctx}/static/template/审批受理单.doc";
var bjTemplate="${ctx}/static/template/行政审批与服务事项办结记录.doc";
$(function(){
	if(openWordOrPDF=='pdf'){
			openPDF();
	}else{
			initDocument();
	}
})

</script>

	<script language="javascript" for="riseOffice" event="OnDocumentOpened(fileName, doc)">		
		var props = "";
		for(var p in jsonObj){
			if(typeof(jsonObj[p])!="function"){     
				if(jsonObj[p]!=null&&jsonObj[p].toString().length>0){
					NTKO_OBJ.SetBookmarkValue(p,jsonObj[p]);
				}
			}     
		}  
	</script>
	<script type="text/javascript">
	//保存word
	function saveDocument(istaohong) {
		if(istaohong==3){
			istaohong=0;
		}
		var ntko = document.getElementById("riseOffice");
		var docType = ntko.DocType; //6= WPS Doc；
		var info="";
		var xbqids = $("#xbqids").val();
		var xbzids = $("#xbzids").val();
		try {
			if(docType==6){
				info = ntko.SaveToURL("${ctx}/bjgz/saveDocument?instanceId="+SPinstanceId+"&xbqids="+xbqids+"&xbzids="+xbzids+"&istaohong="+istaohong+"&printType="+printType,
						"currentDoc", "", "huifu/FileName", 0);
			}else{
				info = ntko.SaveToURL("${ctx}/bjgz/saveDocument?instanceId="+SPinstanceId+"&xbqids="+xbqids+"&xbzids="+xbzids+"&istaohong="+istaohong+"&printType="+printType,
						"currentDoc", "", "huifu/FileName", 0);
			}
			
			if (info == "true") {
				if(istaohong==2){
					alert("保存文件成功！");
				}
				ntko.ActiveDocument.Saved = true;
			} else {
				alert("保存文件错误，请联系管理员！");
				return;
			}
		} catch (e) {
			alert('无法保存文件！');
		}
	}
	
	function setFont() {
		var fonts = NTKO_OBJ.ActiveDocument.Application.FontNames;
		var flag = 0;
		for ( var i = 1; i <= fonts.Count; i++) {
			if (fonts(i) == '华文中宋') {
				flag = 1;
				break;
			}
		}
		if (flag == 0) {
			NTKO_OBJ.ActiveDocument.Content.Font.Name = '宋体';
		}
	}
	
	//套红前要接受所有的修订，且不可以打开修订，之后不能修改正文的内容
	function DoTaoHong(guid) {
		try {
			setMarkModify(false);
			var BookMarkName = "RiseOffice_body";
			var bkCount = NTKO_OBJ.ActiveDocument.BookMarks.Count;//书签数量
			var curSel = NTKO_OBJ.ActiveDocument.Application.Selection;//选取word当前所有的文本
			var saverange;
			var bmObj;
			curSel.WholeStory();
			curSel.Cut();
			NTKO_OBJ.AddTemplateFromURL("${ctx}/ntko/openTaohongTemplate?templateGUID="+ guid);
			if (!NTKO_OBJ.ActiveDocument.BookMarks.Exists(BookMarkName)) {
				alert("套红模板中不存在RiseOffice_body书签,请联系管理员。");
			} else {
				bmObj = NTKO_OBJ.ActiveDocument.BookMarks(BookMarkName);
				saverange = bmObj.Range;
				saverange.Paste();
				NTKO_OBJ.ActiveDocument.Bookmarks.Add(BookMarkName, saverange);
			}
			setFont();
		} catch (err) {
			alert("套红发生异常");
		}
	}
	
	//打开或者关闭修订模式
	function trackRevisions(bool) {
		NTKO_OBJ.ActiveDocument.TrackRevisions = bool;
	}
	//允许或禁止显示修订工具栏和工具菜单（保护修订,用户不能更改当前修订状态）
	function enableReviewBar(bool) {
		try {
			NTKO_OBJ.ActiveDocument.CommandBars("Reviewing").Enabled = bool;
			NTKO_OBJ.ActiveDocument.CommandBars("Track Changes").Enabled = bool;
			NTKO_OBJ.IsShowToolMenu = bool;
		} catch (e) {
			var doc = NTKO_OBJ.ActiveDocument;
			var app = doc.Application;
			var doctype = NTKO_OBJ.DocType;
			if (6 != doctype||1!=doctype) {
				return;
			}
			var cmdbars = app.CommandBars;
			NTKO_OBJ.IsShowToolMenu = !bool;
			doc.TrackRevisions = bool;
			cmdbars("Reviewing").Enabled = !bool;
			cmdbars("Reviewing").Visible = !bool;
			cmdbars(40).Enabled = !bool;
			cmdbars(40).Visible = !bool;
		}
	}
	
	function acceptAllRevisions() {
		NTKO_OBJ.ActiveDocument.AcceptAllRevisions();
	}

	function rejectAllRevisions() {
		NTKO_OBJ.ActiveDocument.RejectAllRevisions();
	}
	
	//进入或退出强制痕迹保留状态:就是打开或者关闭修订模式以及wps本身可操作的工具栏
	function setMarkModify(bool) {
		trackRevisions(bool);
		enableReviewBar(bool);
	}
	
	//选择套红
	function openTaoHong() {
		var activitiUser = parent.$("#activitiUser").val();
		var BookMarkName = "RiseOffice_body";
		var bkCount = NTKO_OBJ.ActiveDocument.BookMarks.Count;//书签数量
		if (bkCount != 0&& NTKO_OBJ.ActiveDocument.BookMarks.Exists(BookMarkName)) {
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
		NTKO_OBJ.SetReadOnly(false,"");
	}
	
	function executeAction(openFlag) {//openFlag=2
		try {
			setMarkModify(false);
			var BookMarkName = "RiseOffice_body";
			var bmObj = NTKO_OBJ.ActiveDocument.BookMarks(BookMarkName);
			var curSel = NTKO_OBJ.ActiveDocument.Application.Selection;
			try {
				NTKO_OBJ.ActiveDocument.Shapes(1).Select();//选中图片
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
				if(printType=="shouLiDan"){
					NTKO_OBJ.OpenFromURL("${ctx}/static/template/审批受理单.doc", false);
				}
				if(printType=="banJieDan"){
					NTKO_OBJ.OpenFromURL("${ctx}/static/template/罗湖区行政审批与服务事项办结记录.doc", false);
				}
			} catch (e) {
			}
			saverange.Paste();
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
	
	//撤销套红
	function undoTaoHong(openFlag) {
		var BookMarkName = "RiseOffice_body";
		if (!NTKO_OBJ.ActiveDocument.BookMarks.Exists(BookMarkName)) {
			alert("没有发现套红模板或者模板中不存在RiseOffice_body书签。");
			return;
		}
		executeAction(openFlag);
	}
	
	//转pdf
	function saveEFileAndTopdf(saveflag) {
		//AddSeal("","sealdata");
		var isPDFCreatorInstalled=NTKO_OBJ.IsPDFCreatorInstalled();
		if(isPDFCreatorInstalled){
			wordTopdf();
		}else{
			alert("本机没有安装PDFCreator(1.2.3版本)转换器！");
			return false;
		}
	}

	//转pdf
	function wordTopdf() {
		try {
			NTKO_OBJ.PublishAsPDFToURL("${ctx}/sp/pdfFile/saveAsPDFFile?SPinstanceId="+SPinstanceId+"&printType="+printType+"&declaresn="+declaresn,"currentDoc");
			//NTKO_OBJ.SaveAsPDFFile("E:/pdfFile/sl"+SPinstanceId,false,false,false,false);
		} catch (e) {
			alert('转换失败！');
		}
	}

	//打开pdf
	function openPDF(){
		var itembox = parent.$("#itembox").val();
		window.location.href="${ctx}/sp/pdfFile/openPDF?SPinstanceId="+SPinstanceId+"&processInstanceId="+ processInstanceId +"&taskId="+taskId+"&printType="+printType+"&processSerialNumber="+processSerialNumber+"&declaresn="+declaresn;
	}
	
	
	
	//从URL增加印章到当前光标所在的段落的指定位置。
	function MyAddSignFromURL() {
		NTKO_OBJ.AddSignFromURL("刘翔","${ctx}/static/signature/risesoft.esp", 50, 50,"SignKey4456",1,100,0);
	}
	
	//获取pdf文件
	function getPdfFile(SPinstanceId){
		$.ajax({
			async:false,
			cache: false,
			type : "POST",
			url : ctx + "/sp/pdfFile/getPdfFile",
			data : {
				SPinstanceId : SPinstanceId
			},
			success : function(data) {
				if(data.success){
					$("#content").val(data.filePath);
					//pdfFileSign();
				}
			}
		});
	}
	
	
	</script>
	<script language="JScript" for="riseOffice" event="AfterPublishAsPDFToURL(ret,code)">
		  if(code!=0){
			  
		  }else{
			  //getPdfFile(SPinstanceId);
			  //pdfFileSign();
			  openPDF();
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