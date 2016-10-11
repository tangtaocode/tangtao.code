<%@ page language="java" pageEncoding="GBK"%>
<%@ include file="../share/taglib.jsp"%>
<html>
<head>
</head>
<body>
          <table width="100%" align="center" border="0" cellspacing="0" cellpadding="0" class="table_leftop">
  			<tr>
  				<td  class="title_center">姓名：</td>
      			<td class="td_left" ><s:property value="#session.loginUser.personUser.true_name"/> &nbsp;</td>
      			<td  class="title_center">性别：</td>
      			<td class="td_left" ><s:property value="#session.loginUser.personUser.sex"/>&nbsp;</td>
  			</tr>
  			<tr>
  				<td  class="title_center">出生日期：</td>
      			<td class="td_left" ><s:property value="#session.loginUser.personUser.birth_date"/>&nbsp;</td>
      			<td  class="title_center">民族：</td>
      			<td class="td_left" ><s:property value="#session.loginUser.personUser.nation"/>&nbsp;</td>
  			</tr>
  			<tr >
  			<td  class="title_center">证件类型：</td>
      			<td class="td_left" ><s:property value="#session.loginUser.cardtype"/>&nbsp;</td>
      			<td  class="title_center">证件号码：</td>
      			<td class="td_left" ><s:property value="#session.loginUser.cardid"/>&nbsp;</td>
  				
  			</tr>
  			<tr >
  				<td  class="title_center">联系电话：</td>
      			<td class="td_left"><s:property value="#session.loginUser.personUser.contact_tel"/>&nbsp;</td>
      			<td  class="title_center">联系手机：</td>
      			<td class="td_left"><s:property value="#session.loginUser.personUser.mobile"/>&nbsp;</td>
      			
  			</tr>
  			<tr >
  				<td  class="title_center">学历：</td>
      			<td class="td_left"><s:property value="#session.loginUser.personUser.schoolage"/>&nbsp;</td>
  				<td  class="title_center">工作单位：</td>
      			<td class="td_left"><s:property value="#session.loginUser.personUser.work_company"/>&nbsp;</td>
  			</tr>
  			<tr >
  				<td  class="title_center">开户银行：</td>
      			<td class="td_left" colspan="3"><s:property value="#session.loginUser.personUser.bankname"/>&nbsp;</td>
  			</tr>
  			<tr >
  				<td  class="title_center">账户名称：</td>
      			<td class="td_left"><s:property value="#session.loginUser.personUser.accountname"/>&nbsp;</td>
      			<td  class="title_center">银行帐号：</td>
      			<td class="td_left"><s:property value="#session.loginUser.personUser.banknum"/>&nbsp;</td>
  			</tr>
  			
  			<tr>
  				<td  class="title_center">籍贯：</td>
      			<td class="td_left" colspan="3"><s:property value="#session.loginUser.personUser.home_add"/>&nbsp;</td>
  			</tr>
  			<tr>
  				<td  class="title_center">住址：</td>
      			<td class="td_left" colspan="3"><s:property value="#session.loginUser.personUser.native_add"/>&nbsp;</td>
  			</tr>
		  </table>
</body>
</html>
