<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript">
  //function changeType(){
  //	changeOrder('orderCode','app');
  //	searchApp($("#approveType").val(),$("#isOnlineApply").val());
  //}
  function topage(pageNum){
	searchResult(pageNum);
  }
</script>
<style type="text/css">
<!--
-->
</style>
</head>
	<body>
	<s:hidden name="page" id="pageCount"></s:hidden>
  	<s:hidden name="orderType" id="orderType"></s:hidden>
 	<s:hidden name="ascOrDesc" id="ascOrDesc"></s:hidden>
 	<s:hidden name="approveType" id="approveType"></s:hidden>
  <div class="BS_depm">
  		
  		<s:if test="#request.yxtywlsh!=null">
			<div class="searchAppInfo">共查询到<s:property value="#request.pageView.totalRecord"/>笔业务</div>
			<div class="clean"></div>
  		</s:if>
		
		<table cellpadding="0" cellspacing="0" border="0" width="99%"
			class="BS_list">
			<tr>
				<th width="18%" style="text-align: center">
					受理编号
				</th>
				<th width="24%" style="text-align: center">
					承办单位
				</th>
				<th style="text-align: center">
					事项名称
				</th>
				<th width="18%" style="text-align: center">
					申请时间
				</th>
				<th width="12%" style="text-align: center">
					状态
				</th>
			</tr>
				<s:iterator value="busiList" status="busin">
					<s:if test="(#busin.Index+1)%2==0">
						<tr class="odd">
					</s:if>
					<s:else>
						<tr class="even">
					</s:else>
					<td class="font_right_li_center02" style="text-align: center">
						<s:if test="sblsh!=null&&sblsh.length()>0">
							<s:property value="sblsh" />
						</s:if>
						<s:if test="sblsh==null||sblsh.length()<0">
							<s:if test="%{yxtywlsh!=null&&yxtywlsh.length()>12}">
								<s:property value="%{yxtywlsh.substring(0,12)}" />...
	                  		</s:if>
							<s:else>
								<s:property value="yxtywlsh" />
							</s:else>
						</s:if>
					</td>
					<td class="font_right_li_left02">
						<s:property value="sljgmc" />
					</td>
					
					<td class="font_right_li_left02">
						<s:if test="%{spsxmc!=null&&spsxmc.length()>12}">
							<s:property value="%{spsxmc.substring(0,12)}" />...
                  </s:if>
						<s:else>
							<s:property value="spsxmc" />
						</s:else>

					</td>
					<td class="font_right_li_left02" style="text-align:center">
						<s:property value="slsj" />
					</td>
					<td class="font_right_li_center02">
						<s:property value="blzt" />
					</td>
				</s:iterator>
				<tr>
	  				<td align="center" colspan="5" class="pageLinkTd">
						<%@ include file="/WEB-INF/page/share/page.jsp"%>
					</td>
				</tr>
		</table>
	</body>
</html>
