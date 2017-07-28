<!--
	/**
 * @ FileName: ordersOnline.jsp
 * @Description: 窗口人员“网上预约”列表
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
<title>网上预约</title>

<link href="${ctx}/static/risesoft/css/style.css" rel="stylesheet" type="text/css"/>

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
					{ display: '预约码', name: 'CODE', align: 'center', width: "10%"},
					{ display: '预约事项', name: 'APPROVEITEMNAME', align: 'center', width: "10%"},
	                { display: '预约人', name: 'ORDERPERSON', align: 'center', width: "10%"},
	                { display: '身份证号码', name: 'ORDERID', align: 'center', width: "15%"},
	                { display: '预约时间', name: 'ORDERTIME', align: 'center', width: "10%"},
	                { display: '联系电话', name: 'ORDERTEL', align: 'center', width: "10%", isSort:false},
	                { display: '提交时间', name: 'HFSJ',align: 'center', width: "10%"},
	                { display: '回复状态', isAllowHide: false, align: 'center', width: "10%",
	                	render: function(rowdata, rowindex, value, column){
	                		if(rowdata.ISPASS!=null&&'0'==rowdata.ISPASS){
                		    	ret= '<a href="#" title="写回复" onclick="onOrderHuifu(\'' + rowdata.ORDERPERSONGUID +'\');">未回复</a>' ;
                		    }else{
                		    	ret= '<a href="#" title="查看回复" onclick="onOrderHuifu(\'' + rowdata.ORDERPERSONGUID +'\');">已回复</a>';
	                		
                		    }
                		    return ret;
	                	}
	                },
                	{ display: '受理状态', isAllowHide: false, align: 'center', width:"10%",							 
	                	render: function (rowdata, rowindex, value, column){
	                		    if(rowdata.SLSTATE!=null&&'1'==rowdata.SLSTATE){
	                		    	ret= '<a href="#" title="查看受理情况" onclick="onMaterialInfo(\'' + rowdata.GUID + '\',\'' + rowdata.WORKFLOWGUID + '\',\''+rowdata.DECLARESN+'\');">已受理</a>';
	                		    	
	                		    }else{
	                		    	ret= '<a href="#" title="开始受理" onclick="onBeginShouli(\''+rowdata.ORDERPERSONGUID + '\',\'' + rowdata.APPROVEITEMGUID + '\',\'' + rowdata.ORDERID +'\');">未受理</a>' ;
		                		
	                		    }
	                		    return ret;
	                	}
                    }
         ], 
        url:"${ctx}/orderOnline/orderList",rownumbers:true,pageSize:20,percentWidthMode:true,usePager:true,
        height: '100%', width:"100%"
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

function onOrderHuifu(orderGuid) {
	Dialog.open({URL:"${ctx}/orderOnline/orderHuifu?orderGuid="+orderGuid,Title:"预约回复",Width:280,Height:200});
}

function onBeginShouli(orderGuid, approve, cardid) {
	$.post("${ctx}/orderOnline/changeSlstate?orderGuid="+orderGuid,{},function(data) {
		if(data.msg) {
			window.location.href="${ctx}/windowApprove/newInstanceAction?guid="+approve+"&value="+cardid;
		}
	});
	
}
</script>

</head>
<body>
	<div class="box2" panelTitle="查询" id="searchPanel">
		<form action="${ctx}/orderOnline/orderList" id="queryForm" method="post" align="center">
		<table>
			<tr>
				<td>预约人：</td>
				<td><input type="text" name="orderPerson" id="orderPerson" ></input></td>
				<td>身份证号码：</td>
				<td><input type="text" name="orderId" id="orderId" ></input></td>
				<td>预约日期：</td>
				<td>
					从<input type="text" name="beginDate" id="beginDate" class="date" ></input>至<input type="text" name="endDate" class="date"></input>
				</td>
			</tr>
			<tr>
				<td>回复状态:</td>
				<td>
					<select name="ishf">
						<option value="">全部</option>
						<option value="0">未回复</option>
						<option value="1">已回复</option>
						</select>
				</td>
				<td>处理状态:</td>
				<td>
					<select name="slstate">
						<option value="">全部</option>
						<option value="0">未处理</option>
						<option value="1">已处理</option>
						</select>
				</td>
				<td></td>
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