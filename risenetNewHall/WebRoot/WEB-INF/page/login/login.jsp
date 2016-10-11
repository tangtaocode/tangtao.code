<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="net.risesoft.util.ValidatorUtil"%>
<%@page import="net.risesoft.common.Common"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
//获取需要跳转的url
String toUrl = (String)session.getAttribute(Common.loginSuUrl);

//添加以下代码 处理会话标识未更新漏洞
session.invalidate();//清空session
Cookie cookie = request.getCookies()[0];//获取cookie 
cookie.setMaxAge(0);//让cookie过期 ；

//设置跳转的url
request.getSession().setAttribute(Common.loginSuUrl,toUrl);
%>
<html>
	<head>
		<title>广东省网上办事大厅深圳市龙岗区住建局窗口-用户登录</title>
		<link href="/css/login-box.css" rel="stylesheet" type="text/css" />
		<script src="/js/Scripts/jquery.min.js" type="text/javascript"></script>
		<script src="/js/zDialog/risenetDialog.js" type="text/javascript"></script>
		<script type="text/javascript">
			function do_jpassword(){
			var obj  = document.getElementById("j_password");
			obj.value=obj.value.replace(/./g,'*');	
			var pass=document.getElementById("password");
			pass.value = pass.value.substring(0,obj.value.length);
		}
		function hiddenPass(e){
			var keynum;
			var keychar;
			var pass=document.getElementById("password");
			var j_pass=document.getElementById("j_password");
			if(window.event) // IE
			{
				keynum = e.keyCode
			}
			else if(e.which) // Netscape/Firefox/Opera
			{
				keynum = e.which
			}
			keychar = String.fromCharCode(keynum);
			pass.value = pass.value+keychar;
		}
		
		</script>
	</head>
	<body
		style="background-color: #fff; background-image: url('/images/govpublic/background.jpg'); background-position: center top; background-repeat: repeat-y; background-attachment: fixed;">
		<div id="pagecontainer" style="width: 1000px; margin: 0 auto">
			<div class="SD_Top ">
				<jsp:include page="/WEB-INF/page/public/topMenu.jsp" />
			</div> 
		</div>
		<div class="GovOpenIndex ">
			<table border="0" cellpadding="0" cellspacing="0"
				style="width: 1000px; heigth: 100%" align="center">
				<tr>
					<td valign="top" align="center" height="155px">
						<!-- begin: 顶部面板 -->
						<div id="Portal_Banner">
							<table border="0" cellpadding="0" cellspacing="20px"
								style="width: 100%;">
								<tr>
									<td align="left">
										<span style="font-size: 18pt;">用户登录</span>
									</td>
									<td align="right">
										&nbsp;
									</td>
								</tr>
							</table>
						</div>
						<!-- end: 标题面板 -->
						<!-- begin: 内容面板 -->
						<div id="Portal_Content">
							<table style="width: 960px;" cellpadding="0px" cellspacing="0px"
								border="0px">
								<tr>
									<td colspan="3">
										<img src="/images/govpublic/shadow_T.png" />
									</td>
								</tr>
								<tr>
									<td width="5px" height="300px"
										style="background-image: url(/images/govpublic/shadow_R.png); background-repeat: repeat-y;">
									</td>
									<td valign="top" style="width: 956px;">

										<table cellpadding="0px" cellspacing="0px" border="0px"
											id="login-box" width="98%">
											<tr>
												<th>
													用户名：
												</th>
												<td>
													<s:textfield cssClass="loginInput" name="userName"
														id="userName" title="用户名" size="20"
														onblur="this.className='inputOut'"
														onfocus="this.className='inputOn'"></s:textfield>
												</td>
											</tr>
											<tr>
												<th>
													密&nbsp;&nbsp;&nbsp;码：
												</th>
												<td>
												<s:hidden  id="rpassword" name="rpassword" /> 
												<s:hidden  id="password" name="password" /> 
												<input type="text" id="j_password" name="j_password"  class="loginInput" 
			    									onkeypress="hiddenPass(event);" onkeyup="do_jpassword();"
			    									oncopy="return false" onpaste="return false"
			    									value="<%=ValidatorUtil.filter((String)request.getAttribute("password")) %>"/>
			    									<!-- 
													<s:password cssClass="loginInput" name="password"
														id="password" title="密码" size="20"
														onblur="this.className='inputOut'"
														onfocus="this.className='inputOn'"></s:password> -->
												</td>
											</tr>
											<tr>
												<th>
													验证码：
												</th>
												<td>
													<s:textfield cssClass="loginInput"
														onkeypress="if(event.keyCode == 13)doLogin(); "
														name="checkCode" id="checkCode" title="验证码" size="10"
														onblur="this.className='inputOut'"
														onfocus="this.className='inputOn'"></s:textfield>
													&nbsp;&nbsp;
													<a href="#"><img onclick="changeCode()"
															id="checkCodeImg" border="0"
															src="/servlet/CheckCodeServlet?<%=Math.random()%>" />
												</td>
											</tr>
											<tr>
												<td colspan="2" style="text-align: center">
													<input onclick="doLogin()" type="button" value="登录"
														class="submit">
													&nbsp;&nbsp;&nbsp;
													<input type="button"
														onclick="window.location.href='/userService/userRegister.html'"
														value="注册" class="regist">
												</td>
											</tr>
										</table>
									</td>
									<td width="2px" height="300px"
										style="background-image: url(/images/govpublic/shadow_R.png); background-repeat: repeat-y;">
										<img alt="" class="rich-spacer " height="1" id="form:j_id59"
											src="/a4j/g/3_3_2.SR1images/spacer.gif" width="2px" />
									</td>

								</tr>
								<tr>
									<td colspan="3">
										<img src="/images/govpublic/shadow_B.png" />
									</td>
								</tr>
							</table>

						</div>
						<!-- end: 内容面板 -->
					</td>

				</tr>
				<tr>
					<td align="left"></td>
				</tr>

			</table>
			<jsp:include page="/WEB-INF/page/public/bottom.jsp"></jsp:include>
		</div>
		<div id="light" class="white_content"></div> 
        <div id="fade" class="black_overlay"></div> 
	</body> 
</html>