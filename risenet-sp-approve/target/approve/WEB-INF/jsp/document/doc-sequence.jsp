<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<title>填写意见</title>
<link type="text/css" rel="stylesheet" href="${ctx}/static/jquery/jquery-ui/css/redmond/jquery-ui-1.10.3.custom.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/static/jquery/layout/layout-default-latest.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/static/jquery/jtable/themes/lightcolor/blue/jtable.css" />

<link type="text/css" rel="stylesheet" href="${ctx}/static/risesoft/themes/blue/css/mstyle.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/static/risesoft/themes/blue/css/listyle.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/static/risesoft/themes/blue/css/form.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/static/risesoft/themes/blue/css/style.css" />
<link href="${ctx }/static/jquery/jui/extends/timepicker/jquery-ui-timepicker-addon.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/static/jquery/jquery-ui/js/jquery-ui-1.10.3.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/layout/jquery.layout-latest.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/layout/jquery.layout.slideOffscreen.min-1.1.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/databind/DataBind.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/tools/jquery.json-2.4.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/form/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/editable/jquery.editable.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/jtable/jquery.jtable.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/layer/layer.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/layer/extend/layer.ext.js"></script>
<script src="${ctx }/static/jquery/jui/extends/timepicker/jquery-ui-timepicker-addon.js" type="text/javascript"></script>
<script src="${ctx }/static/jquery/jui/extends/i18n/jquery-ui-date_time-picker-zh-CN.js" type="text/javascript"></script>
<script type="text/javascript">

$(document).ready(function() {
	$('#submit1').click(function(){
		var result=$('#sequence').val();
		parent.setSequence(result);
		//parent.result = $('#sequence').val();
		closeLayer();
	});
	
	$('#cancel1').click(function(){
		closeLayer();
	});
});

function closeLayer(){
	closeCurWindow(parent.frameID,'close');
	//var index = parent.layer.getFrameIndex(window.name);
	//parent.layer.close(index);
}
</script>
</head>
<body>
<div align="center">
<table border="0px">
	<tr>
		<td style="font-size:12px;"><font color="red">编号：</font></td>
	</tr>
	<tr>
		<td>
			<input type="text" id="sequence" name="sequence" value="${sequence}" style="text-align:center;border-top:none;border-left:none;border-right:none;border-bottom: 1px solid black;">
		</td>
	</tr>
	<tr>
		<td align="center">
			<input type="button" id="submit1" name="submit1" style="border:1px dotted black;font:menu;background:white" value="确定">&nbsp;&nbsp;&nbsp;
			<input type="button" id="cancel1" name="cancel1" style="border:1px dotted black;font:menu;background:white" value="取消" onclick="opener.activeButton();window.close()" type="button">
		</td>
	</tr>
</table>
</div>
</body>
</html>