<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <script type="text/javascript">
		function topage(page){
			searchLaw(page);
  		}
	</script>
  </head>
  <body>
  <s:hidden name="page" id="pageCount"></s:hidden>
					<table cellpadding="0" cellspacing="0" border="0" width="100%"
						class="BS_list">
						<tr>
							<th width="8%" style="text-align:center" >序号</th>
			                 <th style="text-align: center">政策名称</th>
			                 <th width="15%" style="text-align: center">发布时间</th>
						</tr>
						
				<s:if test="#request.pageView.totalRecord">
         			<s:iterator value="#request.pageView.records" status="entity">
         			<s:if test="(#entity.Index+1)%2==0"><tr class="odd"></s:if>
         			<s:else><tr class="even"></s:else>
         			<td class="font_right_li_center02">
	         			<s:property value="#entity.index+1"/>
         			</td>
			         <td class="font_right_li_left02">
			            <a href="/bizNotice/findNotice/guid/<s:property value="guid"/>/ntype/1/action.html" target="_blank"><s:property value="title"/> </a>       
			         </td>
         			<td class="font_right_li_center02"><s:date name="createtime" format="yyyy-MM-dd"/> </td>
         		</tr>
          			</s:iterator>
				</s:if>
				<s:else>
					<tr>
         				<td colspan="6" class="noDateText">无记录！</td></tr>
				</s:else>
				<tr>
	  				<td align="center" colspan="6" class="pageLinkTd">
						<%@ include file="/WEB-INF/page/share/page.jsp"%>
					</td>
				</tr>		
					</table>
				
  </body>
</html>
