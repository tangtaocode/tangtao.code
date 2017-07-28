<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv=Content-Type content="text/html;charset=utf-8">
<meta http-equiv=X-UA-Compatible content="IE=edge">
<title>深圳市罗湖区审批平台-综合窗口</title>
</head>
<script type="text/javascript">
var g;


	
function initComplete(){
	g = $("#dataBasic").quiGrid({

		columns: [ 
					{ display: '主管部门', name: 'BUREAUNAME', align: 'center', width: "30%"},
					{ display: '事项名称', name: 'APPROVEITEMNAME', align: 'center', width: "40%"},
					{ display: '操作', name: '',align: 'center', width: "20%",
						render: function(rowdata, rowindex, value, column){
							return '<input type="button" onclick="startBusi(\'' + rowdata.APPROVEITEMGUID + '\');" value="开始处理"/>';
						}

				    }
	         ], 

	        url:'${ctx}/togetherWindow/pointItemList',sortName:'TIMELIMIT',rownumbers:true,percentWidthMode:true,checkbox:false,
	        height: '100%', width:"100%",pageSize:20,enabledEdit: true,clickToEdit: false
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
<body>
	<div class="box2" panelTitle="查询" id="searchPanel">
		<form action="${ctx}/togetherWindow/pointItemList" id="queryForm" method="post" align="center">
		
		<table>
			<tr>
				<td>部门:</td>
				<td>
					<select name="bureauGUID" id="bureauGUID" prompt="全部" url="${ctx}/bureau/wbjSelect" boxWidth="200" boxHeight="200" selWidth="180"></select>
				
				</td>
				
				<td>事项名称：</td>
				<td><input type="text" name="approveItemName" style="width:200px"/></td>
			
				<td>
					<button type="button" onclick="searchHandler();"><span class="icon_find">查询</span></button>
					<button type="reset"><span class="icon_clear">重置</span></button>
				</td>
			</tr>
		</table>
		
		</form>
	</div>

	<div class="padding_right5">
		<div id="dataBasic"></div>
    </div>
</body>
</html>