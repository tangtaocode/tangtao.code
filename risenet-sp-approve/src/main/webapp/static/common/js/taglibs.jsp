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
</script>

<link href="${context}/static/common/js/plugins/dwz/themes/default/style.css" rel="stylesheet" type="text/css" />
<link href="${context}/static/common/js/plugins/dwz/themes/css/core.css" rel="stylesheet" type="text/css" />
<link href="${context}/static/common/js/plugins/jquery-easyui-1.4.3/themes/extcloud/easyui.css" rel="stylesheet" type="text/css" />
<!--[if IE]>
<link href="${context}/static/common/js/plugins/dwz/themes/css/ieHack.css" rel="stylesheet" type="text/css" />
<![endif]-->
<link rel="stylesheet" type="text/css" href="${context}/static/common/js/plugins/zTree-3.5.18/css/zTreeStyle/zTreeStyle.css" >

<script type="text/javascript" src="${context}/static/common/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${context}/static/common/js/plugins/jquery-easyui-1.4.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${context}/static/common/js/plugins/zTree-3.5.18/js/jquery.ztree.all-3.5.min.js" ></script>
<script type="text/javascript" src="${context}/static/common/js/jquery.form.js"></script>
<script src="${context}/static/common/js/plugins/dwz/js/speedup.js" type="text/javascript"></script>

<script src="${context}/static/common/js/plugins/dwz/js/jquery.cookie.js" type="text/javascript"></script>
<script src="${context}/static/common/js/plugins/dwz/js/jquery.validate.js" type="text/javascript"></script>
<script src="${context}/static/common/js/plugins/dwz/js/jquery.bgiframe.js" type="text/javascript"></script>
<script src="${context}/static/common/js/plugins/dwz/js/dwz.min.js" type="text/javascript"></script>
<script src="${context}/static/common/js/plugins/dwz/js/dwz.regional.zh.js" type="text/javascript"></script>

<script type="text/javascript">
$(function(){	
	DWZ.init(context + "/static/common/js/plugins/dwz/dwz.frag.xml", {
		//loginUrl:"/management/index!login.do", loginTitle:"Login",	// 弹出登录对话框
		loginUrl:context + "/index!login.do",	// 跳到登录页面
		debug:false,	// 调试模式 【true|false】
		callback:function(){
			initEnv();
			$("#themeList").theme({themeBase:context + "/static/common/js/plugins/dwz/themes"});
		}
	});
	
});
//清理浏览器内存,只对IE起效,FF不需要
if ($.browser.msie) {
	window.setInterval("CollectGarbage();", 10000);
}
</script>