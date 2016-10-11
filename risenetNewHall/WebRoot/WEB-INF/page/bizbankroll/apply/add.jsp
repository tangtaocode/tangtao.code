<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<html>
<head>
<script type="text/javascript">
$(document).ready(function(){
	var	editor = KindEditor.create('#affirms',{
			resizeType : 1,
			allowFileManager : false,
			allowImageUpload : false,
			items : [
				'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
				'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
				'insertunorderedlist']
		});
	editor.html('<s:property value="applicationBean.enterprises_affirms" escape="false"/>');
		
	getEditorValue = function(){
		return editor.html();
	};
	
	isValueNull = function(){
		return editor.isEmpty();
	}
});
</script>
</head>
<body>
<s:form id="submitForm">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="BS_list">
	<tr>
        <th colspan="2">在线申报</th>
      </tr>
		<TR>
			<TD style="width:20%;text-align:right;" nowrap="nowrap">
			<s:hidden name="applicationBean.category_id" id="category_id"></s:hidden>
			<s:hidden name="applicationBean.category" id="category"></s:hidden>
			<s:hidden name="applicationBean.provision" id="provision"></s:hidden>
			<s:hidden name="applicationBean.pressor_state" id="pressor_state" value="1"></s:hidden>
			<s:hidden name="applicationBean.xmlxroot" id="xmlxroot"></s:hidden>
			<s:hidden name="applicationBean.guid" id="guid"></s:hidden>
			<s:hidden name="applicationBean.zgbmid" id="zgbmid"></s:hidden>
			<s:hidden name="applicationBean.slbh" id="slbh"></s:hidden>
			<s:hidden name="applicationBean.createtuser" id="createtuser"></s:hidden>
			<s:hidden name="applicationBean.sbztid" id="sbztid"></s:hidden>
			<s:hidden name="applicationBean.enterprises_affirms" id="enterprises_affirms"></s:hidden>
			申请资助类别：</TD>
			<TD>
				<s:property value="applicationBean.category"/>
			</TD>
		</TR>
		<TR>
			<TD style="width:20%;text-align:right;">申请资助依据条款：</TD>
			<TD style="width:80%;">
				<s:property value="applicationBean.according" escape="false"/>
			</TD>
		</TR>
		<TR>
			<TD style="width:20%;text-align:right;">申请资助项目名称：</TD>
			<TD style="width:80%;">
			<s:textfield  name="applicationBean.pro_name" id="pro_name" size="70" cssClass="zc_input02" verify="申请资助项目名称|NotNull" maxlength="1000"></s:textfield><span style="color:red">*</span> </TD>
		</TR>
		<TR>
			<TD style="width:20%;text-align:right;">申请资助项总额：</TD>
			<TD style="width:80%;">
			<s:textfield  name="applicationBean.sqje" onkeyup="restrictNumber(this,'2',2)" id="sqje" cssClass="zc_input02" verify="申请资助项总额|NotNull" maxlength="7"></s:textfield>万元<span style="color:red">*</span> </TD>
		</TR>
		<TR>
			<TD style="width:20%;text-align:right;">项目基本情况简介：</TD>
			<TD style="width:80%;">
			<s:textarea id="affirms" cssStyle="width:700px;height:200px;visibility:hidden;" verify="项目基本情况简介|NotNull" ></s:textarea><span style="color:red">*</span>
		</TR>
		</table>
</s:form>
</body>
</html>
