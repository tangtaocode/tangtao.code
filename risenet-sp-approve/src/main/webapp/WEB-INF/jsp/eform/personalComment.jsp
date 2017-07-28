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
<script type="text/javascript" src="<%=contextPath%>/static/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx }/static/risesoft/js/newComment.js"></script>
<script type="text/javascript" src="${ctx}/static/common/js/jquery.orgStructure.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/risesoft/css/newComment.css">
<object classid="clsid:31D9C2D1-3BEC-4B25-87D6-16F6C3D75DE6" id="SZCAOcx"></object><!--深圳电子证书认证-->
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" style="font: Menu">
	<iframe ID="SubmitTargetFrame" name="SubmitTargetFrame" frameborder="0"
		width="100" scrolling="no" height="100" src="" style="display: none"
		align="middle"></iframe>
		<div align="center">
			<table width="643" height="15px">
				<tr>
					<td align="center" nowrap><font
						style="font-size: 15px; color:red; width: 400"><b>意见填写</b><span
							style='font-size: 13px;'>(注：如有增加附件，请在意见中说明详见附件)</span></font></td>
				</tr>
			</table>
		</div>
		<div align="center" width="723" border="1">
				<table width="723"  cellpadding="4" cellspacing="0"
					bordercolor="#666666" bordercolordark="#FFFFFF"
					style="font-family: 宋体; font-size: 10pt">
					<tr>
						<form id="opinionForm" name="opinionForm" method="post" action="${ctx}/sp/opinion4New/saveOrUpdate">
							<input  type = "hidden" name="id"  id="id" value="${opinion != null ? opinion.id:''}" />
							<input  type = "hidden" name="processInstanceId"  id="processInstanceId" value="${processInstanceId}" />
							<input  type = "hidden" name="taskId"  id="taskId" value="${taskId}" />
							<input  type = "hidden" name="processSerialNumber"  id="processSerialNumber" value="${processSerialNumber}" />
							<input  type = "hidden" name="category"  id="category" value="${category}" />
							<input  type = "hidden" name="opinionType"  id="opinionType" value="1" />
							<input  type = "hidden" name="guids"  id="guids" value="" />
							<!--签名信息-->
							<input type="hidden" name="SignTxt"/> 
							<input type="hidden" name="CertTxt"/> 
							<input type="hidden" name="DataTxt"/> 
							<!--签名信息-->
							<table border="0" cellspacing="0" cellpadding="2"
								style="font: Menu">
								<tr>
									<td colspan="2">
										<fieldset style="padding: 5px;border-width: 2px;">
											<legend>
												<font color="red">请输入意见</font>
											</legend>
											<textarea id="content" name="content" rows="8" style="font-size: 14px; line-height: 26px; resize: none;width:680px;height:120px">${opinion != null ? opinion.content :''}</textarea>
										</fieldset>
									</td>
								</tr>
								<tr>
									<td align="left">&nbsp;<font id="agentfontId">实际处理人：</font>
									<input type="text" id="agentUserName" name="agentUserName" value="${opinion.agentUserName==null?userName:opinion.agentUserName}" 
									readonly="readonly" style="width: 50px; color: red"></input> 
									<input type="hidden" id="agentUserId" name="agentUserId" value="${opinion.agentUserId}"></input> 
									<input type="hidden"name=agentUserDeptId id="agentUserDeptId" value="${opinion.agentUserDeptId}"/>
									<img  title="选取代录对象" src="${ctx}/static/images/user.gif" onclick="javascript:getPersion();">
									&nbsp;&nbsp;&nbsp;
									<font id="Comment_DatefontId">处理时间：</font>
										<input id="realityDate" name="createDate1"  value="${date}" readonly="readonly" style="width: 80px;"/>
											<!-- onkeydown = "WdatePicker({isShowClear:true,dateFmt:'yyyy-MM-dd HH:mm:ss',realDateFmt:'yyyy-MM-dd HH:mm:ss'}) ;"
											onClick="WdatePicker({isShowClear:true,dateFmt:'yyyy-MM-dd HH:mm:ss',realDateFmt:'yyyy-MM-dd HH:mm:ss'}) "  -->
										<!-- <font id="Comment_PersonfontId">当前登录人：</font> -->
									</td>
									<td align="right">
									<input  type="submit" value="确定">&nbsp;
									<input name="commentDelete"
									id="commentDelete" type="button" value="删除"
									onclick="javascript:deleteComment()">&nbsp;
									<input name="commentAdd"
									id="commentAdd" type="button" value="存为常用语"
									onclick="javascript:addCommonSentences()">&nbsp; 
									</td>&nbsp;
								</tr>
							</table>
						</form>
					</tr>
					<tr>
						<fieldset style="width:89%;height: 97%; padding: 5px;border-width: 2px;">
							<legend align="left">
								<font color="red">请选择意见</font>
							</legend>
							<input type="hidden" id="recordComment" value=""/>
							<input type="hidden" id="recordCount"></input>
							<div style="width:100%; height:190px; overflow-y:scroll;">
								<table id='sentTable' cellspacing='0' border='0' style='width:100%;' >
								</table>
							</div>
						</fieldset>
					</tr>
					<tr align="right">
						<div align="right" style="width:89%; height:30px;">
							<input type="button" value="关闭" id="guanbi"></input>
							
						</div>	
					</tr>
				</table>
		</div>
