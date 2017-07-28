<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tld/risefile-html.tld" prefix="risefilehtml" %>
<%@ include file="/static/common/util.jsp"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>基本表格模板</title>

<style type="text/css" media=print>
.noprint{display : none }
</style>
<script type="text/javascript">
function save(){
	var gid=$("#gid").val();
	$.post("${ctx}/scanning/saveData",{gid:gid},function(result){
		alert(result.info);
		if(result.info!=null&&result.info=="保存成功"){
			window.location.href="${ctx}/scanning/into";	
		}
		  history.go(-1);
 	  },"json");  
}

</script>
		


<title>证照扫描</title>
</head>
<body>

 <div  class="box1" panelWidth="100%" panelHeight="500"  overflow="true"  position="center">
    <div class="boxContent" >
		<form action="" id="queryForm" >
			<input  type="hidden" name="gid" id="gid" value="${id }"/>
			<input type="hidden" name="processDefinitionId" id="processDefinitionId" value="${id }"/>
            <table class="tableStyle" formMode="line">
		        <tr><th colspan="2">业务基本信息</th></tr>
		        <tr><td style="width: 40%;">业务编号：</td><td>${code}</td></tr>
		        <tr><td>事项名称：</td><td>${name}</td></tr>
		        <tr><td>申请单位/人：</td><td>${unit}</td></tr>
		        <tr><td>办结时间：</td><td>${time}</td></tr>
		        <tr><td>证照有效期：</td><td>${VALIDITYPERIOD}</td></tr>
		        <tr><td  bordercolorlight="#fff" colspan="2">
		        <table bordercolordark="#ffffff" frame="border" cellpadding="3"
		align="center" width="640" rules="all" border="1" cellspacing="0"
		bordercolorlight="#000000">
		<tbody>
			<tr>
				<td width="120"><font size="2" color="#000000">申请材料电子文档：</font>
				
				</td>
				<td width="400" colspan="3">
                    <risefilehtml:risefilebox
						appname="workflow" keepminimalversion="true" appinstguid="${instanceguid }" 
						fileboxname="TANGER_OCX" subappname="fujian" height="200"
						viewtype="1" savemode="true" isfilterextends="true"
						majorversion="6" width="400" ishandleextends="true">
					</risefilehtml:risefilebox>
					<script language="javascript">isArchManager = "true";showVersion("TANGER_OCX");	</script>
					</td>
				<td><font color="#ff0000">
						
							<center>注意事项:</center>
						
						<div>请将扫描的文件默认名重命名为正规文件名后上传附件以便查看!</div>
				</font></td>
			</tr>
		</tbody>
	</table>
	<div align="center" style="height:35px;"><input  type="button" onclick="save();" value="保存"/></div>
		        </td></tr>
		    </table><br>

			</form>
			</div>
	  </div>
  </body>
</html>