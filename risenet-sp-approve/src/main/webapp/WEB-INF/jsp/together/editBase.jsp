<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title></title>
	<META http-equiv=Content-Type content="text/html; charset=utf-8">
	<script type="text/javascript" src="${ctx}/static/jquery/form/jquery.form.js"></script>
	<style type="text/css">
		.font_beizhu {font-family: "宋体";font-size: 13px;color: #993300;}
	</style>
	<script language="javascript">
		//保存表单数据
		function saveForm(method){
			if(!validateData()){
				return false;
			}
			if(method=='done'){
				$("#handleStatus").val("办结");
			}
			var ytjids = "";
			var xbqids = "";
			var xbzids = "";
			if($('#state').val() == "1"){
				parent.window.frames[1].moren();
				ytjids = parent.window.frames[1].document.getElementById('ytjs').value;
				xbqids = parent.window.frames[1].document.getElementById('xbqs').value;
				xbzids = parent.window.frames[1].document.getElementById('xbzs').value;
			}
			$('#ytjids').val(ytjids);
			$('#xbqids').val(xbqids);
			$('#xbzids').val(xbzids);
			//parent.window.frames[0].document.getElementById('handleStatus').value = "";
			//var status = $("#handleStatus").val();
			$('#saveForm').ajaxSubmit({
				type : 'POST',
				dataType : 'json',
				url : '${ctx}/togetherWindow/saveReceive',
				success : function(responseText, statusText, xhr, $form) {
					if(responseText.msg=="1"){
						if(method=='save'){
							alert("保存成功");
						}
						if(method=='send'){
							parent.departmentChoise();//打开部门选择框
						}
						if(method=='done'){
							alert("办结成功");
						}
					}else{
						alert("保存失败");
					}
				},
				error:function(){
					alert("保存失败");
				}
			});
		}
		
		// 验证数据
		function validateData() {
			if ($('#approveItemName').val() == "") {
				alert("审批事项名称不能为空!");
				return false;
			}
			if ($('#xiangmumingcheng').val() == "") {
				alert("项目名称不能为空!");
				return false;
			}
			if ($('#declarerPerson').val() == "") {
				alert("申请单位/人不能为空!");
				return false;
			}
			if (!isChinaOrLett($('#declarerPerson').val())) {
				alert("申请人不能填写数字或特殊符号！");
				return false;
			}
			if ($('#zhengjiandaima').val() == "") {
				alert("证件代码不能为空!");
				return false;
			}
			if ($('#declareType').val() == "") {
				alert("申请人类型不能为空!");
				return false;
			}
			if ($('#declarerlxr').val() == "") {
				alert("联系人不能为空!");
				return false;
			}
			if (!isChinaOrLett($('#declarerlxr').val())) {
				alert("联系人人不能填写数字或特殊符号！");
				return false;
			}
			if ($('#declarerlxrIdtype').val() == "") {
				alert("联系人证件类型不能为空!");
				return false;
			}
			if ($('#declarerlxrid').val() == "") {
				alert("联系人证件号码不能为空!");
				return false;
			}
			if ($('#declarerMobile').val() == "") {
				alert("手机号码不能为空!");
				return false;
			}
			if (!isNumber($('#declarerMobile').val())) {
				alert("手机号码格式错误！");
				return false;
			}
			return true;
		}
		
		//判断是否是汉字、字母组成 
		function isChinaOrLett(s){
			var regu = "^[a-zA-Z\u4e00-\u9fa5]+$"; 
			var re = new RegExp(regu); 
			if (re.test(s)) { 
				return true;
			}else{ 
				return false;
			} 
		}
		
		//判断是否数字组成 
		function isNumber(s){
			var regu = "^[0-9]*$"; 
			var re = new RegExp(regu); 
			if (re.test(s)) { 
				return true;
			}else{ 
				return false;
			} 
		}
		
		$(function(){
			$("#declareType").val("${office.declareType}");
			$("#declarerlxrIdtype").val("${office.declarerlxrIdtype}");
		})
	</script>
</head>
<body>
<div align="center" class="mydiv_table" height="400" style="margin-top: 10;margin-bottom:10;display: block;" >
<form id="saveForm">
<input id="state" type="hidden" name="state" />
<input id="ytjids" type="hidden" name="declareAnnexGuids2" />
<input id="xbqids" type="hidden" name="notDeclareAnnexGuids2" />
<input id="xbzids" type="hidden" name="bhgsbcl2" />
<input id="handleStatus" type="hidden" name="handleStatus" value="未受理"/>
<input type="hidden" name="sblsh" value="${office.sblsh }"/>
<input id="xbzids" type="hidden" name="dataFromtype" value="2"/>
<input id="tjfs" type="hidden" name="tjfs" value=""/>

<table border="0" cellpadding="0" cellspacing="0" height="300" style="border-top: rgb(127,127,127) 1px solid; border-right: rgb(127,127,127) 1px solid; border-bottom: rgb(127,127,127) 1px solid; border-left: rgb(127,127,127) 1px solid; border-image: none">
	<caption><font face="黑体" size="6">基本信息处理表</font></caption>
	<input id="guid" mindex="0" name="guid" type="hidden" value="${office.guid }"/>
	<input id="approveitemguid" name="approveitemguid" type="hidden" value="${office.approveitemguid }"/>
	<tbody>
		<tr>
			<td align="center" class="td_lt_v" height="35" width="150">业务编号</td>
			<td align="left" class="td_lt_v">&nbsp;&nbsp;<input id="declaresn" mindex="1" name="declaresn" value="${office.declaresn }" style="width: 245px; " type="text" /></td>
		</tr>
		<tr>
			<td align="center" class="td_lt_v" height="35" width="150">事项名称<span class="font_beizhu">*</span></td>
			<td align="left" class="td_lt_v">&nbsp;&nbsp;<input  id="approveItemName" mindex="2" name="approveItemName"  value="${office.approveItemName }"  style="width: 245px;" type="text" /></td>
		</tr>
		<tr>
			<td align="center" class="td_lt_v" height="35" width="150">项目名称<span class="font_beizhu">*</span></td>
			<td align="left" class="td_lt_v">&nbsp;&nbsp;<input  id="xiangmumingcheng" mindex="3" name="xiangmumingcheng" value="${office.xiangmumingcheng }" style="width: 245px;" type="text" /></td>
		</tr>
		<tr>
			<td align="center" class="td_lt_v" height="35" width="150">申请单位/人<span class="font_beizhu">*</span></td>
			<td align="left" class="td_lt_v">&nbsp;&nbsp;<input  id="declarerPerson" mindex="5" name="declarerPerson"  value="${office.declarerPerson }"  style="width: 245px;" type="text" />  <span class="font_beizhu">注：不能填写数字或特殊符号</span></td>
		</tr>
		<tr>
			<td align="center" class="td_lt_v" height="35" width="150">单位地址/住址</td>
			<td align="left" class="td_lt_v">&nbsp;&nbsp;<input id="address" mindex="7" name="address" value="${office.address }" style="width: 245px;" type="text" /></td>
		</tr>
		<tr>
			<td align="center" class="td_lt_v" height="35" width="150">证件代码<span class="font_beizhu">*</span></td>
			<td align="left" class="td_lt_v">&nbsp;&nbsp;<input  id="zhengjiandaima" mindex="6" name="zhengjiandaima" value="${office.zhengjiandaima }" style="width: 245px;" type="text" /></td>
		</tr>
		<tr>
			<td align="center" class="td_lt_v" height="35" width="150"></td>
			<td align="left" class="td_lt_v">&nbsp;&nbsp;<span class="font_beizhu">注：申请单位填组织机构代码，申请人填身份证号码。</span></td>
		</tr>
		<tr>
			<td align="center" class="td_lt_v" height="35" width="150">申请人类型<span class="font_beizhu">*</span></td>
			<td align="left" class="td_lt_v">&nbsp;&nbsp;<select  id="declareType" mindex="15" name="declareType" value="${office.declareType }"><option selected="selected" value="">--请选择--</option><option value="1">个人</option><option value="2">企业（机关单位）</option><option value="9">其他</option></select></td>
		</tr>
		<tr>
			<td align="center" class="td_lt_v" height="35" width="150">联系人<span class="font_beizhu">*</span></td>
			<td align="left" class="td_lt_v">&nbsp;&nbsp;<input  id="declarerlxr" mindex="8" name="declarerlxr" value="${office.declarerlxr }" style="width: 245px;" type="text" /></td>
		</tr>
		<tr>
			<td align="center" class="td_lt_v" height="35" width="150">联系人证件类型<span class="font_beizhu">*</span></td>
			<td align="left" class="td_lt_v">&nbsp;&nbsp;<select  id="declarerlxrIdtype" mindex="16" name="declarerlxrIdtype" value="${office.declarerlxrIdtype }"><option selected="selected" value="">--请选择--</option><option value="10">身份证</option><option value="11">军官证</option><option value="12">士兵证</option><option value="13">警官证</option><option value="14">港澳居民来往内地通行证</option><option value="15">台湾居民来往大陆通行证</option><option value="16">香港身份证</option><option value="17">澳门身份证</option><option value="18">台湾身份证</option><option value="20">护照</option><option value="40">其他有效个人身份证件</option><option value="50">组织机构代码证</option><option value="51">营业执照</option><option value="52">事业单位登记证书</option><option value="53">社团登记证书</option><option value="54">民办非企业单位登记证书</option><option value="55">工会法人资格证书</option><option value="60">税务登记证</option><option value="80">其他有效机构身份证件</option></select></td>
		</tr>
		<tr>
			<td align="center" class="td_lt_v" height="35" width="150">联系人证件号码<span class="font_beizhu">*</span></td>
			<td align="left" class="td_lt_v">&nbsp;&nbsp;<input  id="declarerlxrid" mindex="9" name="declarerlxrid" value="${office.declarerlxrid }" style="width: 245px;" type="text" /></td>
		</tr>
		<tr>
			<td align="center" class="td_lt_v" height="35" width="150">联系电话</td>
			<td align="left" class="td_lt_v">&nbsp;&nbsp;<input  id="declarerTel" mindex="10" name="declarerTel" value="${office.declarerTel }" style="width: 245px;" type="text" /></td>
		</tr>
		<tr>
			<td align="center" class="td_lt_v" height="35" width="150">手机<span class="font_beizhu">*</span></td>
			<td align="left" class="td_lt_v">&nbsp;&nbsp;<input  id="declarerMobile" mindex="11" name="declarerMobile" value="${office.declarerMobile }" style="width: 245px;" type="text" /></td>
		</tr>
		<tr>
			<td align="center" class="td_lt_v" height="35" width="150">传真</td>
			<td align="left" class="td_lt_v">&nbsp;&nbsp;<input id="declarerfax" mindex="12" name="declarerfax" value="${office.declarerfax }"  style="width: 245px; " type="text" /></td>
		</tr>
		<tr>
			<td align="center" class="td_lt_v" height="35" width="150">经办人</td>
			<td align="left" class="td_lt_v">&nbsp;&nbsp;<input id="employeedeptname" mindex="13" name="employeedeptname" value="${employeedeptname }" style="width: 245px" type="text" /></td>
		</tr>
		<tr>
			<td align="center" class="td_lt_v" height="35" width="150">申请日期</td>
			<td align="left" class="td_lt_v">&nbsp;&nbsp;<input  id="dateTime" mindex="14" name="dateTime" value="${declareDateTime}" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'});" style="width: 245px" type="text" /></td>
		</tr>
		<tr>
		</tr>
	</tbody>
</table>
</form></div>
</body>
</html>
