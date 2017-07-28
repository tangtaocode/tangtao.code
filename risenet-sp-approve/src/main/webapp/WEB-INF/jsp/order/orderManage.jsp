<!--
	/**
 * @ FileName: ordersOnline.jsp
 * @Description: 局管理人员“预约管理”列表
 * @author chenbingni
 * @date 2015-12-24
 */
-->

<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>事项预约管理</title>
<link href="${ctx}/static/risesoft/css/style.css" rel="stylesheet" type="text/css"/>

<!--数据表格start-->

<script src="${ctx}/static/QUI/libs/js/table/quiGrid.js" type="text/javascript"></script>

<!--数据表格end-->

<!--箭头分页start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/nav/pageArrow.js"></script>
<!--箭头分页end-->

<script type="text/javascript">
//定义远程数据
var g;
var ret;
function initComplete(){
	 g = $("#maingrid").quiGrid({
       columns: [ 
					{ display: '委办局名称', name: 'BUREAUNAME', align: 'center', width: "25%"},
					{ display: '事项名称', name: 'APPROVEITEMNAME', align: 'center', width: "30%"},
	                { display: '可否预约', isAllowHide: false, align: 'center', width:"20%",
						render : function(rowdata, rowindex, value, column) {
							if(rowdata.ISORDER == '0') {
								ret = '<a href="#" title="更改为可预约" onclick="changePermission(\''+rowdata.APPROVEITEMGUID +'\'\,1);" >否</a>';
							}else {
								ret = '<a href="#" title="更改为不可预约" onclick="changePermission(\''+rowdata.APPROVEITEMGUID +'\'\,0);" >可预约</a>';
							}
							return ret;
						}},
                	{ display: '操作', isAllowHide: false, align: 'center', width:"20%",							 
	                	render: function (rowdata, rowindex, value, column){
	                		    ret= '<a href="#" title="预约配置" onclick="orderManage(\''+rowdata.APPROVEITEMGUID +'\');">预约配置</a>' ;
	                		    
	                		    return ret;
	                	}
                    }
         ], 
        url:"${ctx}/orderOnline/orderManageList",rownumbers:true,pageSize:20,percentWidthMode:true,usePager:true,
        height: '100%', width:"100%"
 	});
	
}

function changePermission(itemguid, orderpermission) {
	$.post("${ctx}/orderOnline/changePermission",{
		itemguid : itemguid,
		orderpermission : orderpermission
	}, function(result) {
		alert(result.message);
		//重新加载数据
		//g.setNewPage(1);
		//重新加载数据
		g.loadData();
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

//预约配置弹出框
function orderManage(approveitemguid) {
	Dialog.open({URL:"${ctx}/orderOnline/editManage?itemguid="+approveitemguid,Title:"预约配置",Width:480,Height:420});
}

</script>

</head>
<body>
	<div class="box2" panelTitle="查询" id="searchPanel">
		<form action="${ctx}/orderOnline/orderManageList" id="queryForm" method="post" align="center">
		<table>
			<tr>
				<td>部门：</td>
				<td>
					<select name="bureauguid" id="bureauGUID" prompt="全部" url="${ctx}/bureau/wbjSelect" boxWidth="200" boxHeight="200" selWidth="180"></select>
				</td>
				<td>事项名称：</td>
				<td><input type="text" name="approvename" id="approveitemname" ></input></td>
				<td>
					<button type="reset"><span class="icon_find">重置</span></button>
					<button type="button" onclick="searchHandler()"><span class="icon_find">查询</span></button>
				</td>
			</tr>
		</table>
		</form>
	</div>

	<div class="padding_right5">
		<div id="maingrid"></div>
    </div>
    
</body>
</html>