[#macro riseDate category item=0 stepMinute=0 showHMS="NO"][#--当datapicker的id属性相同时会产生冲突，这里使用item区分--]
	<INPUT style="WIDTH: 245px" id="riseDate_${category}_${item}" name="riseDate_${category}_${item}" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-bind="value:${category}">
	<INPUT type="hidden" style="WIDTH: 245px" id="${category}" name="${category}">
	<script type="text/javascript">
		$(function(){
			var showHMS='${showHMS}';
			if(showHMS.toLowerCase()=="yes"){
				$("input[name='${category}']").attr("onFocus", "yyyy-MM-dd HH:mm:ss");
			}
			
			$('#riseDate_${category}_${item}').focusout(function() {
				formModel.${category}($('#riseDate_${category}_${item}').val());
			});
		})
	</script>
[/#macro]