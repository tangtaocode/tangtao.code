<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String themes="default";
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path+"/";
	String contextPath = request.getContextPath();
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/risesoft/css/button.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/jquery/easyui/themes/<%=themes%>/easyui.css" id="themes">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/jquery/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/risesoft/css/all.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/risesoft/css/icon-all.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/risesoft/css/easyui-ext.css">

<script type="text/javascript" src="<%=contextPath%>/static/jquery/jquery-1.10.2.min.js" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/risesoft/js/myCore.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/jquery/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/jquery/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/jquery/easyui/datagrid-detailview.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/jquery/easyui-ext/jquery.easyui.extend.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/jquery/easyui-ext/jquery.euploadify.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/jquery/easyui/plugins/jquery.fullcalendar.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/easyXDM/easyXDM.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/risesoft/js/myMenu.js"></script>
<script type="text/javascript">
	var ctx = "<%=contextPath%>";
	(function($){
		$.getRootPath=function(){
			return "<%=basePath%>";
		}
		$.getContextPath=function(){
			return "<%=contextPath%>";
		}
		$.getThemes=function(){
			return "<%=themes%>";
		}
	})(jQuery);
    /* $.ajaxSetup({
        //contentType: "application/x-www-form-urlencoded;charset=utf-8",
        type : "POST",
        complete : function(XMLHttpRequest, textStatus) {
            //通过XMLHttpRequest取得响应头，sessiontimeout，
            var sessiontimeout = XMLHttpRequest.getResponseHeader("sessiontimeout");
            if (sessiontimeout && sessiontimeout == "true") {
                //如果超时就处理 ，指定要跳转的页面
                top.window.location.replace("${ctx}/login");
            }
        }
    });

    function getDom(myid) {
        return document.getElementById(myid);
    }

    function getCmp(myid) {
        return $(document.getElementById(myid));
    } */
</script>