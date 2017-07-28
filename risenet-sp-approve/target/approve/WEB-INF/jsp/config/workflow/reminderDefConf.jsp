<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<title>工作流中间件-催办信息</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery/qtip2/jquery.qtip.min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/risesoft/css/fileflow.css" />

<script type="text/javascript" src="${ctx}/static/jquery/qtip2/jquery.qtip.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/tools/jquery.outerhtml.js"></script>
<script type="text/javascript" src="${ctx}/static/risesoft/js/activiti/workflow.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/tools/jquery.json-2.4.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/form/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/databind/DataBind.js"></script>
<script type="text/javascript" src="${ctx}/static/risesoft/js/myValidate.js"></script>

</head>
<body>
	<div align="center" style="width:100%; background-color:#CCEEFF;">
		<div style="text-align:center;padding-left:10px;padding-top:10px;padding-bottom:10px;">
			版本：
			<select id="procDefVersion" name="procDefVersion">
				
			</select>
			&nbsp;&nbsp;
			<input type="button" id="processDefinitionId_select" name="processDefinitionId_select" onclick="selectproc();" value=" 确 定 ">
		</div>
	</div>
	<div class="easyui-layout" data-options="fit:true,border:true">
		<div data-options="region:'center',title:'催办信息列表(${procDefName})',border:true">
			<table id="list1"></table>
		</div>
		<div>&nbsp;</div>
	</div>
	<div>
		<table>
			<tr>
				<td>
					<input type="hidden" id="procDefKey" name="procDefKey" value="${procDefKey}"/>
				</td>
			</tr>
		</table>
	</div>
	<div id="dialog-reminderconf">
		<form id="remForm" method="POST" action="${ctx}/reminderconf/reminderdef/save">
			<table style='width:90%;'>
				<tr>
					<td align="right">任务名称：</td>
					<td align="left">
						<input type="text" id="remTaskDefName" name="remTaskDefName"  disabled="disabled" />
					</td>
				</tr>
				<tr style="border: #dddddd 1px solid;">
					<td align="right">催办方式：</td>
					<td>
						<input type="checkbox" id="reminderDefine_Type1" name="reminderDefine_Type" value="1"/>&nbsp;短信&nbsp;&nbsp;
						<input type="checkbox" id="reminderDefine_Type2" name="reminderDefine_Type" value="2"/>&nbsp;邮件
						<input type="checkbox" id="reminderDefine_Type3" name="reminderDefine_Type" value="3"/>&nbsp;站内提醒
					</td>
				</tr>
				<tr style="border: #dddddd 1px solid;">
					<td align="right">日期类型：</td>
					<td>
						<input type="radio" id="day_Type1" name="day_Type" value="1"/>自然日
						<input type="radio" id="day_Type2" name="day_Type" value="2"/>工作日
					</td>
				</tr>
				<tr style="border: #dddddd 1px solid;">
					<td align="right">任务到期天数：</td>
					<td>
						<input type="text" id="taskDuration" name="taskDuration" data-key="taskDuration"/>天
					</td>
				</tr>
				<tr style="border: #dddddd 1px solid;">
					<td align="right">催办开始时间：</td>
					<td>
						提前<input type="text" name="remindStart" id="remindStart" style="width: 30px;" data-key="remindStart"/>天
					</td>
				</tr>
				<tr style="border: #dddddd 1px solid;">
					<td align="right">催办间隔时间（小时）：</td>
					<td>
						<input type="text" name="remindInterval" id="remindInterval" style="width: 30px;" data-key="remindInterval"/>小时
					</td>
				</tr>
				<tr style="border: #dddddd 1px solid;">
					<td align="right">催办次数：</td>
					<td>
						<input type="text" name="remindCount" id="remindCount" style="width: 30px;" data-key="remindCount"/>次
					</td>
				</tr>
				<tr style="border: #dddddd 1px solid;">
					<td align="right">内容：</td>
					<td>
						<textarea rows="5" style="width: 96%;" id="msgTemplate" name="msgTemplate" data-key="msgTemplate"></textarea>
					</td>
				</tr>
			</table>
			<input type="hidden" id="processDefinitionId" name="processDefinitionId" data-key="processDefinitionId"/>
			<input type="hidden" id="taskDefKey" name="taskDefKey" data-key="taskDefKey"/>
			<input type="hidden" id="customId" name="customId" data-key="${customId}"/>
			<input type="hidden" id="id" name="id" data-key="id"/>
			<input type="hidden" id="reminderDefine_Automatic" name="reminderDefine_Automatic" value="2" />
		</form>
	
	</div>
	<div id="dialog-delete">
		<p>确定删除当前事项?</p>
	</div>
	<!-- <div id="dialog-confirm">
		<p>是否复制其它催办?</p>
	</div> -->
	
