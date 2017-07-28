<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/bpmTaglib.jsp"%>
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
<style>
.table_v {
	border: 2px solid #9DC3DA;
	width: 90%;
	margin-top: 10px;
	margin-bottom: 50px;
	height: 80%;
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
.ui-jqgrid .ui-userdata {/* 设置jqgrid工具栏高度 */
    height: 30px; /* default value in ui.jqgrid.CSS is 21px */
}
</style>

</head>
<body>
<div id="searchBar" align="left" style="background-color:#CCEEFF;">
	<table>
		<tr>
			<td align="left" style="text-align:left">
				类型：
				<select id="principalType" name="principalType">
					<!-- <option value="">--</option>
					<option value="3">用户</option>
					<option value="2">部门</option> -->
					<option value="1">角色</option>
					<option value="4">动态角色</option>
					<!-- <option value="5">用户组</option> -->
				</select>
				&nbsp;&nbsp;
				名称：<input type="text" id="nameCondition" name="nameCondition" value="" >
				&nbsp;&nbsp;
				版本：
				<select id="procDefVersion" name="procDefVersion">
					
				</select>
				&nbsp;&nbsp;
				<input type="button" id="processDefinitionId_select" name="processDefinitionId_select" onclick="selectproc();" value=" 查 询 ">
			</td>
		</tr>
	</table>
	<input type="hidden" id="procDefKey" name="procDefKey" value="${procDefKey }"/>
	<input type="hidden" id="processDefinitionId" name="processDefinitionId" value="${processDefinitionId }"/><!-- 当前版本的流程定义Id -->
	<input type="hidden" id="latestProcessDefinitionId" name="latestProcessDefinitionId" value="${latestProcessDefinitionId }"/><!-- 最新版本的流程定义Id -->
	<input type="hidden" id="procDefName" name="procDefName" value="${procDefName }"/>
	<input type="hidden" id="taskDefKey" name="taskDefKey" value="${taskDefKey }"/>
	<input type="hidden" id="taskDefName" name="taskDefName" value="${taskDefName }"/>
	<input type="hidden" id="objectType" name="objectType" value="2"><!-- 表示设置的是流程的权限 -->
</div>
<div class="easyui-layout" data-options="fit:true,border:true">
		<div data-options="region:'center',title:'权限列表${procDefName}-->${taskDefName}',border:true">
			<div id="toolbar" class="toolbar"  align="left" style="width:100%;background-color:#CCEEFF;">
				<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="getRole(1)" id="choiceRole" >新增角色</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="getRole(4)" id="choiceRole" >新增动态角色</a>
				<c:if test="${taskDefKey==''}">	
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="copyPerm(false);" id="copyBpmPerm" >复制流程授权</a>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="copyPerm(true);" id="copyAllPerm" >复制所有授权</a>
				</c:if>
				<c:if test="${taskDefKey!=''}">
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="copyPerm(false);" id="copyPerm" >复制本节点授权</a>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="copyPerm(true);" id="copyAllPerm" >复制所有授权</a>
				</c:if>
				<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-back',plain:true" id="back1" >返回</a>
			</div>
			<table id="list1"></table>
		</div>
</div>
<br>
<div id="dialog-confirmdelete">
	<p>确定删除?</p>
</div>
<div id="dialog-confirmCopy">
	<p>确定复制?</p>
</div>
</body>
<script type="text/javascript">
var grid=$("#list1");

$(document).ready(function(){
	grid.datagrid({
		url : '${ctx}/objPerm/getPerm/list?objectGuid=${objectGuid}',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		pagination : true,//分页控件，要分页时设置为true
		pageSize : 30,
		border : true,
		nowrap : false,
		columns : [[ {
			title : 'id',
			field : 'id',
			width : 90,
			align : "center",
			hidden : true
		},{
			title : 'principalId',
			field : 'principalId',
			width : 40,
			align : "center",
			hidden : true
		},{
			title : '名称',
			field : 'principalName',
			width : 40,
			align : "center",
			editable: true,
			hidden : false
		},{
			title : '类型',
			field : 'principalType',
			width : 40,
			align : "center",
			editable: true,
			hidden : false,
			formatter : function(cellvalue, rowObject, rowIndex) {
				if (cellvalue == 1) {
					return "角色";
				}
				if (cellvalue == 2) {
					return "部门";
				}
				if (cellvalue == 3) {
					return "人员";
				}
				if (cellvalue == 4) {
					return "动态角色";
				}
				if (cellvalue == 5) {
					return "用户组";
				}
				return "未定义";
			}
		}/*,{
			title : '权限',
			field : 'permission',
			width : 40,
			align : "center",
			editable: true,
			hidden : false,
			formatter : function(cellvalue, rowObject, rowIndex) {
				if (cellvalue == 1) {
					return "隐藏";
				}
				if (cellvalue == 2) {
					return "只读";
				}
				if (cellvalue == 3) {
					return "读写";
				}
				if (cellvalue == 4) {
					return "管理";
				}
				return "未定义";
			}
		}*/,{
			title : '操作',
			field : 'Opt',
			width : 240,
			fixed : true,
			align : "center",
			formatter : function(cellvalue, rowObject, rowIndex) {
				var s = '';
				/* if(rowObject.tenantId!=''&&rowObject.tenantId!=null&&rowObject.tenantId!="null"){ */
					s += '<a href="javascript:void(0);" onclick="deletePermission(\'' + rowObject.id + '\');">删除</a>';
				/* }else{
					s+='<font color="red">系统默认授权,不可删除!</font>';
				} */
				/* s = '<a href="javascript:void(0);" onclick="editPermission(\'' + rowObject.id + '\',\'' + rowObject.principalName + '\',\'' + rowObject.permission + '\');">编辑</a>'; */
				return s;
				/*var s = '';
				s += '<a href="javascript:void(0);" onclick="deletePermission(\'' + rowObject.id + '\');">删除</a>';
				return s;*/
			}
		} ]]
	});
	
	//获取指定流程定义的所有版本号
	$.ajax({
		async : false,  
	    cache : false,  
        type: "GET",
        url:ctx+'/procDef/getProcDefIds?processDefinitionKey=${procDefKey}',
        dataType: "json",
        success: function(data){
        	$.each(data, function(i,item){
    			$("#procDefVersion").append("<option value='"+item+"'>版本"+(data.length-i)+"</option>");//为Select追加一个Option(下拉项)
    		});
         }
	});
	$("#back1").click(function(){
		history.back();
	});
	
});

var frameID = newGuid();
function getRole(urd){
	var objectGuid=$('#processDefinitionId').val();
	if(objectGuid==""){//流程定义对象ID(例如luohufawen:10:2494)
		alert("请选择流程");
		return false;
	}
	if($('#taskDefKey').val()!=''){//流程中任务节点的Id
		objectGuid=objectGuid+":"+$('#taskDefKey').val();
	}
	openCurWindow({
			id : frameID,
			src : '${ctx}/rc8Department/userChoicePage?urd='+urd+'&hidePrType=2,4',
			destroy : true,
			title : '角色选取',
			width : ($(window).width())*0.6 + 'px',
			height : 450,
			modal : true
	});
}


/* urd：表示是人员、角色、部门中的一个 */
/* objectType：表示设置的是流程的权限 */
function choice(returnvalue,urd) {
	var objectGuid=$('#processDefinitionId').val();
	if($('#taskDefKey').val()!=''){//流程中任务节点的Id
		objectGuid=objectGuid+":"+$('#taskDefKey').val();
	}
	//var returnvalue = window.showModalDialog(url+"?hidePrType=2,4&urd="+urd,"","dialogWidth=635px;dialogHeight=430px;status:no;");
	if(returnvalue!=null){
		$.ajax({
        	type : "POST",
			url : "${ctx}/objPerm/save",
			dataType:'JSON',
			data : {objectGuid:objectGuid,objectType:"2",principalGuid:returnvalue[0],principalName:returnvalue[1],depts:returnvalue[2],principalType:urd,permission:returnvalue[3],roleGroupId:returnvalue[4]},
			beforeSend : function() {
			},
			error : function(data) {
				alert("添加失败");
			},
			success : function(data) {
				alert("添加成功");
				grid.datagrid({url:'${ctx}/objPerm/getPerm/list',queryParams:{
						'objectGuid':objectGuid
					 }
				});
			}
		});
	}
}

function selectproc(){
	var principalType=$('#principalType').val();
	var nameCondition=$('#nameCondition').val();
	var processDefinitionId=$('#procDefVersion').val();
	if($("#procDefVersion option:selected").text()=="版本1"){//当是第一个版本的时候，禁用复制权限按钮
		$('#copyPerm').prop("disabled",true);
	}else{
		$('#copyPerm').prop("disabled",false);
	}
	reloadgrid(principalType,nameCondition,processDefinitionId);
}

function reloadgrid(type,nameCondition,processDefinitionId){
	var objectGuid="${processDefinitionId}";
	if(processDefinitionId!=""&&processDefinitionId!=null){
		objectGuid=processDefinitionId;
	}
	var taskDefKey=$('#taskDefKey').val();
	if(taskDefKey!=""){
		objectGuid=objectGuid+":"+taskDefKey;
	}
	grid.datagrid({url:'${ctx}/objPerm/getPerm/list',queryParams:{
			'objectGuid':objectGuid,
			'principalType':type,
			'nameCondition':nameCondition
		 }
	});
}


function deletePermission(id){
	$( "#dialog-confirmdelete" ).dialog({
		title:'确认删除',
		resizable: false,
		width : 200,
		height:150,
		modal: true,
		buttons:[{
			text:'确定',
			handler:function() {
				$.get(ctx+'/objPerm/delete?id='+id+'&t='+new Date(),function(data){
					alert("删除成功");
					reloadgrid("","","");//$('#principalType').val()
				});
				$("#dialog-confirmdelete").dialog( "close" );
			}
		},
		{
			text:'取消',
			handler: function() {
				$("#dialog-confirmdelete").dialog( "close" );
			}
		}]
	});
}

//复制授权，只有最新版本时，存在该功能
function copyPerm(isWhole){
	$( "#dialog-confirmCopy" ).dialog({
		title:'确认复制',
		resizable: false,
		width : 200,
		height:150,
		modal: true,
		buttons:[{
			text:'确定',
			handler:function() {
				$.get(ctx+'/objPerm/copyPerm?processDefinitionId='+$('#latestProcessDefinitionId').val()+'&taskDefKey=${taskDefKey}&isWhole='+isWhole+'&t='+new Date(),function(data){
					var str = JSON.parse(data); 
					alert(str.msg);
					reloadgrid($('#principalType').val(),"","");
				});
				$("#dialog-confirmCopy").dialog( "close" );
			}
		},
		{
			text:'取消',
			handler: function() {
				$("#dialog-confirmCopy").dialog( "close" );
			}
		}]
	});
}

</script>
</html>