<%@ page contentType="text/html; charset=GBK"%><%@ page language="java" %><%@ page 
import="net.sysmain.common.I_UserConstant"%><%@ page import="net.sysmain.common.I_CommonConstant"%><%
//信息提示公共页
response.setHeader("Pragma","no-cache");
response.setHeader("Expires","0");
response.setHeader("CacheControl","max-age=0");
response.setHeader("Cache-Control","no-store");
%>
<%
String sMessage = (String)request.getAttribute(I_UserConstant.MESSAGE_INFO);
String backUrl =  (String)request.getAttribute(I_CommonConstant.BACK_URL);
String reload =  (String)request.getAttribute(I_CommonConstant.REFRESH_OPENER);
String script =  (String)request.getAttribute(I_CommonConstant.JAVA_SCRIPT_CODE);

if(script != null && !script.trim().equals(""))
{//立即执行的代码%>
<html>
<script language="JavaScript">
<!--
  <%=script%>
//-->
</Script>
</html>
<%
}
if(sMessage == null) sMessage = "";
%>
<html>
<head>
<title>消息提示</title>
</head>
<body leftmargin="0" topmargin="0" rightMargin="0" bottomMargin="0" background="simg/shading.jpg" <%if(reload!=null && reload.equals("yes")){%> onunLoad="if(window.opener != null)window.opener.refresh();"<%}%>>
<table style="width:100%" height="100%" cellspacing="0" cellpadding="0" border='0'>
  <tr>
    <td align="center" valign="top">
      <br><br><br><br><br><br><br>
      <table width="380" height="137" border="1" bordercolor="#E0E0E0">
        <tr>
          <td height="23" align="center" style="font-size: 12px"><b>提示信息&nbsp;</b></td>
        </tr>
        <tr>
          <td align="center" style="font-size: 12px"><b>
            <%
            out.println(sMessage+"<br><br>");
            Object obj = request.getAttribute("exception");
            if(obj!=null)
            {
                Exception exception = (Exception)obj;
                out.println(exception.getMessage());
                out.println("<hr>");
            }
            %>
            </b></td>
      </tr>
    </table><p align="center" style="font-size:12px;">
    <%if(backUrl != null){%><span>[<a href="<%=backUrl%>"><font style="color:#0000E0;text-decoration:none">返 回</font></a>]</span><%}%>
    <script language="JavaScript">
    <!--
        if(window.opener != null)
        {
            document.write("<span>[<a href=\"#\" onclick=\"self.close();\"><font style=\"color:#5050E0;font-size:12px;text-decoration:none\" >关闭窗口</font></a>]</span>");
        }

		self.focus();
    //-->
    </script></p>
   </td>
  </tr>
</table>

</body>
</html>