<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<%@page import="net.risesoft.utils.base.DateFormatUtil"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<title>深圳市龙岗区住房与建设局产业转型升级专项资金申报系统</title>
	<meta name="keywords"
			content="深圳市龙岗区住房与建设局产业转型升级专项资金申报系统,产业转型,专项资金,产业服务,深圳市龙岗区住房与建设局产业服务,深圳市龙岗区住房与建设局" />
		<meta name="description" content="产业转型首页" />
<meta name="robots" content="all" />
<head>
<link href="/css/main.css" type="text/css" rel="stylesheet" />
<link href="/css/bizbankroll.css" type="text/css" rel="stylesheet" />
<link href="/css/login-box.css" type="text/css" rel="stylesheet" />
<link href="/css/initialize.css" type="text/css" rel="stylesheet" />
<link href="/css/appGuid.css" type="text/css" rel="stylesheet" />
<script src="/js/Scripts/jquery.min.js" type="text/javascript" ></script>
<script src="/js/businessJS/bizbankroll.js" type="text/javascript" ></script>
<script src="/js/validation.js" type="text/javascript" ></script>
<script src="/js/userService.js" type="text/javascript" ></script>
<script type="text/javascript" src="/js/kindeditor/kindeditor-min.js"></script>
<script type="text/javascript" src="/js/kindeditor/zh_CN.js"></script>
<script type="text/javascript" src="/js/zDialog/risenetDialog.js"></script>  
</head>
<body>
<jsp:include page="/WEB-INF/page/public/topMenu.jsp">
<jsp:param name="menu" value="3"/>
</jsp:include>
<div class="BS_wrap">
<div class="BS_content" id="BS_content_div">
<div class="clean" style="height:10px;"></div>
<jsp:include page="/WEB-INF/page/public/menu.jsp">
<jsp:param name="menu" value="4"/>
</jsp:include>
   </div>
   <!-- 查询 -->
    <table border="0" cellpadding="0" cellspacing="15px" style="width: 98%;">
		<tr>
			<td align="left">
				<span style="font-size: 18pt;">产业扶持>在线申报</span>
			</td>
			<td align="right">
				&nbsp;
			</td>
		</tr>
	</table>
  	<div class="busi_dotop">&nbsp;</div>
      <div class="busi_domiddle">
       <div class="more_domiddlein">
		<div id="resultDiv" >
			<div style="text-align:left;padding-left:20px;">
				<div class="BS_title2">
					<span class="BS_titletxt"><s:property value="projectTypeInfo.typeName"/>在线申报</span>
				</div> 
				<div class="BS_title6" style="color:#b2b2b2">申请受理机关：<s:property value="projectTypeInfo.departName"/></div>
				<div class="BS_titleass r div_l">申请时间:<%=DateFormatUtil.parseToString(new Date()) %>  </div>
				<div class="noborder div_lf">
					申请人：<s:property value="#session.loginUser.personUser.true_name"/>
							<s:property value="#session.loginUser.companyUser.ename"/>
				</div>
			</div>
		</div>
		<div class="clean"></div>
		<table cellpadding="0" cellspacing="0" border="0" width="98%">
			<tr><td width="10px;">&nbsp;</td>
				<td width="150px;"><a class="tdCardOn" id="card1" href="javascript:changeCard(2,1);void(0)"><span>在线申报</span></a></td>
				<td width="150px;"><a class="tdCardOff" id="card2" href="javascript:changeCard(2,2);void(0)"><span>申报指南</span></a></td>
				<td>&nbsp;</td>
			</tr>
		</table>
		<div class="clean"></div>
		<div id="qyInvestStep1" style="display:">
			<table border="0" cellpadding="0" cellspacing="0px" style="width:100%;">
				<tr> 
					<td align="left" style="padding-left:60px;padding-top:20px">
						<img src="/images/investment/tab_invest_arrow.png" width="23px" height="10px" border="0" /> 
					</td>
				</tr>
				<tr>
					<td>
						<img src="/images/investment/line_invest_split.png" width="956px" height="22px" border="0" />
					</td>
				</tr>
				<tr>
					<td style="text-align:center;">
						<s:hidden name="rootGuid" id="rootGuid"></s:hidden>
						<s:hidden name="id" id="id"></s:hidden>
						<table cellpadding="0" cellspacing="0" border="0" width="99%" id="cardTab0">
							<tr><td style="text-align:left;">
							<div class="step f" id="step1"><img src="/images/lineservice/img_process01b.png">确认用户信息&nbsp;&nbsp; </div>
							<div class="step" id="step2"><img src="/images/lineservice/img_process02.png">申报信息&nbsp;&nbsp;</div>
							<div class="step" id="step3"><img src="/images/lineservice/img_process03.png">上传材料&nbsp;&nbsp;</div>
							<div class="step" id="step4"><img src="/images/lineservice/img_process04.png">完成申报</div>
							</td></tr>
								<tr><td id="userInfoCount">
											<s:if test="#session.loginUser.usertype==1">
											<jsp:include page="/WEB-INF/page/user/bizbankroll/viewPersonal.jsp"></jsp:include>
											</s:if>
											<s:elseif test="#session.loginUser.usertype==2">
											<jsp:include page="/WEB-INF/page/user/bizbankroll/viewCompanyInfo.jsp"></jsp:include>
											</s:elseif>
											<s:else>
											<jsp:include page="/WEB-INF/page/user/bizbankroll/viewOrganizational.jsp"></jsp:include>
											</s:else>
										</td>
										<td style="display:none" id="stepPageCount">
										</td>
								</tr>
										<tr><td style="text-align:center;" id="stepButton">
												<input type="button" id="nextStepBut" value="下一步" class="stepButton" onclick="doStep('2')"/>&nbsp;&nbsp;
								</td></tr>
						</table>
					</td>
				</tr>
			</table></div>
		<div id="qyInvestStep2" style="display:none">
			<table border="0" cellpadding="0" cellspacing="0px" style="width: 100%;">
				<tr> 
					<td align="left" style="padding-left:225px;padding-top:20px">
						<img src="/images/investment/tab_invest_arrow.png" width="23px" height="10px" border="0" /> 
					</td>
				</tr>
				<tr>
					<td>
						<img src="/images/investment/line_invest_split.png" width="956px" height="22px" border="0" />
					</td>
				</tr>
				<tr>
					<td style="text-align:center;">
					<jsp:include page="/WEB-INF/page/bizbankroll/apply/guideInfo.jsp"></jsp:include>
					</td>
				</tr>
			</table></div>
		</div>
       	</div>
      </div>
	<div class="busi_dobottom">&nbsp;</div>
  	
   <div class="clean" style="height:10px;"></div>
   <jsp:include page="/WEB-INF/page/public/bottom.jsp"></jsp:include>    
</body>
</html>

  


