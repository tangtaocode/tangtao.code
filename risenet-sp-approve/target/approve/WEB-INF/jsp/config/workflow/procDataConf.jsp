<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<title>工作流中间件-过程数据配置界面</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery/qtip2/jquery.qtip.min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/risesoft/css/fileflow.css" />

<script type="text/javascript" src="${ctx}/static/jquery/qtip2/jquery.qtip.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/tools/jquery.outerhtml.js"></script>
<script type="text/javascript" src="${ctx}/static/risesoft/js/activiti/workflow.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/tools/jquery.json-2.4.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/databind/DataBind.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/form/jquery.form.js"></script>

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
	<div data-options="region:'center',title:'过程数据配置列表(${procDefName})',border:true">
		<table id="tasklist"></table>
	</div>
</div>


<div id="dialog-taskConf">
	<form id="form1" name="form1" method="POST" action="${ctx}/procData/save">
	<table>
		<tr>
			<td style="width:25%;" rowspan="3">过程数据状态：</td>
			<td style="width:75%;">
				<input type="checkbox" id="status00" name="status" value="00"/>申办
				<input type="checkbox" id="status20" name="status" value="20"/>受理(受理)
				<input type="checkbox" id="status21" name="status" value="21"/>受理(不受理)
			</td>
		</tr>
			<td>
				<input type="checkbox" id="status22" name="status" value="22"/>审批(承办)
				<input type="checkbox" id="status23" name="status" value="23"/>审批(审核)
				<input type="checkbox" id="status24" name="status" value="24"/>审批(批准)
			</td>
		</tr>
		</tr>
			<td>
				<input type="checkbox" id="status44" name="status" value="44"/>办结
			</td>
		</tr>
		<tr rowspan="3">
			<td></td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<input type="submit" value="提交" />
				<input type="reset" value="重置" />
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
var remModel = {};
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
function modify(id,taskDefKey,taskDefName){
	clear();
	$("#taskDefKey").val(taskDefKey);
	$.ajax({
		    async:false,
		    cache: false,
	        type: "GET",
	        url: "${ctx}/procData/findOne",
	        data: {id:id},
	        dataType: "json",
	        success: function(data){
	    		$('#id').val(data.id);
	    		remModel = data;
				DataBind.bind($('#form1'), remModel);
				//设置复选框是否被选中
				if(data.status!=null){
					var tempcheck=remModel.status.split(",");
					for(var i=0;i<tempcheck.length;i++){
						$("#status"+tempcheck[i]).prop("checked",true);
					}
				}
				
	    		openAddDialog();
	    		//getMultiinstanceType(taskDefKey,taskDefName);
	       }
	});
}

function clear(){
	$("#id").val("");//设置为空，否则是上一个的配置
	$("#status00").prop("checked",false);
	$("#status20").prop("checked",false);
	$("#status21").prop("checked",false);
	$("#status22").prop("checked",false);
	$("#status23").prop("checked",false);
	$("#status24").prop("checked",false);
	$("#status44").prop("checked",false);
}

//删除
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
				         url: "${ctx}/procData/delete",
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
	grid.datagrid({url:'${ctx}/procData/getProcDataConfList',queryParams:{
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
		url : '${ctx}/procData/getProcDataConfList?processDefinitionId=${processDefinitionId}',
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

});

//打开添加页面
function openAddDialog(){
	$("#dialog-taskConf").dialog({
		title: '过程数据配置',
		resizable : false,
		height : 180,
		width : 480,
		modal : true
	});
}

function openCopyDialog(){
	$( "#dialog-confirm" ).dialog({
		autoOpen : false,
		resizable: false,
		width : 300,
		height:150,
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

//验证
if ($.fn.validatebox) {
    $.extend($.fn.validatebox.defaults.rules, {
	integer: {// 验证整数 可正负数
	    validator: function (value) {
	    	return /^([-]?[0-9])+\d*$/i.test(value) ;
	    },
	    message: '请输入整数'
	},
	size: {// 验证范围
	    validator: function (value,param) {
	        return param[0]<=value && param[1] >= value;
	    },
	    message: '范围必须在{0}到{1}之间'
	}
  });
}

$(function() {
	var subOptions = { 
		async : false,  
		cache : false,
		dataType:"json",
		type:'POST', 
		error:function(data){
			alert("出现异常,此次操作可能失败");
		},
		success : function(responseText, statusText, xhr, $form) {
			reloadGrid();
			if (responseText.success == true) {
				alert("保存成功!");
			} else {
				alert("保存失败!");
			}
			$("#dialog-taskConf").dialog("close");
		}
 	};
	
	$("form[name='form1']").submit(function(){
		if($(this).form('validate')){
			 $(this).ajaxSubmit(subOptions);
		}
		return false;
	}) ;
});

</script>
</html>