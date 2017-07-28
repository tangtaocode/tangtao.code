<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>组织机构代码</title>
<!--框架必需start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/language/cn.js"></script>
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/framework.js"></script>
<link href="${ctx}/static/QUI/libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="${ctx}/static/QUI/" scrollerY="false"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->
<!--数据表格start-->
<script src="${ctx}/static/QUI/libs/js/table/quiGrid.js" type="text/javascript"></script>
<!--数据表格end-->
<!--表单异步提交start-->
<script src="${ctx}/static/QUI/libs/js/form/form.js" type="text/javascript"></script>
<script src="${ctx}/static/QUI/libs/js/form/stepForm.js" type="text/javascript"></script>
<!--表单异步提交end-->
<!-- 日期控件start -->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/form/datePicker/WdatePicker.js"></script>
<!-- 日期控件end -->
<!--弹窗组件start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/popup/drag.js"></script>
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/popup/dialog.js"></script>
<!--弹窗组件end-->
<!--弹出式提示框start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/popup/messager.js"></script>
<!--弹出式提示框end-->
<!-- 布局 -->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/nav/layout.js"></script>
<!--树组件start -->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/tree/ztree/ztree.js"></script>
<link href="${ctx}/static/QUI/libs/js/tree/ztree/ztree.css" rel="stylesheet" type="text/css"/>
<!--树组件end -->
<!-- 双向选择器start -->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/form/lister.js"></script>
<!-- 双向选择器end -->
<!--数据表格start-->
<script src="${ctx}/static/QUI/libs/js/table/quiGrid.js" type="text/javascript"></script>
<!--数据表格end-->
<!--箭头分页start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/nav/pageArrow.js"></script>
<!--箭头分页end-->
<script type="text/javascript">
	$(function() {
		$.ajax({
			type:'get',
			datatype:'JSON',
			url:'${ctx}/bureaCode/bcjSelect',
			success:function(result){
				var html = "<form><table class=\"tableStyle\" formMode=\"line\"><tr><th bgcolor=\"#CCCCCC\" colspan=\"4\">单位组织机构代码管理 </th></tr><tr><td class='title_center' height=\"35\"  align='center'>单位名称</td><td class='title_center'  align='center'>组织机构代码</td>"+
						 "<td class='title_center' align='center'>单位名称</td><td class='title_center'  align='center'>组织机构代码</td></tr>";
				
				var str="";
				var array = eval(result);
			
 				for(var i=0;i<array.length;i=i+2){
 					str+="<tr><td class='title_center' align='right'>"+array[i].DEPARTMENT_NAME+"&nbsp;&nbsp;</td><td class='title_center' align='center'><input id='"+array[i].guid+"' type='text' name='code' value='"+array[i].value+"'></td>";
					if(i+1<array.length){
						str+="<td class='title_center' align='right'>"+array[i+1].DEPARTMENT_NAME+"&nbsp;&nbsp;</td><td class='title_center' align='center'><input id='"+array[i+1].guid+"' type='text' name='code' value='"+array[i+1].value+"'></td></tr>";
					}else{
						str+="<td></td><td></td></tr>";
					}
				}
				html=html+str+"<tr><td colspan='4' align='center'><input type='button' value='保存' onclick='sel();'></td></tr></table></form>";
				$("#tab").append(html);
			}
		});	
	});
	function sel(){
		var jsondata = "{"; 
		$("[name=code]").each(function () { 
		jsondata += '"' + this.id + '":"' + this.value + '",'; 
		}); 
		//去掉最后一个"," 
		var reg = /,$/gi; 
		jsondata = jsondata.replace(reg, ""); 

		jsondata += "}"; 
		//alert(jsondata); 
		//ajax提交数据 
		$.post("${ctx}/bureaCode/update", { "code": jsondata }, function (result) {
			if(result.success){
				alert("保存成功！"); 
			}else{
				alert("保存失败！"); 
			}
			
		}); 
	}
</script>
</head>
<body>


<div class="box1" panelWidth="1000">
<div id="tab"></div>
</div>

</body>
</html>