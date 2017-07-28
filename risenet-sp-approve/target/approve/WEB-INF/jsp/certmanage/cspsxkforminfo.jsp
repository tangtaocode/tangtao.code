<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>证件扫描</title>
<!--表单异步提交start-->
<script src="${ctx}/static/QUI/libs/js/form/form.js"
	type="text/javascript"></script>
<!--表单异步提交end-->
<!-- 日期控件start -->
<script type="text/javascript"
	src="${ctx}/static/QUI/libs/js/form/datePicker/WdatePicker.js"></script>
<!-- 日期控件end -->
<style type="text/css">
.textinput1 {
	background: #ffffff url("form/textinput_bg.jpg") repeat-x scroll left
		top;
	border-color: #a2b3bd;
	border-style: solid;
	border-width: 1px;
	color: #336699;
	height: 20px;
	line-height: 20px;
	width: 120px;
}
</style>
<script type="text/javascript">
	function savedoc() {
		var zhengbxx = "";
		//验证证照必须信息项采集项
		if (document.all("zhengzbh").value == "") {
			alert("请输入证照编码");
			document.all("zhengzbh").focus();
			return false;
		} else if (document.all("zhengzyxq").value == "") {
			alert("请输入证照有效期");
			document.all("zhengzyxq").focus();
			return false;
		} else if (document.all("fazrq").value == "") {
			alert("请输入发（出）证日期");
			document.all("fazrq").focus();
			return false;
		} else if (document.all("fazdw").value == "") {
			alert("请输入发证单位");
			document.all("fazdw").focus();
			return false;
		} else if (document.all("zhengzztxm").value == "") {
			alert("请输入证照主体姓名（法定代表人/自然人/主要负责人）");
			document.all("zhengzztxm").focus();
			return false;
		} else if (document.all("zhucdz").value == "") {
			alert("请输入注册地址");
			document.all("zhucdz").focus();
			return false;
		} else if (document.all("jingydz").value == "") {
			alert("请输入经营（生产）地址");
			document.all("jingydz").focus();
			return false;
		}
		
		var options={
			url:"${ctx}/scanning/saveconnew?instanceGuid=${instanceGuid}&doctypeguid=${doctypeguid}",  
	        type:'post',                    
	        data:null,
          	success:function(result) {
				if (result.success) {
					 parent.$("#documentForm").ajaxSubmit(
						{
							type : 'POST',
							dataType : 'json',
							url : '${ctx}/sp/document/complete',
							success : function(responseText,statusText, xhr,$form) {
								alert(responseText.msg);
								
								$.post('${ctx}/certificate/sendSms?processInstanceId='+ processInstanceId,
										'',
										function(senateResult) {},
										"text");
								if (responseText.success == true) {
									//办结完成跳转打印证照
									parent.$('#' + parent.frameID).window({'title':'打印证照'});
									var ctx="${ctx}";
									var printurl=ctx+'/sp/pdfFile/banJieZZ?SPinstanceId='+parent.window.SPinstanceId+'&processInstanceId='+parent.window.processInstanceId+"&processSerialNumber="+parent.window.processSerialNumber+"&taskId="+parent.window.taskId;
									location.href=printurl;
								}
								var processInstanceId = parent.processInstanceId;
								
							}
						}); 
						
				} else {
					alert("办结失败！");
				}
          	}
		};
		$("#form1").ajaxSubmit(options);
		

	}
	
