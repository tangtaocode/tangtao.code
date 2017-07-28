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


  function save1(){ 
	  var scoreguid=$("#scoreguid").val();
	  var time=$("#time").val();
	  var isname=$("#isname").val();
	  var userGuid=$("#userguid").val();
		if($("#nwzjqk").val()>10 || $("#zztfx").val()>10|| $("#yxltksj").val()>10|| $("#pyq").val()>10|| $("#cdzt").val()>10|| $("#lmwmyy").val()>10|| $("#mdgp").val()>10){
			alert("只能输入1-10的数字");
			return false;
		}
		$.ajax({
	         type: "POST",
	         dataType: "json",
	         data:{userGuid:userGuid,time:time,isname:isname},
	         url: "${ctx}/dailymanagement/findis",
	         success: function(data){
	        	if(data.is){
	        		alert(data.message);
	        	}else{
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
					<td >日期</td><td><input id="time" name="time" type="text" value="${score.time }"  style="width: 100px"  ></td>
			</tr> 
			<tr>
				<td   align="left">备注</td>
				<td colspan="3"><textarea rows="5" style="width: 100%" name="remarks"  maxNum="150">${score.remarks }</textarea></td>
				
			</tr>
			<tr>
				<td colspan="4" align="center">
					<button  id="test1" type="button" onclick="save1();">保存</button>
					<input type="button" value="取消" onclick="parent.Dialog.close()"/>
					<input  type="hidden" id="scoreguid" name="scoreguid" value="${score.scoreguid}"/>
					<input  type="hidden" id="userguid" name="userguid" value="${score.userguid }"/>
					<input  type="hidden" name="enrollnumber" value="${score.enrollnumber }"/>
			 		<input  type="hidden" name="name" value="${score.name }"/>
			 		<input  type="hidden" id="isname" name="isname" value="${name }"/>
				</td>
			</tr>
	</table>


</form>
</div>
</body>
</html>