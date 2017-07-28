<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv=X-UA-Compatible content="IE=edge">
<title>Insert title here</title>
<link type="text/css" rel="stylesheet" href="${ctx}/static/risesoft/css/code39.css" />

<script type="text/javascript">
function certificateSave(){
	if (document.all("yxtywlsh").value == "") {
		alert("请输入业务编号");
		document.all("yxtywlsh").focus();
		return false;
	}else if (document.all("sqrType").value == "") {
		alert("请选择申请人类型");
		document.all("sqrType").focus();
		return false;
	}else if (document.all("sqr").value == "") {
		alert("请输入申请单位/人");
		document.all("sqr").focus();
		return false;
	}else if (document.all("cardId").value == "") {
		alert("请输入申请人证件代码");
		document.all("cardId").focus();
		return false;
	}else if (document.all("lxr").value == "") {
		alert("请输入联系人");
		document.all("lxr").focus();
		return false;
	}else if (document.all("lxrType").value == "") {
		alert("请选择联系人证件类型");
		document.all("lxrType").focus();
		return false;
	}else if (document.all("lxrId").value == "") {
		alert("请输入联系人证件代码");
		document.all("lxrId").focus();
		return false;
	}else if (document.all("mobile").value == ""){
        alert("请输入联系人手机号");
        document.all("mobile").focus();
        return false;
    }

	$.post("${ctx }/togetherWindow/saveDeclareInfo",$("#form1").serialize(),function(data){
		if(data.msg) {
			alert('保存成功！');					
			//window.parent.location.reload();
			window.location.href= "${ctx}/togetherWindow/goscan?instanceGuid="+data.instanceguid+"&doctypeguid="+data.doctypeguid;
			//Dialog.open({URL:"${ctx}/togetherWindow/goscan?instanceGuid="+data.instanceguid+"&doctypeguid="+data.doctypeguid,Title:"证照资料录入",Width:900,Height:680});
		}else {
			alert('保存失败！');
		}
	} );
}
</script>

</head>
<body align="center">
<form id="form1" action="${ctx }/togetherWindow/saveDeclareInfo" method="post">
	<input type="hidden" name="approveitemguid" value="${approveitemguid }">
	<table class="tableStyle">
		<thead>
		<tr>
			<td class="ali02" colspan="2">
				<h1>基本信息处理表</h1>
			</td>
		</tr>
		</thead>
		<tr style="line-height: 26px;">
			<td width="35%"  class="ali03">业务编号:<span style="color: red;">*</span></td>
			<td width="65%"><input type="text" name="yxtywlsh"
				id="yxtywlsh" style="width: 300px" /></td>
		</tr>
		<tr>
			<td class="ali03">事项名称:<span style="color: red;">*</span></td>
			<td><input type="text" name="approveItemName"
				id="approveItemName" style="width: 300px" value="${approveitemname }"/></td>
		</tr>
		<tr>
			<td class="ali03">项目名称:<span style="color: red;">*</span></td>
			<td><input type="text" name="projectName"
				id="projectName" style="width: 300px" value="${approveitemname }" /></td>
		</tr>
		
		<tr>
			<td class="ali03">申请人类型:<span style="color: red;">*</span></td>
			<td><select type="text" name="sqrType" prompt="请选择" data='{"list":[{"value":"1","key":"个人"},{"value":"2","key":"企业"},{"value":"9","key":"其他"}]}'
				id="sqrType" boxWidth="300" boxHeight="300"
				selWidth="300"></select></td>
		</tr>
		<tr>
			<td class="ali03">申请单位/人:<span style="color: red;">*</span></td>
			<td><input type="text" name="sqr"
				id="sqr" style="width: 300px" /></td>
		</tr>
		<tr>
			<td class="ali03">证件代码:<span style="color: red;">*</span></td>
			<td><input type="text" name="cardId"
				id="cardId" style="width: 300px" /></td>
		</tr>
		<tr>
			<td></td>
			<td style="color: red;">注：申请单位填写组织机构/社会统一信用代码，申请人填写身份证号码。</td>
		</tr>
		<tr>
			<td class="ali03">联系人:<span style="color: red;">*</span></td>
			<td><input type="text" name="lxr"
				id="lxr" style="width: 300px" /></td>
		</tr>
		<tr>
			<td class="ali03">联系人证件类型:<span style="color: red;">*</span></td>
			<td><select name="lxrType" id="lxrType" prompt="请选择"
				url="${ctx}/togetherWindow/zjlxSelect" boxWidth="300" boxHeight="300"
				selWidth="300"></select></td>
		</tr>
		<tr>
			<td class="ali03">联系人证件代码:<span style="color: red;">*</span></td>
			<td><input type="text" name="lxrId"
				id="lxrId" style="width: 300px" /></td>
		</tr>
		<tr>
			<td class="ali03">联系人手机:<span style="color: red;">*</span></td>
			<td><input type="text" name="mobile"
				id="mobile" style="width: 300px" /></td>
		</tr>
		<tr>
			<td class="ali02" colspan="2">
				<button type="button" onclick="certificateSave()">
					<span class="icon_save">保存并录入证照信息</span>
				</button>
			</td>
		</tr>
	</table>
</form>
</body>
</html>