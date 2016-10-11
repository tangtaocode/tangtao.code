<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>深圳市龙岗区住房与建设局行政服务大厅--办事指南</title>
		<meta http-equiv="keywords" content="办事指南,服务大厅" />
		<meta http-equiv="description" content="办事指南" />
		<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1.0, maximum-scale=2.0, user-scalable=no">
		<link href="/css/main.css" type="text/css" rel="stylesheet" />
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
		.content {
			width: 100%;
			margin: 0 auto;
			font-size: 14px;
			text-align: left;
			color: #000;
			line-height: 30px;
		}
		
		.bsck {
			padding: 10px; 
			border-radius: 8px; 
			border: 1px solid rgb(225, 225, 225); 
			width: 92%; height: auto; 
			margin-top: 30px; 
			margin-right: auto; 
			margin-left: auto; 
			display: block; 
			word-wrap: break-word; 
			background-color: rgb(255, 255, 255); 
			-moz-border-radius: 8px; 
			-webkit-border-radius: 8px;
		}
		.bsck_title {
			top: -25px; 
			height: 30px; 
			clear: both; 
			display: block; 
			position: relative;
		}
		.bsck_title span {
			width: auto; 
			height: 30px; 
			color: rgb(255, 255, 255); 
			line-height: 30px; 
			padding-right: 20px; 
			padding-left: 20px; 
			font-family: "微软雅黑"; 
			font-size: 16px; 
			float: left; 
			display: block; 
			background-color: rgb(94, 213, 255);
		}
		.bsck_div {
			top: -10px; 
			width: 100%; 
			height: auto; 
			position: relative;
		}
		.bsck_div hr {
			margin-top: 20px; 
			margin-bottom: 20px;
		}
		.bsck_left {
			width: 80%; 
			height: auto; 
			color: rgb(87, 87, 87); 
			line-height: 28px; 
			font-family: "微软雅黑"; 
			font-size: 16px; 
			margin-right: 70px; 
			word-wrap: break-word;
		}
		.bsck_left span {
			line-height: 36px; 
			font-size: 18px; 
			font-weight: bold;
		}
		.bsck_right {
			width: 63px; 
			height: auto; 
			float: right;
		}
		.bsck_text {
			top: -10px; 
			width: 100%; 
			height: auto; 
			color: rgb(87, 87, 87); 
			font-family: "微软雅黑";
			line-height: 26px;
			font-size: 16px; 
			float: left; 
			display: block; 
			position: relative;
		}

