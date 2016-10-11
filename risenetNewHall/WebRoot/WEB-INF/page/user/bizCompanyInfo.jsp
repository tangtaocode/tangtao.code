<%@ page language="java"  pageEncoding="GBK"%>
<%@ include file="../share/taglib.jsp"%>
<html>
<head>
</head>
<body>
          <table width="100%" align="center" border="0" cellspacing="0" cellpadding="0" class="table_leftop">
  			<tr>
  				<td  class="title_center" width="15%">单位名称：</td>
      			<td class="td_left" colspan="2"> <s:property value="#session.loginUser.companyUser.ename"/>&nbsp;</td>
      			<td  class="title_center">单位性质：</td>
      			<td class="td_left" colspan="2"><s:property value="#session.loginUser.companyUser.kind"/>&nbsp;</td>
      			
  			</tr>
  			<tr>
  				<td  class="title_center">传真：</td>
      			<td class="td_left" colspan="2"><s:property value="#session.loginUser.companyUser.industry"/>&nbsp;</td>
      			<td  class="title_center">组织机构代码：</td>
      			<td class="td_left" colspan="2"><s:property value="#session.loginUser.companyUser.orgcode"/>&nbsp;</td>
  			</tr>
  			<tr >
  				<td  class="title_center">注册资本：</td>
      			<td class="td_left"><s:property value="#session.loginUser.companyUser.regmoney"/>&nbsp;万元</td>
  				<td  class="title_center">成立时间：</td>
      			<td class="td_left" ><s:property value="#session.loginUser.companyUser.regdate"/>&nbsp;</td>
      			<td  class="title_center">注册号：</td>
      			<td class="td_left" ><s:property value="#session.loginUser.companyUser.regcode"/>&nbsp;</td>
  			</tr>
  			<tr>
  				<td  class="title_center">经营范围：</td>
      			<td class="td_left" colspan="5"><s:property value="#session.loginUser.companyUser.limit"/>&nbsp;</td>
  			</tr>
  			<tr>
  				<td  class="title_center">注册地址：</td>
      			<td class="td_left" colspan="5"><s:property value="#session.loginUser.companyUser.address"/>&nbsp;</td>
  			</tr>
  			
  			<tr>
  				<td  class="title_center">营业地址：</td>
      			<td class="td_left" colspan="5"><s:property value="#session.loginUser.companyUser.openadd"/>&nbsp;</td>
  			</tr>
  			
  			<tr >
  				<td  class="title_center" width="15%">税务登记编号：</td>
      			<td class="td_left"><s:property value="#session.loginUser.companyUser.taxnum"/>&nbsp;</td>
  				<td  class="title_center" width="15%">国税纳税编码：</td>
      			<td class="td_left"><s:property value="#session.loginUser.companyUser.nationaltax"/>&nbsp;</td>
      			<td  class="title_center" width="15%">地税纳税编码：</td>
      			<td class="td_left" ><s:property value="#session.loginUser.companyUser.localtax"/>&nbsp;</td>
  			</tr>
  			
  			<tr >
  				<td  class="title_center">法定代表人：</td>
      			<td class="td_left"><s:property value="#session.loginUser.companyUser.lawperson"/>&nbsp;</td>
  				<td  class="title_center">法人电话：</td>
      			<td class="td_left"><s:property value="#session.loginUser.companyUser.lawphone"/>&nbsp;</td>
      			<td  class="title_center">法人手机：</td>
      			<td class="td_left"><s:property value="#session.loginUser.companyUser.lawperphone"/>&nbsp;</td>
  			</tr>
  			<tr >
      			<td  class="title_center">法人邮箱：</td>
      			<td class="td_left" ><s:property value="#session.loginUser.companyUser.lawperemail"/>&nbsp;</td>
      			<td  class="title_center">法人身份证号：</td>
      			<td class="td_left" colspan="3"><s:property value="#session.loginUser.companyUser.lowcardid"/>&nbsp;</td>
  			</tr>
  			<tr >
  				<td  class="title_center">联系人：</td>
      			<td class="td_left"><s:property value="#session.loginUser.companyUser.truename"/>&nbsp;</td>
  				<td  class="title_center">联系人电话：</td>
      			<td class="td_left"><s:property value="#session.loginUser.companyUser.contactphone"/>&nbsp;</td>
      			<td  class="title_center">联系人手机：</td>
      			<td class="td_left" ><s:property value="#session.loginUser.companyUser.contactmobile"/>&nbsp;</td>
  			</tr>
  			<tr >
      			<td  class="title_center">联系人邮箱：</td>
      			<td class="td_left" ><s:property value="#session.loginUser.companyUser.email"/>&nbsp;</td>
      			<td  class="title_center">联系人身份证号：</td>
      			<td class="td_left" colspan="3"><s:property value="#session.loginUser.companyUser.personcardid"/>&nbsp;</td>
  			</tr>
  			<tr >
  				<td  class="title_center">账户名称：</td>
      			<td  class="td_left" colspan="5"><s:property value="#session.loginUser.companyUser.accountname"/>&nbsp;</td>
  			</tr>
  			<tr >
  				<td  class="title_center">开户银行：</td>
      			<td class="td_left"  colspan="2"><s:property value="#session.loginUser.companyUser.bankname"/>&nbsp;</td>
  				<td  class="title_center">银行账号：</td>
      			<td  class="td_left" colspan="2"> <s:property value="#session.loginUser.companyUser.banknum"/>&nbsp;</td>
  			</tr>
  		
</table>
</body>
</html>
