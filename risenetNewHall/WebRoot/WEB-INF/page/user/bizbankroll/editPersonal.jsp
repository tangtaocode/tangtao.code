<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<body> 
<s:form id="editUserDataForm" method="post">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="BS_list" >
<tr> 
	<th colspan="4" style="text-align:center">
	<div style="float:left">个人用户基本信息</div>
	<div style="float:right"><input type="button" onclick="submitEditUser()" class="buttonClass_1" value="保存"></div>
	<s:hidden name="method" value="edit"></s:hidden>
	<s:hidden name="appModel"></s:hidden>
	<s:hidden name="personUser.user_guid"></s:hidden>
	<s:hidden name="personUser.logonguid"></s:hidden>
	<s:hidden name="personUser.work_duty"></s:hidden>
	<s:hidden name="personUser.ifmarry"></s:hidden>
	<s:hidden name="personUser.work_title"></s:hidden>
	<s:hidden name="personUser.register_date"></s:hidden>
	<s:hidden name="personUser.grad_date"></s:hidden>
	<s:hidden name="personUser.idcard_type" value="1"></s:hidden>
	<s:hidden name="personUser.polity"></s:hidden>
	<s:hidden name="personUser.email"></s:hidden>
	<s:hidden name="personUser.postcode"></s:hidden>
	<s:hidden name="personUser.work_tel"></s:hidden>
	<s:hidden name="personUser.contact_add"></s:hidden>
	<s:hidden name="personUser.work_add"></s:hidden>
	</th>
</tr>
  			<tr>
      			<td style="text-align:right" width="18%">姓名：</td>
      			<td width="32%"> 
				<s:textfield maxlength="50" verify="姓名|NotNull"  cssClass="zc_input02" size="20" name="personUser.true_name" id="true_name" ></s:textfield>
				<span style="color:red">*</span>
				</td>
  				<td style="text-align:right" width="18%">性别：</td>
      			<td width="32%"> 
      			<s:select cssClass="zc_input02" list="#{'':'---请选择---','男':'男','女':'女'}" name="personUser.sex" id="sex" ></s:select><span style="color:red">*</span>
				</td>
  			</tr>
  			<tr>
  				<td style="text-align:right">出生日期：</td>
      			<td>
				<s:textfield verify="出生日期|NotNull" value="%{getText('global.date',{personUser.birth_date})}"  onclick="WdatePicker({skin:'ext'});" cssClass="zc_input02" size="20" name="personUser.birth_date" id="birth_date" readonly="true"></s:textfield>
				<span style="color:red">*</span>
				</td>
  				<td style="text-align:right">民族：</td>
      			<td><s:textfield maxlength="10" verify="民族|NotNull"  cssClass="zc_input02" size="20" name="personUser.nation" id="nation" ></s:textfield>
				<span style="color:red">*</span>
		</td>
  			</tr>
  			<tr>
  				<td style="text-align:right">
  				<s:if test="personUser.idcard_type==1">
  				身份证号码：
  				</s:if>
  				<s:if test="personUser.idcard_type==2">
  				护照号码：
  				</s:if>
  				</td>
      			<td colspan="3"> 
				<s:textfield maxlength="30" readonly="true" verify="证件号码|NotNull"  cssClass="zc_input02" size="50" name="personUser.idcard_code" id="idcard_code" ></s:textfield>
				<span style="color:red">*</span>
		</td>
  			</tr>
  			<tr>
      			<td style="text-align:right">手机号码：</td>
      			<td><s:textfield maxlength="11" onkeyup="restrictNumber(this,'1',11)" verify="手机号码|NotNull|phone" size="20"  cssClass="zc_input02"  name="personUser.mobile" id="mobile" ></s:textfield>
		<span style="color:red">*</span>
		</td>
      			<td style="text-align:right">联系电话：</td>
      			<td><s:textfield maxlength="30" verify="联系电话|NotNull|telephone" size="20" cssClass="zc_input02"  name="personUser.contact_tel" id="contact_tel" ></s:textfield>
		<span style="color:red">*</span>
		</td>
  			</tr>
  			<tr>
  			<td style="text-align:right">工作单位：</td>
      			<td> <s:textfield maxlength="100"  cssClass="zc_input02" size="20" name="personUser.work_company" id="work_company" ></s:textfield>
			</td>
  				
  				<td style="text-align:right">学历：</td>
      			<td> 
      			<s:select cssClass="zc_input02" list="#{'':'---请选择---','博士':'博士','硕士':'硕士','本科':'本科','大专':'大专','高中':'高中','初中':'初中','小学':'小学','其它':'其它'}" name="personUser.schoolage" id="schoolage" ></s:select><span style="color:red">*</span>
  				</td>
  			</tr>
  			
  			<tr>
      			 <td style="text-align:right">年龄：</td>
      			<td>  <s:textfield maxlength="3" verify="年龄|NotNull" onkeyup="restrictNumber(this,'1',3)" cssClass="zc_input02" size="20" name="personUser.age" id="age" ></s:textfield>
				<span style="color:red">*</span>
				<td style="text-align:right">银行账户名称：</td>
      			<td>
      			<s:textfield maxlength="100" verify="账户名称|NotNull" cssClass="zc_input02" size="20" name="personUser.accountname" id="accountname" ></s:textfield>
      			<span style="color:red">*</span>
      			</td>
		</td>
  			</tr>
  			<td style="text-align:right">开户银行：</td>
      			<td> 
      			<s:textfield maxlength="50" verify="开户银行|NotNull" cssClass="zc_input02" size="20" name="personUser.bankname" id="bankname" ></s:textfield>
      			<span style="color:red">*</span>
      			</td>
				<td style="text-align:right">银行账号：</td>
      			<td> 
      			<s:textfield maxlength="50" verify="银行账号|NotNull" cssClass="zc_input02" size="20" name="personUser.banknum" id="banknum" ></s:textfield>
      			<span style="color:red">*</span></td>
			</tr>
  			<tr>
  				<td style="text-align:right">籍贯：</td>
      			<td width="80%" colspan="3">
      			<s:textfield maxlength="100"  cssClass="zc_input02"  verify="籍贯|NotNull" size="70"  name="personUser.home_add" id="home_add" ></s:textfield>
		<span style="color:red">*</span>
		</td>
  			</tr>
  			<tr>
  				<td style="text-align:right">住址：</td>
      			<td width="80%" colspan="3"><s:textfield maxlength="200" verify="住址|NotNull" size="70" cssClass="zc_input02" name="personUser.native_add" id="native_add" ></s:textfield>
		<span style="color:red">*</span>
		</td>
  			</tr>
  			<tr>
  				<td style="text-align:right">户口所在地：</td>
      			<td width="80%" colspan="3"><s:textfield maxlength="100" verify="户口所在地|NotNull"  cssClass="zc_input02" size="70" name="personUser.reg_add" id="reg_add" ></s:textfield>
		</td>
  			</tr>
  			
			
			<s:if test="personUser.true_name!=null">
  			<tr>
  			<td style="text-align:right">修改原因：</td>
  			<td colspan="3">
  				<s:textarea name="personUser.modifyreason" value="" verify="修改原因|NotNull" cssClass="zc_textarea" id="modifyreason" rows="5" cols="70" maxlength="4000"></s:textarea><span style="color:red">*</span>
  			</td>
  			</tr>
  			</s:if>
		  </table>
		  </s:form>
</body>
</html>
