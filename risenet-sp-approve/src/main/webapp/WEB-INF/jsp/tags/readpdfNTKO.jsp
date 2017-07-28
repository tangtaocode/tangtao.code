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
	window.onload = function() {
		var success = new PDFObject({
			url : "${ctx}/static/tags/ocx/123456.pdf"
		}).embed();
	};
</script>
<body>
	<p>
		It appears you don't have Adobe Reader or PDF support in this web
		browser. <a href="${ctx}/static/tags/ocx/123456.pdf">Click here to
			download the PDF</a>
	</p>
</body>
</body>
</html>
