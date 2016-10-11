<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>退出登录</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<script type="text/javascript">
window.location.href="http://10.0.0.98:8000/sso/logout?service=http://10.0.0.98:8088";
</script>
</head>
<body>
<%
Cookie[] cookies = request.getCookies() == null ? new Cookie[0] : request.getCookies();
for (Cookie cookie : cookies)
{
    cookie.setMaxAge(0);
    response.addCookie(cookie);
}
if (!session.isNew())
	session.invalidate();
	
%>
</body>
</html>
