<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<title>运行中的流程实例</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery/qtip2/jquery.qtip.min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/risesoft/css/fileflow.css" />

<script type="text/javascript" src="${ctx}/static/jquery/layer/layer.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/qtip2/jquery.qtip.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/tools/jquery.outerhtml.js"></script>
<script type="text/javascript" src="${ctx}/static/risesoft/js/activiti/workflow.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/tools/jquery.json-2.4.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/form/jquery.form.js"></script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true,border:true">
			<table id="list1"></table>
	</div>
</body>
<script type="text/javascript">
    var grid = $('#list1');
	$(document).ready(function() {
		grid.datagrid({
			url : '${ctx}/workflowInstance/running/list',
			toolbar : '#toolbar',
			fit : true,
			fitColumns : true,
			striped : true,
			rownumbers : true,
			pagination : true,//分页控件，要分页时设置为true
			pageSize : 30,
			border : true,
			nowrap : false,
			columns : [[ {
				title : '流程执行ID',
				field : 'id',
				width : 40,
				align : "center"
			}, {
				title : '流程实例ID',
				field : 'processInstanceId',
				width : 40,
				align : "center"
			},{
				title : '流程定义ID',
				field : 'processDefinitionId',
				width : 55,
				align : "center",
				hidden : false
			}, {
				title : '当前节点',
				field : 'activityName',
				width : 55,
				align : "center",
				hidden : false
			},  {
				title : '是否挂起',
				field : 'suspended',
				width : 50,
				align : "center",
				formatter : function(cellvalue, rowObject, rowIndex) {
					if (cellvalue) {
						return '已挂起';
					} else {
						return '未挂起';
					}
				}
			},{
				title : '操作',
				field : 'opt',
				width : 240,
				fixed : true,
				align : "center",
				formatter : function(cellvalue, rowObject, rowIndex) {
                    var s = '<a href="javascript:void(0);" onclick="deleteProcessInstance(\'' + rowObject.processInstanceId + '\');">删除</a>';
                    if (rowObject.suspended) {
                        s += '&nbsp;|&nbsp;<a href="javascript:void(0);" onclick="switchSuspendOrActive(\'active\',\'' + rowObject.processInstanceId + '\');">激活</a>';
                    } else {
                        s += '&nbsp;|&nbsp;<a href="javascript:void(0);" onclick="switchSuspendOrActive(\'suspend\',\'' + rowObject.processInstanceId + '\');">挂起</a>';
                    }
                    s += '&nbsp;|&nbsp;<a href="javascript:void(0);" onclick="history(\'' + rowObject.processInstanceId + '\');">历程</a>';
                    s += '&nbsp;|&nbsp;<a href="javascript:void(0);" onclick="graphTrace(\'\',\'' + rowObject.processDefinitionId + '\');">流程图</a>';
                    return s;
                }
			} ]]
		});
    });
	
	function switchSuspendOrActive(state, processInstanceId) {
        $.getJSON('${ctx}/workflowInstance/switchSuspendOrActive', {
            'state' : state,
            'processInstanceId' : processInstanceId
        }, function(data, textStatus, jqXHR) {
        	grid.datagrid('reload');
            alert(data.msg);
        });
    }
	
	//删除流程实例
	function deleteProcessInstance(processInstanceId) {
		$.getJSON('${ctx}/workflowInstance/delete?type=1&processInstanceId=' + processInstanceId, function(data, textStatus, jqXHR) {
			grid.datagrid("reload");
			alert(data.msg);
		});
	}
	
	function history(processInstanceId) {
		var url='${ctx}/worklist/history/' + processInstanceId + '/show';
		window.parent.openTab("实例["+processInstanceId+"]的历程",url);
    }
</script>
</html>