<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/bpmTaglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<title>工作流中间件-任务配置界面</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery/qtip2/jquery.qtip.min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/risesoft/css/fileflow.css" />

<script type="text/javascript" src="${ctx}/static/jquery/qtip2/jquery.qtip.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/tools/jquery.outerhtml.js"></script>
<script type="text/javascript" src="${ctx}/static/risesoft/js/activiti/workflow.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/tools/jquery.json-2.4.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/form/jquery.form.js"></script>

</head>
<body>
	<div align="center" style="width:100%; background-color:#CCEEFF;">
		<div style="text-align:center;padding-left:10px;padding-top:10px;padding-bottom:10px;width:100%; background-color:#CCEEFF;">
			版本：
			<select id="procDefVersion" name="procDefVersion">
				
			</select>
			&nbsp;&nbsp;
			<input type="button" id="processDefinitionId_select" name="processDefinitionId_select" onclick="selectproc();" value=" 确 定 ">
		</div>
	</div>
<div class="easyui-layout" data-options="fit:true,border:true">
	<div data-options="region:'center',title:'任务配置列表(${procDefName})',border:true">
		
		<table id="tasklist"></table>
	</div>
</div>


<div id="dialog-taskConf">
	<form id="form1" method="POST" action="">
	<table style='width:90%;height'>
		<tr style="border: #dddddd 1px solid;">
			<td align="right" width="50%" height="36px">是否只显示部门：</td>
			<td style="padding-left:10px;">
				<input type="radio" id="isSelDept1" name="isSelDept" value="1"/>是
				<input type="radio" id="isSelDept2" name="isSelDept" value="0" checked="checked"/>否
			</td>
		</tr>
		<tr style="border: #dddddd 1px solid;">
			<td align="right" width="50%" height="36px">是否只选取单个人员：</td>
			<td style="padding-left:10px;">
				<input type="radio" id="isSingle1" name="isSingle" value="true"/>是
				<input type="radio" id="isSingle2" name="isSingle" value="false" checked="checked"/>否
			</td>
		</tr>
		<tr style="border: #dddddd 1px solid;">
			<td align="right" width="50%" height="36px">是否直接发送：</td>
			<td style="padding-left:10px;">
				<input type="radio" id="isDirectlySend1" name="isDirectlySend"  value="true"/>是
				<input type="radio" id="isDirectlySend2" name="isDirectlySend"  value="false" checked="checked"/>否
			</td>
		</tr>
		<tr style="border: #dddddd 1px solid;">
			<td align="right" height="36px">是否必签意见：</td>
			<td style="padding-left:10px;">
				<input type="radio" id="isSignOpinion1" name="isSignOpinion"  value="true" checked="checked" />是
				<input type="radio" id="isSignOpinion2" name="isSignOpinion"  value="false"/>否
			</td>
		</tr>
		<tr style="border: #dddddd 1px solid;" id="sponsorTr">
			<td align="right" width="50%" height="36px">是否区分主协办：</td>
			<td style="padding-left:10px;">
				<input type="radio" id="isSponsor1" name="isSponsor" value="true"/>是
				<input type="radio" id="isSponsor2" name="isSponsor" value="false" checked="checked"/>否
			</td>
		</tr>
		<tr style="border: #dddddd 1px solid;">
			<td align="right" width="50%" height="36px">功能按钮：</td>
			<td>
				<table>
					<tr>
						<td align="center">
							已选
						</td>
						<td align="center">
							
						</td>
						<td align="center">
							待选
						</td>
					</tr>
					<tr>
						<td align="center">
							<input type="hidden" id="operation" name="operation" value=""/>
							<select id="operation1" name="operation1" size="5" style="width:110px;height:120px;">
							
							</select>
						</td>
						<td align="center" width="24px">
							<img id="addOperation" src="${ctx}/static/images/arrow_left.gif" width="20px" height="20px" border=0 alt="添加"><br><br>
							<img id="removeOperation" src="${ctx}/static/images/arrow_right.gif" width="20px" height="20px" border=0 alt="移除">
						</td>
						<td align="center">
							<select id="operationBak" name="operationBak" size="5" style="width:110px;height:120px;">
								<option value='button03'>收回</option>
								<option value='button04'>退回</option>
								<option value='button05'>委托</option>
								<option value='button06'>协商</option>
								<option value='button07'>批准、不予批准</option>
								<option value='button08'>补齐补正</option>
								<option value='button09'>特别程序申请</option>
								<option value='button10'>特殊办结</option>
							</select>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<input type="hidden" id="procDefName" name="procDefName" value="${procDefName}">
	<input type="hidden" id="procDefKey" name="procDefKey" value="${procDefKey}"/>
	<input type="hidden" id="processDefinitionId" name="processDefinitionId" value="${processDefinitionId}"/>
	<input type="hidden" id="taskDefKey" name="taskDefKey" value="${taskDefKey}"/>
	<input type="hidden" id="id" name="id" value="${id}"/>
