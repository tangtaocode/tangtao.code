<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head>
 <script type="text/javascript" src="/js/validation.js"></script>
 <script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	function doAdd(type){
		if(!validations("submitForm"))return false;
		if($("#kind").val()==""){
			Dialog.alert("企业或机构性质");
			return false;
		}
		$("#submitForm").submit();
	}
	function doEdit(type){
		if(!validations("submitForm"))return false;
		if($("#kind").val()==""){
			Dialog.alert("企业或机构性质");
			return false;
		}
		 var data = $("#submitForm").serializeArray(); 
		  $.post("/userService/editCompanyUserInfo.html", data,function(data){
		  	Dialog.alert(data.message);
		  },"json");
	}
</script>
</head>
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
<td class="title">企业或组织用户基本信息</td>
</s:if>
<s:else>
<td class="title1">修改企业或组织用户基本信息</td>
</s:else>
</tr>
<tr><td class="td_left1">
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" id="userInfoTab">
           <tr>
  				<th>
  				<s:if test="userInfo.usertype==2">
  				企业或单位
  				</s:if>
  				<s:if test="userInfo.usertype==3">
  				组织机构
  				</s:if>
  				名称：</th>
      			<td colspan="3">
      			<s:textfield maxlength="200" verify="单位或机构名称|NotNull"  cssClass="zc_input02" size="60" name="companyUser.ename" id="ename" ></s:textfield>
			<span style="color:red">*</span>
      		</td>
  			</tr>
  			<tr>
  				<th>
  				<s:if test="userInfo.usertype==2">
  				企业或单位
  				</s:if>
  				<s:if test="userInfo.usertype==3">
  				组织机构
  				</s:if>
  				性质：</th>
      			<td>
      			<s:if test="userInfo.usertype==2">
      				<s:select name="companyUser.kind" id="kind" cssClass="zc_input02" list="#{'':'---请选择---','全民所有制企业':'全民所有制企业','集体所有制企业':'集体所有制企业','有限责任公司':'有限责任公司','股份有限公司':'股份有限公司','中外合资企业':'中外合资企业','中外合作企业':'中外合作企业','外资企业':'外资企业','合伙企业':'合伙企业','个人独资企业':'个人独资企业','私营企业':'私营企业','其它':'其它'}"></s:select>
      			</s:if>
      			<s:if test="userInfo.usertype==3">
      				<s:select name="companyUser.kind" id="kind" cssClass="zc_input02" list="#{''：'---请选择---','国家机关':'国家机关','事业单位':'事业单位','社会团体':'社会团体','其他机构':'其他机构'}"></s:select>
      			</s:if>
      			&nbsp;&nbsp;<span style="color:red">* </span></td>
  				<th>机构代码：</th>
      			<td> 
      			<s:textfield maxlength="30" verify="机构代码|NotNull" readonly="true"  cssClass="zc_input02" size="20" name="companyUser.orgcode" id="orgcode" ></s:textfield>
      			<span style="color:red">*</span></td>
  			</tr>
  			<tr>
  				<th>登记注册号：</th>
      			<td> 
      			<s:textfield maxlength="30" verify="登记注册号|NotNull"  cssClass="zc_input02" size="20" name="companyUser.regcode" id="regcode" ></s:textfield>
      			<span style="color:red">*</span>
      			</td>
      			<th>成立时间：</th>
      			<td>
      			<s:textfield verify="成立时间|NotNull" readonly="true"  onclick="WdatePicker();" cssClass="zc_input02" size="20" name="companyUser.regdate" id="regdate" ></s:textfield>
      			<span style="color:red">*</span></td>
  			</tr>
  			<tr>
  				<th>传真：</th>
      			<td>
      			<s:textfield maxlength="30"  cssClass="zc_input02" size="20" name="companyUser.industry" id="industry" ></s:textfield>
  				<th>企业或组织人数：</th>
      			<td> 
      			<s:textfield maxlength="7" verify="企业或组织人数|NotNull" onkeyup="restrictNumber(this,'1',7)" cssClass="zc_input02" size="20" name="companyUser.totalpeop" id="totalpeop" ></s:textfield>
      			<span style="color:red">*</span></td>
  			</tr>
  			<tr>
  			<th>联系人姓名：</th>
      			<td>
      			<s:textfield maxlength="30" verify="联系人姓名|NotNull" cssClass="zc_input02" size="20" name="companyUser.truename" id="truename" ></s:textfield>
      			<span style="color:red">*</span></td>
      			<th>联系人手机号码：</th>
      			<td>
      			<s:textfield maxlength="11" verify="联系人手机号码|NotNull|phone" onkeyup="restrictNumber(this,'1',11)" cssClass="zc_input02" size="20" name="companyUser.contactmobile" id="contactmobile" ></s:textfield>
      			<span style="color:red">*</span>
      			</td>
  			</tr>
  			<tr>
      			<th>联系人电话：</th>
      			<td>
      			<s:textfield maxlength="20" verify="联系人电话|NotNull|telephone" cssClass="zc_input02" size="20" name="companyUser.contactphone" id="contactphone" ></s:textfield>
      			<span style="color:red">*</span>
      			</td>
  				<th>联系人邮箱：</th>
      			<td> 
      			<s:textfield maxlength="30" verify="联系人邮箱|NotNull|email" cssClass="zc_input02" size="20" name="companyUser.email" id="email" ></s:textfield>
      			<span style="color:red">*</span>
      			</td>
  			</tr>
  			<tr>
      			<th>联系人身份证号：</th>
      			<td> 
      				<s:textfield maxlength="30" verify="联系人身份证号|NotNull" cssClass="zc_input02" size="20" name="companyUser.personcardid" id="personcardid" ></s:textfield>
      			<span style="color:red">*</span>
      			</td>
  				<th>法定代表人：</th>
      			<td>
      			<s:textfield maxlength="20" verify="法定代表人|NotNull" cssClass="zc_input02" size="20" name="companyUser.lawperson" id="lawperson" ></s:textfield>
      			<span style="color:red">*</span>
      			 </td>
  			</tr>
  			<tr>
				<th>法人电话：</th>
      			<td>
      			<s:textfield maxlength="20" verify="法人电话|NotNull|telephone" cssClass="zc_input02" size="20" name="companyUser.lawphone" id="lawphone" ></s:textfield>
      			<span style="color:red">*</span>
      			</td>
      			<th>法人手机：</th>
      			<td>
      			<s:textfield maxlength="11" verify="法人手机|NotNull|phone" onkeyup="restrictNumber(this,'1',11)" cssClass="zc_input02" size="20" name="companyUser.lawperphone" id="lawperphone" ></s:textfield>
      			<span style="color:red">*</span>
      			</td>
  			</tr>
  			<tr>
  				<th>法人邮箱：</th>
      			<td> 
      				<s:textfield maxlength="30" verify="法人邮箱|NotNull|email" cssClass="zc_input02" size="20" name="companyUser.lawperemail" id="lawperemail" ></s:textfield>
      			<span style="color:red">*</span>
      			</td>
      			<th>法人身份证号：</th>
      			<td>
				<s:textfield maxlength="30" verify="法人身份证号|NotNull" cssClass="zc_input02" size="20" name="companyUser.lowcardid" id="lowcardid" ></s:textfield>
      			<span style="color:red">*</span>
				</td>
  			</tr>
  			<tr>
  				<th>注册地址：</th>
      			<td colspan="3">
      				<s:textfield maxlength="200" verify="注册地址|NotNull" cssClass="zc_input02" size="60" name="companyUser.address" id="address" ></s:textfield>
      			<span style="color:red">*</span>
      			</td>
  			</tr>
  		
  			<s:if test="userInfo.sxlx==2">
  			<tr >
  				<th>公司或机构资质（资质通过情况）：</th>
      			<td colspan="3">
      			<s:textarea name="companyUser.companyaptitudes" verify="公司或机构资质|NotNull" maxlength="4000" rows="5" cols="65" id="companyaptitudes" cssClass="zc_textarea"></s:textarea>
      			<span style="color:red">*</span>
      			</td>
  			</tr>
  			<tr>
  				<th>获奖情况：</th>
      			<td colspan="3">
      			<s:textarea name="companyUser.awards" rows="5" cols="65" maxlength="4000" id="awards" cssClass="zc_textarea"></s:textarea>
      			</td>
  			</tr>
  			</s:if>
  			<s:if test="userInfo.usertype==2">
  			<tr>
  				<th>邮编：</th>
      			<td> 
      			<s:textfield maxlength="10"  cssClass="zc_input02" onkeyup="restrictNumber(this,'1',10)"  size="20" name="companyUser.postcode" id="postcode" ></s:textfield>
      			<span style="color:red">*</span>
      			</td>
  				<th>注册资本：</th>
      			<td> 
      			<s:textfield maxlength="22" verify="注册资本|NotNull" onkeyup="restrictNumber(this,'2',2)" cssClass="zc_input02" size="20" name="companyUser.regmoney" id="regmoney" ></s:textfield>
      			<span style="color:red">*</span>
      			</td>
  			</tr>
  			<tr >
  				<th>大专以上员工数：</th>
      			<td width="30%" colspan="3"> 
      			<s:textfield maxlength="7" verify="大专以上员工数|NotNull" onkeyup="restrictNumber(this,'1',7)" cssClass="zc_input02" size="20" name="companyUser.dztotalpeop" id="dztotalpeop" ></s:textfield>
      			<span style="color:red">*</span>
      			</td>
  			</tr>
  			
  			<tr>
  				<th>营业地址：</th>
      			<td colspan="3">
      			<s:textfield maxlength="500" verify="营业地址|NotNull" cssClass="zc_input02" size="60" name="companyUser.openadd" id="openadd" ></s:textfield>
      			<span style="color:red">*</span>
      			</td>
  			</tr>
  			<tr>
  				<th>经营范围：</th>
      			<td colspan="3">
      			<s:textarea name="companyUser.limit" verify="经营范围|NotNull" rows="5" maxlength="4000" cols="65" id="limit" cssClass="zc_textarea"></s:textarea>
      			</td>
  			</tr>
  			</s:if>
  					<tr >
  				<th>公司或机构简介：</th>
      			<td colspan="3">
      			<s:textarea name="companyUser.companyabout" verify="公司或机构简介|NotNull" maxlength="4000" rows="5" cols="65" id="companyabout" cssClass="zc_textarea"></s:textarea>
      			<span style="color:red">*</span>
      			</td>
  			</tr>
  			<s:if test="userInfo.usertype==2&&userInfo.sxlx==2">
  				<tr >
  				<th>税务登记编号（纳税人识别号）：</th>
      			<td>
      			<s:textfield maxlength="50" verify="税务登记编号|NotNull" cssClass="zc_input02" size="20" name="companyUser.taxnum" id="taxnum" ></s:textfield>
      			<span style="color:red">*</span>
      			</td>
  				<th>国税纳税编码：</th>
      			<td>
      			<s:textfield maxlength="50" verify="国税纳税编码|NotNull" cssClass="zc_input02" size="20" name="companyUser.nationaltax" id="nationaltax" ></s:textfield>
      			<span style="color:red">*</span>
      			</td>
  			</tr>
  			<tr>
  				<th>地税纳税编码：</th>
      			<td>
      			<s:textfield maxlength="50" verify="地税纳税编码|NotNull" cssClass="zc_input02" size="20" name="companyUser.localtax" id="localtax" ></s:textfield>
      			<span style="color:red">*</span>
      			 </td>
  				<th>营业面积：</th>
      			<td>
      			<s:textfield maxlength="20" verify="营业面积|NotNull" cssClass="zc_input02" size="20" onkeyup="restrictNumber(this,'2',2)" name="companyUser.area" id="area" ></s:textfield>平方米
      			<span style="color:red">*</span>
      			</td>
  			</tr>
  			</s:if>
  			<s:if test="userInfo.sxlx==2||userInfo.sxlx==3">
  			<tr >
  				<th>开户银行：</th>
      			<td>
      			<s:textfield maxlength="500" verify="开户银行|NotNull" cssClass="zc_input02" size="20" name="companyUser.bankname" id="bankname" ></s:textfield>
      			<span style="color:red">*</span>
      			 </td>
  				<th>银行账号：</th>
      			<td> 
      			<s:textfield maxlength="30" verify="银行账号|NotNull" cssClass="zc_input02" onkeyup="restrictNumber(this,'1',30)" size="20" name="companyUser.banknum" id="banknum" ></s:textfield>
      			<span style="color:red">*</span>
      			</td>
  			</tr>
  			<tr >
  				<th>账户名称：</th>
      			<td colspan="3">
      			<s:textfield maxlength="100" verify="账户名称|NotNull" cssClass="zc_input02" size="20" name="companyUser.accountname" id="accountname" ></s:textfield>
      			<span style="color:red">*</span>
      			 </td>
  			</tr>
  			</s:if>
  				<s:if test="companyUser.ename!=null">
  				<tr>
  				<th >修改原因：</th>
      			<td colspan="3">
  			<s:textarea name="companyUser.modifyreason" cssClass="zc_textarea" verify="修改原因|NotNull" id="modifyreason" maxlength="4000" rows="5" cols="65"></s:textarea><span style="color:red">*</span>
      			 </td>
      			 	</tr>
  			</s:if>
  				<tr><td colspan="4" style="text-align:center;">
  					<s:if test="isEdit==null">
  	<input type="button" onclick="doAdd()" value="保 存" class="buttonClass_1"/> 
  	</s:if>
  	<s:else>
  	<input type="button" onclick="doEdit()" value="保 存" class="buttonClass_1"/> 
  	</s:else>
  				&nbsp;&nbsp;<input type="reset" value="重 置" class="buttonClass_1"/> &nbsp;&nbsp;
			</td>
			
			 </tr>
		  </table>
		  </td></tr></table>
</s:form>
</body>
</html>
