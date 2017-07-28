<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>证照领取</title>
<link type="text/css" rel="stylesheet" href="${ctx}/static/risesoft/css/code39.css" />
<%@ include file="/static/common/clientactivex.jsp"%>
<script type="text/javascript">
var ctx="${ctx}";
function certificateSave(){
	if (document.all("statusperson").value == "") {
		alert("请输入领取人");
		document.all("statusperson").focus();
		return false;
	}else if (document.all("getpersoncard").value == "") {
		alert("请输入领取人证件编号");
		document.all("getpersoncard").focus();
		return false;
	}else if (document.all("getpersonphone").value == "") {
		alert("请输入领取人手机号");
		document.all("getpersonphone").focus();
		return false;
	}else if (!validateInput(document.all("getpersonphone").value, "^[0-9]*$")){
        alert("领取人手机号输入有误！",null,null,null,20);
        document.all("getpersonphone").focus();
        return false
    }
	$.post('${ctx}/certificate/certificateSave', $("#form1").serialize(), function(result) {
		alert(result.msg);
		tourl="${ctx}/certificate/index";
		validateForm();
		//window.location.href = "${ctx}/certificate/index"; 
	},"json"); 
	//document.forms["form1"].submit(); 
}

function fanhui(){
	history.go(-1);
}
	
</script>
<title>领取登记</title>
</head>
<body>
<div class="box1" panelWidth="1000">

