	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<%@ include file="/static/common/taglib.jsp"%>
	<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<%@ include file="/static/common/head.jsp"%>
	<%@ include file="/static/common/common.jsp"%>
	<title>工作流中间件-表单绑定</title>
	<script type="text/javascript" src="${ctx}/static/jquery/jquery-ui/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/jquery/databind/DataBind.js"></script>
	<script type="text/javascript" src="${ctx}/static/jquery/tools/jquery.json-2.4.js"></script>
	<script type="text/javascript" src="${ctx}/static/jquery/form/jquery.form.js"></script>
	<script type="text/javascript" src="${ctx}/static/jquery/chained/jquery.chained.remote.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/risesoft/js/myValidate.js"></script>
	<script type="text/javascript" src="${ctx}/static/risesoft/js/myValidate.js"></script>
	</head>
	<body>
		<div class="easyui-layout" data-options="fit:true,border:false">
				<form:form id="myForm" name="myForm" action="${ctx}/eform/bind/saveEformTaskBind" method="POST"  commandName="eformTaskBind">
					<input type="hidden" id="id" name="id" value="${id}"/>
					<table border="0" cellpadding="0" cellspacing="1" class="table">
						<tr>
							<td class="lefttd lbl-must" width="20%" >当前流程</td>
							<td width="80%" class="rigthtd">
								<input type="hidden" id="processDefinitionId" name="processDefinitionId" value="${processDefinitionId}"/>
								${procDefName}
							</td>
						</tr>
						<c:if test="${taskDefKey!=''}">
							<tr>
								<td class="lefttd lbl-must">当前任务</td>
								<td class="rigthtd">
									<input type="hidden" id="taskDefKey" name="taskDefKey" value="${taskDefKey}"/>
									${taskDefName}
								</td>
							</tr>
						</c:if>
						<tr>
							<td width="20%" class="lefttd lbl-must">表单名称</td>
							<td width="80%" class="rigthtd"> 
								<input type="hidden" id="formId" name="formId" value="${eformTaskBind.formId}"/>
								<input type="text" id="formName" name="formName" value="${eformTaskBind.formName}" readonly/>
								<button id="xuanze" name="xuanze">选择</button>
							</td>
						</tr>
						<tr>
							<td width="20%" class="lefttd lbl-must">显示顺序</td>
							<td class="rigthtd">
								<form:input path="tabIndex" />
							</td>
						</tr>
						<tr>
							<td width="20%" class="lefttd lbl-must">选择其它</td>
							<td class="rigthtd">
								<form:checkbox path="showFileTab" />附件 &nbsp;&nbsp;
								<form:checkbox path="showDocumentTab" />正文 &nbsp;&nbsp;
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center" >
								<button id="button01" name="button01">保存</button>
								<button id="button02" name="button02">取消</button>
							</td>
						</tr>
					</table>
				</form:form>
		</div>
		<div id="dialog-confirm" title="确定复制" style="display: none;">
			<p>是否复制其它绑定表单?</p>
		</div>
	</body>
	<script type="text/javascript">
	$(document).ready(function() {
		$('button').click(function(event) {
			switch (this.id) {
				case 'button01':
					if(!tabIndexValidate()){
						return false;
					}
					$("#myForm").ajaxSubmit({
						async:false, 
						cache:false,
						type : "POST",
						dataType:'JSON',
						success : function(data) {
							if (data.success == true) {
								/* if(data.otherTaskDef!=""){
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
											
											},
											"不复制": function() {
												closeCurWindow(parent.frameID,'close');
											}
										}
									});
								}else{ */
									alert("保存成功");
									parent.selectproc();
									closeCurWindow(parent.frameID,'close');
								//}
							} else {
								alert(data.msg);
							}
						}
					});
					break;
				case 'button02':
					closeCurWindow(parent.frameID,'close');
					break;
				case 'xuanze':
					eformList();
				break;
			}
			return false;//避免提交两次。
		});
		
		$('#tabIndex').blur(function(){
			tabIndexValidate();
		});
	
	});
	
	var frameID = newGuid();
	function eformList() {
		openCurWindow({
			id : frameID,
			src : '${ctx}/eform/bind/eFormListShow',
			destroy : true,
			title : '电子表单',
			width : 500,
			height : 300,
			modal : true
    	});
	}
	
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
