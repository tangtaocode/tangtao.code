<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/static/common/util.jsp"%>
<!DOCTYPE">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新增证照类别</title>
</head>
<body>
<div class="box1" panelWidth="500">

<form id="myForm">

    <table class="tableStyle" formMode="line">
        <tr><td>证照类别编号：</td><td><input type="text" name="doctypecode" class="validate[required,custom[noSpecialCaracters]]"/></td></tr>

        <tr><td>证照名称  ：</td><td><input type="text" name="doctypename" class="validate[required,custom[noSpecialCaracters]]"/></td></tr>    

        <tr><td>出证局：</td><td><select name="departmentGuid" id="departmentGuid" prompt="选择出证局" url="${ctx}/bureau/wbjSelect" boxWidth="200" boxHeight="200" selWidth="180"></select></td></tr>

        <tr><td>备注：</td><td><textarea name="memo"></textarea></td></tr>

        <tr><td colspan="2"><input type="button" value="提交" onclick="validateForm('#form1');"/>&nbsp;<input type="reset" value="重置" /></td></tr>

    </table>

</form>
</div>
<script type="text/javascript">
	function validateForm(containerId){
		var url = "${ctx}/businesstypemanger/doadd";
		$.post(url,$('#myForm').serialize(),function(data){  
			parent.closeDialog(data);    
		});	
	}
</script>
</body>
</html>