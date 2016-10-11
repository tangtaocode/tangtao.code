<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
<style type="text/css">
.panelHead {
	color: #008000;
	font-size: 18px;
	height: 40px;
}
.panelSid{
	width: 100%; 
	text-align: center; 
	vertical-align: top;
	 height: 100%;
}
.mapEnble{
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
.mapDisenble{
	background: url("/images/govpublic/bg_btndark.png") no-repeat 0px 0px;
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
.subjectContentPanelBg {
	background-color: #F5F5FE;
	width: 900px;
	padding:10px;
	margin-left:auto;
	margin-right:auto;
}

.webpart_head_td_split {
	height: 39px;
	padding: 0px;
	padding-left: 15px;
	padding-right: 10px;
	margin: 0px;
	background-image: url(../../images/portal/webpart_td_split.gif);
	background-repeat: no-repeat;
	background-repeat: repeat-x;
	background-position: bottom;
}

.dataHeadSearchTd {
	padding-left: 10px;
	color: #919191;
	font-size: 14px;
}
.tableLeftPadding0{
	line-height:35px;
}
		
</style>
	</head>
	<body>
		<div class="panelSid">
				<!-- 写两个div为了兼容浏览器 -->
				<div id="subjectContentBgDiv" class="subjectContentPanelBg">
					<table cellspacing="0px" cellpadding="0px" border="0px" width="100%">
						<tr>
							<td colspan="2" class="panelHead">单位信息<img alt="" class="rich-spacer " height="1" src="/images/share/spacer.gif" width="48px" />
								<span style="color:gray;font-size: 14px;">更新时间：</span>
								<span style="color:#000000;font-size: 14px;"><s:date name="bureauBean.updatetime" format="yyyy-MM-dd"/> </span></td>
						</tr>
						<tr>
							<td colspan="2">
							<table class="tableLeftPadding0" cellspacing="0px" cellpadding="0px" border="0px" width="100%">
								<tr>
									<td width="80" align="right" nowrap="nowrap" valign="top" class="dataHeadSearchTd">单位地址： </td>
									<td width="360px" align="left" valign="top">
									<div style="white-space: normal; word-break: break-all; width: 360px">
									<s:property value="bureauBean.bureauaddress"/>
									</div>
									</td>
									<td width="80" align="right" nowrap="nowrap" valign="top" class="dataHeadSearchTd">咨询电话：</td>
									<td width="360px" align="left" valign="top">
									<div style="white-space: normal; word-break: break-all; width: 360px">
									<s:property value="bureauBean.bureauconsultingtel"/>
									</div>
									</td>
								</tr>
							</table>
							</td>
						</tr>
	
						<tr>
							<td class="panelHead" colspan="2">办事窗口</td>
						</tr>
						<tr>
							<td colspan="2">
								<table class="tableLeftPadding0" cellspacing="0px" cellpadding="0px" border="0px" width="100%">
									<s:iterator value="windList" status="win">
									<tr>
										<td colspan="2" nowrap="nowrap" valign="top" style="padding-top: 8px; padding-bottom: 8px">
										<div style="white-space: nowrap; word-break: break-all;text-overflow:ellipsis;overflow:hidden;">
										<span style="color: #E18B46; font-size: 16px;"><s:property value="name"/></span>
										<img alt="" class="rich-spacer " height="1" src="/images/share/spacer.gif" width="24px" />
											<s:if test="maptype==1">		
											<a href="javascript:showMap('map<s:property value="#win.index+1"/>');void(0)" class="mapEnble">查看地图</a>
											</s:if>
											<s:if test="maptype==2">
												<a href="<s:property value="maplinks"/>" target="_blank" class="mapEnble">查看地图</a>
											</s:if>
											<img alt="" class="rich-spacer " height="1" src="/images/share/spacer.gif" width="24px" />
											<span style="color:gray;font-size: 14px;">更新时间：</span>
											<span style="color:#000000;font-size: 14px;"><s:date name="createtime" format="yyyy-MM-dd"/> </span></div>
										</td>
									</tr>
									<tr>
										<td colspan="2">
										<table cellspacing="0px"  class="tableLeftPadding0" cellpadding="0px" border="0px" width="100%" >
											<tr>
												<td width="80" align="right" nowrap="nowrap" valign="top" class="dataHeadSearchTd">地址：</td>
												<td width="360px" align="left" valign="top">
												<div style="white-space: normal; word-break: break-all; width: 360px">
												<s:property value="address"/></div>
												</td>
												<td width="80" align="right" nowrap="nowrap" valign="top" class="dataHeadSearchTd">
													联系电话：</td>
												<td width="360px" align="left" valign="top">
												<div style="white-space: normal; word-break: break-all; width: 360px"><s:property value="phone"/></div>
												</td>
											</tr>
											<tr>
												<td width="80" align="right" nowrap="nowrap" valign="top" class="dataHeadSearchTd">
													办公时间：</td>
												<td colspan="3" align="left" valign="top">
												<div style="white-space: normal; word-break: break-all;width:820px;">
												<s:property value="officehours"/>
												</div>
												</td>
											</tr>
											<tr>
												<td width="80px" align="right" nowrap="nowrap" valign="top" class="dataHeadSearchTd">
													交通指引：</td>
												<td  colspan="3" align="left" valign="top">
												<div style="white-space: normal; word-break: break-all;"> 
												<s:property value="trafficguide"/>
												</div>
												</td>
											</tr>
										</table>
										</td>
									</tr>
									
									<tr>
										<td colspan="2" align="left">
										<s:if test="mapname!=null">	
										<div style="display:none" id="map<s:property value="#win.index+1"/>"><img src="/servlet/DownFileServlet/downType/windMap/fileId/<s:property value="guid"/>.html"/> </div>
										</s:if>
											<hr width="893px" style="border-top: #ccc 1px dashed; height: 0px" />
										</td>
									</tr>
									</s:iterator>
								</table></td>
						</tr>
	
					</table>
				</div>
				</div>
		
	</body>
</html>

