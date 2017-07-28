<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>基本表格模板</title>
<!--框架必需start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/language/cn.js"></script>
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/framework.js"></script>
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
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/form/datePicker/WdatePicker.js"></script>
<!-- 日期控件end -->

<script type="text/javascript">
	    //定义远程数据
        var g;
		function initComplete(){
			 g = $("#maingrid").quiGrid({
		       columns: [ 
							{ display: '办理状态', name: 'status', align: 'center', width: "10%"},
			                { display: '任务期限', name: 'taskLimitTime', align: 'center', width: "20%"},
			                { display: '任务到期时间', name: 'taskEndTime', align: 'center', width: "10%"},
			                { display: '业务编号', name: 'SN', align: 'center', width: "10%", isSort:false},
			                { display: '项目名称', name: 'approveItemName', align: 'center', width: "10%"},
			                { display: '申请单位(人)', name: 'declarePerson',align: 'center', width: "10%"},
			                { display: '受理人', name: 'person_Name',align: 'center', width: "10%"},
			                { display: '申请时间', name: 'createDate',   align: 'center', width: "10%",type:"hfstate"},
			                { display: '规定结束时间', name: 'estimateEndDate',   align: 'center', width: "10%" },
		                	 
		         ], 
		        url:"${ctx}/Handle/dbjlist",rownumbers:true,percentWidthMode:true,
		        height: '100%', width:"100%",
         	});
			
			 $("#searchPanel").bind("stateChange",function(e,state){
				g.resetHeight();
			});
			
		}
		
		 $.quiDefaults.Grid.formatters['hfstate'] = function (value, column) {
			    if(value==0){
			    	return "未回复";
			    }else{
			    	return "已回复";
			    }
		 }
	
		
		//查询
		function searchHandler(){
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
		

	
	//删除
	function onDelete(rowid,rowidx){
		top.Dialog.confirm("确定要删除该记录吗？",function(){
		  	top.Dialog.alert("向后台发送ajax请求来删除。见JAVA版或.NET版演示。");
		});
	}
		
    //刷新表格 表单提交的回调
    function afterFormSubmit(){
        g.loadData();
    }
 
</script>	
</head>
<body>
	<div class="box2" panelTitle="查询" id="searchPanel">
		<form action="${ctx}/sbApprove/approveList" id="queryForm" method="post" align="center">
		<table>
			<tr>
				<td>业务编号：</td>
				<td><input type="text" name="approveName"  clearable="true"/></td>
				<td>申请单位(人):</td>
				<td><input type="text" name="approveName"  clearable="true"/></td>
				<td>项目(事项)名称:</td>
				<td><input type="text" name="approveName"  clearable="true"/></td>
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
