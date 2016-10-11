<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../../share/taglib.jsp"%>
<html>
<head>

<title>深圳市龙岗区住房与建设局社会建设和民生创新专项资金评估专家申请</title>
<meta http-equiv='Pragma' content='no-cache' />
<meta http-equiv='Cache-Control' content='no-cache' />
<meta http-equiv='Expires' content='0' />
<style type="text/css">
body{ margin: 0;
	  padding: 0; 
	  }
.setpTitle{
	text-align:center;
	font-size:20px;
}
</style>
<link href="/css/appGuide.css" rel="stylesheet" type="text/css"/>
 <link href="/css/tabIdCard.css" rel="stylesheet" type="text/css"/>
 <script src="/js/Scripts/jquery.min.js" type="text/javascript"></script>
<script src="/js/jquery.idTabs.min.js" type="text/javascript" ></script>
<script type="text/javascript" src="/js/zDialog/risenetDialog.js"></script> 
<script charset="utf-8" src="/js/validation.js"></script>
<script type="text/javascript">
$(document).ready(function(){
		$("a.tab").click(function () {
			var content_show = $(this).attr("title");
			if(content_show=="content_2"){
				if($("#organizationtype").val()=="") {
					Dialog.alert("请选择先机构类型");
					return false;
				}else{
					tabSwich(this);
				}
			}else{
				tabSwich(this);
			}
		});
	  });
	 function tabSwich(obj){
	 	$(".active").removeClass("active");
		$(obj).addClass("active");
		var content_show = $(obj).attr("title");
		$(".content").slideUp();
		$("#"+content_show).slideDown();
	 }
	 function doSubmit(type){
	 	if(!validations("content_1"))return false;
	 	$("#status").attr("value",type);
	 	$("#queryFormId").submit();
	 }
	 function loadUpFilePage(){
	 	$.post("/civilApply/findOrgFile.YS",{orgType:$("#organizationtype").val(),guid:$("#guid").val()},function(page){
	 		$("#fileUploadPage").html(page);
	 	});
	 }
