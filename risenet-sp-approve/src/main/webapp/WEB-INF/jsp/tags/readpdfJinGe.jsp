<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/head.jsp"%>
<title></title>
<link type="text/css" rel="stylesheet"
	href="${ctx}/static/jquery/jquery-ui/css/redmond/jquery-ui-1.10.3.custom.css" />
<link type="text/css" rel="stylesheet"
	href="${ctx}/static/jquery/layout/layout-default-latest.css" />
<link type="text/css" rel="stylesheet"
	href="${ctx}/static/jquery/jtable/themes/lightcolor/blue/jtable.css" />

<link type="text/css" rel="stylesheet"
	href="${ctx}/static/risesoft/themes/blue/css/mstyle.css" />
<link type="text/css" rel="stylesheet"
	href="${ctx}/static/risesoft/themes/blue/css/listyle.css" />
<link type="text/css" rel="stylesheet"
	href="${ctx}/static/risesoft/themes/blue/css/form.css" />
<link type="text/css" rel="stylesheet"
	href="${ctx}/static/risesoft/themes/blue/css/style.css" />
<link
	href="${ctx}/static/jquery/jui/extends/timepicker/jquery-ui-timepicker-addon.css"
	type="text/css" rel="stylesheet" />
<script type="text/javascript"
	src="${ctx}/static/js/pdf/pdfobject.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<script type="text/javascript">
var iWebPDF = document.getElementById('iWebPDF2015');
var recordId='${recordId}';
var fileType='${fileType}';
	function load(){
		if(recordId!='null'&&recordId!=''){
			WebOpen();
		}
	}
	function WebOpen()
	{   
		alert("用iWebPDF2015插件打开数据库中的pdf");
		iWebPDF2015.COMAddins("KingGrid.MsgServer2000").Object.WebUrl ='${ctx}/iWebOfficeServlet';
		alert("1");
		var tempFile = iWebPDF2015.COMAddins("KingGrid.MsgServer2000").Object.CreateTempFileName();
		iWebPDF2015.COMAddins("KingGrid.MsgServer2000").Object.SetMsgByName("DBSTEP","DBSTEP");
		iWebPDF2015.COMAddins("KingGrid.MsgServer2000").Object.SetMsgByName("OPTION","LOADFILE");
		iWebPDF2015.COMAddins("KingGrid.MsgServer2000").Object.SetMsgByName("RECORDID",recordId);
		iWebPDF2015.COMAddins("KingGrid.MsgServer2000").Object.SetMsgByName("FILETYPE",fileType);
		iWebPDF2015.COMAddins("KingGrid.MsgServer2000").Object.SetMsgByName("mOperation","loadPDF");
		alert("2");
		var bool=iWebPDF2015.COMAddins("KingGrid.MsgServer2000").Object.PostDBPacket(false);
		alert("bool="+bool);
		alert("2.1");
		if ( iWebPDF2015.COMAddins("KingGrid.MsgServer2000").Object.PostDBPacket(false))
		{ 
			alert("3");
			iWebPDF2015.COMAddins("KingGrid.MsgServer2000").Object.MsgFileSave(tempFile);
			alert("4");
			iWebPDF2015.Documents.Open(tempFile);
			iWebPDF2015.Documents.ActiveDocument.Views.ActiveView.Zoom = 100;//设置缩放比例为100%大小
			addState("打开成功");
		}
		else
		{
			alert("打开失败");
			addState("打开失败");
		}
	}
</script>
<body onload='load();'>
	<object classid="clsid:7017318C-BC50-4DAF-9E4A-10AC8364C315"
		codebase="${ctx}/static/tags/ocx/iWebPDF2015.cab#version=1,0,3,1080" id=iWebPDF2015 height=0
		width=100%></object>
</body>
</html>
