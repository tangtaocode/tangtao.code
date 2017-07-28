<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ include file="/static/common/util.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查看备注</title>
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/framework.js"></script>
</head>

<script type="text/javascript">

</script>

<body>
<div onload="getDate();" class="box1" id="formContent" whiteBg="true">
<form id="form" action=""   method="post">	
	<table class="tableStyle"  formMode="transparent" border="0">
	<tr>
	<td ><textarea rows="10" style="width: 100%" >${score.remarks }</textarea></td>
				
	</tr>
	</table>
</form>
</div>
</body>
</html>