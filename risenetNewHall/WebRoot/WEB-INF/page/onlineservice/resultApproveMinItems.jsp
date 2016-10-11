<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<head>
	<style type="text/css">
		.one{
			background:#FFFACD;
		}
		.two{
			background:#FFEFDB;
		}
	</style>
</head>
<html>
  <body>
  	<table class="BS_list" cellpadding="0" cellspacing="0" style="border:none;width:100%;height:100%;border-collapse:collapse;border-color:silver;">
	<s:iterator value="minApproveItem" status="entity">
		<s:if test="(#entity.Index+1)%2==0"><tr class="two"></s:if>
		<s:else><tr class="one"></s:else>
		
			<s:if test="#entity.Index==0">
			<td width="60%" style="border-top:0;border-right:1;border-right-color:silver;">
			</s:if>
			<s:else>
			<td width="60%" style="border-right:1;border-right-color:silver;">
			</s:else>
			&nbsp;&nbsp;&nbsp;&nbsp;
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
			
			<s:if test="#entity.Index==0">
			<td style="border-top:0px;border-left-width:1px;border-left-style:solid;border-left-color:silver;">
			</s:if>
			<s:else>
			<td style="border-left-width:1px;border-left-style:solid;border-left-color:silver;">
			</s:else>
				<s:property value="bureauName"/>&nbsp;
			</td>
			
			<s:if test="#entity.Index==0">
			<td width="14%" style="border-right:0px;border-top:0px;border-left-width:1px;border-left-style:solid;border-left-color:silver;">
			</s:if>
			<s:else>
			<td width="14%" style="border-right:0px;border-left-width:1px;border-left-style:solid;border-left-color:silver;">
			</s:else>
				
				<!--  
				<s:if test="#session.loginUser!=null">
				<s:if test="approveplace==1&&applicationformurl==null&&((approveobject.substring(0,1)==1&&#session.loginUser.usertype==1)||(approveobject.substring(1,2)==1&&#session.loginUser.usertype==2)||#session.loginUser.usertype==3)">
					<a target="_blank" href="/onlineService/initOnlineApply/approveItemGUID/<s:property value="itemid"/>.html" class="BS_btnblue" >在线申办</a>
				</s:if>
					<s:elseif test="approveplace==1&&applicationformurl!=null&&((approveobject.substring(0,1)==1&&#session.loginUser.usertype==1)||(approveobject.substring(1,2)==1&&#session.loginUser.usertype==2)||#session.loginUser.usertype==3)">
						<a target="_blank" href="<s:property value="applicationformurl"/>" class="BS_btnblue" >在线申办</a>
					</s:elseif>
					</s:if>
					<s:else>
						<s:if test="approveplace==1&&applicationformurl==null">
							<a target="_blank" href="/onlineService/initOnlineApply/approveItemGUID/<s:property value="itemid"/>.html" class="BS_btnblue" >在线申办</a>
					</s:if>
					<s:elseif test="approveplace==1&&applicationformurl!=null&&applicationformurl!='暂未开始'">
						<a target="_blank" href="<s:property value="applicationformurl"/>" class="BS_btnblue" >在线申办</a>
					</s:elseif>
					<s:elseif test="approveplace==1&&applicationformurl!=null">
						暂未开始
					</s:elseif>
					<s:else>
						&nbsp;
					</s:else>
				</s:else>
				-->
				<s:if test="approveplace==1&&applicationformurl==null">
				<!-- 
				<a target="_blank" href="/onlineService/initOnlineApply/approveItemGUID/<s:property value="itemid"/>.html" class="BS_btnblue" >在线申办</a>
				 -->
					
					<a href="javascript:void(0)" class="BS_btnblue" 
					 	onclick="checkUserType('<s:property value="itemid"/>','/onlineService/initOnlineApply/approveItemGUID/<s:property value="itemid"/>.html')">在线申办</a>
				</s:if>
				<s:elseif test="approveplace==1&&applicationformurl!=null">
					<!-- 
					<a target="_blank" href="<s:property value="applicationformurl"/>" class="BS_btnblue" >在线申办</a>
					 -->
					<a href="javascript:void(0)" class="BS_btnblue" 
					    onclick="checkUserType('<s:property value="itemid"/>','<s:property value="applicationformurl"/>');" >在线申办</a>
				</s:elseif>
				<s:else>
					&nbsp;
				</s:else>
			</td>
			</tr>
       	</s:iterator>
       	</table>
	</body>
</html>