</body>
<script language="JavaScript">
var SPinstanceId = $("#SPinstanceId");//审批业务数据主键Id
var xmlreq;
$(document).ready(function() {
	buildCommonSentences();
	$( "#tabs" ).tabs();
	$( "#tabs-2" ).tabs();
});

var sData = dialogArguments; 


$("#guanbi").click(function(){
	window.close();
});


function buildCommonSentences(){
	var contentstr = "";
	//$("#SelectPersonalComment2").empty();
    $.ajax({
        type: "GET",
		async:false,
		cache:false,
        url: "${ctx}/commonSentences/list",
        data: {username:$("#username").val(), content:$("#content").val()},
        dataType: "json",
        success: function(data){
			//var contentstr="";
			var count = data.length;
			$("#recordCount").val(count);
			$.each(data,function(i,item){
					var type = item.type;
					var content = item.content;
					var tabIndex = item.tabIndex;
					contentstr=contentstr
					+'<tr align="left" onmouseover="editRowOver('+i+');" id="editRow'+i+'" class="">'
					+'<td align="left" id="editComment'+i+'" ondblclick="appendComment('+i+');" style="width:85%;height:20px;">'+content+'</td>'
					+'<td align="left" id="editButton'+i+'" style="width:15%;height:20px;">'
					+'<a href="javascript:saveEdit('+tabIndex+','+i+');"><input type="button" style="display:none;border:none" id="saveEditButton'+i+'" value="保存"/></a>&nbsp;'
					+'<a href="javascript:returnEdit('+i+');" ><input type="button" style="display:none;border:none" id="returnEditButton'+i+'" value="取消"/></a>'
					+'<a href="javascript:updateEdit('+i+');"><input type="button" style="display:none;border:none" class="icon_save" id="updateEditButton'+i+'" value="编辑"/></a>&nbsp;'
					+'<a href="javascript:deleteEdit('+tabIndex+','+i+');"><input type="button" style="display:none;border:none" id="deleteEditButton'+i+'" value="删除"/></a>'
					+'</td></tr>';
			});
			$("#sentTable").html(contentstr);  
		}
    });
}

//双击时添加意见
function appendComment(index) {
	var editComment = $("#editComment"+index);
	var flag = editComment.html().indexOf("<textarea");
	var content = $("#content");
	var text = content.val();//意见框的内容
	if(flag==0){
		var obj = $("#inputComment"+index);//编辑时的意见
		content.val(text+obj.val());
	}else{
		var editCommentValue = editComment.text().replace(/(^\s*)/g, "");
		content.val(text+editCommentValue);
	}
}

/* function appendComment(obj){  // 添加日常用语到意见	
	alert(444)
	  var SelectComment = obj;
	  var idx = SelectComment.selectedIndex;
	  if (idx < 0){
	  	  alert("至少要选择一条日常用语！");
		  return;
	  }
	  var text = SelectComment.options[idx].value;
	  document.getElementById("content").value = document.getElementById("content").value + text;
} */

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
/* function getPersion(){
	openCurWindow({
			id : frameID,
			src : '${ctx}/department/userChoicePage?urd=3&chkStyle=radio',
			destroy : true,
			title : '人员选取',
			width : 580,
			height : 450,
			modal : true
	});
} */

var personTreeOptions = {
		treeType:'tree_type_person',
		title:'选取人员',
		orgCheckSetting: {
			orgNoCheck:true,
			deptNoCheck:true,
			groupNoCheck:true,
			positionNoCheck:true,
			personNoCheck:true
	    },
	    selectedMulti:false,
		callback: {
					afterConfirm:function(checkedNodes,selectedNodes){
										if(selectedNodes.length>0){
											if(selectedNodes[0].orgType=="Person"){
												if(selectedNodes[0].name.indexOf("(")!=-1){
													var name = selectedNodes[0].name.split("(")[0];
													$('#agentUserName').val(name);
												}else{
													$('#agentUserName').val(selectedNodes[0].name);
												}
												$('#agentUserId').val(selectedNodes[0].ID);
											}else{
												$.messager.alert('提示', "只能点选人员","info");
												return false;
											}
										}
								 }
				  }
};

