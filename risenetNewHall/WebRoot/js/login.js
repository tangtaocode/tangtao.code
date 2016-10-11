
/**
 * 刷新验证码
 */
function changeCode(){
	$("#checkCodeImg").attr("src","/servlet/CheckCodeServlet?"+Math.random());
}
/**
 * 登录
 */
function doLogin(){
	if(!checkData()) return false;	
		$("#light").css("display","block");
		$("#fade").css("display","block");
  		$.post("/user/doLogin.html",
			{userName:$("#userName").val(),password:$("#password").val(),checkCode:$("#checkCode").val()},
  			function(data){
  				if(data.message!="success"){
					$("#light").css("display","none");
					$("#fade").css("display","none");
					showInfo(data.message);
					changeCode();
				}else{
					window.location.href=data.urlAddress;
				}
  			}
  		);
}

function closes(){
	ownerDialog.close();
	
}
/**
 * 验证登录数据
 */
function checkData(){
	if($("#userName").val()==''){
		showInfo("请输入：用户名");
		return false;
	}
	if($("#password").val()==''){
		showInfo("请输入：密码");
		return false;
	}
	if($("#checkCode").val()==''){
		showInfo("请输入：验证码");
		return false;
	}
	return true;
}
/**
 * 弹出框的回调函数，继续弹出登陆框
 */
function goLogin(){
	showPage('用户登录','/user/loginAction.html',380,245);
}
/**
 * 退出系统
 */
function logOut(){
	showConfirm("确定要退出系统吗？",
				function(){
  					$.post("/user/logoutAction.html",{},
  					function(result){
						$("#userInfoTd").html('<a href="javascript:goLogin()"><font style="font-size:12pt; color:#7acbf4">用户登录</font></a>');
					}
  				);
  	});

}
/**
 * 弹出找回密码框
 */
function forgetPassword(){
	showPage('找回密码','/userService/forgetPassword.html',400,240);
}
/**
 * 弹出注册对话框
 */
function userRegister(){
	showPage('用户注册','/userService/userRegister.html',1000,700);
}
