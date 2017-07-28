<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ include file="/static/common/util.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>日考评表评分</title>
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/framework.js"></script>
</head>

<script type="text/javascript">
/* $(document).ready(function(){
	
	var sel='${score.tshby}';
	
	var opp=document.getElementById("yj");
	
	for(var i=0;i<opp.length;i++){
		alert(opp[i].value);
		if(opp[i].value==sel){
			$("select").val(opp[i].value);
		}
	}
}) */

  function save1(){ 
		if($("#nwzjqk").val()>10 || $("#zztfx").val()>10|| $("#yxltksj").val()>10|| $("#pyq").val()>10|| $("#cdzt").val()>10|| $("#lmwmyy").val()>10|| $("#mdgp").val()>10){
			alert("只能输入1-10的数字");
			return false;
		}
		 $("#form").ajaxSubmit({
			
				type : 'POST',
				dataType : 'json', 
				url : '${ctx}/dailymanagement/savepf',
				success : function(data) {
					//reloadGrid();
						alert(data.message,function(){window.location.reload()});
						parent.afterFormSubmit();
						parent.Dialog.close();  
				} 
			})
		}  
	 	  
	
	  	 
</script>

<body>
<div onload="getDate();" class="box1" id="formContent" whiteBg="true">
<form id="form" action=""   method="post">

<strong  >窗口人员<span style="color: blue">${score.name }</span>的日考评表评分</strong>	
	<table class="tableStyle"  formMode="transparent" border="0">
		
			
			<tr>
				<td>内务整洁情况</td>
				<td><input id="nwzjqk" name="nwzjqk" type="text" value="${score.nwzjqk }" inputMode="numberOnly"
				 style="width: 100px"/></td>
				<td>着装、头发、鞋</td>
				<td><input id="zztfx" name="zztfx" type="text" value="${score.zztfx }" style="width: 100px" inputMode="numberOnly"/></td>					
			</tr>
			<tr>				
				<td>游戏、聊天、看手机</td>
				<td><input id="yxltksj" name="yxltksj"  type="text" value="${score.yxltksj }" style="width: 100px" inputMode="numberOnly" /></td>
				<td>评议器</td>
				<td><input id="pyq" name="pyq" type="text" value="${score.pyq }" style="width: 100px" inputMode="numberOnly"/></td>
			</tr>
			<tr>
				<td>迟到、早退</td>
				<td><input id="cdzt"  name="cdzt" type="text" value="${score.cdzt }" style="width: 100px" inputMode="numberOnly"/></td>
				<td>礼貌文明用语</td>	
				<td><input id="lmwmyy" name="lmwmyy" type="text" value="${score.lmwmyy }" style="width: 100px" inputMode="numberOnly"/></td>	
			</tr>
			<tr>
				<td >没带工牌</td>
				<td><input id="mdgp" name="mdgp" type="text" value="${score.mdgp }" style="width: 100px" inputMode="numberOnly"/></td>
				
			</tr>
			 <tr>
				<td>是否投诉或表扬</td>
				
				<td><select name="tshby" id="yj">
							<option value="无" ${'无' eq score.tshby ?'selected':''}
							>无</option>
							<option value="投诉"  ${'投诉' eq score.tshby ?'selected':''}>投诉</option>
							<option value="表扬"  ${'表扬' eq score.tshby ?'selected':''}>表扬</option>
					</select>
				</td>
					<td >日期</td><td><input name="time" type="text" value="${score.time }"  style="width: 100px"  ></td>
			</tr> 
			<tr>
				<td   align="left">备注</td>
				<td colspan="3"><textarea rows="5" style="width: 100%" name="remarks"  maxNum="150">${score.remarks }</textarea></td>
				
			</tr>
			<tr>
				<td colspan="4" align="center">
					<button  id="test1" type="button" onclick="save1();">保存</button>
					<input type="button" value="取消" onclick="parent.Dialog.close()"/>
					<input  type="hidden" name="scoreguid" value="${score.scoreguid}"/>
					<input  type="hidden" name="userguid" value="${score.userguid }"/>
			 		<input  type="hidden" name="name" value="${score.name }"/>
				</td>
			</tr>
	</table>


</form>
</div>
</body>
</html>