<%@ page language="java"%>
<%
/*
 �ļ����ص�ͨ�ó��򣬻���Ҫ����ò���
1���ļ�����Ŀ¼�Ĵ洢
2���������ݵĴ������
3���������ݵĴ����ṩһ������Ϊ:
   setListFilesValue(����) ��
   ������ʽΪ��
       ʵ���ļ���(�س�����)
       �ļ��洢��URL
*/
%>
<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="net.business.engine.TemplateEngine" %>
<%@ page import="net.business.engine.eo.UpLoadResult" %>
<%
  TemplateEngine te = TemplateEngine.getInstance();
  UpLoadResult ur = null;

  try
  {
      ur = te.uploadFile(application, request, response); 
  }
  catch(Exception ex)
  {
      ex.printStackTrace();
  }
  //System.out.println(ur.getFileList());
%>
<html>
<script language="JavaScript">
<!--
var paraWin = <%=ur.getCallWindow()%>;
var fileList = "<%=ur.getFileList()%>";
var code = <%=ur.getCode()%>;
var message = "<%=ur.getMessage()%>";
var componentName = "<%=ur.getComponentName()%>";

if(code != -1 && paraWin != null)
{
    try
    {
        if(fileList != "") paraWin.setListFilesValue(fileList, componentName);
    }
    catch(e)
    {
        alert(e);
    }
}
if(message != "") alert(message);
//self.close();
//-->
</script>
</html>
