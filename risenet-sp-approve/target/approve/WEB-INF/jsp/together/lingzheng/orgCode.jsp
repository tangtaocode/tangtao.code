<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<%@ include file="/static/common/util.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>填写个人/机构代码</title>

	
</head>
<body>	
	<div>
		<form action="${ctx}/togetherWindow/newReceive" id="form" method="post" align="center">	
			<input type="text" name="approveitemguid" id="approveitemguid" value="${approveitemguid }">
			<input type="text" name="approveitemname" id="approveitemname" value="${approveitemname }">	
		<table>
			<tr>
				<td align="center"><font style="font-size:14px;">业务编号</font></td>			
			</tr>
			<tr>
				<td align="center">
				<input id="yxtywlsh" name="yxtywlsh"></input>&nbsp;
				<input type="button" id="cmd" value="确定" onclick="returnV();"></input></td>
			</tr>
		</table>
		</form>
	</div>	

<script type="text/javascript">

	function returnV(){
		var yxtywlsh = $("#yxtywlsh").val();
		var approveitemname = $("#approveitemname").val();
		var approveitemguid = $("#approveitemguid").val();
		var url;
		$.post("${ctx}/togetherWindow/scanOrLingqu?yxtywlsh="+yxtywlsh, {
		}, function(backdata) {
			if(backdata.code=="0") {//没找到该业务编号的业务信息
				Dialog.alert(backdata.msg);
			}else if(backdata.code=="3"){//证照扫描
				//Dialog.alert(backdata.msg);
				//this.parent.Dialog.close();
				url="${ctx}/togetherWindow/readyScan?approveitemname="+approveitemname+"&approveitemguid="+approveitemguid+"&yxtywlsh="+yxtywlsh;
			}else if(backdata.code=="5" || backdata.code=="6") {//领取登记
				Dialog.alert(backdata.msg);
				url="${ctx}/togetherWindow/readyLingqu?approveitemname="+approveitemname+"&approveitemguid="+approveitemguid+"&yxtywlsh="+yxtywlsh;	
			//this.parent.window.location.href="${ctx}/togetherWindow/newReceive?approveitemname="+approveitemname+"&approveitemguid="+approveitemguid+"&yxtywlsh="+yxtywlsh;
			}else {
				Dialog.alert('系统异常，请联系管理员！');
			}
		});
		Dialog.alert('before turn！');
		this.parent.window.location.href=url;
    	this.parent.Dialog.close();

    }
	    	
	    

</script>	
</body>
</html>
