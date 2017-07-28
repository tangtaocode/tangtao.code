<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/static/common/head.jsp"%>
<title>工作流中间件-权限设置</title>
<link type="text/css" rel="stylesheet" href="${ctx}/static/jquery/jquery-ui/css/redmond/jquery-ui-1.10.3.custom.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery/jgrid/css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery/jgrid/css/ui.multiselect.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/risesoft/css/fileflow.css" />

<script type="text/javascript" src="${ctx}/static/jquery/jquery-ui/js/jquery-ui-1.10.3.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/tools/jquery.json-2.4.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/jgrid/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/jgrid/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/form/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/databind/DataBind.js"></script>

<script src="${ctx }/static/jquery/jui/extends/timepicker/jquery-ui-timepicker-addon.js" type="text/javascript"></script>
<script src="${ctx }/static/jquery/jui/extends/i18n/jquery-ui-date_time-picker-zh-CN.js" type="text/javascript"></script>
<script type="text/javascript">
function choice(url) {
	var returnvalue = window.showModalDialog(url+"?url="+setAddBtndisplay(),"","dialogWidth=800px;dialogHeight=420px;status:no;");
	if(typeof(returnvalue)!="undefined" && returnvalue.length>0)
	{
		//var result=returnvalue.split(";");
		//var result1=result[0].split(",");
		//var result2=result[1].split(",");
		for(var i=0;i<result1.length;i++)
		{
			$("#select_principal").append("<option value='"+result1[i]+"'>"+result2[i]+"</option>");   //为Select追加一个Option(下拉项)
		}
		$('#principalGuid').val(returnvalue[0]);
		$('#principalName').val(returnvalue[1]);
	}
	
}

function clearall()
{
	$('#id').val("");
	$('#principalGuid').val("");
	$('#principalName').val("");
	$("#select_principal option").remove();
	$('#prtype'+$('input[name=permission]').val()).prop("checked",false);
}
function editPermission(id,principalId,principalName,permission)
{
	clearall();
	$('#id').val(id);
	$('#principalGuid').val(principalId);
	$('#principalName').val(principalName);
	$("#select_principal").append("<option value='"+principalId+"'>"+principalName+"</option>");   //为Select追加一个Option(下拉项)
	$('#prtype'+permission).prop("checked",true);
	$("#dialog-add").dialog("open");
}

function deletePermission(id)
{
	$.get(ctx+'/objPerm/delete?id='+id+'&t='+new Date(),function(data){
		alert(data.msg);
		reloadgrid($('#principalType').val());
	});
}

function setAddBtndisplay()
{
	if($('#principalType').val()==1)
	{
		return "${ctx}/role/findAll";
	}
	if($('#principalType').val()==2)
	{
		return "${ctx}/department/findDeptById";
	}
	if($('#principalType').val()==3)
	{
		return "${ctx}/department/findDeptAndUserById";
	}
}

function addnew()
{
	clearall();
	$("#prtype2").prop("checked",true);
	$("#dialog-add").dialog("open");
}

