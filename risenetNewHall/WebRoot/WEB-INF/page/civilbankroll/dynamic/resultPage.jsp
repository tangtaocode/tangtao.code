<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../share/taglib.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<script type="text/javascript" src="/js/base.js"></script>
		<link href="/css/pageStyle.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<s:form action="/civilDynamic/resultPage.YS" method="post" id="queryFormId">
			<s:hidden name="page" />
			<s:hidden name="slbh"/>
<s:hidden name="projectName"/>
<s:hidden name="sszt"/>
<s:hidden name="projectType"/>
<s:hidden name="sqsj_S"/>
<s:hidden name="sqsj_E"/>
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				id="resultTableId">
				<tr>
					<th width="15%" style="text-align: center">
						业务编号
					</th>
					<th width="10%">
						项目类别
					</th>
					<th width="20%">
						项目名称
					</th>
					<th width="20%">
						申报单位
					</th>
					<th width="20%">
						实施主体
					</th>
					<th width="15%">
						发布时间
					</th>
				</tr>
				<s:if test="#request.pageView.totalRecord">
					<s:iterator value="#request.pageView.records" status="entity">
						<tr style="cursor:hand;" onmouseover="changeTrBg(this,'0')"  onmouseout="changeTrBg(this,'1')">
							<td class="font_right_li_center02" width="15%">
								<s:property value="slbh" />
							</td>
							<td class="font_right_li_left02" width="10%">
								<s:property value="xmlxname" />
							</td>
							<td class="font_right_li_left02" width="20%">
								<s:if test="%{xmname!=null&&xmname.length()>13}">
																	<s:property value="%{xmname.substring(0,13)}" />...
                  												</s:if>
																<s:else>
																	<s:property value="xmname" />
																</s:else>
							</td>
							<td class="font_right_li_left02" width="20%">
								<s:if test="%{sbztid!=null&&sbztid.length()>13}">
																	<s:property value="%{sbztid.substring(0,13)}" />...
                  												</s:if>
																<s:else>
																	<s:property value="sbztid" />
																</s:else>
							</td>
							<td class="font_right_li_center02" width="20%">
								<s:if test="%{sszt!=null&&sszt.length()>13}">
																	<s:property value="%{sszt.substring(0,13)}" />...
                  												</s:if>
																<s:else>
																	<s:property value="sszt" />
																</s:else>
							</td>
							<td class="font_right_li_center02" width="15%">
								<s:property value="createtimeStr" />
							</td>
						</tr>
					</s:iterator>
				</s:if>
				<s:else>
					<tr>
						<td colspan="6" class="noDateText">
							无记录！
						</td>
					</tr>
				</s:else>
				<tr>
					<td align="center" colspan="5" class="pageLinkTd">
						<%@ include file="/WEB-INF/page/share/page.jsp"%>
					</td>
			</table>
		</s:form>
	</body>