</body>
<script type="text/javascript">
	var remModel = {};
	var ids="";
	var grid = $("#list1");
	var processDefinitionKey="${procDefKey}";
	var customId="${customId}";
	$(document).ready(function(){
		$("#processDefinitionId").val("${processDefinitionId}");
		$('#day_Type1').attr("checked","checked");
		//获取指定流程定义的所有版本号
		$.ajax({
			async:false,
			cache:false,
			type:"GET",
			dataType: "json",
			url:ctx+"/procDef/getProcDefIds",
			data:{processDefinitionKey:processDefinitionKey},
			success:function(data){
				$.each(data, function(i,item){
					$("#procDefVersion").append("<option value='"+item+"'>版本"+(data.length-i)+"</option>");//为Select追加一个Option(下拉项)
				});
			}
		});
		
		grid.datagrid({
			url : '${ctx}/reminderconf/gettaskbyprocessDefinitionId?processDefinitionId=${processDefinitionId}',
			fit : true,
			fitColumns : true,
			striped : true,
			rownumbers : true,
			//pagination : true,//分页控件，要分页时设置为true
			//pageSize : 30,
			border : true,
			singleSelect:true,
			nowrap : false,
			columns : [[ {
				title : 'taskDefKey',
				field : 'taskDefKey',
				width : 90,
				align : "center",
				hidden : true
			},{
				title : 'reminderDefId',
				field : 'reminderDefId',
				width : 40,
				align : "center",
				hidden : true
			},{
				title : '任务名称',
				field : 'taskDefName',
				width : 40,
				align : "left",
				editable: true,
				hidden : false
			},{
				title : '操作',
				field : 'taskOpt',
				width : 240,
				fixed : true,
				align : "center",
				formatter : function(cellvalue, rowObject, rowIndex) {
					var s = '';
					if ($.trim(cellvalue).length == 0) {
						s = '<a href="javascript:void(0);" onclick="addreminder(\'' + rowObject.taskDefKey + '\',\'' + rowObject.taskDefName + '\');">新增</a>';
						s += '&nbsp;|&nbsp;<font color="dddddd">删除<font>';
					} else {
						s = '<a href="javascript:void(0);" onclick="modifyreminder(\'' + rowObject.taskDefKey + '\',\'' + rowObject.taskDefName + '\');">修改</a>';
						s += '&nbsp;|&nbsp;<a href="javascript:void(0);" onclick="openDeleteDialog(\'' + rowObject.reminderDefId + '\');">删除</a>';
					}
					
					return s;
				}
			} ]]
		});
		
		$('#taskDuration').focusout(function(){
			numcheck("taskDuration");
		});
		$('#remindStart').focusout(function(){
			numcheck("remindStart");
		});
		$('#remindInterval').focusout(function(){
			numcheck("remindInterval");
		});
		$('#remindCount').focusout(function(){
			numcheck("remindCount");
		});
	});
	
	function addreminder(taskDefKey,taskDefName)
	{
		cleanreminder();
		
		$("#remTaskDefName").val(taskDefName);
		$("#taskDefKey").val(taskDefKey);
		$("#id").val("");//设置为空，否则是上一个reminderDefine的配置
		openRDCDialog();
	}
	
	function modifyreminder(taskDefKey,taskDefName)
	{
		cleanreminder();
		$("#remTaskDefName").val(taskDefName);
		$.ajax({
			async:true,
			cache: false,
            type: "GET",
            url: "${ctx}/reminderconf/getreminder",
            data: {processDefinitionId:$("#processDefinitionId").val(), taskDefKey:taskDefKey},
            dataType: "json",
            success: function(data){
	            	remModel = data;
					DataBind.bind($('#remForm'), remModel);
					//设置复选框是否被选中
					var tempcheck=remModel.reminderDefine_Type.split(",");
					for(var i=0;i<tempcheck.length;i++){
						$("#reminderDefine_Type"+tempcheck[i]).prop("checked",true);
					}
					var dayType=remModel.day_Type;//设置单选框是否被选中
					$("#day_Type"+dayType).prop("checked",true);
					openRDCDialog();
             }
		});
		
	}
	
	function openRDCDialog(){
		$("#dialog-reminderconf").dialog({
			title:'催办配置',
			resizable: false,
			width : ($(window).width())*0.7 + 'px',
			height : ($(window).height())*0.9 + 'px',
			modal : true,
			buttons:[{
				text:'确定',
				handler:function() {
					numcheck("taskDuration");
					numcheck("remindStart");
					numcheck("remindInterval");
					numcheck("remindCount");
					$("#remForm").ajaxSubmit({
						type : 'POST',
						dataType : 'json',
						url : '${ctx}/reminderconf/reminderdef/save',
						success : function(responseText, statusText, xhr, $form) {
							reloadgrid();
							if (responseText.success == true) {
								ids=responseText.ids;
								alert("保存成功!");
								if(ids!="")
								{
									openCopyDialog();
								}
							} else {
								alert("保存失败!");
							}
						}
					});
					$("#dialog-reminderconf").dialog("close");
				}
			},
			{
				text:'取消',
				handler: function() {
					$("#dialog-reminderconf").dialog("close");
				}
			}]
		});
	}
	
	/* function openCopyDialog(){
		$( "#dialog-confirm" ).dialog({
			title:'确认复制',
			resizable: false,
			width : 200,
			height:200,
			modal: true,
			buttons:[{
				text:'复制',
				handler:function() {
					$.ajax({
						type: "POST",
						url: "${ctx}/reminderconf/copy",
						dataType:'JSON',
						data: {ids:ids,processDefinitionId:$("#processDefinitionId").val()},
						success: function(msg){
							alert("复制成功");
							reloadgrid();
							$("#dialog-confirm").dialog("close");
						},
						error:function(){
							alert("复制失败");
							$("#dialog-confirm").dialog("close");
						}
					});
				}
			},
			{
				text:'取消',
				handler: function() {
					$("#dialog-confirm").dialog("close");
				}
			}]
		});
	} */
	function openDeleteDialog(reminderDefId)
	{
		$( "#dialog-delete" ).dialog({
			title:'确定删除',
			resizable: false,
			width : 200,
			height:130,
			left:200,
			top:200,
			modal: true,
			buttons:[{
				text:'确定',
				handler:function() {
					$.get(ctx+'/reminderconf/delreminder?reminderDefId='+reminderDefId+'&t='+new Date(),function(data){
						//alert(data.msg);
						reloadgrid();
					});
					$("#dialog-delete").dialog( "close" );
				}
			},
			{
				text:'取消',
				handler: function() {
					$("#dialog-delete").dialog( "close" );
				}
			}]
		});
	}
	function cleanreminder()
	{
		//设置为空，否则是上一个reminderDefine的配置
		for(var i=1;i<=3;i++)
		{
			$("#reminderDefine_Type"+i).prop("checked",false);
		}
		for(var i=1;i<=2;i++)
		{
			$("#day_Type"+i).prop("checked",false);
		}
		$("#remindCount").val("");
		$("#taskDuration").val("");
		$("#remindStart").val("");
		$("#msgTemplate").val("");
		$("#remindInterval").val("");
	}
	
	//下面是数字校验
	function numcheck(t)
	{
		if(!isNumber($("#"+t).val()))
		{
			alert("输入含有非数字，请重新输入");
			return false;
		}else
		{
			return true;
		}
	}
	
	//查询版本号
	function selectproc()
	{
		$("#processDefinitionId").val($('#procDefVersion').val());
		reloadgrid();
	}
	
	//重新加载jqgrid
	function reloadgrid()
	{
		grid.datagrid({url:'${ctx}/reminderconf/gettaskbyprocessDefinitionId',queryParams:{
				'processDefinitionId':$("#processDefinitionId").val()
		 	}
		});
	}
</script>
</html>