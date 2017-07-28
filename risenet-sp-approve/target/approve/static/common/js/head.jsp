<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!Doctype html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<% 
response.setHeader("Pragma","No-Cache"); 
response.setHeader("Cache-Control","No-Cache"); 
response.setDateHeader("Expires", 0); 
%>
<c:set var="context" value="${pageContext.request.contextPath}"></c:set>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Content-Language" content="utf-8"/>
<meta http-equiv="pragma" content="no-cache">   
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script type="text/javascript">
	var context = "${pageContext.request.contextPath}";
	var userName = "${_const_cas_assertion_.principal.attributes.name}";
	var userGUID = "${_const_cas_assertion_.principal.attributes.ID}" 
</script>
<link rel="stylesheet" type="text/css" href="${context}/static/common/js/plugins/jquery-easyui-1.4.3/themes/extcloud/easyui.css" />
<link rel="stylesheet" type="text/css" href="${context}/static/common/js/plugins/jquery-easyui-1.4.3/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="${context}/static/common/js/plugins/jquery-easyui-1.4.3/themes/color.css" >
<script type="text/javascript" src="${context}/static/common/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${context}/static/common/js/plugins/jquery-easyui-1.4.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${context}/static/common/js/plugins/jquery-easyui-1.4.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${context}/static/common/js/jquery.easyui.extend.js"></script>
<script type="text/javascript" src="${context}/static/common/js/frameUtil.js"></script>