</style>
	</head>
	<body>
		<div>
			<!-------------事项名称------------->
			<DIV class="bsck">
			<DIV class="bsck_title"><SPAN>事项名称</SPAN></DIV>
			<DIV class="bsck_text"><s:property value="approveItem.approveitemname" /><br/></DIV>
			<DIV style="clear: both;"></DIV>
			</DIV>
			<!-------------办理对象------------->
			<DIV class="bsck">
			<DIV class="bsck_title"><SPAN>办理对象</SPAN></DIV>
			<DIV class="bsck_text"><s:property value="approveItem.itemProvince.serviceobject" /><br/></DIV>
			<DIV style="clear: both;"></DIV>
			</DIV>
			<!-------------法律依据------------->
			<DIV class="bsck">
			<DIV class="bsck_title"><SPAN>法律依据</SPAN></DIV>
			<DIV class="bsck_text"><s:property value="approveItem.approveitembasis"  escape="false" /><br/></DIV>
			<DIV style="clear: both;"></DIV>
			</DIV>
			<!-------------法律效力------------->
			<DIV class="bsck">
			<DIV class="bsck_title"><SPAN>法律效力</SPAN></DIV>
			<DIV class="bsck_text"><s:property value="approveItem.xiaoli" /><br/></DIV>
			<DIV style="clear: both;"></DIV>
			</DIV>
			<!-------------数量及方式------------->
			<DIV class="bsck">
			<DIV class="bsck_title"><SPAN>数量及方式</SPAN></DIV>
			<DIV class="bsck_text"><s:property value="approveItem.quantitylimit" /><br/></DIV>
			<DIV style="clear: both;"></DIV>
			</DIV>
			<!--------------办理条件------------>
			<DIV class="bsck">
			<DIV class="bsck_title"><SPAN>办理条件</SPAN></DIV>
			<DIV class="bsck_text"><s:property value="approveItem.declarecondition" escape="false"/><br/></DIV>
			<DIV style="clear: both;"></DIV>
			</DIV>
			<!--------------申请材料------------>
			<DIV class="bsck">
			<DIV class="bsck_title"><SPAN>申请材料</SPAN></DIV>
			<DIV class="bsck_text"><s:property value="approveItem.materialStr" escape="false"/><br/></DIV>
			<DIV style="clear: both;"></DIV>
			</DIV>
			<!--------------证件名称及有效期限------------>
			<DIV class="bsck">
			<DIV class="bsck_title"><SPAN>证件名称及有效期限</SPAN></DIV>
			<DIV class="bsck_text"><s:property value="approveItem.zhengjianmingcheng" escape="false"/><br/></DIV>
			<DIV style="clear: both;"></DIV>
			</DIV>
			<!--------------办理时限------------>
			<DIV class="bsck">
			<DIV class="bsck_title"><SPAN>办理时限</SPAN></DIV>
			<DIV class="bsck_text">
				<s:if test="approveItem.timelimit == -1">
							法定时限：无法定时限<br/>
						</s:if>
						<s:elseif test="approveItem.timelimit ==  '' || approveItem.timelimit == 0">
							法定时限：已办件、符合法定形式之日起1个工作日</br>
						</s:elseif>
						<s:else>
							法定时限：申请材料齐全、符合法定形式之日起
							<s:property value="approveItem.timelimit" />
							个工作日内<br/> 
						</s:else>
						<s:if test="approveItem.timelimitdesc!=null && approveItem.timelimitdesc!=''">
							(<s:property value="approveItem.timelimitdesc"/>)<br/>
						</s:if>
						<s:if test="approveItem.promiselimittime=='' || approveItem.promiselimittime == 0">
							承诺时限：已办件、符合法定形式之日起1个工作日</br>
						</s:if>
						<s:else>
							承诺时限：申请材料齐全、符合法定形式之日起
							<s:property value="approveItem.promiselimittime" />
							个工作日内<br/>
						</s:else>
						<s:if test="approveItem.promiselimittimedesc!=null && approveItem.promiselimittimedesc!=''">
							(<s:property value="approveItem.promiselimittimedesc"/>)<br/>
						</s:if>
			<br/></DIV>
			<DIV style="clear: both;"></DIV>
			</DIV>
			<!--------------事项收费------------>
			<DIV class="bsck">
			<DIV class="bsck_title"><SPAN>事项收费</SPAN></DIV>
			<DIV class="bsck_text"><s:property value="approveItem.chargeinfo" escape="false"/><br/></DIV>
			<DIV style="clear: both;"></DIV>
			</DIV>
			<!--------------决定机关------------>
			<DIV class="bsck">
			<DIV class="bsck_title"><SPAN>决定机关</SPAN></DIV>
			<DIV class="bsck_text"><s:property value="approveItem.decideorg" /><br/></DIV>
			<DIV style="clear: both;"></DIV>
			</DIV>
			<!--------------申请受理机关------------>
			<DIV class="bsck">
			<DIV class="bsck_title"><SPAN>申请受理机关</SPAN></DIV>
			<DIV class="bsck_text"><s:property value="approveItem.acceptjiguan" /><br/></DIV>
			<DIV style="clear: both;"></DIV>
			</DIV>
			<!--------------窗口办理------------>
			<DIV class="bsck">
			<DIV class="bsck_title"><SPAN>窗口办理</SPAN></DIV>
			<DIV class="bsck_text">
				<s:iterator value="approveItem.windList" status="win">
					<div>
						<span style="color: #E18B46"><s:property value="name"/> </span>
						<!--<s:if test="maptype==1">		
							<a href="javascript:showMap('map<s:property value="#win.index+1"/>');void(0)" class="BS_btnblue">查看地图</a>
						</s:if>
						<s:if test="maptype==2">
							<a href="<s:property value="maplinks"/>" target="_blank" class="BS_btnblue">查看地图</a>
						</s:if>-->
					</div>
					<div>
						工作时间：<s:property value="officehours"/><br/>
					</div>
					<div>
						窗口地址：<s:property value="address"/><br/>
					</div>
					<div>
						联系电话：<s:property value="phone"/><br/>
					</div>
					<div>
						交通指引：<s:property value="trafficguide"/><br/>
					</div>
					<div>
						<s:if test="maptype==1">	
						<div style="display:none" id="map<s:property value="#win.index+1"/>"><img src="/servlet/DownFileServlet?downType=windMap&fileId=<s:property value="guid"/>"/> </div>
						</s:if>
						<hr style="border-top: #ccc 1px dashed; height: 0px" />
					</div>
				</s:iterator>
				<s:if test="approveItem.windList.size==0">
					<s:property value="approveItem.itemProvince.windowaddress" escape="false"/>
				</s:if>
			<br/></DIV>
			<DIV style="clear: both;"></DIV>
			</DIV>
			<!--------------年审或年检------------>
			<DIV class="bsck">
			<DIV class="bsck_title"><SPAN>年审或年检</SPAN></DIV>
			<DIV class="bsck_text"><s:property value="approveItem.nianshenStr" /><br/></DIV>
			<DIV style="clear: both;"></DIV>
			</DIV>
			<!--------------监督机关------------>
			<DIV class="bsck">
			<DIV class="bsck_title"><SPAN>监督机关</SPAN></DIV>
			<DIV class="bsck_text"><s:property value="approveItem.jdjg" /><br/></DIV>
			<DIV style="clear: both;"></DIV>
			</DIV>
			<!--------------救济方式------------>
			<DIV class="bsck">
			<DIV class="bsck_title"><SPAN>救济方式</SPAN></DIV>
			<DIV class="bsck_text"><s:property value="approveItem.legalremedy" /><br/></DIV>
			<DIV style="clear: both;"></DIV>
			</DIV>
			<!--------------公开类型------------>
			<DIV class="bsck">
			<DIV class="bsck_title"><SPAN>公开类型</SPAN></DIV>
			<DIV class="bsck_text"><s:property value="approveItem.gkType" /><br/></DIV>
			<DIV style="clear: both;"></DIV>
			</DIV>
			<!--------------事项程序------------>
			<DIV class="bsck">
			<DIV class="bsck_title"><SPAN>事项程序</SPAN></DIV>
			<DIV class="bsck_text">
				请登录龙岗住房和建设局门户网站办事指南下载
				<%--<s:if test="approveItem.powerFlow>0">
					<a class="BS_btnblue" href="/servlet/DownFileServlet/downType/approveItemWindow/fileId/<s:property value="approveItem.itemid" />.html" >点击下载流程图</a>
				</s:if>
					<s:property value="approveItem.itemProvince.powerworkflow" escape="false"/>
				<s:if test="approveItem.powerFlow.length==0&&approveItem.itemProvince.powerworkflow==null">
					无
				</s:if> --%>
			<br/></DIV>
			<DIV style="clear: both;"></DIV>
			</DIV>
			<!--------------窗口办理流程------------>
			<DIV class="bsck">
			<DIV class="bsck_title"><SPAN>窗口办理流程</SPAN></DIV>
			<DIV class="bsck_text">
				<s:if test="approveItem.windowFlow>0">
					<a class="BS_btnblue" href="/servlet/DownFileServlet/downType/approveItemWindow/fileId/<s:property value="approveItem.itemid" />.html" >点击下载流程图</a>
				</s:if>
					<s:property value="approveItem.itemProvince.windowworkflow" escape="false"/>
				<s:if test="approveItem.windowFlow.length==0&&approveItem.itemProvince.windowworkflow==null">
					无
				</s:if>
			<br/></DIV>
			<DIV style="clear: both;"></DIV>
			</DIV>
			<!--------------申请表格------------>
			<DIV class="bsck">
			<DIV class="bsck_title"><SPAN>申请表格</SPAN></DIV>
			<DIV class="bsck_text">
				<s:if test="approveItem.reformnameBc !=null ">
					<s:property value="approveItem.reformnameBc" escape="false"/><br/>
				</s:if>
					<div>
						<div class="countFont">
							<table border="0" cellspacing="0" cellpadding="0" width="100%" align="left" >
								<s:iterator value="approveItem.appTabTempList"
									status="fileStat">
									<div
										style="white-space: normal; word-break: break-all;font-size:15px;">
										<%--<a style="color:#6688CC"
											href="/servlet/DownFileServlet/downType/approveItemFileTemp/fileId/<s:property value="attachmentguid"/>.html"
											target="_blank"><s:property value="file_name" />
										</a> --%>
										<s:property value="file_name" />
									</div>
								</s:iterator>
							</table>
						</div>
					</div>
			<br/></DIV>
			<DIV style="clear: both;"></DIV>
			</DIV>
		</div>
<object classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" height="0" id="WB" width="0"></object>
	</body>
</html>
