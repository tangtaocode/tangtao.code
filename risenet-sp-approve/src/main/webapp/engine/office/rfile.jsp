<%@ page contentType="text/html;charset=gbk"%><%@ page import="net.sysmain.common.Operator"%><%@ page 
import="net.sysmain.common.I_UserConstant"%><%
Operator op = (Operator)request.getSession().getAttribute(I_UserConstant.USER_INFO);
if(op == null)
{
    return;
}

net.business.db.OfficeManager om = new net.business.db.OfficeManager();
om.deleteFile(request);
%>
<html>
<script language="Javascript">
try{opener.doRefresh()}catch(e){}
self.close();
</script>
</html>