<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>材料证书</title>
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/framework.js"></script>
</head>
<body>
	<form  id="form">
	<div>
		<input type="hidden" id="idText" value="${itemId}"/>
		<table align="center">
			<tr>
				<td cospan="2" align="center">&nbsp;&nbsp;&nbsp;&nbsp;</td>
			</tr>
			<tr>
				<td align="center">材料名称</td>
				<td align="center"><input type="text" id="materialName"  style="width:250px" />
				</td>
			</tr>
			<tr>
				<td align="center">材料描述</td>
				<td align="center"><textarea type="text" id="materialDis"  style="width:250px" ></textarea>
			</tr>
			<tr style="height:80px">
				<th colspan="2" align="center"><input type="button" onclick="save();" value="保存"/></th>
				
			</tr>
		</table>
		</div>
	</form>	
</body>
<script type="text/javascript">


	function save(){
		var name= $("#materialName").val();
		var itemId= $("#idText").val();
		var materialDis=$("#materialDis").val();
		//console.info(name);
		//parent.add(name);
		 $("#form").ajaxSubmit({
				type : 'POST',
				dataType : 'json', 
				url : '${ctx}/certmanage/saveMaterial?name='+name+"&itemId="+itemId+"&materialDis="+materialDis,
				success : function(responseText, statusText, xhr, $form) {
					//reloadGrid();
					//console.info(responseText);
					//Dialog.alert(responseText.info);
					parent.add(responseText.info);
					//Dialog.alert(responseText.info);
				}
			});
	}
	
</script>
</html>