<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <body>
   <ul class="item admin">
        <li onclick="closeData();"><s:property value="bureau.bureauname"/> </li>
   </ul>
   <ul class="item">
  <s:iterator value="menuList" status="menu">
  	  <li onclick="closeData();"><s:property value="value"/> </li>
  </s:iterator>
      </ul>
  </body>
</html>
