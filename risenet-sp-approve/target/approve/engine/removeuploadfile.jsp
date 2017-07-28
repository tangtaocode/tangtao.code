<%
/*
成功后的函数调用
*/
%>
<%@ page language="java"%>
<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="net.business.engine.eo.UpLoadResult" %>
<%@ page import="net.sysmain.common.I_CommonConstant" %>
<%
String message = null;
String cllaWin = request.getParameter(I_CommonConstant.CALL_WINDOW);
String seqIds =  request.getParameter("seqIds");
UpLoadResult ur = new UpLoadResult();

if(seqIds == null || seqIds.equals(""))
{
    ur.deleteAttachment(application, request);
}
else
{
     ur.deleteAttachFromDb(application, request);
}
if(cllaWin == null) cllaWin = "null";

if(ur.getCode() == -1)
{
    message = ur.getMessage();
}
if(message == null) message = "";
%>
<script language="JavaScript">
<!--
var paraWin = <%=cllaWin%>
var message = "<%=message%>";
var code = <%=ur.getCode()%>;
var componentName = "<%=ur.getComponentName()%>";

if(message != "") 
{
    alert(message);
}
if(code == 0 && paraWin != null)
{
    try
    {
        paraWin.removeFromList(componentName);
    }
    catch(e)
    {
        ;//do nothing
    }
}
//-->
</script>

