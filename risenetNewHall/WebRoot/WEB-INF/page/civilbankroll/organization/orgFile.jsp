<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../../share/taglib.jsp"%>
<html>
<head>

<title>深圳市龙岗区住房与建设局社会建设和民生创新专项资金评估专家申请</title>
<meta http-equiv='Pragma' content='no-cache' />
<meta http-equiv='Cache-Control' content='no-cache' />
<meta http-equiv='Expires' content='0' />
<style type="text/css">
body{ margin: 0;
	  padding: 0; 
	  }
.setpTitle{
	text-align:center;
	font-size:20px;
}
</style>
<link href="/css/appGuide.css" rel="stylesheet" type="text/css"/>
</head>
<body>
         <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="table_leftop">
			<TR>
				<TD width="20%" class="title_center" nowrap="nowrap">申报机构类别：</TD>
			<TD width="80%" class="td_left"   >
				<s:property value="civilOrgFile.organizationtype"/>
			</TD>
		</TR>
		<TR>
				<TD width="20%" class="title_center" nowrap="nowrap">评审任务：</TD>
			<TD width="80%" colspan="3" class="td_left"   >
				<s:property value="civilOrgFile.evaluationtask" escape="false"/>
			</TD>
		</TR>
		<TR>
			<TD width="20%" class="title_center" >申报条件：</TD>
			<TD width="80%" colspan="3" class="td_left">
			<s:property value="civilOrgFile.declarecondition" escape="false"/> </TD>
		</TR>
		<TR>
			<TD width="20%" class="title_center" nowrap="nowrap">网上申报需递交资料：</TD>
			<TD width="80%" colspan="3" class="td_left">
				<s:property value="civilOrgFile.onlinefile" escape="false"/>
				<jsp:include page="/WEB-INF/page/share/upFilePage.jsp"></jsp:include>
			</TD>
		</TR>
		<TR>
			<TD width="20%" class="title_center" nowrap="nowrap">窗口申报需递交资料：</TD>
			<TD width="80%" class="td_left"   >
				<s:property value="civilOrgFile.windowsfile" escape="false"/>
			</TD>
		</TR>
		<TR>
				<TD width="20%" class="title_center" nowrap="nowrap">备注：</TD>
			<TD width="80%" class="td_left"   >
				<s:property value="civilOrgFile.remarks" escape="false"/>
			</TD>
		</TR>
</table>
</body>
</html>
