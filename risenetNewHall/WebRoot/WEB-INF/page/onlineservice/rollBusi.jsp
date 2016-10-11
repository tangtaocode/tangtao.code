<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript">

</script>
</head>
	<body>
		<table cellpadding="0" cellspacing="0" border="0" width="99%"
			class="BS_listroll">
			<tr>
				<th width="16%" style="text-align: center">
					受理编号
				</th>
				<th width="18%" style="text-align: center">
					承办单位
				</th>
				<th style="text-align: center">
					事项名称
				</th>
				<th width="20%" style="text-align: center">
					申请时间
				</th>
				<th width="10%" style="text-align: center">
					状态
				</th>
			</tr>
			<tr><td colspan="5">
			<div id="scrollDiv" style="overflow: hidden;height:180px;width:100%">
					<div id="scrollText">
			<marquee behavior="scroll" onmouseover="this.stop();" onmouseout="this.start();" direction="up" scrollAmount="3" width="100%" height="180px">
			<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<s:iterator value="busiList" status="busin">
					<s:if test="(#busin.Index+1)%2==0">
						<tr class="odd">
					</s:if>
					<s:else>
						<tr class="even">
					</s:else>
					<td class="font_right_li_center02" width="16%" style="text-align: center">
						<s:if test="sblsh!=null&&sblsh.length()>0">
							<s:property value="sblsh" />
						</s:if>
						<s:if test="sblsh==null||sblsh.length()>0">
							<s:if test="%{yxtywlsh!=null&&yxtywlsh.length()>12}">
								<s:property value="%{yxtywlsh.substring(0,12)}" />...
	                  		</s:if>
							<s:else>
								<s:property value="yxtywlsh" />
							</s:else>
						</s:if>
					</td>
					<td class="font_right_li_left02" width="18%">
						<s:property value="sljgmc" />
					</td>
					
					<td class="font_right_li_left02">
						<s:if test="%{spsxmc!=null&&spsxmc.length()>12}">
							<s:property value="%{spsxmc.substring(0,12)}" />...
                  </s:if>
						<s:else>
							<s:property value="spsxmc" />
						</s:else>

					</td>
					<td class="font_right_li_left02" style="text-align:center" width="20%">
						<s:property value="slsj" />
					</td>
					<td class="font_right_li_center02" width="10%" style="border-right:none;">
						<s:property value="blzt" />
					</td>
				</s:iterator>
				</table>
				</marquee>
			</div>
					<div id="printDiv"></div>
				</div>
			
			</td></tr>
		</table>
	</body>
</html>
