<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<title>工作流中间件-表单绑定</title>
<link type="text/css" rel="stylesheet" href="${ctx}/static/jquery/jquery-ui/css/redmond/jquery-ui-1.10.3.custom.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/static/jquery/layout/layout-default-latest.css" />

<link type="text/css" rel="stylesheet" href="${ctx}/static/risesoft/themes/blue/css/mstyle.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/static/risesoft/themes/blue/css/listyle.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/static/risesoft/themes/blue/css/form.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/static/risesoft/themes/blue/css/style.css" />

<style>
.table_v {
	border: 2px solid #9DC3DA;
	width: 90%;
	margin-top: 5px;
	margin-bottom: 5px;
	height: 90%;
}

.table_v tr {
	height: 30px;
}

.table_v td {
	border-right: 1px #9DC3DA solid;
	padding: 7px;
	border-bottom: 1px #9DC3DA solid;
}

.table_v input select {
	height: 18px;
}
</style>

<script type="text/javascript" src="${ctx}/static/jquery/jquery-ui/js/jquery-ui-1.10.3.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/databind/DataBind.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/tools/jquery.json-2.4.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/form/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/chained/jquery.chained.remote.min.js"></script>
<script type="text/javascript" src="${ctx}/static/risesoft/js/myValidate.js"></script>

</head>
<body>
	<form:form id="myForm" action="${ctx}/form/bind/save" method="POST" commandName="formTaskBind">
			<table cellspacing="0" cellpadding="0" align="center" border="0" class="table_v">
				<!-- <tr>
					<td align="center" colspan="4" style="font-size: 26px; font-weight: bold; padding: 20px; border-right: 0px;">表单基本信息</td>
				</tr> -->
				<tr>
					<td width="20%" align="center">当前流程</td>
					<td align="left">
						<input type="hidden" id="processDefinitionId" name="processDefinitionId" value="${processDefinitionId}"/>
						${procDefName}
					</td>
				</tr>
				<c:if test="${taskDefKey!=''}">
				<tr>
					<td width="20%" align="center">当前任务</td>
					<td align="left">
						<input type="hidden" id="taskDefKey" name="taskDefKey" value="${taskDefKey}"/>
						${taskDefName}
					</td>
				</tr>
				</c:if>
				<!-- <tr>
					<td width="20%" align="center">业务名称</td>
					<td align="left"><select id="category" name="category">
							<option value="">--</option>
						</select></td>
				</tr> -->
				<tr>
					<td width="20%" align="center">选择表单</td>
					<td align="left"><form:select path="formId">
							<form:option value="" label="--" />
							<form:options items="${formMap}" />
						</form:select></td>
				</tr>
				<tr>
					<td width="20%" align="center">显示顺序</td>
					<td align="left">
						<form:input path="tabIndex" />
						<!-- <input type="text" id="tabIndex" name="tabIndex" value=""/> -->
					</td>
				</tr>
				<tr>
					<td width="20%" align="center">选择其它</td>
					<td align="left">
						<form:checkbox path="showFileTab" />附件 &nbsp;&nbsp;
						<form:checkbox path="showDocumentTab" />正文 &nbsp;&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan="4" align="center">
						<button id="button01" name="button01">保存</button>
						<button id="button02" name="button02">取消</button>
						<!-- <input type="button" id="button01" name="button01" value="保存"></input>
						<input type="button" id="button02" name="button02" value="取消"></input> -->
					</td>
				</tr>
			</table>
		<form:hidden path="id" />
	</form:form>
	<div id="dialog-confirm" title="确定复制" style="display: none;">
		<p>是否复制其它绑定表单?</p>
	</div>
</body>
<script type="text/javascript">
	$(document).ready(function() {
		//var frameIndex = parent.layer.getFrameIndex(window.name);
		$('button').click(function(event) {
			switch (this.id) {
			case 'button01':
				if(!tabIndexValidate()){
					return false;
				}
				$("#myForm").ajaxSubmit({
					dataType : 'json',
					success : function(data) {
						if (data.success == true) {
							if(data.otherTaskDef!=""){
								/* var dtd = $.Deferred(); */
								alert("保存成功");
								$( "#dialog-confirm" ).dialog({
									resizable: false,
									height:200,
									modal: true,
									buttons: {
										"复制": function() {
											var formIds="";
											$.each(data.otherTaskDef,function(i,item){
												if(formIds==""){//说明是流程表单
													formIds=item.id;
												}else{//说明是流程节点表单
													formIds=formIds+","+item.id;
												}
											});
											$.ajax({
												type: "POST",
												url: "${ctx}/form/bind/copyForm",
												dataType:'JSON',
												data: {formIds:formIds,processDefinitionId:"${processDefinitionId}"},
												success: function(msg){
													alert("复制成功");
													closeCurWindow(parent.frameID,'close');
													//parent.layer.close(frameIndex);
												},
												error:function(){
													alert("复制失败");
													closeCurWindow(parent.frameID,'close');
													//parent.layer.close(frameIndex);
												}
											});
											//复制表单，如果需要一个一个提示，可以使用该方法
											/* $.when(
												copyForm(dtd,data.otherTaskDef)
											).then(function(){
												parent.layer.close(frameIndex);
											}); */
										},
										"不复制": function() {
											closeCurWindow(parent.frameID,'close');
											//parent.layer.close(frameIndex);
										}
									}
								});
							}else{
								parent.selectproc();
								closeCurWindow(parent.frameID,'close');
								//parent.layer.close(frameIndex);
							}
						} else {
							alert(data.msg);
						}
					}
				});
				break;
			case 'button02':
				closeCurWindow(parent.frameID,'close');
				//parent.layer.close(frameIndex);
				break;
			}
			return false;//避免提交两次。
		});
		
		$('#tabIndex').blur(function(){
			tabIndexValidate();
		});

	});
	
	//验证tabIndex输入的是不是数字
	function tabIndexValidate(){
		if(!isNumber($('#tabIndex').val())){
			alert("顺序值必须是数字");
			return false;
		}else{
			return true;
		}
	}
	
	//复制表单，如果需要一个一个提示，可以使用该方法，不过尚未完成
	function copyForm(dtd,otherFormData){
		$.each(otherFormData,function(i,item){
			if(item.taskDefName==""){//说明是流程表单
				alert("是否复制流程表单:"+item.formName);
			}else{//说明是流程节点表单
				alert("是否复制"+taskDefName+"节点表单:"+item.formName);
			}
		});
		dtd.resolve(); // 改变deferred对象的执行状态
		return dtd;
	}
</script>
</html>
