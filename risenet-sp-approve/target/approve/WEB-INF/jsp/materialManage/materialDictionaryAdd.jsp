<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>数据字典新增</title>
          
<script type="text/javascript">
function savedoc(){
	if (document.all("material_code").value == "") {
		alert("请输入物料编码");
		document.all("material_code").focus();
		return false;
	} else if (document.all("material_name").value == "") {
		alert("请输入物料名称");
		document.all("material_name").focus();
		return false;
	}else if (document.all("unit").value == "") {
		alert("请输入单位");
		document.all("unit").focus();
		return false;
	}
	dycl ="&material_code="+document.all("material_code").value;
	dycl +="&material_name="+document.all("material_name").value;
	dycl +="&unit="+document.all("unit").value;
		$.post("${ctx}/materialManage/materialDictionarySave", dycl, function(result) {
			if (result.success=true) {
				alert("保存成功！");
				window.location.href = "${ctx}/materialManage/materialDictionaryIndex"; 
			}else {
				alert("保存失败！");
			}
		}); 
	}

	 function fanhui(){
		history.go(-1);
	}
</script>
<title>新增物料</title>
</head>
<body>
<div class="box1" panelWidth="800">

<form  name="form1" id="form1" method="post" action=""  failAlert="请将表单填写完整！">
   <table class="tableStyle" formMode="line">
                <tr><td colspan="4" align="left" ><div align="left" style="color:#0066CC;"><strong>数据字典新增</strong></div></td></tr>
                <tr><td width="223"><div align="right">物料编码</div></td>
                <td width="171"><input type="text" name="material_code" value="${wlbm}" readonly="readonly"/><span class="star">*</span></td>
                <td width="130"><div align="right">物料名称</div></td>
                <td width="209"><input type="text" name="material_name"/><span class="star">*</span></td></tr>
                <tr>
				<td><div align="right">单位</div></td><td><input type="text" name="unit" /><span class="star">*</span></td>
				<td></td><td></td>
				</tr>
                <td colspan="4"><button type="button" onclick="savedoc()"><span class="icon_save">保存</span></button>
                <button type="button" onclick="fanhui()"><span class="icon_back">返回</span></button>
                </td>
                </tr>
				  
               
    </table>

</form>

</div>

   
   </body>
 
</html>