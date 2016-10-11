<%@ page contentType="text/html;"%><%@ page import="net.sysmain.common.Operator"%><%@ page 
import="net.sysmain.common.I_UserConstant"%><%@ page import="net.sysmain.common.upload.SingleFileUpload"%><%@ page 
import="net.sysmain.util.StringTools"%><%
response.setHeader("Pragma","no-cache");
response.setHeader("Expires","0");
response.setHeader("CacheControl","max-age=0");
response.setHeader("Cache-Control","no-store");

/**
   1、判断系统是否超时
**/
Operator op = (Operator)request.getSession().getAttribute(I_UserConstant.USER_INFO);

if(op == null)
{%> <html>
    <script type="text/javascript">
    window.parent.CKEDITOR.tools.callFunction(2, "", "系统超时，请重新登录访问");
    </script>
    </html>
<%return;
}

/**
   2、文件上载
**/
String msg = null;
String fileName = null;

try
{
    String queryString = net.business.engine.common.Tools.getQueryString(request);
    //System.out.println(queryString);
    String type = "file";
    String[] qValues = queryString.split("&");
    for(int i=0; i<qValues.length; i++)
    {
        if(qValues[i].equals("type=image")) {type = "image"; break;}
    }
    String relativePath = "image".equals(type)?"/UserFiles/Image/":"/UserFiles/File/";
    String path = application.getRealPath(relativePath);
    
    //禁止上传文件名限制
    String[] denyExtFile = null;
    String denyExt =  net.sysmain.util.GlobalParameter.getInstance().getParameter("deniedExtensionsFile");
    
    if(denyExt != null && !"".equals(denyExt))
    {
        denyExtFile = denyExt.split("\\|");
    }
    //产生一个随机的文件名
    fileName = net.sysmain.util.Tools.getUniqueNameByTime();

    SingleFileUpload upload = new SingleFileUpload(path, fileName, denyExtFile);
    upload.setEncoding("utf-8");
    upload.doUpload(request);
    //返回最终的文件名
    fileName = relativePath + upload.getFileName();
}
catch(Exception ex)
{
    ex.printStackTrace();
    if(!(ex instanceof net.sysmain.common.exception.TemplateMessageException)) ex.printStackTrace();%>
<html>
    <script type="text/javascript">
    window.parent.CKEDITOR.tools.callFunction(2, "", "<%=StringTools.toJavaScript(ex.getMessage())%>");
    </script>
    </html>
<%return;}%> 
<html>
  <script type="text/javascript">
  window.parent.CKEDITOR.tools.callFunction(2, "<%=fileName%>");
  </script>
</html>