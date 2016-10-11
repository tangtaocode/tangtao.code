<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>深圳市龙岗区住房与建设局行政服务大厅--办事指南</title>
		<meta http-equiv="keywords" content="办事指南,服务大厅" />
		<meta http-equiv="description" content="办事指南" />
		
		<style type="text/css">
		body{
			font-family: "Microsoft YaHei", "΢微软雅黑", "Segoe UI", Arial;
		}
		table{
			line-height:25px;
			font-size:15px;
		}
		.countFont{
			white-space: normal; 
			word-break: break-all; 
			width: auto;
			font-size:15px;
		}
.panelHead {
	color: #008000;
	font-size: 18px;
	height: 30px;
	padding-top: 18px;
}

.subjectContentPanelBg {
	background-color: #F5F5FE;
}
</style>
	</head>
	<body>
		<table border="0" cellpadding="0" cellspacing="0"
			style="width: 100%; heigth: 100%;">
			<tr>
				<td align="center" valign="top" class="Portal_Backgroud">
					<table border="0" cellpadding="0" cellspacing="0"
						style="heigth: 100%; background-color: #FFFFFF;">
						<tr>
							<td align="left">
								<!-- begin: 标题面板 -->
					<!-- end: 标题面板 -->
								<!-- begin: 内容面板 -->
