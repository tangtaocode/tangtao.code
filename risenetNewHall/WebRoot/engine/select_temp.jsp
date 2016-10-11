<%/*
该文件是一个临时的程序
入口参数
ma  主表别名
ta  当前表别名
tId 当前模板ID
*/
%>
<%@ page language="java" %>
<%@ page contentType="text/html; charset=gb2312"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="net.business.engine.cache.ObjectCache"%>
<%@ page import="net.sysmain.common.ConnectionManager"%>
<%@ page import="net.sysmain.common.I_UserConstant"%>
<%@ page import="net.sysmain.common.Operator"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.Statement"%>
<%@ page import="java.sql.ResultSet"%>

<%@ page import="net.sysmain.common.I_UserConstant"%>

<%request.setCharacterEncoding("gb2312");%>
<%
ObjectCache cache = ObjectCache.getInstance();
int yearNum = Calendar.getInstance().get(Calendar.YEAR);
String userId = "test";
String maintable = null;
String currentTable = null;
String ma = request.getParameter("ma");
String ta = request.getParameter("ta");
String tId = request.getParameter("tId");
String type =  request.getParameter("type");
String pType = "";
Operator opt = (Operator)request.getSession().getAttribute(I_UserConstant.USER_INFO);
String sqlMain = null;
String sql = null;
Connection conn = null;
Statement smt = null;
ResultSet rs = null;
String GUID = null;
String url = null;
boolean isAdd = false;
String message = null;

if(opt != null)
{
    userId = opt.getUserId();
}

if(ma != null)
{
   maintable = cache.getTableNameByAlias(ma);
}
if(ta != null)
{
   currentTable = cache.getTableNameByAlias(ta);
}

//System.out.println("***********************************");
//System.out.println(maintable);
//System.out.println(currentTable);
//Connection conn = ConnectionManager.getInstance().getConnection();
//进入到主表
if(maintable == null || tId == null)
{
    out.println("<html>URL参数设置错误</html>");
    return;
}
//业务类型
if(type != null)
{
    pType = "&type=" + type;
    type = " and a.BusinessType='" + type + "'";   
}
else
{
    type = "";
}

sqlMain = "select a.GUID from " + maintable + " a left outer join User_ReInfor_Master b on a.GUID=b.GUID where a.UnitLoginID='" + userId + "'" + " and a.year=" + yearNum + type + " and (b.isComplete is null || b.isComplete<>1)";

try
{
    System.out.println(sqlMain);
    conn = ConnectionManager.getInstance().getConnection();
    smt = conn.createStatement();
    rs = smt.executeQuery(sqlMain);
    if(rs.next()) GUID = rs.getString("GUID");
}
catch(Exception ex)
{
    ex.printStackTrace();
    message = ex.getMessage();
}
finally
{
    ConnectionManager.close(rs);
    ConnectionManager.close(smt);
}

//只进入主表
if(message == null)
{
    if(currentTable == null)
    {
        if(GUID == null)   //主表新增
        {
            if(tId.equals("10"))
            {
                url = "gettemplate.jsp?temp_Id=" + tId + pType + "&querystring=reg.LoginID='" + opt.getUserId() + "'";
            }
            else
            {
                url = "gettemplate.jsp?temp_Id=" + tId + pType;
            }
        }
        else  //主表修改
        {
           url = "gettemplate.jsp?temp_Id=" + tId + "&edittype=1&querystring=" + ma + ".GUID='" + GUID + "'" + pType;
        }
    }
    else  //关联表处理
    {
        sql = "select GUID from " + currentTable + " where GUID='" + GUID + "'";
        try
        {
            smt = conn.createStatement();
            rs = smt.executeQuery(sql);
            if(rs.next()) //关联表修改
            {
                url = "gettemplate.jsp?temp_Id=" + tId + "&edittype=1&querystring=" + ta + ".GUID='" + GUID + "'";
            }
            else  //关联表新增
            {
                if(GUID != null)
                {
                    if(tId.equals("10"))
                    {
                        url = "gettemplate.jsp?temp_Id=" + tId + "&GUID=" + GUID + "&querystring=reg.LoginID='" + opt.getUserId() + "'";
                    }
                    else
                    {
                        url = "gettemplate.jsp?temp_Id=" + tId + "&querystring=" + ma + ".GUID='" + GUID + "'" + pType;
                    }
                }
                else
                {//错误的地址
                    message = "请先填写基本信息表";
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            message = ex.getMessage();
        }
        finally
        {
            ConnectionManager.close(rs);
            ConnectionManager.close(smt);
        }
    }
}

System.out.println("url:" + url);
ConnectionManager.close(conn);
if(message != null)
{
    request.setAttribute(I_UserConstant.MESSAGE_INFO, message);
    request.getRequestDispatcher("message.jsp").forward(request, response); 
    return;
}

//URL错误
if(url != null)
{
    response.sendRedirect(url);
}
%>
