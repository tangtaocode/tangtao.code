<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../../share/taglib.jsp"%>
<html>
<head>

<title>深圳市龙岗区住房与建设局社会建设和民生创新专项资金网上申报系统</title>
<meta http-equiv='Pragma' content='no-cache' />
<meta http-equiv='Cache-Control' content='no-cache' />
<meta http-equiv='Expires' content='0' />
<style type="text/css">
body{ margin: 0;
	  padding: 0; 
	  }
.setpTitle{
	text-align:center;
	font-size:20px;
}
</style>
<link href="/css/appGuide.css" rel="stylesheet" type="text/css"/>
 <link rel="stylesheet" href="/js/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
 <link href="/css/tabIdCard.css" rel="stylesheet" type="text/css"/>
 <script src="/js/Scripts/jquery.min.js" type="text/javascript"></script>
 <script type="text/javascript" src="/js/zTree/jquery.ztree.core-3.5.min.js"></script>
<script src="/js/civilApply.js" type="text/javascript"></script>
<script charset="utf-8" src="/js/kindeditor/kindeditor-min.js"></script>
<script src="/js/jquery.idTabs.min.js" type="text/javascript" ></script>
<script type="text/javascript" src="/js/zDialog/risenetDialog.js"></script> 
<script charset="utf-8" src="/js/kindeditor/zh_CN.js"></script>
<script charset="utf-8" src="/js/validation.js"></script>
<script type="text/javascript">
//编辑器		
			var editor;
			KindEditor.ready(function(K) {
				editor = K.create('#affirms',{
					allowFileManager : false,
					allowImageUpload : false
				});
				editor.html('<s:property value="applicationBean.projectdes" escape="false"/>');
			});
			function getEditorValue(){
				return editor.html();
			}
			function isValueNull(){
				return editor.isEmpty()
			}
//tree
	$(document).ready(function(){
		$("a.tab").click(function () {
			var content_show = $(this).attr("title");
			if(content_show=="content_3"){
				if(!validations("content_2")) {
					return false;
				}else{
					tabSwich(this);
				}
			}else if(content_show=="content_4"){
				if(!validations("content_3")) {
					return false;
				}else{
					loadUpFilePage();
					tabSwich(this);
				}
			}else{
				tabSwich(this);
			}
			expAllNode();
		});
	  });
	 function tabSwich(obj){
	 	$(".active").removeClass("active");
		$(obj).addClass("active");
		var content_show = $(obj).attr("title");
		$(".content").slideUp();
		$("#"+content_show).slideDown();
	 }
	 function doSubmit(type){
	 	$("#pressor_state").attr("value",type);
	 	$("#projectdes").attr("value",getEditorValue());
	 	$("#queryFormId").submit();
	 }
