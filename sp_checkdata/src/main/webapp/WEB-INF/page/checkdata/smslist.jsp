<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>发送短信人员列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!--框架必需start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/framework.js"></script>
<script src="${ctx}/static/QUI/libs/js/table/quiGrid.js" type="text/javascript"></script>
<!--数据表格end-->
<!--表单异步提交start-->
<script src="${ctx}/static/QUI/libs/js/form/form.js" type="text/javascript"></script>
<!--表单异步提交end-->
</head>
<div class="box2" panelTitle="短信设置" id="searchPanel">
<form action="" method="post" id="smsform">
<table>
			<tr>
				<td>姓名：</td>
				<td>
					<input type="text" id="name" name="name" />
				</td>
				<td><button type="button" onclick="searchHandler()"><span class="icon_find">查询</span></button></td>
				<td><button type="button" onclick="resetHandler()"><span class="icon_reload">重置</span></button></td>
			</tr>
		</table>
</form>
</div>
<body>
	<div>
		<div id="maingrid"></div>
    </div>
<!--qui加载数据表格-->
<script type="text/javascript">
//查询
function searchHandler(){
	//得到查询参数
	var query = $("#smsform").formToArray(); 
	//将查询参数传给grid表格
	grid.setOptions({ params: query});
	//页号重置为1
	grid.setNewPage(1);
	//重新加载数据
	grid.loadData();
}
//重置
function resetHandler(){
   //表单常规元素还原
   $("#smsform")[0].reset();
   //下拉框还原
   $("#smsform").find("select").render();
   //重新查询
   searchHandler();
}
var grid = null;
function initComplete(){
	grid = $("#maingrid").quiGrid({
		columns:[
                 { display: '', name: 'GUID', align: 'center', hide:true, width: "0%"}, 
                 { display: '姓名', name: 'NAME', align: 'center',  width: "10%"},
                 { display: '联系手机号码', name: 'MOBILE_PHONE', align: 'center',  width: "20%"},
                 { display: '创建时间', name: 'CREATETIME', align: 'center',  width: "10%"},
                 { display: '更新时间', name: 'UPDATETIME', align: 'center',  width: "10%"},
                 { display: '操作', isAllowHide: false, align: 'center', width:"10%",
                     render: function (rowdata, rowindex, value, column){
                    	 var guid = rowdata.GUID;
                     return "<div class='padding_top4 padding_left5'>"
                                 + "<span class='img_edit hand' title='修改' onclick=\"onEdit('"+guid+"')\"></span>"
                                 + "<span class='img_delete hand' title='删除' onclick=\"onDelete('"+guid+"')\"></span>"
                             + "</div>";
                     	}
               		 }
		         ],
		 url:"/Sms/getSmsData",rownumbers:true,percentWidthMode:true,checkbox:false,fixedCellHeight:true,
	        height: '100%', width:"100%",pageSize:20,checkbox:true,
	        toolbar:{
	            items:[
	                 {text: '新增', click: addSms, iconClass: 'icon_add'},
	                 { line : true }
	               ]
	           }
     	});
}
/*添加信息*/
function addSms(){
	Dialog.open({URL:"/Sms/getSmsById",Title:"新增",Width:600,Height:200});

}
/*添加信息*/
function onEdit(obj){
	Dialog.open({URL:"/Sms/getSmsById?guid="+obj,Title:"修改",Width:600,Height:200});

}
/*删除信息*/
function onDelete(guid){
	$.ajax({
		url:"/Sms/deleteById",
		type:"post",
		data:{"guid":guid},
		dataType:"json",
		success:function(data){
			if(data.outcome=='1'){
				  Dialog.alert("数据删除成功！",function(){
					  searchHandler();
                    });
			}else{
				 Dialog.alert("数据删除失败！",function(){
                   });
			}
		}
	});
}

</script>
</body>
</html>