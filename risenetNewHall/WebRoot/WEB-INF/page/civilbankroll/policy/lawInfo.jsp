<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../share/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>深圳市龙岗区住房与建设局行政服务大厅--政策法规</title>
<meta http-equiv="keywords" content="网上行政服务大厅,政策法规,服务大厅"/>
<meta http-equiv="description" content="政策法规"/>
<link href="/css/index.css" rel="stylesheet" type="text/css" />
<link href="/css/pageStyle.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
.table_index{
	border-width: 1px;
	border-style: solid;
	border-color: #ACE8FC;
}
</style>
</head>

<body style="text-align:center">

<div id="container" >
<jsp:include page="../../home/menu.jsp"/>
<div class="main">
<s:form action="/civilPolicy/findPolicy.YS" method="post">
<table width="1000" border="0" align="center" cellpadding="0" cellspacing="0" class="table_index">
  <tr>
    <td height="1px" colspan="2"></td>
  </tr>
  <tr>
    <td width="100%" valign="top" >
  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="right_top">
      <tr>
        <td width="1%"><img src="/images/page/right_top1.jpg" width="11" height="36" /></td>
        <td width="97%">
        <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td width="4%"><img src="/images/page/icon.png" width="24" height="24" /></td>
            <td width="30%" style="text-align: left">民生创新==》扶持政策==》详细信息 </td>
          	<td width="60%" style="text-align: right" class="userInfo" id="loginUserInfo"><s:property value="userString"/></td>
          </tr>
        </table></td>
         <td width="2%" align="right"><img src="/images/page/right_top2.jpg" width="11" height="36" /></td>
      </tr>
      </table>
     </td> </tr>
     <tr>
     <td >
     <!-- 开始 -->
      <table width="1000" border="0" align="center" cellpadding="0" cellspacing="0" class="table_index">
  <tr>
    <td class="font_xxxx_title"><s:property value="civilPolicyBean.title"/>
    <hr width="100%" size="1" noshade /><center><font class="font_xx_right">发布时间：<s:property value="civilPolicyBean.starttime"/> </font></center></td>
  </tr>

  <tr>
    <td valign="top">
    	<table width="98%" border="0" cellpadding="0" cellspacing="0"  >
    		<tr>
    			<td width="2%">&nbsp;</td>
    			<td align="left"><s:property value="civilPolicyBean.content" escape="false"/> </td>
    			<td width="2%">&nbsp;</td>
    		</tr>
    	</table>
    </td>
  </tr>
  <tr>
    <td align="right">
    <img src="/images/page/guanbi.jpg" width="75" height="24" onclick="window.close();"/>&nbsp;&nbsp;
    <a href="#" onclick="print();"><img src="/images/page/dayin.jpg" width="75" height="24" border="0" /></a>&nbsp;&nbsp;&nbsp;&nbsp;</td>
  </tr>
</table>
	<!-- 结束 -->
	 </td>
     </tr>
     </table>
</s:form>
<object classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2 height=0 id=WB width=0></object>
<jsp:include page="../../home/bottom.html"/>
</div>
</div>
</body>
</html>
<script>
changeMenuBg(13,13);
</script>