<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>
<head>
<meta http-equiv=Content-Type content="text/html;charset=utf-8">
<meta http-equiv=X-UA-Compatible content="IE=edge">
<title>编辑事项简码</title>
	
</script>
</head>
<body>	
	<div>
		<form action="" id="form" method="post" align="center">
			<input name="itemid" id="itemid" value="${itemid }" type="hidden" ></input>
			<input name="method" id="method" value="${method }" type="hidden" ></input>	
		<table>
			<tr>
				<td align="center">
					部门名称：
				</td>
				<td>
					<input name="bureauname" id="bureauname" value="${bureauname }"></input>
				</td>
			</tr>
			<tr>
				<td align="center">
					事项名称：
				</td>
				<td>
					<input name="approveitemname" id="approveitemname" value="${approveitemname }"></input>
				</td>
			</tr>
			<tr>
				<td align="center">
					事项简码：
				</td>
				<td>
					<input name="shortcode" id="shortcode" value="${shortcode }"></input>
				</td>
			</tr>
			
		</table>
		</form>
	</div>	

<script type="text/javascript">
</script>	
</body>
</html>
