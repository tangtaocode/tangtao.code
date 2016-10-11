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
 <link href="/css/tabIdCard.css" rel="stylesheet" type="text/css"/>
 <script src="/js/Scripts/jquery.min.js" type="text/javascript"></script>
<script src="/js/jquery.idTabs.min.js" type="text/javascript" ></script>
<script type="text/javascript">
 $(document).ready(function(){
		$("a.tab").click(function () {
			$(".active").removeClass("active");
			$(this).addClass("active");
			$(".content").slideUp();
			var content_show = $(this).attr("title");
			$("#"+content_show).slideDown();
		});
		$.post('/civilApply/initFileUpload.YS',
			{'typeGUID':$("#xmlxguid").val(),'guid':$("#xmguid").val()},
			function(data){
				$("#fileUploadPage").html(data);
		});
	 });
</script>
</head>
<body>
<s:form action="/civilApply/viewApply.YS" method="post" id="queryFormId">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_leftop">
<tr><td class="top3"> </td> </tr>
<tr><td>
<div id="tabbed_box_1" class="tabbed_box">

    <div class="tabbed_area" id="contentDiv">
        <ul class="tabs" id="cardTagsId">
            <li><a href="#" title="content_1" class="tab active">基础信息</a></li>
            <li><a href="#" title="content_2" class="tab">在线申报</a></li>
            <li><a href="#" title="content_3" class="tab">附件上传</a></li>
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
         <div id="content_2" class="content" style="overflow: scroll;height:480px;scrollbar-face-color:#E0F2FF;">
         <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="table_leftop">
	<tr>
        <td colspan="2" class="td_left">
        <h5 class="setpTitle">在线申报</h5></td>
      </tr>
      <s:hidden name="applicationBean.xmlxguid" id="xmlxguid"></s:hidden>
			<s:hidden name="applicationBean.xmguid" id="xmguid"></s:hidden>
		<TR>
			<TD width="20%" class="title_center" >
			申请资助项目名称：</TD>
			<TD width="80%" class="td_left">
			<s:property value="applicationBean.xmname"/>
			</TD>
		</TR>
		<TR>
			<TD width="20%" class="title_center" nowrap="nowrap">项目负责人：</TD>
			<TD width="80%" class="td_left">
			<s:property value="applicationBean.fzr"/>
			</TD>
		</TR>
		<TR>
				<TD width="20%" class="title_center" nowrap="nowrap">联系电话：</TD>
			<TD width="80%" class="td_left"   >
			<s:property value="applicationBean.phone"/>
			</TD>
		</TR>
		<TR>
				<TD width="20%" class="title_center" nowrap="nowrap">联系手机：</TD>
			<TD width="80%" class="td_left"   >
			<s:property value="applicationBean.contactmobile"/>
			</TD>
		</TR>
		<TR>
				<TD width="20%" class="title_center" nowrap="nowrap">联系地址：</TD>
			<TD width="80%" class="td_left"  >
			<s:property value="applicationBean.address"/>
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
			<s:property value="applicationBean.fcmoney"/>
			</TD>
		</TR>
		<TR>
			<TD width="20%" class="title_center" nowrap="nowrap">项目基本情况简介：</TD>
			<TD width="80%" class="td_left">
			<s:property value="applicationBean.projectdes"  escape="false"/>
			</TD>
		</TR>
		</table>
        </div>
        
         <div id="content_3" class="content" style="overflow: scroll;height:480px;scrollbar-face-color:#E0F2FF;">
         <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="table_leftop">
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
