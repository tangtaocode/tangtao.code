<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>

  <script type="text/javascript">
  function changeType(){
  	changeOrder('orderCode','app');
  	searchApp($("#approveType").val(),$("#isOnlineApply").val());
  }
  function topage(page){
	searchApp($("#approveType").val(),$("#isOnlineApply").val(),page);
  }
  </script>
  </head>
  
  <body>
  <s:hidden name="page" id="pageCount"></s:hidden>
  <s:hidden name="orderType" id="orderType"></s:hidden>
  <s:hidden name="ascOrDesc" id="ascOrDesc"></s:hidden>
  <s:hidden name="approveType" id="approveType"></s:hidden>
	
  <div class="BS_domiddlein">
			<!--depmiddle start-->
			<div class="BS_depm">
				<s:if test="0==isOnlineApply">
					<div class="searchAppInfo">共有<s:property value="#request.pageView.totalRecord"/>个可网上申报的服务事项</div>
				</s:if>
				<s:else>
					<div class="searchAppInfo">共有<s:property value="#request.pageView.totalRecord"/>个服务事项</div>
				</s:else>
				<div class="searchAppPara">
					部门：<s:select cssClass="loginInput" list="departMap" listKey="code" listValue="value" onchange="changeType()" name="departGUID" id="departGUID"  headerKey="" headerValue="---请选择---" value="departGUID"></s:select> 
					&nbsp;&nbsp;
					<!-- 可在线申报：<s:select cssClass="loginInput" list="#{'':'---请选择---','0':'可在线申报','1':'不可在线申报'}" onchange="changeType()" name="isOnlineApply" id="isOnlineApply"></s:select> -->
				</div>
				<div class="clean"></div>
				<!--搜索结果 start-->
					<table cellpadding="0" cellspacing="0" border="0" width="100%"
						class="BS_list" style="line-height:100%">
						<tr>
							<th width="50%">
								<a href="javascript:changeOrder('itemName','app');"><font style="font-weight:bold;">服务事项名称</font></a>
								<s:if test="orderType=='itemName'">
							<s:if test="ascOrDesc=='asc'">
								<img src="/images/govpublic/orderUp.png" class="order"/>
							</s:if>
							<s:if test="ascOrDesc=='desc'">
								<img src="/images/govpublic/orderDown.png" class="order"/>
							</s:if>
							</s:if>
							</th>
							<th>
								<a href="javascript:changeOrder('departName','app');"><font style="font-weight:bold;">主管部门</font></a>
								<s:if test="orderType=='departName'">
							<s:if test="ascOrDesc=='asc'">
								<img src="/images/govpublic/orderUp.png" class="order"/>
							</s:if>
							<s:if test="ascOrDesc=='desc'">
								<img src="/images/govpublic/orderDown.png" class="order"/>
							</s:if>
							</s:if>
							</th>
							
							<th width="14%">
								网上申报
							</th>
						</tr>
				<s:if test="#request.pageView.totalRecord">
         			<s:iterator value="#request.pageView.records" status="entity">
         			<s:if test="(#entity.Index+1)%2==0"><tr class="odd"></s:if>
         			<s:else><tr class="even"></s:else>
							<td>
								<a href="/approve/findAppGuideInfo/approveItemGUID/<s:property value="itemid"/>.html" target="_blank" title="<s:property value="approveitemname"/>"><s:property value="approveitemname"/></a>									</td>
							<td>
								<s:property value="bureauName"/>&nbsp;
							</td>
							<td>
								<s:if test="approveplace==1&&applicationformurl==null">
								<a target="_blank" href="/onlineService/initOnlineApply/approveItemGUID/<s:property value="itemid"/>.html" class="BS_btnblue" >在线申办</a>
								</s:if>
								<s:elseif test="approveplace==1&&applicationformurl!=null">
									<a target="_blank" href="<s:property value="applicationformurl"/>" class="BS_btnblue" >在线申办</a>
								</s:elseif>
								<s:else>
									&nbsp;
								</s:else>
							</td>
						</tr>
          			</s:iterator>
				</s:if>
				<s:else>
					<tr>
         				<td colspan="3" class="noDateText">无记录！</td></tr>
				</s:else>
				<tr>
	  				<td align="center" colspan="5" class="pageLinkTd">
						<%@ include file="/WEB-INF/page/share/page.jsp"%>
					</td>
				</tr>		
					</table>
				<div class="clean"></div>
			</div>
			<!--depmiddle end-->
		</div>
		<!--middlein end-->
  </body>
</html>