<table style="width: 940px;" cellpadding="0px" cellspacing="0px" border="0px">
							
							<tr>
								
								<td valign="top" style="width: 956px;">
									<table cellpadding="0px" cellspacing="0px" border="0px">
										<tr>
											<td valign="top">
												<table border="0" cellpadding="0" cellspacing="0"
													style="width: 100%;">
													<tr>
														<td colspan="3">
															<!-- begin: 名称 -->
																<table cellspacing="8" cellpadding="2" border="0" width="100%">
																	<tr>
																		<td align="left" nowrap="nowrap"
																			style="padding-left: 0px;"
																			valign="top">
																			<span
																				style="white-space:normal; word-break: break-all; width:500px;font-size:20px;font-weight:bold">
																				<s:property value="approveItem.approveitemname" />
																			</span>
																		</td>
																		<td width="15%">
																			&nbsp;
																		</td>
																		
																	</tr>
																</table> 
															<!-- end: 左边 -->
														</td>
													</tr>
													<tr>
														<td width="10px" nowrap="nowrap">
															<span style="width: 10px"></span>
														</td>
														<td width="856px" nowrap="nowrap" valign="top"
															align="center">
															<!-- begin: 右边内容 -->
															<span
																style="width: 100%; text-align: left; vertical-align: top; padding-right: 18px; padding-top: 0px; height: 100%">
																<!-- 写两个span为了兼容浏览器 -->
																<span id="subjectContentBgspan"
																	class="subjectContentPanelBg"
																	style="width: 906px; padding: 10px">
																	<table class="tableLeftPadding10" cellspacing="0px"
																		cellpadding="0px" border="0px" width="900px">
																		
																		<tr>
																			<td class="panelHead" style="padding-top: 0px">
																				办理对象
																			</td>
																		</tr>
																		<tr>
																			<td>
																				<span class="countFont">
																					<s:property value="approveItem.itemProvince.serviceobject" />
																				</span>
																			</td>
																		</tr>
																		<tr>
																			<td class="panelHead" style="padding-top: 0px">
																				办理条件
																			</td>
																		</tr>
																		<tr>
																			<td>
																				<span class="countFont">
																					<s:property value="approveItem.declarecondition" escape="false"/>
																				</span>
																			</td>
																		</tr>
																		<tr>
																			<td class="panelHead" style="padding-top: 0px">
																				所需材料
																			</td>
																		</tr>
																		<tr>
																			<td>
																				<span class="countFont">
																					<table width="100%" border="0" cellspacing="0"
																						cellpadding="0">
																						<s:iterator value="approveItem.guideFilTypeeList"
																								status="typeStat">
																								<tr>
																									<td style="font-weight: bold;color:#E18B46">
																										<s:property value="typename" />
																									</td>
																								</tr>
																								<s:iterator value="guideFileList"
																									status="fileStat">
																									<tr>
																										<td>
																											(
																											<s:property value="#fileStat.index+1" />
																											)、
																											<s:property value="materialname" escape="false" />
																											<s:if test="describe!=null">
																											<div style="padding-left:40px;"><font style="color:#C2C2C2;">（说明：<s:property value="describe" escape="false"/>） </font></div>
																											</s:if>
																										</td>
																									</tr>
																								</s:iterator>
																							</s:iterator>
																							
																							<s:iterator value="approveItem.guideFileList"
																								status="typeStat">
																								<tr>
																									<td>
																										(
																										<s:property value="#typeStat.index+1" />
																										)、
																										<s:property value="materialname" escape="false" />
																										<s:if test="describe!=null">
																											<div style="padding-left:40px;"><font style="color:#C2C2C2;">（说明：<s:property value="describe" escape="false"/>） </font></div>
																											</s:if>
																									</td>
																								</tr>
																							</s:iterator>
																					</table>
																					<s:if test="approveItem.guideFilTypeeList.size==0&&approveItem.guideFileList.size==0">
																					无
																					</s:if>
																				</span>
																			</td>
																		</tr>
																		<tr>
																			<td class="panelHead" style="padding-top: 0px">
																				窗口办理流程
																			</td>
																		</tr>
																		<tr>
																			<td>
																				<span class="countFont">
																					<s:if test="approveItem.windowFlow>0">
																						<a class="BS_btnblue"
																									href="/servlet/DownFileServlet/downType/approveItemWindow/fileId/<s:property value="approveItem.itemid"/>.html" >点击下载流程图
																								</a>
																					</s:if>
																					<s:property value="approveItem.itemProvince.windowworkflow" escape="false"/>
																					<s:if test="approveItem.windowFlow.length==0&&approveItem.itemProvince.windowworkflow==null">
																					无
																					</s:if>
																				</span>
																			</td>
																		</tr>
																		<tr>
																			<td class="panelHead" style="padding-top: 0px">
																				网上申报流程
																			</td>
																		</tr>
																		<tr>
																			<td>
																				<span class="countFont">
																					<s:if test="approveItem.declarepic.length>0">
																						<a class="BS_btnblue"
																									href="/servlet/DownFileServlet?downType=approveItemGuide&fileId=${approveItem.itemid}" >点击下载流程图
																								</a>
																					</s:if>
																					<s:property value="approveItem.declaredesc" escape="false"/>
																					<s:if test="approveItem.declarepic.length==0&&approveItem.declaredesc==null">
																					无
																					</s:if>
																				</span>
																			</td>
																		</tr>
																		<tr>
																			<td class="panelHead" style="padding-top: 0px">
																				办理时限
																			</td>
																		</tr>
																		<tr>
																			<td>
																				<span class="countFont">
																					法定时限：申请材料齐全、符合法定形式之日起
																					<s:property value="approveItem.timelimit" />
																					个工作日内<br/> 
																					承诺时限：申请材料齐全、符合法定形式之日起
																					<s:property value="approveItem.promiselimittime" />
																					个工作日内
																				</span>
																			</td>
																		</tr>
																		<tr>
																			<td class="panelHead" style="padding-top: 0px">
																				办事窗口
																			</td>
																		</tr>
																		<tr>
																			<td>
																				<span class="countFont">
																					<s:iterator value="approveItem.windList" status="win">
																						<table cellspacing="0px" cellpadding="0px" border="0px" width="100%">
																							<tr>
																								<td colspan="2" nowrap="nowrap" valign="top">
																								<table cellspacing="0px" cellpadding="0px" border="0px" width="100%">
																									<tr>
																										<td valign="top">
																										<span style="color: #E18B46"><s:property value="name"/> </span>
																										</td>
																										<td width="130px" nowrap="nowrap" align="left" valign="top" style="padding-top: 2px; padding-left: 10px">
																									  		<s:if test="maptype==1">		
																										<a href="javascript:showMap('map<s:property value="#win.index+1"/>');void(0)" class="BS_btnblue">查看地图</a>
																										</s:if>
																										<s:if test="maptype==2">
																											<a href="<s:property value="maplinks"/>" target="_blank" class="BS_btnblue">查看地图</a>
																										</s:if>
																										</td>
																									</tr>
																								</table>
																							</td>
																							</tr>
																							<tr>
																								<td width="80" align="right" nowrap="nowrap" valign="top">工作时间：</td>
																								<td width="800px" align="left" valign="top">
																								<s:property value="officehours"/> 
																								</td>
																							</tr>
																							<tr>
																								<td width="80" align="right" nowrap="nowrap" valign="top">窗口地址：</td>
																								<td width="800px" align="left" valign="top">
																								<s:property value="address"/> 
																								</td>
																							</tr>
																							<tr>
																								<td width="80" align="right" nowrap="nowrap" valign="top">联系电话：</td>
																								<td width="800px" align="left" valign="top">
																								<s:property value="phone"/> 
																								</td>
																							</tr>
																							<tr>
																								<td width="80" align="right" nowrap="nowrap" valign="top">交通指引：</td>
																								<td width="800px" align="left" valign="top">
																								<s:property value="trafficguide"/> 
																								</td>
																							</tr>
																							<tr>
																									<td colspan="2" align="left">
																									<s:if test="maptype==1">	
																									<div style="display:none" id="map<s:property value="#win.index+1"/>"><img src="/servlet/DownFileServlet?downType=windMap&fileId=<s:property value="guid"/>"/> </div>
																									</s:if>
																										<hr width="893px" style="border-top: #ccc 1px dashed; height: 0px" />
																									</td>
																								</tr>
																						</table>
																					</s:iterator>
																					<s:if test="approveItem.windList.size==0">
																					<s:property value="approveItem.itemProvince.windowaddress" escape="false"/></s:if>
																				</span>
																			</td>
																		</tr>
																		<tr>
																			<td class="panelHead" style="padding-top: 0px">
																				收费标准
																			</td>
																		</tr>
																		<tr>
																			<td>
																				<span class="countFont">
																					<s:property value="approveItem.chargeinfo" escape="false"/>
																				</span>
																			</td>
																		</tr>

																		<tr>
																			<td class="panelHead" style="padding-top: 0px">
																				常见问题解答
																			</td>
																		</tr>
																		<tr>
																			<td>
																				<span class="countFont">
																					<s:property value="approveItem.itemProvince.faq" escape="false"/>
																				</span>
																			</td>
																		</tr>
																		<tr>
																			<td class="panelHead" style="padding-top: 0px">
																				主管部门
																			</td>
																		</tr>
																		<tr>
																			<td>
																				<div class="countFont">
																					<s:property value="approveItem.adminorgid" />
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td class="panelHead" style="padding-top: 0px">
																				受理机构
																			</td>
																		</tr>
																		<tr>
																			<td>
																				<span class="countFont">
																					<s:property value="approveItem.acceptjiguan" />
																				</span>
																			</td>
																		</tr>
																		<tr>
																			<td class="panelHead" style="padding-top: 0px">
																				表格下载
																			</td>
																		</tr>
																		<tr>
																			<td>
																				<span class="countFont">
																					<table border="0" cellspacing="0" cellpadding="0"
																					width="100%" align="left" >
																					<s:iterator value="approveItem.appTabTempList"
																						status="fileStat">
																						<span
																							style="white-space: normal; word-break: break-all; font-color:blue;font-size:15px;">
																							<a style="color:#6688CC"
																								href="/servlet/DownFileServlet?downType=approveItemFileTemp&fileId=<s:property value="attachmentguid"/>"
																								target="_blank"><s:property
																									value="file_name" />
																							</a>
																						</span>
																					</s:iterator>
																				</table>
																				</span>
																			</td>
																		</tr>
																		<tr>
																			<td class="panelHead" style="padding-top: 0px">
																				办理依据
																			</td>
																		</tr>
																		<tr>
																			<td>
																				<span class="countFont">
																					<table width="100%" border="0" cellspacing="0"
																						cellpadding="0">
																						<s:iterator value="approveItem.lawAppList"
																							status="lawStat">
																							<tr>
																								<td width="100%"
																									style="font-family: '宋体'; font-size: 14px;">
																									<s:if test="status==1">
																										<a style="color:#6688CC"
																										href="/laws/lawsAction/id/<s:property value="id"/>.html"
																										target="_blank"><s:property value="title" />
																										</a>
																									</s:if>
																									<s:else>
																										<a style="color:#6688CC"
																										href="/servlet/DownFileServlet/downType/lawFile/fileId=<s:property value="id"/>.html"
																										target="_blank"><s:property value="title" />
																										</a>
																									</s:else>
																								</td>
																							</tr>
																						</s:iterator>
																					</table>
																					<s:if test="approveItem.lawAppList.size==0">
																				<s:property value="approveItem.approveitembasis" escape="false"/>
																				</s:if>
																				</span>
																			</td>
																		</tr>
																		
																		
																		
																	</table>
																</span>
															</span>
															<span style="height: 18px;"></span>
															<!-- end: 右边内容 -->
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
									</td>
									</tr>
								</table></td></tr></table></td></tr></table>
	</body>
</html>
