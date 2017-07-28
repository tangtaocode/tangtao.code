<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<title>工作流中间件-待办件</title>
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
	<div id="toolbar" class="toolbar" align="center">
				<table border="0" cellpadding="0" cellspacing="0" style="width: 100%;background-color:#CCEEFF;">
					<tr>
						<td align="left" style="width: 60%;">
							表单名称 :
							<input type="text" id="formName" name="formName" value="" style="width: 30%;"/>
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" id="find" >查找</a>
						</td>
						<td align="right" style="width: 40%;">
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" id="queding" >确定</a>
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" id="guanbi" >关闭</a>
						</td>
					</tr>
				</table>
			</div>
			<table id="list1"></table>
	</div>
	
</body>
<script type="text/javascript">
var width = $(window).width();
var height = $(window).height() - 40;
var frameID = newGuid();
var grid = $('#list1');
$(document).ready(function() {
	grid.datagrid({
		url : '${ctx}/eform/bind/eFormList',
		toolbar : '#toolbar',
		//view : detailview,
		fit : true,
		singleSelect : true,
		remoteSort:false,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		//pagination : true,//分页控件，要分页时设置为true
		pageSize : 30,
		border : true,
		nowrap : false,
		columns : [ [
			{
				title : "",
				field : "id",
				checkbox : true
			},{
				title : '表单ID',
				field : 'TEMP_ID',
				width : 50,
				align : "center",
			}, {
				title : '表单名称',
				field : 'TEMPLATENAME',
				width : 100,
				align : "center",
			}
		] ]
	});
	
});

	$('#queding').click(function() {
			queding();
		});
	
	$('#guanbi').click(function() {
			guanbi();
		});
	
	$('#find').click(function() {
			find();
		});

//确定
function queding(){
	var efromList=$('#list1').datagrid('getSelections');
	var num=efromList.length;
	if(num<=0){
		alert("请选择表单!");
		return;
	}else{
		parent.$('#formName').val(efromList[0].TEMPLATENAME) ;
		parent.$('#formId').val(efromList[0].TEMP_ID) ;
		closeCurWindow(parent.frameID,'close');
	}
}

//关闭
function guanbi(){
	closeCurWindow(parent.frameID,'close');
}

//查找
function find(){
	var formName = $("#formName").val();
	grid.datagrid({
			url : '${ctx}/eform/bind/eFormList',
			queryParams:{
			  'formName':formName
		  }
	});
}

</script>
</html>