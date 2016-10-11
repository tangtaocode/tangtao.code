<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>广东省网上办事大厅深圳市龙岗区住房与建设局-效能监察</title>
<meta name="keywords" content="深圳市龙岗区住房与建设局网上办事大厅-效能监察,深圳市龙岗区住房与建设局办事大厅效能监察,效能监察,深圳市龙岗区住房与建设局效能监察"></meta>
<meta name="description" content="广东省网上办事大厅深圳市龙岗区住房与建设局-效能监察首页"></meta>
<meta name="robots" content="all" />
<script src="/js/Scripts/jquery.min.js" type="text/javascript"></script>
<link href="/css/portal.css" type="text/css" rel="stylesheet" />
<style type="text/css">
.titleTab11 {
	overflow: hidden;
	text-align: center;
	vertical-align: middle;
	background-position: 0px 0px;
	width: 161px;
	height: 27px;
	cursor: pointer;
	font-size:11pt;
}

.titleTab11:hover {
	overflow: hidden;
	text-align: center;
	vertical-align: middle;
	background-position: 0px 0px;
	width: 161px;
	height: 27px;
	cursor:pointer;
	color:red;
	font-size:11pt;
}
.titleTab11 a:hover{
	text-decoration:none;
}
.titleTabSelected11 {
	overflow: hidden;
	text-align: center;
	vertical-align: middle;
	background-image: url(/images/supervise/btn_title_enabled2.gif);
	background-position: 0px 0px;
	width: 161px;
	height: 27px;
	cursor:pointer;
	color:white;
	font-size:11pt;
}

.titleTabSelected11 a{
	color:white;
	font-size:11pt;
	cursor: pointer;
}
.effect_font{
	width:70px;
}
.fl{
	float:left;
}
#pjinfo ul{
	list-style-type:none;
	list-style-image:none;
	font-size:12px;
}
</style>
<script type="text/javascript">
function changeSearch(item,text){
	$.post('/supervise/superviseDefault.html',{'type':item},function(data){
		$("#WebPart1").html(data);
		$("#itemDIVText").html(text);
	});
	if(item=="ywpj"){
		$("#tjsjId").html("统计时间：2013-06-27");
	}else{
		$("#tjsjId").html("&nbsp;");
	}
}
function tabSelected(obj,tableId,defaultClass,selectedClass){
	var tab_table = document.getElementById(tableId);
	var tabs = tab_table.getElementsByTagName('div');
	for(i=0;i<tabs.length;i++){
		var tab = tabs[i];
		tab.className = defaultClass;
	}
	obj.className = selectedClass;
}

