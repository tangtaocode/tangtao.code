<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
<script type="text/javascript">
function topage(page){
	searchAppList($("#departGUID").val(),$("#isOnlineApply").val(),$("#departmentName").val(),page);
}
</script>
	</head>
	<body>
	<s:hidden name="departGUID" id="departGUID"></s:hidden>
	<s:hidden name="module" id="module"></s:hidden>
	<s:hidden name="isOnlineApply" id="isOnlineApply"></s:hidden>
	<s:hidden name="departmentName" id="departmentName"></s:hidden>
	<s:hidden name="page" id="page"></s:hidden>
	<s:hidden name="orderType" id="orderType"></s:hidden>
	<s:hidden name="ascOrDesc" id="ascOrDesc"></s:hidden>
<table cellspacing="0" border="0" class="ListToolbarWebpart" style="showTopBorder =='false'?'border-top: none ':' '" width="100%">
						<tr>
						<td align="left" style="font-size: 12pt; color: #858585;padding-left: 16px;">
							共<s:property value="#request.pageView.totalRecord"/>个办事程序
						</td>
							<td valign="top" align="right" style="height:50px;text-align: right; padding-right: 16px;" >
							<div style=" width:100%" align="right">
								<div style="background-image: url(/images/govpublic/search_bar.png);BACKGROUND-REPEAT:no-repeat; height: 29px; width: 265px">
								<table cellpadding="6" cellspacing="0" style=" width:100%">
									<tr>
										<td align="right">
											<s:textfield id="approveName" name="approveName" cssStyle="width: 223px; color: #000; border: 0px solid #ccc"></s:textfield>
										</td>
										<td align="right">
										<a id="searchLink" href="javascript:searchAppList('<s:property value="departGUID"/>','<s:property value="isOnlineApply"/>','<s:property value="departmentName"/>');void(0);"><img src="/images/govpublic/tb_see_right.gif" style="border:0px;vertical-align: middle" /> </a>
										</td>
									</tr>
								</table>
								</div>
								</div>
							</td>
						</tr>
					</table>
					<div style="text-align:center;">
					<table class="rich-table Grid" border="0" cellpadding="0" cellspacing="0" width="100%" style="LINE-HEIGHT:25px;width:98%">
				<thead class="rich-table-thead">
					<tr class="rich-table-subheader Header">
						<th class="rich-table-subheadercell Header " scope="col" width="60%">
							<a href="javascript:changeOrder('itemName');void(0);">事项名称
							<s:if test="orderType=='itemName'">
							<s:if test="ascOrDesc=='asc'">
								<img src="/images/govpublic/orderUp.png" class="order"/>
							</s:if>
							<s:if test="ascOrDesc=='desc'">
								<img src="/images/govpublic/orderDown.png" class="order"/>
							</s:if>
							</s:if>
							</a></th>
					<th class="rich-table-subheadercell Header " scope="col" style="text-align:center">
							<a href="javascript:changeOrder('itemType');void(0);">事项性质
							<s:if test="orderType=='itemType'">
							<s:if test="ascOrDesc=='asc'">
								<img src="/images/govpublic/orderUp.png" class="order"/>
							</s:if>
							<s:if test="ascOrDesc=='desc'">
								<img src="/images/govpublic/orderDown.png" class="order"/>
							</s:if>
							</s:if>
							</a></th>
					<th style="text-align:center" class="rich-table-subheadercell Header " scope="col" width="14%">
							<a href="#">更新时间</a></th>
					</tr>
				</thead>
				<tbody >
					<s:if test="#request.pageView.totalRecord">
         			<s:iterator value="#request.pageView.records" status="entity">
         			<s:if test="(#entity.Index+1)%2==0"><tr class="rich-table-row rich-table-firstrow OddRow"></s:if>
         			<s:else><tr class="rich-table-row  EvenRow"></s:else>
					 <td>
						 <a href="javascript:loadAppGUID('<s:property value="itemid"/>','<s:property value="bureauName"/>','<s:property value="departGUID"/>')" title="<s:property value="approveitemname"/>"><s:property value="approveitemname"/></a>
					 </td>
					<td style="text-align:center"><s:property value="bureautype"/> </td>
					<td style="text-align:center"><s:date name="handletime" format="yyyy-MM-dd"/> </td>	
						
						</tr>
					
          			</s:iterator>
				</s:if>
				<s:else>
					<tr>
         				<td colspan="3" class="noDateText">无记录！</td></tr>
				</s:else>
				<tr>
	  				<td align="center" colspan="3" class="pageLinkTd">
						<%@ include file="/WEB-INF/page/share/page.jsp"%>
					</td>
				</tr>	
					</tbody>
					</table>
					<div class="listBotton"></div>
					</div>
</body>
</html>
