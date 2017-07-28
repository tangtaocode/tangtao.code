<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="Content-Language" content="utf-8"/>
		<meta http-equiv="pragma" content="no-cache">   
   	 	<meta http-equiv="cache-control" content="no-cache">
    	<meta http-equiv="expires" content="0">
    
		<title>特别程序结果</title>
		
		<script type="text/javascript">
		
		</script>
	</head>
		
	<body>
		<div class="easyui-layout" data-options="fit:true,border:false">
			<div data-options="region:'center',border:false">
				<form id="teBieChengXuJieGuo" name="teBieChengXuJieGuo" method="post"  action="${ctx}/sp/teBieChengXuShenQing/saveTeBieChengXuJieGuo">
				<input type=hidden name="processInstanceId" value="${processInstanceId}">
				<input type=hidden name="sjbbh" value="${resMap.sjbbh}">
				<input type=hidden name="taskId" value="${taskId}">
				<input type=hidden name=eeguids id=eeguids>	
				<table border="0" cellpadding="0" cellspacing="1" class="table">
						<tr>
						    <td class="lefttd lbl-must" style="width: 25%">特别程序结果：<font color=red>*</font></td>
							<td class="rigthtd" style="width: 75%" colspan="3" >
								<textarea id="tbcxjg" name="tbcxjg" style="width: 97%;height:110px"  required="true"></textarea>
							</td>
						</tr>
						<tr>
						    <td class="lefttd lbl-must" style="width: 25%">结果产生日期：</td>
							<td class="rigthtd" style="width: 75%" colspan="3">
								<input id="tbcxk" name="jgcsrq" type="jgcsrq" value="${jgcsrq}"  readonly="readonly"/>
							</td>
						</tr>
						<tr>
						    <td class="lefttd lbl-must" style="width: 25%">特别程序结束日期：</td>
							<td class="rigthtd" style="width: 75%" colspan="3">
								<input id="tbcxk" name="tbcxjsrq" type="tbcxjsrq" value="${tbcxjsrq }" readonly="readonly"/>
							</td>
						</tr>
						<tr>
						    <td class="lefttd lbl-must" style="width: 25%">特别程序收费金额：</td>
							<td class="rigthtd" nowrap colspan="3">
								<input id="tbcxzl" name="tbcxsfje" type="tbcxsfje" value="" />
								<br>
								<font style="color:red">说明：收费时用中填写数额及单位，不收费填0，如：40元，5美元
								</font>
							</td>
						</tr>
						<tr>
						    <td class="lefttd lbl-must" style="width: 25%">备注：</td>
							<td class="rigthtd" style="width: 75%" colspan="3">
								<textarea id="bz" name="bz" style="width: 97%;height:110px"   ></textarea>
							</td>
						</tr>
					
						<tr >	
							<td colspan="4" align="center">
								<input id="tijiao" type="submit" value="提交" />
								<input id="cancel" type="button" value="取消" />
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>

	</body>
	
	<script type="text/javascript" src="${ctx}/static/jquery/form/jquery.form.js"></script>
	<script type="text/javascript">
		$("form[name=teBieChengXuJieGuo]").submit(function(){
			$("#teBieChengXuJieGuo").ajaxSubmit({
						async : false,  
						cache : false,
						url: "${ctx}/sp/teBieChengXuShenQing/saveTeBieChengXuJieGuo",
						dataType:"json",
						type:'POST', 
						error:function(data){
							alert("出现异常,此次操作可能失败");
						},
						success:function(data){
							if(data.success==true){
								alert("保存成功！");
								parent.window.parent.refreshMenu();
							}else{
								alert("保存失败！");
							}
						}
			});
			return false;
		});
	
		$("#cancel").click(function() {
			closeCurWindow(parent.frameID,'close');
		});
		
	</script>
</html>