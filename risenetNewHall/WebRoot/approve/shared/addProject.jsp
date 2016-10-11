<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="net.risesoft.dwr.approve.dao.ScjgkXmxxDao"%>
<%@page import="net.risesoft.beans.user.UserInfo"%>
<%@page import="net.risesoft.common.Common"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>项目建项</title>
<script type="text/javascript" src="/js/Scripts/jquery-1.7.1.min.js"></script>
<script src='/dwr/engine.js'></script>
<script src='/dwr/util.js'></script>
<script src='/dwr/interface/XzspDwrDeal.js'></script>
<link href="/css/emp.css" type="text/css" rel="stylesheet"/>
<link href="/css/login-box.css" type="text/css" rel="stylesheet" />
<%UserInfo userinfo = (UserInfo)request.getSession().getAttribute(Common.sessionLoginUserID); %>
<script language='JavaScript'>
function checkField(){
   var form = document.forms[0];
   var patrnf = "^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$";
   var patrnz = "^\d+$ ";
   var xmztz = form.xmztz.value;
   if(xmztz!=""){
	   if (null==xmztz.match(patrnf)&&null==xmztz.match(patrnz)){
		   alert("项目总投资为数字!!");form.xmztz.focus();
	   	return false;
	   }  
	   return true ;
	}else{
		return true;
	}
}
function SaveFormDataThenShow(){
	var flag = true;
  	var xmmc = $("#xmmc").val();
	var xmbh = $("#xmbh").val();
	var jsdw = $("#jsdw").val();
	var gcdz = $("#gcdz").val();;
	var xmztz = $("#xmztz").val();
	var jhlxwh = $("#jhlxwh").val();
  	var ghxkzh = $("#ghxkzh").val();
  	var jgdm = $("#jgdm").val();
  	var method = $("#method").val();
  	var judexmbh = "";
  	//保存时生成xmbh
  	$.ajax({   
          type:"POST",  
           url:"/onlineService/GetxmbhAction.html",
           cache:false,
             data: {xmbh:xmbh}, 
       		async:false,  
           dataType:"json",  
          success:function(result){ 
  		xmbh = result.message;
  		judexmbh = result.flag;
        },  
         error:function (result) {   
            alert('Failed');   
         }
  });
  	if(judexmbh=="1"){
  		alert("编号重复保存失败!");
  		window.close();
  		return;
  	}
  	if(!checkField()){
  		flag = false;
  	}
   if(xmmc ==null || xmmc ==""){
   		flag = false;
   		alert("请填写项目名称!");
   		return;
   }else{
	  var exists = $("#exists").val();
	  if(exists=="1"){
		  flag =false;
		  return;
	  }
   }
  

   if(xmbh ==null || xmbh ==""){
	   flag = false;
	   alert("请填写项目编号!");
	   return;
   }
   
	if(jsdw ==null || jsdw==""){
		flag = false;
		alert("请填写建设单位!");
		return;
	}
	
	if(xmztz ==null || xmztz==""){
		flag = false;
		alert("请填写项目总投资!");
		return;
	}
	
	if(flag){
		var array = new Array(xmmc,xmbh,jsdw,gcdz,xmztz,jhlxwh,ghxkzh);
		$.post("/onlineService/scjgkXmxxAction.html",
				{'xmmc':xmmc,'xmbh':xmbh,'jsdw':jsdw,'gcdz':gcdz,'xmztz':xmztz,'jhlxwh':jhlxwh,'ghxkzh':ghxkzh,'jgdm':jgdm,'method':method},
				function(data){
					window.returnValue = array;
			   		window.close();
			});
	}else{
		return;
	}
}

/**
 * 判断项目名称是否存在
 */
