<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>实体窗口批准</title>

<script type="text/javascript" src="${ctx}/static/risesoft/js/myCore.js"></script>
</head>
<body>	
		<form action="" id="queryForm" method="post" align="center">
		<table>						
			<tr>
				<td>窗口名称：</td>
				<td><input type="text" name="name" style="width:200px"/></td>
				<td><button type="button" onclick="searchHandler()"><span class="icon_find">查询</span></button></td>
				<td><button type="button" onclick="approve();"><span class="icon_find">批准</span></button></td> 
			</tr>
			<input type="hidden" value="${guid}" id="guid"/>
			<input type="hidden" value="${type}" id="type"/>
		</table>
		</form>			
	<div class="padding_right5">
		<div id="maingrid"></div>
    </div>

<script type="text/javascript">
		var frameID = newGuid();
        var g;
		function initComplete(){
			 g = $("#maingrid").quiGrid({
		       columns: [ 
							{ display: '窗口guid', name: 'GUID', align: 'center', width: "30%",showTitle:true,hide:true},
			                { display: '窗口名称', name: 'NAME', align: 'center', width: "45%",showTitle:true},
			                { display: '地址',name:'ADDRESS', align: 'center', width: "55%",showTitle:true}			               
		         ], 
		        url:"${ctx}/hallmanager/choicewindow",usePager:true,rownumbers:true,percentWidthMode:true,checkbox:true,
		       	pageSize:20,
		       	params:[{pageNo:"2"},{pageSize:"20"}],
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
		

		//绑定
			function approve(){
 			 var row=g.getSelectedRows();
			 var len=row.length; 
			
		  	 if(len<1){
				Dialog.alert("至少选择一条记录！|提示",null);
				return;
			}  else if(len>1){
				Dialog.alert("只能选择一条记录！|提示",null);
				return;
			} else if(len==1){
  				  var entitywindowname=row[0].NAME;
 				  var windowguid=row[0].GUID;
				var guid=$('#guid').val();
 				var type=$('#type').val();
 			  	$.ajax({
					type:"POST",
					dataType:"json",
					data:{entitywindowname:entitywindowname,guid:guid,type:type,windowguid:windowguid},
					url:"${ctx}/hallmanager/windowapprovestatus",
					success:function(data){
						Dialog.alert(data.message,function(){		
								parent.location.reload();
								parent.Dialog.close();
							});										
						}
					});   
			}        
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
		
	//添加
	function addUser() {
		top.Dialog.open({URL:"${ctx}/static/QUI/sample_skin/normal/user-management-content.html",Title:"新增",Width:500,Height:330}); 
	}
	
	//批量删除
	function deleteUser() {
		top.Dialog.alert("向后台发送ajax请求来批量删除。见JAVA版或.NET版演示。");
	}
	
	//导入
	function importUser() {
		top.Dialog.alert("见JAVA版或.NET版演示。");
	}
	//导出
	function exportUser() {
		top.Dialog.alert("见JAVA版或.NET版演示。");
	}
	
	//导出所有
	function exportUser2() {
		top.Dialog.alert("见JAVA版或.NET版演示。");
	}
	
	//查看
	function onView(rowid){
		alert("选择的记录Id是:" + rowid );
		top.Dialog.open({URL:"${ctx}/static/QUI/sample_skin/normal/user-management-content2.html",Title:"查看",Width:500,Height:330}); 
	}
	
	//修改
	function onEdit(rowid){
		top.Dialog.alert("见JAVA版或.NET版演示。");
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
</body>
</html>
