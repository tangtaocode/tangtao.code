<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="../../share/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>深圳市龙岗区住房与建设局行政服务大厅--政策法规</title>
<meta http-equiv="keywords" content="网上行政服务大厅,政策法规,服务大厅"/>
<meta http-equiv="description" content="政策法规"/>
<link href="/css/pageStyle.css" rel="stylesheet" type="text/css"/>
<link href="/css/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/js/main.js"></script>
<script type="text/javascript">
function loadDivPage(){
	document.forms[0].action='/civilNotice/resultPage.YS'
	document.forms[0].submit(); 
}
function doReset(){
	document.forms[0].reset();
	return; 
}
</script>
</head>
<body style="text-align:center">
<div id="container" >
<jsp:include page="../../home/menu.jsp"/>
<div class="main">
<s:form action="/civilNotice/queryPage.YS" method="post" id="queryFormId" target="queryIframe">
<table width="1000" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="1px" colspan="2"></td>
  </tr>
  <tr>
    <td width="100%" valign="top">
       <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="right_top">
      <tr>
        <td width="1%"><img src="/images/page/right_top1.jpg" width="11" height="36" /></td>
        <td width="97%">
        <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td width="4%"><img src="/images/page/icon.png" width="24" height="24" /></td>
            <td width="30%" style="text-align: left">产业扶持==》通知公告  </td>
          	<td width="60%" style="text-align: right" class="userInfo" id="loginUserInfo"><s:property value="userString"/></td>
          </tr>
        </table></td>
         <td width="2%" align="right"><img src="/images/page/right_top2.jpg" width="11" height="36" /></td>
      </tr>
      </table>
      <table width="100%" border="0" cellpadding="0" cellspacing="0" class="right_table">
    	<tr>
    	<td class="serachTd">
    	<table width="100%" border="0" cellspacing="0" cellpadding="0" id="searchTableId">
    	<tr><td colspan="5" height="5"></td> </tr>
    	<tr>
    		<th width="10%" >公告名称：</th>
    		<td width="15%"> <s:textfield  name="title" cssClass="input_blue2" ></s:textfield></td> 
    	 <td align="left"  width="30%" nowrap="nowrap">
    	 	<a href="#" onclick="loadDivPage()"><img src="/images/page/ser.gif" width="62" height="20"/> </a>
         	  &nbsp;&nbsp; 
         	  <a href="#" onclick="doReset()"><img src="/images/page/cz.gif" width="62" height="20"/> </a>
         	</td>
    	</tr>
    	</table>
    	</td>
    	</tr>
        <tr>
          <td class="resultTd" >
           <iframe id="queryIframe" name="queryIframe" onload="reinitIframe()" width="100%"  frameborder="0" scrolling="no" src="/civilNotice/resultPage.YS">
           </iframe>
			</td>
          </tr>
	</table>
    </td>
  </tr>
</table>
</s:form>
<jsp:include page="../../home/bottom.html"/>
</div>
</div>
</body>
</html>

<script language="JavaScript">
changeMenuBg(13,13);
</script>