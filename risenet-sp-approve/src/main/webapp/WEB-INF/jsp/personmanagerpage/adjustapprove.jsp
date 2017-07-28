<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>调整批准</title>
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
			var employeeid=row[0].employeeid;
			var windowhallname=row[0].windowhallname;
			var adjuststatus=row[0].adjuststatus;
			if(adjuststatus=='0'){
				$.ajax({
					type:'POST',
					dataType:'json',
					data:{employeeid:employeeid,windowhallname:windowhallname,type:type},
					url:'${ctx}//personmanager/updatestatus',
					success:function(data){
						Dialog.alert(data.message,function(){window.location.reload()});	
							}
						})
			}else{
				Dialog.alert("不能对已有状态进行修改");
			}

		}			
	} 
	
	//调整原因
	function minute(){
		var row=g.getSelectedRows();
		var len=row.length;
		if(len<1){
			Dialog.alert("至少选择一条记录！|提示",null);
			return;
		}else if(len>1){
			Dialog.alert("只能选择一条记录!|提示",null);
			return;
		}else if(len==1){			
			Dialog.alert(row[0].adjustcause);
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
</head>
<body>
	<div class="box2" panelTitle="查询" id="searchPanel">
		<form id="queryForm">
			<table>
				<tr>
					<td>窗口人员姓名</td>
					<td><input id="personname" name="windowname"/></td>
					<td><button type="button" onclick="searchHandler();"><span class="icon_find">查询</span></button></td>
					<td><button type="button" onclick="select('2');"><span class="icon_ok">批准</span></button></td>
					<td><button type="button" onclick="select('1');"><span class="icon_delete">不予批准</span></button></td>	
				</tr>
			</table>
		</form>
	</div>
	<div class="padding_right5">
			<div id="maingrid">
			</div>
	</div>
 <script type="text/javascript">
 	var g;
	function initComplete(){
		g=$('#maingrid').quiGrid({
			columns:[
					 {display:'管理员',name:'username',align:'center',width:'10%',showTitle:true},
			         {display:'窗口人员姓名',name:'windowname',align:'center',width:'10%',showTitle:true},
			         {display:'窗口人员guid',name:'employeeid',align:'center',width:'10%',showTitle:true,hide:true},
			         {display:'工号',name:'enrollnumber',align:'center',width:'10%',showTitle:true},
			         {display:'窗口所在大厅名称',name:'windowhallname',align:'center',width:'10%',showTitle:true},
			         {display:'所属部门和科室',name:'departmentdesk',align:'center',width:'10%',showTitle:true},
			         {display:'调整状态',name:'adjuststatus',align:'center',width:'10%',showTitle:true,
			        	 render:function(rowdata){
			        		 if(rowdata.adjuststatus=='0'){
			        			 return '申请中';
			        		 }else if(rowdata.adjuststatus=='1'){
			        			 return '未通过';
			        		 }else if(rowdata.adjuststatus=='2'){
			        			 return '成功通过';
			        		 }
			        	 }
			        		 
			         },
			         {display:'调整原因',name:'adjustcause',align:'center',width:'10%',
							render:function(){
								ret='<a href="javascript:void(0);" onclick="minute();">查看</a>';
								return ret;
							}
			         },
			         {display:'是否为窗口负责人',name:'ismanager',align:'center',width:'10%',showTitle:true,
			        			render:function(rowdata){
			        				if(rowdata.ismanager=='1'){
			        					return '是';
			        				}else{
			        					return '否';
			        				}
			        			}	 
			         }
			         ],
			url:"${ctx}//personmanager/adjustpage",usePager:true,rownumbers:true,percentWidthMode:true,checkbox:true,
			 pageSize:20,
		     params:[{pageNo:"2"},{pageSize:"20"}],
		     height: '100%', width:"100%"
		})
	} 
</script> 
</body>
</html>
