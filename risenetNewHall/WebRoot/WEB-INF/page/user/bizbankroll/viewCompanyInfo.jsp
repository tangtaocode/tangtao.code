<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<body>
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="BS_list">
<tr> 
	<th colspan="4"><div style="float:left">企业用户基本信息</div><div style="float:right">
	<input type="button" onclick="editUser('bizbankroll')" class="buttonClass_1" value="修改信息"></div></th>
</tr>
           <tr>
  				<td width="18%" style="text-align:right">企业名称：</td>
      			<td colspan="3" width="82%">
      			<s:property value="#session.loginUser.companyUser.ename"/>
      		</td>
  			</tr>
  			<tr>
  				<td width="18%"  style="text-align:right">企业性质：</td>
      			<td width="32%" >
      				<s:property value="#session.loginUser.companyUser.kind"/>
      			</td>
  				<td width="18%"  style="text-align:right">机构代码：</td>
      			<td width="32%" > 
      			<s:property value="#session.loginUser.companyUser.orgcode" />
      			</td>
  			</tr>
  			<tr>
  				<td  style="text-align:right">登记注册号：</td>
      			<td > 
      			<s:property value="#session.loginUser.companyUser.regcode" />
      			
      			</td>
      			<td style="text-align:right">成立时间：</td>
      			<td >
				<s:date name="#session.loginUser.companyUser.regdate" format="yyyy-MM-dd"/>					
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
      			<td style="text-align:right">联系人身份证号：</td>
      			<td> 
      				<s:property  value="#session.loginUser.companyUser.personcardid" />
      			
      			</td>
  				<td style="text-align:right">法定代表人：</td>
      			<td>
      			<s:property  value="#session.loginUser.companyUser.lawperson"  />
      			
      			 </td>
  			</tr>
  			<tr>
				<td style="text-align:right">法人电话：</td>
      			<td>
      			<s:property  value="#session.loginUser.companyUser.lawphone"  />
      			
      			</td>
      			<td style="text-align:right">法人手机：</td>
      			<td>
      			<s:property  value="#session.loginUser.companyUser.lawperphone"/>
      			
      			</td>
  			</tr>
  			<tr>
  				<td style="text-align:right">法人邮箱：</td>
      			<td> 
      				<s:property value="#session.loginUser.companyUser.lawperemail"  />
      			
      			</td>
      			<td style="text-align:right">法人身份证号：</td>
      			<td>
				<s:property value="#session.loginUser.companyUser.lowcardid" />
      			
				</td>
  			</tr>
  			
  			<tr >
  				<td style="text-align:right">传真：</td>
      			<td>
      			<s:property  value="#session.loginUser.companyUser.industry" />
  				</td>
				<td style="text-align:right">税务登记编号：</td>
      			<td> 
      			<s:property value="#session.loginUser.companyUser.taxnum"  />
      			</td>
  			</tr>
  			<tr>
			<td style="text-align:right">国税纳税编码：</td>
      			<td> 
      			<s:property value="#session.loginUser.companyUser.nationaltax"  />
      			</td>
				<td style="text-align:right">地税纳税编码：</td>
      			<td> 
      			<s:property value="#session.loginUser.companyUser.localtax"  /></td>
			</tr>
			<tr>
			<td style="text-align:right">开户银行：</td>
      			<td> 
      			<s:property value="#session.loginUser.companyUser.bankname"  />
      			</td>
				<td style="text-align:right">银行账号：</td>
      			<td> 
      			<s:property value="#session.loginUser.companyUser.banknum"  /></td>
			</tr>
			<tr>
  				<td style="text-align:right">银行账户名称：</td>
      			<td>
      			<s:property value="#session.loginUser.companyUser.accountname"  />
      			</td>
      			<td style="text-align:right">上年末从业人数：</td>
      			<td> 
      			<s:property value="#session.loginUser.companyUser.userCommoney.cyrs"  />人
      			</td>
  			</tr>
  			<tr>
				<td style="text-align:right">上年度纳税总额：</td>
      			<td> 
      			<s:property value="#session.loginUser.companyUser.userCommoney.nsze"  />万元
      			</td>
				<td style="text-align:right">上年度营业收入：</td>
      			<td> 
      			<s:property value="#session.loginUser.companyUser.userCommoney.yysr"  />万元</td>
			</tr>
  			<tr>
			<td style="text-align:right">是否总部企业：</td>
      			<td> 
      			<s:property value="#session.loginUser.companyUser.contactadd"  />
      			</td>
				<td style="text-align:right">是否金融企业：</td>
      			<td> 
				<s:property value="#session.loginUser.companyUser.contactper"  /></td>
			</tr>
  			
  			<tr>
  				<td style="text-align:right">注册地址：</td>
      			<td colspan="3">
      				
      			<s:property value="#session.loginUser.companyUser.address" />
      			</td>
  			</tr>
		  			
  			
  			<tr>
  				<td style="text-align:right">营业地址：</td>
      			<td colspan="3">
      			<s:property value="#session.loginUser.companyUser.openadd" />
      			
      			</td>
  			</tr>
  			<tr>
  				<td style="text-align:right">经营范围：</td>
      			<td colspan="3">
      			<s:property value="#session.loginUser.companyUser.limit" />
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
