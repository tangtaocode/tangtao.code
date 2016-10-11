<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<body> 
<table cellpadding="0" cellspacing="0" border="0" width="100%" class="BS_list" style="font-family:微软雅黑;display:">
 <s:iterator value="upFileList" status="inx" id="upf">
	<tr>
		<td>（<s:property value="#inx.index+1"/>）、<s:property value="fileName" escape="false"/>
			<s:if test="alreadyUpFile!=null&alreadyUpFile.size!=0">
			<table width="98%" cellpadding="0" cellspacing="0" border="0" id="file_table">
				<s:iterator value="alreadyUpFile" status="upIndx">
					<tr id='<s:property value="fileGUID"/>'><td width="90%" style="border:0px;">
						<img width="8px" style="padding-top:6px;" height="8px" src="/images/lineservice/fileli.png" />&nbsp;
					<a href="/servlet/DownFileServlet/downType/<s:property value="modeType"/>/fileId/<s:property value="fileGUID"/>.html"><s:property value="fileName"/></a></td>
					<c:if test="${upf.isPass=='0' }">
					<td style="border:0px;"><a href="#" onclick="deleteFile('<s:property value="fileGUID"/>','<s:property value="modeType"/>')">
						<img border="0" src="/images/lineservice/delete.png" width="16px" height="16px"/></a></td>
						</c:if>
						</tr>
				</s:iterator>
			</table>
			</s:if>
		</td> 
		<td>
		<s:if test="isPass==0">
		<a href="#" onclick="showUpFilePanel('文件上传','<s:property value="url"/>.YS?correctionGUID=<s:property value="fileGUID"/>&modeType=<s:property value="modeType"/>&projectFK=<s:property value="projectFK"/>','<s:property value="modeType"/>');">
			<img src="/images/lineservice/upLine.png" class="imagefile" /> </a>
		</s:if>
		<s:else>
		&nbsp;
		</s:else>
		</td>
		
	</tr>
</s:iterator>
</table>
<div style="text-align:center;width:100%;padding-top:10px;" class="buttonControl">
	<input type="button" value="确 认" class="stepButton" onclick="doSubmit('<s:property value="declaresn"/>')" />
</div>
</body>
</html>