<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../share/taglib.jsp"%>
<html>
<head>
 <script type="text/javascript" src="/js/validation.js"></script>
 <script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	function doAdd(type){
		if(!validations("submitForm"))return false;
		$("#submitForm").submit();
	}
	function doEdit(type){
		if(!validations("submitForm"))return false;
		 var data = $("#submitForm").serializeArray(); 
		  $.post("/userService/editCompanyUserInfo.html", data,function(data){
		  	Dialog.alert(data.message);
		  },"json");
	}
</script>
<body>
<s:form action="#" method="post" id="submitForm">
<s:hidden name="method" value="add"></s:hidden>
<s:hidden name="isEdit"></s:hidden>
	<s:hidden name="companyUser.guid"></s:hidden>
	<s:hidden name="companyUser.logonguid"></s:hidden>
	<s:hidden name="companyUser.register_date"></s:hidden>
	<s:hidden name="companyUser.name"></s:hidden>
	<s:hidden name="companyUser.regtype"></s:hidden>
     <table width="99%" cellpadding="0" cellspacing="0">
<tr> 
<s:if test="isEdit==null">
<td class="title">组织机构基本信息</td>
</s:if>
<s:else>
<td class="title1">修改组织机构基本信息</td>
</s:else>
</tr>
<tr><td class="td_left1">
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" id="userInfoTab">
           <tr>
  				<th>单位名称：</th>
      			<td colspan="3">
      			<s:textfield maxlength="200" verify="单位或机构名称|NotNull"  cssClass="zc_input02" size="60" name="companyUser.ename" id="ename" ></s:textfield>
			<span style="color:red">*</span>
      			</td>
  			</tr>
  			<tr>
  			<th>单位性质：</th>
      			<td>
      			<s:textfield maxlength="200" verify="单位性质|NotNull"  cssClass="zc_input02" size="20" name="companyUser.kind" id="kind" ></s:textfield>
			<span style="color:red">*</span>
			<th>机构代码：</th>
      			<td > 
      			<s:textfield maxlength="30" verify="机构代码|NotNull"   cssClass="zc_input02" size="20" name="companyUser.orgcode" id="orgcode" readonly="true"></s:textfield>
      			<span style="color:red">*</span>
      			</td>
  			</tr>
  			<tr>
  			<th>成立时间：</th>
      			<td > 
      			<s:textfield verify="成立时间|NotNull" readonly="true"  onclick="WdatePicker();" cssClass="zc_input02" size="20" name="companyUser.regdate" id="regdate" ></s:textfield>
      			<span style="color:red">*</span>
				</td>
				<th>法定代表人：</th>
      			<td> 
      			<s:textfield maxlength="20" verify="法定代表人|NotNull" cssClass="zc_input02" size="20" name="companyUser.lawperson" id="lawperson" ></s:textfield>
      			<span style="color:red">*</span>
      			</td>
  			</tr>
  			<tr>
      			<th>法人身份证号：</th>
      			<td >
      			<s:textfield maxlength="30" verify="法人身份证号|NotNull" cssClass="zc_input02" size="20" name="companyUser.lowcardid" id="lowcardid" ></s:textfield>
      			<span style="color:red">*</span>
      			</td>
      			<th>法人联系电话：</th>
      			<td>
      			<s:textfield maxlength="20" verify="法人电话|NotNull|telephone" cssClass="zc_input02" size="20" name="companyUser.lawphone" id="lawphone" ></s:textfield>
      			<span style="color:red">*</span>
  			</tr>
  			
  			<tr>
				<th>主管部门：</th>
      			<td >
      			<s:textfield maxlength="20" verify="主管部门|NotNull" cssClass="zc_input02" size="20" name="companyUser.zgbm" id="zgbm" ></s:textfield>
      			<span style="color:red">*</span>
      			 </td>
      			<th>联系人姓名：</th>
      			<td> 
      			<s:textfield maxlength="30" verify="联系人姓名|NotNull" cssClass="zc_input02" size="20" name="companyUser.truename" id="truename" ></s:textfield>
      			<span style="color:red">*</span>
      			</td>
      		
  			</tr>
  			<tr>
  			<th>联系人手机：</th>
      			<td > 
      			<s:textfield maxlength="11" verify="联系人手机号码|NotNull|phone" onkeyup="restrictNumber(this,'1',11)" cssClass="zc_input02" size="20" name="companyUser.contactmobile" id="contactmobile" ></s:textfield>
      			<span style="color:red">*</span>
      			</td>
      			<th>联系人电话：</th>
      			<td > 
      			<s:textfield maxlength="20" verify="联系人电话|NotNull|telephone" cssClass="zc_input02" size="20" name="companyUser.contactphone" id="contactphone" ></s:textfield>
      			<span style="color:red">*</span>
      			</td>
  			</tr>
  			<tr>
      			<th>联系人身份证：</th>
      			<td>
      				<s:textfield maxlength="30" verify="联系人身份证号|NotNull" cssClass="zc_input02" size="20" name="companyUser.personcardid" id="personcardid" ></s:textfield>
      			<span style="color:red">*</span>
      			</td>
  				<th>联系人邮箱：</th>
      			<td > 
      			<s:textfield maxlength="30" verify="联系人邮箱|NotNull|email" cssClass="zc_input02" size="20" name="companyUser.email" id="email" ></s:textfield>
      			<span style="color:red">*</span>
      			</td>
  			</tr>
  			<tr>
  				<th>注册所在地：</th>
      			<td colspan="3" >
      			<s:textfield maxlength="200" verify="注册所在地|NotNull" cssClass="zc_input02" size="60" name="companyUser.openadd" id="openadd" ></s:textfield>
      			<span style="color:red">*</span>
      			</td>
  			</tr>
  			<tr>
  				<th>注册地址：</th>
      			<td colspan="3" >
      			<s:textfield maxlength="200" verify="注册地址|NotNull" cssClass="zc_input02" size="60" name="companyUser.address" id="address" ></s:textfield>
      			<span style="color:red">*</span>
      			</td>
  			</tr>
  			<tr >
  				<th>开户银行：</th>
      			<td colspan="3"> 
      			<s:textfield maxlength="500" verify="开户银行|NotNull" cssClass="zc_input02" size="20" name="companyUser.bankname" id="bankname" ></s:textfield>
      			<span style="color:red">*</span>
      			</td>
  			</tr>
  			<tr >
  			<th>银行账号：</th>
      			<td > 
      			<s:textfield maxlength="30" verify="银行账号|NotNull" cssClass="zc_input02" onkeyup="restrictNumber(this,'1',30)" size="20" name="companyUser.banknum" id="banknum" ></s:textfield>
      			<span style="color:red">*</span>
      			</td>
  				<th>账户名称：</th>
      			<td> 
      			<s:textfield maxlength="100" verify="账户名称|NotNull" cssClass="zc_input02" size="20" name="companyUser.accountname" id="accountname" ></s:textfield>
      			<span style="color:red">*</span>
      			</td>
      			
  			</tr>
  			
  		
  			
  			<s:if test="companyUser.ename!=null">
  				<tr >
  				<th>修改原因：</th>
      			<td colspan="3">
  			<s:textarea name="companyUser.modifyreason" cssClass="zc_textarea" verify="修改原因|NotNull" id="modifyreason" maxlength="4000" rows="5" cols="65"></s:textarea><span style="color:red">*</span>
      			 </td>
      			 	</tr>
  		</s:if>
  		
  		<tr><td colspan="4"style="height:30px;text-align:center;">
  				
  							<s:if test="isEdit==null">
  	<input type="button" onclick="doAdd()" value="保 存" class="buttonClass_1"/> 
  	</s:if>
  	<s:else>
  	<input type="button" onclick="doEdit()" value="保 存" class="buttonClass_1"/> 
  	</s:else>
  				
  				&nbsp;&nbsp;
			<input type="reset" value="重 置" class="buttonClass_1"/> &nbsp;&nbsp;
			
			</td>
			
			 </tr>
		  </table>
          </td></tr></table>
</s:form>

</body>
</html>