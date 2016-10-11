<%@page import="net.risesoft.beans.user.UserInfo"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<style type="text/css">
	.step{
		font-family:"微软雅黑";
		font-size:16px;
		float:left;
		padding:12px;
	}
	.f{
		color:#6688CC;
	}
	
</style>
</head>
<body>
	<table cellpadding="0" cellspacing="0" border="0" width="99%" id="cardTab0">
	<s:hidden id="stepNumber" value="1"></s:hidden>
		<tr><td style="text-align:left;">
		<div class="step f" id="step1"><img src="/images/lineservice/img_process01b.png">确认用户信息&nbsp;&nbsp; </div>
		<s:if test="guideFilTypeeList.size>0">
			<div class="step" id="step2"><img src="/images/lineservice/img_process02.png">选择事项类型&nbsp;&nbsp;</div>
			<s:if test="formList.size>0">
				<div class="step" id="step3"><img src="/images/lineservice/img_process03.png">填写申请表&nbsp;&nbsp;</div>
				<div class="step" id="step4"><img src="/images/lineservice/img_process04.png">申请材料&nbsp;&nbsp;</div>
				<div class="step" id="step5"><img src="/images/lineservice/img_process05.png">完成申报</div>
			</s:if>
			<s:if test="formList.size<1">
				<div class="step" id="step3"><img src="/images/lineservice/img_process03.png">申请材料&nbsp;&nbsp;</div>
				<div class="step" id="step4"><img src="/images/lineservice/img_process04.png">完成申报</div>
			</s:if>
		</s:if>
		<s:if test="guideFilTypeeList.size<1">
			<s:if test="formList.size>0">
			<div class="step" id="step2"><img src="/images/lineservice/img_process02.png">填写申请表&nbsp;&nbsp;</div>
			<div class="step" id="step3"><img src="/images/lineservice/img_process03.png">申请材料&nbsp;&nbsp;</div>
			<div class="step" id="step4"><img src="/images/lineservice/img_process04.png">完成申报</div>
		</s:if>
		<s:if test="formList.size<1">
			<div class="step" id="step2"><img src="/images/lineservice/img_process02.png">申请材料&nbsp;&nbsp;</div>
			<div class="step" id="step3"><img src="/images/lineservice/img_process03.png">完成申报</div>
		</s:if>
		</s:if>
		</td></tr>
			<tr><td id="stepPageCount">
						<s:if test="#session.loginUser.usertype==1">
							<jsp:include page="/WEB-INF/page/user/approveitm/viewPersonal.jsp"></jsp:include>
						</s:if>
						<s:elseif test="#session.loginUser.usertype==2">
							<jsp:include page="/WEB-INF/page/user/approveitm/viewCompanyInfo.jsp"></jsp:include>
						</s:elseif>
						<s:else>
							<jsp:include page="/WEB-INF/page/user/approveitm/viewOrganizational.jsp"></jsp:include>
						</s:else>
					</td>
			</tr>
			<tr><td style="text-align:center;"><input type="hidden" id="state" value="<s:property value="state"/>">
				<s:if test="guideFilTypeeList.size>0">
					<input type="button" value="下一步"  id="nextStepBut" class="stepButton" onclick="doStep('3','2')"/>&nbsp;&nbsp;
				</s:if>
				<s:if test="guideFilTypeeList.size<1">
					<s:if test="formList.size>0">
						<input type="button" value="下一步"  id="nextStepBut" class="stepButton" onclick="doStep('2','2')"/>&nbsp;&nbsp;
					</s:if>
					<s:if test="formList.size<1">
						<input type="button" value="下一步"  id="nextStepBut" class="stepButton" onclick="doStep('1','2')"/>&nbsp;&nbsp;
					</s:if>
				</s:if>
			</td></tr>
	</table>
</body>
</html>