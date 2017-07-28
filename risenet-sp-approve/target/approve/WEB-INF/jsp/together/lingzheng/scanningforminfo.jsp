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
	$(function() {
		$
				.ajax({
					type : 'get',
					datatype : 'JSON',
					url : '${ctx}/scanning/basicinfo?doctypeguid=${doctypeguid}',
					success : function(result) {
						//			var html = "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr ><td class='title_center' height=\"35\"  align='center'>节点名称</td><td class='title_center'  align='center'>内容</td>"+
						//					 "<td class='title_center'  align='center'>节点名称</td><td class='title_center'  align='center'>内容</td></tr>";
						var html = "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"tableStyle\">";
						var str = "";
						var array = eval(result);

						for (var i = 0; i < array.length; i = i + 2) {
							str += "<tr><td height=\"35\" align='right' width='25%'>"
									+ array[i].INFOMEMO
									+ "&nbsp;&nbsp;</td><td  width='25%'><div align=\"left\"><input id='"+array[i].INFOCODE+"' class='textinput1' type='text' name='infocon' value=''></div></td>";
							if (i + 1 < array.length) {
								str += "<td  width='25%' height=\"35\" align='right'>"
										+ array[i + 1].INFOMEMO
										+ "&nbsp;&nbsp;</td><td  width='25%' ><div align=\"left\"><input id='"+array[i+1].INFOCODE+"' class='textinput1' type='text' name='infocon' value=''></div></td></tr>";
							} else {
								str += "<td  width='25%'>&nbsp;</td><td  width='25%'> &nbsp;</td></tr>";
							}
						}
						html = html + str + "</table>";

						$("#tab").append(html);
					}
				});
	});

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

		/* zhengbxx ="&zhengzbh="+document.all("zhengzbh").value;
		zhengbxx +="&zhengzyxq="+document.all("zhengzyxq").value;
		zhengbxx +="&fazrq="+document.all("fazrq").value;
		zhengbxx +="&fazdw="+document.all("fazdw").value;
		zhengbxx +="&zhengzztxm="+document.all("zhengzztxm").value;
		zhengbxx +="&zhucdz="+document.all("zhucdz").value;
		zhengbxx +="&jingydz="+document.all("jingydz").value;
		zhengbxx +="&zhengznr="+document.all("zhengznr").value;
		zhengbxx +="&zhengzlx="+document.all("zhengzlx").value; */

		//alert(zhengbxx);
		//获取证照基本信息项采集项
		var jsondata = "{";
		$("[name=infocon]").each(function() {
			jsondata += '"' + this.id + '":"' + this.value + '",';
		});
		//去掉最后一个"," 
		var reg = /,$/gi;
		jsondata = jsondata.replace(reg, "");

		jsondata += "}";
		//alert(jsondata);
		//ajax提交数据 
		var cjData = {
			"zhengzbh" : document.all("zhengzbh").value,
			"zhengzyxq" : document.all("zhengzyxq").value,
			"fazrq" : document.all("fazrq").value,
			"fazdw" : document.all("fazdw").value,
			"zhengzztxm" : document.all("zhengzztxm").value,
			"zhucdz" : document.all("zhucdz").value,
			"jingydz" : document.all("jingydz").value,
			"zhengznr" : document.all("zhengznr").value,
			"zhengzlx" : document.all("zhengzlx").value,
			"infocondata" : jsondata
		};
		$
				.post(
						"${ctx}/scanning/savecon?instanceGuid=${instanceGuid}&doctypeguid=${doctypeguid}",
						cjData,
						function(result) {
							if (result.success) {
								if(confirm('办结成功！现在去【扫描/领取】页面进行扫描和领取登记？')) {
									window.location.href="${ctx}/togetherWindow/readyScan";
								} else {
									window.location.href="${ctx}/togetherWindow/lingzhengIndex";
								}
							
								//top.frmright.closeFirstDialog();

								//window.location.href="${ctx}/togetherWindow/readyScan?approveitemname="+approveitemname;
								//window.location.href = "${ctx}/scanning/upData?id="+id+"&code="+code+"&name="+name+"&unit="+unit+"&time="+time+"&instanceguid="+instanceguid+"&VALIDITYPERIOD="+VALIDITYPERIOD;
							} else {
								Dialog.alert("办结失败！");
							}
						});

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
			action="${ctx}/scanning/savecon?instanceGuid=${instanceGuid}&jsondata=${jsondata}"
			failAlert="请将表单填写完整！">
			<table class="tableStyle" formMode="line" style="width:100%;height:100%;">

				<tr>
					<th colspan="4" align="center">${approveitemname}</th>
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
					<td width="12%"><div align="left">证照有效期</div></td>
					<td width="38%"><input style="width:70%;" type="text" name="zhengzyxq"
						class="date" /><span class="star">*</span></td>
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
					<td colspan="4"   id="tab"></td>
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