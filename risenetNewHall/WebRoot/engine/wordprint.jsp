<%
/**
 ���ܣ�word�򿪳���   
 ������
      wn:wordģ������,ָ��Ŀ¼�µ�word�ļ�(���Բ�������չ����)
      temp_Id������ģ�壬���ģ��ʹ��,����(���ָ����ʾģ��)  
      querrystring��ģ�����(����ģ���蹫�õĲ���)������querystring=pr.id=14
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
<title>ģ���ӡ</title>
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
<br><!--  input type="button" value="��ӡ" onClick="runMain('<%=request.getScheme() + "://" + request.getServerName() + ((request.getServerPort()==80)?"":":" +
                request.getServerPort()) + request.getContextPath()%>/engine/temp/<%=wn%>')"-->
</body>	
<script>
runMain('<%=request.getScheme() + "://" + request.getServerName() + ((request.getServerPort()==80)?"":":" +
                request.getServerPort()) + request.getContextPath()%>/engine/temp/<%=wn%>')
</script>
</html>