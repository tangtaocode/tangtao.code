<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../../share/taglib.jsp"%>
<html>
<head>

<title>深圳市龙岗区住房与建设局产业转型升级专项资金网上申报</title>
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
		$.post('/bizApply/initFileUpload.YS',
			{'typeGUID':$("#category_id").val(),'guid':$("#guid").val()},
			function(data){
				$("#fileUploadPage").html(data);
		});
	 });
</script>
<body>
<s:form action="/bizApply/viewApply.html" method="post" id="queryFormId">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_leftop">
<tr><td class="top"> </td> </tr>
<tr><td>
<div id="tabbed_box_1" class="tabbed_box">
    <div class="tabbed_area" id="contentDiv">
        <ul class="tabs" id="cardTagsId">
            <li><a href="#" title="content_1" class="tab active">基础信息</a></li>
            <li><a href="#" title="content_2" class="tab">申报信息</a></li>
            <li><a href="#" title="content_3" class="tab">附件信息</a></li>
        </ul>
        <div id="content_1" class="content">
        
        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_leftop">
  <tr>
        <td class="td_left"><h5 class="setpTitle">基础信息</h5></td>
      </tr>
      <tr><td >
      	<s:if test="#session.loginUser.usertype==1">
       	<jsp:include page="/WEB-INF/page/user/BizPersonal.jsp"></jsp:include>
       	</s:if>
       	<s:if test="#session.loginUser.usertype==2">
       	<jsp:include page="/WEB-INF/page/user/bizCompanyInfo.jsp"></jsp:include>
       	</s:if>
       	<s:if test="#session.loginUser.usertype==3">
       	<jsp:include page="/WEB-INF/page/user/bizOrganizational.jsp"></jsp:include>
       	</s:if>
      </td></tr>
  </table>
        </div>
         <div id="content_2" class="content">
         <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="table_leftop">
	<tr>
        <td colspan="2" class="td_left"><h5 class="setpTitle">在线申报</h5></td>
      </tr>
		<TR>
			<TD width="20%" class="title_center" nowrap="nowrap">
			<s:hidden name="applicationBean.category_id" id="category_id"></s:hidden>
			<s:hidden name="applicationBean.guid" id="guid"></s:hidden>
			申请资助类别：</TD>
			<TD width="80%" class="td_left"  id="categoryTD" >
				<s:property value="applicationBean.category"/>
			</TD>
		</TR>
		<TR>
			<TD width="20%" class="title_center" nowrap="nowrap">申请资助依据条款：</TD>
			<TD width="80%" class="td_left" id="provisionTD">
				<s:property value="applicationBean.according"/>
			</TD>
		</TR>
		<TR>
			<TD width="20%" class="title_center" nowrap="nowrap">申请资助项目名称：</TD>
			<TD width="80%" class="td_left">
			<s:property value="applicationBean.pro_name"/>
		</TR>
		<TR>
			<TD width="20%" class="title_center" nowrap="nowrap">申请资助项总额：</TD>
			<TD width="80%" class="td_left">
			<s:property value="applicationBean.sqje"/>&nbsp;万元 </TD>
		</TR>
		<TR>
			<TD width="20%" class="title_center" nowrap="nowrap">项目基本情况简介：</TD>
			<TD width="80%" class="td_left">
			<s:property value="applicationBean.enterprises_affirms" escape="false"/>
		</TR>
		</table>
        </div>
         <div id="content_3" class="content">
         <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="table_leftop">
		 <tr>
        <td class="td_left"><h5 class="setpTitle">附件上传</h5></td>
      </tr>
       <tr><td style="text-align:center"> 
       <input type="button" onclick="ownerDialog.close();" class="buttonClass" value=" 关  闭 ">&nbsp;&nbsp;
      </td></tr>
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
