<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>数据规范监察</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!--框架必需start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/framework.js"></script>
<script src="${ctx}/static/QUI/libs/js/table/quiGrid.js" type="text/javascript"></script>
<!--数据表格end-->
<!--表单异步提交start-->
<script src="${ctx}/static/QUI/libs/js/form/form.js" type="text/javascript"></script>
<!--表单异步提交end-->
<script type="text/javascript" src="${ctx}/js/portal.js"></script>
<script type="text/javascript" src="${ctx}/js/layer/layer.min.js"></script>
<style>
a{color:blue;}
a:hover {color: blue;} 
</style>
</head>
<body>
<div class="box2" panelTitle="数据规范性问题查询" id="searchPanel">
<form action="" method="post" id="hourform">
<table>
			<tr>
				<td>申办流水号：</td>
				<td>
					<input type="text" id="sblsh" name="sblsh"  />
				</td>
				<td>项目名称：</td>
				<td>
					<input type="text" id="sxmc" name="sxmc"/>
				</td>
				<td><button type="button" onclick="searchHandler()"><span class="icon_find">查询</span></button></td>
				<td><button type="button" onclick="resetHandler()"><span class="icon_reload">重置</span></button></td>
			</tr>
		</table>
</form>
</div>
	<div>
		<div id="maingrid"></div>
    </div>
<!--qui加载数据表格-->
<script type="text/javascript">
//查询
function searchHandler(){
	//得到查询参数
	var query = $("#hourform").formToArray(); 
	//将查询参数传给grid表格
	grid.setOptions({ params: query});
	//页号重置为1
	grid.setNewPage(1);
	//重新加载数据
	grid.loadData();
}
//重置
function resetHandler(){
   //表单常规元素还原
   $("#hourform")[0].reset();
   //下拉框还原
   $("#hourform").find("select").render();
   //重新查询
   searchHandler();
}
var grid = null;
function initComplete(){
	grid = $("#maingrid").quiGrid({
		columns:[
                 { display: '', name: 'GUID', align: 'center', hide:true, width: "0%"}, 
                 { display: '申办流水号', name: 'SBLSH', align: 'center', width: "15%"},
                 { display: '项目名称', name: 'SPSXMC', align: 'center', width: "45%"},
                 { display: '审批事项编码', name: 'SPSXBH', align: 'center', width: "15%"},
                 { display: '检查时间', name: 'CHECK_TIME', align: 'center', width: "15%"},
                 { display: '问题描述', name: 'SBLSH', align: 'center', width: "10%",
                		render: function (rowdata, rowindex, value, column){
		            		 var view = "<a href='javascript:void(0);' onclick=\"javascript:openProblemListWin('"+rowdata.SBLSH+"')\">问题详情</a>";
			                 return view;
		            	}}
		         ],
		 url:"/checkData/checkJianchaData",rownumbers:true,percentWidthMode:true,checkbox:false,fixedCellHeight:true,
	        height: '100%', width:"100%",pageSize:20
     	});
}
</script>
</body>
</html>