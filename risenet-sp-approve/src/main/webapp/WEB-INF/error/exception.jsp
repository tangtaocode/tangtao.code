<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/include/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/static/include/head.jsp"%>
<title>错误页面</title>
</head>
<body>
<table width="100%" border="1">
  <tr>
    <td>类名&nbsp;</td>
    <td>${error.exception}</td>
  </tr>
  <tr>
    <td>错误信息&nbsp;</td>
    <td>${error.message}</td>
  </tr>
  <tr>
    <td>详细信息&nbsp;</td>
    <td>${error.stackTrace}</td>
  </tr>
</table>
</body>
</html>
