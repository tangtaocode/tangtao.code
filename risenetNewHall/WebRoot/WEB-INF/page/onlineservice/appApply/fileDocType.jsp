<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link href="/css/login-box.css" rel="stylesheet" type="text/css" />
<link href="/css/main.css" type="text/css" rel="stylesheet" />
<script src="/js/Scripts/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript">
function saveDocType(fileId){
	var appGuid = '<s:property value="appInstanceGuid"/>';
	var fileGuid = '<s:property value="fileGuid"/>';
	var $cardGuid = $("input[id='cardGuid']");
	var $docTypeGuid = $("input[id='docTypeGuid']");
	var $docTypeNum = $("input[id='docTypeNum']");
	var $docTypeNumName = $("input[id='docTypeNumName']");
	var len = $docTypeGuid.length;
	var j = 0;
	for(var i=0;i<$docTypeGuid.length;i++){
		$.post("/onlineService/saveDocType.html",{
		'webApplyFileDoctype.guid':$cardGuid[i].value,
		'webApplyFileDoctype.xmgguid':appGuid,
		'webApplyFileDoctype.clguid':fileGuid,
		'webApplyFileDoctype.zzguid':$docTypeGuid[i].value,
		'webApplyFileDoctype.cardid':$docTypeNum[i].value,
		'webApplyFileDoctype.zzname':$docTypeNumName[i].value
		},function(data){
			if(data.message=="1")j++;
			if(len==j&&data.message=="1"){
				parent.Dialog.alert("保存成功！",function(){
					parent.findFile();
					ownerDialog.close();
				});
			}else if(data.message=="0"){
				parent.Dialog.alert("系统错误");
			}
		});
	}
}
</script>
</head>
<body>
	<table cellpadding="0" cellspacing="0" border="0" width="100%" id="cardTab1"
			class="BS_list" style="font-family:微软雅黑;display:">
						<tr>
							<th style="text-align:left" colspan="2">
								请输入证件编号:
							</th>
						</tr>
						<s:iterator value="DocTypeList" status="doct">
							<tr><td width="50%">
									<s:property value="xmgguid"/>
									<span style="color:#BFBFBF;font-size:13px;">(<s:property value="clguid"/>，如：<s:property value="zzname"/>)</span>
									</td>
									<td width="50%">
										<s:hidden name="guid" id="cardGuid"></s:hidden>
										<s:hidden name="zzguid" id="docTypeGuid"></s:hidden>
										<s:textfield  cssClass="zc_input02" size="25" name="cardid" id="docTypeNum"></s:textfield>
										<s:hidden name="clguid" id="docTypeNumName"></s:hidden>
									</td>
								</tr>
							</s:iterator>	
						<tr><td style="text-align:center" colspan="2">
						<input type="button" value="确定" onclick="saveDocType()"; class="buttonClass_4">
						</td></tr>
				</table>
				
</body>
</html>
