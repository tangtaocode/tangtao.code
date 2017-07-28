[#macro riseDepartment category showButton buttonValue]
	[#if "${showButton}"=="true"]
		<INPUT type="text" style="WIDTH: 190px" id="${category}" name="${category}" data-bind="value:${category}">
		<INPUT type="button" id="getDept-${category}" name="getDept-${category}" onclick="getDept('${category}');" value="${buttonValue}">
	[#else]
		<INPUT type="text" style="WIDTH: 140px" id="${category}" name="${category}" data-bind="value:${category}">
	[/#if]
	<script type="text/javascript">
		var frameID = newGuid();
		function getDept(category)
		{
			openCurWindow({
					id : frameID,
					src : '${ctx}/department/userChoicePage?urd=2&chkStyle=radio',
					destroy : true,
					title : '单位选取',
					width : 580,
					height : 450,
					modal : true
			});
		}
	
		function choice(result,urd){
			if (typeof (result) != "undefined" && result != "") {
				formModel.${category}(result[1]);
			}
		}
	</script>
[/#macro]