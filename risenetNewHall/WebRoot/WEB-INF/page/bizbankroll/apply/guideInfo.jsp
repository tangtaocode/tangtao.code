<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		
	</head>
	<body>
		<div style="text-align: left">
			<!-- 写两个div为了兼容浏览器 -->
			<div id="subjectContentBgDiv" class="subjectContentPanelBg"
				style="padding: 10px; width: 98%;">
				<table class="tableLeftPadding10" cellspacing="0px"
					cellpadding="0px" border="0px" style="width: 98%;">
					<tr>
						<td class="panelHead1" style="padding-top: 0px">
							扶持类型
						</td>
					</tr>
					<tr>
						<td>
							<div class="countFont1">
								<s:property value="projectTypeInfo.typePathName" />
							</div>
						</td>
					</tr>
					<tr>
						<td class="panelHead1" style="padding-top: 0px">
							主管单位
						</td>
					</tr>
					<tr>
						<td>
							<div class="countFont1">
								<s:property value="projectTypeInfo.departName" />
							</div>
						</td>
					</tr>
					<tr>
						<td class="panelHead1" style="padding-top: 0px">
							扶持细则条款
						</td>
					</tr>
					<tr>
						<td>
							<div class="countFont1">
								<s:property value="projectTypeInfo.xztkcontent"
									escape="false" />
							</div>
						</td>
					</tr>
					<tr>
						<td class="panelHead1" style="padding-top: 0px">
							需提交材料清单
						</td>
					</tr>
					<tr>
						<td>
							<div class="countFont1">
								<table width="100%" border="0" cellspacing="0" cellpadding="0" >
									<s:iterator value="projectTypeInfo.upFileList" status="stat">
										<tr>
											<td class="tdfont">
												（
												<s:property value="#stat.index+1" />
												）、
												<s:property value="fileName" escape="false" />
											</td>
										</tr>
									</s:iterator>
								</table>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>

	</body>
</html>



