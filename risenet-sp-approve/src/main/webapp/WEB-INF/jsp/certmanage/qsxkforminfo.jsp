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
		//alert("数据验证成功");
		var options={
			url:"${ctx}/scanning/saveconnew?instanceGuid=${instanceGuid}&doctypeguid=${doctypeguid}",  
	        type:'post',                    
	        data:null,
          	success:function(result) {
				if (result.success) {
					parent.submitForm();
					//parent.executeSave();
					parent.$("#documentForm").ajaxSubmit(
						{
							type : 'POST',
							dataType : 'json',
							url : '${ctx}/sp/document/complete',
							success : function(responseText,statusText, xhr,$form) {
								alert(responseText.msg);
								if (responseText.success == true) {
									//办结完成跳转打印办结单
									parent.$('#' + parent.frameID).window({'title':'打印办结单'});
									var ctx="${ctx}";
									var printurl=ctx+'/sp/pdfFile/banJieDanForm?SPinstanceId='+parent.window.SPinstanceId+'&processInstanceId='+parent.window.processInstanceId+"&processSerialNumber="+parent.window.processSerialNumber+"&taskId="+parent.window.taskId;
									location.href=printurl;
								}
								var processInstanceId = parent.processInstanceId;
								$.post('${ctx}/certificate/sendSms?processInstanceId='+ processInstanceId,
										'',
										function(senateResult) {},
										"text");
								}
						});
				} else {
					alert("办结失败！");
				}
          	}
		};
		printBanJieDan();
		$("#form1").ajaxSubmit(options);
		//document.forms["form1"].submit(); 

	}
	/* $(function(){
	 $.post("${ctx}/scanning/getSelection",{},function(result){
	 // var $selbox = $('<select  prompt="请选择部门"></select>');
	 //var selData={"list":[{"value":"1","key":"员工1"},{"value":"2","key":"员工2"}]};
	 // console.info(result.info);
	 //$selbox.data("data",result.info);
	 // $("#testBtn3").after($selbox);   

	 // $("#testBtn3").after("<br/>");

	 // $selbox.render(); 
	 $("#sel-1-2").data("data",result.info);
	 $("#sel-1-2").render(); 
	 },"json");
	 })
	 function onchangeHandler(obj){
	 console.info($(obj).val());
	 //Dialog.alert("您选择了：" + $(obj).val());
	 $.post("${ctx}/scanning/getDataSource",{},function(result){
	
	 },"json");
	 } */
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
				<tr>
					<td ><div align="left">主键标识</div></td>
					<td><input type="text" name="nid" value="${nid}" style="width:70%;"/><span
						class="star">*</span></td>
					<td ><div align="left">证照序号</div></td>
					<td ><input type="text" name="zzbh" value="${zzbh}" style="width:70%;"
						class="validate[required]" /><span class="star">*</span></td>
				</tr>
				<tr>
					<td ><div align="left">取水权人名称</div></td>
					<td><input type="text" name="qsqrmc" value="${qsqrmc}" style="width:70%;"/><span
						class="star">*</span></td>
					<td ><div align="left">法定代表人</div></td>
					<td ><input type="text" name="fddbr" value="${fddbr}" style="width:70%;"
						class="validate[required]" /><span class="star">*</span></td>
				</tr>
				<tr>
					<td ><div align="left">取水地点</div></td>
					<td><input type="text" name="qsdd" value="${qsdd}" style="width:70%;"/><span
						class="star">*</span></td>
					<td ><div align="left">取水方式</div></td>
					<td ><input type="text" name="qsfs" value="${qsfs}" style="width:70%;"
						class="validate[required]" /><span class="star">*</span></td>
				</tr>
				<tr>
					<td ><div align="left">取水量</div></td>
					<td><input type="text" name="qsl" value="${qsl}" style="width:70%;"/><span
						class="star">*</span></td>
					<td ><div align="left">取水用途</div></td>
					<td ><input type="text" name="qsyt" value="${qsyt}" style="width:70%;"
						class="validate[required]" /><span class="star">*</span></td>
				</tr>
				<tr>
					<td ><div align="left">水源类型</div></td>
					<td><input type="text" name="sylx" value="${sylx}" style="width:70%;"/><span
						class="star">*</span></td>
					<td ><div align="left">退水地点</div></td>
					<td ><input type="text" name="tsdd" value="${tsdd}" style="width:70%;"
						class="validate[required]" /><span class="star">*</span></td>
				</tr>
				<tr>
					<td ><div align="left">退水方式</div></td>
					<td><input type="text" name="tsfs" value="${tsfs}" style="width:70%;"/><span
						class="star">*</span></td>
					<td ><div align="left">退水量</div></td>
					<td ><input type="text" name="tsl" value="${tsl}" style="width:70%;"
						class="validate[required]" /><span class="star">*</span></td>
				</tr>
				<tr>
					<td ><div align="left">退水水质要求</div></td>
					<td><input type="text" name="tssz" value="${tssz}" style="width:70%;"/><span
						class="star">*</span></td>
					<td ><div align="left">证照有效期</div></td>
					<td ><input type="text" name="zzyxq" value="${zzyxq}" style="width:70%;"
						class="validate[required]" /><span class="star">*</span></td>
				</tr>
				<tr>
					<td ><div align="left">退水水质要求</div></td>
					<td><input type="text" name="批准日期" value="${pzrq}" style="width:70%;"/><span
						class="star">*</span></td>
					<td ><div align="left"></div></td>
					<td ></td>
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