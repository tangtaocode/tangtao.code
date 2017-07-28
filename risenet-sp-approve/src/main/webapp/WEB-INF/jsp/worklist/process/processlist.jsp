<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<title>工作流中间件-流程部署</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery/qtip2/jquery.qtip.min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/risesoft/css/fileflow.css" />

<script type="text/javascript" src="${ctx}/static/jquery/qtip2/jquery.qtip.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/tools/jquery.outerhtml.js"></script>
<script type="text/javascript" src="${ctx}/static/risesoft/js/activiti/workflow.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/tools/jquery.json-2.4.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/form/jquery.form.js"></script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true,border:true">
			<div id="toolbar" class="toolbar" style="background-color:#CCEEFF;">
				<table border="0" cellpadding="0" cellspacing="0" style="width: auto">
					<tr>
						<td>
							<a href="javascript:void(0);" class="easyui-linkbutton"	data-options="iconCls:'icon-edit',plain:true" id="deployBPM">部署流程</a>
						</td>
					</tr>
				</table>
			</div>
			<table id="list1"></table>
	</div>
	<div id="dialog-form" title="上传文件,支持的文件格式：zip、bar、bpmn、bpmn20.xml"  class="easyui-dialog" data-options="modal:true,closed:true">
		<form id="fileForm" method="POST" ENCTYPE="multipart/form-data" action="${ctx}/workflow/deploy">
			<div style="height: 20px"></div>
			<input type="file" name="barfile" id="barfile" style="width: 520px; height: 26px" />
		</form>
	</div>
