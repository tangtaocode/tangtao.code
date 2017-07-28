<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<% 
response.setHeader("Pragma","No-Cache"); 
response.setHeader("Cache-Control","No-Cache"); 
response.setDateHeader("Expires", 0); 
%> 

<c:set var="ctx" value="${pageContext.request.contextPath}" />