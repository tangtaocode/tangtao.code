<%@ page contentType="text/html;charset=gbk"%><%@ page import="net.sysmain.common.Operator"%><%@ page 
import="net.sysmain.common.I_UserConstant"%><%
Operator op = (Operator)request.getSession().getAttribute(I_UserConstant.USER_INFO);
if(op == null)
{
    request.setAttribute(I_UserConstant.MESSAGE_INFO, "系统可能超时，请重新登录访问");
    try
    {
        request.getRequestDispatcher("/engine/message.jsp").forward(request,response);
    }
    catch(Exception ex){}
    return;
}
net.business.db.OfficeManager om = new net.business.db.OfficeManager();

String msg = null;
try
{
msg = om.uploadFile(request);
%><script><%if(msg != null){%>
alert("<%=msg%>");
<%}else{%>
var openWin = (parent)?parent:opener;
try{openWin.doRefresh()}catch(e){}
<%}%>
self.close();
</script>
<%}catch(Exception ex){%><html><head>
   <meta http-equiv="Content-Type" content="text/html; charset=gb2312"></head><body oncontextmenu="return false;" style="background-color:#FFFFFF"><br>
<div align="center"><%=ex.getMessage()%>&nbsp;&nbsp;<a href="javascript:history.back()">返回</a></div></body></html>
<%}%>