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
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/form/datePicker/WdatePicker.js"></script>
<!-- 日期控件end -->
<!--弹窗组件start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/popup/drag.js"></script>
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/popup/dialog.js"></script>
<!--弹窗组件end-->
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
var fixedObj=10;

function customHeightSet(contentHeight){

    $("#scrollContent").height(contentHeight-fixedObj);

}
</script>

<title>材料清单</title>
</head>
<body>
<div class="box1" panelWidth="900"  id="scrollContent">
   
    <table width="980" height="100%"   class="tableStyle" formMode="line">

        <tr>
		  <th colspan="2">${APPROVEITEMNAME }</th>
		</tr>
		<TR>
			<TD width="20%" >服务对象：</TD>
			<TD  width="80%"  >${DECLAREUNITTYPE }</TD>
		</TR>
		<TR>
			<TD  >申办条件：</TD>
			<TD   >${DECLARECONDITION }</TD>
		</TR>
		<TR>
			<TD   >事项性质：</TD>
			<TD   >${APPROVEITEMTYPE }</TD>
		</TR>
		<TR>
			<TD  >事项类型：</TD>
			<TD  >${BUREAUTYPE }</TD>
		</TR>
		<TR>
			<TD  >收费标准：</TD>
			<TD  >${CHARGEINFO }
			</TD>
		</TR>
		<TR>
			<TD    >时限(工作日)：</TD>
			<TD  >${TIMELIMIT }</TD>
		</TR>
		<TR>
			<TD    >办事地点：</TD>
			<TD    >${SUPERVISEUNIT}</TD>
		</TR>
		<TR>
			<TD >审批时限说明：</TD>
			
			<TD >${TIMEIMITDESC }</TD>
		</TR>
	  <TR>
		 <TD >办事流程说明：</TD>
		 <TD >${DECLAREDESC }</TD>
	  </TR>
	    <tr>
	    	<TD >办事流程：</TD>
	    	 <Td >${THEFILENAME }</td>
				 
	    </tr>
		<TR>
		<TD >申请材料：</TD>
		 <Td >${DECLAREANNEXNAME }</td>
         </TR>
         <TR>
		<TD  >申请材料描述：</TD>
		 <Td >${DECLAREANNEXDESC }</td>
         </TR>
    
		<TR >
			<TD  >法律、法规：</TD>
			<TD >${APPROVEITEMPOLICYNAME }</TD>
	  </TR>
	  
	  <TR>
		 <TD >事项备注：</TD>
		 <TD >${APPROVEITEMDESC }</TD>
	  </TR>
    </table>

</div>


</body>
</html>