</form>
</div>
<div id="dialog-delete">
	<p>确定删除当前配置?</p>
</div>
<div id="dialog-confirm">
	<p>是否复制其它催办?</p>
</div>
</body>
<script type="text/javascript">
var ids="";
var grid = $("#tasklist");
var processDefinitionKey="${procDefKey}";
var processDefinitionId="${processDefinitionId}";

function getMultiinstanceType(taskDefKey,taskDefName){
	$.ajax({
		    async:false,
		    cache: false,
	        type: "GET",
	        url: "${ctx}/taskConf/getMultiinstanceType",
	        data: {processDefinitionId:processDefinitionId, taskDefKey:taskDefKey},
	        dataType: "json",
	        success: function(data){
	        	if(data.multiinstanceType=="parallel"){
	    			$("#sponsorTr").show();
	    		}else{
	    			$("#sponsorTr").hide();
	    		}
	    		openAddDialog();
	       }
	});
}

//新增
function add(taskDefKey,taskDefName){	
	clear();
	$("#taskDefKey").val(taskDefKey);
	getMultiinstanceType(taskDefKey,taskDefName);
}

//编辑
function modify(id,taskDefKey,taskDefName)
{
	clear();
	$("#taskDefKey").val(taskDefKey);
	$.ajax({
		    async:false,
		    cache: false,
	        type: "GET",
	        url: "${ctx}/taskConf/findOne",
	        data: {id:id},
	        dataType: "json",
	        success: function(data){
	    		$('#id').val(data.id);
	    		if(data.isSelDept=="1"){
	    			$("input[name=isSelDept][value=1]").prop("checked",'checked');
	    		}else{
	    			$("input[name=isSelDept][value=0]").prop("checked",'checked');
	    		}
	    		
	    		if(data.isSingle){
	    			$("input[name=isSingle][value=true]").prop("checked",'checked');
	    		}else{
	    			$("input[name=isSingle][value=false]").prop("checked",'checked');
	    		}
	    		
	    		if(data.isSponsor){
	    			$("input[name=isSponsor][value=true]").prop("checked",'checked');
	    		}else{
	    			$("input[name=isSponsor][value=false]").prop("checked",'checked');
	    		}
	    		
	    		if(data.isDirectlySend){
	    			$("input[name=isDirectlySend][value=true]").prop("checked",'checked');
	    		}else{
	    			$("input[name=isDirectlySend][value=false]").prop("checked",'checked');
	    		}
	    		
	    		if(data.isSignOpinion){
	    			$("input[name=isSignOpinion][value=true]").prop("checked",'checked');
	    		}else{
	    			$("input[name=isSignOpinion][value=false]").prop("checked",'checked');
	    		}
	    		
	    		if(data.operation!="" && data.operation!=null){
	    			var curOperation=data.operation.split(",");
	    			for(var i=0;i<curOperation.length;i++){
	    				var existFlag=false;
	    				$("#operation1 option").each(function(){//遍历operation1，看已选是否存在，如果存在则不再添加
	    					if($(this).val()==curOperation[i])
	    					{
	    						existFlag=true;
	    					}
	    				});
	    				if(!existFlag){
	    					$('#operation1').append("<option value='"+curOperation[i]+"'>"+$('#operationBak option[value="'+curOperation[i]+'"]').text()+"</option>");
	    					$('#operationBak option[value="'+curOperation[i]+'"]').hide();
	    				}
	    			}
	    		}
	    		getMultiinstanceType(taskDefKey,taskDefName);
	       }
	});
}

function clear(){
	$("#id").val("");//设置为空，否则是上一个的配置
	$("input[name=isSelDept][value=0]").prop("checked",'checked');
	$("input[name=isSingle][value=false]").prop("checked",'checked');
	$("input[name=isDirectlySend][value=false]").prop("checked",'checked');
	$("input[name=isSponsor][value=false]").prop("checked",'checked');
	$("input[name=isSignOpinion][value=true]").prop("checked",'checked');
	$("#operation1 option").each(function(){
		$("#operation1 option[value='"+$(this).val()+"']").remove();
		$('#operationBak option[value="'+$(this).val()+'"]').show();
	});
}

