<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/static/common/head.jsp"%>
<link type="text/css" rel="stylesheet" href="${ctx}/static/jquery/menu/css/menu-css.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/static/jquery/menu/css/style.css" />

<script type="text/javascript" src="${ctx}/static/jquery/menu/menu_min.js"></script>

<style type="text/css">
<!--
a:link {
 text-decoration: none;
}
a:visited {
 text-decoration: none;
}
a:hover {
 text-decoration: none;
}
a:active {
 text-decoration: none;
}
-->
</style>
<script type="text/javascript">
$(document).ready(function (){
	$.get(ctx+'/menu/getPermMenus?',function(data){
		$.each(data,function(i,item){
			var pId=item.pId;
			var id=item.id;
			var name=item.name;
			var href=item.href;
			//alert(href);
			if(href==null || href=="")
			{
				href="";
			}
			if(pId=="rootGuid")
			{
				$("#top").append("<li id=\""+id+"\"><a href=\"${ctx}/"+href+"\">"+name+"</a></li>");
			}else
			{//判断下面是否已经存在ul标签，如果存在则只添加元素，都则整体添加整个ul
				if($('#'+pId+' ul').length>0)
				{
					$("#"+pId+' ul').append("<li id=\""+id+"\"><a href=\"${ctx}/"+href+"\">"+name+"</a></li>");
				}else
				{
					//alert(name+" no exist");
					$("#"+pId).append("<ul style=\"display: block;\"></ul>");
					$("#"+pId+' ul').append("<li id=\""+id+"\"><a href=\"${ctx}/"+href+"\">"+name+"</a></li>");
				}
			}
		});
		/* 这里要把$(".menu ul li").menu();写在$.get里面，因为写在外面不起作用，通过alert($("#top").html());找不到前面添加的语句 */
		$(".menu ul li").menu();
	});
});
</script>
</head>
<body>
<div id="content">
	<div class="menu">
		<ul id="top">
		</ul>
	</div>
</div>
<a href="${ctx }/document/testt">asdfas</a>
</body>
</html>