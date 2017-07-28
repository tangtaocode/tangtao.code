<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<SCRIPT language=JScript event="OnCompleted(hResult,pErrorObject, pAsyncContext)" for=foo> 
MACAddr=unescape(MACAddr); 
//document.forms[0].txtIPAddr.value=unescape(IPAddr); 
//document.forms[0].txtDNSName.value=unescape(sDNSName); 
</SCRIPT> 
<SCRIPT language=JScript event=OnObjectReady(objObject,objAsyncContext) for=foo> 
	if(objObject.IPEnabled != null && objObject.IPEnabled != "undefined" && objObject.IPEnabled == true) 
	{ 
		if(objObject.MACAddress != null && objObject.MACAddress != "undefined") 
			MACAddr = objObject.MACAddress; 
// 		if(objObject.IPEnabled && objObject.IPAddress(0) != null && objObject.IPAddress(0) != "undefined") 
// 			IPAddr = objObject.IPAddress(0); 
// 		if(objObject.DNSHostName != null && objObject.DNSHostName != "undefined") 
// 			sDNSName = objObject.DNSHostName; 
	} 
</SCRIPT> 
<META content="MSHTML 6.00.2800.1106" name=GENERATOR></HEAD> 
<BODY> 
<OBJECT id=locator classid=CLSID:76A64158-CB41-11D1-8B02-00600806D9B6 VIEWASTEXT></OBJECT> 
<OBJECT id=foo classid=CLSID:75718C9A-F029-11d1-A1AC-00C04FB6C223></OBJECT> 
<SCRIPT language=JScript> 
	var service = locator.ConnectServer(); 
	var MACAddr ; 
	var IPAddr ; 
	var DomainAddr; 
	var sDNSName; 
	service.Security_.ImpersonationLevel=3; 
	service.InstancesOfAsync(foo, 'Win32_NetworkAdapterConfiguration'); 
</SCRIPT> 
<!-- client MAC Addr End -->
<!-- client 评价器 -->
<OBJECT id=pjq style="LEFT: 0px; WIDTH: 0px; TOP: 0px; HEIGHT: 0px" codeBase="${ctx}/static/tags/ocx/SLE300.ocx" classid=clsid:61F112FC-2AEA-4E34-82A6-4C94AF61152E>
<PARAM NAME="_Version" VALUE="65536"><PARAM NAME="_ExtentX" VALUE="926">
<PARAM NAME="_ExtentY" VALUE="423"><PARAM NAME="_StockProps" VALUE="0">
</OBJECT>
<script type="text/javascript" src="${ctx}/static/risesoft/js/estimate.js"></script>
