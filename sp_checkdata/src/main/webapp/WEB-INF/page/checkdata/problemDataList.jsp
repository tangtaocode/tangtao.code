<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>问题详细描述列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!--框架必需start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/framework.js"></script>
<script src="${ctx}/static/QUI/libs/js/table/quiGrid.js" type="text/javascript"></script>
<!--数据表格end-->
<!--表单异步提交start-->
<script src="${ctx}/static/QUI/libs/js/form/form.js" type="text/javascript"></script>
<!--表单异步提交end-->
</head>
<body>
	<div>
		<div id="maingrid"></div>
    </div>
<!--qui加载数据表格-->
<script type="text/javascript">
var grid = null;
function initComplete(){
	grid = $("#maingrid").quiGrid({
		columns:[
                 { display: '', name: 'GUID', align: 'center', hide:true, width: "0%"}, 
                 { display: '问题类型', name: 'TYPE', align: 'center', width: "10%",
                	 render: function (rowdata, rowindex, value, column){
    	           		 var view = "";
    	           		 var pam = rowdata.TYPE;
    	           		 var temp = "";
    	           		 if(pam=="1"){
    	           			temp="超期问题";
    	           		 }else if(pam=="2"){
    	           			temp="环节完整性问题";
    	           		 }else if(pam=="3"){
    	           			temp="内容完整性问题"; 
    	           		 }else if(pam=="4"){
    	           			temp="内容规范性问题"; 
    	           		 }else if(pam=="5"){
    	           			temp="逻辑错误";
    	           		 }
    	           		 view += "<span title="+pam+">"+temp+"</span> ";
    	                 return view;
    		           	}  	
                 },
                 { display: '问题描述', name: 'MIAOSU', align: 'center', width: "90%"}
		         ],
		 url:"/checkData/getProblemList?sblsh=${sblsh}",rownumbers:true,percentWidthMode:true,checkbox:false,fixedCellHeight:true,
	        height: '100%', width:"100%",pageSize:20
     	});
}
</script>
</body>
</html>