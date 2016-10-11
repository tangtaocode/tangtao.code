<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/css/index_style.css" rel="stylesheet" type="text/css" />
<title>日程安排</title>
<style>
h1 {
	font-size: 24px;
	fort-family: "宋体";
}

p {
	font-size: 14px;
	fort-family: "宋体";
}

.hr {
	font-size: 14px;
	fort-family: "宋体";
}
</style>

</head>
<body>
  <table border="0" cellpadding="0" cellspacing="0" class="td_bottom" width="580" align="center">
    <tr>
      <td colspan="2" align="center"><h1>日程安排</h1></td>
    </tr>
    <tr>
      <td class="td_lt text_center" width="100">安排事件</td>
      <td class="td_lt td_right">${schedule.TITLE}</td>
    </tr>
    <tr>
      <td class="td_lt text_center">具体内容</td>
      <td class="td_lt td_right">
      <div style="width:470px;height:220px;overflow: auto;">${schedule.CONTENT}</div>
      </td>
    </tr>
    <tr>
      <td class="td_lt text_center">安排人</td>
      <td class="td_lt td_right">${schedule.CREATOR}</td>
    </tr>
    <tr>
      <td class="td_lt text_center">安排时间</td>
      <td class="td_lt td_right">
      ${schedule.STARTTIME}&nbsp;至&nbsp;${schedule.FINISHTIME}
      </td>
    </tr>
    <tr>
      <td class="td_lt text_center">是否提醒</td>
      <td class="td_lt td_right">
      <c:if test="${schedule.ISREMIND==0}">否</c:if>
      <c:if test="${schedule.ISREMIND==1}">是</c:if>
      </td>
    </tr>
    <tr>
      <td class="td_lt text_center">提醒时间</td>
      <td class="td_lt td_right">
      <c:if test="${schedule.ISREMIND==0}">没有提醒</c:if>
      <c:if test="${schedule.ISREMIND==1}">${schedule.REMINDTIME}</c:if>
      </td>
    </tr>
    <tr>
      <td colspan="2" class="td_lt td_right text_center"><input name="button_search2" value="关闭" type="button" class="searchBtn"
        onclick="parent.closeLayer()" /></td>
    </tr>
  </table>
</body>
</html>
