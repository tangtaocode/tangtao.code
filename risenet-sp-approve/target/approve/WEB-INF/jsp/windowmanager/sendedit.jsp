<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>调整申请</title>
<script type="text/javascript">
	var row=parent.g.getSelectedRows();	
	function start(){
		$('#employeeid').val(row[0].employeeid)
		$('#windowname').val(row[0].windowname);
		$('#departmentdesk').val(row[0].departmentdesk);
		$('#windowhallname').val(row[0].windowhallname);
		$('#username').val(row[0].username);
	}
	
	//提交调整申请
 	function save(){
		var content=$('#content').val();
		var employeeid=$('#employeeid').val();
 		if(content==""){
			Dialog.alert("请填写调整原因");
		}else{
			$.ajax({
				type:'POST',
				dataType:'json',
				url:'${ctx}/hallmanager/adjustapply',
				data:{content:content,employeeid:employeeid},
				success:function(data){	
					Dialog.alert(data.message,function(){
						parent.location.reload();
						parent.Dialog.close();
					});
				}				
			})
		} 
	} 
</script>
</head>
<body onload="start();">
<div class="box1" panelWidth="500">
	<form>
		    <table class="tableStyle" formMode="line">				
		        <tr>
		        	<td>窗口人员姓名：</td>
		        	<td><input type="text" id="windowname" readonly/></td>
		        </tr>		
		        <tr>
		        	<td>所属科室和部门：</td>
		        	<td><input type="text" id="departmentdesk" readonly/></td>
		        </tr>
		        <tr>
		        	<td>目前所在大厅：</td>
		        	<td><input type="text" id="windowhallname" readonly/></td>
		        </tr>
		         <tr>
		        	<td>管理员姓名：</td>
		        	<td><input type="text" id="username" readonly/></td>
		        </tr>     				       		
		        <tr>
		        	<td>调整原因：</td>
		        	<td><textarea  id="content" style="resize:none;width:300px;height:100px" maxNum="100"></textarea></td>
		        </tr>		
		        <tr>
		        	<td colspan="2"><input type="button" value="提交" onclick="save();"/></td>
		        </tr>
				<input type="hidden" id="employeeid"/>
		    </table>
	</form>

</div>
 

</body>
</html>