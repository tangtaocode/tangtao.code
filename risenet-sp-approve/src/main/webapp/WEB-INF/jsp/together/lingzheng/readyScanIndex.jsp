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
		<form action="${ctx}/togetherWindow/scanList" id="queryForm" method="post" align="center">
		
		<table>
			<tr>				
				<td>部门名称</td>
				<td>
					<select name="bureauGUID" id="bureauGUID" prompt="全部" url="${ctx}/bureau/wbjSelect" boxWidth="200" boxHeight="200" selWidth="180"></select>
				</td>
				<td>事项名称</td>
				<td><input type="text" name="approveitemname" id="approveitemname" value="${approveitemname }" style="width:200px"/></td>
				<td>业务编号</td>
				<td><input type="text" name="yxtywlsh" id="yxtywlsh" value="${yxtywlsh }" style="width:200px"/></td>
				<td></td>
			</tr>
			<tr>
				<td>扫描状态</td>
				<td>
					<select name="iscan" id="iscan" prompt="请选择" data='{"list":[{"value":"1","key":"已扫描"},{"value":"0","key":"未扫描"}]}' boxWidth="200" boxHeight="200" selWidth="180"></select>
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
var ret = "";
	
function initComplete(){
	g = $("#dataBasic").quiGrid({
		columns: [ 
					{ display: '部门名称', name: 'BUREAUNAME', align: 'center', width: "10%"},
					{ display: '事项名称', name: 'APPROVEITEMNAME', align: 'center', width: "20%"},
					{ display: '业务编号', name: 'YXTYWLSH', align: 'center', width: "10%"},
					{ display: '申请人', name: 'SQR',align: 'center', width: "20%"},
					{ display: '证照状态', name: 'STATE',   align: 'center', width: "10%", 
				     	render: function(rowdata, rowindex, value, column){
							if(rowdata.STATE=='2') {
								return '未录入证照信息' ;								
							} else {
								if(rowdata.STATE=='0') {
									return '未扫描证照';
								}else if(rowdata.STATE=='1') {
									return '已扫描上传';
								}								
							}
						}
				    },
				    { display: '证照操作', align: 'center', width: "10%", 
				     	render: function(rowdata, rowindex, value, column){
							
							if(rowdata.STATE=='2') {
								ret = '<a href="javascript:void(0);" title="录入证照信息" onclick="luru(\''+ rowdata.INSTANCEGUID + '\',\''+ rowdata.DOCTYPEGUID +'\')">录入证照信息</a>' ;								
							} else if(rowdata.STATE=='0'){
								ret = '<a href="javascript:void(0);" title="证照上传" onclick="up(\'' + rowdata.ID + '\',\'' + rowdata.YXTYWLSH + '\',\'' + rowdata.APPROVEITEMNAME + '\',\'' + rowdata.SQR + '\',\'' + rowdata.CHENGNUORIQI + '\',\'' + rowdata.INSTANCEGUID + '\',\'' + rowdata.VALIDITYPERIOD + '\');">证 照 上 传</a>';
							} else if(rowdata.STATE=='1') {
								ret = '<a href="javascript:void(0);" onclick="onMaterialInfo(\'' + rowdata.INSTANCEGUID + '\',\'' + rowdata.APPROVEITEMNAME + '\',\'' + rowdata.SQR + '\',\'' + rowdata.SQR + '\',\'' + rowdata.CHENGNUORIQI + '\',\'' + rowdata.BUREAUNAME + '\',\'' + rowdata.VALIDITYPERIOD + '\');">[查看影像]</a>' 
									+ '<br/><a href="javascript:void(0);"  onclick="upscanning(\'' + rowdata.ID + '\',\'' + rowdata.YXTYWLSH + '\',\'' + rowdata.APPROVEITEMNAME + '\',\'' + rowdata.SQR + '\',\'' + rowdata.CHENGNUORIQI + '\',\'' + rowdata.INSTANCEGUID + '\',\'' + rowdata.VALIDITYPERIOD + '\');">[补充扫描]</a> ';									
							}
							
							return ret;
						}
				    },
					{ display: '领取状态', name: 'ISRECEIVE',   align: 'center', width: "10%", 
				    	render: function(rowdata, rowindex, value, column){
							if(rowdata.ISRECEIVE=='2') {
								return '未录入证照信息' ;								
							} else {
								if(rowdata.ISRECEIVE=='0') {
									return '未领取';
								}else if(rowdata.ISRECEIVE=='1') {
									return '已领取';
								}								
							}
						}
				    },
					{ display: '领取操作', align: 'center', width: "8%", 
				     	render: function(rowdata, rowindex, value, column){				
						
							if(rowdata.ISRECEIVE=='0') {
								ret = '<a href="javascript:void(0);" title="领取登记" onclick="getCertificate(\'' + rowdata.STATE + '\',\'' + rowdata.INSTANCEGUID + '\',\'' + rowdata.APPROVEITEMNAME + '\',\'0\');">领取登记</a>';
							} else {
								ret = '<a href="javascript:void(0);" title="查看" onclick="getCertificate(\'' + rowdata.STATE + '\',\'' + rowdata.INSTANCEGUID + '\',\'' + rowdata.APPROVEITEMNAME + '\',\'1\');">查看</a>';
							}
							return ret;
						}
				    },
	         ], 

	        url:'${ctx}/togetherWindow/scanList', params: [{name:"approve", value:"${approve}"}], rownumbers:true,percentWidthMode:true,checkbox:false,
	        height: '100%', width:"100%",pageSize:20,enabledEdit: true,clickToEdit: false
	});
}

