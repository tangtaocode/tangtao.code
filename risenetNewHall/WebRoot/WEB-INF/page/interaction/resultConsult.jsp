<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>

  <script type="text/javascript">
  function topage(pageNum){
		searchConsult(pageNum);
  }
  </script>
  </head>
  
  <body>
  <s:hidden name="page" id="pageCount"></s:hidden>
  <div class="BS_domiddlein" style="width:950px;">
			<!--depmiddle start-->
			<div class="BS_depm">
					<table cellpadding="0" cellspacing="0" border="0"
						class="BS_list" style="line-height:100%;width:99%">
						<tr>
							<th width="47%" style="text-align: center">
								标题
							</th>
							<th width="10%" style="text-align: center">
								咨询人
							</th>
							<th width="18%" style="text-align: center">
								咨询部门
							</th>
							<th width="13%" style="text-align: center">
								咨询时间
							</th>
							<th width="12%" style="text-align: center">
								回复状态
							</th>
						</tr>
				<s:if test="#request.pageView.totalRecord">
         			<s:iterator value="#request.pageView.records" status="entity">
         			<s:if test="(#entity.Index+1)%2==0"><tr class="odd"></s:if>
         			<s:else><tr class="even"></s:else>
							<td>
								<a href="javascript:findConsult('<s:property value="consultationguid"/>','<s:property value="page"/>')" title="<s:property value="title"/>"><s:property value="title"/></a>
							</td>
							<td style="text-align: center">
								<s:property value="username"/>
							</td>
							<td>
								<s:property value="bureau"/>
							</td>
							<td style="text-align: center">
								<s:date name="consultationdate" format="yyyy-MM-dd"/>
							</td>
							<td style="text-align: center">
								<s:if test="recontent==null">
								未回复
								</s:if>
								<s:else>
								已回复
								</s:else>
							</td>
						</tr>
          			</s:iterator>
				</s:if>
				<s:else>
					<tr>
         				<td colspan="5" class="noDateText">无记录！</td></tr>
				</s:else>
				<tr>
	  				<td align="center" colspan="5" class="pageLinkTd">
						<%@ include file="/WEB-INF/page/share/page.jsp"%>
					</td>
				</tr>		
					</table>
				<div class="clean"></div>
			</div>
		</div>
  </body>
</html>
