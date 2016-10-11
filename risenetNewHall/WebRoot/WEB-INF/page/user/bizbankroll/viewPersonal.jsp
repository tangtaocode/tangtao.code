<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<body> 
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="BS_list" >
<tr> 
	<th colspan="4" style="text-align:center"><div style="float:left">个人用户基本信息</div><div style="float:right">
	<input type="button" onclick="editUser('bizbankroll')" class="buttonClass_1" value="修改信息"></div></th>
</tr>
  			<tr>
      			<td style="text-align:right" width="18%">姓名：</td>
      			<td width="32%"> 
		<s:property  value="#session.loginUser.personUser.true_name"  ></s:property>
		
				</td>
  				<td style="text-align:right" width="18%">性别：</td>
      			<td width="32%"> 
      			<s:property value="#session.loginUser.personUser.sex" ></s:property></td>
  			</tr>
  			<tr>
  				<td style="text-align:right">出生日期：</td>
      			<td><s:property value="#session.loginUser.personUser.birth_date"></s:property>
		</td>
  				<td style="text-align:right">民族：</td>
      			<td> <s:property value="#session.loginUser.personUser.nation"></s:property>
		</td>
  			</tr>
  			<tr>
  				<td style="text-align:right">
  				<s:if test="#session.loginUser.cardtype==1">
  				身份证号码：
  				</s:if>
  				<s:if test="#session.loginUser.cardtype==2">
  				护照号码：
  				</s:if>
  				</td>
      			<td colspan="3"> <s:property value="#session.loginUser.personUser.idcard_code" ></s:property>
		</td>
  			</tr>
  			
  			<tr>
      			<td style="text-align:right">手机号码：</td>
      			<td><s:property value="#session.loginUser.personUser.mobile"></s:property>
		</td>
      			<td style="text-align:right">联系电话：</td>
      			<td><s:property value="#session.loginUser.personUser.contact_tel"></s:property>
		</td>
  			</tr>
  			<tr>
  			<td style="text-align:right">工作单位：</td>
      			<td> <s:property value="#session.loginUser.personUser.work_company" ></s:property>
		</td>
  				
  				<td style="text-align:right">学历：</td>
      			<td> 
      			<s:property value="#session.loginUser.personUser.schoolage"></s:property>
  				</td>
  			</tr>
  			<tr>
      			 <td style="text-align:right">年龄：</td>
      			<td> 
				<s:property value="#session.loginUser.personUser.age"></s:property>
				</td>
				<td style="text-align:right">银行账户名称：</td>
      			<td>
      			<s:property value="#session.loginUser.personUser.accountname"></s:property>
      			</td>
		</td>
  			</tr>
  			<td style="text-align:right">开户银行：</td>
      			<td> 
      			<s:property value="#session.loginUser.personUser.bankname"></s:property>
      			</td>
				<td style="text-align:right">银行账号：</td>
      			<td> 
      			<s:property value="#session.loginUser.personUser.banknum"></s:property>
      			</td>
			</tr>
  			
  			<tr>
  				<td style="text-align:right">籍贯：</td>
      			<td width="80%" colspan="3">
      			<s:property value="#session.loginUser.personUser.home_add"></s:property>
		</td>
  			</tr>
  			<tr>
  				<td style="text-align:right">住址：</td>
      			<td width="80%" colspan="3"><s:property value="#session.loginUser.personUser.native_add" escape="false"></s:property>
		</td>
  			</tr>
  			<tr>
  				<td style="text-align:right">户口所在地：</td>
      			<td width="80%" colspan="3"><s:property  value="#session.loginUser.personUser.reg_add"></s:property>
		</td>
  			</tr>
  			
  			
		  </table>
		  <s:hidden name="nextStep" id="nextStep" value="1"></s:hidden>
</body>
</html>