</script>
<title>证照扫描</title>
</head>
<body>
	<div class="box1" style="width:100%;height:100%;overflow-y:scroll">

		<form name="form1" id="form1" method="post"
			failAlert="请将表单填写完整！" enctype="multipart/form-data">
			<table class="tableStyle" formMode="line" style="width:100%;height:100%;">

				<tr>
					<th colspan="4" align="center">${approveitemname}
					<input type="hidden" name="approveitemname" value="${approveitemname}"/>
					</th>
				</tr>
				<tr>
					<td  colspan="4" align="left"><div align="left"
							style="color: #0066CC;">
							<strong>证照必须信息项采集</strong>
						</div></td>
				</tr>

				<tr>
					<td width="20%"><div align="left">证照编号</div></td>
					<td width="30%"><input style="width:70%;" type="text" name="zhengzbh"
						value="${DECLARESN }" /><span class="star">*</span></td>
					<td width="20%"><div align="left">证照有效期</div></td>
					<td width="30%"><input style="width:70%;" type="text" name="zhengzyxq"
						class="date" value="${jzrq}"/><span class="star">*</span></td>
				</tr>
				<tr>
					<td ><div align="left">发（出）证日期</div></td>
					<td><input type="text" style="width:70%;" name="fazrq" class="date" value="${fazrq}"/><span
						class="star">*</span></td>
					<td ><div align="left">发证单位</div></td>
					<td ><input type="text" name="fazdw" style="width:70%;"
						value="${FAZDW }" /><span class="star">*</span></td>
				</tr>
				<tr>
					<td ><div align="left">
							证照主体姓名<br>（法定代表人/自然人/主要负责人）
						</div></td>
					<td><input type="text" name="zhengzztxm" value="${zhengzztxm}" style="width:70%;"/><span
						class="star">*</span></td>
					<td ><div align="left">注册地址</div></td>
					<td ><input type="text" name="zhucdz" value="${address}" style="width:70%;"
						class="validate[required]" /><span class="star">*</span></td>
				</tr>
				<tr>
					<td ><div align="left">经营（生产）地址</div></td>
					<td><input type="text" name="jingydz" value="${address}" style="width:70%;"
						class="validate[required]" /><span class="star">*</span></td>
					<td ><div align="left">证照类型</div></td>
					<td ><select name="zhengzlx" style="width:75%"><option
								value="1">许可证</option>
							<option value="2">营业证</option>
							<option value="3">营业执照</option>
							<option value="4">税务登记证</option>
							<option value="5">资格证</option>
							<option value="6">批准文件</option></select></td>
				</tr>
				<tr height="100">
					<td  colspan="1" ><div align="left">证照内容概要</div></td>
					<td  colspan="3" ><textarea name="zhengznr" style="width:100%;height:100%;"></textarea></td>
				</tr>

				<!-- 动态获取的证照基本信息项采集 -->
				<tr>
					<td colspan="4" align="left"><div align="left"
							style="color: #0066CC;">
							<strong>证照基本信息项采集</strong>
						</div></td>
				</tr>
				<%-- <tr>
					<td ><div align="left">排水许可内容</div></td>
					<td><input type="text" name="psxknr" value="${psxknr}" style="width:70%;"/><span
						class="star">*</span></td>
				</tr> --%>
				<tr>
					<td ><div align="left">排水用户名称</div></td>
					<td ><input type="text" name="psyhmc" value="${zhengzztxm}" style="width:70%;"
						class="validate[required]" /><span class="star">*</span></td>
					<td ><div align="left">排水用户管理单位</div></td>
					<td><input type="text" name="psyhgldw" value="" style="width:70%;"/><span
						class="star">*</span></td>
				</tr>
				<tr>
					<td ><div align="left">详细排水地址</div></td>
					<td colspan="3"><input type="text" name="xxpsdz" value="${address}" style="width:70%;"
						class="validate[required]" /><span class="star">*</span></td>
				</tr>
				<%-- <tr>
					<td ><div align="left">变更登记</div></td>
					<td><input type="text" name="bgdj" value="${bgdj}" style="width:70%;"/><span
						class="star">*</span></td>
				</tr> --%>
				<tr>
					<td ><div align="left">主键标识</div></td>
					<td><input type="text" name="nid" value="" style="width:70%;"/><span
						class="star">*</span></td>
					<td ><div align="left">排水许可单位</div></td>
					<td ><input type="text" name="psxkdw" value="" style="width:70%;"
						class="validate[required]" /><span class="star">*</span></td>
				</tr>
				<tr>
					<td ><div align="left" >发证日期</div></td>
					<td><input type="text" name="fzrq" value="${fzrq}" class="date" style="width:70%;"/><span
						class="star">*</span></td>
					<td ><div align="left">许可证序号</div></td>
					<td ><input type="text" name="zzbh" value="${zzbh}" style="width:70%;"
						class="validate[required]" /><span class="star">*</span></td>
				</tr>
				<tr>
					<td ><div align="left">排水总量</div></td>
					<td><input type="text" name="pszl" value="" style="width:70%;"/><span
						class="star">*</span></td>
					<td ><div align="left">排水口数量</div></td>
					<td ><input type="text" name="psksl" value="" style="width:70%;"
						class="validate[required]" /><span class="star">*</span></td>
				</tr>
				<tr>
				<td colspan="4" style="padding:0;">
				<table id="ld"  class="tableStyle">
				<caption style="text-align:left;">排水口信息(根据实际数量填写)</caption>
				<tr>
					<td style="width:10%"><div align="right">序号</div></td>
					<td style="width:30%;"><div align="center">接入路段</div></td>
					<td style="width:30%;align:center;"><div align="center">起止路段</div></td>
					<td style="width:15%;align:center;"><div align="center">接入管径</div></td>
					<td style="width:15%;align:center;"><div align="center">市政管径</div></td>
				</tr>
					<tr>
						<td><div align="right">1</div></td>
						<td><input type="text" name="jrld1" style="width:100%;"/></td>
						<td><input type="text" name="qzld1" style="width:100%;"/></td>
						<td><input type="text" name="jrgj1" style="width:100%;"/></td>
						<td><input type="text" name="szgj1" style="width:100%;"/></td>
					</tr>
					<tr>
						<td ><div align="right">2</div></td>
						<td ><input type="text" name="jrld2" style="width:100%;"/></td>
						<td><input type="text" name="qzld2" style="width:100%;"/></td>
						<td><input type="text" name="jrgj2" style="width:100%;"/></td>
						<td><input type="text" name="szgj2" style="width:100%;"/></td>
					</tr>
					<tr>
						<td ><div align="right">3</div></td>
						<td ><input type="text" name="jrld3" style="width:100%;"/></td>
						<td><input type="text" name="qzld3" style="width:100%;"/></td>
						<td><input type="text" name="jrgj3" style="width:100%;"/></td>
						<td><input type="text" name="szgj3" style="width:100%;"/></td>
					</tr>
					<tr>
						<td ><div align="right">4</div></td>
						<td ><input type="text" name="jrld4" style="width:100%;"/></td>
						<td><input type="text" name="qzld4" style="width:100%;"/></td>
						<td><input type="text" name="jrgj4" style="width:100%;"/></td>
						<td><input type="text" name="szgj4" style="width:100%;"/></td>
					</tr>
					<tr class="moreld">
						<td ><div align="right">5</div></td>
						<td ><input type="text" name="jrld5" style="width:100%;"/></td>
						<td><input type="text" name="qzld5" style="width:100%;"/></td>
						<td><input type="text" name="jrgj5" style="width:100%;"/></td>
						<td><input type="text" name="szgj5" style="width:100%;"/></td>
					</tr>
					<tr class="moreld">
						<td ><div align="right">6</div></td>
						<td ><input type="text" name="jrld6" style="width:100%;"/></td>
						<td><input type="text" name="qzld6" style="width:100%;"/></td>
						<td><input type="text" name="jrgj6" style="width:100%;"/></td>
						<td><input type="text" name="szgj6" style="width:100%;"/></td>
					</tr>
					<tr class="moreld">
						<td ><div align="right">7</div></td>
						<td ><input type="text" name="jrld7" style="width:100%;"/></td>
						<td><input type="text" name="qzld7" style="width:100%;"/></td>
						<td><input type="text" name="jrgj7" style="width:100%;"/></td>
						<td><input type="text" name="szgj7" style="width:100%;"/></td>
					</tr>
					<tr class="moreld">
						<td ><div align="right">8</div></td>
						<td ><input type="text" name="jrld8" style="width:100%;"/></td>
						<td><input type="text" name="qzld8" style="width:100%;"/></td>
						<td><input type="text" name="jrgj8" style="width:100%;"/></td>
						<td><input type="text" name="szgj8" style="width:100%;"/></td>
					</tr>
					
				</table>
				</td>
				</tr>
				
				
				<tr>
					<td colspan="4"  align="center" style="text-align: center;"><input
						type="button" onclick="savedoc();" value="出证办结" /> <input
						type="reset" value="重 置 " /></td>
				</tr>
			</table>

		</form>

	</div>


</body>

</html>