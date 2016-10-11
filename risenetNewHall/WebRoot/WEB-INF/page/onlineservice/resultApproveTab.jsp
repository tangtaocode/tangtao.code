<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<script type="text/javascript">
		function topage(page){
			searchTable(page);
  		}
	</script>
  </head>
  <body>
  <s:hidden name="page" id="pageCount"></s:hidden>
  <div class="BS_domiddlein">
			<!--depmiddle start-->
			<div class="BS_depm">
			<div class="searchAppInfo">共查询到<s:property value="#request.pageView.totalRecord"/>个表格</div>
			<div class="searchAppPara">事项名称：<s:textfield size="40" onkeypress="if(isSearch()){searchTable();}" name="approveName" id="tabAppName" cssClass="loginInput"></s:textfield>
			<a id="searchLink" href="javascript:searchTable()"><img src="/images/govpublic/tb_see_right.gif" style="border:0px;vertical-align: middle" /> </a>
			 </div>
				<div class="clean"></div>
				<!--搜索结果 start-->
				<div class="">
					<table cellpadding="0" cellspacing="0" border="0" width="100%"
						class="BS_list">
						<tr>
							<th width="50%">
								服务事项名称
							</th>
							<th width="50%">
								表格名称
							</th>
							
						</tr>
						
				<s:if test="#request.pageView.totalRecord">
         			<s:iterator value="#request.pageView.records" status="entity">
         			<s:if test="(#entity.Index+1)%2==0"><tr class="odd"></s:if>
         			<s:else><tr class="even"></s:else>
							<td>
								<a href="/approve/findAppGuideInfo/approveItemGUID/<s:property value="itemid"/>.html" target="_blank" title="<s:property value="approveitemname"/>"><s:property value="approveitemname"/></a>
								</td>
							<td>
								<a href="/servlet/DownFileServlet/downType/approveItemFileTemp/fileId/<s:property value="attachmentguid"/>.html" target="_blank" title="<s:property value="file_name"/>"><s:property value="file_name"/></a>		
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
				</div>
				
				<div class="clean"></div>
			</div>
			<!--depmiddle end-->
		</div>
		<!--middlein end-->
  </body>
</html>
