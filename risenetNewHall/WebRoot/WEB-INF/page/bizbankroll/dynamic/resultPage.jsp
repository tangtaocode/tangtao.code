<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <script type="text/javascript">
		function topage(page){
			searchBuis(page);
  		}
	</script>
  </head>
  <body>
  <s:hidden name="page" id="pageCount"></s:hidden>
					<table cellpadding="0" cellspacing="0" border="0" width="100%"
						class="BS_list">
				<tr>
					<th width="15%" style="text-align: center">
						受理编号
					</th>
					<th width="22%">
						项目名称
					</th>
					<th width="22%">
						申请人/单位
					</th>
					<th width="13%">
						主管单位
					</th>
					<th width="14%">
						申报时间
					</th>
					<th width="14%">
						业务状态
					</th>
				</tr>
						
				<s:if test="#request.pageView.totalRecord">
					<s:iterator value="#request.pageView.records" status="entity">
						<tr style="cursor:hand;" onmouseover="changeTrBg(this,'0')"  onmouseout="changeTrBg(this,'1')">
							<td class="font_right_li_center02" width="15%">
								<s:property value="slbh" />
							</td>
							<td class="font_right_li_left02" width="22%">
								<s:if test="%{pro_name!=null&&pro_name.length()>14}">
									<s:property value="%{pro_name.substring(0,14)}" />...
                  												</s:if>
								<s:else>
									<s:property value="pro_name" />
								</s:else>
							</td>
							<td class="font_right_li_left02" width="22%">
								<s:if test="%{sbztid!=null&&sbztid.length()>14}">
									<s:property value="%{sbztid.substring(0,14)}" />...
                  												</s:if>
								<s:else>
									<s:property value="sbztid" />
								</s:else>
							</td>
							<td class="font_right_li_left02" width="13%">
								<s:property value="zgbmid" />
							</td>
							<td class="font_right_li_center02" width="14%">
								<s:date name="createtime" format="yyyy-MM-dd"/>
							</td>
							<td class="font_right_li_center02" width="14%">
								<s:property value="pressor_state" />
							</td>
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

