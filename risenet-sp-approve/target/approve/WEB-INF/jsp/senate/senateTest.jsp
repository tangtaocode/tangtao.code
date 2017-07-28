<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="GENERATOR" content="Microsoft FrontPage 4.0">
<meta name="ProgId" content="FrontPage.Editor.Document">
<title>评价器测试</title>

<script type="text/javascript">

</script>
</head>
<body>
<div class="" panelWidth="800" panelHeight="1000">
<OBJECT id=pjq style="LEFT: 0px; WIDTH: 35px; TOP: 0px; HEIGHT: 16px" classid=clsid:61F112FC-2AEA-4E34-82A6-4C94AF61152E
	codeBase="<%=request.getContextPath()%>/static/tags/ocx/SLE300.ocx" >
	<PARAM NAME="_Version" VALUE="65536">
	<PARAM NAME="_ExtentX" VALUE="926">
	<PARAM NAME="_ExtentY" VALUE="423">
	<PARAM NAME="_StockProps" VALUE="0">
</OBJECT>
<SCRIPT Language="JavaScript">

var handle; 
function PJQ_Open(){
	var lRet;
	lRet = pjq.Open();		//打开设备串口
	if(lRet <= 0){
		msg.value = "启动评价器失败，可能未安装USB驱动程序。\n\n";    //失败
	}
	else{
		msg.value = "评价器已启动。\n\n";//" Open device succeed.\n\n";   //成功
		handle = lRet;														 //保存设备串口句柄
	}
}

function PJQ_Voice1(){
	var lRet;
	lRet = pjq.Voice(handle,1);			//播放语音: "您好，欢迎光临"
	if(lRet != 0){
		msg.value += "失败!\n\n";//" failed.\n\n";		//失败
	}
	else{
		msg.value += "您好，欢迎光临!\n\n";//" succeed.\n\n";   //成功
	}
}

function PJQ_Voice2(){
	var lRet;
	lRet = pjq.Voice(handle,2);     //播放语音: "请在一米线外排队等候"
	if(lRet != 0){
		msg.value += "失败!\n\n";//" failed.\n\n";    //失败
	}
	else{
		msg.value += "请在一米线外排队等候!\n\n";//" succeed.\n\n";   //成功
	}
}

function PJQ_Star1(){
	var lRet;
	lRet = pjq.SetLevel(handle,1);		//设置服务星级为1星级
	if(lRet != 0){
		msg.value += " failed.\n\n";		//失败
	}
	else{
	    msg.value += " 1 Star.\n\n";  //成功
	}	
}

function PJQ_Star2(){
	var lRet;
	lRet = pjq.SetLevel(handle,2);		//设置服务星级为2星级
	if(lRet != 0){
		msg.value += " failed.\n\n";    //失败
	}
	else{
	    msg.value += " 2 Star.\n\n";	//成功
	}	
}

function PJQ_Star3(){
	var lRet;
	lRet = pjq.SetLevel(handle,3);		//设置服务星级为3星级
	if(lRet != 0){
		msg.value += " failed.\n\n";		//失败
	}
	else{
	    msg.value += " 3 Star.\n\n";	//成功
	}	
}

function PJQ_Star4(){
	var lRet;
	lRet = pjq.SetLevel(handle,4);		//设置服务星级为4星级
	if(lRet != 0){
		msg.value += " failed.\n\n";		//失败
	}
	else{
	    msg.value += " 4 Star.\n\n";	//成功
	}	
}

function PJQ_Star5(){
	var lRet;
	
	lRet = pjq.SetLevel(handle,5);		//设置服务星级为5星级
	if(lRet != 0){
		msg.value += " failed.\n\n";		//失败
	}
	else{
	    msg.value += " 5 Star.\n\n";	//成功
	}	
}

