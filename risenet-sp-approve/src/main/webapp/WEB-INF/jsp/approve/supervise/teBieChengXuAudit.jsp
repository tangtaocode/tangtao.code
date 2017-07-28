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
		<title>特别程序审核</title>
		
		<script type="text/javascript">
		
		</script>
	</head>
		
	<body>
		<div class="easyui-layout" data-options="fit:true,border:false">
			<div data-options="region:'center',border:false">
				<form id="teBieChengXujieguo" name="teBieChengXujieguo" method="post">
				<input type=hidden name="processInstanceId" value="${processInstanceId}">
				<input type=hidden name="taskId" value="${taskId}">
				<input type=hidden name="sjbbh" value="${map.sjbbh}">
				<input type=hidden name=eeguids id=eeguids>	
				<table border="0" cellpadding="0" cellspacing="1" class="table">
						<tr>
						    <td class="lefttd lbl-must" style="width: 25%">事项类型：</td>
							<td class="rigthtd" style="width: 75%" colspan="3" >
								${resMap.sxlx}
							</td>
						</tr>
						<tr>
						    <td class="lefttd lbl-must" style="width: 25%">特别程序开始日期：</td>
							<td class="rigthtd" style="width: 75%" colspan="3">
								${map.tbcxksrq}
							</td>
						</tr>
						<tr>
						    <td class="lefttd lbl-must" style="width: 25%">特别程序批准人：</td>
							<td class="rigthtd" style="width: 75%" colspan="3">
								${map.tbcxpzr}
							</td>
						</tr>
						<tr rowspan="4">
						    <td class="lefttd lbl-must" style="width: 25%" >特别程序启动理由或依据：</td>
							<td class="rigthtd" style="width: 75%" colspan="3">
								<textarea id="tbcxqdlyhyj" name="tbcxqdlyhyj"style="width: 97%;height:110px"  readonly="readonly" >${map.tbcxqdlyhyj}</textarea>
							</td>
						</tr>
						<tr>
						    <td class="lefttd lbl-must" style="width: 25%">特别程序种类：</td>
							<td class="rigthtd" nowrap colspan="3">
								<c:if test="${map.tbcxzl=='A'}">
									A类延长审批
								</c:if>
								<c:if test="${map.tbcxzl=='B'}">
									特殊原因申请
								</c:if>
								<%-- ${tbcxzl=='B'?'B类除延长审批之外':'A类延长审批'} --%>
							</td>
						</tr>
						<tr>
						    <td class="lefttd lbl-must" style="width: 25%">特别程序种类名称：</td>
							<td class="rigthtd" style="width: 75%" colspan="3">
								${map.tbcxzlmc}
								<font style="color:red"><br>说明：填写上栏列举延长审批、专家鉴定等，不再上列范围时可自行扩展，自行扩展的都按照<br>B类处理。</font>
							</td>
						</tr>
						<tr   >
						    <td class="lefttd lbl-must" style="width: 25%" >申请内容：</td>
							<td class="rigthtd" style="width: 75%" colspan="3" >
								<textarea id="sqnr" name="sqnr"style="width: 97%;height:110px"  readonly="readonly" >${map.sqnr}</textarea>
							</td>
						</tr>
						<tr>
						    <td class="lefttd lbl-must" style="width: 25%">特别程序时限：</td>
							<td class="rigthtd" style="width: 75%" colspan="3">
								${map.tbcxsx}
							</td>
						</tr>
						<tr>
						    <td class="lefttd lbl-must" style="width: 25%">特别程序时限单位：</td>
							<td class="rigthtd" style="width: 75%" colspan="3">
								<c:if test="${map.tbcxsxdw=='G'}">
									工作日
								</c:if>
								<c:if test="${map.tbcxsxdw=='Z'}">
									自然日
								</c:if>
							</td>
						</tr>
					<%-- 	<tr>
							<td>特别程序批准人</td>
							<td>${tbcxpzr}</td>
						</tr>
						<tr>
							<td>特别程序批准人电话</td>
							<td>${tbcxpzrdh}</td>
						</tr>
						<tr>
							<td>特别程序批准人手机</td>
							<td>${tbcxpzrsj}</td>
						</tr> --%>
						<tr>
						    <td class="lefttd lbl-must" style="width: 25%">备注：</td>
							<td class="rigthtd" style="width: 75%" colspan="3">
								<textarea id="sqnr" name="sqnr"style="width: 97%;height:110px"  readonly="readonly" >${map.bz}</textarea>
							</td>
						</tr>
					
						</tr>
						<tr >	
							<td colspan="4" align="center">
								<input type="button" value="通过" onclick="saveForm('1');"/>
								<input type="button" value="不通过" onclick="saveForm('0');"/>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<div id="dialog-jieguo">
			<form id="reminderForm" name="reminderForm" method="post" action="${ctx}/reminder/saveOrUpdate">
				<table border="0" cellpadding="0" cellspacing="1" class="table">
					<tr>
					    <td class="lefttd lbl-must" style="width: 25%;height:10%">特别程序结果：</td>
						<td class="rigthtd" style="width: 75%" colspan="3">
							<textarea id="tbcxjg" name="tbcxjg"style="width: 97%;height:110px" ></textarea>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</body>
	<script type="text/javascript">
	/* 	function saveForm(type){
			  $.ajax({
			       async : false,  
			       cache : false,  
			       type: 'GET',
			       dataType:'JSON',
			      // data: {processInstanceId:${processInstanceId},sjbbh:${map.sjbbh},type:type},
			       url:  "${ctx}/sp/teBieChengXuShenQing/teBieChengXuJieGuo",
			       error: function () {
			       },
			       success:function(data){
			    	  
			       }
			});
			return false;
		} */
		
		function saveForm(type){
			  $.ajax({
			       async : false,  
			       cache : false,  
			       type: 'GET',
			       dataType:'JSON',
			       data: {processInstanceId:${processInstanceId},taskId:${taskId},type:type},
			       url:  "${ctx}/sp/teBieChengXuShenQing/saveTeBieChengXuShenHe",
			       error: function () {
			    	   alert("审核失败");
			       },
			       success:function(data){
			    	   if(data.success==true){
			    		   alert("审核成功");
			    		   parent.window.parent.refreshMenu();
			    	   }
			    	   if(data.success==false){
			    		   alert("审核失败");
			    	   }
			       }
			});
			  
		/* 	$( "#dialog-jieguo" ).dialog({
				title:"特别程序结果",
				resizable: false,
				height:220,
				width : 600,
				modal: true,
				buttons:[{
					text:'确定',
					handler:function() {
						var str = $('#tbcxjg').val();
						if(str==null||str==''){
							alert("请填写特别程序结果")
							return false;
						}
						  $.ajax({
						       async : false,  
						       cache : false,  
						       type: 'GET',
						       dataType:'JSON',
						       data: {processInstanceId:${processInstanceId},taskId:${taskId},sjbbh:${map.sjbbh},type:type,tbcxjg:str},
						       url:  "${ctx}/sp/teBieChengXuShenQing/saveTeBieChengXuJieGuo",
						       error: function () {
						    	   alert("特别程序处理失败");
					    		   $("#dialog-jieguo").dialog( "close" );
						       },
						       success:function(data){
						    	   if(data.success==true){
						    		   alert("特别程序处理成功");
						    		   parent.window.parent.initMenu();
						    	   }
						    	   if(data.success==false){
						    		   alert("特别程序处理失败");
						    		   $("#dialog-jieguo").dialog( "close" );
						    	   }
						       }
						}); 
					}
				},{
					text:'关闭',
					handler: function() {
						$("#dialog-jieguo").dialog( "close" );
					}
				}]
			}); */
		}
	</script>
</html>