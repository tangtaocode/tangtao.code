<%@ page language="java" %><%@ page contentType="text/html; charset=gb2312"%><%@ page 
import="net.business.engine.DataEngine"%><%@ page 
import="net.business.engine.common.MessageObject"%><%        
DataEngine de = null;
MessageObject mo = null;

de = DataEngine.getInstance();
mo = de.deleteDataByJob(request);
%>
<html>
<script language="JavaScript">
<!--
<%if(mo.getCode() != 0){%>alert("<%=mo.getMessage()%>");
<%}else{%>
try
{
    parent.refresh();
}
catch(e)
{
    ;
}
location = "blank.htm";
<%}%>
//-->
</script>
</html>