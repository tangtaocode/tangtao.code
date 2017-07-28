<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>填写个人/机构代码</title>
<!--框架必需start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/language/cn.js"></script>
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/framework.js"></script>
<link href="${ctx}/static/QUI/libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="${ctx}/static/QUI/" scrollerY="false"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->
<!--数据表格start-->
<script src="${ctx}/static/QUI/libs/js/table/quiGrid.js" type="text/javascript"></script>
<!--数据表格end-->
<!--表单异步提交start-->
<script src="${ctx}/static/QUI/libs/js/form/form.js" type="text/javascript"></script>
<!--表单异步提交end-->
<!-- 日期控件start -->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/form/datePicker/WdatePicker.js"></script>
<!-- 日期控件end -->
<!--弹窗组件start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/popup/drag.js"></script>
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/popup/dialog.js"></script>
<!--弹窗组件end-->
<!--弹出式提示框start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/popup/messager.js"></script>
<!--弹出式提示框end-->
<script type="text/javascript">
	
</script>
</head>
<body onload="FindReader_onclick();">	
<object classid="clsid:46E4B248-8A41-45C5-B896-738ED44C1587" id="SynCardOcx1" codeBase="SynCardOcx.CAB#version=1,0,0,1" width="0" height="0" >
</object>
	<div>
		<form action="${ctx}/togetherWindow/newReceive" id="form" method="post" align="center">		
		<table>
			<tr>
				<td align="center"><font style="font-size:14px;">个人身份证号或企业机构代码</font></td>			
			</tr>
			<tr>
				<td align="center">
				<input id="guid" name="guid" value="${guid }" type="hidden"/>
				<input id="approvePlace" name="approvePlace" value="${approvePlace }" type="hidden"/>
				<input id="code" name="value"></input>&nbsp;<input type="button" id="cmd" value="确定" onclick="returnV();"></input></td>
			</tr>
			<tr>
				<td align="left"><span id="message" style="color:red"></span></td>
			</tr>
		</table>
		</form>
	</div>	
<script language="JavaScript1.2">
  function FindReader_onclick()
  {
  	 var str;
  	str = SynCardOcx1.FindReader();
  	if (str <= 0)
  	{
		$('#message').text("设备连接失败");
  	}else{
  		//每隔两秒钟执行一次
		setInterval("ReadCard_onclick()",2000);		
	} 
	
  }

 
  function ReadCard_onclick()
  {
	var nRet;
		
  	nRet = SynCardOcx1.ReadCardMsg();
  	if(nRet==0)
  	{

		document.getElementById("code").value=SynCardOcx1.CardNo;
		//document.getElementById("username").value=SynCardOcx1.NameA;
		returnV();
  	}
  }
  
  function returnV(){
	//取消定时器
	clearInterval("ReadCard_onclick()");  
	var itemGuid = $("#guid").val();
	var typeGuid='';
   	var value=$.trim($('#code').val());
   	//var username=$('#username').val();
   	if(value==''){
    	 alert("证件号码不能为空！");
    	 return;
     }	  	
   	this.parent.window.location.href="${ctx}/togetherWindow/newReceive?guid=${guid }&value="+value;
	
	this.parent.Dialog.close();
}


</script>	
</body>
</html>
