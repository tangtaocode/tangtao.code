<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<body>
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="BS_list">
<tr> 
	<th colspan="4"><div style="float:left">单位基本信息</div><div style="float:right">
	<input type="button" onclick="editUser('bizbankroll')" class="buttonClass_1" value="修改信息"></div></th>
</tr>
           <tr>
  				<td width="18%" style="text-align:right">单位名称：</td>
      			<td colspan="3" width="82%">
      			<s:property value="#session.loginUser.companyUser.ename"/>
      		</td>
  			</tr>
  			<tr>
  				<td width="18%"  style="text-align:right">单位性质：</td>
      			<td width="32%" >
      				<s:property value="#session.loginUser.companyUser.kind"/>
      			</td>
  				<td width="18%"  style="text-align:right">机构代码：</td>
      			<td width="32%" > 
      			<s:property value="#session.loginUser.companyUser.orgcode" />
      			</td>
  			</tr>
  			<tr>
  				
      			<td style="text-align:right">成立时间：</td>
      			<td >
				<s:date name="#session.loginUser.companyUser.regdate" format="yyyy-MM-dd"/>					
      			</td>
      			<td style="text-align:right">传真：</td>
      			<td>
      			<s:property  value="#session.loginUser.companyUser.industry" />
      			</td>
  			</tr>
  			<tr>
  			<td style="text-align:right">联系人姓名：</td>
      			<td>
      			<s:property value="#session.loginUser.companyUser.truename"  />
      			</td>
      			<td style="text-align:right">联系人手机号码：</td>
      			<td>
      			<s:property value="#session.loginUser.companyUser.contactmobile" />
      			
      			</td>
  			</tr>
  			<tr>
      			<td style="text-align:right">联系人电话：</td>
      			<td>
      			<s:property value="#session.loginUser.companyUser.contactphone"  />
      			
      			</td>
  				<td style="text-align:right">联系人邮箱：</td>
      			<td> 
      			<s:property  value="#session.loginUser.companyUser.email" />
      			
      			</td>
  			</tr>
  			<tr>
      			 <td style="text-align:right">邮编：</td>
      			<td > 
      			<s:property value="#session.loginUser.companyUser.postcode" />
      			</td>
				<td style="text-align:right">银行账户名称：</td>
      			<td>
      			<s:property value="#session.loginUser.companyUser.accountname"></s:property>
      			</td>
		</td>
  			</tr>
  			<td style="text-align:right">开户银行：</td>
      			<td> 
      			<s:property value="#session.loginUser.companyUser.bankname"></s:property>
      			</td>
				<td style="text-align:right">银行账号：</td>
      			<td> 
      			<s:property value="#session.loginUser.companyUser.banknum"></s:property>
      			</td>
			</tr>
  			<tr>
  				<td style="text-align:right">联系地址：</td>
      			<td colspan="3">
      			<s:property value="#session.loginUser.companyUser.address" />
      			</td>
  			</tr>
  			<tr>
  				<td style="text-align:right">公司或机构简介：</td>
      			<td colspan="3">
      			<s:property value="#session.loginUser.companyUser.companyabout" />
      			</td>
  			</tr>
		  </table>
</body>
</html>
