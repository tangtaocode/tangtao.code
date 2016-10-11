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
				alert($("#orgCode").val()+"==="+$("#key").val());
				$.post("/civilApply/saveOrg.YS",{method:"toEdit",orgCode:$("#orgCode").val(),key:$("#key").val()},function(data){
					if(data.message=="0"){
						showInfo("未找到相应的记录！");
					}else{
						showPage("民生创新社会建设-评估机构修改","/civilApply/saveOrg.YS?method=todo&orgCode="+$("#orgCode").val()+"&key="+$("#key").val() ,1000 , 600);
					}
				});
			});
			
			$("#addBotton").bind("click",function(){
				showPage("民生创新社会建设-评估机构申请","/civilApply/saveOrg.YS?method=add" ,1000 , 600);
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
		<s:form action="/civilApply/saveOrg.YS" method="post"
			cssClass="organdspec" id="queryFormId">
			<s:hidden name="method" value="todo"></s:hidden>
			<table width="96%" border="0" align="center" cellpadding="0"
				class="table_leftop" cellspacing="0">
				<tr>
					<td class="title" colspan="2" style="height: 30px;">
						评估机构申请及信息修改
					</td>
				</tr>
				<tr>
					<td class="td_left" colspan="2">
						1、未申报的机构点击申报按钮进行申报。<br/>
						2、已申报的机构输入组织机构代码和密码进行修改或者查看
					</td>
				</tr>
				<tr>
					<td class="title_center">
						组织机构代码：
					</td>
					<td class="td_left">
						<s:textfield name="orgCode" id="orgCode" verify="身份证号码|NotNull"
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
						<input type="button" class="buttonClass" value="申 请"
							id="addBotton">&nbsp;&nbsp;&nbsp;
						<input type="button" class="buttonClass" value="修  改"
							id="editBotton">
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
