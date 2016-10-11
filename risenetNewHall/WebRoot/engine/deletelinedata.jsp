<%@ page language="java" %><%@ page contentType="text/html; charset=gb2312"%><%@ page 
import="net.business.engine.DataEngine"%><%@ page 
import="net.business.engine.common.MessageObject"%><%@ page 
import="net.business.engine.ListFieldEvent"%><%
request.setCharacterEncoding("gb2312");                    
DataEngine de = null;
MessageObject mo = null;
String formName = request.getParameter(ListFieldEvent.DELETE_FORM_CTRL_NAME);
if(formName == null) 
{
    formName = "null";
}
else
{
    formName = "\"" + formName + "\"";
}
de = DataEngine.getInstance();
mo = de.deleteLineData(request);
%>
<html>
<script language="JavaScript">
<!--
<%if(mo.getCode() != 0){%>alert("<%=mo.getMessage()%>");
<%}else{%>
try
{
    parent.refresh(<%=formName%>);
}
catch(e)
{
    alert(e);
}
<%}%>
location = "blank.htm";
//-->
</script>
</html>