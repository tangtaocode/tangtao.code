<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="Content-Language" content="utf-8"/>
		<meta http-equiv="pragma" content="no-cache">   
   	 	<meta http-equiv="cache-control" content="no-cache">
    	<meta http-equiv="expires" content="0">
		<title>有生软件组织架构平台</title>
		<%@ include file="/static/common/head.jsp"%>
		<%@ include file="/static/common/common.jsp"%>
	</head>
		
	<body>
		<div class="easyui-layout" data-options="fit:true,border:false">
			<div data-options="region:'center',border:false">
				<form id="cyForm" name="cyForm" method="post" action="${ctx}/organWord/modifyDocumentNumberDetail.">
					<input  type = "hidden" name="id"  id="id" value="${documentNumberDetail != null ? documentNumberDetail.id:''}" />
					<table border="0" cellpadding="0" cellspacing="1" class="table">
						<tr>
						    <td class="lefttd" style="width: 30%">文号年份<font color="red">*</font>：</td>
							<td class="rigthtd" style="width: 70%" colspan="3">
							<input  name="calendarYear" value="${documentNumberDetail != null ? documentNumberDetail.calendarYear:''}" class="easyui-numberbox" min="${currentYear}" data-options="required:true" />
							</td>
						</tr>
						<tr>
						    <td class="lefttd lbl-must" style="width: 10%">序号初始值<font color="red">*</font>：</td>
							<td class="rigthtd" style="width: 40%">
								<input  name="sequenceInitValue" value="${documentNumberDetail != null ? documentNumberDetail.sequenceInitValue:''}" class="easyui-numberbox"  data-options="required:true"/>
							</td>
							<td class="lefttd lbl-must" style="width: 10%">序号长度<font color="red">*</font>：</td>
							<td class="rigthtd" style="width: 40%">
								<input  name="numLength" value="${documentNumberDetail != null ? documentNumberDetail.numLength:''}" class="easyui-numberbox" min="2" max="4" data-options="required:true" missingMessage="必须填写2~4之间的数字"/>
							</td>
						</tr>
						
						<tr>
							<td colspan="4" align="center">
								<input type="submit" value="提交" />
								<input type="reset" value="重置" />
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</body>
	
	<script type="text/javascript" src="${ctx}/static/jquery/form/jquery.form.js"></script>
	<script type="text/javascript">
		var options = { 
				async : false,  
				cache : false,
				dataType:"json",
				type:'POST', 
				error:function(data){
					alert("出现异常,此次操作可能失败");
				},
				success:function(result){
					top.$.messager.alert('提示', "保存成功！" , "info" , function(){
						closeCurWindow(parent.frameID,'close');
					});
				}
		 	};
		
		$("form[name=cyForm]").submit(function(){
			if($(this).form('validate')){
				 $(this).ajaxSubmit(options);
			}
			return false;
		}) ;
	</script>
</html>