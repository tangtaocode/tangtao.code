<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新增领用</title>
          
<script type="text/javascript">
function savedoc(){
	if (document.all("materialguid").value == "") {
		alert("请输选择物料名称");
		document.all("materialguid").focus();
		return false;
	} else if (document.all("using_num").value == "") {
		alert("请输入物领用数量");
		document.all("using_num").focus();
		return false;
	}else if (!validateInput(document.all("using_num").value, "^[0-9]*$")){
        Dialog.alert("领用数量必须是数字！",null,null,null,20);
        document.all("using_num").focus();
        return false
   }else if (document.all("using_name").value == "") {
		alert("请输入领用人");
		document.all("using_name").focus();
		return false;
	}else if (document.all("description").value == "") {
		alert("请输入领用说明");
		document.all("description").focus();
		return false;
	}
	dycl ="&materialguid="+document.all("materialguid").value;
	dycl +="&using_num="+document.all("using_num").value;
	dycl +="&using_name="+document.all("using_name").value;
	dycl +="&description="+document.all("description").value;
		$.post("${ctx}/materialManage/materialUsingSave", dycl, function(result) {
			if (result.success=true) {
				alert("保存成功！");
				window.location.href = "${ctx}/materialManage/materialUsing"; 
			} else {
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
<title>新增领用</title>
</head>
<body>
<div class="box1" panelWidth="800">

<form  name="form1" id="form1" method="post" action=""  failAlert="请将表单填写完整！">
   <table class="tableStyle" formMode="line">
                <tr><td colspan="4" align="left" ><div align="left" style="color:#0066CC;"><strong>新增领用</strong></div></td></tr>
                
                <tr>
                <td width="223"><div align="right">物料名称</div></td>
                <td width="171">
				<%--<select name="materialguid" id="materialguid" prompt="请选择" url="${ctx}/materialManage/getMaterialList"></select> --%>
                <input type="text" name="material_name" id="material_name" readonly="readonly" onclick="selectMaterial(this);"/>
                <input type="hidden" name="materialguid" id="materialguid" value="" />
                <span class="star">*</span></td>
                <td width="130"><div align="right">领用数量</div></td>
                <td width="209"><input type="text" name="using_num" id="using_num"/><span class="star">*</span></td>
                </tr>
                
                <tr>
                <td><div align="right">领用人</div></td>
                <td colspan="3"><input type="text" name="using_name" id="using_name" ><span class="star">*</span></td>
                </tr>
                <tr>
                <td rowspan="2"><div align="right">领用说明</div></td>
                <td colspan="3" rowspan="2"><textarea name="description" id="description" style="width: 500;height: 50"></textarea><span class="star">*</span></td>
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