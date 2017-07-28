<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>资产维修</title>
<!-- 日期控件start -->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/form/datePicker/WdatePicker.js"></script>
<script type="text/javascript">
var g_pageNo = 1;

var g_pageSize = 20;

//数据表格使用

  var g;

  var gridData;
  var jyData={"list":[{"value":"","key":""},{"value":"1","key":"是"},{"value":"0","key":"否"},]};
  var clData={"list":[{"value":"","key":""},{"value":"1","key":"有效"},{"value":"0","key":"无效"},]};
	
function initComplete(){
	g = $("#dataBasic").quiGrid({

	    columns:[
	        { display: '物料编码', name: 'MATERIAL_CODE',     align: 'center', width: "20%",editor: { type: 'text',maxlength:20,tip:'不能超过20个字符' }},

	        { display: '物料名称', name: 'MATERIAL_NAME',    align: 'center', width: "20%",editor: { type: 'text',maxlength:20,tip:'不能超过20个字' }},
	        
	        { display: '入库数量', name: 'MATERIAL_NUM',    align: 'center', width: "20%",editor: { type: 'text',maxlength:20,tip:'不能超过20个字' }},
	        
	        { display: '领用数量', name: 'USING_NUM',    align: 'center', width: "20%",editor: { type: 'text',maxlength:20,tip:'不能超过20个字' }},
	        
			{ display: '维修次数', name: 'REPAIR_NUM',    align: 'center', width: "20%",editor: { type: 'text',maxlength:20,tip:'不能超过20个字' }}
	       ],

	        url:'${ctx}/materialManage/materialMixQuery',sortName:'USING_TIME',rownumbers:true,percentWidthMode:true,checkbox:false,
	        height: '100%', width:"100%",pageSize:20,enabledEdit: false,clickToEdit: false,onDblClickRow:function(rowdata, rowindex){
                
              },
	});
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

</script>
</head>
<body>
<div class="box2" panelTitle="查询" id="searchPanel">
		<form action="${ctx}/materialManage/materialList" id="queryForm" method="post" align="center">
		<table>
			<tr>
				<td>时间：</td>
				<td><input type="text" name="start_time" id="start_time" class="date"/>
				--
				<input type="text" name="end_time" id="end_time" class="date"/>
				</td>
				<td>物料编号：</td>
				<td><input type="text" name="material_code"   style="width:140px;"/></td>
				<td>物料名称：</td>
				<td><input type="text" name="material_name"   style="width:140px;"/></td>
				<td>&nbsp;&nbsp;&nbsp;<button type="button" onclick="searchHandler()"><span class="icon_find">查询</span></button></td>
			</tr>
		</table>
		</form>
		
	</div>
		<div class="padding_right5">
		<div id="dataBasic"></div>
    </div>

</body>
</html>