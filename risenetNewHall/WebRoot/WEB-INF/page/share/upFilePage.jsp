<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<body> 
<s:if test="modeType=='bizApply'">
<s:hidden name="guid" id="guid"></s:hidden>
<s:hidden name="id" id="id"></s:hidden>
</s:if>
<table cellpadding="0" cellspacing="0" border="0" width="100%" class="BS_list" style="font-family:微软雅黑;display:">
 <s:iterator value="upFileList" status="inx">
	<tr>
		<td>（<s:property value="#inx.index+1"/>）、<s:property value="fileName" escape="false"/>
			<s:if test="alreadyUpFile!=null&alreadyUpFile.size!=0">
			<table width="98%" cellpadding="0" cellspacing="0" border="0">
				<s:iterator value="alreadyUpFile" status="upIndx">
					<tr><td width="90%" style="border:0px;color:#6688CC;">
						<img width="8px" style="padding-top:6px;" height="8px" src="/images/lineservice/fileli.png">&nbsp;
					<a href="/servlet/DownFileServlet/downType/<s:property value="modeType"/>/fileId/<s:property value="fileGUID"/>.html"><s:property value="fileName"/></a></td>
					<td><a href="#" onclick="deleteFile('<s:property value="fileGUID"/>','<s:property value="modeType"/>')">
						<img border="0" src="/images/lineservice/delete.png" width="16px" height="16px"/></a></td></tr>
				</s:iterator>
			</table>
			</s:if>
		</td> 
		<td>
		<a href="#" onclick="parent.showUpFilePanel('文件上传','<s:property value="url"/>.YS?correctionGUID=<s:property value="fileGUID"/>&modeType=<s:property value="modeType"/>&projectFK=<s:property value="projectFK"/>','<s:property value="modeType"/>');">
			<img src="/images/lineservice/upLine.png" class="imagefile"> </td></a>
		</td>
	</tr>
</s:iterator>
</table>
</body>
</html>