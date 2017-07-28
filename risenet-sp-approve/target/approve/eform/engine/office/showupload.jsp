<%@ page contentType="text/html;charset=gbk" %><%@ page language="java" 
import="java.io.*,java.sql.*"%><%@ page import="net.sysmain.common.Operator"%><%@ page 
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

String pguid = request.getParameter("pguid")==null?"":request.getParameter("pguid").toString().trim();
String currentUserName = "";
String tid = request.getParameter("tid");

if(tid != null)
{
    tid = "&tid=" + tid;
}
else
{
    tid = "";
}
%>
<html>
<head>
 <title>文件上载</title>
   <meta http-equiv=Content-Type content="text/html; charset=gb2312">
   <meta http-equiv="pragram" content="no-cache">
   <meta http-equiv="expires" content="0">
</head>
<body oncontextmenu="return false;" style="background-color:#FFFFFF">
    <form id="form1" method="post" action="uploadfile.jsp?pguid=<%=pguid%><%=tid%>" enctype="multipart/form-data" style="padding:0px;margin:0px;"><br>
    <div align="center">文件：<input type="file" name="filename"></div><br>
    <div align="center"><input type="submit" value="提 交"></div>
    </form>
</body>
</script>
</html>
