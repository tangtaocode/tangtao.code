<%@ page language="java" pageEncoding="GBK"%>
<%@ include file="../share/taglib.jsp"%>
<html>
<head>
</head>
<body>
          <table width="100%" align="center" border="0" cellspacing="0" cellpadding="0" class="table_leftop">
  			<tr>
  				<td  class="title_center">������</td>
      			<td class="td_left" ><s:property value="#session.loginUser.personUser.true_name"/> &nbsp;</td>
      			<td  class="title_center">�Ա�</td>
      			<td class="td_left" ><s:property value="#session.loginUser.personUser.sex"/>&nbsp;</td>
  			</tr>
  			<tr>
  				<td  class="title_center">�������ڣ�</td>
      			<td class="td_left" ><s:property value="#session.loginUser.personUser.birth_date"/>&nbsp;</td>
      			<td  class="title_center">���壺</td>
      			<td class="td_left" ><s:property value="#session.loginUser.personUser.nation"/>&nbsp;</td>
  			</tr>
  			<tr >
  			<td  class="title_center">֤�����ͣ�</td>
      			<td class="td_left" ><s:property value="#session.loginUser.cardtype"/>&nbsp;</td>
      			<td  class="title_center">֤�����룺</td>
      			<td class="td_left" ><s:property value="#session.loginUser.cardid"/>&nbsp;</td>
  				
  			</tr>
  			<tr >
  				<td  class="title_center">��ϵ�绰��</td>
      			<td class="td_left"><s:property value="#session.loginUser.personUser.contact_tel"/>&nbsp;</td>
      			<td  class="title_center">��ϵ�ֻ���</td>
      			<td class="td_left"><s:property value="#session.loginUser.personUser.mobile"/>&nbsp;</td>
      			
  			</tr>
  			<tr >
  				<td  class="title_center">ѧ����</td>
      			<td class="td_left"><s:property value="#session.loginUser.personUser.schoolage"/>&nbsp;</td>
  				<td  class="title_center">������λ��</td>
      			<td class="td_left"><s:property value="#session.loginUser.personUser.work_company"/>&nbsp;</td>
  			</tr>
  			<tr >
  				<td  class="title_center">�������У�</td>
      			<td class="td_left" colspan="3"><s:property value="#session.loginUser.personUser.bankname"/>&nbsp;</td>
  			</tr>
  			<tr >
  				<td  class="title_center">�˻����ƣ�</td>
      			<td class="td_left"><s:property value="#session.loginUser.personUser.accountname"/>&nbsp;</td>
      			<td  class="title_center">�����ʺţ�</td>
      			<td class="td_left"><s:property value="#session.loginUser.personUser.banknum"/>&nbsp;</td>
  			</tr>
  			
  			<tr>
  				<td  class="title_center">���᣺</td>
      			<td class="td_left" colspan="3"><s:property value="#session.loginUser.personUser.home_add"/>&nbsp;</td>
  			</tr>
  			<tr>
  				<td  class="title_center">סַ��</td>
      			<td class="td_left" colspan="3"><s:property value="#session.loginUser.personUser.native_add"/>&nbsp;</td>
  			</tr>
		  </table>
</body>
</html>
