<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link href="/css/login-box.css" rel="stylesheet" type="text/css" />
<link href="/css/main.css" type="text/css" rel="stylesheet" />
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
</style>
<script type="text/javascript">

this.tipTitle = function(){
		xOffset = 10;
		yOffset = 20;	
	$(".tipTitle").hover(function(e){
		if(this.title!="0"){
			this.t = this.title;
			this.title = "";									  
			$("body").append("<p id='tipTitle'>材料说明："+ this.t +"</p>");
			$("#tipTitle")
				.css("top",(e.pageY - xOffset) + "px")
				.css("left",(e.pageX + yOffset) + "px")
				.fadeIn("fast");
		}else{
			this.title = "";
			this.t="0"
		}										  
    },
	function(){
		this.title = this.t;		
		$("#tipTitle").remove();
    });	
	$(".tipTitle").mousemove(function(e){
		$("#tipTitle")
			.css("top",(e.pageY - xOffset) + "px")
			.css("left",(e.pageX + yOffset) + "px");
	});			
};
$(document).ready(function(){
	tipTitle();
});
</script>
</head>
<body>
	<table cellpadding="0" cellspacing="0" border="0" width="100%" id="cardTab1"
			class="BS_list" style="font-family:微软雅黑;display:">
							<th style="text-align:left">
								需递交材料清单:
							</th>
						</tr>	
					<s:iterator value="approveinstance.fileList" status="typeStat">
						<tr><td>
						<span class="tipTitle" title="<s:property value="describe"/>">(<s:property value="#typeStat.index+1" />)<s:property value="materialname" /><font style="color:#C2C2C2;">（说明：<s:property value="describe"/>） </font></span>
							</td>
						</tr>
					</s:iterator>
				</table>
				
</body>
</html>
