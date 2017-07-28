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
		<form action="${ctx}/togetherWindow/lingzhengList" id="queryForm" method="post" align="center">
		<table>
			<tr>				
				<td>部门名称</td>
				<td>
					<select name="bureauGUID" id="bureauGUID" prompt="全部" url="${ctx}/bureau/wbjSelect" boxWidth="200" boxHeight="200" selWidth="180"></select>
				</td>
				<td>事项名称</td>
				<td><input type="text" name="approveItemName" id="approveItemName" style="width:200px"/></td>		
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
					{ display: '事项名称', name: 'APPROVEITEMNAME', align: 'center', width: "60%"},
					//{ display: '业务编号', name: 'YXTYWLSH', align: 'center', width: "20%"},
					//{ display: '申请人', name: 'SQR',align: 'center', width: "20%"},
					//{ display: '领取状态', name: 'ISRECEIVE',align: 'center', width: "5%",
				    //	render: function(rowdata, rowindex, value, column){
					//		if(rowdata.ISRECEIVE=='1') {
					//			return '已领取';								
					//		} else if(rowdata.ISRECEIVE=='0'){
					//			return '未领取';
					//		} else {
					//			return '';
					//		}
					//	}
				    //},
					{ display: '操作', name: 'FLAG',   align: 'center', width: "20%", 
				     	render: function(rowdata, rowindex, value, column){
							if(rowdata.FLAG=='0') {
								return '<input type="button" onclick="inputYxtywlsh(\''+rowdata.APPROVEITEMGUID+'\',\'' + rowdata.APPROVEITEMNAME + '\');" value="出证办结"/>';								
							} else {
								return '<input type="button" onclick="declareinfo(\''+rowdata.APPROVEITEMGUID+'\',\'' + rowdata.APPROVEITEMNAME + '\');" value="录入证照信息"/>';
							}
						}
				    },
	         ], 

	        url:'${ctx}/togetherWindow/lingzhengList',rownumbers:true,percentWidthMode:true,checkbox:false,
	        height: '100%', width:"98%",pageSize:20,enabledEdit: true,clickToEdit: false
	});
}

//如果是审批平台的业务，则输入业务编号，以此判断进入“待扫描”还是“领取登记”页面
function inputYxtywlsh(approveitemguid, approveitemname) {
//	alert(approveitemguid+':'+approveitemname);
//	Dialog.open({URL:"${ctx}/togetherWindow/inputYxtywlsh?approveitemguid="+approveitemguid+"&approveitemname="+approveitemname,Title:"填写完整的业务编号",Width:500,Height:330});
	window.location.href="${ctx}/togetherWindow/readyScan?approveitemname="+approveitemname;
}

//非审批平台的业务，第一步：填写申请人、手机号、业务编号等信息，第二步：填写证照信息，第三步：证照扫描上传，第四步：领取登记
function declareinfo(approveitemguid, approveitemname) {
	window.location.href = "${ctx}/togetherWindow/declareinfo?approveitemguid="+approveitemguid+"&approveitemname="+approveitemname;
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