<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <link href="/css/zwgk.css" type="text/css" rel="stylesheet" />
  <style type="text/css">
.titleTab {
	background: url("/images/govpublic/bg_btnblue.png") no-repeat 0px 0px;
	overflow: hidden;
	text-align: center;
	vertical-align: middle;
	width: 94px;
	height: 21px;
	cursor: hand;
	color:#FFF;
	font-size:11pt;
}
.titleTabo {
	overflow: hidden;
	text-align: center;
	vertical-align: middle;
	width: 94px;
	height: 21px;
	cursor: hand;
	color:#FFF;
	font-size:11pt;
}
.titleTab a {
	color:#FFF;
	text-decoration:none;
}
.titleTab a:hover {
	color:#FFF;
	text-decoration:none;
}

.titleTabSelected {
	overflow: hidden;
	text-align: center;
	vertical-align: middle;
	background-image: url(/images/govpublic/btn_title_enabled.gif);
	background-position: 0px 0px;
	width: 114px;
	height: 27px;
	cursor:pointer;
	color:white;
	font-size:11pt;
}
a.BS_btnblue:link {
    background: url("/images/govpublic/bg_btnblue.png") no-repeat 0px 0px;
    font: 12px / 21px "宋体";
    width: 94px;
    height: 21px;
    text-align: center;
    color: rgb(255, 255, 255);
    display: block;
    font-size-adjust: none;
    font-stretch: normal;
	vertical-align:middle;
}
</style>
<script type="text/javascript">
	
</script>
  </head>
  
  <body>
  <s:hidden name="page" id="pageCount"></s:hidden>
  <table border="0" cellpadding="0" cellspacing="0" style="width: 100%; heigth: 100%;">
			<tr>
				<td align="center" valign="top" class="Portal_Backgroud">
				<table border="0" cellpadding="0" cellspacing="0" style="width: 970px; heigth: 100%;background-color: #FFFFFF;">
					<tr>
						<td align="left"><!-- begin: 标题面板 -->
						<!-- end: 标题面板 --> <!-- begin: 内容面板 -->
						<div id="Portal_Content">
						
						<table style="width: 960px;" cellpadding="0px" cellspacing="0px" border="0px">
							<tr>
								<td colspan="3"><img src="/images/govpublic/shadow_T.png" /></td>
							</tr>
							<tr>
								<td width="2px" height="300px" style="background-image: url(/images/govpublic/shadow_R.png); background-repeat: repeat-y;">
								</td>
								<td valign="top" style="width: 956px;">
		<table border="0" cellpadding="0" cellspacing="0" style="width: 100%;">
			<tr>
				<td style="padding-left: 16px; ">
	<table border="0" cellpadding="0" style="width: 95%;padding-top:8px">
		<tr>
			<td align="left">
				<span style="font-size: 16pt; color: #858585"><s:property value="departmentName"/> </span>
			</td>
			<td align="right" nowrap="nowrap" width="30%" valign="top">
				<table id="titleTabTable" border="0" cellpadding="0" cellspacing="0" style="width: 380px;">
					<tr>
						<td width="100%">
						</td>
						<td nowrap="nowrap">
								<div class="titleTab" id="publicMenu0" onclick="changeClass('publicMenu',0,3,'titleTab','titleTabo');searchAppList('<s:property value="departGUID"/>','','<s:property value="departmentName"/>')">
									<a href="#">服务事项</a>
								</div>
						</td>
						<td width="2px" nowrap="nowrap"><div style="width:0px;height:20px;border:#ccc solid 1px;"></div></td>
						<td nowrap="nowrap">
								<div class="titleTabo" id="publicMenu1" onclick="changeClass('publicMenu',1,3,'titleTab','titleTabo');findwindows('<s:property value="departGUID"/>');" >
									<a href="#">办事窗口</a>
								</div>
						</td>
						<td width="2px" nowrap="nowrap"><div style="width:0px;height:20px;border:#ccc solid 1px;"></div></td>
						<td nowrap="nowrap">
								<div class="titleTabo" id="publicMenu2" onclick="changeClass('publicMenu',2,3,'titleTab','titleTabo');findLawByDepartGUID('<s:property value="departGUID"/>');">
									<a href="#">办事依据</a>
								</div>
						</td>
						<td width="2px" nowrap="nowrap"><div style="width:0px;height:20px;border:#ccc solid 1px;"></div></td>
						<td nowrap="nowrap">
								<div class="titleTabo">
									<a href="/govpublic/initIndexPage.html">返回</a>
								</div>
						</td>
						
					</tr>
				</table>
			</td>
			
			
		</tr>
	</table>
				</td>
			</tr>
			<tr>
				<td width="100%" valign="top" align="left" id="windowsCount">
					<jsp:include page="/WEB-INF/page/govpublic/result.jsp"></jsp:include>
					
					<div style="height: 16px"></div>
				</td>
			</tr>
		</table>
								 </td>
								<td width="2px" height="300px" style="background-image: url(/images/govpublic/shadow_R.png); background-repeat: repeat-y;">
								</td>
								
							</tr>
							<tr>
								<td colspan="3"><img src="/images/govpublic/shadow_B.png" /></td>
							</tr>
						</table>
						
						</div>
						<!-- end: 内容面板 --></td>

					</tr>
					<tr>
						<td align="left"></td>
					</tr>
		
				</table>

				</td>
			</tr>
		</table>
  </body>
</html>
