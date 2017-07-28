<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<title>表单与流程任务的绑定</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery/qtip2/jquery.qtip.min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/risesoft/css/fileflow.css" />

<script type="text/javascript" src="${ctx}/static/jquery/qtip2/jquery.qtip.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/tools/jquery.outerhtml.js"></script>
<script type="text/javascript" src="${ctx}/static/risesoft/js/activiti/workflow.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/tools/jquery.json-2.4.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/form/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/layer/layer.min.js"></script>

</head>
<body>
	<div align="center" style="width:100%; background-color:#CCEEFF;">
		<div id="searchBar" align="center" style="padding-left:10px;padding-top:10px;padding-bottom:10px;">
			版本：
			<select id="procDefVersion" name="procDefVersion">
				
			</select>
			&nbsp;&nbsp;
			<input type="button" id="processDefinitionId_select" name="processDefinitionId_select" onclick="selectproc();" value=" 确 定 ">
		</div>
	</div>
	<div class="easyui-layout" data-options="fit:true,border:true">
		<div data-options="region:'center',title:'表单绑定列表(${procDefName}-->${taskDefName})',border:true">
			<div id="toolbar" class="toolbar"  align="left" style="width:100%; background-color:#CCEEFF;">
				<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" id="button01" >增加绑定</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-back',plain:true" id="back01" >返回</a>
			</div>
			<table id="list1"></table>
		</div>
	</div>
	<div id="dialog-delete">
		<p>确定删除当前事项?</p>
	</div>
</body>
<script type="text/javascript">
	var grid = $("#list1");
	var processDefinitionKey="${procDefKey}";
	$(document).ready(function(){
		grid.datagrid({
			url : '${ctx}/form/bind/getList?processDefinitionId=${processDefinitionId}&taskDefKey=${taskDefKey}',
			toolbar : '#toolbar',
			fit : true,
			fitColumns : true,
			striped : true,
			singleSelect:true,
			rownumbers : true,
			border : true,
			nowrap : false,
			columns : [[ {
				title : '表单id',
				field : 'formId',
				width : 40,
				align : "center",
				editable: true,
				hidden : true
			},{
				title : '表单名称',
				field : 'formName',
				width : 40,
				align : "center",
				editable: true,
				hidden : false
			},{
				title : '流程ID',
				field : 'processDefinitionId',
				width : 40,
				align : "center",
				editable: true,
				hidden : true
			},{
				title : '任务ID',
				field : 'taskDefKey',
				width : 40,
				align : "center",
				editable: true,
				hidden : true
			},{
				title : '操作',
				field : 'id',
				width : 240,
				fixed : true,
				align : "center",
				formatter : function(cellvalue, rowObject, rowIndex) {
					var s = '';
					s += '<a href="javascript:void(0);" onclick="formBindDelete(\'' + rowObject.id + '\');">删除</a>';
					s += '&nbsp;|&nbsp;<a href="javascript:void(0);" onclick="formBindEdit(\'' + rowObject.id + '\');">修改</a>';
					return s;
				}
			} ]]
		});
		
		$('#button01').click(function() {
			formBindEdit('');
		});
		
		$('#back01').click(function() {
			history.back();
		});
		
		//获取指定流程定义的所有版本号
        $.ajax({
            type: "GET",
			async:false,
			cache:false,
            url: ctx+"/procDef/getProcDefIds",
            data: {processDefinitionKey:processDefinitionKey},
            dataType: "json",
            success:function(data){
    			$.each(data, function(i,item){
    				$("#procDefVersion").append("<option value='"+item+"'>版本"+(data.length-i)+"</option>");//为Select追加一个Option(下拉项)
    			});
    		}
        });
	});

	function formBindDelete(id) {
		$( "#dialog-delete" ).dialog({
			title:"确定删除",
			resizable: false,
			width : 200,
			height:130,
			left:200,
			top:200,
			modal: true,
			buttons:[{
				text:'确定',
				handler:function() {
					$.getJSON('${ctx}/form/bind/delete?id=' + id, function(data, textStatus, jqXHR) {
						alert(data.msg);
						selectproc();
					});
					$("#dialog-delete").dialog( "close" );
				}
			},{
				text:'取消',
				handler: function() {
					$("#dialog-delete").dialog( "close" );
				}
			}]
		});
	}

	var frameID = newGuid();
	function formBindEdit(id) {
		openCurWindow({
			id : frameID,
			src : '${ctx}/form/bind/edit?id=' + id +'&processDefinitionId=${processDefinitionId}&procDefName=${procDefName}&taskDefKey=${taskDefKey}&taskDefName=${taskDefName}',
			destroy : true,
			title : '表单绑定基本信息',
			width : 500,
			height : 300,
			modal : true
    	});
		/*$.layer({
			type : 2,
			title : '表单绑定基本信息',
			offset : [ '0px', '0px' ],
			closeBtn : [ 0, true ],
			bgcolor : '#fff',
			area : [ ($(window).width() - 10) + 'px', ($(window).height() - 10) + 'px' ],
			border : [ 5, 0.5, '#EF8016', true ],
			iframe : {
				src : '${ctx}/form/bind/edit?id=' + id +'&processDefinitionId=${processDefinitionId}&procDefName=${procDefName}&taskDefKey=${taskDefKey}&taskDefName=${taskDefName}'
			},
			end : function() {
				selectproc();
			}
		});*/
	}
	
	function selectproc(){
		var processDefinitionId=$('#procDefVersion').val();
		reloadgrid(processDefinitionId);
	}

	//重新加载jqgrid
	function reloadgrid(processDefinitionId){
		grid.datagrid({url:'${ctx}/form/bind/getList',queryParams:{
				'processDefinitionId':'${processDefinitionId}',
				'taskDefKey':'${taskDefKey}'
	 		}
		});
	}
</script>
</html>
