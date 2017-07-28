<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/static/common/bpmTaglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<title>填写意见</title>
<script type="text/javascript" src="${ctx}/static/jquery/layer/layer.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/layer/extend/layer.ext.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/form/jquery.form.js"></script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" style="font: Menu">
	<iframe ID="SubmitTargetFrame" name="SubmitTargetFrame" frameborder="0"
		width="100" scrolling="no" height="100" src="" style="display: none"
		align="middle"></iframe>
		<div align="center">
			<table width="643" height="15px">
				<tr>
					<td align="center" nowrap><font
						style="font-size: 15px; color =red; width: 400"><b>意见填写</b><span
							style='font-size: 13px;'>(注：如有增加附件，请在意见中说明详见附件)</span></font></td>
				</tr>
			</table>
		</div>
		<div align="center">
				<table width="643" border="1" cellpadding="4" cellspacing="0"
					bordercolor="#666666" bordercolordark="#FFFFFF"
					style="font-family: 宋体; font-size: 10pt">
					<tr>
						<form id="opinionForm" name="opinionForm" method="post" action="${ctx}/opinion4New/saveOrUpdate">
						<input  type = "hidden" name="id"  id="id" value="${opinion != null ? opinion.id:''}" />
						<input  type = "hidden" name="processInstanceId"  id="processInstanceId" value="${processInstanceId}" />
						<input  type = "hidden" name="taskId"  id="taskId" value="${taskId}" />
						<input  type = "hidden" name="processSerialNumber"  id="processSerialNumber" value="${processSerialNumber}" />
						<input  type = "hidden" name="category"  id="category" value="${category}" />
						<input  type = "hidden" name="opinionType"  id="opinionType" value="3" />
						<input  type = "hidden" name="guids"  id="guids" value="" />
						<td colspan="2" align="center" valign="middle" nowrap>
							<table border="0" cellspacing="0" cellpadding="2"
								style="font: Menu">
								<tr>
									<td colspan="2">
										<fieldset style="padding: 5px">
											<legend>
												<font color="red">领导意见</font>
											</legend>
											<textarea id="content" name="content" rows="8" style="font-size: 14px; line-height: 26px; resize: none;width:680px;height:120px">${opinion != null ? opinion.content :''}</textarea>
										</fieldset>
									</td>
								</tr>
								<tr>
									<td align="left">
										<font id="Comment_PersonfontId">当前登录人：</font>
										<input type="text" name="userName" value="${userName}" id="Comment_Person" style="width: 50px; color: red" disabled="disabled" ></td>
									<td align="right"><input  type="submit" value="确定"></td>&nbsp;
								</tr>
								</form>
								<tr>
									<td colspan="2">
										<div id="commonSentencesId" class="easyui-tabs" style="width:700px;height:250px;">   
										    <div title="常用办公语">   
										        <fieldset style="height: 250px">
													<select id="SelectPersonalComment"
														name="SelectPersonalComment" size="12"
														style="width: 100%; height: 200px; font-size: 14px"
														ondblclick="javascript:appendComment(SelectPersonalComment)">
														${commentStr}
														</select>
												</fieldset>    
										    </div>   
										    <div title="自定义个人办公用语">   
											        <fieldset style="height: 150px; padding: 5px">
														<select id="SelectPersonalComment2"
															name="SelectPersonalComment2" size="12"
															style="width: 100%; height: 140px; font-size: 14px"
															ondblclick="javascript:appendComment2(SelectPersonalComment2)">
														</select>
													</fieldset>
													<font color="red">&nbsp;输入个人常用办公用语： </font><input type="text" id="addCommonSentencesId"
														value="" /> <input type="button" id="addCommonSentencesId2"
														value="添加" onclick="addCommonSentences()" /> <input
														type="button" id="removeCommonSentencesId" value="删除"
														onclick="removeCommonSentences()" /> 
										    </div> 
										</div> 
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
		</div>
</body>
<script language="JavaScript">
var xmlreq;
$(document).ready(function() {
	buildCommonSentences();
	$( "#tabs" ).tabs();
	$( "#tabs-2" ).tabs();
});

