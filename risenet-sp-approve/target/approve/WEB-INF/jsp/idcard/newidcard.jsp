<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script language="javascript" for="SynCardOcx1" event="CardIn( State );">
{ 
  if(State == 1)
  {
	  document.all['S1'].value=document.all['S1'].value+"读卡成功\r\n";	
	  document.all['S1'].value=document.all['S1'].value+"姓名:"+SynCardOcx1.NameA +"\r\n";
	 
	  document.all['S1'].value=document.all['S1'].value+"性别:"+SynCardOcx1.Sex +"\r\n";
	  document.all['S1'].value=document.all['S1'].value+"民族:"+SynCardOcx1.Nation +"\r\n";
	  document.all['S1'].value=document.all['S1'].value+"出生日期:"+SynCardOcx1.Born +"\r\n";
	  document.all['S1'].value=document.all['S1'].value+"地址:"+SynCardOcx1.Address +"\r\n";
	  document.all['S1'].value=document.all['S1'].value+"身份证号:"+SynCardOcx1.CardNo +"\r\n";
	  document.all['S1'].value=document.all['S1'].value+"有效期开始:"+SynCardOcx1.UserLifeB +"\r\n";
	  document.all['S1'].value=document.all['S1'].value+"有效期结束:"+SynCardOcx1.UserLifeE +"\r\n";
	  document.all['S1'].value=document.all['S1'].value+"发证机关:"+SynCardOcx1.Police +"\r\n";
	  document.all['S1'].value=document.all['S1'].value+"照片文件名:"+SynCardOcx1.PhotoName +"\r\n";
  }
}
</script>


<script language="JavaScript1.2">
  function FindReader_onclick()
  {
  	var str;
  	str = SynCardOcx1.FindReader();
  	if (str > 0)
  	{
  		if(str>1000)
  		{
  			str =document.all['S1'].value+ "读卡器连接在USB " + str+"\r\n" ;
  		}
  		else
  		{
  			str =document.all['S1'].value+ "读卡器连接在COM " + str+"\r\n" ;
  		}
  	}
  	else
  	{
  		str =document.all['S1'].value+ "没有找到读卡器\r\n";
  	}
  	document.all['S1'].value=str;
 	
  }
  function ReadSAMID_onclick()
  {
  	var str=SynCardOcx1.GetSAMID();
  	document.all['S1'].value=document.all['S1'].value+"读卡器SAMID为:"+str+"\r\n";
  }
  function Clear_onclick()
  {
  	document.all['S1'].value="";
  }
  function ReadCard_onclick()
  {
	var nRet;
  	SynCardOcx1.SetReadType(0);
  	nRet = SynCardOcx1.ReadCardMsg();
  	if(nRet==0)
  	{
  		document.all['S1'].value=document.all['S1'].value+"读卡成功\r\n";	
  		document.all['S1'].value=document.all['S1'].value+"姓名:"+SynCardOcx1.NameA +"\r\n";
  		 if(SynCardOcx1.Sex/2==0){
  			  document.all['S1'].value=document.all['S1'].value+"性别:"+'女' +"\r\n";
  		  }else{
  			  document.all['S1'].value=document.all['S1'].value+"性别:"+'男' +"\r\n";
  		  }  	
  		var str=SynCardOcx1.Nation;
  		$('#nation').val(str);
  		$("#form2").ajaxSubmit({
			type : 'POST',
			dataType : 'json', 
			/* url : '${ctx}/zkAttendance/accept?department='+$('select').find('option:selected').text(), */
			url : '${ctx}/idcard/find',
			success : function(responseText, statusText, xhr, $form) {
				document.all['S1'].value=document.all['S1'].value+"民族:"+responseText.name+"\r\n"; 
				document.all['S1'].value=document.all['S1'].value+"出生日期:"+SynCardOcx1.Born +"\r\n";
		  		document.all['S1'].value=document.all['S1'].value+"地址:"+SynCardOcx1.Address +"\r\n";
		  		document.all['S1'].value=document.all['S1'].value+"身份证号:"+SynCardOcx1.CardNo +"\r\n";
		  		document.all['S1'].value=document.all['S1'].value+"有效期开始:"+SynCardOcx1.UserLifeB +"\r\n";
		  		document.all['S1'].value=document.all['S1'].value+"有效期结束:"+SynCardOcx1.UserLifeE +"\r\n";
		  		document.all['S1'].value=document.all['S1'].value+"发证机关:"+SynCardOcx1.Police +"\r\n";
		  		document.all['S1'].value=document.all['S1'].value+"照片文件名:"+SynCardOcx1.PhotoName +"\r\n";
 		}
		}); 
  		
  		
  	}
  }
  function ReadCardAuto_onclick()
  {
  	SynCardOcx1.SetloopTime(1000);
  	SynCardOcx1.SetReadType(1);
  }

  function PhotoPath_onclick()
  {
  	var str="";
  	SynCardOcx1.SetPhotoPath(0,str);
  	document.all['S1'].value=document.all['S1'].value+"照片保存路径设置为C盘根目录\r\n";
  }
  function PhotoPath2_onclick()
  {
  	var str="";
  	SynCardOcx1.SetPhotoPath(1,str);
  	document.all['S1'].value=document.all['S1'].value+"照片保存路径设置为当前目录\r\n";
  }
  function PhotoPath3_onclick()
  {
  	var str="D:\\Photo";
  	var nRet;
  	nRet= SynCardOcx1.SetPhotoPath(2,str);
  	if(nRet == 0)
  	{
  		document.all['S1'].value=document.all['S1'].value+"照片保存路径设置为"+str+"\r\n";
  	}
  	else
  	{
  		document.all['S1'].value=document.all['S1'].value+"照片保存路径设置失败\r\n";  	
  	}
  }
  function PhotoType_onclick()
  {
  	var nRet;
	nRet=SynCardOcx1.SetPhotoType(0);
	if(nRet==0)
	{
		document.all['S1'].value=document.all['S1'].value+"照片保存格式设置为Bmp\r\n";
	}
  }
  function PhotoType2_onclick()
  {
  	var nRet;
	nRet=SynCardOcx1.SetPhotoType(1);
	if(nRet==0)
	{
		document.all['S1'].value=document.all['S1'].value+"照片保存格式设置为Jpeg\r\n";
	}
  }
  function PhotoType3_onclick()
  {
  	var nRet;
	nRet=SynCardOcx1.SetPhotoType(2);
	if(nRet==0)
	{
		document.all['S1'].value=document.all['S1'].value+"照片保存格式设置为Base64\r\n";
	}
  }
  function PhotoName_onclick()
  {
   	var nRet;
	nRet=SynCardOcx1.SetPhotoName(0);
	if(nRet==0)
	{
		document.all['S1'].value=document.all['S1'].value+"照片保存文件名设置为tmp\r\n";
	}
  }
  function PhotoName2_onclick()
  {
   	var nRet;
	nRet=SynCardOcx1.SetPhotoName(1);
	if(nRet==0)
	{
		document.all['S1'].value=document.all['S1'].value+"照片保存文件名设置为 姓名\r\n";
	}
  }
  function PhotoName3_onclick()
  {
   	var nRet;
	nRet=SynCardOcx1.SetPhotoName(2);
	if(nRet==0)
	{
		document.all['S1'].value=document.all['S1'].value+"照片保存文件名设置为 身份证号\r\n";
	}
  }
  function PhotoName4_onclick()
  {
   	var nRet;
	nRet=SynCardOcx1.SetPhotoName(3);
	if(nRet==0)
	{
		document.all['S1'].value=document.all['S1'].value+"照片保存文件名设置为 姓名_身份证号\r\n";
	}
  }
  function PhotoName5_onclick()
  {
  	var str= SynCardOcx1.Base64Photo;
	document.all['S1'].value=document.all['S1'].value+str+"  \r\n";
  }
	
  function load(){
	  FindReader_onclick();
  }
  
  function readCardNo(){
	  ReadCard_onclick();	  
	  document.getElementById("text1").value=SynCardOcx1.CardNo;	
  }
