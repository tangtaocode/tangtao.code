<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv=Content-Type content="text/html;charset=utf-8">
	<meta http-equiv=X-UA-Compatible content="IE=edge">

		<%@ include file="/static/common/head.jsp"%>
		<%@ include file="/static/common/common.jsp"%>
		<title>查看证照</title> <script type="text/javascript"
			src="${ctx}/static/jquery/jquery-1.10.2.min.js"></script>

		<script type="text/javascript"
			src="${ctx}/static/QUI/libs/js/jquery.js"></script>
		<script type="text/javascript"
			src="${ctx}/static/QUI/libs/js/language/cn.js"></script>
		<script type="text/javascript"
			src="${ctx}/static/QUI/libs/js/framework.js"></script>

		<script type="text/javascript"
			src="${ctx}/static/jquery/form/jquery.form.js"></script>

		<link href="${ctx}/static/QUI/libs/css/import_basic.css"
			rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" id="skin"
			prePath="${ctx}/static/QUI/" scrollerY="false" />
		<link rel="stylesheet" type="text/css" id="customSkin" />
		<!--数据表格start-->
		<script src="${ctx}/static/QUI/libs/js/table/quiGrid.js"
			type="text/javascript"></script>
		<!--数据表格end-->
</head>
<body>

	<div class="padding_right5">
		<div id="maingrid"></div>
	</div>

</body>
<script type="text/javascript">
	//var ret;
	var grid = $("#list");
	var declareannexguid = '${declareannexguid}';//材料ID
	var instanceId = '${instanceId}';//流程实例ID
	var g;
	function initComplete() {

		initGrid();

	}

	function initGrid() {
		g = $("#maingrid")
				.quiGrid(
						{
							columns : [
									{
										display : '证照名称',
										name : 'DOCNAME',
										width : "15%",
										align : 'center'
									},
									{
										display : '证照编号',
										name : 'DOCNO',
										width : "15%",
										align : 'center'
									},
									{
										display : '审批事项名称',
										name : 'APPROVENAME',
										width : "10%",
										align : 'center'
									},
									{
										display : '证照主体',
										name : 'NAME',
										width : "10%",
										align : 'center'
									},
									{
										display : '发证单位',
										name : 'TEL',
										width : "5%",
										align : 'center'
									},
									{
										display : '发证日期',
										name : 'CHENGNUORIQI',
										width : "10%",
										align : 'center'
									},
									{
										display : '证照有效截止日期',
										name : 'VALIDITYPERIOD',
										width : "15%",
										align : 'center'
									},
									{
										display : '操作',
										isAllowHide : false,
										align : 'center',
										width : "15%",
										render : function(rowdata, rowindex,
												value, column) {
											ret = '<a href="javascript:void(0);" onclick="onMaterialInfo(\''
													+ rowdata.WORKFLOWINSTANCE_GUID
													+ '\',\''
													+ rowdata.APPROVENAME
													+ '\',\''
													+ rowdata.NAME
													+ '\',\''
													+ rowdata.PERSON
													+ '\',\''
													+ rowdata.CHENGNUORIQI
													+ '\',\''
													+ rowdata.TEL
													+ '\',\''
													+ rowdata.VALIDITYPERIOD
													+ '\');">[查看影像]</a>';
											return ret;
										}
									} ],
							url : '${ctx}/scanninghistory/zhengzhaoList?declareannexguid='
									+ declareannexguid
									+ '&instanceId='
									+ instanceId,
							rownumbers : true,
							pageSize : 20,
							percentWidthMode : true,
							usePager : false,
							height : "100%",
							width : "98%"

						});

	}
	
	//查看影像
	function onMaterialInfo(GUID,APPROVNAME,NAME,PERSON,CHENGNUORIQI,TEL,VALIDITYPERIOD) {
	
		window.location.href = "${ctx}/scanninghistory/getView?gid="+GUID+"&APPROVNAME="+APPROVNAME+"&NAME="+NAME+"&PERSON="+PERSON+"&CHENGNUORIQI="+CHENGNUORIQI+"&TEL="+TEL+"&VALIDITYPERIOD="+VALIDITYPERIOD;
	}

	
</script>
</html>