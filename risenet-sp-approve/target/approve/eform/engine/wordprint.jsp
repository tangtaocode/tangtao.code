<%
/**
 功能：word打开程序   
 参数：
      wn:word模板名称,指定目录下的word文件(可以不包含扩展名称)
      temp_Id：参数模板，多个模板使用,隔开(最好指定显示模板)  
      querrystring：模板参数(所有模板需公用的参数)。例：querystring=pr.id=14
**/
%><%@ page contentType="text/html; charset=gbk"%><%@ page 
import="net.business.db.DataRetrieve" %><%
response.setHeader("Pragma","no-cache");
response.setHeader("Expires","0");
response.setHeader("CacheControl","max-age=0");
response.setHeader("Cache-Control","no-store");
String wn = request.getParameter("wn");

if(wn == null) return;
if(!wn.endsWith(".doc")) wn = wn + ".doc";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>模板打印</title>
<script src="sinc/map.js" language="javascript"></script>
<script src="sinc/word.js" language="javascript"></script>
</head>
<body>
<%
DataRetrieve dt = null;
try
{
    dt = new DataRetrieve(request);
    out.print(dt.getXmlWordPrintData());
}
catch(Exception ex)
{
    ex.printStackTrace();
}
finally
{
    //if(dt != null) dt.close();
}
%>	
<br><!--  input type="button" value="打印" onClick="runMain('<%=request.getScheme() + "://" + request.getServerName() + ((request.getServerPort()==80)?"":":" +
                request.getServerPort()) + request.getContextPath()%>/engine/temp/<%=wn%>')"-->
</body>	
<script>
runMain('<%=request.getScheme() + "://" + request.getServerName() + ((request.getServerPort()==80)?"":":" +
                request.getServerPort()) + request.getContextPath()%>/engine/temp/<%=wn%>')
</script>
</html>