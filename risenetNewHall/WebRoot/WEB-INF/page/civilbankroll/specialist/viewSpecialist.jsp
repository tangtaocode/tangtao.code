<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../share/taglib.jsp"%>
<html>
	<head>
		<title>评估专家</title>
		<link href="/css/appGuide.css" rel="stylesheet" type="text/css" />
		<script src="/js/zDialog/risenetDialog.js"></script>
		<style type="text/css">
body {
	margin: 0;
	padding: 0;
}
</style>
	</head>
	<body>
		<s:form action="/civilApply/saveSpec.YS" method="post"
			id="queryFormId">
			<table width="100%" border="0" align="center" cellpadding="0"
				class="table_leftop" cellspacing="0">
				<tr>
					<th class="top5"></th>
				</tr>
				<tr>
					<td>
						<div
							style="overflow: scroll; height: 459px; scrollbar-face-color: #E0F2FF;">
							<table width="96%" border="0" align="center" cellpadding="0"
								class="table_leftop" cellspacing="0">
								<tr>
									<td class="title_center">
										专家姓名：
									</td>
									<td class="td_left">
										<s:property value="civilSpecialist.name" />
									</td>
									<td class="title_center">
										密码:
									</td>
									<td class="td_left">
										******
									</td>
									<td class="title_center">
										性别:
									</td>
									<td class="td_left">
										<s:property value="civilSpecialist.sex" />
									</td>
								</tr>
								<tr>
									<td class="title_center">
										民族：
									</td>
									<td class="td_left">
										<s:property value="civilSpecialist.nation" />
									</td>
									<td class="title_center">
										出生日期:
									</td>
									<td class="td_left">
										<s:property value="civilSpecialist.birthday" />
									</td>
									<td class="title_center">
										政治面貌:
									</td>
									<td class="td_left">
										<s:property value="civilSpecialist.politics" />
									</td>
								</tr>
								<tr>
									<td class="title_center">
										担任职务：
									</td>
									<td class="td_left">
										<s:property value="civilSpecialist.position" />
									</td>
									<td class="title_center">
										在所在领域任职时间:
									</td>
									<td class="td_left">
										<s:property value="civilSpecialist.worktime" />
									</td>
									<td class="title_center">
										文化程度:
									</td>
									<td class="td_left">
										<s:property value="civilSpecialist.education" />
									</td>
								</tr>
								<tr>
									<td class="title_center">
										籍贯：
									</td>
									<td class="td_left" colspan="5">
										<s:property value="civilSpecialist.birthplace" />
									</td>
								</tr>
								<tr>
									<td class="title_center">
										毕业院校及专业：
									</td>
									<td class="td_left" colspan="5">
										<s:property value="civilSpecialist.professional" />
									</td>
								</tr>
								<tr>
									<td class="title_center">
										身份证号码：
									</td>
									<td class="td_left" colspan="6">
										<s:property value="civilSpecialist.cardid" />
									</td>
								</tr>
								<tr>
									<td class="title_center">
										联系电话:
									</td>
									<td class="td_left">
										<s:property value="civilSpecialist.telephone" />
									</td>
									<td class="title_center">
										联系手机:
									</td>
									<td class="td_left">
										<s:property value="civilSpecialist.mobilephone" />
									</td>
									<td class="title_center">
										邮箱：
									</td>
									<td class="td_left">
										<s:property value="civilSpecialist.mail" />
									</td>
								</tr>

								<tr>
									<td class="title_center">
										专业技术资格或职称：
									</td>
									<td class="td_left" colspan="5">
										<s:property value="civilSpecialist.qualifications" />
									</td>
								</tr>
								<tr>
									<td class="title_center">
										简历：
									</td>
									<td class="td_left" colspan="5">
										<s:property value="civilSpecialist.resume" escape="false"/>
									</td>
								</tr>
								<tr>
									<td class="title_center">
										获得荣誉：
									</td>
									<td class="td_left" colspan="5">
										<s:property value="civilSpecialist.honor" escape="false"/>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				<tr>
					<td height="60" style="text-align: center" class="td_left"
						colspan="4">
						<input type="button" onclick="parent.Dialog.close();"
							class="buttonClass" value="关 闭">
					</td>
				</tr>
			</table>
		</s:form>
	</body>
</html>

