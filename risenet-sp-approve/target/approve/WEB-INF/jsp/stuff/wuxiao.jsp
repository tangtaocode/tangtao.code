<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<%@ include file="/static/common/util.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
function tijiao() {
	$.post('${ctx}/stuff/saveWuxiao',$("#wuxiaoForm").serialize(),function(data){
		if(data.msg) {
			alert('保存成功！');					
			window.parent.location.reload();
		}else {
			alert('保存失败！');
		}
	});			 
}
</script>
</head>
<body>
	<form id="wuxiaoForm" >
		<input type="hidden" name="stuffdataguid" id="stuffdataguid" value="${stuffdataguid }"></input>
		<table width="100%">
			<tr><td><label for="remark">无效原因：</label></td></tr>
			<tr><td><textarea type="text" name="remark" id="remark" maxNum="1000"></textarea></td></tr>
			<tr>
				<td align="center"><button type="button" onclick="tijiao();"><span class="icon_save">保存</span></button></td>
			</tr>
		</table>
	</form>

</body>
</html>