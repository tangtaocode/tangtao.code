<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
</head>
<body>
<table width="99%" border="0" cellpadding="0" cellspacing="0" id="userInfoTab" >
              <tr>
                <th>ҵ���ţ�</th>
                <td><s:textfield size="38" name="businessInfo.yxtywlsh" cssClass="zc_input01" readonly="true"></s:textfield>
                </td>
                <th>֤����ţ�</th>
                <td><s:textfield size="38" name="businessInfo.cardId" id="cardId" cssClass="zc_input01" readonly="true"></s:textfield>
                  </td>
              </tr>
              <tr>
                <th>�������ƣ�</th>
                <td><s:textfield size="38" name="businessInfo.spsxmc" cssClass="zc_input01" readonly="true"></s:textfield></td>
              <th>���뵥λ/�ˣ�</th>
                <td><s:textfield size="38" name="businessInfo.sqdwhsqrxm" cssClass="zc_input01" readonly="true"></s:textfield></td>
              </tr>
              <tr>
               
                <th>�а쵥λ��</th>
                <td><s:textfield size="38" name="businessInfo.sljgmc" cssClass="zc_input01" readonly="true"></s:textfield></td>
                  <th>��&nbsp;��&nbsp;�ˣ�</th>
                <td><s:textfield size="38" name="businessInfo.employeedeptname" cssClass="zc_input01" readonly="true"></s:textfield></td>
              </tr>
              <tr>
               <th>����ʱ�䣺</th>
                <td><s:textfield size="38" name="businessInfo.slsj" cssClass="zc_input01" readonly="true"></s:textfield></td>
              <th>����״̬��</th>
                <td><s:textfield size="38" name="businessInfo.blzt" id="blzt" cssClass="zc_input01" readonly="true"></s:textfield>
               </td>
              </tr>
              
            <s:if test="appraiseBean.issenate==0&&businessInfo.isfinished==1">
            	<tr>
              <th>��&nbsp;&nbsp;��������</th>
              <td colspan="3">
              <s:hidden name="appraiseBean.senateguid" id="senateguid"></s:hidden>
              <s:hidden name="appraiseBean.declaresn" id="declaresn"></s:hidden>
              	<s:radio list="#{1:'����',2:' һ��',3:'������'}" onclick="changeAppraise(this)" cssClass="zc_input02" id="satisfaction" name="appraiseBean.satisfaction" ></s:radio>
             </td>
             </tr>
             <tr style="display:none" id="otherYawp">
              <th>������ԭ��</th>
              <td colspan="3">
              <s:radio list="#{1:'̬�Ȳ���',2:' ҵ����',3:'ʱ��̫��',4:'����'}" cssClass="zc_input02" onclick="changeYawp(this)" id="yawp" name="appraiseBean.yawp" ></s:radio>
             </td>
             </tr>
             	<tr  style="display:none" id="noYawpdescribe">
             	<th>������ԭ�����ݣ�</th>
                <td colspan="3"><s:textarea name="appraiseBean.yawpdescribe" id="yawpdescribe" cols="93" rows="4" cssClass="zc_textarea"></s:textarea></td>
             	</tr>
              <tr >
                <th>���Ľ��飺</th>
                <td colspan="3"><s:textarea name="appraiseBean.problem" id="problem" cols="93" rows="9" cssClass="zc_textarea"></s:textarea></td>
              </tr>
            <tr><td colspan="4" style="text-align:center;">
            <input type="button" class="buttonClass_1" value="�� ��" onclick="doSubmit()">
            </td></tr>
            </s:if>
            <s:if test="appraiseBean.issenate==1&&businessInfo.isfinished==1">
            <tr>
              <th>��&nbsp;&nbsp;�飺</th>
              <td colspan="3">
              <s:if test="appraiseBean.satisfaction==1">����</s:if>
              <s:if test="appraiseBean.satisfaction==2">һ��</s:if>
              <s:if test="appraiseBean.satisfaction==3">������</s:if>
             </td>
             </tr>
              <s:if test="appraiseBean.satisfaction==3">
               <tr >
              <th>������ԭ��</th>
              <td colspan="3">
               <s:if test="appraiseBean.yawp==1">̬�Ȳ���</s:if>
               <s:if test="appraiseBean.yawp==2">ҵ����</s:if>
               <s:if test="appraiseBean.yawp==3">ʱ��̫��</s:if>
               <s:if test="appraiseBean.yawp==4"><s:property value="appraiseBean.yawpdescribe"/> </s:if>
             </td>
             </tr>
              </s:if>
              <tr  >
                <th>���Ľ��飺</th>
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
		showInfo("��ѡ����������ȡ�");
		return false;
	}
	if($("input:radio[name='appraiseBean.satisfaction']:checked").val()=="3"&&$("input:radio[name='appraiseBean.yawp']:checked").val()==null){
		showInfo("��ѡ������ԭ��");
		return false;
	}
	if($("input:radio[name='appraiseBean.yawp']:checked").val()=="4"&&$("#yawpdescribe").val()==""){
		showInfo("����д����������ԭ��");
		return false;
	}
	return true;
}
function doSubmit(){
	if(!checkAppraiseData())return false;
	if($("#problem").val()==""){
		showConfirm("��δ��д������������Ƿ�ȷ���ύ����",function(){
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
			if(data.message=="���۳ɹ�"){
				searchAppraise();	
			}
			showInfo(data.message);
		});
}
//-->
</script>
			