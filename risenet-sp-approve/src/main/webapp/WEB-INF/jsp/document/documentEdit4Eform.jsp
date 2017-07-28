<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/bpmTaglib.jsp"%>
<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<meta http-equiv=Content-Type content="text/html;charset=utf-8">
<meta http-equiv=X-UA-Compatible content="IE=10">
<META content="MSHTML 6.00.2900.6550" name=GENERATOR>
<title>水务局审批</title>

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
<script type="text/javascript" src="${ctx}/static/risesoft/js/documentEditEform.js"></script>
<script type="text/javascript" src="${ctx}/static/risesoft/js/documentEditEform4ButtonOperation.js"></script>
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
 #users{
 colors:red;
}

/* .tabs-header{
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
} */
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
var SPinstanceId="${SPinstanceId}";//审批业务数据主键Id
var status="${status}";
var doctypeguid = "${doctypeguid}";//证件类型guid，用于出证办结
var print = "${print}";//打印按钮控制，print为“banJieDan”，打印办结单，print为“shouLiDan”，打印受理单。

var bqbzStatus = "${bqbzStatus}";//补齐补正状态
var xieBan = "${xieBan}";//判断是否是协办，true为是，其他为不是。
var zhuBan = "${zhuBan}";//判断是否是主办，true为是，其他为不是。
var role = '';//角色，用于意见框的角色勾选，如果为勾选角色，为勾选批准状态，不能送窗口人员办结。
var route = '';//批准、不批准状态
var taskDefKeys = "${taskDefKeys}";//当前节点的目标任务路由
var taskDefNames = "${taskDefNames}";//当前节点的目标任务名称
//初始化estimate.js 中的tourl，documenttitle两个变量，为页面跳转做准备
var slurl="";
function initTourl(){
	tourl=slurl;
	documenttitle=$('#documentTitle').val();
}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true,border:true">
		<div data-options="region:'center',title:'',border:true" style="width:99%" scrolling="no">
			<div id="easyui-tabs" class="easyui-tabs" style="width:100%" scrolling="yes" data-options="tools:'#tab-tools',toolPosition:'right',tabPosition:'top'">
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
					<input type="hidden" name="zhuBan" id="zhuBan" value="${zhuBan}" />
					<input type="hidden" name="xieBan" id="xieBan" value="${xieBan}" />
					<input type="hidden" name="fromPage" id="fromPage" value="" />
					
					<!--电子签章字段-->
					<input type="hidden" id="sealdata" name="sealdata">
  			      	<input type="hidden" id="position" name="position">
  			      	<input type="hidden" id="number" name="number">   
  			      	<input type="hidden" id="auto" name="auto"> 
  			      	<!--电子签章字段-->
				</form>
			</div>
			<div id="tab-tools" class="tabs-tool">
				<table>
					<tr>
						${button}
						<td id="sendbtn">
							<a href="javascript:void(0);" class="easyui-menubutton" name="easyuiLinkButton" data-options="menu:'#mm1',iconCls:'icon-print',plain:true" id="button">打印</a>
							<div id="mm1">
								<c:if test="${print=='shouLiDan'||print==''||print==null}">
									<div  data-options="iconCls:'icon-print',plain:true" name="printShouLiDan" id="printShouLiDan" onclick="printShouLiDan();">打印受理单</div>
								</c:if>
								<c:if test="${print == 'banJieDan'}">
									<div  data-options="iconCls:'icon-print',plain:true" name="printBanJieDan" id="printBanJieDan" onclick="printBanJieDan();">打印办结单</div>
								</c:if>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div id="dialog-form">
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
	</div>
	<br>
	<div id="dialog-confirmdelete">
		<p>您未签写个人意见或者领导意见！确定发送？</p>
	</div>
	<div id="dialog-users">
		<p  align="center" ><span >以下协办人员还未办理完成：</span></p>
		<p  align="center" colors="red"><span id="users"></span></p>
		<p  align="center"><span >确定继续办理？</span></p>	</div>
</body>
	<script type="text/javascript">	
		var width = $(window).width()-2;
		var height = $(window).height()-5;
		$('#easyui-tabs').css({
			"width" : (width) + "px",
			"height" :(height) + "px"
		});
		
		//打印受理单
		function printShouLiDan() {
			var processInstanceId = $('#processInstanceId').val();
			openCurWindow({
				id : frameID,
		        src : ctx+'/sp/pdfFile/banliDanForm?SPinstanceId='+SPinstanceId+'&processInstanceId='+processInstanceId+"&processSerialNumber="+processSerialNumber+"&taskId="+taskId,
				destroy : true,
				title : '打印受理单',
				width : ($(window).width()-10)+'px',
				height : ($(window).height() - 10) + 'px',
				modal : true
			});
		}
		
		//打印办结单
		function printBanJieDan() {
			var processInstanceId = $('#processInstanceId').val();
			openCurWindow({
				id : frameID,
		        src : ctx+'/sp/pdfFile/banJieDanForm?SPinstanceId='+SPinstanceId+'&processInstanceId='+processInstanceId+"&processSerialNumber="+processSerialNumber+"&taskId="+taskId,
				destroy : true,
				title : '打印办结单',
				width : ($(window).width()-10)+'px',
				height : ($(window).height() - 10) + 'px',
				modal : true
			});
		}
	</script>	
</html>