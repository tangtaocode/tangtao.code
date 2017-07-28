<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/static/common/head.jsp"%>
<title>登录页面</title>
<style type="text/css">
body {
	background: #f6f6f6
}

body,td,th {
	font-family: "Lucida Grande", "Lucida Sans Unicode", Arial, Verdana,
		sans-serif;
	color: #333;
	font-size: 12px;
}

#box {
	width: 500px;
}

#bigbox {
	width: 940px;
}

#bigbox,#box {
	margin: 30px;
	padding: 0;
	border: thin black solid;
	background: #fff
}

#bigbox .title,#box .title {
	display: block;
	margin: 0 0 5px 0;
	padding: 5px 5px 5px 15px;
	background: #ddd;
	font-size: 18px
}

#bigbox .title .info {
	float: right;
	padding-right: 10px;
	font-size: 12px
}

.errors {
	color: red;
	padding: 10px 0 10px 0;
}

.form-label {
	float: left;
	width: 100px;
}

.content {
	padding: 10px;
}

.content div {
	padding: 5px 0 5px 0
}

#manageUsers {
	width: 800px
}

#manageUsers th {
	text-align: left
}

a:active,a:visited,a:link {
	color: #6666DD;
	text-decoration: none;
}

a:hover {
	color: #6666FF;
	text-decoration: underline;
}

.clearfix:after {
	content: ".";
	display: block;
	height: 0;
	clear: both;
	visibility: hidden;
}

.clearfix {
	display: inline-block;
}

* html .clearfix {
	height: 1%;
}

.clearfix {
	display: block;
}
</style>
</head>
<body>
	<div id="box">
		<div class="title">用户登录</div>

		<div class="content">
			<form:form modelAttribute="loginCommand">

				<form:errors path="*" element="div" cssClass="errors" />

				<div>
					<div class="form-label">名称:</div>
					<form:input path="username" />
				</div>
				<div>
					<div class="form-label">密码:</div>
					<form:password path="password" />
				</div>
				<div>
					<div class="form-label">租户:</div>
					<form:input path="tenantId" />
				</div>

				<div>
					<input type="submit" value="登录" />
				</div>
			</form:form>
		</div>
	</div>

	<script type="text/javascript">
        if (top.location != self.location) {
            top.location = self.location;
        }
        document.getElementById('username').focus();
    </script>

</body>
</html>
