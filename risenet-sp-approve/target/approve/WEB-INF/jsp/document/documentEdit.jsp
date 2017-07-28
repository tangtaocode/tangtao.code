<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv=Content-Type content="text/html;charset=utf-8">
<meta http-equiv=X-UA-Compatible content="IE=edge">
<META content="MSHTML 6.00.2900.6550" name=GENERATOR>
<title>工作流中间件-文档编辑</title>
<%@ include file="/static/common/bpmTaglib.jsp"%>
<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<%@ include file="/static/common/clientactivex.jsp"%>
<link type="text/css" rel="stylesheet" href="${ctx}/static/risesoft/themes/blue/css/mstyle.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/static/risesoft/themes/blue/css/listyle.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/static/risesoft/themes/blue/css/form.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/static/risesoft/themes/blue/css/style.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/static/risesoft/css/easyui-ext.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/static/risesoft/css/code39.css" />

<script type="text/javascript" src="${ctx}/static/js/knockout/knockout-3.2.0.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/form/jquery.form.js"></script>

<script type="text/javascript" src="${ctx}/static/jquery/qtip2/jquery.qtip.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/tools/jquery.outerhtml.js"></script>
<script type="text/javascript" src="${ctx}/static/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/static/risesoft/js/documentEdit.js"></script>
<script type="text/javascript" src="${ctx}/static/risesoft/js/documentEdit4ButtonOperation.js"></script>

<style>
body {
	font-family: "宋体", "Helvetica", "Arial", "Verdana", "sans-serif";
	font-size: 12px;
	width: 100%;
	height: 100%;
	/* padding: 0;
	margin: 0;  */
	overflow: hidden !important;
}

.tabs-header{
	//position:relative;
	//position:fixed;
	//width:500px;
	position:absolute;
	z-index:999
}
.tabs-panels{
	position:relative;
	top:30px;
	z-index:99
}
</style>

<script type="text/javascript">
var str = "${str}";
var formModel = {};
var grid = $("#attachmentTableContainer");
var formUrls = "${formUrls}";
var formNames = "${formNames}";
var tableNames = "${tableNames}";
var formIds = "${formIds}";
var formEdittypes = "${formEdittypes}";
var showOtherFlag = "${showOtherFlag}";
var processDefinitionId = "${processDefinitionId}";
var activitiUser = "${activitiUser}";
var processDefinitionKey = "${processDefinitionKey}";
var itembox = "${itembox}";
var processSerialNumber="${processSerialNumber}";
var processInstanceId="${processInstanceId}";
var isSignOpinion="${isSignOpinion}";
var taskDefKey="${taskDefKey}";
var taskId="${taskId}";
var ctx="${ctx}";
var refuseClaimTip="${refuseClaimTip}";
var SPinstanceId="${SPinstanceId}";//审批业务数据Id
var status="${status}";
var doctypeguid = "${doctypeguid}";//证件类型guid，用于出证办结
var slurl="";
//初始化estimate.js 中的tourl，documenttitle两个变量，为页面跳转做准备
function initTourl(){
	tourl=slurl;
	documenttitle=$('#documentTitle').val();
}
</script>
</head>
<body>

	<div class="easyui-layout" data-options="fit:true,border:true">
		<div data-options="region:'center',title:'',border:true">
			<div id="easyui-tabs" class="easyui-tabs" style="width:100%" data-options="tools:'#tab-tools',toolPosition:'right',tabPosition:'top'">
				<form id="documentForm" action="" method="post">
					
					<input type="hidden" name="pageconf" id="pageconf" value="${pageConf }" />
					<input type="hidden" name="processDefinitionKey" id="processDefinitionKey" value="${processDefinitionKey}" />
					<input type="hidden" name="processDefinitionId" id="processDefinitionId" value="${processDefinitionId}" />
					<input type="hidden" name="itembox" id="itembox" value="${itembox}" />
					<input type="hidden" name="processSerialNumber" id="processSerialNumber" value="${processSerialNumber}" />
					<input type="hidden" name="taskId" id="taskId" value="${taskId}" />
					<input type="hidden" name="formIds" id="formIds" value="${formIds}" />
					<input type="hidden" name="processInstanceId" id="processInstanceId" value="${processInstanceId}" />
					<input type="hidden" name="documentTitle" id="documentTitle" value="" />
					<input type="hidden" name="activitiUser" id="activitiUser" value="${activitiUser}" /><!-- 由于存在一人多岗，需要保存当前办理流程的人员guid和部门guid -->
					<input type="hidden" name="fromPage" id="fromPage" value="" />
				</form>
			</div>
			<div id="tab-tools" class="tabs-tool">
				<table>
					<tr>
						${button}
					</tr>
				</table>
			</div>
		</div>
		<!--<div id="dialog-form">
			<form id="selectUserForm" action="${ctx}/document/forwarding">
				<table cellspacing="4" cellpadding="4" align="center" border="0">
					<tr>
						<td>任务名称：</td>
						<td><select name="routeToTasks" id="routeToTasks" style="width: 300px; height: 26px">
								<option value=" ">请选择</option>
							</select></td>
					</tr>
				</table>
			</form>
		</div>
		<div id="dialog-choiceDept">
			<table id="tableDept"></table>
		</div>
		<div id="dialog-rejectReason">
		</div>
		<div id="dialog-refuseClaimTip">
			<font color="red" size="4">${refuseClaimTip}</font>
		</div>
	</div>-->
	<br>
	<div id="dialog-confirmdelete">
		<p>您未签写个人意见或者领导意见！确定发送？</p>
	</div>
</body>
	<script type="text/javascript">	
		var width = $(window).width()-2;
		$('#easyui-tabs').css({
			"width" : (width) + "px"
		});
	</script>	
</html>