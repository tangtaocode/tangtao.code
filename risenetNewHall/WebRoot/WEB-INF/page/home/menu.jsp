<%@ page contentType="text/html; charset=UTF-8" %>
<link href="/css/menu.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
<!--
	$(document).ready(function(){
		$(".nav-item").removeClass("active");
		$("#nav-item_<%=request.getParameter("menu")%>").addClass("active");
	});
//-->
</script>
 <div class="banner">
	<div id="dd-navigation">
    	<div id="nav-strip">
            <ul>
                <li class="nav-item active" id="nav-item_1"><a href="/bizHomeInfo/homePage.html" ><span class="primary-link">首&nbsp;&nbsp;页</span></a></li>
                <li class="nav-item" id="nav-item_2" ><a href="/bizNotice/queryPage/ntype/1.html" ><span class="primary-link">扶持政策</span></a> </li>
                <li class="nav-item" id="nav-item_3" ><a href="/bizNotice/queryPage/ntype/0.html" ><span class="primary-link">通知公告</span></a></li>
                <li class="nav-item" id="nav-item_4" ><a href="/bizApply/projectType.html" ><span class="primary-link">网上申报</span></a></li>
                <li class="nav-item" id="nav-item_5" ><a href="/bizDynamic/queryPage.html" ><span class="primary-link">办理查询</span></a></li>
                <li class="nav-item" id="nav-item_6" ><a href="/zxts.jsp" ><span class="primary-link">公示异议</span></a></li>
                <li class="nav-item" id="nav-item_7" ><a href="#" onclick="islogin()" ><span class="primary-link">办事跟踪</span></a></li>
                <li class="nav-item" id="nav-item_8" ><a href="/bizHomeInfo/connectPhone.html"><span class="primary-link" >咨询电话</span></a></li>
			</ul>
       	</div>
    </div>
</div>

  