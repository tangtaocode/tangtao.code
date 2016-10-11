<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<html>
	<head>
<script type="text/javascript" src="/js/zDialog/risenetDialog.js"></script> 
	</head>
	<body>
<s:if test="url==null">
<script type="text/javascript">
	Dialog.alert('<s:property value="message"/>',function(){
		if(ownerDialog){
			ownerDialog.close();
		}
	});
</script>
</s:if>
<s:else>
<script type="text/javascript">
	Dialog.alert('<s:property value="message"/>',function(){
		window.location.href='<s:property value="url"/>';
	});
</script>
</s:else>
	</body>
</html>
