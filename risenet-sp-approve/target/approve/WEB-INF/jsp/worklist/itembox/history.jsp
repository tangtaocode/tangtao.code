<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<title>工作流中间件-历程</title>
<style>
	.panel-header{
		background :#ADE8F6;
	}
</style>

</head>
<body>
	<div id="historyDiv" class="easyui-layout" data-options="fit:true,border:true">

	</div>
</body>
<script type="text/javascript">
	$(document).ready(function() {
		var winHeight;
		function size(){
			winHeight = ($(window).height())*0.95;
		}
		
		var procInstanceIds="${processInstanceId}".split(",");
		var bureauNames="${bureauName}".split(",");
		var num=procInstanceIds.length+1;//因为下面的for循环中的i是从1开始的
		for(var i=1;i<num;i++)
		{
			var pseStr=procInstanceIds[i-1].split(";");
			var processInstanceId=pseStr[0];
			var startTime=pseStr[1];
			var endTime=pseStr[2];
			var bureauName=bureauNames[i-1];
			//$('#historyDiv').append('<table bgcolor="#CCEEFF" width="100%" align="center" style="font-size:13px;"><tr><td></td><td>开始时间：'+startTime+'</td><td>到期时间:'+endTime+'</td></tr></table><table id="list'+i+'" style="height:97%;" title="'+bureauName+'"></table>');
			$('#historyDiv').append('<table bgcolor="#CCEEFF" width="100%" align="center" style="font-size:13px;"><tr><td></td><td>开始时间：'+startTime+'</td><td>到期时间:'+endTime+'</td></tr></table><table id="list'+i+'" style="height:97%;"></table>');
			$("#list"+i).datagrid({
				url : '${ctx}/sp/worklist/history/'+processInstanceId+'/json',
				fit : false,
				singleSelect : true,
				fitColumns : true,
				striped : true,
				height : winHeight,
				rownumbers : true,
				pagination : false,
				//pageSize : 10,
				border : true,
				nowrap : false,
				onLoadSuccess:function(data){
					for ( var i = 0; i < data.rows.length; i++) {
						if (data.rows[i].name.indexOf("不予批准")) {
							var tooltipReason = '不予批准原因：'+'<font color="red">'+data.rows[i].name.split("：")[2]+'</font>';
							addTooltip(tooltipReason,data.rows[i].id);  
						}
					}
	            }, 
				columns : [[
				{
					title : 'id',
					field : 'id',
					width : 40,
					align : "center",
					hidden : true
				}, {
					title : '发送人',
					field : 'taskSender',
					width : 20,
					align : "center"
				}, {
					title : '动作名称',
					field : 'actionName',
					width : 40,
					align : "center"
				}, {
					title : '收件人',
					field : 'assignee',
					width : 40,
					align : "center"
				} , {
					title : '任务名称',
					field : 'name',
					width : 40,
					align : "center"
				}, {
					title : '意见/描述',
					field : 'opinion',
					width : 100,
					align : "center",
					formatter : function(cellvalue, rowObject, rowIndex) {
						var s=cellvalue;
						if(s!=""){
							s ='<font color="red">'+s+'</font>';
						}
						return s;
					}
				}, {
					title : 'taskId',
					field : 'taskId',
					width : 40,
					align : "center",
					hidden : true
				},{
					title : '开始时间',
					field : 'startTime',
					width : 55,
					align : "center"
				}, {
					title : '结束时间',
					field : 'endTime',
					width : 55,
					align : "center"
				},{
					title : '办理时长',
					field : 'time',
					width : 55,
					align : "center"
				}]]
			});
		}
	});
	
	$('#back').click(function() {
		if(type=="todo"){
			window.parent.openTab("待办件",ctx + '/sp/worklist/todo');
		}
		if(type=="doing"){
			window.parent.openTab("在办件",ctx + '/sp/worklist/doing');
		}
		if(type=="done"){
			window.parent.openTab("办结件",ctx + '/sp/worklist/done');
		}
		if(type=="pause"){
			window.parent.openTab("暂停件",ctx + '/sp/worklist/pause');
		}
	});
	function addTooltip(tooltipContentStr,tootipId){
		  $('#'+tootipId).tooltip({  
		     position: 'right',
		     showEvent:'mouseenter',
		     content: tooltipContentStr   
		  }); 
	}
</script>
</html>
