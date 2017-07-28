<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>基本表格模板</title>
<!--框架必需start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/language/cn.js"></script>
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/framework.js"></script>
<link href="${ctx}/static/QUI/libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="${ctx}/static/QUI/" scrollerY="false"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->
<!--数据表格start-->
<script src="${ctx}/static/QUI/libs/js/table/quiGrid.js" type="text/javascript"></script>
<!--数据表格end-->
<!--表单异步提交start-->
<script src="${ctx}/static/QUI/libs/js/form/form.js" type="text/javascript"></script>
<!--表单异步提交end-->
<!-- 日期控件start -->
<script type="text/javascript" src="${ctx}/static/js/My97DatePicker/WdatePicker.js"></script>
<!-- 日期控件end -->
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
							{ display: 'guid', name: 'GUID', align: 'center', width: "10%",hide:true},
							{ display: 'workflowguid', name: 'WORKFLOWGUID', align: 'center', width: "10%",hide:true},
							{ display: '受理编号', name: 'YXTYWLSH', align: 'center', width: "15%"},
			                { display: '事项名称', name: 'APPROVEITEMNAME', align: 'center', width: "25%",
								render: function (rowdata, rowindex, value, column){
		                		    var res = '<a href="javascript:void(0);" class="aColor"  onclick="openTab(\'' + rowdata.GUID + '\',\'' + rowdata.WORKFLOWGUID + '\',\'' + rowdata.APPROVEITEMNAME + '\');">'+rowdata.APPROVEITEMNAME+'</a>' ;
		                		    return res;
								}
			                },
			                { display: '申请人姓名', name: 'DECLARERPERSON', align: 'center', width: "15%"},
			                { display: '开始时间', name: 'BJGZSJ', align: 'center', width: "15%"},
			                { display: '到期时间', name: '', align: 'center', width: "15%", isSort:false},
			                { display: '受理人', name: 'BJGZFCR',align: 'center', width: "15%"}
		         ], 
		        url:"${ctx}/bjgz/bjgzList",rownumbers:true,pageSize:20,percentWidthMode:true,usePager:true,
		        height: '100%', width:"100%"
         	});
			
			 $("#searchPanel").bind("stateChange",function(e,state){
				g.resetHeight();
			});
			
		}
		
		 $.quiDefaults.Grid.formatters['hfstate'] = function (value, column) {
			    if(value==0){
			    	return "未回复";
			    }else{
			    	return "已回复";
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
		
		//补齐补正开始处理
		function openTab(GUID,workflowguid,APPROVEITEMNAME) {
			//window.location.href = "${ctx}/bjgz/newInstanceAction?guid="+GUID+"&processDefinitionKey="+workflowguid;
			var url = "${ctx}/bjgz/newInstanceAction?guid="+GUID+"&processDefinitionKey="+workflowguid;
			window.parent.openTab(APPROVEITEMNAME,url);
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
</head>
<body>
	<div class="box2" panelTitle="查询" id="searchPanel">
		<form action="${ctx}/bjgz/bjgzList" id="queryForm" method="post" align="center">
		<table>
			<tr>
				<td>事项名称：</td>
				<td><input type="text" name="name" style="width:200px"/></td>
				<td>业务编号：</td>
				<td><input type="text" name="yxtywlsh" style="width:200px"/></td>
				<td>申请人姓名：</td>
				<td><input type="text" name="declareperson" style="width:200px"/></td>
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
