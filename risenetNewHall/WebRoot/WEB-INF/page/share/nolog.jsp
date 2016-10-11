<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<html>
	<head>
<script type="text/javascript" src="/js/zDialog/risenetDialog.js"></script> 
	</head>
	<body>
<script type="text/javascript">
	Dialog.alert('您尚未登录系统不能进行此操作，请进行登录!',function(){
		window.location.href='${skipToPageUrl}';
	});
</script>
	</body>
</html>
