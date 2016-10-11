<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link rel="stylesheet" href="/css/jquery-ui.css" />  
<script src="/js/Scripts/jquery-1.7.1.min.js"></script>  
<script src="/js/Scripts/jquery-ui.min.js"></script>  
<script src="/js/businessJS/shareWebService.js"></script>

<style type="text/css">
a:hover {color: red; text-decoration:none;}
#tipTitle{
	font-size:15px;
	text-align:left;
	width:400px;
	position:absolute;
	border:1px solid #333;
	background:#f7f5d1;
	padding:2px 5px;
	color:#333;
	display:none;
	padding:5px;
    border-radius:5px;
    -webkit-border-radius:5px;
    -moz-border-radius:5px;
}	
a{
	cursor:pointer;
}
</style>
<script type="text/javascript">

$(function() {
	//$( "#tabs" ).tabs();
	
	$(".material_del").click(function(){
		var delGuid = $(this).attr("id");
		deleteFile(delGuid,$(this));
	});
});

function deleteFile(fileGuid,needDel){
	$.post("/riseFile/deleteFile.html",
		{'fileGUID':fileGuid,'modeType':'approveItem'},
		function(data){
			if(data.message=="1"){
				//showInfo("É¾³ý²ÄÁÏ³É¹¦£¡");
				needDel.parent().parent().remove();
			}else{
				showInfo("É¾³ý²ÄÁÏÊ§°Ü£¡");
			}
		});
}

</script>
</head>
<body>
<table style="font-size: 12px;">
									<s:iterator value="webUpFileList" status="docT">
									
										<tr>
											<td width="90%" style="border:0px;">
											<a href="/servlet/DownFileServlet/downType/approveItemWeb/fileId/<s:property value="guid"/>.html"><s:property value="filename" /></a></td>
											<td style="border:0px;"><a class="material_del" id='<s:property value="guid"/>'><span  title="É¾³ý²ÄÁÏ" style="color:red;">É¾³ý</span> </a></td>
										</tr>
										
									</s:iterator>
</table>
</body>
</html>
