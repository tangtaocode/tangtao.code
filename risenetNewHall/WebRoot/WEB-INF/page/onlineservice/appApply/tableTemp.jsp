<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>
<body>
				<table cellpadding="0" cellspacing="0" border="0" width="100%" id="cardTab1" class="BS_list" style="font-family:微软雅黑;">
						<tr>
							<th>
								文件名称
							</th>
							<th width="14%" style="text-align:center;">
								下载
							</th>
						</tr>
						<s:iterator value="appTabTempList" status="fileStat">
							<tr><td>
									<s:property value="file_name" />
									</td>
								<td style="text-align:center;"><a href="/servlet/DownFileServlet/downType/approveItemFileTemp/fileId/<s:property value="attachmentguid"/>.html"><img src="/images/lineservice/downLine.png" class="imagefile"></a>  </td>
							</tr>
						</s:iterator>
						<s:if test="appTabTempList.size==0||appTabTempList==null">
							<tr><td colspan="2" style="font-size:18px;text-align:center;">无相关表格模板</td>
							</tr>
						</s:if>
						
				</table>
				
</body>
</html>
