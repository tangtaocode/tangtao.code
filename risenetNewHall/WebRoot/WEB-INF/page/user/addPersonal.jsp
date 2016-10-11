<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <script type="text/javascript" src="/js/validation.js"></script>
 <script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>
 
 
<script type="text/javascript">
	function checkData(type){
		if(!validations("submitForm"))return false;
		if($("#sex").val()==""){
			Dialog.alert("请选择性别");
			return false;
		}
		if($("#schoolage").val()==""){
			Dialog.alert("请选择学历");
			return false;
		}
		if(type=="1"){
			if($("#ifmarry").val()==""){
				Dialog.alert("请选择婚姻状况");
				return false;
			}
		}
		return true;
	}
	function doAddPer(type){
		if(!checkData(type))return false;
		 $("#submitForm").submit();
	}
	function doEditPer(type){
		if(!checkData(type))return false;
		 var data = $("#submitForm").serializeArray(); 
		  $.post("/userService/editPersonUserInfo.html", data,function(data){
		  	Dialog.alert(data.message);
		  },"json");
	}
</script>
</head>
<body> 
<s:form action="#" method="post" id="submitForm" >
<s:hidden name="method" value="add"></s:hidden>
<s:hidden name="isEdit"></s:hidden>
<s:hidden name="personUser.idcard_type"></s:hidden>
<s:hidden name="personUser.user_guid"></s:hidden>
<s:hidden name="personUser.logonguid"></s:hidden>
<s:hidden name="personUser.register_date"></s:hidden>
<table width="99%" cellpadding="0" cellspacing="0">
<tr> 
<s:if test="isEdit==null">
<td class="title">个人用户基本信息</td>
</s:if>
<s:else>
<td class="title1">修改个人用户基本信息</td>
</s:else>
</tr>
<tr><td class="td_left1">
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" id="userInfoTab">
  			<tr>
      			<th>真实姓名：</th>
      			<td> 
		<s:textfield maxlength="50" verify="真实姓名|NotNull"  cssClass="zc_input02" size="20" name="personUser.true_name" id="true_name" ></s:textfield>
		<span style="color:red">*</span>
				</td>
  				<th>性别：</th>
      			<td> 
      			<s:select cssClass="zc_input02" list="#{'':'---请选择---','男':'男','女':'女'}" name="personUser.sex" id="sex" ></s:select><span style="color:red">*</span></td>
  			</tr>
  			<tr>
  			
  				<th>出生日期：</th>
      			<td><s:textfield verify="出生日期|NotNull"  onclick="WdatePicker();" cssClass="zc_input02" size="20" name="personUser.birth_date" id="birth_date" readonly="true"></s:textfield>
		<span style="color:red">*</span></td>
  				<th>民族：</th>
      			<td> <s:textfield maxlength="10" verify="民族|NotNull"  cssClass="zc_input02" size="20" name="personUser.nation" id="nation" ></s:textfield>
		<span style="color:red">*</span></td>
  			</tr>
  			<tr>
  				<th>
  				<s:if test="personUser.idcard_type==1">
  				身份证号码：
  				</s:if>
  				<s:if test="personUser.idcard_type==2">
  				护照号码：
  				</s:if>
  				</th>
      			<td colspan="3"> <s:textfield maxlength="30" readonly="true" verify="证件号码|NotNull"  cssClass="zc_input02" size="50" name="personUser.idcard_code" id="idcard_code" ></s:textfield>
		<span style="color:red">*</span></td>
  			</tr>
  			<tr>
  				<th>居住证号：</th>
      			<td colspan="3"> 
      			<s:textfield maxlength="50"  cssClass="zc_input02"  name="personUser.work_duty" id="work_duty" size="50" ></s:textfield>
      			</td>
  			</tr>
  			<tr>
      			<th>手机号码：</th>
      			<td><s:textfield maxlength="11" onkeyup="restrictNumber(this,'1',11)" verify="手机号码|NotNull|phone" size="20"  cssClass="zc_input02"  name="personUser.mobile" id="mobile" ></s:textfield>
		<span style="color:red">*</span></td>
      			<th>联系电话：</th>
      			<td><s:textfield maxlength="30" verify="联系电话|NotNull|telephone" size="20" cssClass="zc_input02"  name="personUser.contact_tel" id="contact_tel" ></s:textfield>
		<span style="color:red">*</span></td>
  			</tr>
  			<tr>
  			<th>工作单位：</th>
      			<td> <s:textfield maxlength="100"  cssClass="zc_input02" size="20" name="personUser.work_company" id="work_company" ></s:textfield>
		</td>
  				
  				<th>学历：</th>
      			<td> 
      			<s:select cssClass="zc_input02" list="#{'':'---请选择---','博士':'博士','硕士':'硕士','本科':'本科','大专':'大专','高中':'高中','初中':'初中','小学':'小学','其它':'其它'}" name="personUser.schoolage" id="schoolage" ></s:select><span style="color:red">*</span>
  				</td>
  			</tr>
  			<tr>
  				<th>籍贯：</th>
      			<td width="80%" colspan="3">
      			<s:textfield maxlength="100"  cssClass="zc_input02"  verify="籍贯|NotNull" size="70"  name="personUser.home_add" id="home_add" ></s:textfield>
		<span style="color:red">*</span></td>
  			</tr>
  			<tr>
  				<th>住址：</th>
      			<td width="80%" colspan="3"><s:textfield maxlength="200" verify="住址|NotNull" size="70" cssClass="zc_input02" name="personUser.native_add" id="native_add" ></s:textfield>
		<span style="color:red">*</span></td>
  			</tr>
  			<s:if test="userInfo.sxlx==1">
  			<tr>
  				<th>婚姻状况：</th>
      			<td>
      			<s:select list="#{'':'---请选择---','未婚':'未婚','已婚':'已婚','离异':'离异'}" name="personUser.ifmarry" cssClass="zc_input02" id="ifmarry"></s:select>
      			 &nbsp;&nbsp;<span style="color:red">* </span></td>
      			 <th>年龄：</th>
      			<td> <s:textfield maxlength="3" verify="年龄|NotNull" onkeyup="restrictNumber(this,'1',3)" cssClass="zc_input02" size="20" name="personUser.age" id="age" ></s:textfield>
		<span style="color:red">*</span></td>
  			</tr>
  			<tr>
  				<th>职称：</th>
      			<td> <s:textfield maxlength="30"  cssClass="zc_input02" size="20" name="personUser.work_title" id="work_title" ></s:textfield>
		</td>
      			<th>政治面貌：</th>
      			<td> <s:textfield maxlength="10"  cssClass="zc_input02" size="20" name="personUser.polity" id="polity" ></s:textfield>
		</td>
  			</tr>
  			<tr>
  				<th>邮箱：</th>
      			<td> <s:textfield maxlength="50" verify="邮箱|Null|email" cssClass="zc_input02" size="20" name="personUser.email" id="email" ></s:textfield>
		</td>
  				<th>邮编：</th>
      			<td> <s:textfield maxlength="10" onkeyup="restrictNumber(this,'1',10)"  cssClass="zc_input02" size="20" name="personUser.postcode" id="postcode" ></s:textfield>
		</td>
  			</tr>
  			<tr>
  				<th>联系地址：</th>
      			<td width="80%" colspan="3"><s:textfield maxlength="300" verify="联系地址|NotNull" cssClass="zc_input02" size="70" name="personUser.contact_add" id="contact_add" ></s:textfield>
		<span style="color:red">*</span></td>
  			</tr>
  			<tr>
  				<th>户口所在地：</th>
      			<td width="80%" colspan="3"><s:textfield maxlength="100" verify="户口所在地|NotNull"  cssClass="zc_input02" size="70" name="personUser.reg_add" id="reg_add" ></s:textfield>
		<span style="color:red">*</span></td>
  			</tr>
  			<tr>
  				<th>办公电话：</th>
      			<td> <s:textfield maxlength="30" verify="户口所在地|Null|telephone" cssClass="zc_input02" size="20" name="personUser.work_tel" id="work_tel" ></s:textfield>
		</td>
  			</tr>
  			<tr>
  				<th>单位地址：</th>
      			<td width="80%" colspan="3"><s:textfield maxlength="100"  cssClass="zc_input02" size="70" name="personUser.work_add" id="work_add" ></s:textfield>
		 </td>
  			</tr>
  			</s:if>
  			<s:if test="userInfo.sxlx==2">
  			<tr >
  				<th>开户银行：</th>
      			<td><s:textfield maxlength="300" verify="开户银行|NotNull"  cssClass="zc_input02" size="20" name="personUser.bankname" id="bankname" ></s:textfield>
		<span style="color:red">*</span></td>
  				<th>银行账号：</th>
      			<td><s:textfield maxlength="30" verify="银行账号|NotNull" onkeyup="restrictNumber(this,'1',30)" cssClass="zc_input02" size="20" name="personUser.banknum" id="banknum" ></s:textfield>
		<span style="color:red">*</span></td>
  			</tr>
  			<tr >
  				<th>账户名称：</th>
      			<td> <s:textfield maxlength="100" verify="账户名称|NotNull" cssClass="zc_input02" size="20" name="personUser.accountname" id="accountname" ></s:textfield>
		<span style="color:red">*</span></td>
  			</tr>
  			</s:if>
  			<s:if test="personUser.true_name!=null">
  			<tr>
  			<th>修改原因：</th>
  			<td colspan="3">
  				<s:textarea name="personUser.modifyreason" verify="修改原因|NotNull" cssClass="zc_textarea" id="modifyreason" rows="5" cols="70" maxlength="4000"></s:textarea><span style="color:red">*</span>
  			</td>
  			</tr>
  			</s:if>
  	<tr><td colspan="4" style="height:30px;text-align:center;">
  	<s:if test="isEdit==null">
  	<input type="button" onclick="doAddPer('<s:property value="userInfo.sxlx"/>')" value="保 存" class="buttonClass_1"/> 
  	</s:if>
  	<s:else>
  	<input type="button" onclick="doEditPer('<s:property value="userInfo.sxlx"/>')" value="保 存" class="buttonClass_1"/> 
  	</s:else>
  	
			&nbsp;&nbsp;<input type="reset" value="重 置" class="buttonClass_1"/> &nbsp;&nbsp;
			</td> </tr>
		  </table>
         </td></tr></table>
</s:form>

</body>
</html>