</script>
</head>
<body>
<s:form action="/civilApply/saveOrg.YS" method="post" id="queryFormId">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_leftop">
<tr><td class="top4"> </td> </tr>
<tr><td>
<div id="tabbed_box_1" class="tabbed_box">

    <div class="tabbed_area" id="contentDiv">
        <ul class="tabs" id="cardTagsId">
            <li><a href="#" title="content_1" class="tab active">基础信息</a></li>
            <li><a href="#" title="content_2" class="tab">附件上传</a></li>
        </ul>
         <div id="content_1" class="content" style="overflow:scroll;height:430px;scrollbar-face-color:#E0F2FF;">
         <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="table_leftop">
			<s:hidden name="organization.guid" id="guid"></s:hidden>
			<s:hidden name="organization.status" id="status"></s:hidden>
			<s:hidden name="organization.createdate" id="createdate"></s:hidden>
			<TR>
				<TD width="20%" class="title_center" nowrap="nowrap">机构代码：</TD>
			<TD width="30%" class="td_left"   >
				<s:textfield  name="organization.organizationcode" id="organizationcode" size="30" cssClass="zc_input02" verify="机构代码|NotNull" maxlength="11"></s:textfield><span style="color:red">*</span>
			</TD>
			<TD width="20%" class="title_center" nowrap="nowrap">机构类型：</TD>
			<TD width="30%" class="td_left"  >
				<s:select name="organization.organizationtype" onchange="loadUpFilePage();" verify="机构类型|NotNull" id="organizationtype" list="#{'':'---请选择---','1':'全日制大学或学院','2':'公办、民办研究机构','3':'评估机构'}"></s:select>
				<span style="color:red">*</span>
			</TD>
		</TR>
		<TR>
				<TD width="20%" class="title_center" nowrap="nowrap">密码：</TD>
			<TD width="80%" colspan="3" class="td_left"   >
				<s:textfield  name="organization.password" id="password" size="80" cssClass="zc_input02" verify="机构代码|NotNull" maxlength="20"></s:textfield><span style="color:red">*</span>
			</TD>
		</TR>
		<TR>
			<TD width="20%" class="title_center" >单位名称：</TD>
			<TD width="80%" colspan="3" class="td_left">
			<s:textfield  name="organization.name" id="name" size="80" cssClass="zc_input02" verify="单位名称|NotNull" maxlength="200"></s:textfield><span style="color:red">*</span> </TD>
		</TR>
		<TR>
			<TD width="20%" class="title_center" nowrap="nowrap">单位地址：</TD>
			<TD width="80%" colspan="3" class="td_left">
				<s:textfield  name="organization.address" id="address" size="80" cssClass="zc_input02" verify="单位地址|NotNull" maxlength="500"></s:textfield><span style="color:red">*</span>
			</TD>
		</TR>
		<TR>
			<TD width="20%" class="title_center" nowrap="nowrap">开户银行：</TD>
			<TD width="30%" class="td_left"   >
				<s:textfield  name="organization.bankname" id="bankname" size="30" cssClass="zc_input02" verify="开户银行|NotNull" maxlength="200"></s:textfield><span style="color:red">*</span>
			</TD>
			<TD width="20%" class="title_center" nowrap="nowrap">户名：</TD>
			<TD width="30%" class="td_left"   >
				<s:textfield  name="organization.accountname" id="accountname" size="30" cssClass="zc_input02" verify="户名|NotNull" maxlength="200"></s:textfield><span style="color:red">*</span>
			</TD>
		</TR>
		<TR>
				<TD width="20%" class="title_center" nowrap="nowrap">开户账号：</TD>
			<TD width="30%" class="td_left"   >
				<s:textfield  name="organization.bankcode" id="bankcode" size="30"  cssClass="zc_input02" verify="开户账号|NotNull" maxlength="30"></s:textfield><span style="color:red">*</span>
			</TD>
			<TD width="20%" class="title_center" nowrap="nowrap">负责人：</TD>
			<TD width="30%" class="td_left"  >
				<s:textfield  name="organization.head" id="head" size="30" cssClass="zc_input02" verify="负责人|NotNull" maxlength="20"></s:textfield><span style="color:red">*</span>
			</TD>
		</TR>
			<TR>
				<TD width="20%" class="title_center" nowrap="nowrap">办公电话：</TD>
			<TD width="30%" class="td_left"   >
				<s:textfield  name="organization.telephone" id="telephone" size="30"  cssClass="zc_input02" verify="办公电话|NotNull|telephone" maxlength="20"></s:textfield><span style="color:red">*</span>
			</TD>
			<TD width="20%" class="title_center" nowrap="nowrap">手机号码：</TD>
			<TD width="30%" class="td_left"  >
				<s:textfield  name="organization.mobilephone" id="mobilephone" size="30" cssClass="zc_input02" verify="手机号码|NotNull|phone" onkeyup="restrictNumber(this,'1',11)" maxlength="11"></s:textfield><span style="color:red">*</span>
			</TD>
		</TR>
		<TR>
				<TD width="20%" class="title_center" nowrap="nowrap">电子邮箱：</TD>
			<TD width="30%" class="td_left"   >
				<s:textfield  name="organization.mail" id="mail" size="30" cssClass="zc_input02" verify="电子邮箱|NotNull|email" maxlength="50"></s:textfield><span style="color:red">*</span>
			</TD>
			<TD width="20%" class="title_center" nowrap="nowrap">邮编：</TD>
			<TD width="30%" class="td_left"  >
				<s:textfield  name="organization.zipcode" id="zipcode" size="30" cssClass="zc_input02" verify="邮编|NotNull" maxlength="20"></s:textfield><span style="color:red">*</span>
			</TD>
		</TR>
		<TR>
			<TD width="20%" class="title_center" nowrap="nowrap">单位简介：</TD>
			<TD width="80%" colspan="3" class="td_left">
			<s:textarea  id="companydes" name="organization.companydes" cols="80" rows="10" cssClass="zc_input02" verify="单位简介|NotNull" ></s:textarea><span style="color:red">*</span>
		</TR>
		<TR>
			<TD width="20%" class="title_center" nowrap="nowrap">参与项目：</TD>
			<TD width="80%" colspan="3" class="td_left">
			<s:textarea  id="oldproject" name="organization.oldproject" cols="80" rows="10" cssClass="zc_input02" verify="参与项目|NotNull" ></s:textarea><span style="color:red">*</span>
		</TR>
		</table>
        </div>
        
         <div id="content_2" class="content" style="overflow:scroll;height:430px;scrollbar-face-color:#E0F2FF;">
         <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="table_leftop">
		 <tr>
        <td class="td_left"><h5 class="setpTitle">附件上传</h5></td>
      </tr>
		<tr><td class="td_left" id="fileUploadPage">
      </td></tr>
     
		</table>
        </div>
     </div>
 </div>   

</td> </tr>
  <tr><td style="text-align:center"> 
       <input type="button" onclick="doSubmit('1')" class="buttonClass" value=" 暂  存 ">&nbsp;&nbsp;
       <input type="button" onclick="doSubmit('2')" class="buttonClass" value=" 提  交 ">&nbsp;&nbsp;
       <input type="button" onclick="closeWin()" class="buttonClass" value=" 关  闭 ">&nbsp;&nbsp;
      </td></tr>
</table>
</s:form>
</body>
</html>
