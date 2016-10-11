<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<body>
<s:form id="editUserDataForm" method="post">
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="BS_list">
<tr> 
	<th colspan="4"><div style="float:left">单位基本信息</div>
	<div style="float:right"><input type="button" onclick="submitEditUser()" class="buttonClass_1" value="保存"></div>
	<s:hidden name="method" value="edit"></s:hidden>
	<s:hidden name="appModel"></s:hidden>
	<s:hidden name="companyUser.guid"></s:hidden>
	<s:hidden name="companyUser.logonguid"></s:hidden>
	<s:hidden name="companyUser.name"></s:hidden>
	<s:hidden name="companyUser.register_date"></s:hidden>
	<s:hidden name="companyUser.regtype"></s:hidden>
	
	</th>
</tr>
           <tr>
  				<td width="18%" style="text-align:right">单位名称：</td>
      			<td colspan="3" width="82%">
      			<s:textfield maxlength="200" verify="单位名称|NotNull"  cssClass="zc_input02" size="60" name="companyUser.ename" id="ename" ></s:textfield>
			<span style="color:red">*</span>
      		</td>
  			</tr>
  			<tr>
  				<td width="18%"  style="text-align:right">单位性质：</td>
      			<td width="32%">
      				<s:select name="companyUser.kind" id="kind" cssClass="zc_input02" list="#{'':'---请选择---','国家机关':'国家机关','事业单位':'事业单位','社会团体':'社会团体','其他机构':'其他机构'}"></s:select>
      			&nbsp;&nbsp;<span style="color:red">* </span>
      			</td>
  				<td width="18%" style="text-align:right">机构代码：</td>
      			<td width="32%"> 
      			<s:textfield maxlength="30" verify="机构代码|NotNull" readonly="true"  cssClass="zc_input02" size="20" name="companyUser.orgcode" id="orgcode" ></s:textfield>
      			<span style="color:red">*</span>
      			</td>
  			</tr>
  			<tr>
  				
      			<td  style="text-align:right">成立时间：</td>
      			<td >
					<s:textfield verify="成立时间|NotNull" readonly="true" value="%{getText('global.date',{companyUser.regdate})}" onclick="WdatePicker({skin:'ext'});" cssClass="zc_input02" size="20" name="companyUser.regdate" id="regdate" ></s:textfield>
					<span style="color:red">*</span>
      			</td>
      			<td style="text-align:right">传真：</td>
      			<td>
      			<s:textfield maxlength="30"  cssClass="zc_input02" size="20" name="companyUser.industry" id="industry" ></s:textfield>
				</td>   
  			</tr>
  			<tr>
  			<td style="text-align:right">联系人姓名：</td>
      			<td>
      			<s:textfield maxlength="30" verify="联系人姓名|NotNull" cssClass="zc_input02" size="20" name="companyUser.truename" id="truename" ></s:textfield>
      			<span style="color:red">*</span>
      			</td>
      			<td style="text-align:right">联系人手机号码：</td>
      			<td>
      			<s:textfield maxlength="11" verify="联系人手机号码|NotNull|phone" onkeyup="restrictNumber(this,'1',11)" cssClass="zc_input02" size="20" name="companyUser.contactmobile" id="contactmobile" ></s:textfield>
      			<span style="color:red">*</span>
      			
      			</td>
  			</tr>
  			<tr>
      			<td style="text-align:right">联系人电话：</td>
      			<td>
      			<s:textfield maxlength="20" verify="联系人电话|NotNull|telephone" cssClass="zc_input02" size="20" name="companyUser.contactphone" id="contactphone" ></s:textfield>
      			<span style="color:red">*</span>
      			
      			</td>
  				<td style="text-align:right">联系人邮箱：</td>
      			<td> 
      			<s:textfield maxlength="30" verify="联系人邮箱|NotNull|email" cssClass="zc_input02" size="20" name="companyUser.email" id="email" ></s:textfield>
      			<span style="color:red">*</span>
      			
      			</td>
  			</tr>
  		
  			<tr>
  			<tr>
  			<td style="text-align:right">邮编：</td>
      			<td> 
      			<s:textfield maxlength="10"  cssClass="zc_input02" onkeyup="restrictNumber(this,'1',10)"  size="20" name="companyUser.postcode" id="postcode" ></s:textfield>
      			<span style="color:red">*</span>
      			</td>
  				<td style="text-align:right">银行账户名称：</td>
      			<td>
      			<s:textfield maxlength="100" verify="银行账户名称|NotNull" cssClass="zc_input02" size="20" name="companyUser.accountname" id="accountname" ></s:textfield>
      			<span style="color:red">*</span>
      			</td>
  			</tr>
			<td style="text-align:right">开户银行：</td>
      			<td> 
      			<s:textfield maxlength="50" verify="开户银行|NotNull" cssClass="zc_input02" size="20" name="companyUser.bankname" id="bankname" ></s:textfield>
      			<span style="color:red">*</span>
      			</td>
				<td style="text-align:right">银行账号：</td>
      			<td> 
      			<s:textfield maxlength="50" verify="银行账号|NotNull" cssClass="zc_input02" size="20" name="companyUser.banknum" id="banknum" ></s:textfield>
      			<span style="color:red">*</span></td>
			</tr>
			
  			<tr>
  				<td style="text-align:right">联系地址：</td>
      			<td colspan="3">
      				<s:textfield maxlength="200" verify="联系地址|NotNull" cssClass="zc_input02" size="60" name="companyUser.address" id="address" ></s:textfield>
      			<span style="color:red">*</span>
      			</td>
  			</tr>
  			
  				<tr>
  				<td style="text-align:right">机构简介：</td>
      			<td colspan="3">
      			<s:textarea name="companyUser.companyabout" verify="机构简介|NotNull" maxlength="4000" rows="5" cols="65" id="companyabout" cssClass="zc_textarea"></s:textarea>
      			<span style="color:red">*</span>
      			</td>
  			</tr>
			<s:if test="companyUser.ename!=null">
  				<tr>
  				<td style="text-align:right">修改原因：</td>
      			<td colspan="3">
  					<s:textarea name="companyUser.modifyreason" value="" cssClass="zc_textarea" verify="修改原因|NotNull" id="modifyreason" maxlength="4000" rows="5" cols="65"></s:textarea><span style="color:red">*</span>
      			 </td>
      			 	</tr>
  			</s:if>
			
		  </table>
		  </s:form>
</body>
</html>