//点击弹出人员树
function getPersion(){
	$.fn.extcloudTreeStructure(personTreeOptions);
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

//判断字符串是否为空
function isNull(str){
	if ( str == "" ) return true;
	var regu = "^[ ]+$";
	var re = new RegExp(regu);
	return re.test(str);
}

//存为常用语
function addCommonSentences(){
	var content=$("#content").val();
	if(isNull(content)){
		alert("存为常用语不能为空！");
		return;
	}
	$.ajax({
		async:true,
		type: "POST",
		dataType:'JSON',
		url:"${ctx}/commonSentences/save",
		data:{content:content},
		success:function (date){
			if(date.success){
				buildCommonSentences();
				//alert("添加个人常用语成功");
			}else{
				//alert("添加个人常用语失败");
			}
		}
  });
}

//保存编辑常用语
function saveEdit(tabIndex,index){
	var content = $("#inputComment"+index).val();
	$.ajax({
		async:true,
		type: "POST",
		dataType:'JSON',
		url:"${ctx}/commonSentences/saveEdit",
		data:{content:content,tabIndex:tabIndex},
		success:function (date){
			if(date.success){
				buildCommonSentences();
			}else{
				alert("保存失败");
			}
		}
  });
}

function removeCommonSentences(tabIndex){
	$.ajax({
		async:true,
		type: "POST",
		cache: false,
		dataType:'JSON',
		url:"${ctx}/commonSentences/remove?tabIndex="+tabIndex,
		success:function (date){
			if(date.success){
				buildCommonSentences();
				//alert("删除个人常用语成功");
			}else{
				//alert("删除个人常用语失败");
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
		success:function(data){
			if(data.success){
				if(data.opinionSign){
					alert("保存成功！");
				}else{
					alert("签名失败！\n保存成功！");
				}
				window.close();
			}else{
				alert("保存失败")
			}
		}
 	};

$("form[name=opinionForm]").submit(
		function(){
			var b=validation();
			if(b){
				getSpmApproveitem($(this));
			}
		return false;
}) ;

//获取事项，无纸化意见签名目前只针对部分事项，需要获取事项控制
function getSpmApproveitem(obj){
	$.ajax({
		async:true,
		type: "POST",
		cache: false,
		dataType:'JSON',
		url:"${ctx}/sp/opinion4New/getSpmApproveitem?SPinstanceId="+SPinstanceId,
		success:function (data){
			if(data.msg){
				opinionSign();//意见签名
				obj.ajaxSubmit(options);
			}else{
				obj.ajaxSubmit(options);
			}
		}
	});
}

//意见签名
function opinionSign(){
	try{
	     //初始化
	     SZCAOcx.Axinit();
	     //筛选证书         "sc;szca;证书名称;序列号;扩展", sc:签名证书，ec:加密证书，szca:颁发者 
	     SZCAOcx.AxSetCertFilterStr("sc;szca;#;#;#");
	     //设置加密签名（弹框显示证书供选择），返回布尔值
	     var result = SZCAOcx.AxSetKeyStore();
	     if(result){
	    	 var content = $("#content").val();
	         //调用接口,对意见content（原文）进行签名
	         var result = SZCAOcx.AxSign(content);  //结果为base64格式
	         if(result != null){
	            //向表里写值
	        	opinionForm.CertTxt.value = SZCAOcx.AxGetCertData();  //获取证书信息（base64格式）
	        	opinionForm.SignTxt.value = result ; //签名结果
	            var extinfo  = SZCAOcx.AxGetCertExt("2.16.156.112548");//唯一标识
	            opinionForm.DataTxt.value = extinfo ; //证书扩展项
	         } else{
	        	 alert("签名失败,证书有误!");  //弹框提示“签名失败，证书有误”
	         }
	     }else{
	    	 alert("请选择证书!");
	     }
	  }catch(error){
		 alert("签名失败，请检查是否允许运行ActiveX组件或是否正确安装驱动程序!");
	  }
}

//提交前验证
function validation(){
	var opinion =$("#content").val();
	var agentUserId=$("#agentUserId").val();
	var realityDate=$("#realityDate").val();
	var onlyAddAgentComment='${onlyAddAgentComment}';
	opinion=opinion.replace(/\r\n|\r|\n/g,'<br>');
	if(opinion==null||opinion==""){
		alert("意见不能为空！");
		return false;
	}
	/* if(onlyAddAgentComment=="true"){暂时不用代录意见
		if(agentUserId==null||agentUserId==""){
			alert("请选择代录对象");
			return false;
		}else{
			if(realityDate==""){
				alert("请选择实际录入时间！");
				return false;
			}
		}
	}else{ */
		if(agentUserId!=null&&agentUserId!=""){
			if(realityDate==""){
				alert("请选择实际录入时间！");
				return false;
			}
		}
	//}
	return true;
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
				url:"${ctx}/opinion4New/delete?id="+id,
				success:function (date){
					alert(date.msg);
					window.close();
					//closeCurWindow(parent.frameID,'close');
				}
		});
	  }else{
		  return;
	  }
}
</script>
</html>