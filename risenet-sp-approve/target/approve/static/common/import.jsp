<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!--框架必备-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/language/cn.js"></script>
<link href="${ctx}/static/QUI/libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="${ctx}/static/QUI/" scrollerY="false"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必备结束-->
<link href="${ctx}/static/QUI/libs/portal/skins/lightblue/style.css" rel="stylesheet" type="text/css"/>
<!--弹窗组件-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/portal/js/jquery-1.4.js"></script>
<script type="text/javascript" src="${ctx}/static/QUI/libs/portal/js/jquery-ui-1.8.24.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/static/QUI/libs/portal/js/drag.js"></script>
<script type="text/javascript" src="${ctx}/static/QUI/libs/portal/js/dialog.js"></script>
<script type="text/javascript" src="${ctx}/static/QUI/libs/portal/js/qWidget.js"></script>
<!--弹窗组件end-->
