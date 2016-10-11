<%@ page contentType="image/pjpeg;charset=gb2312"%><%
javax.servlet.ServletOutputStream stream = response.getOutputStream();

String id = request.getParameter("id");
String path = application.getRealPath("/engine/template/sign/");
byte[] bt = new byte[10000];
int readLength = 0;

if(!path.endsWith(java.io.File.separator)) path = path + java.io.File.separator;

java.io.InputStream in = new java.io.FileInputStream(path + id);
while(readLength != -1)
{
    if((readLength = in.read(bt)) != -1)
    {
        //System.out.println(readLength);
        stream.write(bt, 0, readLength);
    }
}
in.close();
stream.close();
%>