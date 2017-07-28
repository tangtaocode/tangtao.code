<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<title>pdf在线管理</title>
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<link type="text/css" rel="stylesheet" href="${ctx}/static/tags/css/pdf.css" />
</head>
<body>
	<object id="iWebPDF"  width="100%" height="100%" classid="clsid:39E08D82-C8AC-4934-BE07-F6E816FD47A1" 
	codebase="${ctx}/static/tags/ocx/iWebPDF.cab#version=8,2,0,1004" VIEWASTEXT></object>
	<script language="javascript">
		var iWebPDF=document.getElementById('iWebPDF');
		$(function() {
			load();
		});
		
		function load() {
			try {
				iWebPDF.WebUrl = '${ctx}/IWebOfficeServletPDF';
				iWebPDF.RecordID = '${recordId}';
				iWebPDF.FileType = '.pdf';
				
				/*iWebPDF.SaveRight = 0;//允许保存文档
				iWebPDF.ShowMenus = 0;//菜单栏可见 
				iWebPDF.ShowSides = 0;//侧边栏可见
				iWebPDF.ShowState = 0;//状态栏可见
				iWebPDF.ShowMarks = 0;//书签树可见
				iWebPDF.ShowSigns = 0;//签章工具栏可见
				iWebPDF.ShowTools = 0;//标准工具栏可见
				*/				
				iWebPDF.WebOpen();
				iWebPDF.Zoom = 100;//将当前文档的显示比例设为 100%
			} catch (e) {
				alert(e);
			}
		}
	</script>
</body>
</html>