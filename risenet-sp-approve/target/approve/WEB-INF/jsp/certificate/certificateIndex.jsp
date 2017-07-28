<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>证照领取列表</title>
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
	        { display: '主键', name: 'WORKFLOWINSTANCE_GUID',     align: 'center', width: "",hide:true},

	        { display: '受理编号', name: 'DECLARESN',     align: 'center', width: "10%"},

	        { display: '事项名称', name: 'APPROVEITEMNAME',    align: 'center', width: "20%"},
	        
	        { display: '受理时间', name: 'DECLAREDATETIME',    align: 'center', width: "15%",editor: { type: 'date',dateFmt:'yyyy-MM-dd'}},
	        
			{ display: '办结时间', name: 'ENDDATE',    align: 'center', width: "15%",editor: { type: 'date',dateFmt:'yyyy-MM-dd'}},
	        
	        { display: '领取时间', name: 'RECEIVEDATE',    align: 'center', width: "15%",editor: { type: 'date',dateFmt:'yyyy-MM-dd'}},
			
	        { display: '领取状态', name: 'ISRECEIVE',    align: 'center', width: "15%",
	        	render: function (rowdata){
	        		if(rowdata.ISRECEIVE=='1'){
	        			return "已领取";
	        		}
	        		if(rowdata.ISRECEIVE=='0'){
	        			return "未领取";
	        		}
        		}
	        },
	        
	        { display: '领取操作', align: 'center', width: "10%",showTitle:true,
            	render: function (rowdata, rowindex, value, column){
            		var view="";
            		if(rowdata.ISRECEIVE=='1'){
            			view="<input type='button' onclick=getCertificate('"+ rowdata.WORKFLOWINSTANCE_GUID+"','"+rowdata.APPROVEITEMNAME+"','1'); value='    查看    '/>";
	        		}
	        		if(rowdata.ISRECEIVE=='0'){
	        			view="<input type='button' onclick=getCertificate('"+ rowdata.WORKFLOWINSTANCE_GUID+"','"+rowdata.APPROVEITEMNAME+"','0'); value='领取登记'/>";
	        		}
            		 return view;
            		 
            	}
            }
	       ],

	        url:'${ctx}/certificate/list',rownumbers:true,percentWidthMode:true,checkbox:false,
	        height: '100%', width:"100%",pageSize:20,enabledEdit: false,clickToEdit: false,
	});
}

//领取登记页面
function getCertificate(workflowinstance_guid,approveitem_name,isrecieve) {
	window.location.href = "${ctx}/certificate/getCertificate?workflowinstance_guid="+workflowinstance_guid+"&approveitem_name="+approveitem_name+"&isrecieve="+isrecieve; 
}

function fanhui(){
	history.go(-1);
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
		<form action="" id="queryForm" method="post" align="center">
		<table>
			<tr>
				<td>业务编号：</td>
				<td><input type="text" name="declaresn"   style="width:140px;"/></td>
				<td>事项名称：</td>
				<td><input type="text" name="approveitemname"   style="width:140px;"/></td>
				<td>领取状态：</td>
				<td>
				<select name="isreceive" id="isreceive" style="width:140px;">
					<option value="">请选择</option>
					<option value="0">未领取</option>
					<option value="1">已领取</option>
				</select>
				</td>
			</tr>
			<tr>
				<td>证件号码：</td>
				<td><input type="text" name="getpersoncard"   style="width:140px;"/></td>
				<td>受理时间：</td>
				<td>从<input type="text" name="start_time" id="start_time" class="date"/></td>
				<td>至：<input type="text" name="end_time" id="end_time" class="date"/></td>
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