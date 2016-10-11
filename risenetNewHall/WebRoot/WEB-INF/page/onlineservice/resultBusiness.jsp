<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <script src="/js/businessJS/onlineService.js" type="text/javascript"></script>
  <script type="text/javascript">
		function topage(page){		
			searchBusi_stus('false',page);
			//if($("#yxtywlsh").val()==""||$("#yxtywlsh").val()==null){
			//	alert('jggsSearchBusi_stus');	
			//	jggsSearchBusi_stus('false',page);//结果公示查询
			//}else{alert('searchBusi_stus');
			//	searchBusi_stus('false',page);
			//}						
  		}
	</script>
  </head>
  
  <body>
  <s:hidden name="page" id="pageCount"></s:hidden>
  <s:hidden name="orderType" id="orderType"></s:hidden>
  <s:hidden name="ascOrDesc" id="ascOrDesc"></s:hidden>
  <div class="BS_domiddlein">
			<!--depmiddle start-->
			<div class="BS_depm">
			<div class="searchAppInfo">共查询到<s:property value="#request.pageView.totalRecord"/>笔业务</div>
				<div class="clean"></div>
				<!--搜索结果 start-->
				<div class="">
					<table cellpadding="0" cellspacing="0" border="0" width="100%"
						class="BS_list">
						<tr>
							<th width="15%" style="text-align:center" >受理编号</th>
                 <th width="20%" style="text-align: center">受理单位</th>
                 <th width="25%" style="text-align: center">事项名称</th>
                 <th width="15%" style="text-align: center">申请时间</th>
                 <th width="10%" style="text-align: center">状态</th>
						</tr>
						
				<s:if test="#request.pageView.totalRecord">
         			<s:iterator value="#request.pageView.records" status="entity">
         			<s:if test="(#entity.Index+1)%2==0"><tr class="odd"></s:if>
         			<s:else><tr class="even"></s:else>
         <td class="font_right_li_center02">
			<s:if test="sblsh!=null&&sblsh.length()>0">
				<s:property value="sblsh" />
			</s:if>
			<s:if test="sblsh==null||sblsh.length()<0">
				<s:if test="%{yxtywlsh!=null&&yxtywlsh.length()>12}">
					<s:property value="%{yxtywlsh.substring(0,12)}" />...
	            </s:if>
				<s:else>
					<s:property value="yxtywlsh" />
				</s:else>
			</s:if>
         </td>
         <td class="font_right_li_left02">
            <s:property value="sljgmc"/>        
         </td>
         <td class="font_right_li_left02">
         <s:if test="%{spsxmc!=null&&spsxmc.length()>12}">
                  <s:property value="%{spsxmc.substring(0,12)}"/>...
                  </s:if>
                  <s:else>
                  <s:property value="spsxmc"/>
                  </s:else>
         
         </td>
         
         <td class="font_right_li_center02"><s:property value="slsj"/></td>
         <td class="font_right_li_center02"><s:property value="blzt"/></td>
         </tr>
          			</s:iterator>
				</s:if>
				<s:else>
					<tr>
         				<td colspan="6" class="noDateText">无记录！</td></tr>
				</s:else>
				<tr>
	  				<td align="center" colspan="6" class="pageLinkTd">
						<%@ include file="/WEB-INF/page/share/page.jsp"%>
					</td>
				</tr>		
					</table>
				</div>
				
				<div class="clean"></div>
			</div>
			<!--depmiddle end-->
		</div>
		<!--middlein end-->
  </body>
</html>
