<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
 <script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>
 <script type="text/javascript">
 	function checkTrueName(){
 		//校验联系人姓名只能为中文，不能含空格及特殊字符
		var truename = $("#truename");
		var reg = /^[\u4e00-\u9fa5]+$/;//只允许中文
		if (!reg.test(truename.val())){
			alert("联系人姓名不能为空，只能为中文，不能包含空格及特殊符号，请检查！");
			truename.focus();
			return false;
		}
		return true;
 	}
 </script>
<body>
<s:form id="editUserDataForm" method="post">
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="BS_list">
<tr> 
	<th colspan="4"><div style="float:left">企业用户基本信息</div>
	<div style="float:right"><input type="button" onclick="if (checkTrueName()){submitEditUser();}" class="buttonClass_1" value="保存"></div>
	<s:hidden name="method" value="edit"></s:hidden>
	<s:hidden name="appModel"></s:hidden>
	<s:hidden name="companyUser.guid"></s:hidden>
	<s:hidden name="companyUser.logonguid"></s:hidden>
	<s:hidden name="companyUser.name"></s:hidden>
	<s:hidden name="companyUser.register_date"></s:hidden>
	<s:hidden name="companyUser.regtype"></s:hidden>
	<s:hidden name="companyUser.contactadd"></s:hidden>
	<s:hidden name="companyUser.contactper"></s:hidden>
	<s:hidden name="companyUser.bankname"></s:hidden>
	<s:hidden name="companyUser.banknum"></s:hidden>
	<s:hidden name="companyUser.accountname"></s:hidden>
	
	</th>
</tr>
           <tr>
  				<td width="18%" style="text-align:right">企业或单位名称：</td>
      			<td colspan="3" width="82%">
      			<s:textfield maxlength="200" verify="单位或机构名称|NotNull"  cssClass="zc_input02" size="68" name="companyUser.ename" id="ename" ></s:textfield>
			<span style="color:red">*</span>
      		</td>
  			</tr>
  			<tr>
  				<td width="18%"  style="text-align:right">企业或单位性质：</td>
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
					<s:textfield readonly="true" value="%{getText('global.date',{companyUser.regdate==null?'':companyUser.regdate})}" onclick="WdatePicker({skin:'ext'});" cssClass="zc_input02" size="20" name="companyUser.regdate" id="regdate" ></s:textfield>
      			</td>
  			</tr>
  			<tr>
  				<td style="text-align:right">传真：</td>
      			<td colspan="3">
      			<s:textfield maxlength="30"  cssClass="zc_input02" size="20" name="companyUser.industry" id="industry" ></s:textfield>
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
      				<s:textfield maxlength="30" cssClass="zc_input02" size="20" name="companyUser.personcardid" id="personcardid" ></s:textfield>
      			</td>
  				<td style="text-align:right">法定代表人：</td>
      			<td>
      			<s:textfield maxlength="20" verify="法定代表人|NotNull" cssClass="zc_input02" size="20" name="companyUser.lawperson" id="lawperson" ></s:textfield>
      			<span style="color:red">*</span>
      			
      			 </td>
  			</tr>
  			<tr>
  				<td style="text-align:right">注册地址：</td>
      			<td colspan="3">
      				<s:textfield maxlength="200" cssClass="zc_input02" size="68" name="companyUser.address" id="address" ></s:textfield>
      			</td>
  			</tr>
  			<tr>
  				<td style="text-align:right">所在行政区：</td>
      			<td colspan="3">
      				<s:select name="companyUser.administrative" id="administrative" cssClass="zc_input02" list="#{'':'---请选择---','罗湖区':'罗湖区','福田区':'福田区','南山区':'南山区','宝安区':'宝安区','龙岗区':'龙岗区','大鹏新区':'大鹏新区','坪山新区':'坪山新区','光明新区':'光明新区','龙华新区':'龙华新区','前海新区':'前海新区'}"></s:select>
      			</td>
  			</tr>
  			<tr>
  				<td style="text-align:right">邮编：</td>
      			<td> 
      			<s:textfield maxlength="10"  cssClass="zc_input02" onkeyup="restrictNumber(this,'1',10)"  size="20" name="companyUser.postcode" id="postcode" ></s:textfield>
      			</td>
				<td style="text-align:right">税务登记编号：</td>
      			<td> 
      			<s:textfield maxlength="50" cssClass="zc_input02" size="20" name="companyUser.taxnum" id="taxnum" ></s:textfield>
      			</td>
  			</tr>
  			<tr>
			<td style="text-align:right">国税纳税编码：</td>
      			<td> 
      			<s:textfield maxlength="50" cssClass="zc_input02" size="20" name="companyUser.nationaltax" id="nationaltax" ></s:textfield>
      			</td>
				<td style="text-align:right">地税纳税编码：</td>
      			<td> 
      			<s:textfield maxlength="50" cssClass="zc_input02" size="20" name="companyUser.localtax" id="localtax" ></s:textfield>
			</tr>
  			<tr>
  				<td style="text-align:right">营业地址：</td>
      			<td colspan="3">
      				<s:textfield maxlength="500" cssClass="zc_input02" size="60" name="companyUser.openadd" id="openadd" ></s:textfield>
      			</td>
  			</tr>
  			<tr>
  				<td style="text-align:right">经营范围：</td>
      			<td colspan="3">
      				<s:textarea name="companyUser.limit" rows="5" maxlength="4000" cols="65" id="limit" cssClass="zc_textarea"></s:textarea>
      			</td>
  			</tr>
  				<tr>
  				<td style="text-align:right">公司或机构简介：</td>
      			<td colspan="3">
      				<s:textarea name="companyUser.companyabout" maxlength="4000" rows="5" cols="65" id="companyabout" cssClass="zc_textarea"></s:textarea>
      			</td>
  			</tr>
			
		  </table>
		  </s:form>
</body>
</html>