function delete1(id){
	$( "#dialog-delete" ).dialog({
		title: '删除配置数据',
		resizable: false,
		width : 200,
		height:130,
		modal: true,
		buttons:[{
			text:'确定',
			handler:function() {
				$.ajax({
					     async:false,
					     cache: false,
				         type: "GET",
				         url: "${ctx}/taskConf/delete",
				         data: {id:id},
				         dataType: "json",
				         success: function(data){
				        	alert(data.msg);
							reloadGrid();
				        }
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

//重新加载list
function reloadGrid(){
	grid.datagrid({url:'${ctx}/taskConf/getTaskConfList',queryParams:{
		 processDefinitionId:$("#processDefinitionId").val()
	}
	});
}

//根据版本号查询
function selectproc(){
	$("#processDefinitionId").val($('#procDefVersion').val());
	reloadGrid();
}

$(document).ready(function(){
	//初始化鼠标样式
	$("#addOperation").css('cursor', 'pointer');
	$("#removeOperation").css('cursor', 'pointer');
	//获取指定流程定义的所有版本号
	$.ajax({
		    async:false,
		    cache: false,
	        type: "GET",
	        url: "${ctx}/procDef/getProcDefIds",
	        data: {processDefinitionKey:processDefinitionKey},
	        dataType: "json",
	        success: function(data){
	        	$.each(data, function(i,item){
	    			$("#procDefVersion").append("<option value='"+item+"'>版本"+(data.length-i)+"</option>");//为Select追加一个Option(下拉项)
	    		});
	       }
	});
	grid.datagrid({
		url : '${ctx}/taskConf/getTaskConfList?processDefinitionId=${processDefinitionId}',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		pagination : true,//分页控件，要分页时设置为true
		singleSelect:true,
		pageSize : 30,
		border : true,
		nowrap : false,
		columns : [[ {
			title : 'taskDefKey',
			field : 'taskDefKey',
			width : 90,
			align : "center",
			hidden : true
		}, {
			title : 'id',
			field : 'id',
			width : 80,
			align : "center",
			hidden : true
		}, {
			title : '任务名称',
			field : 'taskDefName',
			width : 40,
			align : "left",
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
					s = '<a href="javascript:void(0);" onclick="add(\'' + rowObject.taskDefKey+ '\',\'' + rowObject.taskDefName + '\');">新增</a>';
					s += '&nbsp;|&nbsp;<font color="dddddd">删除<font>';
				} else {
					s = '<a href="javascript:void(0);" onclick="modify(\'' + rowObject.id + '\',\'' + rowObject.taskDefKey + '\',\'' + rowObject.taskDefName + '\');">修改</a>';
					s += '&nbsp;|&nbsp;<a href="javascript:void(0);" onclick="delete1(\'' + rowObject.id + '\');">删除</a>';
				}
				return s;
			}
		} ]]
	});
	//新增功能按钮，返回结果是选取的按钮，例如“button04:回退;button05:转办;button06:协办”
	$('#addOperation').click(function(){
		var existFlag=false;
		$("#operation1 option").each(function(){//遍历operation1，看已选是否存在，如果存在则不再添加
			if($(this).val()==$('#operationBak').val())
			{
				existFlag=true;
			}
		});
		if(!existFlag)
		{
			$('#operation1').append("<option value='"+$('#operationBak').val()+"'>"+$('#operationBak option:selected').text()+"</option>");
			$('#operationBak option[value="'+$('#operationBak').val()+'"]').hide();
		}
	});
	
	//删除功能按钮
	$('#removeOperation').click(function(){
		$('#operationBak option[value="'+$('#operation1').val()+'"]').show();
		$('#operation1 option[value="'+$('#operation1').val()+'"]').remove();	//为Select删除一个Option(下拉项)
	});
});

function openAddDialog(){
	$("#dialog-taskConf").dialog({
		title: '任务基本配置',
		resizable : false,
		width : ($(window).width())*0.8 + 'px',
		height : ($(window).height())*0.7 + 'px',
		modal : true,
		buttons : [{
			text:'确定',
			handler: function() {
				var operationVal="";
				$("#operation1 option").each(function(){
					if(operationVal=="")
					{
						operationVal=$(this).val();
					}else
					{
						operationVal=operationVal+","+$(this).val();
					}
				});
				$('#operation').val(operationVal);
				$("#form1").ajaxSubmit({
					type : 'POST',
					dataType : 'json',
					url : '${ctx}/taskConf/save',
					success : function(responseText, statusText, xhr, $form) {
						reloadGrid();
						if (responseText.success == true) {
							ids=responseText.ids;
							alert("保存成功!");
							/* if(ids!="")
							{
								openCopyDialog();
							} */
						} else {
							alert("保存失败!");
						}
					}
				});
				$("#dialog-taskConf").dialog("close");
			}},
			{text:'取消',
			handler: function() {
				$("#dialog-taskConf").dialog("close");
			}
		}]
	});
}
function openCopyDialog(){
	$( "#dialog-confirm" ).dialog({
		autoOpen : false,
		resizable: false,
		height : 400,
		height:200,
		modal: true,
		buttons: {
			"复制": function() {
				$.ajax({
					type: "POST",
					url: "${ctx}/taskConf/copy",
					dataType:'JSON',
					data: {ids:ids,processDefinitionId:$("#processDefinitionId").val()},
					success: function(msg){
						reloadGrid();
						alert("复制成功");
						$("#dialog-confirm").dialog("close");
					},
					error:function(){
						alert("复制失败");
						$("#dialog-confirm").dialog("close");
					}
				});
			},
			"不复制": function() {
				$("#dialog-confirm").dialog("close");
			}
		}
	});
}
</script>
</html>