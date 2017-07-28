<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/head.jsp"%>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

</head>
<body onload="loadobj()">

	<div>
			<table align="center" heigth="600px">
			    <tr>
			        <td>
			            <input type="button" value="保存pdf" onclick="savePDF()">
			            <input type="button" value="撤销pdf" onclick="undoPDF()">
			        </td>
			    </tr>
			    <tr>
				    <td>
					    <object align="middle" id="PDFReader" classid="CLSID:A7018BBC-35EA-49DE-9E97-988099D4E80C"
						    viewastext width="830px" height="550px">
						</object>
				    </td>
				</tr>
			</table>
	</div>
	
<script language="javascript">
var dlg,pdf,temppath,wnd;
function loadobj(){
	dlg=new ActiveXObject("BCWindowsLib.FileOpr");
	dlg.Caption="请选择合适的文件";
	pdf=document.getElementById("PDFReader").PDFWnd;
	wnd=document.getElementById("PDFReader");
	pdf.Authority=new pwddlg();
	//wnd.object.m_params.recall=invokeVB;
	//window.external.SetCaption(document.title);
	temppath=location.pathname.replace(/\//g,"\\").replace(/%20/g," ");
	temppath=temppath.substring(1,temppath.lastIndexOf("\\")+1)+"temp\\";
	hiddenToolbar();
	openserver();
}


var xmlprogid = "Msxml2.XMLHTTP.4.0";
function openserver() {
	try {
	    var d = new Date();
	    var url = "${ctx}/ntko/loadPDF?processSerialNumber=${processSerialNumber}";
	    if (url == "")
	    	return;
	    var request;
	    try {
	    	request = new ActiveXObject("Msxml2.XMLHTTP");
	    	if (request == null)
	    		throw new Error(undefined, "");
	    } catch (e) {
			this.alert("请确认您机器上安装的Windows的XML补丁（不低于4.0版本），并且IE的安全设备可以使用ActiveX控件。");
			return false;
	    }
	    request.open("GET", url, false);

	    try {
	    	request.send();
	    } catch (e) {
			this.alert("发送文档请求失败：" + e.message);
			return false;
	    }
	    if (request.status != 200) {
			this.alert("服务器返回错误：" + request.responseText);
			return false;
	    }
	    try {
	    	pdf.Load(request.responseBody);
	    } catch (e) {
			this.alert("接收文档数据失败：" + e.message);
			return false;
	    }
	} catch (e) {
	    return;
	}
}

//保存pdf
function savePDF() {
	//var count = getSealcount();
	pdf1 = document.getElementById("PDFReader");
	var url ="${ctx}/ntko/saveAsPDFFile?processSerialNumber=${processSerialNumber}&processInstanceId=${processInstanceId}&taskId=${taskId}&istaohong=2&fileType=.pdf";
	var request;
	try {
		try {
			request = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try {
				request = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e2) {
				request = false;
			}
		}
		if (request == null)
			throw new Error(undefined, "");
	} catch (e) {
		this.alert("请确认您机器上安装的Windows的XML补丁（不低于4.0版本），并且IE的安全设备可以使用ActiveX控件。");
		return false;
	}
	request.open("POST", url, false);
	request.setRequestHeader("Content-Type", "application/octet-stream");
	try {
		request.send(pdf1.PDFWnd.SaveAs("binary"));
	} catch (e) {
		this.alert("发送文档请求失败，请重试！");
		return false;
	}
	alert(request.status);
	if (request.status != 200) {
		this.alert("服务器返回错误，请重试！");
		return false;
	}
	alert("保存成功!");
	window.location.reload();
}
//撤销PDF,打开转PDF前的状态
function undoPDF(){
	var processSerialNumber="${processSerialNumber}";
	$.ajax({
	    async:false,
	    cache: false,
        type: "GET",
        url: "${ctx}/ntko/undoPDF",
        data: {processSerialNumber:processSerialNumber},
        dataType: "json",
        success: function(data){
             if(data.success){
            	 alert("撤销PDF成功");
            	 window.location.href="${ctx}/ntko/display?processSerialNumber=${processSerialNumber}&itembox=${itembox}&taskId=${taskId}";
             }else{
            	 alert("撤销PDF成功");
             }
       }
	});
}

//获取印章数量
function getSealcount(){
	var seal = new ActiveXObject("BCPdfSealLib.PdfDocument");
	seal.Document = pdf;
	return seal.seals.Count();
}
//隐藏工具栏
function hiddenToolbar(){
	toolbar1();
	//toolbar2();
	//toolbar3();
	toolbar4();
	toolbar5();
}

function invokeVB(parms){    
    wnd.object.m_params.x=parms.x;
    wnd.object.m_params.y=parms.y;
    wnd.object.m_params.page=parms.page;
    wnd.GoOnSign();
    wnd.object.m_params.recall=invokeVB;
}

function pwddlg(){
	this.UserPassword=function(){return window.prompt("请输入密码","");}
}
//打印
function print(){
    pdf.PrintDocument(0);
}
//打印控制
function toolbar1(){
    try{
    	wnd.PrintBarVisible=!wnd.PrintBarVisible;
    }catch (e){
        alert("出现错误："+e.number+" "+e.message);
    }
}
//文件工具栏
function toolbar2(){
    try{
   	 	wnd.FileBarVisible=!wnd.FileBarVisible;
    }catch (e){
        alert("出现错误："+e.number+" "+e.message);
    }
}
//签章工具栏
function toolbar3(){
    try{
    	wnd.SignSealBarVisible=!wnd.SignSealBarVisible;
    }catch (e){
        alert("出现错误："+e.number+" "+e.message);
    }
}
//视图工具栏
function toolbar4(){
    try{    
    	wnd.ZoomBarVisible=!wnd.ZoomBarVisible;
    } catch (e){
        alert("出现错误："+e.number+" "+e.message);
    }
}
//书签工具栏
function toolbar5(){
    try{    
    	wnd.BookMarkVisible=!wnd.BookMarkVisible;
    }catch (e){
        alert("出现错误："+e.number+" "+e.message);
    }
}
</script>
</body>
</html>