function luru(instanceguid, doctypeguid) {
	window.location.href= "${ctx}/togetherWindow/goscan?instanceGuid="+instanceguid+"&doctypeguid="+doctypeguid;
}
//领取登记页面
function getCertificate(state, workflowinstance_guid,approveitem_name,isrecieve) {
	if(state=="0" && isrecieve=="0" ) { 
		if(confirm("您尚未进行证照扫描，确定领取证照？")) {		
			window.location.href = "${ctx}/certificate/getCertificate?workflowinstance_guid="+workflowinstance_guid+"&approveitem_name="+approveitem_name+"&isrecieve="+isrecieve; 
		}
	}else {
		window.location.href = "${ctx}/certificate/getCertificate?workflowinstance_guid="+workflowinstance_guid+"&approveitem_name="+approveitem_name+"&isrecieve="+isrecieve; 
	}
	
}

//证照上传
function up(id,code,name,unit,time,instanceguid,VALIDITYPERIOD){
	window.location.href = "${ctx}/scanning/upData?id="+id+"&code="+code+"&name="+name+"&unit="+unit+"&time="+time+"&instanceguid="+instanceguid+"&VALIDITYPERIOD="+VALIDITYPERIOD;
	 //var rows = grid.getSelectedRows();
	 //alert(VALIDITYPERIOD);
}

//查看影像
function onMaterialInfo(GUID,APPROVNAME,NAME,PERSON,CHENGNUORIQI,TEL,VALIDITYPERIOD) {

	window.location.href = "${ctx}/scanninghistory/getView?gid="+GUID+"&APPROVNAME="+APPROVNAME+"&NAME="+NAME+"&PERSON="+PERSON+"&CHENGNUORIQI="+CHENGNUORIQI+"&TEL="+TEL+"&VALIDITYPERIOD="+VALIDITYPERIOD;
}

//补充扫描
function upscanning(id,code,name,unit,time,instanceguid,VALIDITYPERIOD){
	window.location.href = "${ctx}/scanning/upData?id="+id+"&code="+code+"&name="+name+"&unit="+unit+"&time="+time+"&instanceguid="+instanceguid+"&VALIDITYPERIOD="+VALIDITYPERIOD;
	 //var rows = grid.getSelectedRows();
	//console.info(a);
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