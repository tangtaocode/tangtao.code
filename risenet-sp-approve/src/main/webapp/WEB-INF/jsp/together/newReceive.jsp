<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv=Content-Type content="text/html;charset=utf-8">
<meta http-equiv=X-UA-Compatible content="IE=edge">
<title>新建收件</title>
<%@ include file="/static/common/bpmTaglib.jsp"%>
<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<link type="text/css" rel="stylesheet" href="${ctx}/static/risesoft/themes/blue/css/mstyle.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/static/risesoft/themes/blue/css/listyle.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/static/risesoft/themes/blue/css/form.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/static/risesoft/themes/blue/css/style.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/static/risesoft/css/easyui-ext.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/static/risesoft/css/code39.css" />

<%-- <script type="text/javascript" src="${ctx}/static/js/knockout/knockout-3.2.0.js"></script> --%>
<script type="text/javascript" src="${ctx}/static/jquery/form/jquery.form.js"></script>

<script type="text/javascript" src="${ctx}/static/jquery/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/qtip2/jquery.qtip.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/tools/jquery.outerhtml.js"></script>
<script type="text/javascript" src="${ctx}/static/risesoft/js/together/together.js"></script>

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

</style>
<script type="text/javascript">
	var ctx="${ctx}";
	var instanceGuid = "${instanceGuid}";//审批主表guid
	var guid = "${guid}";//事项guid
	var code = "${code}";
	var processDefinitionKey = "${processDefinitionKey}";
	var processSerialNumber = "${processSerialNumber}";
	var tenantId = "${tenantId}";
	var documentTitle = "";
	var approvePlace = "${approvePlace}";
	var status = "${status}";
	var method="${method}";
	function submitForm(obj){
		window.frames[1].moren();
		window.frames[1].qing_repeat();
		window.frames[0].document.getElementById('handleStatus').value = obj;
		window.frames[0].document.getElementById('ytjids').value = window.frames[1].document.getElementById('ytjs').value;
		window.frames[0].document.getElementById('xbqids').value = window.frames[1].document.getElementById('xbqs').value;
		window.frames[0].document.getElementById('xbzids').value = window.frames[1].document.getElementById('xbzs').value;
		window.frames[0].saveForm();
 	}
	
	$(function(){
		if(method=='end'){
			$("#savebtn").hide();
			$("#sendbtn").hide();
		}else{
			if(approvePlace=='1'){
				$("#savebtn").show();
				$("#sendbtn").hide();
				if(status!='0'){
					$("#button01").hide();
					$("#button02").show();
				}
			}else{
				$("#sendbtn").show();
				$("#savebtn").hide();
			}
		}
	})
</script>

</head>
<body>
	<div class="easyui-layout" data-options="fit:true,border:true">
		<div data-options="region:'center',title:'',border:true" style="width:99%" scrolling="no">
			<div id="easyui-tabs" class="easyui-tabs" style="width:100%" scrolling="yes" data-options="tools:'#tab-tools',toolPosition:'right',tabPosition:'top'" >
				<form id="documentForm" action="" method="post">
					<input type="hidden" name="processDefinitionKey" id="processDefinitionKey" value="${processDefinitionKey}" />
					<input type="hidden" name="processSerialNumber" id="processSerialNumber" value="${processSerialNumber}" />
					<input type="hidden" name="documentTitle" id="documentTitle" value="" />
					<input type="hidden" name="activitiUser" id="activitiUser" value="${activitiUser}" /><!-- 由于存在一人多岗，需要保存当前办理流程的人员guid和部门guid -->
				</form>
			</div>
			<div id="tab-tools" class="tabs-tool">
				<table>
					<tr>
						<td id="sendbtn">
							<a href="javascript:void(0);" class="easyui-menubutton" name="easyuiLinkButton" data-options="menu:'#mm',iconCls:'icon-redo',plain:true" id="button">发送</a>
							<div id="mm" style="width:150px;">
								<div data-options="iconCls:'icon-redo'" name="easyuiLinkButton" id="waidanweibanli">外单位办理</div>
							</div>
						</td>
						<td id="savebtn"  style="display:none">
							<a id="button01" data-options="iconCls:'icon-save',plain:true" name="easyuiLinkButton"
							class="easyui-linkbutton l-btn l-btn-small l-btn-plain" href="javascript:saveReceive();" group="">保存</a>
							<a id="button02" style="display:none" data-options="iconCls:'icon-ok',plain:true" name="easyuiLinkButton"
							class="easyui-linkbutton l-btn l-btn-small l-btn-plain" href="javascript:done();" group="">办结</a>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">	
	var width = $(window).width()-2;
	var height = $(window).height()-5;
	$('#easyui-tabs').css({
		"width" : (width) + "px",
		"height" :(height) + "px"
	});
</script>
</html>