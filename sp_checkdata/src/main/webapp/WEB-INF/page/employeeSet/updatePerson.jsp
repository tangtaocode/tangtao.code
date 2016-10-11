<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>用户基本信息</title>
 <script type="text/javascript" src="../../js/jquery-1.8.1.min.js"></script>
 <!-- <link rel="stylesheet" href="../../css/employeeSet.css" type="text/css" /> -->
 <link href="/css/mstyle.css" rel="stylesheet" type="text/css" />
 <link />
</head>
<script type="text/javascript">
$("#tijiao").click(function(){
	$("#personform").submit();
});

</script> 
<body>
      <div class="jbxx"> 
	      <div class="grsz_title"><span>&nbsp;&nbsp;基本信息</span><a href="javascript:void(0);" onclick="" id="tijiao" class="grsz_title_a">保存</a></div>
	    <div class="jbxx_text">
          <form:form action="/person/updatePerson" method="post" modelAttribute="person" id="personform" enctype="multipart/form-data">
			<form:hidden path="UID"/>
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="jbxx_bt">
            <tr>
              <td><span></span>基本信息</td>
            </tr>
          </table>
	      <table width="100%" border="0" cellpadding="0" cellspacing="0" class="jbxx_table">
            <tr>
              <td class="jbxx_td01" width="10%">人员名称：</td>
              <td width="15%"><form:input path="name"/></td>
              <td class="jbxx_td01" width="10%">登录名：</td>
              <td width="15%"><form:input path="loginName" disabled="true"/></td>
              <td class="jbxx_td01" width="10%">性别：</td>
              <td width="15%"><form:select path="sex">
				<form:option value="0">男</form:option>
				<form:option value="1">女</form:option>
				</form:select>
              </td>
              <td class="jbxx_td01" width="10%">政治面貌：</td>
              <td width="15%">
              <form:select path="policitalStatus">
			<form:option value=""></form:option>
			<form:option value="1">党员</form:option>
			<form:option value="2">团员</form:option>
			<form:option value="3">群众</form:option>
				</form:select>
              </td>
            </tr>
            <tr>
              <td class="jbxx_td01" width="10%">人员职务：</td>
              <td width="15%"><form:input path="duty"/></td>
              <td class="jbxx_td01" width="10%">人员职级：</td>
              <td  width="15%"><form:input path="dutyLevelName"/></td>
              <td class="jbxx_td01" width="10%">是否在编：</td>
              <td  width="15%">
				<form:select path="official">
				<form:option value="1">是</form:option>
				<form:option value="0">否</form:option>
				</form:select>
				</td>
              <td class="jbxx_td01" width="10%">编制类型：</td>
              <td  width="15%"> 
              <form:select path="officialType">
			<form:option value=""></form:option>
			<form:option value="个人编">个人编</form:option>
			<form:option value="雇员编">雇员编</form:option>
			<form:option value="事业编">事业编</form:option>
			<form:option value="其他">其他</form:option>
			</form:select>
             </td>
            </tr>
          </table>
	      <br />
	      <table width="100%" border="0" cellpadding="0" cellspacing="0" class="jbxx_bt">
            <tr>
              <td><span></span>办公信息</td>
            </tr>
          </table>
	      <table width="100%" border="0" cellpadding="0" cellspacing="0" class="jbxx_table">
            <tr>
			<td class="jbxx_td01" width="10%">办公室：</td>
			<td  width="15%"><form:input path="officeAddress"/></td>
			<td class="jbxx_td01" width="10%">办公电话：</td>
			<td  width="15%"><form:input path="officePhone"/></td>
			<td class="jbxx_td01" width="10%">办公传真：</td>
			<td  width="15%"><form:input path="officeFax"/></td>
			<td class="jbxx_td01" width="10%">电子邮件：</td>
			<td  width="15%"><form:input path="email"/></td>
			</tr>
			<tr>
			<td class="jbxx_td01" width="10%">CA认证码：</td>
			<td colspan="7" width="90%"><form:input path="caID"/></td>
			</tr>
          </table>
	      <br />
	      <table width="100%" border="0" cellpadding="0" cellspacing="0" class="jbxx_bt">
            <tr>
              <td><span></span>个人信息</td>
            </tr>
          </table>
	      <table width="100%" border="0" cellpadding="0" cellspacing="0" class="jbxx_table">
<tr>
	<td class="jbxx_td01" width="10%">出生日期：</td>
	<td width="15%"><form:input path="birthday"/></td>
	<td class="jbxx_td01" width="10%">婚姻状况：</td>
	<td width="15%">
	<form:select path="maritalStatus">
		<form:option value="0">保密</form:option>
		<form:option value="1">未婚</form:option>
		<form:option value="2">已婚</form:option>
		</form:select>
	</td>
	<td class="jbxx_td01" width="10%">学历学位：</td>
	<td width="15%"><form:input path="education"/></td>
	<td class="jbxx_td01" width="10%">所学专业：</td>
	<td width="15%"><form:input path="professional"/></td>
</tr>
<tr>
	<td class="jbxx_td01" width="10%">居住城市：</td>
	<td width="15%"><form:input path="city"/></td>
	<td class="jbxx_td01" width="10%">居住国家：</td>
	<td width="15%"><form:input path="country"/></td>
	<td class="jbxx_td01" width="10%">家庭电话：</td>
	<td width="15%"><form:input path="homePhone"/></td>
	<td class="jbxx_td01" width="10%">证件类型：</td>
	<td width="15%">
	<form:select path="idType">
	<form:option value="0">身份证</form:option>
	<form:option value="1">护照</form:option>
	<form:option value="2">军官证</form:option>
	<form:option value="3">其他</form:option>
	</form:select>
	</td>
</tr>
<tr>
	<td class="jbxx_td01" width="10%">证件号码：</td>
	<td colspan="3" width="15%"><form:input path="idNum"/></td>
	<td class="jbxx_td01" width="10%">移动号码：</td>
	<td width="15%"><form:input path="mobile"/></td>
	<td class="jbxx_td01" width="10%">人员籍贯：</td>
	<td colspan="3" width="15%"><form:input path="province" /></td>
</tr>
<tr>
<td class="jbxx_td01" width="10%">家庭住址：</td>
<td colspan="7" width="90%"><form:textarea path="homeAddress" /></td>
</tr>
<tr>
<td class="jbxx_td01" width="10%">人员描述：</td>
<td colspan="7" width="90%"><form:textarea  path="description" /></td>
</tr>
          </table>
          </form:form>
	    </div>
    </div>
</body>
</html>