function checkXmmc(){
	var xmmc = $("#xmmc").val();
	var xmmcnote = $("#xmmcnote");
	
	if(null == xmmc || xmmc==""){
		xmmcnote.html("<font color='red'>*项目名称不能为空!</span>");
	}else{
		$.post("/onlineService/checkXmmcAction.html",
			{'xmmc':xmmc},
			function(data){
				var obj =  eval('(' + data + ')');  
				if(obj.message=="0"){
					xmmcnote.html("<input type='hidden' value='0' id='exists'/><font color='red'>*</span>");
				}else{
					xmmcnote.html("<input type='hidden' value='1' id='exists'/><font color='red'>*项目已建项,不能重复建!</span>");
				}
		});
	}
}

function selectJsdw(){
    var shareId="{0A009FA8-0000-0000-1FF0-D290000001CD}";
    var url = "/approve/shared/sharedXmxx.jsp?sharedGuid="+shareId;
	//var url = "/shared/selectXmxx/shareId/{0A009FA8-0000-0000-1FF0-D290000001CD}.html";    
     window.showModalDialog(url,window,"dialogWidth:700px;dialogHeight:450px;center:yes;status:no;help:no;");
}
</script> 
</head>
<body>
<s:form action="/scjgk/ywbl/scjgkXmxxAction.do" method="post">
   <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" class="table1 table1-bordered">    	
   <tr>
   	  <th style="width:20%;text-align:right;">项目名称:</th>
	   <td>
	   		<s:textfield cssClass="input1_query" name="xmmc" id="xmmc" onblur="checkXmmc();"/>
	   		<span id="xmmcnote"><font color="red" >*</span></font>
	   </td>
   </tr>
   <tr>   
	   <th style="text-align:right">项目编号:</th>
	   <td >
	   		<input name="xmbh" class="input1_query"  id="xmbh" value="<%=ScjgkXmxxDao.setDefaultXmbh(request) %>"  readonly="readonly">
	   </td>
   </tr> 
   <tr>   
     <th style="text-align:right">建设单位:</th>
   <td >
      <s:textfield name="jsdw" cssClass="input1_query" id="jsdw"/>&nbsp;
      <input type="button" class="buttonClass_4" value="选择" onclick="selectJsdw()"/>
   </td>
   </tr>    
    <tr>   
     <th style="text-align:right">工程地址:</th>
   	<td>
   		<s:textfield name="gcdz" cssClass="input1_query" id="gcdz"></s:textfield>
   </td>
   </tr>     
   <tr>   
     <th style="text-align:right">项目总投资:</th>
   <td >
   		<s:textfield cssClass="input1_query" name="xmztz" id="xmztz"/>
   		<s:label>万元</s:label>
   </td>
   </tr>
    <tr>   
     <th style="text-align:right">计划立项文号:</th>
   <td>
   		<s:textfield  cssClass="input1_query" name="jhlxwh" id="jhlxwh"/>
   </td>
   </tr>  
   <tr>   
     <th style="text-align:right">规划许可证号:</th>
   <td>
   		<s:textfield cssClass="input1_query" name="ghxkzh" id="ghxkzh"/>
   </td>
   </tr> 
   <tr>   
     <th style="text-align:right">机构代码编号:</th>
   <td >
   	<s:textfield name="jgdm" id="jgdm" cssClass="input1_query"/>
   </td>
   </tr>
   <tr>   
     <th style="text-align:right">用户名称:</th>
   <td>
   		 <input class="input1_query"  name="username" id="username" value="<s:property value='#session.loginUser.companyUser.ename'/>" readonly="true"/>
   </td>
   </tr> 
   <tr>   
     <th style="text-align:right">登陆名称:</th>
   <td >
   	<input name="longingname" id="longingname" class="input1_query" value="<s:property value='#session.loginUser.loginname'/>" readonly="true"/>
   </td>
   </tr>  
   <tr>
   	<td colspan="2">
   		<input type="hidden" name="method" id="method" value="childsave"/>
		<input type="button" class="buttonClass_4" style="width:60px;" text="确定" value="确定" onclick="SaveFormDataThenShow();"/>
   	</td>
   </tr>
</table>
</s:form > 
</body>
</html>