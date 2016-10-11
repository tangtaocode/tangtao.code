<%/*
  显示表单指定的数据字典作为为辅助输入
  参数：示例，a.xb
*/%>
<%@ page language="java" %><%@ page contentType="text/html; charset=GBK"%><%@ page 
import="net.business.db.CodeFactory"%><%@ page import="net.business.engine.control.unit.CodeEditListUnit"%><%
response.setHeader("Pragma","no-cache");
response.setHeader("Expires","0");
response.setHeader("CacheControl","max-age=0");
response.setHeader("Cache-Control","no-store");

request.setCharacterEncoding("GBK");
CodeFactory code = new CodeFactory();
int pos = -1;
String alias = request.getParameter("alias");
String obj = request.getParameter("obj");

java.util.ArrayList list = null;
String[][] arrValues = null;
if(alias != null && !alias.equals(""))
{
    pos = alias.indexOf(".");
    if(pos != -1) list = code.getCodeByAlias(alias.substring(0, pos), alias.substring(pos+1));
}
else
{
    String tid = request.getParameter("tid");
    String cid = request.getParameter("cid");
    arrValues = CodeEditListUnit.getListItems(Integer.parseInt(tid), cid, request);
}

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
    try{parent.document.getElementById(parent.<%=obj%>.getSignMenu()).style.display = "none"}catch(e){}
}

function setSignValue(obj)
{
    if(selectedLine != null)
    {
        selectedLine.style.backgroundColor = "";
        selectedLine.style.color = "";
        selectedLine = null;
    }
    try
    {
        eval("parent.<%=obj%>.setAssign(obj)");
    }
    catch(e){}
}

function clearValue()
{
    if(selectedLine != null)
    {
        selectedLine.style.backgroundColor = "";
        selectedLine.style.color = "";
        selectedLine = null;
    }
    try
    {
        eval("parent.<%=obj%>.clearValue()");
    }
    catch(e){}
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
//-->
</script>
</head>
<body  bgcolor="#FFFFFF" leftmargin=0 topmargin=0  doncontextmenu="return false" ondragstart="return false" onselectstart ="return false" onselect="document.selection.empty()"
oncopy="document.selection.empty()" onbeforecopy="return false" onmouseup="document.selection.empty()" scroll="no">
<div align="right" style="width:100%;font-size:12px;margin:5px 1px 0px;cursor:default;">[<font onclick="clearValue()" style="color:#0000A0">清空</font>][<font onclick="hideMenu()" style="color:#0000A0">关闭</font>]</div>
<div id="_div" style="overflow:auto;cursor:default">
<table width="100%" cellpadding="1" cellspacing="1" border="0" bgcolor="#E0E0E0">
<%if(list != null){for(int i=0; i<list.size(); i++){String[] temp = (String[])list.get(i);%> <tr>
    <td<%=clickEvent%>="setSignValue(this)" bgcolor="#FFFFFF" code="<%=temp[0]%>"><%=temp[1]%></td>
  </tr>
<%}}else if(arrValues != null){for(int i=0; i<arrValues.length; i++){%> <tr>
    <td<%=clickEvent%>="setSignValue(this)" bgcolor="#FFFFFF" code="<%=arrValues[i][0]%>"><%=arrValues[i][1]%></td>
  </tr>
  <%}}else{%>
  <tr>
    <td bgcolor="#FFFFFF">&nbsp;</td>
  </tr>
  <%}%>
</table>
</div>
</body>
<script language="JavaScript">
try
{
   eval("document.getElementById('_div').style.width = parent.<%=obj%>.width-5");
   eval("document.getElementById('_div').style.height = parent.<%=obj%>.height-20");
}
catch(e){}
</script>
</html>