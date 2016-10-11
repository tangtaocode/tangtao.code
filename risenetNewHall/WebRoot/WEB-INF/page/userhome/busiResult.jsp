<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link href="/css/portal.css" type="text/css" rel="stylesheet" />
<script language="javascript" src="/commons/LodopFuncs.js" ></script>
<script type="text/javascript">
function topage(page){
	busiFlow(page);
  }
  
  /*删除提示-Jon*/
function buisDelete(approveguid,guid){
	var message = "删除之后，将无法恢复，你确定要删除？";
	showConfirm(message,function(){
		///onlineService/buisDelete/approveItemGUID/<s:property value="approveguid"/>/appInstanceGuid/<s:property value="guid"/>/state/1.html
		window.location.href="/onlineService/buisDelete/approveItemGUID/"+approveguid+"/appInstanceGuid/"+guid+"/state/1.html";
	});
}
</script>
<style type="text/css">
.Grid .EvenRow {
    BACKGROUND-COLOR: #f1f6fe
} 
.Grid .OddRow {
    BACKGROUND-COLOR: #ffffff
}
</style>
</head>
<body>
 <s:hidden name="page" id="pageCount"></s:hidden>
<table class="rich-table Grid" border="0" cellpadding="0" style="border-collapse:collapse;" cellspacing="0" style="LINE-HEIGHT:25px;width:100%">
				<thead class="rich-table-thead">
					<tr class="rich-table-subheader Header">
						<th class="rich-table-subheadercell Header " scope="col" width="15%">
							<a href="#">申请编号</a></th>
						<th class="rich-table-subheadercell Header " scope="col" width="25%">
							<a href="#">事项名称</a></th>
						<th class="rich-table-subheadercell Header " scope="col" width="25%">
							<a href="#">工程名称</a></th>
						<th class="rich-table-subheadercell Header " scope="col" width="10%">
							<a href="#">办理状态</a></th>
						<th class="rich-table-subheadercell Header " scope="col" width="12%">
							<a href="#">申请时间</a></th>
						<th class="rich-table-subheadercell Header " scope="col">
							<a href="#">操作</a></th>
					</tr>
				</thead>
				<tbody >
					<s:if test="#request.pageView.totalRecord">
         			<s:iterator value="#request.pageView.records" status="entity">
         			<s:if test="(#entity.Index+1)%2==0"><tr class="rich-table-row rich-table-firstrow OddRow"></s:if>
         			<s:else><tr class="rich-table-row  EvenRow"></s:else>
							<td><s:property value="declaresn"/>&nbsp;</td>
							<td><s:property value="formname"/></td>
							<td><s:property value="gcmc" default=""/></td>
							<td><s:property value="status" /></td>
							<td>
								<s:date name="submittime" format="yyyy-MM-dd"/>
							</td>
							<td>
								
								<s:if test="issubmit==0||issubmit==1||issubmit==4">
								<a href="/onlineService/initOnlineApply/approveItemGUID/<s:property value="approveguid"/>/appInstanceGuid/<s:property value="guid"/>/state/1.html">【修改】</a>
								</s:if> 
								<s:else>
									<a href="/onlineService/buisView/approveItemGUID/<s:property value="approveguid"/>/appInstanceGuid/<s:property value="guid"/>/state/1.html" target="_blank">【查看】</a>
										<!--<a href="/businessfollow/pringtHz/guid/<s:property value="guid"/>.html" target="_blank">【打印网上预受理回执】</a>
										-->
										<s:if test="issubmit==2||issubmit==3">
										<a href="javascript:PreviewMytable('/printBackView/printZbgg/sbguid/<s:property value="guid"/>.html');void(0)">【打印网上预受理回执】</a>
										</s:if>
								</s:else>
								<s:if test="hfstate==1&&(issubmit==0||issubmit==1||issubmit==2||issubmit==3)">
								<a href="javascript:showPage('网上申请回复查看','/businessfollow/findBusinessResponse/guid/<s:property value="guid"/>.html',700,500);void(0);">【回复】</a>
								
								</s:if>
								
								<s:if test="(hfstate==1&&issubmit==1)||issubmit==0">
									<!-- <a href="/onlineService/buisDelete/approveItemGUID/<s:property value="approveguid"/>/appInstanceGuid/<s:property value="guid"/>/state/1.html">【删除】</a> -->
									<a href="javascript:buisDelete('<s:property value="approveguid"/>','<s:property value="guid"/>')">【删除】</a>
								</s:if>
								
								<s:if test="hfstate==1&&approveguid=='{7F000001-0000-0000-7185-AA7900000004}'&&status=='转窗口收件'">
									<span style="display:block;">
									 <a href="javascript:showPage('需递交材料清单','/businessfollow/findBusinessCLResponse/guid/<s:property value="guid"/>.html',900,700);void(0);">【需递交材料】</a>
									</span>
								</s:if>
								
							</td>
						</tr>
          			</s:iterator>
				</s:if>
				<s:else>
					<tr>
         				<td colspan="4" class="noDateText">无记录！</td></tr>
				</s:else>
				<tr>
	  				<td align="center" colspan="5" class="pageLinkTd" style="border-left:0px">
						<%@ include file="/WEB-INF/page/share/page.jsp"%>
					</td>
				</tr>	
					</tbody>
					</table>
</body>
</html>
<script language="javascript" type="text/javascript">
var LODOP; //声明为全局变量
		function PreviewMytable(surl){
	    var styles='<LINK href="/css/main.css" type=text/css rel=stylesheet><LINK href="/css/printstyles.css" type=text/css rel=stylesheet><LINK href="/engine/system.css" type=text/css rel=stylesheet><LINK href="/engine/style.css" type=text/css rel=stylesheet><style type="text/css"> .box_table{display:none;} </style>';
		LODOP=getLodop(); 
		LODOP.PRINT_INIT("回执打印");
		var s = "<%=request.getScheme() %>";
		var sname = "<%=request.getServerName() %>";
		var port = "<%=request.getServerPort() %>";
		var purl = s+"://"+sname+":"+port+surl;
		LODOP.ADD_PRINT_URL(2,0,'100%','100%',purl);
		LODOP.PREVIEW();	
	}
</script>