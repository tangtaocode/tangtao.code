<%@ page language="java"  pageEncoding="GBK"%>
<%@ include file="../share/taglib.jsp"%>
<html>
<head>
</head>
<body>
          <table width="100%" align="center" border="0" cellspacing="0" cellpadding="0" class="table_leftop">
  			<tr>
  				<td  class="title_center" width="15%">��λ���ƣ�</td>
      			<td class="td_left" width="35%"> <s:property value="#session.loginUser.companyUser.ename"/>&nbsp;</td>
      			<td  class="title_center" width="15%">λ���ʣ�</td>
      			<td class="td_left" width="35%"><s:property value="#session.loginUser.companyUser.kind"/>&nbsp;</td>
  			</tr>
  			<tr>
  				<td  class="title_center">����ʱ�䣺</td>
      			<td class="td_left"><s:property value="#session.loginUser.companyUser.regdate"/>&nbsp;</td>
      			<td  class="title_center">��֯�������룺</td>
      			<td class="td_left"><s:property value="#session.loginUser.companyUser.orgcode"/>&nbsp;</td>
  			</tr>
  			<tr >
  				<td  class="title_center">���������ˣ�</td>
      			<td class="td_left"><s:property value="#session.loginUser.companyUser.lawperson"/>&nbsp;</td>
      			<td  class="title_center">�������֤�ţ�</td>
      			<td class="td_left"><s:property value="#session.loginUser.companyUser.lowcardid"/>&nbsp;</td>
  			</tr>
  			<tr >
  			<td  class="title_center">���˵绰��</td>
      			<td class="td_left"><s:property value="#session.loginUser.companyUser.lawphone"/>&nbsp;</td>
      			<td  class="title_center">���ܲ��ţ�</td>
      			<td class="td_left" ><s:property value="#session.loginUser.companyUser.zgbm"/>&nbsp;</td>
      			
  			</tr>
  			<tr >
  				<td  class="title_center">��ϵ�ˣ�</td>
      			<td class="td_left"><s:property value="#session.loginUser.companyUser.truename"/>&nbsp;</td>
      			<td  class="title_center">��ϵ�����֤�ţ�</td>
      			<td class="td_left"><s:property value="#session.loginUser.companyUser.personcardid"/>&nbsp;</td>
  			</tr>
  			<tr >
  				<td  class="title_center">��ϵ�˵绰��</td>
      			<td class="td_left"><s:property value="#session.loginUser.companyUser.contactphone"/>&nbsp;</td>
      			<td  class="title_center">��ϵ�����䣺</td>
      			<td class="td_left" ><s:property value="#session.loginUser.companyUser.email"/>&nbsp;</td>
  			</tr>
  			<tr >
  				<td  class="title_center">��ϵ���ֻ���</td>
      			<td class="td_left" ><s:property value="#session.loginUser.companyUser.contactmobile"/>&nbsp;</td>
  				<td  class="title_center">�˻����ƣ�</td>
      			<td  class="td_left"><s:property value="#session.loginUser.companyUser.accountname"/>&nbsp;</td>
  			</tr>
  			<tr >
  				<td  class="title_center">�������У�</td>
      			<td class="td_left"><s:property value="#session.loginUser.companyUser.bankname"/>&nbsp;</td>
  				<td  class="title_center">�����˺ţ�</td>
      			<td  class="td_left"> <s:property value="#session.loginUser.companyUser.banknum"/>&nbsp;</td>
  			</tr>
  			<tr>
  				<td  class="title_center">ע�����ڵأ�</td>
      			<td class="td_left" colspan="3"><s:property value="#session.loginUser.companyUser.openadd"/>&nbsp;</td>
  			</tr>
  			<tr>
  				<td  class="title_center">ע���ַ��</td>
      			<td class="td_left" colspan="3"><s:property value="#session.loginUser.companyUser.address"/>&nbsp;</td>
  			</tr>
  		
</table>
</body>
</html>
