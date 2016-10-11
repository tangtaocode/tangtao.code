<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../share/taglib.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="/js/base.js"></script>
<link href="/css/pageStyle.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<s:form action="/civilNotice/resultPage.YS" method="post" id="queryFormId">
<s:hidden name="page"/>
<s:hidden name="title"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0" id="resultTableId">
          <tr>
             	<th width="10%" style="text-align:center" >序号</th>
                 <th width="90%" >政策名称</th>
           </tr>
          <s:if test="#request.pageView.totalRecord">
         <s:iterator value="#request.pageView.records" status="entity">
         <tr style="cursor:hand;" onmouseover="changeTrBg(this,'0')"  onmouseout="changeTrBg(this,'1')">
         <td class="font_right_li_center02"><s:property value="#entity.Index+1"/></td>
         <td class="font_right_li_left02"><a href="/civilNotice/findNotice.YS?guid=<s:property value="id"/>" target="_blank">·
          <s:property value="title"/>      
         </a></td>
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
			</table>
			</s:form>
			</body>

			