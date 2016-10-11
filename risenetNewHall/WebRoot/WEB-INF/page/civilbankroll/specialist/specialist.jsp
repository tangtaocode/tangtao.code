<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../share/taglib.jsp"%>
<html>
	<head>
		<title>评估专家</title>
		<link href="/css/appGuide.css" rel="stylesheet" type="text/css" />
		<script
			src="/js/Scripts/jquery.min.js"></script>
		<script src="/js/zDialog/risenetDialog.js"></script>
		<script type="text/javascript" src="/js/calendar.js"></script>
		<script src="/js/validation.js"></script>
		<script type="text/javascript">
		function doSubmit(type){
			if(!validations("queryFormId")) return false;
			$("#status").attr("value",type);
			$("#queryFormId").submit();
		}
		</script>
	<style type="text/css">
		body{ margin: 0;
	  		padding: 0; 
	  	}
</style>
	</head>
	<body >
		<s:form action="/civilApply/saveSpec.YS" method="post"
			id="queryFormId">
			<s:hidden name="method" value="add"></s:hidden>
			<s:hidden name="civilSpecialist.guid"></s:hidden>
			<s:hidden name="civilSpecialist.status" id="status"></s:hidden>
			<table width="100%" border="0" align="center" cellpadding="0"
				class="table_leftop" cellspacing="0">
				<tr ><th class="top5"></th></tr>
				<tr ><td>
				<div style="overflow: scroll;height:459px;scrollbar-face-color:#E0F2FF;">
				<table width="96%" border="0" align="center" cellpadding="0"
				class="table_leftop" cellspacing="0">
				<tr>
					<td class="title_center">
						专家姓名：
					</td>
					<td class="td_left">
						<s:textfield name="civilSpecialist.name" id="name"
							verify="专家姓名|NotNull" cssClass="zc_input02" size="20" 
							maxLength="30"></s:textfield>
						<span style="color:red">*</span>
					</td>
					<td class="title_center">
						密码:
					</td>
					<td class="td_left">
						<s:textfield name="civilSpecialist.userkey" id="userkey"
							verify="密码|NotNull" cssClass="zc_input02" size="20" 
							maxLength="20"></s:textfield>
						<span style="color:red">*</span>
					</td>
					<td class="title_center">
						性别:
					</td>
					<td class="td_left">
						<s:select list="#{'':'---请选择---','男':'男','女':'女'}" name="civilSpecialist.sex" id="sex"></s:select>
					</td>
				</tr>
				<tr>
					<td class="title_center">
						民族：
					</td>
					<td class="td_left">
						<s:textfield name="civilSpecialist.nation" id="nation"
							verify="民族|NotNull" cssClass="zc_input02" size="20" 
							maxLength="20"></s:textfield>
						<span style="color:red">*</span>
					</td>
					<td class="title_center">
						出生日期:
					</td>
					<td class="td_left">
						<s:textfield name="civilSpecialist.birthday" id="birthday"
							verify="出生日期|NotNull" cssClass="zc_input02" size="20"  onclick="calendar.show(this);" readonly="true"
							maxLength="15"></s:textfield>
						<span style="color:red">*</span>
					</td>
					<td class="title_center">
						政治面貌:
					</td>
					<td class="td_left">
						<s:textfield name="civilSpecialist.politics" id="politics"
							verify="政治面貌|NotNull" cssClass="zc_input02" size="20" 
							maxLength="30"></s:textfield>
						<span style="color:red">*</span>
					</td>
				</tr>
				<tr>
					<td class="title_center">
						担任职务：
					</td>
					<td class="td_left">
						<s:textfield name="civilSpecialist.position" id="position"
							verify="担任职务|NotNull" cssClass="zc_input02" size="20" 
							maxLength="50"></s:textfield>
						<span style="color:red">*</span>
					</td>
					<td class="title_center">
						在所在领域任职时间:
					</td>
					<td class="td_left">
						<s:textfield name="civilSpecialist.worktime" id="worktime"
							verify="在所在领域任职时间|NotNull" cssClass="zc_input02" size="20" 
							maxLength="50"></s:textfield>
						<span style="color:red">*</span>
					</td>
					<td class="title_center">
						文化程度:
					</td>
					<td class="td_left">
					<s:select name="civilSpecialist.education" id="education" cssClass="zc_input02" list="#{'':'---请选择---','小学':'小学','初中':'初中','高中':'高中','大专':'大专','本科':'本科','硕士':'硕士','博士':'博士','其它':'其它'}"></s:select>
						<span style="color:red">*</span>
					</td>
				</tr>
				<tr>
					<td class="title_center">
						籍贯：
					</td>
					<td class="td_left" colspan="5">
						<s:textfield name="civilSpecialist.birthplace" id="birthplace"
							verify="籍贯|NotNull" cssClass="zc_input02" size="90" 
							maxLength="500"></s:textfield>
						<span style="color:red">*</span>
					</td>
				</tr>
				<tr>
					<td class="title_center">
						毕业院校及专业：
					</td>
					<td class="td_left" colspan="5">
						<s:textfield name="civilSpecialist.professional" id="professional"
							verify="毕业院校及专业|NotNull" cssClass="zc_input02" size="90" 
							maxLength="200"></s:textfield>
						<span style="color:red">*</span>
					</td>
				</tr>
				<tr>
					<td class="title_center">
						身份证号码：
					</td>
					<td class="td_left" colspan="6">
						<s:textfield name="civilSpecialist.cardid" id="cardid"
							verify="身份证号码|NotNull" cssClass="zc_input02" size="20" 
							maxLength="30" readonly="true"></s:textfield>
						<span style="color:red">*</span>
					</td>
				</tr>
				<tr>
					<td class="title_center">
						联系电话:
					</td>
					<td class="td_left">
						<s:textfield name="civilSpecialist.telephone" id="telephone"
							verify="联系电话|NotNull" cssClass="zc_input02" size="20" 
							maxLength="30"></s:textfield>
						<span style="color:red">*</span>
					</td>
					<td class="title_center">
						联系手机:
					</td>
					<td class="td_left">
						<s:textfield name="civilSpecialist.mobilephone" id="mobilephone"
							verify="联系手机|NotNull" cssClass="zc_input02" size="20" 
							maxLength="11"></s:textfield>
						<span style="color:red">*</span>
					</td>
					<td class="title_center">
						邮箱：
					</td>
					<td class="td_left">
						<s:textfield name="civilSpecialist.mail" id="mail"
							verify="邮箱|NotNull" cssClass="zc_input02" size="20" 
							maxLength="30"></s:textfield>
						<span style="color:red">*</span>
					</td>
				</tr>
				
				<tr>
					<td class="title_center">
						专业技术资格或职称：
					</td>
					<td class="td_left" colspan="5">
					<s:textfield name="civilSpecialist.qualifications" id="qualifications"
							verify="专业技术资格或职称|NotNull" cssClass="zc_input02" size="90" 
							maxLength="200"></s:textfield>
						<span style="color:red">*</span>
					</td>
				</tr>
				<tr>
					<td class="title_center">
						简历：
					</td>
					<td class="td_left" colspan="5">
						<s:textarea name="civilSpecialist.resume" id="resume" verify="简历|NotNull" cols="90" rows="10" cssClass="zc_input02" maxLength="30"></s:textarea>
						<span style="color:red">*</span>
					</td>
				</tr>
					<tr>
					<td class="title_center">
						获得荣誉：
					</td>
					<td class="td_left" colspan="5">
						<s:textarea name="civilSpecialist.honor" id="honor" verify="获得荣誉|NotNull" cols="90" rows="10" cssClass="zc_input02" maxLength="30"></s:textarea>
						<span style="color:red">*</span>
					</td>
				</tr>
				</table>
				</div>
				</td></tr>
				<tr>
					<td height="60" style="text-align: center" class="td_left"
						colspan="4">
						<input type="button" class="buttonClass" value="暂 存" onclick="doSubmit('1')">
						&nbsp;&nbsp;&nbsp;
						<input type="button" class="buttonClass" value="提  交" onclick="doSubmit('2')">
						&nbsp;&nbsp;&nbsp;
						<input type="reset" class="buttonClass" value="重  置">
						&nbsp;&nbsp;&nbsp;
						<input type="button" onclick="parent.Dialog.close();"
							class="buttonClass" value="关 闭">
					</td>
				</tr>


			</table>
		</s:form>
	</body>
</html>