</script>
</head>
<body onload="load()">
	<p>
<object classid="clsid:4B3CB088-9A00-4D24-87AA-F65C58531039" id="SynCardOcx1" codeBase="SynCardOcx.CAB#version=1,0,0,1" width="102" height="126" >
</object>
</p>
<form method="POST"  name="form1" id="form2">
  <p>
  <input type="hidden" id="nation" name="nation" value="">
  <textarea rows="17" name="S1" cols="82"></textarea></p>
	<p>
	身份证<input type="text" id="text1" onfocus="readCardNo()"  style="width:170px" >
  <input type="button" value="自动寻找读卡器" name="FindReadBtn" onclick="FindReader_onclick()">
  <input type="button" value="获得SAMID" name="GetSAMIDBtn" onclick="ReadSAMID_onclick()">
  <input type="button" value="手动读卡" name="ReadCardBtn" onclick="ReadCard_onclick()">
  <input type="button" value="自动读卡" name="ReadCardAutoBtn" onclick="ReadCardAuto_onclick()">
  <input type="button" value="清除所有信息" name="ClearBtn" onclick="Clear_onclick()"></p>
	<p>
  照片保存路径:&nbsp;&nbsp;
  <input type="button" value="C盘根目录" name="PathBtn" onclick="PhotoPath_onclick()">
  <input type="button" value="当前目录" name="Path2Btn" onclick="PhotoPath2_onclick()">
  <input type="button" value="指定路径" name="Path3Btn" onclick="PhotoPath3_onclick()"></p>
	<p>
  照片保存格式:&nbsp;&nbsp;
  <input type="button" value="bmp" name="PhotoTypeBtn" onclick="PhotoType_onclick()">
  <input type="button" value="Jpeg" name="PhotoType2Btn" onclick="PhotoType2_onclick()">
  <input type="button" value="Base64" name="PhotoType3Btn" onclick="PhotoType3_onclick()"></p>
	<p>
  照片文件名格式:
  <input type="button" value="tmp" name="PhotoNameBtn" onclick="PhotoName_onclick()">
  <input type="button" value="姓名" name="PhotoName2Btn" onclick="PhotoName2_onclick()">
  <input type="button" value="身份证号" name="PhotoName3Btn" onclick="PhotoName3_onclick()">
  <input type="button" value="姓名_身份证号" name="PhotoName4Btn" onclick="PhotoName4_onclick()">
  <input type="button" value="Base64照片" name="PhotoName4Btn0" onclick="PhotoName5_onclick()"></p>
</form>
</body>
</html>