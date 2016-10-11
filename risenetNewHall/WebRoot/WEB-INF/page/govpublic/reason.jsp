<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
<script type="text/javascript">
function topage(page){
	findLawByDepartGUID($("#departGUID").val(),page);
}
</script>
	</head>
	<body>
	<s:hidden name="departGUID" id="departGUID"></s:hidden>
		<table cellspacing="0" border="0" class="ListToolbarWebpart" style="showTopBorder =='false'?'border-top: none ':' '" width="100%">
						<tr>
						<td align="left" style="font-size: 12pt; color: #858585;padding-left: 16px;">
							共<s:property value="#request.pageView.totalRecord"/>个办事依据
						</td>
							<td valign="top" align="right" style="height:50px;text-align: right; padding-right: 16px;" >
							<div style=" width:100%" align="right">
								<div style="background-image: url(/images/govpublic/search_bar.png);BACKGROUND-REPEAT:no-repeat; height: 29px; width: 265px">
								<table cellpadding="6" cellspacing="0" style=" width:100%">
									<tr>
										<td align="right">
										<s:textfield id="lawName" name="lawName" cssStyle="width: 223px; color: #000; border: 0px solid #ccc"></s:textfield>
										</td>
										<td align="right">
										<a id="searchLink" href="javascript:findLawByDepartGUID('<s:property value="departGUID"/>');void(0)"><img src="/images/govpublic/tb_see_right.gif" style="border:0px;vertical-align: middle" /> </a>
										</td>
									</tr>
								</table>
								</div>
								</div>
							</td>
						</tr>
					</table>
					<div style="text-align:center;">
					<table class="rich-table Grid" border="0" cellpadding="0" cellspacing="0" width="100%" style="LINE-HEIGHT:25px;width:98%">
				<thead class="rich-table-thead">
					<tr class="rich-table-subheader Header">
						<th class="rich-table-subheadercell Header " scope="col" style="text-align:center;width:5%">
							<a href="#">序号</a></th>
						<th class="rich-table-subheadercell Header " scope="col">
							<a href="#">办事依据</a></th>
						<th class="rich-table-subheadercell Header " scope="col">
							<a href="#">更新时间</a></th>
					</tr>
				</thead>
				<tbody >
					<s:if test="#request.pageView.totalRecord">
         			<s:iterator value="#request.pageView.records" status="entity">
         			<s:if test="(#entity.Index+1)%2==0"><tr class="rich-table-row rich-table-firstrow OddRow"></s:if>
         			<s:else><tr class="rich-table-row  EvenRow"></s:else>
         			<td style="text-align:center"><s:property value="#entity.index+1"/> </td>
							<td>
							<s:if test="status==1">
								<a style="color:#6688CC" title="<s:property value="title"/>"
									href="/laws/lawsAction/id/<s:property value="id"/>.html"
									target="_blank">
									  <s:if test="%{title!=null&&title.length()>53}">
					                  <s:property value="%{title.substring(0,53)}"/>...
					                  </s:if>
					                  <s:else>
					                  <s:property value="title"/>
					                  </s:else>        
								</a>
							</s:if>
							<s:else>
								<a style="color:#6688CC" title="<s:property value="title"/>"
								href="/servlet/DownFileServlet/downType/lawFile/fileId/<s:property value="id"/>.html"
								target="_blank"><s:if test="%{title!=null&&title.length()>53}">
				                  <s:property value="%{title.substring(0,53)}"/>...
				                  </s:if>
				                  <s:else>
				                  <s:property value="title"/>
				                  </s:else>        
								</a>
							</s:else>
																</td>
							<td style="vertical-align:bottom;">
							<s:date name="createtime" format="yyyy-MM-dd" /> 
							</td>
						</tr>
          			</s:iterator>
				</s:if>
				<s:else>
					<tr>
         				<td colspan="3" class="noDateText">无记录！</td></tr>
				</s:else>
				<tr>
	  				<td align="center" colspan="3" class="pageLinkTd">
						<%@ include file="/WEB-INF/page/share/page.jsp"%>
					</td>
				</tr>	
					</tbody>
					</table>
					<div class="listBotton"></div>
					</div>
		
	</body>
</html>

