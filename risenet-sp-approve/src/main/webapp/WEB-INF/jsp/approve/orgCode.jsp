<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>填写个人/机构代码</title>
<!-- 树结构引用 -->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/form/selectTree.js"></script>
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/tree/ztree/ztree.js"></script>
<link href="${ctx}/static/QUI/libs/js/tree/ztree/ztree.css" rel="stylesheet" type="text/css" />
<!--树结构end -->


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
		document.getElementById("username").value=SynCardOcx1.NameA;
		returnV();
  	}
  }
  
  function returnV(){
	//取消定时器
	clearInterval("ReadCard_onclick()");  
	var itemGuid = $("#guid").val();
	var typeGuid='';
   	var value=$.trim($('#code').val());
   	var username=$('#username').val();
   	if(value==''){
    	 alert("证件号码不能为空！");
    	 return;
     }	  	
   	this.parent.window.location.href="${ctx}/windowApprove/newInstanceAction?guid=${guid }&value="+value+"&username="+username;
}




function load(){
FindReader_onclick();
}
</script>

</head>
<body leftmargin="0" topmargin="0" onLoad="load();">
<object classid="clsid:46E4B248-8A41-45C5-B896-738ED44C1587" id="SynCardOcx1" codeBase="SynCardOcx.CAB#version=1,0,0,1" width="0" height="0" >
</object>  
		<div>
		<form action="${ctx}/windowApprove/newInstanceAction" id="form" method="post" align="center">		
		<table style="text-align:center" width="100%">
			<tr>
 				<td align="left" width="30%"><font style="font-size:14px;">请输入证件号码：</font></td>							
 					<td align="left">
				<input id="guid" name="guid" value="${guid}" type="hidden"/>
				<input id="username" type="hidden"/>
				</td>
			</tr>			
			<tr>
				<td colspan="2" style="margin-left:10px;" align="left">
				<input id="code" name="value" style="width:200px"></input>&nbsp;
					<input type="button" id="cmd" value="确定" onclick="returnV();"></input>					
				</td>
			</tr>
			<tr>
				<td align="left"><span id="message" style="color:red"></span></td>
			</tr>
		</table>
		</form>
	</div>
</body>

</html>