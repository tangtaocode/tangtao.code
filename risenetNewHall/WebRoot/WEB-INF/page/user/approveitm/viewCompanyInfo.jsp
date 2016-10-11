<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<body>
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="BS_list">
<tr> 
	<th colspan="4"><div style="float:left">企业用户基本信息</div><div style="float:right">
	<input type="button" onclick="editUser('approveItem')" class="buttonClass_1" value="修改信息"></div></th>
</tr>
           <tr>
  				<td width="18%" style="text-align:right">企业名称：</td>
      			<td width="82%" colspan="3">
      				<span id="ename"><s:property value="#session.loginUser.companyUser.ename"/></span>
      			</td>
  			</tr>
  			<tr>
  				<td width="18%"  style="text-align:right">企业性质：</td>
      			<td width="32%" >
      				<s:property value="#session.loginUser.companyUser.kind"/>&nbsp;
      			</td>
  				<td width="18%"  style="text-align:right">机构代码：</td>
      			<td width="32%" > 
      			<s:property value="#session.loginUser.companyUser.orgcode"/>&nbsp;
      			</td>
  			</tr>
  			<tr>
  				<td  style="text-align:right">登记注册号：</td>
      			<td > 
      			<s:property value="#session.loginUser.companyUser.regcode"/>&nbsp;
      			
      			</td>
      			<td style="text-align:right">成立时间：</td>
      			<td >
				<s:date name="#session.loginUser.companyUser.regdate" format="yyyy-MM-dd"/>					
      			</td>
  			</tr>
  			<tr>
  				<td style="text-align:right">传真：</td>
      			<td colspan="3">
      				<s:property  value="#session.loginUser.companyUser.industry"/>&nbsp;
      			</td>
  			</tr>
  			<tr>
  			<td style="text-align:right">联系人姓名：</td>
      			<td>
      			<s:property value="#session.loginUser.companyUser.truename"/>&nbsp;
      			</td>
      			<td style="text-align:right">联系人手机号码：</td>
      			<td>
      			<s:property value="#session.loginUser.companyUser.contactmobile"/>&nbsp;
      			
      			</td>
  			</tr>
  			<tr>
      			<td style="text-align:right">联系人电话：</td>
      			<td>
      			<s:property value="#session.loginUser.companyUser.contactphone"/>&nbsp;
      			
      			</td>
  				<td style="text-align:right">联系人邮箱：</td>
      			<td> 
      			<s:property  value="#session.loginUser.companyUser.email"/>&nbsp;
      			
      			</td>
  			</tr>
  			<tr>
      			<td style="text-align:right">联系人身份证号：</td>
      			<td> 
      				<s:property  value="#session.loginUser.companyUser.personcardid"/>&nbsp;
      			
      			</td>
  				<td style="text-align:right">法定代表人：</td>
      			<td>
      			<s:property  value="#session.loginUser.companyUser.lawperson"/>&nbsp;
      			
      			 </td>
  			</tr>
  			<tr>
  				<td style="text-align:right">注册地址：</td>
      			<td colspan="3">
      				<s:property value="#session.loginUser.companyUser.address"/>&nbsp;
      			</td>
      		</tr>
  			<tr>
  				<td style="text-align:right">所在行政区：</td>
      			<td colspan="3">
      				<s:property value="#session.loginUser.companyUser.administrative"/>&nbsp;
      			</td>
  			</tr>
      		<tr>
  				<td style="text-align:right">邮编：</td>
      			<td> 
      				<s:property value="#session.loginUser.companyUser.postcode"/>&nbsp;
      			</td>
      			<td style="text-align:right">税务登记编号：</td>
      			<td> 
      				<s:property value="#session.loginUser.companyUser.taxnum"/>&nbsp;
      			</td>
  			</tr>
  			<tr>
			<td style="text-align:right">国税纳税编码：</td>
      			<td>
      				<s:property value="#session.loginUser.companyUser.nationaltax"/>&nbsp;
      			</td>
				<td style="text-align:right">地税纳税编码：</td>
      			<td>
      				<s:property value="#session.loginUser.companyUser.localtax"/>&nbsp;
      			</td>
			</tr>
  			<tr>
  				<td style="text-align:right">营业地址：</td>
      			<td colspan="3">
      			<s:property value="#session.loginUser.companyUser.openadd"/>&nbsp;
      			
      			</td>
  			</tr>
  			<tr>
  				<td style="text-align:right">经营范围：</td>
      			<td colspan="3">
      			<s:property value="#session.loginUser.companyUser.limit"/>&nbsp;
      			</td>
  			</tr>
  				<tr>
  				<td style="text-align:right">公司或机构简介：</td>
      			<td colspan="3">
      			<s:property value="#session.loginUser.companyUser.companyabout"/>&nbsp;
      			</td>
  			</tr>
		  </table>
</body>
</html>
