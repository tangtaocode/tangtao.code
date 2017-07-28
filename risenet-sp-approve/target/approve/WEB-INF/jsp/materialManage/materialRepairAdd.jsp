<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新增维修</title>
<!-- 日期控件start -->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/form/datePicker/WdatePicker.js"></script>          
<script type="text/javascript">
function savedoc(){
	if (document.all("materialguid").value == "") {
		alert("请输选择物料名称");
		document.all("materialguid").focus();
		return false;
	}else if (document.all("repair_num").value == "") {
		alert("请输入物领用数量");
		document.all("repair_num").focus();
		return false;
	}else if (!validateInput(document.all("repair_num").value, "^[0-9]*$")){
        Dialog.alert("维修数量必须是数字！",null,null,null,20);
        document.all("repair_num").focus();
        return false
   }else if (document.all("repair_funds").value == "") {
		alert("请输入维修费用");
		document.all("repair_funds").focus();
		return false;
	}else if (!validateInput(document.all("repair_funds").value, "^[0-9]*$")){
        Dialog.alert("维修费用必须是数字！",null,null,null,20);
        document.all("repair_funds").focus();
        return false
   }else if (document.all("repair_reason").value == "") {
		alert("请输入故障描述");
		document.all("repair_reason").focus();
		return false;
	}else if (document.all("charge_person").value == "") {
		alert("负责人");
		document.all("charge_person").focus();
		return false;
	}
	dycl ="&materialguid="+document.all("materialguid").value;
	dycl +="&repair_num="+document.all("repair_num").value;
	dycl +="&repair_funds="+document.all("repair_funds").value;
	dycl +="&repair_person="+document.all("repair_person").value;
	dycl +="&repair_reason="+document.all("repair_reason").value;
	dycl +="&repair_time_start="+document.all("repair_time_start").value;
	dycl +="&repair_time_done="+document.all("repair_time_done").value;
	dycl +="&charge_person="+document.all("charge_person").value;
		$.post("${ctx}/materialManage/materialRepairSave", dycl, function(result) {
			if (result.success=true) {
				alert("保存成功！");
				window.location.href = "${ctx}/materialManage/materialRepair"; 
			}else {
				alert("保存失败！");
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
	 function fanhui(){
		history.go(-1);
	}
	function selectMaterial(obj){
		Dialog.open({URL:"${ctx}/materialManage/selectMaterial",Title:"请选择物料类型",Width:700,Height:330});
	}
</script>
<title>新增维修记录</title>
</head>
<body>
<div class="box1" panelWidth="800">

<form  name="form1" id="form1" method="post" action=""  failAlert="请将表单填写完整！">
   <table class="tableStyle" formMode="line">
                <tr><td colspan="4" align="left" ><div align="left" style="color:#0066CC;"><strong>新增维修记录</strong></div></td></tr>
                
                <tr>
                <td width="223"><div align="right">物料名称</div></td>
                <td width="171">
				<%--<select name="materialguid" id="materialguid" prompt="请选择" url="${ctx}/materialManage/getMaterialList"></select> --%>
                <input type="text" name="material_name" id="material_name" readonly="readonly" onclick="selectMaterial(this);"/>
                <input type="hidden" name="materialguid" id="materialguid" value="" />
                <span class="star">*</span></td>
                <td width="130"><div align="right">维修数量</div></td>
                <td width="209"><input type="text" name="repair_num" id="repair_num"/><span class="star">*</span></td>
                </tr>
                
                <tr>
                <td><div align="right">维修费用</div></td>
                <td><input type="text" name="repair_funds" id="repair_funds" /><span class="star">*</span></td>
                <td><div align="right">维修方名称</div></td>
                <td><input type="text" name="repair_person" id="repair_person" /><span class="star">*</span></td>
                </tr>
                
                <tr>
                <td><div align="right">负责人</div></td>
                <td><input type="text" name="charge_person" id="charge_person" /><span class="star">*</span></td>
                <td><div align="right">操作人</div></td>
                <td colspan="3"><input type="text" name="create_person" id="create_person" value="${create_person}" disabled="disabled" /><span class="star">*</span></td>
                </tr>
                
                <tr>
                <td><div align="right">维修开始时间</div></td>
                <td><input type="text" name="repair_time_start" id="repair_time_start" class="date"/><span class="star">*</span></td>
                <td><div align="right">维修完成时间</div></td>
                <td><input type="text" name="repair_time_done" id="repair_time_done" class="date"/><span class="star">*</span></td>
                </tr>
                
                <tr>
                <td rowspan="2"><div align="right">故障描述</div></td>
                <td colspan="3" rowspan="2"><textarea name="repair_reason" id="repair_reason" style="width: 500;height: 50"></textarea><span class="star">*</span></td>
                </tr>
                <tr></tr>
                <tr>
                <td colspan="4"><button type="button" onclick="savedoc()"><span class="icon_save">保存</span></button>
                <button type="button" onclick="fanhui()"><span class="icon_back">返回</span></button>
                </td>
                </tr>
    </table>

</form>

</div>

   
   </body>
 
</html>