<%@ page contentType="text/xml; charset=gb2312"%><%
response.setHeader("Pragma","no-cache");
response.setHeader("Expires","0");
response.setHeader("CacheControl","max-age=0");
response.setHeader("Cache-Control","no-store");
StringBuffer resultXML = new StringBuffer();

resultXML.append("<?xml version=\"1.0\" encoding=\"gb2312\" ?><root>");
resultXML.append("<value><![CDATA[").append("dddddddddd").append("]]></value>");
resultXML.append("<value><![CDATA[").append("ÀÏÆÅ»µ").append("]]></value>");
resultXML.append("<value><![CDATA[").append("fd").append("]]></value>");
resultXML.append("</root>");

out.print(resultXML.toString());
%>