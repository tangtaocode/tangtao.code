<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../share/taglib.jsp"%>
<html>
	<head>
		<title>评估专家</title>
		<link href="/css/appGuide.css" rel="stylesheet" type="text/css" />
		<script
			src="/js/Scripts/jquery.min.js"></script>
		<script src="/js/zDialog/risenetDialog.js"></script>
		<script src="/js/validation.js"></script>
		<script type="text/javascript">
		 $(document).ready(function(){
			$("#editBotton").bind("click",function(){
				if(!validations("queryFormId")) return false;
				$.post("/civilApply/saveSpec.YS",{method:"todo",idCard:$("#idCard").val(),key:$("#key").val()},function(data){
					if(data.message=="0"){
						showInfo("未找到相应的记录！");
					}else{
						showPage("民生创新社会建设-评估专家","/civilApply/saveSpec.YS?method=todo&idCard="+$("#idCard").val()+"&key="+$("#key").val() ,1000 , 600);
					}
				});
			});
	 	});
		</script>
		<style type="text/css">
body {
	margin: 0;
	padding: 0;
}
</style>
	</head>
	<body>
		<s:form action="/civilApply/saveSpec.YS" method="post"
			cssClass="organdspec" id="queryFormId">
			<s:hidden name="method" value="todo"></s:hidden>
			<table width="96%" border="0" align="center" cellpadding="0"
				class="table_leftop" cellspacing="0">
				<tr>
					<td class="title" colspan="2" style="height: 30px;">
						评估专家信息修改
					</td>
				</tr>
				<tr>
					<td class="td_left" colspan="2">
						说明： 默认密码为 “123456” ，为了确保您的信息安全第一次修改时信息请修改密码。
					</td>
				</tr>
				<tr>
					<td class="title_center">
						身份证号码：
					</td>
					<td class="td_left">
						<s:textfield name="idCard" id="idCard" verify="身份证号码|NotNull"
							cssClass="zc_input02" size="25" value="440111199111266022"
							maxLength="30"></s:textfield>
						<span style="color: red">*</span>
					</td>
				</tr>
				<tr>
					<td class="title_center">
						密 码：
					</td>
					<td class="td_left">
						<s:password name="key" id="key" verify="密 码|NotNull"
							cssClass="zc_input02" size="25" maxLength="20"></s:password>
						<span style="color: red">*</span>
					</td>
				</tr>
				<tr>
					<td height="60" style="text-align: center" class="td_left"
						colspan="4">
						<input type="button" class="buttonClass" value="修 改"
							id="editBotton">
						&nbsp;&nbsp;&nbsp;
						<input type="reset" class="buttonClass" value="重 置">
						&nbsp;&nbsp;&nbsp;
						<input type="button" onclick="parent.Dialog.close();"
							class="buttonClass" value="关 闭">
					</td>
				</tr>


			</table>
		</s:form>
	</body>
</html>
