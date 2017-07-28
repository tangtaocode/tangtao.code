<%@ page language="java" %><%@ page import="net.sysmain.common.ConnectionManager"%><%@ page 
import="java.sql.Connection"%><%@ page import="java.sql.ResultSet" %><%
String id = request.getParameter("id");

Connection conn = null;
ResultSet rs = null;
byte[] signData;
java.io.InputStream signDataStream = null;
javax.servlet.ServletOutputStream myOutputStream = response.getOutputStream();

try
{
    conn = ConnectionManager.getInstance().getConnection();
    rs = conn.createStatement().executeQuery("select FileContent from ElecStampData_sys where guid='" + id + "'");
    if(rs.next())
    {
        response.reset();
        response.setContentType("applcation/octet-stream");
        signDataStream = rs.getBinaryStream("FileContent");
        signData = new byte[1024];
        int readCount = 0 ;
        while((readCount=signDataStream.read(signData,0,1024))!=-1)
        {
             myOutputStream.write(signData,0,readCount);
        }
        myOutputStream.flush();
        response.flushBuffer();
    }
}
catch(Exception ex)
{
    ex.printStackTrace();
}
finally
{
    ConnectionManager.close(conn);
}
%>