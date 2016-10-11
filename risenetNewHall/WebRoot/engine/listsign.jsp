<%/*
  
  签名数据字典的显示

*/%>
<%@ page language="java" %><%@ page contentType="text/html; charset=GBK"%><%@ page import="net.sysmain.common.Operator"%>
<%@ page import="net.business.engine.manager.AppDataManager"%><%
request.setCharacterEncoding("gbk");
Operator operator = (Operator) session.getAttribute(net.sysmain.common.I_UserConstant.USER_INFO);
String url = "listsign.jsp?p=" + ((request.getParameter("p")==null)?"":request.getParameter("p"));

if("del".equals(request.getParameter("a")))
{
    AppDataManager.deleteOpinionDic(operator, request.getParameter("guid"));
}
else if("add".equals(request.getParameter("a")))
{
    AppDataManager.addOpinionDic(operator, request.getParameter("value"),  request.getParameter("p"),  request.getParameter("lc"));
}
else
{
    url = null;
}

if(url != null)
{
    response.sendRedirect(url);
    return;
}
java.util.ArrayList list = AppDataManager.getOpinionDic(operator, request.getParameter("p"));

String clickEvent = " onclick=\"focusLine()\" ondblclick";
%>
<html>
<head>
<style>
td{padding-left:3px;font-size:13px}
</style>
<script language="JavaScript">
<!--
var selectedLine = null;
function hideMenu()
{
    try{parent.document.getElementById(parent.__signMenu).style.display = "none"}catch(e){}
}

function __getEvent()
{
    if(window.event)
    {
        return window.event.srcElement;
    }

    var _caller = __getEvent.caller;
    while(_caller != null)
    {
        var _argument = _caller.arguments[0];
        if(_argument)
        {
            var _temp = _argument.constructor;
            if(_temp.toString().indexOf("Event") != -1)
            {
                return _argument.target;
            }
        }
        _caller = _caller.caller;
    }
    return null;
}

function setSignValue(obj)
{
    var s = parent.__signMenu;
    if(s == null) return;
    if(selectedLine != null)
    {
        selectedLine.style.backgroundColor = "";
        selectedLine.style.color = "";
        selectedLine = null;
    }
    eval("parent." + s.replace(/_menu/,"") + "_setAssign(obj)");
}

function focusLine()
{
    if(selectedLine != null)
    {
        selectedLine.style.backgroundColor = "";
        selectedLine.style.color = "";
    }
    selectedLine = __getEvent();
    while(selectedLine != null && selectedLine.tagName != "TD")
    {
        selectedLine = selectedLine.parentElement;
    }
    selectedLine.style.backgroundColor = "#303030";
    selectedLine.style.color = "#FFFFFF";
}

function deleteLine(guid)
{
    if(!confirm("确认删除吗？")) return;
    location = "listsign.jsp?p=<%=(request.getParameter("p")==null)?"":request.getParameter("p")%>&a=del&guid=" + guid;
}


function setValue(value, lc)
{
    location = "listsign.jsp?lc=" + lc + "&p=<%=(request.getParameter("p")==null)?"":request.getParameter("p")%>&a=add&value=" + value;
}

function showAdd()
{
    window.open("addlistsign.htm", "_blank", "top=350,left=400,width=300,height=150,scrollbars=no")
}
//-->
</script>
</head>
<body bgcolor="#FFFFFF" leftmargin=0 topmargin=0  oncontextmenu="return false" ondragstart="return false" onselectstart ="return false" onselect="document.selection.empty()"
oncopy="document.selection.empty()" onbeforecopy="return false" onmouseup="document.selection.empty()" scroll="no" style="overflow-x:hidden">
<div align="right" style="width:100%;font-size:12px;margin:5px 1px 0px;cursor:default;">[<font onclick="showAdd()" style="color:#0000A0">增加</font>]
[<font onclick="hideMenu()" style="color:#0000A0">关闭</font>]</div>
<div style="width:295px;height:180px;overflow:auto;cursor:default">
<table width="100%" cellpadding="1" cellspacing="1" border="0" bgcolor="#E0E0E0">
<%if(list.size() > 0){for(int i=0; i<list.size(); i++){String[] temp = (String[])list.get(i);%> <tr>
    <td<%=clickEvent%>="setSignValue(this)" bgcolor="#FFFFFF"><%=temp[1]%></td>
    <td width="30" style="color:#0000A0" bgcolor="#FFFFFF"<%if(temp[0] != null){%>onclick="deleteLine('<%=temp[0]%>')">删除<%}else{%>>&nbsp;<%}%></td>
  </tr>
  <%}}else{%>
  <tr>
    <td bgcolor="#FFFFFF" colspan="2">&nbsp;</td>
  </tr>
  <%}%>
</table>
</div>
</body>
</html>