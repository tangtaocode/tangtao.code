<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />

<link rel="stylesheet" type="text/css" href="${ctx}/static/risesoft/css/button.css" />
<script src="${ctx}/static/jquery/jquery-1.10.2.min.js" type="text/javascript"></script>
<script type="text/javascript">
    var ctx = "${ctx}";
    $.ajaxSetup({
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
    }
</script>