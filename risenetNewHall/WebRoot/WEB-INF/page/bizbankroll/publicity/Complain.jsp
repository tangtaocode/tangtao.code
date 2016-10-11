<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../share/taglib.jsp"%>
<html>
	<head>
		<title>投诉</title>
		<link href="/css/pageStyle.css" rel="stylesheet" type="text/css" />
		<link href="/css/appGuide.css" rel="stylesheet" type="text/css" />
		<script
			src="/js/Scripts/jquery.min.js"></script>
		<script src="/js/zDialog/risenetDialog.js"></script>
		<script src="/js/validation.js"></script>
		<script type="text/javascript">
		function doSubmit(){
			if(!validations("queryFormId")) return false;
			$("#queryFormId").submit();
		}
		</script>
	</head>
	<body>
		<s:form action="/bizPublicity/saveComplain.YS" method="post"
			id="queryFormId">
			<s:hidden name="projectguid" id="projectguid"></s:hidden>
			<s:hidden name="complainBean.complaintype" value="0"
				id="complaintype"></s:hidden>
			
						<table width="96%" border="0" align="center" cellpadding="0" class="table_leftop"
							cellspacing="0">
							<tr>
								<td class="td_left" style="text-align: left;" colspan="4">
									<strong>投诉注意事项：</strong>
									<br>
									&nbsp;&nbsp;&nbsp; 1、欢迎您对产业转型升级专项资金项目提出意见和质疑；凡与此无关的谩骂、发泄等将不予答复。
									<br>
									&nbsp;&nbsp;&nbsp; 2、个人信息必须真实有效，我们将予以严格保密；我们不处理匿名投诉。
									<br>
									&nbsp;&nbsp;&nbsp; 3、带*为必填项，如果输入手机号码，会收到回复处理的短信提醒。
								</td>
							</tr>
						<tr>
											<td class="title_center" >
												姓 名：
											</td>
											<td class="td_left" >
												<s:textfield name="complainBean.username" id="username"
													verify="姓 名|NotNull" cssClass="zc_input02" size="25"
													maxLength="4"></s:textfield>
												<span style="color:red">*</span>
											</td>
											<td class="title_center" >
												电 话：
											</td>
											<td class="td_left" >
												<s:textfield name="complainBean.telephone" id="telephone"
													verify="电话|Null|telephone" cssClass="zc_input02"
													size="25"></s:textfield>
											</td>
										</tr>
										<tr>
											<td class="title_center" >
												手 机：
											</td>
											<td class="td_left" >
												<s:textfield name="complainBean.mobile" id="mobile" onkeyup="restrictNumber(this,'1',11)"
													verify="手机|NotNull|phone" cssClass="zc_input02"
													size="25" maxLength="11"></s:textfield>
												<span style="color:red">*</span>
											</td>
											<td class="title_center" >
												邮 箱：
											</td>
											<td class="td_left" >
												<s:textfield name="complainBean.email" id="email"
													verify="邮箱|Null|email" cssClass="zc_input02" size="25"></s:textfield>
											</td>
										</tr>

										<tr>
											<td class="title_center" >
												标 题：
											</td>
											<td colspan="3" class="td_left" >
												<s:textfield name="complainBean.title" id="title"
													verify="投诉标题|NotNull" cssClass="zc_input02" size="25"
													maxLength="1000"></s:textfield>
												<span style="color:red">*</span>
											</td>
										</tr>
										<tr>
											<td class="title_center" >
												内 容：
											</td>
											<td colspan="3" class="td_left" >
												<s:textarea name="complainBean.content" id="content"
													verify="投诉内容|NotNull" cssClass="zc_input02" cols="60"
													rows="5"></s:textarea>
												<span style="color:red">*</span>
											</td>
										</tr>
								<tr>
								<td height="60" style="text-align:center" class="td_left" colspan="4">
									<input type="button" class="submitBtn" onclick="doSubmit()"
										value="">
									&nbsp;&nbsp;&nbsp;
									<input type="reset" class="resetBtn" value="">
									&nbsp;&nbsp;&nbsp;
									<input type="button" onclick="parent.Dialog.close();"
										class="closeBtn" value="">
								</td>
							</tr>	
							
							
			</table>
		</s:form>
	</body>
</html>
