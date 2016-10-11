<%@ page contentType="text/html; charset=gbk" %> 
<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<style type="text/css">
.selectMenu{
	font-size: 14px;
    font-family: "黑体";
    background: url(/images/investment/btn_stepe.gif) no-repeat;
    border: 0px;
    width: 125px;
    height: 27px;
    cursor: hand;
    color: #FFF;
    text-align: center;
   
}
.selectMenud{
	font-size: 14px;
    font-family: "黑体";
    background: url(/images/investment/btn_stepd.gif) no-repeat;
    border: 0px;
    width: 125px;
    height: 27px;
    cursor: hand;
    color: #FFF;
    text-align: center;
   
}
</style>
<meta http-equiv="content-type" content="text/html; charset=GBK" />
<script language="javascript" src="/commons/LodopFuncs.js" ></script>
<script src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script src='<%=request.getContextPath()%>/dwr/interface/SearchSharedData.js'></script>

<script type="text/javascript">
var zurl=$("#showform").attr("src");
var state=$("#state").val();

function changeUrl(obj,aurl,approveItemGUID,insGuid,userId){
	zurl=aurl+"&approveItemGUID="+approveItemGUID+"&insGuid="+insGuid+"&userId="+userId;
	if(state==1){
		$("#showform").attr("src",aurl+"&approveItemGUID="+approveItemGUID+"&insGuid="+insGuid+"&userId="+userId+"&edittype=1");
	}else{
		$("#showform").attr("src",aurl+"&approveItemGUID="+approveItemGUID+"&insGuid="+insGuid+"&userId="+userId);
	}
	$("#formStepButton > input").removeClass();
	$("#formStepButton > input").addClass("selectMenud");
	$(obj).removeClass();
	$(obj).addClass("selectMenu");
	
}
function myload(){
	state=1;
	$("#state").val(state);
	$("#showform").attr("src",zurl+"&edittype=1");
}

$(document).ready(function(){
	if(state==1){
		$("#showform").attr("src",zurl+"&edittype=1");
		$(".selectMenu")
	}
});
</script>
</head>
<body>
	<table cellpadding="0" cellspacing="0" border="0" width="100%" id="cardTab1"
			class="BS_list" style="font-family:微软雅黑;display:">
						<tr>
							<th id="formStepButton">
								<s:iterator value="formList" status="form">
									<s:if test="#form.index+1==1">
									<input type="button" class="selectMenu" onclick="changeUrl(this,'<s:property value='url'/>','<s:property value='approveItemGUID'/>','<s:property value='appInstanceGuid'/>','<s:property value="#session.loginUser.guid"/>')" value="<s:property value="formname"/>">&nbsp;&nbsp;
									</s:if>
									<s:else>
									<input type="button" class="selectMenud" onclick="changeUrl(this,'<s:property value='url'/>','<s:property value='approveItemGUID'/>','<s:property value='appInstanceGuid'/>','<s:property value="#session.loginUser.guid"/>')" value="<s:property value="formname"/>">&nbsp;&nbsp;
									</s:else>
								</s:iterator>
								<div style="float: right; margin-top:-25px; font-size:12px;cursor:pointer;" onclick="PreviewMytable()" >打印预览</div>
								<input id="guids" type="hidden" value="<s:property value="guids"/>">
							</th>
						</tr>
						<tr>
							<s:iterator value="formList" status="form">
								<s:if test="#form.getCount()==1">
									<td><iframe id="showform" name="showform" style="height: 700px;width: 100%; border-top: 1px solid #333;" src="<s:property value='url'/>&approveItemGUID=<s:property value='approveItemGUID'/>&insGuid=<s:property value='appInstanceGuid'/>&userId=<s:property value="#session.loginUser.guid"/>"></iframe></td>
								</s:if>
							</s:iterator>
						</tr>
				</table>	
</body>
<script language="javascript" type="text/javascript">
//document.get
var LODOP; //声明为全局变量
	function PreviewMytable(){//alert(document.frames["showform"].document.body.table.innerHTML);
	    var styles='<LINK href="/css/main.css" type=text/css rel=stylesheet><LINK href="/css/printstyles.css" type=text/css rel=stylesheet><LINK href="/engine/system.css" type=text/css rel=stylesheet><LINK href="/engine/style.css" type=text/css rel=stylesheet><style type="text/css"> .box_table{display:none;} </style>';
		LODOP=getLodop(); 
		LODOP.PRINT_INIT("申报表单打印");
		var surl = $("#showform").attr("src")
		var s = "<%=request.getScheme() %>";
		var sname = "<%=request.getServerName() %>";
		var port = "<%=request.getServerPort() %>";
		var purl = s+"://"+sname+":"+port+surl;
	// LODOP.ADD_PRINT_HTML(25,-10,500,1550,styles+"<body><table width='700'> <tr><td>"+document.frames["showform"].document.body.innerHTML+"</td></tr></table></body>");
		LODOP.SET_PRINT_STYLEA(0,"DataCharset","UTF-8");
		LODOP.ADD_PRINT_URL(2,0,500,1550,purl);
		
	//	LODOP.SET_PRINT_STYLEA(23,"Vorient",0);
	//	LODOP.ADD_PRINT_TEXT(1060,660,20,30,"第#页/共&页");	
	//	LODOP.SET_PRINT_STYLEA(0,"ItemType",2);
	//	LODOP.SET_PRINT_STYLEA(0,"Horient",1);
	//	LODOP.NewPageA();
		LODOP.PREVIEW();	
		
	}
</script>
</html>
