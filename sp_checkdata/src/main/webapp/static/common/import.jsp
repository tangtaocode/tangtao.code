<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!--框架必需start-->
<script type="text/javascript" src="${ctx }/static/QUI/libs/js/jquery.js"></script>
<script type="text/javascript" src="${ctx }/static/QUI/libs/js/language/cn.js"></script>
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/framework.js"></script>
<%-- <script type="text/javascript" src="${ctx }/static/QUI/libs/js/main.js"></script> --%>
<link href="${ctx }/static/QUI/libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link href="${ctx }/static/QUI/libs/skins/lightBlue/style.css" rel="stylesheet" type="text/css" id="theme" themeColor="lightBlue" positionTarget="positionContent" selInputHeight="28" selButtonWidth="29" defaultSelWidth="160" fileBtnWidth="60" defaultFileInputWidth="222" defaultGridHeaderHeight="32" defaultGridRowHeight="32" defaultFontSize="14" defaultPageSelWidth="55" defaultFilterItemHeight="28" defaultFontFamily="微软雅黑"/>
<link  rel="stylesheet" type="text/css" id="skin"  skinPath="${ctx }/static/QUI/system/layout_html/skin/"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<script type="text/javascript" src="${ctx}/static/jquery/jquery.json.js"></script>
<!--框架必需end-->
<!--数据表格start-->
<link href="${ctx}/static/risesoft/css/style.css" rel="stylesheet" type="text/css"/>
<script src="${ctx}/static/QUI/libs/js/table/quiGrid.js" type="text/javascript"></script>
<!--数据表格end-->
<!--表单异步提交start-->
<script src="${ctx}/static/QUI/libs/js/form/form.js" type="text/javascript"></script>
<!--表单异步提交end-->
<!-- 表单验证start -->
<script src="${ctx}/static/QUI/libs/js/form/validationRule.js" type="text/javascript"></script>
<script src="${ctx}/static/QUI/libs/js/form/validation.js" type="text/javascript"></script>
<!-- 表单验证end -->
<!-- 布局 -->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/nav/layout.js"></script>
<!--树组件start -->
<!-- 树形下拉框start -->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/form/selectTree.js"></script>
<!-- 树形下拉框end -->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/tree/ztree/ztree.js"></script>
<link href="${ctx}/static/QUI/libs/js/tree/ztree/ztree.css" rel="stylesheet" type="text/css" />

<!--树组件end -->
<!--父子表start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/table/detailTable.js"></script>
<!--父子表end-->
<!--弹窗组件start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/popup/drag.js"></script>
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/popup/dialog.js"></script>
<!--弹窗组件end-->
<!--弹出式提示框start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/popup/messager.js"></script>
<!--弹出式提示框end-->
<!-- 时间轴组件 -->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/nav/basicTabProcess.js"></script>
<!-- 时间轴组件end -->
<!-- 日期选择框start -->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/form/datePicker/WdatePicker.js"></script>
<!-- 日期选择框end -->
<!-- 选项卡-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/nav/basicTab.js"></script>
<!-- 双向选择器start -->
<script type="text/javascript" src="${ctx }/static/QUI/libs/js/form/lister.js"></script>
<!-- 双向选择器end -->


