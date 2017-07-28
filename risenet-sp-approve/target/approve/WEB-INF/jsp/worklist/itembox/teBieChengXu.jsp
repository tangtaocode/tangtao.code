<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>基本表格模板</title>
<!--框架必需start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/language/cn.js"></script>
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/framework.js"></script>
<script type="text/javascript" src="${ctx}/static/risesoft/js/myCore.js"></script>
<link href="${ctx}/static/QUI/libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="${ctx}/static/QUI/" scrollerY="false"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->
<!--数据表格start-->
<script src="${ctx}/static/QUI/libs/js/table/quiGrid.js" type="text/javascript"></script>
<!--数据表格end-->
<!--表单异步提交start-->
<script src="${ctx}/static/QUI/libs/js/form/form.js" type="text/javascript"></script>
<!--表单异步提交end-->
<!-- 日期控件start -->
<script type="text/javascript" src="${ctx}/static/js/My97DatePicker/WdatePicker.js"></script>
<!-- 日期控件end -->
<!--箭头分页start-->

<script type="text/javascript" src="${ctx}/static/QUI/libs/js/nav/pageArrow.js"></script>

<!--箭头分页end-->

<script type="text/javascript">
	    //定义远程数据
        var g;
	    var ret;
	    var winWidth;
		var winHeight;
		function initComplete(){
			size();
			 g = $("#maingrid").quiGrid({
		       columns: [ 
			                { display: '业务流水号', name: 'declaresn', align: 'center', width: "10%"},
			                { display: '办理状态', name: 'state', align: 'center', width: "15%"},
			                { display: '文件标题', name: 'documentTitle', align: 'center', width: "15%",
			                	render: function (rowdata, rowindex, value, column){
			                		var s = '';
									if ($.trim(rowdata.taskAssignee).length == 0) {
										s = '<a href="javascript:void(0);" class="aColor" onclick="process(\'' + rowdata.taskId  + '\',\'' + rowdata.documentTitle + '\');">'+rowdata.documentTitle+'</a>';
									} else {
										s = '<a href="javascript:void(0);" class="aColor" onclick="process(\'' + rowdata.taskId  + '\',\'' + rowdata.documentTitle + '\');">'+rowdata.documentTitle+'</a>';
									}
									return s;
								}
			                },
			                { display: '开始时间', name: 'taskCreateTime',align: 'center', width: "15%"},
			                { display: '到期时间', name: 'taskDueDate',align: 'center', width: "15%"},
			                { display: '时限', name: 'taskDuration',align: 'center', width: "10%",
			                	render: function (rowdata, rowindex, value, column){
				                	var s = rowdata.taskDuration;
									if (s!=null) {
										return s;
									} else {
										return '--';
									}
								}
			                },
			                { display: '发送人', name: 'taskSender',   align: 'center', width: "10%"},
			                { display: '操作', name: 'taskAssignee',   align: 'center', width: "10%" ,
			                	render: function (rowdata, rowindex, value, column){
				                	var s = '';
									s += '<a href="javascript:void(0);" class="aColor" onclick="history(\'' + rowdata.processInstanceId + '\',\'' + rowdata.documentTitle + '\');">历程</a>';
									return s;
								}
			                }
		                	
		         ], 
		         url : '${ctx}/sp/worklist/teBieChengXu/list',rownumbers:true,pageSize:30,percentWidthMode:true,usePager:true,
		         pageSizeOptions:[10, 20, 30, 40, 50],height: '100%', width:"100%"
         	});
			
			 $("#searchPanel").bind("stateChange",function(e,state){
				g.resetHeight();
			});
			
		}
		
		//办理页面
		function process(taskId,documentTitle) {
			var url = '${ctx}/sp/document/edit?itembox=teBieChengXu&taskId=' + taskId;
			window.parent.openTab(documentTitle,url);
		}
		
		function size() {
			winWidth = $(window).width();
			winHeight = $(window).height() - 40;
		}

		
		//历程页面
		function history(processInstanceId,documentTitle){
	    	Dialog.open({URL:"${ctx}/sp/worklist/history/show?processInstanceId="+processInstanceId,Title:"历程（"+documentTitle+"）",Width:winWidth*0.8,Height:winHeight*0.8});
	    }
		
		//查询
		function searchHandler(){
			var startTime = $("#startTime").val();
			var endTime = $("#endTime").val();
			if(startTime!=""&&endTime!=""&&startTime>endTime){
				alert("时间选择有误，请重新选择!");
				return;
			}
			//得到查询参数
			var query = $("#queryForm").formToArray(); 
			//将查询参数传给grid表格
			g.setOptions({ params: query});
			//页号重置为1
			g.setNewPage(1);
			//重新加载数据
			g.loadData();
		}
		
		//重置
		function resetHandler(){
		   //表单常规元素还原
		   $("#queryForm")[0].reset();
		   //下拉框还原
		   $("#queryForm").find("select").render();
		   //重新查询
		   searchHandler();
		}
		
    //刷新表格 表单提交的回调
    function afterFormSubmit(){
        g.loadData();
    }
 
</script>	
</head>
<body>
	<div class="box2" panelTitle="查询" id="searchPanel">
		<form action="${ctx}/sp/worklist/teBieChengXu/list" id="queryForm" method="post" align="center">
		<table>
			<tr>
				<td>文件标题：</td>
				<td><input type="text" name="documentTitle" id="documentTitle" 
							style="width: 100px;"/></td>
				 <td>发送人：</td>
				<td>
					<input type="text" name="taskSender" id="taskSender" 
							style="width: 100px;"/>
				</td>
				<!--<td>当前办理人：</td>
				<td>
					<input type="text" name="taskAssignee" id="taskAssignee" 
							style="width: 100px;"/>
				</td> -->
				<td>开始时间：</td>
				<td>
					<input  class="date"  type="text" id="startTime" name="startTime"/>
					--至--
					<input  class="date"  type="text" id="endTime" name="endTime"/>
				</td>
				<td><button type="button" onclick="searchHandler()"><span class="icon_find">查询</span></button></td>
			</tr>
		</table>
		</form>
	</div>

	<div class="padding_right5">
		<div id="maingrid"></div>
    </div>


</body>
</html>
