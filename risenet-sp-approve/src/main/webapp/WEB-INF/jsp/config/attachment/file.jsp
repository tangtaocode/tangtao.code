<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<title>工作流中间件-权限设置</title>
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
		<div id="toolbar" class="toolbar"  align="left" style="width:100%; background-color:#CCEEFF;">
			<c:if test="${itembox!='history'&&itembox!='done'&&itembox!='doing'}">
				<table>
					<tr>
						<td><a href="javascript:void(0);" class="easyui-linkbutton"
							data-options="iconCls:'icon-add',plain:true" id="uploadfiles" >上传附件</a>
						</td>
						<td>&nbsp;&nbsp;&nbsp;</td>
					</tr>
				</table>
			</c:if>
			<span id="msg"></span>
		</div>
		<table id="list1"></table>
	</div>
	
	<br>
	 <div id="dialog-deletefileAttachment">
	<p>确定删除该附件？</p>
	</div>
	<div id="dialog-uploadfiles">
		<form id="fileForm" enctype="multipart/form-data" action="">
			<div style="height: 20px"></div>
			<input type="file" name="attachmentFile" id="attachmentFile" style="width: 500px; height: 26px" />
		</form>
		<br>
		<font color="red">请上传小于20M的附件！</font>
	</div> 
</body>

<script type="text/javascript">
	var grid=$("#list1");
	//var DECLAREANNEXGUID='${DECLAREANNEXGUID}';//材料ID
	//var instanceId='${instanceId}';//流程实例ID
	//var itembox="${itembox}";
	var stuffSeq='${stuffSeq}';//材料编号
	var sblsh='${sblsh}';//申办流水号
	var version='${version}';	//数据版本号
	var errorMsg = '${msg}';
	//var processInstanceId = "";
	$(document).ready(function(){
		grid.datagrid({
			//url : '${ctx}/wssbcl/fileList?DECLAREANNEXGUID=' + DECLAREANNEXGUID+'&instanceId='+instanceId,
			url : '${ctx}/wssbcl/fileList?stuffSeq=' + stuffSeq+'&sblsh='+sblsh+'&version='+version,
			//toolbar : '#toolbar',
			fit : true,
			fitColumns : true,
			striped : true,
			rownumbers : true,
			singleSelect : true,
			border : true,
			nowrap : false,
			columns : [[ {
				title : 'id',
				field : 'id',
				width : 90,
				align : "center",
				hidden : true
			},{
				title : '文件名称',
				field : 'fileName',
				width : 250,
				align : "center",
				hidden : false,
				formatter : function(cellvalue, rowObject, rowIndex) {
					var s = '';
					//s += '<a href="${ctx}/wssbcl/download?id=' + rowObject.id + '&instanceId='+instanceId+'&declareGuid='+DECLAREANNEXGUID+'&itembox='+itembox+'" class="aColor" >' + rowObject.fileName + '</a>';
					s += '<a href="'+rowObject.filePath+'" class="aColor" >' + rowObject.fileName + '</a>';
					return s;
				}
			},
			{
				title : '上传时间',
				field : 'uploadTime',
				width : 150,
				align : "center",
				editable: true,
				hidden : false
			}/* ,{
				title : '操作',
				field : 'Opt',
				width : 100,
				fixed : true,
				hidden : false,
				align : "center",
				formatter : function(cellvalue, rowObject, rowIndex) {
					//if(itembox!='history'&&itembox!='done'&&itembox!='doing'){
						var s = '';
						s += '<a href="javascript:void(0);" onclick="deleteFileAttachment(\'' + rowObject.id + '\');">删除</a>';
						return s;
					//	return '';
					//}
				}
			} */
			]]
			
		});
	
		/* $("#uploadfiles").click(function(){
			clearUpload();
			openUploadFilesDialog();
		}); */
		$("#msg").append('<div style="color:red">'+errorMsg+'</div>');
	});
	
	/* function clearUpload(){
		var ie = (navigator.appVersion.indexOf("MSIE")!=-1);//IE
		if(ie){
			$("#attachmentFile").val("");
			//$("#attachmentFile").select();
			//document.execCommand("delete");   
		}else{   
			$("#attachmentFile").val("");	
		}
	} */
	
	//上传附件
	 function openUploadFilesDialog(){
		$( "#dialog-uploadfiles" ).dialog({
			title:"上传文件",
			resizable: false,
			width : 550,
			height:200,
			modal: true,
			buttons:[{
				text:'确定',
				handler:function() {
					var fileName = $('#attachmentFile').val();
					if (!fileName || fileName.lastIndexOf('.') == -1) {
						alert('请选择文件，且文件格式为:doc、docx、pdf、xls、wps');
						return;
					} else {
						var fileExtension = fileName.substr(fileName.lastIndexOf('.') + 1);
						
							$('#fileForm').ajaxSubmit({
								type : 'POST',
								dataType : 'json',
								data : {
									'declareannexguid':DECLAREANNEXGUID,
									'processInstanceId' :instanceId 
								},
								url : '${ctx}/wssbcl/upload',
								success : function(responseText, statusText, xhr, $form) {
									if (responseText.success == true) {
										$("#dialog-uploadfiles").dialog("close");
										alert(responseText.msg);
										$("#list1").datagrid('reload');
									} else {
										alert(responseText.msg);
									}
								},
								error:function(){
								}
							});
							
					}
				}
			},{
				text:'取消',
				handler: function() {
					$("#dialog-uploadfiles").dialog("close");
				}
			}]
		});
	} 
	
	//删除附件
	 function deleteFileAttachment(id){
		$( "#dialog-deletefileAttachment" ).dialog({
			title:"确定删除附件",
			resizable: false,
			width : 200,
			height:130,
			modal: true,
			buttons:[{
				text:'确定',
				handler:function() {
					$.getJSON('${ctx}/wssbcl/delete?id=' + id, function(data, textStatus, jqXHR) {
						if(data.Result=='OK'){
							alert(data.msg);
							$("#dialog-deletefileAttachment").dialog( "close" );
							grid.datagrid('reload');
						}else{
							alert(data.msg);
							return false;
						}
					});
				}
			},
			{
				text:'取消',
				handler: function() {
					$("#dialog-deletefileAttachment").dialog( "close" );
				}
			}]
		});
	} 
</script>
</html>