<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv=Content-Type content="text/html;charset=utf-8">
<meta http-equiv=X-UA-Compatible content="IE=edge">
<title>深圳市罗湖区审批平台-综合窗口</title>
</head>

<!--自动提示框start-->

<script type='text/javascript' src='${ctx}/static/QUI/libs/js/form/suggestion.js'></script>

<!--自动提示框end-->


<script type="text/javascript">
var g;

var gridData;
var jyData={"list":[{"value":"1","key":"是"},{"value":"0","key":"否"}]};
var clData={"list":[{"value":"1","key":"有效"},{"value":"0","key":"无效"}]};
	
function initComplete(){
	g = $("#dataBasic").quiGrid({
		columns: [ 
					
					{ display: '主管部门', name: 'BUREAUNAME',align: 'center', width: "10%"},
					{ display: '事项名称', name: 'APPROVEITEMNAME', align: 'center', width: "20%"},
					{ display: '事项简码', name: 'SHORTCODE', align: 'center', width: "10%"},
					{ display: '系统名称', name: 'SYSTEMNAME', align: 'center', width: "15%"},
					{ display: '事项性质', name: 'APPROVEITEMTYPE', align: 'center', width: "10%"},
					{ display: '收件处理', name: '',align: 'center', width: "10%",
						render: function(rowdata, rowindex, value, column){
								return '<input type="button" onclick="startBusi(\'' + rowdata.APPROVEITEMGUID + '\');" value="开始处理"/>';								
						}

				    },
					
					{ display: '事项类型', name: 'BUREAUTYPE',   align: 'center', width: "10%"},
					{ display: '办理时限', name: 'TIMELIMIT',   align: 'center', width: "10%" }
	         ], 

	        url:'${ctx}/togetherWindow/receiveList',sortName:'TIMELIMIT',rownumbers:true,percentWidthMode:true,checkbox:false,
	        height: '100%', width:"99%",pageSize:20,enabledEdit: true,clickToEdit: false
	});
	
	//快捷打开收件要点
	$("#sug-1").bind("listSelect",function(e,obj){
	    
	    var itemid=$("#sug-1").attr("relValue");
	    
	    Dialog.open({URL:"${ctx}/togetherWindow/editPoint?itemid="+itemid,Title:"编辑收件要点",Width:1300,Height:550});
	    
	   var relText = $("#sug-1").attr("relText");
	   var itemName = relText.substring(relText.indexOf("|")+1, relText.length);
	    $("#approveItemName").val(itemName);
	   
	   searchHandler();

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

//开始处理(审批平台)
function startOrg(guid){
	Dialog.open({URL:"${ctx}/windowApprove/code?guid="+guid,Title:"填写个人/机构代码",Width:500,Height:330});
}

//开始处理(其他系统)
function startBusi(guid,approvePlace) {
	Dialog.open({URL:"${ctx}/togetherWindow/code?guid="+guid,Title:"填写个人/机构代码",Width:500,Height:330});
}

</script>
<body>
	<div class="box2" panelTitle="查询" id="searchPanel">
		<form action="${ctx}/togetherWindow/receiveList" id="queryForm" method="post" align="center">
		
		<table>
			<tr>
				
				<td>事项名称：</td>
				<td><input type="text" name="approveItemName" id="approveItemName" style="width:200px"/></td>
			
				<td><button type="button" onclick="searchHandler()"><span class="icon_find">查询</span></button></td>
				
				<td>
					<div class="suggestion" url="${ctx}/togetherWindow/getListByWordKey" suggestMode="remote" id="sug-1" inputWidth="400" suggestTitle=""></div>					
				</td>
			</tr>
		</table>
		</form>
	</div>

	<div class="padding_right5">
		<div id="dataBasic" style="overflow:hidden"></div>
    </div>
</body>
</html>