/**
 * 获取手机验证码
 */
function getCheckCode(){
	var reg =  /(^0{0,1}1[3|4|5|6|7|8|9][0-9]{9}$)/;
	var mobile = $("#mobile").val();
	if(mobile==""){
		Dialog.alert("手机号码不能为空",function(){
				changeInputBg($("#mobile"));
		});
		return;
	}
	if(!reg.exec(mobile)){
 		Dialog.alert("手机号码输入有误,请重新输入",function(){
				changeInputBg($("#mobile"));
		});
		return;
	}else{
		/*UserDwrJs.sendmobileMessage($("#mobile").val(),{callback:function(isSuccess){
			if(isSuccess){
				Dialog.alert("验证码发送成功，请注意查收。");
			}else{
				Dialog.alert("验证码发送失败。");
			}
		}});*/
		UserDwrJs.sendWsdlMobileMessage($("#mobile").val(),{callback:function(isSuccess){
			if(isSuccess){
				Dialog.alert("验证码发送成功，请注意查收。");
			}else{
				Dialog.alert("验证码发送失败。");
			}
		}});
	}
	
}
/**
 * 验证用户名
 * @param {Object} type 1找回密码验证，2用户注册验证
 */
function checkUserName(type){
	var loginName = $("#loginname").val();
	if(loginName!=""&&type=="1"){
		UserDwrJs.findUserByLoginName(loginName,{callback:function(isExist){
		if(!isExist){
			Dialog.alert("用户："+loginName+" 不存在，请重新输入。",function(){
				changeInputBg($("#loginname"));
			});
		}
		}});
	}else if(type=="2"){
		if(loginName==""){
			$("#userMessage").html("<font color='red' style='font-weight:bold;'>登录名不能为空</font>");
			return;
		}
		var re = "^\\w+$";
		if(!loginName.match(re)){
			Dialog.alert("登录名："+loginName+" 格式输入错误。",function(){
				changeInputBg($("#loginname"));
				$("#userMessage").html("<font color='red' style='font-weight:bold;'>格式错误</font>");
			});
			return;
		}
		if (loginName.length <3){
			Dialog.alert("登录名："+loginName+" 长度小于3。",function(){
				changeInputBg($("#loginname"));
				$("#userMessage").html("<font color='red' style='font-weight:bold;'>登陆名长度不能小于3</font>");
			});
			return;
		}
		
		UserDwrJs.findUserByLoginName(loginName,{callback:function(isExist){
		if(isExist){
				//Dialog.alert("登录名："+loginName+" 已存在，请重新输入。",function(){
				//changeInputBg($("#loginname"));
				$("#userMessage").html("<span style='color:red'>登录名："+loginName+" 已存在,请重新输入</span>");
				$("#submitUser").attr("disabled",true);

			//});
		}else{
			$("#userMessage").html("<font color='green' style='font-weight:bold;'>用户名可用</font>");
			$("#submitUser").attr("disabled",false);
		}
		}});
	}
}
/**
 * 改变input底色
 * @param {Object} obj input框对象
 */
function changeInputBg(obj){
	obj.focus();
	obj.attr("class","errorInput");
	obj.attr("onclick","setClass(this)");	
}
/**
 * 找回密码提交
 */
function doSubmit(){
	if(!validations("submitForm"))return false;
	$("#submitForm").submit();
}
/**
 * 焦点事件，判断密码框是否为空
 */
function checkPwdIsNull(){
	if($("#password").val()==""){
		$("#passMessage").html("<font color='red' style='font-weight:bold;'>密码不能为空</font>");
		return;
	}else{
		$("#passMessage").html("");
	}
}
/**
 * 判断二次密码输入是否为空，与第一次输入是否相同
 */
