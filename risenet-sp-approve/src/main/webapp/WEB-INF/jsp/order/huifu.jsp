<!--
	/**
 * @ FileName: ordersOnline.jsp
 * @Description: 窗口人员“预约回复”弹窗
 * @author chenbingni
 * @date 2015-12-24
 */
-->

<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<%@ include file="/static/common/util.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>网上预约</title>

<!-- 表单异步提交start -->

<script src="${ctx}/static/QUI/libs/js/form/form.js" type="text/javascript"></script>

<!-- 表单异步提交end -->

<script type="text/javascript">


	 
	 function tijiao() {
			$.post('${ctx}/orderOnline/saveHuifu',$("#huifuForm").serialize(),function(data){
				if(data.msg) {
					alert('保存成功！');					
					window.parent.location.reload();
				}else {
					alert('保存失败！');
				}
			});			 
	}
</script>

</head>
<body>
	<c:if test="${ispass==0 }">
		<form id="huifuForm" >
			<input type="hidden" name="orderGuid" id="orderGuid" value="${orderGuid }"></input>
			<table width="100%">
				<tr><td><label for="hfnr">回复内容：</label></td></tr>
				<tr><td><textarea type="text" name="hfnr" id="hfnr" maxNum="1000"></textarea></td></tr>
				<tr>
					<td align="center"><button type="button" onclick="tijiao();"><span class="icon_save">保存</span></button></td>
				</tr>
			</table>
		</form>
	</c:if>
	<c:if test="${ispass!=0 }">
	<table>
		<tr>
			<td>回复内容：</td>
		</tr>
		<tr>
			<td>
			<textarea type="text" name="hfnr" id="hfnr" autoHeight="true">${hfnr }</textarea></td>
		</tr>
		<tr>
			<td width="20%"></td>			
		</tr>
		<tr><td><span>${hftime }</span></td></tr>
		<tr>
			<td>
				<span>${hfry }</span>
			</td>
		</tr>
		<tr>
	</table>
    </c:if>
</body>
</html>