[#--direction表示当前字段是横排（用h表示）还是竖排（用v表示）--]
[#macro riseSubForm category subCategory subCategory direction]
	<table id="riseSubForm_${category}"  border=0 width="100%" height="100%">
	</table>
	<INPUT type="text" style="WIDTH: 245px" id="${category}_${item}" name="${category}" readOnly data-bind="value:${category}">
	<script type="text/javascript">
		var subCategory="${subCategory}";
		var str="";
		$(document).ready(function() {
			if(subCategory!="")
			{
				var subCategorys=subCategory.split(",");
				str="<!-- ko foreach: ${category} --><br/>";
				
				str+="<!-- /ko --><br/>";
			}else
			{
				alert("自定义标签${category}未设置子元素subCategory");
			}
			
		});
	</script>
[/#macro]