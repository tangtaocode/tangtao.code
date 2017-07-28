<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/static/common/util.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv=X-UA-Compatible content="IE=edge">
<title>综合窗口-领证</title>
</head>
<body>
	<div class="box2" panelTitle="查询" id="searchPanel">
		<form action="${ctx}/togetherWindow/lingquList" id="queryForm" method="post" align="center">
		
		<table>
			<tr>				
				<td>部门名称</td>
				<td>
					<select name="bureauGUID" id="bureauGUID" prompt="全部" url="${ctx}/bureau/wbjSelect" boxWidth="200" boxHeight="200" selWidth="180"></select>
				</td>
				<td>事项名称</td>
				<td><input type="text" name="approveItemName" id="approveItemName" style="width:200px"/></td>
				<td>业务编号</td>
				<td><input type="text" name="yxtywlsh" id="yxtywlsh" style="width:200px"/></td>
				
			</tr>
			<tr>
				<td>领取状态</td>
				<td>
					<select name="isreceive" id="isreceive" prompt="请选择" data='{"list":[{"value":"1","key":"已领取"},{"value":"0","key":"未领取"}]}' boxWidth="200" boxHeight="200" selWidth="180"></select>
				</td>				
				<td>申请人姓名</td>
				<td><input type="text" name="sqrxm" id="sqrxm" style="width:200px"/></td>
				<td>领取状态</td>
				<td>
					<select name="isreceive" id="isreceive" prompt="请选择" data='{"list":[{"value":"1","key":"已领取"},{"value":"0","key":"未领取"}]}' boxWidth="200" boxHeight="200" selWidth="180"></select>
				</td>				
				<td>
					<button type="button" onclick="searchHandler()"><span class="icon_find">查询</span></button>
				</td>
			</tr>
		</table>
		</form>
	</div>

	<div class="padding_right5">
		<div id="dataBasic"></div>
    </div>
</body>
<script type="text/javascript">
var g;
	
function initComplete(){
	g = $("#dataBasic").quiGrid({
		columns: [ 
					{ display: '部门名称', name: 'BUREAUNAME', align: 'center', width: "20%"},
					{ display: '事项名称', name: 'APPROVEITEMNAME', align: 'center', width: "20%"},
					{ display: '业务编号', name: 'YXTYWLSH', align: 'center', width: "20%"},
					{ display: '申请人', name: 'SQR',align: 'center', width: "20%"},
					{ display: '操作', name: 'ISRECEIVE',   align: 'center', width: "15%", 
				     	render: function(rowdata, rowindex, value, column){
				     		var view="";
		            		if(rowdata.ISRECEIVE=='1'){
		            			view="<input type='button' onclick=getCertificate('"+ rowdata.WORKFLOWINSTANCE_GUID+"','"+rowdata.APPROVEITEMNAME+"','1'); value='    查看    '/>";
			        		}
			        		if(rowdata.ISRECEIVE=='0'){
			        			view="<input type='button' onclick=getCertificate('"+ rowdata.WORKFLOWINSTANCE_GUID+"','"+rowdata.APPROVEITEMNAME+"','0'); value='领取登记'/>";
			        		}
		            		 return view;
						}
				    },
	         ], 

	        url:'${ctx}/togetherWindow/lingquList',rownumbers:true,percentWidthMode:true,checkbox:false,
	        height: '100%', width:"98%",pageSize:20,enabledEdit: true,clickToEdit: false
	});
}

//领取登记页面
function getCertificate(workflowinstance_guid,approveitem_name,isrecieve) {
	window.location.href = "${ctx}/certificate/getCertificate?workflowinstance_guid="+workflowinstance_guid+"&approveitem_name="+approveitem_name+"&isrecieve="+isrecieve; 
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
</html>