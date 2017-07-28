<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>事项与表单绑定</title>

<script type="text/javascript" src="${ctx}/static/risesoft/js/myCore.js"></script>
</head>
<body>	
		<form action="${ctx}/bureau/bureauList" id="queryForm" method="post" align="center">
		<table>
			<tr>
				<td>事项名称:</td>
				<td><input style="width:300px" readonly value="${approve.approveitemname}"/></td>
				<td><input  id="input01" type="hidden" value="${approve.approveitemguid}"/></td>
				
			</tr>
			<tr>
				<td>材料类型:</td>
				<td>
					<select  id="cltype" selWidth="300" style="width:300px" onchange="chooseeform(this);">
						 <c:forEach items="${list}" var="temp">
								<option value="${temp.DECLAREANNEXTYPEGUID}">${temp.DECLAREANNEXTYPENAME}</option>
						</c:forEach>
					</select>
				</td>				
			</tr>
			<tr>
				<td>材料:</td>
				<td>	
					<select id="material" selWidth="300"></select>
				</td>
			</tr>
			<tr>
				<td>表单名称：</td>
				<td><input type="text" name="eformname" style="width:200px"/></td>
				<td><button type="button" onclick="searchHandler()"><span class="icon_find">查询</span></button></td>
				<td><button type="button" onclick="matches()"><span class="icon_find">绑定</span></button></td> 
			</tr>
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
			                { display: '模板ID', name: 'TEMP_ID', align: 'center', width: "20%",showTitle:true},
			                { display: '模板名称',name:'TEMPLATENAME', align: 'center', width: "50%",showTitle:true},	
			                { display: '模板类型',name:'ACCESSMETHOD', align: 'center', width: "30%",showTitle:true}
		         ], 
		        url:"${ctx}/bureau/eformfind",usePager:true,rownumbers:true,percentWidthMode:true,checkbox:true,
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
		
		//根据材料类型去选择相应的材料材料
		function chooseeform(obj){
			//类型
			var listguid=$(obj).val();
			$.ajax({
				type:'POST',
				dataType:'json',
				data:{listguid:listguid},
				url:"${ctx}/bureau/correspond",
				success:function(data){
					//采用的是QUI中的json格式
					//var selData={"list":[{"value":"1","key":"员工1"},{"value":"2","key":"员工2"}]};
						$("#material").data("data",data);
						$("#material").render();				
				}
			}); 	
   
		}
		
		//绑定
	 	function matches(){
	 			var row=g.getSelectedRows();
				 var len=row.length;
			 	if(len<1){
					Dialog.alert("至少选择一条记录！|提示",null);
					return;
				}  else if(len>1){
					Dialog.alert("只能选择一条记录！|提示",null);
					return;
				} else if(len==1){
					//材料id	
			 		var listguid=$('#material').val();
			 		//代表该类型没有电子表单、所以不需要绑定
			 		if(null==listguid){
			 			alert("该类型没有电子表单");
			 		}else{
						var tempid=row[0].TEMP_ID;
						var templatename=row[0].TEMPLATENAME;
						var accessmethod=row[0].ACCESSMETHOD;
						//事项id
						var approveitemguid=$('#input01').val();
						var guid = row[0].GUID;
						//类型id
						var typeguid=$('#cltype option:selected').val();
						var typename = $("#cltype").attr("relText");
					  	$.ajax({
							type:"POST",
							dataType:"json",
							data:{tempid:tempid,templatename:templatename,approveitemguid:approveitemguid,guid:guid,typeguid:typeguid,typename:typename,accessmethod:accessmethod,listguid:listguid},
							url:"${ctx}/bureau/eformmatches",
							success:function(responseText, statusText, xhr, $form){
								Dialog.alert(responseText.message);
								}
							});   
			 		}

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
