<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>基本表格模板</title>

<!--框架必需start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/framework.js"></script>
<!--框架必需end-->
<!--数据表格start-->
<script src="${ctx}/static/QUI/libs/js/table/quiGrid.js" type="text/javascript"></script>
<!--数据表格end-->

<script src="${ctx}/static/risesoft/js/myCore.js"></script>
 

<script type="text/javascript">
	function chartHandler(){
		var year = $("#year").val();
		var month = $("#month").val();
		var quart = $("#quart").val();
		window.top.openNewTab("业务量统计","${ctx}/chart/bar?year="+year+"&month="+month+"&quart="+quart,""); 
	}
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
	function pieHandler(){
		var year = $("#year").val();
		var month = $("#month").val();
		var quart = $("#quart").val();
		window.top.openNewTab("满意率统计","${ctx}/chart/pie?year="+year+"&month="+month+"&quart="+quart,""); 
	}
</script>
</head>
<body>
	
	<div class="box2" id="searchPanel">
		<form action="${ctx }/chart/businessList" id="queryForm" method="post" align="center">
		<table>
			<tr>
				<td>年度:</td>
				<td>
				<input id="year" name="year" type="text" class="Wdate" value="${year }" onfocus="WdatePicker({dateFmt:'yyyy'});"/></input></td>			
				<td>&nbsp;&nbsp;&nbsp;月份:</td>
				<td>
				<input id="month" name="month" type="text" class="Wdate" value="${month }" onfocus="WdatePicker({dateFmt:'MM'});"/></input></td>
				<td>&nbsp;&nbsp;&nbsp;季度:</td>
				<td>
				<select id="quart" name="quart" selectedValue="${quart }">
					<option value=""></option>					
					<option value="1">第一季度</option>
					<option value="2">第二季度</option>
					<option value="3">第三季度</option>
					<option value="4">第四季度</option>
				</select>
				</td>
							
				<td><button type="button" onclick="searchHandler()"><span  style="cursor:pointer;" class="icon_find">查询</span></button>
				<button type="button" onclick="chartHandler()"><span  style="cursor:pointer;" class="icon_chart_bar">业务量统计</span></button>
				<!-- <button type="button" onclick="pieHandler()"><span  style="cursor:pointer;" class="icon_chart_pie">满意率统计</span></button> -->
				</td>
			</tr>
		</table>
		</form>
	</div>
	<div class="padding_right5">
		<div id="maingrid"></div>
    </div>

<script type="text/javascript">
	    //定义远程数据
	   
        var g;
		function initComplete(){
			 g = $("#maingrid").quiGrid({
		       columns: [ 
						{ display: '部门', name: 'BUREAUNAME', align: 'center', width: "20%"},
						{ display: '业务量', name: 'INT1',align: 'center', width: "20%"},
						{ display: '评价量', name: 'INT5',align: 'center', width: "20%"},
						{ display: '满意率', name: 'INT7',align: 'center', width: "20%"},
						{ display: '不满意率', name: 'INT8',align: 'center', width: "20%"}
		         ], 
		        url:"${ctx}/chart/businessList",rownumbers:true,percentWidthMode:true,checkbox:false,
		        pageSize:20,
		       	params:[{pageNo:"2"},{pageSize:"20"}],
		        height: '100%', width:"100%"
         	});
		}
		
		
		function startOrg(guid){
			Dialog.open({URL:"${ctx}/windowApprove/code?guid="+guid,Title:"填写个人/机构代码",AutoClose:25,Width:500,Height:330});
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
		
	//添加
	function addUser() {
		top.Dialog.open({URL:"${ctx}/static/QUI/sample_skin/normal/user-management-content.html",Title:"新增",Width:500,Height:330}); 
	}
	
	//批量删除
	function deleteUser() {
		top.Dialog.alert("向后台发送ajax请求来批量删除。见JAVA版或.NET版演示。");
	}
	
	//导入
	function importUser() {
		top.Dialog.alert("见JAVA版或.NET版演示。");
	}
	//导出
	function exportUser() {
		top.Dialog.alert("见JAVA版或.NET版演示。");
	}
	
	//导出所有
	function exportUser2() {
		top.Dialog.alert("见JAVA版或.NET版演示。");
	}
	
	//查看
	function onView(rowid){
		alert("选择的记录Id是:" + rowid );
		top.Dialog.open({URL:"${ctx}/static/QUI/sample_skin/normal/user-management-content2.html",Title:"查看",Width:500,Height:330}); 
	}
	
	//修改
	function onEdit(rowid){
		top.Dialog.alert("见JAVA版或.NET版演示。");
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
</body>
</html>
