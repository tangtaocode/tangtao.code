<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <link href="/css/zwgk.css" type="text/css" rel="stylesheet" />
  <link href="/css/portal.css" type="text/css" rel="stylesheet" />
  <style type="text/css">
.titleTab {
	overflow: hidden;
	text-align: center;
	vertical-align: middle;
	background-position: 0px 0px;
	width: 114px;
	height: 27px;
	cursor: hand;
	color:black;
	font-size:11pt;
}

.titleTab:hover {
	overflow: hidden;
	text-align: center;
	vertical-align: middle;
	background-position: 0px 0px;
	width: 114px;
	height: 27px;
	cursor:pointer;
	color:red;
	font-size:11pt;
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

</style>

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
				<span style="font-size: 16pt; color: #858585"><s:property value="linkInfo"/> </span>
			</td>
		</tr>
	</table>
				</td>
			</tr>
			<tr>
				<td width="100%" valign="top" align="left">
					<table cellspacing="0" border="0" class="ListToolbarWebpart" style="showTopBorder =='false'?'border-top: none ':' '" width="100%">
						<tr>
						<td align="left" style="font-size: 12pt; color: #858585;padding-left: 16px;">
							共<s:property value="appCount"/>个办事程序
						</td>
							<td valign="top" align="right" style="height:50px;text-align: right; padding-right: 16px;">
							<div style=" width:100%" align="right">
								<div style="background-image: url(/images/govpublic/search_bar.png); height: 29px; width: 265px">
								<table cellpadding="6" cellspacing="0" style=" width:100%">
									<tr>
										<td align="right">
										<input id="searchKey" value="" name="" style="width: 223px; color: #000; border: 0px solid #ccc" /></td>
										<td align="right">
										<a id="searchLink" href="#"><img src="/images/govpublic/tb_see_right.gif" style="border:0px;vertical-align: middle" /> </a>
										</td>
									</tr>
								</table>
								</div>
								</div>
							</td>
						</tr>
					</table>
					<div style="text-align:center;">
					<table class="rich-table Grid" border="0" cellpadding="0" cellspacing="0" width="100%" style="LINE-HEIGHT:25px;width:98%">
				<thead class="rich-table-thead">
					<tr class="rich-table-subheader Header">
						<th class="rich-table-subheadercell Header " scope="col">
							<a href="#">服务事项名称</a></th>
						<th class="rich-table-subheadercell Header " scope="col">
							<a href="#">主管部门</a></th>
						<th class="rich-table-subheadercell Header " scope="col">
							<a href="#">网上申报</a></th>
					</tr>
				</thead>
				<tbody >
					<s:if test="#request.pageView.totalRecord">
         			<s:iterator value="#request.pageView.records" status="entity">
         			<s:if test="(#entity.Index+1)%2==0"><tr class="rich-table-row rich-table-firstrow OddRow"></s:if>
         			<s:else><tr class="rich-table-row  EvenRow"></s:else>
							<td>
								<a href="javascript:loadAppGUID('<s:property value="approveitemguid"/>','<s:property value="streetApp"/>')" title="<s:property value="approveitemname"/>"><s:property value="approveitemname"/></a></td>
							
							<td><s:property value="bureauname"/> </td>
							<td style="vertical-align:bottom;">
								<s:if test="haveForm!=null">
								<a href="#" class="BS_btnblue" >在线申办</a>
								</s:if>
								&nbsp;
							</td>
						</tr>
          			</s:iterator>
				</s:if>
				<s:else>
					<tr>
         				<td colspan="3" class="noDateText" >无记录！</td></tr>
				</s:else>
				<tr>
	  				<td align="center" colspan="3" class="pageLinkTd">
						<%@ include file="/WEB-INF/page/share/page.jsp"%>
					</td>
				</tr>	
					</tbody>
					</table>
					<div class="listBotton"></div>
					</div>
					
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
