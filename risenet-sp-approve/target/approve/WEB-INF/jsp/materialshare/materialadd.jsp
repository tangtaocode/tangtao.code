<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新增材料</title>
          
<script type="text/javascript">
function savedoc(){
	if (document.all("cailbm").value == "") {
		Dialog.alert("请输入材料定义编码！",null,null,null,20);
		document.all("cailbm").focus();
		return false;
	} else if (document.all("cailmc").value == "") {
		Dialog.alert("请输入材料定义名称！",null,null,null,20);
		document.all("cailmc").focus();
		return false;
	}else if (!validateInput(document.all("cailmc").value, "^[\u4e00-\u9fa5]+$")){
        Dialog.alert("材料定义名称需要是中文！",null,null,null,20);
        return false
   }
	dycl ="&CODE="+document.all("cailbm").value;
	dycl +="&STUFFDEFINENAME="+document.all("cailmc").value;
	dycl +="&ISCHECKVALID="+document.all("jianyyxq").value;
	dycl +="&TABINDEX="+document.all("paixh").value;
	dycl +="&STATE="+document.all("cailzt").value;
		$.post("${ctx}/materialshare/materialsave", dycl, function(result) {
			if (result.success=true) {
				alert("保存成功！");
				window.location.href = "${ctx}/materialshare/materialdefinition"; 
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
</script>
<title>证照扫描</title>
</head>
<body>
<div class="box1" panelWidth="800">

<form  name="form1" id="form1" method="post" action=""  failAlert="请将表单填写完整！">
   <table class="tableStyle" formMode="line">
                <tr><td colspan="4" align="left" ><div align="left" style="color:#0066CC;"><strong>新增共享材料定义</strong></div></td></tr>
                
                <tr><td width="223"><div align="right">材料定义编码</div></td>
                <td width="171"><input type="text" name="cailbm" /><span class="star">*</span></td>
                <td width="130"><div align="right">材料定义名称</div></td>
                <td width="209"><input type="text" name="cailmc"/><span class="star">*</span></td></tr>
                <tr><td><div align="right">是否需要检验有效期</div></td><td><select name="jianyyxq"><option value="0">否</option><option value="1">是</option></select></td>
				<td><div align="right">排序号</div></td><td><input type="text" name="paixh" /></td></tr>
                <tr><td><div align="right">材料状态</div></td><td><select name="cailzt"><option value="1">有效</option><option value="0">无效</option></select></td>
				<td><div align="right"></div></td><td></td></tr>
                <tr><td colspan="4"><button type="button" onclick="savedoc()"><span class="icon_save">保存</span></button></td></tr>
				  
               
    </table>

</form>

</div>

   
   </body>
 
</html>