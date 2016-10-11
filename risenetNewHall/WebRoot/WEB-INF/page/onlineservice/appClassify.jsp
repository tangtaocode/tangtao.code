<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>

  <script type="text/javascript">
  function changeType(){
  	changeOrder('orderCode','img');
  	searchAppByImage($("#approveItemType").val());
  }
  function topage(page){
	searchAppByImage($("#approveItemType").val(),page);
  }
  function backClassify(){
  	 $("#formSubId").submit();
  }
  </script>
  </head>
  
  <body>
  <s:form action="/onlineService/initOnlineService.html" id="formSubId">
  <s:hidden name="twoLevel" id="twoLevel" ></s:hidden>
  <s:hidden name="page" id="pageCount"></s:hidden>
  <s:hidden name="orderType" id="orderType"></s:hidden>
  <s:hidden name="ascOrDesc" id="ascOrDesc"></s:hidden>
  <s:hidden name="approveItemType" id="approveItemType"></s:hidden>
  <div class="BS_dotop1">&nbsp;</div>
  <div class="BS_domiddle">
  <div class="BS_domiddlein">
			<!--depmiddle start-->
			<div class="BS_depm">
				<div style="text-align:left;padding-left:20px;">
				<div class="BS_title2">
				<img src="/images/approveitem<s:property value="itemClassify.imagename"/>.png" width="60" height="60" /> 
					<span class="BS_titletxt"><s:property value="itemClassify.name"/></span>
				</div> 
				<div class="BS_title3">共有<s:property value="#request.pageView.totalRecord"/>个服务事项</div>
				<div class="BS_titleass r"><input onclick="backClassify()" type="button" value="返回" class="backButton"></div>
				<div class="BS_doheader noborder" style="text-align:right;font-size:16px;">
					部门：<s:select cssClass="loginInput" list="departMap" onchange="changeType()"  name="departGUID" id="departGUID"  headerKey="" headerValue="---请选择---" value="departGUID" listKey="code" listValue="value"></s:select> 
					&nbsp;&nbsp;
					<!-- 可在线申报：<s:select cssClass="loginInput" list="#{'':'---请选择---','0':'可在线申报','1':'不可在线申报'}" onchange="changeType()" name="isOnlineApply" id="isOnlineApply"></s:select>
				&nbsp;&nbsp; -->
				事项名称：<s:textfield size="20" name="approveName" id="approveName" cssClass="loginInput"></s:textfield>
			<a id="searchLink" href="javascript:changeType()"><img src="/images/govpublic/tb_see_right.gif" style="border:0px;vertical-align: middle" /> </a>
				</div>
				</div>
				<div class="clean"></div>
				<!--搜索结果 start-->
					<table cellpadding="0" cellspacing="0" border="0" width="100%"
						class="BS_list">
						<tr>
							<th width="50%">
								<a href="javascript:changeOrder('itemName','img');" >服务事项名称</a>
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
								<a href="javascript:changeOrder('departName','img');">主管部门</a>
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
								<s:if test="approveplace==0&&(itemid=='{0A0A017E-FFFF-FFFF-992E-3300FFFFFF8E}'||itemid=='{09A16D48-0000-0000-75BB-8BBD00000D07}')">
									<a target="_blank" href="/onlineService/initOnlineApply/approveItemGUID/<s:property value="itemid"/>.html" class="BS_btnblue" >在线申办</a>
								</s:if>
								<s:if test="approveplace==1&&applicationformurl!=null">
									<a target="_blank" href="<s:property value="applicationformurl"/>" class="BS_btnblue" >在线申办</a>
								</s:if>
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
		</div>
		<div class="BS_dobottom">&nbsp;</div>
		</s:form>
		
  </body>
</html>
