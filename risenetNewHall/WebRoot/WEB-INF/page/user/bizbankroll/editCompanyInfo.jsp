<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<body>
<s:form id="editUserDataForm" method="post">
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="BS_list">
<tr> 
	<th colspan="4"><div style="float:left">企业用户基本信息</div>
	<div style="float:right"><input type="button" onclick="submitEditUser()" class="buttonClass_1" value="保存"></div>
	<s:hidden name="method" value="edit"></s:hidden>
	<s:hidden name="appModel"></s:hidden>
	<s:hidden name="companyUser.guid"></s:hidden>
	<s:hidden name="companyUser.logonguid"></s:hidden>
	<s:hidden name="companyUser.name"></s:hidden>
	<s:hidden name="companyUser.register_date" value="2013-02-02"></s:hidden>
	<s:hidden name="companyUser.regtype"></s:hidden>
	<s:hidden name="companyUser.totalpeop"></s:hidden>
	<s:hidden name="companyUser.postcode"></s:hidden>
	<s:hidden name="companyUser.regmoney"></s:hidden>
	<s:hidden name="companyUser.dztotalpeop"></s:hidden>
	<s:hidden name="companyUser.companyabout"></s:hidden>
	
	</th>
</tr>
           <tr>
  				<td width="18%" style="text-align:right">企业名称：</td>
      			<td colspan="3" width="82%">
      			<s:textfield maxlength="200" verify="单位或机构名称|NotNull"  cssClass="zc_input02" size="60" name="companyUser.ename" id="ename" ></s:textfield>
			<span style="color:red">*</span>
      		</td>
  			</tr>
  			<tr>
  				<td width="18%"  style="text-align:right">企业性质：</td>
      			<td width="32%">
      				<s:select name="companyUser.kind" id="kind" cssClass="zc_input02" list="#{'':'---请选择---','全民所有制企业':'全民所有制企业','集体所有制企业':'集体所有制企业','有限责任公司':'有限责任公司','股份有限公司':'股份有限公司','中外合资企业':'中外合资企业','中外合作企业':'中外合作企业','外资企业':'外资企业','合伙企业':'合伙企业','个人独资企业':'个人独资企业','私营企业':'私营企业','其它':'其它'}"></s:select>
      			&nbsp;&nbsp;<span style="color:red">* </span>
      			</td>
  				<td width="18%" style="text-align:right">机构代码：</td>
      			<td width="32%"> 
      			<s:textfield maxlength="30" verify="机构代码|NotNull" readonly="true"  cssClass="zc_input02" size="20" name="companyUser.orgcode" id="orgcode" ></s:textfield>
      			<span style="color:red">*</span>
      			</td>
  			</tr>
  			<tr>
  				<td style="text-align:right">登记注册号：</td>
      			<td > 
      			<s:textfield maxlength="30" verify="登记注册号|NotNull"  cssClass="zc_input02" size="20" name="companyUser.regcode" id="regcode" ></s:textfield>
      			<span style="color:red">*</span>
      			
      			</td>
      			<td  style="text-align:right">成立时间：</td>
      			<td >
					<s:textfield verify="成立时间|NotNull" readonly="true" value="%{getText('global.date',{companyUser.regdate==null?'':companyUser.regdate})}" onclick="WdatePicker({skin:'ext'});" cssClass="zc_input02" size="20" name="companyUser.regdate" id="regdate" ></s:textfield>
					<span style="color:red">*</span>
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
      			<td style="text-align:right">联系人身份证号：</td>
      			<td> 
      					<s:textfield maxlength="30" verify="联系人身份证号|NotNull" cssClass="zc_input02" size="20" name="companyUser.personcardid" id="personcardid" ></s:textfield>
      			<span style="color:red">*</span>
      			
      			</td>
  				<td style="text-align:right">法定代表人：</td>
      			<td>
      			<s:textfield maxlength="20" verify="法定代表人|NotNull" cssClass="zc_input02" size="20" name="companyUser.lawperson" id="lawperson" ></s:textfield>
      			<span style="color:red">*</span>
      			
      			 </td>
  			</tr>
  			<tr>
				<td style="text-align:right">法人电话：</td>
      			<td>
      			<s:textfield maxlength="20" verify="法人电话|NotNull|telephone" cssClass="zc_input02" size="20" name="companyUser.lawphone" id="lawphone" ></s:textfield>
      			<span style="color:red">*</span>
      			</td>
      			<td style="text-align:right">法人手机：</td>
      			<td>
      				<s:textfield maxlength="11" verify="法人手机|NotNull|phone" onkeyup="restrictNumber(this,'1',11)" cssClass="zc_input02" size="20" name="companyUser.lawperphone" id="lawperphone" ></s:textfield>
      			<span style="color:red">*</span>
      			</td>
  			</tr>
  			<tr>
  				<td style="text-align:right">法人邮箱：</td>
      			<td> 
      				<s:textfield maxlength="30" verify="法人邮箱|NotNull|email" cssClass="zc_input02" size="20" name="companyUser.lawperemail" id="lawperemail" ></s:textfield>
      			<span style="color:red">*</span>
      			</td>
      			<td style="text-align:right">法人身份证号：</td>
      			<td>
				<s:textfield maxlength="30" verify="法人身份证号|NotNull" cssClass="zc_input02" size="20" name="companyUser.lowcardid" id="lowcardid" ></s:textfield>
      			<span style="color:red">*</span>
				</td>
  			</tr>
  			
  			<tr >
  				<td style="text-align:right">传真：</td>
      			<td>
      			<s:textfield maxlength="30"  cssClass="zc_input02" size="20" name="companyUser.industry" id="industry" ></s:textfield>
  				</td>
				<td style="text-align:right">税务登记编号：</td>
      			<td> 
      			<s:textfield maxlength="50" verify="税务登记编号|NotNull" cssClass="zc_input02" size="20" name="companyUser.taxnum" id="taxnum" ></s:textfield>
      			<span style="color:red">*</span>
      			</td>
  			</tr>
  			<tr>
			<td style="text-align:right">国税纳税编码：</td>
      			<td> 
      			<s:textfield maxlength="50" verify="国税纳税编码|NotNull" cssClass="zc_input02" size="20" name="companyUser.nationaltax" id="nationaltax" ></s:textfield>
      			<span style="color:red">*</span>
      			</td>
				<td style="text-align:right">地税纳税编码：</td>
      			<td> 
      			<s:textfield maxlength="50" verify="地税纳税编码|NotNull" cssClass="zc_input02" size="20" name="companyUser.localtax" id="localtax" ></s:textfield>
      			<span style="color:red">*</span></td>
			</tr>
			<tr>
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
  				<td style="text-align:right">账户名称：</td>
      			<td>
      			<s:textfield maxlength="100" verify="账户名称|NotNull" cssClass="zc_input02" size="20" name="companyUser.accountname" id="accountname" ></s:textfield>
      			<span style="color:red">*</span>
      			</td>
      			<td style="text-align:right">上年末从业人数：</td>
      			<td> 
      			<s:textfield maxlength="50" verify="上年末从业人数|NotNull" cssClass="zc_input02" size="20" name="companyUser.userCommoney.cyrs" id="cyrs" ></s:textfield>
      			人<span style="color:red">*</span>
      			</td>
  			</tr>
  			<tr>
				<td style="text-align:right">上年度纳税总额：</td>
      			<td> 
      			<s:textfield maxlength="50" verify="上年度纳税总额|NotNull" cssClass="zc_input02" size="20" name="companyUser.userCommoney.nsze" id="nsze" ></s:textfield>
      			万元<span style="color:red">*</span>
      			</td>
				<td style="text-align:right">上年度营业收入：</td>
      			<td> 
      			<s:hidden anme="companyUser.userCommoney.guid"></s:hidden>
      			<s:hidden anme="companyUser.userCommoney.isold"></s:hidden>
      			<s:textfield maxlength="50" verify="上年度营业收入|NotNull" cssClass="zc_input02" size="20" name="companyUser.userCommoney.yysr" id="banknum" ></s:textfield>
      			万元<span style="color:red">*</span></td>
			</tr>
  			<tr>
			<td style="text-align:right">是否总部企业：</td>
      			<td> 
      			<s:select list="#{'':'---请选择---','是':'是','否':'否'}" cssClass="zc_input02"  name="companyUser.contactadd" id="contactadd"></s:select>
      			<span style="color:red">*</span>
      			</td>
				<td style="text-align:right">是否金融企业：</td>
      			<td> 
      			<s:select list="#{'':'---请选择---','是':'是','否':'否'}" cssClass="zc_input02"  name="companyUser.contactper" id="contactper"></s:select>
      			<span style="color:red">*</span></td>
			</tr>
  			<tr>
  				<td style="text-align:right">注册地址：</td>
      			<td colspan="3">
      				<s:textfield maxlength="200" verify="注册地址|NotNull" cssClass="zc_input02" size="60" name="companyUser.address" id="address" ></s:textfield>
      			<span style="color:red">*</span>
      			</td>
  			</tr>
  			<tr>
  				<td style="text-align:right">营业地址：</td>
      			<td colspan="3">
      			<s:textfield maxlength="500" verify="营业地址|NotNull" cssClass="zc_input02" size="60" name="companyUser.openadd" id="openadd" ></s:textfield>
      			<span style="color:red">*</span>
      			</td>
  			</tr>
  			<tr>
  				<td style="text-align:right">经营范围：</td>
      			<td colspan="3">
      			<s:textarea name="companyUser.limit" verify="经营范围|NotNull" rows="5" maxlength="4000" cols="65" id="limit" cssClass="zc_textarea"></s:textarea>
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
