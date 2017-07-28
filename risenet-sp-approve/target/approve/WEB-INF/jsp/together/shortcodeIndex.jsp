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
<script type="text/javascript">
var g;
var ret;
	
function initComplete(){
	g = $("#dataBasic").quiGrid({

		columns: [ 
					{ display: '部门名称', name: 'BUREAUNAME', align: 'center', width: "20%"},
					{ display: '事项名称', name: 'APPROVEITEMNAME', align: 'center', width: "40%"},
					{ display: '事项简码', name: 'SHORTCODE', align: 'center', width: "20%"},
					{ display: '操作', name: '',align: 'center', width: "15%",
						render: function(rowdata, rowindex, value, column){
							if(rowdata.SHORTCODE==null) {
								ret ='<span class="img_add hand" title="编码" onclick="newOrUpdate(\'' + rowdata.ITEMID + '\',\'' + rowdata.BUREAUNAME + '\',\'' + rowdata.APPROVEITEMNAME + '\',\'' + rowdata.SHORTCODE +'\',\'add\');"></span>'
								+'<span class="img_list hand" title="编辑收件要点" onclick="editPoint(\'' + rowdata.ITEMID + '\');"></span>';								
							} else {
								ret ='<span class="img_edit hand" title="修改事项编码" onclick="newOrUpdate(\'' + rowdata.ITEMID + '\',\'' + rowdata.BUREAUNAME + '\',\'' + rowdata.APPROVEITEMNAME + '\',\'' + rowdata.SHORTCODE +'\',\'upd\');"></span>'
								+'<span class="img_list hand" title="编辑收件要点" onclick="editPoint(\'' + rowdata.ITEMID + '\');"></span>';
							}
							return ret;
						}

				    }
	         ], 

	        url:'${ctx}/togetherWindow/shortcodeList',rownumbers:true,percentWidthMode:true,checkbox:false,
	        height: '100%', width:"100%",pageSize:20,enabledEdit: true,clickToEdit: false
	});
}

//事项简码编写
function newOrUpdate(itemid, bureauname, approveitemname, shortcode, method) {
	var diag = new Dialog();

	diag.Title = "新增/编辑事项简码";
	
	diag.URL = "${ctx}/togetherWindow/editShort?ITEMID="+itemid+"&BUREAUNAME="+bureauname+"&APPROVEITEMNAME="+approveitemname+"&SHORTCODE="+shortcode+"&METHOD="+method;
	
	diag.Width = 480;
	diag.Height = 270;
	
	diag.ShowButtonRow = true;
	diag.ShowOkButton = true;
	diag.ShowCancelButton = true;	
	diag.OkButtonText = "保存";
	diag.CancelButton = "取消";
	diag.ButtonAlign = "center";
	diag.OKEvent = function() {
		var itemid = diag.innerFrame.contentWindow.document.getElementById('itemid').value;
		var shortcode = diag.innerFrame.contentWindow.document.getElementById('shortcode').value;
		var method = diag.innerFrame.contentWindow.document.getElementById('method').value;
		$.post("${ctx}/togetherWindow/saveShort",{
			"itemid": itemid,
			"shortcode": shortcode,
			"method": method
		}, function(backdata) {
			alert(backdata.msg);
			diag.close();
			window.location.reload();

		});
	};
	diag.CancelEvent = function() {
		diag.close();
	};
	
	diag.show();

}

function editPoint(itemid) {
	Dialog.open({URL:"${ctx}/togetherWindow/editPoint?itemid="+itemid,Title:"编辑收件要点",Width:1300,Height:550});
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
<body>
	<div class="box2" panelTitle="查询" id="searchPanel">
		<form action="${ctx}/togetherWindow/shortcodeList" id="queryForm" method="post" align="center">
		
		<table>
			<tr>
				<td>部门:</td>
				<td>
					<select name="bureauGUID" id="bureauGUID" prompt="全部" url="${ctx}/bureau/wbjSelect" boxWidth="200" boxHeight="200" selWidth="180"></select>
				
				</td>
				
				<td>&nbsp;&nbsp;事项名称：</td>
				<td><input type="text" name="approveItemName" style="width:200px"/></td>
				<td>&nbsp;&nbsp;事项简码：</td>
				<td><input type="text" name="shortCode" style="width:120px"/></td>
				<td>
					<button type="button" onclick="searchHandler();"><span class="icon_find">查询</span></button>
					<button type="reset"><span class="icon_clear">重置</span></button>
				</td>
			</tr>
		</table>
		</form>
	</div>

	<div class="padding_right5">
		<div id="dataBasic"></div>
    </div>
</body>
</html>