<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>

  <script type="text/javascript">
  function changeType(){
  	  changeOrder('orderCode','app');
  	  searchApp($("#approveType").val(),$("#isOnlineApply").val());
  }
  function topage(page){
	  if("0"=="<s:property value="isOnlineApply"/>"){
	  	searchApp($("#approveType").val(),'0',page);
	  }else{
		searchApp($("#approveType").val(),$("#isOnlineApply").val(),page);
	  }
  }
  
/**
 * 查询、打开大类包含的小类
 */
  $(function(){
  	$(".itemShow").click(function(){
  		var isOnlineApply = $("#isOnlineApply").val();
	  	var approveName;
  		if('0'==isOnlineApply){
	  		approveName = $("#wsapproveName").val();
  		}else{
  			approveName = $("#approveName").val();
  		}
  		var $thisTR = $(this).parent().parent();
  		if($thisTR.next().attr("id")=="none"){
	  		$.post("/approve/approveQueryResultX.html",
	  		{"approveItemGUID":$(this).attr("id"),"isOnlineApply":"<s:property value="isOnlineApply"/>","approveName":approveName},
	  		function(data){
	  			$thisTR.next().children().html(data);
	  			$thisTR.next().attr("id","many")
	  		}
	  		);
  		}
	  	$(this).children().toggle(function(){});
  		$thisTR.next().toggle(function(){});
  	});
  });
  </script>
  <style type="text/css">
		.one{
			background:#FFFACD;
		}
		.two{
			background:#FFEFDB;
		}
	</style>
  </head>
  
  <body>
  <s:hidden name="page" id="pageCount"></s:hidden>
  <s:hidden name="orderType" id="orderType"></s:hidden>
  <s:hidden name="ascOrDesc" id="ascOrDesc"></s:hidden>
  <s:hidden name="approveType" id="approveType"></s:hidden>
  <s:hidden name="isOnlineApply" id="isOnlineApply"></s:hidden>
	
  <div class="BS_domiddlein">
			<!--depmiddle start-->
			<div class="BS_depm">
				<s:if test="''!=isOnlineApply&&0==isOnlineApply">
					<div class="searchAppInfo">共有<s:property value="#request.allMinCount"/>个可网上申报的服务事项</div>
				</s:if>
				<s:else>
					<div class="searchAppInfo">共有<s:property value="#request.allMinCount"/>个服务事项</div>
				</s:else>
				<div class="searchAppPara">
					部门：<s:select cssClass="loginInput" list="departMap" listKey="code" listValue="value" onchange="changeType();" name="departGUID" id="departGUID"  headerKey="" headerValue="---请选择---" value="departGUID"></s:select> 
					&nbsp;&nbsp;
					<!-- 可在线申报：<s:select cssClass="loginInput" list="#{'':'---请选择---','0':'可在线申报','1':'不可在线申报'}" onchange="changeType()" name="isOnlineApply" id="isOnlineApply"></s:select> -->
				</div>
				<div class="clean"></div>
				<!--搜索结果 start-->
					<table cellpadding="0" cellspacing="0" border="0" width="100%"
						class="BS_list" style="line-height:100%">
						<tr>
							<th width="60%">
								<a href="javascript:changeOrder('itemName','app');"><font style="font-weight:bold;">服务事项名称</font></a>
								<s:if test="orderType=='itemName'">
							<s:if test="ascOrDesc=='asc'">
								<img src="/images/govpublic/orderUp.png" class="order"/>
							</s:if>
							<s:if test="ascOrDesc=='desc'">
								<img src="/images/govpublic/orderDown.png" class="order"/>
							</s:if>
							</s:if>
							</th>
							<th>
								<a href="javascript:changeOrder('departName','app');"><font style="font-weight:bold;">主管部门</font></a>
								<s:if test="orderType=='departName'">
							<s:if test="ascOrDesc=='asc'">
								<img src="/images/govpublic/orderUp.png" class="order"/>
							</s:if>
							<s:if test="ascOrDesc=='desc'">
								<img src="/images/govpublic/orderDown.png" class="order"/>
							</s:if>
							</s:if>
							</th>
							
							<th width="14%">
								网上申报
							</th>
						</tr>
				<s:if test="#request.pageView.totalRecord">
         			<s:iterator value="#request.pageView.records" status="entity">
         			<s:if test="(#entity.Index+1)%2==0">
	         			<s:if test="type!=1">
	         				<tr class="one">
	         			</s:if>
	         			<s:else>
		         			<tr class="odd">
	         			</s:else>
         			</s:if>
         			<s:else>
         				<s:if test="type!=1">
	         				<tr class="two">
	         			</s:if>
	         			<s:else>
         					<tr class="even">
         				</s:else>
         			</s:else>
							<td>
								<s:if test="type==1">
									<a href="javascript:void(0);" title="<s:property value="approveitemname"/>" class="itemShow" id="<s:property value="itemid"/>">
										<img src="/images/home/tree/wjjg.png" id="wjjgPicture"/>
										<img src="/images/home/tree/wjjk.png" class="hiddenDiv" id="wjjkPicture"/>
									<s:property value="approveitemname"/></a>
								</s:if>
								<s:else>
									<a href="/approve/findAppGuideInfo/approveItemGUID/<s:property value="itemid"/>.html" target="_blank" title="<s:property value="approveitemname"/>">
										<img src="/images/home/tree/wj.png"/>
									<s:property value="approveitemname"/></a>
								</s:else>
								</td>
							<td>
								<s:property value="bureauName"/>&nbsp;
							</td>
							<td>
								<s:if test="approveplace==1&&applicationformurl==null">
								<!-- 
									<a target="_blank" href="/onlineService/initOnlineApply/approveItemGUID/<s:property value="itemid"/>.html" class="BS_btnblue" >在线申办</a>
								 -->
									<a href="javascript:void(0)"   class="BS_btnblue" 
									   onclick="checkUserType('<s:property value="itemid"/>','/onlineService/initOnlineApply/approveItemGUID/<s:property value="itemid"/>.html')">在线申办</a>
								</s:if>
								<s:elseif test="approveplace==1&&applicationformurl!=null">
								<!-- 
									<a target="_blank" href="<s:property value="applicationformurl"/>" class="BS_btnblue" >在线申办</a>
								 -->
									<a href="javascript:void(0)" target="_blank" class="BS_btnblue" 
										onclick="checkUserType('<s:property value="itemid"/>','<s:property value="applicationformurl"/>');" >在线申办</a>
								</s:elseif>
								<s:else>
									&nbsp;
								</s:else>
							</td>
							<tr class="hiddenDiv" id="none">
								<td colspan="3" style="padding:0px 0px 0px 0px;marggin:0px 0px 0px 0px;"></td>
							</tr>
						</tr>
          			</s:iterator>
				</s:if>
				<s:else>
					<tr>
         				<td colspan="3" class="noDateText">无记录！</td></tr>
				</s:else>
				<tr>
	  				<td align="center" colspan="5" class="pageLinkTd">
						<%@ include file="/WEB-INF/page/share/page.jsp"%>
					</td>
				</tr>		
					</table>
				<div class="clean"></div>
			</div>
			<!--depmiddle end-->
		</div>
		<!--middlein end-->
  </body>
</html>