<form  name="form1" id="form1" method="post" action=""  failAlert="请将表单填写完整！">
	<input type="hidden" name="workflowinstance_guid" id="workflowinstance_guid" value="${certificateMap.workflowinstance_guid}"/>
	<input type="hidden" name="processInstanceId" id="processInstanceId" value="${certificateMap.workflowinstance_guid}"/>
	<input type="hidden" name="fromPage" id="fromPage" value="zzlq"/>
   <table class="tableStyle" formMode="line">
        <tr><th colspan="4" style="text-align:center;font-size: 16px;">${certificateMap.APPROVEITEMNAME} 证件领取情况 </th></tr>
        <tr>
            <td>业务编号:</td>
            <td >${certificateMap.DECLARESN}&nbsp; </td>
            <td >事项名称:</td>
	        <td >
	         ${empty certificateMap.APPROVEITEMNAME ? '' : certificateMap.APPROVEITEMNAME }&nbsp;
			</td>
        </tr>
        <tr >
			<td >受理时间:</td>
            <td >
            ${empty certificateMap.DECLAREDATETIME ? '' : certificateMap.DECLAREDATETIME }&nbsp;
		    </td>
		    <td >办理人证件编号:</td>
            <td >
            ${empty certificateMap.ZHENGJIANDAIMA ? '' : certificateMap.ZHENGJIANDAIMA }&nbsp;
			</td>
        </tr>
        <c:if test="${(certificateMap.DOCWAY eq '√')&&(certificateMap.CERTIFYWAY eq '√')}"> 
	     	<tr >
				<td >文件标题:</td>
	          <td >
	           ${empty certificateMap.DOCTITLE ? '' : certificateMap.DOCTITLE }&nbsp;
				</td>
				<td >文号:</td>
	          <td >
	          ${empty certificateMap.DOCNUMBER ? '' : certificateMap.DOCNUMBER }&nbsp;
				</td>
	        </tr>
	        <tr>
	          <td>发文时间:</td>
	          <td >
	          ${empty certificateMap.SENDDATE ? '' : certificateMap.SENDDATE }&nbsp;
				</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
	        </tr>
	        
	        <tr >
				<td >证照名:</td>
	          <td >
	          ${empty certificateMap.CERTIFYNAME ? '' : certificateMap.CERTIFYNAME }&nbsp;
				</td>
				<td >证照编号:</td>
	          <td >
	          ${empty certificateMap.CERTIFYNUMBER ? '' : certificateMap.CERTIFYNUMBER }&nbsp;
				</td>
	        </tr>
		</c:if>
		<c:if test="${(certificateMap.DOCWAY eq '√')&&!(certificateMap.CERTIFYWAY eq '√')}"> 
	     	<tr >
			  <td >文件标题:</td>
	          <td >
	           ${empty certificateMap.DOCTITLE ? '' : certificateMap.DOCTITLE }&nbsp;
				</td>
				<td >文号:</td>
	          <td >
	          ${empty certificateMap.DOCNUMBER ? '' : certificateMap.DOCNUMBER }&nbsp;
				</td>
	        </tr>
	        <tr>
	       <td >发文时间:</td>
	          <td >
	          ${empty certificateMap.SENDDATE ? '' : certificateMap.SENDDATE }&nbsp;
				</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
	        </tr>
		</c:if>
		<c:if test="${!(certificateMap.DOCWAY eq '√')&&(certificateMap.CERTIFYWAY eq '√')}"> 
	     	<tr >
			  <td >证照名:</td>
	          <td >
	          ${empty certificateMap.CERTIFYNAME ? '' : certificateMap.CERTIFYNAME }&nbsp;
				</td>
				<td >证照编号:</td>
	          <td >
	           ${empty certificateMap.CERTIFYNUMBER ? '' : certificateMap.CERTIFYNUMBER }&nbsp;
				</td>
	        </tr>
		</c:if>

          <tr >
          <td >领取人:</td>
          <td >
          <input type="text" name="statusperson" size="20" ${readonly} id="statusperson" class="queryinput" value="${empty certificateMap.STATUSPERSON ? declareInfo.DECLARERLXR : certificateMap.STATUSPERSON }"/> 
			</td>
			<td >领取人手机号:</td>
          <td  >
          <input type="text" name="getpersonphone" ${readonly} id="getpersonphone" class="queryinput" value="${empty certificateMap.GETPERSONPHONE ? declareInfo.DECLARERMOBILE : certificateMap.GETPERSONPHONE }" >
          </td>
        </tr>
        	
		<tr >
		<td >领取人证件类型:</td>
			<td  >
			<SELECT name="idtype">
			<OPTION value=10 ${declareInfo.DECLARERLXRIDTYPE=='10'?'selected':''}>身份证</OPTION>
			<OPTION value=11 ${declareInfo.DECLARERLXRIDTYPE=='11'?'selected':''}>军官证</OPTION>
			<OPTION value=12 ${declareInfo.DECLARERLXRIDTYPE=='12'?'selected':''}>士兵证</OPTION>
			<OPTION value=13 ${declareInfo.DECLARERLXRIDTYPE=='13'?'selected':''}>警官证</OPTION> 
			<OPTION value=14 ${declareInfo.DECLARERLXRIDTYPE=='14'?'selected':''}>港澳居民来往内地通行证</OPTION>
			<OPTION value=15 ${declareInfo.DECLARERLXRIDTYPE=='15'?'selected':''}>台湾居民来往大陆通行证</OPTION> 
			<OPTION value=16 ${declareInfo.DECLARERLXRIDTYPE=='16'?'selected':''}>香港身份证</OPTION>
			<OPTION value=17 ${declareInfo.DECLARERLXRIDTYPE=='17'?'selected':''}>澳门身份证</OPTION> 
			<OPTION value=18 ${declareInfo.DECLARERLXRIDTYPE=='18'?'selected':''}>台湾身份证</OPTION>
			<OPTION value=20 ${declareInfo.DECLARERLXRIDTYPE=='20'?'selected':''}>护照</OPTION> 
			<OPTION value=40 ${declareInfo.DECLARERLXRIDTYPE=='40'?'selected':''}>其他有效个人身份证件</OPTION>
			<OPTION value=50 ${declareInfo.DECLARERLXRIDTYPE=='50'?'selected':''}>组织机构代码证</OPTION> 
			<OPTION value=51 ${declareInfo.DECLARERLXRIDTYPE=='51'?'selected':''}>营业执照</OPTION>
			<OPTION value=52 ${declareInfo.DECLARERLXRIDTYPE=='52'?'selected':''}>事业单位登记证书</OPTION> 
			<OPTION value=53 ${declareInfo.DECLARERLXRIDTYPE=='53'?'selected':''}>社团登记证书</OPTION>
			<OPTION value=54 ${declareInfo.DECLARERLXRIDTYPE=='54'?'selected':''}>民办非企业单位登记证书</OPTION> 
			<OPTION value=55 ${declareInfo.DECLARERLXRIDTYPE=='55'?'selected':''}>工会法人资格证书</OPTION>
			<OPTION value=60 ${declareInfo.DECLARERLXRIDTYPE=='60'?'selected':''}>税务登记证</OPTION> 
			<OPTION value=80 ${declareInfo.DECLARERLXRIDTYPE=='80'?'selected':''}>其他有效机构身份证件</OPTION></SELECT>			
			</td>
			<td>领取人证件编号:</td>
            <td >
            <input type="text" name="getpersoncard" ${readonly} id="getpersoncard" class="queryinput" value="${empty certificateMap.GETPERSONCARD ? declareInfo.DECLARERLXRID : certificateMap.GETPERSONCARD }">
            </td>	        
		</tr>
		<tr>
		<td >领取方式:</td>
			<td>
			<SELECT name="gettype"><OPTION value=0>实体窗口</OPTION>
			<OPTION value=1>网上</OPTION> <OPTION value=2>信函</OPTION><OPTION value=3>电报</OPTION> 
			<OPTION value=4>电传</OPTION><OPTION value=5>传真</OPTION> 
			<OPTION ${certificateMap.ISYJ=='1'?'selected':''} value=6>邮件</OPTION><OPTION value=7>电子数据交换</OPTION> 
			<OPTION value=8>快递</OPTION><OPTION value=9>其它</OPTION></SELECT>			
			</td>	       
		
		<c:if test="${(isrecieve eq '1')}"> 
			 <td >领取时间:</td>
			<td  >
			${empty certificateMap.RECEIVEDATE ? '' : certificateMap.RECEIVEDATE }&nbsp;
			</td>
		</c:if>
		<c:if test="${!(isrecieve eq '1')}"> 
			<td>&nbsp;</td>
        	<td>&nbsp;</td>
		</c:if>
		</tr>
		
		 <tr >
          <td >是否邮寄:</td>
          <td id="tdisyj">
           <select name="isyj" onchange="isshow()" >
           	<option value="是">是</option>
           	<option value="否">否</option>
           </select>
			</td>
			<c:if test="${(isrecieve eq '1')}"> 
				 <td >领取时间:</td>
				<td  >
				${empty certificateMap.RECEIVEDATE ? '' : certificateMap.RECEIVEDATE }&nbsp;
				</td>
			</c:if>
			<c:if test="${!(isrecieve eq '1')}"> 
				<td>&nbsp;</td>
	        	<td>&nbsp;</td>
			</c:if>
        </tr>

        <tr id="shi2">
          <td >收件人:</td>
           <td >
          <input type="text" name="yjrname" size="20" ${readonly} id="yjrname" class="queryinput" value="${empty certificateMap.YJRNAME ? '' : certificateMap.YJRNAME }"/> 
			</td>
	 
			<td >收件人电话:</td>
          <td  >
          <input type="text" name="yjrtel" ${readonly} id="yjrtel" class="queryinput" value="${empty certificateMap.YJRTEL ? '' : certificateMap.YJRTEL }">
          </td>
        </tr>
        <tr>
          <td>收件人身份证号码:</td>
          <td colspan="3">
         <input type="text" name="yjrsfz" style="width: 600px;" ${readonly} id="yjrsfz" class="queryinput" value="${empty declareInfo.YJRSFZ ? '' : declareInfo.YJRSFZ }">
          </td>
        </tr>
        <tr id="shi1">
          <td>邮寄物品:</td>
          <td>
           <input type="text" name="yjwp" ${readonly} id="yjwp" class="queryinput" value="${empty certificateMap.YJWP ? '' : certificateMap.YJWP }">
          
          </td>
			<td >快递单号:</td>
          <td  >
          <input type="text" name="kddh" ${readonly} id="kddh" class="queryinput" value="${empty certificateMap.KDDH ? '' : certificateMap.KDDH }">
          </td>
        </tr>
        <tr id="shi3">
          <td>邮寄地址:</td>
          <td colspan="3">
         <input type="text" name="yjrdz" style="width: 600px;" ${readonly} id="yjrdz" class="queryinput" value="${empty certificateMap.YJRDZ ? '' : certificateMap.YJRDZ }">
          </td>
        </tr>
                <tr></tr>
                <tr>
                <td colspan="4">
                <c:if test="${(isrecieve eq '0')}">
                <button type="button" onclick="certificateSave();"><span class="icon_save">领取</span></button>
                </c:if>
                <button type="button" onclick="fanhui();"><span class="icon_back">返回</span></button>
                </td>
                </tr>
				  
               
    </table>

</form>

</div>

   
   </body>
 
</html>