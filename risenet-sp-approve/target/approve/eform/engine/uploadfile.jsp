<%@ page language="java"%>
<%
/*
 文件上载的通用程序，基本要求调用参数
1、文件上载目录的存储
2、接受数据的窗体对象
3、接受数据的窗体提供一个函数为:
   setListFilesValue(参数) ；
   参数格式为：
       实际文件名(回车换行)
       文件存储的URL
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