</body>
<script type="text/javascript">
	var grid = $('#list1');

	$(document).ready(function() {
		
		initUploadDialog();
		
		grid.datagrid({
			url : '${ctx}/workflow/process/list',
			toolbar : '#toolbar',
			fit : true,
			singleSelect : true,
			fitColumns : true,
			striped : true,
			rownumbers : true,
			pagination : true,//分页控件，要分页时设置为true
			pageSize : 30,
			border : true,
			nowrap : false,
			columns : [[ {
				title : '名称',
				field : 'name',
				width : 20,
				align : "center"
			}, {
				title : 'key',
				field : 'key',
				width : 20,
				align : "center"
			},{
				title : 'id',
				field : 'id',
				width : 10,
				align : "left",
				hidden : true
			}, {
				title : '部署ID',
				field : 'deploymentId',
				width : 20,
				align : "center",
				hidden : true
			},  {
				title : '版本',
				field : 'version',
				width : 10,
				align : "center"
			}, {
				title : 'XML',
				field : 'resourceName',
				width : 80,
				align : "center",
				hidden : true
				/* ,
				formatter : 'showlink',
				formatoptions : {
					target : '_blank',
					baseLinkUrl : '${ctx}/workflow/resource/read',
					addParam : '&resourceType=xml',
					idName : 'processDefinitionId'
				} */
			}, {
				title : '图片',
				field : 'diagramResourceName',
				width : 80,
				align : "center",
				hidden : true
				/* ,
				formatter : 'showlink',
				formatoptions : {
					target : '_blank',
					baseLinkUrl : '${ctx}/workflow/resource/read',
					addParam : '&resourceType=image',
					idName : 'processDefinitionId'
				} */
			}, {
				title : '部署时间',
				field : 'deploymentTime',
				width : 20,
				align : "center"
			}, {
				title : '是否挂起',
				field : 'suspended',
				width : 10,
				align : "center",
				formatter : function(cellvalue, rowObject, rowIndex) {
					if (cellvalue) {
						return '已挂起';
					} else {
						return '未挂起';
					}
				}
			}, {
				title : '操作',
				field : 'opt',
				width : 450,
				fixed : true,
				align : "center",
				formatter : function(cellvalue, rowObject, rowIndex) {
					//options : { rowId: rid, colModel: cm}
					//where rowId - is the id of the row
					//colModel is the object of the properties for this column getted from colModel array of jqGrid
					var s = '<a href="javascript:void(0);" onclick="deleteProcessDefinition(\'' + rowObject.deploymentId + '\',\'' + rowObject.id + '\');">删除</a>';
					if (rowObject.suspended) {
						s += '&nbsp;|&nbsp;<a href="javascript:void(0);" onclick="switchSuspendOrActive(\'active\',\'' + rowObject.id + '\');">激活</a>';
					} else {
						s += '&nbsp;|&nbsp;<a href="javascript:void(0);" onclick="switchSuspendOrActive(\'suspend\',\'' + rowObject.id + '\');">挂起</a>';
					}
					
					s += '&nbsp;|&nbsp;<a href="${ctx}/workflow/process/convert-to-model/' + rowObject.id + '">转换为Model</a>';
					s += '&nbsp;|&nbsp;<a href="javascript:void(0);" onclick="graphTrace(\'\',\'' + rowObject.id + '\');">流程图</a>';
					s += '&nbsp;|&nbsp;<a href="javascript:void(0);" onclick="sync(\'' + rowObject.id + '\');" >同步资源</a>';
					s += '&nbsp;|&nbsp;<a href="javascript:void(0);" onclick="publishToSystemApp(\'' + rowObject.key + '\');" >发布为系统应用</a>';
					
					if(rowObject.key!='sendDocument' && rowObject.key!='receiveDocument'){
						//s += '&nbsp;|&nbsp;<a href="javascript:void(0);" onclick="startProcess(\'' + rowObject.id + '\');">发起</a>';
					}
					return s;
				}
			} ]]
		});

		//部署流程，上传流程
		$('#deployBPM').click(function() {
			$("#dialog-form").dialog("open");
		});

	});
	function sync(proccessid){
		$.ajax({
			url:"${ctx}/sync/syncProcess",
			type:"POST",
			data:{"processDefinitionId":proccessid},
			dataType:"html",
			success:function(msg){
				alert(msg);
			}
		});
	}
	
	function publishToSystemApp(processDefineKey){
		$.ajax({
			url:"${ctx}/workflow/publishToSystemApp",
			type:"POST",
			data:{"processDefineKey":processDefineKey},
			dataType:"json",
			success:function(data){
				if(data.success==false){
					alert("发布为系统应用失败！");
				}else if(data.success==true){
					alert("发布为系统应用成功！");
				}
			}
		});
	}
	//删除流程
	function deleteProcessDefinition(deploymentId,proccessid) {
		if (confirm("确认删除？")){
			deleteProcess(deploymentId,proccessid);
		}else{
			 return false;
		}
	}
	
	function deleteProcess(deploymentId,proccessid){
		$.getJSON('${ctx}/workflow/process/delete?deploymentId=' + deploymentId, function(data, textStatus, jqXHR) {			
		$.post("${ctx}/sync/deleteProcess",{"processDefinitionId":proccessid},function(){});
		grid.datagrid('reload');
		alert(data.msg);
		});  
	}
	//挂起激活流程
	function switchSuspendOrActive(state, processDefinitionId) {
		$.getJSON('${ctx}/workflow/switchSuspendOrActive', {
			'state' : state,
			'processDefinitionId' : processDefinitionId
		}, function(data, textStatus, jqXHR) {			
			grid.datagrid('reload');
			alert(data.msg);
		});
	}
	
	//初始化上传窗口
	function initUploadDialog()
	{
		var dlg=$('#dialog-form');
		dlg.dialog({
			width : 600,
			height : 140,
			modal : true,
			title : '部署流程',
			buttons : [{
				text : '部署',
				iconCls : 'icon-save',
				handler : function() {
					var fileName = $('#barfile').val();
					if (!fileName || fileName.lastIndexOf('.') == -1) {
						alert('文件格式不对。支持的文件格式：zip、bar、bpmn、bpmn20.xml');
						return;
					} else {
						var fileExtension = fileName.substr(fileName.lastIndexOf('.') + 1);
						switch (fileExtension) {
						case 'zip':
						case 'rar':
						case 'bar':
						case 'bpmn':
						case 'xml':
							//alert('submitting....');
							$("#fileForm").ajaxSubmit({
								//type : 'POST',
								dataType : 'json',
								success : function(responseText, statusText, xhr, $form) {
									if (responseText.success == true) {										
										grid.datagrid('reload');
										$("#dialog-form").dialog("close");
										alert(responseText.msg);
									} else {
										alert(responseText.msg);
									}
								}
							});
							break;
						default:
							alert('文件格式不对。支持的文件格式：zip、bar、bpmn、bpmn20.xml');
						}
					}
				}
			}, {
				text : '关闭',
				iconCls : 'icon-cancel',
				handler : function() {
					dlg.dialog('close');
				}
			} ],
			onClose : function() {
				//$(this).remove();//移除窗体，新建临时窗口时使用，这里不使用
			}
		});
	}
</script>
</html>