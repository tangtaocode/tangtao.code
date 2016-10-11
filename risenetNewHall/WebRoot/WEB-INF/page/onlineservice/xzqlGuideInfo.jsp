<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>深圳市龙岗区住房与建设局行政服务大厅--办事指南</title>
		<meta http-equiv="keywords" content="办事指南,服务大厅" />
		<meta http-equiv="description" content="办事指南" />
		<link href="/css/appGuid.css" type="text/css" rel="stylesheet" />
		<link href="/css/main.css" type="text/css" rel="stylesheet" />
		<script src="/js/Scripts/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript">
function showMap(mapId){
		$("#"+mapId).slideToggle(700);
}
var HKEY_Root,HKEY_Path,HKEY_Key;
 HKEY_Root="HKEY_CURRENT_USER";
 HKEY_Path="\\Software\\Microsoft\\Internet Explorer\\PageSetup\\";
 //设置网页打印的页眉页脚为空
 function PageSetup_Null(){
	 try{
		 var Wsh=new ActiveXObject("WScript.Shell");
		 HKEY_Key="header";
		 Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"");
		 HKEY_Key="footer";
		 Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"");
	 }catch(e){
	 }
 }
	function preview(oper) {
		PageSetup_Null();
		 if (oper < 10){ 
			 bdhtml=window.document.body.innerHTML;//获取当前页的html代码 
			 sprnstr="<!--startprint"+oper+"-->";//设置打印开始区域 
			 eprnstr="<!--endprint"+oper+"-->";//设置打印结束区域 
			 prnhtml=bdhtml.substring(bdhtml.indexOf(sprnstr)+18); //从开始代码向后取html 
			 prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));//从结束代码向前取html 
			 window.document.body.innerHTML=prnhtml; 
			 window.print(); 
			 window.document.body.innerHTML=bdhtml; 
		 }else{ 
		 	window.print(); 
		 } 
 	}
 
 
</script>
	</head>
	<body style="background-color:#fff;background-image:url('/images/govpublic/background.jpg');background-position:center top;background-repeat:repeat-y; background-attachment:fixed;">
