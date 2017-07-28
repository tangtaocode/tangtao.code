<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>审核批准角色绑定</title>
<script type="text/javascript" src="${ctx}/static/risesoft/js/myCore.js"></script>
<script type="text/javascript" src="${ctx}/static/auditRole/orgBureau.js"></script>

<!-- 表单验证start -->
<script src="${ctx}/static/QUI/libs/js/form/validationRule.js" type="text/javascript"></script>
<script src="${ctx}/static/QUI/libs/js/form/validation.js" type="text/javascript"></script>
<!-- 表单验证end -->
<!--布局控件start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/nav/layout.js"></script>
<!--布局控件end-->
<!--数据表格start-->
<script src="${ctx}/static/QUI/libs/js/table/quiGrid.js" type="text/javascript"></script>
<!--数据表格end-->

<script type="text/javascript">
	    //定义远程数据
        var g;
	    var ret;
	    var winWidth;
		var winHeight;
		function initComplete(){
			size();
			 g = $("#maingrid").quiGrid({
		       columns: [ 
		               		{ display: '人员id', name: 'PERSONGUID', hide:true,align: 'center'},
			                { display: '姓名', name: 'PERSONNAME', align: 'center', width: "10%"},
			                { display: '委办局id', name: 'BUREAUGUID',hide:true, align: 'center'},
			                { display: '事项ID', name: 'APPROVEITEMGUID',hide:true, align: 'center'},
			                { display: '事项名称', name: 'APPROVEITEMNAME', align: 'center', width: "80%"},
			                { display: '操作', name: 'taskAssignee',   align: 'center', width: "10%" ,
			                	render: function (rowdata, rowindex, value, column){
				                	var s = '';
									s += '<a href="javascript:void(0);" class="aColor" onclick="deleteRole(\'' + rowdata.PERSONGUID + '\');"><span class=\'icon_delete\'>删除</span></a>';
									return s;
								}
			                }
		                	
		         ], 
		         url : '${ctx}/auditRole/getRoleList',rownumbers:true,checkbox:true,pageSize:30,percentWidthMode:true,usePager:true,
		         pageSizeOptions:[10, 20, 30, 40, 50],height: '100%', width:"100%",

		         toolbar:{
		             items:[
		                   {text: '新增',click:addPerson,iconClass: 'icon_add'},
		                   { line : true },
		                   {text: '批量删除',click:deletePerson,iconClass: 'icon_delete'},
		                   { line : true },
		                   {text: '绑定事项',click:approveItemConfig,iconClass: 'icon_config'}
		                 ]
		             }
         	});
			
			 $("#searchPanel").bind("stateChange",function(e,state){
				g.resetHeight();
			}); 
			
		}
		
		function size() {
			winWidth = $(window).width();
			winHeight = $(window).height() - 40;
		}
		
    //刷新表格 表单提交的回调
    function afterFormSubmit(){
        g.loadData();
    }
 
</script>	
</head>
<body>
<!-- 	<div id="div1">
		<button onclick="addPerson()"><span class="icon_config">添加人员</span></button>
		<button onclick="deletePerson()"><span class="icon_delete">删除人员</span></button>
		<button onclick="approveItemConfig()"><span class="icon_calendar">绑定事项</span></button>
	</div> -->
