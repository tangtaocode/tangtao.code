<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>证照类别管理</title>

<script type="text/javascript">
var g;
function initComplete(){
	 g = $("#maingrid").quiGrid({
       columns: [ 
	                { display: '证照类别编号', name: 'DOCTYPECODE', align: 'center', width: "15%",showTitle:true},
	                { display: '', name: 'GUID', align: 'center', width: "0%",hide:true},
	                { display: '证照名称', name: 'DOCTYPENAME', align: 'center', width: "10%",showTitle:true},
	                { display: '出证单位', name: 'BUREAUNAME',align: 'center', width: "15%",showTitle:true}, 
	                { display: '备注', name: 'MEMO',align: 'center', width: "30%",showTitle:true},
	                { display: '证照信息项条数', name: 'TYPENUM',align: 'center', width: "10%",showTitle:true},
	                { display: '证照信息项', name: '',  align: 'center', width: "20%",
	                	render: function (rowdata, rowindex, value, column){
	                		var html = '<a href=${ctx}/doctypeinfo/findDocTypeInfo?guid='+rowdata.GUID+'&docname='+rowdata.DOCTYPENAME+'>维护<a>';
	                		return html;
	                	}
	                }
         ], 
        url:"${ctx}/businesstypemanger/wbjList",sortName:'GUID',rownumbers:true,percentWidthMode:true,checkbox:true,
       	pageSize:20,
        height: '100%', width:"100%",
        toolbar:{
       	 items:[
       		  {text: '新增', click: addUnit,    iconClass: 'icon_add'},
       		  { line : true },
       		 {text: '批量删除', click: deleteUnit, iconClass: 'icon_delete'},
    		  { line : true }
        		]
        	}
 	});
}

//流程管理
function handleFlowManage(){
	var row = g.getSelectedRows();
	var len = row.length;
    if(len<1) { 
    	Dialog.alert("至少选择一条记录！|提示",null);
		return;
	}else if(len>1){
		Dialog.alert("只能选择一条记录！|提示",null);
		return;
	}else if(len==1){
		var approveGuid = row[0].APPROVEITEMGUID;			
		Dialog.open({URL:"${ctx}/bureau/lcgl?approveGuid="+approveGuid,Title:"流程管理",Width:500,Height:330});
		 
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

//添加
function addUnit() {
Dialog.open({
	URL:"${ctx}/businesstypemanger/toadd",
	Title:"添加",Width:500,Height:350});
}
function closeDialog(data){
if(data == 1){
    Dialog.close();
    Dialog.alert("保存成功！");
    g.loadData();
}else{
    Dialog.alert("保存失败！");
}
}
//批量删除
function deleteUnit() {
var rows = g.getSelectedRows();
var rowsLength = rows.length;
var guids="";
for(var i=0;i<rowsLength;i++){
	guids+=rows[i].GUID+","
}
if(rowsLength == 0) {
	Dialog.alert("请选中要删除的记录!");
	return;
}
Dialog.confirm("确定要删除吗？",function(){
		$.post("${ctx}/businesstypemanger/delete",
				//获取所有选中行
				{guids:guids},
				function(result){
					handleResult(result);
				},
				"json");
	});
}
//删除后的提示

function handleResult(result){
if(result == 1){	
    Dialog.alert("删除成功！");
    g.loadData();
}else{
    Dialog.alert("删除失败！");
}
}		
//刷新表格 表单提交的回调
function afterFormSubmit(){
g.loadData();
}

</script>		

</head>
<body>
	<div class="box2" panelTitle="查询" id="searchPanel">
		<form action="${ctx}/businesstypemanger/bureauList" id="queryForm" method="post" align="center">
		<table>
			<tr>		
				<td>证照类型名称：</td>
				<td><input type="text" name="departmentName" style="width:200px" /></td>
				<td>选择出证单位:</td>
				<td>
					<%-- <select name="departmentGuid" id="departmentGuid" prompt="选择出证局" url="${ctx}/businesstypemanger/wbjSelect" boxWidth="200" boxHeight="200" selWidth="200"></select>	--%>	
				<select name="departmentGuid" id="departmentGuid" prompt="全部" url="${ctx}/bureau/wbjSelect" boxWidth="200" boxHeight="200" selWidth="180"></select>		 
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
