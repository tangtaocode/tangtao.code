<%@ page language="java"  pageEncoding="GBK"%>
<%@ include file="../share/taglib.jsp"%>
<html>
<head>
</head>
<body>
          <table width="100%" align="center" border="0" cellspacing="0" cellpadding="0" class="table_leftop">
  			<tr>
  				<td  class="title_center" width="15%">单位名称：</td>
      			<td class="td_left" width="35%"> <s:property value="#session.loginUser.companyUser.ename"/>&nbsp;</td>
      			<td  class="title_center" width="15%">位性质：</td>
      			<td class="td_left" width="35%"><s:property value="#session.loginUser.companyUser.kind"/>&nbsp;</td>
  			</tr>
  			<tr>
  				<td  class="title_center">成立时间：</td>
      			<td class="td_left"><s:property value="#session.loginUser.companyUser.regdate"/>&nbsp;</td>
      			<td  class="title_center">组织机构代码：</td>
      			<td class="td_left"><s:property value="#session.loginUser.companyUser.orgcode"/>&nbsp;</td>
  			</tr>
  			<tr >
  				<td  class="title_center">法定代表人：</td>
      			<td class="td_left"><s:property value="#session.loginUser.companyUser.lawperson"/>&nbsp;</td>
      			<td  class="title_center">法人身份证号：</td>
      			<td class="td_left"><s:property value="#session.loginUser.companyUser.lowcardid"/>&nbsp;</td>
  			</tr>
  			<tr >
  			<td  class="title_center">法人电话：</td>
      			<td class="td_left"><s:property value="#session.loginUser.companyUser.lawphone"/>&nbsp;</td>
      			<td  class="title_center">主管部门：</td>
      			<td class="td_left" ><s:property value="#session.loginUser.companyUser.zgbm"/>&nbsp;</td>
      			
  			</tr>
  			<tr >
  				<td  class="title_center">联系人：</td>
      			<td class="td_left"><s:property value="#session.loginUser.companyUser.truename"/>&nbsp;</td>
      			<td  class="title_center">联系人身份证号：</td>
      			<td class="td_left"><s:property value="#session.loginUser.companyUser.personcardid"/>&nbsp;</td>
  			</tr>
  			<tr >
  				<td  class="title_center">联系人电话：</td>
      			<td class="td_left"><s:property value="#session.loginUser.companyUser.contactphone"/>&nbsp;</td>
      			<td  class="title_center">联系人邮箱：</td>
      			<td class="td_left" ><s:property value="#session.loginUser.companyUser.email"/>&nbsp;</td>
  			</tr>
  			<tr >
  				<td  class="title_center">联系人手机：</td>
      			<td class="td_left" ><s:property value="#session.loginUser.companyUser.contactmobile"/>&nbsp;</td>
  				<td  class="title_center">账户名称：</td>
      			<td  class="td_left"><s:property value="#session.loginUser.companyUser.accountname"/>&nbsp;</td>
  			</tr>
  			<tr >
  				<td  class="title_center">开户银行：</td>
      			<td class="td_left"><s:property value="#session.loginUser.companyUser.bankname"/>&nbsp;</td>
  				<td  class="title_center">银行账号：</td>
      			<td  class="td_left"> <s:property value="#session.loginUser.companyUser.banknum"/>&nbsp;</td>
  			</tr>
  			<tr>
  				<td  class="title_center">注册所在地：</td>
      			<td class="td_left" colspan="3"><s:property value="#session.loginUser.companyUser.openadd"/>&nbsp;</td>
  			</tr>
  			<tr>
  				<td  class="title_center">注册地址：</td>
      			<td class="td_left" colspan="3"><s:property value="#session.loginUser.companyUser.address"/>&nbsp;</td>
  			</tr>
  		
</table>
</body>
</html>
