<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>窗口申请</title>
<meta http-equiv="X-UA-Compatible" content="IE=10" />
<script type="text/javascript" src="${ctx}/static/risesoft/js/myCore.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.orgStructure.js">
</script>
<!-- 弹出人员选择框开始 -->
<script type="text/javascript" src="${ctx}/static/js/choiceperson.js">
</script>
<!-- 弹出人员选择框结束-->
</head>
<body>	
		<div>
		<table align="center">		
			<tr>
				<td width="45%" align="right">申请人:</td>					
			<td><input value="${username}" readonly="true"/></td>	
 			</tr>			
			<tr>
				<td align="right">所属部门和科室</td>
				<td>
					<input value="${departmentdesk}" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td align="right">申请人所属大厅</td>
				
				<td>
					<input value="${windowhallname}" readonly="true" Style="width:200"/>
				</td>
			</tr>
			<tr>
				<td align="center" colspan="2"><button onclick="commit();"><span class="icon_ok">申请窗口</span></button></td>				
			</tr>
		</table>
		</div>			
	 <div class="padding_right5">
		<div id="maingrid"></div>
    </div> 

 <script type="text/javascript">
 	var ctx='${ctx}';
 	var g;
	function initComplete(){
		g=$('#maingrid').quiGrid({
			columns:[
					 {display:'',name:'guid',align:'center',width:'10%',showTitle:true,hide:true},
			         {display:'申请人',name:'applyperson',align:'center',width:'10%',showTitle:true},			         
			         {display:'所属部门和科室',name:'departmentdesk',align:'center',width:'10%',showTitle:true},
			         {display:'窗口所在大厅名称',name:'windowhallname',align:'center',width:'15%',showTitle:true},
			         {display:'窗口名称',name:'entitywindowname',align:'center',width:'25%',showTitle:true},
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
			url:"${ctx}/hallmanager/windowapplypage",usePager:true,rownumbers:true,percentWidthMode:true,checkbox:false,
			 pageSize:20,
		     params:[{pageNo:"2"},{pageSize:"20"}],
		     height: '100%', width:"100%"
		})
	} 
</script>
<script type="text/javascript">	
	 function commit(){
			 $.ajax({
					type:'POST',
					dataType:'json',
					data:{employeeid:"${employeeid}",proposer:"${username}",departmentdesk:"${departmentdesk}",windowhallname:"${windowhallname}"}, 
					url:'${ctx}/hallmanager/windowapplysend',
					success:function(date){
						Dialog.alert(date.message,function(){window.location.reload()});
							}
						})
		 }				 

	
	//删除	
	function onDelete(windowname,enrollnumber,windowhallname){
		var row=g.getSelectedRows();
		var len=row.length;
		if(len<1){
			Dialog.alert("至少选择一条记录！|提示",null);
			return;
		}else if(len>1){
			Dialog.alert("只能选择一条记录!|提示",null);
			return;
		}else if(len==1){
			Dialog.confirm("确定要删除该记录吗？",function(){
				$.ajax({
					type:'POST',
					dataType:'json',
					url:'${ctx}/zkAttendance/windowsdelete',
					data:{windowname:windowname,enrollnumber:enrollnumber,windowhallname:windowhallname},
					success:function(data){
						Dialog.alert(data.message);
					}
				})		
				});
		}			
	}
</script>
<script type="text/javascript">

</script>	
</body>
</html>
