<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<title></title>

</head>
<body>

	<div>
		<form id="fileForm" enctype="multipart/form-data" action="">
			<table>
				<tr>
					<td>委办局名称：</td>
					<td><input type="text" name="bureau_name" id="bureau_name" value="${bureau_name}"
						style="width: 250px; height: 20px" readonly="readonly"/>
						<input type="button" id="choiceDept" value="选取委办局" onclick="choice('${ctx}/department/userChoicePage','6');"/></td>
						<td>&nbsp;&nbsp;&nbsp;</td>
					<td><input type="hidden" name="bureau_guid" value="${bureau_guid}"
						id="bureau_guid"/></td>
				</tr>
				
				<tr>
					<td>模版类型：</td>
					<td>
						<input type="text" id="template_type" name="template_type" value="${template_type}" readonly="readonly">
						<input type="button" id="templateTypeButton" value="选择模版类型" onclick="choiceTemplateType();">
					</td>
				</tr>
				
				<tr>
					<td><input type="hidden" name="template_guid" id="template_guid" value="${template_guid}"/></td>
					<td>&nbsp;&nbsp;&nbsp;</td>
				</tr>

			</table>
			<div align="center">
				<input type="file" name="attachmentFile" id="attachmentFile" 
					style="width: 420px; height: 26px" />
			</div>
		</form>
	</div>
</body>
</html>
