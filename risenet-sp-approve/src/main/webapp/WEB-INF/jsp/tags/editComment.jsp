<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<title>填写意见</title>
<script type="text/javascript" src="${ctx}/static/jquery/layer/layer.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/layer/extend/layer.ext.js"></script>
<script language="JavaScript">
	var xmlreq;
	$(document).ready(function() {
		buildCommonSentences();
		var AddEditPcDept='${AddEditPcDept}';
		var opinionType='${opinion.opinionType}';
		$("#opinionType").val(opinionType);
		$("#opinionType").attr("disabled","disabled");
		if(AddEditPcDept=="AddOnlyDO"){
			$('#imageID').hide();
			$('#agentfontId').hide();
			$('#Comment_Date').hide();
			$('#Comment_DatefontId').hide();
			$('#agentUserName').hide();
		}else if(AddEditPcDept=="addOnlyLeader"){
			$('#imageID').hide();
			$('#agentfontId').hide();
			$('#Comment_Date').hide();
			$('#Comment_DatefontId').hide();
			$('#agentUserName').hide();
		}
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
		  document.getElementById("commentContent").value = document.getElementById("commentContent").value + text;
	}
	
	function appendComment2(obj2){  // 添加个人用语到意见	
	  var SelectComment = obj2;
	  var idx = SelectComment.selectedIndex;
	  if (idx < 0){
	  	  alert("至少要选择一条日常用语！");
		  return;
	  }
	  var text = SelectComment.options[idx].text;
	  document.getElementById("commentContent").value = document.getElementById("commentContent").value + text;
	}
	
	//編輯意见
	function updateComment(){
		$("#opinionType").removeAttr("disabled");
		var id='${opinion.id}';
		var taskId='${taskId}';
		var opinionType='${opinion.opinionType}';
		var opinion =$("#commentContent").val();
		var agentUserId=$("#agentUserId").val();
		var agentUserName=$("#agentUserName").val();
		var agentUserDeptId=$("#agentUserDeptId").val();
		var opinionDate=$('#Comment_Date').datebox('getValue');
		var isAgent=1;
		var AddEditPcDept='${AddEditPcDept}';
		opinion=opinion.replace(/\r\n|\r|\n/g,'<br>');
		if(opinionType==2||opinionType==3||agentUserDeptId==""){
			isAgent=0;
		}
		if(opinion==null||opinion==""){
			alert("意见不能为空！");
			return;
		}
		if(agentUserName!=""&&opinionDate==""){
			alert("请选择实际录入时间！");
			return;
		}
		if(AddEditPcDept=="AddOnlyAgent"){
			if(agentUserId==null||agentUserId==""){
				alert("请选择代录对象");
				return;
			}
		}
		$.ajax({
			async:true,
			type:"POST",
			url:"${ctx}/opinionRc8/update",
			dataType:'JSON',
			data: "id="+id+"&opinion="+opinion+"&isAgent="+isAgent+"&agentUserDeptId="+agentUserDeptId+"&opinionDate="+opinionDate+"&agentUserId="+agentUserId+"&agentUserName="+agentUserName+"&activitiUser=${activitiUser}&taskId="+taskId,
			success:function (date){
				if(date.success){
					parent.buildComment_${category}();
				}
				//var index = parent.layer.getFrameIndex(window.name);
				//parent.layer.close(index);
				alert(date.msg);
				closeCurWindow(parent.frameID,'close');
			}
		})
	}
	
	//删除意见
	function deleteComment(){
		var id='${opinion.id}';
		if (window.confirm("确定删除该意见?")) {
			$.ajax({
				    async:true,
				    type: "POST",
				    cache: false,
				    dataType:'JSON',
					url:"${ctx}/opinionRc8/delete?id="+id,
					success:function (date){
						if(date.success){
							parent.buildComment_${category}();
							alert(date.msg);
							closeCurWindow(parent.frameID,'close');
							//var index = parent.layer.getFrameIndex(window.name);
							//parent.layer.close(index);
						}else{
							//var index = parent.layer.getFrameIndex(window.name);
							//parent.layer.close(index);
							closeCurWindow(parent.frameID,'close');
						}
					}
			});
		  }else{
			  return;
		  }
	}
	
	var frameID = newGuid();
	function getPersion(){
		openCurWindow({
				id : frameID,
				src : '${ctx}/rc8Department/userChoicePage?urd=3&chkStyle=radio',
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
	
	//部门意见不能代录，处理时间不可选，应为报讯意见的系统时间。为了方便就隐藏了
	function hideAgent(){ 
		if(document.getElementById("isDepartmentOpinionId").options[1].selected){
			$("#imageID").style.display = "none";
			$("#agentfontId").style.display = "none";
			$("#Comment_Date").style.display = "none";
			$("#agentUserName").style.display = "none";
			$("#Comment_DatefontId").style.display = "none";
		}else{
			$("#imageID").style.display = "";
			$("#agentfontId").style.display = "";
			$("#Comment_Date").style.display = "";
			$("#agentUserName").style.display = "";
			$("#Comment_DatefontId").style.display = "";
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
	
	$(function() {
		$( "#tabs" ).tabs();
		$( "#tabs-2" ).tabs();
	  });
</script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" style="font: Menu">
	<iframe ID="SubmitTargetFrame" name="SubmitTargetFrame" frameborder="0"
		width="100" scrolling="no" height="100" src="" style="display: none"
		align="middle"></iframe>
	<form id="commentForm" method="post" target="SubmitTargetFrame"
		action="">
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
					<td colspan="2" align="center" valign="middle" nowrap>
						<table border="0" cellspacing="0" cellpadding="2"
							style="font: Menu">
							<tr>
								<td colspan="2">
									<fieldset style="padding: 5px">
										<legend>
											<font color="red">请输入意见</font>
										</legend>
										<textarea id="commentContent" name="commentContent" rows="8" style="font-size: 14px; line-height: 26px; resize: none;width:680px;height: 120px">${opinion.content}</textarea>
									</fieldset>
								</td>
							</tr>
							<tr>
								<td align="left">&nbsp;<font id="agentfontId">实际处理人：</font><input
									type="text" id="agentUserName" name="agentUserName"
									value="${opinion.agentUserName}" disabled="disabled"
									style="width: 50px; color: red"></input> <input type="hidden"
									id="agentUserId" name="agentUserId" value="${opinion.agentUserId}"
									disabled="disabled"></input> <input type="hidden"
									name=agentUserDeptId id="agentUserDeptId"
									value="${opinion.agentUserDeptId}" disabled="disabled" /> <input
									type="image" id="imageID" title="选取代录对象"
									src="${ctx}/static/images/user.gif"
									onclick="javascript:getPersion()"></input>
									&nbsp;<font id="Comment_DatefontId">处理时间：</font>
									<input type="text" class="easyui-datebox" name="Comment_Date" value="${opinion.opinionDate}" id="Comment_Date" style="width: 90px; color: red"></input> 
										&nbsp;个人丨部门意见：<select id="opinionType"
										name="opinionType" onclick="javascript:hideAgent()">
											<option value="1">个人</option>
											<option value="2">部门</option>
											<option value="3">领导</option>
									</select>&nbsp;&nbsp;&nbsp; &nbsp;<font id="Comment_PersonfontId">当前登录人：</font><input
										type="text" name="Comment_Person" value="${userName}"
										id="Comment_Person" style="width: 50px; color: red"
										disabled="disabled"></td>
								<td align="right"><input name="commentDelete"
									id="commentDelete" type="button" value="删除"
									onclick="javascript:deleteComment()">&nbsp; <input
										name="save" id="save" type="button" value="确定"
										onclick="javascript:updateComment()">&nbsp; &nbsp;</td>
							</tr>
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
		<br>
	</form>
</body>
</html>