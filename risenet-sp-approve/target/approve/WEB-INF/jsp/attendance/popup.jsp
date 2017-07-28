<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>IP和端口设置</title>
<script type="text/javascript">
	 function save(){
		 $("#form").ajaxSubmit({
			type : 'POST',
			dataType : 'json', 
			/* url : '${ctx}/zkAttendance/accept?department='+$('select').find('option:selected').text(), */
			url : '${ctx}/zkAttendance/accept',
			success : function(responseText, statusText, xhr, $form) {
				//reloadGrid();
				Dialog.alert(responseText.message);
 		}
		}); 		
	} 	
	
</script>
<style type="text/css">
/* #select{
width:300px;
} */
</style>
</head>
<body>
	<div id="div1" class="box1" panelWidth="500">
		<form action="" method="post" id="form">
			<table class="tableStyle" formMode="line">
			<tr>
				<td>IP</td>
				<td><input name="ip"/></td>
			</tr>
			<tr>
				<td>端口</td>
				<td><input name="port"/></td>
			</tr>
			<tr>
				<td>请输入大厅或街道名称</td>
				<td>
					  <select  selWidth="200" name="department">
						<option value="罗湖区行政服务大厅">罗湖区行政服务大厅</option>
						<option value="罗湖区民政局婚姻登记大厅">罗湖区民政局婚姻登记大厅</option>
						<option value="罗湖区财政局专业服务大厅">罗湖区财政局专业服务大厅</option>
						<option value="罗湖区人力资源局专业服务大厅">罗湖区人力资源局专业服务大厅</option>
						<option value="罗湖区卫生监督所专业服务大厅">罗湖区卫生监督所专业服务大厅</option>												
						<option value="桂园街道">桂园街道</option>
						<option value="黄贝街道">黄贝街道</option>
						<option value="东门街道">东门街道</option>						
						<option value="翠竹街道">翠竹街道</option>
						<option value="东晓街道">东晓街道</option>
						<option value="南湖街道">南湖街道</option>						
						<option value="笋岗街道">笋岗街道</option>
						<option value="东湖街道">东湖街道</option>
						<option value="莲塘街道">莲塘街道</option>						
						<option value="清水河街道">清水河街道</option>
					</select> 
				</td>
			</tr>
			<tr>
				<td>是否设置为主厅</td>
				<td><input type="radio" name="hall" value="0"/>是<input type="radio" name="hall" value="1"/>否</td>
			</tr>
			<tr>
				<td colspan="2"><button type="button" onclick="save()">保存</button></td> 								 
			</tr>
			</table>
		</form>
	</div>
</body>
</html>