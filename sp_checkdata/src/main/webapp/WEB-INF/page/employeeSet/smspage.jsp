<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- <link href="/css/sms.css" rel="stylesheet" type="text/css" /> -->
<link href="/css/mstyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../../js/sms.js" ></script>
<script type="text/javascript" src="../../js/jquery-1.8.1.min.js"></script>
<title>短信设置</title>
<link />
</head>
<script type="text/javascript">
$(document).ready(function(){
	smsPage(1,7);
});
</script>
<body>
<!--表显示部分 -->
 <div class="dxsz" id="smstable"> 
	      <div class="grsz_title"><span>&nbsp;&nbsp;短信设置</span><a href="javascript:setsms();" class="grsz_title_a">保存</a></div>
	    <div class="dxsz_ss">
	      <table width="96%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td align="right">所属部门：</td>
              <td><input id="department" type="text" class="dxsz_ss01" />
              </td>
              <td align="right">人员姓名：</td>
              <td><input id="employeename" type="text" class="dxsz_ss01" maxlength="20" /></td>
              <td><input id="search" onclick="javascript:search();"  type="button" class="dxsz_ss02" value="查询" />
              <input type="hidden" id="curentpage"/>
              </td>
            </tr>
          </table>
	    </div>
  </div>
</body>
</html>