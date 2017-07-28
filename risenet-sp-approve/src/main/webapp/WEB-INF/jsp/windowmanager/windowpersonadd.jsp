<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>窗口人员添加</title>

<script type="text/javascript" src="${ctx}/static/risesoft/js/myCore.js"></script>
<script type="text/javascript">
var frameID = newGuid();
	//添加窗口人员
	function add(){
/* 	var windowhallname=$('#sel').val();
	alert(windowhallname); */
	Dialog.open({URL:"${ctx}/hallmanager/adduser",title:"窗口人员添加",Height:420});		
	}
	
	//发送调整申请
	function edit(){
		var row=g.getSelectedRows();
		var len=row.length;
		if(len<1){
			Dialog.alert("请选择一条记录!|提示",null);
			return;
		}else if(len>1){
			Dialog.alert("只能选择一条记录!|提示",null);
			return;
		}else if(len==1){	
			var adjuststatus=row[0].adjuststatus;
			if(typeof(adjuststatus)=='undefined'||adjuststatus=='1'){
				Dialog.open({URL:"${ctx}/hallmanager/edit",Width:500,Height:340});
			}else{
				Dialog.alert("不能修改调整状态");
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
	
	//删除窗口人员	
	function onDelete(){
		var row=g.getSelectedRows();
		var len=row.length;		
		if(len<1){
			Dialog.alert("至少选择一条记录！|提示",null);
			return;
		}else if(len>1){
			Dialog.alert("只能选择一条记录!|提示",null);
			return;
		}else if(len==1){
			var adjuststatus=row[0].adjuststatus;
			var windowname=row[0].windowname;
			var enrollnumber=row[0].enrollnumber;
			var windowhallname=row[0].windowhallname;
			if(adjuststatus=='2'){
				Dialog.confirm("确定要删除该记录吗？",function(){
					$.ajax({
						type:'POST',
						dataType:'json',
						url:'${ctx}/hallmanager/windowsdelete',
						data:{windowname:windowname,enrollnumber:enrollnumber,windowhallname:windowhallname},
						success:function(data){
							Dialog.alert(data.message,function(){window.location.reload()});
						}
					})		
				});				
			}else{
				Dialog.alert("请先发送调整申请");
			}

		}			
	}

</script>

</head>
<body>
	<div class="box2">
		<form id="queryForm">
			<table>
				<tr>
					<td>窗口人员姓名</td>
					<td><input id="personname" name="windowname"/></td>
					<td><button type="button" onclick="searchHandler();"><span class="icon_find">查询</span></button></td>
					<td><button type="button" onclick="add();"><span class="icon_add">新增</span></button></td>	
					<td><button type="button" onclick="edit();"><span class="icon_edit">发送调整申请</span></button></td>
					<td><button type="button" onclick="onDelete();"><span class="icon_delete">删除</span></button></td>
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
			         {display:'窗口所在大厅名称',name:'windowhallname',align:'center',width:'15%',showTitle:true},
			         {display:'窗口名称',name:'entitywindowname',align:'center',width:'25%',showTitle:true},
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
			url:"${ctx}/hallmanager/personaddpage",usePager:true,rownumbers:true,percentWidthMode:true,checkbox:true,
			 pageSize:20,
		     params:[{pageNo:"2"},{pageSize:"20"}],
		     height: '100%', width:"100%"
		})
	} 
</script>
<script type="text/javascript">
	

</script>	 
</body>
</html>
