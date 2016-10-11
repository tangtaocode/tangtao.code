<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link href="/css/login-box.css" rel="stylesheet" type="text/css" />
<link href="/css/main.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="/js/Scripts/jquery-1.7.1.min.js"></script>
<script language="javascript" src="/commons/LodopFuncs.js" charset="GBK"></script>
<object id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0> 
	<embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0 pluginspage="/commons/install_lodop.exe"></embed>
</object>
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
	
	var xbq_tr = $(".xbq_tr");
	var xbz_tr = $(".xbz_tr");
	if(xbq_tr==undefined||xbq_tr.length==0){
		$("#xbq_text").remove();
	}
	if(xbz_tr==undefined||xbz_tr.length==0){
		$("#xbz_text").remove();
	}
});
</script>
</head>
<body>
	<table cellpadding="0" cellspacing="0" border="0" width="100%" id="cardTab1"
			class="BS_list" style="font-family:微软雅黑;display:">
			<p><a style="float: right;" href="javascript:void(0)"><img src="/images/lineservice/print.png" onclick="PreviewMytable()" border="0"/></a></p>
						<tr>
							<th style="text-align:left">
								回复内容:
							</th>
						</tr>
						<s:iterator value="feedBackLists" id="feed">
							<tr>
								<td> 
									<s:property value="content" escape="false"/>&nbsp;
								</td>
							</tr>
						</s:iterator>
						
						
						<tr id="xbq_text">
							<th style="text-align:left">
								需补齐材料:
							</th>
						</tr>
						
							<s:iterator value="approveinstance.fileList" status="fileStat">
							<s:if test="type==1">
							<tr class="xbq_tr">
								<td>
									<span class="tipTitle" title="<s:property value="describe"/>">(<s:property value="#fileStat.index+1" />)、<s:property value="materialname" /></span>
								</td>
							</tr>
							</s:if>
								
							</s:iterator>
						<tr id="xbz_text">
							<th style="text-align:left">
								需补正材料:
							</th>
						</tr>	
					<s:iterator value="approveinstance.fileList" status="typeStat">
						<s:if test="type==0">
						<tr class="xbz_tr">
							<td>
								<span class="tipTitle" title="<s:property value="describe"/>">
									(<s:property value="#typeStat.index+1" />)、<s:property value="materialname" />
									<s:if test="bzyj!=null&&bzyj!=''">
										<span style="color:red;">（补正意见：<s:property value="bzyj" />）</span>
									</s:if>
								</span>
							</td>
						</tr>
						</s:if>
						
					</s:iterator>
				</table>
				
</body>

<script language="javascript" type="text/javascript">
//document.get
var LODOP; //声明为全局变量
	function PreviewMytable(){//alert(document.frames["showform"].document.body.table.innerHTML);
	    var styles='<LINK href="/css/main.css" type=text/css rel=stylesheet><style type="text/css"> .box_table{display:none;} </style>';
		LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));  		
		LODOP.PRINT_INIT("网上预受理回执单");
		LODOP.ADD_PRINT_TABLE(80,"6%","90%",450,styles+"<body>"+document.body.innerHTML+"</body>");
		LODOP.SET_PRINT_STYLEA(0,"Vorient",3);
		LODOP.ADD_PRINT_TEXT(1060,660,80,30,"第#页/共&页");	
		LODOP.SET_PRINT_STYLEA(0,"ItemType",2);
		LODOP.SET_PRINT_STYLEA(0,"Horient",1);
		LODOP.NewPageA();		
		LODOP.PREVIEW();
		
	}
</script>
</html>