function PJQ_Evaluate(){
	var lRet;									//评价键返回值
	var userid;  							//员工号
	var tradecode;						//交易代码
	var netpoint;							//机构码
	var serveripaddress;			//后台服务器IP地址
	var xj;										//员工服务星级
	lRet = pjq.Evaluate(handle,8);		//触发评价，语音提示："请对本次服务评价". 等待时间8秒
	switch( lRet ){
		case 1: msg.value += "非常满意!\n\n";//" Very Satisfied.\n\n";			//非常满意
						break;		
		case 2: msg.value += "一般!\n\n";//" Basically Satisfied.\n\n"; //一般
		        break;
		case 3: msg.value += "很不满意!\n\n";//" Very Disatisfied.\n\n";		//很不满意
		        break;
		case 5: msg.value += "超时未评!\n\n";//" overtime.\n\n";						//超时未评
		        break;
		default: msg.value += "评价失败!\n\n";//" failed.\n\n";							//评价失败
	}
	userid = "y002";
	tradecode = "7890";
	netpoint = "abcd"
	serveripaddress = "10.21.1.88";
	pjq.SendData(userid,tradecode,netpoint,serveripaddress,lRet);		//向后台服务器发送数据
	xj = pjq.GetStarNumber();														//获取员工当前服务星级
	pjq.SetLevel(handle,xj);														//点亮星级指示灯	
}

function PJQ_Close(){
	var lRet;
	lRet = pjq.Close(handle);					//关闭设备串口
	if(lRet != 0){
		msg.value += "关闭失败!\n\n";//" failed.\n\n";		//失败
	}
	else{
		msg.value += "关闭成功!\n\n";//" succeed.\n\n";		//成功
	}
}
</SCRIPT>
<p align="left">
	<font size="6" face="隶书" color="#ff0000" style="COLOR: darkgreen"> 
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;窗口评价器测试&nbsp; 
  	</font>
</p>
<p align="center">
</p>
<P align=center><TEXTAREA style="WIDTH: 913px; HEIGHT: 300px" name=msg rows=14 cols=111></TEXTAREA>&nbsp;</P>
<P align=center>&nbsp;&nbsp;&nbsp;&nbsp;<INPUT onclick=PJQ_Open() type=button value="启动" name=btnOpenDevice style="WIDTH: 82px; HEIGHT: 24px" size=34>&nbsp;&nbsp;&nbsp;</P>
<P align=center>&nbsp;<INPUT language=javascript style="WIDTH: 88px; HEIGHT: 24px" onclick=PJQ_Voice1() type=button size=27 value="问候" name=btnvoice1>&nbsp;&nbsp;&nbsp;
<INPUT language=javascript style="LEFT: 428px; WIDTH: 92px; TOP: 563px; HEIGHT: 24px" onclick=PJQ_Voice2() type=button size=31 value="请等候" name=btnvoice2>&nbsp; 
<INPUT language=javascript style="WIDTH: 90px; HEIGHT: 24px" onclick=PJQ_Evaluate() type=button size=31 value="开启评价" name=btnevaluate></P>
<!-- <P align=center><INPUT language=javascript style="WIDTH: 88px; HEIGHT: 24px" onclick=PJQ_Star1() type=button size=27 value="1  Star" name=btnstar1>&nbsp;&nbsp;<INPUT language=javascript style="WIDTH: 88px; HEIGHT: 24px" onclick=PJQ_Star2() type=button size=27 value="2  Stars" name=btnstar2>&nbsp; 
<INPUT language=javascript style="WIDTH: 88px; HEIGHT: 24px" onclick=PJQ_Star3() type=button size=27 value="3  Stars" name=btnstar3>&nbsp; 
<INPUT language=javascript style="WIDTH: 88px; HEIGHT: 24px" onclick=PJQ_Star4() type=button size=27 value="4  Stars" name=btnstar4>&nbsp; 
<INPUT language=javascript style="WIDTH: 88px; HEIGHT: 24px" onclick=PJQ_Star5() type=button size=27 value="5  Stars" name=btnstar5></P> -->
<P align=center>&nbsp;&nbsp;<INPUT language=javascript id="" style="WIDTH: 79px; HEIGHT: 24px" onclick=PJQ_Close() type=button size=105 value="关闭" name=btnclose>&nbsp;&nbsp;</P> 

</div>
</body>
</html>