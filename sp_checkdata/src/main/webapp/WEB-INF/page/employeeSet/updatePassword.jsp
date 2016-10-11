<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>修改密码</title>
    <script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
   <!--  <link rel="stylesheet" href="../../css/psdEdit.css" type="text/css" /> -->
   <link href="/css/mstyle.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
     <div class="grsz_mm" > 
	      <div class="grsz_title"><span>&nbsp;&nbsp;密码修改</span></div>
		  <div class="grsz_text">
		    <table width="90%" border="0" align="center">

			  <tr>
                <td align="right">旧密码：</td>
                <td><input type="password" id="oldpwd" /></td>
              </tr>
              <tr>
                <td align="right" >新密码：</td>
                <td><input  type="password" id="newpwd" /></td>
              </tr>
              <tr>
                <td align="right">确认密码：</td>
                <td><input type="password" id="relpwd" /></td>
              </tr>
            </table>
		  </div>
		  <div class="grsz_bt"><a href="javascript:void(0)" id="sumb" class="grsz_bt1">确认</a><a href="javascript:void(0)" id="res" class="grsz_bt2">取消</a></div>
	  </div>
<script type="text/javascript">
	$(document).ready(function(){
		$("#sumb").bind("click",function(){
			var relpwd=$("#relpwd").val();
			var newpwd=$("#newpwd").val();
			var oldpwd=$("#oldpwd").val();
			if(oldpwd==''){
				alert("请输入旧密码!");
				return;
			}
			if(newpwd==''){
				alert("请输入新密码!");
				return;
			}
			if(!checkRules()){
				alert('字符长度至少9位，应由大写字母，小写字母，数字，特殊字符等组成!');
				return;
			}
			if(newpwd.length<6){
				alert("请输入9位数以上新密码!");
				return;
			}
			if(relpwd==''){
				alert("请输入确认密码!");
				return;
			}
			if(relpwd!=newpwd){
				alert("确认密码和新密码不一样！");
				return;
			}
           $.post("/person/updatePassword",{'oldpwd':oldpwd,'newpwd':newpwd},function(msg){
        	   var mess = eval('('+msg+')');
           		var flag=$.trim(mess.flag);
           		if(flag=='true'){
           			alert("密码修改成功！");
           			window.location.href='/oneHome/Logout';
           		}else{
           			alert("输入的旧密码错误,请重新输入！");
           			$("#oldpwd").val("");
           			return;
           		}
           });
		});
		$("#res").bind("click",function(){
			$("#relpwd").val("");
			$("#newpwd").val("");
			$("#oldpwd").val("");
		});
		
		$('#newpwd').on('click', function(){
		    layer.tips('字符长度至少9位，应由大写字母，小写字母，数字，特殊字符等组成!', this, {
		        guide: 1,
		        time: 5,
		        isGuide: true,
		        style: ['background-color:#15A1F0; color:#fff', '#15A1F0'],
		        maxWidth:240
		    });
		});	
	});
	//显示修改密码规则
function checkRules(){
	var newpwd=$("#newpwd").val();
	var strongRegex = new RegExp("^(?=.{9,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$", "g");
	if(!strongRegex.test(newpwd)){  
		return false;
	}else{
		return true;
	}
}
</script>
  </body>
</html>
