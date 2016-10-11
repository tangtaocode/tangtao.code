<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
</head>
<body>
<table width="99%" border="0" cellpadding="0" cellspacing="0" id="userInfoTab" >
              <tr>
                <th>业务编号：</th>
                <td><s:textfield size="38" name="businessInfo.yxtywlsh" cssClass="zc_input01" readonly="true"></s:textfield>
                </td>
                <th>证件编号：</th>
                <td><s:textfield size="38" name="businessInfo.cardId" id="cardId" cssClass="zc_input01" readonly="true"></s:textfield>
                  </td>
              </tr>
              <tr>
                <th>事项名称：</th>
                <td><s:textfield size="38" name="businessInfo.spsxmc" cssClass="zc_input01" readonly="true"></s:textfield></td>
              <th>申请单位/人：</th>
                <td><s:textfield size="38" name="businessInfo.sqdwhsqrxm" cssClass="zc_input01" readonly="true"></s:textfield></td>
              </tr>
              <tr>
               
                <th>承办单位：</th>
                <td><s:textfield size="38" name="businessInfo.sljgmc" cssClass="zc_input01" readonly="true"></s:textfield></td>
                  <th>受&nbsp;理&nbsp;人：</th>
                <td><s:textfield size="38" name="businessInfo.employeedeptname" cssClass="zc_input01" readonly="true"></s:textfield></td>
              </tr>
              <tr>
               <th>申请时间：</th>
                <td><s:textfield size="38" name="businessInfo.slsj" cssClass="zc_input01" readonly="true"></s:textfield></td>
              <th>办理状态：</th>
                <td><s:textfield size="38" name="businessInfo.blzt" id="blzt" cssClass="zc_input01" readonly="true"></s:textfield>
               </td>
              </tr>
              
            <s:if test="appraiseBean.issenate==0&&businessInfo.isfinished==1">
            	<tr>
              <th>评&nbsp;&nbsp;议评级：</th>
              <td colspan="3">
              <s:hidden name="appraiseBean.senateguid" id="senateguid"></s:hidden>
              <s:hidden name="appraiseBean.declaresn" id="declaresn"></s:hidden>
              	<s:radio list="#{1:'满意',2:' 一般',3:'不满意'}" onclick="changeAppraise(this)" cssClass="zc_input02" id="satisfaction" name="appraiseBean.satisfaction" ></s:radio>
             </td>
             </tr>
             <tr style="display:none" id="otherYawp">
              <th>不满意原因：</th>
              <td colspan="3">
              <s:radio list="#{1:'态度不好',2:' 业务不熟',3:'时间太长',4:'其他'}" cssClass="zc_input02" onclick="changeYawp(this)" id="yawp" name="appraiseBean.yawp" ></s:radio>
             </td>
             </tr>
             	<tr  style="display:none" id="noYawpdescribe">
             	<th>不满意原因内容：</th>
                <td colspan="3"><s:textarea name="appraiseBean.yawpdescribe" id="yawpdescribe" cols="93" rows="4" cssClass="zc_textarea"></s:textarea></td>
             	</tr>
              <tr >
                <th>您的建议：</th>
                <td colspan="3"><s:textarea name="appraiseBean.problem" id="problem" cols="93" rows="9" cssClass="zc_textarea"></s:textarea></td>
              </tr>
            <tr><td colspan="4" style="text-align:center;">
            <input type="button" class="buttonClass_1" value="评 价" onclick="doSubmit()">
            </td></tr>
            </s:if>
            <s:if test="appraiseBean.issenate==1&&businessInfo.isfinished==1">
            <tr>
              <th>评&nbsp;&nbsp;议：</th>
              <td colspan="3">
              <s:if test="appraiseBean.satisfaction==1">满意</s:if>
              <s:if test="appraiseBean.satisfaction==2">一般</s:if>
              <s:if test="appraiseBean.satisfaction==3">不满意</s:if>
             </td>
             </tr>
              <s:if test="appraiseBean.satisfaction==3">
               <tr >
              <th>不满意原因：</th>
              <td colspan="3">
               <s:if test="appraiseBean.yawp==1">态度不好</s:if>
               <s:if test="appraiseBean.yawp==2">业务不熟</s:if>
               <s:if test="appraiseBean.yawp==3">时间太长</s:if>
               <s:if test="appraiseBean.yawp==4"><s:property value="appraiseBean.yawpdescribe"/> </s:if>
             </td>
             </tr>
              </s:if>
              <tr  >
                <th>您的建议：</th>
                <td colspan="3">
                <s:property value="appraiseBean.problem"/>
                </td>
              </tr>
            </s:if>
            
          </table>
			</body>
</html>
<script type="text/javascript">
<!--
function changeAppraise(obj){
	if(obj.value=="3"){
		$("#otherYawp").css("display",'');
	}else{
		$("input[name=yawp]").attr("checked",false);
		$("#yawpdescribe").attr("value",'');
		$("#otherYawp").css("display",'none');
		$("#noYawpdescribe").css("display",'none');
	}
}
function changeYawp(obj){
	if(obj.value=="4"){
		$("#noYawpdescribe").css("display",''); 
	}else{
		$("#noYawpdescribe").css("display",'none');
		$("#yawpdescribe").attr("value",''); 
	}
}
function checkAppraiseData(){
	if($("input:radio[name='appraiseBean.satisfaction']:checked").val()==null){
		showInfo("请选择评价满意度。");
		return false;
	}
	if($("input:radio[name='appraiseBean.satisfaction']:checked").val()=="3"&&$("input:radio[name='appraiseBean.yawp']:checked").val()==null){
		showInfo("请选择不满意原因。");
		return false;
	}
	if($("input:radio[name='appraiseBean.yawp']:checked").val()=="4"&&$("#yawpdescribe").val()==""){
		showInfo("请填写其他不满意原因。");
		return false;
	}
	return true;
}
function doSubmit(){
	if(!checkAppraiseData())return false;
	if($("#problem").val()==""){
		showConfirm("您未填写对我们意见，是否确认提交？。",function(){
			subData();
		});
	}else{
		subData();
	}
}
function subData(){
	$.post("/interaction/interactionQuery.html",
		{'appraiseBean.senateguid':$("#senateguid").val(),
		'appraiseBean.declaresn':$("#declaresn").val(),
		'appraiseBean.satisfaction':$("input[name='appraiseBean.satisfaction']:checked").val(),
		'appraiseBean.yawp':$("input[name='appraiseBean.yawp']:checked").val(),
		'appraiseBean.yawpdescribe':$("#yawpdescribe").val(),
		'appraiseBean.problem':$("#problem").val(),
		'method':'doUpdate',
		'businessInfo.cardId':$("#cardId").val(),
		'businessInfo.blzt':$("#blzt").val()},
		function(data){
			if(data.message=="评价成功"){
				searchAppraise();	
			}
			showInfo(data.message);
		});
}
//-->
</script>
			