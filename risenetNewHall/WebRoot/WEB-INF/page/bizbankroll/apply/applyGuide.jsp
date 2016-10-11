<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<title>深圳市龙岗区住房与建设局产业转型升级专项资金申报系统</title>
	<meta name="keywords"
			content="深圳市龙岗区住房与建设局产业转型升级专项资金申报系统,产业转型,专项资金,产业服务,深圳市龙岗区住房与建设局产业服务,深圳市龙岗区住房与建设局" />
		<meta name="description" content="产业转型首页" />
<meta name="robots" content="all" />
<head>
<link href="/css/main.css" type="text/css" rel="stylesheet" />
<link href="/css/bizbankroll.css" type="text/css" rel="stylesheet" />
<link href="/css/appGuid.css" type="text/css" rel="stylesheet" />

<script type="text/javascript">
var HKEY_Root,HKEY_Path,HKEY_Key;
 HKEY_Root="HKEY_CURRENT_USER";
 HKEY_Path="\\Software\\Microsoft\\Internet Explorer\\PageSetup\\";
 //设置网页打印的页眉页脚为空
 function PageSetup_Null(){
	 try{
		 var Wsh=new ActiveXObject("WScript.Shell");
		 HKEY_Key="header";
		 Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"");
		 HKEY_Key="footer";
		 Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"");
	 }catch(e){
	 }
 }
	function preview(oper) {
		PageSetup_Null();
		 if (oper < 10){ 
			 bdhtml=window.document.body.innerHTML;//获取当前页的html代码 
			 sprnstr="<!--startprint"+oper+"-->";//设置打印开始区域 
			 eprnstr="<!--endprint"+oper+"-->";//设置打印结束区域 
			 prnhtml=bdhtml.substring(bdhtml.indexOf(sprnstr)+18); //从开始代码向后取html 
			 prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));//从结束代码向前取html 
			 window.document.body.innerHTML=prnhtml; 
			 window.print(); 
			 window.document.body.innerHTML=bdhtml; 
		 }else{ 
		 	window.print(); 
		 } 
 	}
 
 
</script>

</head>
<body>
<jsp:include page="/WEB-INF/page/public/topMenu.jsp">
<jsp:param name="menu" value="3"/>
</jsp:include>
<div class="BS_wrap">
<div class="BS_content" id="BS_content_div">
<div class="clean" style="height:10px;"></div>
<jsp:include page="/WEB-INF/page/public/menu.jsp">
<jsp:param name="menu" value="4"/>
</jsp:include>
   </div>
   <!-- 查询 -->
    <table border="0" cellpadding="0" cellspacing="15px" style="width: 98%;">
		<tr>
			<td align="left">
				<span style="font-size: 18pt;">在线申报</span>
			</td>
			<td align="right">
				&nbsp;
			</td>
		</tr>
	</table>
  	<div class="busi_dotop">&nbsp;</div>
      <div class="busi_domiddle">
       <div class="more_domiddlein">
		<div id="resultDiv" >
			<table cellspacing="8" cellpadding="2" border="0" width="99%">
								<tr>
									<td align="right" nowrap="nowrap" colspan="2"
										style="padding-right: 10px">
										<a href="javascript:void(0)"><img
												src="/images/lineservice/print.png" onclick="preview(1)"
												border="0" />
										</a>
									</td>
								</tr>
							</table>
							<!--startprint1-->
							<jsp:include page="/WEB-INF/page/bizbankroll/apply/guideInfo.jsp"></jsp:include>	
							<!--endprint1-->
		</div>
       	</div>
      </div>
	<div class="busi_dobottom">&nbsp;</div>
  	</div>
   <div class="clean" style="height:10px;"></div>
   <jsp:include page="/WEB-INF/page/public/bottom.jsp"></jsp:include>    
</body>
</html>

  