function checkPasswd(){
	if($("#passwordRepeat").val()==""){
		$("#rpassMessage").html("<font color='red' style='font-weight:bold;'>重复密码不能为空</font>");
		return;
	}
	if($("#password").val()!=$("#passwordRepeat").val()&&$("#passwordRepeat").val()!=""){
		Dialog.alert("两次输入密码不一致，请重新输入。",function(){
				$("#passwordRepeat").attr("value","");
				$("#passwordRepeat").focus();
		});
	}else{
		$("#rpassMessage").html("");
	}
}
/**
 * 打开服务条款
 */
function openServiceItems(){
	$("#registbefore").css("display","");
	$("#registNext").css("display","none");
	checkTime();
}
/**
 * 关闭服务条款，打开注册页
 */
function showRegistDiv(){
	$("#registbefore").css("display","none");
	$("#registNext").css("display","");
	$("#agreeLaw").attr("checked",true);
}

	var sec=10;
	var secs = 9;
	var wait = secs * 1000;
/**
 * 监测时间，按钮倒计时
 */
function checkTime(){
	$("#regButton").attr("value","我同意 [" + secs + "]");
	$("#regButton").attr("disabled",true);
	for(i = 1; i <= secs; i++){
      window.setTimeout("Update(" + i + ")", i * 1000);
	}
	window.setTimeout("Timer()", wait);
}
/**
 * 更新按钮
 * @param {Object} num 等待时间
 */
function Update(num){
   if(num != secs){
       printnr = (wait / 1000) - num;
	   $("#regButton").attr("value","我同意 [" + printnr + "]");
   }
}
 /**
  * 倒计时完成，更新按钮状态和按钮名称
  */                    
function Timer(){
	$("#regButton").attr("value","我同意");
	$("#regButton").attr("disabled",false);
}

/**
 * 根据账号类型控制证件类型
 * @param {Object} obj
 */
function changeCardByUser(obj){
	//如果为个人
	if(obj.value=="1"){
		$("input:radio[name='userInfo.cardtype']").get(0).disabled = false;
		$("input:radio[name='userInfo.cardtype']").get(1).disabled = false;
		$("input:radio[name='userInfo.cardtype']").get(2).disabled = true;
		$("input:radio[name='userInfo.cardtype']").attr("checked",false);
		$("#nameTypeText").html("姓名：");
		$("#cardTypeText").html("证件号码：");
	}else if(obj.value=="3"){
		$("input:radio[name='userInfo.cardtype']").attr("disabled",false);
		$("input:radio[name='userInfo.cardtype']").attr("checked",false);
		$("#nameTypeText").html("企业/单位名称：");
		$("#cardTypeText").html("组织机构代码：");
	}else{
		$("input:radio[name='userInfo.cardtype']").get(0).disabled = true;
		$("input:radio[name='userInfo.cardtype']").get(1).disabled = true;
		$("input:radio[name='userInfo.cardtype']").get(2).disabled = false;
		$("input:radio[name='userInfo.cardtype']").get(2).checked = true; 
		$("#nameTypeText").html("企业/单位名称：");
		$("#cardTypeText").html("组织机构代码：");
	}
}
/**
 * 根据证件类型控件证件证件显示text
 * @param {Object} obj
 */
function chageCardText(obj){
	var usertype = $("input:radio[name='userInfo.usertype']:checked").val();
	if(!usertype){
		Dialog.alert("请先选择账号类型！");
		$("input:radio[name='userInfo.cardtype']").attr("checked",false); 
		return;
	}
	if(obj.value=="1"){
		$("#cardTypeText").html("身份证号：");
	}else if(obj.value=="2"){
		$("#cardTypeText").html("护照号码：");
	}else if (obj.value == "3"){
		$("#cardTypeText").html("组织机构代码：");
	}
}
/**
 * 检测证件号码是否重复
 */
