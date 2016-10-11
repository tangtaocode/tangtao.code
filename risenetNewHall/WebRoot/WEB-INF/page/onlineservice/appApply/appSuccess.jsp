<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<body>
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="BS_list">
<tr> 
	<th colspan="4" style="text-align:center">申请信息</th>
</tr>
			<tr>
  				<td width="15%" style="text-align:right">受理编号：</td>
      			<td width="35%">
      			<s:property value="approveinstance.declaresn"/>
      		</td>
      		<td width="15%" style="text-align:right">市统一编号：</td>
      			<td width="35%">&nbsp;
      			<s:property value="approveinstance.sjtybh"/>
      		</td>
           <tr>
  				<td width="18%" style="text-align:right">事项名称：</td>
      			<td colspan="3" width="82%">
      			<s:property value="approveinstance.formname"/>
      		</td>
  			</tr>
  			<tr>
  				<td width="18%" style="text-align:right">申请人/单位：</td>
      			<td colspan="3" width="82%">
      			<s:property value="#session.loginUser.companyUser.ename"/>
      			<s:property value="#session.loginUser.personUser.true_name"/>
      		</td>
  			</tr>
  			<tr>
      			<td style="text-align:right">联系电话：</td>
      			<td>
      			<s:property value="#session.loginUser.mobile"/>
      			</td>
      			<td style="text-align:right">申请时间：</td>
      			<td>
      			<s:date name="approveinstance.submittime" format="yyyy-MM-dd HH:mm:ss"/>
      			</td>
  			</tr>
  			
  			<tr>
  				<td style="text-align:right">已提交材料清单：</td>
      			<td colspan="3"> 
      				<ul>
      				<s:set name="count_y" value="0"/>
      				<s:iterator value="approveinstance.fileList" status="ysc">
      					<s:if test="type==1">
      					<s:set name="count_y" value="#count_y++"/>
      					<li>（<s:property value="#ysc.index+1"/>）<s:property value="materialname"/> </li>
      					</s:if>
      				</s:iterator>
      				<s:if test="#count_y==0">
      				<li></li>
      				</s:if>
      				</ul>
      			</td>
  				
  			</tr>
  			<tr>
  				<td style="text-align:right">需提交材料清单：</td>
      			<td colspan="3">
      				<ul>
      				<s:set name="count_w" value="0"/>
      				<s:iterator value="approveinstance.fileList" status="wsc">
      					<!--<s:if test="type==0">
      					<s:set name="count_w" value="#count_w++"/>
      					<li>（<s:property value="#wsc.index+1"/>）<s:property value="materialname"/> </li>
      					</s:if>-->
      					<s:set name="count_w" value="#count_w++"/>
      					<li>（<s:property value="#wsc.index+1"/>）<s:property value="materialname"/> </li>
      				</s:iterator>
      				<s:if test="#count_w==0">
      					<li></li>
      				</s:if>
      				</ul>
      			</td>
  			</tr>
		  </table>
</body>
</html>
