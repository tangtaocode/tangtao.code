<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>基本表格模板</title>

<script type="text/javascript" src="${ctx}/static/risesoft/js/myCore.js"></script>
</head>
<body>
	<div id="div1">
		
		<button onclick="handleFlowManage()"><span class="icon_config">流程配置</span></button>
		<button onclick="approveItemLimitConfig()"><span class="icon_calendar">时限配置</span></button>
		<button onclick="eformConfig()"><span class="icon_calendar">表单配置</span></button>
	</div>
	<div class="box2" panelTitle="查询" id="searchPanel">
	
		<form action="${ctx}/bureau/bureauList" id="queryForm" method="post" align="center">
		<input type="hidden" value="${Workflow_GUID}" name="Workflow_GUID"/>
		<input type="hidden" value="${bos }" name="bos"/>
		<input type="hidden" value="${isProject }" name="isProject"/>
		<input type="hidden" value="${isExternal}" name="isExternal"/>
		<table>
			<tr>
			  
				<td>部门:</td>
				<td>
					<select name="bureauGUID" id="bureauGUID" prompt="全部" url="${ctx}/bureau/streetSelect" boxWidth="200" boxHeight="200" selWidth="180"></select>
				
				</td>
			
				<td>事项名称：</td>
				<td><input type="text" name="approveItemName" style="width:200px"/></td>
				
				<td>类别:</td>
				<td>
					<select name="approveItemType">
						<option VALUE="">选择类别</option>
						<option value="承诺件">承诺件</option>
						<option value="即办件">即办件</option>
						</select>
				</td>
				<td>运行状态:</td>
				<td>
					<select name="approveItemStatus">
						<option value="">选择类别</option>
						<option value="运行">运行</option>
						<option value="停止">停止</option>
					</select>
				</td>
				<td><button type="button" onclick="searchHandler()"><span class="icon_find">查询</span></button></td>
			</tr>
		</table>
		</form>
		
	</div>

	<div class="padding_right5">
		<div id="maingrid"></div>
    </div>

<script type="text/javascript">
		var frameID = newGuid();
        var g;
		function initComplete(){
			 g = $("#maingrid").quiGrid({
		       columns: [ 
			                { display: '单位名称', name: 'BUREAUNAME', align: 'center', width: "15%",showTitle:true},
			                { display: '', name: 'APPROVEITEMGUID', align: 'center', width: "15%",hide:true},
			                { display: '科室名称', name: 'DEPARTMENT_NAME', align: 'center', width: "10%",showTitle:true},
			                { display: '事项名称', name: 'APPROVEITEMNAME',align: 'center', width: "25%",showTitle:true},
			                { display: '查看子事项', name: '',align: 'center', width: "20%",showTitle:true},
			                { display: '时限', name: 'TIMELIMIT',   align: 'center', width: "10%"},
			                { display: '类别', name: 'APPROVEITEMTYPE',   align: 'center', width: "10%" },
		                	{ display: '状态', name:'APPROVEITEMSTATUS', align: 'center', width:"10%"	}
		         ], 
		        url:"${ctx}/bureau/streetList",usePager:true,rownumbers:true,percentWidthMode:true,checkbox:true,
		       	pageSize:20,
		       	params:[{pageNo:"2"},{pageSize:"20"}],
		        height: '100%', width:"100%"
		        
         	});
		}
		
		//流程管理
		function handleFlowManage(){
			var row = g.getSelectedRows();
			var len = row.length;
		    if(len<1) { 
		    	Dialog.alert("至少选择一条记录！|提示",null);
				return;
			}else if(len>1){
				Dialog.alert("只能选择一条记录！|提示",null);
				return;
			}else if(len==1){
				var approveGuid = row[0].APPROVEITEMGUID;			
				Dialog.open({URL:"${ctx}/bureau/lcgl?approveGuid="+approveGuid,Title:"流程管理",Width:500,Height:330});
			}
		}
		
		function approveItemLimitConfig(){
			var row = g.getSelectedRows();
			var len = row.length;
		    if(len<1) { 
		    	Dialog.alert("至少选择一条记录！|提示",null);
				return;
			}else if(len>1){
				Dialog.alert("只能选择一条记录！|提示",null);
				return;
			}else if(len==1){
				//1.确定该事项绑定流程  2.跳转到流程的时限配置页面
				var approveItemGuid = row[0].APPROVEITEMGUID;			
				$.ajax({
				     async:false,
				     cache: false,
			         type: "GET",
			         url: "${ctx}/bureau/processDefineId",
			         data: {approveItemGuid:approveItemGuid},
			         dataType: "json",
			         success: function(data){
			              if(data.success){
			            	  alert("跳转到流程时限设置页面！流程定义Id:"+data.processDefineId);
			            	  var processDefinitionId=data.processDefineId
			            	  var procDefKey=data.processDefineKey
			            	  var procDefName=data.processDefineName
			            	  Dialog.open({URL:"${ctx}/reminderconf/show?procDefKey="+procDefKey+"&processDefinitionId="+processDefinitionId+"&procDefName="+procDefName+"&customId="+approveItemGuid,Title:"事项时限配置",Width:700,Height:500});
			              }else{
			            	  alert("请先绑定一个流程！");
			            	  return false;
			              }
			        }
				});
			}
		}
		
		
		//将事项与表单进行配置(一个事项对应一个或者多个表单)
		function eformConfig(){
			var row=g.getSelectedRows();			
			var len=row.length;
			if(len<1){
				Dialog.alert("至少选择一条记录!|提示",null);
				return;
			}else if(len>1){
				Dialog.alert("只能选择一条记录!|提示",null);
				return;
			}else if(len==1){
				//确定该事项要绑定表单，跳转到表单绑定页面
				var approveitemguid=row[0].APPROVEITEMGUID;
				Dialog.open({URL:"${ctx}/bureau/eformbound?approveitemguid="+approveitemguid,title:"事项与表单绑定",width:500,height:200})
				
			}
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