</script>
</head>
<body>
<s:form action="/civilApply/saveApply.YS" method="post" id="queryFormId">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_leftop">
<tr><td class="top3"> </td> </tr>
<tr><td>
<div id="tabbed_box_1" class="tabbed_box">

    <div class="tabbed_area" id="contentDiv">
        <ul class="tabs" id="cardTagsId">
            <li><a href="#" title="content_1" class="tab active">基础信息</a></li>
            <li><a href="#" title="content_2" class="tab">扶持项目类型</a></li>
            <li><a href="#" title="content_3" class="tab">在线申报</a></li>
            <li><a href="#" title="content_4" class="tab">附件上传</a></li>
        </ul>
        <div id="content_1" class="content">
        
        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_leftop">
  <tr>
        <td class="td_left"><h5 class="setpTitle">基础信息</h5></td>
      </tr>
      <tr><td >
      	<jsp:include page="/WEB-INF/page/user/bizOrganizational.jsp"></jsp:include>
      </td></tr>
  </table>
        </div>
        <div id="content_2" class="content">
        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="table_leftop">
  <tr>
        <td colspan="2" class="td_left"><h5 class="setpTitle">选择扶持项目类型</h5></td>
      </tr>
 	<TR>
			<TD colspan="2" class="td_left" >
			扶持项目类型名称：<s:textfield  name="applicationBean.xmlxname" id="xmlxname"  cssClass="zc_input02"  verify="扶持项目类型名称|NotNull" readonly="true" size="100"></s:textfield><span style="color:red">*</span>
			</TD>
		</TR>
      <tr><td width="50%" class="td_left" valign="top">
      	<ul id="applyTypeTree" class="ztree"></ul>&nbsp;
      </td>
      <td width="50%" class="td_left" valign="top">
      	<div id="projectInfo" style="overflow: scroll;height:400px;scrollbar-face-color:#E0F2FF;">
      		
      	</div>
      </td>
      </tr>
  </table>
        </div>
        
         <div id="content_3" class="content" style="overflow: scroll;height:480px;scrollbar-face-color:#E0F2FF;">
         <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="table_leftop">
	<tr>
        <td colspan="2" class="td_left">
        <h5 class="setpTitle">在线申报</h5></td>
      </tr>
      <s:hidden name="applicationBean.xmlxguid" id="xmlxguid"></s:hidden>
			<s:hidden name="applicationBean.fctype"></s:hidden>
			<s:hidden name="applicationBean.pressor_state" id="pressor_state" value="1"></s:hidden>
			<s:hidden name="applicationBean.xmguid" id="xmguid"></s:hidden>
			<s:hidden name="applicationBean.projectdes" id="projectdes"></s:hidden>
			<s:hidden name="applicationBean.slbh" id="slbh"></s:hidden>
		<TR>
			<TD width="20%" class="title_center" >
			申请资助项目名称：</TD>
			<TD width="80%" class="td_left">
			<s:textfield  name="applicationBean.xmname" id="xmname" size="70" cssClass="zc_input02" verify="申请资助项目名称|NotNull" maxlength="500"></s:textfield><span style="color:red">*</span> </TD>
		</TR>
		<TR>
			<TD width="20%" class="title_center" nowrap="nowrap">项目负责人：</TD>
			<TD width="80%" class="td_left">
				<s:textfield  name="applicationBean.fzr" id="fzr" size="70" cssClass="zc_input02" verify="项目负责人|NotNull" maxlength="20"></s:textfield><span style="color:red">*</span>
			</TD>
		</TR>
		<TR>
				<TD width="20%" class="title_center" nowrap="nowrap">联系电话：</TD>
			<TD width="80%" class="td_left"   >
				<s:textfield  name="applicationBean.phone" id="phone" size="70" cssClass="zc_input02" verify="联系电话|NotNull|telephone" maxlength="20"></s:textfield><span style="color:red">*</span>
			</TD>
		</TR>
		<TR>
				<TD width="20%" class="title_center" nowrap="nowrap">联系手机：</TD>
			<TD width="80%" class="td_left"   >
				<s:textfield  name="applicationBean.contactmobile" id="contactmobile" size="70" onkeyup="restrictNumber(this,'1',11)" cssClass="zc_input02" verify="联系手机|NotNull|phone" maxlength="11"></s:textfield><span style="color:red">*</span>
			</TD>
		</TR>
		<TR>
				<TD width="20%" class="title_center" nowrap="nowrap">联系地址：</TD>
			<TD width="80%" class="td_left"  >
				<s:textfield  name="applicationBean.address" id="address" size="70" cssClass="zc_input02" verify="联系地址|NotNull" maxlength="500"></s:textfield><span style="color:red">*</span>
			</TD>
		</TR>
		<TR>
				<TD width="20%" class="title_center" nowrap="nowrap">扶持项目类型：</TD>
			<TD width="80%" class="td_left"  id="xmlxnameTD" >
				<s:property value="applicationBean.xmlxname"/>
			</TD>
		</TR>
		<TR>
			<TD width="20%" class="title_center" nowrap="nowrap">申请资助总额：</TD>
			<TD width="80%" class="td_left">
			<s:textfield  name="applicationBean.fcmoney" onkeyup="restrictNumber(this,'2',2)" id="sqje" cssClass="zc_input02" verify="申请资助总额|NotNull" maxlength="10"></s:textfield>万元<span style="color:red">*</span> </TD>
		</TR>
		<TR>
			<TD width="20%" class="title_center" nowrap="nowrap">项目基本情况简介：</TD>
			<TD width="80%" class="td_left">
			<s:textarea  id="affirms" cssStyle="width:750px;height:240px;visibility:hidden;" verify="项目基本情况简介|NotNull" ></s:textarea><span style="color:red">*</span>
		</TR>
		</table>
        </div>
        
         <div id="content_4" class="content" style="overflow: scroll;height:480px;scrollbar-face-color:#E0F2FF;">
         <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="table_leftop">
         <tr><td style="text-align:center"> 
       <input type="button" onclick="doSubmit('1')" class="buttonClass" value=" 暂  存 ">&nbsp;&nbsp;
       <input type="button" onclick="doSubmit('2')" class="buttonClass" value=" 提  交 ">&nbsp;&nbsp;
       <input type="button" onclick="closeWin()" class="buttonClass" value=" 关  闭 ">&nbsp;&nbsp;
      </td></tr>
		 <tr>
        <td class="td_left"><h5 class="setpTitle">附件上传</h5></td>
      </tr>
		<tr><td class="td_left" id="fileUploadPage">
      </td></tr>
     
		</table>
        </div>
     </div>
 </div>   

</td> </tr>
</table>
</s:form>
</body>
</html>
