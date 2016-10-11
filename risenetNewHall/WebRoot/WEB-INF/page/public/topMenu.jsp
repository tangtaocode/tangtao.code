<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon"/>
<script type="text/javascript" src="/js/login.js"></script>
<script src="/js/Scripts/jquery.min.js"></script>
<script type="text/javascript" src="/js/zDialog/risenetDialog.js"></script>
<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
#menuNav tr td table tr td a{
	font-family: "微软雅黑";
	font-size: 12pt;
	text-decoration: none;
	font-weight:bold;
	color: #FFFfff;	
	width: 100px;
	line-height: 35px;
	cursor:hand;
}
.menuOff{
	font-family: "微软雅黑";
	font-size: 12pt;
	text-decoration: none;
	font-weight:bold;
	color: #FFFfff;	
	background: url(/images/share/menubg.jpg) no-repeat 0px 0px;
	width: 100px;
	line-height: 35px;
}
a:hover div{
	font-family: "微软雅黑";
	text-decoration: none;
	font-size: 12pt;
	font-weight:bold;
	width:100px;
	line-height: 35px;
	background: url(/images/share/menubg1.jpg) no-repeat;  
	height:35px;
}
.menuOn{
	font-family: "微软雅黑";
	text-decoration: none;
	font-size: 12pt;
	font-weight:bold;
	width:100px;
	line-height: 35px;
	background: url(/images/share/menubg1.jpg) no-repeat;  
	height:35px;
}
</style>

<%String m = request.getParameter("menu"); %>
</head>
<body>
<table width="1000" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="37" background="/images/share/topbanner1.jpg">
    
    <table width="1000" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="340" height="36" align="center" valign="middle">
         <table width="340" height="21" border="0" cellpadding="0" cellspacing="0">
          <tr>
			<!--  
            <td width="165" align="left" valign="middle"><a href="http://wsbs.sz.gov.cn/shenzhen/home"  class="topmenuword"><font color="#FFFFFF" style="font-size:12pt; color:#7acbf4">返回市办事大厅</font></a></td>
            -->
            <td width="35" height="30" align="center" valign="top"><img src="/images/share/home.png" border="0" /></td>
            <td align="left" valign="middle"><a href="/onlineService/initOnlineService.html"  class="topmenuword"><font color="#FFFFFF" style="font-size:12pt; color:white; font-weight:bold;">网上申报首页</font></a></td>
			<td width="35" height="30" align="center" valign="top"><img src="/images/share/home.png" border="0" /></td>
			<td align="left" valign="middle"><a href="http://www.cb.gov.cn/"  class="topmenuword"><font color="#FFFFFF" style="font-size:12pt; color:white; font-weight:bold;">网站首页</font></a></td>
		  </tr>
         </table>
        </td>
        <td width="501" align="center" valign="middle">
	<%--住建局隐藏除网上申报的菜单 <table width="500" border="0" cellspacing="0" cellpadding="0" id="menuNav">
          <tr>
             <td width="100" height="36" align="center" valign="middle"><table width="100" border="0" cellspacing="0" cellpadding="0" style="border-right:#FFFfff solid 2px;">
              <tr>
                <td height="25" align="center"  valign="middle">
                <a href="http://www.szjs.gov.cn" target="_blank" >
					<div class="menuOff">部门首页</div>
				</a>
                </td>
              </tr>
            </table></td>
            
            <td width="100" height="36" align="center" valign="middle"><table width="100" border="0" cellspacing="0" cellpadding="0" style="border-right:#FFFfff solid 2px;">
              <tr>
                <td height="25" align="center"  valign="middle">
                <a href="/govpublic/initIndexPage.html" target="_self" >
					<div class="<%if("1".equals(m)){out.print("menuOn");}else{out.print("menuOff");} %>">政务公开</div>
				</a>
                </td>
              </tr>
            </table></td>
            
            <td width="100" align="center" valign="middle"><table width="100" border="0" cellspacing="0" cellpadding="0" style="border-right:#FFFfff solid 2px;">
              <tr>
                <td height="25" align="center"  valign="middle" > 
                <a href="/investment/investmentInitAction.html" target="_self" >
                <div class="<%if("2".equals(m)){out.print("menuOn");}else{out.print("menuOff");} %>">投资审批</div></a></td>
              </tr>
            </table></td>
            
            <td width="100" align="center" valign="middle" ><table width="100" border="0" cellspacing="0" cellpadding="0" style="border-right:#FFFfff solid 2px;">
              <tr>
                <td height="25" align="center"  valign="middle" >
                <a href="/onlineService/initOnlineService.html" target="_self" >
                <div class="<%if("3".equals(m)){out.print("menuOn");}else{out.print("menuOff");} %>" >网上办事</div></a></td>
              </tr>
            </table></td>
            <td width="100" align="center" valign="middle"><table width="100" border="0" cellspacing="0" cellpadding="0" style="border-right:#FFFfff solid 2px;">
              <tr>
                <td height="25" align="center"  valign="middle" >
                <a href="/interaction/interactionAction.html" target="_self" >
                <div class="<%if("4".equals(m)){out.print("menuOn");}else{out.print("menuOff");} %>">政民互动</div></a></td>
              </tr>
            </table></td>
            <td width="100" align="center" valign="middle"><table width="100" border="0" cellspacing="0" cellpadding="0" style="border-right:#FFFfff solid 2px;">
              <tr>
                <td height="25" align="center"  valign="middle" >
                <a href="/supervise/superviseDefault.html" target="_self" >
                <div class="<%if("5".equals(m)){out.print("menuOn");}else{out.print("menuOff");} %>">效能监察</div></a></td>
              </tr>
            </table></td>
          </tr>
        </table> --%>
		</td>
        <td width="150px" align="center" valign="middle"><table width="150px" height="21" border="0" cellpadding="0" cellspacing="0">
          <tr>
          <s:if test="#session.loginUser!=null">
          <td width="20px" height="30" align="right" valign="middle"><img src="/images/login/user3.png"  width="16" height="16" alt="<s:property value="userString"/>" border="0" /></td>
            <td width="130px" align="left" valign="middle" id="userInfoTd"><a href="/businessfollow/initBusinessfollow.html"><font style="font-size:12pt; color:#7acbf4">办事跟踪</font></a>|<a style="font-size:12pt; color:#FF0005" href="/user/logoutAction.html">注销</a></td>
          </s:if>
          <s:else>
          <td width="20px" height="30" align="right" valign="middle"><img src="/images/share/login.png" alt="" border="0" /></td>
            <td width="130px" align="left" valign="middle" id="userInfoTd"><a style="font-size:12pt; color:#7acbf4" href="/user/loginAction.html">用户登录</a><span style="font-size:12pt; color:#7acbf4">|</span><a style="font-size:12pt; color:#7acbf4" href="/userService/userRegister.html">注册</a></td>
          </s:else>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="110" width="1000" align="right" valign="bottom" style="background-image: url('/images/share/top_zj.gif')">
    <!-- <table width="467" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="359" height="36" align="left" valign="bottom">&nbsp;</td>
        <td width="108" align="left" valign="bottom">&nbsp;</td>
      </tr>
    </table> -->
   	</td>
  </tr>
</table>
</body>
</html>