</script>
</head>
<body style="background-color:#fff;background-image:url('/images/govpublic/background.jpg');background-position:center top;background-repeat:repeat-y; background-attachment:fixed;">
    <div id="pagecontainer" style="text-align:center;">
	     <div class="sf_cols">
			 <div style="float: none; width: 100%; margin: 0;" class="sf_colsOut">
			 	<div class="sf_colsIn ">
					<div class="SD_Top ">
					    <jsp:include page="/WEB-INF/page/public/topMenu.jsp">
					    <jsp:param name="menu" value="5"/>
					    </jsp:include>
					</div>
				</div>
			</div>
		 </div>
		
		 <!-- 选择卡结束 -->
 			<div class="sf_cols">
 			<div style="float: none; width: 100%; margin: 0;" class="sf_colsOut">
 				<div class="sf_colsIn" style="padding-top: 5px; padding-right: 15px; padding-bottom: 18px; padding-left:15px;">
				<table border="0" cellpadding="0" cellspacing="0px" width="1000px" align="center">
					<tr>
						<td align="left" width="20%" nowrap="nowrap">
							<span style="font-size: 18pt;padding-left:20px;">效能监察 </span>
						</td>
						<td width="10%">
						&nbsp;
						</td>
						<td align="right" nowrap="nowrap" width="70%">
							<table id="titleTabTable11" border="0" cellpadding="0" cellspacing="0" style="width: 380px;">
								<tr>
									<td align="center" nowrap="nowrap" width="165px">
										<div class="titleTabSelected11" onclick="tabSelected(this,'titleTabTable11','titleTab11','titleTabSelected11');">
										<a href="/supervise/superviseDefault.html">
													<span style="width:161px;height:27px;display:block;">服务事项进驻情况</span></a>
										</div>
									</td>
									<td align="center" nowrap="nowrap" width="165px">
											<div class="titleTab11" onclick="tabSelected(this,'titleTabTable11','titleTab11','titleTabSelected11');changeSearch('wsdj','网上服务登记情况');">
											<a href="#">
													<span style="width:161px;height:27px;display:block">网上服务登记情况</span></a>
											</div>
									</td>
									<td align="center" nowrap="nowrap" width="165px">
											<div class="titleTab11" onclick="tabSelected(this,'titleTabTable11','titleTab11','titleTabSelected11');changeSearch('ywpj','在线办理率级别评定');">
											<a href="#">
													<span style="width:161px;height:27px;display:block">在线办理率级别评定 </span></a>
											</div>
									</td>
									
								</tr>
							</table>
						</td>
					</tr>
				</table>
				</div>
				</div>
				</div>
				<!-- 选择卡结束 -->
		
		<!-- 统计内容开始 -->
		<div class="sf_cols">
 			<div style="float: none; width: 100%; margin: 0; ;" class="sf_colsOut">
 				<div class="sf_colsIn ">
 					<div id="Portal_Content">
						<table style="width: 960px;" align="center" cellpadding="0px" cellspacing="0px"
							border="0px">
							<tr>
								<td colspan="3">
									<img src="/images/supervise/shadow_T.png" />
								</td>
							</tr>
							<tr>
								<td width="2px" height="300px"
									style="background-image: url(/images/supervise/shadow_L.png); background-repeat: repeat-y;"></td>
								<td valign="top" style="width: 956px;">
					
									<table border="0" cellpadding="0" cellspacing="0"
										style="width: 100%;">
										<tr>
											<td style="padding-left: 16px;">
												<table border="0" cellpadding="0"
													style="width: 100%; padding-top: 8px">
													<tr>
														<td align="left" nowrap="nowrap" valign="top">
															<div
																style="white-space: normal; word-break: break-all; width: 430px">
																<span style="font-size: 16pt; color: #858585" id="itemDIVText">服务事项进驻情况</span>
															</div>
														</td>
														<td align="right" nowrap="nowrap" width="100%" valign="top">
															<span id="tjsjId" style="padding-right:30px;">&nbsp;</span>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td style="padding: 16px">
												<div id="allSubjectDiv">
					
													<div id="WebPart1">
														<table width="924px" cellspacing="0" cellpadding="4"
															style="border: #c0c0c0 1px solid; border-collapse: collapse">
															<tr>
																<td class="colColor1">部门名称</td>
																<td class="colColor2">
																	服务事项总数
																</td>
																<td class="colColor3">
																	行政许可事项
																</td>
																<td class="colColor4">
																	非行政许可审批事项
																</td>
																<td class="colColor5">
																	社会事务服务事项
																</td>
															</tr>
															<s:set var ="total1" value="0" />
										                      <s:set var ="total3" value="0" />
										                      <s:set var ="total4" value="0" />
										                      <s:set var ="total5" value="0" />
										                      <s:iterator value="statList" status="item"> 
															<tr>
										                        <td class="titleCol"><s:property value="column1"/> </td>
										                        <td class="numCol"><s:property value="int1"/></td>
										                        <td class="numCol"><s:property value="int3"/></td>
										                        <td class="numCol"><s:property value="int4"/></td>
										                        <td class="numCol"><s:property value="int5"/></td>
										                        <s:set var ="total1" value="cint1+#total1"/>
										                        <s:set var ="total3" value="cint3+#total3"/>
										                        <s:set var ="total4" value="cint4+#total4"/>
										                        <s:set var ="total5" value="cint5+#total5"/>
											               </tr>
									                      </s:iterator>
									                      <tr>
										                        <td class="titleCol">合计</td>
										                        <td class="numCol"><s:property value="#total1"/></td>
										                        <td class="numCol"><s:property value="#total3"/></td>
										                        <td class="numCol"><s:property value="#total4"/></td>
										                        <td class="numCol"><s:property value="#total5"/></td>
											               </tr>
														</table>
													</div>
												</div>
											</td>
										</tr>
									</table>
								</td>
								<td width="2px" height="300px"
									style="background-image: url(/images/supervise/shadow_R.png); background-repeat: repeat-y;"></td>
							</tr>
							<tr>
								<td colspan="3">
									<img src="/images/supervise/shadow_B.png" />
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
		<!-- 结束 -->
		<div class="sf_cols">
 			<div style="float: none; width: 100%; margin: 0; ;" class="sf_colsOut">
 				<div class="sf_colsIn ">
					<jsp:include page="/WEB-INF/page/public/bottom.jsp"></jsp:include>    
				</div>
			</div>
		</div>
    </div>
</body>
</html>