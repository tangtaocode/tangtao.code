=[#macro riseSequence category showButton buttonValue]
	[#if "${showButton}"=="true"]
		<select id="character" name="character" >
			
		</select>
		<INPUT type="text" style="WIDTH: 140px;color: red" id="${category}" name="${category}" readonly data-bind="value:${category}">
		<INPUT type="button" id="gen-${category}" name="gen-${category}" onclick="genSequence('${category}');" value="编号">
	[#else]
		<INPUT type="text" style="WIDTH: 245px" id="${category}" name="${category}" readonly data-bind="value:${category}">
	[/#if]
	<script type="text/javascript">
		$(document).ready(function() {
			$.ajax({
					   async:false, 
					   cache:false,
				       type:'get',
				       dataType:'JSON',         
				       url:'${ctx}/organWord/characterValues4Select',    
				       success:function(data){
				       		$("#character").append(data.cv4select);
				       }    
				});
		});
		
		var frameID = newGuid();
		function genSequence(category){
			openCurWindow({
						id : frameID,
						src : '${ctx}/wf/autoFormSequence/docSequence?character='+$("#character").val()+'&labelName='+category,
						destroy : true,
						title : '编号',
						width : 220,
						height : 180,
						modal : true
				});
		}
		
		function setSequence(result){
					if (typeof (result) != "undefined" && result != "") {
						formModel.${category}(result);//将生成的编号放入表单，可以同时更新多个表单
						$.ajax({
							type: "POST",
							dataType:'JSON',
							data:{
								'labelName':'${category}',
								'character':$("#character").val()
							},
							beforeSend: function() {},
							error:function(){},
							success: function(msg){
							}
						});
					}
				}
	</script>
[/#macro]