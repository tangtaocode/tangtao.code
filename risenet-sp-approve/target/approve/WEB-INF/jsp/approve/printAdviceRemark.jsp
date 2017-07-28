﻿﻿﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/static/common/taglib.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
 <link href="${ctx }/static/risesoft/css/riseform.css" rel="stylesheet" type="text/css" ></link>

 
<title>一次性补交告知单</title>
<script language="javascript"> 
 
function code39Init() {
	var sn = "null";
	document.getElementById("code39Img").src="Code39ImgDisplay.jsp?sn="+sn;
}
</script>


</head>
<body style="font-family: '微软雅黑';font-size: 15px;">
 
 
<OBJECT classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2 height=0 id=WB width=0></OBJECT>
<script language="JavaScript"> 
var hkey_root,hkey_path,hkey_key,RegWsh;
hkey_root="HKEY_CURRENT_USER";
hkey_path="\\Software\\Microsoft\\Internet Explorer\\PageSetup\\";
RegWsh = new ActiveXObject("WScript.Shell");
function pagesetup_null()
{
	try{
	    hkey_key="header"; 
	    RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,"");
	    hkey_key="footer";
	    RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,"");
	  }catch(e){}
}
function pagesetup_default()
{
	try{
	    hkey_key="header";  
	    RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,"&w&b页码，&p/&P");
	    hkey_key="footer";
	    RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,"&u&b&d");
	   }catch(e){}
}	
function setPrint()
{
	WB.ExecWB(8,1);
}
function previewPrint()
{
	pagesetup_null();
	actionbar.style.display = 'none';
	WB.ExecWB(7,1);
	actionbar.style.display = 'block';
}
function derectPrint()
{
	WB.printing.copies = document.actionbar.pagenum;
   	WB.printing.Print(false);
	actionbar.style.display = 'none';
	WB.ExecWB(6,6);
	actionbar.style.display = 'block';
}
function print(obj)
{
	actionbar.style.display = 'none';
	pagesetup_null();
	hkey_key = "margin_top";
	
	if (typeof(this.openComment)=="undefined" && !false)
		RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,"1.9675");		
	else
		RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,"0.78");
	hkey_key = "margin_bottom";
	RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,"0.591");
	WB.ExecWB(6,1);
	actionbar.style.display = 'block';
}

function doFolder(obj,id){
	var name = "list"+id;
	var isOpen = $(obj).attr('isOpen');//获取目录打开状态,0为关闭，1为打开
    var $ptr = $(obj).parent();//获取父级节点tr
    if (isOpen == '0') {
    	 $("tr[name='"+name+"']").each(function(){
        	 $(this).show(500);
        })
        $(obj).children('img').attr('src', '${ctx}/static/images/open.gif');//更新目录标示图片的打开状态
        $(obj).attr('isOpen', '1');//更新目录打开状态
    }
    else {
        $("tr[name='"+name+"']").each(function(){
        	 $(this).hide(500);
        })
        $(obj).children('img').attr('src', '${ctx}/static/images/close.gif');//更新目录标示图片的打开状态
        $(obj).attr('isOpen', '0');//更新目录打开状态
    }
}
</SCRIPT>
 
<div id=actionbar align=center >
<INPUT type="button" value="打    印" onclick="print(1)" style="border:none" class="button orange">
<INPUT type="button" value="打印设置" onclick="setPrint()" style="border:none" class="button orange">
<INPUT type="button" value="打印预览" onclick="previewPrint()" style="border:none" class="button orange">
</div>
 
	<%-- <table  width="90%" align="center" cellspacing="0" cellpadding="0" class="guide_table MT15" border="1" bordercolor="#dedede" style="border-collapse: collapse;">
		<tr><th colspan="4"><font id="title1">福保街道办事处计生证件类《计划生育服务证》办理所需提供资料一次性告知单(存根)</font></th>
		<tr>
			<td width="20%"  align="center">姓名：</td><td width="25%">${map.person }</td>
			<td width="20%"  align="center">联系电话：</td><td>${map.mobile }</td>
		</tr>
		<tr>
			<td  align="center">办理事项：</td><td id="blsx1">${map.itemname }</td>
			<td  align="center">业务流水号：</td><td id="ywlsh1">${map.declaresn }</td>
		</tr>
		<tr>
			<td  align="center">告知时间：</td>
			<td id="gzsj1" colspan="3">${map.adviceTime }</td>
		</tr>
		<tr>
			<td colspan="4">注意：当事人承诺所递交的材料真实有效。如提供虚假材料的，将被列入问题名单，一年内不能办理。 </td>
		</tr>
		<tr>
			<td colspan="4" align="center">当事人签名：</td>
		</tr>
		
	</table>
	<div style="height: 50px;"></div>
	<hr style="border-bottom:1px dashed #000;" width="90%"> --%>
	<div style="height: 10px;"></div>
	<table  width="90%" align="center"  cellspacing="0" cellpadding="0" class="guide_table MT15" border="1" bordercolor="#dedede" style="border-collapse: collapse;">
		<tr><th colspan="4"><font id="title2">${map.approveitemname }一次性告知说明</font></th>
		<tr>
			<td width="20%" align="center">事项名称：</td><td width="25%">${map.approveitemname }</td>
			<td  width="20%" align="center">事项性质：</td><td id="ywlsh2">${map.approveitemtype }</td>
		</tr>
		<tr>
			<td align="center">承诺时限：</td><td id="lxdh2">${map.timelimit }(单位：工作日)</td>
			<td align="center">法定时限：</td><td id="gzsj2">${map.legaltimelimit }(单位：工作日)</td>
		</tr>
		<tr>
		<td align="left" colspan="4">材料列表:</td>
		</tr>
		<c:if test="${!empty listtype }">
		<c:forEach items="${listtype }" var="type" varStatus="t">
			<tr onclick="closeFolder('${t.index+1}');">
				<th colspan="4" align="left" isopen='1' onclick="doFolder(this,'${t.index+1}');">&nbsp;&nbsp;&nbsp;&nbsp;<img name="folder${t.index+1}"  src="${ctx}/static/images/open.gif" onclick="closeFolder('${t.index+1}')">&nbsp;&nbsp;材料类别：${type.declareannextypename}</th>
			</tr>
			<c:forEach items="${list}" var="annex" varStatus="st">
				<c:if test="${type.declareannextypeguid==annex.typeguid}">
					<tr name="list${t.index+1}">
						<td colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${st.index+1}、${annex.declareannexname}</td>
					</tr>
				</c:if>
			</c:forEach>
		</c:forEach>
		</c:if>
		<tr>
			<td colspan="4">注意：<br>
				&nbsp;&nbsp;&nbsp;&nbsp;1、当事人承诺所递交的材料真实有效。如提供虚假材料的，将被列入问题名单，一年内不能办理;<br>
				&nbsp;&nbsp;&nbsp;&nbsp;2、即日起15个工作日以内需补齐补正材料到窗口办理（网上申报的可以直接网上提交）,逾期自动作废;
				&nbsp;&nbsp;&nbsp;&nbsp;3、审批平台办理的业务只可以发起一次补交告知;
			 </td>
		</tr>
		<tr>
		<td colspan="2" align="center">主管部门:
			${map.bureauname }</td>
			<td align="center">工作人员：${name }</td>
			<td>
			咨询电话：${tel }
			</td>
		</tr>
	</table>
</body>
</html>