function buildCommonSentences(){
	$("#SelectPersonalComment2").empty();
    $.ajax({
        type: "GET",
		async:false,
		cache:false,
        url: "${ctx}/commonSentences/list",
        data: {username:$("#username").val(), content:$("#content").val()},
        dataType: "json",
        success: function(data){
			var contentstr="";
			$.each(data,function(i,item){
					var content=item.content;
					var tabIndex=item.tabIndex;
					contentstr=contentstr+"<option value='"+tabIndex+"'>"+content+"</option>"
			});
			$("#SelectPersonalComment2").append(contentstr);  
		}
    });
}

function appendComment(obj){  // 添加日常用语到意见	
	  var SelectComment = obj;
	  var idx = SelectComment.selectedIndex;
	  if (idx < 0){
	  	  alert("至少要选择一条日常用语！");
		  return;
	  }
	  var text = SelectComment.options[idx].value;
	  document.getElementById("content").value = document.getElementById("content").value + text;
}

function appendComment2(obj2){  // 添加个人用语到意见	
  var SelectComment = obj2;
  var idx = SelectComment.selectedIndex;
  if (idx < 0){
  	  alert("至少要选择一条日常用语！");
	  return;
  }
  var text = SelectComment.options[idx].text;
  document.getElementById("content").value = document.getElementById("content").value + text;
}

var frameID = newGuid();
function getPersion(){
	openCurWindow({
			id : frameID,
			src : '${ctx}/department/userChoicePage?urd=3&chkStyle=radio',
			destroy : true,
			title : '人员选取',
			width : 580,
			height : 450,
			modal : true
	});
}

function choice(returnvalue,urd){
	//var returnvalue = window.showModalDialog('${ctx}/department/userChoicePage?urd=3&chkStyle=radio',"","dialogWidth=635px;dialogHeight=410px;status:no;");
	if(returnvalue!=null){	
		var userids=returnvalue[0];
		var names=returnvalue[1];
		var deptIds=returnvalue[2];
		if(userids.indexOf(";")>0){
		}else{
			document.getElementById("agentUserName").value=names;
			document.getElementById("agentUserId").value=userids;
			document.getElementById("agentUserDeptId").value=deptIds;
		}
	}
}

function addCommonSentences(){
	var content=$("#addCommonSentencesId").val();
	//var tabIndex=$("#SelectPersonalComment2 option").size();
	$.ajax({
		async:true,
		type: "POST",
		dataType:'JSON',
		url:"${ctx}/commonSentences/save",
		data:{content:content},
		success:function (date){
			if(date.success){
				buildCommonSentences();
				alert("添加个人常用语成功");
			}else{
				alert("添加个人常用语失败");
			}
		}
  });
}

function removeCommonSentences(){
	var tabIndex=$("#SelectPersonalComment2 ").val();
	$.ajax({
		async:true,
		type: "POST",
		cache: false,
		dataType:'JSON',
		url:"${ctx}/commonSentences/remove?tabIndex="+tabIndex,
		success:function (date){
			if(date.success){
				buildCommonSentences();
				alert("删除个人常用语成功");
			}else{
				alert("删除个人常用语失败");
			}
		}
  });
}

var operationType = '${not empty opinion ? "modify":"create"}';
var options = { 
		async : false,  
		cache : false,
		dataType:"json",
		type:'POST', 
		error:function(data){
			alert("出现异常,此次操作可能失败");
		},
		success:function(date){
			if(date.success){
				alert("保存成功！");
				window.close();
			}else{
				alert("保存失败！");
			}
		}
 	};
$("form[name=opinionForm]").submit(
		function(){
			var b=validation();
			if(b){
				$(this).ajaxSubmit(options);
			}
		return false;
}) ;

//提交前验证
function validation(){
	var opinion =$("#content").val();
	opinion=opinion.replace(/\r\n|\r|\n/g,'<br>');
	if(opinion==null||opinion==""){
		alert("意见不能为空！");
		return false;
	}
	return true;
}
</script>
</html>