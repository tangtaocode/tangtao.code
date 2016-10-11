<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>广东省网上办事大厅深圳龙岗住房和建设局-我要评议</title>
		<meta name="keywords"
			content="深圳市龙岗区网上办事大厅 -我要评议,龙岗区办事大厅我要评议,我要评议,龙岗区我要评议"></meta>
		<meta name="description" content="广东省网上办事大厅深圳龙岗住房和建设局-我要评议"></meta>
		<meta name="robots" content="all" />
		<link href="/css/qyInvestIndex.css" type="text/css" rel="stylesheet" />
		<link href="/css/login-box.css" type="text/css" rel="stylesheet" />
		<link href="/css/main.css" type="text/css" rel="stylesheet" />
		<script src="/js/Scripts/jquery.min.js" type="text/javascript"></script>
		<script src="/js/validation.js" type="text/javascript"></script>
		<script src="/js/zDialog/risenetDialog.js" type="text/javascript"></script>
<script type="text/javascript">
function searchBusi(){
	if(!validations("searchForm"))return false;
	var formData = $("#searchForm").serializeArray();
	$.post("/correction/correctionDefault.html",formData,function(data){
		if(data.message=="1"){
			Dialog.alert("未找到对应的需要补齐补正业务。");
		}else if(data.message=="0"){
			Dialog.alert("系统错误");
		}else{
			$("#pageCountTd").html(data);
		}
	});
}
function deleteFile(guid,type){
	$.post("/riseFile/deleteFile.html",{'fileGUID':guid,'modeType':type},function(data){
		if(data.message=="1"){
			Dialog.alert("删除成功");
		}else{
			Dialog.alert("删除失败");
		}
	});
}
function doSubmit(guid){
	$.post("/correction/updateCorrection.html",{'declaresn':guid},function(data){
		if(data.message=="1"){
			Dialog.alert("提交成功",function(){
				searchBusi();
			});
		}else{
			Dialog.alert("处理失败");
		}
	});
}
</script>
	</head>
	<body>
		<div class="SD_Top ">
			<jsp:include page="/WEB-INF/page/public/topMenu.jsp">
				<jsp:param name="menu" value="3" />
			</jsp:include>
		</div>
		<div class="GovOpenIndex ">
			<table border="0" cellpadding="0" cellspacing="0"
				style="width: 1000px; heigth: 100%" align="center">
				<tr>
					<td valign="top" align="center" height="155px">
						<!-- begin: 顶部面板 -->
						<!-- end: 标题面板 -->
						<!-- begin: 内容面板 -->
						<div id="Portal_Content">
							<table border="0" cellpadding="0" cellspacing="20px"
								style="width: 100%;">
								<tr>
									<td align="left">
										<span style="font-size: 18pt;">补齐补正</span>
									</td>
									<td align="right">
										&nbsp;
									</td>
								</tr>
							</table>
							<table style="width: 960px;" cellpadding="0px" cellspacing="0px"
								border="0px">
								<tr>
									<td colspan="3">
										<img src="/images/govpublic/shadow_T.png" />
									</td>
								</tr>
								<tr>
									<td width="5px" height="300px"
										style="background-image: url(/images/govpublic/shadow_R.png); background-repeat: repeat-y;">
									</td>
									<td valign="top" style="width: 956px;" id="mainCount">
										<table border="0" cellpadding="0" cellspacing="0"
											style="width: 100%;">
											<tr>
												<td style="text-align: center;">
													<s:form id="searchForm">
														<table border="0" cellpadding="0" cellspacing="0"
															style="width: 100%;">
															<tr>
																<td style="text-align: left; padding: 10px;" width="90%">
																	<table id="userInfoTab" border="0" cellpadding="0"
																		cellspacing="0" style="width: 90%">
																		<tr>
																			<th>
																				受理编号：
																			</th>
																			<td>
																				<s:textfield name="declaresn" id="declaresn" size="20"
																					cssClass="zc_input02" verify="受理编号|NotNull"></s:textfield>
																				<s:hidden name="method" id="method" value="query"></s:hidden>
																			</td>
																			<th>
																				证件编号：
																			</th>
																			<td>
																				<s:textfield name="zhengjiandaima" id="zhengjiandaima" size="20"
																					cssClass="zc_input02" verify="证件编号|NotNull"></s:textfield>
																			</td>
																			<td>
																				<input type="button" class="searchButton" value="查 询" onclick="searchBusi()">
																			</td>
																		</tr>
																	</table>
																</td>
															</tr>
														</table>
													</s:form>
												</td>
											</tr>
											<tr>
												<td width="100%" valign="top" align="center">
													<div id="qyInvestStep">
														<!--阶段一-->
														<div id="qyInvestStep1">
															<table border="0" cellpadding="0" cellspacing="0px"
																style="width: 100%;">
																<tr>
																	<td>
																		<img src="/images/investment/line_invest_split.png"
																			width="956px" height="22px" border="0" />
																	</td>
																</tr>
																<tr>
																	<td id="pageCountTd">
																		<jsp:include page="/WEB-INF/page/correction/resultPage.jsp"></jsp:include>
																	</td>
																</tr>
															</table>
														</div>

													</div>
												</td>
											</tr>
										</table>
									</td>
									<td width="2px" height="300px"
										style="background-image: url(/images/govpublic/shadow_R.png); background-repeat: repeat-y;">
										<img alt="" class="rich-spacer " height="1" id="form:j_id59"
											src="/a4j/g/3_3_2.SR1images/spacer.gif" width="2px" />
									</td>

								</tr>
								<tr>
									<td colspan="3">
										<img src="/images/govpublic/shadow_B.png" />
									</td>
								</tr>
							</table>

						</div>
						<!-- end: 内容面板 -->
					</td>

				</tr>
				<tr>
					<td align="left"></td>
				</tr>

			</table>
			<jsp:include page="/WEB-INF/page/public/bottom.jsp"></jsp:include>
		</div>
	</body>
</html>