function reloadgrid(type)
{
	$("#list1").jqGrid('setGridParam',{
		url:"${ctx}/objPerm/getPerm/list",
		postData:{
			'objectGuid':"${objectGuid}",
			'principalType':type
		}
	}).trigger("reloadGrid");
}
$(document).ready(function(){
	
	$('#objectGuid').val("${objectGuid}");
	$('#urd1').prop("checked",true);
	$('#principalType').val(1);
	
	$('#urd1').click(function(){
		reloadgrid(1);
		$('#principalType').val(1);
	});
	
	$('#urd2').click(function(){
		reloadgrid(2);
		$('#principalType').val(2);
	});
	
	$('#urd3').click(function(){
		reloadgrid(3);
		$('#principalType').val(3);
	});
	
	var grid = $("#list1");
	grid.jqGrid({
		url : '${ctx}/objPerm/getPerm/list?objectGuid=${objectGuid}&principalType=1',
		datatype : "json",
		colModel : [ {
			label : 'id',
			name : 'id',
			index : 'id',
			width : 40,
			align : "center",
			hidden : true
		},{
			label : 'principalId',
			name : 'principalId',
			index : 'principalId',
			width : 40,
			align : "center",
			hidden : true
		},{
			label : '名称',
			name : 'principalName',
			index : 'principalName',
			width : 40,
			align : "center",
			editable: true,
			hidden : false
		},{
			label : '权限',
			name : 'permission',
			index : 'permission',
			width : 25,
			align : "center",
			editable: true,
			edittype:"select",editoptions:{value:"2:只读;3:读写;4:管理;1:隐藏"},
			hidden : false,
			formatter : function(cellvalue, options, rowObject) {
				if (cellvalue == 2) {
					return "只读";
				}
				if (cellvalue == 3) {
					return "读写";
				}
				if (cellvalue == 4) {
					return "管理";
				}
				if (cellvalue == 1) {
					return "隐藏";
				}
			}
		}, {
			label : '操作',
			name : 'opt',
			index : 'opt',
			width : 180,
			fixed : true,
			sortable : false,
			resize : false,
			align : "center",
			formatter : function(cellvalue, options, rowObject) {
				var s = '';
				s = '<a href="javascript:void(0);" onclick="editPermission(\'' + rowObject.id + '\',\'' + rowObject.principalId + '\',\'' + rowObject.principalName + '\',\'' + rowObject.permission + '\');">编辑</a>';
				s += '&nbsp;|&nbsp;<a href="javascript:void(0);" onclick="deletePermission(\'' + rowObject.id + '\');">删除</a>';
				return s;
			}
		} ],
		caption : "权限管理列表",
		rowNum : 30,
		autowidth : true,
		rownumbers : true,
		height : '100%',
		rowList : [ 30, 50, 100 ],
		pager : '#pager1',
		multiselect : false,
		hidegrid : false,
		viewrecords : true,
		editurl: "${ctx}/objPerm/save",
		//sortname : 'id',
		//sortorder : "desc",			
		/* prmNames: {
			page:'page',
			rows:'rows', 
			sort:'sidx', 
			order:'sord'
		}, */
		jsonReader : {
			root : "items",
			page : "currpage",
			total : "totalpages",
			records : "totalrecords",
			repeatitems : false,
			id : "id"
		}
	});

	grid.jqGrid('navGrid', '#pager1', {
		edit : false,
		add : false,
		del : false
	});
	
	jQuery("#ed1").click( function() {
		alert("ed1");
		jQuery("#list1").jqGrid('editRow',"3");
	});
	
	$("#dialog-add").dialog({
		autoOpen : false,
		height : 500,
		width : 350,
		modal : true,
		buttons : {
			"保存" : function() {
				$("#addForm").ajaxSubmit({
					type : 'POST',
					dataType : 'json',
					url:'${ctx}/objPerm/save',
					success : function(responseText, statusText, xhr, $form) {
						if (responseText.success == true) {
							reloadgrid($('#principalType').val());
						} else {
							alert("新增失败!");
						}
					}
				});
				
				$("#dialog-add").dialog("close");
			},
			"取消" : function() {
				$("#dialog-add").dialog("close");
			}
		}
	});
	
});
</script>
</head>
<body>
<div class="header">
	<input type="button" id="add_new" name="add_new" onclick="addnew();" value="新增"/>
</div>
	<input type="radio" id="urd1" name="urd">角色管理&nbsp;
	<input type="radio" id="urd2" name="urd">部门管理&nbsp;
	<input type="radio" id="urd3" name="urd">用户管理
	<br>
	
		<!-- 这里暂时没用标签页，因为在切换标签的时候列表显示问题 -->
		<table id="list1"></table>
		<div id="pager1"></div>
		<!-- use to save type of user dept role -->
	

<div id="dialog-add" title="新增" style="display: none;">
	<form id="addForm" method="POST" action="${ctx}/objPerm/save">
	<table>
		<tr>
			<td>
				权限：
			</td>
			<td>
				<input type="radio" id="prtype2" name="permission" value="2">只读&nbsp;
				<input type="radio" id="prtype3" name="permission" value="3">读写&nbsp;
				<input type="radio" id="prtype4" name="permission" value="4">管理
				<input type="radio" id="prtype1" name="permission" value="1">隐藏&nbsp;
			</td>
		</tr>
		<tr>
			<td rowspan="2">
				人员：
			</td>
			<td>
				<input type="button" id="choice1" name="choice1" onclick="choice('${ctx}/department/userPermChoicePage');" value="新增"/>
			</td>
		</tr>
		<tr>
			<td>
				<select id="select_principal" name="select_principal" multiple style="width:230px;" size="18">
				</select>
			</td>
		</tr>
	</table>
	<input type="hidden" id="id" name="id" value="">
	<input type="hidden" id="objectGuid" name="objectGuid" value="">
	<input type="hidden" id="principalGuid" name="principalGuid" value="">
	<input type="hidden" id="principalName" name="principalName" value="">
	<input type="hidden" id="principalType" name="principalType" value="">
	</form>
</div>
</body>
</html>