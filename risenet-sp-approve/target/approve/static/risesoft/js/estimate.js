var handle; 
var success = false;
var tourl="";
var documenttitle="";
var spApproveItemId="";//获取审批服务评价事项id
function PJQ_Open()
{
	var lRet;
	lRet = pjq.Open();		//打开设备串口		
	if(lRet <= 0)
	{
		alert("启动评价器失败，可能未安装USB驱动程序。\n\n或者请拨出评价器后再插回原来的USB口。");    //失败
		toPageFunction();
		return;
	}/*
	else
	{
		alert(" 评价器已启动。\n\n");   //成功
		handle = lRet;						
	}*/
    handle = lRet;
}

function PJQ_Voice1() 
{
	var lRet;	
	lRet = pjq.Voice(handle,1);			//播放语音: "您好，欢迎光临"
	if(lRet != 0)
	{
		alert("失败!\n\n");		//失败
	}
	else
	{
		alert("您好，欢迎光临!\n\n");   //成功
	}
}


function PJQ_Voice2() 
{
	var lRet;	
	lRet = pjq.Voice(handle,2);     //播放语音: "请在一米线外排队等候"
	if(lRet != 0)
	{
		alert("失败!\n\n");    //失败
	}
	else
	{
		alert("请在一米线外排队等候!\n\n");//" succeed.\n\n";   //成功
	}
}


function PJQ_Star1() 
{
	var lRet;	
	lRet = pjq.SetLevel(handle,1);		//设置服务星级为1星级
	if(lRet != 0)
	{
		alert(" failed.\n\n");		//失败
	}
	else
	{
	    alert(" 1 Star.\n\n");  //成功
	}	
}


function PJQ_Star2() 
{
	var lRet;	
	lRet = pjq.SetLevel(handle,2);		//设置服务星级为2星级
	if(lRet != 0)
	{
		alert(" failed.\n\n");    //失败
	}
	else
	{
	    alert(" 2 Star.\n\n");	//成功
	}	
}


function PJQ_Star3() 								
{
	var lRet;
	
	lRet = pjq.SetLevel(handle,3);		//设置服务星级为3星级
	if(lRet != 0)
	{
		alert(" failed.\n\n");		//失败
	}
	else
	{
	    alert(" 3 Star.\n\n");	//成功
	}	
}


function PJQ_Star4() 
{
	var lRet;	
	lRet = pjq.SetLevel(handle,4);		//设置服务星级为4星级
	if(lRet != 0)
	{
		alert(" failed.\n\n");		//失败
	}
	else
	{
	    alert(" 4 Star.\n\n");	//成功
	}	
}


function PJQ_Star5() 
{
	var lRet;
	
	lRet = pjq.SetLevel(handle,5);		//设置服务星级为5星级
	if(lRet != 0)
	{
		alert(" failed.\n\n");		//失败
	}
	else
	{
	    alert(" 5 Star.\n\n");	//成功
	}	
}


function PJQ_Evaluate() 
{
	var lRet;									//评价键返回值
	var userid;  							//员工号
	var tradecode;						//交易代码
	var netpoint;							//机构码
	var serveripaddress;			//后台服务器IP地址
	var xj;										//员工服务星级				
	lRet = pjq.Evaluate(handle,8);		//触发评价，语音提示："请对本次服务评价". 等待时间8秒
	/*switch( lRet )
	{
		case 1:alert("非常满意!\n\n");//" Very Satisfied.\n\n";			//非常满意
						break;		
		case 2: alert("一般!\n\n");//" Basically Satisfied.\n\n"; //一般
		        break;
		case 3: alert("很不满意!\n\n");//" Very Disatisfied.\n\n";		//很不满意
		        break;
		case 5: alert("超时未评!\n\n");//" overtime.\n\n";						//超时未评
		        break;
		default: alert("评价失败!\n\n");//" failed.\n\n";							//评价失败
	}*/
    if(lRet == 1 || lRet == 2 || lRet == 3){
    	if($("#fromPage").val().indexOf("spfwpj")>-1){//审批服务评价
    		$("#remindTd").html("正在提交请稍后......"); 
    		$("#remindTd").css('color','red'); 
    		$("#startbut").attr("disabled",true);
    		$.post(ctx+'/senateManage/spItemsSenateSave?problem=1&type=5&svalue='+lRet+'&spApproveItemId='+spApproveItemId+'&userMacAddr=' + MACAddr+'&fromPage='+$('#fromPage').val()+'&declaresn='+$('#declaresn').val(), '', function(senateResult) {
        		alert(senateResult.msg);
        		$("#remindTd").html("提示：请输入已有业务编号，如无业务编号，系统将自动产生。"); 
        		$("#remindTd").css('color','black'); 
        		$("#startbut").attr("disabled",false);
    		},"json");
    	}else{
    		$.post(ctx+'/senateManage/senateSave?problem=1&svalue='+lRet+'&processInstanceId='+$('#processInstanceId').val() + "&userMacAddr=" + MACAddr+'&fromPage='+$('#fromPage').val(), '', function(senateResult) {
        		alert(senateResult.msg);
        		toPageFunction();
    		},"json");
    		success = true;
    	}
    }else if(lRet == 5){
    	//Dialog.alert("超时未评，请重新评价！",null,null,null,20);
         alert("超时未评，请重新评价！");
         toPageFunction();
         success = false;
    }else{
    	//Dialog.alert("评价失败!",null,null,null,20);
        alert("评价失败!");
        toPageFunction();
         success = false;
    }
    /*
	userid = "y002";
	tradecode = "7890";
	netpoint = "abcd"
	serveripaddress = 'http://10.161.109.89:8081/approve/supervise/senate/11.jsp';//'/approve/supervise/senate/SenateSaveAction.do?problem=1&svalue='+lRet+'&instanceguid='+document.all('instanceGUID').value;

	pjq.SendData(userid,tradecode,netpoint,serveripaddress,lRet);		//向后台服务器发送数据
	
    xj = pjq.GetStarNumber();alert(xj);	
	pjq.SetLevel(handle,xj);*/
	PJQ_Close();														
}


function PJQ_Close()
{
	var lRet;
	
	lRet = pjq.Close(handle);					//关闭设备串口
	/*
	if(lRet != 0)
	{
		alert("关闭失败!\n\n");//" failed.\n\n";		//失败
	}
	else
	{
		alert("关闭成功!\n\n");//" succeed.\n\n";		//成功
	}*/	
}
function receive(){
	PJQ_Open();
	if(typeof(handle) != 'undefined')
		PJQ_Evaluate();
}
function validateForm(){
    try{        
    	if(!success){
			if(confirm("未进行服务评价，是否现在评价？")){
				receive();
				window.parent.refreshMenu();
				return false;
			}else{
				window.parent.refreshMenu();
			}
		}
    	//toPageFunction();
    	if($("#fromPage").val().indexOf("zzlq")>-1){//证照领取 不进行评价器评价，则发送短信评价
    		$.post(ctx+'/certificate/sendSms?workflowinstance_guid='+$("#workflowinstance_guid").val(), '', function(senateResult) {
    			
    		},"text");
    	}
	}catch(e){}
	return true;
}

function toPageFunction(){
	if($("#fromPage").val().indexOf("zzlq")>-1){//证照领取评价
		window.location.href=tourl;
	}else if($("#fromPage").val().indexOf("kaishishouli")>-1){//开始受理评价
		window.parent.updateTab(documenttitle,tourl);
	}else{//无证照办结评价
	}
}