<jsp:include page="/WEB-INF/page/public/topMenu.jsp">
<jsp:param name="menu" value="3"/>
</jsp:include>
<div class="BS_wrap">
<div class="BS_content" id="BS_content_div">
	<div class="BS_docontent"> 
      <!--top start-->
      <div class="guid_dotop">&nbsp;</div>
      <div class="BS_domiddle"> 
      <div class="BS_domiddlein"> 
		<table border="0" cellpadding="0" cellspacing="0"
			style="width: 100%; heigth: 100%;">
			<tr>
				<td align="center" valign="top" class="Portal_Backgroud">
					<table border="0" cellpadding="0" cellspacing="0"
						style="heigth: 100%; background-color: #FFFFFF;">
						
						<tr>
						<td width="2px" height="300px">
									</td>
							<td align="center" style="padding-left:13px;" >
								<!-- begin: 标题面板 -->

								<!-- end: 标题面板 -->
								<!-- begin: 内容面板 -->
								<div id="Portal_Content">
									<!--startprint1-->
									<table cellpadding="0px" cellspacing="0px" border="0px">

										<tr>
											<td valign="top">
												<table border="0" cellpadding="0" cellspacing="0"
													style="width: 98%;">
													<tr>
														<td colspan="3">
															<!-- begin: 名称 -->
															<span id="form:namePanel">
																<table cellspacing="8" cellpadding="2" border="0"
																	width="99%">
																	<tr>
																		<td align="left" nowrap="nowrap"
																			style="padding-left: 10px; font-size: 18px"
																			valign="top">
																			<div class="title">
																				<s:property value="approveItemLog.approveitemname" />
																			</div>

																		</td>
																		<td width="10%">
																		</td>
																		<td align="right" nowrap="nowrap"
																			style="padding-right: 10px">
																		 <a href="javascript:void(0)"><img src="/images/lineservice/print.png" onclick="preview(1)" border="0"/></a>
																		</td>
																	</tr>
																</table> </span>
															<!-- end: 左边 -->
														</td>
													</tr>
													<tr>
														<td width="100%" valign="top" align="center">
															<!-- begin: 左边内容 -->
															<div class="tableLeftPadding18" style="width: 100%;">
																<div style="font-size: 14px">
																	<table class="grayFont" cellspacing="0px"
																		cellpadding="0px" border="0px" width="100%" >
																		<tr>
																			<td valign="top" align="left" nowrap="nowrap" style="text-align:left;font-size:14px;color:#B4B4B4">
																				主管部门：
																			</td>
																			<td style="padding: 0px" width="100%" valign="top"
																				align="left">
																				<div
																					style="white-space: normal; word-break: break-all; width: 125px;font-size:15px;">
																					<s:property value="approveItemLog.adminorgid" /> 
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td valign="top" align="left" nowrap="nowrap" style="text-align:left;font-size:14px;color:#B4B4B4">
																				受理机构：
																			</td>
																			<td style="padding: 0px" width="100%" valign="top"
																				align="left">
																				<div
																					style="white-space: normal; word-break: break-all; width: 125px;font-size:15px;">
																					<s:property value="approveItemLog.acceptjiguan" />
																				</div>
																			</td>
																		</tr>
																	</table>
																</div>
																<div style="padding-top: 10px">
																	<table cellspacing="0px" cellpadding="0px" border="0px"
																		width="100%">
																		<tr>
																			<td width="80px" style="padding-top: 18px">
																				
																				<s:if test="approveItem.extend.approveplace==0&&(approveItem.itemid=='{0A0A017E-FFFF-FFFF-992E-3300FFFFFF8E}'||approveItem.itemid=='{09A16D48-0000-0000-75BB-8BBD00000D07}')">
																					<a target="_blank" href="/onlineService/initOnlineApply/approveItemGUID/<s:property value="approveItem.itemid"/>.html">
																					<img
																						src="/images/lineservice/onlineApplying.gif"
																						style="border: 0px" />
																					</a>
																				</s:if>
																				<s:elseif test="approveItem.extend.approveplace==1&&approveItem.extend.applicationformurl!=null">
																					<a target="_blank" href="<s:property value="approveItem.extend.applicationformurl"/>">
																					<img
																						src="/images/lineservice/onlineApplying.gif"
																						style="border: 0px" />
																					</a>
																				</s:elseif>
																				<s:else>
																				<img src="/images/lineservice/onlineApplying_d.gif"
																						style="border: 0px" />
																				</s:else>
																				
																			</td>
																		</tr>
																		<tr>
																			<td width="80px" style="padding-top: 18px">
																				<a href="http://service.szlh.gov.cn:8088/zxts.jsp" target="_blank"><img
																						src="/images/lineservice/businessConsulting_d.gif"
																						style="border: 0px" /> </a>

																			</td>
																		</tr>
																		<tr>
																			<td width="80px" style="padding-top: 18px">
																				<a  href="/interaction/queryResult.html"><img
																						src="/images/lineservice/progressInquirying.gif"
																						style="border: 0px" /> </a>
																			</td>
																		</tr>
																		<tr>
																			<td width="80px" style="padding-top: 18px">
																				<a href="/interaction/queryResult.html"><img
																						src="/images/lineservice/resultInquirying.gif"
																						style="border: 0px" /> </a>
																			</td>
																		</tr>
																	</table>
																</div>
																<div style="padding-top: 20px">
																	<table cellspacing="0px" cellpadding="0px" border="0px"
																		width="100%">
																		<tr>
																			<td class="grayFont"  valign="top" style="text-align:left;font-size:14px;color:#B4B4B4"
																				align="right" nowrap="nowrap">
																				表格下载：
																			</td>
																			
																		</tr>
																		<tr>
																			<td align="left" >
																				<table border="0" cellspacing="0" cellpadding="0"
																					width="100%" align="left" >
																					<s:iterator value="approveItemLog.appTabTempList"
																						status="fileStat">
																						<div
																							style="white-space: normal; word-break: break-all; width: 180px;font-color:blue;font-size:15px;">
																							<a style="color:#6688CC"
																								href="/servlet/DownFileServlet/downType/approveItemFileTemp/fileId/<s:property value="attachmentguid"/>.html"
																								target="_blank"><s:property
																									value="file_name" />
																							</a>
																						</div>
																					</s:iterator>
																				</table>
																				<s:if test="approveItemLog.appTabTempList.size==0">
																					<font style="font-size: 18px;">无相关表格</font>
																				</s:if>
																			</td>
																		</tr>
																	</table>
																</div>
															</div>
															<!-- end: 左边内容 -->
														</td>
														<td width="10px" nowrap="nowrap">
															<div style="width: 10px"></div>
														</td>
														<td width="710px" nowrap="nowrap" valign="top"
															align="right">
															<!-- begin: 右边内容 -->
															<div style="text-align:left">
																<!-- 写两个div为了兼容浏览器 -->
																<div id="subjectContentBgDiv"
																	class="subjectContentPanelBg"
																	style="width: 690px; padding: 10px">
																	<table class="tableLeftPadding10" cellspacing="0px"
																		cellpadding="0px" border="0px" width="690px">
																		<tr>
																			<td class="panelHead" style="padding-top: 0px">
																				办理对象
																			</td>
																		</tr>
																		<tr>
																			<td>
																				<div class="countFont">
																					<s:property value="approveItemLog.itemProvince.serviceobject" />
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td class="panelHead" style="padding-top: 0px">
																				办理条件
																			</td>
																		</tr>
																		<tr>
																			<td>
																				<div class="countFont">
																					<s:property value="approveItemLog.declarecondition" escape="false"/>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td class="panelHead" style="padding-top: 0px">
																				所需材料
																			</td>
																		</tr>
																		<s:if test="approveItemExtend!=null&&approveItemExtend.isrereq==1">
																			<tr>
																				<td>
																					<div class="countFont">
																						<table width="100%" border="0" cellspacing="0" cellpadding="0">
																							<tr>
																								<td>
																									<s:property value="approveItemExtend.rereq"/>
																								</td>
																							</tr>
																						</table>
																					</div>	
																				</td>
																			</tr>
																		</s:if>
																		<s:if test="approveItemExtend==null||approveItemExtend.isrereq!=1">
																			<tr>
																				<td>
																					<div class="countFont">
																						<table width="100%" border="0" cellspacing="0"
																							cellpadding="0">
																								<s:iterator value="approveItemLog.guideFilTypeeList"
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
																												<s:property value="materialname" escape="false"/>
																												<s:if test="describe!=null">
																												<div style="padding-left:40px;"><font style="color:#C2C2C2;">（说明：<s:property value="describe" escape="false"/>） </font></div>
																												</s:if>
																											</td>
																										</tr>
																									</s:iterator>
																								</s:iterator>
																								<s:if test="approveItemLog.guideFilTypeeList.size<1">
																								<s:iterator value="approveItemLog.guideFileList"
																									status="typeStat">
																									<tr>
																										<td>
																											(
																											<s:property value="#typeStat.index+1" />
																											)、
																											<s:property value="materialname" escape="false"/>
																											<s:if test="describe!=null">
																												<div style="padding-left:40px;"><font style="color:#C2C2C2;">（说明：<s:property value="describe" escape="false"/>） </font></div>
																												</s:if>
																										</td>
																									</tr>
																								</s:iterator>
																								</s:if>
																						</table>
																						<s:if test="approveItemLog.guideFilTypeeList.size==0&&approveItemLog.guideFileList.size==0">
																						无
																						</s:if>
																					</div>
																				</td>
																			</tr>
																		</s:if>
																		<tr>
																			<td class="panelHead" style="padding-top: 0px">
																				窗口办理流程
																			</td>
																		</tr>
																		<tr>
																			<td>
																				<div class="countFont">
																					<s:if test="approveItemLog.windowFlow>0">
																						<a class="BS_btnblue"
																									href="/servlet/DownFileServlet/downType/approveItemWindow/fileId/<s:property value="approveItemLog.itemid"/>.html" >点击下载流程图
																								</a>
																					</s:if>
																					<s:property value="approveItemLog.itemProvince.windowworkflow" escape="false"/>
																					<s:if test="approveItemLog.windowFlow.length==0&&approveItemLog.itemProvince.windowworkflow==null">
																					无
																					</s:if>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td class="panelHead" style="padding-top: 0px">
																				网上申报流程
																			</td>
																		</tr>
																		<tr>
																			<td>
																				<div class="countFont">
																					<s:if test="approveItemLog.declarepic.length>0">
																						<a class="BS_btnblue"
																									href="/servlet/DownFileServlet/downType/approveItemGuide/fileId/<s:property value="approveItemLog.itemid"/>.html" >点击下载流程图
																								</a>
																					</s:if>
																					<s:property value="approveItemLog.declaredesc" escape="false"/>
																					<s:if test="approveItemLog.declarepic.length==0&&approveItemLog.declaredesc==null">
																					无
																					</s:if>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td class="panelHead" style="padding-top: 0px">
																				办理时限
																			</td>
																		</tr>
																		<tr>
																			<td>
																				<div class="countFont">
																					<s:if test="approveItemLog.timelimit==0">
																						法定时限：即办件<br/>
																					</s:if>
																					<s:if test="approveItemLog.promiselimittime==0">
																						承诺时限：即办件<br/>
																					</s:if>
																					<s:if test="approveItemLog.timelimit!=0">
																						<s:if test="approveItemLog.timelimit==''||approveItemLog.timelimit==null">
																							法定时限：无法定时限<br/> 
																						</s:if>
																						<s:if test="approveItemLog.timelimit!=''&&approveItemLog.timelimit!=null">
																							法定时限：申请材料齐全、符合法定形式之日起
																							<s:property value="approveItemLog.timelimit" />个
																							<s:if test='approveItemLog.timelimitunit=="G"'>
																								工作日
																							</s:if>
																							<s:if test='approveItemLog.timelimitunit=="Z"'>
																								自然日
																							</s:if>
																							内
																							<s:if test="approveItemLog.timelimitdesc!=null&&approveItemLog.timelimitdesc!=''">
																								<div style="padding-left:40px;"><font style="color:#C2C2C2;">（说明：<s:property value="approveItemLog.timelimitdesc"/>） </font></div>
																							</s:if>
																							<s:if test="approveItemLog.timelimitdesc==null||approveItemLog.timelimitdesc==''">
																							<br/>
																							</s:if>
																						</s:if>
																					</s:if>
																					<s:if test="approveItemLog.promiselimittime!=0">
																						承诺时限：申请材料齐全、符合法定形式之日起
																						<s:property value="approveItemLog.promiselimittime" />个
																						<s:if test='approveItemLog.promiselimittimeunit=="G"'>
																								工作日
																							</s:if>
																							<s:if test='approveItemLog.promiselimittimeunit=="Z"'>
																								自然日
																							</s:if>
																							内
																							<s:if test="approveItemLog.promiselimittimedesc!=null&&approveItemLog.promiselimittimedesc!=''">
																								<div style="padding-left:40px;"><font style="color:#C2C2C2;">（说明：<s:property value="approveItemLog.promiselimittimedesc"/>） </font></div>
																							</s:if>
																					</s:if>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td class="panelHead" style="padding-top: 0px">
																				办事窗口
																			</td>
																		</tr>
																		<tr>
																			<td>
																				<div class="countFont">
																					<s:iterator value="approveItemLog.windList" status="win">
																						<table cellspacing="0px" cellpadding="0px" border="0px" width="100%">
																							<tr>
																								<td colspan="2" nowrap="nowrap" valign="top">
																								<table cellspacing="0px" cellpadding="0px" border="0px" width="100%">
																									<tr>
																										<td valign="top">
																										<span style="color: #E18B46"><s:property value="name"/> </span>
																										</td>
																										<td width="100px" nowrap="nowrap" align="left" valign="top" style="padding-top: 2px; padding-left: 10px">
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
																								<td  align="left" valign="top">
																								<s:property value="officehours"/> 
																								</td>
																							</tr>
																							<tr>
																								<td width="80" align="right" nowrap="nowrap" valign="top">窗口地址：</td>
																								<td  align="left" valign="top">
																								<s:property value="address"/> 
																								</td>
																							</tr>
																							<tr>
																								<td width="80" align="right" nowrap="nowrap" valign="top">联系电话：</td>
																								<td  align="left" valign="top">
																								<s:property value="phone"/> 
																								</td>
																							</tr>
																							<tr>
																								<td width="80" align="right" nowrap="nowrap" valign="top">交通指引：</td>
																								<td  align="left" valign="top">
																								<s:property value="trafficguide"/> 
																								</td>
																							</tr>
																							<tr>
																									<td colspan="2" align="left">
																									<s:if test="maptype==1">	
																									<div style="display:none" id="map<s:property value="#win.index+1"/>"><img src="/servlet/DownFileServlet/downType/windMap/fileId/<s:property value="guid"/>.html"/> </div>
																									</s:if>
																										<hr width="690px" style="border-top: #ccc 1px dashed; height: 0px" />
																									</td>
																								</tr>
																						</table>
																					</s:iterator>
																				<s:if test="approveItemLog.windList.size==0">
																					<s:property value="approveItemLog.itemProvince.windowaddress" escape="false"/></s:if>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td class="panelHead" style="padding-top: 0px">
																				收费标准
																			</td>
																		</tr>
																		<tr>
																			<td>
																				<div class="countFont">
																					<s:property value="approveItemLog.chargeinfo" escape="false"/>
																				</div>
																			</td>
																		</tr>

																		<tr>
																			<td class="panelHead" style="padding-top: 0px">
																				常见问题解答
																			</td>
																		</tr>
																		<tr>
																			<td>
																				<div class="countFont">
																					<s:property value="approveItemLog.itemProvince.faq" escape="false"/>
																				</div>
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
																					<%-- <s:property value="approveItemLog.adminorgid" /> --%>
																					深圳市住房和建设局
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
																				<div class="countFont">
																					<s:property value="approveItemLog.acceptjiguan" />
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td class="panelHead" style="padding-top: 0px">
																				办理依据
																			</td>
																		</tr>
																		<tr>
																			<td>
																				<div class="countFont">
																					<table width="100%" border="0" cellspacing="0"
																						cellpadding="0">
																						<s:iterator value="approveItemLog.lawAppList"
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
																										href="/servlet/DownFileServlet/downType/lawFile/fileId/<s:property value="id"/>.html"
																										target="_blank"><s:property value="title" />
																										</a>
																									</s:else>
																								</td>
																							</tr>
																						</s:iterator>
																					</table>
																				<s:if test="approveItemLog.lawAppList.size==0">
																				<s:property value="approveItemLog.approveitembasis" escape="false"/>
																				</s:if>
																				</div>
																			</td>
																		</tr>
																	</table>
																</div>
															</div>
															<div style="height: 18px;"></div>
															<!-- end: 右边内容 -->
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
								</div>
								<!--endprint1-->
								<!-- end: 内容面板 -->
							</td>
						</tr>
					</table>
				</td>
				
			</tr>
		</table>
		</div>
		</div>
		</div>
		<div class="BS_dobottom">&nbsp;</div>
		<object classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2"
			height="0" id="WB" width="0"></object>
 </div>
   <div class="clean" style="height:10px;"></div>
   <jsp:include page="/WEB-INF/page/public/bottom.jsp"></jsp:include>    
</body>
</html>

  