<%-- 	 <div class="box2" panelTitle="查询" id="searchPanel">
		<form action="${ctx}/sp/worklist/todo/list" id="queryForm" method="post" align="center">
		<table>
			<tr>
				<td>文件标题：</td>
				<td><input type="text" name="doingDocumentTitle" id="doingDocumentTitle" 
							style="width: 100px;"/></td>
				<td>任务名称：</td>
				<td>
					<input type="text" name="taskName" id="taskName" 
							style="width: 100px; "/>
				</td>
				<!-- <td>发送人：</td>
				<td>
					<input type="text" name="taskSender" id="taskSender" 
							style="width: 100px;"/>
				</td>
				<td>当前办理人：</td>
				<td>
					<input type="text" name="taskAssignee" id="taskAssignee" 
							style="width: 100px;"/>
				</td> -->
				<td>开始时间：</td>
				<td>
					<input  class="date"  type="text" id="startTime" name="startTime"/>
					--至--
					<input  class="date"  type="text" id="endTime" name="endTime"/>
				</td>
				<td><button type="button" onclick="searchHandler()"><span class="icon_find">查询</span></button></td>
				<td><button type="button" onclick="urgetodo()"><span class="icon_add">催办</span></button></td>
			</tr>
		</table>
		</form>
	</div>  --%>

	<div class="padding_right5">
		<div id="maingrid"></div>
    </div>
    <script type="text/javascript">
    	var ctx="${ctx}";
	 	var personTreeOptions = {
					treeType:'tree_type_person',
					title:'委办局人员',
					orgCheckSetting: {
						orgNoCheck:true,
						deptNoCheck:true,
						groupNoCheck:true,
						positionNoCheck:true,
						personNoCheck:false
				    },
				    selectedMulti:true,
					callback: {
						afterConfirm:function(checkedNodes,selectedNodes){
							if(checkedNodes.length>0){
								if(checkedNodes[0].orgType=="Person"){
									//$('#drafter').val(checkedNodes[0].name);
									var personGuids = "";
									var personNames = "";
									for(var i = 0;i<checkedNodes.length;i++){
										if(i == 0){
											personGuids = checkedNodes[i].ID;
											personNames = checkedNodes[i].name;
										}else{
											personGuids = personGuids + "," + checkedNodes[i].ID;
											personNames = personNames + "," + checkedNodes[i].name;
										}
									}
									 $.ajax({
									       async : false,  
									       cache : false,  
									       type: 'POST',
									       dataType:"json",
									       url: '${ctx}'+'/auditRole/saveRole?personGuids='+personGuids+'&personNames='+personNames,
									       success:function(data){
									    	   if(data.success){
									    		   alert('保存成功！');  
										    	   g.loadData();
									    	   }else{
									    		   alert('保存失败！');   
									    	   }
									       },
									       error: function () {
									           alert('保存失败！');  
									       }
									});
								}else{
									$.messager.alert('提示', "只能点选人员","info");
									return false;
								}
							}
						}
					}
			};
	 	
			 //点击弹出人员树
			function addPerson(){
				$.fn.extcloudTreeStructure(personTreeOptions);
			} 
			 
			 //删除
			function deleteRole(personGuid){
			 	 $.ajax({
				       async : false,  
				       cache : false,  
				       type: 'POST',
				       dataType:"json",
				       url: '${ctx}'+'/auditRole/deleteRole?personGuids='+personGuid,
				       success:function(data){
				    	   if(data.success){
				    		   alert('删除成功！');  
					    	   g.loadData();
				    	   }else{
				    		   alert('删除失败！');
				    	   }
				       },
				       error: function () {
				           alert('删除失败！');  
				       }
				}); 
			}
			 
			 //删除人员
			function deletePerson(){
				 var obj = g.getCheckedRows();
				 var personGuids = "";
				 if(obj.length==0){
					 alert("请选择一条记录！")
				 }else{
					 for(var i = 0;i<obj.length;i++){
						 if(i==0){
							 personGuids = obj[i].PERSONGUID; 
						 }else{
							 personGuids = personGuids + "," + obj[i].PERSONGUID;
						 }
					 }
				 	 $.ajax({
					       async : false,  
					       cache : false,  
					       type: 'POST',
					       dataType:"json",
					       url: '${ctx}'+'/auditRole/deleteRole?personGuids='+personGuids,
					       success:function(data){
					    	   if(data.success){
					    		   alert('删除成功！');  
						    	   g.loadData();
					    	   }else{
					    		   alert('删除失败！');
					    	   }
					       },
					       error: function () {
					           alert('删除失败！');  
					       }
					}); 
				 }
			} 
			 
			 //绑定事项
			function approveItemConfig() {
			    window.Dialog.open({
			            URL:'${ctx}'+'/auditRole/showApproveItem',
			            Title:"事项列表",Width:winWidth*2/3,Height:winHeight*3/4});
			}

			
	</script>

</body>
</html>
