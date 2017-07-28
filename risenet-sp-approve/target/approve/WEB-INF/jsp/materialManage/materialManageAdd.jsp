<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新增物料</title>

<script type="text/javascript">
function savedoc(){
	if (document.all("materialguid").value == "") {
		alert("请输选择物料名称");
		document.all("materialguid").focus();
		return false;
	}else if (document.all("material_num").value == "") {
		alert("请输入库存");
		document.all("material_num").focus();
		return false;
	}else if (!validateInput(document.all("material_num").value, "^[0-9]*$")){
        Dialog.alert("库存必须是数字！",null,null,null,20);
        document.all("material_num").focus();
        return false
   }else if (document.all("tabindex").value == "") {
		alert("请输入排序号");
		document.all("tabindex").focus();
		return false;
	}else if (document.all("unit").value == "") {
		alert("请输入单位");
		document.all("unit").focus();
		return false;
	}
	dycl ="&materialguid="+document.all("materialguid").value;
	dycl +="&material_code="+document.all("material_code").value;
	dycl +="&material_name="+document.all("material_name").value;
	dycl +="&material_num="+document.all("material_num").value;
	dycl +="&tabindex="+document.all("tabindex").value;
	dycl +="&unit="+document.all("unit").value;
		$.post("${ctx}/materialManage/materialManageSave", dycl, function(result) {
			if (result.success=true) {
				alert("保存成功！");
				window.location.href = "${ctx}/materialManage/materialManageIndex"; 
			} else {
				alert("保存失败！");
			}

		}); 
	}
	 function fanhui(){
		history.go(-1);
	}
	 
	 function getSelectedItem(){
		    var text = $("#materialguid").find("option:selected").text();
		    if(text){
		    	$("#material_name").val(text);
		    }
		    $.post("${ctx}/materialManage/getUnit",{materialguid:$("#materialguid").val()}, function(result) {
				if (result) {
					var str= new Array();
					str=result.split(",");
					$("#unit").val(str[0]); 
					$("#material_code").val(str[1]);
				} else {
					alert("获取失败！");
				}

			}); 
		}

function selectMaterial(obj){
// 	var diag = new Dialog();
//     diag.Title = "请选择物料类型";
//     Width:1600;
//     Height:330;
//     diag.URL = "${ctx}/materialManage/selectDictionary";
//     diag.show();
	Dialog.open({URL:"${ctx}/materialManage/selectDictionary",Title:"请选择物料类型",Width:600,Height:330});
}
</script>
<title>新增物料</title>
</head>
<body>
<div class="box1" panelWidth="800">

<form  name="form1" id="form1" method="post" action=""  failAlert="请将表单填写完整！">
   <table class="tableStyle" formMode="line">
                <tr><td colspan="4" align="left" ><div align="left" style="color:#0066CC;"><strong>新增物料</strong></div></td></tr>
                
                <tr>
                <td width="223"><div align="right">物料名称</div></td>
                <td width="171" nowrap="nowrap">
				<%--<select name="materialguid" id="materialguid" onchange="getSelectedItem();" prompt="请选择" url="${ctx}/materialManage/getDictionaryList"></select> --%>
				<input type="text" name="material_name" id="material_name" readonly="readonly" onclick="selectMaterial(this);"/>
                <span class="star">*</span></td>
                <input type="hidden" name="material_code" id="material_code" value="" />
                <input type="hidden" name="materialguid" id="materialguid" value="" />
                <td><div align="right">库存</div></td><td><input type="text" name="material_num"/><span class="star">*</span></td>
                </tr>
                <tr>
				<td><div align="right">单位</div></td><td><input type="text" name="unit" id="unit" readonly="readonly"/><span class="star">*</span></td>
				<td><div align="right">排序号</div></td><td><input type="text" name="tabindex"/><span class="star">*</span></td>
				</tr>
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