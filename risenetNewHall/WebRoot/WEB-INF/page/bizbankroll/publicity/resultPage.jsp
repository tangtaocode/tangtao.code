<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../share/taglib.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<script type="text/javascript" src="/js/base.js"></script>
		<link href="/css/pageStyle.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<s:form action="/bizPublicity/resultPage.YS" method="post"
			id="queryFormId">
			<s:hidden name="page" />
			<s:hidden name="slbh" />
			<s:hidden name="projectName" />
			<s:hidden name="sqdw" />
			<s:hidden name="projectType" />
			<s:hidden name="sqsj_S" />
			<s:hidden name="sqsj_E" />
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				id="resultTableId">
				<tr>
					<th width="15%" style="text-align: center">
						受理编号
					</th>
					<th width="20%">
						项目名称
					</th>
					<th width="22%">
						申请人/单位
					</th>
					<th width="15%">
						主管单位
					</th>
					<th width="14%">
						申报时间
					</th>
					<th width="14%">
						投诉
					</th>
				</tr>
				<s:if test="#request.pageView.totalRecord">
					<s:iterator value="#request.pageView.records" status="entity">
						<tr style="cursor:hand;" onmouseover="changeTrBg(this,'0')"  onmouseout="changeTrBg(this,'1')">
							<td class="font_right_li_center02" width="15%">
								<s:property value="slbh" />
							</td>
							<td class="font_right_li_left02" width="20%">
								<s:if test="%{pro_name!=null&&pro_name.length()>14}">
									<s:property value="%{pro_name.substring(0,14)}" />...
                  												</s:if>
								<s:else>
									<s:property value="pro_name" />
								</s:else>
							</td>
							<td class="font_right_li_left02" width="20%">
								<s:if test="%{sbztid!=null&&sbztid.length()>14}">
									<s:property value="%{sbztid.substring(0,14)}" />...
                  												</s:if>
								<s:else>
									<s:property value="sbztid" />
								</s:else>
							</td>
							<td class="font_right_li_left02" width="20%">
								<s:property value="zgbmid" />
							</td>
							<td class="font_right_li_center02" width="15%">
								<s:property value="sqsj" />
							</td>
							<td class="font_right_li_center02" width="10%">
								<a href="#"
									onclick="parent.showPage('产业扶持-公示异议','/bizPublicity/initComplain.YS?projectguid=<s:property value="guid" />',750,420)"><img
										style="cursor: hand" src="/images/page/ts.png" /> </a>
							</td>
						</tr>
					</s:iterator>
				</s:if>
				<s:else>
					<tr>
						<td colspan="3" class="noDateText">
							无记录！
						</td>
					</tr>
				</s:else>
				<tr>
					<td align="center" colspan="6" class="pageLinkTd">
						<%@ include file="/WEB-INF/page/share/page.jsp"%>
					</td>
			</table>
		</s:form>
	</body>
	