<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="utf-8"%>
<%@ include file="/static/common/util.jsp"%>   
<%@ include file="/static/common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>窗口批准</title>
<script type="text/javascript" src="${ctx}/static/risesoft/js/myCore.js"></script>
<script type="text/javascript">
//用户至少勾选一行	
function select(type){
		var row=g.getSelectedRows();
		var len=row.length;
		if(len<1){
			Dialog.alert("至少选择一条记录！|提示",null);
			return;
		}else if(len>1){
			Dialog.alert("只能选择一条记录!|提示",null);
			return;
		}else if(len==1){
			var guid=row[0].guid;
			var status=row[0].status;
			if(status=='0'){
				if(type=='2'){
					//批准
					Dialog.open({URL:"${ctx}/hallmanager/choice?guid="+guid+"&type="+type,Title:"选择实体大厅窗口",Width:700,Height:430});
				}else{
					//不批准
					$.ajax({
					type:'POST',
					dataType:'json',
					data:{guid:guid,type:type},
					url:'${ctx}/hallmanager/windowapprovestatus',
					success:function(data){
						Dialog.alert(data.message,function(){window.location.reload()});	
							}
						})
					}
			}else{
				Dialog.alert("不能修改已操作的状态");
			}	
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
</script>
<script type="text/javascript">
	var g;
 	function initComplete(){
		g=$('#maingrid').quiGrid({
			columns:[
					 {display:'',name:'guid',align:'center',width:'10%',showTitle:true,hide:true},
			         {display:'申请人',name:'applyperson',align:'center',width:'10%',showTitle:true},
			         {display:'所属部门和科室',name:'departmentdesk',align:'center',width:'10%',showTitle:true},
			         {display:'窗口所在大厅名称',name:'windowhallname',align:'center',width:'10%',showTitle:true},
			         {display:'窗口名称',name:'entitywindowname',align:'center',width:'10%',showTitle:true},
			         {display:'申请时间',name:'applytime',align:'center',width:'10%',showTitle:true},
			         {display:'批准人',name:'approveperson',align:'center',width:'10%',showTitle:true},
			         {display:'批准时间',name:'approvetime',align:'center',width:'10%',showTitle:true},
			         {display:'状态',name:'status',align:'center',width:'10%',showTitle:true,
			        	 	render:function(rowdata){
			        	 		if(rowdata.status=='0'){
			        	 			return '审核中';
			        	 		}else if(rowdata.status=='1'){
			        	 			return '未批准';
			        	 		}else{
			        	 			return '已批准'
			        	 		}
			        	 	}	
			         }
			         ],
			url:"${ctx}/hallmanager/windowapprovepage",usePager:true,rownumbers:true,percentWidthMode:true,checkbox:true,
			 pageSize:20,
		     params:[{pageNo:"2"},{pageSize:"20"}],
		     height: '100%', width:"100%"
		})
	} 
</script>
</head>
<body>
	<div class="box2">
		<form id="queryForm">
			<table>
				<tr>
					<td>窗口所在大厅名称</td>
					<td><input name="windowhallname"/></td>
					<td><button type="button" onclick="searchHandler();"><span class="icon_find">查询</span></button></td>
					<td><button type="button" onclick="select('2');"><span class="icon_ok">批准</span></button></td>
					<td><button type="button" onclick="select('1');"><span class="icon_delete">不予批准</span></button></td>	
				</tr>
			</table>
		</form>
	</div>
	<div class="padding_right5">
		<div id="maingrid"></div>	
	</div>
</body>
</html>