function checkCard(){
	var card = $("#cardid").val();
	var cardType = $("input:radio[name='userInfo.cardtype']:checked").val();
	var text = $("#cardTypeText").html();
	if(card==""){
		$("#cardidMessage").html("<font color='red' style='font-weight:bold;'>"+text+"不能为空</font>");
		return;
	}
	if(!cardType){
		Dialog.alert("请先选择证件类型！");
		return;
	}
	UserDwrJs.checkCardNum(card,cardType,{callback:function(isExist){
		if(isExist){
			Dialog.alert(text+"："+card+" 已存在，请重新输入。",function(){
				changeInputBg($("#cardid"));
				$("#cardidMessage").html("<font color='red' style='font-weight:bold;'>"+text+"不可用</font>");
			});
		}else{
			$("#cardidMessage").html("<font color='green' style='font-weight:bold;'>"+text+"可用</font>");
		}
	}});
}
/**
 * 检测验证码是否输入错误
 */
function checkMobileNum(){
	var checkNum = $("#phoneCheckNum").val();
	if(checkNum!=""){
		UserDwrJs.checkMobileCheckNum(checkNum,{callback:function(isSame){
		if(!isSame){
			Dialog.alert("验证码输入错误，请重新输入。",function(){
				changeInputBg($("#phoneCheckNum"));
			});
		}
		}});
	}
}
/**检查姓名*/
function checkNameType(){
	var userType = $("input:radio[name='userInfo.usertype']:checked");
	var comuser = $("#comuser");
	var comuserMessage = $("#comuserMessage");
	var reg = /^[\u4e00-\u9fa5]+$/;
	if (userType.val() != undefined){
		if (userType.val() == "1"){
			if (!reg.test(comuser.val())){
				comuserMessage.html("<font style='color: red;font-weight:bold;'>姓名：不能为空，只能输入中文，且不能包含空格及特殊字符！</font>");
				//comuser.focus();
				return false;
			}
		}
	}
	comuserMessage.html("");
	return true;
}

/**
 * 提交用户注册信息
 */
function submitUserInfo(){
	if(!validations("submitForm"))return false;
	if(!checkRadio("userInfo.usertype","账号类型"))return false;
	if(!checkRadio("userInfo.cardtype","证件类型"))return false;
	if(!checkRadio("serviceCheckBox","同意服务条款"))return false;
	if(!checkNameType()){
		Dialog.alert("姓名：不能为空，只能输入中文，且不能包含空格及特殊字符！");
		$("#comuser").focus();
		return false;
	}
	var formData = $("#submitForm").serializeArray();
	$.post("/userService/userRegister.html",formData,function(data){
		if(data.message=="1"){
			Dialog.alert("注册成功，请登录。",function(){
				window.location.href=data.url;
			});
		}else{
			Dialog.alert("注册失败，请重新注册。"+data.message,function(){
				window.location.href=data.url;
			});
		}
	},"json");
}
/**
 * 修改密码
 */
function checkPassword(){
	var oldPwd = $("#oldPassword").val();
	var guid = $("#guid").val();
	if(oldPwd!=""){
		UserDwrJs.checkOldPassword(oldPwd,guid,{callback:function(isTrue){
			if(!isTrue){
				Dialog.alert("旧密码输入错误。",function(){
					changeInputBg($("#oldPassword"));
				});
			}
		}});
	}
}
/**
 * 根据模块名称异步加载用户修改页面
 * @param {Object} modelType 模块名称
 */
function editUser(modelType){
	$.post("/userService/editUserInfo.html",{appModel:modelType},function(data){
		$("#stepPageCount").html(data);
		$("#nextStepBut").css("display","none");
	});
}
/**
 * 修改用户信息
 */
function submitEditUser(){
	if(!validations("editUserDataForm"))return false;
	var formData = $("#editUserDataForm").serializeArray();
	$.post("/userService/editUserInfo.html",
			formData,
			function(data){
				if(data.message=="0"){
					Dialog.alert("修改失败！");
				}else{
					$("#stepPageCount").html(data);	
					$("#nextStepBut").css("display","block");		
				}
			});
}

function busiFlow(page){
	$.post('/businessfollow/initBusinessfollow.html',{'query':'true','page':page},function(data){
		$("#stepPageCount").html(data);
	});
}
function changePwd(){
	$.post('/userService/editPassword.html',{},function(data){
		$("#stepPageCount").html(data);
	});
}
